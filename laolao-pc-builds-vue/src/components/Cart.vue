<template>
    <div>
        <Drawer>
            <DrawerTrigger>Open</DrawerTrigger>
            <DrawerContent v-if="products && products.length > 0" class="h-3/4">
                <DrawerHeader>
                    <DrawerTitle class="text-3xl">我的购物车</DrawerTitle>
                    <DrawerDescription></DrawerDescription>
                </DrawerHeader>
                <div class="flex gap-6 py-4 overflow-y-auto">
                    <div class="w-full flex flex-col space-y-2">
                        <div v-for="product in products" :key="product.name"
                            class="bg-white rounded-lg shadow-sm border border-gray-200 p-4 flex items-center">
                            <img :src="product.image" class="w-15 h-15 object-cover rounded-md mr-4" />
                            <div class="flex-1">
                                <h3 class="font-medium text-gray-900">{{ product.name }}</h3>
                                {{ product.variantName }}
                            </div>
                            <div class="ml-auto mx-10">
                                <span class="text-lg font-bold text-red-600">{{ product.price }}元</span>
                            </div>
                            <div class="ml-auto mx-10">
                                <Button variant="outline" size="icon" class="h-8 w-8 shrink-0 rounded-full"
                                    @click="quantity(product, 0)">
                                    <span class="icon-[mdi--minus] h-4 w-4"></span>
                                    <span class="sr-only">Decrease</span>
                                </Button>
                                <span class="text-lg font-bold mx-1">{{ product.quantity }}</span>
                                <Button variant="outline" size="icon" class="h-8 w-8 shrink-0 rounded-full"
                                    @click="quantity(product, 1)">
                                    <span class="icon-[mdi--plus] h-4 w-4"></span>
                                    <span class="sr-only">Increase</span>
                                </Button>
                            </div>
                        </div>
                    </div>
                </div>
                <DrawerFooter>
                    <div class="text-xl ml-auto font-bold text-red-600">总价: {{ totalPrice.toFixed(2) }}元</div>
                    <Button class="h-15">提交订单</Button>
                </DrawerFooter>
            </DrawerContent>

            <DrawerContent v-else class="h-3/4">
                <DrawerHeader>
                    <DrawerTitle class="text-3xl">我的购物车</DrawerTitle>
                    <DrawerDescription></DrawerDescription>
                </DrawerHeader>
                <div class="text-4xl text-center">
                    购物车是空的！
                </div>
                <DrawerFooter>
                    <Button class="h-15" disabled>提交订单</Button>
                </DrawerFooter>
            </DrawerContent>
        </Drawer>

    </div>
</template>

<script setup lang="ts">
    import axios from "@/utils/myAxios"
    import { onMounted, ref, computed } from "vue"
    import { Drawer, DrawerContent, DrawerDescription, DrawerFooter, DrawerHeader, DrawerTitle, DrawerTrigger } from "@/components/ui/drawer"
    import { Button } from "@/components/ui/button"

    onMounted(() => {
        showCart()
    })

    interface Product {
        id: number,
        type: number,
        name: string
        variantName: string,
        image: string,
        price: number,
        quantity: number
    }

    const products = ref<Product[]>([])

    const totalPrice = computed(() =>
        products.value.reduce((sum, product) => sum + product.price * product.quantity, 0)
    )

    const showCart = async () => {
        try {
            const response = await axios.get("/user/cart/list")
            products.value = response.data.data || []
        } catch (error) {
            console.log(error)
        }
    }

    const quantity = async (product: Product, type: number) => {
        try {
            if (type === 0) {
                await axios.delete("/user/cart/minus", {
                    data: {
                        type: product.type,
                        productId: product.id
                    }
                })

                const update = products.value.find(p => p.id === product?.id)
                if (update) {
                    update.quantity = update.quantity - 1
                    if(update.quantity === 0) {
                        products.value = products.value.filter(p => p.id !== product?.id)
                    }
                }
                console.log(products.value)

            } else {
                await axios.post("/user/cart/plus", {
                    type: product.type,
                    productId: product.id
                })

                const update = products.value.find(p => p.id === product?.id)
                if (update) {
                    update.quantity = update.quantity + 1
                }
            }

        } catch (error) {
            console.log(error)
        }
    }
    

</script>

<style scoped></style>