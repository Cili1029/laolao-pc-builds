<template>
  <div class="h-full overflow-y-auto scrollbar-edge">
    <div class="w-full p-3">
      <h3 class="text-xl font-bold text-gray-800">我的优惠券</h3>
    </div>
    <div class="w-full p-3">
      <h3 class="text-xl font-bold text-gray-800">更多优惠券</h3>
      <div v-for="coupon in shopCoupons" :key="coupon.id"
        class="flex items-center w-2/3 bg-gradient-to-b from-red-500 to-red-600 text-white rounded-lg overflow-hidden justify-between border-5 border-red-600">
        <div class="flex">
          <div class="bg-white text-red-600 p-4 flex flex-col items-center">
            <span class="text-2xl font-bold">¥{{ coupon.discountAmount }}</span>
            <span class="text-sm">实付满{{ coupon.minimumAmount }}</span>
            <span class="text-sm">可用</span>
          </div>
          <div class="flex-col p-4">
            <span class="text-lg font-bold">{{ coupon.name }}</span>
            <!-- <span class="text-xs bg-white/20 px-2 py-1 rounded">快失效</span> -->
            <p class="text-sm mt-1">{{ coupon.description }}</p>
            <p class="text-sm mt-1">有效期至{{ dayjs(coupon.validEndTime).format('YYYY-MM-DD HH:mm:ss') }}</p>
          </div>
        </div>
        <div class="p-4">
          <button class="bg-white text-red-600 px-4 py-2 rounded font-medium">领取</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import axios from "@/utils/myAxios"
  import { onMounted, ref } from "vue"
  import dayjs from 'dayjs'
  import 'dayjs/locale/zh-cn'

  onMounted(() => {
    getUserCoupon()
    getShopCoupon()
  })

  interface coupon {
    id: number
    name: string
    description: string
    minimumAmount: number
    discountAmount: number
    validStartTime: string
    validEndTime: string
  }

  const shopCoupons = ref<coupon[]>([])
  const userCoupons = ref<coupon[]>([])

  const getShopCoupon = async () => {
    try {
      const response = await axios.get("/api/user/shop/coupon")
      shopCoupons.value = response.data.data
    }
    catch (error) {
      console.log(error)
    }
  }

  const getUserCoupon = async () => {
    try {
      const response = await axios.get("/api/user/user/coupon")
      userCoupons.value = response.data.data
    }
    catch (error) {
      console.log(error)
    }
  }
</script>