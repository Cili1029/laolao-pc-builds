<template>
    <div class="min-h-screen bg-gradient-to-br from-slate-50 via-white to-slate-100 px-6 py-4">
        <div class="mx-auto flex max-w-7xl gap-6">
            <!-- 侧栏 -->
            <div
                class="sticky top-0 h-fit w-1/5 rounded-2xl border border-slate-100 bg-white/90 p-4 shadow-xl backdrop-blur">
                <div class="space-y-4">
                    <div>
                        <p class="text-xs font-semibold uppercase tracking-[0.2em] text-slate-400">工具</p>
                        <Dialog>
                            <DialogTrigger as-child>
                                <div
                                    class="mt-3 flex cursor-pointer items-center rounded-xl border border-transparent px-4 py-3 transition hover:border-sky-100 hover:bg-sky-50">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="45" height="45" viewBox="0 0 24 24">
                                        <path fill="#36c9b1"
                                            d="M18.404 2.998c-.757-.754-2.077-.751-2.828.005l-1.784 1.791L11.586 7H7a1 1 0 0 0-.939.658l-4 11c-.133.365-.042.774.232 1.049l2 2a1 1 0 0 0 1.049.232l11-4A1 1 0 0 0 17 17v-4.586l2.207-2.207v-.001h.001L21 8.409c.378-.378.586-.881.585-1.415c0-.535-.209-1.038-.588-1.415zm-3.111 8.295A1 1 0 0 0 15 12v4.3l-9.249 3.363l4.671-4.671c.026.001.052.008.078.008A1.5 1.5 0 1 0 9 13.5c0 .026.007.052.008.078l-4.671 4.671L7.7 9H12c.266 0 .52-.105.707-.293L14.5 6.914L17.086 9.5zm3.206-3.208l-2.586-2.586l1.079-1.084l2.593 2.581z" />
                                    </svg>
                                    <div class="pl-3">
                                        <p class="text-base font-semibold text-slate-800">我有话说</p>
                                        <p class="text-xs text-slate-500">发布一个新帖</p>
                                    </div>
                                </div>
                            </DialogTrigger>
                            <DialogContent class="sm:max-w-[800px]">
                                <DialogHeader>
                                    <DialogTitle>发布帖子</DialogTitle>
                                    <DialogDescription>
                                        <div
                                            class="mt-5 flex items-center rounded-xl bg-sky-50 px-5 py-3 text-slate-700">
                                            <span class="text-base leading-relaxed">
                                                <span class="font-bold">真诚、友善、团结、专业</span>，共建你我引以为荣之社区。
                                            </span>
                                        </div>
                                    </DialogDescription>
                                </DialogHeader>

                                <div class="space-y-4">
                                    <div class="flex items-center">
                                        <label class="w-16 text-sm font-medium text-slate-600">类别</label>
                                        <Select v-model="categoryId">
                                            <SelectTrigger class="w-[220px] rounded-xl">
                                                <SelectValue placeholder="选择一个类别" />
                                            </SelectTrigger>
                                            <SelectContent>
                                                <SelectGroup>
                                                    <template v-for="category in categories" :key="category.id">
                                                        <SelectItem v-if="category.id !== 5" :value="category.id">
                                                            <img :src="category.image" class="h-10 w-10" />
                                                            <span>{{ category.name }}</span>
                                                        </SelectItem>
                                                    </template>
                                                </SelectGroup>
                                            </SelectContent>
                                        </Select>
                                    </div>
                                    <div class="flex items-center gap-3">
                                        <label class="w-16 text-sm font-medium text-slate-600">标题</label>
                                        <Input v-model="title" class="w-full rounded-xl" placeholder="我是标题..." />
                                    </div>
                                    <div class="flex items-start gap-3">
                                        <label class="w-16 pt-2 text-sm font-medium text-slate-600">内容</label>
                                        <Textarea v-model="content" class="h-40 w-full rounded-xl"
                                            placeholder="说点什么..."></Textarea>
                                    </div>
                                </div>
                                <DialogFooter class="flex flex-col gap-3 sm:flex-row sm:justify-start">
                                    <Dialog>
                                        <DialogTrigger as-child>
                                            <Button :disabled="uploading" class="rounded-full px-5">
                                                <span class="icon-[charm--folder]"></span>
                                                {{ uploading ? "上传中" : fileCount > 0 ? `上传了${fileCount}张图片` : "上传图片（可选）"
                                                }}
                                            </Button>
                                        </DialogTrigger>
                                        <DialogContent class="md:max-w-4xl">
                                            <DialogHeader>
                                                <DialogTitle>上传图片</DialogTitle>
                                            </DialogHeader>
                                            <div>
                                                <FileUpload v-model:data="images" :max-files="5" />
                                            </div>
                                            <DialogFooter class="sm:justify-start">
                                                <DialogClose as-child>
                                                    <Button type="button" class="w-full" @click="uploadFiles()">
                                                        提交
                                                    </Button>
                                                </DialogClose>
                                            </DialogFooter>
                                        </DialogContent>
                                    </Dialog>
                                    <DialogClose as-child class="sm:ml-auto">
                                        <Button type="submit" class="rounded-full px-6 shadow-lg shadow-orange-200/60"
                                            :disabled="!title || !content || !(categoryId !== 0) || uploading"
                                            @click="create()">
                                            <span class="icon-[charm--rocket]"></span>
                                            发布！
                                        </Button>
                                    </DialogClose>
                                </DialogFooter>
                            </DialogContent>
                        </Dialog>
                    </div>

                    <div class="space-y-3">
                        <p class="text-xs font-semibold uppercase tracking-[0.2em] text-slate-400">板块</p>
                        <div class="space-y-2">
                            <div @click="changeCategory(category.id)" v-for="category in categories" :key="category.id"
                                class="flex cursor-pointer items-center rounded-xl px-4 py-3 transition"
                                :class="category.id === categoryStore.currentCategory ? 'bg-sky-50 shadow-inner ring-1 ring-sky-100' : 'hover:bg-slate-50'">
                                <img :src="category.image" class="h-10 w-10" />
                                <div class="pl-3">
                                    <p class="text-sm font-semibold text-slate-800">{{ category.name }}</p>
                                    <p class="text-xs text-slate-500" v-if="category.description">{{
                                        category.description }}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 内容区域 - 使用 router-view 显示子路由 -->
            <div class="flex-1 overflow-hidden rounded-3xl border border-slate-100 bg-white/90 shadow-xl backdrop-blur">
                <RouterView v-slot="{ Component }">
                    <transition name="fade" mode="out-in">
                        <component :is="Component" />
                    </transition>
                </RouterView>
            </div>
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
    import FileUpload from '@/components/front/common/Upload.vue';
    import { useForumCategoryStore } from '@/stores/ForumCategoryStore'
    import { useRouter, useRoute } from 'vue-router'
    const route = useRoute()
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
        image: string
        description: string
    }

    const categories = ref<Category[]>([])

    // 获取分类
    const getCategory = async () => {
        const response = await axios.get("/api/user/forum/category")
        categories.value = response.data.data
        if (Number(route.params.categoryId) === -1) {
            // 默认页
            router.replace(`/forum/${categories.value[1]?.id}`)
            categoryStore.currentCategory = categories.value[1]!.id
            categoryStore.setCategories(categories.value)
        } else {
            const category = categories.value.find(c => c.id === Number(route.params.categoryId))
            if (category) {
                categoryStore.currentCategory = category.id
                categoryStore.setCategories(categories.value)
            } else {
                router.push('/home');
            }
        }
    }

    const changeCategory = (id: number) => {
        categoryStore.currentCategory = id
        // 切换分类时回到帖子列表
        router.replace(`/forum/${id}`)
    }

    // 发布帖子
    const categoryId = ref(0)
    const title = ref('')
    const content = ref('')
    const url = ref([])

    const create = async () => {
        const response = await axios.post("/api/user/forum/post/create", {
            categoryId: categoryId.value,
            title: title.value,
            content: content.value,
            images: url.value
        })

        // 在当前分类的发布
        if (categoryId.value == categoryStore.currentCategory) {
            postStore.setPostSimple(response.data.data)
        }
        const res = categories.value.find(category => category.id === categoryId.value) as Category
        changeCategory(res.id)
        categoryId.value = 0
        title.value = ''
        content.value = ''
        url.value = []
        images.value = []
        fileCount.value = 0
    }

    // 图片上传
    const images = ref<File[]>([])
    const fileCount = ref(0)
    const uploading = ref<boolean>(false)

    const uploadFiles = async () => {
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
                formData.append('type', "postImages")
            });

            // 发送 POST 请求
            uploading.value = true
            const response = await axios.post("/api/common/upload", formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            fileCount.value = fileCount.value + response.data.data.count
            url.value = response.data.data.images
            console.log(url.value)
        } catch (error) {
            console.error('上传失败:', error)
        } finally {
            uploading.value = false
        }
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