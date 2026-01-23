import { defineStore } from 'pinia'
import { ref } from 'vue'
import { toast } from 'vue-sonner'
import { useCommonStore } from '@/stores/CommonStore'

export const useWebSocketStore = defineStore('websocket', () => {
    const commonStore = useCommonStore()
    const socket = ref<WebSocket | null>(null)
    const isConnected = ref(false)
    let heartbeatTimer: any = null
    let reconnectTimer: any = null
    let isIntentionalDisconnect = false // 标记是否为主动断开

    // location.host 会自动适配 localhost:5173
    const WS_URL = `ws://${location.host}/ws/notify`

    // 1. 初始化连接
    const connect = () => {
        if (socket.value?.readyState === WebSocket.OPEN) return

        socket.value = new WebSocket(WS_URL)

        socket.value.onopen = () => {
            console.log('✅ WebSocket 连接成功')
            isConnected.value = true
            isIntentionalDisconnect = false // 连接成功时重置标记
            startHeartbeat()
        }

        socket.value.onmessage = (event) => {
            handleMessage(event.data)
        }

        socket.value.onclose = () => {
            console.log('❌ WebSocket 连接断开')
            isConnected.value = false
            stopHeartbeat()

            // 如果是主动断开（退出登录），不重连
            if (isIntentionalDisconnect) {
                isIntentionalDisconnect = false // 重置标记
                return
            }

            // 只有意外断开时才重连
            clearTimeout(reconnectTimer)
            reconnectTimer = setTimeout(() => {
                console.log('🔄 尝试重连 WebSocket...')
                connect()
            }, 5000)
        }

        socket.value.onerror = (error) => {
            console.error('WebSocket 错误:', error)
            socket.value?.close()
        }
    }

    // 2. 处理消息 (核心业务逻辑)
    const handleMessage = (msg: string) => {
        if (msg === 'pong') return // 心跳回执，忽略

        try {
            const data = JSON.parse(msg)
            switch (data.type) {
                case 'new_order':
                    // 管理员收到新订单
                    toast('新订单', {
                        description: data.data,
                        action: {
                            label: '查看',
                            onClick: () => console.log('Undo'),
                        },
                    })
                    break

                // case 'order_shipped':
                //     // 用户收到发货
                //     toast.success('发货提醒', {
                //         description: data.content,
                //     })
                //     break

                case 'online_users':
                    commonStore.onlineUser = data.data
                    break

                default:
                    console.log('收到未知消息:', data)
            }
        } catch (e) {
            console.log('收到非JSON消息:', msg)
        }
    }

    // 3. 心跳检测 (防止 Nginx/防火墙 60s 自动断开)
    const startHeartbeat = () => {
        stopHeartbeat()
        heartbeatTimer = setInterval(() => {
            if (socket.value?.readyState === WebSocket.OPEN) {
                socket.value.send('ping') // 发送心跳包
            }
        }, 30000) // 30秒一次
    }

    const stopHeartbeat = () => {
        if (heartbeatTimer) clearInterval(heartbeatTimer)
    }

    // 4. 主动断开 (退出登录时调用)
    const disconnect = () => {
        isIntentionalDisconnect = true // 标记为主动断开
        if (socket.value) {
            socket.value.close()
            socket.value = null
        }
        stopHeartbeat()
        clearTimeout(reconnectTimer)
    }

    return {
        isConnected,
        connect,
        disconnect
    }
})