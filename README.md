# 劳劳的装机工坊

**劳劳的装机工坊（LaoLao PC Builds）** 是一个面向 PC DIY 玩家的一站式装机与社区平台。项目同时提供装机商城与社区论坛：用户可以自由挑选 CPU / GPU 等配件或整机套餐、查看实时库存与优惠信息，并在社区中分享装机经验、讨论技术问题。系统包含完善的登录注册、订单履约、优惠券发放、图片上传与地址联动等业务链路。

## 功能亮点

- **装机商城**
  - 多级商品分类（配件与整机）、版本（如显存容量）、热销榜单。
  - 购物车、订单、优惠券、收货地址（高德地图行政区联动）等交易闭环。
  - 定时任务自动关闭超时未支付订单，Redis 缓存热点数据。
- **装机社区**
  - 帖子分类、热门榜、详情页。
  - 评论 / 回复 / 点赞与多图上传，图片托管在阿里云 OSS。
- **用户中心**
  - 支持邮箱验证码与账号密码登录，注册后自动登录。
  - 基于 JWT + 拦截器的身份校验、Cookies 会话管理。
  - 个人主页、资料编辑、点赞帖子收藏夹。
- **通用能力**
  - `image/` 目录提供示例物料，可作为 README 截图或商品展示。
  - `laolao_pc_builds.sql` 提供完整数据库初始化脚本，开箱即用。

## 技术栈

| 层级 | 技术 |
| ---- | ---- |
| 后端 | Spring Boot 3.5 · Web / WebFlux · MyBatis & PageHelper · MySQL · Redis · MapStruct · JWT · Alibaba OSS & DirectMail · Druid |
| 前端 | Vue 3 + TypeScript + Vite · Pinia · Vue Router · Tailwind CSS v4 · Axios · lucide 图标 · @vueuse/core |
| 配套 | Maven Wrapper · npm / Node.js · 阿里云对象存储 · 高德地图开放平台 |

## 目录结构

```
├─ README.md                # 项目说明（当前文件）
├─ image/                   # 示例产品图、CPU/GPU/配件素材
├─ laolao_pc_builds.sql     # MySQL 初始化脚本
├─ laolao-pc-builds/        # Spring Boot 后端
│  ├─ src/main/java/com/laolao
│  │  ├─ controller/        # 用户/商城/论坛/通用接口
│  │  ├─ service/           # 业务实现（含定时任务）
│  │  ├─ mapper/pojo/       # MyBatis 映射与实体/DTO/VO
│  │  └─ common/            # 配置、常量、工具类（JWT、Ali、AMap 等）
│  └─ src/main/resources/
│     ├─ application*.yaml  # 多环境配置
│     └─ mapper/            # MyBatis XML
└─ laolao-pc-builds-vue/    # Vue 3 + Vite 前端
   ├─ src/components/       # 商城、论坛、用户模块 UI
   ├─ src/stores/           # Pinia 状态（商品、论坛、用户）
   ├─ src/router/           # 路由与页面切换
   └─ src/utils/            # axios 封装、工具函数
```

## 环境要求

- JDK 17+
- Maven 3.9+（已提供 `mvnw` / `mvnw.cmd` 可免安装）
- Node.js 20+ / npm 10+
- MySQL 8.x
- Redis 6+
- 可选：阿里云 OSS、阿里云邮件推送、高德地图 Web 服务 Key（用于生产环境）

## 快速开始

### 1. 克隆仓库

```bash
git clone https://github.com/your-org/laolao-pc-builds.git
cd laolao-pc-builds
```

### 2. 初始化数据库

1. 创建数据库 `laolao_pc_builds`。
2. 导入根目录下的 `laolao_pc_builds.sql`。

```bash
mysql -u root -p laolao_pc_builds < laolao_pc_builds.sql
```

### 3. 配置后端

1. 复制 `laolao-pc-builds/src/main/resources/application-dev.yaml`，按需修改：
   - `laolao.datasource.*`：MySQL 地址与凭证
   - `laolao.redis.*`：Redis 连接
   - `laolao.aliyun.*`：OSS、邮件推送密钥（如不使用，可保留占位）
   - `laolao.amap.key`：高德地图 Key
2. 其他环境可通过 `application-prod.yaml` 自定义。

### 4. 启动后端

```bash
cd laolao-pc-builds
./mvnw spring-boot:run
# Windows: mvnw.cmd spring-boot:run
```

服务默认监听 `http://localhost:8080`。

### 5. 启动前端

```bash
cd laolao-pc-builds-vue
npm install
npm run dev
```

Vite Dev Server 默认在 `http://localhost:5173` 运行，`vite.config.ts` 已将 `/api` 代理至 `http://localhost:8080`，确保前后端联调零配置。

## 关键配置说明

| 配置项 | 作用 |
| ------ | ---- |
| `spring.datasource` | 指定 MySQL 连接参数 |
| `spring.data.redis` | Redis 连接，缓存用户态与热点数据 |
| `laolao.jwt.user-secret-key` | JWT 签名秘钥，自行更换并妥善保管 |
| `aliyun.oss.*` | 阿里云 OSS 存储桶信息，用于用户上传图片 |
| `aliyun.access-key.*` | 阿里云邮件推送 AK/SK，负责发送邮箱验证码 |
| `amap.key` | 高德地图 Web 服务 Key，用于地址解析 / 三级联动 |

> 提示：开发阶段如暂不接入云服务，可将 OSS / 邮件逻辑替换为本地实现或 Mock。

## 常用命令

| 场景 | 命令 |
| ---- | ---- |
| 后端单元测试 | `./mvnw test` |
| 后端打包 | `./mvnw clean package -DskipTests` |
| 前端类型检查 | `npm run type-check`（需在 `laolao-pc-builds-vue`） |
| 前端构建 | `npm run build` |

## 开发计划

- [ ] 接入后台 CMS，支持商品 / 帖子审核。
- [ ] 订单支付通道对接（支付宝 / 微信）。
- [ ] 实时聊天 / 评论 @ 通知。
- [ ] 产品推荐算法（基于用户行为的配置推荐）。

欢迎 Issue / PR，一起把装机体验做到极致 ✨


