<template>
  <div class="max-w-4xl mx-auto p-6">
    <h1 class="text-2xl font-bold mb-6">文件上传示例</h1>
    
    <!-- 基本用法 -->
    <div class="mb-8">
      <h2 class="text-lg font-semibold mb-3">基本文件上传</h2>
      <FileUpload 
        v-model:data="files1" 
        :max-files="5"
      />
    </div>
    
    <!-- 文件列表显示 -->
    <div v-if="files1.length > 0" class="mt-8">
      <h2 class="text-xl font-bold mb-4">已上传的文件</h2>
      <div class="bg-white shadow rounded-lg overflow-hidden">
        <table class="min-w-full divide-y divide-gray-200">
          <thead class="bg-gray-50">
            <tr>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">文件名</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">大小</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">类型</th>
            </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
            <tr v-for="(file, index) in files1" :key="index">
              <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{{ file.name }}</td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ formatFileSize(file.size) }}</td>
              <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{{ file.type || '未知' }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import FileUpload from '@/components/Upload.vue';

const files1 = ref<File[]>([]);
const formatFileSize = (bytes: number): string => {
  if (bytes === 0) return '0 Bytes';
  
  const k = 1024;
  const sizes = ['Bytes', 'KB', 'MB', 'GB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  
  return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
};
</script>