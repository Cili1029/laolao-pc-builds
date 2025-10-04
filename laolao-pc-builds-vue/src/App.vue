<template>
  <Button 
    variant="outline" 
    @click="showToast"
  >
    Add to calendar
  </Button>

  <LoginOrRegister v-if="showAComponent" @close="showAComponent = false" />

  <Toaster position="top-right" />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import LoginOrRegister from './components/LoginOrRegister.vue'

// 悬浮通知
import { Toaster } from '@/components/ui/sonner'
import 'vue-sonner/style.css'


const showAComponent = ref(false)

const showToast = () => {
   showAComponent.value = true
}
</script>

<style scoped></style>