<template>
  <div class="h-full overflow-y-auto scrollbar-edge">
    <div class="w-full p-3">
      <h3 class="text-xl font-bold text-gray-800">我的优惠券</h3>
      <div v-if="userCoupons && userCoupons.length > 0">
        <div v-for="coupon in userCoupons" :key="coupon.id"
          class="flex items-center w-2/3 bg-gradient-to-b from-red-500 to-red-600 text-white rounded-lg overflow-hidden justify-between border-5 border-red-600">
          <div class="flex">
            <div class="bg-white text-red-600 p-4 flex flex-col items-center">
              <span class="text-2xl font-bold">¥{{ coupon.discountAmount }}</span>
              <span class="text-sm">实付满{{ coupon.minimumAmount }}</span>
              <span class="text-sm">可用</span>
            </div>
            <div class="flex-col p-4">
              <span class="text-lg font-bold">{{ coupon.name }}</span>
              <p class="text-sm mt-1">{{ coupon.description }}</p>
              <p class="text-sm mt-1">{{ dayjs(coupon.validEndTime).format('YYYY-MM-DD')}}过期</p>
            </div>
          </div>
          <div class="p-4">
            <button class="bg-white text-red-600 px-4 py-2 rounded font-medium">使用</button>
          </div>
        </div>
      </div>
      <div v-else>
        什么也没有
      </div>
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
            <p class="text-sm mt-1">{{ coupon.description }}</p>
            <p class="text-sm mt-1">有效期至{{ dayjs(coupon.validEndTime).format('YYYY-MM-DD HH:mm:ss') }}</p>
          </div>
        </div>
        <div class="p-4">
          <button @click="getCoupon(coupon)" class="bg-white text-red-600 px-4 py-2 rounded font-medium">领取</button>
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
    showUserCoupon()
    showShopCoupon()
  })

  interface ShopCoupon {
    id: number
    name: string
    description: string
    minimumAmount: number
    discountAmount: number
    validStartTime: string
    validEndTime: string
  }

  interface UserCoupon {
    id: number
    couponId: number
    name: string
    description: string
    minimumAmount: number
    discountAmount: number
    validStartTime: string
    validEndTime: string
    status: number
    claimedAt: string
    usedAt: string
  }

  const shopCoupons = ref<ShopCoupon[]>([])
  const userCoupons = ref<UserCoupon[]>([])

  const showShopCoupon = async () => {
    try {
      const response = await axios.get("/api/user/shop/coupon/shop")
      shopCoupons.value = response.data.data
    }
    catch (error) {
      console.log(error)
    }
  }

  const showUserCoupon = async () => {
    try {
      const response = await axios.get("/api/user/shop/coupon/user")
      userCoupons.value = response.data.data
    }
    catch (error) {
      console.log(error)
    }
  }

  const getCoupon = async (shopCoupon: ShopCoupon) => {
    try {
      const response = await axios.post("/api/user/shop/coupon/get", {
        couponId: shopCoupon.id
      })
      userCoupons.value.push(response.data.data)
    }
    catch (error) {
      console.log(error)
    }
  }
</script>