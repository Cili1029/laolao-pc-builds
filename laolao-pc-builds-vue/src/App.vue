<template>
  <div class="min-h-screen flex flex-col h-screen">
    <!-- 固定导航条 -->
    <div class="sticky top-0 z-50 bg-white/30 backdrop-blur-md shadow-md">
      <div class="flex justify-between items-center px-4 sm:px-6 lg:px-40 h-16">
        <div class="flex items-center space-x-1">
          <img :src="logo" class="w-9 h-9 rounded-md" @click="goHome" />
          <span class="text-xl font-bold">劳劳的装机工坊</span>
        </div>
        <!-- 导航链接 -->
        <div class="flex space-x-6 items-center">
          <RouterLink to="/hello" class="text-gray-600 hover:text-blue-500 transition-colors">功能测试</RouterLink>

          <!-- 购物车 -->
          <Drawer>
            <DrawerTrigger class="text-gray-600 hover:text-blue-500" @click="showCart()">购物车</DrawerTrigger>
            <DrawerContent v-if="products && products.length > 0" class="h-3/4">
              <DrawerHeader>
                <DrawerTitle class="text-3xl">我的购物车</DrawerTitle>
                <DrawerDescription>
                  <p class="text-xl hover:text-blue-500" @click="clear()">清空购物车</p>
                </DrawerDescription>
              </DrawerHeader>
              <div class="flex gap-6 py-4 overflow-y-auto scrollbar-edge">
                <div class="w-full flex flex-col space-y-2">
                  <div v-for="product in products" :key="product.name"
                    class="bg-white rounded-lg shadow-sm border border-gray-200 p-4 flex items-center">
                    <img :src="product.image" class="w-15 h-15 object-cover rounded-md mr-4" />
                    <div class="flex-1">
                      <h3 class="font-medium text-gray-900">{{ product.name }}</h3>
                      {{ product.variantName }}
                    </div>
                    <div class="ml-auto mx-10">
                      <span class="text-lg font-bold text-red-600">￥{{ product.price }}</span>
                    </div>
                    <div class="ml-auto mx-10">
                      <Button variant="outline" size="icon" class="h-8 w-8 shrink-0 rounded-full"
                        @click="quantity(product, 0)">
                        <span class="icon-[mdi--minus] h-4 w-4"></span>
                        <span class="sr-only">Decrease</span>
                      </Button>
                      <span class="text-lg font-bold mx-1">{{ product.quantity }}</span>
                      <Button variant="outline" size="icon" class="h-8 w-8 shrink-0 rounded-full"
                        @click="quantity(product, 1)">
                        <span class="icon-[mdi--plus] h-4 w-4"></span>
                        <span class="sr-only">Increase</span>
                      </Button>
                    </div>
                  </div>
                </div>
              </div>
              <DrawerFooter>
                <div class="text-xl ml-auto font-bold text-red-600">总价（未算优惠券）: ￥{{ totalPrice.toFixed(2) }}</div>
                <DrawerClose as-child>
                  <Button class="h-15" @click="order()">去结算</Button>
                </DrawerClose>
              </DrawerFooter>
            </DrawerContent>

            <DrawerContent v-else class="h-3/4">
              <DrawerHeader>
                <DrawerTitle class="text-3xl">我的购物车</DrawerTitle>
                <DrawerDescription></DrawerDescription>
              </DrawerHeader>
              <div class="text-4xl text-center">
                购物车是空的！
              </div>
              <DrawerFooter>
                <Button class="h-15" disabled>去结算</Button>
              </DrawerFooter>
            </DrawerContent>
          </Drawer>

          <!-- 未登录，点击登录 -->
          <div v-if="!userStore.signedIn">
            <RouterLink to="/sign">
              <Avatar class="cursor-pointer hover:ring-2 hover:ring-blue-500 transition-all">
                <AvatarFallback>登录</AvatarFallback>
              </Avatar>
            </RouterLink>
          </div>

          <!-- 已登录，显示用户数据 -->
          <div v-else>
            <DropdownMenu>
              <DropdownMenuTrigger as-child>
                <Avatar class="cursor-pointer hover:ring-2 hover:ring-blue-500 transition-all rounded-md">
                  <AvatarImage :src="userStore.user.avatar" alt="用户头像" />
                  <AvatarFallback>{{ userStore.user.name.substring(0, 1) }}</AvatarFallback>
                </Avatar>
              </DropdownMenuTrigger>
              <DropdownMenuContent class="w-56" align="end">
                <DropdownMenuLabel class="flex flex-col">
                  <span class="font-semibold">{{ userStore.user.name }}</span>
                  <span class="text-xs text-gray-500 font-normal">欢迎回来！</span>
                </DropdownMenuLabel>
                <DropdownMenuSeparator />
                <DropdownMenuItem>
                  <User class="mr-2 h-4 w-4" />
                  <span>个人信息(还没写)</span>
                </DropdownMenuItem>
                <DropdownMenuItem>
                  <Smile class="mr-2 h-4 w-4" />
                  <span>签到/优惠券(还没写)</span>
                </DropdownMenuItem>
                <RouterLink to="/my-orders">
                  <DropdownMenuItem>
                    <ShoppingBag class="mr-2 h-4 w-4" />
                    <span>我的订单</span>
                  </DropdownMenuItem>
                </RouterLink>
                <DropdownMenuSeparator />
                <DropdownMenuItem @click="signOut" class="text-red-600 focus:text-red-600">
                  <LogOut class="mr-2 h-4 w-4" />
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
    <!-- 全局消息弹窗 -->
    <Toaster position="top-right" />
  </div>
</template>

<script setup lang="ts">
  import { ref, onMounted, computed } from 'vue'
  import axios from './utils/myAxios'
  import { useRoute } from 'vue-router'
  const route = useRoute()
  import logo from '@/assets/logo.jpg'
  import { Toaster } from '@/components/ui/sonner'
  import { toast } from "vue-sonner"
  import 'vue-sonner/style.css'
  import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
  import { ShoppingBag, LogOut, User, Smile } from "lucide-vue-next"
  import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from "@/components/ui/dropdown-menu"
  import { Drawer, DrawerContent, DrawerDescription, DrawerFooter, DrawerHeader, DrawerTitle, DrawerTrigger, DrawerClose } from "@/components/ui/drawer"
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
</script>

<style scoped>
  .min-h-screen {
    background-image: url('./assets/background1.jpg');
    background-size: cover;
    background-position: center;
    background-attachment: fixed;
    background-repeat: no-repeat;
  }

  /* 过渡样式 */
  .fade-enter-active,
  .fade-leave-active {
    transition: opacity 0.1s ease;
  }

  .fade-enter-from,
  .fade-leave-to {
    opacity: 0;
  }

</style>