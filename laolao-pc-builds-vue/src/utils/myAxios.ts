import axios from "axios";
import { toast } from "vue-sonner"
import router from '@/router'
import { useUserStore } from '@/stores/UserStore'

const request = axios.create({
    baseURL: '',
    withCredentials: true,
})

// 请求拦截器
request.interceptors.request.use(
    (config) => {
        return config
    },
    (error) => {
        return Promise.reject(error)
    }
)

// 响应拦截器
request.interceptors.response.use(
    response => {
        // 如果业务代码为0（失败），显示消息并结束
        if (response.data.msg != null) {
            toast("嗨！", {
                description: response.data.msg,
                action: {
                    label: '我知道了',
                },
            })
        }
        return response
    },
    error => {
        let msg = ''
        // HTTP状态码错误
        if (error.response) {
            const status = error.response.status
            switch (status) {
                case 401:
                    // 跳转到登录页
                    router.replace('/sign')
                    msg = '登录已过期，请重新登录'
                    // 清空现有user数据
                    const userStore = useUserStore()
                    userStore.clearUser()
                    break
                case 403:
                    // 跳转到用户首页
                    router.replace('/')
                    msg = '非管理员，无权限！'
                    break
                default:
                    msg = '未知错误'
            }
        }

        toast("嗨！", {
            description: msg,
            action: {
                label: '我知道了',
            },
        })
        return Promise.reject(error)
    })

export default request