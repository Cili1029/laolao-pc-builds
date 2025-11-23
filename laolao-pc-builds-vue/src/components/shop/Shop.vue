<template>
    <div class="flex h-full flex-col">
        <div class="flex items-center justify-between mx-10 py-3">
            <p class="text-orange-500 text-lg font-bold py-3">热卖分类</p>
            <div class="flex gap-1.5 w-1/2 items-center">
                <Input type="text" placeholder="搜索点什么..." v-model="searchContent"
                    class="py-5 border-2 border-orange-500" />
                <Button type="submit" @click="search(currentCategory?.id)"
                    class="py-5 bg-orange-500 font-bold hover:bg-orange-600">搜索</Button>
            </div>
            <div></div>
        </div>
        <div class="flex gap-2 pb-3">
            <span v-for="category in categories" :key="category.id" @click="ShowComponent(category)"
                class="p-2 rounded-md shadow-sm border-1 hover:border-orange-500 hover:text-orange-500 transition-all flex items-center gap-1"
                :class="{ 'border-orange-500 text-orange-500': category.id === currentCategory.id }">
                <img :src="category.image" class="h-7 w-7 rounded-md" />
                <span>{{ category.name }}</span>
            </span>
        </div>
        <div class="w-full bg-white rounded-xl shadow-sm p-4 h-full overflow-y-auto scrollbar-edge">
            <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-5 gap-4 relative">
                <router-link :to="`/buy/product/${product.productType}/${product.id}`" class="flex flex-col items-center p-3 rounded-lg shadow-sm hover:bg-gray-100 transition-all
                       relative z-10 hover:z-50 hover:scale-105 hover:shadow-lg" v-for="product in products"
                    :key="product.id">
                    <div class="bg-gray-200 rounded-lg flex items-center justify-center mb-2 overflow-hidden">
                        <img :src="product.image" class="w-full rounded-md" />
                    </div>
                    <h4 class="font-medium py-2">{{ product.name }}</h4>
                    <div class="items-center w-full mt-auto">
                        <div class="flex items-center mt-auto">
                            <p class="font-bold text-orange-600 text-lg">￥{{ product.price
                            }}
                                <span v-if="product.productType === 1" class="text-base font-medium">起</span>
                            </p>
                            <span class="text-sm pl-3">{{ product.sales > 0 ? product.sales+"+人付款" : "新品" }}</span>
                        </div>
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
        sales: number
    }

    interface category {
        id: number
        productType: number
        name: string
        image: string
    }

    const products = ref<Product[]>([])
    const categories = ref<category[]>([])
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
