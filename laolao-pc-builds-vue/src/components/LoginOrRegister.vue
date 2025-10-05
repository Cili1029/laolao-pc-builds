<template>
  <div class="fixed inset-0 z-50 bg-cover bg-center" :style="{ backgroundImage: `url(${background})` }">
    <div class="flex justify-center items-center h-screen">
      <Tabs default-value="login" class="w-[400px]">
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
                <Input id="password" v-model="user.password" />
              </div>
              <div class="space-y-1">
                <Label for="Password">手机号</Label>
                <Input id="phone" v-model="user.phone" />
              </div>
              <div class="space-y-1">
                <Label for="Code">验证码</Label>
                <div class="flex gap-2">
                  <Input id="code" v-model="user.smsCode" class="flex-1" placeholder="请输入验证码" />
                  <Button :disabled="countDown > 0" @click="getSmsCode()" class="w-1/4">
                    {{ countDown > 0 ? countDown + 's' : "发送验证码" }}
                  </Button>
                </div>
              </div>
            </CardContent>
            <CardFooter>
              <Button class="w-full" @click="loginOrRegister('login')">登录</Button>
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
                <Input id="password" required v-model="user.password" />
              </div>
              <div class="space-y-1">
                <Label for="Password">手机号</Label>
                <Input id="phone" v-model="user.phone" />
              </div>
              <div class="space-y-1">
                <Label for="Code">验证码</Label>
                <div class="flex gap-2">
                  <Input id="code" v-model="user.smsCode" class="flex-1" placeholder="请输入验证码" />
                  <Button :disabled="countDown > 0" @click="getSmsCode()" class="w-1/4">
                    {{ countDown > 0 ? countDown + 's' : "发送验证码" }}
                  </Button>
                </div>
              </div>
            </CardContent>
            <CardFooter>
              <Button class="w-full" @click="loginOrRegister('register')">注册</Button>
            </CardFooter>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { reactive } from "vue"
  import axios from "axios"

  import { Button } from "@/components/ui/button"
  import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
  } from "@/components/ui/card"
  import { Input } from "@/components/ui/input"
  import { Label } from "@/components/ui/label"
  import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
  import 'vue-sonner/style.css'
  import { toast } from "vue-sonner"

  // 背景图
  import background from '@/assets/background1.jpg'
  import { ref } from "vue"

  const user = reactive({
    username: "",
    password: "",
    phone: "",
    smsCode: ""
  })

  const countDown = ref(0)
  let timer: number | null = null

  const getSmsCode = async () => {
    if (!user.phone) {
      toast("啊哈！", {
        description: "不得为空！",
        action: {
          label: '我知道了',
        },
      })
      return
    }

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
    }
  }

  const loginOrRegister = (type: "login" | "register") => {
    if (!user.username || !user.password || !user.smsCode || !user.phone || !user.smsCode) {
      toast("啊哈！", {
        description: "不得为空！",
        action: {
          label: '我知道了',
        },
      })
      return
    }

    const url = type === 'login' ? '/user/user/login' : '/user/user/register'
    axios.post(url,
      {
        username: user.username,
        password: user.password,
        phone: user.phone,
        smsCode: user.smsCode
      })
      .then(response => {
        toast("啊哈！", {
          description: response.data.msg,
          action: {
            label: '我知道了',
          },
        })
      })
      .catch(error => console.error(error))
  }

</script>

<style scoped></style>