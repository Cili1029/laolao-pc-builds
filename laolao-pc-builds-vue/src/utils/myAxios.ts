import axios from "axios";
import { toast } from "vue-sonner"
import router from '@/router'

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
                    router.push('/home')
                    msg = '该功能需要登录，请先登录'
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