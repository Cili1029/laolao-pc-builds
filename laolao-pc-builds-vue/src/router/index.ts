// src/router/index.ts
import { createRouter, createWebHistory} from 'vue-router'
import Hello from '@/components/HelloWorld.vue'
import Home from '../components/Home.vue'
import Buy from '../components/Buy.vue';
import Order from '@/components/Order.vue';
import MyOrders from '@/components/MyOrders.vue';

// 使用 RouteRecordRaw 类型定义路由
const routes = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
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
  },
  {
    path: '/order/:number',
    name: 'Order',
    component: Order
  },
  {
    path: '/my-orders',
    name: 'MyOrders',
    component: MyOrders
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes
})

export default router