<template>
  <div>
    <CouponDialog :type="1" v-model:isOpen="showCouponDialog"></CouponDialog> 
    <p @click="a()">{{ showCouponDialog }}</p>
  </div>
</template>

<script setup lang="ts">
  import { ref } from 'vue';
  import CouponDialog from '../shop/CouponDialog.vue';
  const showCouponDialog = ref(false); // 控制弹窗显示/隐藏

  const a = () => {
    showCouponDialog.value = true
  }
</script>

<style scoped></style>