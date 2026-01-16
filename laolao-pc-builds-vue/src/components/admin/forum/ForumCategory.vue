<template>
    <div class="h-full w-full overflow-hidden relative">
        <!-- 有数据时显示表格 -->
        <Table v-if="categories && categories.length > 0">
            <TableHeader>
                <TableRow>
                    <TableHead class="w-[60px] text-center">ID</TableHead>
                    <TableHead>分类信息</TableHead>
                    <TableHead class="w-[100px]">描述</TableHead>
                    <TableHead class="text-center w-[100px]">状态</TableHead>
                    <TableHead class="text-center w-[80px]">排序权重</TableHead>
                    <TableHead class="hidden xl:table-cell w-[120px] text-center">创建信息</TableHead>
                    <TableHead class="hidden xl:table-cell w-[120px] text-center">最后更新</TableHead>
                    <!-- 新增 -->
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
                        <DialogContent class="sm:max-w-[500px]">
                            <DialogHeader>
                                <DialogTitle>新增分类</DialogTitle>
                                <DialogDescription></DialogDescription>
                            </DialogHeader>

                            <div class="grid gap-6 py-4">
                                <!-- 图片上传区域 -->
                                <div class="flex flex-col items-center gap-4" @click="showAddDialog = true">
                                    <div
                                        class="relative group cursor-pointer overflow-hidden rounded-xl border shadow-sm">
                                        <img :src="newCategory.image[0]" v-if="newCategory.image.length > 0"
                                            class="h-32 w-32 object-cover transition-transform duration-300 group-hover:scale-105" />
                                        <div v-else class="h-32 w-32"></div>
                                        <div
                                            class="absolute inset-0 bg-black/40 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity">
                                            <span
                                                class="text-white text-xs font-medium bg-black/50 px-2 py-1 rounded">添加图片</span>
                                        </div>
                                    </div>
                                    <FileManager v-model:open="showAddDialog" v-model="newCategory.image" :max-files="1"
                                        upload-api="/api/common/file/upload" delete-api="/api/common/file/delete"
                                        :upload-extra-data="{ type: 'laolaoPC/forum/category' }" />
                                </div>

                                <!-- 表单字段区域 -->
                                <div class="grid gap-4">
                                    <div class="grid gap-2">
                                        <Label for="name">分类名称</Label>
                                        <Input id="name" v-model="newCategory!.name" />
                                    </div>

                                    <div class="grid gap-2">
                                        <Label for="name">描述</Label>
                                        <Textarea v-model="newCategory!.description" placeholder="描述..." />
                                    </div>
                                </div>
                            </div>

                            <DialogFooter>
                                <DialogClose as-child>
                                    <Button type="submit" @click="add()"
                                        :disabled="newCategory.description === '' || newCategory.image.length === 0 || newCategory.name === ''">
                                        添加
                                    </Button>
                                </DialogClose>
                            </DialogFooter>
                        </DialogContent>
                    </Dialog>
                </TableRow>
            </TableHeader>

            <TableBody>
                <TableRow v-for="category in categories" :key="category.id">
                    <!-- ID -->
                    <TableCell class="font-medium text-center">{{ category.id }}</TableCell>

                    <!-- 分类：图片 + 名称 -->
                    <TableCell>
                        <div class="flex items-center gap-3">
                            <Avatar class="h-9 w-9 rounded-lg border">
                                <AvatarImage :src="category.image" class="object-cover" />
                                <AvatarFallback class="rounded-lg">IMG</AvatarFallback>
                            </Avatar>
                            <span class="font-medium text-sm">{{ category.name }}</span>
                        </div>
                    </TableCell>

                    <!-- 描述 -->
                    <TableCell>
                        <div class="max-w-[200px] truncate" :title="category.description">
                            {{ category.description || '-' }}
                        </div>
                    </TableCell>

                    <!-- 状态 -->
                    <TableCell>
                        <div class="flex items-center justify-center gap-2">
                            <span class="flex h-2 w-2 rounded-full"
                                :class="category.status === 1 ? 'bg-green-500' : 'bg-red-500'" />
                            <span class="text-sm text-muted-foreground">
                                {{ category.status === 1 ? "启用" : "停用" }}
                            </span>
                        </div>
                    </TableCell>

                    <!-- 排序 -->
                    <TableCell class="text-center">
                        <span class="font-mono text-sm">{{ category.sort }}</span>
                    </TableCell>

                    <TableCell class="hidden xl:table-cell text-center text-[10px] text-muted-foreground leading-tight">
                        <div>{{ category.createdBy }}</div>
                        <div class="opacity-70 mt-0.5">{{ dayjs(category.createdAt).format('YYYY-MM-DD') }}
                        </div>
                    </TableCell>

                    <TableCell class="hidden xl:table-cell text-center text-[10px] text-muted-foreground leading-tight">
                        <div>{{ category.updatedBy }}</div>
                        <div class="opacity-70 mt-0.5">{{ dayjs(category.updatedAt).format('YYYY-MM-DD') }}
                        </div>
                    </TableCell>

                    <!-- 操作 -->
                    <TableCell class="text-center">
                        <div class="flex justify-center gap-2">
                            <Dialog>
                                <DialogTrigger as-child>
                                    <Button variant="ghost" size="sm" @click="initUpdateData(category)">
                                        编辑
                                    </Button>
                                </DialogTrigger>
                                <DialogContent class="sm:max-w-[500px]">
                                    <DialogHeader>
                                        <DialogTitle>编辑分类</DialogTitle>
                                        <DialogDescription>修改分类的详细信息。</DialogDescription>
                                    </DialogHeader>

                                    <div class="grid gap-6 py-4">
                                        <!-- 图片上传区域 -->
                                        <div class="flex flex-col items-center gap-4" @click="showUpdateDialog = true">
                                            <div
                                                class="relative group cursor-pointer overflow-hidden rounded-xl border shadow-sm">
                                                <img :src="category.image"
                                                    class="h-32 w-32 object-cover transition-transform duration-300 group-hover:scale-105" />
                                                <div
                                                    class="absolute inset-0 bg-black/40 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity">
                                                    <span
                                                        class="text-white text-xs font-medium bg-black/50 px-2 py-1 rounded">更换图片</span>
                                                </div>
                                            </div>
                                            <FileManager v-model:open="showUpdateDialog" v-model="updateData.image"
                                                :max-files="1" upload-api="/api/common/file/upload"
                                                delete-api="/api/common/file/delete"
                                                :upload-extra-data="{ type: 'laolaoPC/forum/category' }" />
                                        </div>

                                        <!-- 表单字段区域 -->
                                        <div class="grid gap-4">
                                            <div class="grid gap-2">
                                                <Label for="name">分类名称</Label>
                                                <Input id="name" v-model="updateData!.name" />
                                            </div>

                                            <div class="grid gap-2">
                                                <Label for="name">描述</Label>
                                                <Textarea v-model="updateData!.description" placeholder="描述..." />
                                            </div>

                                            <div class="grid gap-2">
                                                <Label for="sort">排序权重</Label>
                                                <Input id="sort" type="number" v-model="updateData!.sort" />
                                            </div>
                                        </div>
                                    </div>

                                    <DialogFooter>
                                        <DialogClose as-child>
                                            <Button type="submit" @click="update(category.id)"
                                                :disabled="!updateData!.name">
                                                保存更改
                                            </Button>
                                        </DialogClose>
                                    </DialogFooter>
                                </DialogContent>
                            </Dialog>

                            <!-- 状态按钮 -->
                            <Button variant="destructive" size="sm" v-if="category.status === 1"
                                @click="changeStatus(category.id, 0)">
                                禁用
                            </Button>
                            <Button variant="outline" size="sm" v-else @click="changeStatus(category.id, 1)">
                                启用
                            </Button>

                            <AlertDialog>
                                <AlertDialogTrigger as-child>
                                    <Button variant="destructive" size="sm">
                                        删除
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
                                        <AlertDialogAction @click="deleteCategory(category.id)">确定</AlertDialogAction>
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
            <Button variant="outline" size="sm" @click="getCategory">
                返回全部数据
            </Button>
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
    import { onMounted, ref, watch } from 'vue';
    import axios from '@/utils/myAxios'
    import dayjs from 'dayjs'
    import relativeTime from 'dayjs/plugin/relativeTime'
    import 'dayjs/locale/zh-cn'
    dayjs.extend(relativeTime)
    dayjs.locale('zh-cn')
    import { Button } from '@/components/ui/button'
    import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow, } from '@/components/ui/table'
    import { Avatar, AvatarFallback, AvatarImage, } from '@/components/ui/avatar'
    import { Dialog, DialogClose, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, } from '@/components/ui/dialog'
    import { Input } from '@/components/ui/input'
    import { Label } from '@/components/ui/label'
    import FileManager from '@/components/common/FileManager.vue';
    import { Textarea } from '@/components/ui/textarea'
    import { Pagination, PaginationContent, PaginationEllipsis, PaginationItem, PaginationNext, PaginationPrevious, } from '@/components/ui/pagination'
    import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle, AlertDialogTrigger, } from '@/components/ui/alert-dialog'
    import { Ghost, Plus } from 'lucide-vue-next'
    import { useCommonStore } from '@/stores/CommonStore'
    const commonStore = useCommonStore()

    watch(
        () => commonStore.search.search,
        (newSearch) => {
            if (newSearch) {
                getCategory()
                commonStore.search.search = false
                commonStore.search.searchContent = ''
            }
        }
    )


    onMounted(() => {
        getCategory()
    })

    interface Category {
        id: number
        name: string
        description: string
        image: string
        status: number
        sort: number
        createdBy: string
        createdAt: string
        updatedBy: string
        updatedAt: string
    }

    interface NewCategory {
        name: string
        description: string
        image: string[]
    }

    const categories = ref<Category[]>([])
    const pageNum = ref(1)
    const pageSize = ref(10)
    const total = ref(0)

    watch(
        () => pageNum.value,
        () => {
            getCategory()
        }
    )

    const getCategory = async () => {
        try {
            const response = await axios.get("/api/admin/forum/category/list", {
                params: {
                    pageNum: pageNum.value,
                    pageSize: pageSize.value,
                    searchContent: commonStore.search.searchContent
                }
            })
            const resData = response.data.data
            categories.value = resData.list
            total.value = resData.total
        } catch (error) {
            console.log(error)
        }
    }

    const changeStatus = async (categoryId: number, status: number) => {
        try {
            if (status === 1) {
                await axios.patch("/api/admin/forum/category/activate", {
                    id: categoryId
                })
            }
            else {
                await axios.patch("/api/admin/forum/category/deactivate", {
                    id: categoryId
                })
            }
            categories.value!.find(category => category.id === categoryId)!.status = status
        } catch (error) {
            console.log(error)
        }
    }

    const updateData = ref({
        id: 0,
        name: "",
        description: "",
        image: [] as string[],
        sort: 0
    })

    const initUpdateData = (category: Category) => {
        // 1. 手动构建 updateData，避免深拷贝覆盖 image 数组类型
        updateData.value = {
            id: category.id,
            name: category.name,
            description: category.description,
            image: category.image ? [category.image] : [], // 强制转为数组
            sort: category.sort,
        }
    };

    const update = async (id: number) => {
        try {
            await axios.patch("/api/admin/forum/category/update", {
                id: id,
                name: updateData.value?.name,
                description: updateData.value?.description,
                image: updateData.value.image[0],
                sort: updateData.value?.sort,
            })
            const category = categories.value!.find(category => category.id === id)
            category!.name = updateData.value.name
            category!.description = updateData.value.description
            category!.image = updateData.value.image[0]!
            category!.sort = updateData.value.sort
        } catch (error) {
            console.log(error)
        }
    }

    const newCategory = ref<NewCategory>({
        name: '',
        description: '',
        image: []
    })

    const add = async () => {
        try {
            await axios.post("/api/admin/forum/category/add", {
                name: newCategory.value?.name,
                description: newCategory.value.description,
                image: newCategory.value.image[0],
            })
            newCategory.value.name = ''
            newCategory.value.description = ''
            newCategory.value.image = []
            getCategory()
        } catch (error) {
            console.log(error)
        }
    }

    const deleteCategory = async (id: number) => {
        try {
            await axios.delete("/api/admin/forum/category/delete", {
                params: {
                    id: id
                }
            })
            categories.value = categories.value.filter(category => category.id !== id)
        } catch (error) {
            console.log(error)
        }
    }

    // 控制弹窗开关
    const showAddDialog = ref(false)
    const showUpdateDialog = ref(false)
</script>