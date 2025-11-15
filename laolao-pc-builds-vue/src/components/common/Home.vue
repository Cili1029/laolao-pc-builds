<template>
    <div class="min-h-full flex flex-col gap-6 py-6 sm:py-8 lg:py-12">
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 sm:gap-8 lg:gap-12 max-w-6xl mx-auto px-4 sm:px-6">
            <!-- 配件卡片 -->
            <div
                class="group bg-white rounded-xl sm:rounded-2xl shadow-lg sm:shadow-xl p-6 sm:p-8 border border-gray-100 transform hover:-translate-y-2 sm:hover:-translate-y-3 transition-all duration-500 hover:shadow-xl sm:hover:shadow-2xl relative overflow-hidden">
                <!-- 装饰性背景元素 -->
                <div
                    class="absolute -top-6 -right-6 sm:-top-10 sm:-right-10 w-20 h-20 sm:w-32 sm:h-32 bg-blue-50 rounded-full opacity-60 group-hover:scale-110 transition-transform duration-500">
                </div>

                <div
                    class="w-16 h-16 sm:w-20 sm:h-20 bg-blue-500 rounded-xl sm:rounded-2xl flex items-center justify-center mb-4 sm:mb-6 shadow-md relative z-10">
                    <span class="icon-[material-symbols--shopping-cart-outline] text-3xl sm:text-4xl text-white"></span>
                </div>
                <h3 class="text-2xl sm:text-3xl font-bold mb-3 sm:mb-4 relative z-10">就缺这个了！</h3>
                <p class="text-gray-600 mb-4 sm:mb-6 leading-relaxed text-sm sm:text-base relative z-10">
                    精选CPU、显卡、主板等优质配件，正品保障，价格透明，一站式满足您的装机需求
                </p>
                <router-link :to="`/buy/${-1}`"
                    class="w-full bg-blue-500 hover:bg-blue-600 text-white font-semibold py-3 sm:py-4 rounded-lg sm:rounded-xl transition-all duration-300 block text-center shadow-md hover:shadow-lg transform hover:scale-[1.02] text-sm sm:text-base">
                    选购配件
                </router-link>
            </div>

            <!-- 论坛卡片 -->
            <div
                class="group bg-white rounded-xl sm:rounded-2xl shadow-lg sm:shadow-xl p-6 sm:p-8 border border-gray-100 transform hover:-translate-y-2 sm:hover:-translate-y-3 transition-all duration-500 hover:shadow-xl sm:hover:shadow-2xl relative overflow-hidden">
                <!-- 装饰性背景元素 -->
                <div
                    class="absolute -top-6 -right-6 sm:-top-10 sm:-right-10 w-20 h-20 sm:w-32 sm:h-32 bg-green-50 rounded-full opacity-60 group-hover:scale-110 transition-transform duration-500">
                </div>

                <div
                    class="w-16 h-16 sm:w-20 sm:h-20 bg-green-500 rounded-xl sm:rounded-2xl flex items-center justify-center mb-4 sm:mb-6 shadow-md relative z-10">
                    <span class="icon-[material-symbols--forum-outline-rounded] text-3xl sm:text-4xl text-white"></span>
                </div>
                <h3 class="text-2xl sm:text-3xl font-bold mb-3 sm:mb-4 relative z-10">带的动XXX不？</h3>
                <p class="text-gray-600 mb-4 sm:mb-6 leading-relaxed text-sm sm:text-base relative z-10">
                    与其他DIY玩家交流心得，分享装机经验，解决技术难题，找到最佳配置方案
                </p>
                <router-link :to="`/forum/${-1}`"
                    class="w-full bg-green-500 hover:bg-green-600 text-white font-semibold py-3 sm:py-4 rounded-lg sm:rounded-xl transition-all duration-300 block text-center shadow-md hover:shadow-lg transform hover:scale-[1.02] text-sm sm:text-base">
                    进入论坛
                </router-link>
            </div>
        </div>
        <!-- 热门商品和热门帖子 -->
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 sm:gap-8 lg:gap-12 max-w-6xl mx-auto px-4 sm:px-6">
            <!-- 热门商品 -->
            <div
                class="group bg-white rounded-xl sm:rounded-2xl shadow-lg sm:shadow-xl p-6 sm:p-8 border border-gray-100 transform hover:-translate-y-2 sm:hover:-translate-y-3 transition-all duration-500 hover:shadow-xl sm:hover:shadow-2xl relative overflow-hidden">
                <div class="flex justify-between">
                    <div class="flex items-center">
                        <span class="icon-[noto--face-savouring-delicious-food] text-3xl"></span>
                        <span class="px-2 items-center">热门商品</span>
                    </div>
                    <router-link :to="`/buy/${1}`" class="hover:text-sky-400 flex items-center">
                        更多商品👉
                    </router-link>
                </div>
                <div v-for="product in products" :key="product.id" class="flex items-center">
                    <img :src="product.image" class="w-16 h-16 rounded-lg" />
                    <span>{{ product.name }}</span>
                    <router-link :to="`/buy/${product.categoryId}`" @click="setProduct(product)"
                        class="flex-shrink-0 ml-auto">
                        <span class="icon-[material-symbols--shopping-cart-outline] text-4xl hover:bg-red-500 "></span>
                    </router-link>
                    <div class="border-t-2"></div>
                </div>
            </div>

            <!-- 热门帖子 -->
            <div
                class="group bg-white rounded-xl sm:rounded-2xl shadow-lg sm:shadow-xl p-6 sm:p-8 border border-gray-100 transform hover:-translate-y-2 sm:hover:-translate-y-3 transition-all duration-500 hover:shadow-xl sm:hover:shadow-2xl relative overflow-hidden">
                <div class="flex justify-between">
                    <div class="flex items-center">
                        <span class="icon-[noto--nerd-face] text-3xl"></span>
                        <span class="px-2 items-center">热门帖子</span>
                    </div>
                    <router-link :to="`/forum/${5}`" class="hover:text-sky-400 flex items-center">
                        更多热门👉
                    </router-link>
                </div>
                <div v-for="simple in postSimple" :key="simple.id">
                    <div class="flex mx-2 my-4 justify-between items-center">
                        <router-link :to="`/forum/${simple.categoryId}/post/${simple.id}`"
                            class="text-xl cursor-pointer">
                            {{ simple.title }}
                        </router-link>
                    </div>
                    <div class="border-t-2"></div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { onMounted } from 'vue';
    import axios from '@/utils/myAxios'
    import { ref } from 'vue'
    import { useProductStore } from '@/stores/ProductStore'
    const productStore = useProductStore()

    onMounted(() => {
        getPostHot()
        getProductHot()
    })

    // 帖子简单信息
    interface PostSimple {
        id: number
        categoryId: number
        title: string
        likeCount: number
        commentCount: number
        updatedAt: string
    }
    const postSimple = ref<PostSimple[]>([])

    // 获取热度帖子
    const getPostHot = async () => {
        const response = await axios.get("/api/user/forum/post/hot", {
            params: {
                count: 5
            }
        })
        postSimple.value = response.data.data
    }


    interface Product {
        id: number
        productType: number
        categoryId: number
        name: string
        price: number
        image: string
        commonDescription: string
        description: string
        sales: number
    }
    const products = ref<Product[]>([])

    // 获取热度商品
    const getProductHot = async () => {
        const response = await axios.get("/api/user/shop/products/hot", {
            params: {
                count: 5
            }
        })
        products.value = response.data.data
    }

    const setProduct = (product: Product) => {
        productStore.setProduct(product.id, product.productType)
    }

</script>

<style scoped></style>