# 劳劳的装机工坊

**劳劳的装机工坊（LaoLao PC Builds）** 是一个面向 PC DIY 玩家的一站式装机与社区平台。项目同时提供装机商城与社区论坛：用户可以自由挑选 CPU / GPU 等配件或整机套餐、查看实时库存与优惠信息，并在社区中分享装机经验、讨论技术问题。系统包含完善的登录注册、订单履约、优惠券发放、图片上传与地址联动等业务链路。

## 功能亮点

### 用户端功能

- **装机商城**
  - 多级商品分类（配件与整机）、商品变体（如显存容量）、热销榜单。
  - 购物车管理、订单创建与查询、优惠券使用、收货地址管理（高德地图行政区三级联动）。
  - 定时任务自动关闭超时未支付订单（15分钟），RocketMQ 异步消息队列处理订单超时。
  - 布隆过滤器优化商品查询性能，防止缓存穿透。
  - 支持整机套餐（Bundle）和单个配件（Component）两种商品类型。
- **装机社区**
  - 帖子分类浏览、热门榜、详情页。
  - 评论 / 回复 / 点赞功能，支持多图上传，图片托管在阿里云 OSS。
  - 帖子收藏与个人主页展示。
- **用户中心**
  - 支持邮箱验证码与账号密码登录，注册后自动登录。
  - 基于 JWT + 拦截器的身份校验、Cookies 会话管理。
  - 个人主页、资料编辑、订单历史查询。

### 管理端功能

- **数据统计 Dashboard**：提供用户、商城、论坛三个维度的数据可视化仪表盘，包含趋势图表、排名统计、实时数据等。
- **用户管理**：用户信息查看、编辑、状态管理。
- **商城管理**：商品分类、配件管理、整机套餐管理、优惠券管理、商品变体管理。
- **论坛管理**：论坛分类管理、帖子审核与管理、评论管理。
- **实时通知**：基于 WebSocket 的实时在线用户统计、新订单通知等功能。

### 通用能力

- `laolaoPC/` 目录提供示例物料（商品图片、用户头像、论坛分类图标等），可作为商品展示。
- `laolao_pc_builds.sql` 提供完整数据库初始化脚本，开箱即用。
- 文件上传管理，支持阿里云 OSS 存储。
- SQL 日志记录与拦截器，便于调试与监控。
- RocketMQ 消息队列，支持异步订单超时处理。
- 布隆过滤器优化查询性能，防止缓存穿透。
- WebSocket 实时通信，支持在线用户统计、实时通知推送。
- 数据可视化，基于 ECharts 的图表展示，支持趋势分析、排名统计等。

## 技术栈

| 层级 | 技术 |
| ---- | ---- |
| 后端 | Spring Boot 3.5.6 · Spring Web / WebFlux · Spring WebSocket · MyBatis 3.0.5 & PageHelper 2.1.1 · MySQL 8.x · Redis & Redisson 4.1.0 · RocketMQ 2.3.5 · MapStruct 1.6.3 · JWT (jjwt 0.13.0) · Alibaba OSS 3.18.3 & DirectMail · Druid 1.2.27 · Lombok 1.18.42 · Spring AOP · 定时任务（@Scheduled） · 布隆过滤器 |
| 前端 | Vue 3.5.22 + TypeScript 5.9.3 + Vite 7.1.7 · Pinia 3.0.3 · Vue Router 4.5.1 · Tailwind CSS v4.1.14 · shadcn-vue (reka-ui 2.6.1) · Axios 1.12.2 · lucide-vue-next 0.544.0 · @vueuse/core 13.9.0 · vue-sonner 2.0.9 · vue3-cookies 1.0.6 · dayjs 1.11.18 · ECharts 6.0.0 & vue-echarts 8.0.1 · @tanstack/vue-table 8.21.3 |
| 配套 | Maven Wrapper · npm / Node.js 20+ · 阿里云对象存储（OSS）· 阿里云邮件推送（DirectMail）· 高德地图开放平台 Web 服务 API |

## 目录结构

```
├─ README.md                    # 项目说明（当前文件）
├─ laolao_pc_builds.sql         # MySQL 数据库初始化脚本
├─ laolaoPC/                    # 静态资源目录（商品图片、用户头像、论坛分类图标等）
│  ├─ shop/                     # 商城相关图片
│  │  ├─ component/             # 配件图片（按分类编号）
│  │  ├─ bundle/                 # 整机套餐图片
│  │  ├─ category/              # 商城分类图标
│  │  └─ common/                # 商城通用图片
│  ├─ forum/                    # 论坛相关图片
│  │  ├─ category/               # 论坛分类图标
│  │  ├─ post/                   # 帖子图片
│  │  └─ comment/                # 评论图片
│  └─ user/                     # 用户相关图片
│     └─ avatar/                 # 用户头像
├─ laolao-pc-builds/             # Spring Boot 后端
│  ├─ src/main/java/com/laolao/
│  │  ├─ controller/             # 控制器层
│  │  │  ├─ admin/               # 管理端接口（用户、商城、论坛、Dashboard）
│  │  │  │  └─ dashboard/       # Dashboard 统计接口
│  │  │  ├─ user/                # 用户端接口（商城、论坛、用户）
│  │  │  └─ common/              # 通用接口（文件上传、用户信息）
│  │  ├─ service/                # 业务逻辑层
│  │  │  ├─ admin/               # 管理端服务
│  │  │  ├─ user/                # 用户端服务
│  │  │  └─ common/              # 通用服务
│  │  ├─ consumer/               # RocketMQ 消息消费者
│  │  ├─ mapper/                 # MyBatis 数据访问层
│  │  │  ├─ admin/               # 管理端 Mapper
│  │  │  ├─ user/                # 用户端 Mapper
│  │  │  ├─ common/              # 通用 Mapper
│  │  │  └─ dashboard/          # Dashboard 统计 Mapper
│  │  ├─ pojo/                   # 实体类、DTO、VO
│  │  │  ├─ shop/                # 商城相关实体
│  │  │  ├─ forum/               # 论坛相关实体
│  │  │  └─ user/                # 用户相关实体
│  │  ├─ common/                 # 通用组件
│  │  │  ├─ config/              # 配置类（MyBatis、Redis、WebSocket）
│  │  │  ├─ constant/            # 常量定义
│  │  │  ├─ context/             # 上下文（用户上下文、SQL日志上下文）
│  │  │  ├─ exception/           # 异常定义
│  │  │  ├─ handler/             # 处理器（全局异常、类型转换）
│  │  │  ├─ init/                # 初始化器（布隆过滤器初始化）
│  │  │  ├─ properties/          # 配置属性类
│  │  │  ├─ result/              # 统一响应结果
│  │  │  ├─ utils/               # 工具类（JWT、OSS、邮件、地图）
│  │  │  └─ websocket/           # WebSocket 处理器与拦截器
│  │  ├─ converter/              # MapStruct 转换器
│  │  ├─ Interceptor/            # 拦截器（登录、管理员、SQL日志、自动填充）
│  │  ├─ task/                   # 定时任务（订单超时处理、文件清理）
│  │  ├─ webconfig/              # Web 配置（跨域、拦截器注册）
│  │  └─ LaolaoPcBuildsApplication.java
│  └─ src/main/resources/
│     ├─ application.yaml        # 主配置文件
│     ├─ application-dev.yaml    # 开发环境配置
│     ├─ application-prod.yaml   # 生产环境配置
│     ├─ logback-spring.xml      # 日志配置
│     └─ mapper/                 # MyBatis XML 映射文件
│        ├─ admin/               # 管理端 Mapper XML
│        ├─ user/                # 用户端 Mapper XML
│        └─ common/              # 通用 Mapper XML
└─ laolao-pc-builds-vue/         # Vue 3 + Vite 前端
   ├─ src/
   │  ├─ components/              # 组件目录
   │  │  ├─ admin/               # 管理端组件
   │  │  │  ├─ AdminLayout.vue   # 管理端布局
   │  │  │  ├─ dashboard/        # Dashboard 统计组件（用户、商城、论坛）
   │  │  │  ├─ shop/             # 商城管理组件
   │  │  │  ├─ forum/            # 论坛管理组件
   │  │  │  ├─ user/             # 用户管理组件
   │  │  │  └─ sidebar/          # 侧边栏组件
   │  │  ├─ front/               # 用户端组件
   │  │  │  ├─ common/           # 通用组件（首页、布局）
   │  │  │  ├─ shop/             # 商城组件
   │  │  │  ├─ forum/            # 论坛组件
   │  │  │  └─ user/             # 用户中心组件
   │  │  ├─ common/              # 公共组件（登录注册、文件管理）
   │  │  └─ ui/                  # shadcn-vue UI 组件库
   │  ├─ stores/                 # Pinia 状态管理
   │  │  ├─ UserStore.ts         # 用户状态
   │  │  ├─ PostStore.ts         # 帖子状态
   │  │  ├─ ForumCategoryStore.ts # 论坛分类状态
   │  │  ├─ CommonStore.ts       # 通用状态
   │  │  └─ websocketStore.ts    # WebSocket 连接状态
   │  ├─ router/                 # 路由配置
   │  │  └─ index.ts             # 路由定义
   │  ├─ utils/                  # 工具函数
   │  │  └─ myAxios.ts           # Axios 封装
   │  ├─ lib/                    # 库文件
   │  │  └─ utils.ts             # 通用工具函数
   │  ├─ App.vue                 # 根组件
   │  ├─ main.ts                 # 入口文件
   │  └─ style.css               # 全局样式
   ├─ public/                    # 静态资源
   ├─ components.json            # shadcn-vue 配置
   ├─ package.json               # 依赖配置
   ├─ vite.config.ts             # Vite 配置
   └─ tsconfig.json              # TypeScript 配置
```

## 环境要求

### 必需环境

- **JDK 17+**：Java 开发环境
- **Maven 3.9+**：项目已提供 `mvnw` / `mvnw.cmd`，可免安装 Maven
- **Node.js 20+ / npm 10+**：前端开发环境
- **MySQL 8.x**：数据库
- **Redis 6+**：缓存服务
- **RocketMQ 5.x**：消息队列服务

### 可选服务（用于完整功能）

- **阿里云 OSS**：对象存储，用于图片上传
- **阿里云邮件推送（DirectMail）**：发送邮箱验证码
- **高德地图开放平台**：Web 服务 Key，用于地址三级联动

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

### 6. 访问应用

- **用户端**：`http://localhost:5173`
- **管理端**：`http://localhost:5173/admin`（需要管理员权限）
- **后端 API**：`http://localhost:8080/api`

## 关键配置说明

### 后端配置（application-dev.yaml）

| 配置项 | 作用 | 说明 |
| ------ | ---- | ---- |
| `laolao.datasource.*` | MySQL 数据库连接 | 包括 host、port、database、username、password |
| `laolao.redis.*` | Redis 连接配置 | 包括 host、port、database，用于缓存用户态与热点数据 |
| `laolao.jwt.user-secret-key` | JWT 签名秘钥 | 自行更换并妥善保管，用于生成和验证用户 Token |
| `laolao.jwt.user-ttl` | JWT 过期时间 | 默认 604800000 毫秒（7天） |
| `rocketmq.name-server` | RocketMQ 服务器地址 | 默认 127.0.0.1:9876，用于订单超时消息队列 |
| `rocketmq.producer.*` | RocketMQ 生产者配置 | 包括 group、send-message-timeout、retry-times-when-send-failed |
| `aliyun.oss.*` | 阿里云 OSS 配置 | 包括 endpoint、bucket-name，用于用户上传图片 |
| `aliyun.access-key.*` | 阿里云 AccessKey | 包括 accessKey-id、accessKey-secret，用于 OSS 和邮件推送 |
| `laolao.amap.key` | 高德地图 Web 服务 Key | 用于地址解析和三级联动 |

### 前端配置（vite.config.ts）

- **代理配置**：`/api` 请求自动代理到 `http://localhost:8080`，`/ws` WebSocket 连接自动代理到 `ws://localhost:8080`
- **路径别名**：`@` 指向 `src` 目录

> **提示**：开发阶段如暂不接入云服务，可将 OSS / 邮件逻辑替换为本地实现或 Mock。生产环境请务必修改默认密钥和配置。

## 常用命令

### 后端命令

| 场景 | 命令 | 说明 |
| ---- | ---- | ---- |
| 启动开发服务器 | `./mvnw spring-boot:run` | Windows: `mvnw.cmd spring-boot:run` |
| 运行单元测试 | `./mvnw test` | 执行所有测试用例 |
| 打包项目 | `./mvnw clean package -DskipTests` | 跳过测试打包，生成 jar 文件 |
| 清理构建 | `./mvnw clean` | 清理 target 目录 |

### 前端命令

| 场景 | 命令 | 说明 |
| ---- | ---- | ---- |
| 启动开发服务器 | `npm run dev` | 启动 Vite 开发服务器 |
| 构建生产版本 | `npm run build` | 构建生产环境代码（包含类型检查） |
| 预览生产构建 | `npm run preview` | 预览构建后的应用 |
| 类型检查 | `vue-tsc -b` | 执行 TypeScript 类型检查 |

> 注意：所有命令需要在对应的项目目录下执行。

## 核心特性

### 定时任务

- **订单超时处理**：每 5 分钟检查一次，自动关闭超过 15 分钟未支付的订单，并释放已使用的优惠券。

### RocketMQ 消息队列

- **异步订单处理**：使用 RocketMQ 实现订单超时的异步消息处理，提高系统响应性能。
- **生产者消费者模式**：订单超时任务作为生产者发送延迟消息，OrderConsumer 作为消费者处理订单取消逻辑。
- **消息可靠性**：支持消息重试机制，确保订单超时处理不遗漏。

### 布隆过滤器

- **商品查询优化**：使用 Redisson 实现的分布式布隆过滤器，缓存商品ID，提升查询性能。
- **防缓存穿透**：在查询商品前先检查布隆过滤器，避免无效的数据库查询。
- **自动初始化**：应用启动时自动加载所有商品ID到布隆过滤器，预热缓存。

### WebSocket 实时通信

- **在线用户统计**：实时统计并广播在线用户数量变化。
- **实时通知推送**：支持向指定用户或所有管理员推送消息（如新订单通知）。
- **心跳机制**：30 秒心跳检测，自动重连，保证连接稳定性。
- **连接管理**：区分普通用户和管理员会话，支持多客户端连接。

### 拦截器机制

- **登录拦截器**：验证用户 JWT Token，保护需要登录的接口。
- **管理员拦截器**：验证管理员权限，保护管理端接口。
- **SQL 日志拦截器**：记录 SQL 执行日志，便于调试和性能分析。
- **自动填充拦截器**：自动填充创建时间、更新时间等字段。

### 数据缓存

- **Redis 缓存**：缓存热点数据，提升查询性能。
- **用户状态缓存**：缓存用户登录状态和基本信息。

### 数据可视化

- **Dashboard 仪表盘**：管理端提供用户、商城、论坛三个维度的数据统计仪表盘。
- **ECharts 图表**：使用 ECharts 实现趋势图、柱状图、排名图等多种数据可视化。
- **实时数据更新**：Dashboard 数据支持实时刷新，结合 WebSocket 实现动态更新。

### 文件管理

- **阿里云 OSS 集成**：支持图片上传到阿里云 OSS。
- **文件清理任务**：定时清理无效文件，节省存储空间。

## 开发计划

- [x] 基础商城功能（商品、购物车、订单、优惠券）
- [x] 论坛功能（帖子、评论、点赞）
- [x] 用户系统（登录、注册、个人中心）
- [x] 管理端（用户管理、商品管理、论坛管理）
- [x] 定时任务（订单超时处理）
- [x] WebSocket 实时通信（在线用户统计、实时通知）
- [x] Dashboard 数据可视化（用户、商城、论坛统计）
- [ ] 订单支付通道对接（支付宝 / 微信）
- [ ] 实时聊天 / 评论 @ 通知
- [ ] 产品推荐算法（基于用户行为的配置推荐）
- [ ] 商品库存预警
- [ ] 更多数据统计与分析功能

## 项目结构说明

### 后端架构

- **Controller 层**：处理 HTTP 请求，参数校验，调用 Service
- **Service 层**：业务逻辑实现，事务管理
- **Mapper 层**：数据访问，MyBatis 映射
- **POJO 层**：实体类（Entity）、数据传输对象（DTO）、视图对象（VO）
- **Common 层**：通用工具、配置、常量、异常处理

### 前端架构

- **组件化开发**：按功能模块划分组件（admin、front、common、ui）
- **状态管理**：使用 Pinia 管理全局状态
- **路由管理**：Vue Router 实现页面路由和权限控制
- **UI 组件库**：基于 shadcn-vue（reka-ui）构建的组件系统

## 注意事项

1. **数据库初始化**：首次运行前务必执行 `laolao_pc_builds.sql` 初始化数据库。
2. **配置文件**：开发环境配置在 `application-dev.yaml`，生产环境请使用 `application-prod.yaml`。
3. **密钥安全**：生产环境请务必修改 JWT 密钥和阿里云 AccessKey，不要提交到版本控制。
4. **跨域配置**：前后端分离开发时，已配置跨域支持。
5. **静态资源**：`laolaoPC/` 目录包含示例图片，可根据需要替换。

## 贡献指南

欢迎提交 Issue 和 Pull Request，一起把装机体验做到极致 ✨

## 许可证

本项目采用 MIT 许可证。



