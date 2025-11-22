<template>
    <div class="flex h-full flex-col">
        <div class="flex items-center justify-between mx-10 py-3">
            <p class="text-orange-500 text-lg font-bold py-3">热卖分类</p>
            <div class="flex gap-1.5 w-1/2 items-center">
                <Input type="text" placeholder="搜索点什么..." v-model="searchContent" class="py-5 border-2 border-orange-500"/>
                <Button type="submit" @click="search(currentCategory?.id)" class="py-5 bg-orange-500 font-bold hover:bg-orange-600">搜索</Button>
            </div>
            <div></div>
        </div>
        <div class="flex gap-2 pb-3">
            <span v-for="category in categories" :key="category.id" @click="ShowComponent(category)"
                class="p-2 rounded-md shadow-sm border-1 hover:border-orange-500 hover:text-orange-500 transition-all flex items-center gap-1"
                :class="{ 'border-orange-500 text-orange-500': category.id === currentCategory.id }">
                <span class="icon-[streamline-cyber--smiley-sigh] text-xl"></span>
                <span>{{ category.name }}</span>
            </span>
        </div>
        <div class="w-full bg-white rounded-xl shadow-sm p-6 h-full overflow-y-auto scrollbar-edge">
            <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-5 gap-4">
                <router-link :to="`/buy/product/${product.productType}/${product.id}`"
                    class="flex flex-col items-center p-3 rounded-lg hover:bg-gray-100 transition-colors shadow-sm"
                    v-for="product in products" :key="product.id">
                    <div class="w-22 h-22 bg-gray-200 rounded-lg flex items-center justify-center mb-2">
                        <img :src="product.image" />
                    </div>
                    <div class="text-center mb-5 h-30">
                        <h4 class="font-medium">{{ product.name }}</h4>
                        <span v-if="product.productType === 1" class="text-sm block mt-1">{{
                            product.commonDescription
                        }}</span>
                    </div>
                    <span v-show="product.sales !== 0" class="flex items-start mt-1 self-start">已售:{{ product.sales
                    }}</span>
                    <div class="flex items-center justify-between w-full mt-auto">
                        <span v-if="product.productType === 1" class="font-bold text-red-500">￥{{ product.price
                        }}起</span>
                        <span v-else-if="product.productType === 2" class="font-bold text-red-500">￥{{ product.price
                        }}</span>
                        <span class="icon-[material-symbols--shopping-cart-outline] text-4xl hover:bg-red-500"></span>
                    </div>
                </router-link>
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
        commonDescription: string
        description: string
        sales: number
    }

    interface category {
        id: number
        productType: number
        name: string
    }

    const products = ref<Product[]>([])
    const categories = ref<category[]>([])
    const currentCategory = ref<category>({
        id: 0,
        productType: 0,
        name: ''
    })

    onMounted(() => {
        ShowCategory()
    })

    const ShowCategory = async () => {
        try {
            const response = await axios.get('/api/user/shop/category/list')
            categories.value = response.data.data
            if (Number(route.params.id) === -1) {
                // 默认页
                router.replace(`/buy/${categories.value[0]?.id}`)
                currentCategory.value = categories.value[0]!
                ShowComponent(categories.value[0]!)
            } else {
                const category = categories.value.find(c => c.id === Number(route.params.id))
                if (category) {
                    currentCategory.value = category
                    ShowComponent(category)
                }
            }
        } catch (error) {
            console.log(error)
        }
    }

    const ShowComponent = async (category: category) => {
        try {
            currentCategory.value = category
            router.replace(`/buy/${category.id}`)
            if (category.productType === 1) {
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

            products.value = response.data.data
        } catch (error) {
            console.log(error)
        }
    }
</script>
