<template>
    <div class="h-full overflow-y-auto">
        <!-- 板块描述 -->
        <div class="m-5 pl-5 py-1 bg-sky-100 shadow-sm flex items-center">
            <span class="text-xl">
                <span class="font-bold">真诚、友善、团结、专业</span>，共建你我引以为荣之社区。
            </span>
        </div>
        <div class="h-24 m-5 pl-5 bg-white shadow-sm rounded-sm flex items-center">
            <span class="text-6xl" :class="categoryStore.getIconClass(categoryStore.category.id)"></span>
            <div class="p-3">
                <p class="text-2xl font-bold">{{ categoryStore.category.name }}</p>
                <p>{{ categoryStore.category.description }}</p>
            </div>
        </div>
        <div class="border-t-4"></div>
        <div class="flex justify-between items-center p-2">
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
                <div class="text-l text-gray-500">帖子</div>
                <div class="text-l flex justify-between items-center text-gray-500">
                    <div class="w-22 font-bold text-center">浏览量</div>
                    <div class="w-22 font-bold text-center">回复</div>
                    <div class="w-22 font-bold text-center">点赞</div>
                </div>
            </div>
            <div class="border-t-2"></div>
        </div>
        <div v-for="simple in postSimple" :key="simple.id">
            <div class="flex mx-2 my-4 justify-between items-center">
                <router-link :to="`/forum/post/${simple.id}`" class="text-xl cursor-pointer">
                    {{ simple.title }}
                </router-link>
                <div class="flex text-gray-600">
                    <div class="w-22 font-bold text-center">{{ simple.viewCount }}</div>
                    <div class="w-22 font-bold text-center">{{ simple.commentCount }}</div>
                    <div class="w-22 font-bold text-center">{{ simple.likeCount }}</div>
                </div>
            </div>
            <div class="border-t-2"></div>
        </div>
    </div>

</template>

<script setup lang="ts">
    import axios from '@/utils/myAxios'
    import { ref, watchEffect } from 'vue'
    import { Input } from "@/components/ui/input"
    import { Button } from "@/components/ui/button"
    import { useForumCategoryStore } from '@/stores/ForumCategoryStore'
    const categoryStore = useForumCategoryStore()
    import { usePostStore } from '@/stores/PostStore'
    const postStore = usePostStore()

    // 帖子简单信息
    interface PostSimple {
        id: number
        title: string
        viewCount: number
        likeCount: number
        commentCount: number
    }
    const postSimple = ref<PostSimple[]>([])

    const back = ref<boolean>(false)
    const getPost = async () => {
        back.value = false
        const response = await axios.get("/api/user/forum/post/simple", {
            params: {
                categoryId: categoryStore.category.id
            }
        })
        postSimple.value = response.data.data
    }
    // 监听路由变化
    watchEffect(() => {
        if (categoryStore.category.id) {
            getPost()
        }
        if (postStore.postSimple.id) {
            postSimple.value.push(postStore.postSimple)
        }
    })

    // 搜索
    const searchContent = ref()
    
    const search = async () => {
        const response = await axios.get("/api/user/forum/post/search", {
            params: {
                categoryId: categoryStore.category.id,
                searchContent: searchContent.value
            }
        })
        postSimple.value = response.data.data
        searchContent.value = null
        back.value = true
    }
</script>

<style scoped></style>