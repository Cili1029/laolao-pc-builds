<template>
  <div class="h-full flex flex-col">
    <!-- 个人信息栏 -->
    <div class="w-full h-48 rounded-lg shadow-sm p-5 flex mt-auto justify-between my-2">
      <div class="flex mt-auto">
        <Avatar class="w-25 h-25 text-2xl cursor-pointer hover:ring-2 hover:ring-blue-500 transition-all rounded-full">
          <AvatarImage :src="user?.user.avatar || ''" alt="用户头像" />
          <AvatarFallback>{{ user?.user.name.substring(0, 1) }}</AvatarFallback>
        </Avatar>
        <div class="px-5 flex flex-col justify-center">
          <p class="text-3xl">{{ user?.user.name }}</p>
          <p class="text-xl">{{ user?.user.username }}</p>
        </div>
      </div>
      <div class="flex flex-col mt-auto">
        <Dialog>
          <DialogTrigger as-child>
            <Button v-show="userStore.user.id === user?.user.id" variant="outline">
              编辑资料
            </Button>
          </DialogTrigger>
          <DialogContent class="sm:max-w-[425px]">
            <DialogHeader>
              <DialogTitle>编辑资料</DialogTitle>
              <DialogDescription></DialogDescription>
            </DialogHeader>
            <div class="grid gap-4 py-4">
              <div class="flex justify-center">
                <Dialog>
                  <DialogTrigger as-child>
                    <Avatar
                      class="w-25 h-25 text-2xl cursor-pointer hover:ring-2 hover:ring-blue-500 transition-all rounded-full">
                      <AvatarImage :src="url" alt="用户头像" />
                      <AvatarFallback>{{ user?.user.name.substring(0, 1) }}</AvatarFallback>
                    </Avatar>
                  </DialogTrigger>
                  <DialogContent class="md:max-w-4xl">
                    <DialogHeader>
                      <DialogTitle>上传图片</DialogTitle>
                      <DialogDescription>
                      </DialogDescription>
                    </DialogHeader>
                    <div>
                      <FileUpload v-model:data="images" :max-files="1" />
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
              </div>
              <div class="grid grid-cols-4 items-center gap-4">
                <Label for="name" class="text-right">
                  昵称
                </Label>
                <Input v-model="newName" id="name" class="col-span-3" />
              </div>
            </div>
            <DialogFooter>
              <DialogClose as-child>
                <Button type="submit" @click="update()" :disabled="!newName || uploading">
                  保存
                </Button>
              </DialogClose>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      </div>
    </div>
    <!-- 帖子信息 -->
    <div class="flex-1 flex flex-col rounded-lg shadow-sm p-2 bg-white overflow-hidden">
      <!-- 类别栏 -->
      <div class="flex gap-6">
        <div class="flex items-center cursor-pointer border-b-5" :class="currentType === 1 ? 'border-sky-400' : ''"
          @click="changeType(1)">
          <span class="icon-[humbleicons--chat] text-4xl mr-1"></span>
          <p class="text-2xl hover:text-sky-400">{{ userStore.user.id === user?.user.id ? "我" : "Ta" }}的帖子</p>
        </div>
        <div class="flex items-center cursor-pointer border-b-5" :class="currentType === 2 ? 'border-sky-400' : ''"
          @click="changeType(2)">
          <span class="icon-[material-symbols--thumb-up-outline] text-4xl mr-1"></span>
          <p class="text-2xl hover:text-sky-400">{{ userStore.user.id === user?.user.id ? "我" : "Ta" }}点赞的</p>
        </div>
      </div>
      <!-- 内容 -->
      <div class="flex-1 overflow-y-auto scrollbar-edge">
        <div v-if="currentType === 1 && user?.userPostList && user.userPostList.length > 0">
          <div>
            <div class="flex m-2 justify-between items-center">
              <div class="text-l justify-between items-center text-gray-500">
                <div class="w-22 font-bold">帖子</div>
              </div>
              <div class="text-l flex justify-between items-center text-gray-500">
                <div class="w-22 font-bold text-center">回复</div>
                <div class="w-22 font-bold text-center">点赞</div>
                <div class="w-22 font-bold text-center">最后回复</div>
              </div>
            </div>
            <div class="border-t-2"></div>
          </div>
          <div v-for="userPost in user?.userPostList" :key="userPost.id">
            <div class="flex mx-2 my-4 justify-between items-center">
              <router-link :to="`/forum/post/${userPost.id}`" class="text-xl cursor-pointer">
                {{ userPost.title }}
              </router-link>
              <div class="flex text-gray-600">
                <div class="w-22 font-bold text-center">{{ userPost.commentCount }}</div>
                <div class="w-22 font-bold text-center">{{ userPost.likeCount }}</div>
                <div class="w-22 font-bold text-center">{{ formatTime(userPost.updatedAt) }}</div>
              </div>
            </div>
            <div class="border-t-2"></div>
          </div>
        </div>
        <div v-else-if="currentType === 2 && user?.likePostList && user.likePostList.length > 0">
          <div>
            <div class="flex m-2 justify-between items-center">
              <div class="text-l justify-between items-center text-gray-500">
                <div class="w-22 font-bold">帖子</div>
              </div>
              <div class="text-l flex justify-between items-center text-gray-500">
                <div class="w-22 font-bold text-center">回复</div>
                <div class="w-22 font-bold text-center">点赞</div>
                <div class="w-22 font-bold text-center">最后回复</div>
              </div>
            </div>
            <div class="border-t-2"></div>
          </div>
          <div v-for="likePost in user?.likePostList" :key="likePost.id">
            <div class="flex mx-2 my-4 justify-between items-center">
              <router-link :to="`/forum/post/${likePost.id}`" class="text-xl cursor-pointer">
                {{ likePost.title }}
              </router-link>
              <div class="flex text-gray-600">
                <div class="w-22 font-bold text-center">{{ likePost.commentCount }}</div>
                <div class="w-22 font-bold text-center">{{ likePost.likeCount }}</div>
                <div class="w-22 font-bold text-center">{{ formatTime(likePost.updatedAt) }}</div>
              </div>
            </div>
            <div class="border-t-2"></div>
          </div>
        </div>
        <div v-else class="h-full flex items-center justify-center">
          <div class="flex flex-col items-center">
            <span class="icon-[noto--enraged-face] text-9xl"></span>
            <div class="font-bold">什么也没有！</div>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script setup lang="ts">
  import axios from '@/utils/myAxios'
  import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
  import { Button } from "@/components/ui/button"
  import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, DialogClose } from "@/components/ui/dialog"
  import { Input } from "@/components/ui/input"
  import { Label } from "@/components/ui/label"
  import FileUpload from '@/components/Upload.vue';
  import { onMounted, ref, watch } from "vue"
  import { useRoute } from 'vue-router'
  const route = useRoute()
  import { useUserStore } from '@/stores/UserStore'
  const userStore = useUserStore()

  import dayjs from 'dayjs'
  import relativeTime from 'dayjs/plugin/relativeTime'
  import 'dayjs/locale/zh-cn'
  // 初始化dayjs
  dayjs.extend(relativeTime)
  dayjs.locale('zh-cn')

  // 时间格式化
  const formatTime = (timeStr: string | undefined) => {
    if (!timeStr) return '无效日期'
    return dayjs(timeStr).fromNow()
  }

  onMounted(() => {
    getUser()
  })

  watch(
    () => route.params.id,
    () => {
      getUser()
    }
  )

  interface PostSimple {
    id: number
    title: string
    likeCount: number
    commentCount: number
    updatedAt: string
  }

  interface User {
    id: number
    avatar: string
    username: string
    name: string
  }

  interface UserVO {
    user: User
    userPostList: PostSimple[]
    likePostList: PostSimple[]
  }

  const user = ref<UserVO>()

  const getUser = async () => {
    try {
      const response = await axios.get(`/api/user/user/${route.params.id}`)
      user.value = response.data.data
      newName.value = user.value!.user.name
      url.value = user.value?.user.avatar!
    } catch (error) {
      console.log(error)
    }
  }

  const newName = ref("")

  // 图片上传
  const images = ref<File[]>([])
  const fileCount = ref(0)
  const uploading = ref<boolean>(false)
  const url = ref('');

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
        formData.append('type', "avatars")
      });

      // 发送 POST 请求
      uploading.value = true
      const response = await axios.post("/api/common/upload", formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      fileCount.value = fileCount.value + response.data.data.count
      url.value = response.data.data.images[0]
    } catch (error) {
      console.error('上传失败:', error)
    } finally {
      uploading.value = false
    }
  }

  const update = async () => {
    try {
      if (url.value !== user.value?.user.avatar || newName.value !== user.value?.user.name) {
        await axios.post("/api/user/user/update", {
          name: newName.value,
          avatar: url.value
        })
        user.value!.user.name = newName.value
        user.value!.user.avatar = url.value
      }
      images.value = []
      fileCount.value = 0
    } catch (error) {
      console.log(error)
    }
  }

  // 显示类别
  const currentType = ref(1)

  const changeType = (type: number) => {
    currentType.value = type
  }
</script>