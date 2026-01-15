package com.laolao.common.constant;

public class RedisConstant {
    public static final String SIGN_IN_CODE_KEY = "login:code:";
    public static final Long SIGN_IN_CODE_TTL = 2L;

    // 商城
    // 商品分类
    public static final String SHOP_CATEGORY = "category:";

    // 产品简略内容，根据分类id存储
    public static final String SHOP_COMPONENT_SIMPLE_KEY = "component:simple:category:";
    public static final String SHOP_BUNDLE_SIMPLE_KEY = "bundle:simple:category:";

    // 产品详细内容，格局产品Id存储
    public static final String SHOP_COMPONENT_DETAIL_KEY = "component:detail:";
    public static final String SHOP_BUNDLE_DETAIL_KEY = "bundle:detail:";

    // 产品销量排序的数据（主页和全部）
    public static final String SHOP_HOT_HOME = "hot:home:";
    public static final String SHOP_HOT_ALL = "hot:all:";

    // 论坛
    // 仪表盘
    public static final String ADMIN_DASHBOARD_USER ="admin:dashboard:user:";
    public static final String ADMIN_DASHBOARD_SHOP = "admin:dashboard:shop:";
    public static final String ADMIN_DASHBOARD_FORUM = "admin:dashboard:forum:";
}
