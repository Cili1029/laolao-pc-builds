<template>
  <div class="min-h-screen bg-gray-50 flex flex-col">
    <!-- 固定导航条 -->
    <header class="bg-white sticky top-0 z-50 shadow-md">
      <div class="w-full">
        <div class="container mx-auto px-4 py-3 flex justify-between items-center border-l border-r border-gray-200">
          <!-- Logo -->
          <div class="flex items-center space-x-2">
            <img :src="logo" class="w-9 h-9 rounded-md" @click="goHome" />
            <span class="text-xl font-bold">劳劳的装机工坊</span>
          </div>

          <!-- 导航链接 -->
          <div class="hidden md:flex space-x-6 items-center">
            <RouterLink to="/buy" class="text-gray-600 hover:text-blue-500 transition-colors">买</RouterLink>
            <RouterLink to="/hello" class="text-gray-600 hover:text-blue-500 transition-colors">功能</RouterLink>

            <!-- 头像区域 - 根据前端 Cookie 状态显示不同内容 -->
            <div v-if="!isLoggedIn">
              <Avatar class="cursor-pointer hover:ring-2 hover:ring-blue-500 transition-all"
                @click="showLoginModal = true">
                <AvatarFallback>登录</AvatarFallback>
              </Avatar>
            </div>

            <div v-else>
              <DropdownMenu>
                <DropdownMenuTrigger as-child>
                  <Avatar class="cursor-pointer hover:ring-2 hover:ring-blue-500 transition-all rounded-md">
                    <AvatarImage src="https://github.com/unovue.png" alt="用户头像" />
                    <AvatarFallback>{{ userInitials }}</AvatarFallback>
                  </Avatar>
                </DropdownMenuTrigger>
                <DropdownMenuContent class="w-56" align="end">
                  <DropdownMenuLabel class="flex flex-col">
                    <span class="font-semibold">{{ username }}</span>
                    <span class="text-xs text-gray-500 font-normal">欢迎回来！</span>
                  </DropdownMenuLabel>
                  <DropdownMenuSeparator />
                  <DropdownMenuItem>
                    <User class="mr-2 h-4 w-4" />
                    <span>个人信息</span>
                  </DropdownMenuItem>
                  <DropdownMenuItem>
                    <CreditCard class="mr-2 h-4 w-4" />
                    <span>账单管理</span>
                  </DropdownMenuItem>
                  <DropdownMenuItem>
                    <Settings class="mr-2 h-4 w-4" />
                    <span>账户设置</span>
                  </DropdownMenuItem>
                  <DropdownMenuSeparator />
                  <DropdownMenuItem @click="logout" class="text-red-600 focus:text-red-600">
                    <LogOut class="mr-2 h-4 w-4" />
                    <span>退出登录</span>
                  </DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            </div>
          </div>
        </div>
      </div>
    </header>

    <!-- 主要内容区域 -->
    <main class="flex-grow container mx-auto px-4 py-8 border-l border-r border-gray-200">
      <RouterView></RouterView>
    </main>

    <!-- Footer区域 -->
    <footer class="bg-gray-800 text-white">
      <div class="container mx-auto px-4 py-6 border-l border-r border-gray-700">
        <div class="flex flex-col md:flex-row justify-between items-center">
          <div class="mb-4 md:mb-0">
            <div class="flex items-center space-x-2">
              <img :src="logo" alt="Company Logo" class="w-6 h-6 rounded-md">
              <span class="text-lg font-bold">我的网站</span>
            </div>
            <p class="text-gray-400 text-sm mt-2">© 2025 我的网站. 保留所有权利.</p>
          </div>

          <div class="flex space-x-6">
            <a href="#" class="text-gray-300 hover:text-white transition-colors">假如再也见不到你</a>
            <a href="#" class="text-gray-300 hover:text-white transition-colors">祝你</a>
            <a href="#" class="text-gray-300 hover:text-white transition-colors">早安</a>
            <a href="#" class="text-gray-300 hover:text-white transition-colors">午安</a>
            <a href="#" class="text-gray-300 hover:text-white transition-colors">晚安</a>
          </div>
        </div>
      </div>
    </footer>

    <!-- 登录 -->
    <LoginModal v-model="showLoginModal" @login-success="handleLoginSuccess" />
  </div>
  <Toaster position="top-right" />
</template>

<script setup lang="ts">
  import { ref, onMounted, computed } from 'vue'
  import axios from "axios"
  import { useRouter } from 'vue-router'
  import { useCookies } from 'vue3-cookies'
  import LoginModal from '@/components/LoginOrRegister.vue'
  import { Toaster } from '@/components/ui/sonner'
  import 'vue-sonner/style.css'
  import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
  import { CreditCard, LogOut, Settings, User } from "lucide-vue-next"
  import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from "@/components/ui/dropdown-menu"
  import 'vue-sonner/style.css'
  import { toast } from "vue-sonner"

  import logo from '@/assets/logo.jpg'

  const router = useRouter()
  const { cookies } = useCookies()

  const goHome = () => {
    router.push('/');
  }

  // 控制登录模态框显示状态
  const showLoginModal = ref(false)
  // 登录状态
  const isLoggedIn = ref(false)
  // 用户信息
  const username = ref('')

  // 检查前端 Cookie 登录状态
  const checkLoginStatus = () => {
    const loginStatus = cookies.get('user_login_status')
    const storedUsername = cookies.get('user_username')

    isLoggedIn.value = loginStatus === '1'
    username.value = storedUsername || '用户'
  }

  // 用户名字首字母（用于头像回退显示）
  const userInitials = computed(() => {
    if (!username.value) return '用户'
    return username.value.substring(0, 2).toUpperCase()
  })

  // 处理登录成功
  const handleLoginSuccess = () => {
    checkLoginStatus()
    showLoginModal.value = false
  }

  // 退出登录
  const logout = async () => {
    try {
      const response = await axios.get('/user/user/logout')

      toast("啊哈！", {
        description: response.data.msg,
        action: {
          label: '我知道了',
        },
      })
    } catch (error) {
      console.log(error)
      // 即使后端退出失败，也要清除前端状态
      toast("啊哈！", {
        description: "退出登录遇到问题，但已清除本地状态",
        action: {
          label: '我知道了',
        },
      })
    } finally {
      // 无论后端退出是否成功，都清除前端状态
      cookies.remove('user_login_status')
      cookies.remove('user_username')

      // 更新状态
      isLoggedIn.value = false
      username.value = ''
    }
  }

  // 组件挂载时检查登录状态
  onMounted(() => {
    checkLoginStatus()
  })
</script>

<style scoped>
  .min-h-screen {
  background-image: url('./assets/background1.jpg');
  background-size: cover; /* 覆盖整个容器 */
  background-position: center; /* 居中显示 */
  background-attachment: fixed; /* 固定背景，不随内容滚动 */
  background-repeat: no-repeat; /* 不重复 */
}
</style>