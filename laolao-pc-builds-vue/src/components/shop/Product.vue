<template>
  <div class="h-full flex bg-white">
    <div v-if="Number(route.params.productType) === 1" class="flex space-x-5">
      <div class="w-7/12 rounded-md p-4 flex flex-col gap-4 overflow-x-auto scrollbar-edge">
        <img :src="component?.image" class="w-full object-contain rounded-md" :alt="component?.name">
        <div class="border-t-2 my-3"></div>
        <p class="mb-2">信息</p>
        <p class="pb-20">{{ selectedVariant?.description }}</p>
      </div>

      <div class="w-5/12 flex flex-col rounded-md">
        <div class="flex flex-col m-3 flex-grow">
          <p class="text-xl font-bold">{{ component?.name }}</p>
          <p class="flex justify-between">
          <p>
            <span class="text-sm">券后¥</span>
            <span class="text-lg font-bold">{{ selectedVariant?.price }}</span>
          </p>
          <span>已售：{{ component?.sales }}</span>
          </p>
          <div class="border-t-2 my-3"></div>
          <p>{{ component?.commonDescription }}</p>
          <div class="mt-auto">
            <div class="border-t-2 my-3"></div>
            <p class="mb-2">可选类别</p>
            <div class="flex gap-2 pb-3">
              <span v-for="variant in component?.variants" :key="variant.id" @click="selectVariant(variant)"
                class="p-2 rounded-md shadow border-1 hover:border-orange-500 hover:text-orange-500 transition-all"
                :class="{ 'border-orange-500 text-orange-500': variant.id === selectedVariant?.id }">
                {{ variant.variantName }}
              </span>
            </div>
            <p class="mb-2">数量</p>
            <div class="flex items-center gap-3">
              <ButtonGroup>
                <Button variant="outline">
                  <MinusIcon />
                </Button>
                <Input placeholder="我" class="w-12" />
                <Button variant="outline">
                  <PlusIcon />
                </Button>
              </ButtonGroup>
              <span>库存：{{ selectedVariant?.stock }}</span>
            </div>
            <Button @click="addToCart(1, selectedVariant?.id)" variant="outline"
              class="w-full py-6 my-2 bg-orange-500 hover:bg-orange-600">
              <span class="font-bold text-white">加入购物车</span>
            </Button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="Number(route.params.productType) === 2" class="flex space-x-5">
      <div class="w-7/12 rounded-md p-4 flex flex-col gap-4 overflow-x-auto scrollbar-edge">
        <img :src="bundle?.image" class="w-full object-contain rounded-md" :alt="bundle?.name">
        <div class="border-t-2 my-3"></div>
        <p class="mb-2">捆绑组合详细</p>
        <div class="flex flex-col gap-2 pb-20">
          <router-link :to="`/buy/product/${1}/${variant.componentId}`" v-for="variant in bundle?.variants"
            :key="variant.id" class="bg-white rounded-lg shadow-sm border border-gray-200 p-4 flex items-center">
            <img :src="variant.image" class="w-15 h-15 object-cover rounded-md mr-4" />
            <div class="flex-1">
              <h3 class="font-medium text-gray-900">{{ variant.name }}</h3>
              {{ variant.variantName }}
            </div>
            <div class="ml-auto">
              原价:<span class="text-lg font-bold text-red-600">￥{{ variant.price
              }}</span>
            </div>
          </router-link>
        </div>
      </div>

      <div class="w-5/12 flex flex-col rounded-md overflow-x-auto scrollbar-edge">
        <div class="flex flex-col m-3 flex-grow">
          <p class="text-xl font-bold">{{ bundle?.name }}</p>
          <p class="flex justify-between">
          <p>
            <span class="text-sm">券后¥</span>
            <span class="text-lg font-bold">{{ bundle?.price }}</span>
          </p>
          <span>已售：{{ bundle?.sales }}</span>
          </p>
          <div class="border-t-2 my-3"></div>
          <p>{{ bundle?.description }}</p>
          <div class="mt-auto">
            <div class="border-t-2 my-3"></div>
            <p class="mb-2">数量</p>
            <div class="flex items-center gap-3">
              <ButtonGroup>
                <Button variant="outline">
                  <MinusIcon />
                </Button>
                <Input placeholder="我" class="w-12" />
                <Button variant="outline">
                  <PlusIcon />
                </Button>
              </ButtonGroup>
              <span>库存：{{ bundle?.stock }}</span>
            </div>
            <Button @click="addToCart(2, bundle?.id)" variant="outline"
              class="w-full py-6 my-2 bg-orange-500 hover:bg-orange-600">
              <span class="font-bold text-white">加入购物车</span>
            </Button>
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
  import { useRoute } from 'vue-router'
  const route = useRoute()
  import { Button } from "@/components/ui/button"
  import { MinusIcon, PlusIcon } from 'lucide-vue-next'
  import { Input } from "@/components/ui/input"
  import { ButtonGroup } from '@/components/ui/button-group'

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
    image: string
    commonDescription: string
    sales: number
    variants: Variant[]
  }

  interface Bundle {
    id: number
    name: string
    categoryId: number
    price: number
    image: string
    description: string
    sales: number
    stock: number
    variants: Variant[]
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
    if (productType === 1) {
      try {
        const response = await axios.get('/api/user/shop/products/component', {
          params: {
            id: id
          }
        })
        component.value = response.data.data
        selectedVariant.value = component.value!.variants[0] ?? null
      } catch (error) {
        console.log(error)
      }
    }
    else {
      try {
        const response = await axios.get('/api/user/shop/products/bundle', {
          params: {
            id: id
          }
        })
        bundle.value = response.data.data
      } catch (error) {
        console.log(error)
      }
    }
  }

  // 选择部件版本的方法
  const selectVariant = (edi: Variant) => {
    selectedVariant.value = edi
  }

  const addToCart = async (productType: number, id: number | undefined) => {
    try {
      await axios.post('/api/user/shop/cart/plus', {
        productType: productType,
        productId: id
      })
    } catch (error) {
      console.log(error)
    }
  }
</script>

<style scoped></style>