// src/router/index.ts
import { createRouter, createWebHistory } from 'vue-router'
import Hello from '@/components/common/HelloWorld.vue'
import Home from '../components/common/Home.vue'
import Shop from '../components/shop/Shop.vue';
import Order from '@/components/shop/Order.vue';
import MyOrders from '@/components/shop/MyOrders.vue';
import Forum from '@/components/forum/Forum.vue';
import Post from '@/components/forum/Post.vue';
import PostSimple from '@/components/forum/PostSimple.vue';
import Sign from '@/components/user/Sign.vue';
import User from '@/components/user/User.vue';
import Product from '@/components/shop/product.vue';


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
    path: '/user/:id',
    name: 'User',
    component: User,
    meta: { showFooter: false }
  },
  {
    path: '/sign',
    name: 'Sign',
    component: Sign,
    meta: { showFooter: true }
  },
  {
    path: '/buy/:id',
    name: 'Shop',
    component: Shop,
    meta: { showFooter: false }
  },
  {
    path: '/buy/product/:productType/:id',
    name: 'Product',
    component: Product,
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
    path: '/forum/:categoryId',
    component: Forum,
    meta: { showFooter: false },
    children: [
      {
        path: '',
        component: PostSimple
      },
      {
        path: 'post/:postId', // 帖子详情，作为子路由
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