<template>
  <div class="min-h-screen bg-gray-50 flex flex-col">
    <!-- 固定导航条 -->
    <header class="bg-white sticky top-0 z-50 shadow-md">
      <div class="w-full">
        <div class="container mx-auto px-4 py-3 flex justify-between items-center border-l border-r border-gray-200">
          <div class="flex items-center space-x-2">
            <img :src="logo" class="w-9 h-9 rounded-md" @click="goHome" />
            <span class="text-xl font-bold">劳劳的装机工坊</span>
          </div>

          <!-- 导航链接 -->
          <div class="hidden md:flex space-x-6 items-center">
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
                <div class="flex gap-6 py-4 overflow-y-auto">
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
            <div v-if="!isLoggedIn">
              <Dialog :open="showLoginModal" @update:open="(value) => {
                showLoginModal = value
                if (value) {
                  // 打开登陆注册框时重置到密码登录
                  status = 'loginA'
                  resetLoginForm()
                }
              }">
                <DialogTrigger as-child>
                  <Avatar class="cursor-pointer hover:ring-2 hover:ring-blue-500 transition-all">
                    <AvatarFallback>登录</AvatarFallback>
                  </Avatar>
                </DialogTrigger>
                <DialogContent class="sm:max-w-[425px]">
                  <DialogHeader>
                    <DialogTitle>登录或注册</DialogTitle>
                  </DialogHeader>
                  <DialogDescription></DialogDescription>
                  <Tabs default-value="loginA" class="w-full">
                    <TabsList class="grid w-full grid-cols-3">
                      <TabsTrigger value="loginA" @click="status = 'loginA', resetLoginForm()">
                        密码登录
                      </TabsTrigger>
                      <TabsTrigger value="loginB" @click="status = 'loginB', resetLoginForm()">
                        验证码登录
                      </TabsTrigger>
                      <TabsTrigger value="register" @click="status = 'register', resetLoginForm()">
                        注册
                      </TabsTrigger>
                    </TabsList>
                    <TabsContent v-for="tab in tabs" :key="tab.value" :value="tab.value">
                      <Card>
                        <CardHeader>
                          <CardTitle>{{ tab.title }}</CardTitle>
                          <CardDescription>
                            {{ tab.description }}
                          </CardDescription>
                        </CardHeader>
                        <CardContent class="space-y-2">
                          <div class="space-y-1" v-if="status === 'loginA' || status === 'register'">
                            <Label for="Username">用户名</Label>
                            <Input id="username" v-model="loginUser.username" />
                          </div>
                          <div class="space-y-1" v-if="status === 'loginA' || status === 'register'">
                            <Label for="Password">密码</Label>
                            <Input id="password" type="password" v-model="loginUser.password" />
                          </div>
                          <div class="space-y-1" v-if="status === 'loginB' || status === 'register'">
                            <Label for="Password">邮箱号</Label>
                            <Input id="email" v-model="loginUser.email" />
                          </div>
                          <div class="space-y-1" v-if="status === 'loginB' || status === 'register'">
                            <Label for="Code">验证码</Label>
                            <div class="flex gap-2">
                              <Input id="code" v-model="loginUser.emailCode" class="flex-1" placeholder="请输入验证码" />
                              <Button :disabled="countDown > 0 || isSendingCode" @click="getEmailCode()" class="w-1/4">
                                <span v-if="isSendingCode">发送中...</span>
                                <span v-else>{{ countDown > 0 ? countDown + 's' : "发送验证码" }}</span>
                              </Button>
                            </div>
                          </div>
                        </CardContent>
                        <CardFooter>
                          <Button class="w-full" :disabled="isLoading"
                            @click="loginOrRegister(tab.value as 'login' | 'register')">
                            <span v-if="isLoading && currentAction === tab.value">{{ tab.buttonText }}中...</span>
                            <span v-else>{{ tab.buttonText }}</span>
                          </Button>
                        </CardFooter>
                      </Card>
                    </TabsContent>
                  </Tabs>
                </DialogContent>
              </Dialog>
            </div>

            <!-- 已登录，显示用户数据 -->
            <div v-else>
              <DropdownMenu>
                <DropdownMenuTrigger as-child>
                  <Avatar class="cursor-pointer hover:ring-2 hover:ring-blue-500 transition-all rounded-md">
                    <AvatarImage :src="user?.avatar" alt="用户头像" />
                    <AvatarFallback>{{ userInitials }}</AvatarFallback>
                  </Avatar>
                </DropdownMenuTrigger>
                <DropdownMenuContent class="w-56" align="end">
                  <DropdownMenuLabel class="flex flex-col">
                    <span class="font-semibold">{{ user.name }}</span>
                    <span class="text-xs text-gray-500 font-normal">欢迎回来！</span>
                  </DropdownMenuLabel>
                  <DropdownMenuSeparator />
                  <DropdownMenuItem>
                    <User class="mr-2 h-4 w-4" />
                    <span>个人信息</span>
                  </DropdownMenuItem>
                  <DropdownMenuItem>
                    <Smile class="mr-2 h-4 w-4" />
                    <span>签到/优惠券</span>
                  </DropdownMenuItem>
                  <DropdownMenuItem>
                    <ShoppingBag class="mr-2 h-4 w-4" />
                    <RouterLink to="/my-orders">我的订单</RouterLink>
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
    <main class="flex-grow container mx-auto border-l border-r border-gray-200">
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
    <!-- 全局消息弹窗 -->
    <Toaster position="top-right" />
  </div>
</template>

<script setup lang="ts">
  import { ref, onMounted, computed, reactive } from 'vue'
  import axios from './utils/myAxios'
  import { useRouter } from 'vue-router'
  import logo from '@/assets/logo.jpg'
  import { Toaster } from '@/components/ui/sonner'
  import { toast } from "vue-sonner"
  import 'vue-sonner/style.css'
  import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
  import { ShoppingBag, LogOut, User, Smile } from "lucide-vue-next"
  import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from "@/components/ui/dropdown-menu"
  import { Button } from "@/components/ui/button"
  import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
  import { Input } from "@/components/ui/input"
  import { Label } from "@/components/ui/label"
  import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
  import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
  const router = useRouter()

  // 组件挂载时检查登录状态
  onMounted(() => {
    getProfile()
  })

  // 回主页
  const goHome = () => {
    router.push('/home');
  }

  // 登陆注册显示问题
  const status = ref('loginA')
  const tabs = [
    {
      value: 'loginA',
      title: '自己人？快进来！',
      description: '输入您的账户（用户名），密码',
      buttonText: '登录'
    },
    {
      value: 'loginB',
      title: '自己人？快进来！',
      description: '输入您的邮箱号，验证码',
      buttonText: '登录'
    },
    {
      value: 'register',
      title: '没有号？整一个！',
      description: '填写您的账户（用户名），密码以及验证码',
      buttonText: '注册'
    }
  ]

  // 登录框状态
  const showLoginModal = ref(false)
  // 判断是否登陆用
  const isLoggedIn = ref(false)

  // 登录表单数据
  const loginUser = reactive({
    username: "",
    password: "",
    email: "",
    emailCode: ""
  })

  // 验证码相关状态
  const countDown = ref(0)
  const isLoading = ref(false)
  const isSendingCode = ref(false)
  const currentAction = ref<"login" | "register" | null>(null)
  let timer: number | null = null



  // 图片加载失败显示用户名字首字母
  const userInitials = computed(() => {
    if (!user.value) return '用户'
    return user.value.name.substring(0, 2).toUpperCase()
  })

  // 切换注册登录选项重置表单
  const resetLoginForm = () => {
    Object.assign(loginUser, {
      username: "",
      password: "",
      email: "",
      emailCode: ""
    })
  }

  const getProfile = async () => {
    try {
      const response = await axios.get('/user/user/profile')
      if (response.data.code === 1) {
        user.value = response.data.data
        // 设置已登录
        isLoggedIn.value = true
      }

    } catch (error) {
      console.log(error)
    }
  }

  // 当前登录的用户昵称
  const user = ref({
    avatar: '',
    username: '',
    name: '',
  })

  // 发送验证码
  const getEmailCode = async () => {
    if (!loginUser.email) {
      toast("嗨！", {
        description: "邮箱号不得为空！",
        action: {
          label: '我知道了',
        },
      })
      return
    }

    isSendingCode.value = true

    try {
      await axios.post('/user/user/email-code', {
        email: loginUser.email
      })

      countDown.value = 60
      timer = setInterval(() => {
        countDown.value--
        if (countDown.value <= 0 && timer) {
          clearInterval(timer)
          timer = null
        }
      }, 1000)

    } catch (error) {
      console.error(error)
      toast("嗨！", {
        description: "发送验证码失败，请重试",
        action: {
          label: '我知道了',
        },
      })
    } finally {
      isSendingCode.value = false
    }
  }

  // 登录和注册
  const loginOrRegister = async (type: "login" | "register") => {
    // 验证字段
    if (type === 'register') {
      // 注册验证
      if (!loginUser.username || !loginUser.password || !loginUser.emailCode || !loginUser.email) {
        toast("嗨！", {
          description: "所有字段都不得为空！",
          action: {
            label: '我知道了',
          },
        })
        return
      }
    } else {
      // 登录验证 - 根据当前状态区分密码登录和验证码登录
      if (status.value === 'loginA') {
        // 密码登录验证
        if (!loginUser.username || !loginUser.password) {
          toast("嗨！", {
            description: "用户名和密码不能为空！",
            action: {
              label: '我知道了',
            },
          })
          return
        }
      } else if (status.value === 'loginB') {
        // 验证码登录验证
        if (!loginUser.email || !loginUser.emailCode) {
          toast("嗨！", {
            description: "邮箱号和验证码不能为空！",
            action: {
              label: '我知道了',
            },
          })
          return
        }
      }
    }

    isLoading.value = true
    currentAction.value = type

    try {
      let response

      if (type === 'register') {
        // 注册逻辑
        response = await axios.post('/user/user/register', {
          username: loginUser.username,
          password: loginUser.password,
          email: loginUser.email,
          emailCode: loginUser.emailCode
        })
      } else {
        // 登录逻辑 - 根据状态发送不同的数据
        if (status.value === 'loginA') {
          // 密码登录
          response = await axios.post('/user/user/login', {
            username: loginUser.username,
            password: loginUser.password
          })
        } else {
          // 验证码登录
          response = await axios.post('/user/user/login', {
            email: loginUser.email,
            emailCode: loginUser.emailCode
          })
        }
      }

      if (response.data.code === 1) {
        user.value = response.data.data
        showLoginModal.value = false
        isLoggedIn.value = true
      }
    } catch (error) {
      console.log(error)
    } finally {
      isLoading.value = false
      currentAction.value = null
    }
  }

  // 退出登录
  const logout = async () => {
    try {
      await axios.get('/user/user/logout')
      goHome()
    } catch (error) {
      toast("嗨！", {
        description: "退出登录遇到问题，但已清除本地状态",
        action: {
          label: '我知道了',
        },
      })
    } finally {
      isLoggedIn.value = false
      user.value = {
        avatar: '',
        username: '',
        name: ''
      }
    }
  }

  // 以下是购物车模块
  import { Drawer, DrawerContent, DrawerDescription, DrawerFooter, DrawerHeader, DrawerTitle, DrawerTrigger,DrawerClose } from "@/components/ui/drawer"

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
      const response = await axios.get("/user/cart/list")
      products.value = response.data.data || []
      console.log(products.value)
    } catch (error) {
      console.log(error)
    }
  }

  const quantity = async (product: Product, type: number) => {
    try {
      if (type === 0) {
        await axios.delete("/user/cart/minus", {
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
        console.log(products.value)

      } else {
        await axios.post("/user/cart/plus", {
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
      await axios.delete("/user/cart/clear")

      products.value = []
    } catch (error) {
      console.log(error)
    }
  }

  const order = async () => {
    try {
      const response = await axios.post("/user/order/create")
      const id = response.data.data
      // 跳转到订单页面并传递id
      if (response.data.code === 1) {
        router.push(`/order/${id}`)
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
</style>