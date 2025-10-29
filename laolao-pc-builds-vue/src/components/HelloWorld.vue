<template>
    <div>
        <input v-model="inputText" @input="checkInput" />
        <div>{{ errorMessage }}</div>
    </div>

</template>

<script setup lang="ts">
import { ref } from 'vue'

const inputText = ref('')
const errorMessage = ref('')

const checkInput = () => {
  // 检查是否有非法字符（非数字和字母）
  if (/[^a-zA-Z0-9]/.test(inputText.value)) {
    errorMessage.value = '只能输入数字和字母！'
  } else {
    errorMessage.value = ''
  }
}
</script>

<style scoped></style>