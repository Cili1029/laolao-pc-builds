package com.laolao.common.constant;

// Redis的key定义
public class RedisConstant {
    // 时间
    public static class Expire {
        public static final String STR_MIN_10 = "#" + ExpireConstant.MIN_10;
        public static final String STR_HOUR_1 = "#" + ExpireConstant.HOUR_1;
        public static final String STR_HOUR_12 = "#" + ExpireConstant.HOUR_12;
        public static final String STR_DAY_1 = "#" + ExpireConstant.DAY_1;
    }
    
    // 用户
    public static class User {
        public static final String CACHE_NAME = "user";

        public static final String SIGN_IN_CODE = CACHE_NAME + "::login:code:";
        public static final String SIGN_IN_JWT = CACHE_NAME + "::login:jwt:";

        // 邮箱验证码邮箱限流 一个邮箱1分钟只能发送1次
        public static final String LIMIT_EMAIL_RATE = CACHE_NAME + "::login:limit:email:";
        // 邮箱验证码IP请求限流 一个IP24小时只能发送10次
        public static final String LIMIT_IP_RATE = CACHE_NAME + "::login:limit:ip:";


    }

    // 商城
    public static class Shop {
        public static final String CACHE_NAME = "shop";

        // 优惠券
        public static final String COUPON_LOCK = CACHE_NAME + "::coupon:lock:";
        public static final String COUPON_STOCK = CACHE_NAME + "::coupon:stock:";

        // 商品分类
        public static final String CATEGORY = "category:";

        // 产品简略内容，根据分类id存储
        public static final String COMPONENT_SIMPLE = "component:simple:category:";
        public static final String BUNDLE_SIMPLE = "bundle:simple:category:";

        // 产品详细内容，格局产品Id存储
        public static final String COMPONENT_DETAIL = "component:detail:";
        public static final String BUNDLE_DETAIL = "bundle:detail:";

        // 产品销量排序的数据（主页和全部）
        public static final String HOT_HOME = "hot:home:";
        public static final String HOT_ALL = "hot:all:";
    }

    // 管理端
    public static class Admin {
        public static final String CACHE_NAME = "admin";

        public static final String DASHBOARD_PREFIX = "dashboard:";

        // 仪表盘
        public static final String USER = DASHBOARD_PREFIX + "user:";
        public static final String SHOP = DASHBOARD_PREFIX + "shop:";
        public static final String FORUM = DASHBOARD_PREFIX + "forum:";
    }

    // 布隆过滤器
    public static class BloomFilter {
        public static final String CACHE_NAME = "bloom:";
        public static final String COMPONENT_BLOOM = BloomFilter.CACHE_NAME + "component";
        public static final String BUNDLE_BLOOM = BloomFilter.CACHE_NAME + "bundle";
    }
}
