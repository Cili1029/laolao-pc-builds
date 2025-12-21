<template>
    <div class="h-full w-full overflow-hidden relative">
        <div class="absolute inset-0 bottom-16 overflow-y-auto scrollbar-edge">
            <div class="h-full overflow-auto">
                <Table v-if="posts && posts.length > 0">
                    <TableHeader>
                        <TableRow class="bg-muted/40 hover:bg-muted/40 sticky top-0 z-10 shadow-sm">
                            <TableHead class="w-[60px] text-center text-sm font-semibold">ID</TableHead>
                            <TableHead class="w-[160px] text-sm font-semibold">标题</TableHead>
                            <TableHead class="min-w-[200px] text-sm font-semibold">描述</TableHead>
                            <TableHead class="text-center w-[80px] text-sm font-semibold">点赞</TableHead>
                            <TableHead class="text-center w-[80px] text-sm font-semibold">评论</TableHead>
                            <TableHead class="text-center w-[90px] text-sm font-semibold">状态</TableHead>
                            <TableHead class="hidden xl:table-cell w-[140px] text-center text-sm font-semibold">创建信息
                            </TableHead>
                            <TableHead class="hidden xl:table-cell w-[140px] text-center text-sm font-semibold">最后评论
                            </TableHead>
                            <TableHead class="hidden xl:table-cell w-[140px] text-center text-sm font-semibold">最后更新
                            </TableHead>
                            <TableHead class="w-[180px] text-center text-sm font-semibold">操作</TableHead>
                        </TableRow>
                    </TableHeader>

                    <TableBody>
                        <TableRow v-for="post in posts" :key="post.id" class="hover:bg-muted/10">
                            <!-- ID: 字体加大到 text-sm -->
                            <TableCell class="font-mono text-sm text-muted-foreground text-center">
                                {{ post.id }}
                            </TableCell>

                            <!-- 标题: 限制宽度 max-w-[150px] 并截断 -->
                            <TableCell>
                                <router-link :to="`/forum/${post.categoryId}/post/${post.id}`"
                                    class="max-w-[150px] truncate font-medium text-sm text-foreground"
                                    :title="post.title">
                                    {{ post.title }}
                                </router-link>
                            </TableCell>

                            <!-- 描述: 限制宽度 max-w-[220px] 并截断 -->
                            <TableCell>
                                <div class="flex items-center gap-3">
                                    <!-- 图片稍微加大到 h-10 (40px) 方便看清 -->
                                    <Avatar v-if="post.images && post.images.length > 0"
                                        class="h-10 w-10 rounded-md border shrink-0">
                                        <AvatarImage :src="post.images[0]!" class="object-cover" />
                                        <AvatarFallback class="rounded-md bg-muted text-xs">IMG</AvatarFallback>
                                    </Avatar>
                                    <div v-else
                                        class="h-10 w-10 rounded-md border bg-muted/30 flex items-center justify-center shrink-0">
                                        <span class="text-xs text-muted-foreground">-</span>
                                    </div>

                                    <!-- 文本截断关键：flex-1 + min-w-0 + truncate -->
                                    <div class="flex-1 min-w-0">
                                        <p class="truncate text-sm text-muted-foreground max-w-[220px]"
                                            :title="post.content">
                                            {{ post.content }}
                                        </p>
                                    </div>
                                </div>
                            </TableCell>

                            <!-- 数据统计: 字体加大 -->
                            <TableCell class="text-center font-mono text-sm">
                                {{ post.likeCount }}
                            </TableCell>

                            <TableCell class="text-center font-mono text-sm">
                                {{ post.commentCount }}
                            </TableCell>

                            <!-- 状态 -->
                            <TableCell class="text-center">
                                <Badge :variant="post.status === 1 ? 'secondary' : 'destructive'"
                                    class="px-2 py-0.5 text-xs">
                                    {{ post.status === 1 ? "正常" : "锁定" }}
                                </Badge>
                            </TableCell>

                            <!-- 时间信息: 字体使用 text-xs (比之前大一点)，两行显示 -->
                            <TableCell class="hidden xl:table-cell text-center text-xs text-muted-foreground">
                                <div class="font-medium text-foreground/80 mb-1 truncate max-w-[100px] mx-auto">{{
                                    post.userId }}</div>
                                <div class="font-mono">{{ dayjs(post.createdAt).format('YYYY-MM-DD') }}</div>
                            </TableCell>

                            <TableCell class="hidden xl:table-cell text-center text-xs text-muted-foreground">
                                <div v-if="post.commentedBy">
                                    <div class="font-medium text-foreground/80 mb-1 truncate max-w-[100px] mx-auto">{{
                                        post.commentedBy }}</div>
                                    <div class="font-mono">{{ dayjs(post.commentedAt).format('YYYY-MM-DD') }}</div>
                                </div>
                                <span v-else>-</span>
                            </TableCell>

                            <TableCell class="hidden xl:table-cell text-center text-xs text-muted-foreground">
                                <div v-if="post.updatedBy">
                                    <div class="font-medium text-foreground/80 mb-1 truncate max-w-[100px] mx-auto">
                                        {{ post.updatedBy }}</div>
                                    <div class="font-mono">{{ dayjs(post.updatedAt).format('YYYY-MM-DD') }}</div>
                                </div>
                                <p v-else>无数据</p>
                            </TableCell>

                            <!-- 操作: 按钮稍微大一点 h-9 -->
                            <TableCell class="text-center">
                                <div class="flex items-center justify-center gap-2">
                                    <Select v-model="post.categoryId"
                                        @update:modelValue="changeCategory(post.id, Number(post.categoryId))">
                                        <SelectTrigger>
                                            <SelectValue placeholder="类别" />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectGroup>
                                                <SelectItem v-for="category in categories" :key="category.id"
                                                    :value="category.id">
                                                    {{ category.name
                                                    }}</SelectItem>
                                            </SelectGroup>
                                        </SelectContent>
                                    </Select>

                                    <Button v-if="post.status === 1" variant="ghost" size="sm"
                                        class="h-9 px-2 text-sm text-orange-600 hover:text-orange-700 hover:bg-orange-50"
                                        @click="changeStatus(post.id, 0)">
                                        锁定
                                    </Button>
                                    <Button v-else variant="ghost" size="sm"
                                        class="h-9 px-2 text-sm text-green-600 hover:text-green-700 hover:bg-green-50"
                                        @click="changeStatus(post.id, 1)">
                                        解锁
                                    </Button>

                                    <AlertDialog>
                                        <AlertDialogTrigger as-child>
                                            <Button variant="ghost" size="sm"
                                                class="h-9 px-2 text-sm text-red-600 hover:text-red-700 hover:bg-red-50">
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
                                                <AlertDialogAction @click="deletePost(post.id)">确定</AlertDialogAction>
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
                    <Button variant="outline" size="sm" @click="getPost()">
                        返回全部数据
                    </Button>
                </div>
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
    import { Select, SelectContent, SelectGroup, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
    import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle, AlertDialogTrigger, } from '@/components/ui/alert-dialog'
    import { Ghost } from 'lucide-vue-next'
    import { Pagination, PaginationContent, PaginationEllipsis, PaginationItem, PaginationNext, PaginationPrevious, } from '@/components/ui/pagination'
    import Post from '@/components/front/forum/Post.vue'
    import { useCommonStore } from '@/stores/CommonStore'
    const commonStore = useCommonStore()

    watch(
        () => commonStore.search.search,
        (newSearch) => {
            if (newSearch) {
                getPost()
                commonStore.search.search = false
                commonStore.search.searchContent = ''
            }
        }
    )

    onMounted(() => {
        getCategory()
        getPost()
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

    const categories = ref<Category[]>([])

    const getCategory = async () => {
        try {
            const response = await axios.get("/api/admin/forum/category/other_need")
            categories.value = response.data.data
        } catch (error) {
            console.log(error)
        }
    }

    interface Post {
        id: number
        userId: number
        categoryId: number
        title: string
        content: string
        images: string[]
        likeCount: number
        commentCount: number
        status: number
        createdAt: string
        commentedBy: number
        commentedAt: string
        updatedBy: number
        updatedAt: string
    }

    const posts = ref<Post[]>([])
    const pageNum = ref(1)
    const pageSize = ref(10)
    const total = ref(0)

    watch(
        () => pageNum.value,
        () => {
            getPost()
        }
    )

    const getPost = async () => {
        try {
            const response = await axios.get("/api/admin/forum/post", {
                params: {
                    pageNum: pageNum.value,
                    pageSize: pageSize.value,
                    searchContent: commonStore.search.searchContent
                }
            })
            const resData = response.data.data
            posts.value = resData.list
            total.value = resData.total
        } catch (error) {
            console.log(error)
        }
    }

    const changeCategory = async (id: number, categoryId: number) => {
        try {
            await axios.patch(`/api/admin/forum/post/${id}/category`, {}, {
                params: {
                    categoryId: categoryId
                }
            })
            getPost()
        } catch (error) {
            console.log(error)
        }
    }

    const changeStatus = async (id: number, status: number) => {
        try {
            if (status === 1) {
                await axios.patch(`/api/admin/forum/post/${id}/activate`, {}, {
                    params: {
                        id: id
                    }
                })
            }
            else {
                await axios.patch(`/api/admin/forum/post/${id}/deactivate`, {
                    id: id
                })
            }
            posts.value!.find(p => p.id === id)!.status = status
        } catch (error) {
            console.log(error)
        }
    }

    const deletePost = async (id: number) => {
        try {
            await axios.delete("/api/admin/forum/post", {
                params: {
                    id: id
                }
            })
            posts.value = posts.value.filter(p => p.id !== id)
        } catch (error) {
            console.log(error)
        }
    }
</script>