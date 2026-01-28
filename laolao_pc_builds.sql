/*
 Navicat Premium Dump SQL

 Source Server         : 数据库
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39)
 Source Host           : localhost:3306
 Source Schema         : laolao_pc_builds

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39)
 File Encoding         : 65001

 Date: 28/01/2026 16:21:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for forum_category
-- ----------------------------
DROP TABLE IF EXISTS `forum_category`;
CREATE TABLE `forum_category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类描述',
  `image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态（1-启用，0-禁用）',
  `created_by` int NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` int NOT NULL DEFAULT 0 COMMENT '最后更新人',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for forum_comment
-- ----------------------------
DROP TABLE IF EXISTS `forum_comment`;
CREATE TABLE `forum_comment`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `post_id` int NOT NULL COMMENT '关联帖子id',
  `user_id` int NOT NULL COMMENT '评论用户id',
  `parent` int NULL DEFAULT 0 COMMENT '回复指向的评论id（0-直接评论）',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `images` json NULL COMMENT '评论图片，最多一张',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态（1-正常，0-删除）',
  `like_count` int NULL DEFAULT 0 COMMENT '点赞数',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 286 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子评论' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for forum_like
-- ----------------------------
DROP TABLE IF EXISTS `forum_like`;
CREATE TABLE `forum_like`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int NOT NULL COMMENT '用户id',
  `like_type` tinyint NOT NULL COMMENT '点赞类型 (1-帖子, 2-评论和回复)',
  `like_id` int NOT NULL COMMENT '被点赞的Id',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 (1-点赞, 0-取消)',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间（点赞，取消点赞时间）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_target`(`user_id` ASC, `like_type` ASC, `like_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '点赞记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for forum_post
-- ----------------------------
DROP TABLE IF EXISTS `forum_post`;
CREATE TABLE `forum_post`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int NOT NULL COMMENT '发帖用户id',
  `category_id` int NOT NULL COMMENT '所属分类id',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子内容',
  `images` json NULL COMMENT '帖子图片',
  `like_count` int NULL DEFAULT 0 COMMENT '点赞数',
  `comment_count` int NULL DEFAULT 0 COMMENT '评论数',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态：（1-正常，0-删除）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `commented_by` int NOT NULL COMMENT '最后评论人',
  `commented_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后评论时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '最后更新人',
  `updated_at` datetime NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_address
-- ----------------------------
DROP TABLE IF EXISTS `shop_address`;
CREATE TABLE `shop_address`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int NOT NULL COMMENT '用户id',
  `consignee` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电话',
  `province` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省份名称',
  `city` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '市名称',
  `district` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '区县名称',
  `detail_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '详细地址',
  `default_address` tinyint(1) NULL DEFAULT 0 COMMENT '是否默认地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '收货人信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_bundle
-- ----------------------------
DROP TABLE IF EXISTS `shop_bundle`;
CREATE TABLE `shop_bundle`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '整机名',
  `category_id` int NOT NULL COMMENT '分类id',
  `price` decimal(10, 2) NOT NULL COMMENT '价格',
  `images` json NULL COMMENT '图片',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '描述',
  `sales` int NULL DEFAULT 0 COMMENT '销售额',
  `stock` int NULL DEFAULT 0 COMMENT '库存',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态（1-启用，0-停用）',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `created_by` int NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` int NOT NULL DEFAULT 0 COMMENT '最后更新人',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_bundles_category_status`(`category_id` ASC, `status` ASC, `sort` ASC) USING BTREE,
  INDEX `idx_bundles_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '整机信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_bundle_configuration
-- ----------------------------
DROP TABLE IF EXISTS `shop_bundle_configuration`;
CREATE TABLE `shop_bundle_configuration`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bundle_id` int NOT NULL COMMENT '整机id',
  `variant_id` int NOT NULL COMMENT '组件id',
  `quantity` int NOT NULL COMMENT '数量',
  `created_by` int NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` int NOT NULL DEFAULT 0 COMMENT '最后更新人',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_bundle_config_bundle`(`bundle_id` ASC, `variant_id` ASC) USING BTREE,
  INDEX `idx_bundle_config_quantity`(`bundle_id` ASC, `quantity` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '整机配置信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_cart_item
-- ----------------------------
DROP TABLE IF EXISTS `shop_cart_item`;
CREATE TABLE `shop_cart_item`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int NOT NULL COMMENT '所属用户id',
  `product_type` int NOT NULL COMMENT '商品类型',
  `product_id` int NOT NULL COMMENT '所属商品id',
  `quantity` int NOT NULL DEFAULT 1 COMMENT '数量',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 218 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '购物车' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_category
-- ----------------------------
DROP TABLE IF EXISTS `shop_category`;
CREATE TABLE `shop_category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_type` int NOT NULL COMMENT '分类类别',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名',
  `image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态（1-启用，0-停用）',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `created_by` int NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` int NOT NULL DEFAULT 0 COMMENT '最后更新人',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_category_type_status`(`product_type` ASC, `status` ASC, `sort` ASC) USING BTREE,
  INDEX `idx_category_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分类信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_component
-- ----------------------------
DROP TABLE IF EXISTS `shop_component`;
CREATE TABLE `shop_component`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '组件名',
  `category_id` int NOT NULL COMMENT '类别id',
  `images` json NULL,
  `common_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '通用描述',
  `sales` int NULL DEFAULT 0 COMMENT '销售额',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态（1-启用，0-停用）',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `created_by` int NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` int NOT NULL DEFAULT 0 COMMENT '最后更新人',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_components_category_status`(`category_id` ASC, `status` ASC, `sort` ASC) USING BTREE,
  INDEX `idx_components_name`(`name` ASC) USING BTREE,
  INDEX `idx_components_status_sort`(`status` ASC, `sort` ASC, `id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '组件信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_component_variant
-- ----------------------------
DROP TABLE IF EXISTS `shop_component_variant`;
CREATE TABLE `shop_component_variant`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `component_id` int NOT NULL COMMENT '组件id',
  `variant_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '版本名',
  `price` decimal(10, 2) NOT NULL COMMENT '价格',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '描述',
  `stock` int NULL DEFAULT 0 COMMENT '库存',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（1-启用，0-停用）',
  `created_by` int NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` int NOT NULL DEFAULT 0 COMMENT '最后更新人',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_variants_component_status`(`component_id` ASC, `status` ASC) USING BTREE,
  INDEX `idx_variants_stock_status`(`stock` ASC, `status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 131 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '组件版本信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_coupon
-- ----------------------------
DROP TABLE IF EXISTS `shop_coupon`;
CREATE TABLE `shop_coupon`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '优惠券Id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '优惠券名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '描述',
  `minimum_amount` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '最低使用金额',
  `discount_amount` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '抵扣金额',
  `stock` int NULL DEFAULT 0 COMMENT '库存',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态（1-启用，0-停用）',
  `valid_start_time` datetime NOT NULL COMMENT '有效期开始',
  `valid_end_time` datetime NOT NULL COMMENT '有效期结束',
  `created_by` int NOT NULL DEFAULT 0 COMMENT '创建人',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` int NOT NULL DEFAULT 0 COMMENT '最后更新人',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_order
-- ----------------------------
DROP TABLE IF EXISTS `shop_order`;
CREATE TABLE `shop_order`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单号',
  `status` int NOT NULL DEFAULT 1 COMMENT '订单状态（1-待付款，2-待发货，3-已发货，4-待收货，5-已完成，6-已取消）',
  `user_id` int NOT NULL COMMENT '下单用户id',
  `original_amount` decimal(10, 2) NOT NULL COMMENT '商品原金额',
  `discount_amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '商品优惠金额',
  `user_coupon_id` int NULL DEFAULT NULL COMMENT '使用的优惠券id',
  `consignee` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人手机号',
  `address_id` int NULL DEFAULT NULL COMMENT '当前选择的地址id',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货地址（用于存入固定订单信息）',
  `tracking_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '快递单号',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `checkout_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `ship_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `arrival_time` datetime NULL DEFAULT NULL COMMENT '到货时间',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '确认收货时间',
  `cancel_time` datetime NULL DEFAULT NULL COMMENT '订单取消时间',
  `cancel_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单取消原因',
  `rejection_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单拒绝原因',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 131 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `shop_order_detail`;
CREATE TABLE `shop_order_detail`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名',
  `variant_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品版本',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片',
  `order_id` int NOT NULL COMMENT '订单id',
  `quantity` int NOT NULL DEFAULT 1 COMMENT '数量',
  `price` decimal(10, 2) NOT NULL COMMENT '单价',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 153 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件链接',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0-临时文件，1-已确认文件）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态（1-启用，0-禁用）',
  `admin` tinyint NOT NULL DEFAULT 0 COMMENT '管理权限（1-是，0-否）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` int NOT NULL DEFAULT 0 COMMENT '最后更新人',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '员工信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_coupon
-- ----------------------------
DROP TABLE IF EXISTS `user_coupon`;
CREATE TABLE `user_coupon`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户持有的优惠券id',
  `user_id` int NOT NULL COMMENT '用户id',
  `coupon_id` int NOT NULL COMMENT '优惠券id',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '使用的订单id',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态（0-未使用，1-已使用，2-已过期）',
  `claimed_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '领取时间',
  `used_at` datetime NULL DEFAULT NULL COMMENT '使用时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_notification
-- ----------------------------
DROP TABLE IF EXISTS `user_notification`;
CREATE TABLE `user_notification`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `receiver_id` int NOT NULL COMMENT '接收通知的用户id',
  `sender_id` int NOT NULL COMMENT '发送通知的用户id',
  `type` tinyint NOT NULL COMMENT '类型（0-系统通知，1-评论帖子，2-回复评论，3-帖子点赞，4-评论点赞）',
  `post_id` int NULL DEFAULT NULL COMMENT '所属帖子id',
  `comment_id` int NULL DEFAULT NULL COMMENT '所属评论id',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '通知标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '通知简要内容（冗余存储，用于展示）',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '是否已读（0-未读，1-已读）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户通知表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
