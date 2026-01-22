<template>
    <div class="h-full w-full flex items-center justify-center bg-gray-100 font-sans">
        <div class="relative h-[550px] w-[850px] rounded-2xl bg-white shadow-2xl overflow-hidden">

            <!-- ================= 注册 (Sign Up) ================= -->
            <div class="absolute left-0 top-0 flex flex-col h-full w-1/2 justify-center p-10 transition-all duration-700 space-y-3"
                :class="Sign ? 'opacity-0 z-0' : 'opacity-100 z-10'">
                <h2 class="mb-4 text-3xl font-bold text-gray-800">创建账号</h2>

                <!-- 用户名 -->
                <div class="relative">
                    <input type="text" placeholder="用户名" v-model="suUsername" v-bind="suUserAttrs"
                        class="w-full rounded bg-gray-100 px-4 py-3 text-sm outline-none transition-all focus:ring-2"
                        :class="suUserErr ? 'ring-red-500' : 'focus:ring-orange-200'" />
                    <span v-show="suUsername" class="absolute right-3 top-3 text-xs font-bold"
                        :class="!suUserErr ? 'text-green-500' : 'text-red-500'">
                        {{ !suUserErr ? 'true' : 'false' }}
                    </span>
                    <p v-if="suUserErr" class="text-[10px] text-red-500 mt-1">{{ suUserErr }}</p>
                </div>

                <!-- 密码 -->
                <div class="relative">
                    <input type="password" placeholder="密码" v-model="suPassword" v-bind="suPassAttrs"
                        class="w-full rounded bg-gray-100 px-4 py-3 text-sm outline-none focus:ring-2"
                        :class="suPassErr ? 'ring-red-500' : 'focus:ring-orange-200'" />
                    <span v-show="suPassword" class="absolute right-3 top-3 text-xs font-bold"
                        :class="!suPassErr ? 'text-green-500' : 'text-red-500'">
                        {{ !suPassErr ? 'true' : 'false' }}
                    </span>
                    <p v-if="suPassErr" class="text-[10px] text-red-500 mt-1">{{ suPassErr }}</p>
                </div>

                <!-- 邮箱 -->
                <div class="relative">
                    <input type="email" placeholder="Email" v-model="suEmail" v-bind="suEmailAttrs"
                        class="w-full rounded bg-gray-100 px-4 py-3 text-sm outline-none focus:ring-2"
                        :class="suEmailErr ? 'ring-red-500' : 'focus:ring-orange-200'" />
                    <span v-show="suEmail" class="absolute right-3 top-3 text-xs font-bold"
                        :class="!suEmailErr ? 'text-green-500' : 'text-red-500'">
                        {{ !suEmailErr ? 'true' : 'false' }}
                    </span>
                    <p v-if="suEmailErr" class="text-[10px] text-red-500 mt-1">{{ suEmailErr }}</p>
                </div>

                <!-- 验证码 -->
                <div>
                    <div class="flex gap-2 items-center">
                        <input type="text" placeholder="输入验证码" v-model="suEmailCode" v-bind="suCodeAttrs"
                            class="w-5/7 rounded bg-gray-100 px-4 py-3 text-sm outline-none focus:ring-2 focus:ring-orange-200" />
                        <Button @click="!countDown && getEmailCode(suEmail)"
                            :disabled="countDown > 0 || isSendingCode || !suEmail || !!suEmailErr"
                            class="w-2/7 rounded bg-orange-100 text-xs text-orange-500 hover:bg-orange-200">
                            {{ countDown > 0 ? countDown + 's' : "发送验证码" }}
                        </Button>
                    </div>
                    <p v-if="suCodeErr" class="text-[10px] text-red-500 mt-1">{{ suCodeErr }}</p>
                </div>

                <Button @click="handleSignUp"
                    :disabled="isLoading || !suUsername || !suPassword || !suEmail || !suEmailCode || suUserErr || suPassErr || suEmailErr || suCodeErr"
                    class="w-full rounded-full border border-orange-500 bg-transparent px-12 py-2 text-xs font-bold uppercase text-orange-500 transition-colors hover:bg-orange-500 hover:text-white">
                    注册
                </Button>
            </div>

            <!-- ================= 登录 (Sign In) ================= -->
            <div class="absolute right-0 top-0 flex flex-col h-full w-1/2 justify-center p-10 transition-all duration-700"
                :class="!Sign ? 'opacity-0 z-0' : 'opacity-100 z-10'">
                <h2 class="mb-5 text-3xl font-bold text-gray-800">登录</h2>

                <div class="mb-4 flex gap-6 text-sm">
                    <p @click="SingInType = 'password'"
                        :class="SingInType === 'password' ? 'border-b-2 border-orange-500 font-bold text-orange-500' : 'text-gray-500 hover:text-gray-800'"
                        class="pb-1 cursor-pointer transition-colors">
                        账号密码登录
                    </p>
                    <p @click="SingInType = 'email'"
                        :class="SingInType === 'email' ? 'border-b-2 border-orange-500 font-bold text-orange-500' : 'text-gray-500 hover:text-gray-800'"
                        class="pb-1 cursor-pointer transition-colors">
                        邮箱验证码登录
                    </p>
                </div>

                <!-- 登录方式切换 -->
                <div class="w-full space-y-3">
                    <!-- 账号登录 -->
                    <div v-if="SingInType === 'password'" class="space-y-3">
                        <div>
                            <input type="text" placeholder="账号" v-model="siUsername" v-bind="siUserAttrs"
                                class="w-full rounded bg-gray-100 px-4 py-3 text-sm outline-none focus:ring-2 focus:ring-orange-200" />
                            <p v-if="siUserErr" class="text-xs text-red-500 pt-0.5">{{ siUserErr }}</p>
                        </div>

                        <div>
                            <input type="password" placeholder="密码" v-model="siPassword" v-bind="siPassAttrs"
                                class="w-full rounded bg-gray-100 px-4 py-3 text-sm outline-none focus:ring-2 focus:ring-orange-200" />
                            <p v-if="siPassErr" class="text-xs text-red-500 pt-0.5">{{ siPassErr }}</p>
                        </div>

                        <Button @click="handleSignIn"
                            :disabled="!siUsername || !siPassword || isLoading || siUserErr || siPassErr"
                            class="w-full rounded-full border border-orange-500 bg-transparent px-12 py-2 text-xs font-bold uppercase text-orange-500 hover:bg-orange-500 hover:text-white">
                            登录
                        </Button>
                    </div>

                    <!-- 邮箱登录 -->
                    <div v-else class="space-y-3">
                        <div>
                            <input type="email" placeholder="邮箱" v-model="siEmail" v-bind="siEmailAttrs"
                                class="w-full rounded bg-gray-100 px-4 py-3 text-sm outline-none focus:ring-2 focus:ring-orange-200" />
                            <p v-if="siEmailErr" class="text-xs text-red-500 pt-0.5">{{ siEmailErr }}</p>
                        </div>

                        <div>
                            <div class="flex gap-2 items-center">
                                <input type="text" placeholder="输入验证码" v-model="siEmailCode" v-bind="siCodeAttrs"
                                    class="w-5/7 rounded bg-gray-100 px-4 py-3 text-sm outline-none focus:ring-2 focus:ring-orange-200" />
                                <Button @click="!countDown && getEmailCode(siEmail)"
                                    :disabled="countDown > 0 || isSendingCode || !siEmail || !!siEmailErr"
                                    class="w-2/7 rounded bg-orange-100 text-xs text-orange-500 hover:bg-orange-200">
                                    {{ countDown > 0 ? countDown + 's' : "发送验证码" }}
                                </Button>
                            </div>
                            <p v-if="siCodeErr" class="text-xs text-red-500 pt-0.5">{{ siCodeErr }}</p>
                        </div>
                        <Button @click="handleSignIn"
                            :disabled="!siEmail || !siEmailCode || isLoading || siEmailErr || siCodeErr"
                            class="w-full rounded-full border border-orange-500 bg-transparent px-12 py-2 text-xs font-bold uppercase text-orange-500 hover:bg-orange-500 hover:text-white">
                            登录
                        </Button>
                    </div>
                </div>
            </div>

            <!-- 遮罩 (左右切换面板) -->
            <div class="absolute left-0 top-0 flex h-full w-1/2 flex-col items-center justify-center bg-gradient-to-r from-orange-500 to-red-500 text-white transition-transform duration-700 ease-in-out z-20"
                :class="Sign ? 'translate-x-0' : 'translate-x-full'">
                <div class="flex flex-col items-center px-10 text-center">
                    <h2 class="mb-4 text-3xl font-bold">{{ Sign ? "欢迎回来!" : "Hello, 朋友!" }}</h2>
                    <p class="mb-8 text-sm">{{ Sign ? "请登录你的账号以继续连接" : "输入你的个人信息，开始我们的旅程" }}</p>
                    <button @click="Sign = !Sign"
                        class="rounded-full border border-white px-12 py-2 text-xs font-bold uppercase transition-colors hover:bg-white hover:text-orange-500">
                        {{ Sign ? "去注册" : "去登录" }}
                    </button>
                </div>
            </div>

        </div>
    </div>
</template>

<script setup lang="ts">
    import { ref, onUnmounted } from 'vue'
    import { useRouter } from 'vue-router'
    import { useForm, useField } from '@vorms/core'
    import axios from '@/utils/myAxios'
    import { Button } from "@/components/ui/button"
    import { useUserStore } from '@/stores/UserStore'

    const router = useRouter()
    const userStore = useUserStore()

    // 状态控制
    const Sign = ref(true) // true为登录，false为注册
    const SingInType = ref('password')
    const isLoading = ref(false)

    // 正则表达式
    const USERNAME_REG = /^[a-zA-Z0-9]+$/
    const PASSWORD_INVALID_REG = /[\u4e00-\u9fa5\u3000-\u303f\uff00-\uffef]/
    const EMAIL_REG = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    const CODE_REG = /^\d{6}$/;;

    // 注册表单
    const { handleSubmit: handleSignUp } = useForm({
        initialValues: { suUsername: '', suPassword: '', suEmail: '', suEmailCode: '' },
        validateMode: 'input',
        validateOnMounted: true,
        onSubmit: async (values) => {
            try {
                isLoading.value = true
                // 重命名
                const payload = { username: values.suUsername, password: values.suPassword, email: values.suEmail, emailCode: values.suEmailCode }
                const res = await axios.post("/api/common/user/sign-up", payload)
                if (res.data.code === 1) {
                    userStore.setUser(res.data.data)
                    router.replace('/home')
                }
            } finally { isLoading.value = false }
        }
    })

    // 注册字段定义
    const { value: suUsername, error: suUserErr, attrs: suUserAttrs } = useField<string>('suUsername', {
        validate: (val) => (!val || USERNAME_REG.test(val) ? undefined : '账户为数字或字母组合！')
    })
    const { value: suPassword, error: suPassErr, attrs: suPassAttrs } = useField<string>('suPassword', {
        validate: (val) => (!val || !PASSWORD_INVALID_REG.test(val) ? undefined : '密码不包含中文字符！')
    })
    const { value: suEmail, error: suEmailErr, attrs: suEmailAttrs } = useField<string>('suEmail', {
        validate: (val) => (!val || EMAIL_REG.test(val) ? undefined : '邮箱格式不正确！')
    })
    const { value: suEmailCode, error: suCodeErr, attrs: suCodeAttrs } = useField<string>('suEmailCode', {
        validate: (val) => (!val || CODE_REG.test(val) ? undefined : '验证码为六位数数字！')
    })

    // 登录表单
    const { handleSubmit: handleSignIn } = useForm({
        initialValues: { siUsername: '', siPassword: '', siEmail: '', siEmailCode: '' },
        validateMode: 'input',
        validateOnMounted: true,
        onSubmit: async (values) => {
            try {
                isLoading.value = true
                const url = SingInType.value === 'password'
                    ? "/api/common/user/sign-in/username"
                    : "/api/common/user/sign-in/email";

                // 重新命名
                const payload = SingInType.value === 'password'
                    ? { username: values.siUsername, password: values.siPassword }
                    : { email: values.siEmail, emailCode: values.siEmailCode };

                const res = await axios.post(url, payload);
                if (res.data.code === 1) {
                    userStore.setUser(res.data.data)
                    router.replace('/')
                }
            } finally { isLoading.value = false }
        }
    })

    // 登录字段定义
    const { value: siUsername, error: siUserErr, attrs: siUserAttrs } = useField<string>('siUsername', {
        validate: (val) => (!val || USERNAME_REG.test(val) ? undefined : '账户为数字或字母组合！')
    })
    const { value: siPassword, error: siPassErr, attrs: siPassAttrs } = useField<string>('siPassword', {
        validate: (val) => (!val || !PASSWORD_INVALID_REG.test(val) ? undefined : '密码不包含中文字符！')
    })
    const { value: siEmail, error: siEmailErr, attrs: siEmailAttrs } = useField<string>('siEmail', {
        validate: (val) => (!val || EMAIL_REG.test(val) ? undefined : '邮箱错误')
    })
    const { value: siEmailCode, error: siCodeErr, attrs: siCodeAttrs } = useField<string>('siEmailCode', {
        validate: (val) => (!val || CODE_REG.test(val) ? undefined : '验证码为六位数数字！')
    })

    // 验证码倒计时逻辑
    const countDown = ref(0)
    const isSendingCode = ref(false)
    let timer: any = null

    const getEmailCode = async (email: string) => {
        if (!email || isSendingCode.value) return
        try {
            isSendingCode.value = true
            await axios.post('/api/common/user/email-code', { email })
            countDown.value = 60
            timer = setInterval(() => {
                countDown.value--
                if (countDown.value <= 0) {
                    clearInterval(timer)
                    timer = null
                }
            }, 1000)
        } catch (err) {
            console.error(err)
        } finally {
            isSendingCode.value = false
        }
    }

    onUnmounted(() => { if (timer) clearInterval(timer) })
</script>