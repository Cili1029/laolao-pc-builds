<template>
    <div class="h-full w-full overflow-hidden relative">
        <div class="absolute inset-0 bottom-16 overflow-y-auto scrollbar-edge">
            <div class="rounded-md border bg-background shadow-sm h-full flex flex-col">
                <Table v-if="coupons && coupons.length > 0">
                    <TableHeader>
                        <TableRow class="bg-muted/40 hover:bg-muted/40 sticky top-0 z-10 shadow-sm border-b">
                            <TableHead class="w-[60px] text-center">ID</TableHead>
                            <TableHead class="w-[150px]">优惠券名</TableHead>
                            <TableHead class="min-w-[200px] hidden md:table-cell">描述</TableHead>
                            <TableHead class="w-[100px] text-center">最低金额</TableHead>
                            <TableHead class="w-[100px] text-center">抵扣金额</TableHead>
                            <TableHead class="w-[80px] text-center">库存</TableHead>
                            <TableHead class="w-[80px] text-center">状态</TableHead>
                            <TableHead class="w-[120px] text-center hidden lg:table-cell">开始时间</TableHead>
                            <TableHead class="w-[120px] text-center hidden xl:table-cell">结束时间</TableHead>
                            <TableHead class="hidden xl:table-cell w-[120px] text-center">创建信息</TableHead>
                            <TableHead class="hidden xl:table-cell w-[120px] text-center">最后更新</TableHead>
                            <!-- 新增弹窗 -->
                            <Dialog>
                                <DialogTrigger as-child>
                                    <TableHead class="w-[120px] text-center">
                                        <div class="flex justify-between">
                                            <p></p>
                                            <p>操作</p>
                                            <Button size="icon" variant="ghost" class="h-5 w-5 hover:bg-background">
                                                <Plus class="h-3 w-3" />
                                            </Button>
                                        </div>
                                    </TableHead>
                                </DialogTrigger>
                                <DialogContent class="sm:max-w-[600px]">
                                    <DialogHeader>
                                        <DialogTitle>新增优惠券</DialogTitle>
                                        <DialogDescription>配置新的优惠券规则。</DialogDescription>
                                    </DialogHeader>
                                    <div class="grid gap-4 py-4">
                                        <div class="grid gap-2">
                                            <Label>优惠券名</Label>
                                            <Input v-model="newCoupon.name" />
                                        </div>

                                        <div class="grid gap-2">
                                            <Label>库存</Label>
                                            <Input type="number" v-model="newCoupon.stock" />
                                        </div>

                                        <div class="grid gap-2">
                                            <Label>描述</Label>
                                            <Textarea v-model="newCoupon.description" class="resize-none" rows="3" />
                                        </div>

                                        <div class="grid grid-cols-2 gap-4">
                                            <div class="grid gap-2">
                                                <Label>最低使用金额 (¥)</Label>
                                                <Input type="number" v-model="newCoupon.minimumAmount" />
                                            </div>
                                            <div class="grid gap-2">
                                                <Label>抵扣金额 (¥)</Label>
                                                <Input type="number" v-model="newCoupon.discountAmount" />
                                            </div>
                                        </div>

                                        <!-- 编辑日期选择 -->
                                        <div class="grid grid-cols-2 gap-4">
                                            <div class="flex flex-col gap-1">
                                                <Label>开始时间</Label>
                                                <Popover>
                                                    <PopoverTrigger as-child>
                                                        <Button variant="outline"
                                                            :class="cn('w-full justify-start text-left font-normal', !newCoupon.validStartDate && 'text-muted-foreground')">
                                                            <CalendarIcon class="mr-2 h-4 w-4" />{{
                                                                newCoupon.validStartDate || "选择日期" }}
                                                        </Button>
                                                    </PopoverTrigger>
                                                    <PopoverContent class="w-auto p-0">
                                                        <Calendar v-model="startDateProxy" initial-focus />
                                                    </PopoverContent>
                                                </Popover>
                                                <div class="flex space-x-1 items-center">
                                                    <Input class="text-center px-1" type="number" :min="0" :max="23"
                                                        v-model="newCoupon.validStartTime[0]" />
                                                    <span class="text-muted-foreground">:</span>
                                                    <Input class="text-center px-1" type="number" :min="0" :max="59"
                                                        v-model="newCoupon.validStartTime[1]" />
                                                    <span class="text-muted-foreground">:</span>
                                                    <Input class="text-center px-1" type="number" :min="0" :max="59"
                                                        v-model="newCoupon.validStartTime[2]" />
                                                </div>
                                            </div>

                                            <!-- 结束时间 -->
                                            <div class="flex flex-col gap-1">
                                                <Label>结束时间</Label>
                                                <Popover>
                                                    <PopoverTrigger as-child>
                                                        <Button variant="outline"
                                                            :class="cn('w-full justify-start text-left font-normal', !newCoupon.validEndDate && 'text-muted-foreground')">
                                                            <CalendarIcon class="mr-2 h-4 w-4" />{{
                                                                newCoupon.validEndDate || "选择日期" }}
                                                        </Button>
                                                    </PopoverTrigger>
                                                    <PopoverContent class="w-auto p-0">
                                                        <Calendar v-model="endDateProxy" initial-focus />
                                                    </PopoverContent>
                                                </Popover>
                                                <div class="flex space-x-1 items-center">
                                                    <Input class="text-center px-1" type="number" :min="0" :max="23"
                                                        v-model="newCoupon.validEndTime[0]" />
                                                    <span class="text-muted-foreground">:</span>
                                                    <Input class="text-center px-1" type="number" :min="0" :max="59"
                                                        v-model="newCoupon.validEndTime[1]" />
                                                    <span class="text-muted-foreground">:</span>
                                                    <Input class="text-center px-1" type="number" :min="0" :max="59"
                                                        v-model="newCoupon.validEndTime[2]" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <DialogFooter>
                                        <DialogClose as-child><Button type="submit" @click="addCoupon()">确认添加</Button>
                                        </DialogClose>
                                    </DialogFooter>
                                </DialogContent>
                            </Dialog>
                        </TableRow>
                    </TableHeader>

                    <TableBody>
                        <TableRow v-for="coupon in coupons" :key="coupon.id" class="hover:bg-muted/10">
                            <TableCell class="font-mono text-muted-foreground text-center">{{ coupon.id }}
                            </TableCell>
                            <TableCell>{{ coupon.name }}</TableCell>
                            <TableCell class="hidden md:table-cell">
                                <div class="truncate max-w-[200px] text-sm text-muted-foreground"
                                    :title="coupon.description">{{ coupon.description || '-' }}</div>
                            </TableCell>
                            <TableCell class="text-center">¥{{ coupon.minimumAmount }}</TableCell>
                            <TableCell class="text-center text-red-600">-¥{{ coupon.discountAmount }}
                            </TableCell>
                            <TableCell class="text-center font-mono">{{ coupon.stock }}</TableCell>
                            <TableCell class="text-center">
                                <div class="flex items-center justify-center gap-2">
                                    <span class="flex h-2 w-2 rounded-full"
                                        :class="coupon.status === 1 ? 'bg-green-500' : 'bg-red-500'" />
                                    <span class="text-sm text-muted-foreground">{{ coupon.status === 1 ? "启用" : "停用"
                                        }}</span>
                                </div>
                            </TableCell>
                            <TableCell class="hidden lg:table-cell text-center text-muted-foreground">{{
                                dayjs(coupon.validStartTime).format('YYYY-MM-DD') }}</TableCell>
                            <TableCell class="hidden xl:table-cell text-center text-muted-foreground">{{
                                dayjs(coupon.validEndTime).format('YYYY-MM-DD') }}</TableCell>
                            <TableCell
                                class="hidden xl:table-cell text-center text-[10px] text-muted-foreground leading-tight">
                                <div>{{ coupon.createdBy }}</div>
                                <div class="opacity-70 mt-0.5">{{ dayjs(coupon.createdAt).format('YYYY-MM-DD') }}
                                </div>
                            </TableCell>

                            <TableCell
                                class="hidden xl:table-cell text-center text-[10px] text-muted-foreground leading-tight">
                                <div>{{ coupon.updatedBy }}</div>
                                <div class="opacity-70 mt-0.5">{{ dayjs(coupon.updatedAt).format('YYYY-MM-DD') }}
                                </div>
                            </TableCell>
                            <TableCell class="text-center">
                                <div class="flex items-center justify-center gap-2">

                                    <!-- 编辑按钮及弹窗 -->
                                    <Dialog>
                                        <DialogTrigger as-child>
                                            <Button variant="ghost" size="icon"
                                                class="h-8 w-8 text-muted-foreground hover:text-primary"
                                                @click="openEdit(coupon)">
                                                <Edit class="h-4 w-4" />
                                            </Button>
                                        </DialogTrigger>
                                        <DialogContent class="sm:max-w-[600px]">
                                            <DialogHeader>
                                                <DialogTitle>编辑优惠券</DialogTitle>
                                                <DialogDescription>修改优惠券详细信息。</DialogDescription>
                                            </DialogHeader>

                                            <div class="grid gap-4 py-4">
                                                <div class="grid gap-2">
                                                    <Label>优惠券名</Label>
                                                    <Input v-model="editingCoupon.name" />
                                                </div>

                                                <div class="grid gap-2">
                                                    <Label>库存</Label>
                                                    <Input type="number" v-model="editingCoupon.stock" />
                                                </div>

                                                <div class="grid gap-2">
                                                    <Label>描述</Label>
                                                    <Textarea v-model="editingCoupon.description" class="resize-none"
                                                        rows="3" />
                                                </div>

                                                <div class="grid grid-cols-2 gap-4">
                                                    <div class="grid gap-2">
                                                        <Label>最低使用金额 (¥)</Label>
                                                        <Input type="number" v-model="editingCoupon.minimumAmount" />
                                                    </div>
                                                    <div class="grid gap-2">
                                                        <Label>抵扣金额 (¥)</Label>
                                                        <Input type="number" v-model="editingCoupon.discountAmount" />
                                                    </div>
                                                </div>

                                                <!-- 编辑日期选择 -->
                                                <div class="grid grid-cols-2 gap-4">
                                                    <!-- 开始时间 -->
                                                    <div class="flex flex-col gap-1">
                                                        <Label>有效期开始</Label>
                                                        <Popover>
                                                            <PopoverTrigger as-child>
                                                                <Button variant="outline"
                                                                    :class="cn('w-full justify-start text-left font-normal', !editingCoupon.validStartDate && 'text-muted-foreground')">
                                                                    <CalendarIcon class="mr-2 h-4 w-4" />
                                                                    {{ editingCoupon.validStartDate || "选择日期" }}
                                                                </Button>
                                                            </PopoverTrigger>
                                                            <PopoverContent class="w-auto p-0" align="start">
                                                                <!-- 绑定 editStartDateProxy -->
                                                                <Calendar v-model="editStartDateProxy" initial-focus />
                                                            </PopoverContent>
                                                        </Popover>
                                                        <div class="flex space-x-1 items-center">
                                                            <Input class="text-center px-1" type="number" :min="0"
                                                                :max="23" v-model="editingCoupon.validStartTime[0]" />
                                                            <span class="text-muted-foreground">:</span>
                                                            <Input class="text-center px-1" type="number" :min="0"
                                                                :max="59" v-model="editingCoupon.validStartTime[1]" />
                                                            <span class="text-muted-foreground">:</span>
                                                            <Input class="text-center px-1" type="number" :min="0"
                                                                :max="59" v-model="editingCoupon.validStartTime[2]" />
                                                        </div>
                                                    </div>

                                                    <!-- 结束时间 -->
                                                    <div class="flex flex-col gap-1">
                                                        <Label>有效期结束</Label>
                                                        <Popover>
                                                            <PopoverTrigger as-child>
                                                                <Button variant="outline"
                                                                    :class="cn('w-full justify-start text-left font-normal', !editingCoupon.validEndDate && 'text-muted-foreground')">
                                                                    <CalendarIcon class="mr-2 h-4 w-4" />
                                                                    {{ editingCoupon.validEndDate || "选择日期" }}
                                                                </Button>
                                                            </PopoverTrigger>
                                                            <PopoverContent class="w-auto p-0" align="start">
                                                                <!-- 绑定 editEndDateProxy -->
                                                                <Calendar v-model="editEndDateProxy" initial-focus />
                                                            </PopoverContent>
                                                        </Popover>
                                                        <div class="flex space-x-1 items-center">
                                                            <Input class="text-center px-1" type="number" :min="0"
                                                                :max="23" v-model="editingCoupon.validEndTime[0]" />
                                                            <span class="text-muted-foreground">:</span>
                                                            <Input class="text-center px-1" type="number" :min="0"
                                                                :max="59" v-model="editingCoupon.validEndTime[1]" />
                                                            <span class="text-muted-foreground">:</span>
                                                            <Input class="text-center px-1" type="number" :min="0"
                                                                :max="59" v-model="editingCoupon.validEndTime[2]" />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <DialogFooter>
                                                <DialogClose as-child>
                                                    <Button type="submit" @click="submitUpdate()">保存更改</Button>
                                                </DialogClose>
                                            </DialogFooter>
                                        </DialogContent>
                                    </Dialog>

                                    <!-- 状态切换 -->
                                    <Button v-if="coupon.status === 1" variant="destructive" size="sm"
                                        @click="changeStatus(coupon.id, 0)">禁用</Button>
                                    <Button v-else variant="outline" size="sm"
                                        @click="changeStatus(coupon.id, 1)">启用</Button>

                                    <!-- 删除 -->
                                    <AlertDialog>
                                        <AlertDialogTrigger as-child>
                                            <Button variant="ghost" size="icon"
                                                class="h-8 w-8 text-destructive hover:bg-destructive/10">
                                                <Trash2 class="h-4 w-4" />
                                            </Button>
                                        </AlertDialogTrigger>
                                        <AlertDialogContent>
                                            <AlertDialogHeader>
                                                <AlertDialogTitle>确定删除吗?</AlertDialogTitle>
                                            </AlertDialogHeader>
                                            <AlertDialogFooter>
                                                <AlertDialogCancel>取消</AlertDialogCancel>
                                                <AlertDialogAction @click="deleteById(coupon.id)">确定</AlertDialogAction>
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
                        <h3 class="text-lg font-medium">暂无数据</h3>
                        <p class="text-sm text-muted-foreground">
                            当前没有任何用户数据，或者未找到匹配的结果。
                        </p>
                    </div>
                    <Button variant="outline" size="sm" @click="getCoupon()">
                        返回全部数据
                    </Button>
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
    import { onMounted, ref, watch, computed, toRef, type Ref } from 'vue'
    import axios from '@/utils/myAxios'
    import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table'
    import { Button } from '@/components/ui/button'
    import { Label } from '@/components/ui/label'
    import { Textarea } from '@/components/ui/textarea'
    import { Input } from '@/components/ui/input'
    import { parseDate, type DateValue } from '@internationalized/date'
    import { CalendarIcon, Trash2, Plus, Edit, Ghost } from 'lucide-vue-next'
    import { cn } from '@/lib/utils'
    import { Calendar } from '@/components/ui/calendar'
    import { Popover, PopoverContent, PopoverTrigger } from '@/components/ui/popover'
    import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle, AlertDialogTrigger } from '@/components/ui/alert-dialog'
    import { Dialog, DialogClose, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger } from '@/components/ui/dialog'
    import { Pagination, PaginationContent, PaginationEllipsis, PaginationItem, PaginationNext, PaginationPrevious } from '@/components/ui/pagination'
    import dayjs from 'dayjs'
    import relativeTime from 'dayjs/plugin/relativeTime'
    import 'dayjs/locale/zh-cn'
    dayjs.extend(relativeTime)
    dayjs.locale('zh-cn')
    import { useCommonStore } from '@/stores/CommonStore'
    const commonStore = useCommonStore()

    watch(
        () => commonStore.search.search,
        (newSearch) => {
            if (newSearch) {
                getCoupon()
                commonStore.search.search = false
                commonStore.search.searchContent = ''
            }
        }
    )

    // --- 数据定义 ---
    interface Coupon {
        id: number
        name: string
        description: string
        minimumAmount: number
        discountAmount: number
        stock: number
        status: number
        validStartTime: string
        validEndTime: string
        createdBy: string
        createdAt: string
        updatedBy: string
        updatedAt: string
    }

    const coupons = ref<Coupon[]>([])
    const pageNum = ref(1)
    const pageSize = ref(10)
    const total = ref(0)

    onMounted(() => getCoupon())
    watch(() => pageNum.value, () => getCoupon())

    const getCoupon = async () => {
        try {
            const response = await axios.get("/api/admin/shop/coupon", {
                params: {
                    pageNum: pageNum.value,
                    pageSize: pageSize.value,
                    searchContent: commonStore.search.searchContent
                }
            })
            const resData = response.data.data
            coupons.value = resData.list
            total.value = resData.total
        } catch (error) { console.error(error) }
    }

    const changeStatus = async (id: number, status: number) => {
        try {
            const url = status === 1 ? "/api/admin/shop/coupon/activate" : "/api/admin/shop/coupon/deactivate"
            await axios.patch(url, { id })
            const coupon = coupons.value.find(c => c.id === id)
            if (coupon) coupon.status = status
        } catch (error) { console.error(error) }
    }

    const deleteById = async (id: number) => {
        try {
            await axios.delete("/api/admin/shop/coupon", {
                params: {
                    id
                }
            })
            coupons.value = coupons.value.filter(c => c.id !== id)
        } catch (error) { console.error(error) }
    }

    const createDateProxy = (targetRef: Ref<string>) => {
        return computed({
            get: () => {
                if (!targetRef.value) return undefined
                try { return parseDate(targetRef.value) } catch (e) { return undefined }
            },
            set: (val: DateValue | undefined) => {
                targetRef.value = val ? val.toString() : ''
            }
        })
    }

    const pad = (n: number | string) => n.toString().padStart(2, '0')
    const formatTimeStr = (arr: any[]) => arr.map(pad).join(':')

    const newCoupon = ref({
        name: "",
        stock: 0,
        description: "",
        minimumAmount: 0,
        discountAmount: 0,
        validStartDate: dayjs().format("YYYY-MM-DD"),
        validEndDate: dayjs().format("YYYY-MM-DD"),
        validStartTime: [12, 0, 0],
        validEndTime: [12, 0, 0]
    })

    const startDateProxy = createDateProxy(toRef(newCoupon.value, 'validStartDate'))
    const endDateProxy = createDateProxy(toRef(newCoupon.value, 'validEndDate'))

    const addCoupon = async () => {
        try {
            const finalStart = `${newCoupon.value.validStartDate}T${formatTimeStr(newCoupon.value.validStartTime)}`
            const finalEnd = `${newCoupon.value.validEndDate}T${formatTimeStr(newCoupon.value.validEndTime)}`

            await axios.post("/api/admin/shop/coupon/add", {
                name: newCoupon.value.name,
                stock: newCoupon.value.stock,
                description: newCoupon.value.description,
                minimumAmount: newCoupon.value.minimumAmount,
                discountAmount: newCoupon.value.discountAmount,
                validStartTime: finalStart,
                validEndTime: finalEnd,
            })
            pageNum.value = 1
            getCoupon()
        } catch (error) { console.error(error) }
    }
    
    // 编辑用的数据模型
    const editingCoupon = ref({
        id: 0,
        name: "",
        stock: 0,
        description: "",
        minimumAmount: 0,
        discountAmount: 0,
        validStartDate: "",
        validEndDate: "",
        validStartTime: [0, 0, 0],
        validEndTime: [0, 0, 0]
    })

    const editStartDateProxy = computed({
        get: () => {
            const val = editingCoupon.value.validStartDate
            if (!val) return undefined
            try { return parseDate(val) } catch (e) { return undefined }
        },
        set: (val: DateValue | undefined) => {
            editingCoupon.value.validStartDate = val ? val.toString() : ''
        }
    })

    const editEndDateProxy = computed({
        get: () => {
            const val = editingCoupon.value.validEndDate
            if (!val) return undefined
            try { return parseDate(val) } catch (e) { return undefined }
        },
        set: (val: DateValue | undefined) => {
            editingCoupon.value.validEndDate = val ? val.toString() : ''
        }
    })

    // 打开编辑弹窗：回显数据
    const openEdit = (coupon: Coupon) => {
        // 1. 解析日期时间 (假设后端返回格式: 2024-12-01T12:05:09)
        const startObj = dayjs(coupon.validStartTime)
        const endObj = dayjs(coupon.validEndTime)

        // 2. 填充数据
        editingCoupon.value = {
            id: coupon.id,
            name: coupon.name,
            stock: coupon.stock,
            description: coupon.description,
            minimumAmount: coupon.minimumAmount,
            discountAmount: coupon.discountAmount,
            // 拆分日期
            validStartDate: startObj.format('YYYY-MM-DD'),
            validEndDate: endObj.format('YYYY-MM-DD'),
            // 拆分时间 (转为数字数组，方便 Input type=number)
            validStartTime: [startObj.hour(), startObj.minute(), startObj.second()],
            validEndTime: [endObj.hour(), endObj.minute(), endObj.second()]
        }
    }

    // 提交编辑
    const submitUpdate = async () => {
        try {
            // 拼接最终时间字符串
            const finalStart = `${editingCoupon.value.validStartDate}T${formatTimeStr(editingCoupon.value.validStartTime)}`
            const finalEnd = `${editingCoupon.value.validEndDate}T${formatTimeStr(editingCoupon.value.validEndTime)}`

            await axios.put("/api/admin/shop/coupon/update", {
                id: editingCoupon.value.id,
                name: editingCoupon.value.name,
                stock: editingCoupon.value.stock,
                description: editingCoupon.value.description,
                minimumAmount: editingCoupon.value.minimumAmount,
                discountAmount: editingCoupon.value.discountAmount,
                validStartTime: finalStart,
                validEndTime: finalEnd,
            })

            // 刷新列表
            getCoupon()
        } catch (error) {
            console.error(error)
        }
    }
</script>