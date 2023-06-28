package cn.neud.itms.redis.constant;

public interface RedisConstant {

    String SKUKEY_PREFIX = "sku:";
    String SKUKEY_SUFFIX = ":info";
    //单位：秒
    long SKUKEY_TIMEOUT = 24 * 60 * 60;
    // 定义变量，记录空对象的缓存过期时间 缓存穿透key的过期时间
    long SKUKEY_TEMPORARY_TIMEOUT = 10 * 60;

    //单位：秒 尝试获取锁的最大等待时间
    long SKULOCK_EXPIRE_PX1 = 1;
    //单位：秒 锁的持有时间
    long SKULOCK_EXPIRE_PX2 = 1;
    String SKULOCK_SUFFIX = ":lock";

    String USER_KEY_PREFIX = "user:";
    String USER_CART_KEY_SUFFIX = ":cart";
    long USER_CART_EXPIRE = 60 * 60 * 24 * 7;
    String SROCK_INFO = "stock:info:";
    String ORDER_REPEAT = "order:repeat:";

    //用户登录
    String USER_LOGIN_KEY_PREFIX = "user:login:";
    String ADMIN_LOGIN_KEY_PREFIX = "admin:login:";
    //    String userinfoKey_suffix = ":info";
    int USERKEY_TIMEOUT = 365;
    String ORDER_SKU_MAP = "order:sku:";

    //秒杀商品前缀
    String SECKILL_TIME_MAP = "seckill:time:map";
    String SECKILL_SKU_MAP = "seckill:sku:map";
    String SECKILL_SKU_LIST = "seckill:sku:list:";
    String SECKILL_USER_MAP = "seckill:user:map:";
    String SECKILL_ORDERS_USERS = "seckill:orders:users";
    String SECKILL_STOCK_PREFIX = "seckill:stock:";
    String SECKILL_USER = "seckill:user:";
    //用户锁定时间 单位：秒
    int SECKILL__TIMEOUT = 60 * 60 * 1;
    
    // ----
    String LOGIN_CODE_KEY = "login:code:";
    Long LOGIN_CODE_TTL = 2L;
    String LOGIN_USER_KEY = "login:token:";
    Long LOGIN_USER_TTL = 36000L;
    String REGISTER_KEY = "register:token:";
    Long CACHE_NULL_TTL = 2L;

    Long CACHE_TRACE_TTL = 30L;
    String CACHE_TRACE_KEY = "cache:trace:";

    String LOCK_TRACE_KEY = "lock:trace:";
    Long LOCK_TRACE_TTL = 10L;

    String SECKILL_STOCK_KEY = "seckill:stock:";
    String NOTE_LIKED_KEY = "note:liked:";
    String FEED_KEY = "feed:";
    String TRACE_GEO_KEY = "trace:geo:";
    String USER_SIGN_KEY = "sign:";
}
