<template>
    <div class="h-full overflow-y-auto scrollbar-edge p-4">
        <div v-if="orders.length">
            <Dialog>
                <DialogTrigger as-child>
                    <div
                        class="rounded-3xl border border-slate-100 bg-white/90 p-4 shadow-xl backdrop-blur transition hover:shadow-2xl">
                        <div class="space-y-4">
                            <div v-for="order in orders" :key="order.number"
                                class="rounded-xl border border-slate-100 bg-gradient-to-br from-white via-white to-slate-50/60 px-5 py-2 shadow-sm transition hover:-translate-y-0.5 hover:border-orange-100 hover:shadow-lg cursor-pointer"
                                @click="showDetail(order.number, order.status)">
                                <div class="mb-4 flex flex-wrap items-center justify-between gap-3">
                                    <div>
                                        <p class="text-xs uppercase tracking-[0.3em] text-slate-400">订单号</p>
                                        <p class="font-mono text-lg font-semibold text-slate-800">{{ order.number }}</p>
                                    </div>
                                    <span class="rounded-full px-4 py-1 text-sm font-semibold"
                                        :class="order.status === 1 ? 'bg-orange-100 text-orange-600' :
                                            order.status === 5 ? 'bg-emerald-100 text-emerald-600' :
                                                order.status === 6 ? 'bg-slate-100 text-slate-500' : 'bg-sky-100 text-sky-600'">
                                        {{ getOrderStatus(order.status) }}
                                    </span>
                                </div>
                                <div class="flex items-center gap-4">
                                    <img :src="order.image"
                                        class="h-20 w-20 flex-shrink-0 rounded-xl object-cover shadow-inner" />
                                    <div class="flex-1 min-w-0">
                                        <h3 class="text-base font-semibold text-slate-900 line-clamp-1">{{ order.name }}
                                        </h3>
                                        <p class="text-sm text-slate-500 mt-1" v-if="order.productCount > 1">
                                            等{{ order.productCount - 1 }}个商品
                                        </p>
                                    </div>
                                    <div class="text-right">
                                        <p class="text-xs uppercase tracking-[0.3em] text-slate-400">实付款</p>
                                        <p class="text-2xl font-black text-slate-900">￥{{ order.finalAmount }}</p>
                                    </div>
                                </div>
                                <div class="mt-4 flex flex-wrap items-center justify-end gap-2" @click.stop>
                                    <AlertDialog>
                                        <AlertDialogTrigger as-child>
                                            <Button v-if="order.status == 1 || order.status == 2"
                                                class="rounded-full border border-orange-200 bg-orange-50 text-orange-600 hover:bg-orange-100">
                                                取消订单
                                            </Button>
                                        </AlertDialogTrigger>
                                        <AlertDialogContent class="space-y-3">
                                            <AlertDialogHeader>
                                                <AlertDialogTitle>确定要取消该订单吗？</AlertDialogTitle>
                                                <AlertDialogDescription>
                                                    操作一旦完成无法撤回，请谨慎选择
                                                </AlertDialogDescription>
                                                <div class="flex items-center">
                                                    <label class="w-16 text-sm font-medium text-slate-600">原因</label>
                                                    <Select v-model="reason">
                                                        <SelectTrigger class="w-[220px] rounded-xl">
                                                            <SelectValue placeholder="选择一个原因" />
                                                        </SelectTrigger>
                                                        <SelectContent>
                                                            <SelectGroup>
                                                                <SelectItem value="没手感">
                                                                    没手感
                                                                </SelectItem>
                                                                <SelectItem value="刚睡醒">
                                                                    刚睡醒
                                                                </SelectItem>
                                                                <SelectItem value="对面开挂了">
                                                                    对面开挂了
                                                                </SelectItem>
                                                                <SelectItem value="天气太冷了">
                                                                    天气太冷了
                                                                </SelectItem>
                                                                <SelectItem value="电量低提示了">
                                                                    电量低提示了
                                                                </SelectItem>
                                                                <SelectItem value="我妈叫我了">
                                                                    我妈叫我了
                                                                </SelectItem>
                                                                <SelectItem value="皮肤手感不行">
                                                                    皮肤手感不行
                                                                </SelectItem>
                                                                <SelectItem value="刚刚黑客入侵了">
                                                                    刚刚黑客入侵了
                                                                </SelectItem>
                                                                <SelectItem value="我在上课">
                                                                    我在上课
                                                                </SelectItem>
                                                                <SelectItem value="边吃饭边打的">
                                                                    边吃饭边打的
                                                                </SelectItem>
                                                            </SelectGroup>
                                                        </SelectContent>
                                                    </Select>
                                                </div>
                                            </AlertDialogHeader>
                                            <AlertDialogFooter>
                                                <AlertDialogCancel>算了</AlertDialogCancel>
                                                <AlertDialogAction @click.stop="cancel(order.number)">取消
                                                </AlertDialogAction>
                                            </AlertDialogFooter>
                                        </AlertDialogContent>
                                    </AlertDialog>
                                    <AlertDialog v-if="order.status == 3 || order.status == 4">
                                        <AlertDialogTrigger as-child>
                                            <Button class="rounded-full bg-slate-900 text-white hover:bg-slate-800">
                                                确认收货
                                            </Button>
                                        </AlertDialogTrigger>
                                        <AlertDialogContent>
                                            <AlertDialogHeader>
                                                <AlertDialogTitle>确定收货吗?</AlertDialogTitle>
                                                <AlertDialogDescription>
                                                    1123
                                                </AlertDialogDescription>
                                            </AlertDialogHeader>
                                            <AlertDialogFooter>
                                                <AlertDialogCancel>取消</AlertDialogCancel>
                                                <AlertDialogAction @click="receive(order.number)">确认收货
                                                </AlertDialogAction>
                                            </AlertDialogFooter>
                                        </AlertDialogContent>
                                    </AlertDialog>
                                </div>
                            </div>
                        </div>
                    </div>
                </DialogTrigger>
                <DialogContent class="sm:max-w-[900px] p-0 gap-0 overflow-hidden flex flex-col max-h-[90vh]">
                    <DialogHeader class="px-6 py-4 border-b border-gray-100 bg-gray-50/50 flex-shrink-0">
                        <div class="flex items-center justify-between mr-6">
                            <DialogTitle class="text-lg font-bold text-gray-800">订单详情</DialogTitle>
                            <span class="text-sm font-mono text-gray-400">{{ detail?.number }}</span>
                        </div>
                        <DialogDescription class="hidden"></DialogDescription>
                    </DialogHeader>

                    <div class="flex flex-col md:flex-row flex-1 overflow-hidden">
                        <!-- 左侧：商品列表 (可滚动) -->
                        <div
                            class="w-full md:w-7/12 bg-gray-50/30 p-6 overflow-y-auto scrollbar-edge border-r border-gray-100 h-[300px] md:h-auto">
                            <div class="space-y-3">
                                <div v-for="product in detail?.products" :key="detail?.number"
                                    class="bg-white rounded-lg p-3 flex gap-3 shadow-sm border border-gray-100">
                                    <img :src="product.image" class="w-16 h-16 object-cover rounded bg-gray-50" />
                                    <div class="flex-1 min-w-0 flex flex-col justify-between py-0.5">
                                        <h3 class="font-medium text-gray-900 text-sm line-clamp-1">{{ product.name }}
                                        </h3>
                                        <div class="flex justify-between items-end">
                                            <span class="text-xs text-gray-500">{{ product.variantName }}</span>
                                            <div class="text-right">
                                                <div class="font-bold text-sm">￥{{ product.price }}</div>
                                                <div class="text-xs text-gray-400">x{{ product.quantity }}</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- 右侧：信息 (可滚动) -->
                        <div class="w-full md:w-5/12 bg-white p-6 overflow-y-auto scrollbar-edge h-[400px] md:h-auto">
                            <div class="space-y-6">
                                <div v-if="detail?.trackingNo"
                                    class="bg-blue-50/50 px-4 py-2 my-2 rounded-lg border border-blue-50 space-y-2">
                                    <div class="flex justify-between text-xs text-gray-500">
                                        <span>订单号</span>
                                        <span>{{ detail.trackingNo }}</span>
                                    </div>
                                </div>
                                <!-- 金额块 -->
                                <div class="bg-blue-50/50 p-4 rounded-lg border border-blue-50 space-y-2">
                                    <div class="flex justify-between text-xs text-gray-500">
                                        <span>商品总额</span>
                                        <span>￥{{ detail?.originalAmount ?? 0 }}</span>
                                    </div>
                                    <div v-if="detail?.status != 1 && detail?.status != 5"
                                        class="flex justify-between text-xs text-gray-500">
                                        <span>优惠</span>
                                        <span class="text-red-500">-￥{{ detail?.discountAmount ?? 0 }}</span>
                                    </div>
                                    <div class="flex justify-between items-center pt-2 border-t border-blue-100">
                                        <span class="font-bold text-gray-700 text-sm">实付款</span>
                                        <span class="font-bold text-xl text-blue-600">￥{{ (detail?.originalAmount ?? 0)
                                            -
                                            (detail?.discountAmount ?? 0) }}</span>
                                    </div>
                                </div>

                                <!-- 配送信息 -->
                                <div class="space-y-3 text-sm">
                                    <div v-if="detail?.consignee" class="flex gap-3">
                                        <div
                                            class="flex-shrink-0 w-6 h-6 rounded-full bg-gray-100 flex items-center justify-center text-gray-500 icon-[lucide--map-pin] text-xs">
                                            📍</div>
                                        <div>
                                            <div class="font-bold text-gray-800">
                                                {{ detail?.consignee }} <span class="text-gray-400 font-normal ml-1">{{
                                                    detail?.phone
                                                }}</span>
                                            </div>
                                            <div class="text-gray-500 text-xs mt-0.5 leading-relaxed">{{ detail?.address
                                            }}</div>
                                        </div>
                                    </div>
                                </div>

                                <!-- 时间线及原因 -->
                                <div class="border-t border-gray-100 pt-4 space-y-2 text-xs text-gray-500">
                                    <p v-if="detail?.createdAt" class="flex justify-between">
                                        <span>创建时间</span><span>{{
                                            dayjs(detail?.createdAt).format('YYYY-MM-DD HH:mm:ss') }}</span>
                                    </p>
                                    <p v-if="detail?.checkoutTime" class="flex justify-between">
                                        <span>支付时间</span><span>{{
                                            dayjs(detail?.checkoutTime).format('YYYY-MM-DD HH:mm:ss') }}</span>
                                    </p>
                                    <p v-if="detail?.shipTime" class="flex justify-between">
                                        <span>发货时间</span><span>{{
                                            dayjs(detail?.shipTime).format('YYYY-MM-DD HH:mm:ss') }}</span>
                                    </p>
                                    <p v-if="detail?.arrivalTime" class="flex justify-between">
                                        <span>到货时间</span><span>{{
                                            dayjs(detail?.arrivalTime).format('YYYY-MM-DD HH:mm:ss') }}</span>
                                    </p>
                                    <p v-if="detail?.receiveTime" class="flex justify-between">
                                        <span>完成时间</span>
                                        <span>{{ dayjs(detail?.receiveTime).format('YYYY-MM-DD HH:mm:ss') }}</span>
                                    </p>
                                    <p v-if="detail?.cancelTime" class="flex justify-between text-red-500">
                                        <span>
                                            {{ detail?.cancelReason ? "取消时间" : "拒绝时间" }}
                                        </span>
                                        <span>{{ dayjs(detail?.cancelTime).format('YYYY-MM-DD HH:mm:ss') }}</span>
                                    </p>
                                    <p v-if="detail?.cancelReason" class="flex justify-between text-red-500">
                                        <span>取消原因</span>
                                        <span>{{ detail.cancelReason }}</span>
                                    </p>
                                    <p v-if="detail?.rejectionReason" class="flex justify-between text-red-500">
                                        <span>拒绝原因</span>
                                        <span>{{ detail.rejectionReason }}</span>
                                    </p>
                                </div>
                            </div>

                            <div class="mt-6">
                                <DialogClose as-child>
                                    <Button variant="secondary" class="w-full">关闭详情</Button>
                                </DialogClose>
                            </div>
                        </div>
                    </div>
                </DialogContent>
            </Dialog>
        </div>
        <div v-else class="flex justify-center items-center h-full">
            <div class="flex flex-col items-center">
                <span class="icon-[noto--enraged-face] text-9xl"></span>
                <div class="font-bold">什么也没有！</div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { onMounted, ref } from 'vue';
    import axios from "@/utils/myAxios"
    import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger, DialogClose } from "@/components/ui/dialog"
    import { Button } from "@/components/ui/button"
    import { toast } from "vue-sonner"
    import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle, AlertDialogTrigger, } from '@/components/ui/alert-dialog'
    import 'vue-sonner/style.css'
    import { useRouter } from 'vue-router'
    const router = useRouter()
    import dayjs from 'dayjs'
    import 'dayjs/locale/zh-cn'
    import { Select, SelectContent, SelectGroup, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"

    onMounted(() => {
        getOrders()
    })

    // 获取全部订单
    interface Order {
        number: string
        status: number
        name: string
        productCount: number
        image: string
        finalAmount: string
    }

    const orders = ref<Order[]>([])

    const orderStatus: { [key: number]: string } = {
        1: '待付款',
        2: '待发货',
        3: '已发货',
        4: '待收货',
        5: '已完成',
        6: '已取消'
    };

    function getOrderStatus(status: number | undefined) {
        if (status === undefined) return '未知状态';
        return orderStatus[status] || '未知状态';
    }

    const getOrders = async () => {
        try {
            const response = await axios.get('/api/user/shop/order/my-orders')
            orders.value = response.data.data
        } catch (error) {
            console.log(error)
        }
    }

    // 获取订单详情
    interface Product {
        name: string
        variantName: string
        image: string
        quantity: number
        price: number
    }
    interface Detail {
        number: string
        status: number
        products: Product[]
        originalAmount: number
        discountAmount: number
        consignee: string
        phone: string
        address: string
        trackingNo: number
        createdAt: string
        checkoutTime: string
        shipTime: string
        arrivalTime: string
        receiveTime: string
        cancelTime: string
        cancelReason: string
        rejectionReason: string
    }
    const detail = ref<Detail>()

    const showDetail = async (number: string, status: number) => {
        if (status === 1) {
            router.push(`/order/${number}`)
            return
        }
        detail.value = undefined
        try {
            const response = await axios.get('/api/user/shop/order/detail', {
                params: {
                    number: number
                }
            })
            detail.value = response.data.data

        } catch (error) {
            console.log(error)
        }
    }

    // 取消订单
    const reason = ref('')
    const cancel = async (number: string) => {
        if (reason.value === '') {
            toast("嗨！", {
                description: "原因不得为空！",
                action: {
                    label: '我知道了',
                },
            })
            return
        }
        try {
            const response = await axios.patch('/api/user/shop/order/cancel', {
                number: number,
                cancelReason: reason.value
            })
            const order = orders.value.find(order => order.number === number)
            if (response.data.code === 1 && order) {
                order.status = 6
            }
        } catch (error) {
            console.log(error)
        }
    }

    // 确认收货
    const receive = async (number: string) => {
        try {
            await axios.patch("/api/user/shop/order/receive", {}, {
                params: {
                    number: number
                }
            })
            orders.value.find(order => order.number === number)!.status = 5
        } catch (error) {
            console.error(error)
        }
    }
</script>