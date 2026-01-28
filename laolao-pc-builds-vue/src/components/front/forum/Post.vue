<template>
    <div class="h-full overflow-y-auto p-3 scrollbar-edge">
        <!-- 贴主 -->
        <div class="bg-white/90 p-5">
            <div class="flex justify-between">
                <RouterLink :to="`/user/${post?.user.id}`" class="flex items-center space-x-3">
                    <Avatar
                        class="h-16 w-16 cursor-pointer rounded-full text-2xl shadow ring-2 ring-transparent transition hover:-translate-y-0.5 hover:ring-sky-300">
                        <AvatarImage :src="post?.user.avatar || ''" alt="用户头像" />
                        <AvatarFallback>{{ post?.user.name.substring(0, 1) }}</AvatarFallback>
                    </Avatar>
                    <p class="text-xl font-semibold text-slate-800">{{ post?.user.name }}</p>
                </RouterLink>
                <p>{{ formatTime(post?.createdAt) }}</p>
            </div>

            <p class="text-2xl font-black text-slate-900">{{ post?.title }}</p>
            <!-- 内容 -->
            <div class="w-full space-y-4">
                <div class="text-base leading-relaxed text-slate-700">
                    <p class="whitespace-pre-line">{{ post?.content }}</p>
                    <div class="mt-4 grid gap-4 md:grid-cols-2">
                        <img :src="image" v-for="image in post?.images" :key="image"
                            class="max-h-96 w-full rounded-2xl object-cover shadow transition hover:shadow-lg"
                            alt="帖子图片">
                    </div>
                </div>
                <div class="flex items-center justify-between">
                    <div class="flex gap-6 text-center text-sm text-slate-500">
                        <div @click="changeCategory()"
                            class="inline-flex w-fit items-center rounded-full bg-slate-100/80 px-3 py-1 text-sm text-slate-600 transition hover:bg-slate-200">
                            <img :src="categoryStore.getCategoryById(post.categoryId)?.image" class="h-5 w-5" />
                            {{ categoryStore.getCategoryById(post.categoryId)?.name }}
                        </div>
                        <div>
                            <p class="text-base font-bold text-slate-900">{{ post?.commentCount }}</p>
                            <p>评论量</p>
                        </div>
                        <div class="flex flex-col items-center">
                            <p class="text-base font-bold text-slate-900">{{ post?.likeCount }}</p>
                            <p>点赞</p>
                        </div>
                    </div>
                    <div class="flex items-center gap-2">
                        <div @click="like(1, post!.id, 0)"
                            class="flex h-10 w-10 items-center justify-center rounded-full bg-slate-100 text-xl transition hover:bg-slate-200">
                            <ThumbsUp :class="post?.like !== 0 ? 'text-orange-500' : null" />
                        </div>
                        <div class="flex items-center space-x-2">
                            <AlertDialog v-if="post?.user.id === userStore.user.id || userStore.user.admin === 1">
                                <AlertDialogTrigger as-child>
                                    <p class="cursor-pointer text-xs text-slate-400 hover:text-orange-500">删除帖子</p>
                                </AlertDialogTrigger>
                                <AlertDialogContent>
                                    <AlertDialogHeader>
                                        <AlertDialogTitle>确定要删除该帖子吗？</AlertDialogTitle>
                                        <AlertDialogDescription>
                                            操作一旦完成无法撤回，请谨慎选择
                                        </AlertDialogDescription>
                                    </AlertDialogHeader>
                                    <AlertDialogFooter>
                                        <AlertDialogCancel>算了</AlertDialogCancel>
                                        <AlertDialogAction @click="deletePost(post?.id)">删除</AlertDialogAction>
                                    </AlertDialogFooter>
                                </AlertDialogContent>
                            </AlertDialog>

                            <AlertDialog v-if="userStore.user.admin === 1">
                                <AlertDialogTrigger as-child>
                                    <p class="cursor-pointer text-xs text-slate-400 hover:text-orange-500">彻底删除
                                    </p>
                                </AlertDialogTrigger>
                                <AlertDialogContent>
                                    <AlertDialogHeader>
                                        <AlertDialogTitle>确定要删除该回复吗？</AlertDialogTitle>
                                        <AlertDialogDescription>
                                            操作一旦完成无法撤回，请谨慎选择
                                        </AlertDialogDescription>
                                    </AlertDialogHeader>
                                    <AlertDialogFooter>
                                        <AlertDialogCancel>算了</AlertDialogCancel>
                                        <AlertDialogAction @click="adminDeletePost()">删除
                                        </AlertDialogAction>
                                    </AlertDialogFooter>
                                </AlertDialogContent>
                            </AlertDialog>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="w-full h-px bg-gray-200"></div>

        <!-- 评论 -->
        <div class="py-4 space-y-4">
            <div v-for="comment in post?.comment" :key="comment.id"
                class="space-y-2 rounded-base border border-slate-100 bg-white/90 p-5 shadow-sm">
                <div class="flex justify-between items-center">
                    <RouterLink :to="`/user/${comment.user.id}`" class="flex items-center space-x-3">
                        <Avatar
                            class="h-12 w-12 cursor-pointer rounded-full text-2xl ring-2 ring-transparent transition hover:-translate-y-0.5 hover:ring-sky-300">
                            <AvatarImage :src="comment.user.avatar || ''" alt="用户头像" />
                            <AvatarFallback>{{ comment.user.name.substring(0, 1) }}</AvatarFallback>
                        </Avatar>
                        <div class="flex flex-col">
                            <p class="font-semibold text-slate-800">{{ comment.user.name }}</p>
                            <p class=" text-xs">{{ formatTime(comment.createdAt) }}</p>
                        </div>
                    </RouterLink>
                    <div class="flex items-center gap-1 text-slate-600">
                        <p class="text-base font-semibold">{{ comment.likeCount }}</p>
                        <div @click="like(2, comment?.id, 0)"
                            class="flex h-10 w-10 items-center justify-center rounded-full bg-slate-100 text-xl transition hover:bg-slate-200">
                            <ThumbsUp :class="comment?.like !== 0 ? 'text-orange-500' : null" />
                        </div>
                    </div>
                </div>
                <!-- 头像 -->

                <!-- 直接评论 -->
                <div class="flex gap-4">
                    <!-- 内容 -->
                    <div class="w-full space-y-4">
                        <div class="rounded-2xl bg-slate-50/70 px-4 py-3 text-slate-700">
                            <p class="whitespace-pre-line">{{ comment.content }}</p>
                            <div class="mt-4 grid gap-3 md:grid-cols-2">
                                <img :src="image" v-for="image in comment.images" :key="image"
                                    class="max-h-72 w-full rounded-2xl object-cover shadow transition hover:shadow-lg"
                                    alt="评论图片">
                            </div>
                        </div>
                        <div class="flex flex-wrap items-center justify-between gap-3">
                            <div class="flex flex-wrap items-center gap-2">
                                <div v-if="replyMap.has(comment.id)">
                                    <Button v-if="!replyMap.get(comment.id)" variant="secondary"
                                        class="rounded-full px-4" @click="openReply(comment.id), getReply(comment.id)">
                                        {{ comment.replyCount }}条回复
                                        <ChevronDown />
                                    </Button>
                                    <Button v-else variant="secondary" class="rounded-full px-4"
                                        @click="openReply(comment.id)">
                                        {{ comment.replyCount }}条回复
                                        <ChevronUp />
                                    </Button>
                                </div>
                                <Dialog>
                                    <DialogTrigger as-child>
                                        <Button class="rounded-full" variant="secondary">回复层主</Button>
                                    </DialogTrigger>
                                    <DialogContent class="sm:max-w-[600px]">
                                        <DialogHeader>
                                            <DialogTitle>回复层主</DialogTitle>
                                        </DialogHeader>
                                        <Textarea class="h-32 rounded-2xl" v-model="myComment"
                                            placeholder="说点什么..."></Textarea>
                                        <DialogFooter>
                                            <div class="flex flex-wrap items-center justify-end gap-3">
                                                <Image @click="showReplyDialog = true" class="w-[1.5em] h-[1.5em]" />
                                                <FileManager v-model:open="showReplyDialog" v-model="replyImg"
                                                    :max-files="1" upload-api="/api/common/file/upload"
                                                    delete-api="/api/common/file/delete"
                                                    :upload-extra-data="{ type: 'laolaoPC/forum/comment' }" />
                                                <DialogClose as-child class="ml-auto">
                                                    <Button :disabled="!myComment" class="rounded-full px-5"
                                                        @click="submitReply(comment.id)">
                                                        <Rocket />
                                                        发送！
                                                    </Button>
                                                </DialogClose>
                                            </div>
                                        </DialogFooter>
                                    </DialogContent>
                                </Dialog>
                            </div>
                            <div class="flex items-center gap-3 space-x-2">
                                <AlertDialog v-if="comment.user.id === userStore.user.id || userStore.user.admin === 1">
                                    <AlertDialogTrigger as-child>
                                        <p class="cursor-pointer text-xs text-slate-400 hover:text-orange-500">删除评论
                                        </p>
                                    </AlertDialogTrigger>
                                    <AlertDialogContent>
                                        <AlertDialogHeader>
                                            <AlertDialogTitle>确定要删除该评论吗？</AlertDialogTitle>
                                            <AlertDialogDescription>
                                                操作一旦完成无法撤回，请谨慎选择
                                            </AlertDialogDescription>
                                        </AlertDialogHeader>
                                        <AlertDialogFooter>
                                            <AlertDialogCancel>算了</AlertDialogCancel>
                                            <AlertDialogAction @click="deleteComment(comment.id)">删除
                                            </AlertDialogAction>
                                        </AlertDialogFooter>
                                    </AlertDialogContent>
                                </AlertDialog>

                                <AlertDialog v-if="userStore.user.admin === 1">
                                    <AlertDialogTrigger as-child>
                                        <p class="cursor-pointer text-xs text-slate-400 hover:text-orange-500">彻底删除
                                        </p>
                                    </AlertDialogTrigger>
                                    <AlertDialogContent>
                                        <AlertDialogHeader>
                                            <AlertDialogTitle>确定要删除该回复吗？</AlertDialogTitle>
                                            <AlertDialogDescription>
                                                操作一旦完成无法撤回，请谨慎选择
                                            </AlertDialogDescription>
                                        </AlertDialogHeader>
                                        <AlertDialogFooter>
                                            <AlertDialogCancel>算了</AlertDialogCancel>
                                            <AlertDialogAction @click="adminDeleteComment(1, comment.id)">删除
                                            </AlertDialogAction>
                                        </AlertDialogFooter>
                                    </AlertDialogContent>
                                </AlertDialog>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 评论的回复 -->
                <div v-if="replyMap.get(comment.id)" class="space-y-3 rounded-2xl bg-slate-50/80 p-4">
                    <div class="flex gap-4" v-for="reply in comment.reply" :key="reply.id">
                        <!-- 头像 -->
                        <RouterLink :to="`/user/${reply.user.id}`">
                            <Avatar
                                class="h-11 w-11 cursor-pointer rounded-full text-xl ring-2 ring-transparent transition hover:-translate-y-0.5 hover:ring-sky-300">
                                <AvatarImage :src="reply.user.avatar || ''" alt="用户头像" />
                                <AvatarFallback>{{ reply.user.name.substring(0, 1) }}</AvatarFallback>
                            </Avatar>
                        </RouterLink>
                        <!-- 内容 -->
                        <div
                            class="w-full space-y-3 rounded-base border border-slate-100 bg-white/80 px-4 py-3 shadow-sm">
                            <div class="flex flex-wrap justify-between text-xs text-slate-500">
                                <p class="font-semibold text-slate-800">{{ reply.user.name }}</p>
                                <p>{{ formatTime(reply.createdAt) }}</p>
                            </div>
                            <p class="text-sm text-slate-700">{{ reply.content }}</p>
                            <div class="grid gap-3 md:grid-cols-2">
                                <img :src="image" v-for="image in reply.images" :key="image"
                                    class="max-h-64 w-full rounded-2xl object-cover shadow transition hover:shadow-lg"
                                    alt="回复图片">
                            </div>
                            <div class="flex items-center justify-between">
                                <div class="flex items-center gap-2 text-slate-600">
                                    <p class="text-base font-semibold">{{ reply.likeCount }}</p>
                                    <div @click="like(2, reply?.id, reply.parent)"
                                        class="flex h-9 w-9 items-center justify-center rounded-full bg-slate-100 text-lg transition hover:bg-slate-200">
                                        <ThumbsUp :class="reply?.like !== 0 ? 'text-orange-500' : null" />
                                    </div>
                                </div>
                                <div class="flex items-center space-x-2">
                                    <AlertDialog
                                        v-if="reply.user.id === userStore.user.id || userStore.user.admin === 1">
                                        <AlertDialogTrigger as-child>
                                            <p class="cursor-pointer text-xs text-slate-400 hover:text-orange-500">删除回复
                                            </p>
                                        </AlertDialogTrigger>
                                        <AlertDialogContent>
                                            <AlertDialogHeader>
                                                <AlertDialogTitle>确定要删除该回复吗？</AlertDialogTitle>
                                                <AlertDialogDescription>
                                                    操作一旦完成无法撤回，请谨慎选择
                                                </AlertDialogDescription>
                                            </AlertDialogHeader>
                                            <AlertDialogFooter>
                                                <AlertDialogCancel>算了</AlertDialogCancel>
                                                <AlertDialogAction @click="deleteReply(comment.id, reply.id)">删除
                                                </AlertDialogAction>
                                            </AlertDialogFooter>
                                        </AlertDialogContent>
                                    </AlertDialog>

                                    <AlertDialog v-if="userStore.user.admin === 1">
                                        <AlertDialogTrigger as-child>
                                            <p class="cursor-pointer text-xs text-slate-400 hover:text-orange-500">彻底删除
                                            </p>
                                        </AlertDialogTrigger>
                                        <AlertDialogContent>
                                            <AlertDialogHeader>
                                                <AlertDialogTitle>确定要删除该回复吗？</AlertDialogTitle>
                                                <AlertDialogDescription>
                                                    操作一旦完成无法撤回，请谨慎选择
                                                </AlertDialogDescription>
                                            </AlertDialogHeader>
                                            <AlertDialogFooter>
                                                <AlertDialogCancel>算了</AlertDialogCancel>
                                                <AlertDialogAction @click="adminDeleteComment(2, reply.id)">删除
                                                </AlertDialogAction>
                                            </AlertDialogFooter>
                                        </AlertDialogContent>
                                    </AlertDialog>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 回复 -->
        <div class="mt-4 grid gap-3">
            <Textarea v-model="myComment" class="h-32 rounded-2xl" placeholder="说点什么..."></Textarea>
            <div class="flex flex-wrap items-center justify-end gap-3">
                <Image @click="showCommentDialog = true" class="w-[1.5em] h-[1.5em]" />
                <FileManager v-model:open="showCommentDialog" v-model="commentImg" :max-files="1"
                    upload-api="/api/common/file/upload" delete-api="/api/common/file/delete"
                    :upload-extra-data="{ type: 'laolaoPC/forum/comment' }" />
                <Button @click="submitComment()" :disabled="!myComment"
                    class="rounded-full px-6 shadow-lg shadow-orange-200/60">
                    <Rocket />
                    发送！
                </Button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
    import axios from '@/utils/myAxios'
    import { onMounted, ref } from 'vue'
    import { useRoute } from 'vue-router'
    import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
    import { Textarea } from "@/components/ui/textarea"
    import { Button } from "@/components/ui/button"
    import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle, AlertDialogTrigger, } from '@/components/ui/alert-dialog'
    import { Dialog, DialogContent, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, DialogClose } from "@/components/ui/dialog"
    import dayjs from 'dayjs'
    import relativeTime from 'dayjs/plugin/relativeTime'
    import 'dayjs/locale/zh-cn'
    import FileManager from '@/components/common/FileManager.vue'
    import { useForumCategoryStore } from '@/stores/ForumCategoryStore'
    const categoryStore = useForumCategoryStore()
    import { useUserStore } from '@/stores/UserStore'
    const userStore = useUserStore()
    import router from '@/router'
    import { ChevronDown, ChevronUp, Image, Rocket, ThumbsUp } from 'lucide-vue-next'


    // 初始化dayjs
    dayjs.extend(relativeTime)
    dayjs.locale('zh-cn')

    const route = useRoute()
    // 帖子Id
    const id = Number(route.params.postId)

    onMounted(() => {
        getPost(id)
    })

    interface UserVO {
        id: number
        username: string
        avatar: string
        name: string
    }
    interface ReplyVO {
        id: number
        user: UserVO
        parent: number
        content: string
        images: string[]
        likeCount: number
        like: number
        createdAt: string
    }
    interface CommentVO {
        id: number
        postId: number
        user: UserVO
        content: string
        images: string[]
        likeCount: number
        like: number
        createdAt: string
        replyCount: number
        reply: ReplyVO[]
    }
    interface PostVO {
        id: number
        categoryId: number
        user: UserVO
        title: string
        content: string
        images: string[]
        likeCount: number
        like: number
        commentCount: number
        createdAt: string
        comment: CommentVO[]
    }

    const post = ref<PostVO>({
        id: 0,
        categoryId: 0,
        user: {
            id: 0,
            username: '',
            avatar: '',
            name: ''
        },
        title: '',
        content: '',
        images: [], // 初始化为空数组
        likeCount: 0,
        like: 0,
        commentCount: 0,
        createdAt: '',
        comment: [] // 嵌套的评论数组初始化为空数组
    })

    const getPost = async (id: number) => {
        const response = await axios.get("/api/user/forum/post", {
            params: {
                id: id
            }
        })
        post.value = response.data.data
        if (post.value?.comment) {
            // 设置为有回复
            setReply(post.value.comment)
        }
        if (categoryStore.currentCategory !== 5) {
            categoryStore.currentCategory = post.value!.categoryId
        }
    }

    const getReply = async (id: number) => {

        const res = post.value?.comment.find(Comment => Comment.id === id)
        // 有就不加载了
        if (res?.reply) {
            return
        }
        const response = await axios.get("/api/user/forum/post/reply", {
            params: {
                id: id
            }
        })
        res!.reply = response.data.data
    }


    // 时间格式化
    const formatTime = (timeStr: string | undefined) => {
        if (!timeStr) return '无效日期'
        return dayjs(timeStr).fromNow()
    }

    // 评论的回复
    const replyMap = ref(new Map<number, boolean>())
    const setReply = (commentList: CommentVO[]) => {
        commentList.forEach(comment => {
            if (comment.replyCount != 0) {
                replyMap.value.set(comment.id, false)
            }
        })
    }
    // 打开回复
    const openReply = (id: number) => {
        replyMap.value.set(id, !replyMap.value.get(id))
    }

    // 评论
    const myComment = ref('')

    // 直接评论
    const submitComment = async () => {
        // 之后追加最新消息
        const response = await axios.post("/api/user/forum/comment", {
            id: post.value?.id,
            content: myComment.value,
            images: commentImg.value
        })
        myComment.value = ''
        commentImg.value = []
        if (post.value && response.data.data) {
            if (!post.value.comment) {
                post.value.comment = []
            }
            post.value.comment.push(response.data.data)
            post.value.commentCount += 1
        }
    }

    // 评论的回复
    const submitReply = async (parent: number) => {
        // 之后追加最新消息
        const response = await axios.post("/api/user/forum/comment/reply", {
            id: post.value?.id,
            parent: parent,
            content: myComment.value,
            images: replyImg.value
        })
        const res = post.value?.comment.find(comment => comment.id === parent)
        if (response.data.code === 1 && res) {
            if (!res.reply) {
                res.reply = [] // 如果reply不存在，初始化为空数组
            }
            res.reply.push(response.data.data)
            replyMap.value.set(parent, true)
            res.replyCount += 1
            post.value!.commentCount += 1
        }
        myComment.value = ''
        replyImg.value = []
    }

    // 删除帖子
    const deletePost = async (id: number) => {
        await axios.delete(`/api/user/forum/post/${id}`)
        router.back()
    }

    // 删除评论
    const deleteComment = async (id: number) => {
        await axios.delete("/api/user/forum/comment", {
            params: {
                postId: post.value?.id,
                commentId: id
            }
        })
        if (post.value) {
            const comment = post.value.comment.find(comment => comment.id === id)
            post.value.comment = post.value.comment.filter(comment => comment.id !== id)
            if (!comment?.reply) {
                post.value.commentCount -= 1
            } else {
                post.value.commentCount -= (comment.reply.length + 1)
            }
        }
    }

    // 删除楼中楼评论
    const deleteReply = async (commentId: number, replyId: number) => {
        await axios.delete("/api/user/forum/comment/reply", {
            params: {
                postId: post.value?.id,
                replyId: replyId
            }
        })
        if (post.value) {
            const comment = post.value.comment.find(c => c.id === commentId)
            if (comment && comment.reply) {
                // 删掉那条信息
                comment.reply = comment.reply.filter(reply => reply.id !== replyId)
                comment.replyCount -= 1
                // 判断这是唯一一条就删除
                if (comment.reply.length === 0) {
                    if (replyMap.value.has(commentId)) {
                        replyMap.value.delete(commentId)
                    }
                    comment.replyCount = 0
                }
                post.value.commentCount -= 1
            }
        }
    }

    // 点赞
    const like = async (likeType: number, likeId: number, parentId: number) => {
        try {
            const response = await axios.post("/api/user/forum/like", {
                likeType: likeType,
                likeId: likeId
            })
            if (response.data.code === 1) {
                const delta = response.data.data // 1 或 -1
                const newLikeStatus = delta === 1 ? 1 : 0
                switch (likeType) {
                    case 1: // 帖子
                        if (post.value) {
                            post.value.likeCount += delta
                            post.value.like = newLikeStatus
                        }
                        break
                    case 2: // 评论或回复
                        if (parentId === 0) {
                            // 直接评论
                            const comment = post.value?.comment.find(c => c.id === likeId)
                            if (comment) {
                                comment.likeCount += delta
                                comment.like = newLikeStatus
                            }
                        } else {
                            // 回复
                            const parentComment = post.value?.comment.find(c => c.id === parentId)
                            const reply = parentComment?.reply.find(r => r.id === likeId)
                            if (reply) {
                                reply.likeCount += delta
                                reply.like = newLikeStatus
                            }
                        }
                        break
                }
            }
        } catch (error) {
            console.error('点赞失败:', error)
        }
    }

    const changeCategory = () => {
        categoryStore.currentCategory = post.value.categoryId
        // 切换分类时回到帖子列表
        router.replace(`/forum/${post.value.categoryId}`)
    }

    // 控制弹窗开关
    const showCommentDialog = ref(false)
    const showReplyDialog = ref(false)
    // 数据源：现有的图片 URL
    const commentImg = ref<string[]>([])
    const replyImg = ref<string[]>([])

    // 管理员功能
    const adminDeleteComment = async (type: number, commentId: number) => {
        try {
            await axios.delete(`/api/admin/forum/post/comment/${type}/${commentId}`)
        } catch (error) {
            console.log(error)
        }
        getPost(id)
    }

    const adminDeletePost = async () => {
        try {
            await axios.delete("/api/admin/forum/post", {
                params: {
                    id: post.value.id
                }
            })
        } catch (error) {
            console.log(error)
        }
        router.push(`/forum/${post.value.categoryId}`)
    }
</script>

<style scoped></style>