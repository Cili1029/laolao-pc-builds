// src/router/index.ts
import { createRouter, createWebHistory} from 'vue-router'
import Hello from '@/components/HelloWorld.vue'
import Login from '../components/LoginOrRegister.vue'

// 使用 RouteRecordRaw 类型定义路由
const routes = [
  {
    path: '/hello',
    name: 'Hello',
    component: Hello
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes
})

export default router