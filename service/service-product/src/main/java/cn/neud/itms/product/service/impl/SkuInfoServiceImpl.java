package cn.neud.itms.product.service.impl;

import cn.neud.itms.common.exception.ItmsException;
import cn.neud.itms.common.result.ResultCodeEnum;
import cn.neud.itms.model.product.*;
import cn.neud.itms.mq.constant.MqConstant;
import cn.neud.itms.mq.service.RabbitService;
import cn.neud.itms.product.mapper.SkuInfoMapper;
import cn.neud.itms.product.service.*;
import cn.neud.itms.redis.constant.RedisConstant;
import cn.neud.itms.vo.product.SkuInfoQueryVo;
import cn.neud.itms.vo.product.SkuInfoVo;
import cn.neud.itms.vo.product.SkuStockLockVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author david
 * @since 2023-04-04
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {

    //sku图片
    @Autowired
    private SkuImageService skuImageService;

    //sku平台属性
    @Autowired
    private SkuAttrValueService skuAttrValueService;

    //sku海报
    @Autowired
    private SkuPosterService skuPosterService;

    @Autowired
    private SkuWareService skuWareService;

    @Autowired
    private RabbitService rabbitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    //添加商品sku信息
    @Override
    public void saveSkuInfo(SkuInfoVo skuInfoVo) {
        //1 添加sku基本信息
        // SkuInfoVo 复制-- SkuInfo
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        baseMapper.insert(skuInfo);

        //2 保存sku海报
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            //遍历，向每个海报对象添加商品skuId
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuInfo.getId());
            }
            skuPosterService.saveBatch(skuPosterList);
        }

        //3 保存sku图片
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImageList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            for (SkuImage skuImage : skuImagesList) {
                //设置商品skuId
                skuImage.setSkuId(skuInfo.getId());
            }
            skuImageService.saveBatch(skuImagesList);
        }

        //4 保存sku平台属性
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                //设置商品skuId
                skuAttrValue.setSkuId(skuInfo.getId());
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }
    }

    //获取sku信息
    @Override
    public SkuInfoVo getSkuInfo(Long id) {
        SkuInfoVo skuInfoVo = new SkuInfoVo();

        //根据id查询sku基本信息
        SkuInfo skuInfo = baseMapper.selectById(id);

        //根据id查询商品图片列表
        List<SkuImage> skuImageList = skuImageService.getImageListBySkuId(id);

        //根据id查询商品海报列表
        List<SkuPoster> skuPosterList = skuPosterService.getPosterListBySkuId(id);

        //根据id查询商品属性信息列表
        List<SkuAttrValue> skuAttrValueList = skuAttrValueService.getAttrValueListBySkuId(id);

        //根据id查询商品库存信息列表
        List<SkuWare> skuWareList = skuWareService.getSkuWareListBySkuId(id);

        //封装所有数据，返回
        BeanUtils.copyProperties(skuInfo, skuInfoVo);
        skuInfoVo.setSkuImageList(skuImageList);
        skuInfoVo.setSkuPosterList(skuPosterList);
        skuInfoVo.setSkuAttrValueList(skuAttrValueList);
        skuInfoVo.setSkuWareList(skuWareList);
        return skuInfoVo;
    }

    //修改sku
    @Override
    public void updateSkuInfo(SkuInfoVo skuInfoVo) {
        // 修改sku基本信息
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo, skuInfo);
        baseMapper.updateById(skuInfo);

        Long skuId = skuInfoVo.getId();
        // 海报信息
        skuPosterService.remove(new LambdaQueryWrapper<SkuPoster>().eq(SkuPoster::getSkuId, skuId));
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if (!CollectionUtils.isEmpty(skuPosterList)) {
            // 遍历，向每个海报对象添加商品skuId
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuId);
            }
            skuPosterService.saveBatch(skuPosterList);
        }

        // 商品图片
        skuImageService.remove(new LambdaQueryWrapper<SkuImage>().eq(SkuImage::getSkuId, skuId));
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImageList();
        if (!CollectionUtils.isEmpty(skuImagesList)) {
            for (SkuImage skuImage : skuImagesList) {
                // 设置商品skuId
                skuImage.setSkuId(skuId);
            }
            skuImageService.saveBatch(skuImagesList);
        }

        // 商品属性
        skuAttrValueService.remove(new LambdaQueryWrapper<SkuAttrValue>().eq(SkuAttrValue::getSkuId, skuId));
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if (!CollectionUtils.isEmpty(skuAttrValueList)) {
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                //设置商品skuId
                skuAttrValue.setSkuId(skuId);
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }
    }

    //商品审核
    @Override
    public void check(Long skuId, Integer status) {
        SkuInfo skuInfo = baseMapper.selectById(skuId);
        skuInfo.setCheckStatus(status);
        baseMapper.updateById(skuInfo);
    }

    //商品上下架
    @Override
    public void publish(Long skuId, Integer status) {
        if (status == 1) { // 上架
//            SkuInfo skuInfo = baseMapper.selectById(skuId);
            SkuInfo skuInfo = new SkuInfo();
            skuInfo.setId(skuId);
            skuInfo.setPublishStatus(status);
            baseMapper.updateById(skuInfo);
            //整合mq把数据同步到es里面
            rabbitService.sendMessage(
                    MqConstant.EXCHANGE_GOODS_DIRECT,
                    MqConstant.ROUTING_GOODS_UPPER,
                    skuId
            );
        } else { // 下架
//            SkuInfo skuInfo = baseMapper.selectById(skuId);
            SkuInfo skuInfo = new SkuInfo();
            skuInfo.setId(skuId);
            skuInfo.setPublishStatus(status);
            baseMapper.updateById(skuInfo);
            //整合mq把数据同步到es里面
            rabbitService.sendMessage(
                    MqConstant.EXCHANGE_GOODS_DIRECT,
                    MqConstant.ROUTING_GOODS_LOWER,
                    skuId
            );
        }
    }

    //新人专享
    @Override
    public void isNewPerson(Long skuId, Integer status) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setIsNewPerson(status);
        baseMapper.updateById(skuInfo);
    }

    //根据skuId列表得到sku信息列表
    @Override
    public List<SkuInfo> findSkuInfoList(List<Long> skuIdList) {
        return baseMapper.selectBatchIds(skuIdList);
    }

    //根据关键字匹配sku列表
    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        return baseMapper.selectList(
                new LambdaQueryWrapper<SkuInfo>().like(SkuInfo::getSkuName, keyword)
        );
    }

    // 获取新人专享商品
    @Override
    public List<SkuInfo> findNewPersonSkuInfoList() {
        //条件1 ： is_new_person=1
        //条件2 ： publish_status=1
        //条件3 ：显示其中三个
        //获取第一页数据，每页显示三条记录
        Page<SkuInfo> pageParam = new Page<>(1, 4);
        //封装条件
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuInfo::getIsNewPerson, 1);
        wrapper.eq(SkuInfo::getPublishStatus, 1);
//        wrapper.orderByDesc(SkuInfo::getStock);//库存排序
        //调用方法查询
        IPage<SkuInfo> skuInfoPage = baseMapper.selectPage(pageParam, wrapper);
        List<SkuInfo> skuInfoList = skuInfoPage.getRecords();
        return skuInfoList;
    }

    // 根据skuId获取sku信息
    @Override
    public SkuInfoVo getSkuInfoVo(Long skuId) {
        SkuInfoVo skuInfoVo = new SkuInfoVo();

        //skuId查询skuInfo
        SkuInfo skuInfo = baseMapper.selectById(skuId);

        //skuId查询sku图片
        List<SkuImage> imageList = skuImageService.getImageListBySkuId(skuId);

        //skuId查询sku海报
        List<SkuPoster> posterList = skuPosterService.getPosterListBySkuId(skuId);

        //skuId查询sku属性
        List<SkuAttrValue> attrValueList = skuAttrValueService.getAttrValueListBySkuId(skuId);

        //封装到skuInfoVo对象
        BeanUtils.copyProperties(skuInfo, skuInfoVo);
        skuInfoVo.setSkuImageList(imageList);
        skuInfoVo.setSkuPosterList(posterList);
        skuInfoVo.setSkuAttrValueList(attrValueList);
        return skuInfoVo;
    }

    // 验证和锁定库存
    @Override
    public Boolean checkAndLock(
            List<SkuStockLockVo> skuStockLockVoList,
            Long wareId,
            String orderNo
    ) {
        //1 判断skuStockLockVoList集合是否为空
        if (CollectionUtils.isEmpty(skuStockLockVoList)) {
            throw new ItmsException(ResultCodeEnum.DATA_ERROR);
        }

        //2 遍历skuStockLockVoList得到每个商品，验证库存并锁定库存，具备原子性
        skuStockLockVoList.forEach(vo -> checkLock(wareId, vo));

        //3 只要有一个商品锁定失败，所有锁定成功的商品都解锁
        boolean flag = skuStockLockVoList.stream()
                .anyMatch(skuStockLockVo -> !skuStockLockVo.getIsLock());
        if (flag) {
            //所有锁定成功的商品都解锁
            skuStockLockVoList.stream().filter(SkuStockLockVo::getIsLock)
                    .forEach(skuStockLockVo -> {
                        baseMapper.unlockStock(wareId, skuStockLockVo.getSkuId(),
                                skuStockLockVo.getSkuNum());
                    });
            //返回失败的状态
            return false;
        }

        //4 如果所有商品都锁定成功了，redis缓存相关数据，为了方便后面解锁和减库存
        redisTemplate.opsForValue()
                .set(RedisConstant.STOCK_INFO + orderNo, skuStockLockVoList);
        return true;
    }

    //扣减库存成功，更新订单状态
    @Override
    public void minusStock(Long wareId, String orderNo) {
        //从redis获取锁定库存信息
        List<SkuStockLockVo> skuStockLockVoList =
                (List<SkuStockLockVo>) redisTemplate.opsForValue()
                        .get(RedisConstant.STOCK_INFO + orderNo);
        if (CollectionUtils.isEmpty(skuStockLockVoList)) {
            return;
        }
        //遍历集合，得到每个对象，减库存
        skuStockLockVoList.forEach(skuStockLockVo -> {
            baseMapper.minusStock(wareId, skuStockLockVo.getSkuId(), skuStockLockVo.getSkuNum());
        });

        //删除redis数据
        redisTemplate.delete(RedisConstant.STOCK_INFO + orderNo);
    }

    @Override
    public void addStock(Long wareId, Long skuId, int skuNum) {
        baseMapper.addStock(wareId, skuId, skuNum);
    }

    @Override
    public List<SkuInfo> findNewSkuInfoList() {
        return baseMapper.selectList(new LambdaQueryWrapper<SkuInfo>()
                .eq(SkuInfo::getPublishStatus, 1)
                .orderByDesc(SkuInfo::getCreateTime)
                .last("limit 8")
        );
    }

    //2 遍历skuStockLockVoList得到每个商品，验证库存并锁定库存，具备原子性
    private void checkLock(Long wareId, SkuStockLockVo skuStockLockVo) {
        //获取锁
        //公平锁
        RLock rLock = this.redissonClient.getFairLock(RedisConstant.SKUKEY_PREFIX + skuStockLockVo.getSkuId());
        //加锁
        rLock.lock();

        try {
            //验证库存
            SkuInfo skuInfo = baseMapper.checkStock(wareId, skuStockLockVo.getSkuId(), skuStockLockVo.getSkuNum());
            //判断没有满足条件商品，设置isLock值false，返回
            if (skuInfo == null) {
                skuStockLockVo.setIsLock(false);
                return;
            }
            //有满足条件商品
            //锁定库存:update
            Integer rows = baseMapper.lockStock(wareId, skuStockLockVo.getSkuId(), skuStockLockVo.getSkuNum());
            if (rows == 1) {
                skuStockLockVo.setIsLock(true);
            }
        } finally {
            //解锁
            rLock.unlock();
        }
    }

    //sku列表
    @Override
    public IPage<SkuInfo> selectPageSkuInfo(Page<SkuInfo> pageParam,
                                            SkuInfoQueryVo skuInfoQueryVo) {
        String keyword = skuInfoQueryVo.getKeyword();
        Long categoryId = skuInfoQueryVo.getCategoryId();
//        Long wareId = skuInfoQueryVo.getWareId();
        String skuType = skuInfoQueryVo.getSkuType();

        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            wrapper.like(SkuInfo::getSkuName, keyword);
        }
        if (!StringUtils.isEmpty(categoryId)) {
            wrapper.eq(SkuInfo::getCategoryId, categoryId);
        }
        if (!StringUtils.isEmpty(skuType)) {
            wrapper.like(SkuInfo::getSkuType, skuType);
        }
        return baseMapper.selectPage(pageParam, wrapper);
    }
}
