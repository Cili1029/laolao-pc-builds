<template>
  <!-- 子组件根容器：占满父容器 -->
  <div class="h-full w-full overflow-hidden relative">
    <div class="absolute inset-0 bottom-16 overflow-y-auto p-6">
      <h2 class="text-2xl font-bold mb-4">顶部滚动区域</h2>
      <p class="mb-4">内容过多时将在该区域内滚动，底部区域固定</p>
      
      <!-- 模拟大量内容 -->
      <div class="space-y-3">
        <div v-for="item in 50" :key="item" class="p-4 border rounded">
          内容项 {{ item }} - 纯子组件实现，不依赖父布局
        </div>
      </div>
    </div>

    <!-- 底部固定区域 -->
    <div class="absolute bottom-0 left-0 right-0 h-16 border-t flex items-center justify-center px-6">
      底部固定区域
    </div>
  </div>
</template>

<script setup>
// 按需添加组件逻辑
</script>