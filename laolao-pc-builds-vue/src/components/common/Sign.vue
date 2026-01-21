<template>
    <div class="h-full w-full flex items-center justify-center bg-gray-100">
        <div class="relative h-[550px] w-[850px] rounded-2xl bg-white shadow-2xl overflow-hidden">
            <!-- 注册 -->
            <div
                class="absolute left-0 top-0 flex flex-col h-full w-1/2 justify-center p-10 transition-all duration-700 space-y-3">
                <h2 class="mb-4 text-3xl font-bold text-gray-800">创建账号</h2>
                <input type="text" placeholder="用户名" v-model="signUpDetails.username"
                    @input="validateField('signUp', 'username')" @compositionend="validateField('signUp', 'username')"
                    class="mb-3 w-full rounded bg-gray-100 px-4 py-3 text-sm outline-none focus:ring-2 focus:ring-orange-200" />
                <p v-if="formErrors.signUp.username" class="text-xs text-red-500">{{ formErrors.signUp.username }}</p>
                <input type="password" placeholder="密码" v-model="signUpDetails.password"
                    @input="validateField('signUp', 'password')" @compositionend="validateField('signUp', 'password')"
                    class="mb-3 w-full rounded bg-gray-100 px-4 py-3 text-sm outline-none focus:ring-2 focus:ring-orange-200" />
                <p v-if="formErrors.signUp.password" class="text-xs text-red-500">{{ formErrors.signUp.password }}</p>
                <input type="email" placeholder="Email" v-model="signUpDetails.email"
                    @input="validateField('signUp', 'email')"
                    class="mb-3 w-full rounded bg-gray-100 px-4 py-3 text-sm outline-none focus:ring-2 focus:ring-orange-200" />
                <p v-if="formErrors.signUp.email" class="text-xs text-red-500">{{ formErrors.signUp.email }}</p>
                <div class="flex gap-2 items-center">
                    <input type="text" placeholder="输入验证码" v-model="signUpDetails.emailCode"
                        class="w-5/7 rounded bg-gray-100 px-4 py-3 text-sm outline-none focus:ring-2 focus:ring-orange-200" />
                    <Button @click="!countDown && getEmailCode(signUpDetails.email)"
                        :disabled="countDown > 0 || isSendingCode || !signUpDetails.email"
                        class="w-2/7 rounded bg-orange-100 text-xs text-orange-500 hover:bg-orange-200">
                        {{ countDown > 0 ? countDown + 's' : "发送验证码" }}
                    </Button>
                </div>
                <Button @click="signUp()" :disabled="isLoading || !signUpIsValid || !signUpDetails.emailCode"
                    class="w-full rounded-full border border-orange-500 bg-transparent px-12 py-2 text-xs font-bold uppercase text-orange-500 transition-colors hover:bg-orange-500 hover:text-white">
                    注册
                </Button>
            </div>

            <!-- 登录 -->
            <div
                class="absolute right-0 top-0 flex flex-col h-full w-1/2 justify-center p-10 transition-all duration-700">
                <h2 class="mb-5 text-3xl font-bold text-gray-800">登录</h2>

                <div class="mb-4 flex gap-6 text-sm">
                    <p @click="SingInType = 'password'"
                        :class="SingInType === 'password' ? 'border-b-2 border-orange-500 font-bold text-orange-500' : 'text-gray-500 hover:text-gray-800'"
                        class="pb-1 transition-colors">
                        账号密码登录
                    </p>
                    <p @click="SingInType = 'email'"
                        :class="SingInType === 'email' ? 'border-b-2 border-orange-500 font-bold text-orange-500' : 'text-gray-500 hover:text-gray-800'"
                        class="pb-1 transition-colors">
                        邮箱验证码登录
                    </p>
                </div>

                <div class="w-full space-y-3">
                    <div v-if="SingInType === 'password'" class="space-y-3">
                        <input type="text" placeholder="账号" v-model="signInDetails.username"
                            @input="validateField('signIn', 'username')"
                            class="w-full rounded bg-gray-100 px-4 py-3 text-sm outline-none focus:ring-2 focus:ring-orange-200" />
                        <p v-if="formErrors.signIn.username" class="text-xs text-red-500">{{ formErrors.signIn.username
                            }}</p>
                        <input type="password" placeholder="密码" v-model="signInDetails.password"
                            @input="validateField('signIn', 'password')"
                            class="w-full rounded bg-gray-100 px-4 py-3 text-sm outline-none focus:ring-2 focus:ring-orange-200" />
                        <p v-if="formErrors.signIn.password" class="text-xs text-red-500">{{ formErrors.signIn.password
                            }}</p>
                        <Button @click="SignInWithUsername()" :disabled="isLoading || !signInWithPasswordIsValid"
                            class="w-full rounded-full border border-orange-500 bg-transparent px-12 py-2 text-xs font-bold uppercase text-orange-500 transition-colors hover:bg-orange-500 hover:text-white">
                            登录
                        </Button>
                    </div>
                    <div v-else class="space-y-3">
                        <input type="tel" placeholder="邮箱" v-model="signInDetails.email"
                            @input="validateField('signIn', 'email')"
                            class="w-full rounded bg-gray-100 px-4 py-3 text-sm outline-none focus:ring-2 focus:ring-orange-200" />
                        <p v-if="formErrors.signIn.email" class="text-xs text-red-500">{{ formErrors.signIn.email }}</p>
                        <div class="flex gap-2 items-center">
                            <input type="text" placeholder="输入验证码" v-model="signInDetails.emailCode"
                                class="w-5/7 rounded bg-gray-100 px-4 py-3 text-sm outline-none focus:ring-2 focus:ring-orange-200" />
                            <Button @click="!countDown && getEmailCode(signInDetails.email)"
                                :disabled="countDown > 0 || isSendingCode || !signInDetails.email || formErrors.signIn.email.length > 0"
                                class="w-2/7 rounded bg-orange-100 text-xs text-orange-500 hover:bg-orange-200"
                                :class="{ 'cursor-not-allowed': countDown }">
                                {{ countDown > 0 ? countDown + 's' : "发送验证码" }}
                            </Button>
                        </div>
                        <Button @click="signInWithEmail" :disabled="isLoading || !signInWithEmailIsValid"
                            class="w-full rounded-full border border-orange-500 bg-transparent px-12 py-2 text-xs font-bold uppercase text-orange-500 transition-colors hover:bg-orange-500 hover:text-white">
                            登录
                        </Button>
                    </div>
                </div>
            </div>

            <!-- 遮罩 -->
            <div class="absolute left-0 top-0 flex h-full w-1/2 flex-col items-center justify-center bg-gradient-to-r from-orange-500 to-red-500 text-white transition-transform duration-700 ease-in-out"
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
    import axios from '@/utils/myAxios'
    import { Button } from "@/components/ui/button"
    import { ref, computed, reactive, onUnmounted } from 'vue'
    import { useRouter } from 'vue-router'
    const router = useRouter()
    import { useUserStore } from '@/stores/UserStore'
    const userStore = useUserStore()

    // true 为登录，false 为注册
    const Sign = ref(true)

    // 卸载组件清空time
    onUnmounted(() => {
        if (timer) {
            clearInterval(timer);
            timer = null;
        }
    });

    // 统一格式验证
    const USERNAME_REG = /^[a-zA-Z0-9]+$/
    const PASSWORD_INVALID_REG = /[\u4e00-\u9fa5\u3000-\u303f\uff00-\uffef]/
    const EMAIL_REG = /^[^\s@]+@[^\s@]+\.[^\s@]+$/

    type ValidatedField = 'username' | 'password' | 'email'

    const formErrors = reactive<Record<'signUp' | 'signIn', Record<ValidatedField, string>>>({
        signUp: { username: '', password: '', email: '' },
        signIn: { username: '', password: '', email: '' }
    })

    const getValidationMessage = (field: ValidatedField, value: string) => {
        if (!value) return ''
        if (field === 'username' && !USERNAME_REG.test(value)) return '账户为数字或字母组合！'
        if (field === 'password' && PASSWORD_INVALID_REG.test(value)) return '密码不包含中文字符！'
        if (field === 'email' && !EMAIL_REG.test(value)) return '邮箱格式不正确！'
        return ''
    }

    const validateField = (form: 'signUp' | 'signIn', field: ValidatedField) => {
        const formData = form === 'signUp' ? signUpDetails.value : signInDetails.value
        formErrors[form][field] = getValidationMessage(field, formData[field] ?? '')
    }

    const signUpIsValid = computed(() => {
        const { username, password, email } = signUpDetails.value
        return username && password && email &&
            !getValidationMessage('username', username) &&
            !getValidationMessage('password', password) &&
            !getValidationMessage('email', email)
    })
    const signInWithPasswordIsValid = computed(() => {
        const { username, password } = signInDetails.value
        return username && password &&
            !getValidationMessage('username', username) &&
            !getValidationMessage('password', password)
    })
    const signInWithEmailIsValid = computed(() => {
        const { email, emailCode } = signInDetails.value
        return email && emailCode &&
            !getValidationMessage('email', email)
    })

    // 验证码相关
    const countDown = ref(0)
    const isSendingCode = ref(false)
    let timer: number | null = null

    // 发送验证码
    const getEmailCode = async (email: string) => {
        try {
            isSendingCode.value = true
            await axios.post('/api/common/user/email-code', {
                email: email
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
            console.log(error)
        } finally {
            isSendingCode.value = false
        }
    }

    interface SignDetails {
        username: string,
        password: string,
        email: string,
        emailCode: string
    }

    // 登录注册加载
    const isLoading = ref(false)

    // 注册
    // 注册表单数据
    const signUpDetails = ref<SignDetails>({
        username: '',
        password: '',
        email: '',
        emailCode: ''
    })

    // 注册
    const signUp = async () => {
        try {
            isLoading.value = true
            const response = await axios.post("/api/common/user/sign-up", {
                username: signUpDetails.value!.username,
                password: signUpDetails.value!.password,
                email: signUpDetails.value!.email,
                emailCode: signUpDetails.value!.emailCode
            })

            user.value = response.data.data
            if (response.data.code === 1) {
                setUser(user.value)
                router.replace('/home');
            }
        } finally {
            isLoading.value = false
        }
    }

    // 登录
    // 登录表单数据
    const signInDetails = ref<SignDetails>({
        username: '',
        password: '',
        email: '',
        emailCode: ''
    })

    // 登录方式：'password' (密码) 或 'email' (验证码)
    const SingInType = ref('password')

    const SignInWithUsername = async () => {
        try {
            isLoading.value = true
            const response = await axios.post("/api/common/user/sign-in/username", {
                username: signInDetails.value!.username,
                password: signInDetails.value!.password
            })
            user.value = response.data.data
            if (response.data.code === 1) {
                setUser(user.value)
                router.replace('/');
            }
        } finally {
            isLoading.value = false
        }

    }

    const signInWithEmail = async () => {
        try {
            isLoading.value = true
            const response = await axios.post("/api/common/user/sign-in/email", {
                email: signInDetails.value!.email,
                emailCode: signInDetails.value!.emailCode
            })

            user.value = response.data.data

            if (response.data.code === 1) {
                setUser(user.value)
                router.replace('/home');
            }
        } finally {
            isLoading.value = false
        }
    }


    // 当前登录的用户昵称
    interface User {
        id: number
        avatar: string
        username: string
        name: string
        admin: number
    }

    const user = ref<User>({
        id: 0,
        avatar: '',
        username: '',
        name: '',
        admin: 0
    })

    // 登陆后存进Pinia
    const setUser = (user: User) => {
        userStore.setUser(user)
    }
</script>