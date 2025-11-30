<template>
    <div class="w-full rounded-md border bg-background">
        <!-- 有数据时显示表格 -->
        <Table v-if="categories && categories.length > 0">
            <TableHeader>
                <TableRow>
                    <TableHead class="w-[60px] text-center">ID</TableHead>
                    <TableHead class="text-center w-[100px]">分类类别</TableHead>
                    <TableHead>分类信息</TableHead>
                    <TableHead class="text-center w-[100px]">状态</TableHead>
                    <TableHead class="text-center w-[80px]">排序权重</TableHead>
                    <TableHead class="hidden text-center md:table-cell">创建人</TableHead>
                    <TableHead class="hidden text-center md:table-cell">创建时间</TableHead>
                    <TableHead class="text-center">操作</TableHead>
                </TableRow>
            </TableHeader>

            <TableBody>
                <TableRow v-for="category in categories" :key="category.id">
                    <!-- ID -->
                    <TableCell class="font-medium text-center">{{ category.id }}</TableCell>

                    <!-- 分类类别：数字转文字 + Badge -->
                    <TableCell class="text-center">
                        <Badge variant="outline" class="font-normal">
                            {{ category.productType === 1 ? '组件' : (category.productType === 2 ? '整机' : '其他') }}
                        </Badge>
                    </TableCell>

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

                    <!-- 创建人 -->
                    <TableCell class="hidden text-center md:table-cell text-sm text-muted-foreground">
                        {{ category.createdBy }}
                    </TableCell>

                    <!-- 创建时间 -->
                    <TableCell class="hidden text-center md:table-cell text-sm text-muted-foreground">
                        {{ dayjs(category.createdAt).format('YYYY-MM-DD') }}
                    </TableCell>

                    <!-- 操作 -->
                    <TableCell class="text-center">
                        <div class="flex justify-center gap-2">
                            <Dialog>
                                <DialogTrigger as-child>
                                    <!-- 防止直接修改原对象，使用深拷贝 -->
                                    <Button variant="ghost" size="sm"
                                        @click="UpdateData = JSON.parse(JSON.stringify(category))">
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
                                        <div class="flex flex-col items-center gap-4">
                                            <Dialog>
                                                <DialogTrigger as-child>
                                                    <div
                                                        class="relative group cursor-pointer overflow-hidden rounded-xl border shadow-sm">
                                                        <!-- 使用 Avatar 或 img 均可，这里用 img 方便控制大图 -->
                                                        <img :src="category.image"
                                                            class="h-32 w-32 object-cover transition-transform duration-300 group-hover:scale-105" />
                                                        <div
                                                            class="absolute inset-0 bg-black/40 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity">
                                                            <span
                                                                class="text-white text-xs font-medium bg-black/50 px-2 py-1 rounded">更换图片</span>
                                                        </div>
                                                    </div>
                                                </DialogTrigger>
                                                <!-- 内层上传 Dialog 保持不变 -->
                                                <DialogContent class="md:max-w-xl">
                                                    <DialogHeader>
                                                        <DialogTitle>上传新图片</DialogTitle>
                                                    </DialogHeader>
                                                    <div class="py-4">
                                                        <FileUpload v-model:data="images" :max-files="1" />
                                                    </div>
                                                    <DialogFooter>
                                                        <DialogClose as-child>
                                                            <Button type="button"
                                                                @click="uploadFiles('update')">确认上传</Button>
                                                        </DialogClose>
                                                    </DialogFooter>
                                                </DialogContent>
                                            </Dialog>
                                        </div>

                                        <!-- 表单字段区域 -->
                                        <div class="grid gap-4">
                                            <div class="grid gap-2">
                                                <Label>所属类别</Label>
                                                <!-- 注意：Select 的 value 需要转为字符串匹配 -->
                                                <Select v-model="UpdateData!.productType"
                                                    :default-value="String(UpdateData!.productType)">
                                                    <SelectTrigger>
                                                        <SelectValue placeholder="请选择类别" />
                                                    </SelectTrigger>
                                                    <SelectContent>
                                                        <SelectGroup>
                                                            <!-- 这里的 value 视你的后端是 number 还是 string 而定 -->
                                                            <SelectItem :value="1">组件</SelectItem>
                                                            <SelectItem :value="2">整机</SelectItem>
                                                        </SelectGroup>
                                                    </SelectContent>
                                                </Select>
                                            </div>

                                            <div class="grid gap-2">
                                                <Label for="name">分类名称</Label>
                                                <Input id="name" v-model="UpdateData!.name" />
                                            </div>

                                            <div class="grid gap-2">
                                                <Label for="sort">排序权重</Label>
                                                <Input id="sort" type="number" v-model="UpdateData!.sort" />
                                            </div>
                                        </div>
                                    </div>

                                    <DialogFooter>
                                        <DialogClose as-child>
                                            <Button type="submit" @click="update(category.id)"
                                                :disabled="!UpdateData!.name || uploading">
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

        <!-- 新增 -->
        <Dialog v-model:open="commonStore.openDialog">
            <DialogTrigger as-child>
            </DialogTrigger>
            <DialogContent class="sm:max-w-[500px]">
                <DialogHeader>
                    <DialogTitle>编辑分类</DialogTitle>
                    <DialogDescription>修改分类的详细信息。</DialogDescription>
                </DialogHeader>

                <div class="grid gap-6 py-4">
                    <!-- 图片上传区域 -->
                    <div class="flex flex-col items-center gap-4">
                        <Dialog>
                            <DialogTrigger as-child>
                                <div class="relative group cursor-pointer overflow-hidden rounded-xl border shadow-sm">
                                    <!-- 使用 Avatar 或 img 均可，这里用 img 方便控制大图 -->
                                    <img :src="newCategory.image" v-if="newCategory.image"
                                        class="h-32 w-32 object-cover transition-transform duration-300 group-hover:scale-105" />
                                    <div v-else class="h-32 w-32"></div>
                                    <div
                                        class="absolute inset-0 bg-black/40 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity">
                                        <span
                                            class="text-white text-xs font-medium bg-black/50 px-2 py-1 rounded">添加图片</span>
                                    </div>
                                </div>
                            </DialogTrigger>
                            <!-- 内层上传 Dialog 保持不变 -->
                            <DialogContent class="md:max-w-xl">
                                <DialogHeader>
                                    <DialogTitle>上传图片</DialogTitle>
                                </DialogHeader>
                                <div class="py-4">
                                    <FileUpload v-model:data="images" :max-files="1" />
                                </div>
                                <DialogFooter>
                                    <DialogClose as-child>
                                        <Button type="button" @click="uploadFiles('add')">确认上传</Button>
                                    </DialogClose>
                                </DialogFooter>
                            </DialogContent>
                        </Dialog>
                    </div>

                    <!-- 表单字段区域 -->
                    <div class="grid gap-4">
                        <div class="grid gap-2">
                            <Label>所属类别</Label>
                            <!-- 注意：Select 的 value 需要转为字符串匹配 -->
                            <Select v-model="newCategory!.productType">
                                <SelectTrigger>
                                    <SelectValue placeholder="请选择类别" />
                                </SelectTrigger>
                                <SelectContent>
                                    <SelectGroup>
                                        <!-- 这里的 value 视你的后端是 number 还是 string 而定 -->
                                        <SelectItem :value="1">组件</SelectItem>
                                        <SelectItem :value="2">整机</SelectItem>
                                    </SelectGroup>
                                </SelectContent>
                            </Select>
                        </div>

                        <div class="grid gap-2">
                            <Label for="name">分类名称</Label>
                            <Input id="name" v-model="newCategory!.name" />
                        </div>
                    </div>
                </div>

                <DialogFooter>
                    <DialogClose as-child>
                        <Button type="submit" @click="add()"
                            :disabled="newCategory.productType === -1 || newCategory.image === '' || newCategory.name === '' || uploading">
                            添加
                        </Button>
                    </DialogClose>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    </div>
</template>

<script setup lang="ts">
    import { onMounted, ref } from 'vue';
    import axios from '@/utils/myAxios'
    import dayjs from 'dayjs'
    import relativeTime from 'dayjs/plugin/relativeTime'
    import 'dayjs/locale/zh-cn'
    dayjs.extend(relativeTime)
    dayjs.locale('zh-cn')
    import { Button } from '@/components/ui/button'
    import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow, } from '@/components/ui/table'
    import { Avatar, AvatarFallback, AvatarImage, } from '@/components/ui/avatar'
    import { Badge } from '@/components/ui/badge'
    import { Dialog, DialogClose, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, } from '@/components/ui/dialog'
    import { Input } from '@/components/ui/input'
    import { Label } from '@/components/ui/label'
    import FileUpload from '@/components/front/common/Upload.vue'
    import { Select, SelectContent, SelectGroup, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
    import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle, AlertDialogTrigger, } from '@/components/ui/alert-dialog'
    import { useCommonStore } from '@/stores/CommonStore'
    const commonStore = useCommonStore()
    import { Ghost } from 'lucide-vue-next'


    onMounted(() => {
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

    interface NewCategory {
        productType: number
        name: string
        image: string
    }

    const categories = ref<Category[]>([])

    const getCategory = async () => {
        try {
            const response = await axios.get("/api/admin/shop/category/list")
            categories.value = response.data.data
        } catch (error) {
            console.log(error)
        }
    }

    const changeStatus = async (categoryId: number, status: number) => {
        try {
            if (status === 1) {
                await axios.patch("/api/admin/shop/category/activate", {
                    id: categoryId
                })
            }
            else {
                await axios.patch("/api/admin/shop/category/deactivate", {
                    id: categoryId
                })
            }
            categories.value!.find(category => category.id === categoryId)!.status = status
        } catch (error) {
            console.log(error)
        }
    }

    const UpdateData = ref<Category>()

    const update = async (id: number) => {
        try {
            await axios.patch("/api/admin/shop/category/update", {
                id: id,
                productType: UpdateData.value?.productType,
                name: UpdateData.value?.name,
                image: url.value,
                sort: UpdateData.value?.sort,
            })
            const index = categories.value!.findIndex(category => category.id === id)
            categories.value![index] = UpdateData.value!
            url.value = ''
        } catch (error) {
            console.log(error)
        }
    }

    const newCategory = ref<NewCategory>({
        productType: -1,
        name: '',
        image: ''
    })

    const add = async () => {
        try {
            await axios.post("/api/admin/shop/category/add", {
                productType: newCategory.value?.productType,
                name: newCategory.value?.name,
                image: newCategory.value.image,
            })
            newCategory.value.productType = -1
            newCategory.value.name = ''
            newCategory.value.image = ''
            commonStore.openDialog = false
            getCategory()
        } catch (error) {
            console.log(error)
        }
    }

    const deleteCategory = async (id: number) => {
        try {
            await axios.delete("/api/admin/shop/category/delete", {
                params: {
                    id: id
                }
            })
            categories.value = categories.value.filter(category => category.id !== id)
        } catch (error) {
            console.log(error)
        }
    }

    // 图片上传
    const images = ref<File[]>([])
    const fileCount = ref(0)
    const uploading = ref<boolean>(false)
    const url = ref('');

    const uploadFiles = async (type: string) => {
        if (images.value.length === 0) {
            alert('请先选择文件')
            return;
        }

        try {
            // 创建 FormData 对象
            const formData = new FormData()
            // 将每个文件添加到 FormData 中
            images.value.forEach(image => {
                formData.append('images', image)
                formData.append('type', "avatars")
            });

            // 发送 POST 请求
            uploading.value = true
            const response = await axios.post("/api/common/upload", formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            fileCount.value = fileCount.value + response.data.data.count
            url.value = response.data.data.images[0]
            if (type === 'update') {
                UpdateData.value!.image = url.value
            } else {
                newCategory.value.image = url.value
            }
        } catch (error) {
            console.error('上传失败:', error)
        } finally {
            uploading.value = false
        }
    }

</script>