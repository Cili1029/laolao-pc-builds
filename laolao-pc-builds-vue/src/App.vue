<template>
  <div class="min-h-screen flex flex-col h-screen font-sans bg-slate-50">
    <!-- 固定导航条 -->
    <!-- 美化：增强了毛玻璃效果 (backdrop-blur-xl)，加了细腻的边框，去除了生硬的深阴影 -->
    <div class="sticky top-0 z-50 bg-white/80 backdrop-blur-xl border-b border-white/40 shadow-sm supports-[backdrop-filter]:bg-white/60">
      <div class="flex justify-between items-center px-4 sm:px-6 lg:px-40 h-16">
        <!-- Logo 区域 -->
        <div class="flex items-center space-x-2 group cursor-pointer" @click="goHome">
          <div class="relative overflow-hidden rounded-lg shadow-sm border border-gray-100 group-hover:shadow-md transition-all duration-300">
             <img :src="logo" class="w-9 h-9 object-cover transform group-hover:scale-110 transition-transform duration-500" />
          </div>
          <span class="text-xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-gray-800 to-gray-600">
            劳劳的装机工坊
          </span>
        </div>
        
        <!-- 导航链接 -->
        <div class="flex space-x-4 md:space-x-6 items-center">
          <RouterLink to="/hello" class="text-sm font-medium text-gray-600 hover:text-orange-600 hover:bg-orange-50 px-3 py-1.5 rounded-full transition-all">
            功能测试
          </RouterLink>
          <span @click="showCouponDialog(1)" class="cursor-pointer text-sm font-medium text-gray-600 hover:text-orange-600 hover:bg-orange-50 px-3 py-1.5 rounded-full transition-all flex items-center gap-1">
            <span class="icon-[charm--gift]"></span> 更多优惠券
          </span>

          <!-- 购物车 -->
          <Sheet>
            <SheetTrigger as-child class="text-gray-600 hover:text-orange-600 transition-colors cursor-pointer" @click="showCart()">
              <div class="relative p-2 rounded-full hover:bg-gray-100">
                 <span class="icon-[mdi--cart-outline] text-2xl"></span>
                 <!-- 小红点 -->
                 <span v-if="products.length > 0" class="absolute top-1.5 right-1.5 h-2 w-2 bg-red-500 rounded-full ring-2 ring-white"></span>
                 <span class="sr-only">购物车</span>
              </div>
            </SheetTrigger>
            <SheetContent class="gap-0 flex flex-col h-full bg-white/95 backdrop-blur-sm">
              <SheetHeader class="px-6 py-4 border-b border-gray-100">
                <SheetTitle class="flex items-center gap-2 text-xl font-bold text-gray-900">
                    <span class="icon-[mdi--cart] text-orange-500"></span> 我的购物车
                </SheetTitle>
                <SheetDescription>
                  <p class="text-xs text-gray-400 hover:text-red-500 transition-colors cursor-pointer flex items-center gap-1 mt-1" 
                     :class="{ 'opacity-50 cursor-not-allowed': products.length === 0 }"
                    @click="products.length > 0 && clear()">
                    <span class="icon-[mdi--trash-can-outline]"></span> 清空购物车
                  </p>
                </SheetDescription>
              </SheetHeader>
              
              <!-- 购物车列表 -->
              <div v-if="products && products.length > 0" class="flex-1 overflow-y-auto scrollbar-edge py-4 px-2 space-y-3">
                <div class="w-full flex flex-col space-y-3">
                  <div v-for="product in products" :key="product.name"
                    class="group flex flex-col bg-white rounded-xl shadow-sm border border-gray-100 hover:shadow-md transition-all p-3 mx-1 relative">
                    <div class="flex gap-3">
                      <div class="h-16 w-16 shrink-0 bg-gray-50 rounded-lg overflow-hidden border border-gray-100">
                        <img :src="product.image" class="w-full h-full object-cover" />
                      </div>
                      <div class="flex-1 min-w-0">
                        <h3 class="font-bold text-gray-800 text-sm truncate">{{ product.name }}</h3>
                        <p class="text-xs text-gray-500 mt-0.5 bg-gray-50 inline-block px-1.5 py-0.5 rounded">{{ product.variantName }}</p>
                      </div>
                    </div>
                    <div class="flex justify-between items-end mt-3 border-t border-gray-50 pt-2">
                      <div>
                        <span class="text-base font-bold text-orange-600">￥{{ product.price }}</span>
                      </div>
                      <div class="flex items-center gap-2 bg-gray-50 rounded-lg p-1 border border-gray-100">
                        <Button variant="ghost" size="icon" class="h-6 w-6 rounded hover:bg-white hover:shadow-sm hover:text-orange-600"
                          @click="quantity(product, 0)">
                          <span class="icon-[mdi--minus] h-3 w-3"></span>
                        </Button>
                        <span class="text-sm font-bold w-4 text-center">{{ product.quantity }}</span>
                        <Button variant="ghost" size="icon" class="h-6 w-6 rounded hover:bg-white hover:shadow-sm hover:text-orange-600"
                          @click="quantity(product, 1)">
                          <span class="icon-[mdi--plus] h-3 w-3"></span>
                        </Button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              
              <!-- 空状态 -->
              <div v-else class="flex-1 flex flex-col justify-center items-center h-full text-gray-300 gap-4">
                <div class="bg-gray-50 p-6 rounded-full shadow-inner">
                    <span class="icon-[mdi--cart-off] text-6xl text-gray-300"></span>
                </div>
                <div class="text-center">
                    <p class="font-bold text-gray-400">购物车空空如也</p>
                    <p class="text-xs mt-1">快去添加点什么吧</p>
                </div>
              </div>

              <SheetFooter class="border-t border-gray-100 p-6 bg-gray-50 mt-auto">
                <div class="w-full space-y-4">
                    <div class="flex justify-between items-end">
                      <span class="text-sm text-gray-500">总计 (不含运费)</span>
                      <span class="text-xl font-extrabold text-orange-600">￥{{ totalPrice.toFixed(2) }}</span>
                    </div>
                    <SheetClose as-child>
                      <Button class="w-full h-11 text-base font-bold bg-gradient-to-r from-orange-500 to-red-500 hover:from-orange-600 hover:to-red-600 shadow-lg shadow-orange-500/20 rounded-xl transition-all active:scale-95" 
                        @click="order()" 
                        :disabled="!(products && products.length > 0)">
                        立即结算
                      </Button>
                    </SheetClose>
                </div>
              </SheetFooter>
            </SheetContent>
          </Sheet>

          <!-- 未登录 -->
          <div v-if="!userStore.signedIn">
            <RouterLink to="/sign">
              <Avatar class="cursor-pointer border-2 border-transparent hover:border-orange-200 transition-all">
                <AvatarFallback class="bg-gray-100 text-gray-500 font-bold text-xs">登录</AvatarFallback>
              </Avatar>
            </RouterLink>
          </div>

          <!-- 已登录 -->
          <div v-else>
            <DropdownMenu>
              <DropdownMenuTrigger as-child>
                <Avatar class="cursor-pointer border-2 border-transparent hover:border-orange-200 transition-all rounded-full shadow-sm h-9 w-9">
                  <AvatarImage :src="userStore.user.avatar" alt="用户头像" />
                  <AvatarFallback class="bg-orange-50 text-orange-600 font-bold">{{ userStore.user.name.substring(0, 1) }}</AvatarFallback>
                </Avatar>
              </DropdownMenuTrigger>
              <DropdownMenuContent class="w-56 p-2 rounded-xl shadow-xl border-gray-100" align="end">
                <DropdownMenuLabel class="flex flex-col px-2 py-1.5">
                  <span class="font-bold text-gray-800">{{ userStore.user.name }}</span>
                  <span class="text-xs text-gray-400 font-normal">欢迎回来，尊贵的用户</span>
                </DropdownMenuLabel>
                <DropdownMenuSeparator class="bg-gray-100" />
                <RouterLink :to="`/user/${userStore.user.id}`">
                  <DropdownMenuItem class="cursor-pointer rounded-lg hover:bg-orange-50 hover:text-orange-600 focus:bg-orange-50 focus:text-orange-600">
                    <span class="icon-[charm--person] mr-2"></span>
                    <span>个人信息</span>
                  </DropdownMenuItem>
                </RouterLink>
                <RouterLink to="/my-orders">
                  <DropdownMenuItem class="cursor-pointer rounded-lg hover:bg-orange-50 hover:text-orange-600 focus:bg-orange-50 focus:text-orange-600">
                    <span class="icon-[charm--clipboard] mr-2"></span>
                    <span>我的订单</span>
                  </DropdownMenuItem>
                </RouterLink>
                <DropdownMenuItem @click="showCouponDialog(2)" class="cursor-pointer rounded-lg hover:bg-orange-50 hover:text-orange-600 focus:bg-orange-50 focus:text-orange-600">
                  <span class="icon-[charm--gift] mr-2"></span>
                  <span>我的优惠券</span>
                </DropdownMenuItem>
                <DropdownMenuSeparator class="bg-gray-100" />
                <DropdownMenuItem @click="signOut" class="cursor-pointer rounded-lg text-red-500 hover:bg-red-50 hover:text-red-600 focus:bg-red-50 focus:text-red-600">
                  <span class="icon-[picon--exit] mr-2"></span>
                  <span>退出登录</span>
                </DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
          </div>
        </div>
      </div>
    </div>

    <div class="flex-grow overflow-hidden">
      <div class="overflow-y-auto scrollbar-edge h-full">
        <RouterView v-slot="{ Component }" class="px-4 sm:px-6 lg:px-40">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </RouterView>
        <div v-if="route.meta.showFooter" class="bg-gray-800 text-white mt-auto px-4 sm:px-6 lg:px-40 py-6">
          <div class="flex flex-col lg:flex-row justify-between items-center space-y-4 lg:space-y-0">
            <!-- 左侧logo和备案信息 -->
            <div class="flex flex-col items-center lg:items-start text-center lg:text-left">
              <div class="flex items-center space-x-2 mb-2">
                <img :src="logo" alt="Company Logo" class="w-6 h-6 rounded-md">
                <span class="text-lg font-bold">劳劳的网站</span>
              </div>
              <div class="text-gray-400 text-sm">
                <a href="https://beian.miit.gov.cn" target="_blank"
                  class="text-gray-400 hover:text-white transition-colors">
                  闽ICP备2025117246号-1
                </a>
              </div>
            </div>

            <!-- 右侧链接 -->
            <div class="flex flex-wrap justify-center gap-3 sm:gap-4 lg:gap-6">
              <a href="#" class="text-gray-300 hover:text-white transition-colors text-sm sm:text-base">假如再也见不到你</a>
              <a href="#" class="text-gray-300 hover:text-white transition-colors text-sm sm:text-base">祝你</a>
              <a href="#" class="text-gray-300 hover:text-white transition-colors text-sm sm:text-base">早安</a>
              <a href="#" class="text-gray-300 hover:text-white transition-colors text-sm sm:text-base">午安</a>
              <a href="#" class="text-gray-300 hover:text-white transition-colors text-sm sm:text-base">晚安</a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 全局弹窗 -->
    <CouponDialog :type="couponDialogType" v-model:isOpen="isOpenCouponDialog"></CouponDialog>
    <Toaster position="bottom-left" :toastOptions="{
       style: { background: 'white', border: '1px solid #e5e7eb', color: '#1f2937' },
       class: 'shadow-lg rounded-xl',
    }" />
  </div>
</template>

<script setup lang="ts">
// 脚本内容完全保持不变
  import { ref, onMounted, computed } from 'vue'
  import axios from './utils/myAxios'
  import { useRoute } from 'vue-router'
  const route = useRoute()
  import logo from '@/assets/logo.jpg'
  import { Toaster } from '@/components/ui/sonner'
  import CouponDialog from '@/components/shop/CouponDialog.vue';
  import { toast } from "vue-sonner"
  import 'vue-sonner/style.css'
  import { Sheet, SheetClose, SheetContent, SheetDescription, SheetFooter, SheetHeader, SheetTitle, SheetTrigger, } from "@/components/ui/sheet"
  import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
  import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from "@/components/ui/dropdown-menu"
  import { Button } from "@/components/ui/button"
  import { useRouter } from 'vue-router'
  const router = useRouter()
  import { useUserStore } from '@/stores/UserStore'
  const userStore = useUserStore()

  // 组件挂载时检查登录状态
  onMounted(() => {
    getProfile()
  })

  // 回主页
  const goHome = () => {
    router.push('/home');
  }

  interface User {
    id: number
    avatar: string
    username: string
    name: string
  }

  const user = ref<User>({
    id: 0,
    avatar: '',
    username: '',
    name: '',
  })

  const getProfile = async () => {
    try {
      const response = await axios.get('/api/user/user/profile')

      if (response.data.code === 1) {
        user.value = response.data.data
        userStore.setUser(user.value)
      }
    } catch (error) {
      console.log(error)
    }
  }

  // 退出登录
  const signOut = async () => {
    try {
      await axios.get('/api/user/user/sign-out')
      goHome()
    } catch (error) {
      toast("嗨！", {
        description: "退出登录遇到问题，但已清除本地状态",
        action: {
          label: '我知道了',
        },
      })
    } finally {
      userStore.clearUser()
    }
  }

  // 以下是购物车模块
  interface Product {
    id: number,
    productType: number,
    name: string
    variantName: string,
    image: string,
    price: number,
    quantity: number
  }

  const products = ref<Product[]>([])

  const totalPrice = computed(() =>
    products.value.reduce((sum, product) => sum + product.price * product.quantity, 0)
  )

  const showCart = async () => {
    products.value = []
    try {
      const response = await axios.get("/api/user/shop/cart/list")
      products.value = response.data.data || []
    } catch (error) {
      console.log(error)
    }
  }

  // 商品数量改变
  const quantity = async (product: Product, type: number) => {
    try {
      if (type === 0) {
        await axios.delete("/api/user/shop/cart/minus", {
          data: {
            productType: product.productType,
            productId: product.id
          }
        })

        const update = products.value.find(p => p.id === product?.id)
        if (update) {
          update.quantity = update.quantity - 1
          if (update.quantity === 0) {
            products.value = products.value.filter(p => p.id !== product?.id)
          }
        }

      } else {
        await axios.post("/api/user/shop/cart/plus", {
          productType: product.productType,
          productId: product.id
        })

        const update = products.value.find(p => p.id === product?.id)
        if (update) {
          update.quantity = update.quantity + 1
        }
      }
    } catch (error) {
      console.log(error)
    }
  }

  const clear = async () => {
    try {
      await axios.delete("/api/user/shop/cart/clear")

      products.value = []
    } catch (error) {
      console.log(error)
    }
  }

  const order = async () => {
    try {
      const response = await axios.post("/api/user/shop/order/create")
      const number = response.data.data
      // 跳转到订单页面并传递id
      if (response.data.code === 1) {
        router.push(`/order/${number}`)
        products.value = []
      }
    } catch (error) {
      console.log(error)
    }
  }

  const isOpenCouponDialog = ref(false); // 控制弹窗显示/隐藏
  const couponDialogType = ref(1)
  const showCouponDialog = (type: number) => {
    couponDialogType.value = type
    isOpenCouponDialog.value = true
  }
</script>

<style scoped>

  /* 过渡样式 */
  .fade-enter-active,
  .fade-leave-active {
    transition: opacity 0.2s ease, transform 0.2s ease;
  }

  .fade-enter-from {
    opacity: 0;
    transform: translateY(5px);
  }
  .fade-leave-to {
    opacity: 0;
    transform: translateY(-5px);
  }
</style>