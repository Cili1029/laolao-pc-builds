package com.laolao.common.constant;

public class RedisConstant {
    public static final String SIGN_IN_CODE_KEY = "login:code:";
    public static final Long SIGN_IN_CODE_TTL = 2L;

    // 时间
    public static class Expire {
        public static final String MIN_10 = "#10";
        public static final String HOUR_1 = "#60";
        public static final String HOUR_12 = "#720";
        public static final String DAY_1 = "#1440";
    }

    // 商城
    public static class Shop {
        public static final String CACHE_NAME = "shop";

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
}
