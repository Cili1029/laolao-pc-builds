<template>
    <div class="h-full overflow-y-auto scrollbar-edge">
        <!-- 板块描述 -->
        <div class="m-5 pl-5 py-1 bg-sky-100 shadow-sm flex items-center">
            <span class="text-xl">
                <span class="font-bold">真诚、友善、团结、专业</span>，共建你我引以为荣之社区。
            </span>
        </div>
        <div class="h-24 m-5 pl-5 bg-white shadow-sm rounded-sm flex items-center">
            <span class="text-6xl" :class="categoryStore.getCategoryById(categoryStore.currentCategory)?.icon"></span>
            <div class="p-3">
                <p class="text-2xl font-bold">{{ categoryStore.getCategoryById(categoryStore.currentCategory)?.name }}</p>
                <p>{{ categoryStore.getCategoryById(categoryStore.currentCategory)?.description }}</p>
            </div>
        </div>
        <div class="border-t-4"></div>
        <div v-show="categoryStore.currentCategory !== 5" class="flex justify-between items-center p-2">
            <div></div>
            <div class="flex w-1/3 max-w-sm items-center gap-1.5">
                <Button v-if="back" @click="getPost()">返回</Button>
                <Input type="text" placeholder="搜索帖子..." v-model="searchContent" />
                <Button type="submit" @click="search()" :disabled="!searchContent">搜索</Button>
            </div>
        </div>
        <div class="border-t-4"></div>
        <div>
            <div class="flex m-2 justify-between items-center">
                <div class="text-l justify-between items-center text-gray-500">
                    <div class="w-22 font-bold">帖子</div>
                </div>
                <div class="hidden md:flex text-l text-gray-500">
                    <div class="w-22 font-bold text-center">回复</div>
                    <div class="w-22 font-bold text-center">点赞</div>
                    <div class="w-22 font-bold text-center">最后回复</div>
                </div>
            </div>
            <div class="border-t-2"></div>
        </div>
        <div v-for="simple in postSimple" :key="simple.id">
            <div class="flex mx-2 my-1 justify-between items-center">
                <div>
                    <router-link :to="`/forum/${simple.categoryId}/post/${simple.id}`" class="text-xl cursor-pointer">
                        <p>{{ simple.title }}</p>
                    </router-link>
                    <div @click="categoryStore.currentCategory = simple.categoryId" class="inline-flex items-center bg-gray-200 py-0.5 px-2 cursor-pointer rounded">
                        <span class="text-sm mr-1" :class="categoryStore.getCategoryById(simple.categoryId)?.icon"></span>
                        <span class="text-xs">{{ categoryStore.getCategoryById(simple.categoryId)?.name }}</span>
                    </div>
                </div>

                <div class="hidden md:flex text-gray-600">
                    <div class="w-22 font-bold text-center">{{ simple.commentCount }}</div>
                    <div class="w-22 font-bold text-center">{{ simple.likeCount }}</div>
                    <div class="w-22 font-bold text-center">{{ formatTime(simple.updatedAt) }}</div>
                </div>
            </div>
            <div class="border-t-2"></div>
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