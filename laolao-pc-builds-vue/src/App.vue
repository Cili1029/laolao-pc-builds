<template>
  <RouterView />

  <Toaster position="bottom-left" :toastOptions="{
    style: { background: 'white', border: '1px solid #e5e7eb', color: '#1f2937' },
    class: 'shadow-lg rounded-xl',
  }" />
</template>

<script setup lang="ts">
  import { Toaster } from '@/components/ui/sonner'
  import { onMounted, ref } from 'vue'
  import axios from '@/utils/myAxios'
  import { useUserStore } from '@/stores/UserStore'
  const userStore = useUserStore()

  // 组件挂载时检查登录状态
  onMounted(() => {
    getProfile()
  })

  interface User {
    id: number
    avatar: string
    username: string
    name: string
    admin: number
  }

  const user = ref<User>({
    id: 0,
    avatar: '',
    username: '',
    name: '',
    admin: 0
  })

  const getProfile = async () => {
    try {
      const response = await axios.get('/api/common/user/profile')

      if (response.data.code === 1) {
        user.value = response.data.data
        userStore.setUser(user.value)
      }
    } catch (error) {
      console.log(error)
    }
  }
</script>