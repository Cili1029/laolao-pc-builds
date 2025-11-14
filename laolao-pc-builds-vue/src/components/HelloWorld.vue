<template>
  <div>
    <!-- 对话框组件，通过 open 状态控制显示 -->
    <Dialog :open="isDialogOpen" @open-change="isDialogOpen = $event">
      <DialogContent>
        <DialogHeader>
          <DialogTitle>自动弹出提示</DialogTitle>
          <DialogDescription>
            页面加载已超过5秒，这是自动弹出的对话框
          </DialogDescription>
        </DialogHeader>
        <div class="flex justify-end mt-4">
          <Button @click="isDialogOpen = false">关闭</Button>
        </div>
      </DialogContent>
    </Dialog>
  </div>

</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
// 导入 shadcn/ui 的对话框组件
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogDescription } from '@/components/ui/dialog'
import { Button } from '@/components/ui/button'

// 控制对话框显示的状态变量
const isDialogOpen = ref(false)

// 页面加载完成后启动定时器
onMounted(() => {
  // 5秒后将对话框状态改为显示
  const timer = setTimeout(() => {
    isDialogOpen.value = true
  }, 5000) // 5000毫秒 = 5秒

  // 组件卸载时清除定时器（避免内存泄漏）
  return () => clearTimeout(timer)
})
</script>