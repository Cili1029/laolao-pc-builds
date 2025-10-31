<template>
    <div class="flex h-full space-x-2">
        <!-- 侧栏 -->
        <div class="sticky top-16 w-1/5 h-full bg-white rounded-lg shadow-sm p-2">
            <div>
                <div class="p-2 text-base flex rounded-lg">工具</div>
                <Dialog>
                    <DialogTrigger as-child>
                        <div class="p-2 text-lg cursor-pointer hover:bg-sky-100 flex rounded-lg">
                            <span class="icon-[noto--writing-hand-light-skin-tone] text-4xl"></span>
                            <p class="pl-3">我有话说</p>
                        </div>
                    </DialogTrigger>
                    <DialogContent class="sm:max-w-[800px]">
                        <DialogHeader>
                            <DialogTitle>发布帖子</DialogTitle>
                            <DialogDescription>
                                <div class="px-3 mt-5 pl-5 py-1 bg-sky-100 shadow-sm flex items-center">
                                    <span class="text-xl">
                                        <span class="font-bold">真诚、友善、团结、专业</span>，共建你我引以为荣之社区。
                                    </span>
                                </div>
                            </DialogDescription>
                        </DialogHeader>

                        <div class="space-y-4">
                            <div class="flex items-center">
                                <label class="text-sm font-medium w-18">类别</label>
                                <Select v-model="categoryId">
                                    <SelectTrigger class="w-[180px]">
                                        <SelectValue placeholder="选择一个类别" />
                                    </SelectTrigger>
                                    <SelectContent>
                                        <SelectGroup>
                                            <SelectItem :value="category.id" v-for="category in categories"
                                                :key="category.id">
                                                <span :class="categoryStore.getIconClass(category.id)"></span>
                                                {{ category.name }}
                                            </SelectItem>
                                        </SelectGroup>
                                    </SelectContent>
                                </Select>
                            </div>
                            <div class="flex items-center">
                                <label class="text-sm font-medium w-20">标题</label>
                                <Input v-model="title" class="w-full" placeholder="我是标题..." />
                            </div>
                            <div class="flex items-start">
                                <label class="text-sm font-medium w-20 mt-2">内容</label>
                                <Textarea v-model="content" class="w-full h-32" placeholder="说点什么..."></Textarea>
                            </div>
                        </div>
                        <DialogFooter>
                            <DialogClose as-child>
                                <Button type="submit" :disabled="!title || !content || !(categoryId !== 0)"
                                    @click="create()">
                                    发布！
                                </Button>
                            </DialogClose>
                        </DialogFooter>
                    </DialogContent>
                </Dialog>
            </div>

            <div class="border-t-3 mt-2"></div>
            <div>
                <div class="p-2 text-base flex rounded-lg">类别</div>
                <div @click="changeCategory(category)" v-for="category in categories" :key="category.id"
                    class="p-2 text-lg cursor-pointer hover:bg-sky-100 flex rounded-lg"
                    :class="category.id === currentCategory ? 'bg-sky-100' : ''">
                    <span :class="categoryStore.getIconClass(category.id)"></span>
                    <p class="pl-3">{{ category.name }}</p>
                </div>
            </div>
        </div>

        <!-- 内容区域 - 使用 router-view 显示子路由 -->
        <div class="w-4/5 px-3 bg-white rounded-lg shadow-sm h-full overflow-y-auto scrollbar-edge">
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
    import { Button } from "@/components/ui/button"
    import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, DialogClose } from "@/components/ui/dialog"
    import { Select, SelectContent, SelectGroup, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
    import { Input } from "@/components/ui/input"
    import { Textarea } from "@/components/ui/textarea"
    import { useForumCategoryStore } from '@/stores/ForumCategoryStore'
    import { useRouter } from 'vue-router'
    const router = useRouter()
    import { usePostStore } from '@/stores/PostStore'
    const postStore = usePostStore()

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
        const response = await axios.get("/api/user/forum/category")
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

    // 发布帖子
    const categoryId = ref(0)
    const title = ref('')
    const content = ref('')

    const create = async () => {
        const response = await axios.post("/api/user/forum/post/create", {
            categoryId: categoryId.value,
            title: title.value,
            content: content.value
        })

        // 在当前分类的发布
        if (categoryId.value == currentCategory.value) {
            postStore.setPostSimple(response.data.data)
        }
        const res = categories.value.find(category => category.id === categoryId.value) as Category
        changeCategory(res)
        categoryId.value = 0
        title.value = ''
        content.value = ''
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