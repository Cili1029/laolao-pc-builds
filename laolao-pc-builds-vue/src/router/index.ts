import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router';
import frontLayout from '@/components/front/FrontLayout.vue';
import Home from '@/components/front/Home.vue'
import Shop from '@/components/front/shop/Shop.vue';
import Order from '@/components/front/shop/Order.vue';
import MyOrders from '@/components/front/shop/MyOrders.vue';
import Forum from '@/components/front/forum/Forum.vue';
import Post from '@/components/front/forum/Post.vue';
import PostSimple from '@/components/front/forum/PostSimple.vue';
import Sign from '@/components/common/Sign.vue';
import FrontUser from '@/components/front/user/User.vue';
import Product from '@/components/front/shop/Product.vue';
import PaySuccess from '@/components/front/common/paySuccess.vue';
import Error from '@/components/front/common/Error.vue';

import AdminLayout from '@/components/admin/AdminLayout.vue';
import AdminUser from '@/components/admin/user/User.vue';
import ShopCategory from '@/components/admin/shop/ShopCategory.vue';
import ForumCategory from '@/components/admin/forum/ForumCategory.vue';
import Component from '@/components/admin/shop/Component.vue';
import Bundle from '@/components/admin/shop/Bundle.vue';
import ShopCoupon from '@/components/admin/shop/ShopCoupon.vue';
import AdminPost from '@/components/admin/forum/Post.vue';
import AdminOrder from '@/components/admin/shop/Order.vue';
import UserDashboard from '@/components/admin/dashboard/UserDashboard.vue';
import ShopDashboard from '@/components/admin/dashboard/ShopDashboard.vue';
import ForumDashboard from '@/components/admin/dashboard/ForumDashboard.vue';

import Test from '@/components/common/Test.vue';



const routes: Array<RouteRecordRaw> = [
  // 1. 用户端路由组
  {
    path: '/',
    component: frontLayout,
    redirect: '/home',
    children: [
      {
        path: 'test',
        name: 'Test',
        component: Test,
        meta: { showFooter: false }
      },
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
      },
      {
        path: 'pay-success',
        name: 'PaySuccess',
        component: PaySuccess,
        meta: { showFooter: false }
      },
      {
        path: 'error',
        name: 'Error',
        component: Error,
        meta: { showFooter: false }
      },
      {
        path: ':pathMatch(.*)*',
        name: 'NotFound',
        component: Error
      }
    ]
  },

  {
    path: '/admin',
    component: AdminLayout,
    redirect: '/admin/dashboard/user',
    children: [
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
      {
        path: 'shop/coupon',
        name: 'ShopCoupon',
        component: ShopCoupon,
      },
      {
        path: 'forum/post',
        name: 'AdminPost',
        component: AdminPost,
      },
      {
        path: 'user/order',
        name: 'AdminOrder',
        component: AdminOrder,
      },
      {
        path: 'dashboard/user',
        name: 'UserStatistics',
        component: UserDashboard,
      },
      {
        path: 'dashboard/shop',
        name: 'ShopDashboard',
        component: ShopDashboard,
      },
      {
        path: 'dashboard/forum',
        name: 'ForumDashboard',
        component: ForumDashboard,
      },
    ]
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes
})

export default router