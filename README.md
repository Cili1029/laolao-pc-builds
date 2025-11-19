# 劳劳的装机工坊（LaoLao PC Builds）

以「装机 + 社区」为核心的一站式 PC 交流/购置平台。该仓库同时包含 Spring Boot 3 后端以及 Vue 3 + Vite 前端，支持从硬件选型、下单支付到玩家社区互动的完整闭环。

## 项目亮点
- **装机商城**：商品/组件/整机三级维度，支持检索、规格切换、优惠券、购物车、地址与订单全链路管理。
- **玩家论坛**：帖子分类、搜索、点赞、评论/回复，热帖榜单及富文本发布能力。
- **用户体系**：邮箱验证码注册登录、JWT + Cookie 会话、个人主页、资料编辑、喜欢的帖子等。
- **系统能力**：Redis 缓存、MyBatis + PageHelper 分页、MapStruct DTO/VO 转换、阿里云邮件 & OSS、定时任务自动关闭超时订单、全局拦截器统一注入 `/api` 前缀与鉴权。

## 技术栈
- **后端**：Spring Boot 3.5、Spring MVC/WebFlux、MyBatis、PageHelper、Redis、MySQL、MapStruct、Lombok、Aliyun SDK、JJWT。
- **前端**：Vue 3 + TypeScript、Vite 7、Pinia、Vue Router 4、Tailwind CSS（v4）、axios、vue-sonner、Lucide Icons。
- **基础设施**：MySQL 8+、Redis 6+/7+、阿里云 DirectMail & OSS、高德开放平台（地址解析/地图）。

## 仓库结构
```
.
├── laolao-pc-builds/               # Spring Boot 服务
│   ├── src/main/java/com/laolao
│   │   ├── controller/             # user/shop/forum 等 REST 模块
│   │   ├── service/、mapper/       # 业务与 MyBatis
│   │   ├── converter/              # MapStruct DTO/VO 映射
│   │   ├── common/                 # 全局配置、异常、工具、JWT
│   │   ├── Interceptor/SignInInterceptor.java
│   │   ├── task/OrderTask.java     # 15 分钟订单超时关闭
│   │   └── webconfig/WebConfig.java# /api 前缀 + 登录拦截
│   └── src/main/resources
│       ├── application.yaml / application-*.yaml
│       └── mapper/**/*.xml
├── laolao-pc-builds-vue/           # Vue3 前端
│   ├── src/components/{shop,forum,user,common}
│   ├── src/stores/*.ts             # Pinia（产品/帖子/用户）
│   ├── src/router/index.ts         # 首页/购机/论坛/订单/用户
│   ├── src/utils/myAxios.ts        # axios 实例与拦截器
│   └── vite.config.ts              # /api -> :8080 代理
├── image/                          # 项目截图/素材
└── laolao_pc_builds.sql            # 数据库初始化脚本
```

## 功能模块速览
- **商城模块（`controller/user/shop`）**：商品、分类、组件规格、购物车、收货地址、优惠券、订单（含状态流转/取消原因）。`OrderTask` 每 5 分钟扫描并关闭 15 分钟未支付订单。
- **论坛模块（`controller/user/forum`）**：帖子 CRUD、评论/回复、点赞、热帖列表与分类检索。
- **用户模块（`controller/user/user`）**：邮箱验证码（阿里云 DirectMail）、用户名/邮箱登录、注册自动登录、JWT 签发、个人资料与喜欢的帖子。
- **公共能力**：`common.properties` 统一承载阿里云/OSS/高德配置，`common.handler` 处理全局异常，`SignInInterceptor` 对除白名单外的 `/api/**` 接口做登录校验。

## 快速开始
### 1. 前置准备
- JDK 17、Maven 3.9+（或使用 `mvnw`）、Node.js 18+/pnpm 或 npm、MySQL 8+、Redis 6+/7+。
- 可选：阿里云邮件/OSS、Redis、本地 SMTP、地图服务等账号。

### 2. 初始化数据库
```bash
mysql -u root -p < laolao_pc_builds.sql
```
如需自定义库名，请同步修改 `application-*.yaml` 中的 `laolao.datasource.database`。

### 3. 配置后端
1. 编辑 `laolao-pc-builds/src/main/resources/application-dev.yaml`（或新建 `application-prod.yaml`）：
   - `laolao.datasource.*`：数据库连接。
   - `laolao.redis.*`：Redis 缓存。
   - `laolao.aliyun.*`：DirectMail、OSS，生产环境请改为安全的密钥管理方式。
   - `laolao.amap.key`：高德开放平台 key。
   - `laolao.jwt.*`：JWT 密钥、TTL（默认 7 天）。
2. `application.yaml` 中 `spring.profiles.active` 默认 `dev`，部署时切到 `prod`。

### 4. 启动后端服务
```bash
cd laolao-pc-builds
mvn spring-boot:run
# 或
mvn clean package && java -jar target/laolao-pc-builds-0.0.1-SNAPSHOT.jar
```
后端默认监听 `http://localhost:8080`，所有接口统一挂载在 `/api/**`。

### 5. 启动前端客户端
```bash
cd laolao-pc-builds-vue
npm install        # 或 pnpm install / yarn
npm run dev        # 默认 5173 端口
```
- `vite.config.ts` 已把 `/api` 代理到 `http://localhost:8080`，开发环境无需额外修改。
- 生产部署请在 `src/utils/myAxios.ts` 中为 `baseURL` 配置真实网关地址，或使用 Vite 环境变量（`import.meta.env.VITE_API_BASE_URL`）封装二次构建。
- 构建生产资源：`npm run build && npm run preview`。

## 常用命令速查
| 场景 | 命令 |
| --- | --- |
| 后端单元测试 | `mvn test` |
| 格式化 + 编译 | `mvn clean verify` |
| 前端代码检查 | `npx vue-tsc --noEmit` |
| 前端打包 | `npm run build` |

## 开发约定
- **接口规范**：所有 `@RestController` 通过 `WebConfig` 自动加上 `/api` 前缀；统一返回 `com.laolao.common.result.Result`。
- **鉴权**：`SignInInterceptor` 针对除邮箱验证码、注册、公开数据外的接口强制校验 JWT，401 时前端 axios 拦截器会跳转 `/sign`。
- **缓存**：Redis 用于验证码、热点数据、会话信息等，可依据场景设置 TTL。
- **对象转换**：DTO/VO 通过 MapStruct（`converter` 包）集中维护，减少样板代码。
- **分页**：结合 PageHelper + MyBatis XML Mapper，SQL 位于 `src/main/resources/mapper/**`.

## 常见问题
1. **前端无法访问接口？** 确认 `npm run dev` 控制台无跨域报错，必要时在 `myAxios.ts` 设置 `baseURL = import.meta.env.VITE_API_BASE_URL || '/api'`。
2. **定时任务未执行？** 确保 `@EnableScheduling` 已在启动类启用，并且服务未处于单线程阻塞状态。
3. **OSS/邮件凭证安全？** 切勿在公开仓库保留真实密钥，可通过环境变量、KMS 或配置中心注入。
4. **数据库脚本更新？** 请同步维护 `laolao_pc_builds.sql`，必要时附带 `migrate` 说明。

## 下一步
- 丰富 `controller/admin` 管控端能力（库存、帖子审核等）。
- 引入自动化测试 & API 文档（Swagger / SpringDoc）。
- 完善 CICD（单元测试、lint、Docker 镜像等）。

欢迎提交 Issue/PR，让「劳劳的装机工坊」更易用 ❤️

