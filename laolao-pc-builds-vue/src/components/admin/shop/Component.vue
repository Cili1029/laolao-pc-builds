<template>
    <!-- 根容器：占满父容器，相对定位 -->
    <div class="h-full w-full overflow-hidden relative">
        <!-- 表格滚动区域：填充分页上方所有空间 -->
        <div class="absolute inset-0 bottom-16 overflow-y-auto">
            <div class="rounded-md border bg-background shadow-sm h-full flex flex-col">
                <Table>
                    <TableHeader>
                        <TableRow class="bg-muted/40 hover:bg-muted/40">
                            <TableHead class="w-[40px] px-2"></TableHead>
                            <TableHead class="w-[70px] text-center">预览</TableHead>
                            <TableHead class="w-[60px] text-center">ID</TableHead>
                            <TableHead class="min-w-[150px]">组件名</TableHead>
                            <TableHead class="w-[100px]">类别</TableHead>
                            <TableHead class="hidden md:table-cell min-w-[200px]">描述</TableHead>
                            <TableHead class="w-[80px] text-center">状态</TableHead>
                            <TableHead class="w-[60px] text-center">排序权重</TableHead>
                            <TableHead class="hidden xl:table-cell w-[120px] text-center">创建信息</TableHead>
                            <TableHead class="w-[50px] text-center">
                                <Button size="icon" variant="ghost" class="h-5 w-5 hover:bg-background">
                                    <Plus class="h-3 w-3" />
                                </Button>
                            </TableHead>
                        </TableRow>
                    </TableHeader>

                    <TableBody>
                        <template v-for="component in components" :key="component.id">
                            <!-- 父行 -->
                            <TableRow class="cursor-pointer group transition-all duration-200"
                                :class="openId === component.id ? 'bg-muted/60' : 'hover:bg-muted/30'"
                                @click="open(component)">
                                <TableCell class="py-3 px-2 text-center">
                                    <div
                                        class="flex items-center justify-center h-6 w-6 rounded-md hover:bg-muted/80 transition-colors mx-auto">
                                        <ChevronRight
                                            class="h-4 w-4 text-muted-foreground transition-transform duration-200"
                                            :class="openId === component.id ? 'rotate-90 text-foreground' : ''" />
                                    </div>
                                </TableCell>

                                <TableCell class="py-3 text-center">
                                    <div
                                        class="relative h-10 w-10 rounded-md border bg-muted flex items-center justify-center overflow-hidden mx-auto">
                                        <template v-if="component.images.length > 0">
                                            <img :src="component.images[0]!" class="h-full w-full object-cover" />
                                            <div v-if="component.images?.length > 1"
                                                class="absolute bottom-0 right-0 bg-black/70 text-white text-[9px] px-1 rounded-tl-sm font-medium leading-tight">
                                                +{{ component.images.length - 1 }}
                                            </div>
                                        </template>
                                        <ImageIcon v-else class="h-4 w-4 text-muted-foreground/50" />
                                    </div>
                                </TableCell>

                                <TableCell class="font-mono text-xs text-muted-foreground text-center">
                                    {{ component.id }}
                                </TableCell>

                                <TableCell class="font-medium text-foreground">
                                    <div class="truncate max-w-[200px] lg:max-w-[300px]" :title="component.name">
                                        {{ component.name }}
                                    </div>
                                </TableCell>

                                <TableCell>
                                    <Badge variant="outline" class="font-normal text-xs whitespace-nowrap">
                                        {{ component.categoryId }}
                                    </Badge>
                                </TableCell>

                                <TableCell class="hidden md:table-cell">
                                    <p class="text-sm text-muted-foreground truncate max-w-[200px]"
                                        :title="component.commonDescription">
                                        {{ component.commonDescription }}
                                    </p>
                                </TableCell>

                                <TableCell class="text-center">
                                    <div class="flex items-center justify-center gap-1.5">
                                        <span class="flex h-1.5 w-1.5 rounded-full"
                                            :class="component.status === 1 ? 'bg-green-500' : 'bg-red-500'" />
                                        <span class="text-xs text-muted-foreground">
                                            {{ component.status === 1 ? "启用" : "停用" }}
                                        </span>
                                    </div>
                                </TableCell>

                                <TableCell class="text-center font-mono text-xs">
                                    {{ component.sort }}
                                </TableCell>

                                <TableCell
                                    class="hidden xl:table-cell text-center text-[10px] text-muted-foreground leading-tight">
                                    <div>{{ component.createdBy }}</div>
                                    <div class="opacity-70 mt-0.5">{{ dayjs(component.createdAt).format('YYYY-MM-DD') }}
                                    </div>
                                </TableCell>

                                <TableCell @click.stop class="text-center px-2">
                                    <DropdownMenu>
                                        <DropdownMenuTrigger>
                                            <Button variant="ghost" size="icon" class="h-7 w-7">
                                                <MoreHorizontal class="h-4 w-4" />
                                            </Button>
                                        </DropdownMenuTrigger>
                                        <DropdownMenuContent align="end">
                                            <DropdownMenuItem v-if="component.status === 1"
                                                @click="changeComponentStatus(component.id, 0)">
                                                <CircleOff class="mr-2 h-3.5 w-3.5" />禁用
                                            </DropdownMenuItem>
                                            <DropdownMenuItem v-else @click="changeComponentStatus(component.id, 1)">
                                                <Circle class="mr-2 h-3.5 w-3.5" />启用
                                            </DropdownMenuItem>
                                            <DropdownMenuItem>
                                                <Edit class="mr-2 h-3.5 w-3.5" /> 编辑
                                            </DropdownMenuItem>
                                            <DropdownMenuItem class="text-red-600 focus:text-red-600">
                                                <Trash2 class="mr-2 h-3.5 w-3.5" /> 删除
                                            </DropdownMenuItem>
                                        </DropdownMenuContent>
                                    </DropdownMenu>
                                </TableCell>
                            </TableRow>

                            <!-- 子行：版本列表 -->
                            <TableRow v-if="openId === component.id">
                                <TableCell colspan="12" class="p-0 border-t-0 bg-muted/20 shadow-inner">
                                    <div class="pl-12 pr-4 py-3">
                                        <div class="rounded-md border bg-background overflow-hidden">
                                            <div
                                                class="flex items-center justify-between bg-muted/30 px-3 py-1.5 border-b">
                                                <div class="flex items-center gap-2">
                                                    <span class="text-xs font-medium text-foreground/80">版本规格列表</span>
                                                    <Badge variant="secondary" class="h-4 px-1 text-[9px] font-normal">
                                                        {{ component.variants!.length }} 个
                                                    </Badge>
                                                </div>
                                                <Button size="icon" variant="ghost" class="h-5 w-5 hover:bg-background">
                                                    <Plus class="h-3 w-3" />
                                                </Button>
                                            </div>

                                            <Table>
                                                <TableHeader>
                                                    <TableRow class="hover:bg-transparent border-b-0">
                                                        <TableHead class="h-8 w-[60px] text-center text-xs">ID
                                                        </TableHead>
                                                        <TableHead class="h-8 w-[120px] text-xs">版本名</TableHead>
                                                        <TableHead class="h-8 w-[80px] text-center text-xs">价格
                                                        </TableHead>
                                                        <TableHead class="h-8 text-xs hidden md:table-cell">描述
                                                        </TableHead>
                                                        <TableHead class="h-8 w-[60px] text-center text-xs">库存
                                                        </TableHead>
                                                        <TableHead class="h-8 w-[70px] text-center text-xs">状态
                                                        </TableHead>
                                                        <TableHead class="h-8 w-[140px] text-center text-xs">快捷操作
                                                        </TableHead>
                                                        <TableHead class="h-8 w-[40px]"></TableHead>
                                                    </TableRow>
                                                </TableHeader>
                                                <TableBody>
                                                    <TableRow v-for="variant in component.variants" :key="variant.id"
                                                        class="hover:bg-muted/10 border-b border-muted/40 last:border-0">
                                                        <TableCell
                                                            class="py-2 text-center font-mono text-[10px] text-muted-foreground">
                                                            {{ variant.id }}
                                                        </TableCell>
                                                        <TableCell class="py-2 text-xs font-medium">
                                                            {{ variant.variantName }}
                                                        </TableCell>
                                                        <TableCell
                                                            class="py-2 text-center text-xs font-medium text-red-600">
                                                            ¥{{ variant.price }}
                                                        </TableCell>
                                                        <TableCell class="py-2 hidden md:table-cell">
                                                            <div class="truncate max-w-[250px] text-xs text-muted-foreground"
                                                                :title="variant.description">
                                                                {{ variant.description || '-' }}
                                                            </div>
                                                        </TableCell>
                                                        <TableCell class="py-2 text-center text-xs font-mono">
                                                            {{ variant.stock }}
                                                        </TableCell>
                                                        <TableCell class="py-2 text-center">
                                                            <div class="flex items-center justify-center gap-1.5">
                                                                <span class="flex h-1.5 w-1.5 rounded-full"
                                                                    :class="variant.status === 1 ? 'bg-green-500' : 'bg-red-500'" />
                                                                <span class="text-xs text-muted-foreground">
                                                                    {{ variant.status === 1 ? "启用" : "停用" }}
                                                                </span>
                                                            </div>
                                                        </TableCell>
                                                        <TableCell class="py-2 text-center">
                                                            <div class="flex items-center justify-center gap-1">
                                                                <Button variant="outline" class="h-6 px-2 text-[10px]">
                                                                    补货
                                                                </Button>
                                                                <Button variant="outline" v-if="variant.status === 1"
                                                                    @click="changeVariantStatus(variant, 0)"
                                                                    class="h-6 px-2 text-[10px]">禁用
                                                                </Button>
                                                                <Button variant="outline" v-else
                                                                    @click="changeVariantStatus(variant, 1)"
                                                                    class="h-6 px-2 text-[10px]">启用
                                                                </Button>
                                                            </div>
                                                        </TableCell>
                                                        <TableCell>
                                                            <DropdownMenu>
                                                                <DropdownMenuTrigger as-child>
                                                                    <Button variant="ghost" size="icon" class="h-8 w-8">
                                                                        <MoreHorizontal class="h-4 w-4" />
                                                                    </Button>
                                                                </DropdownMenuTrigger>
                                                                <DropdownMenuContent align="end">
                                                                    <DropdownMenuItem>
                                                                        <Edit class="mr-2 h-4 w-4" /> 编辑
                                                                    </DropdownMenuItem>
                                                                    <DropdownMenuSeparator />
                                                                    <DropdownMenuItem class="text-red-600">
                                                                        <AlertDialog>
                                                                            <AlertDialogTrigger as-child>
                                                                                <Trash2 class="mr-2 h-4 w-4" /> 删除
                                                                            </AlertDialogTrigger>
                                                                            <AlertDialogContent>
                                                                                <AlertDialogHeader>
                                                                                    <AlertDialogTitle>确定删除吗?
                                                                                    </AlertDialogTitle>
                                                                                    <AlertDialogDescription>删除后无法找回
                                                                                    </AlertDialogDescription>
                                                                                </AlertDialogHeader>
                                                                                <AlertDialogFooter>
                                                                                    <AlertDialogCancel>取消
                                                                                    </AlertDialogCancel>
                                                                                    <AlertDialogAction @click="">确定
                                                                                    </AlertDialogAction>
                                                                                </AlertDialogFooter>
                                                                            </AlertDialogContent>
                                                                        </AlertDialog>
                                                                    </DropdownMenuItem>
                                                                </DropdownMenuContent>
                                                            </DropdownMenu>
                                                        </TableCell>
                                                    </TableRow>
                                                </TableBody>
                                            </Table>
                                        </div>
                                    </div>
                                </TableCell>
                            </TableRow>
                        </template>
                    </TableBody>
                </Table>
            </div>
        </div>

        <!-- 分页固定区域：底部固定 -->
        <div class="absolute bottom-0 left-0 right-0 h-16 border-t flex items-center p-2">
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
    import { onMounted, ref, watch } from 'vue'
    import axios from '@/utils/myAxios'
    import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow, } from '@/components/ui/table'
    import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuSeparator, DropdownMenuTrigger, } from '@/components/ui/dropdown-menu'
    import { Badge } from '@/components/ui/badge'
    import { Button } from '@/components/ui/button'
    import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle, AlertDialogTrigger, } from '@/components/ui/alert-dialog'
    import { ChevronRight, MoreHorizontal, Edit, Trash2, ImageIcon, Plus, CircleOff, Circle } from 'lucide-vue-next'
    import { Pagination, PaginationContent, PaginationEllipsis, PaginationItem, PaginationNext, PaginationPrevious, } from '@/components/ui/pagination'
    import dayjs from 'dayjs'
    import relativeTime from 'dayjs/plugin/relativeTime'
    import 'dayjs/locale/zh-cn'
    dayjs.extend(relativeTime)
    dayjs.locale('zh-cn')

    onMounted(() => {
        getComponent()
    })

    interface Variant {
        id: number
        componentId: number
        variantName: string
        price: number
        description: string
        stock: number
        status: number
        createdBy: string
        createdAt: string
    }

    interface Component {
        id: number
        name: string
        categoryId: number
        images: string[]
        commonDescription: string
        status: number
        sort: number
        createdBy: string
        createdAt: string
        variants?: Variant[]
    }

    const components = ref<Component[]>([])
    const pageNum = ref(1)
    const pageSize = ref(10)
    const total = ref(0)

    watch(
        () => pageNum.value,
        () => {
            getComponent()
        }
    )

    const getComponent = async () => {
        try {
            const response = await axios.get("/api/admin/shop/component", {
                params: {
                    pageNum: pageNum.value,
                    pageSize: pageSize.value
                }
            })
            const resData = response.data.data
            components.value = resData.list
            total.value = resData.total
        } catch (error) {
            console.log(error)
        }
    }


    const getVariant = async (id: number) => {
        try {
            const response = await axios.get("/api/admin/shop/variant", {
                params: {
                    id: id
                }
            })
            return response.data.data
        } catch (error) {
            console.log(error)
            return [] // 出错返回空数组
        }
    }

    const changeComponentStatus = async (id: number, status: number) => {
        try {
            if (status === 1) {
                await axios.patch("/api/admin/shop/component/activate", { id })
            } else {
                await axios.patch("/api/admin/shop/component/deactivate", { id })
            }
            const targetComponent = components.value.find(component => component.id === id)

            targetComponent!.status = status

            if (targetComponent!.variants && targetComponent!.variants.length > 0) {
                targetComponent!.variants.forEach(variant => {
                    variant.status = status
                })
            }
        } catch (error) {
            console.error(error)
        }
    }

    const changeVariantStatus = async (variant: Variant, status: number) => {
        try {
            let response
            if (status === 1) {
                response = await axios.patch("/api/admin/shop/variant/activate", {
                    id: variant.id
                })
            } else {
                response = await axios.patch("/api/admin/shop/variant/deactivate", {
                    id: variant.id
                })
            }
            if (response.data.code === 1) {
                variant.status = status
            }

        } catch (error) {
            console.error(error)
        }
    }

    // 状态管理：存储当前展开的行 ID
    const openId = ref<number | null>(null)

    const open = async (component: Component) => {
        const id = component.id

        if (openId.value === id) {
            openId.value = null // 折叠当前行
            return
        }

        // 已有数据直接展开
        if (component.variants && component.variants.length > 0) {
            openId.value = id
            return
        }

        // 加载变体数据后展开
        const variantsData = await getVariant(id)
        component.variants = variantsData
        openId.value = id
    }
</script>