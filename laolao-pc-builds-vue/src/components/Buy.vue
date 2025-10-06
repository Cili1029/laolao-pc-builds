<template>
    <div class="flex">
        <div class="w-1/5 bg-white rounded-xl shadow-sm p-6 mr-4">
            <div class="flex justify-between items-center mb-6">
                <h3 class="text-xl font-bold text-gray-800 flex items-center">
                    <i class="fas fa-fire text-red-500 mr-2"></i> 分类
                </h3>
            </div>
            <div class="space-y-4">
                <div class="flex items-center p-3 rounded-lg transition-colors" :class="cat.id === CategoryIndex ? 'bg-gray-100' : 'hover:bg-gray-50'" v-for="cat in Category" :key="cat.id" @click="show(cat.id)">
                    <span class="icon-[streamline-cyber--smiley-sigh] text-3xl"></span>
                    <div class="ml-4 flex-1">
                        <h4 class="font-medium">{{ cat.name }}</h4>
                    </div>
                </div>
            </div>
        </div>

        <div class="w-4/5 bg-white rounded-xl shadow-sm p-6">
            <div class="flex justify-between items-center mb-6">
                <h3 class="text-xl font-bold text-gray-800 flex items-center">
                    <i class="fas fa-fire text-red-500 mr-2"></i> 热门商品
                </h3>
            </div>
            <div class="space-y-4">
                <div class="flex items-center p-3 rounded-lg hover:bg-gray-100 transition-colors" v-for="product in products" :key="product.id">
                    <div class="w-22 h-22 bg-gray-200 rounded-lg flex items-center justify-center">
                        <img :src="product.image"/>
                    </div>
                    <div class="ml-4 flex-1">
                        <h4 class="font-medium">{{ product.name }}</h4>
                        <div class="flex items-center mt-1">
                            <!-- <span class="text-accent font-bold">{{ product.price }}</span> -->
                            <span class="text-sm">{{ product.description }}</span>
                        </div>
                    </div>
                    <span class="font-bold border-10 border-transparent">{{ product.price }}元</span>
                    <span class="icon-[material-symbols--shopping-cart-outline] text-4xl hover:bg-red-400"></span>
                </div>
            </div>
        </div>
    </div>
    
</template>

<script setup lang="ts">
    import axios from "axios"
    import { onMounted, ref } from "vue"

    interface Product {
        id: number
        name: string
        price: number
        image: string
        description: string
    }
    interface Category {
        id: number
        name: string
    }

    const products = ref<Product[]>([])
    const Category = ref<Category[]>([])

    const CategoryIndex = ref(1)

    onMounted(() => {
        ShowCategory()
    })

    const show = (id: number) => {
        console.log(id)
        ShowProducts(id)
    }

    const ShowCategory = async () => {
        try {
            const response = await axios.get('/user/category/list')

            // 默认第一页
            ShowProducts(1)

            Category.value = response.data.data

        } catch (error) {
            console.log(error)
        }
    }

    const ShowProducts = async (id: number) => {
        try {
            CategoryIndex.value = id
            const response = await axios.get('/user/products/list', {
                params: {
                    categoryId: id
                }
            })

            products.value = response.data.data

        } catch (error) {
            console.log(error)
        }
    }
</script>

<style scoped></style>