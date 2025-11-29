<template>
  <RouterView />
  
  <Toaster position="bottom-left" :toastOptions="{
      style: { background: 'white', border: '1px solid #e5e7eb', color: '#1f2937' },
      class: 'shadow-lg rounded-xl',
    }" />
</template>

<script setup lang="ts">
import { Toaster } from '@/components/ui/sonner'
</script>