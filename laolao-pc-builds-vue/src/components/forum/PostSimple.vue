<template>
    <div class="h-full overflow-y-auto bg-gradient-to-b from-slate-50 to-white scrollbar-edge">
        <!-- 板块描述 -->
        <div class="rounded-3xl border border-slate-100 bg-white/90 p-6 m-2 shadow-md backdrop-blur">
            <div class="rounded-2xl bg-sky-50 px-5 py-3 text-slate-700 shadow-inner">
                <span class="text-base leading-relaxed">
                    <span class="font-bold">真诚、友善、团结、专业</span>，共建你我引以为荣之社区。
                </span>
            </div>
            <div
                class="mt-5 flex items-center gap-4 rounded-2xl border border-slate-100 bg-white/90 px-6 py-5 shadow-sm">
                <img :src="categoryStore.getCategoryById(categoryStore.currentCategory)?.image" class="h-15 w-15" />
                
                <div>
                    <p class="text-3xl font-black text-slate-900">
                        {{ categoryStore.getCategoryById(categoryStore.currentCategory)?.name }}
                    </p>
                    <p class="text-sm text-slate-500">
                        {{ categoryStore.getCategoryById(categoryStore.currentCategory)?.description }}
                    </p>
                </div>
            </div>
            <div v-show="categoryStore.currentCategory !== 5"
                class="mt-6 flex flex-wrap items-center justify-between gap-3 rounded-2xl bg-slate-50/70 px-4 py-3">
                <p class="text-xs font-semibold uppercase tracking-[0.3em] text-slate-400">发现好贴</p>
                <div class="flex w-full max-w-md items-center gap-2">
                    <Button v-if="back" @click="getPost()" class="rounded-full px-4">返回</Button>
                    <Input type="text" placeholder="搜索帖子..." v-model="searchContent"
                        class="flex-1 rounded-full border-slate-200" />
                    <Button type="submit" class="rounded-full px-4" @click="search()"
                        :disabled="!searchContent">搜索</Button>
                </div>
            </div>
        </div>

        <div class="mt-6 rounded-3xl border border-slate-100 bg-white/90 shadow-xl">
            <div
                class="flex items-center justify-between rounded-t-3xl bg-slate-50/80 px-6 py-4 text-xs font-semibold uppercase tracking-[0.3em] text-slate-400">
                <span>帖子</span>
                <div class="hidden items-center gap-4 text-slate-500 md:flex">
                    <p class="w-12 text-center text-base">回复</p>
                    <p class="w-12 text-center text-base">点赞</p>
                    <p class="w-18 text-center text-sm">最后回复</p>
                </div>
            </div>
            <div class="divide-y divide-slate-100">
                <div v-for="simple in postSimple" :key="simple.id"
                    class="flex flex-wrap items-center justify-between gap-4 px-6 py-4 transition hover:bg-slate-50/60">
                    <div class="space-y-2">
                        <router-link :to="`/forum/${simple.categoryId}/post/${simple.id}`"
                            class="text-lg font-semibold text-slate-900 hover:text-orange-500">
                            {{ simple.title }}
                        </router-link>
                        <div @click="categoryStore.currentCategory = simple.categoryId"
                            class="inline-flex cursor-pointer items-center rounded-full bg-slate-100 px-3 py-1 mx-2 text-xs text-slate-500 transition hover:bg-slate-200">
                                <img :src="categoryStore.getCategoryById(simple.categoryId)?.image" class="h-4 w-4" />
                                
                            {{ categoryStore.getCategoryById(simple.categoryId)?.name }}
                        </div>
                    </div>

                    <div class="hidden items-center gap-4 text-slate-500 md:flex">
                        <p class="w-12 text-center text-base font-bold text-slate-900">{{
                            simple.commentCount }}</p>
                        <p class="w-12 text-center text-base font-bold text-slate-900">{{ simple.likeCount
                            }}</p>
                        <p class="w-18 text-center text-sm font-bold text-slate-900">{{
                            formatTime(simple.updatedAt) }}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
    import axios from '@/utils/myAxios'
    import { ref, watch } from 'vue'
    import { Input } from "@/components/ui/input"
    import { Button } from "@/components/ui/button"
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
        updatedAt: string
    }
    const postSimple = ref<PostSimple[]>([])

    const back = ref<boolean>(false)
    const getPost = async () => {
        back.value = false
        const response = await axios.get("/api/user/forum/post/simple", {
            params: {
                categoryId: categoryStore.currentCategory
            }
        })
        postSimple.value = response.data.data
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