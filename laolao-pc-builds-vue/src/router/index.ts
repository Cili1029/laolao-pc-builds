// src/router/index.ts
import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router';
import frontLayout from '@/components/front/common/FrontLayout.vue';
import Home from '@/components/front/common/Home.vue'
import Shop from '@/components/front/shop/Shop.vue';
import Order from '@/components/front/shop/Order.vue';
import MyOrders from '@/components/front/shop/MyOrders.vue';
import Forum from '@/components/front/forum/Forum.vue';
import Post from '@/components/front/forum/Post.vue';
import PostSimple from '@/components/front/forum/PostSimple.vue';
import Sign from '@/components/front/user/Sign.vue';
import FrontUser from '@/components/front/user/User.vue';
import Product from '@/components/front/shop/Product.vue';

import Test from '@/components/admin/test.vue';
import AdminLayout from '@/components/admin/AdminLayout.vue';
import AdminUser from '@/components/admin/user/User.vue';
import ShopCategory from '@/components/admin/shop/ShopCategory.vue';
import ForumCategory from '@/components/admin/forum/ForumCategory.vue';
import Component from '@/components/admin/shop/Component.vue';
import Bundle from '@/components/admin/shop/Bundle.vue';

const routes: Array<RouteRecordRaw> = [
  // 1. 用户端路由组
  {
    path: '/',
    component: frontLayout,
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'Home',
        component: Home,
        meta: { showFooter: true }
      },
      {
        path: 'user/:id',
        name: 'FrontUser',
        component: FrontUser,
        meta: { showFooter: false }
      },
      {
        path: 'sign',
        name: 'Sign',
        component: Sign,
        meta: { showFooter: false }
      },
      {
        path: 'buy/:id',
        name: 'Shop',
        component: Shop,
        meta: { showFooter: false }
      },
      {
        path: 'buy/product/:productType/:id',
        name: 'Product',
        component: Product,
        meta: { showFooter: false }
      },
      {
        path: 'order/:number',
        name: 'Order',
        component: Order,
        meta: { showFooter: false }
      },
      {
        path: 'my-orders',
        name: 'MyOrders',
        component: MyOrders,
        meta: { showFooter: false }
      },
      {
        path: 'forum/:categoryId',
        component: Forum,
        meta: { showFooter: false },
        children: [
          {
            path: '',
            component: PostSimple
          },
          {
            path: 'post/:postId',
            component: Post,
          }
        ]
      }
    ]
  },

  {
    path: '/admin',
    component: AdminLayout,
    redirect: '/admin/user',
    children: [
      {
        path: 'test',
        name: 'Test',
        component: Test,
      },
      {
        path: 'user',
        name: 'AdminUser',
        component: AdminUser,
      },
      {
        path: 'shop/category',
        name: 'ShopCategory',
        component: ShopCategory,
      },
      {
        path: 'forum/category',
        name: 'ForumCategory',
        component: ForumCategory,
      },
      {
        path: 'shop/component',
        name: 'Component',
        component: Component,
      },
      {
        path: 'shop/bundle',
        name: 'Bundle',
        component: Bundle,
      },
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes
})

export default router