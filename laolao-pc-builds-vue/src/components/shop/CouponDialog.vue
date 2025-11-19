<template>
  <div>
    <!-- 更多优惠券 -->
    <Dialog v-model:open="localIsOpen" @update:open="handleOpenChange">
      <DialogContent class="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>{{ props.type === 1 ? "更多优惠券" : "我的优惠券" }}</DialogTitle>
          <DialogDescription>
          </DialogDescription>
        </DialogHeader>
        <div v-if="props.type === 1" class="h-120 overflow-y-auto scrollbar-edge">
          <div v-if="shopCoupons && shopCoupons.length > 0">
            <div v-for="coupon in shopCoupons" :key="coupon.id"
              class="flex items-center mb-3 bg-gradient-to-b from-red-500 to-red-600 text-white rounded-lg overflow-hidden justify-between border-5 border-red-600">
              <div class="flex">
                <div class="bg-white text-red-600 p-4 flex flex-col items-center">
                  <span class="text-2xl font-bold">¥{{ coupon.discountAmount }}</span>
                  <span class="text-sm">实付满{{ coupon.minimumAmount }}</span>
                  <span class="text-sm">可用</span>
                </div>
                <div class="flex-col p-4">
                  <span class="text-lg font-bold">{{ coupon.name }}</span>
                  <p class="text-sm mt-1">{{ coupon.description }}</p>
                  <p class="text-sm mt-1">有效期至{{ dayjs(coupon.validEndTime).format('YYYY-MM-DD') }}</p>
                </div>
              </div>
              <div class="p-4">
                <button @click="getCoupon(coupon)"
                  class="bg-white text-red-600 px-4 py-2 rounded font-medium">领取</button>
              </div>
            </div>
          </div>
          <div v-else class="flex justify-center items-center h-full">
            <div class="flex flex-col items-center">
              <span class="icon-[noto--enraged-face] text-9xl"></span>
              <div class="font-bold">什么也没有！</div>
            </div>
          </div>
        </div>
        <div v-else-if="props.type === 2 || props.type === 3" class="h-120 overflow-y-auto scrollbar-edge">
          <div v-if="userCoupons && userCoupons.length > 0">
            <div v-for="coupon in userCoupons" :key="coupon.id"
              class="flex items-center mb-3 bg-gradient-to-b from-red-500 to-red-600 text-white rounded-lg overflow-hidden justify-between border-5 border-red-600">
              <div class="flex">
                <div class="bg-white text-red-600 p-4 flex flex-col items-center">
                  <span class="text-2xl font-bold">¥{{ coupon.discountAmount }}</span>
                  <span class="text-sm">实付满{{ coupon.minimumAmount }}</span>
                  <span class="text-sm">可用</span>
                </div>
                <div class="flex-col p-4">
                  <span class="text-lg font-bold">{{ coupon.name }}</span>
                  <p class="text-sm mt-1">{{ coupon.description }}</p>
                  <p class="text-sm mt-1">{{ dayjs(coupon.validEndTime).format('YYYY-MM-DD') }}过期</p>
                </div>
              </div>
              <div class="p-4">
                <button v-if="coupon.status !== 1"
                  class="bg-white text-red-600 px-4 py-2 rounded font-medium disabled">{{ coupon.status === 2 ? "已使用" :
                    "已过期" }}</button>
                <div v-else>
                  <button v-if="props.type === 2" @click="goToUse()"
                    class="bg-white text-red-600 px-4 py-2 rounded font-medium">去使用</button>
                  <div v-if="props.type === 3">
                    <button v-if="userCouponId === coupon.id" @click="cancelCoupon(coupon.id)"
                      class="bg-white text-red-600 px-4 py-2 rounded font-medium">取消使用</button>
                    <button v-else @click="useCoupon(coupon.id, coupon.discountAmount)"
                      class="bg-white text-red-600 px-4 py-2 rounded font-medium">使用</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-else class="flex justify-center items-center h-full">
            <div class="flex flex-col items-center">
              <span class="icon-[noto--enraged-face] text-9xl"></span>
              <div class="font-bold">什么也没有！</div>
            </div>
          </div>
        </div>
      </DialogContent>
    </Dialog>
  </div>
</template>

<script setup lang="ts">
  import axios from "@/utils/myAxios"
  import { ref, watch } from "vue"
  import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, } from '@/components/ui/dialog'
  import dayjs from 'dayjs'
  import 'dayjs/locale/zh-cn'
  import { useRouter } from 'vue-router'
  const router = useRouter()

  const props = defineProps({
    type: Number,
    isOpen: Boolean,
    number: String,
    userCouponId: Number
  })

  const emits = defineEmits<{
    (e: 'update:isOpen', value: boolean): void
    (e: 'userCouponId', value: number): void
    (e: 'discountAmount', discountAmount: number): void;
  }>()

  // 定义本地变量，初始值为 props.isOpen
  const localIsOpen = ref(props.isOpen)

  // 监听 props.isOpen 变化，同步到本地变量（父组件主动打开弹窗时生效）
  watch(
    () => props.isOpen,
    (newVal) => {
      localIsOpen.value = newVal
      if (props.type === 1) {
        showShopCoupon()
      } else {
        showUserCoupon()
      }
    }
  )

  // 当本地变量变化时，通过事件通知父组件更新
  const handleOpenChange = (value: boolean) => {
    emits('update:isOpen', value)
  }

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
    } catch (error) {
      console.log(error)
    }
  }

  const getCoupon = async (shopCoupon: ShopCoupon) => {
    try {
      await axios.post("/api/user/shop/coupon/get", {
        couponId: shopCoupon.id
      })
      // 更新用户优惠券
      showUserCoupon()
    } catch (error) {
      console.log(error)
    }
  }

  const handleUseCoupon = (discountAmount: number, couponId: number) => {
    emits('discountAmount', discountAmount);
    // 2. 可选：使用后关闭优惠券弹窗（根据需求决定是否添加）
    localIsOpen.value = false
    emits('update:isOpen', false) // 同步父组件的 isOpen 状态
    emits('userCouponId', couponId)
  };

  const useCoupon = async (couponId: number, discountAmount: number) => {
    try {
      await axios.patch("/api/user/shop/order/coupon", {
        id: couponId,
        number: props.number
      })
      handleUseCoupon(discountAmount, couponId)
    } catch (error) {
      console.log(error)
    }
  }

  const cancelCoupon = async (couponId: number) => {
    try {
      await axios.patch("/api/user/shop/order/coupon/cancel", {
        id: couponId,
        number: props.number
      })
      handleUseCoupon(0, 0)
    } catch (error) {
      console.log(error)
    }
  }

  const goToUse = () => {
    router.push(`/buy/${-1}`)
    emits('update:isOpen', false);
  }
</script>