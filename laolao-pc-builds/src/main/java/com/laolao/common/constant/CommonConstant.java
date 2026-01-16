package com.laolao.common.constant;

public class CommonConstant {
    // 用户
    // 管理员标识
    public static class UserType {
        // 普通用户
        public static final int USER = 0;
        // 管理员
        public static final int ADMIN = 1;
    }

    // 商城
    // 订单状态
    public static class Order {
        // 待付款
        public static final int PENDING_PAYMENT = 1;
        // 待发货
        public static final int PENDING_SHIPMENT = 2;
        // 已发货/物流中
        public static final int SHIPPED = 3;
        // 待收货
        public static final int PENDING_RECEIPT = 4;
        // 已完成
        public static final int COMPLETED = 5;
        // 已取消
        public static final int CANCELLED = 6;
    }

    // 产品类型
    public static class Product {
        // 组件
        public static final int COMPONENT = 1;
        // 整机
        public static final int BUNDLE = 2;
        // 全部（用于搜索）
        public static final int ALL = 0;
    }

    // 论坛
    // 点赞类别
    public static class ForumLike {
        // 帖子点赞
        public static final int POST = 1;
        // 评论/回复点赞
        public static final int COMMENT = 2;
    }

    // 通用状态
    public static class CommonStatus {
        // 停用
        public static final int DISABLED = 0;
        // 启用
        public static final int ENABLED = 1;

        // 未点赞
        public static final int NOT_LIKED = 0;
        // 已点赞
        public static final int LIKED = 1;

        // 未使用
        public static final int NOT_USED = 0;
        // 已使用
        public static final int USED = 1;
    }
}
