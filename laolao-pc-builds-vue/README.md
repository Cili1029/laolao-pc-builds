# LaoLao PC Builds · 前端

Vue 3 + TypeScript + Vite 实现的装机商城与社区前端，包含用户端、管理端（/admin）、Dashboard、WebSocket 实时通知等模块。

## 运行与构建

```bash
# 安装依赖
npm install

# 开发调试（默认端口 5173）
npm run dev

# 生产构建（含类型检查）
npm run build

# 预览构建产物
npm run preview
```

Vite 代理已将 `/api`、`/ws` 指向后端 `http://localhost:8080`，本地联调零配置。

## 目录速览

- `src/main.ts`：应用入口
- `src/router/`：路由定义（用户端 / 管理端）
- `src/stores/`：Pinia 状态（用户、帖子、分类、通用、WebSocket）
- `src/components/`：按场景拆分的页面组件（admin/front/common/ui）
- `src/utils/myAxios.ts`：Axios 封装与统一拦截
- `src/style.css`：全局样式，内置 Tailwind v4 配置

## 开发约定

- 接口均走 `/api` 前缀，请保持与后端一致。
- 管理端路由在 `/admin` 下，访问需具备管理员权限。
- WebSocket 连接使用 `/ws`，配合 `websocketStore` 管理心跳与重连。
- 新增接口时，建议在 `stores` 中集中管理状态与请求。

更多后端配置、数据库初始化等信息见仓库根目录 `README.md`。
