<template>
  <Transition
    enter-active-class="transition-opacity duration-200"
    enter-from-class="opacity-0"
    enter-to-class="opacity-100"
    leave-active-class="transition-opacity duration-200"
    leave-from-class="opacity-100"
    leave-to-class="opacity-0">
    <div v-if="modelValue" class="fixed inset-0 z-50 flex items-center justify-center backdrop-blur-sm" @click="closeModal">
      <Transition
        enter-active-class="transition-all duration-200 ease-out"
        enter-from-class="opacity-0 scale-95"
        enter-to-class="opacity-100 scale-100"
        leave-active-class="transition-all duration-200 ease-in"
        leave-from-class="opacity-100 scale-100"
        leave-to-class="opacity-0 scale-95">
        <div class="rounded-lg max-w-md w-full mx-4 overflow-hidden" @click.stop>
          <Tabs default-value="login" class="w-full">
            <TabsList class="grid w-full grid-cols-2">
              <TabsTrigger value="login">
                登录
              </TabsTrigger>
              <TabsTrigger value="register">
                注册
              </TabsTrigger>
            </TabsList>
            <TabsContent value="login">
              <Card>
                <CardHeader>
                  <CardTitle>自己人？快进来！</CardTitle>
                  <CardDescription>
                    输入您的账户（用户名），密码以及验证码
                  </CardDescription>
                </CardHeader>
                <CardContent class="space-y-2">
                  <div class="space-y-1">
                    <Label for="Username">用户名</Label>
                    <Input id="username" v-model="user.username" />
                  </div>
                  <div class="space-y-1">
                    <Label for="Password">密码</Label>
                    <Input id="password" type="password" v-model="user.password" />
                  </div>
                  <div class="space-y-1">
                    <Label for="Password">手机号</Label>
                    <Input id="phone" v-model="user.phone" />
                  </div>
                  <div class="space-y-1">
                    <Label for="Code">验证码</Label>
                    <div class="flex gap-2">
                      <Input id="code" v-model="user.smsCode" class="flex-1" placeholder="请输入验证码" />
                      <Button :disabled="countDown > 0 || isSendingCode" @click="getSmsCode()" class="w-1/4">
                        <span v-if="isSendingCode">发送中...</span>
                        <span v-else>{{ countDown > 0 ? countDown + 's' : "发送验证码" }}</span>
                      </Button>
                    </div>
                  </div>
                </CardContent>
                <CardFooter>
                  <Button class="w-full" :disabled="isLoading" @click="loginOrRegister('login')">
                    <span v-if="isLoading && currentAction === 'login'">登录中...</span>
                    <span v-else>登录</span>
                  </Button>
                </CardFooter>
              </Card>
            </TabsContent>
            <TabsContent value="register">
              <Card>
                <CardHeader>
                  <CardTitle>没有号？整一个！</CardTitle>
                  <CardDescription>
                    填写您的账户（用户名），密码以及验证码
                  </CardDescription>
                </CardHeader>
                <CardContent class="space-y-2">
                  <div class="space-y-1">
                    <Label for="Username">用户名</Label>
                    <Input id="username" required v-model="user.username" />
                  </div>
                  <div class="space-y-1">
                    <Label for="password">密码</Label>
                    <Input id="password" type="password" required v-model="user.password" />
                  </div>
                  <div class="space-y-1">
                    <Label for="Password">手机号</Label>
                    <Input id="phone" v-model="user.phone" />
                  </div>
                  <div class="space-y-1">
                    <Label for="Code">验证码</Label>
                    <div class="flex gap-2">
                      <Input id="code" v-model="user.smsCode" class="flex-1" placeholder="请输入验证码" />
                      <Button :disabled="countDown > 0 || isSendingCode" @click="getSmsCode()" class="w-1/4">
                        <span v-if="isSendingCode">发送中...</span>
                        <span v-else>{{ countDown > 0 ? countDown + 's' : "发送验证码" }}</span>
                      </Button>
                    </div>
                  </div>
                </CardContent>
                <CardFooter>
                  <Button class="w-full" :disabled="isLoading" @click="loginOrRegister('register')">
                    <span v-if="isLoading && currentAction === 'register'">注册中...</span>
                    <span v-else>注册</span>
                  </Button>
                </CardFooter>
              </Card>
            </TabsContent>
          </Tabs>
        </div>
      </Transition>
    </div>
  </Transition>
</template>

<script setup lang="ts">
  import { reactive, ref } from "vue"
  import axios from "axios"
  import { useCookies } from 'vue3-cookies'
  import { Button } from "@/components/ui/button"
  import {Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle, } from "@/components/ui/card"
  import { Input } from "@/components/ui/input"
  import { Label } from "@/components/ui/label"
  import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
  import 'vue-sonner/style.css'
  import { toast } from "vue-sonner"

  const { cookies } = useCookies()

  defineProps<{
    modelValue: boolean
  }>()

  const emit = defineEmits<{
    'update:modelValue': [value: boolean]
    'login-success': [] // 添加登录成功事件
  }>()

  const user = reactive({
    username: "",
    password: "",
    phone: "",
    smsCode: ""
  })

  const countDown = ref(0)
  const isLoading = ref(false) // 登录/注册加载状态
  const isSendingCode = ref(false) // 发送验证码加载状态
  const currentAction = ref<"login" | "register" | null>(null) // 当前操作类型
  let timer: number | null = null

  // 关闭登陆注册
  const closeModal = () => {
    emit('update:modelValue', false)
    // 重置表单
    Object.assign(user, {
      username: "",
      password: "",
      phone: "",
      smsCode: ""
    })
    // 重置加载状态
    isLoading.value = false
    isSendingCode.value = false
    currentAction.value = null
  }

  // 添加 async 关键字修复错误
  const getSmsCode = async () => {
    if (!user.phone) {
      toast("啊哈！", {
        description: "手机号不得为空！",
        action: {
          label: '我知道了',
        },
      })
      return
    }

    // 设置发送验证码加载状态
    isSendingCode.value = true

    try {
      const response = await axios.post('/user/user/smscode', {
        phone: user.phone
      })

      toast("啊哈！", {
        description: response.data.msg,
        action: {
          label: '我知道了',
        },
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
      toast("啊哈！", {
        description: "发送验证码失败，请重试",
        action: {
          label: '我知道了',
        },
      })

      
    } finally {
      // 无论成功或失败，都重置发送状态
      isSendingCode.value = false
    }
  }

  const loginOrRegister = async (type: "login" | "register") => {
    if (!user.username || !user.password || !user.smsCode || !user.phone) {
      toast("啊哈！", {
        description: "所有字段都不得为空！",
        action: {
          label: '我知道了',
        },
      })
      return
    }

    // 设置加载状态
    isLoading.value = true
    currentAction.value = type

    const url = type === 'login' ? '/user/user/login' : '/user/user/register'
    
    try {
      const response = await axios.post(url, {
        username: user.username,
        password: user.password,
        phone: user.phone,
        smsCode: user.smsCode
      })
      
      toast("啊哈！", {
        description: response.data.msg,
        action: {
          label: '我知道了',
        },
      })
      
      // 根据后端返回的 code 判断是否登录成功
      if (response.data.code === 1) {
        // 登录成功，设置前端 Cookie 标记登录状态
      // 使用字符串格式的过期时间
      cookies.set('user_login_status', '1', '7d') // 直接传递字符串
      
      // 可选：存储用户名用于显示
      cookies.set('user_username', user.username, '7d')
        
        // 发射登录成功事件
        emit('login-success')
        closeModal()
      }
    } catch (error) {
      console.error(error)
      toast("啊哈！", {
        description: "请求失败，请重试",
        action: {
          label: '我知道了',
        },
      })
    } finally {
      // 无论成功或失败，都重置加载状态
      isLoading.value = false
      currentAction.value = null
    }
  }
</script>

<style scoped>
</style>