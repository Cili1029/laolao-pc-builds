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
        <!-- 优惠券中心 -->
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
                <button v-if="coupon.obtained === 0" @click="getCoupon(coupon)"
                  class="bg-white text-red-600 p-2 rounded font-medium">领取</button>
                <button v-else class="bg-white text-red-600 p-2 rounded font-medium">已领取</button>
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

        <!-- 我的优惠券 -->
        <div v-else-if="props.type === 2" class="h-120 overflow-y-auto scrollbar-edge">
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
                <button v-if="coupon.status === 0" @click="goToUse()"
                  class="bg-white text-red-600 p-2 rounded font-medium">去使用</button>
                <button v-else class="bg-white text-red-600 p-2 rounded font-medium">{{ coupon.status === 1 ?
                  "已使用"
                  : "已过期" }}</button>
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

        <!-- 使用优惠券 -->
        <div v-else-if="props.type === 3" class="h-120 overflow-y-auto scrollbar-edge">
          <div v-if="orderCoupons && orderCoupons.length > 0">
            <div v-for="coupon in orderCoupons" :key="coupon.id"
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
                <span v-if="userCouponId === coupon.id" @click="cancelCoupon(coupon.id)"
                  class="icon-[charm--circle-tick] text-3xl"></span>
                <span v-else @click="useCoupon(coupon.id, coupon.discountAmount)"
                  class="icon-[charm--circle] text-3xl"></span>
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
      if (newVal) {
        localIsOpen.value = newVal
        if (props.type === 1) {
          showShopCoupon()
        } else if (props.type === 2) {
          showUserCoupon()
        } else if (props.type === 3) {
          showAvailableCoupon()
        }
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
    obtained: number
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
  const orderCoupons = ref<UserCoupon[]>([])

  // 优惠券中心
  const showShopCoupon = async () => {
    try {
      const response = await axios.get("/api/user/shop/coupon/shop")
      shopCoupons.value = response.data.data
    }
    catch (error) {
      console.log(error)
    }
  }

  // 用户的优惠券
  const showUserCoupon = async () => {
    try {
      const response = await axios.get("/api/user/shop/coupon/user")
      userCoupons.value = response.data.data
    } catch (error) {
      console.log(error)
    }
  }

  // 订单中可以使用的优惠券
  const showAvailableCoupon = async () => {
    try {
      const response = await axios.get("/api/user/shop/coupon/order", {
        params: {
          // 传递被这个订单使用的优惠券供订单取消
          userCouponId: props.userCouponId
        }
      })
      // status为0的优惠券
      orderCoupons.value = response.data.data
    } catch (error) {
      console.log(error)
    }
  }

  // 领取优惠券
  const getCoupon = async (shopCoupon: ShopCoupon) => {
    try {
      await axios.post("/api/user/shop/coupon/get", {
        couponId: shopCoupon.id
      })
      shopCoupon.obtained = 1
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
    localIsOpen.value = false
    emits('update:isOpen', false)
  }
</script>