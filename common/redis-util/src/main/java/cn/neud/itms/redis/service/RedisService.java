package cn.neud.itms.redis.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.neud.itms.model.base.ExpiredData;
import cn.neud.itms.redis.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Slf4j
@Component
public class RedisService {

    private final StringRedisTemplate stringRedisTemplate;

    private static final ThreadPoolExecutor CACHE_REBUILD_EXECUTOR = new ThreadPoolExecutor(
            10, 10, 10L, TimeUnit.MILLISECONDS,
            new java.util.concurrent.ArrayBlockingQueue<>(1000),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @Resource
    private RedissonClient redissonClient;

    public RedisService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    // 缓存雪崩：随机过期时间 N+n
    public void set(String key, Object value, Long time, TimeUnit unit) {
        // 随机过期时间，避免缓存同时过期
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time + (int) (Math.random() * 60 * 60), unit);
    }

    // 缓存击穿：逻辑过期
    public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {
        // 设置逻辑过期
        ExpiredData expiredData = new ExpiredData();
        expiredData.setData(value);
        expiredData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
        // 写入Redis
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(expiredData));
    }

    // 缓存穿透：缓存空值
    public <R, ID> R queryWithPassThrough(
            String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit) {
        String key = keyPrefix + id;
        // 1.从redis查询场景缓存
        String json = stringRedisTemplate.opsForValue().get(key);
        // 2.判断是否存在
        if (StrUtil.isNotBlank(json)) {
            // 3.存在，直接返回
            return JSONUtil.toBean(json, type);
        }
        // 判断命中的是否是空值
        if (json != null) {
            // 返回一个错误信息
            return null;
        }

        // 4.不存在，根据id查询数据库
        R r = dbFallback.apply(id);
        // 5.不存在，返回错误
        if (r == null) {
            // 将空值写入redis
            stringRedisTemplate.opsForValue().set(key, "", RedisConstant.CACHE_NULL_TTL, TimeUnit.MINUTES);
            // 返回错误信息
            return null;
        }
        // 6.存在，写入redis
        this.set(key, r, time, unit);
        return r;
    }

    // breakdown
    // 缓存击穿：逻辑过期
    public <R, ID> R queryWithLogicalExpire(
            String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit) {
        String key = keyPrefix + id;
        // 1.从redis查询场景缓存
        String json = stringRedisTemplate.opsForValue().get(key);
        // 2.判断是否存在
        if (StrUtil.isBlank(json)) {
            // 3.不存在，直接返回
            return null;
        }
        // 4.命中，需要先把json反序列化为对象
        ExpiredData expiredData = JSONUtil.toBean(json, ExpiredData.class);
        R r = JSONUtil.toBean((JSONObject) expiredData.getData(), type);
        LocalDateTime expireTime = expiredData.getExpireTime();
        // 5.判断是否过期
        if (expireTime.isAfter(LocalDateTime.now())) {
            // 5.1.未过期，直接返回场景信息
            return r;
        }
        // 5.2.已过期，需要缓存重建
        // 6.缓存重建
        // 6.1.获取互斥锁
//        String lockKey = RedisConstant.LOCK_TRACE_KEY + id;
//        boolean isLock = tryLock(lockKey);
        // 创建锁对象
        RLock redisLock = redissonClient.getLock(RedisConstant.LOCK_TRACE_KEY + id);
        // 尝试获取锁
        boolean isLock = redisLock.tryLock();
        // 6.2.判断是否获取锁成功
        if (isLock) {
            // 6.3.成功，开启独立线程，实现缓存重建
            CACHE_REBUILD_EXECUTOR.submit(() -> {
                try {
                    // 查询数据库
                    R newR = dbFallback.apply(id);
                    // 重建缓存
                    this.setWithLogicalExpire(key, newR, time, unit);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    // 释放锁
//                    unlock(lockKey);
                    redisLock.unlock();
                }
            });
        }
        // 6.4.返回过期的场景信息
        return r;
    }

    // 缓存击穿：互斥锁
    public <R, ID> R queryWithMutex(
            String keyPrefix, ID id, Class<R> type, Function<ID, R> dbFallback, Long time, TimeUnit unit) {
        String key = keyPrefix + id;
        // 1.从redis查询场景缓存
        String traceJson = stringRedisTemplate.opsForValue().get(key);
        // 2.判断是否存在
        if (StrUtil.isNotBlank(traceJson)) {
            // 3.存在，直接返回
            return JSONUtil.toBean(traceJson, type);
        }
        // 判断命中的是否是空值
        if (traceJson != null) {
            // 返回一个错误信息
            return null;
        }

        // 4.实现缓存重建
        // 4.1.获取互斥锁
//        String lockKey = RedisConstant.LOCK_TRACE_KEY + id;
//        boolean isLock = tryLock(lockKey);
        // 创建锁对象
        RLock redisLock = redissonClient.getLock(RedisConstant.LOCK_TRACE_KEY + id);
        // 尝试获取锁
        boolean isLock = redisLock.tryLock();
        R r = null;
        try {
            // 4.2.判断是否获取成功
            if (!isLock) {
                // 4.3.获取锁失败，休眠并重试
                Thread.sleep(50);
                return queryWithMutex(keyPrefix, id, type, dbFallback, time, unit);
            }
            // 4.4.获取锁成功，根据id查询数据库
            r = dbFallback.apply(id);
            // 5.不存在，返回错误
            if (r == null) {
                // 将空值写入redis
                stringRedisTemplate.opsForValue().set(key, "", RedisConstant.CACHE_NULL_TTL, TimeUnit.MINUTES);
                // 返回错误信息
                return null;
            }
            // 6.存在，写入redis
            this.set(key, r, time, unit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 7.释放锁
//            unlock(lockKey);
            redisLock.unlock();
        }
        // 8.返回
        return r;
    }

}
