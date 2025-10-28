<template>
    <div class="flex justify-center items-center h-full">
        <Tabs default-value="A" class="w-[400px]">
            <TabsList class="grid w-full grid-cols-3">
                <TabsTrigger value="A" @click="currentMethod = 1">
                    密码登录
                </TabsTrigger>
                <TabsTrigger value="B" @click="currentMethod = 2">
                    验证码登录
                </TabsTrigger>
                <TabsTrigger value="C" @click="currentMethod = 3">
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
                        <div class="space-y-1" v-if="currentMethod !== 2">
                            <Label for="Username">用户名</Label>
                            <Input id="username" v-model="signDetails.username" />
                        </div>
                        <div class="space-y-1" v-if="currentMethod !== 2">
                            <Label for="Password">密码</Label>
                            <Input id="password" type="password" v-model="signDetails.password" />
                        </div>
                        <div class="space-y-1" v-if="currentMethod !== 1">
                            <Label for="Password">邮箱号</Label>
                            <Input id="email" v-model="signDetails.email" />
                        </div>
                        <div class="space-y-1" v-if="currentMethod !== 1">
                            <Label for="Code">验证码</Label>
                            <div class="flex gap-2">
                                <Input id="code" v-model="signDetails.emailCode" class="flex" placeholder="请输入验证码" />
                                <Button :disabled="countDown > 0 || isSendingCode || !signDetails.email"
                                    @click="getEmailCode()" class="w-1/4">
                                    <span v-if="isSendingCode">发送中...</span>
                                    <span v-else>{{ countDown > 0 ? countDown + 's' : "发送验证码" }}</span>
                                </Button>
                            </div>
                        </div>
                    </CardContent>
                    <CardFooter>
                        <!-- 账号密码登录 -->
                        <Button v-if="currentMethod === 1" class="w-full"
                            :disabled="isLoading || !signDetails.username || !signDetails.password"
                            @click="SignInWithUsername()">
                            <span v-if="isLoading">{{ tab.buttonText }}中...</span>
                            <span v-else>{{ tab.buttonText }}</span>
                        </Button>
                        <!-- 邮箱验证码登录 -->
                        <Button v-else-if="currentMethod === 2" class="w-full"
                            :disabled="isLoading || !signDetails.email || !signDetails.emailCode"
                            @click="signInWithEmail()">
                            <span v-if="isLoading">{{ tab.buttonText }}中...</span>
                            <span v-else>{{ tab.buttonText }}</span>
                        </Button>
                        <!-- 注册 -->
                        <Button v-else class="w-full"
                            :disabled="isLoading || !signDetails.username || !signDetails.password || !signDetails.email || !signDetails.emailCode"
                            @click="signUp()">
                            <span v-if="isLoading">{{ tab.buttonText }}中...</span>
                            <span v-else>{{ tab.buttonText }}</span>
                        </Button>
                    </CardFooter>
                </Card>
            </TabsContent>
        </Tabs>
    </div>
</template>

<script setup lang="ts">
    import axios from '@/utils/myAxios'
    import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
    import { ref, watchEffect } from 'vue'
    import 'vue-sonner/style.css'
    import { Button } from "@/components/ui/button"
    import { Input } from "@/components/ui/input"
    import { Label } from "@/components/ui/label"
    import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
    import { useRouter } from 'vue-router'
    const router = useRouter()
    import { useUserStore } from '@/stores/UserStore'
    const userStore = useUserStore()


    const tabs = [
        {
            value: 'A',
            title: '自己人？快进来！',
            description: '输入您的账户（用户名），密码',
            buttonText: '登录'
        },
        {
            value: 'B',
            title: '自己人？快进来！',
            description: '输入您的邮箱号，验证码',
            buttonText: '登录'
        },
        {
            value: 'C',
            title: '没有号？整一个！',
            description: '填写您的账户（用户名），密码以及验证码',
            buttonText: '注册'
        }
    ]

    // 登录表单数据
    const signDetails = ref({
        username: "",
        password: "",
        email: "",
        emailCode: ""
    })

    const currentMethod = ref(1)

    // 改变功能就清空表单
    watchEffect(() => {
        if (currentMethod.value) {
            signDetails.value.username = ""
            signDetails.value.password = ""
            signDetails.value.email = ""
            signDetails.value.emailCode = ""
        }
    })

    // 当前登录的用户昵称

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

    // 登录注册加载
    const isLoading = ref(false)

    // 登陆后存进Pinia
    const setUser = (user: User) => {
        userStore.setUser(user)
    }

    const SignInWithUsername = async () => {
        isLoading.value = true
        const response = await axios.post("/api/user/user/sign-in/username", {
            username: signDetails.value.username,
            password: signDetails.value.password
        })

        user.value = response.data.data
        
        isLoading.value = false
        if (response.data.code === 1) {
            setUser(user.value)
            router.push('/');
        }
    }

    // 验证码相关
    const countDown = ref(0)
    const isSendingCode = ref(false)
    let timer: number | null = null

    // 发送验证码
    const getEmailCode = async () => {
        isSendingCode.value = true
        await axios.post('/api/user/user/email-code', {
            email: signDetails.value.email
        })

        countDown.value = 60
        timer = setInterval(() => {
            countDown.value--
            if (countDown.value <= 0 && timer) {
                clearInterval(timer)
                timer = null
            }
        }, 1000)

        isSendingCode.value = false
    }

    const signInWithEmail = async () => {
        isLoading.value = true
        const response = await axios.post("/api/user/user/sign-in/email", {
            email: signDetails.value.email,
            emailCode: signDetails.value.emailCode
        })

        user.value = response.data.data
        isLoading.value = false
        if (response.data.code === 1) {
            setUser(user.value)
            router.push('/home');
        }
    }

    const signUp = async () => {
        isLoading.value = true
        const response = await axios.post("/api/user/user/sign-up", {
            username: signDetails.value.username,
            password: signDetails.value.password,
            email: signDetails.value.email,
            emailCode: signDetails.value.emailCode
        })

        user.value = response.data.data
        isLoading.value = false
        if (response.data.code === 1) {
            setUser(user.value)
            router.push('/home');
        }
    }
</script>