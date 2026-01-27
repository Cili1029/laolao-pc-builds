<template>
  <div>
    <!-- 更多优惠券 -->
    <Dialog v-model:open="localIsOpen" @update:open="handleOpenChange">
      <DialogContent class="sm:max-w-md p-0 overflow-hidden bg-gray-50 border-none shadow-2xl rounded-2xl">
        <DialogHeader class="px-6 py-4 bg-white border-b border-gray-100">
          <DialogTitle class="text-lg font-bold text-gray-800 flex items-center gap-2">
            <Mail class="w-5 h-5" />
            <span>
              我的消息
            </span>
          </DialogTitle>
          <DialogDescription class="hidden"></DialogDescription>
        </DialogHeader>

        <div class="h-[500px] overflow-y-auto scrollbar-edge px-4 py-2">
          <div v-if="notifications && notifications.length > 0">
            <div v-for="notification in notifications" :key="notification.id"
              class="flex mb-3 bg-white rounded-xl shadow-sm hover:shadow-md transition-shadow border border-gray-50">
              <div class="flex w-full p-4 justify-between gap-4">
                <div class="flex-1">
                  <p class="font-bold text-gray-800 text-sm mb-1">{{ notification.title }}</p>
                  <p class="text-gray-500 text-sm leading-relaxed">{{ notification.content }}</p>
                </div>

                <div class="flex flex-col items-end justify-between gap-2 shrink-0">
                  <span class="text-xs text-gray-400">
                    {{ formatTime(notification.createdAt) }}
                  </span>
                  <Button @click="goTo(notification)" size="sm" class="h-8 px-3 text-xs">
                    去查看
                  </Button>
                </div>
              </div>
            </div>
          </div>

          <!-- 空状态 -->
          <div v-else class="flex flex-col items-center justify-center h-full text-gray-300 gap-4">
            <div class="w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center">
              <MailX class="w-8 h-8 text-gray-300" />
            </div>
            <p class="text-sm font-medium">暂时没有消息</p>
          </div>
        </div>
      </DialogContent>
    </Dialog>
  </div>
</template>

<script setup lang="ts">
  import { ref, watch } from "vue"
  import { useRouter } from 'vue-router'
  import axios from "@/utils/myAxios"
  import dayjs from 'dayjs'
  import relativeTime from 'dayjs/plugin/relativeTime'
  import 'dayjs/locale/zh-cn'
  import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, } from '@/components/ui/dialog'
  import { Button } from "@/components/ui/button"
  import { Mail, MailX, } from "lucide-vue-next"

  dayjs.extend(relativeTime)
  dayjs.locale('zh-cn')

  const router = useRouter()

  interface Notification {
    id: number
    type: number
    postId: number
    categoryId: number
    title: string
    content: string
    createdAt: string
  }

  const props = defineProps({
    isOpen: Boolean
  })

  const emits = defineEmits<{
    (e: 'update:isOpen', value: boolean): void
  }>()

  const localIsOpen = ref(props.isOpen) // 弹窗本地状态
  const notifications = ref<Notification[]>([]) // 通知列表数据

  const formatTime = (timeStr: string | undefined) => {
    if (!timeStr) return '无效日期'
    return dayjs(timeStr).fromNow()
  }

  const getList = async () => {
    try {
      const response = await axios.get("/api/user/notification")
      notifications.value = response.data.data
    } catch (error) {
      console.error('获取通知列表失败:', error)
    }
  }

  const goTo = (notification: Notification) => {
    if (notification.type === 0) {
      router.push("/my-orders")
    } else {
      router.push(`/forum/${notification.categoryId}/post/${notification.postId}`)
    }
    localIsOpen.value = false
    emits('update:isOpen', false)
  }

  const handleOpenChange = (value: boolean) => {
    emits('update:isOpen', value)
  }

  watch(
    () => props.isOpen,
    (newVal) => {
      if (newVal) {
        localIsOpen.value = newVal
        getList();
      }
    },
    { immediate: false }
  )
</script>