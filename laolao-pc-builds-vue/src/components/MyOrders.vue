<template>
    <div class="h-full overflow-y-auto p-4">
        <Dialog>
            <DialogTrigger as-child>
                <div class="bg-white flex border border-gray-200 p-2 rounded-lg">
                    <div class="w-full flex flex-col space-y-2">
                        <div v-for="order in orders" :key="order.number"
                            class="rounded-lg shadow-sm border border-gray-200 p-4"
                            @click="showDetail(order.number, order.status)">
                            <div class="mb-3 flex justify-between">
                                <p class="font-bold">订单号：{{ order.number }}</p>
                                <p class="font-bold"> {{ getOrderStatus(order.status) }}</p>
                            </div>
                            <div class="flex items-center">
                                <img :src="order.image" class="w-15 h-15 object-cover rounded-md mr-4" />
                                <div class="flex-1">
                                    <h3 class="font-medium text-gray-900">{{ order.name }}</h3>
                                    <span v-if="order.productCount > 1">等{{ order.productCount - 1 }}个商品</span>
                                </div>
                                <div class="ml-auto">
                                    <span class="text-m">实付款￥
                                        <span class="text-lg font-bold">{{ order.finalAmount }}</span>
                                    </span>
                                </div>
                            </div>
                            <div class="mb-3 flex justify-end" @click.stop>
                                <AlertDialog>
                                    <AlertDialogTrigger as-child>
                                        <Button v-if="order.status == 1 || order.status == 2">取消订单</Button>
                                    </AlertDialogTrigger>
                                    <AlertDialogContent>
                                        <AlertDialogHeader>
                                            <AlertDialogTitle>确定要取消吗？</AlertDialogTitle>
                                            <AlertDialogDescription>
                                                点错了就关，别真取消了，在被窝里偷偷听反方向的钟
                                            </AlertDialogDescription>
                                            <Textarea v-model="reason" placeholder="取消原因？"></Textarea>
                                        </AlertDialogHeader>
                                        <AlertDialogFooter>
                                            <AlertDialogCancel>点错了</AlertDialogCancel>
                                            <AlertDialogAction @click.stop="cancel(order.number)">故意的
                                            </AlertDialogAction>
                                        </AlertDialogFooter>
                                    </AlertDialogContent>
                                </AlertDialog>
                                <Button v-if="order.status == 3 || order.status == 4">确认收货</Button>
                            </div>
                        </div>
                    </div>
                </div>
            </DialogTrigger>
            <DialogContent class="sm:max-w-[1000px]">
                <DialogHeader>
                    <DialogTitle>商品详情</DialogTitle>
                    <DialogDescription class="flex justify-between">
                        <div>订单号：{{ detail?.number }}</div>
                        <div>{{ getOrderStatus(detail?.status) }}</div>
                    </DialogDescription>
                </DialogHeader>
                <div class="flex gap-6 py-4">
                    <div class="w-3/5 flex flex-col space-y-2">
                        <div v-for="product in detail?.products" :key="detail?.number"
                            class="bg-white rounded-lg shadow-sm border border-gray-200 p-4 flex items-center">
                            <img :src="product.image" class="w-15 h-15 object-cover rounded-md mr-4" />
                            <div class="flex-1">
                                <h3 class="font-medium text-gray-900">{{ product.name }}</h3>
                                {{ product.variantName }}
                            </div>
                            <div class="ml-auto">
                                原价:<span class="text-lg font-bold text-red-600">￥{{ product.price }}</span>
                            </div>
                        </div>

                    </div>
                    <div class="w-2/5 flex flex-col justify-between">
                        <p class="flex justify-between">
                            <span>原价</span>
                            <span>{{ detail?.originalAmount ?? 0 }}</span>
                        </p>
                        <p v-if="detail?.status != 1 && detail?.status != 5" class="flex justify-between">
                            <span>优惠</span>
                            <span>{{ detail?.discountAmount ?? 0 }}</span>
                        </p>
                        <p v-if="detail?.status != 1 && detail?.status != 5" class="flex justify-between">
                            <span>实付款</span>
                            <span>{{ (detail?.originalAmount ?? 0) - (detail?.discountAmount ?? 0) }}</span>
                        </p>
                        <p v-if="detail?.consignee != null" class="flex justify-between">
                            <span>收货人</span>
                            <span>{{ detail?.consignee }}</span>
                        </p>
                        <p v-if="detail?.phone != null" class="flex justify-between">
                            <span>收货人电话</span>
                            <span>{{ detail?.phone }}</span>
                        </p>
                        <p v-if="detail?.address != null" class="flex justify-between">
                            <span>收货人地址</span>
                            <span>{{ detail?.address }}</span>
                        </p>
                        <p v-if="detail?.cancelReason != null" class="flex justify-between">
                            <span>取消原因</span>
                            <span>{{ detail?.cancelReason }}</span>
                        </p>
                        <p v-if="detail?.rejectionReason != null" class="flex justify-between">
                            <span>拒绝原因</span>
                            <span>{{ detail?.rejectionReason }}</span>
                        </p>
                        <p v-if="detail?.cancelTime != null" class="flex justify-between">
                            <span>取消时间</span>
                            <span>{{ detail?.cancelTime }}</span>
                        </p>
                        <p v-if="detail?.checkoutTime != null" class="flex justify-between">
                            <span>支付时间</span>
                            <span>{{ detail?.checkoutTime }}</span>
                        </p>
                        <p v-if="detail?.deliveryTime != null" class="flex justify-between">
                            <span>发货时间</span>
                            <span>{{ detail?.deliveryTime }}</span>
                        </p>
                        <p v-if="detail?.receiveTime != null" class="flex justify-between">
                            <span>确认收货时间</span>
                            <span>{{ detail?.receiveTime }}</span>
                        </p>
                        <DialogFooter class="">
                            <DialogClose as-child>
                                <Button variant="secondary" class="w-full">
                                    关闭
                                </Button>
                            </DialogClose>
                        </DialogFooter>
                    </div>
                </div>
            </DialogContent>
        </Dialog>
    </div>

</template>

<script setup lang="ts">
    import { onMounted, ref } from 'vue';
    import axios from "@/utils/myAxios"
    import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, DialogClose } from "@/components/ui/dialog"
    import { Button } from "@/components/ui/button"
    import { Textarea } from "@/components/ui/textarea"
    import { toast } from "vue-sonner"
    import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle, AlertDialogTrigger, } from '@/components/ui/alert-dialog'
    import 'vue-sonner/style.css'
    import { useRouter } from 'vue-router'
    const router = useRouter()

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
            const response = await axios.get('/user/shop/order/my-orders')
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
        cancelReason: string
        rejectionReason: string
        checkoutTime: string
        cancelTime: string
        deliveryTime: string
        receiveTime: string
        createdAt: string
    }
    const detail = ref<Detail>()

    const showDetail = async (number: string, status: number) => {
        console.log(number)
        if (status === 1) {
            router.push(`/order/${number}`)
            return
        }
        detail.value = undefined
        try {
            const response = await axios.get('/user/shop/order/detail', {
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
            const response = await axios.patch('/user/shop/order/cancel', {
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
</script>