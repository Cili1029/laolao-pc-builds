<template>
    <div class="min-h-screen bg-gray-50/80 font-sans pb-12">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 pt-8 lg:pt-12 space-y-10">

            <div class="grid grid-cols-1 md:grid-cols-2 gap-6 lg:gap-10">

                <!-- 配件商城 -->
                <router-link :to="`/buy/${-1}`"
                    class="group relative overflow-hidden rounded-3xl bg-white border border-gray-100 shadow-lg hover:shadow-2xl transition-all duration-500 hover:-translate-y-1 cursor-pointer">
                    <div
                        class="absolute top-0 right-0 w-64 h-64 bg-gradient-to-br from-blue-400/20 to-cyan-300/20 rounded-full blur-3xl -mr-16 -mt-16 transition-transform duration-700 group-hover:scale-125">
                    </div>

                    <div class="relative p-8 flex flex-col h-full">
                        <div class="flex items-start justify-between mb-6">
                            <div
                                class="p-4 bg-blue-500 rounded-2xl shadow-lg shadow-blue-500/30 group-hover:scale-110 transition-transform duration-300">
                                <span
                                    class="icon-[material-symbols--shopping-cart-outline] text-4xl text-white block"></span>
                            </div>
                            <p
                                class="px-4 py-2 bg-blue-50 text-blue-600 rounded-full text-sm font-semibold hover:bg-blue-100 transition-colors">
                                立即选购 &rarr;
                            </p>
                        </div>

                        <h3
                            class="text-2xl font-extrabold text-gray-800 mb-2 group-hover:text-blue-600 transition-colors">
                            装机配件商城</h3>
                        <p class="text-gray-500 leading-relaxed flex-1">
                            严选 CPU、显卡、主板等核心硬件。正品行货，极速发货，一站式补齐你的性能拼图。
                        </p>
                    </div>
                </router-link>

                <!-- 极客论坛 -->
                <router-link :to="`/forum/${-1}`"
                    class="group relative overflow-hidden rounded-3xl bg-white border border-gray-100 shadow-lg hover:shadow-2xl transition-all duration-500 hover:-translate-y-1 cursor-pointer">
                    <div
                        class="absolute top-0 right-0 w-64 h-64 bg-gradient-to-br from-emerald-400/20 to-teal-300/20 rounded-full blur-3xl -mr-16 -mt-16 transition-transform duration-700 group-hover:scale-125">
                    </div>

                    <div class="relative p-8 flex flex-col h-full">
                        <div class="flex items-start justify-between mb-6">
                            <div
                                class="p-4 bg-emerald-500 rounded-2xl shadow-lg shadow-emerald-500/30 group-hover:scale-110 transition-transform duration-300">
                                <span
                                    class="icon-[material-symbols--forum-outline-rounded] text-4xl text-white block"></span>
                            </div>
                            <p
                                class="px-4 py-2 bg-emerald-50 text-emerald-600 rounded-full text-sm font-semibold hover:bg-emerald-100 transition-colors">
                                加入讨论 &rarr;
                            </p>
                        </div>

                        <h3
                            class="text-2xl font-extrabold text-gray-800 mb-2 group-hover:text-emerald-600 transition-colors">
                            极客交流社区</h3>
                        <p class="text-gray-500 leading-relaxed flex-1">
                            配置单求助、硬核评测、装机晒图。与万千 DIY 玩家探讨技术，拒绝“低能高价”。
                        </p>
                    </div>
                </router-link>
            </div>

            <!-- 热门区域 -->
            <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">

                <!-- 热门商品 -->
                <div class="lg:col-span-2 bg-white rounded-3xl shadow-sm border border-gray-100 p-6">
                    <div class="flex justify-between items-center mb-6">
                        <div class="flex items-center gap-3">
                            <div class="w-1 h-8 bg-red-500 rounded-full"></div>
                            <h2 class="text-xl font-bold text-gray-800">热销商品</h2>
                        </div>
                        <router-link :to="`/buy/${1}`"
                            class="text-gray-400 hover:text-red-500 text-sm flex items-center gap-1 transition-colors">
                            全部商品 <span class="text-lg">›</span>
                        </router-link>
                    </div>

                    <div class="flex flex-col gap-3">
                        <router-link :to="`/buy/product/${product.productType}/${product.id}`"
                            v-for="(product, index) in products" :key="product.id"
                            class="group flex items-center p-3 rounded-2xl hover:bg-gray-50 border border-transparent hover:border-gray-100 transition-all duration-300 relative z-10 hover:z-50 hover:scale-105 hover:shadow-lg">
                            <div
                                class="relative w-20 h-20 flex-shrink-0 rounded-xl overflow-hidden bg-gray-100 border border-gray-100">
                                <img :src="product.image" class="w-full h-full object-cover" />
                                <div v-if="index < 3"
                                    class="absolute top-0 left-0 w-6 h-6 flex items-center justify-center bg-red-500 text-white text-xs font-bold rounded-br-lg z-10">
                                    {{ index + 1 }}
                                </div>
                            </div>

                            <div class="flex-1 min-w-0 ml-4 flex flex-col justify-center gap-1">
                                <h4
                                    class="text-base sm:text-lg font-bold text-gray-800 truncate group-hover:text-blue-600 transition-colors">
                                    {{ product.name }}
                                </h4>
                                <div class="flex items-center">
                                    <span
                                        class="inline-flex items-center gap-1 px-2 py-0.5 rounded-md bg-orange-50 text-orange-600 text-xs font-medium border border-orange-100">
                                        <span
                                            class="icon-[material-symbols--local-fire-department-rounded] text-xs"></span>
                                        热销 {{ product.sales }}
                                    </span>
                                </div>
                            </div>

                            <div class="ml-4 flex items-center gap-4">
                                <p class="text-base font-bold text-red-600 font-mono">
                                    ¥<span class="text-xl font-bold text-red-600 font-mono">
                                        {{ product.price }}
                                    </span>
                                </p>

                                <button @click.stop.prevent="addToCart(product.productType, product.id)"
                                    class="w-10 h-10 flex items-center justify-center rounded-full bg-gray-900 text-white shadow-md group-hover:scale-110 transition-all duration-300 hover:bg-blue-600">
                                    <span class="icon-[material-symbols--shopping-cart-outline] text-xl"></span>
                                </button>
                            </div>
                        </router-link>
                    </div>
                </div>

                <!-- 热门帖子 -->
                <div
                    class="lg:col-span-1 bg-white rounded-3xl shadow-sm border border-gray-100 p-6 sm: h-fit sticky top-6">
                    <div class="flex justify-between items-center mb-6">
                        <div class="flex items-center gap-3">
                            <div class="w-1 h-8 bg-indigo-500 rounded-full"></div>
                            <h2 class="text-xl font-bold text-gray-800">热议话题</h2>
                        </div>
                        <router-link :to="`/forum/${5}`"
                            class="text-gray-400 hover:text-indigo-500 text-sm flex items-center gap-1 transition-colors">
                            更多<span class="text-lg">›</span>
                        </router-link>
                    </div>

                    <div class="flex flex-col gap-1">
                        <router-link :to="`/forum/${simple.categoryId}/post/${simple.id}`"
                            v-for="(simple, index) in postSimple" :key="simple.id"
                            class="group relative py-4 px-2 rounded-xl hover:bg-indigo-50/50 transition-colors">
                            <div class="flex gap-4">
                                <div
                                    class="text-2xl font-black text-gray-200 italic select-none group-hover:text-indigo-200 transition-colors">
                                    {{ index + 1 }}
                                </div>

                                <div class="flex-1 min-w-0">
                                    <h4
                                        class="truncate text-base font-medium text-gray-700 mb-2 line-clamp-2 group-hover:text-indigo-600 transition-colors leading-snug">
                                        {{ simple.title }}
                                    </h4>

                                    <div class="flex items-center gap-4 text-xs text-gray-400">
                                        <span class="flex items-center gap-1">
                                            <span class="icon-[material-symbols--thumb-up-outline] text-sm"></span>
                                            {{ simple.likeCount }}
                                        </span>
                                        <span class="flex items-center gap-1">
                                            <span class="icon-[material-symbols--comment-outline] text-sm"></span>
                                            {{ simple.commentCount }}
                                        </span>
                                        <span class="ml-auto text-gray-300">{{ formatDate(simple.updatedAt) }}</span>
                                    </div>
                                </div>
                            </div>
                            <!-- 分割线 -->
                            <div class="absolute bottom-0 left-12 right-0 h-px bg-gray-50 group-hover:bg-transparent"
                                v-if="index !== postSimple.length - 1"></div>
                        </router-link>
                    </div>
                </div>

            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { onMounted, ref } from 'vue';
    import axios from '@/utils/myAxios'

    // 工具函数：简单的日期格式化
    const formatDate = (dateStr: string) => {
        const date = new Date(dateStr);
        return `${date.getMonth() + 1}-${date.getDate()}`;
    }

    onMounted(() => {
        getPostHot()
        getProductHot()
    })

    interface PostSimple {
        id: number
        categoryId: number
        title: string
        likeCount: number
        commentCount: number
        updatedAt: string
    }
    const postSimple = ref<PostSimple[]>([])

    const getPostHot = async () => {
        try {
            const response = await axios.get("/api/user/forum/post/hot", {
                params: { count: 5 }
            })
            postSimple.value = response.data.data
        } catch (e) {
            console.error("Failed to load hot posts", e)
        }
    }

    interface Product {
        id: number
        productType: number
        categoryId: number
        name: string
        price: number
        image: string
        sales: number
    }

    const products = ref<Product[]>([])

    const getProductHot = async () => {
        try {
            const response = await axios.get("/api/user/shop/products/hot", {
                params: { count: 5 } // 稍微多取几个，或者保持5个
            })
            products.value = response.data.data
        } catch (e) {
            console.error("Failed to load hot products", e)
        }
    }

    const addToCart = async (productType: number, id: number) => {
        try {
            await axios.post('/api/user/shop/cart/plus', {
                productType: productType,
                productId: id,
                quantity: 1
            })
        } catch (error) {
            console.log(error)
        }
    }
</script>