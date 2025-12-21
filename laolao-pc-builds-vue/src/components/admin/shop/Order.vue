<template>
    <div class="h-full w-full overflow-hidden relative bg-muted/10 p-2">
        <div class="absolute inset-0 bottom-14 overflow-hidden rounded-lg border bg-background shadow-sm flex flex-col">
            <div class="flex items-center p-2">
                <ButtonGroup>
                    <Button @click="currentStatus = 2, getOrder(2)" variant="outline"
                        :class="currentStatus === 2 ? 'bg-accent text-accent-foreground' : 'text-muted-foreground'"
                        class="h-8 text-xs font-medium">
                        待发货
                    </Button>
                    <Button @click="currentStatus = 3, getOrder(3)" variant="outline"
                        :class="currentStatus === 3 ? 'bg-accent text-accent-foreground' : 'text-muted-foreground'"
                        class="h-8 text-xs font-medium">
                        物流中
                    </Button>
                    <Button @click="currentStatus = 4, getOrder(4)" variant="outline"
                        :class="currentStatus === 4 ? 'bg-accent text-accent-foreground' : 'text-muted-foreground'"
                        class="h-8 text-xs font-medium">
                        待收货
                    </Button>
                    <Button @click="currentStatus = 5, getOrder(5)" variant="outline"
                        :class="currentStatus === 5 ? 'bg-accent text-accent-foreground' : 'text-muted-foreground'"
                        class="h-8 text-xs font-medium">
                        已完成
                    </Button>
                    <Button @click="currentStatus = 6, getOrder(6)" variant="outline"
                        :class="currentStatus === 6 ? 'bg-accent text-accent-foreground' : 'text-muted-foreground'"
                        class="h-8 text-xs font-medium">
                        已取消
                    </Button>
                </ButtonGroup>

                <ButtonGroup class="ml-auto">
                    <Button @click="getOrder(currentStatus)" variant="outline" aria-label="Refresh">
                        <RotateCw />
                    </Button>
                    <Input v-model="searchContent" placeholder="搜索点什么..." />
                    <Button @click="getOrder(currentStatus)" :disabled="searchContent.length === 0" variant="outline"
                        aria-label="Search">
                        <SearchIcon />
                    </Button>
                </ButtonGroup>
            </div>


            <!-- 表格区域 -->
            <div class="flex-1 overflow-y-auto scrollbar-edge">
                <Table v-if="orders.length > 0">
                    <TableHeader>
                        <TableRow class="bg-muted/40 hover:bg-muted/40 sticky top-0 z-10 border-b">
                            <TableHead class="w-[50px] text-center text-xs">ID</TableHead>
                            <TableHead class="w-[180px] text-xs">订单信息</TableHead>
                            <TableHead class="w-[200px] text-xs">收货信息</TableHead>
                            <TableHead class="min-w-[200px] text-xs">配送地址</TableHead>
                            <TableHead v-if="currentStatus === 3 || currentStatus === 4" class="min-[180px] text-xs">
                                快递单号</TableHead>
                            <TableHead class="w-[140px] text-center text-xs">
                                <span v-if="currentStatus === 2">支付时间</span>
                                <span v-if="currentStatus === 3">发货时间</span>
                                <span v-if="currentStatus === 4">送达时间</span>
                                <span v-if="currentStatus === 5">确认收货时间</span>
                                <span v-if="currentStatus === 6">取消时间</span>
                            </TableHead>
                            <TableHead v-if="currentStatus === 6" class="w-[140px] text-center text-xs">取消原因</TableHead>
                            <TableHead v-if="currentStatus === 2" class="w-[140px] text-center text-xs">操作</TableHead>
                        </TableRow>
                    </TableHeader>

                    <TableBody>
                        <TableRow v-for="order in orders" :key="order.id" class="hover:bg-muted/5">
                            <!-- ID -->
                            <TableCell class="text-center py-3">
                                <span class="font-mono text-xs text-muted-foreground">#{{ order.id }}</span>
                            </TableCell>

                            <!-- 订单信息 -->
                            <TableCell class="py-3">
                                <div class="flex flex-col gap-1">
                                    <span class="font-medium text-sm font-mono tracking-tight">{{ order.number }}</span>
                                    <div class="flex items-center">
                                        <Dialog>
                                            <DialogTrigger as-child>
                                                <Button @click="getDetail(order.id)" variant="ghost" size="sm"
                                                    class="h-8 px-2 text-xs text-blue-600 hover:text-blue-700 hover:bg-blue-50">
                                                    <ListTodo class="mr-1 h-3 w-3" />
                                                    商品详情
                                                </Button>
                                            </DialogTrigger>

                                            <DialogContent class="sm:max-w-[800px]">
                                                <DialogHeader>
                                                    <DialogTitle class="flex items-center gap-2">
                                                        <span>订单商品明细</span>
                                                        <Badge variant="outline"
                                                            class="font-normal text-muted-foreground">
                                                            #{{ order.number }}
                                                        </Badge>
                                                    </DialogTitle>
                                                    <DialogDescription>
                                                        共 {{ details.length }} 件商品，下单时间：{{
                                                            dayjs(order.checkoutTime).format('YYYY-MM-DD HH:mm') }}
                                                    </DialogDescription>
                                                </DialogHeader>

                                                <div class="max-h-[60vh] overflow-y-auto scrollbar-edge">
                                                    <Table>
                                                        <TableHeader>
                                                            <TableRow class="bg-muted/50">
                                                                <TableHead class="min-w-[200px]">商品信息</TableHead>
                                                                <TableHead class="text-right">单价</TableHead>
                                                                <TableHead class="text-center w-[100px]">数量</TableHead>
                                                                <TableHead class="text-right">小计</TableHead>
                                                            </TableRow>
                                                        </TableHeader>

                                                        <TableBody>
                                                            <TableRow v-for="detail in details" :key="detail.id">
                                                                <TableCell>
                                                                    <div class="flex flex-col gap-1">
                                                                        <span class="font-medium text-sm">
                                                                            {{ detail.name }}
                                                                        </span>
                                                                        <div
                                                                            class="flex items-center gap-2 text-xs text-muted-foreground">
                                                                            <span
                                                                                class="bg-muted px-1.5 py-0.5 rounded">
                                                                                {{ detail.variantName || '默认规格' }}
                                                                            </span>
                                                                            <span>ID: {{ detail.id }}</span>
                                                                        </div>
                                                                    </div>
                                                                </TableCell>

                                                                <!-- 单价 -->
                                                                <TableCell class="text-right font-mono">
                                                                    ¥{{ Number(detail.price).toFixed(2) }}
                                                                </TableCell>

                                                                <!-- 数量 -->
                                                                <TableCell class="text-center font-medium">
                                                                    x {{ detail.quantity }}
                                                                </TableCell>

                                                                <!-- 小计 -->
                                                                <TableCell
                                                                    class="text-right font-mono font-medium text-foreground">
                                                                    ¥{{ (Number(detail.price) *
                                                                        detail.quantity).toFixed(2) }}
                                                                </TableCell>
                                                            </TableRow>
                                                        </TableBody>

                                                        <!-- 底部总计 -->
                                                        <TableFooter class="bg-transparent border-t-2">
                                                            <TableRow>
                                                                <TableCell colspan="3"
                                                                    class="text-right font-medium text-muted-foreground">
                                                                    订单总金额
                                                                </TableCell>
                                                                <TableCell
                                                                    class="text-right font-bold text-lg text-red-600">
                                                                    ¥{{ calculateTotal(details) }}
                                                                </TableCell>
                                                            </TableRow>
                                                        </TableFooter>
                                                    </Table>
                                                </div>
                                            </DialogContent>
                                        </Dialog>
                                    </div>
                                </div>
                            </TableCell>

                            <!-- 收货信息：合并姓名和电话 -->
                            <TableCell class="py-3">
                                <div v-if="order.consignee" class="flex flex-col">
                                    <div class="flex items-center gap-2">
                                        <User class="h-3.5 w-3.5" />
                                        <span class="text-sm font-medium">{{ order.consignee }}</span>
                                    </div>
                                    <div class="flex items-center gap-2 mt-1">
                                        <Phone class="h-3.5 w-3.5" />
                                        <span class="text-xs font-mono">{{ order.phone }}</span>
                                    </div>
                                </div>
                            </TableCell>

                            <!-- 地址 -->
                            <TableCell class="py-3">
                                <div v-if="order.address" class="flex items-center max-w-[250px]">
                                    <MapPin class="h-3.5 w-3.5" />
                                    <TableCell class="text-xs leading-snug line-clamp-2">
                                        {{ order.address }}
                                    </TableCell>
                                </div>
                            </TableCell>

                            <!-- 快递单号 -->
                            <TableCell v-if="currentStatus === 3 || currentStatus === 4" class="py-3">
                                <span class="font-medium text-sm font-mono tracking-tight">{{ order.trackingNo }}</span>
                            </TableCell>

                            <!-- 时间/原因 -->
                            <TableCell class="text-center">
                                <div v-if="currentStatus === 2" class="flex flex-col items-center gap-1">
                                    <span class="text-xs font-mono">
                                        {{ dayjs(order.checkoutTime).format('YYYY-MM-DD') }}
                                    </span>
                                    <span class="text-[10px]">
                                        {{ dayjs(order.checkoutTime).format('HH:mm') }}
                                    </span>
                                </div>

                                <div v-if="currentStatus === 3" class="flex flex-col items-center gap-1">
                                    <span class="text-xs font-mono">
                                        {{ dayjs(order.shipTime).format('YYYY-MM-DD') }}
                                    </span>
                                    <span class="text-[10px]">
                                        {{ dayjs(order.shipTime).format('HH:mm') }}
                                    </span>
                                </div>

                                <div v-if="currentStatus === 4" class="flex flex-col items-center gap-1">
                                    <span class="text-xs font-mono">
                                        {{ dayjs(order.arrivalTime).format('YYYY-MM-DD') }}
                                    </span>
                                    <span class="text-[10px]">
                                        {{ dayjs(order.arrivalTime).format('HH:mm') }}
                                    </span>
                                </div>

                                <div v-if="currentStatus === 5" class="flex flex-col items-center gap-1">
                                    <span class="text-xs font-mono">
                                        {{ dayjs(order.receiveTime).format('YYYY-MM-DD') }}
                                    </span>
                                    <span class="text-[10px]">
                                        {{ dayjs(order.receiveTime).format('HH:mm') }}
                                    </span>
                                </div>

                                <div v-if="currentStatus === 6" class="flex flex-col items-center gap-1">
                                    <span class="text-xs font-mono">
                                        {{ dayjs(order.cancelTime).format('YYYY-MM-DD') }}
                                    </span>
                                    <span class="text-[10px]">
                                        {{ dayjs(order.cancelTime).format('HH:mm') }}
                                    </span>
                                </div>
                            </TableCell>

                            <TableCell v-if="currentStatus === 6">
                                <div class="flex flex-col items-center gap-1">
                                    <span v-if="order.rejectionReason">
                                        {{ order.rejectionReason }}
                                    </span>
                                    <span v-else>
                                        {{ order.cancelReason }}
                                    </span>
                                </div>
                            </TableCell>

                            <TableCell v-if="currentStatus === 2" class="text-center py-3">
                                <div class="flex items-center justify-center gap-2">
                                    <Dialog>
                                        <DialogTrigger as-child>
                                            <Button>
                                                发货
                                            </Button>
                                        </DialogTrigger>
                                        <DialogContent class="sm:max-w-[425px]">
                                            <DialogHeader>
                                                <DialogTitle>发货</DialogTitle>
                                                <DialogDescription>
                                                    请谨慎填写订单号
                                                </DialogDescription>
                                            </DialogHeader>
                                            <div class="grid gap-3">
                                                <Label>订单号</Label>
                                                <Input v-model="trackingNo" />
                                            </div>
                                            <DialogFooter>
                                                <DialogClose as-child>
                                                    <Button variant="outline">
                                                        取消
                                                    </Button>
                                                </DialogClose>
                                                <DialogClose as-child>
                                                    <Button @click="ship(order.id)" :disabled="trackingNo === ''"
                                                        type="submit">
                                                        发货
                                                    </Button>
                                                </DialogClose>
                                            </DialogFooter>
                                        </DialogContent>
                                    </Dialog>

                                    <AlertDialog>
                                        <AlertDialogTrigger as-child>
                                            <Button variant="outline">
                                                拒单
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
                                                    <Select v-model="rejectionReason">
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
                                                <AlertDialogAction @click.stop="cancel(order.id)">拒单
                                                </AlertDialogAction>
                                            </AlertDialogFooter>
                                        </AlertDialogContent>
                                    </AlertDialog>
                                </div>
                            </TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
                <div v-else class="flex flex-col h-full items-center justify-center gap-4 text-center">
                    <div class="rounded-full bg-muted/30 p-4">
                        <Ghost class="h-10 w-10 text-muted-foreground/60" />
                    </div>
                    <div class="space-y-1">
                        <h3 class="text-lg font-medium">无数据
                        </h3>
                    </div>
                </div>
            </div>
        </div>

        <!-- 分页 -->
        <div class="absolute bottom-0 left-0 right-0 h-16 border-t flex items-center p-2 bg-background z-10">
            <Pagination v-if="total > 0" v-model:page="pageNum" :total="total" :items-per-page="pageSize"
                :sibling-count="1" show-edges>
                <PaginationContent v-slot="{ items }">
                    <PaginationPrevious />
                    <template v-for="(item, index) in items">
                        <PaginationItem v-if="item.type === 'page'" :key="index" :value="item.value"
                            :is-active="item.value === pageNum">
                            {{ item.value }}
                        </PaginationItem>
                        <PaginationEllipsis v-else :key="item.type" :index="index" />
                    </template>
                    <PaginationNext />
                </PaginationContent>
            </Pagination>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { onMounted, ref, watch, } from 'vue'
    import axios from '@/utils/myAxios'
    import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow, TableFooter } from '@/components/ui/table'
    import { Button } from '@/components/ui/button'
    import { User, Phone, MapPin, Ghost, ListTodo, SearchIcon, RotateCw } from 'lucide-vue-next'
    import { toast } from "vue-sonner"
    import { ButtonGroup } from '@/components/ui/button-group'
    import { Select, SelectContent, SelectGroup, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
    import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle, AlertDialogTrigger, } from '@/components/ui/alert-dialog'
    import { Pagination, PaginationContent, PaginationEllipsis, PaginationItem, PaginationNext, PaginationPrevious } from '@/components/ui/pagination'
    import { Dialog, DialogClose, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, } from '@/components/ui/dialog'
    import { Input } from '@/components/ui/input'
    import { Badge } from '@/components/ui/badge'
    import { Label } from '@/components/ui/label'
    import dayjs from 'dayjs'
    import relativeTime from 'dayjs/plugin/relativeTime'
    import 'dayjs/locale/zh-cn'
    dayjs.extend(relativeTime)
    dayjs.locale('zh-cn')

    interface Order {
        id: number
        number: string
        consignee: string
        phone: string
        address: string
        // 快递单号
        trackingNo: string | null
        // 订单取消原因
        cancelReason: string | null
        // 订单拒绝原因
        rejectionReason: string | null
        // 支付时间
        checkoutTime: string
        // 订单取消时间
        cancelTime: string | null
        // 发货时间
        shipTime: string | null
        // 到货时间
        arrivalTime: string | null
        // 确认收货时间
        receiveTime: string | null
    }

    const orders = ref<Order[]>([])
    const pageNum = ref(1)
    const pageSize = ref(10)
    const total = ref(0)

    onMounted(() => {
        getOrder(currentStatus.value)
    })

    watch(() => pageNum.value,
        () => getOrder(currentStatus.value))

    const currentStatus = ref(2)
    const searchContent = ref("")

    const getOrder = async (status: number) => {
        try {
            const response = await axios.get("/api/admin/shop/order", {
                params: {
                    status: status,
                    searchContent: searchContent.value,
                    pageNum: pageNum.value,
                    pageSize: pageSize.value
                }
            })
            const resData = response.data.data
            orders.value = resData.list
            total.value = resData.total
        } catch (error) {
            console.error(error)
        }
    }

    // 取消订单
    const rejectionReason = ref('')
    const cancel = async (id: number) => {
        if (rejectionReason.value === '') {
            toast("嗨！", {
                description: "原因不得为空！",
                action: {
                    label: '我知道了',
                },
            })
            return
        }
        try {
            await axios.patch('/api/admin/shop/order/reject', {
                id: id,
                rejectionReason: rejectionReason.value
            })
            getOrder(currentStatus.value)
            rejectionReason.value = ''
        } catch (error) {
            console.log(error)
        }
    }

    const trackingNo = ref('')
    const ship = async (id: number) => {
        try {
            await axios.patch("/api/admin/shop/order/ship", {
                id: id,
                trackingNo: trackingNo.value
            })
            getOrder(currentStatus.value)
            trackingNo.value = ''
        } catch (error) {
            console.error(error)
        }
    }

    interface Detail {
        id: number
        name: string
        variantName: string
        quantity: number
        price: number
    }
    const details = ref<Detail[]>([])

    const getDetail = async (id: number) => {
        try {
            const response = await axios.get("/api/admin/shop/order/detail", {
                params: {
                    orderId: id
                }
            })
            details.value = response.data.data
        } catch (error) {
            console.log(error)
        }
    }

    const calculateTotal = (items: any[]) => {
        if (!items || items.length === 0) return '0.00'
        const total = items.reduce((sum, item) => {
            return sum + (Number(item.price) * Number(item.quantity))
        }, 0)
        return total.toFixed(2)
    }
</script>