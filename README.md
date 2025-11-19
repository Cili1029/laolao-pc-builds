# 劳劳的装机工坊
一个基于 Spring Boot 和 Vue 3 的 PC 硬件商城与论坛平台，提供硬件产品销售、订单管理、社区论坛等功能。

## 📋 项目简介

劳劳的装机工坊是一个全栈 Web 应用，包含以下核心功能：

- **商城系统**：PC 硬件产品浏览、购买、订单管理
- **论坛社区**：用户交流、帖子发布、评论互动
- **用户系统**：注册登录、个人中心、订单查询
- **优惠券中心**：领券、个人券包、订单抵扣、退券
- **产品分类**：显卡、CPU、主板、内存、固态硬盘等硬件分类
- **社区运营**：定时任务、分类图标、帖子图片上传

## 🛠️ 技术栈

### 后端技术

- **框架**：Spring Boot 3.5.6
- **语言**：Java 17
- **ORM**：MyBatis 3.0.5
- **数据库**：MySQL 8.0+
- **缓存**：Redis
- **连接池**：Druid 1.2.27
- **认证**：JWT (jjwt 0.13.0)
- **对象映射**：MapStruct 1.6.3
- **分页插件**：PageHelper 2.1.1
- **云服务**：阿里云 OSS（对象存储）、阿里云 DM（邮件服务）
- **其他**：Lombok、Spring AOP、WebFlux

### 前端技术

- **框架**：Vue 3.5.22 + TypeScript 5.3
- **构建工具**：Vite 7.7
- **状态管理**：Pinia 3.0.3
- **路由**：Vue Router 4.5.1
- **UI 组件库**：Reka UI 2.6.0（基于 Radix UI）
- **样式**：TailwindCSS 4.1.14
- **HTTP 客户端**：Axios 1.12.2
- **工具库**：
  - VueUse 13.9.0
  - dayjs 1.11.18（日期处理）
  - vue-sonner 2.0.9（消息提示）
  - vue3-cookies 1.0.6（Cookie 管理）

## 📁 项目结构

```
laolao-pc-builds/
├── laolao-pc-builds/              # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/laolao/
│   │   │   │   ├── controller/    # 控制器层
│   │   │   │   │   ├── admin/     # 管理员控制器
│   │   │   │   │   ├── common/    # 公共控制器
│   │   │   │   │   └── user/      # 用户控制器
│   │   │   │   │       ├── forum/ # 论坛相关
│   │   │   │   │       ├── shop/  # 商城相关
│   │   │   │   │       └── user/  # 用户相关
│   │   │   │   ├── service/       # 服务层
│   │   │   │   ├── mapper/        # MyBatis Mapper 接口
│   │   │   │   ├── pojo/          # 实体类
│   │   │   │   ├── common/        # 公共类、工具类
│   │   │   │   ├── converter/     # 对象转换器
│   │   │   │   ├── handler/       # 处理器、拦截器
│   │   │   │   └── webconfig/     # Web 配置
│   │   │   └── resources/
│   │   │       ├── mapper/        # MyBatis XML 映射文件
│   │   │       └── application*.yaml  # 配置文件
│   │   └── test/                  # 测试代码
│   └── pom.xml                    # Maven 配置文件
│
├── laolao-pc-builds-vue/          # 前端项目
│   ├── src/
│   │   ├── components/            # Vue 组件
│   │   │   ├── common/            # 公共组件
│   │   │   ├── forum/             # 论坛组件
│   │   │   ├── shop/              # 商城组件（Buy、Order、CouponDialog）
│   │   │   ├── user/              # 用户组件
│   │   │   └── ui/                # UI 组件库
│   │   ├── stores/                # Pinia 状态管理
│   │   ├── router/                # 路由配置
│   │   ├── utils/                 # 工具函数
│   │   ├── assets/                # 静态资源
│   │   └── main.ts                # 入口文件
│   ├── package.json
│   └── vite.config.ts
│
├── image/                         # 产品图片资源
│   ├── 1/                         # 显卡图片
│   ├── 2/                         # 显卡图片（AMD）
│   ├── 3/                         # CPU 图片（Intel）
│   ├── 4/                         # CPU 图片（AMD）
│   ├── 5/                         # 主板图片
│   ├── 6/                         # 内存图片
│   └── 7/                         # 固态硬盘图片
│
└── laolao_pc_builds.sql          # 数据库初始化脚本
```

## ✨ 功能特性

### 商城功能
- 产品浏览与搜索
- 产品分类管理（显卡、CPU、主板、内存、SSD）
- 购物车功能
- 订单创建与管理
- 收货地址管理
- 订单详情查看

### 论坛功能
- 分类导航带图标，点击后立即切换并高亮当前分类
- 帖子发布支持标题、正文与最多 5 张图片（上传至阿里云 OSS）
- 帖子详情查看、评论与回复、点赞互动
- Pinia 缓存分类与帖子数据，减少重复请求

### 用户功能
- 用户注册与登录（JWT 认证）
- 个人中心
- 订单查询
- 个人信息管理

### 优惠券功能
- 首页、用户菜单与下单页均可打开 `CouponDialog`（三种模式）
- 「更多优惠券」展示已启用券并支持立即领取
- 「我的优惠券」列出个人券包、跳转下单页或直接应用
- 结算页可选择/取消优惠券，实时回写抵扣金额与券 ID
- dayjs 本地化日期、Pinia 缓存券状态；上传 OSS 的商品图可辅助营销

### 管理功能
- 管理员后台
- 产品管理
- 订单管理
- 用户管理
- 定时任务用于日常运营（基于 Spring Scheduling，5 分钟清理超时订单）

## 🧧 优惠券流程

**前端**
- 核心组件 `src/components/shop/CouponDialog.vue` 通过 `type` 切换「更多优惠券 / 我的优惠券 / 下单用券」三种模式
- `App.vue` 和 `Order.vue` 统一以 `v-model:isOpen` 控制弹窗，订单页会同步 `discountAmount` 与 `userCouponId`
- dayjs 负责优惠券日期格式化，本地路由可从券包直接跳转到下单页

**后端**
- `ShopCouponController` 暴露优惠券领取与查询接口，底层由 `ShopCouponMapper` + `MapStruct` 组装 VO
- `OrderController` 的 `/coupon` 与 `/coupon/cancel` 负责把券状态写回 `user_coupon`，并更新 `shop_order` 的 `discount_amount` 与 `user_coupon_id`
- 定时任务 `OrderTask` 会自动关闭 15 分钟未支付订单，避免优惠券长期占用

**数据库**
- `shop_coupon`：券模板（金额、门槛、库存、状态、有效期）
- `user_coupon`：用户券包（关联用户与订单、状态、领取/使用时间）
- `shop_order`：字段 `discount_amount`、`user_coupon_id` 记录抵扣结果

**API 概览**

```
GET    /api/user/shop/coupon/shop          # 查询可领取优惠券
GET    /api/user/shop/coupon/user          # 查询我的优惠券
POST   /api/user/shop/coupon/get           # 领取指定优惠券
PATCH  /api/user/shop/order/coupon         # 在订单中使用优惠券
PATCH  /api/user/shop/order/coupon/cancel  # 取消已选择的优惠券
```

## 🚀 快速开始

### 环境要求

- **JDK**：17 或更高版本
- **Maven**：3.6+ 或使用项目自带的 Maven Wrapper
- **Node.js**：18+ 
- **npm/yarn/pnpm**：最新版本
- **MySQL**：8.0+
- **Redis**：6.0+

### 后端启动

1. **克隆项目**
```bash
git clone <repository-url>
cd laolao-pc-builds
```

2. **创建数据库**
```bash
# 导入数据库脚本
mysql -u root -p < laolao_pc_builds.sql
```

3. **配置数据库和 Redis**
   
   编辑 `laolao-pc-builds/src/main/resources/application-dev.yaml`（或创建对应环境的配置文件）：
```yaml
laolao:
  datasource:
    host: localhost
    port: 3306
    database: laolao_pc_builds
    username: your_username
    password: your_password
  redis:
    host: localhost
    port: 6379
    database: 1
  aliyun:
    access-key:
      accessKey-id: your_access_key_id
      accessKey-secret: your_access_key_secret
    oss:
      endpoint: your_oss_endpoint
      bucket-name: your_bucket_name
  amap:
    key: your_amap_key
```

4. **启动后端服务**
```bash
cd laolao-pc-builds
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

后端服务将在 `http://localhost:8080` 启动

### 前端启动

1. **进入前端目录**
```bash
cd laolao-pc-builds-vue
```

2. **安装依赖**
```bash
npm install
# 或
yarn install
# 或
pnpm install
```

3. **配置 API 地址**
   
   编辑 `src/utils/myAxios.ts`，配置后端 API 基础地址（如果与默认不同）

4. **启动开发服务器**
```bash
npm run dev
# 或
yarn dev
# 或
pnpm dev
```

前端服务通常在 `http://localhost:5173` 启动（Vite 默认端口）

### 构建生产版本

**后端构建**
```bash
cd laolao-pc-builds
mvnw.cmd clean package
# 生成的 jar 包在 target/ 目录
```

**前端构建**
```bash
cd laolao-pc-builds-vue
npm run build
# 构建产物在 dist/ 目录
```

## ⚙️ 配置说明

### 后端配置

主要配置文件位于 `laolao-pc-builds/src/main/resources/`：

- `application.yaml`：主配置文件
- `application-dev.yaml`：开发环境配置
- `application-prod.yaml`：生产环境配置

关键配置项：
- **数据库连接**：数据源配置
- **Redis 配置**：缓存服务器配置
- **JWT 配置**：认证令牌密钥和过期时间
- **阿里云 OSS**：对象存储配置（用于图片上传）
- **高德地图 API**：用于地址相关功能

### 前端配置

- `vite.config.ts`：Vite 构建配置
- `tsconfig.json`：TypeScript 配置
- `src/utils/myAxios.ts`：Axios 请求配置

## 📝 开发规范

### 代码风格
- 后端遵循 Java 编码规范，使用 Lombok 简化代码
- 前端使用 TypeScript，遵循 Vue 3 Composition API 规范
- 使用 ESLint/Prettier 保持代码风格统一（如果已配置）

### 分支管理
- `main`：主分支，生产环境代码
- `dev`：开发分支
- `feature/*`：功能分支

### 提交规范
建议使用约定式提交（Conventional Commits）：
- `feat:` 新功能
- `fix:` 修复问题
- `docs:` 文档更新
- `style:` 代码格式
- `refactor:` 重构
- `test:` 测试相关
- `chore:` 构建/工具相关

## 🔧 常见问题

### 后端启动失败

1. **端口被占用**：修改 `application.yaml` 中的 `server.port`
2. **数据库连接失败**：检查数据库配置和 MySQL 服务状态
3. **Redis 连接失败**：检查 Redis 服务是否启动
4. **依赖下载失败**：配置 Maven 镜像源

### 前端启动失败

1. **依赖安装失败**：清除缓存后重试
   ```bash
   npm cache clean --force
   rm -rf node_modules package-lock.json
   npm install
   ```
2. **端口被占用**：在 `vite.config.ts` 中配置其他端口
3. **API 请求失败**：检查后端服务是否启动，API 地址是否正确

## 📦 部署说明

### 后端部署

1. 构建 jar 包：
```bash
mvnw.cmd clean package -DskipTests
```

2. 运行 jar 包：
```bash
java -jar target/laolao-pc-builds-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### 前端部署

1. 构建生产版本：
```bash
npm run build
```

2. 将 `dist/` 目录部署到 Nginx 或其他 Web 服务器

3. Nginx 配置示例：
```nginx
server {
    listen 80;
    server_name your-domain.com;
    root /path/to/dist;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    location /api {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

## 📄 许可证

本项目仅供学习交流使用。

## 👥 贡献者

欢迎提交 Issue 和 Pull Request！

## 📞 联系方式

如有问题，请提交 Issue 或联系项目维护者。

---

**注意**：本项目中的配置文件（如 `application-dev.yaml`）包含敏感信息，已添加到 `.gitignore`。部署时请创建对应的配置文件并填入正确的配置信息。




