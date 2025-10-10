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

 Date: 09/10/2025 22:37:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bundle_configurations
-- ----------------------------
DROP TABLE IF EXISTS `bundle_configurations`;
CREATE TABLE `bundle_configurations`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `bundle_id` int NOT NULL,
  `variant_id` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bundle_configurations
-- ----------------------------
INSERT INTO `bundle_configurations` VALUES (1, 1, 1, 1);
INSERT INTO `bundle_configurations` VALUES (2, 1, 24, 1);
INSERT INTO `bundle_configurations` VALUES (3, 1, 44, 1);
INSERT INTO `bundle_configurations` VALUES (4, 1, 52, 2);
INSERT INTO `bundle_configurations` VALUES (5, 1, 60, 2);
INSERT INTO `bundle_configurations` VALUES (6, 2, 2, 1);
INSERT INTO `bundle_configurations` VALUES (7, 2, 25, 1);
INSERT INTO `bundle_configurations` VALUES (8, 2, 45, 1);
INSERT INTO `bundle_configurations` VALUES (9, 2, 53, 2);
INSERT INTO `bundle_configurations` VALUES (10, 2, 61, 2);

-- ----------------------------
-- Table structure for bundles
-- ----------------------------
DROP TABLE IF EXISTS `bundles`;
CREATE TABLE `bundles`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `category_id` int NOT NULL,
  `price` decimal(10, 2) NOT NULL,
  `image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `status` tinyint NULL DEFAULT 1,
  `sort` int NULL DEFAULT 0,
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bundles
-- ----------------------------
INSERT INTO `bundles` VALUES (1, '捆绑销售A', 8, 12999.00, NULL, '站长推荐的捆绑销售', 1, 0, '2025-10-09 21:04:16', '2025-10-09 21:04:16', '1', '1');
INSERT INTO `bundles` VALUES (2, '捆绑销售B', 9, 11999.00, NULL, '网友推荐的捆绑销售', 1, 0, '2025-10-09 21:04:16', '2025-10-09 21:04:16', '1', '1');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` tinyint NULL DEFAULT 1,
  `sort` int NOT NULL DEFAULT 0,
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '显卡-英伟达', 1, 0, '2025-10-06 16:32:38', '2025-10-06 16:32:38', '1', '1');
INSERT INTO `category` VALUES (2, '显卡-AMD', 1, 0, '2025-10-06 16:32:38', '2025-10-06 16:32:38', '1', '1');
INSERT INTO `category` VALUES (3, 'CPU-英特尔', 1, 0, '2025-10-06 16:32:38', '2025-10-06 16:32:38', '1', '1');
INSERT INTO `category` VALUES (4, 'CPU-AMD', 1, 0, '2025-10-06 16:32:38', '2025-10-06 16:32:38', '1', '1');
INSERT INTO `category` VALUES (5, '主板', 1, 0, '2025-10-06 16:32:38', '2025-10-06 16:32:38', '1', '1');
INSERT INTO `category` VALUES (6, '内存', 1, 0, '2025-10-06 16:32:38', '2025-10-06 16:32:38', '1', '1');
INSERT INTO `category` VALUES (7, '硬盘', 1, 0, '2025-10-06 16:32:38', '2025-10-06 16:32:38', '1', '1');
INSERT INTO `category` VALUES (8, '站长推荐', 1, 0, '2025-10-09 21:02:43', '2025-10-09 21:02:43', '1', '1');
INSERT INTO `category` VALUES (9, '网友推荐', 1, 0, '2025-10-09 21:02:43', '2025-10-09 21:02:43', '1', '1');

-- ----------------------------
-- Table structure for component_variants
-- ----------------------------
DROP TABLE IF EXISTS `component_variants`;
CREATE TABLE `component_variants`  (
  `id` int NOT NULL,
  `component_id` int NULL DEFAULT NULL,
  `variant_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `stock` int NULL DEFAULT 0,
  `status` tinyint NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of component_variants
-- ----------------------------
INSERT INTO `component_variants` VALUES (1, 1, 'A版本', 12999.00, '基于先进的Ada Lovelace架构打造，RTX 4070将能效比推向全新高度。采用TSMC 4N定制工艺，拥有5888个CUDA核心，配备12GB GDDR6X高速显存，位宽192-bit，带宽可达504 GB/s。支持DLSS 3革命性技术，通过光学多帧生成实现性能倍增，在2K分辨率下畅玩《赛博朋克2077》、《荒野大镖客2》等3A大作时，帧率轻松突破100FPS。', 100, 1, '2025-10-06 12:32:23', '2025-10-06 19:29:43', '1', '1');
INSERT INTO `component_variants` VALUES (2, 2, 'A版本', 11999.00, '基于先进的Ada Lovelace架构打造，RTX 4070将能效比推向全新高度。采用TSMC 4N定制工艺，拥有5888个CUDA核心，配备12GB GDDR6X高速显存，位宽192-bit，带宽可达504 GB/s。支持DLSS 3革命性技术，通过光学多帧生成实现性能倍增，在2K分辨率下畅玩《赛博朋克2077》、《荒野大镖客2》等3A大作时，帧率轻松突破100FPS。', 100, 1, '2025-10-06 12:32:23', '2025-10-06 19:32:48', '1', '1');
INSERT INTO `component_variants` VALUES (3, 3, 'A版本', 5499.00, 'Ampere架构，性能性价比颠覆之作', 100, 1, '2025-10-06 12:32:23', '2025-10-06 19:29:14', '1', '1');
INSERT INTO `component_variants` VALUES (4, 4, 'A版本', 9999.00, '图灵旗舰，首代光追核心', 100, 1, '2025-10-06 12:32:23', '2025-10-06 19:30:13', '1', '1');
INSERT INTO `component_variants` VALUES (5, 5, 'A版本', 5699.00, 'Pascal架构终极卡皇，性价比旗舰', 100, 1, '2025-10-06 12:32:23', '2025-10-06 19:30:47', '1', '1');
INSERT INTO `component_variants` VALUES (6, 6, 'A版本', 4599.00, 'Pascal首发，16nm工艺飞跃', 100, 1, '2025-10-06 12:32:23', '2025-10-06 19:31:11', '1', '1');
INSERT INTO `component_variants` VALUES (7, 7, 'A版本', 5299.00, 'Maxwell架构旗舰，性能强悍', 100, 1, '2025-10-06 12:32:23', '2025-10-06 19:31:38', '1', '1');
INSERT INTO `component_variants` VALUES (13, 13, 'A版本', 7999.00, 'RDNA3旗舰，挑战4080', 100, 1, '2025-10-06 12:32:23', '2025-10-06 19:39:11', '1', '1');
INSERT INTO `component_variants` VALUES (14, 14, 'A版本', 8399.00, 'RDNA2终极版，性能增强', 100, 1, '2025-10-06 12:32:23', '2025-10-06 19:38:20', '1', '1');
INSERT INTO `component_variants` VALUES (15, 15, 'A版本', 7999.00, 'RDNA2旗舰，正面竞争N卡高端', 100, 1, '2025-10-06 12:32:23', '2025-10-06 19:40:26', '1', '1');
INSERT INTO `component_variants` VALUES (16, 16, 'A版本', 5699.00, '7nm工艺首秀，16G HBM2显存', 100, 1, '2025-10-06 12:32:23', '2025-10-06 19:41:09', '1', '1');
INSERT INTO `component_variants` VALUES (17, 17, 'A版本', 4899.00, 'Vega架构，HBM2显存', 100, 1, '2025-10-06 12:32:23', '2025-10-06 19:41:48', '1', '1');
INSERT INTO `component_variants` VALUES (18, 18, 'A版本', 1999.00, 'Polaris架构，主流市场甜点', 100, 1, '2025-10-06 12:32:23', '2025-10-06 19:42:17', '1', '1');
INSERT INTO `component_variants` VALUES (24, 24, 'A版本', 4299.00, '24核32线程，最高6.0GHz，当前旗舰游戏处理器', 100, 1, '2025-10-06 18:16:28', '2025-10-06 19:47:52', '1', '1');
INSERT INTO `component_variants` VALUES (25, 25, 'A版本', 5199.00, '24核32线程，睿频6.0GHz，13代特别版', 100, 1, '2025-10-06 18:16:28', '2025-10-06 19:48:14', '1', '1');
INSERT INTO `component_variants` VALUES (26, 26, 'A版本', 3199.00, '20核28线程，性价比高的高性能处理器', 100, 1, '2025-10-06 18:16:28', '2025-10-06 19:48:14', '1', '1');
INSERT INTO `component_variants` VALUES (27, 27, 'A版本', 2499.00, '14核20线程，主流游戏最佳选择', 100, 1, '2025-10-06 18:16:28', '2025-10-06 19:48:14', '1', '1');
INSERT INTO `component_variants` VALUES (28, 28, 'A版本', 3999.00, '16核24线程，12代旗舰，混合架构开创者', 100, 1, '2025-10-06 18:16:28', '2025-10-06 19:48:14', '1', '1');
INSERT INTO `component_variants` VALUES (29, 29, 'A版本', 2799.00, '12核20线程，性能均衡的多面手', 100, 1, '2025-10-06 18:16:28', '2025-10-06 19:48:14', '1', '1');
INSERT INTO `component_variants` VALUES (30, 30, 'A版本', 2299.00, '14核20线程，13代性价比之王', 100, 1, '2025-10-06 18:16:28', '2025-10-06 19:48:14', '1', '1');
INSERT INTO `component_variants` VALUES (31, 31, 'A版本', 3499.00, '8核16线程，11代旗舰，高频率优化', 100, 1, '2025-10-06 18:16:28', '2025-10-06 19:48:14', '1', '1');
INSERT INTO `component_variants` VALUES (32, 32, 'A版本', 2399.00, '8核16线程，支持PCIe 4.0', 100, 1, '2025-10-06 18:16:28', '2025-10-06 19:48:14', '1', '1');
INSERT INTO `component_variants` VALUES (33, 33, 'A版本', 1899.00, '10核16线程，入门级高性能选择', 100, 1, '2025-10-06 18:16:28', '2025-10-06 19:48:14', '1', '1');
INSERT INTO `component_variants` VALUES (34, 34, 'A版本', 4999.00, '16核32线程，3D V-Cache技术，游戏性能顶级', 100, 1, '2025-10-06 18:17:02', '2025-10-06 19:50:40', '1', '1');
INSERT INTO `component_variants` VALUES (35, 35, 'A版本', 3999.00, '16核32线程，Zen4架构旗舰，多核性能强劲', 100, 1, '2025-10-06 18:17:02', '2025-10-06 19:50:40', '1', '1');
INSERT INTO `component_variants` VALUES (36, 36, 'A版本', 3499.00, '16核32线程，Zen3旗舰，生产力利器', 100, 1, '2025-10-06 18:17:02', '2025-10-06 19:50:40', '1', '1');
INSERT INTO `component_variants` VALUES (37, 37, 'A版本', 2999.00, '8核16线程，游戏性能最强CPU之一', 100, 1, '2025-10-06 18:17:02', '2025-10-06 19:50:40', '1', '1');
INSERT INTO `component_variants` VALUES (38, 38, 'A版本', 2299.00, '8核16线程，Zen4架构，均衡之选', 100, 1, '2025-10-06 18:17:02', '2025-10-06 19:50:40', '1', '1');
INSERT INTO `component_variants` VALUES (39, 39, 'A版本', 1549.00, '6核12线程，入门Zen4，性价比高', 100, 1, '2025-10-06 18:17:02', '2025-10-06 19:51:34', '1', '1');
INSERT INTO `component_variants` VALUES (40, 40, 'A版本', 2599.00, '12核24线程，Zen3经典，多核性能优秀', 100, 1, '2025-10-06 18:17:02', '2025-10-06 19:51:34', '1', '1');
INSERT INTO `component_variants` VALUES (41, 41, 'A版本', 2199.00, '8核16线程，首款3D V-Cache处理器', 100, 1, '2025-10-06 18:17:02', '2025-10-06 19:52:21', '1', '1');
INSERT INTO `component_variants` VALUES (42, 42, 'A版本', 1199.00, '6核12线程，Zen3入门神U', 100, 1, '2025-10-06 18:17:02', '2025-10-06 19:52:21', '1', '1');
INSERT INTO `component_variants` VALUES (44, 44, 'A版本', 4999.00, 'iGame系列独家定制柒小希系列主板', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:12:55', '1', '1');
INSERT INTO `component_variants` VALUES (45, 45, 'A版本', 7999.00, 'COLORFIRE系列独家定制萌宠“疾风”主板', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:10:37', '1', '1');
INSERT INTO `component_variants` VALUES (46, 46, 'A版本', 6999.00, 'iGame系列定制火神散热铠甲，搭配全覆盖式冷凝片强化散热', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:14:24', '1', '1');
INSERT INTO `component_variants` VALUES (47, 47, 'A版本', 4499.00, '10 Gb/s USB3.1接口，支持多显卡技术(CrossFireX技术)', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:16:51', '1', '1');
INSERT INTO `component_variants` VALUES (48, 48, 'A版本', 5499.00, '英特尔® LGA 4677 插槽: 支持Intel Xeon® W-3400 & W-2400 处理器', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:15:24', '1', '1');
INSERT INTO `component_variants` VALUES (49, 49, 'A版本', 1799.00, '网驰系列定制寒霜散热装甲，搭配全覆盖式冷凝片强化散热', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:19:22', '1', '1');
INSERT INTO `component_variants` VALUES (50, 50, 'A版本', 1299.00, 'Intel H610芯片组，支持双通道DDR4-3200MHz内存', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:19:22', '1', '1');
INSERT INTO `component_variants` VALUES (51, 51, 'A版本', 1099.00, '支持双通道DDR4-3200MHz内存', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:19:22', '1', '1');
INSERT INTO `component_variants` VALUES (52, 52, 'A版本', 1299.00, '原厂高品质DRAM颗粒，Colorfire系列产品，性价比之选', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:21:27', '1', '1');
INSERT INTO `component_variants` VALUES (53, 53, 'A版本', 999.00, ' 提供良好的兼容性，确保系统的稳定运行', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:21:27', '1', '1');
INSERT INTO `component_variants` VALUES (54, 54, 'A版本', 1599.00, '原厂高品质DRAM颗粒，提供良好的兼容性，确保系统的稳定运行且留有巨大超频潜力', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:24:01', '1', '1');
INSERT INTO `component_variants` VALUES (55, 55, 'A版本', 849.00, '采用全金属马甲散热设计，散热更加高效，运行更加稳定', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:24:01', '1', '1');
INSERT INTO `component_variants` VALUES (56, 56, 'A版本', 699.00, '运用Double-data-rate架构的采用，让内存每个时钟周期有两个数据传输，使得玩家的游戏体验更加顺畅', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:24:42', '1', '1');
INSERT INTO `component_variants` VALUES (57, 57, 'A版本', 749.00, '精选颗粒，确保可靠耐用', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:24:01', '1', '1');
INSERT INTO `component_variants` VALUES (58, 58, 'A版本', 799.00, '国产DDR5，性价比高，稳定', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:25:39', '1', '1');
INSERT INTO `component_variants` VALUES (59, 59, 'A版本', 899.00, 'RGB灯效，性能均衡，颜值高', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:25:39', '1', '1');
INSERT INTO `component_variants` VALUES (60, 60, 'A版本', 899.00, 'PCIe4.0，7000MB/s，旗舰性能', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:29:07', '1', '1');
INSERT INTO `component_variants` VALUES (61, 61, 'A版本', 849.00, 'PCIe4.0，7300MB/s，游戏旗舰', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:29:07', '1', '1');
INSERT INTO `component_variants` VALUES (62, 62, 'A版本', 799.00, 'PCIe4.0，高性能，适合工作站', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:29:07', '1', '1');
INSERT INTO `component_variants` VALUES (63, 63, 'A版本', 599.00, '国产PCIe4.0，长江存储，高性价比', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:29:07', '1', '1');
INSERT INTO `component_variants` VALUES (64, 64, 'A版本', 999.00, 'PCIe4.0，高速读写，5年质保', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:29:07', '1', '1');
INSERT INTO `component_variants` VALUES (65, 65, 'A版本', 699.00, 'PCIe3.0，经典款，稳定可靠', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:29:07', '1', '1');
INSERT INTO `component_variants` VALUES (66, 66, 'A版本', 749.00, 'PCIe4.0，高速读写，性价比高', 100, 1, '2025-10-06 18:24:20', '2025-10-06 20:30:15', '1', '1');
INSERT INTO `component_variants` VALUES (67, 67, 'A版本', 499.00, '国产PCIe4.0，高性价比，5年保', 100, 1, '2025-10-06 18:24:20', '2025-10-06 18:24:20', '1', '1');
INSERT INTO `component_variants` VALUES (68, 1, 'B版本', 520.00, '你好', 100, 1, '2025-10-06 18:24:20', '2025-10-06 18:24:20', '1', '1');

-- ----------------------------
-- Table structure for components
-- ----------------------------
DROP TABLE IF EXISTS `components`;
CREATE TABLE `components`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `category_id` int NOT NULL,
  `image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `common_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `status` tinyint NULL DEFAULT 1,
  `sort` int NULL DEFAULT 0,
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of components
-- ----------------------------
INSERT INTO `components` VALUES (1, 'GeForce RTX 4090', 1, 'https://www.colorful.cn/uploads/AttachFile/202303311055003239.JPG', '当前性能霸主，Ada旗舰核心', 1, 0, '2025-10-06 12:32:23', '2025-10-06 19:29:43', '1', '1');
INSERT INTO `components` VALUES (2, 'GeForce RTX 3090', 1, 'https://www.colorful.cn/uploads/AttachFile/202106291651596202.JPG', 'Ampere旗舰，24G显存，8K游戏卡', 1, 0, '2025-10-06 12:32:23', '2025-10-06 19:32:48', '1', '1');
INSERT INTO `components` VALUES (3, 'GeForce RTX 3080', 1, 'https://www.colorful.cn/uploads/AttachFile/202201141654532556.JPG', 'Ampere架构，性能性价比颠覆之作', 1, 0, '2025-10-06 12:32:23', '2025-10-06 19:29:14', '1', '1');
INSERT INTO `components` VALUES (4, 'GeForce RTX 2080 Ti', 1, 'https://www.colorful.cn/uploads/AttachFile/202106291651596202.JPG', '图灵旗舰，首代光追核心', 1, 0, '2025-10-06 12:32:23', '2025-10-06 19:30:13', '1', '1');
INSERT INTO `components` VALUES (5, 'GeForce GTX 1080 Ti', 1, 'https://www.colorful.cn/uploads/AttachFile/201709121732259124.JPG', 'Pascal架构终极卡皇，性价比旗舰', 1, 0, '2025-10-06 12:32:23', '2025-10-06 19:30:47', '1', '1');
INSERT INTO `components` VALUES (6, 'GeForce GTX 1080', 1, 'https://www.colorful.cn/uploads/AttachFile/201807171717152497.JPG', 'Pascal首发，16nm工艺飞跃', 1, 0, '2025-10-06 12:32:23', '2025-10-06 19:31:11', '1', '1');
INSERT INTO `components` VALUES (7, 'GeForce GTX 980 Ti', 1, 'https://www.colorful.cn/Resource/2014/09/201409191723410446.JPG', 'Maxwell架构旗舰，性能强悍', 1, 0, '2025-10-06 12:32:23', '2025-10-06 19:31:38', '1', '1');
INSERT INTO `components` VALUES (13, 'Radeon RX 7900 XTX', 2, 'https://2e.zol-img.com.cn/product/258/768/cewuNT0eQmeV2.jpg', 'RDNA3旗舰，挑战4080', 1, 0, '2025-10-06 12:32:23', '2025-10-06 19:39:11', '1', '1');
INSERT INTO `components` VALUES (14, 'Radeon RX 6950 XT', 2, 'https://2c.zol-img.com.cn/product/256/848/ceNo2QVmY0qA.jpg', 'RDNA2终极版，性能增强', 1, 0, '2025-10-06 12:32:23', '2025-10-06 19:38:20', '1', '1');
INSERT INTO `components` VALUES (15, 'Radeon RX 6900 XT', 2, 'https://2a.zol-img.com.cn/product/221/232/ce8QwQOlY3kM2.jpg', 'RDNA2旗舰，正面竞争N卡高端', 1, 0, '2025-10-06 12:32:23', '2025-10-06 19:40:26', '1', '1');
INSERT INTO `components` VALUES (16, 'Radeon VII', 2, 'https://2f.zol-img.com.cn/product/195/193/cea9vVOkzxMw.jpg', '7nm工艺首秀，16G HBM2显存', 1, 0, '2025-10-06 12:32:23', '2025-10-06 19:41:09', '1', '1');
INSERT INTO `components` VALUES (17, 'Radeon RX Vega 64', 2, 'https://2c.zol-img.com.cn/product/188/154/ceX2ptkplxyKQ.jpg', 'Vega架构，HBM2显存', 1, 0, '2025-10-06 12:32:23', '2025-10-06 19:41:48', '1', '1');
INSERT INTO `components` VALUES (18, 'Radeon RX 480', 2, 'https://2e.zol-img.com.cn/product/175/978/ceCmEPSzJ6vA.jpg', 'Polaris架构，主流市场甜点', 1, 0, '2025-10-06 12:32:23', '2025-10-06 19:42:17', '1', '1');
INSERT INTO `components` VALUES (24, 'Core i9-14900K', 3, 'https://intelcorp.scene7.com/is/image/intelcorp/badge-core-processor-series1:1920-1080?wid=480&hei=270&fmt=webp-alpha', '24核32线程，最高6.0GHz，当前旗舰游戏处理器', 1, 0, '2025-10-06 18:16:28', '2025-10-06 19:47:52', '1', '1');
INSERT INTO `components` VALUES (25, 'Core i9-13900KS', 3, 'https://intelcorp.scene7.com/is/image/intelcorp/badge-core-processor-series1:1920-1080?wid=480&hei=270&fmt=webp-alpha', '24核32线程，睿频6.0GHz，13代特别版', 1, 0, '2025-10-06 18:16:28', '2025-10-06 19:48:14', '1', '1');
INSERT INTO `components` VALUES (26, 'Core i7-14700K', 3, 'https://intelcorp.scene7.com/is/image/intelcorp/badge-core-processor-series1:1920-1080?wid=480&hei=270&fmt=webp-alpha', '20核28线程，性价比高的高性能处理器', 1, 0, '2025-10-06 18:16:28', '2025-10-06 19:48:14', '1', '1');
INSERT INTO `components` VALUES (27, 'Core i5-14600K', 3, 'https://intelcorp.scene7.com/is/image/intelcorp/badge-core-processor-series1:1920-1080?wid=480&hei=270&fmt=webp-alpha', '14核20线程，主流游戏最佳选择', 1, 0, '2025-10-06 18:16:28', '2025-10-06 19:48:14', '1', '1');
INSERT INTO `components` VALUES (28, 'Core i9-12900K', 3, 'https://intelcorp.scene7.com/is/image/intelcorp/badge-core-processor-series1:1920-1080?wid=480&hei=270&fmt=webp-alpha', '16核24线程，12代旗舰，混合架构开创者', 1, 0, '2025-10-06 18:16:28', '2025-10-06 19:48:14', '1', '1');
INSERT INTO `components` VALUES (29, 'Core i7-12700K', 3, 'https://intelcorp.scene7.com/is/image/intelcorp/badge-core-processor-series1:1920-1080?wid=480&hei=270&fmt=webp-alpha', '12核20线程，性能均衡的多面手', 1, 0, '2025-10-06 18:16:28', '2025-10-06 19:48:14', '1', '1');
INSERT INTO `components` VALUES (30, 'Core i5-13600K', 3, 'https://intelcorp.scene7.com/is/image/intelcorp/badge-core-processor-series1:1920-1080?wid=480&hei=270&fmt=webp-alpha', '14核20线程，13代性价比之王', 1, 0, '2025-10-06 18:16:28', '2025-10-06 19:48:14', '1', '1');
INSERT INTO `components` VALUES (31, 'Core i9-11900K', 3, 'https://intelcorp.scene7.com/is/image/intelcorp/badge-core-processor-series1:1920-1080?wid=480&hei=270&fmt=webp-alpha', '8核16线程，11代旗舰，高频率优化', 1, 0, '2025-10-06 18:16:28', '2025-10-06 19:48:14', '1', '1');
INSERT INTO `components` VALUES (32, 'Core i7-11700K', 3, 'https://intelcorp.scene7.com/is/image/intelcorp/badge-core-processor-series1:1920-1080?wid=480&hei=270&fmt=webp-alpha', '8核16线程，支持PCIe 4.0', 1, 0, '2025-10-06 18:16:28', '2025-10-06 19:48:14', '1', '1');
INSERT INTO `components` VALUES (33, 'Core i5-12600K', 3, 'https://intelcorp.scene7.com/is/image/intelcorp/badge-core-processor-series1:1920-1080?wid=480&hei=270&fmt=webp-alpha', '10核16线程，入门级高性能选择', 1, 0, '2025-10-06 18:16:28', '2025-10-06 19:48:14', '1', '1');
INSERT INTO `components` VALUES (34, 'Ryzen 9 7950X3D', 4, 'https://www.amd.com/content/dam/amd/en/images/products/processors/ryzen/2505503-ryzen-9-7900x3d.jpg', '16核32线程，3D V-Cache技术，游戏性能顶级', 1, 0, '2025-10-06 18:17:02', '2025-10-06 19:50:40', '1', '1');
INSERT INTO `components` VALUES (35, 'Ryzen 9 7950X', 4, 'https://www.amd.com/content/dam/amd/en/images/products/processors/ryzen/2505503-ryzen-9-7900x.jpg', '16核32线程，Zen4架构旗舰，多核性能强劲', 1, 0, '2025-10-06 18:17:02', '2025-10-06 19:50:40', '1', '1');
INSERT INTO `components` VALUES (36, 'Ryzen 9 5950X', 4, 'https://www.amd.com/content/dam/amd/en/images/products/processors/ryzen/2505503-ryzen-9-5900x.jpg', '16核32线程，Zen3旗舰，生产力利器', 1, 0, '2025-10-06 18:17:02', '2025-10-06 19:50:40', '1', '1');
INSERT INTO `components` VALUES (37, 'Ryzen 7 7800X3D', 4, 'https://www.amd.com/content/dam/amd/en/images/products/processors/ryzen/2505503-ryzen-7-7800x3d.jpg', '8核16线程，游戏性能最强CPU之一', 1, 0, '2025-10-06 18:17:02', '2025-10-06 19:50:40', '1', '1');
INSERT INTO `components` VALUES (38, 'Ryzen 7 7700X', 4, 'https://www.amd.com/content/dam/amd/en/images/products/processors/ryzen/2505503-ryzen-7-7700x.jpg', '8核16线程，Zen4架构，均衡之选', 1, 0, '2025-10-06 18:17:02', '2025-10-06 19:50:40', '1', '1');
INSERT INTO `components` VALUES (39, 'Ryzen 5 7600X', 4, 'https://www.amd.com/content/dam/amd/en/images/products/processors/ryzen/2505503-ryzen-5-7600x.jpg', '6核12线程，入门Zen4，性价比高', 1, 0, '2025-10-06 18:17:02', '2025-10-06 19:51:34', '1', '1');
INSERT INTO `components` VALUES (40, 'Ryzen 9 5900X', 4, 'https://www.amd.com/content/dam/amd/en/images/products/processors/ryzen/2505503-ryzen-9-5900x.jpg', '12核24线程，Zen3经典，多核性能优秀', 1, 0, '2025-10-06 18:17:02', '2025-10-06 19:51:34', '1', '1');
INSERT INTO `components` VALUES (41, 'Ryzen 7 5800X3D', 4, 'https://www.amd.com/content/dam/amd/en/images/products/processors/ryzen/2505503-ryzen-7-5800x3d.jpg', '8核16线程，首款3D V-Cache处理器', 1, 0, '2025-10-06 18:17:02', '2025-10-06 19:52:21', '1', '1');
INSERT INTO `components` VALUES (42, 'Ryzen 5 5600X', 4, 'https://www.amd.com/content/dam/amd/en/images/products/processors/ryzen/2505503-ryzen-5-5600x.jpg', '6核12线程，Zen3入门神U', 1, 0, '2025-10-06 18:17:02', '2025-10-06 19:52:21', '1', '1');
INSERT INTO `components` VALUES (44, 'iGame X870 柒小希首发纪念版 V14/iGame X870 Senna V14', 5, 'https://www.colorful.cn/content/upload/form/84/202508/24276f8e-2142-4ab2-8859-fb2269a3d8e2.png', 'iGame系列独家定制柒小希系列主板', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:12:55', '1', '1');
INSERT INTO `components` VALUES (45, 'COLORFIRE B760M-MEOW WIFI D5 暗影紫', 5, 'https://www.colorful.cn/content/upload/form/84/202310/d6b78aa1-374b-433c-9606-b8b176f83292.JPG', 'COLORFIRE系列独家定制萌宠“疾风”主板', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:10:37', '1', '1');
INSERT INTO `components` VALUES (46, 'iGame Z790D5 VULCAN X V20', 5, 'https://www.colorful.cn/content/upload/form/84/202408/88cbf2b2-9e1d-459f-bccd-052749388f76.png', 'iGame系列定制火神散热铠甲，搭配全覆盖式冷凝片强化散热', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:14:24', '1', '1');
INSERT INTO `components` VALUES (47, '战旗C.X370AK X5 V14', 5, 'https://www.colorful.cn/uploads/AttachFile/201711291634138483.JPG', '10 Gb/s USB3.1接口，支持多显卡技术(CrossFireX技术)', NULL, 0, '2025-10-06 18:24:20', '2025-10-06 20:16:51', '1', '1');
INSERT INTO `components` VALUES (48, 'COLORFUL WS W790E PRO V20', 5, 'https://www.colorful.cn/content/upload/form/84/202507/b0eb7723-82a1-4a46-a2dc-148355a5ff09.png', '英特尔® LGA 4677 插槽: 支持Intel Xeon® W-3400 & W-2400 处理器', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:15:24', '1', '1');
INSERT INTO `components` VALUES (49, '网驰 Z890M-PLUS DELUXE V20', 5, 'https://www.colorful.cn/content/upload/form/84/202502/bb77a732-80f1-4549-9112-7607e7a8cd75.png', '网驰系列定制寒霜散热装甲，搭配全覆盖式冷凝片强化散热', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:19:22', '1', '1');
INSERT INTO `components` VALUES (50, 'BATTLE-AX H610M-A WIFI V20', 5, 'https://www.colorful.cn/content/upload/form/84/202407/e083b0fa-fd42-4809-8ba7-b912a2a0aac8.png', 'Intel H610芯片组，支持双通道DDR4-3200MHz内存', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:19:22', '1', '1');
INSERT INTO `components` VALUES (51, 'BATTLE-AX H610M-E WIFI V21', 5, 'https://www.colorful.cn/content/upload/form/84/202409/e44a4091-2e20-4eb8-9ed0-554b745f3f99.png', '支持双通道DDR4-3200MHz内存', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:19:22', '1', '1');
INSERT INTO `components` VALUES (52, 'MEOW 橘影橙 DDR5 16G*2 6400', 6, 'https://www.colorful.cn/content/upload/form/143/202410/a6b2d658-305f-4a20-85b5-0e6085aec733.jpg', '原厂高品质DRAM颗粒，Colorfire系列产品，性价比之选', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:21:27', '1', '1');
INSERT INTO `components` VALUES (53, 'COLORFIRE DDR5 16G 6000 天蝎座', 6, 'https://www.colorful.cn/content/upload/form/143/202409/cdfb61d9-a3ed-4f66-a375-53629e31d7fc.jpg', ' 提供良好的兼容性，确保系统的稳定运行', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:21:27', '1', '1');
INSERT INTO `components` VALUES (54, 'iGame ShadowⅡ·白 DDR5 48G*2 6000 C30', 6, 'https://www.colorful.cn/content/upload/form/143/202509/19eba8c3-fb03-43e1-9d5f-7867fa3c2b4d.jpg', '原厂高品质DRAM颗粒，提供良好的兼容性，确保系统的稳定运行且留有巨大超频潜力', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:24:01', '1', '1');
INSERT INTO `components` VALUES (55, '战斧 DDR5 16G*2 6400', 6, 'https://www.colorful.cn/content/upload/form/143/202407/7ee73676-9397-4d84-b967-6a3d116c5125.jpg', '采用全金属马甲散热设计，散热更加高效，运行更加稳定', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:24:01', '1', '1');
INSERT INTO `components` VALUES (56, 'iGame DDR4 8G*2 4266', 6, 'https://www.colorful.cn/uploads/AttachFile/202104191648096580.png', '运用Double-data-rate架构的采用，让内存每个时钟周期有两个数据传输，使得玩家的游戏体验更加顺畅', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:24:42', '1', '1');
INSERT INTO `components` VALUES (57, '战斧 DDR5 16G*2 5600（赤焰白）', 6, 'https://www.colorful.cn/content/upload/form/143/202401/3c875c44-51a7-4160-92bd-364479ace767.jpg', '精选颗粒，确保可靠耐用', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:24:01', '1', '1');
INSERT INTO `components` VALUES (58, 'MEOW 橘影橙 DDR4 8G*2 3600', 6, 'https://www.colorful.cn/content/upload/form/143/202403/d763bd4c-630f-4c97-9cd9-8ceba8850a4f.jpg', '国产DDR5，性价比高，稳定', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:25:39', '1', '1');
INSERT INTO `components` VALUES (59, 'iGame DDR4 8G 3200 K01E/C', 6, 'https://www.colorful.cn/uploads/AttachFile/201809050958407654.JPG', 'RGB灯效，性能均衡，颜值高', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:25:39', '1', '1');
INSERT INTO `components` VALUES (60, 'CN600 256GB 战戟', 7, 'https://www.colorful.cn/uploads/AttachFile/202112241621405857.jpg', 'PCIe4.0，7000MB/s，旗舰性能', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:29:07', '1', '1');
INSERT INTO `components` VALUES (61, 'CN700 512GB 战戟', 7, 'https://www.colorful.cn/uploads/AttachFile/202212151544069178.jpg', 'PCIe4.0，7300MB/s，游戏旗舰', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:29:07', '1', '1');
INSERT INTO `components` VALUES (62, 'CN600 512GB PLUS', 7, 'https://www.colorful.cn/content/upload/form/122/202411/18a3e869-86e3-4a52-9abe-eb62f51ec054.JPG', 'PCIe4.0，高性能，适合工作站', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:29:07', '1', '1');
INSERT INTO `components` VALUES (63, 'CN700SE 2TB', 7, 'https://www.colorful.cn/content/upload/form/122/202409/bde53f21-7023-4776-96d0-eebece02bbfd.jpg', '国产PCIe4.0，长江存储，高性价比', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:29:07', '1', '1');
INSERT INTO `components` VALUES (64, 'CN700 1T PLUS', 7, 'https://www.colorful.cn/content/upload/form/122/202311/61ab308c-e66c-40f2-8145-06e10ca2e58f.JPG', 'PCIe4.0，高速读写，5年质保', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:29:07', '1', '1');
INSERT INTO `components` VALUES (65, 'ME700 橘影橙 1TB', 7, 'https://www.colorful.cn/content/upload/form/122/202409/d3ab96e8-4ab1-40a3-b492-709be781f919.jpg', 'PCIe3.0，经典款，稳定可靠', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:29:07', '1', '1');
INSERT INTO `components` VALUES (66, 'CN600 2TB DDR', 7, 'https://www.colorful.cn/uploads/AttachFile/202303311411577497.jpg', 'PCIe4.0，高速读写，性价比高', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:30:15', '1', '1');
INSERT INTO `components` VALUES (67, 'CN600 512GB PRO', 7, 'https://www.colorful.cn/uploads/AttachFile/202210131028171540.jpg', '国产PCIe4.0，高性价比，5年保', 1, 0, '2025-10-06 18:24:20', '2025-10-06 20:30:15', '1', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '密码',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '姓名',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '手机号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '员工信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'laolao', 'e10adc3949ba59abbe56e057f20f883e', '劳劳', '18760526365', '2025-10-03 18:26:51');

SET FOREIGN_KEY_CHECKS = 1;
