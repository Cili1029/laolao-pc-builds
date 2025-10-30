<template>
    <div class="h-full overflow-y-auto p-4">
        <!-- 贴主 -->
        <div>
            <!-- 标题 -->
            <div class="px-3 mt-5 pl-5 py-1 bg-sky-100 shadow-sm flex items-center">
                <span class="text-xl">
                    <span class="font-bold">真诚、友善、团结、专业</span>，共建你我引以为荣之社区。
                </span>
            </div>
            <div class="h-24 px-3 bg-white flex flex-col justify-center">
                <p class="text-2xl font-bold">{{ post?.title }}</p>
                <div class="flex">
                    <span class="text-xl" :class="categoryStore.getIconClass(categoryStore.category.id)"></span>
                    <p class="text-l">{{ categoryStore.category.name }}</p>
                </div>
            </div>
            <div class="border-t-3"></div>
            <!-- 内容 -->
            <div class="flex px-3 pt-3">
                <!-- 头像 -->
                <Avatar
                    class="w-14 h-14 text-2xl cursor-pointer hover:ring-2 hover:ring-blue-500 transition-all rounded-full">
                    <AvatarImage :src="post?.user.avatar || ''" alt="用户头像" />
                    <AvatarFallback>{{ post?.user.name.substring(0, 1) }}</AvatarFallback>
                </Avatar>
                <!-- 内容 -->
                <div class="p-2 w-full">
                    <div class="flex justify-between">
                        <p class="pb-4">{{ post?.user.name }}</p>
                        <p>{{ formatTime(post?.createdAt) }}</p>
                    </div>
                    <div>
                        <p class="pb-20">{{ post?.content }}</p>
                        <!-- 还有图片 -->
                    </div>
                    <div class="flex flex-col items-end gap-1">
                        <div class="flex items-center">
                            <p class="pr-2">{{ post?.likeCount }}</p>
                            <span class="icon-[streamline--hearts-symbol-remix]"></span>
                        </div>
                        <AlertDialog v-if="post?.user.id === userStore.user.id">
                            <AlertDialogTrigger as-child>
                                <p class="text-xs hover:text-orange-500">删除帖子</p>
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
                    </div>
                </div>
            </div>
            <div class="border-t-3"></div>
            <div class="flex my-1 pl-14">
                <div class="px-5 flex w-fit flex-col items-center">
                    <p class="text-blue-500">{{ post?.commentCount }}</p>
                    <p>评论量</p>
                </div>
                <div class="px-5 flex w-fit flex-col items-center">
                    <p class="text-blue-500">{{ post?.viewCount }}</p>
                    <p>浏览量</p>
                </div>
            </div>
            <div class="border-t-3"></div>
        </div>
        <!-- 评论 -->
        <div class="flex flex-col px-3 pt-3" v-for="comment in post?.comment" :key="comment.id">
            <!-- 直接评论 -->
            <div class="flex w-full">
                <!-- 头像 -->
                <Avatar
                    class="w-14 h-14 text-2xl cursor-pointer hover:ring-2 hover:ring-blue-500 transition-all rounded-full">
                    <AvatarImage :src="comment.user.avatar || ''" alt="用户头像" />
                    <AvatarFallback>{{ comment.user.name.substring(0, 1) }}</AvatarFallback>
                </Avatar>
                <!-- 内容 -->
                <div class="p-2 w-full">
                    <div class="flex justify-between">
                        <p class="pb-4">{{ comment.user.name }}</p>
                        <p>{{ formatTime(comment.createdAt) }}</p>
                    </div>
                    <div>
                        <p class="pb-10">{{ comment.content }}</p>
                        <!-- 还有图片 -->
                    </div>
                    <div class="pb-4 flex justify-between items-center">
                        <div class="flex">
                            <div v-if="replyMap.has(comment.id)">
                                <Button v-if="!replyMap.get(comment.id)" variant="secondary"
                                    @click="openReply(comment.id), getReply(comment.id)">{{ comment.replyCount }}条回复▼</Button>
                                <Button v-else variant="secondary"
                                    @click="openReply(comment.id)">{{ comment.replyCount }}条回复▲</Button>
                            </div>
                            <AlertDialog>
                                <AlertDialogTrigger as-child>
                                    <Button class="ml-2" variant="secondary">回复层主</Button>
                                </AlertDialogTrigger>
                                <AlertDialogContent>
                                    <AlertDialogHeader>
                                        <AlertDialogTitle>回复层主</AlertDialogTitle>
                                        <AlertDialogDescription></AlertDialogDescription>
                                        <Textarea class="h-32" v-model="myComment" placeholder="说点什么..."></Textarea>
                                    </AlertDialogHeader>
                                    <AlertDialogFooter>
                                        <AlertDialogCancel>算了</AlertDialogCancel>
                                        <AlertDialogAction :disabled="!myComment" @click="submitReply(comment.id)">
                                            发送</AlertDialogAction>
                                    </AlertDialogFooter>
                                </AlertDialogContent>
                            </AlertDialog>
                        </div>
                        <div class="flex flex-col items-end gap-1">
                            <div class="flex items-center">
                                <p class="pr-2">{{ comment.likeCount }}</p>
                                <span class="icon-[streamline--hearts-symbol-remix]"></span>
                            </div>
                            <AlertDialog v-if="comment.user.id === userStore.user.id">
                                <AlertDialogTrigger as-child>
                                    <p class="text-xs hover:text-orange-500">删除评论</p>
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
                                        <AlertDialogAction @click="deleteComment(comment.id)">删除</AlertDialogAction>
                                    </AlertDialogFooter>
                                </AlertDialogContent>
                            </AlertDialog>
                        </div>
                    </div>
                    <div class="border-t-2"></div>
                </div>
            </div>
            <!-- 评论的回复 -->
            <div class="shadow-md rounded-md">
                <div class="px-10 flex w-full" v-if="replyMap.get(comment.id)" v-for="reply in comment.reply"
                    :key="reply.id">
                    <!-- 头像 -->
                    <Avatar
                        class="w-14 h-14 text-2xl cursor-pointer hover:ring-2 hover:ring-blue-500 transition-all rounded-full">
                        <AvatarImage :src="reply.user.avatar || ''" alt="用户头像" />
                        <AvatarFallback>{{ reply.user.name.substring(0, 1) }}</AvatarFallback>
                    </Avatar>
                    <!-- 内容 -->
                    <div class="p-2 w-full">
                        <div class="flex justify-between">
                            <p class="pb-4">{{ reply.user.name }}</p>
                            <p>{{ formatTime(reply.createdAt) }}</p>
                        </div>
                        <div>
                            <p>{{ reply.content }}</p>
                            <!-- 还有图片 -->
                        </div>
                        <div class="flex flex-col items-end gap-1 pb-4">
                            <div class="flex items-center">
                                <p class="pr-2">{{ reply.likeCount }}</p>
                                <span class="icon-[streamline--hearts-symbol-remix]"></span>
                            </div>
                            <AlertDialog v-if="reply.user.id === userStore.user.id">
                                <AlertDialogTrigger as-child>
                                    <p class="text-xs hover:text-orange-500">删除回复</p>
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
                        </div>
                        <div class="border-t-2"></div>
                    </div>
                </div>
            </div>

        </div>
        <!-- 回复 -->
        <div class="border-t-3"></div>
        <div class="grid w-full gap-2 pb-15 pt-2">
            <Textarea v-model="myComment" class="h-32" placeholder="说点什么..."></Textarea>
            <Button @click="submitComment()" :disabled="!myComment">发送</Button>
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
    import dayjs from 'dayjs'
    import relativeTime from 'dayjs/plugin/relativeTime'
    import 'dayjs/locale/zh-cn'
    import { useForumCategoryStore } from '@/stores/ForumCategoryStore'
    const categoryStore = useForumCategoryStore()
    import { useUserStore } from '@/stores/UserStore'
    const userStore = useUserStore()
    import router from '@/router'


    // 初始化dayjs
    dayjs.extend(relativeTime)
    dayjs.locale('zh-cn')

    const route = useRoute()
    // 帖子Id
    const id = Number(route.params.id)

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
        image: string
        likeCount: number
        createdAt: string
    }
    interface CommentVO {
        id: number
        postId: number
        user: UserVO
        content: string
        image: string
        likeCount: number
        createdAt: string
        replyCount: number
        reply: ReplyVO[]
    }
    interface PostVO {
        id: number
        user: UserVO
        title: string
        content: string
        image: string
        viewCount: number
        likeCount: number
        commentCount: number
        createdAt: string
        comment: CommentVO[]
    }

    const post = ref<PostVO>()

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
            content: myComment.value
        })
        myComment.value = ''
        if (post.value && response.data.data) {
            if (!post.value.comment) {
                post.value.comment = []
            }
            post.value.comment.push(response.data.data)
        }
    }

    // 评论的回复
    const submitReply = async (parent: number) => {
        // 之后追加最新消息
        const response = await axios.post("/api/user/forum/comment/reply", {
            id: post.value?.id,
            parent: parent,
            content: myComment.value
        })
        const res = post.value?.comment.find(comment => comment.id === parent)
        if (response.data.code === 1 && res) {
            if (!res.reply) {
                res.reply = [] // 如果reply不存在，初始化为空数组
            }
            res.reply.push(response.data.data)
            replyMap.value.set(parent, true)
            res.replyCount += 1
        }
        myComment.value = ''
    }

    // 删除帖子
    const deletePost = async (id: number) => {
        await axios.delete(`/api/user/forum/post/${id}`)
        post.value = undefined
        router.back()
    }

    // 删除评论
    const deleteComment = async (id: number) => {
        await axios.delete(`/api/user/forum/comment/${id}`)
        if (post.value) {
            post.value.comment = post.value.comment.filter(comment => comment.id !== id)
        }
    }

    // 删除楼中楼评论
    const deleteReply = async (commentId: number, replyId: number) => {
        await axios.delete(`/api/user/forum/comment/reply/${replyId}`)
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

            }
        }
    }

</script>

<style scoped></style>