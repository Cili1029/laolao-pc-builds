<template>
    <div class="h-full overflow-y-auto bg-gradient-to-b from-slate-50 to-white scrollbar-edge">
        <!-- 板块描述 -->
        <div class="rounded-2xl border border-slate-100 bg-white/90 p-2 m-2 shadow-sm backdrop-blur">
            <div class="rounded-2xl bg-sky-50 px-5 py-3 text-slate-700">
                <span class="text-base leading-relaxed">
                    <span class="font-bold">真诚、友善、团结、专业</span>，共建你我引以为荣之社区。
                </span>
            </div>
            <div class="flex justify-between items-center py-4">
                <div class="flex">
                    <img :src="categoryStore.getCategoryById(categoryStore.currentCategory)?.image"
                        class="h-15 w-15 mx-3" />
                    <div>
                        <p class="text-3xl font-black text-slate-900">
                            {{ categoryStore.getCategoryById(categoryStore.currentCategory)?.name }}
                        </p>
                        <p class="text-sm text-slate-500">
                            {{ categoryStore.getCategoryById(categoryStore.currentCategory)?.description }}
                        </p>
                    </div>
                </div>
                <div v-show="categoryStore.currentCategory !== 5" class="flex w-full max-w-sm items-center space-x-2">
                    <Button v-if="back" @click="getPost()" class="rounded-full px-4">返回</Button>
                    <Input type="text" placeholder="搜索帖子..." v-model="searchContent"
                        class="flex-1 rounded-full border-slate-200" />
                    <Button type="submit" class="rounded-full px-4" @click="search()" :disabled="!searchContent">
                        搜索
                    </Button>
                </div>
            </div>

        </div>

        <div class="">
            <div
                class="flex items-center justify-between rounded-t-3xl bg-slate-50/80 px-6 py-2 font-semibold text-slate-400">
                <span class="text-base">帖子</span>
                <div class="hidden items-center gap-4 text-slate-500 md:flex">
                    <p class="w-12 text-center text-base">回复</p>
                    <p class="w-12 text-center text-base">点赞</p>
                    <p class="w-24 text-center text-base">最后回复</p>
                </div>
            </div>
            <router-link :to="`/forum/${simple.categoryId}/post/${simple.id}`" v-for="simple in postSimple"
                :key="simple.id" class="flex items-center justify-between gap-4 px-6 py-4 transition hover:bg-slate-50">
                <div class="space-y-2">
                    <span class="text-lg font-semibold">
                        {{ simple.title }}
                    </span>
                    <div @click="categoryStore.currentCategory = simple.categoryId"
                        class="inline-flex cursor-pointer items-center rounded-full bg-slate-100 px-3 py-1 mx-2 text-xs text-slate-500 transition hover:bg-slate-200">
                        <img :src="categoryStore.getCategoryById(simple.categoryId)?.image" class="h-4 w-4" />
                        {{ categoryStore.getCategoryById(simple.categoryId)?.name }}
                    </div>
                </div>

                <div class="hidden items-center gap-4 text-slate-500 md:flex">
                    <p class="w-12 text-center text-base font-bold text-slate-900">
                        {{ simple.commentCount }}</p>
                    <p class="w-12 text-center text-base font-bold text-slate-900">
                        {{ simple.likeCount }}</p>
                    <p class="w-24 text-center text-sm font-bold text-slate-900">
                        {{ formatTime(simple.commentedAt) }}</p>
                </div>
            </router-link>

            <div v-if="categoryStore.currentCategory === 5" class="h-16 border-t flex items-center p-2">
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
    </div>
</template>

<script setup lang="ts">
    import axios from '@/utils/myAxios'
    import { ref, watch } from 'vue'
    import { Input } from "@/components/ui/input"
    import { Button } from "@/components/ui/button"
    import { Pagination, PaginationContent, PaginationEllipsis, PaginationItem, PaginationNext, PaginationPrevious, } from '@/components/ui/pagination'
    import { useForumCategoryStore } from '@/stores/ForumCategoryStore'
    const categoryStore = useForumCategoryStore()
    import { usePostStore } from '@/stores/PostStore'
    const postStore = usePostStore()
    import dayjs from 'dayjs'
    import relativeTime from 'dayjs/plugin/relativeTime'
    import 'dayjs/locale/zh-cn'

    // 帖子简单信息
    interface PostSimple {
        id: number
        categoryId: number
        title: string
        likeCount: number
        commentCount: number
        commentedBy: number
        commentedAt: string
    }
    const postSimple = ref<PostSimple[]>([])
    const pageNum = ref(1)
    const pageSize = ref(10)
    const total = ref(0)

    watch(
        () => pageNum.value,
        () => {
            getPost()
        }
    )

    const back = ref<boolean>(false)
    const getPost = async () => {
        back.value = false
        if (categoryStore.currentCategory === 5) {
            // 热门
            const response = await axios.get("/api/user/forum/post/hot", {
                params: {
                    pageNum: pageNum.value,
                    pageSize: pageSize.value,
                }
            })
            const resData = response.data.data
            postSimple.value = resData.list
            total.value = resData.total
        } else {
            const response = await axios.get("/api/user/forum/post/simple", {
                params: {
                    categoryId: categoryStore.currentCategory
                }
            })
            postSimple.value = response.data.data
        }
    }

    // 初始化dayjs
    dayjs.extend(relativeTime)
    dayjs.locale('zh-cn')

    // 时间格式化
    const formatTime = (timeStr: string | undefined) => {
        if (!timeStr) return '无效日期'
        return dayjs(timeStr).fromNow()
    }

    // 监听路由变化
    watch(
        [() => categoryStore.currentCategory, () => postStore.postSimple?.id], // 只监听这两个特定值
        () => {
            if (categoryStore.currentCategory) {
                getPost() // 分类变化时获取帖子
            }
            if (postStore.postSimple?.id) {
                postSimple.value.push(postStore.postSimple) // 有新帖子时添加
                postStore.setPostSimple({} as PostSimple) // 立即清空，避免重复添加
            }
        },
        { immediate: true }
    )

    // 搜索
    const searchContent = ref()

    const search = async () => {
        const response = await axios.get("/api/user/forum/post/search", {
            params: {
                categoryId: categoryStore.currentCategory,
                searchContent: searchContent.value
            }
        })
        postSimple.value = response.data.data
        searchContent.value = null
        back.value = true
    }
</script>

<style scoped></style>