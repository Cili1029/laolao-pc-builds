// src/router/index.ts
import { createRouter, createWebHistory } from 'vue-router'
import Hello from '@/components/HelloWorld.vue'
import Home from '../components/Home.vue'
import Buy from '../components/Buy.vue';
import Order from '@/components/Order.vue';
import MyOrders from '@/components/MyOrders.vue';
import Forum from '@/components/Forum.vue';
import Post from '@/components/Post.vue';
import PostSimple from '@/components/PostSimple.vue';
import Sign from '@/components/Sign.vue';


// 使用 RouteRecordRaw 类型定义路由
const routes = [
  {
    path: '/',
    redirect: '/home',
    meta: { showFooter: true }
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    meta: { showFooter: true }
  },
  {
    path: '/sign',
    name: 'Sign',
    component: Sign,
    meta: { showFooter: true }
  },
  {
    path: '/buy',
    name: 'Buy',
    component: Buy,
    meta: { showFooter: false }
  },
  {
    path: '/hello',
    name: 'Hello',
    component: Hello,
    meta: { showFooter: false }
  },
  {
    path: '/order/:number',
    name: 'Order',
    component: Order,
    meta: { showFooter: false }
  },
  {
    path: '/my-orders',
    name: 'MyOrders',
    component: MyOrders,
    meta: { showFooter: false }
  },
  {
    path: '/forum',
    component: Forum,
    meta: { showFooter: false },
    children: [
      {
        path: '',
        component: PostSimple
      },
      {
        path: 'post/:id', // 帖子详情，作为子路由
        component: Post,
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes
})

export default router