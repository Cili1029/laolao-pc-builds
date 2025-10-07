<template>
    <div class="flex">
        <div class="w-1/5 bg-white rounded-xl shadow-sm p-6 mr-4">
            <div class="flex justify-between items-center mb-6">
                <h3 class="text-xl font-bold text-gray-800 flex items-center">
                    <i class="fas fa-fire text-red-500 mr-2"></i> 分类
                </h3>
            </div>
            <div class="space-y-4">
                <div class="flex items-center p-3 rounded-lg transition-colors"
                    :class="cat.id === categoryIndex ? 'bg-gray-100' : 'hover:bg-gray-50'" v-for="cat in category"
                    :key="cat.id" @click="ShowProducts(cat.id)">
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
                    <i class="fas fa-fire text-red-500 mr-2"></i> {{ category[categoryIndex - 1]?.name }}
                </h3>
                <div class="flex w-full max-w-sm items-center gap-1.5">
                    <Input type="text" placeholder="搜索点什么..." v-model="searchContent" />
                    <Button type="submit" @click="search(category[categoryIndex - 1]?.id)">搜索</Button>
                </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-4">
                <div class="flex flex-col items-center p-3 rounded-lg hover:bg-gray-100 transition-colors shadow-sm"
                    v-for="product in products" :key="product.id">
                    <div class="w-22 h-22 bg-gray-200 rounded-lg flex items-center justify-center mb-2">
                        <img :src="product.image" />
                    </div>
                    <div class="text-center mb-2 h-30">
                        <h4 class="font-medium">{{ product.name }}</h4>
                        <span class="text-sm block mt-1">{{ product.commonDescription }}</span>
                    </div>
                    <div class="flex items-center justify-between w-full mt-auto">
                        <span class="font-bold text-red-500">{{ product.price }}起</span>
                        <Dialog>
                            <DialogTrigger as-child>
                                <span class="icon-[material-symbols--shopping-cart-outline] text-4xl hover:bg-red-500"
                                    @click="openProductDialog(product)"></span>
                            </DialogTrigger>
                            <DialogContent class="sm:max-w-[1000px]">
                                <DialogHeader>
                                    <DialogTitle>{{ product.name }}</DialogTitle>
                                    <DialogDescription>{{ product.commonDescription }}</DialogDescription>
                                </DialogHeader>
                                <div class="flex gap-6 py-4">
                                    <div class="w-1/2">
                                        <img :src="product.image" class="w-full h-auto rounded-lg" />
                                    </div>
                                    <div class="w-1/2 flex flex-col justify-between">
                                        <div class="mb-4 h-1/2">
                                            <p class="text-sm text-gray-600">{{ selectedEdition?.description ||
                                                currentEditions[0]?.description }}</p>
                                        </div>
                                        <div class="space-y-2 mb-4">
                                            <Button type="button" class="w-full" v-for="edi in currentEditions"
                                                :key="`${edi.productId}-${edi.editionName}`" @click="selectEdition(edi)"
                                                :variant="(selectedEdition?.editionName === edi.editionName || (!selectedEdition && edi === currentEditions[0])) ? 'default' : 'outline'">
                                                {{ edi.editionName }} - {{ edi.price }}元
                                            </Button>
                                        </div>
                                        <DialogFooter class="mt-auto">
                                            <Button type="button" class="w-full" @click="buy()">
                                                买！ - {{ selectedEdition?.price || currentEditions[0]?.price }}元
                                            </Button>
                                        </DialogFooter>
                                    </div>
                                </div>
                            </DialogContent>
                        </Dialog>
                    </div>
                </div>

            </div>
        </div>
    </div>

</template>

<script setup lang="ts">
    import axios from "axios"
    import { onMounted, ref } from "vue"
    import { Button } from "@/components/ui/button"
    import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
    import { Input } from "@/components/ui/input"
    import 'vue-sonner/style.css'
    import { toast } from "vue-sonner"

    interface Product {
        id: number
        name: string
        image: string
        commonDescription: string
        price: number
    }
    interface category {
        id: number
        name: string
    }
    interface Edition {
        productId: number
        editionName: string
        price: number
        description: string
    }

    const products = ref<Product[]>([])
    const category = ref<category[]>([])

    const categoryIndex = ref(1)

    onMounted(() => {
        ShowCategory()
    })

    const ShowCategory = async () => {
        try {
            const response = await axios.get('/user/category/list')

            // 默认第一页
            ShowProducts(1)

            category.value = response.data.data

        } catch (error) {
            console.log(error)
        }
    }

    const ShowProducts = async (id: number) => {
        try {
            categoryIndex.value = id
            const response = await axios.get('/user/products/products-list', {
                params: {
                    categoryId: id
                }
            })

            products.value = response.data.data

        } catch (error) {
            console.log(error)
        }
    }

    // 后端获得的所有数据
    const currentEditions = ref<Edition[]>([])
    // 用户选择的版本  进去默认选择第一个版本  下单版本
    const selectedEdition = ref<Edition | null>(null)

    // 打开商品对话框
    const openProductDialog = async (product: Product) => {
        // 重置之前的状态
        currentEditions.value = []
        selectedEdition.value = null

        // 设置新商品

        try {
            const response = await axios.get('/user/products/edition-list', {
                params: {
                    productsId: product.id
                }
            })
            currentEditions.value = response.data.data

            // 进去默认选择第一个版本
            selectedEdition.value = currentEditions.value[0] || null;
        } catch (error) {
            console.log(error)
        }
    }

    // 选择版本的方法
    const selectEdition = (edi: Edition) => {
        selectedEdition.value = edi
    }

    const buy = () => {
        console.log(selectedEdition.value)
    }

    const searchContent = ref()

    const search = async (categoryId: number | undefined) => {
        try {
            const response = await axios.get('/user/products/search', {
                params: {
                    searchContent: searchContent.value,
                    categoryId: categoryId
                }
            })

            console.log(response.data.data)
            if (response.data.data.length === 0) {
                toast("啊哈！", {
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

<style scoped></style>