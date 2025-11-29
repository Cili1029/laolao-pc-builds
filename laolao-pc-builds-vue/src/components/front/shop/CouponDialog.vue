<template>
  <div>
    <!-- 更多优惠券 -->
    <Dialog v-model:open="localIsOpen" @update:open="handleOpenChange">
      <DialogContent class="sm:max-w-md p-0 overflow-hidden bg-gray-50 border-none shadow-2xl rounded-2xl">
        <DialogHeader class="px-6 py-4 bg-white border-b border-gray-100">
          <DialogTitle class="text-xl font-bold text-gray-800 flex items-center gap-2">
            <span class="icon-[charm--ticket] text-orange-500 text-2xl"></span>
            {{ props.type === 1 ? "领券中心" : props.type === 2 ? "我的优惠券" : "选择优惠券" }}
          </DialogTitle>
          <DialogDescription class="hidden"></DialogDescription>
        </DialogHeader>

        <!-- 统一的优惠券列表容器 -->
        <div class="h-[500px] overflow-y-auto scrollbar-edge p-4 space-y-4">

          <!-- 类型1: 优惠券中心 (领取) -->
          <template v-if="props.type === 1">
            <template v-if="shopCoupons && shopCoupons.length > 0">
              <div v-for="coupon in shopCoupons" :key="coupon.id"
                class="relative flex bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden group hover:shadow-md transition-shadow">
                <!-- 左侧金额区 -->
                <div
                  class="w-28 bg-gradient-to-br from-orange-500 to-red-500 text-white flex flex-col items-center justify-center p-3 relative">
                  <!-- 装饰圆点 -->
                  <div class="absolute -right-1.5 top-1/2 -translate-y-1/2 w-3 h-3 bg-white rounded-full"></div>
                  <div class="flex items-baseline">
                    <span class="text-sm font-bold">¥</span>
                    <span class="text-3xl font-extrabold tracking-tighter">{{ coupon.discountAmount }}</span>
                  </div>
                  <span class="text-xs bg-white/20 px-2 py-0.5 rounded mt-1">满{{ coupon.minimumAmount }}可用</span>
                </div>

                <!-- 右侧信息区 -->
                <div class="flex-1 p-3 flex flex-col justify-between relative">
                  <div>
                    <div class="flex justify-between items-start">
                      <span class="font-bold text-gray-800 text-sm line-clamp-1">{{ coupon.name }}</span>
                    </div>
                    <p class="text-xs text-gray-400 mt-1 line-clamp-2 leading-relaxed">{{ coupon.description }}</p>
                  </div>
                  <div class="flex justify-between items-end mt-2">
                    <span class="text-[10px] text-gray-400 bg-gray-50 px-1.5 py-0.5 rounded">
                      有效期至 {{ dayjs(coupon.validEndTime).format('MM.DD') }}
                    </span>

                    <button v-if="coupon.obtained === 0" @click="getCoupon(coupon)"
                      class="px-3 py-1 bg-orange-50 text-orange-600 text-xs font-bold rounded-full border border-orange-100 hover:bg-orange-500 hover:text-white transition-all active:scale-95">
                      立即领取
                    </button>
                    <button v-else disabled
                      class="px-3 py-1 bg-gray-100 text-gray-400 text-xs font-bold rounded-full cursor-not-allowed">
                      已领取
                    </button>
                  </div>
                </div>
              </div>
            </template>
            <!-- 空状态 -->
            <div v-else class="flex flex-col items-center justify-center h-full text-gray-300 gap-3">
              <div class="w-20 h-20 bg-gray-100 rounded-full flex items-center justify-center">
                <span class="icon-[charm--ticket] text-4xl text-gray-300"></span>
              </div>
              <p class="text-sm font-medium">暂时没有可领取的优惠券</p>
            </div>
          </template>

          <!-- 类型2: 我的优惠券 (查看) -->
          <template v-else-if="props.type === 2">
            <template v-if="userCoupons && userCoupons.length > 0">
              <div v-for="coupon in userCoupons" :key="coupon.id"
                class="relative flex bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden group"
                :class="{ 'grayscale opacity-70': coupon.status !== 0 }">

                <!-- 左侧金额区 -->
                <div class="w-28 flex flex-col items-center justify-center p-3 relative"
                  :class="coupon.status === 0 ? 'bg-gradient-to-br from-red-500 to-pink-500 text-white' : 'bg-gray-200 text-gray-500'">
                  <div class="absolute -right-1.5 top-1/2 -translate-y-1/2 w-3 h-3 bg-white rounded-full"></div>
                  <div class="flex items-baseline">
                    <span class="text-sm font-bold">¥</span>
                    <span class="text-3xl font-extrabold tracking-tighter">{{ coupon.discountAmount }}</span>
                  </div>
                  <span class="text-xs bg-black/10 px-2 py-0.5 rounded mt-1 text-white/90">满{{ coupon.minimumAmount
                    }}可用</span>
                </div>

                <!-- 右侧信息区 -->
                <div class="flex-1 p-3 flex flex-col justify-between">
                  <div>
                    <span class="font-bold text-gray-800 text-sm">{{ coupon.name }}</span>
                    <p class="text-xs text-gray-400 mt-1">{{ coupon.description }}</p>
                  </div>
                  <div class="flex justify-between items-end mt-2">
                    <span class="text-[10px] text-gray-400">
                      {{ dayjs(coupon.validEndTime).format('YYYY-MM-DD') }} 过期
                    </span>

                    <button v-if="coupon.status === 0" @click="goToUse()"
                      class="px-3 py-1 bg-red-50 text-red-600 text-xs font-bold rounded-full border border-red-100 hover:bg-red-500 hover:text-white transition-all">
                      去使用
                    </button>
                    <span v-else class="text-xs font-bold px-2 py-1 rounded bg-gray-100 text-gray-400">
                      {{ coupon.status === 1 ? "已使用" : "已过期" }}
                    </span>
                  </div>
                </div>
              </div>
            </template>
            <div v-else class="flex flex-col items-center justify-center h-full text-gray-300 gap-3">
              <div class="w-20 h-20 bg-gray-100 rounded-full flex items-center justify-center">
                <span class="icon-[mdi--ticket-percent-outline] text-4xl text-gray-300"></span>
              </div>
              <p class="text-sm font-medium">您还没有优惠券</p>
            </div>
          </template>

          <!-- 类型3: 订单使用优惠券 (选择) -->
          <template v-else-if="props.type === 3">
            <template v-if="orderCoupons && orderCoupons.length > 0">
              <div v-for="coupon in orderCoupons" :key="coupon.id"
                class="relative flex bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden cursor-pointer group hover:border-orange-300 transition-all"
                :class="userCouponId === coupon.id ? 'ring-2 ring-orange-500 ring-offset-1' : ''"
                @click="userCouponId === coupon.id ? cancelCoupon(coupon.id) : useCoupon(coupon.id, coupon.discountAmount)">

                <!-- 左侧金额区 -->
                <div
                  class="w-28 bg-gradient-to-br from-orange-400 to-orange-600 text-white flex flex-col items-center justify-center p-3 relative">
                  <div class="absolute -right-1.5 top-1/2 -translate-y-1/2 w-3 h-3 bg-white rounded-full"></div>
                  <div class="flex items-baseline">
                    <span class="text-sm font-bold">¥</span>
                    <span class="text-3xl font-extrabold tracking-tighter">{{ coupon.discountAmount }}</span>
                  </div>
                  <span class="text-xs bg-white/20 px-2 py-0.5 rounded mt-1">满{{ coupon.minimumAmount }}可用</span>
                </div>

                <!-- 右侧信息区 -->
                <div class="flex-1 p-3 flex flex-col justify-between">
                  <div>
                    <span class="font-bold text-gray-800 text-sm">{{ coupon.name }}</span>
                    <p class="text-xs text-gray-400 mt-1 line-clamp-1">{{ coupon.description }}</p>
                  </div>
                  <div class="flex justify-between items-end mt-2">
                    <span class="text-[10px] text-gray-400">
                      {{ dayjs(coupon.validEndTime).format('MM-DD') }} 过期
                    </span>
                    <!-- 选择框图标 -->
                    <div class="w-5 h-5 rounded-full border-2 flex items-center justify-center transition-colors"
                      :class="userCouponId === coupon.id ? 'border-orange-500 bg-orange-500' : 'border-gray-300 group-hover:border-orange-300'">
                      <span v-if="userCouponId === coupon.id" class="icon-[charm--tick] text-white text-xs"></span>
                    </div>
                  </div>
                </div>
              </div>
            </template>
            <div v-else class="flex flex-col items-center justify-center h-full text-gray-300 gap-3">
              <div class="w-20 h-20 bg-gray-100 rounded-full flex items-center justify-center">
                <span class="icon-[mdi--ticket-confirmation-outline] text-4xl text-gray-300"></span>
              </div>
              <p class="text-sm font-medium">暂无可用优惠券</p>
            </div>
          </template>

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
    router.push(`/buy/${0}`)
    localIsOpen.value = false
    emits('update:isOpen', false)
  }
</script>