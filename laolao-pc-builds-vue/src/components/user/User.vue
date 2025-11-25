<template>
  <div class="h-full flex flex-col bg-slate-50 overflow-hidden">
    
    <!-- 顶部个人信息区域 -->
    <div class="bg-white shadow-sm border-b border-gray-100 z-10 shrink-0">
      <!-- 模拟 Banner 背景 (可选，用渐变色代替) -->
      <div class="h-32 bg-gradient-to-r from-blue-400 to-indigo-500 relative overflow-hidden">
          <div class="absolute inset-0 bg-white/10 pattern-dots"></div>
      </div>

      <div class="max-w-5xl mx-auto px-6 pb-6 relative">
        <div class="flex flex-col sm:flex-row items-end -mt-12 gap-6">
          
          <!-- 头像 (带边框，突出显示) -->
          <div class="relative group">
             <Avatar class="w-32 h-32 border-4 border-white shadow-md rounded-full bg-white text-4xl cursor-pointer">
               <AvatarImage :src="user?.user.avatar || ''" alt="用户头像" class="object-cover" />
               <AvatarFallback class="bg-indigo-50 text-indigo-500 font-bold">{{ user?.user.name.substring(0, 1) }}</AvatarFallback>
             </Avatar>
             <!-- 编辑头像的小提示 (仅自己可见) -->
             <div v-if="userStore.user.id === user?.user.id" class="absolute inset-0 rounded-full bg-black/40 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity pointer-events-none">
                <span class="icon-[material-symbols--edit-outline] text-white text-2xl"></span>
             </div>
          </div>

          <!-- 用户名与ID -->
          <div class="flex-1 min-w-0 pb-2">
             <h1 class="text-3xl font-bold text-gray-900 truncate">{{ user?.user.name }}</h1>
             <p class="text-gray-500 font-mono text-sm mt-1">@{{ user?.user.username }}</p>
          </div>

          <!-- 编辑资料按钮 -->
          <div class="pb-2">
            <Dialog>
              <DialogTrigger as-child>
                <Button v-show="userStore.user.id === user?.user.id" variant="outline" class="rounded-full px-6 border-gray-300 hover:bg-gray-50 hover:text-indigo-600 transition-colors">
                  <span class="icon-[material-symbols--edit-square-outline] mr-2"></span>
                  编辑资料
                </Button>
              </DialogTrigger>
              <DialogContent class="sm:max-w-[500px]">
                <DialogHeader>
                  <DialogTitle>编辑个人资料</DialogTitle>
                  <DialogDescription>修改您的头像和昵称</DialogDescription>
                </DialogHeader>
                <div class="flex flex-col gap-6 py-6">
                  <!-- 修改头像 -->
                  <div class="flex flex-col items-center gap-4">
                    <Dialog>
                      <DialogTrigger as-child>
                        <div class="relative group cursor-pointer">
                          <Avatar class="w-24 h-24 border-2 border-gray-100 shadow-sm">
                            <AvatarImage :src="url" class="object-cover" />
                            <AvatarFallback>{{ user?.user.name.substring(0, 1) }}</AvatarFallback>
                          </Avatar>
                          <div class="absolute inset-0 bg-black/50 rounded-full flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity">
                             <span class="text-white text-xs font-medium">更换</span>
                          </div>
                        </div>
                      </DialogTrigger>
                      <DialogContent class="md:max-w-xl">
                        <DialogHeader>
                          <DialogTitle>上传新头像</DialogTitle>
                          <DialogDescription>建议使用正方形图片</DialogDescription>
                        </DialogHeader>
                        <div class="py-4">
                          <FileUpload v-model:data="images" :max-files="1" />
                        </div>
                        <DialogFooter>
                          <DialogClose as-child>
                            <Button type="button" class="w-full bg-indigo-600 hover:bg-indigo-700" @click="uploadFiles()">
                              确认上传
                            </Button>
                          </DialogClose>
                        </DialogFooter>
                      </DialogContent>
                    </Dialog>
                    <span class="text-xs text-gray-400">点击头像进行修改</span>
                  </div>
                  
                  <!-- 修改昵称 -->
                  <div class="space-y-2">
                    <Label for="name" class="text-sm font-medium text-gray-700">昵称</Label>
                    <Input v-model="newName" id="name" class="h-10 border-gray-200 focus:border-indigo-500 focus:ring-indigo-500" />
                  </div>
                </div>
                <DialogFooter>
                  <DialogClose as-child>
                    <Button type="submit" @click="update()" :disabled="!newName || uploading" class="bg-indigo-600 hover:bg-indigo-700 w-full sm:w-auto">
                      保存更改
                    </Button>
                  </DialogClose>
                </DialogFooter>
              </DialogContent>
            </Dialog>
          </div>
        </div>
      </div>
    </div>

    <!-- 主内容区 -->
    <div class="flex-1 max-w-5xl w-full mx-auto px-4 sm:px-6 py-8 flex flex-col min-h-0">
      <div class="flex flex-col h-full bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
        
        <!-- Tab 切换栏 -->
        <div class="flex border-b border-gray-100">
           <button @click="changeType(1)" 
                   class="flex-1 sm:flex-none px-8 py-4 text-sm font-bold border-b-2 transition-colors flex items-center justify-center gap-2"
                   :class="currentType === 1 ? 'border-indigo-500 text-indigo-600 bg-indigo-50/50' : 'border-transparent text-gray-500 hover:text-gray-700 hover:bg-gray-50'">
              <span class="icon-[humbleicons--chat] text-lg"></span>
              <span>{{ userStore.user.id === user?.user.id ? "我" : "Ta" }}的帖子</span>
           </button>
           <button @click="changeType(2)" 
                   class="flex-1 sm:flex-none px-8 py-4 text-sm font-bold border-b-2 transition-colors flex items-center justify-center gap-2"
                   :class="currentType === 2 ? 'border-indigo-500 text-indigo-600 bg-indigo-50/50' : 'border-transparent text-gray-500 hover:text-gray-700 hover:bg-gray-50'">
              <span class="icon-[material-symbols--thumb-up-outline] text-lg"></span>
              <span>{{ userStore.user.id === user?.user.id ? "我" : "Ta" }}点赞的</span>
           </button>
        </div>

        <!-- 列表内容区域 -->
        <div class="flex-1 overflow-y-auto scrollbar-edge p-0">
            <!-- 列表 Header (仅在大屏显示) -->
            <div v-if="(currentType === 1 && user?.userPostList?.length) || (currentType === 2 && user?.likePostList?.length)" 
                 class="hidden sm:flex px-6 py-3 bg-gray-50/50 text-xs font-bold text-gray-400 uppercase tracking-wider border-b border-gray-100">
                <div class="flex-1">帖子标题</div>
                <div class="w-24 text-center">回复 / 点赞</div>
                <div class="w-32 text-right">最后更新</div>
            </div>

            <!-- 帖子列表 -->
            <div v-if="currentType === 1 && user?.userPostList && user.userPostList.length > 0" class="divide-y divide-gray-50">
               <div v-for="userPost in user?.userPostList" :key="userPost.id" 
                    class="group hover:bg-gray-50 transition-colors px-6 py-4 flex flex-col sm:flex-row sm:items-center gap-2 sm:gap-4">
                  <router-link :to="`/forum/${userPost.categoryId}/post/${userPost.id}`" 
                               class="flex-1 text-base font-medium text-gray-800 group-hover:text-indigo-600 transition-colors line-clamp-1">
                    {{ userPost.title }}
                  </router-link>
                  
                  <div class="flex items-center justify-between sm:justify-end gap-6 text-sm text-gray-400 w-full sm:w-auto mt-1 sm:mt-0">
                     <div class="flex items-center gap-4 w-24 justify-center">
                        <span class="flex items-center gap-1" title="回复数"><span class="icon-[material-symbols--comment-outline]"></span>{{ userPost.commentCount }}</span>
                        <span class="flex items-center gap-1" title="点赞数"><span class="icon-[material-symbols--thumb-up-outline]"></span>{{ userPost.likeCount }}</span>
                     </div>
                     <div class="w-32 text-right text-xs">{{ formatTime(userPost.updatedAt) }}</div>
                  </div>
               </div>
            </div>

            <!-- 点赞列表 -->
            <div v-else-if="currentType === 2 && user?.likePostList && user.likePostList.length > 0" class="divide-y divide-gray-50">
               <div v-for="likePost in user?.likePostList" :key="likePost.id" 
                    class="group hover:bg-gray-50 transition-colors px-6 py-4 flex flex-col sm:flex-row sm:items-center gap-2 sm:gap-4">
                  <router-link :to="`/forum/${likePost.categoryId}/post/${likePost.id}`" 
                               class="flex-1 text-base font-medium text-gray-800 group-hover:text-indigo-600 transition-colors line-clamp-1">
                    {{ likePost.title }}
                  </router-link>
                  
                  <div class="flex items-center justify-between sm:justify-end gap-6 text-sm text-gray-400 w-full sm:w-auto mt-1 sm:mt-0">
                     <div class="flex items-center gap-4 w-24 justify-center">
                        <span class="flex items-center gap-1" title="回复数"><span class="icon-[material-symbols--comment-outline]"></span>{{ likePost.commentCount }}</span>
                        <span class="flex items-center gap-1" title="点赞数"><span class="icon-[material-symbols--thumb-up-outline]"></span>{{ likePost.likeCount }}</span>
                     </div>
                     <div class="w-32 text-right text-xs">{{ formatTime(likePost.updatedAt) }}</div>
                  </div>
               </div>
            </div>

            <!-- 空状态 -->
            <div v-else class="h-full flex flex-col items-center justify-center text-gray-300 py-20">
               <div class="w-20 h-20 bg-gray-50 rounded-full flex items-center justify-center mb-4">
                  <span class="icon-[material-symbols--post-add] text-4xl text-gray-300" v-if="currentType === 1"></span>
                  <span class="icon-[material-symbols--favorite-outline] text-4xl text-gray-300" v-else></span>
               </div>
               <p class="font-medium text-gray-400">暂无相关记录</p>
            </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
// Script 部分保持不变，完全复用你原来的逻辑
  import axios from '@/utils/myAxios'
  import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
  import { Button } from "@/components/ui/button"
  import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, DialogClose } from "@/components/ui/dialog"
  import { Input } from "@/components/ui/input"
  import { Label } from "@/components/ui/label"
  import FileUpload from '@/components/common/Upload.vue';
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
    categoryId: number
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

  const getLike = async () => {
    try {
      const response = await axios.get(`/api/user/user/${route.params.id}/like`)
      user.value!.likePostList = response.data.data
      getLiked.value = true
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
  const getLiked = ref<boolean>(false)

  const changeType = (type: number) => {
    currentType.value = type
    if(type === 2 && !getLiked.value) {
      getLike()
    }
  }
</script>