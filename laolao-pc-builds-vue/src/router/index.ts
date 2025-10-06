// src/router/index.ts
import { createRouter, createWebHistory} from 'vue-router'
import Hello from '@/components/HelloWorld.vue'
import Home from '../components/Home.vue'
import Buy from '../components/Buy.vue';

// 使用 RouteRecordRaw 类型定义路由
const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/buy',
    name: 'buy',
    component: Buy
  },
  {
    path: '/hello',
    name: 'Hello',
    component: Hello
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes
})

export default router