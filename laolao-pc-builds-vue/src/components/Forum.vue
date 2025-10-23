<template>
    <div class="flex">
        <!-- 侧栏 -->
        <div class="w-1/5 h-screen bg-white rounded-lg shadow-sm p-2">
            <div class="p-2 text-base flex rounded-lg">类别</div>
            <div @click="getPost(category.id, category.name, category.description)" v-for="category in categories"
                :key="category.id" class="p-2 text-lg hover:bg-gray-100 flex rounded-lg"
                :class="category.id === currentCategory ? 'bg-gray-100' : ''">
                <span :class="getIconClass(category.id)"></span>
                <p class="pl-3">
                    {{ category.name }}
                </p>
            </div>
        </div>

        <!-- 内容 -->
        <div class="w-4/5 mx-3 bg-white rounded-lg">
            <!-- 板块描述 -->
            <div class="h-24 m-5 pl-5 bg-white shadow-sm rounded-sm flex items-center">
                <span class="items-center text-6xl" :class="getIconClass(currentCategory)"></span>
                <div class="p-3">
                    <p class="text-2xl font-bold">{{ currentName }}</p>
                    <p>{{ currentDescription }}</p>
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
                    <p class="text-xl flex-1">{{ simple.title }}</p>
                    <div class="flex text-gray-600">
                        <div class="w-22 font-bold text-center">{{ simple.viewCount }}</div>
                        <div class="w-22 font-bold text-center">{{ simple.commentCount }}</div>
                        <div class="w-22 font-bold text-center">{{ simple.likeCount }}</div>
                    </div>
                </div>
                <div class="border-t-2"></div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
    import axios from '@/utils/myAxios'
    import { onMounted, ref } from 'vue'
    onMounted(() => {
        getCategory()
    })

    interface Category {
        id: number
        name: string
        description: string
    }

    const categories = ref<Category[]>([])
    const currentCategory = ref(0)

    // 获取分类
    const getCategory = async () => {
        const response = await axios.get("/user/forum/category")
        categories.value = response.data.data
        currentCategory.value = response.data.data[0].id

        // 获取默认类别的帖子
        getPost(currentCategory.value, response.data.data[0].name, response.data.data[0].description)
    }

    // 类别图标
    const getIconClass = (id: number) => {
        const iconMap: Record<number, string> = {
            1: 'icon-[noto--old-man-light-skin-tone] text-3xl',
            2: 'icon-[noto--smiling-face-with-open-mouth-and-cold-sweat] text-3xl',
            3: 'icon-[noto--nerd-face] text-3xl',
            4: 'icon-[noto--carpentry-saw] text-3xl'
        };
        return iconMap[id];
    }

    // 帖子简单信息
    interface PostSimple {
        id: number
        title: string
        viewCount: number
        likeCount: number
        commentCount: number
    }
    const postSimple = ref<PostSimple[]>([])
    const currentName = ref('')
    const currentDescription = ref('')

    const getPost = async (categoryId: number, name: string, description: string) => {
        currentCategory.value = categoryId
        currentDescription.value = description
        currentName.value = name
        const response = await axios.get("/user/forum/post/simple", {
            params: {
                categoryId: categoryId
            }
        })
        postSimple.value = response.data.data
    }
</script>
