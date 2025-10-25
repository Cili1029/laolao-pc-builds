<template>
    <div class="flex">
        <!-- 侧栏 -->
        <div class="sticky top-16 w-1/5 h-2/3 bg-white rounded-lg shadow-sm p-2">
            <div class="p-2 text-base flex rounded-lg">类别</div>
            <div @click="changeCategory(category)" v-for="category in categories" :key="category.id"
                class="p-2 text-lg cursor-pointer hover:bg-sky-100 flex rounded-lg"
                :class="category.id === currentCategory ? 'bg-sky-100' : ''">
                <span :class="categoryStore.getIconClass(category.id)"></span>
                <p class="pl-3">{{ category.name }}</p>
            </div>
        </div>

        <!-- 内容区域 - 使用 router-view 显示子路由 -->
        <div class="w-4/5 mx-3 bg-white rounded-lg">
            <RouterView v-slot="{ Component }">
                <transition name="fade" mode="out-in">
                    <component :is="Component" />
                </transition>
            </RouterView>
        </div>
    </div>
</template>

<script setup lang="ts">
    import axios from '@/utils/myAxios'
    import { onMounted, ref } from 'vue'
    import { useForumCategoryStore } from '@/stores/ForumCategoryStore'
    import { useRouter } from 'vue-router'
    const router = useRouter()

    const categoryStore = useForumCategoryStore()

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
        categoryStore.setCategory(categories.value[0] as Category)
    }

    const changeCategory = (category: Category) => {
        currentCategory.value = category.id
        categoryStore.setCategory(category)
        // 切换分类时回到帖子列表
        router.push('/forum')
    }
</script>

<style scoped>
  /* 过渡样式 */
  .fade-enter-active,
  .fade-leave-active {
    transition: opacity 0.1s ease;
  }

  .fade-enter-from,
  .fade-leave-to {
    opacity: 0;
  }

</style>