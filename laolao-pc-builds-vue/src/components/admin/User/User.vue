<template>
    <div class="h-full w-full overflow-hidden relative">
        <Table v-if="users && users.length > 0">
            <!-- 表头 -->
            <TableHeader>
                <TableRow>
                    <TableHead class="w-[60px] text-center">ID</TableHead>
                    <TableHead>用户</TableHead>
                    <TableHead class="text-center">邮箱</TableHead>
                    <TableHead class="text-center">权限</TableHead>
                    <TableHead class="text-center">状态</TableHead>
                    <TableHead class="hidden text-center md:table-cell">加入时间</TableHead>
                    <TableHead class="hidden text-center md:table-cell">最后更新</TableHead>
                    <TableHead class="text-center">操作</TableHead>
                </TableRow>
            </TableHeader>

            <!-- 表格内容 -->
            <TableBody>
                <TableRow v-for="user in users" :key="user.id">
                    <!-- ID -->
                    <TableCell class="font-medium text-center">{{ user.id }}</TableCell>

                    <!-- 用户列 -->
                    <TableCell>
                        <div class="flex items-center gap-3">
                            <Avatar class="h-9 w-9">
                                <AvatarImage :src="user.avatar" :alt="user.username" />
                                <AvatarFallback>{{ user.username.slice(0, 2).toUpperCase() }}</AvatarFallback>
                            </Avatar>
                            <div class="flex flex-col">
                                <span class="font-medium text-sm">{{ user.username }}</span>
                                <span class="text-xs text-muted-foreground">{{ user.name }}</span>
                            </div>
                        </div>
                    </TableCell>

                    <!-- 邮箱 -->
                    <TableCell class="text-center">
                        <span class="text-sm text-muted-foreground">{{ user.email }}</span>
                    </TableCell>

                    <!-- 权限 -->
                    <TableCell class="text-center">
                        <Badge :variant="user.admin === 0 ? 'default' : 'outline'">
                            {{ user.admin === 1 ? "管理员" : "普通用户" }}
                        </Badge>
                    </TableCell>

                    <!-- 状态 -->
                    <TableCell>
                        <div class="flex items-center justify-center gap-2">
                            <span class="flex h-2 w-2 rounded-full"
                                :class="user.status === 1 ? 'bg-green-500' : 'bg-red-500'"></span>
                            <span class="text-sm">
                                {{ user.status === 1 ? "启用" : "停用" }}
                            </span>
                        </div>
                    </TableCell>

                    <!-- 加入时间 -->
                    <TableCell class="hidden text-center md:table-cell text-muted-foreground text-sm">
                        {{ dayjs(user.createdAt).format('YYYY-MM-DD') }}
                    </TableCell>

                    <!-- 最后更新 -->
                    <TableCell class="hidden xl:table-cell text-center text-[10px] text-muted-foreground leading-tight">
                        <div>{{ user.updatedBy }}</div>
                        <div class="mt-0.5">{{ dayjs(user.updatedAt).format('YYYY-MM-DD') }}
                        </div>
                    </TableCell>

                    <!-- 操作按钮 -->
                    <TableCell class="text-center">
                        <div class="flex justify-center gap-2">
                            <Dialog>
                                <DialogTrigger as-child>
                                    <Button variant="outline" size="sm" @click="newData = { ...user }">编辑</Button>
                                </DialogTrigger>
                                <DialogContent class="sm:max-w-[500px]">
                                    <DialogHeader>
                                        <DialogTitle>信息修改</DialogTitle>
                                        <DialogDescription></DialogDescription>
                                    </DialogHeader>
                                    <div class="flex flex-col gap-6 py-6">
                                        <!-- 修改头像 -->
                                        <div class="flex flex-col items-center gap-4"
                                            @click="showUploadDialog = true, avatar[0] = user.avatar">
                                            <div class="relative group cursor-pointer">
                                                <Avatar class="w-24 h-24 border-2 border-gray-100 shadow-sm">
                                                    <AvatarImage :src="user.avatar" class="object-cover" />
                                                    <AvatarFallback>{{ user.name.substring(0, 1) }}
                                                    </AvatarFallback>
                                                </Avatar>
                                                <div
                                                    class="absolute inset-0 bg-black/50 rounded-full flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity">
                                                    <span class="text-white text-xs font-medium">更换</span>
                                                </div>
                                            </div>
                                            <FileManager v-model:open="showUploadDialog" v-model="avatar" :max-files="1"
                                                upload-api="/api/common/file/upload"
                                                delete-api="/api/common/file/delete"
                                                :upload-extra-data="{ type: 'laolaoPC/user/avatar' }" />
                                        </div>

                                        <div class="space-y-2">
                                            <Label class="text-sm font-medium text-gray-700">账号</Label>
                                            <Input v-model="newData!.username" id="name"
                                                class="h-10 border-gray-200 focus:border-indigo-500 focus:ring-indigo-500" />

                                            <Label class="text-sm font-medium text-gray-700">用户名</Label>
                                            <Input v-model="newData!.name" id="name"
                                                class="h-10 border-gray-200 focus:border-indigo-500 focus:ring-indigo-500" />

                                            <Label class="text-sm font-medium text-gray-700">邮箱</Label>
                                            <Input v-model="newData!.email" id="name"
                                                class="h-10 border-gray-200 focus:border-indigo-500 focus:ring-indigo-500" />

                                            <div class="flex items-center pt-2 space-x-2">
                                                <Checkbox :default-value="newData?.admin === 1"
                                                    @update:modelValue="(val) => newData!.admin = val ? 1 : 0" />
                                                <Label for="terms">管理员</Label>
                                            </div>
                                        </div>
                                    </div>
                                    <DialogFooter>
                                        <DialogClose as-child>
                                            <Button type="submit" @click="update(user.id)" :disabled="!newData!.name"
                                                class="bg-indigo-600 hover:bg-indigo-700 w-full sm:w-auto">
                                                保存更改
                                            </Button>
                                        </DialogClose>
                                    </DialogFooter>
                                </DialogContent>
                            </Dialog>
                            <Button variant="destructive" size="sm" v-if="user.status === 1"
                                @click="changeStatus(user.id, 0)">禁用</Button>
                            <Button variant="outline" size="sm" v-else @click="changeStatus(user.id, 1)">启用</Button>
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
            <Button variant="outline" size="sm" @click="getUser()">
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
    import { Badge } from '@/components/ui/badge'
    import { Dialog, DialogClose, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, } from '@/components/ui/dialog'
    import { Input } from '@/components/ui/input'
    import { Label } from '@/components/ui/label'
    import FileManager from '@/components/common/FileManager.vue'
    import { Checkbox } from '@/components/ui/checkbox'
    import { Pagination, PaginationContent, PaginationEllipsis, PaginationItem, PaginationNext, PaginationPrevious, } from '@/components/ui/pagination'
    import { Ghost, } from 'lucide-vue-next'
    import { useCommonStore } from '@/stores/CommonStore'
    const commonStore = useCommonStore()

    onMounted(() => {
        getUser()
    })
    
    watch(
        () => commonStore.search.search,
        (newSearch) => {
            if (newSearch) {
                getUser()
                commonStore.search.search = false
                commonStore.search.searchContent = ''
            }
        }
    )

    interface User {
        id: number
        avatar: string
        username: string
        name: string
        email: string
        status: number
        admin: number
        createdAt: string
        updatedBy: string
        updatedAt: string
    }

    const users = ref<User[]>([])
    const pageNum = ref(1)
    const pageSize = ref(10)
    const total = ref(0)

    watch(
        () => pageNum.value,
        () => {
            getUser()
        }
    )

    const getUser = async () => {
        try {
            const response = await axios.get("/api/admin/user", {
                params: {
                    pageNum: pageNum.value,
                    pageSize: pageSize.value,
                    searchContent: commonStore.search.searchContent
                }
            })
            const resData = response.data.data
            users.value = resData.list
            total.value = resData.total
        } catch (error) {
            console.log(error)
        }
    }

    const changeStatus = async (userId: number, status: number) => {
        try {
            if (status === 1) {
                await axios.patch("/api/admin/user/status/activate", {
                    id: userId
                })
            }
            else {
                await axios.patch("/api/admin/user/status/deactivate", {
                    id: userId
                })
            }
            users.value!.find(user => user.id === userId)!.status = status
        } catch (error) {
            console.log(error)
        }
    }

    const newData = ref<User>()

    const update = async (userId: number) => {
        try {
            await axios.patch("/api/admin/user/update", {
                id: newData.value?.id,
                avatar: avatar.value[0],
                username: newData.value?.username,
                name: newData.value?.name,
                email: newData.value?.email,
                admin: newData.value?.admin,
            })
            const index = users.value!.findIndex(user => user.id === userId)
            users.value![index] = newData.value!
            avatar.value = []
        } catch (error) {
            console.log(error)
        }
    }

    // 控制弹窗开关
    const showUploadDialog = ref(false)
    // 数据源：现有的图片 URL
    const avatar = ref<string[]>([])
</script>