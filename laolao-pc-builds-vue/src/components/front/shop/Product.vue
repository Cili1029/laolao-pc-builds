<template>
  <div class="h-full flex flex-col bg-slate-50 py-4 overflow-hidden">
    <!-- 主卡片容器：用于居中和统一背景 -->
    <div
      class="flex-1 bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden max-w-screen-2xl mx-auto w-full">

      <!-- Type 1: 单一组件商品 -->
      <div v-if="Number(route.params.productType) === 1" class="flex flex-col lg:flex-row h-full">
        <!-- 左侧：图片与详细信息 (可滚动) -->
        <div
          class="w-full lg:w-7/12 h-full overflow-y-auto scrollbar-edge p-4 border-b lg:border-b-0 lg:border-r border-gray-100">
          <div class="w-full flex justify-center mb-8">
            <Carousel class="w-full max-w-xl 2xl:max-w-2xl" :plugins="[Autoplay({ delay: 5000, })]">
              <CarouselContent>
                <CarouselItem v-for="image in component?.images" :key="image">
                  <div class="p-1">
                    <Card>
                      <CardContent class="flex aspect-square items-center justify-center">
                        <img :src="image" class="w-full rounded-md" />
                      </CardContent>
                    </Card>
                  </div>
                </CarouselItem>
              </CarouselContent>
              <CarouselPrevious />
              <CarouselNext />
            </Carousel>
          </div>

          <div class="flex items-center gap-4 py-4">
            <div class="h-px bg-gray-100 flex-1"></div>
            <span class="text-gray-400 text-sm font-medium uppercase tracking-wider">详细说明</span>
            <div class="h-px bg-gray-100 flex-1"></div>
          </div>

          <p class="text-gray-600 leading-relaxed pb-20">{{ selectedVariant?.description || '暂无详细描述' }}</p>
        </div>

        <!-- 右侧：购买操作区 -->
        <div class="w-full lg:w-5/12 h-full flex flex-col p-4 bg-white relative z-10">
          <!-- 标题区 -->
          <div class="mb-4">
            <h1 class="text-2xl lg:text-3xl font-extrabold text-gray-900 tracking-tight mb-3">{{ component?.name }}</h1>
            <div class="flex items-end justify-between">
              <div class="flex items-baseline gap-2">
                <span class="text-sm text-orange-500 font-medium bg-orange-50 px-2 py-0.5 rounded">券后价</span>
                <span class="text-3xl font-bold text-orange-600">¥{{ selectedVariant?.price }}</span>
              </div>
              <span class="text-sm text-gray-500 bg-gray-100 px-2 py-1 rounded-md">已售 {{ component?.sales }}</span>
            </div>
          </div>

          <div class="border-t-3"></div>

          <!-- 通用描述 (可滚动) -->
          <div class="flex-grow overflow-y-auto scrollbar-edge">
            <p class="text-gray-600 text-sm leading-7">{{ component?.commonDescription }}</p>
          </div>

          <!-- 底部操作栏 (固定在底部) -->
          <div class="flex flex-col mt-auto gap-2">
            <div class="border-t-3"></div>

            <!-- 规格选择 -->
            <div>
              <p class="text-sm font-bold text-gray-800 mb-3">选择规格</p>
              <div class="flex flex-wrap gap-2">
                <span v-for="variant in component?.variants" :key="variant.id" @click="selectVariant(variant)"
                  class="cursor-pointer px-4 py-2 rounded-lg text-sm border transition-all duration-200 select-none"
                  :class="variant.id === selectedVariant?.id
                    ? 'border-orange-500 bg-orange-50 text-orange-700 font-bold shadow-sm ring-1 ring-orange-500 ring-opacity-20'
                    : 'border-gray-200 bg-white text-gray-600 hover:border-orange-300 hover:text-orange-500 hover:bg-gray-50'">
                  {{ variant.variantName }}
                </span>
              </div>
            </div>

            <!-- 数量与库存 -->
            <div class="flex flex-col gap-3">
              <p class="text-sm font-bold text-gray-800">购买数量</p>
              <div class="flex items-center justify-between">
                <div class="flex items-center">
                  <Button variant="outline" size="icon"
                    class="h-10 w-10 rounded-l-lg rounded-r-none border-r-0 hover:bg-gray-50"
                    @click="quantity = Math.max(1, quantity - 1)">
                    <MinusIcon class="w-4 h-4" />
                  </Button>
                  <p
                    class="h-10 w-14 text-center rounded-none border border-gray-300 focus-visible:ring-0 z-10 flex items-center justify-center">
                    {{ quantity }}
                  </p>
                  <Button variant="outline" size="icon"
                    class="h-10 w-10 rounded-r-lg rounded-l-none border-l-0 hover:bg-gray-50"
                    @click="quantity = Math.min(selectedVariant!.stock, quantity + 1)">
                    <PlusIcon class="w-4 h-4" />
                  </Button>
                </div>
                <span class="text-sm text-gray-500">库存: <span class="font-medium text-gray-900">{{
                  selectedVariant?.stock }}</span></span>
              </div>
            </div>

            <!-- 按钮组 -->
            <div class="flex gap-4">
              <Button @click="addToCart(1, selectedVariant!.id)" variant="outline"
                class="flex-1 h-12 rounded-xl border-orange-200 text-orange-600 hover:bg-orange-50 hover:text-orange-700 hover:border-orange-300 font-bold text-base transition-all">
                加入购物车
              </Button>
              <Button @click="order(1, selectedVariant!.id)"
                class="flex-1 h-12 rounded-xl bg-gradient-to-r from-orange-500 to-red-500 hover:from-orange-600 hover:to-red-600 text-white font-bold text-base shadow-md hover:shadow-lg transition-all transform active:scale-95">
                立即购买
              </Button>
            </div>
          </div>
        </div>
      </div>

      <!-- Type 2: 捆绑套餐商品 -->
      <div v-if="Number(route.params.productType) === 2" class="flex flex-col lg:flex-row h-full">
        <!-- 左侧：图片与套餐内容 -->
        <div
          class="w-full lg:w-7/12 h-full overflow-y-auto scrollbar-edge p-6 lg:p-10 border-b lg:border-b-0 lg:border-r border-gray-100">
          <div class="w-full flex justify-center mb-8">
            <Carousel class="w-full max-w-xl 2xl:max-w-2xl" :plugins="[Autoplay({ delay: 5000, })]">
              <CarouselContent>
                <CarouselItem v-for="image in bundle?.images" :key="image">
                  <div class="p-1">
                    <Card>
                      <CardContent class="flex aspect-square items-center justify-center">
                        <img :src="image" class="w-full rounded-md" />
                      </CardContent>
                    </Card>
                  </div>
                </CarouselItem>
              </CarouselContent>
              <CarouselPrevious />
              <CarouselNext />
            </Carousel>
          </div>

          <div class="flex items-center gap-4 py-4 mb-4">
            <div class="h-px bg-gray-100 flex-1"></div>
            <span class="text-gray-800 text-base font-bold flex items-center gap-2">
              📦 套餐包含以下商品
            </span>
            <div class="h-px bg-gray-100 flex-1"></div>
          </div>

          <div class="grid gap-3 pb-20">
            <router-link :to="`/buy/product/${1}/${variant.componentId}`" v-for="variant in bundle?.variants"
              :key="variant.id"
              class="group bg-white rounded-xl border-2 border-gray-200 p-4 flex items-center transition-all hover:border-orange-400">
              <div class="h-16 w-16 flex-shrink-0 bg-gray-100 rounded-lg overflow-hidden border border-gray-100">
                <img :src="variant.image" class="w-full h-full" />
              </div>
              <div class="ml-4 flex-1">
                <h3 class="font-bold text-gray-800 group-hover:text-orange-600 transition-colors line-clamp-1">{{
                  variant.name }}</h3>
                <p class="text-sm text-gray-500 mt-1">{{ variant.variantName }}</p>
              </div>
              <div class="text-right ml-4">
                <p class="text-xs text-gray-400 mb-0.5">原价</p>
                <span class="text-base font-bold text-gray-400 line-through">￥{{ variant.price }}</span>
              </div>
            </router-link>
          </div>
        </div>

        <!-- 右侧：套餐购买操作区 -->
        <div class="w-full lg:w-5/12 h-full flex flex-col p-4 bg-white relative z-10">
          <!-- 标题区 -->
          <div class="mb-4">
            <h1 class="text-2xl lg:text-3xl font-extrabold text-gray-900 tracking-tight mb-3">{{ bundle?.name }}</h1>
            <div class="flex items-end justify-between">
              <div class="flex items-baseline gap-2">
                <span class="text-sm text-orange-500 font-medium bg-orange-50 px-2 py-0.5 rounded">{{bundle?.discountPercent}}折</span>
                <span class="text-3xl font-bold text-orange-600">¥{{ bundle?.price }}</span>
              </div>
              <span class="text-sm text-gray-500 bg-gray-100 px-2 py-1 rounded-md">已售 {{ bundle?.sales }}</span>
            </div>
          </div>

          <div class="border-t-3"></div>

          <!-- 通用描述 (可滚动) -->
          <div class="flex-grow overflow-y-auto scrollbar-edge">
            <p class="text-gray-600 text-sm leading-7">{{ bundle?.description }}</p>
          </div>

          <!-- 底部操作栏 (固定在底部) -->
          <div class="flex flex-col mt-auto gap-2">
            <div class="border-t-3"></div>

            <!-- 数量与库存 -->
            <div class="flex flex-col gap-3">
              <p class="text-sm font-bold text-gray-800">购买数量</p>
              <div class="flex items-center justify-between">
                <div class="flex items-center">
                  <Button variant="outline" size="icon"
                    class="h-10 w-10 rounded-l-lg rounded-r-none border-r-0 hover:bg-gray-50"
                    @click="quantity = Math.max(1, quantity - 1)">
                    <MinusIcon class="w-4 h-4" />
                  </Button>
                  <p
                    class="h-10 w-14 text-center rounded-none border border-gray-300 focus-visible:ring-0 z-10 flex items-center justify-center">
                    {{ quantity }}
                  </p>
                  <Button variant="outline" size="icon"
                    class="h-10 w-10 rounded-r-lg rounded-l-none border-l-0 hover:bg-gray-50"
                    @click="quantity = Math.min(bundle!.stock, quantity + 1)">
                    <PlusIcon class="w-4 h-4" />
                  </Button>
                </div>
                <span class="text-sm text-gray-500">库存: <span class="font-medium text-gray-900">{{
                  selectedVariant?.stock }}</span></span>
              </div>
            </div>

            <!-- 按钮组 -->
            <div class="flex gap-4">
              <Button @click="addToCart(2, bundle!.id)" variant="outline"
                class="flex-1 h-12 rounded-xl border-orange-200 text-orange-600 hover:bg-orange-50 hover:text-orange-700 hover:border-orange-300 font-bold text-base transition-all">
                加入购物车
              </Button>
              <Button @click="order(2, bundle!.id)"
                class="flex-1 h-12 rounded-xl bg-gradient-to-r from-orange-500 to-red-500 hover:from-orange-600 hover:to-red-600 text-white font-bold text-base shadow-md hover:shadow-lg transition-all transform active:scale-95">
                立即购买
              </Button>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup lang="ts">
  import axios from "@/utils/myAxios"
  import { onMounted, ref, watch } from "vue"
  import 'vue-sonner/style.css'
  import { useRoute, useRouter } from 'vue-router'
  const route = useRoute()
  const router = useRouter()
  import { Button } from "@/components/ui/button"
  import { MinusIcon, PlusIcon } from 'lucide-vue-next'
  import { Card, CardContent } from '@/components/ui/card'
  import { Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious, } from '@/components/ui/carousel'
  import Autoplay from 'embla-carousel-autoplay'

  onMounted(() => {
    getProductDetails(Number(route.params.productType), Number(route.params.id))
  })

  watch(
    [() => route.params.productType, () => route.params.id],
    () => {
      if (Number(route.params.productType) === 1)
        getProductDetails(Number(route.params.productType), Number(route.params.id))
    }
  )

  interface Component {
    id: number
    name: string
    categoryId: number
    images: string[]
    commonDescription: string
    sales: number
    variants: Variant[]
  }

  interface Bundle {
    id: number
    name: string
    categoryId: number
    price: number
    images: string[]
    description: string
    sales: number
    stock: number
    variants: Variant[]
    discountPercent: number
  }

  interface Variant {
    id: number
    componentId: number
    variantName: string
    price: number
    description: string
    stock: number
    // 主机用
    quantity: number
    name: string
    image: string
  }

  const component = ref<Component | null>(null)
  const bundle = ref<Bundle | null>(null)

  // 用户选择的版本  进去默认选择第一个版本
  const selectedVariant = ref<Variant | null>(null)

  const getProductDetails = async (productType: number, id: number) => {
    try {
      if (productType === 1) {
        const response = await axios.get('/api/user/shop/products/component', {
          params: {
            id: id
          }
        })
        component.value = response.data.data
        selectedVariant.value = component.value!.variants[0] ?? null
      } else {
        const response = await axios.get('/api/user/shop/products/bundle', {
          params: {
            id: id
          }
        })
        bundle.value = response.data.data

        // 计算折扣
        let totalPrice = 0
        bundle.value?.variants.forEach(variant => {
          totalPrice = totalPrice + variant.price * variant.quantity
        })
        bundle.value!.discountPercent = Math.floor(bundle.value!.price / totalPrice* 100)
      }
    } catch (error) {
      console.log(error)
    }
  }

  // 选择部件版本
  const selectVariant = (edi: Variant) => {
    selectedVariant.value = edi
  }

  const quantity = ref(1)

  watch(
    [() => quantity.value],
    () => {
      if (Number(route.params.productType) === 1) {
        if (quantity.value > selectedVariant.value!.stock) {
          quantity.value = 1
        }
      }
      else {
        if (quantity.value > bundle.value!.stock) {
          quantity.value = 1
        }
      }
    }
  )

  const addToCart = async (productType: number, id: number) => {
    try {
      await axios.post('/api/user/shop/cart/plus', {
        productType: productType,
        productId: id,
        quantity: quantity.value
      })
      quantity.value = 1
    } catch (error) {
      console.log(error)
    }
  }

  const order = async (productType: number, id: number) => {
    try {
      const response = await axios.post("/api/user/shop/order/directly", {
        productType: productType,
        productId: id,
        quantity: quantity.value
      })

      const number = response.data.data
      // 跳转到订单页面并传递id
      if (response.data.code === 1) {
        router.push(`/order/${number}`)
      }
    } catch (error) {
      console.log(error)
    }
  }

</script>

<style scoped></style>