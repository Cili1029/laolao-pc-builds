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
                            <TableHead class="w-[80px] text-center">价格</TableHead>
                            <TableHead class="h-8 w-[60px] text-center text-xs">库存</TableHead>
                            <TableHead class="w-[80px] text-center">状态</TableHead>
                            <TableHead class="w-[60px] text-center">排序权重</TableHead>
                            <TableHead class="hidden xl:table-cell w-[120px] text-center">创建信息</TableHead>
                            <TableHead class="w-[80px] text-center">操作</TableHead>
                            <Dialog>
                                <DialogTrigger as-child>
                                    <TableHead class="w-[50px] text-center">
                                        <Button size="icon" variant="ghost" class="h-5 w-5 hover:bg-background">
                                            <Plus class="h-3 w-3" />
                                        </Button>
                                    </TableHead>
                                </DialogTrigger>
                                <DialogContent class="sm:max-w-[500px]">
                                    <DialogHeader>
                                        <DialogTitle>新增分类</DialogTitle>
                                        <DialogDescription></DialogDescription>
                                    </DialogHeader>
                                    <div class="grid gap-6 py-4">
                                        <!-- 表单字段区域 -->
                                        <div class="grid gap-4">
                                            <div class="grid gap-2">
                                                <Label for="name">整机名</Label>
                                                <Input id="name" v-model="newBundle!.name" />
                                            </div>
                                            <div class="grid gap-2">
                                                <Label>组件类别</Label>
                                                <!-- 注意：Select 的 value 需要转为字符串匹配 -->
                                                <Select v-model="newBundle!.categoryId">
                                                    <SelectTrigger>
                                                        <SelectValue placeholder="请选择类别" />
                                                    </SelectTrigger>
                                                    <SelectContent>
                                                        <SelectGroup>
                                                            <SelectItem v-for="category in categories"
                                                                :key="category.id" :value="category.id">{{ category.name
                                                                }}</SelectItem>
                                                        </SelectGroup>
                                                    </SelectContent>
                                                </Select>
                                            </div>
                                            <div class="grid gap-2">
                                                <Label for="name">价格</Label>
                                                <Input id="name" type="number" v-model="newBundle.price"/>
                                            </div>
                                            <div class="grid gap-2">
                                                <Label for="name">描述</Label>
                                                <Textarea v-model="newBundle.description" placeholder="描述..." />
                                            </div>
                                        </div>
                                    </div>

                                    <DialogFooter class="flex flex-col gap-3 sm:flex-row sm:justify-start">
                                        <Button @click="showAddDialog = true">
                                            <span class="icon-[charm--folder]"></span>
                                            上传图片
                                        </Button>
                                        <FileManager v-model:open="showAddDialog" v-model="newBundle.images"
                                            :max-files="5" upload-api="/api/common/upload"
                                            delete-api="/api/common/delete"
                                            :upload-extra-data="{ type: 'laolaoPC/shop/bundle' }" />
                                        <DialogClose as-child class="sm:ml-auto">
                                            <Button type="submit" @click="addBundle()"
                                                :disabled="!newBundle.name || !newBundle.categoryId">
                                                添加
                                            </Button>
                                        </DialogClose>
                                    </DialogFooter>
                                </DialogContent>
                            </Dialog>
                        </TableRow>
                    </TableHeader>

                    <TableBody>
                        <template v-for="bundle in bundles" :key="bundle.id">
                            <!-- 父行 -->
                            <TableRow class="cursor-pointer group transition-all duration-200"
                                :class="openId === bundle.id ? 'bg-muted/60' : 'hover:bg-muted/30'"
                                @click="open(bundle)">
                                <TableCell class="py-3 px-2 text-center">
                                    <div
                                        class="flex items-center justify-center h-6 w-6 rounded-md hover:bg-muted/80 transition-colors mx-auto">
                                        <ChevronRight
                                            class="h-4 w-4 text-muted-foreground transition-transform duration-200"
                                            :class="openId === bundle.id ? 'rotate-90 text-foreground' : ''" />
                                    </div>
                                </TableCell>

                                <TableCell class="py-3 text-center">
                                    <div
                                        class="relative h-10 w-10 rounded-md border bg-muted flex items-center justify-center overflow-hidden mx-auto">
                                        <template v-if="bundle.images.length > 0">
                                            <img :src="bundle.images[0]!" class="h-full w-full object-cover" />
                                            <div v-if="bundle.images?.length > 1"
                                                class="absolute bottom-0 right-0 bg-black/70 text-white text-[9px] px-1 rounded-tl-sm font-medium leading-tight">
                                                +{{ bundle.images.length - 1 }}
                                            </div>
                                        </template>
                                        <ImageIcon v-else class="h-4 w-4 text-muted-foreground/50" />
                                    </div>
                                </TableCell>

                                <TableCell class="font-mono text-xs text-muted-foreground text-center">
                                    {{ bundle.id }}
                                </TableCell>

                                <TableCell class="font-medium text-foreground">
                                    <div class="truncate max-w-[200px] lg:max-w-[300px]" :title="bundle.name">
                                        {{ bundle.name }}
                                    </div>
                                </TableCell>

                                <TableCell>
                                    <Badge variant="outline" class="font-normal text-xs whitespace-nowrap">
                                        <p v-for="category in categories" :key="category.id"
                                            v-show="category.id === bundle.categoryId">
                                            {{ category.name }}
                                        </p>
                                    </Badge>
                                </TableCell>

                                <TableCell class="hidden md:table-cell">
                                    <p class="text-sm text-muted-foreground truncate max-w-[200px]"
                                        :title="bundle.description">
                                        {{ bundle.description }}
                                    </p>
                                </TableCell>

                                <TableCell class="text-center font-medium text-red-600">
                                    ¥{{ bundle.price }}
                                </TableCell>

                                <TableCell class="text-center font-mono">
                                    {{ bundle.stock }}
                                </TableCell>

                                <TableCell class="text-center">
                                    <div class="flex items-center justify-center gap-1.5">
                                        <span class="flex h-1.5 w-1.5 rounded-full"
                                            :class="bundle.status === 1 ? 'bg-green-500' : 'bg-red-500'" />
                                        <span class="text-xs text-muted-foreground">
                                            {{ bundle.status === 1 ? "启用" : "停用" }}
                                        </span>
                                    </div>
                                </TableCell>

                                <TableCell class="text-center font-mono text-xs">
                                    {{ bundle.sort }}
                                </TableCell>

                                <TableCell
                                    class="hidden xl:table-cell text-center text-[10px] text-muted-foreground leading-tight">
                                    <div>{{ bundle.createdBy }}</div>
                                    <div class="opacity-70 mt-0.5">{{ dayjs(bundle.createdAt).format('YYYY-MM-DD') }}
                                    </div>
                                </TableCell>

                                <TableCell @click.stop
                                    class="hidden xl:table-cell text-center text-[10px] text-muted-foreground leading-tight">
                                    <div class="flex items-center justify-center gap-1">
                                        <Dialog>
                                            <DialogTrigger as-child>
                                                <Button variant="outline" @click="newStock = bundle.stock">
                                                    补货
                                                </Button>
                                            </DialogTrigger>
                                            <DialogContent class="sm:max-w-[425px]">
                                                <DialogHeader>
                                                    <DialogTitle>补货</DialogTitle>
                                                    <DialogDescription>
                                                        输入更新后的数量
                                                    </DialogDescription>
                                                </DialogHeader>
                                                <div class="grid gap-3">
                                                    <Label>数量</Label>
                                                    <Input type="number" v-model="newStock" />
                                                </div>
                                                <DialogFooter>
                                                    <DialogClose as-child>
                                                        <Button @click="updateStock(bundle)">
                                                            修改
                                                        </Button>
                                                    </DialogClose>
                                                </DialogFooter>
                                            </DialogContent>
                                        </Dialog>

                                        <Dialog>
                                            <DialogTrigger as-child>
                                                <Button variant="outline"
                                                    @click="updateBundleData = JSON.parse(JSON.stringify(bundle))">
                                                    编辑
                                                </Button>
                                            </DialogTrigger>
                                            <DialogContent class="sm:max-w-[1000px]">
                                                <DialogHeader>
                                                    <DialogTitle>编辑</DialogTitle>
                                                    <DialogDescription></DialogDescription>
                                                </DialogHeader>
                                                <div class="flex space-x-2">
                                                    <div class="gap-6 py-4 w-1/2">
                                                        <!-- 表单字段区域 -->
                                                        <div class="grid gap-4">
                                                            <div class="grid gap-2">
                                                                <Label for="name">组件名</Label>
                                                                <Input id="name" v-model="updateBundleData!.name" />
                                                            </div>
                                                            <div class="grid gap-2">
                                                                <Label>组件类别</Label>
                                                                <Select v-model="updateBundleData!.categoryId">
                                                                    <SelectTrigger>
                                                                        <SelectValue placeholder="请选择类别" />
                                                                    </SelectTrigger>
                                                                    <SelectContent>
                                                                        <SelectGroup>
                                                                            <SelectItem v-for="category in categories"
                                                                                :key="category.id" :value="category.id">
                                                                                {{ category.name
                                                                                }}</SelectItem>
                                                                        </SelectGroup>
                                                                    </SelectContent>
                                                                </Select>
                                                            </div>

                                                            <div class="grid gap-2">
                                                                <Label for="name">价格</Label>
                                                                <Input id="name" type="number"
                                                                    v-model="updateBundleData!.price" />
                                                            </div>

                                                            <div class="grid gap-2">
                                                                <Label for="name">排序权重</Label>
                                                                <Input v-model="updateBundleData.sort" type="number" />
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="w-1/2">
                                                        <div class="grid gap-2">
                                                            <Label for="name">描述</Label>
                                                            <Textarea v-model="updateBundleData.description"
                                                                placeholder="描述..." />
                                                        </div>
                                                    </div>
                                                </div>
                                                <DialogFooter class="flex flex-col gap-3 sm:flex-row sm:justify-start">
                                                    <Button @click="showUpdateDialog = true">
                                                        <span class="icon-[charm--folder]"></span>
                                                        图片管理
                                                    </Button>
                                                    <FileManager v-model:open="showUpdateDialog"
                                                        v-model="updateBundleData.images" :max-files="5"
                                                        upload-api="/api/common/upload" delete-api="/api/common/delete"
                                                        :upload-extra-data="{ type: 'laolaoPC/shop/bundle' }" />
                                                    <DialogClose as-child class="sm:ml-auto">
                                                        <Button type="submit" @click="updateBundle(bundle)"
                                                            :disabled="!updateBundleData.name || !updateBundleData.categoryId">
                                                            修改
                                                        </Button>
                                                    </DialogClose>
                                                </DialogFooter>
                                            </DialogContent>
                                        </Dialog>
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
                                            <DropdownMenuItem v-if="bundle.status === 1"
                                                @click="changeStatus(bundle.id, 0)">
                                                <CircleOff class="mr-2 h-3.5 w-3.5" />禁用
                                            </DropdownMenuItem>
                                            <DropdownMenuItem v-else @click="changeStatus(bundle.id, 1)">
                                                <Circle class="mr-2 h-3.5 w-3.5" />启用
                                            </DropdownMenuItem>
                                            <DropdownMenuItem class="text-red-600 focus:text-red-600"
                                                @click="openDeleteDialog = true, deleteId = bundle.id">
                                                <Trash2 class="mr-2 h-3.5 w-3.5" /> 删除
                                            </DropdownMenuItem>
                                        </DropdownMenuContent>
                                    </DropdownMenu>
                                </TableCell>
                            </TableRow>

                            <!-- 子行：版本列表 -->
                            <TableRow v-if="openId === bundle.id">
                                <TableCell colspan="12" class="p-0 border-t-0 bg-muted/20 shadow-inner">
                                    <div class="pl-12 pr-4 py-3">
                                        <div class="rounded-md border bg-background overflow-hidden">
                                            <div
                                                class="flex items-center justify-between bg-muted/30 px-3 py-1.5 border-b">
                                                <span class="text-xs font-medium text-foreground/80">所包含的版本列表</span>
                                                <Dialog>
                                                    <DialogTrigger as-child>
                                                        <Button size="icon" variant="ghost"
                                                            class="h-5 w-5 hover:bg-background">
                                                            <Plus class="h-3 w-3" />
                                                        </Button>
                                                    </DialogTrigger>
                                                    <DialogContent class="sm:max-w-[800px]">
                                                        <DialogHeader>
                                                            <DialogTitle>添加组件</DialogTitle>
                                                            <DialogDescription>
                                                                搜索，添加，确定数量，提交
                                                            </DialogDescription>
                                                        </DialogHeader>
                                                        <div class="flex flex-col space-y-2">
                                                            <div class="flex w-full max-w-sm items-center space-x-2">
                                                                <Input placeholder="组件名，ID" v-model="searchContent" />
                                                                <Button type="submit" @click="search()">
                                                                    搜索
                                                                </Button>
                                                            </div>
                                                            <div class=" max-h-100 overflow-y-auto scrollbar-edge">
                                                                <Table>
                                                                    <TableHeader>
                                                                        <TableRow>
                                                                            <TableHead class="w-[100px]">
                                                                                组件名
                                                                            </TableHead>
                                                                            <TableHead>版本名</TableHead>
                                                                            <TableHead>价格</TableHead>
                                                                            <TableHead>操作</TableHead>
                                                                        </TableRow>
                                                                    </TableHeader>
                                                                    <TableBody>
                                                                        <TableRow v-for="variant in searchVariant"
                                                                            :key="variant.variantId">
                                                                            <TableCell class="font-medium">
                                                                                {{ variant.name }}
                                                                            </TableCell>
                                                                            <TableCell>{{ variant.variantName }}
                                                                            </TableCell>
                                                                            <TableCell>{{ variant.price }}
                                                                            </TableCell>
                                                                            <TableCell>
                                                                                <Dialog>
                                                                                    <DialogTrigger as-child>
                                                                                        <Button variant="outline">
                                                                                            数量
                                                                                        </Button>
                                                                                    </DialogTrigger>
                                                                                    <DialogContent
                                                                                        class="sm:max-w-[425px]">
                                                                                        <DialogHeader>
                                                                                            <DialogTitle>输入一个数量
                                                                                            </DialogTitle>
                                                                                            <DialogDescription>
                                                                                            </DialogDescription>
                                                                                        </DialogHeader>
                                                                                        <div class="grid gap-3">
                                                                                            <Label>数量</Label>
                                                                                            <Input type="number"
                                                                                                v-model="addVariantQuantity" />
                                                                                        </div>
                                                                                        <DialogFooter>
                                                                                            <DialogClose as-child>
                                                                                                <Button
                                                                                                    @click="addVariant(bundle, variant.variantId)">
                                                                                                    添加
                                                                                                </Button>
                                                                                            </DialogClose>
                                                                                        </DialogFooter>
                                                                                    </DialogContent>
                                                                                </Dialog>
                                                                            </TableCell>
                                                                        </TableRow>
                                                                    </TableBody>
                                                                </Table>
                                                            </div>
                                                        </div>
                                                        <DialogFooter>
                                                            <DialogClose as-child>
                                                                <Button>
                                                                    关闭
                                                                </Button>
                                                            </DialogClose>
                                                        </DialogFooter>
                                                    </DialogContent>
                                                </Dialog>
                                            </div>

                                            <Table>
                                                <TableHeader>
                                                    <TableRow class="hover:bg-transparent border-b-0">
                                                        <TableHead class="h-8 w-[60px] text-center text-xs">ID
                                                        </TableHead>
                                                        <TableHead class="h-8 w-[120px] text-xs">组件名</TableHead>
                                                        <TableHead class="h-8 w-[120px] text-xs">版本名</TableHead>
                                                        <TableHead class="h-8 w-[80px] text-center text-xs">价格
                                                        </TableHead>
                                                        <TableHead class="h-8 w-[60px] text-center text-xs">数量
                                                        </TableHead>
                                                        <TableHead class="h-8 w-[140px] text-center text-xs">操作
                                                        </TableHead>
                                                    </TableRow>
                                                </TableHeader>
                                                <TableBody>
                                                    <TableRow v-for="variant in bundle.variants" :key="variant.id"
                                                        class="hover:bg-muted/10 border-b border-muted/40 last:border-0">
                                                        <TableCell
                                                            class="py-2 text-center font-mono text-[10px] text-muted-foreground">
                                                            {{ variant.variantId }}
                                                        </TableCell>
                                                        <TableCell class="py-2 text-xs font-medium">
                                                            {{ variant.name }}
                                                        </TableCell>
                                                        <TableCell class="py-2 text-xs font-medium">
                                                            {{ variant.variantName }}
                                                        </TableCell>
                                                        <TableCell
                                                            class="py-2 text-center text-xs font-medium text-red-600">
                                                            ¥{{ variant.price }}
                                                        </TableCell>
                                                        <TableCell class="py-2 text-center text-xs font-mono">
                                                            {{ variant.quantity }}
                                                        </TableCell>
                                                        <TableCell class="py-2 text-center">
                                                            <div class="flex items-center justify-center gap-1">
                                                                <Dialog>
                                                                    <DialogTrigger as-child>
                                                                        <Button variant="outline"
                                                                            @click="newQuantity = variant.quantity"
                                                                            class="h-6 px-2 text-[10px]">数量
                                                                        </Button>
                                                                    </DialogTrigger>
                                                                    <DialogContent class="sm:max-w-[425px]">
                                                                        <DialogHeader>
                                                                            <DialogTitle>数量</DialogTitle>
                                                                            <DialogDescription>
                                                                                输入更新后的数量
                                                                            </DialogDescription>
                                                                        </DialogHeader>
                                                                        <div class="grid gap-3">
                                                                            <Label>数量</Label>
                                                                            <Input type="number"
                                                                                v-model="newQuantity" />
                                                                        </div>
                                                                        <DialogFooter>
                                                                            <DialogClose as-child>
                                                                                <Button
                                                                                    @click="updateQuantity(variant)">
                                                                                    修改
                                                                                </Button>
                                                                            </DialogClose>
                                                                        </DialogFooter>
                                                                    </DialogContent>
                                                                </Dialog>

                                                                <AlertDialog>
                                                                    <AlertDialogTrigger as-child>
                                                                        <Button variant="outline"
                                                                            class="h-6 px-2 text-[10px]">删除
                                                                        </Button>
                                                                    </AlertDialogTrigger>
                                                                    <AlertDialogContent>
                                                                        <AlertDialogHeader>
                                                                            <AlertDialogTitle>确定删除吗?</AlertDialogTitle>
                                                                            <AlertDialogDescription>
                                                                                删除后无法找回
                                                                            </AlertDialogDescription>
                                                                        </AlertDialogHeader>
                                                                        <AlertDialogFooter>
                                                                            <AlertDialogCancel>取消</AlertDialogCancel>
                                                                            <AlertDialogAction
                                                                                @click="deleteVariant(bundle, variant.id)">
                                                                                确定
                                                                            </AlertDialogAction>
                                                                        </AlertDialogFooter>
                                                                    </AlertDialogContent>
                                                                </AlertDialog>
                                                            </div>
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

        <!-- 删除弹框 -->
        <AlertDialog v-model:open="openDeleteDialog">
            <AlertDialogTrigger as-child>
            </AlertDialogTrigger>
            <AlertDialogContent>
                <AlertDialogHeader>
                    <AlertDialogTitle>确定删除吗?</AlertDialogTitle>
                    <AlertDialogDescription>
                        删除后无法找回
                    </AlertDialogDescription>
                </AlertDialogHeader>
                <AlertDialogFooter>
                    <AlertDialogCancel>取消</AlertDialogCancel>
                    <AlertDialogAction @click="deleteById()">确定
                    </AlertDialogAction>
                </AlertDialogFooter>
            </AlertDialogContent>
        </AlertDialog>
    </div>
</template>


<script setup lang="ts">
    import { onMounted, ref, watch } from 'vue'
    import axios from '@/utils/myAxios'
    import FileManager from '@/components/common/FileManager.vue'
    import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow, } from '@/components/ui/table'
    import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger, } from '@/components/ui/dropdown-menu'
    import { Badge } from '@/components/ui/badge'
    import { Button } from '@/components/ui/button'
    import { Label } from '@/components/ui/label'
    import { Textarea } from '@/components/ui/textarea'
    import { Input } from '@/components/ui/input'
    import { Select, SelectContent, SelectGroup, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
    import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle, AlertDialogTrigger, } from '@/components/ui/alert-dialog'
    import { ChevronRight, MoreHorizontal, Trash2, ImageIcon, Plus, CircleOff, Circle } from 'lucide-vue-next'
    import { Dialog, DialogClose, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, } from '@/components/ui/dialog'
    import { Pagination, PaginationContent, PaginationEllipsis, PaginationItem, PaginationNext, PaginationPrevious, } from '@/components/ui/pagination'
    import dayjs from 'dayjs'
    import relativeTime from 'dayjs/plugin/relativeTime'
    import 'dayjs/locale/zh-cn'
    dayjs.extend(relativeTime)
    dayjs.locale('zh-cn')

    onMounted(() => {
        getBundle()
        getCategory()
    })

    interface Category {
        id: number
        productType: number
        name: string
        image: string
        status: number
        sort: number
        createdBy: string
        createdAt: string
    }

    const categories = ref<Category[]>([])

    const getCategory = async () => {
        try {
            const response = await axios.get("/api/admin/shop/category/list", {
                params: {
                    type: 2
                }
            })
            categories.value = response.data.data
        } catch (error) {
            console.log(error)
        }
    }

    interface Variant {
        id: number
        variantId: number
        name: string
        variantName: string
        price: number
        quantity: number
    }

    interface Bundle {
        id: number
        name: string
        categoryId: number
        price: number
        images: string[]
        description: string
        stock: number
        status: number
        sort: number
        createdBy: string
        createdAt: string
        variants?: Variant[]
    }

    const bundles = ref<Bundle[]>([])
    const pageNum = ref(1)
    const pageSize = ref(10)
    const total = ref(0)

    watch(
        () => pageNum.value,
        () => {
            getBundle()
        }
    )

    const getBundle = async () => {
        try {
            const response = await axios.get("/api/admin/shop/bundle", {
                params: {
                    pageNum: pageNum.value,
                    pageSize: pageSize.value
                }
            })
            const resData = response.data.data
            bundles.value = resData.list
            total.value = resData.total
        } catch (error) {
            console.log(error)
        }
    }

    const changeStatus = async (id: number, status: number) => {
        try {
            if (status === 1) {
                await axios.patch("/api/admin/shop/bundle/activate", { id })
            } else {
                await axios.patch("/api/admin/shop/bundle/deactivate", { id })
            }
            const targetBundle = bundles.value.find(bundle => bundle.id === id)

            targetBundle!.status = status
        } catch (error) {
            console.error(error)
        }
    }

    const openDeleteDialog = ref<boolean>(false)
    const deleteId = ref(0)

    const deleteById = async () => {
        try {
            await axios.delete("/api/admin/shop/bundle", {
                params: {
                    id: deleteId.value
                }
            })
            bundles.value = bundles.value.filter(bundle => bundle.id !== deleteId.value)
        } catch (error) {
            console.error(error)
        }
    }

    // 新增
    const newBundle = ref({
        name: "",
        categoryId: 0,
        images: [],
        description: "",
        price: 0
    })

    const addBundle = async () => {
        try {
            await axios.post("/api/admin/shop/bundle/add", {
                name: newBundle.value.name,
                categoryId: newBundle.value.categoryId,
                images: newBundle.value.images,
                description: newBundle.value.description,
                price: newBundle.value.price
            })
            pageNum.value = 1
            getBundle()
        } catch (error) {
            console.log(error)
        }
    }

    // 补货
    const newStock = ref<number>(0)
    const updateStock = async (bundle: Bundle) => {
        try {
            await axios.patch("/api/admin/shop/bundle/stock", {
                id: bundle.id,
                stock: newStock.value
            })
            bundle.stock = newStock.value
        } catch (error) {
            console.log(error)
        }
    }

    // 更新
    const updateBundleData = ref({
        name: '',
        categoryId: 0,
        description: '',
        price: 0,
        images: [],
        sort: 0
    })

    const updateBundle = async (bundle: Bundle) => {
        try {
            await axios.patch("/api/admin/shop/bundle/update", {
                id: bundle.id,
                name: updateBundleData.value.name,
                categoryId: updateBundleData.value.categoryId,
                description: updateBundleData.value.description,
                price: updateBundleData.value.price,
                sort: updateBundleData.value.sort,
                images: updateBundleData.value.images
            })
            bundle.name = updateBundleData.value.name
            bundle.categoryId = updateBundleData.value.categoryId
            bundle.description = updateBundleData.value.description
            bundle.images = updateBundleData.value.images
            bundle.sort = updateBundleData.value.sort
            bundle.price = updateBundleData.value.price
        } catch (error) {
            console.log(error)
        }
    }

    const getVariant = async (id: number) => {
        try {
            const response = await axios.get("/api/admin/shop/bundle/variant", {
                params: {
                    id: id
                }
            })
            return response.data.data
        } catch (error) {
            console.log(error)
        }
    }

    // 新增
    const searchVariant = ref<Variant[]>([])
    const searchContent = ref("")

    const search = async () => {
        try {
            const response = await axios.get("/api/admin/shop/bundle/variant/search", {
                params: {
                    searchContent: searchContent.value
                }
            })
            searchVariant.value = response.data.data
        } catch (error) {
            console.log(error)
        }
    }

    const addVariantQuantity = ref(1)
    const addVariant = async (bundle: Bundle, id: number) => {
        try {
            await axios.patch("/api/admin/shop/bundle/variant/add", {
                bundleId: bundle.id,
                variantId: id,
                quantity: addVariantQuantity.value
            })
            const variantsData = await getVariant(bundle.id)
            bundle.variants = variantsData
        } catch (error) {
            console.log(error)
        }
    }

    // 修改数量
    const newQuantity = ref(0)
    const updateQuantity = async (variant: Variant) => {
        try {
            await axios.patch("/api/admin/shop/bundle/variant/quantity", {
                id: variant.id,
                quantity: newQuantity.value
            })
            variant.quantity = newQuantity.value
        } catch (error) {
            console.log(error)
        }
    }

    const deleteVariant = async (bundle: Bundle, variantId: number) => {
        try {
            await axios.delete("/api/admin/shop/bundle/variant/delete", {
                params: {
                    id: variantId
                }
            })
            bundle.variants = bundle.variants?.filter(variant => variant.id !== variantId)
        } catch (error) {
            console.log(error)
        }
    }


    // 状态管理：存储当前展开的行 ID
    const openId = ref<number | null>(null)

    const open = async (bundle: Bundle) => {
        const id = bundle.id

        if (openId.value === id) {
            openId.value = null // 折叠当前行
            return
        }

        // 已有数据直接展开
        if (bundle.variants && bundle.variants.length > 0) {
            openId.value = id
            return
        }

        // 加载变体数据后展开
        const variantsData = await getVariant(id)
        bundle.variants = variantsData
        openId.value = id
    }


    // 控制弹窗开关
    const showAddDialog = ref(false)
    const showUpdateDialog = ref(false)
</script>