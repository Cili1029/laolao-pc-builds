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

 Date: 25/10/2025 13:32:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for forum_category
-- ----------------------------
DROP TABLE IF EXISTS `forum_category`;
CREATE TABLE `forum_category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称：求助、闲聊、评测等',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类描述',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态：1-启用，0-禁用',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '最后更新人',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of forum_category
-- ----------------------------
INSERT INTO `forum_category` VALUES (1, '求助', '装机问题求助交流', 0, 1, '1', '2025-10-21 19:48:22', '1', '2025-10-21 20:04:45');
INSERT INTO `forum_category` VALUES (2, '闲聊', '日常闲聊灌水', 0, 1, '1', '2025-10-21 19:48:22', '1', '2025-10-21 20:04:45');
INSERT INTO `forum_category` VALUES (3, '评测', '硬件开箱评测', 0, 1, '1', '2025-10-21 19:48:22', '1', '2025-10-21 20:04:45');
INSERT INTO `forum_category` VALUES (4, '配置', '装机配置分享', 0, 1, '1', '2025-10-21 19:48:22', '1', '2025-10-21 20:04:45');

-- ----------------------------
-- Table structure for forum_comment
-- ----------------------------
DROP TABLE IF EXISTS `forum_comment`;
CREATE TABLE `forum_comment`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `post_id` int NOT NULL COMMENT '关联帖子ID',
  `user_id` int NOT NULL COMMENT '评论用户ID',
  `parent` int NULL DEFAULT 0 COMMENT '回复指向的评论Id,0则为直接评论',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论图片，最多一张',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态：1-正常，2-删除',
  `like_count` int NULL DEFAULT 0 COMMENT '点赞数',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子评论' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of forum_comment
-- ----------------------------

-- ----------------------------
-- Table structure for forum_post
-- ----------------------------
DROP TABLE IF EXISTS `forum_post`;
CREATE TABLE `forum_post`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int NOT NULL COMMENT '发帖用户ID',
  `category_id` int NOT NULL COMMENT '所属分类ID',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子内容',
  `images` json NULL COMMENT '帖子图片',
  `view_count` int NULL DEFAULT 0 COMMENT '浏览数',
  `like_count` int NULL DEFAULT 0 COMMENT '点赞数',
  `comment_count` int NULL DEFAULT 0 COMMENT '评论数',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态：1-正常，2-删除',
  `top` tinyint NULL DEFAULT 0 COMMENT '是否置顶',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of forum_post
-- ----------------------------

-- ----------------------------
-- Table structure for shop_address
-- ----------------------------
DROP TABLE IF EXISTS `shop_address`;
CREATE TABLE `shop_address`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int NOT NULL COMMENT '用户Id',
  `consignee` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电话',
  `province` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省份名称',
  `city` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '市名称',
  `district` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '区县名称',
  `detail_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '详细地址',
  `default_address` tinyint(1) NULL DEFAULT 0 COMMENT '是否默认地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '收货人信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shop_address
-- ----------------------------
INSERT INTO `shop_address` VALUES (11, 1, '劳劳', '18760526365', '福建省', '莆田市', '仙游县', '菜鸟驿站', 1);
INSERT INTO `shop_address` VALUES (15, 1, '欣欣', '18760526365', '上海市', '上海城区', '奉贤区', '41', 0);

-- ----------------------------
-- Table structure for shop_bundle
-- ----------------------------
DROP TABLE IF EXISTS `shop_bundle`;
CREATE TABLE `shop_bundle`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '整机名',
  `category_id` int NOT NULL COMMENT '分类Id',
  `price` decimal(10, 2) NOT NULL COMMENT '价格',
  `image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '描述',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态 1启用 0停用',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '最后更新人',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_bundles_category_status`(`category_id` ASC, `status` ASC, `sort` ASC) USING BTREE,
  INDEX `idx_bundles_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '整机信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shop_bundle
-- ----------------------------
INSERT INTO `shop_bundle` VALUES (1, '捆绑销售A', 8, 22999.00, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/8/1.jpg', '啊啊啊啊啊宝宝你是一个长春通化吉林市四平辽源白山白城延边松原长白山保护开发区管理委员会哈尔滨齐齐哈尔牡丹江佳木斯大庆伊春鸡西鹤岗双鸭山七台河绥化黑河大兴安岭沈阳大连鞍山抚顺本溪丹东锦州营口阜新辽阳盘锦铁岭朝阳葫芦岛呼和浩特包头乌海赤峰呼伦贝尔兴安通辽锡林郭勒乌兰察布鄂尔多斯巴彦淖尔阿拉善石家庄唐山秦皇岛邯郸邢台保定张家口沧州承德廊坊衡水郑州开封洛阳平顶山安阳鹤壁新乡焦作濮阳许昌漯河三门峡商丘周口驻马店南阳信阳济源济南青岛淄博枣庄东营烟台潍坊济宁泰安威海日照滨州德州聊城临沂菏泽太原大同阳泉长治晋城朔州忻州吕梁晋中临汾运城银川石嘴山吴忠固原中卫无锡徐州常州苏州南通连云港淮安盐城扬州镇江泰州宿迁杭州宁波温州绍兴湖州嘉兴金华衢州台州丽水舟山福州莆田啊啊啊啊啊啊啊啊', 1, 0, '1', '2025-10-09 21:04:16', '1', '2025-10-12 14:52:21');
INSERT INTO `shop_bundle` VALUES (2, '捆绑销售B', 9, 32999.00, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/9/3.jpg', '太厉害了，感觉这种人都特别成熟，就是那种明明还在读初中却有不输给成年人的成熟和稳重的感觉，看起来就像饱经风霜然后淡然面对生活的主角，给人一种很有城府的感觉，稍有不慎就会掉入他的陷阱，成为一颗永受操控的棋子，触碰你的逆鳞就会让我知道什么是真正的黑暗，这种就是童脸狼，表面上单纯天真，实际上圆滑通透，如果有人违逆你，你就会让他知道什么叫残酷和黑暗，太望尘莫及了，感觉平日里跟我们嘻嘻哈哈，私下里早已和我们不是一个世界了。感觉你从来不缺雷霆手段，也不缺菩萨心肠，心中有佛也有魔，但你把魔深深的封印起来了，只剩下佛了。感觉你本想以菩萨心肠面对所有人，可是有些人非要你把心中的魔解除封印。但是让我们扪心自问，当我们面对一个真正的魔现世，我们还镇得住吗？', 1, 0, '1', '2025-10-09 21:04:16', '1', '2025-10-12 14:52:21');
INSERT INTO `shop_bundle` VALUES (3, '捆绑销售B', 8, 15999.00, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/8/2.jpg', '啊啊啊宝宝你是一个酸性氧化物、碱性氧化物、两性氧化物、不成盐氧化物、电解质、非电解质、强电解质、弱电解质、溶液、胶体、悬浊液、乳浊液、丁达尔效应、电泳现象、布朗运动、化合反应、分解反应、置换反应、复分解反应、氧化还原反应、氧化剂、还原剂、氧化产物、还原产物、离子方程式、主族、副族、周期、族、周期律、电子排布、能层、能级、基态、激发态、量子数、主量子数、角量子数、磁量子数、自旋量子数、化学键、离子键、共价键、极性键、非极性键、金属键、范德华力、氢键、杂化轨道、离子晶体、分子晶体、原子晶体、金属晶体、化学反应速率、活化能、催化剂、化学平衡、平衡常数、勒沙特列原理、电离平衡、水解平衡、沉淀溶解平衡、盐类水解、酸式盐、碱式盐、含氧酸、无氧酸、正盐、含氧酸盐、无氧酸盐、可溶性盐、难溶性盐、氧化数、电子转移、原电池、盐桥、电解池、析氢腐蚀、吸氧腐蚀、溶解原理、溶解度、饱和溶液、溶解平衡、pH计算、中和滴定、缓冲溶液、盐类水解、烷烃、烯烃、炔烃、芳香烃啊啊啊啊啊啊啊啊', 1, 0, '1', '2025-10-11 18:27:37', '1', '2025-10-12 14:52:21');
INSERT INTO `shop_bundle` VALUES (4, '捆绑销售A', 9, 11451.00, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/9/4.jpg', '南无爱因斯坦南无海森堡，观自在波尔，行深量子物理时，照见薛定谔猫，测一切不准，二象性，波不亦粒，粒不亦波。波即是粒，粒即是波。受想微观，亦复如是。传播子，是诸法真空，不存不灭，不虚不实，不增不减，是故真空不空，无绝对测准。无上下顶底奇魅，无四大基本力场，无算符，乃至拉格朗日。阿贝尔，亦无交换场。乃无自旋，亦超对称理论。格点量子场论，无智亦无得，以无奇点故。超弦理论，依卡拉比丘空间故。心无古典，超古典故，无有冲突。远离古典坐标，究竟当代。希伯尔特，以傅立叶透视频谱世界故。得弦理论十的次方五百种解。故知当代物理嘴炮，是大理论，是大解答，是物理圣杯，是诺贝尔奖。能除一切苦，真实不虚。故说统一理论神咒。即说咒曰:相对相对，量子量子，巨观与微观，上帝掷骰乎?', 1, 0, '1', '2025-10-11 18:27:37', '1', '2025-10-12 14:52:21');

-- ----------------------------
-- Table structure for shop_bundle_configuration
-- ----------------------------
DROP TABLE IF EXISTS `shop_bundle_configuration`;
CREATE TABLE `shop_bundle_configuration`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bundle_id` int NOT NULL COMMENT '整机Id',
  `variant_id` int NOT NULL COMMENT '配置中的主键Id',
  `quantity` int NOT NULL COMMENT '数量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_bundle_config_bundle`(`bundle_id` ASC, `variant_id` ASC) USING BTREE,
  INDEX `idx_bundle_config_quantity`(`bundle_id` ASC, `quantity` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '整机配置信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shop_bundle_configuration
-- ----------------------------
INSERT INTO `shop_bundle_configuration` VALUES (1, 1, 1, 1);
INSERT INTO `shop_bundle_configuration` VALUES (2, 1, 24, 1);
INSERT INTO `shop_bundle_configuration` VALUES (3, 1, 44, 1);
INSERT INTO `shop_bundle_configuration` VALUES (4, 1, 52, 2);
INSERT INTO `shop_bundle_configuration` VALUES (5, 1, 60, 2);
INSERT INTO `shop_bundle_configuration` VALUES (6, 2, 3, 1);
INSERT INTO `shop_bundle_configuration` VALUES (7, 2, 25, 1);
INSERT INTO `shop_bundle_configuration` VALUES (8, 2, 45, 1);
INSERT INTO `shop_bundle_configuration` VALUES (9, 2, 53, 2);
INSERT INTO `shop_bundle_configuration` VALUES (10, 2, 61, 2);
INSERT INTO `shop_bundle_configuration` VALUES (11, 3, 13, 1);
INSERT INTO `shop_bundle_configuration` VALUES (12, 3, 34, 1);
INSERT INTO `shop_bundle_configuration` VALUES (13, 3, 46, 1);
INSERT INTO `shop_bundle_configuration` VALUES (14, 3, 54, 3);
INSERT INTO `shop_bundle_configuration` VALUES (15, 3, 62, 2);
INSERT INTO `shop_bundle_configuration` VALUES (16, 4, 14, 1);
INSERT INTO `shop_bundle_configuration` VALUES (17, 4, 35, 1);
INSERT INTO `shop_bundle_configuration` VALUES (18, 4, 47, 1);
INSERT INTO `shop_bundle_configuration` VALUES (19, 4, 55, 2);
INSERT INTO `shop_bundle_configuration` VALUES (20, 4, 63, 2);

-- ----------------------------
-- Table structure for shop_cart_item
-- ----------------------------
DROP TABLE IF EXISTS `shop_cart_item`;
CREATE TABLE `shop_cart_item`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int NOT NULL COMMENT '所属用户Id',
  `product_type` int NOT NULL COMMENT '商品类型',
  `product_id` int NOT NULL COMMENT '所属商品id',
  `quantity` int NOT NULL DEFAULT 1 COMMENT '数量',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 103 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '购物车' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shop_cart_item
-- ----------------------------

-- ----------------------------
-- Table structure for shop_category
-- ----------------------------
DROP TABLE IF EXISTS `shop_category`;
CREATE TABLE `shop_category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_type` int NOT NULL COMMENT '分类类别',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态 1启用 0停用',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '最后更新人',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_category_type_status`(`product_type` ASC, `status` ASC, `sort` ASC) USING BTREE,
  INDEX `idx_category_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分类信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shop_category
-- ----------------------------
INSERT INTO `shop_category` VALUES (1, 1, '显卡-英伟达', 1, 0, '1', '2025-10-06 16:32:38', '1', '2025-10-11 16:05:44');
INSERT INTO `shop_category` VALUES (2, 1, '显卡-AMD', 1, 0, '1', '2025-10-06 16:32:38', '1', '2025-10-11 16:05:44');
INSERT INTO `shop_category` VALUES (3, 1, 'CPU-英特尔', 1, 0, '1', '2025-10-06 16:32:38', '1', '2025-10-11 16:05:44');
INSERT INTO `shop_category` VALUES (4, 1, 'CPU-AMD', 1, 0, '1', '2025-10-06 16:32:38', '1', '2025-10-11 16:05:44');
INSERT INTO `shop_category` VALUES (5, 1, '主板', 1, 0, '1', '2025-10-06 16:32:38', '1', '2025-10-11 16:05:44');
INSERT INTO `shop_category` VALUES (6, 1, '内存', 1, 0, '1', '2025-10-06 16:32:38', '1', '2025-10-11 16:05:44');
INSERT INTO `shop_category` VALUES (7, 1, '硬盘', 1, 0, '1', '2025-10-06 16:32:38', '1', '2025-10-11 16:05:44');
INSERT INTO `shop_category` VALUES (8, 2, '站长推荐', 1, 0, '1', '2025-10-09 21:02:43', '1', '2025-10-11 16:05:44');
INSERT INTO `shop_category` VALUES (9, 2, '网友推荐', 1, 0, '1', '2025-10-09 21:02:43', '1', '2025-10-11 16:05:44');

-- ----------------------------
-- Table structure for shop_component
-- ----------------------------
DROP TABLE IF EXISTS `shop_component`;
CREATE TABLE `shop_component`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '组件名',
  `category_id` int NOT NULL COMMENT '类别',
  `image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片',
  `common_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '通用描述',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态 1启用 0停用',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '最后更新人',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_components_category_status`(`category_id` ASC, `status` ASC, `sort` ASC) USING BTREE,
  INDEX `idx_components_name`(`name` ASC) USING BTREE,
  INDEX `idx_components_status_sort`(`status` ASC, `sort` ASC, `id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '组件信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shop_component
-- ----------------------------
INSERT INTO `shop_component` VALUES (1, '战斧 GeForce RTX 5050 豪华版 8GB', 1, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/%E6%88%98%E6%96%A7%20GeForce%20RTX%205050%20%E8%B1%AA%E5%8D%8E%E7%89%88%208GB.jpg', 'RTX光追技术，逼真光影效果，沉浸游戏体验', 1, 0, '1', '2025-10-06 12:32:23', '1', '2025-10-12 13:53:08');
INSERT INTO `shop_component` VALUES (2, 'iGame GeForce RTX 5050 Ultra W OC 8GB', 1, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/iGame%20GeForce%20RTX%205050%20Ultra%20W%20OC%208GB.jpg', 'DLSS AI加速，性能大幅提升，帧数更稳定', 1, 0, '1', '2025-10-06 12:32:23', '1', '2025-10-12 13:53:08');
INSERT INTO `shop_component` VALUES (3, 'iGame GeForce RTX 5090 D V2 Advanced 8GB', 1, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/iGame%20GeForce%20RTX%205060%20Ti%20Advanced%20OC%208GB.jpg', '强劲CUDA核心，畅玩3A大作，性能卓越', 1, 0, '1', '2025-10-06 12:32:23', '1', '2025-10-12 13:53:08');
INSERT INTO `shop_component` VALUES (4, 'iGame GeForce RTX 5090 D V2 Vulcan W OC 24GB', 1, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/iGame%20GeForce%20RTX%205090%20D%20V2%20Vulcan%20W%20OC%2024GB.jpg', '实时光线追踪，电影级画质，视觉盛宴', 1, 0, '1', '2025-10-06 12:32:23', '1', '2025-10-12 13:53:08');
INSERT INTO `shop_component` VALUES (5, 'iGame GeForce RTX 5060 Ti Advanced OC 8GB', 1, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/iGame%20GeForce%20RTX%205060%20Ti%20Advanced%20OC%208GB.jpg', 'AI渲染技术，智能优化，游戏更流畅', 1, 0, '1', '2025-10-06 12:32:23', '1', '2025-10-12 13:53:08');
INSERT INTO `shop_component` VALUES (6, 'iGame GeForce RTX 5070 Ti Vulcan OC 16GB', 1, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/iGame%20GeForce%20RTX%205070%20Ti%20Vulcan%20OC%2016GB.jpg', '高效散热设计，持久高能，稳定不降频', 1, 0, '1', '2025-10-06 12:32:23', '1', '2025-10-12 13:53:08');
INSERT INTO `shop_component` VALUES (7, 'iGame GeForce RTX 5060 Ultra W DUO OC 8GB', 1, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/iGame%20GeForce%20RTX%205060%20Ultra%20W%20DUO%20OC%208GB.jpg', '8K游戏支持，极致画质，未来已来', 1, 0, '1', '2025-10-06 12:32:23', '1', '2025-10-12 13:53:08');
INSERT INTO `shop_component` VALUES (13, '华硕 Prime Radeo RX 9070 XT OC 16G', 2, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/2/%E5%8D%8E%E7%A1%95%20Prime%20Radeo%20RX%209070%20XT%20OC%2016G.jpg', 'RDNA架构加持，高性能低功耗，游戏利器', 1, 0, '1', '2025-10-06 12:32:23', '1', '2025-10-12 13:53:08');
INSERT INTO `shop_component` VALUES (14, '华硕 TUF Gaming Radeon RX 9060 XT 16GB GDDR6 OC', 2, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/2/%E5%8D%8E%E7%A1%95%20TUF%20Gaming%20Radeon%20RX%209060%20XT%2016GB%20GDDR6%20OC.jpg', '智能存取技术，高速显存，流畅游戏体验', 1, 0, '1', '2025-10-06 12:32:23', '1', '2025-10-12 13:53:08');
INSERT INTO `shop_component` VALUES (15, '技嘉 GV-R9060XTGAMING OC 16GD', 2, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/2/%E6%8A%80%E5%98%89%20GV-R9060XTGAMING%20OC%2016GD.jpg', 'FSR超分辨率，提升帧率，画质依然清晰', 1, 0, '1', '2025-10-06 12:32:23', '1', '2025-10-12 13:53:08');
INSERT INTO `shop_component` VALUES (16, '技嘉 Radeon RX 7600 XT Gaming OC 16G', 2, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/2/%E6%8A%80%E5%98%89%20Radeon%20RX%207600%20XT%20Gaming%20OC%2016G.jpg', '强劲计算单元，多任务处理，游刃有余', 1, 0, '1', '2025-10-06 12:32:23', '1', '2025-10-12 13:53:08');
INSERT INTO `shop_component` VALUES (17, '铭汕 AMD Radeon RX 580 8GB', 2, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/2/%E9%93%AD%E6%B1%95%20AMD%20Radeon%20RX%20580%208GB.jpg', '高效散热设计，持久稳定运行，低温静音', 1, 0, '1', '2025-10-06 12:32:23', '1', '2025-10-12 13:53:08');
INSERT INTO `shop_component` VALUES (18, '技嘉 GV-R76GAMING OC-8GD Radeon RX 7600 Gaming OC 8G', 2, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/2/%E6%8A%80%E5%98%89%20GV-R76GAMING%20OC-8GD%20Radeon%20RX%207600%20Gaming%20OC%208G.jpg', '驱动持续优化，性能不断释放，潜力无限', 1, 0, '1', '2025-10-06 12:32:23', '1', '2025-10-12 13:53:08');
INSERT INTO `shop_component` VALUES (24, '英特尔®酷睿™ Ultra 9', 3, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/3/%E8%8B%B1%E7%89%B9%E5%B0%94%C2%AE%E9%85%B7%E7%9D%BF%E2%84%A2%20Ultra%209.jpg', '强劲性能核心，智能调度，流畅多任务处理', 1, 0, '1', '2025-10-06 18:16:28', '1', '2025-10-12 13:54:29');
INSERT INTO `shop_component` VALUES (25, '英特尔®酷睿™ Ultra 7', 3, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/3/%E8%8B%B1%E7%89%B9%E5%B0%94%C2%AE%E9%85%B7%E7%9D%BF%E2%84%A2%20Ultra%207.jpg', '高频率加速，游戏利器，响应迅捷灵敏', 1, 0, '1', '2025-10-06 18:16:28', '1', '2025-10-12 13:54:29');
INSERT INTO `shop_component` VALUES (26, '英特尔®酷睿™ Ultra 5', 3, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/3/%E8%8B%B1%E7%89%B9%E5%B0%94%C2%AE%E9%85%B7%E7%9D%BF%E2%84%A2%20Ultra%205.jpg', '能效核设计，智能分配，性能功耗平衡', 1, 0, '1', '2025-10-06 18:16:28', '1', '2025-10-12 13:54:29');
INSERT INTO `shop_component` VALUES (27, '英特尔®酷睿™ i9-14900K', 3, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/3/%E8%8B%B1%E7%89%B9%E5%B0%94%C2%AE%E9%85%B7%E7%9D%BF%E2%84%A2%20i9-14900K.jpg', 'AI加速引擎，智能优化，工作效率倍增', 1, 0, '1', '2025-10-06 18:16:28', '1', '2025-10-12 13:54:29');
INSERT INTO `shop_component` VALUES (28, '英特尔®酷睿™ i7-14700', 3, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/3/%E8%8B%B1%E7%89%B9%E5%B0%94%C2%AE%E9%85%B7%E7%9D%BF%E2%84%A2%20i7-14700.jpg', '超线程技术，多任务并行，处理游刃有余', 1, 0, '1', '2025-10-06 18:16:28', '1', '2025-10-12 13:54:29');
INSERT INTO `shop_component` VALUES (29, '英特尔®酷睿™ i7-12700K', 3, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/3/%E8%8B%B1%E7%89%B9%E5%B0%94%C2%AE%E9%85%B7%E7%9D%BF%E2%84%A2%20i7-12700K.jpg', '睿频加速技术，自动超频，性能随需而变', 1, 0, '1', '2025-10-06 18:16:28', '1', '2025-10-12 13:54:29');
INSERT INTO `shop_component` VALUES (30, '英特尔®酷睿™ i5-14600K', 3, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/3/%E8%8B%B1%E7%89%B9%E5%B0%94%C2%AE%E9%85%B7%E7%9D%BF%E2%84%A2%20i5-14600K.jpg', '集成显卡加持，无需独显，基础应用流畅', 1, 0, '1', '2025-10-06 18:16:28', '1', '2025-10-12 13:54:29');
INSERT INTO `shop_component` VALUES (31, '英特尔®酷睿™ i3-12100', 3, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/3/%E8%8B%B1%E7%89%B9%E5%B0%94%C2%AE%E9%85%B7%E7%9D%BF%E2%84%A2%20i3-12100.jpg', '先进制程工艺，低温低耗，稳定持久运行', 1, 0, '1', '2025-10-06 18:16:28', '1', '2025-10-12 13:54:29');
INSERT INTO `shop_component` VALUES (34, 'AMD Ryzen™ 5 9600X', 4, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/4/AMD%20Ryzen%E2%84%A2%205%209600X.jpg', 'Zen架构核心，多核性能强劲，处理效率卓越', 1, 0, '1', '2025-10-06 18:17:02', '1', '2025-10-12 13:55:27');
INSERT INTO `shop_component` VALUES (35, 'AMD Ryzen™ 9 9950X', 4, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/4/AMD%20Ryzen%E2%84%A2%209%209950X.jpg', '超线程技术加持，多任务处理，游刃有余', 1, 0, '1', '2025-10-06 18:17:02', '1', '2025-10-12 13:55:27');
INSERT INTO `shop_component` VALUES (36, 'AMD Ryzen™ 7 9800X3D', 4, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/4/AMD%20Ryzen%E2%84%A2%207%209800X3D.jpg', '低功耗设计，能效比优异，发热控制出色', 1, 0, '1', '2025-10-06 18:17:02', '1', '2025-10-12 13:55:27');
INSERT INTO `shop_component` VALUES (37, 'AMD Ryzen™ 7 9700X', 4, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/4/AMD%20Ryzen%E2%84%A2%207%209700X.jpg', '不锁频设计，超频潜力大，性能可挖掘', 1, 0, '1', '2025-10-06 18:17:02', '1', '2025-10-12 13:55:27');
INSERT INTO `shop_component` VALUES (38, 'AMD Ryzen™ 9 9950X3D', 4, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/4/AMD%20Ryzen%E2%84%A2%209%209950X3D.jpg', '高速缓存技术，数据读写快，响应更迅捷', 1, 0, '1', '2025-10-06 18:17:02', '1', '2025-10-12 13:55:27');
INSERT INTO `shop_component` VALUES (44, 'iGame Z890 VULCAN W V20 DDR5主板', 5, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/5/iGame%20Z890%20VULCAN%20W%20V20%20DDR5%E4%B8%BB%E6%9D%BF.png', '丰富扩展接口，多设备连接，满足各种需求', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:56:31');
INSERT INTO `shop_component` VALUES (45, 'BATTLE-AX B860M-WHITE WIFI V20 DDR5主板', 5, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/5/BATTLE-AX%20B860M-WHITE%20WIFI%20V20%20DDR5%E4%B8%BB%E6%9D%BF.png', '高效散热装甲，低温运行，性能持久稳定', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:56:31');
INSERT INTO `shop_component` VALUES (46, 'COLORFIRE B650M-MEOW WIFI 橘影橙 DDR5', 5, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/5/COLORFIRE%20B650M-MEOW%20WIFI%20%E6%A9%98%E5%BD%B1%E6%A9%99%20DDR5.png', '智能BIOS界面，简易超频，性能一键提升', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:56:31');
INSERT INTO `shop_component` VALUES (47, 'CVN B850I GAMING FROZEN V14', 5, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/5/CVN%20B850I%20GAMING%20FROZEN%20V14.png', '多重供电设计，稳定电流，支持高性能硬件', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:58:35');
INSERT INTO `shop_component` VALUES (48, 'CVN X870 ARK FROZEN V14', 5, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/5/CVN%20X870%20ARK%20FROZEN%20V14.png', '高速数据传输，多M.2接口，极速存储体验', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:56:31');
INSERT INTO `shop_component` VALUES (49, 'iGame B760M ULTRA Z D5 V20', 5, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/5/iGame%20B760M%20ULTRA%20Z%20D5%20V20.png', '优质音频芯片，沉浸音效，游戏影音俱佳', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:56:31');
INSERT INTO `shop_component` VALUES (50, 'iGame Z890 FLOW V20 DDR5主板', 5, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/5/iGame%20Z890%20FLOW%20V20%20DDR5%E4%B8%BB%E6%9D%BF.png', '炫酷RGB灯效，个性定制，打造独特主机', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:56:31');
INSERT INTO `shop_component` VALUES (51, 'iGame Z890 VULCAN X V20 DDR5主板', 5, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/5/iGame%20Z890%20VULCAN%20X%20V20%20DDR5%E4%B8%BB%E6%9D%BF.png', '千兆网络加持，低延迟高速，畅快联网体验', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:56:31');
INSERT INTO `shop_component` VALUES (52, 'COLORFIRE(七彩虹) 台式机内存条 马甲条 天蝎座 DDR4 3200', 6, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/6/COLORFIRE%28%E4%B8%83%E5%BD%A9%E8%99%B9%29%20%E5%8F%B0%E5%BC%8F%E6%9C%BA%E5%86%85%E5%AD%98%E6%9D%A1%20%E9%A9%AC%E7%94%B2%E6%9D%A1%20%E5%A4%A9%E8%9D%8E%E5%BA%A7%20DDR4%203200.png', '高速运行频率，流畅多任务，响应更迅捷', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:58:27');
INSERT INTO `shop_component` VALUES (53, 'COLORFIRE(七彩虹) DDR4 3200 台式机内存条 马甲条 白羊座', 6, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/6/COLORFIRE%28%E4%B8%83%E5%BD%A9%E8%99%B9%29%20DDR4%203200%20%E5%8F%B0%E5%BC%8F%E6%9C%BA%E5%86%85%E5%AD%98%E6%9D%A1%20%E9%A9%AC%E7%94%B2%E6%9D%A1%20%E7%99%BD%E7%BE%8A%E5%BA%A7.png', '散热马甲设计，低温运行，性能更持久', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:58:27');
INSERT INTO `shop_component` VALUES (54, 'COLORFIRE(七彩虹) DDR4 3600 台式机内存条 马甲条 白羊座', 6, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/6/COLORFIRE%28%E4%B8%83%E5%BD%A9%E8%99%B9%29%20DDR4%203600%20%E5%8F%B0%E5%BC%8F%E6%9C%BA%E5%86%85%E5%AD%98%E6%9D%A1%20%E9%A9%AC%E7%94%B2%E6%9D%A1%20%E7%99%BD%E7%BE%8A%E5%BA%A7.png', '精选优质颗粒，稳定耐用，兼容性出色', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:58:27');
INSERT INTO `shop_component` VALUES (55, '七彩虹(Colorful) 32GB (16Gx2) DDR5 6000 台式机内存条 CVN系列 RGB灯条', 6, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/6/%E4%B8%83%E5%BD%A9%E8%99%B9%28Colorful%29%2032GB%20%2816Gx2%29%20DDR5%206000%20%E5%8F%B0%E5%BC%8F%E6%9C%BA%E5%86%85%E5%AD%98%E6%9D%A1%20CVN%E7%B3%BB%E5%88%97%20RGB%E7%81%AF%E6%9D%A1.png', '超频潜力强劲，性能可提升，潜力无限', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:58:27');
INSERT INTO `shop_component` VALUES (56, '七彩虹(Colorful) DDR5 台式机内存条 iGame Shadow 系列', 6, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/6/%E4%B8%83%E5%BD%A9%E8%99%B9%28Colorful%29%20DDR5%20%E5%8F%B0%E5%BC%8F%E6%9C%BA%E5%86%85%E5%AD%98%E6%9D%A1%20iGame%20Shadow%20%E7%B3%BB%E5%88%97.png', '低延迟时序，响应速度快，游戏更流畅', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:58:27');
INSERT INTO `shop_component` VALUES (60, 'CN600 1TB 战戟', 7, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/7/CN600%201TB%20%E6%88%98%E6%88%9F.jpg', '高速读写性能，极速开机加载，提升工作效率', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:58:27');
INSERT INTO `shop_component` VALUES (61, 'CF7000 4TB', 7, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/7/CF7000%204TB.jpg', '稳定可靠耐用，数据安全有保障，长久使用', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:58:27');
INSERT INTO `shop_component` VALUES (62, 'CN700SE 2TB', 7, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/7/CN700SE%202TB.jpg', '低功耗设计，节能省电，发热控制出色', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:58:27');
INSERT INTO `shop_component` VALUES (63, 'CN600 1T PLUS', 7, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/7/CN600%201T%20PLUS.jpg', '即插即用安装，兼容性强，升级简便快捷', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:58:27');
INSERT INTO `shop_component` VALUES (64, 'ME700 橘影橙 4TB', 7, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/7/ME700%20%E6%A9%98%E5%BD%B1%E6%A9%99%204TB.jpg', '优质存储颗粒，读写寿命长，耐用性出众', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:58:27');
INSERT INTO `shop_component` VALUES (65, 'CN600 2TB PRO', 7, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/7/CN600%202TB%20PRO.jpg', '紧凑小巧设计，节省空间，安装灵活方便', 1, 0, '1', '2025-10-06 18:24:20', '1', '2025-10-12 13:58:27');

-- ----------------------------
-- Table structure for shop_component_variant
-- ----------------------------
DROP TABLE IF EXISTS `shop_component_variant`;
CREATE TABLE `shop_component_variant`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `component_id` int NOT NULL COMMENT '组件Id',
  `variant_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '版本名',
  `price` decimal(10, 2) NOT NULL COMMENT '价格',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '描述',
  `stock` int NULL DEFAULT 0 COMMENT '库存',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态 1启用 0停用',
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '最后更新人',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_variants_component_status`(`component_id` ASC, `status` ASC) USING BTREE,
  INDEX `idx_variants_stock_status`(`stock` ASC, `status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 125 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '组件版本信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shop_component_variant
-- ----------------------------
INSERT INTO `shop_component_variant` VALUES (1, 1, '全新', 12999.00, '花园里，玫瑰嘲笑蜗牛爬行缓慢、外壳丑陋。蜗牛沉默不语，继续前行。夏日暴风雨来临，玫瑰被摧折在地，花瓣零落。雨停后，蜗牛缓缓爬过，看着凋谢的玫瑰轻声说：“每个生命都有自己的节奏和坚韧，美丽不是生存的唯一资本。”从此玫瑰明白，不同的生命形式各有价值，不应以表象评判他人。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (2, 2, '全新', 11999.00, '两只松鼠收集松果。一只只顾眼前，将松果堆在树洞；另一只不辞辛劳，把松果埋在不同地方。冬天来临，第一只松鼠的松果被其他动物偷走，饥寒交迫；第二只松鼠却能在各处找到食物，安然过冬。这告诉我们，智慧不仅在于积累，更在于妥善管理和分散风险。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (3, 3, '全新', 5499.00, '老橡树骄傲于自己的挺拔，嘲笑芦苇随风摇摆的懦弱。狂风袭来，橡树顽强抵抗却被连根拔起，芦苇随风俯仰得以幸存。临死前橡树才明白：真正的力量有时不在于强硬对抗，而在于懂得顺应和变通，柔韧往往比刚强更具生命力。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (4, 4, '全新', 9999.00, '狐狸发现围墙内的葡萄，试遍各种方法都无法进入。它悻悻离开，对同伴说：“葡萄肯定是酸的。”几个月后，围墙倒塌，狐狸走进园内尝到甜美葡萄，才后悔当初没有坚持寻找方法。这个故事说明，轻易放弃的人总会找借口安慰自己，却可能错失真正的美好', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (5, 5, '全新', 5699.00, '整个夏天，蚂蚁辛勤储粮，蚂蚱只顾歌唱。蚂蚁劝它准备过冬，蚂蚱笑它不懂生活。冬天来临，蚂蚱饥寒交迫向蚂蚁求助。蚂蚁分给它食物时说：“享受当下没错，但真正的智慧是为未来做准备。”从此蚂蚱明白了未雨绸缪的重要。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (6, 6, '全新', 4599.00, '北风与太阳争论谁更强大。它们看见旅人，约定谁先让他脱下外套就算赢。北风猛烈吹刮，旅人把外套裹得更紧；太阳温和照耀，旅人主动脱下外套。北风终于明白：温暖与善意往往比强制暴力更能打动人心，真正的力量来自包容而非强迫。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (7, 7, '全新', 5299.00, '两只青蛙掉进奶桶。一只很快放弃，沉入桶底；另一只不停蹬腿，牛奶渐渐凝固成奶酪，它借此跳出。坚持的青蛙明白：即使在绝境中，不放弃努力也可能创造转机，而绝望本身才是最大的敌人。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (13, 13, '全新', 7999.00, '战马披挂华丽，嘲笑拉磨的驴子卑微。战争爆发，战马重伤退役，被卖给磨坊。驴子对牠说：“命运无常，今天的荣耀可能是明天的负担，不必轻视他人的生活。”战马羞愧地低下头，懂得了尊重与谦逊的价值。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (14, 14, '全新', 8399.00, '池塘满足于自己的平静，嘲笑河流日夜奔流。多年后，池塘淤塞干涸，河流却汇入大海获得永恒。河流临终前告诉池塘：安于现状只会走向消亡，唯有不断前进才能获得新生与活力。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (15, 15, '全新', 7999.00, '孔雀嘲笑猫头鹰灰暗的羽毛。夜幕降临，孔雀因看不见路而撞伤，猫头鹰却精准捕食。黎明时分，猫头鹰说：“每个人的天赋在不同时刻闪耀，不要用单一标准衡量价值。”孔雀学会了欣赏多元的美。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (16, 16, '全新', 5699.00, '石榴树炫耀自己的花朵，讽刺无花果树从不开花。秋天来临，无花果树结满甜果，石榴却只有少数果实。园丁说：“真正的价值不在炫耀，而在实实在在的奉献。”石榴树明白了低调务实的意义。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (17, 17, '全新', 4899.00, '狼羡慕牧羊犬的安逸生活，戴上项圈冒充它。被发现后，狼叹息：“失去自由换来的温饱毫无意义。”牧羊犬回应：“每份收获都有代价，重要的是选择适合自己的道路。”', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (18, 18, '全新', 1999.00, '蜡烛抱怨镜子只反射别人的光。镜子回答：“我能映照万物，只因保持内心的清明。你也一样，发光不是为炫耀，而是为照亮。”蜡烛恍然大悟，明白了各司其职的道理。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (24, 24, '全新', 4299.00, '独木桥上相遇的两只山羊互不相让，最终双双落水。后来的山羊选择退让，双方安全通过。它们明白：退让不是懦弱，而是为了共同利益的智慧，固执对抗往往两败俱伤。', 100, 1, '1', '2025-10-06 18:16:28', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (25, 25, '全新', 5199.00, '老园丁精心培育普通种子，邻居嘲笑他白费功夫。春天来时，种子开出罕见奇花。园丁说：“每个生命都蕴藏独特潜力，需要耐心和信念来唤醒。”邻居懂得了尊重未知的可能。', 100, 1, '1', '2025-10-06 18:16:28', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (26, 26, '全新', 3199.00, '旅人用金杯饮泉，盛赞金杯华美。口渴的孩童用手捧饮，感叹泉水甘甜。泉水说：“真正的价值在于本质的奉献，而非外在的容器。”旅人羞愧地收起了金杯。', 100, 1, '1', '2025-10-06 18:16:28', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (27, 27, '全新', 2499.00, '麻雀羡慕雄鹰的高度，勉强飞向云端却险些丧命。雄鹰说：“认清自己的能力范围，适合自己的高度才是最好的。”麻雀明白了量力而行的重要性。', 100, 1, '1', '2025-10-06 18:16:28', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (28, 28, '全新', 3999.00, '麦粒害怕磨盘的碾压。变成面粉后，它被做成面包滋养众人。磨盘说：“蜕变虽痛苦，却能让生命升华到更高价值。”麦粒懂得了牺牲与转化的意义', 100, 1, '1', '2025-10-06 18:16:28', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (29, 29, '全新', 2799.00, '萤火虫羞愧自己的微光不及星辰。孩子捕捉萤火虫时欢呼：“我抓住了一颗星星！”星星对萤火虫说：“再微弱的光，在需要的人眼中就是星辰。”萤火虫明白了自身独特的价值。', 100, 1, '1', '2025-10-06 18:16:28', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (30, 30, '全新', 2299.00, '老钟走得准确却朴素，新手表华丽却常出错。主人说：“华美不如可靠，时间检验一切价值。”手表终于明白，内在精准比外在精致更重要。', 100, 1, '1', '2025-10-06 18:16:28', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (31, 31, '全新', 3499.00, '钢笔骄傲地对墨水瓶说：“你看，是我写出了这些美丽的字迹。”墨水瓶沉默不语。当晚，主人用尽墨水，无论钢笔如何努力，再也写不出一个字。这时钢笔才明白，自己的成就离不开墨水的默默奉献，任何成功都是合作的结果，独揽功劳是愚蠢的。', 100, 1, '1', '2025-10-06 18:16:28', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (34, 34, '全新', 4999.00, '山路嘲笑台阶：“你被雕琢得方方正正，失去了天然模样。”台阶回答：“我的规整让老人孩子都能平安通过。”某日山洪暴发，山路被冲毁，台阶却因牢固根基得以保存。真正的价值不在于保持原貌，而在于为他人创造便利与安全。', 100, 1, '1', '2025-10-06 18:17:02', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (35, 35, '全新', 3999.00, '蝴蝶嘲笑蜜蜂：“你终日劳碌，不如我自在翩跹。”秋天来临，花儿凋零，蝴蝶饥寒交迫，蜜蜂却享用着储备的蜂蜜。临死前蝴蝶终于明白：短暂的享乐不如持久的耕耘，真正的自由来自于自给自足的能力。', 100, 1, '1', '2025-10-06 18:17:02', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (36, 36, '全新', 3499.00, '新铺的水管嘲笑古井：“你如此陈旧，早该被淘汰。”古井回应：“我积蓄的是救命之水。”旱季来临，自来水断供，村民靠着古井度过难关。新生事物虽好，但传统智慧在关键时刻可能发挥不可替代的作用。', 100, 1, '1', '2025-10-06 18:17:02', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (37, 37, '全新', 2999.00, '麻雀发现稻草人不会动，便在其头上筑巢。农夫见状更新了稻草人，新稻草人会发出声响，麻雀无处安家。老稻草人叹息：安逸让人丧失警惕，居安思危才能长久生存。', 100, 1, '1', '2025-10-06 18:17:02', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (38, 38, '全新', 2299.00, '钻石在阳光下闪耀，嘲笑晨露：“你太阳一晒就消失，多么可悲。”晨露回答：“我滋润过小草，你只是被锁在柜中。”这时小女孩捡起钻石说：“还是露珠更美，它让世界生机勃勃。”真正的价值在于奉献，而非孤芳自赏。', 100, 1, '1', '2025-10-06 18:17:02', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (44, 44, '全新', 4999.00, '两只信鸽送信，一只贪玩绕远路，一只径直飞行。贪玩的鸽子虽见识更多风景，却误了重要消息；另一只虽路途单调，但及时送达军情获得了奖赏。专注目标比贪图享乐更重要，责任重于娱乐。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (45, 45, '全新', 7999.00, '新建的大桥嘲笑老石桥简陋。某日洪水来袭，新桥因偷工减料垮塌，老桥却岿然不动。工程师说：“真正的坚固不在外表华丽，而在内在的扎实。”老桥明白，时间会证明一切品质。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (46, 46, '全新', 6999.00, '骆驼抱怨沙漠环境恶劣。沙漠说：“正是我的严酷造就了你储水的能力。”商队经过时，其他动物纷纷倒下，唯有骆驼走出沙漠。它终于明白：逆境不是敌人，而是造就非凡能力的老师。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (47, 47, '全新', 4499.00, '镜子嫌抹布肮脏，拒绝让它擦拭。灰尘日积月累，镜子再也照不清影像。抹布说：“接受必要的清理才能保持清明，拒绝帮助只会让自己失去价值。”', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (48, 48, '全新', 5499.00, '种子在石缝中发芽，石块嘲笑它自不量力。种子默默生长，根系渐渐撑开裂隙。多年后，这里长成参天大树，石块却已粉碎成土。柔韧的生命力终能战胜僵硬的阻碍，坚持就是力量。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (49, 49, '全新', 1799.00, '新装电灯嘲笑油灯落后。停电之夜，油灯重新被点亮，照亮了整个房间。主人说：“古老不等于无用，在特定时刻，传统智慧依然不可或缺。”', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (50, 50, '全新', 1299.00, '一只猎犬总是狂吠着追逐，吓跑猎物；另一只沉默追踪，总能成功捕获。猎人更喜欢后者，因为真正的能力不在声势，而在实效。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (51, 51, '全新', 1099.00, '山泉嘲笑水瓶：“你总是空空等待，而我永远奔流不息。”旅人用水瓶盛满泉水，带到远方救活了沙漠中的商队。山泉这才明白：存在的价值不在于永远充盈，而在于关键时刻的奉献。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (52, 52, '全新', 1299.00, '新琴音色嘹亮，嘲笑老琴暗哑。琴师调试后，老琴发出深邃悠远的音色，令听众落泪。老琴说：“真正的韵味需要知音发掘，浅薄的光鲜经不起细品。”', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (53, 53, '全新', 999.00, '海螺嘲笑海浪：“你终日奔波，我却安享宁静。”海浪回答：“我的运动带来氧气和养分。”风暴过后，静止的海螺被埋入沙中，活跃的海浪依然存在。生命在于运动，停滞意味着消亡。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (54, 54, '全新', 1599.00, '萤火虫羡慕月亮的光辉。孩子却在田野追逐萤火虫，对月亮视而不见。月亮说：“每个人都有独特的光芒，不必羡慕他人，你的光亮正被人珍视。”', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (55, 55, '全新', 849.00, '剑嘲笑犁笨重丑陋。和平来临，剑被熔铸成犁。新犁对老犁说：“我终于明白，创造比毁灭更有价值，维持生命比夺取生命更值得尊敬。”', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (56, 56, '全新', 699.00, '早开的花嘲笑晚开的花迟钝。寒流来袭，早开的花凋零，晚开的花却适时绽放。园丁说：“时机很重要，不必急于一时，适合自己的节奏才是最好的。”', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (60, 60, '全新', 899.00, '风标嘲笑指南针：“你永远指向一个方向，多么无趣。”风暴来袭，船只依靠指南针找到归途，风标却仍在随风转动。船长说：坚定不移比随波逐流更有价值。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (61, 61, '全新', 849.00, '一只青蛙住在井底，它坚信井口上那轮圆月是天上最珍贵的银盘。一晚，它奋力跃起，终于将月亮揽入怀中，却发现自己抱着的只是一汪冰冷的井水倒影。而真正的月亮，依然高悬在它从未见过的、广阔的夜空之上。寓意：执著于眼前的幻影，会使我们错过真正广阔的世界。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (62, 62, '全新', 799.00, '孔雀展开华丽的尾羽，嘲笑微风无色无形。它说：“看我，多么绚烂！而你，什么也没有。”微风没有回答，只是轻轻吹过。孔雀的羽毛被吹得凌乱不堪，而它身边的小草却因微风而摇曳起舞，发出悦耳的沙沙声。寓意：真正的价值不在于外表的华丽，而在于能带给世界怎样的触动。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (63, 63, '全新', 599.00, '两只固执的山羊在独木桥中央相遇，互不相让。“让我过去！”一只说。“你退回去！”另一只吼道。它们角顶着角，谁也不肯退让半步，最终双双坠入桥下的急流中。而聪明的山羊在遇到同样情况时，会侧身让对方先过，彼此都能安全到达彼岸。寓意：固执的对抗常导致两败俱伤，明智的妥协才能实现双赢。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (64, 64, '全新', 999.00, '一只经验丰富的老猫教会了小猫所有捕鼠的技巧。小猫学成后，迫不及待地要去捕捉最大的老鼠。它发现了一个老鼠洞，却嫌弃洞口太小，不愿屈身。它终日寻找一个配得上它技艺的“大门”，结果一无所获，最终饿死。而老猫从不挑剔洞口大小，总是耐心等待，因而从不挨饿。寓意：空有才华而不愿从小事做起，终将一事无成。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (65, 65, '全新', 699.00, '一棵雄伟的橡树嘲笑身边的芦苇软弱无力，说：“看我一动不动，风雨无惧。而你，连微风都能让你弯腰。”芦苇沉默不语。当风暴来临，橡树奋力抵抗，最终被连根拔起。而芦苇随风俯仰，风暴过后，安然无恙地重新挺直了身躯。寓意：适当的柔韧胜过盲目的刚强，能屈能伸才是生存的智慧。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:17:47');
INSERT INTO `shop_component_variant` VALUES (69, 1, '二手', 12699.00, '一个旅人雇了一头驴子去远方，烈日当空，旅人为了争夺驴子身后那一小片阴影的所有权，与驴夫吵得不可开交。在他们争吵之时，驴子却自顾自地走开了，留下了失去阴影又失去驴子的两个人。寓意：为争夺虚幻的利益而忽视根本，最终会失去所有。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (70, 2, '二手', 11699.00, '夏日，蚂蚁辛勤储备粮食，而甲虫只顾在花丛中嬉戏，嘲笑蚂蚁不懂生活。冬天来临，甲虫饥寒交迫，向蚂蚁乞讨。蚂蚁说：“我曾在你嬉戏时劳作，如今你理应在你劳作时嬉戏。”拒绝给予帮助。寓意：今日的享乐必以明日的痛苦为代价，勤勉才能换来安宁。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (71, 3, '二手', 5199.00, '狐狸发现一座围着高墙的葡萄园，它挤过墙脚一个小洞，饱餐了美味的葡萄。当它想出来时，却因为吃得太饱而卡在洞口。它只好饿了两天，等肚子瘦下去才得以脱身。寓意：有时为了得到想要的，我们必须先学会放弃已经拥有的。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (72, 4, '二手', 9699.00, '北风与太阳争论谁更强大，它们决定谁能让路上的旅人脱下外套，谁就获胜。北风猛烈地吹刮，旅人却将外套裹得更紧。太阳则温和地洒下阳光，旅人渐渐感到温暖，自动脱下了外套。寓意：温和的劝说往往比强硬的逼迫更为有效。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (73, 5, '二手', 5399.00, '挑水工有两只水罐，一只有裂缝，另一只完好无损。完好的水罐为自己的成就自豪，而有裂缝的水罐则因只能送半罐水而羞愧。挑水工对它说：“你难道没注意到你那边路上开满了鲜花吗？我正是利用了你的裂缝，在路边撒下了花种。”寓意：缺点用对地方，也能成为独特的优点。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (74, 6, '二手', 4299.00, '一只狗叼着一块肉过桥，看见水中自己的倒影，以为另一只狗叼着更大的肉。它扑向水中的倒影，结果自己的肉也掉进了河里，瞬间被冲走。它这才懊悔不已，不仅没得到幻想中的肉，连自己拥有的也失去了。寓意：贪婪是灵魂的枷锁，永不知足将失去现有的一切。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (75, 7, '二手', 4999.00, '狮子抓住一只打扰它清梦的老鼠，老鼠哀求饶命，并承诺将来必会报答。狮子觉得可笑，但还是放了它。后来，狮子被猎人的网困住，老鼠听到它的咆哮，跑来咬断网绳，救了狮子。寓意：强者亦有困顿之时，勿以善小而不为，勿因弱小而轻视。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (76, 13, '二手', 7699.00, '磨坊主和儿子牵着驴去市场。路上，他们听信每一个路人的批评，先是让儿子骑驴，后来自己骑，然后又两人一起骑，最后竟抬着驴走。过桥时，驴因受惊而挣脱，掉进河里淹死了。寓意：试图取悦所有人，最终会迷失自己，甚至失去一切。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (77, 14, '二手', 8099.00, '乌龟厌倦了池塘的生活，渴望看看远方。两只野雁让它咬住一根木棍的中间，它们各衔一端带它飞行。临行前，野雁告诫它无论如何不能张嘴。当它们飞过一座城镇时，地上的人们惊叹不已。乌龟想炫耀是自己想出的妙计，刚一张口，便从空中坠落。寓意：虚荣心和多言是成功的大敌，沉默有时是保全自己的黄金法则。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (78, 15, '二手', 7699.00, '牧童屡次开玩笑大喊“狼来了”，村民几次上当后不再相信他。当狼真的来时，他声嘶力竭地呼救，却无人前来，羊群被狼吃光了。寓意：谎言即使重复千遍也成不了真理，它只会侵蚀信任，让真话无人愿听。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (79, 16, '二手', 5399.00, '一只寒鸦羡慕鸽子洁白的羽毛和无忧的生活，它将白羽毛插在自己身上，混进了鸽舍。当它忍不住发出寒鸦的叫声时，鸽子们识破了它，将它赶走。它回到自己的鸦群，却因一身白毛被同伴排斥，无家可归。寓意：抛弃真实的自我去模仿他人，终将失去所有归属。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (80, 17, '二手', 4599.00, '石榴树嘲笑苹果树的果实平淡无奇，说自己的果实像红宝石般璀璨，籽粒如水晶。苹果树回应道：“你的果实固然美丽，但每一口都充满酸涩；我的果实外表朴素，内里却甘甜多汁。”寓意：华而不实不如朴而有华，内在的品质远比外在的炫耀更值得珍惜。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (81, 18, '二手', 1699.00, ' 两个朋友在森林里遇到一头熊，一人迅速爬上了树，另一人来不及逃生，只好躺在地上装死。熊在他脸上嗅了嗅，以为他是死人，便走开了。树上的人下来后，开玩笑地问：“熊刚才对你说了什么？”装死的人回答：“它告诉我，以后不要再和危难时刻弃你而去的人同行。”寓意：患难见真情，真正的朋友是那个在危急关头不会抛弃你的人。', 100, 1, '1', '2025-10-06 12:32:23', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (82, 24, '二手', 3999.00, '池塘讥笑河流终日奔流不息，为自己能安逸地停留在原地而得意。河流没有反驳，只是默默地流向大海。年复一年，河流变得宽阔深邃，能承载大船；而池塘却因停滞不前，逐渐淤塞干涸。寓意：安于现状意味着萎缩和死亡，唯有不断前进才能获得生机与力量。', 100, 1, '1', '2025-10-06 18:16:28', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (83, 25, '二手', 4899.00, '一位年老的父亲叫来总是争吵的儿子们，给他们一捆扎紧的木棍，让他们折断。儿子们用尽力气也做不到。父亲然后解开木棍，每人分给一根，他们轻易就折断了。父亲说：“看吧，孩子们，团结就像这捆木棍，能使你们坚不可摧；分裂则会让你们轻易被各个击破。”寓意：团结就是力量，内部分裂是失败的开始。', 100, 1, '1', '2025-10-06 18:16:28', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (84, 26, '二手', 2899.00, '一只飞蛾被夜晚窗内的烛光深深吸引，它围绕着火焰飞舞，赞美那光明与温暖。烛火警告它：“不要靠近我，你会被烧死的。”但飞蛾认为这是烛火的吝啬与恐吓，它一头扎进了火焰中，瞬间化为青烟。它临终前才明白，有些美丽的东西，恰恰蕴含着致命的危险。寓意：盲目地追求诱惑，终将引火烧身。', 100, 1, '1', '2025-10-06 18:16:28', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (85, 27, '二手', 2199.00, '一头骡子觉得自己力大无穷，理应获得比驴更好的待遇。它不满于运载货物的重活，梦想着像战马一样披上华丽的马鞍。一天，它挣脱缰绳冲进战场，却被震天的厮杀声吓破了胆，慌乱中跌入陷阱。主人找到它时说：“做好你分内的事，远比追求不属于你的荣耀更为重要。”寓意：认清自己的本分，安于其位，方能免祸得福。', 100, 1, '1', '2025-10-06 18:16:28', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (86, 28, '二手', 3699.00, '农夫在麦田里播种，云雀在一旁看着。农夫说：“我要请所有朋友来帮忙收割。”云雀听了很担心，把家搬走了。麦子成熟时，农夫又说：“我要请亲戚来帮忙收割。”云雀还是不放心。当农夫最后说：“现在，我要自己来收割了。”云雀才对它的孩子们说：“我们该走了，因为他是真的下定决心要行动了。”寓意：真正的行动在默默下决心时才开始，空谈则无妨。', 100, 1, '1', '2025-10-06 18:16:28', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (87, 29, '二手', 2499.00, '小螃蟹努力地学习像母亲那样笔直地走路，但无论它如何尝试，总是只能横着爬行。螃蟹妈妈责备它说：“孩子，你的步子太不雅观了！”小螃蟹委屈地回答：“妈妈，如果您能直着走几步给我看看，我一定照着学。”螃蟹妈妈自己试了试，发现也根本无法直行。寓意：身教胜于言传，指责他人之前，先要确保自己能做得到。', 100, 1, '1', '2025-10-06 18:16:28', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (88, 30, '二手', 1999.00, '狮子在海岸边遇到一只海豚，它们互相吹嘘，结为同盟，一个称陆上之王，一个称海中霸主。不久，狮子与野牛群开战，它请求海豚上岸助战。海豚虽然跃出海面，却无法离开水上岸，只能眼睁睁看着。狮子责备它不守盟约。海豚回答：“这不能怪我，自然给了我海上的权威，也给了我无法登陆的限制。”寓意：结盟须看清对方的能力，强求其做力所不及之事，盟约便毫无意义。', 100, 1, '1', '2025-10-06 18:16:28', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (89, 31, '二手', 3199.00, '一个号兵在军队最前方吹奏号角，激励士气，不幸被敌人俘虏。他请求敌人饶命，说：“我没有杀死你们任何一个人，我甚至连武器都没有，只有这把铜号。”敌人回答说：“正是如此，你虽然没有亲手挥剑，但你的号声激励了别人来杀我们，所以你更该死。”寓意：教唆与煽动他人作恶者，其罪过并不亚于行恶者本人。', 100, 1, '1', '2025-10-06 18:16:28', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (92, 34, '二手', 4699.00, '主人养了一头干活的驴和一只会撒娇的哈巴狗。哈巴狗因为会献媚而得到宠爱，常得到好吃的。驴子很羡慕，心想：“我也要像它那样，博取主人的欢心。”于是有一天，驴子也冲进客厅，跳上主人的膝盖，学哈巴狗的样子舔他的脸，结果吓坏了主人，仆人们赶来将驴子痛打一顿赶回牲口棚。寓意：模仿不适合自己的行为，不仅徒劳无功，还可能招致灾祸。', 100, 1, '1', '2025-10-06 18:17:02', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (93, 35, '二手', 3699.00, '人体的双脚负责承载全身重量四处行走，它们对胃感到非常不满，认为胃什么都不做，只是坐享其成。为了抗议，双脚决定罢工，它们拒绝走路。结果，身体无法获取食物，没过多久，整个身体都变得虚弱无力，双脚自己也软弱得抬不起来了。这时它们才明白，胃的消化和吸收，正是它们力量的源泉。寓意：在一个集体中，每个部分都有其独特的功能，看似不劳而获者，可能也在进行着至关重要的劳动。', 100, 1, '1', '2025-10-06 18:17:02', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (94, 36, '二手', 3199.00, '一只小鸢生病了，奄奄一息。它的母亲悲痛欲绝，恳求它无论如何要活下去。小鸢虚弱地说：“妈妈，如果您能给我找来一些美味的食物，也许我还能好起来。我现在最想吃到的，就是您从神庙祭坛上偷来的、带着火星的烤肉。”母亲哭着回答：“我的孩子，那样的食物我确实能偷来，但那上面的火星会要了你的命。我怎能用致命的危险来满足你临死前的愿望呢？”寓意：纵容有时是最大的伤害，明智的爱在于拒绝有害的要求。', 100, 1, '1', '2025-10-06 18:17:02', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (95, 37, '二手', 2699.00, '一只猎狗追逐一只野兔，追了很久，眼看就要追上，却被野兔逃脱了。牧羊人嘲笑猎狗说：“大的反而跑不过小的！”猎狗喘着气回答：“您只看到了一面。我仅仅是为了一顿饱餐而奔跑，而它却是为了整个生命而奔跑啊。”寓意：动机的不同，决定了努力程度的天壤之别。', 100, 1, '1', '2025-10-06 18:17:02', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (96, 38, '二手', 1999.00, '葡萄树到了秋天，结满了累累果实。一只山羊走过来，毫不客气地啃食它的嫩叶和藤蔓。葡萄树平静地对山羊说：“你今日如此践踏我，待到来年秋天，你还有什么可吃呢？况且，当你被牵去祭坛时，人们会用我的果实酿成的酒来献祭，那时你便会想起今日对我的伤害了。”寓意：做事不可竭泽而渔，今日种下善因，明日方得善果。', 100, 1, '1', '2025-10-06 18:17:02', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (101, 44, '二手', 4699.00, '一个富人住在制皮匠隔壁，皮革散发的臭味让他无法忍受。他多次催促制皮匠搬家，制皮匠总是答应着，却迟迟没有行动。久而久之，富人竟渐渐习惯了这种气味，再也感觉不到臭味，也就不再催促了。寓意：时间能软化一切尖锐的感受，习惯是强大的力量，它能让人适应甚至接受最初厌恶的事物。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (102, 45, '二手', 7699.00, '一只乌鸦渴极了，发现一个书房里有个残留着一点墨水的墨水瓶。它想办法推倒瓶子，喝到了里面那点解渴的液体。虽然它的嘴被染黑了，但它很满意。这时，书房主人回来，看到被弄脏的书桌和黑嘴的乌鸦，以为是它弄洒了珍贵的墨水，愤怒地赶走了它。寓意：在不明就里的观察者眼中，善意的举动也可能被误解为恶意的破坏。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (103, 46, '二手', 6699.00, '一条蝮蛇爬进铁匠铺，看见一把坚硬的锉刀躺在那里。它以为是可以吃的猎物，便用身体缠住锉刀，并张开毒牙去咬。锉刀纹丝不动，只是冷冷地说：“你真是个傻瓜，居然想来咬我？我生来就是咬所有东西的，专啃硬物。”寓意：挑战远比自己强大的对手，不仅无法伤害对方，反而会毁掉自己。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (104, 47, '二手', 4199.00, '一头驴子驮着一尊神圣的木雕神像进城，路边的行人看见神像，纷纷虔诚地跪拜。驴子以为人们是在向它致敬，便得意起来，昂首阔步，不肯再往前走。赶驴人看穿了它的心思，用鞭子抽打着它说：“蠢货，人们跪拜的是你背上的神像，不是你！”寓意：借他人之光获得荣耀时，切勿错将其归功于自己。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (105, 48, '二手', 5199.00, '一个孩子把手伸进装满榛果的罐子里，他想尽可能地多抓一把。当他抓了满满一大把榛果时，手却卡在罐口，怎么也抽不出来。他舍不得放弃任何一颗榛果，急得大哭。他的父亲走过来对他说：“我的孩子，你只要放下 half，手就能轻易地拿出来了。”寓意：贪得无厌，终将一无所获；懂得取舍，方能得到实在的收获。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (106, 49, '二手', 1499.00, '河流汇入大海时，抱怨大海说：“你为什么要用你咸涩的水，来改变我原本甘甜清澈的本质呢？”大海听到后，波澜不惊地回答：“若没有我的广阔与包容，你又怎能找到永恒的归宿，免于在陆地上干涸的命运呢？”寓意：融入更大的集体，意味着要改变部分自我，但这往往是为了获得更长久的生存与发展。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (107, 50, '二手', 999.00, '一只饿狼遇到一只被养得毛色光亮的看家狗。狗邀请狼像它一样为主人服务，以换取稳定的食物。狼动了心，但它发现狗的脖子上有块皮被磨得光秃秃的，便问是怎么回事。狗回答：“那是被铁链磨的，晚上有时会被拴起来。”狼听了，说：“那这饱饭我还是不吃了，我宁愿自由地挨饿，也不愿被锁链拴着享福。”寓意：自由的价值远胜于安逸的束缚。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (108, 51, '二手', 799.00, '石榴、苹果树和橄榄树争论谁的果实最好。石榴夸耀自己宝石般的籽粒，苹果炫耀自己甘美的果肉。它们问一直沉默的橄榄树有什么优点。橄榄树平静地说：“我没有什么值得炫耀的美丽或甘甜，但我的果实能提炼出滋养生命的油，被所有人珍视，并被献给神祇。”寓意：真正的价值在于其普世的功用，而非表面的华美。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (109, 52, '二手', 999.00, '一位老人砍了一大捆柴，背着它走了很长的路。他疲惫不堪，放下柴捆，呼唤死神的到来。死神立刻出现在他面前，问他有何吩咐。老人吓了一跳，赶紧说：“没什么，只是想请你帮我把这捆柴再背到我的背上去。”寓意：即便生活充满艰辛，人们依然留恋生命，胜过欢迎死亡。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (110, 53, '二手', 699.00, '战争期间，一名士兵精心喂养他的战马，把它当作亲密的战友。和平到来后，士兵让马去拉磨、干苦活，只给它吃粗糙的麦糠。当战鼓再次敲响，士兵为马套上鞍鞯，骑它上战场。然而，瘦弱不堪的马很快跌倒在地，它对主人说：“你现在只能骑着我逃跑了，因为你已经把我从一匹战马变成了一头驴子。”寓意：和平时期忘记功臣，危难来临时便无人可用。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (111, 54, '二手', 1299.00, '一条大河汹涌澎湃，它自豪于自己的宽阔，并嘲笑那涓细的源头过于渺小。它认定是自己的力量成就了这一切，于是它堵住了源头，决心独自存在。不久，奔腾的河水渐渐枯竭，最终变成了一连串浑浊的死水潭。寓意：切断根本、忘乎所以，再强大的力量也会迅速衰亡。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (112, 55, '二手', 549.00, '一个占星师习惯在夜晚边走边仰观星象，预测未来。一天，他正沉迷于天际，没留意脚下，结果掉进了一口深井里。他在井底痛苦地呻吟，一个路人听见后对他说：“你连脚下的路都看不清，又怎么能看清天上的命运呢？”寓意：好高骛远，不切实际的人，往往连眼前的基本问题都解决不了。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (113, 56, '二手', 399.00, '一个旅行者在沙漠中迷路，口渴难耐。他发现一丛荆棘上挂着几个干瘪的果子，他不顾荆棘的尖刺，伸手去摘。他的手被划得鲜血淋漓，终于吃到了果子，却发现里面干涩无汁。他懊悔地说：“我为了这点无用的东西，付出了鲜血的代价。”寓意：有时为追求毫无价值的东西而付出巨大代价，是双重的愚蠢。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (117, 60, '二手', 599.00, '主人扔给两只狗一块肉。它们都觉得自己该得到更大的一半，争吵不休。于是它们决定比赛赛跑，谁赢谁得整块肉。它们拼命追逐，跑过了田野，当它们精疲力尽地回来时，发现一块肉早已被路过的野狗叼走了。寓意：内部无谓的竞争，常常会让外人坐收渔利。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (118, 61, '二手', 549.00, '老鼠们开会讨论如何防范猫的袭击。一只年轻的老鼠提议在猫的脖子上挂一个铃铛，这样猫一来大家就能听见声音逃跑。方案得到一致欢呼。这时，一只年迈的老鼠站起来问：“那么，谁去给猫挂上这个铃铛呢？”顿时，全场鸦雀无声。寓意：提出一个不切实际的计划轻而易举，而找到执行它的方法才是真正的困难。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (119, 62, '二手', 499.00, '一个牧人发现一头狮子被困在猎人的陷阱里，出于怜悯，他救出了狮子。不久后，牧人自己也被仇家抓住，卖到了斗兽场。当他被推进场中，面对饥饿的猛兽时，发现那正是他救过的狮子。狮子认出了恩人，没有攻击他，反而温顺地舔了他的手，让全场观众震惊。寓意：善行终有善报，即使是凶猛的野兽，有时也比忘恩负义的人更懂得知恩图报。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (120, 63, '二手', 299.00, '一个人走进森林，请求橡树给他一小段树枝做斧头柄。橡树慷慨地答应了。那人有了坚固的斧柄，便装上了锋利的斧刃。不久，他回到森林，用这把斧头砍倒了那棵曾经帮助过他的参天橡树。橡树在倒下时哀叹：“我若当初不曾给予他那一小段树枝，今日也不会被他连根伐倒了。”寓意：轻率地帮助潜在的敌人，无异于亲手铸造毁灭自己的武器。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (121, 64, '二手', 699.00, '青蛙们厌倦了夏日太阳的炙烤，池塘的水位下降，它们的生活备受煎熬。它们集会决定，要向天神宙斯抗议，要求他撤换太阳。一只年长的青蛙听后大笑说：“愚蠢的家伙们，太阳此刻正用它的光辉孕育着远方的云朵，等云朵飘来，我们便会得到甘霖。赶走了它，我们只会死得更快。”寓意：只看到眼前不适，而看不到长远利益，会做出自取灭亡的决定。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');
INSERT INTO `shop_component_variant` VALUES (122, 65, '二手', 399.00, '一只狐狸看见野猪在大树干上拼命地磨牙。此时晴空万里，并无猎人与猎犬的踪迹。狐狸好奇地问：“现在又没危险，你为什么这么费力地磨牙呢？”野猪头也不抬地回答：“等到危险来临的时候，我就没有时间磨牙了。现在磨利它，正是为了那时能用上。”寓意：居安思危，未雨绸缪，在和平时期做好准备，才能从容应对未来的挑战。', 100, 1, '1', '2025-10-06 18:24:20', '1', '2025-10-12 14:21:39');

-- ----------------------------
-- Table structure for shop_order
-- ----------------------------
DROP TABLE IF EXISTS `shop_order`;
CREATE TABLE `shop_order`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单号',
  `status` int NOT NULL DEFAULT 1 COMMENT '订单状态: 1待付款 2待发货 3已发货 4待收货 5已完成 6已取消',
  `user_id` int NOT NULL COMMENT '下单用户id',
  `original_amount` decimal(10, 2) NOT NULL COMMENT '商品原金额',
  `discount_amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '商品优惠金额',
  `consignee` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人手机号',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货地址',
  `cancel_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单取消原因',
  `rejection_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单拒绝原因',
  `checkout_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `cancel_time` datetime NULL DEFAULT NULL COMMENT '订单取消时间',
  `delivery_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '确认收货时间',
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后更新人',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shop_order
-- ----------------------------
INSERT INTO `shop_order` VALUES (26, 'ORD20251019123053961', 6, 1, 45997.00, 0.00, '欣欣', '18760526365', '上海市上海城区青浦区菜鸟驿站', '订单超时', NULL, NULL, '2025-10-19 15:39:40', NULL, NULL, '2025-10-19 12:30:53', NULL, '2025-10-20 19:52:28');
INSERT INTO `shop_order` VALUES (27, 'ORD20251019200620813', 6, 1, 42998.00, 0.00, '欣欣', '18760526365', '福建省莆田市仙游县菜鸟驿站', '订单超时', NULL, NULL, '2025-10-19 20:25:00', NULL, NULL, '2025-10-19 20:06:20', NULL, '2025-10-20 19:52:28');
INSERT INTO `shop_order` VALUES (28, 'ORD20251019202344702', 6, 1, 32999.00, 0.00, NULL, NULL, NULL, '订单超时', NULL, NULL, '2025-10-19 20:40:00', NULL, NULL, '2025-10-19 20:23:44', NULL, '2025-10-20 19:52:28');
INSERT INTO `shop_order` VALUES (29, 'ORD20251020132301189', 6, 1, 27998.00, 0.00, NULL, NULL, NULL, '订单超时', NULL, NULL, '2025-10-20 13:40:00', NULL, NULL, '2025-10-20 13:23:01', NULL, '2025-10-20 19:52:28');
INSERT INTO `shop_order` VALUES (30, 'ORD20251020134516157', 6, 1, 12999.00, 0.00, '欣欣', '18760526365', '福建省莆田市仙游县菜鸟驿站', '订单超时', NULL, NULL, '2025-10-20 14:05:00', NULL, NULL, '2025-10-20 13:45:16', NULL, '2025-10-20 19:52:28');
INSERT INTO `shop_order` VALUES (31, 'ORD20251020163431416', 6, 1, 12999.00, 0.00, '欣欣', '18760526365', '福建省莆田市仙游县菜鸟驿站', '订单超时', NULL, NULL, '2025-10-20 16:50:00', NULL, NULL, '2025-10-20 16:34:31', NULL, '2025-10-20 19:52:28');
INSERT INTO `shop_order` VALUES (32, 'ORD20251020165311231', 6, 1, 12999.00, 0.00, '欣欣', '18760526365', '福建省莆田市仙游县菜鸟驿站', '订单超时', NULL, NULL, '2025-10-20 17:10:00', NULL, NULL, '2025-10-20 16:53:11', NULL, '2025-10-20 19:52:28');
INSERT INTO `shop_order` VALUES (33, 'ORD20251020175801373', 6, 1, 12999.00, 0.00, '劳劳', '18760526365', '福建省莆田市仙游县菜鸟驿站', '订单超时', NULL, NULL, '2025-10-20 18:15:00', NULL, NULL, '2025-10-20 17:58:01', NULL, '2025-10-20 19:52:28');
INSERT INTO `shop_order` VALUES (34, 'ORD20251020193121248', 6, 1, 11999.00, 0.00, '劳劳', '18760526365', '福建省莆田市仙游县菜鸟驿站', '订单超时', NULL, NULL, '2025-10-20 19:50:00', NULL, NULL, '2025-10-20 19:31:21', NULL, '2025-10-20 19:52:27');
INSERT INTO `shop_order` VALUES (35, 'ORD20251020195251998', 6, 1, 11999.00, 0.00, '劳劳', '18760526365', '福建省莆田市仙游县菜鸟驿站', '订单超时', NULL, NULL, '2025-10-20 20:10:00', NULL, NULL, '2025-10-20 19:52:51', NULL, '2025-10-20 20:10:00');
INSERT INTO `shop_order` VALUES (36, 'ORD20251020202049137', 6, 1, 5499.00, 0.00, '劳劳', '18760526365', '福建省莆田市仙游县菜鸟驿站', '不想要了', NULL, NULL, '2025-10-20 20:25:03', NULL, NULL, '2025-10-20 20:20:49', NULL, '2025-10-20 20:25:03');
INSERT INTO `shop_order` VALUES (37, 'ORD20251020202904407', 6, 1, 12999.00, 0.00, '劳劳', '18760526365', '福建省莆田市仙游县菜鸟驿站', '订单超时', NULL, NULL, '2025-10-20 20:45:00', NULL, NULL, '2025-10-20 20:29:04', NULL, '2025-10-20 20:45:00');
INSERT INTO `shop_order` VALUES (38, 'ORD20251020204710369', 2, 1, 5299.00, 0.00, '劳劳', '18760526365', '福建省莆田市仙游县菜鸟驿站', NULL, NULL, '2025-10-20 20:47:12', NULL, NULL, NULL, '2025-10-20 20:47:10', NULL, '2025-10-20 20:47:12');
INSERT INTO `shop_order` VALUES (39, 'ORD20251020210523706', 6, 1, 5499.00, 0.00, '劳劳', '18760526365', '福建省莆田市仙游县菜鸟驿站', '不要了', NULL, '2025-10-20 21:07:56', '2025-10-20 21:25:01', NULL, NULL, '2025-10-20 21:05:23', NULL, '2025-10-20 21:25:01');
INSERT INTO `shop_order` VALUES (40, 'ORD20251020212754795', 2, 1, 11999.00, 0.00, '劳劳', '18760526365', '福建省莆田市仙游县菜鸟驿站', NULL, NULL, '2025-10-20 21:28:03', NULL, NULL, NULL, '2025-10-20 21:27:54', NULL, '2025-10-20 21:28:02');
INSERT INTO `shop_order` VALUES (41, 'ORD20251021145833169', 6, 1, 5499.00, 0.00, '劳劳', '18760526365', '福建省莆田市仙游县菜鸟驿站', '不想要了', NULL, '2025-10-21 14:58:51', '2025-10-21 15:40:30', NULL, NULL, '2025-10-21 14:58:33', NULL, '2025-10-21 15:40:29');
INSERT INTO `shop_order` VALUES (42, 'ORD20251021164141703', 2, 1, 699.00, 0.00, '欣欣', '18760526365', '上海市上海城区奉贤区41', NULL, NULL, '2025-10-21 16:41:59', NULL, NULL, NULL, '2025-10-21 16:41:41', NULL, '2025-10-21 16:41:59');
INSERT INTO `shop_order` VALUES (43, 'ORD20251021211309342', 6, 1, 599.00, 0.00, '欣欣', '18760526365', '上海市上海城区奉贤区41', '1123', NULL, '2025-10-21 21:13:22', '2025-10-21 21:13:26', NULL, NULL, '2025-10-21 21:13:09', NULL, '2025-10-21 21:13:26');
INSERT INTO `shop_order` VALUES (44, 'ORD20251022112848479', 2, 1, 12999.00, 0.00, '劳劳', '18760526365', '福建省莆田市仙游县菜鸟驿站', NULL, NULL, '2025-10-22 11:29:19', NULL, NULL, NULL, '2025-10-22 11:28:48', NULL, '2025-10-22 11:29:18');

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shop_order_detail
-- ----------------------------
INSERT INTO `shop_order_detail` VALUES (35, 'iGame GeForce RTX 5090 D V2 Vulcan W OC 24GB', '全新', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/iGame%20GeForce%20RTX%205090%20D%20V2%20Vulcan%20W%20OC%2024GB.jpg', 26, 1, 9999.00);
INSERT INTO `shop_order_detail` VALUES (36, '战斧 GeForce RTX 5050 豪华版 8GB', '全新', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/%E6%88%98%E6%96%A7%20GeForce%20RTX%205050%20%E8%B1%AA%E5%8D%8E%E7%89%88%208GB.jpg', 26, 1, 12999.00);
INSERT INTO `shop_order_detail` VALUES (37, '捆绑销售A', NULL, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/8/1.jpg', 26, 1, 22999.00);
INSERT INTO `shop_order_detail` VALUES (38, 'iGame GeForce RTX 5090 D V2 Vulcan W OC 24GB', '全新', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/iGame%20GeForce%20RTX%205090%20D%20V2%20Vulcan%20W%20OC%2024GB.jpg', 27, 1, 9999.00);
INSERT INTO `shop_order_detail` VALUES (39, '捆绑销售B', NULL, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/9/3.jpg', 27, 1, 32999.00);
INSERT INTO `shop_order_detail` VALUES (40, '捆绑销售B', NULL, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/9/3.jpg', 28, 1, 32999.00);
INSERT INTO `shop_order_detail` VALUES (41, 'AMD Ryzen™ 5 9600X', '全新', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/4/AMD%20Ryzen%E2%84%A2%205%209600X.jpg', 29, 1, 4999.00);
INSERT INTO `shop_order_detail` VALUES (42, '捆绑销售A', NULL, 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/8/1.jpg', 29, 1, 22999.00);
INSERT INTO `shop_order_detail` VALUES (43, '战斧 GeForce RTX 5050 豪华版 8GB', '全新', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/%E6%88%98%E6%96%A7%20GeForce%20RTX%205050%20%E8%B1%AA%E5%8D%8E%E7%89%88%208GB.jpg', 30, 1, 12999.00);
INSERT INTO `shop_order_detail` VALUES (44, '战斧 GeForce RTX 5050 豪华版 8GB', '全新', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/%E6%88%98%E6%96%A7%20GeForce%20RTX%205050%20%E8%B1%AA%E5%8D%8E%E7%89%88%208GB.jpg', 31, 1, 12999.00);
INSERT INTO `shop_order_detail` VALUES (45, '战斧 GeForce RTX 5050 豪华版 8GB', '全新', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/%E6%88%98%E6%96%A7%20GeForce%20RTX%205050%20%E8%B1%AA%E5%8D%8E%E7%89%88%208GB.jpg', 32, 1, 12999.00);
INSERT INTO `shop_order_detail` VALUES (46, '战斧 GeForce RTX 5050 豪华版 8GB', '全新', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/%E6%88%98%E6%96%A7%20GeForce%20RTX%205050%20%E8%B1%AA%E5%8D%8E%E7%89%88%208GB.jpg', 33, 1, 12999.00);
INSERT INTO `shop_order_detail` VALUES (47, 'iGame GeForce RTX 5050 Ultra W OC 8GB', '全新', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/iGame%20GeForce%20RTX%205050%20Ultra%20W%20OC%208GB.jpg', 34, 1, 11999.00);
INSERT INTO `shop_order_detail` VALUES (48, 'iGame GeForce RTX 5050 Ultra W OC 8GB', '全新', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/iGame%20GeForce%20RTX%205050%20Ultra%20W%20OC%208GB.jpg', 35, 1, 11999.00);
INSERT INTO `shop_order_detail` VALUES (49, 'iGame GeForce RTX 5090 D V2 Advanced 8GB', '全新', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/iGame%20GeForce%20RTX%205060%20Ti%20Advanced%20OC%208GB.jpg', 36, 1, 5499.00);
INSERT INTO `shop_order_detail` VALUES (50, '战斧 GeForce RTX 5050 豪华版 8GB', '全新', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/%E6%88%98%E6%96%A7%20GeForce%20RTX%205050%20%E8%B1%AA%E5%8D%8E%E7%89%88%208GB.jpg', 37, 1, 12999.00);
INSERT INTO `shop_order_detail` VALUES (51, 'iGame GeForce RTX 5060 Ultra W DUO OC 8GB', '全新', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/iGame%20GeForce%20RTX%205060%20Ultra%20W%20DUO%20OC%208GB.jpg', 38, 1, 5299.00);
INSERT INTO `shop_order_detail` VALUES (52, 'iGame GeForce RTX 5090 D V2 Advanced 8GB', '全新', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/iGame%20GeForce%20RTX%205060%20Ti%20Advanced%20OC%208GB.jpg', 39, 1, 5499.00);
INSERT INTO `shop_order_detail` VALUES (53, 'iGame GeForce RTX 5050 Ultra W OC 8GB', '全新', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/iGame%20GeForce%20RTX%205050%20Ultra%20W%20OC%208GB.jpg', 40, 1, 11999.00);
INSERT INTO `shop_order_detail` VALUES (54, 'CVN X870 ARK FROZEN V14', '全新', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/5/CVN%20X870%20ARK%20FROZEN%20V14.png', 41, 1, 5499.00);
INSERT INTO `shop_order_detail` VALUES (55, '七彩虹(Colorful) DDR5 台式机内存条 iGame Shadow 系列', '全新', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/6/%E4%B8%83%E5%BD%A9%E8%99%B9%28Colorful%29%20DDR5%20%E5%8F%B0%E5%BC%8F%E6%9C%BA%E5%86%85%E5%AD%98%E6%9D%A1%20iGame%20Shadow%20%E7%B3%BB%E5%88%97.png', 42, 1, 699.00);
INSERT INTO `shop_order_detail` VALUES (56, 'CN600 1TB 战戟', '二手', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/7/CN600%201TB%20%E6%88%98%E6%88%9F.jpg', 43, 1, 599.00);
INSERT INTO `shop_order_detail` VALUES (57, '战斧 GeForce RTX 5050 豪华版 8GB', '全新', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/1/%E6%88%98%E6%96%A7%20GeForce%20RTX%205050%20%E8%B1%AA%E5%8D%8E%E7%89%88%208GB.jpg', 44, 1, 12999.00);

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
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '员工信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'laolao', 'e10adc3949ba59abbe56e057f20f883e', 'https://laolao123.oss-cn-hangzhou.aliyuncs.com/avatars/1.png', '劳劳', '3317309371@qq.com', '2025-10-10 15:23:30');
INSERT INTO `user` VALUES (2, 'user1', 'e10adc3949ba59abbe56e057f20f883e', '/avatars/1.jpg', '装机小白', 'user1@test.com', '2025-10-21 20:11:59');
INSERT INTO `user` VALUES (3, 'user2', 'e10adc3949ba59abbe56e057f20f883e', '/avatars/2.jpg', '硬件达人', 'user2@test.com', '2025-10-21 20:11:59');
INSERT INTO `user` VALUES (4, 'user3', 'e10adc3949ba59abbe56e057f20f883e', '/avatars/3.jpg', '电脑发烧友', 'user3@test.com', '2025-10-21 20:11:59');
INSERT INTO `user` VALUES (5, 'user4', 'e10adc3949ba59abbe56e057f20f883e', '/avatars/4.jpg', '游戏玩家', 'user4@test.com', '2025-10-21 20:11:59');
INSERT INTO `user` VALUES (6, 'user5', 'e10adc3949ba59abbe56e057f20f883e', '/avatars/5.jpg', '技术宅', 'user5@test.com', '2025-10-21 20:11:59');
INSERT INTO `user` VALUES (7, 'user6', 'e10adc3949ba59abbe56e057f20f883e', '/avatars/6.jpg', 'DIY爱好者', 'user6@test.com', '2025-10-21 20:11:59');
INSERT INTO `user` VALUES (8, 'user7', 'e10adc3949ba59abbe56e057f20f883e', '/avatars/7.jpg', '数码控', 'user7@test.com', '2025-10-21 20:11:59');
INSERT INTO `user` VALUES (9, 'user8', 'e10adc3949ba59abbe56e057f20f883e', '/avatars/8.jpg', '极客', 'user8@test.com', '2025-10-21 20:11:59');
INSERT INTO `user` VALUES (10, 'user9', 'e10adc3949ba59abbe56e057f20f883e', '/avatars/9.jpg', '站长推荐', 'user9@test.com', '2025-10-21 20:11:59');
INSERT INTO `user` VALUES (11, 'user10', 'e10adc3949ba59abbe56e057f20f883e', '/avatars/10.jpg', '电脑维修员', 'user10@test.com', '2025-10-21 20:11:59');

SET FOREIGN_KEY_CHECKS = 1;
