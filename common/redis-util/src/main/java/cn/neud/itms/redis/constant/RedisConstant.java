package cn.neud.itms.redis.constant;

public interface RedisConstant {
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
