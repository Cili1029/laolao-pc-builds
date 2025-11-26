<template>
    <div class="flex h-full flex-col bg-slate-50">
        <!-- 顶部搜索区域 -->
        <div class="flex flex-col md:flex-row items-center justify-between px-6 py-6 gap-4 bg-white shadow-sm z-20">
            <div class="flex items-center gap-2">
                <div class="h-8 w-1 bg-orange-500 rounded-full"></div>
                <p class="text-gray-800 text-xl font-extrabold tracking-tight">{{ currentCategory.id === 0 ? "所有宝贝" :
                    "热卖分类" }}</p>
            </div>

            <div class="flex w-full md:w-1/2 max-w-2xl gap-2 items-center relative">
                <Input type="text" placeholder="搜索您心仪的商品..." v-model="searchContent"
                    class="h-11 pl-4 pr-4 rounded-full border-gray-200 bg-gray-50 focus-visible:ring-orange-500 focus-visible:border-orange-500 transition-all shadow-sm" />
                <Button type="submit" @click="search(currentCategory?.id)"
                    class="h-11 px-8 rounded-full bg-gradient-to-r from-orange-500 to-red-500 font-bold text-white hover:from-orange-600 hover:to-red-600 shadow-md transition-all active:scale-95">
                    搜索
                </Button>
            </div>
            <!-- 占位，保持中间对齐 -->
            <div class="hidden md:block w-[100px]"></div>
        </div>

        <!-- 分类选择区域 -->
        <div class="p-4 rounded-b-xl bg-white border-t border-gray-100">
            <div class="flex flex-wrap gap-3 overflow-x-auto pb-2">
                <span v-for="category in categories" :key="category.id" @click="() => {
                    if (category.id !== currentCategory.id) {
                        ShowComponent(category);
                    }
                }" class="cursor-pointer select-none group flex items-center gap-2 px-3 py-3 rounded-xl border-2 transition-all duration-300 ease-in-out whitespace-nowrap"
                    :class="category.id === currentCategory.id
                        ? 'border-orange-200 text-orange-600'
                        : 'text-gray-600 hover:border-orange-200 hover:text-orange-500'">
                    <img :src="category.image" class="h-6 w-6 rounded object-cover" />
                    <span class="font-medium text-sm">{{ category.name }}</span>
                </span>
            </div>
        </div>

        <!-- 商品列表区域 -->
        <div class="flex-1 overflow-hidden">
            <div class="h-full overflow-y-auto scrollbar-edge scrollbar-track-transparent pb-20">
                <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-3 p-3">
                    <router-link :to="`/buy/product/${hot.productType}/${hot.id}`" v-show="currentCategory.id === 0"
                        class="group bg-white rounded-2xl overflow-hidden border border-gray-100 hover:border-gray-100 transition-all duration-300 relative z-10 hover:z-50 hover:scale-105 hover:shadow-lg"
                        v-for="hot in hot" :key="hot.id">

                        <!-- 图片容器 -->
                        <div class="relative w-full aspect-square bg-gray-50 overflow-hidden">
                            <img :src="hot.image" class="w-full h-full" />
                            <!-- 遮罩层，增加质感 -->
                            <div
                                class="absolute inset-0 bg-black/0 group-hover:bg-black/5 transition-colors duration-300">
                            </div>
                        </div>

                        <!-- 内容区域 -->
                        <div class="p-4 flex flex-col flex-1">
                            <h4
                                class="font-medium text-gray-800 mb-2 line-clamp-2 h-[calc(2*1.6em)] text-sm leading-relaxed group-hover:text-orange-600 transition-colors">
                                {{ hot.name }}
                            </h4>

                            <div class="mt-auto pt-2 flex items-end justify-between w-full">
                                <div>
                                    <p class="flex items-baseline gap-0.5 text-orange-600 font-bold">
                                        <span class="text-xs">￥</span>
                                        <span class="text-xl">{{ hot.price }}</span>
                                        <span v-if="hot.productType === 1"
                                            class="text-xs text-gray-400 font-normal ml-1">起</span>
                                    </p>
                                </div>
                                <div class="bg-orange-50 px-2 py-0.5 rounded text-xs font-medium text-orange-600/80">
                                    {{ hot.sales > 0 ? hot.sales + "+人付款" : "新品上架" }}
                                </div>
                            </div>
                        </div>
                    </router-link>
                    <router-link :to="`/buy/product/${product.productType}/${product.id}`"
                        v-show="currentCategory.id !== 0"
                        class="group bg-white rounded-2xl overflow-hidden border border-gray-100 hover:border-gray-100 transition-all duration-300 relative z-10 hover:z-50 hover:scale-105 hover:shadow-lg"
                        v-for="product in products" :key="product.id">

                        <!-- 图片容器 -->
                        <div class="relative w-full aspect-square bg-gray-50 overflow-hidden">
                            <img :src="product.image" class="w-full h-full" />
                            <!-- 遮罩层，增加质感 -->
                            <div
                                class="absolute inset-0 bg-black/0 group-hover:bg-black/5 transition-colors duration-300">
                            </div>
                        </div>

                        <!-- 内容区域 -->
                        <div class="p-4 flex flex-col flex-1">
                            <h4
                                class="font-medium text-gray-800 mb-2 line-clamp-2 h-[calc(2*1.6em)] text-sm leading-relaxed  group-hover:text-orange-600 transition-colors">
                                {{ product.name }}
                            </h4>

                            <div class="mt-auto pt-2 flex items-end justify-between w-full">
                                <div>
                                    <p class="flex items-baseline gap-0.5 text-orange-600 font-bold">
                                        <span class="text-xs">￥</span>
                                        <span class="text-xl">{{ product.price }}</span>
                                        <span v-if="product.productType === 1"
                                            class="text-xs text-gray-400 font-normal ml-1">起</span>
                                    </p>
                                </div>
                                <div class="bg-orange-50 px-2 py-0.5 rounded text-xs font-medium text-orange-600/80">
                                    {{ product.sales > 0 ? product.sales + "+人付款" : "新品上架" }}
                                </div>
                            </div>
                        </div>
                    </router-link>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
    import axios from "@/utils/myAxios"
    import { onMounted, ref } from "vue"
    import { Button } from "@/components/ui/button"
    import { Input } from "@/components/ui/input"
    import 'vue-sonner/style.css'
    import { toast } from "vue-sonner"
    import { useRoute, useRouter } from 'vue-router'
    const route = useRoute()
    const router = useRouter()

    interface Product {
        id: number
        productType: number
        name: string
        price: number
        image: string
        sales: number
    }

    interface category {
        id: number
        productType: number
        name: string
        image: string
    }

    const products = ref<Product[]>([])
    const categories = ref<category[]>([{
        id: 0,
        productType: 0,
        name: "所有宝贝",
        image: "https://laolao123.oss-cn-hangzhou.aliyuncs.com/%E7%BB%84%E4%BB%B6%E5%9B%BE/%E9%80%9A%E7%94%A8/1.png"
    }])
    const hot = ref<Product[]>([])

    const currentCategory = ref<category>({
        id: 0,
        productType: 0,
        name: '',
        image: ''
    })

    onMounted(() => {
        ShowCategory()
    })

    const ShowCategory = async () => {
        try {
            const response = await axios.get('/api/user/shop/category/list')
            categories.value.push(...response.data.data)
            const category = categories.value.find(c => c.id === Number(route.params.id))
            if (category) {
                currentCategory.value = category
                ShowComponent(category)
            }
        } catch (error) {
            console.log(error)
        }
    }

    const ShowComponent = async (category: category) => {
        try {
            currentCategory.value = category
            router.replace(`/buy/${category.id}`)
            if (category.productType === 0) {
                const response = await axios.get("/api/user/shop/products/hot", {
                    params: { count: 0 } // 稍微多取几个，或者保持5个
                })
                hot.value = response.data.data
            } else if (category.productType === 1) {
                const response = await axios.get('/api/user/shop/products/components', {
                    params: {
                        categoryId: category.id
                    }
                })
                products.value = response.data.data
            } else {
                const response = await axios.get('/api/user/shop/products/bundles', {
                    params: {
                        categoryId: category.id
                    }
                })
                products.value = response.data.data
            }
            searchContent.value = ''
        } catch (error) {
            console.log(error)
        }

    }

    const searchContent = ref('')

    const search = async (categoryId: number | undefined) => {
        try {
            const response = await axios.get('/api/user/shop/products/search', {
                params: {
                    searchContent: searchContent.value,
                    categoryId: categoryId
                }
            })

            if (response.data.data.length === 0) {
                toast("嗨！", {
                    description: "什么也没有",
                    action: {
                        label: '我知道了',
                    },
                })
            }
            if (categoryId === 0) {
                hot.value = response.data.data
            } else {
                products.value = response.data.data
            }
        } catch (error) {
            console.log(error)
        }
    }
</script>
