<template>
    <div class="w-full p-3">
        <h3 class="text-xl font-bold text-gray-800">确认订单</h3>
    </div>
    <div class="flex space-x-2">
        <!-- 左边地址，订单 -->
        <div class="w-5/7 space-y-2">
            <!-- 确认收货地址 -->
            <div class="bg-white flex flex-col p-2 border-2 rounded-lg">
                <div class="flex justify-between w-full items-center">
                    <p class="font-bold">确认收货地址</p>
                    <Dialog>
                        <DialogTrigger as-child>
                            <Button variant="outline" @click="addAddress()">新增地址</Button>
                        </DialogTrigger>
                        <DialogContent class="sm:max-w-[450px]">
                            <DialogHeader>
                                <DialogTitle>添加收货地址</DialogTitle>
                                <DialogDescription></DialogDescription>
                            </DialogHeader>
                            <div class="grid gap-4 py-4">
                                <div class="grid grid-cols-4 items-center gap-4">
                                    <Label for="consignee" class="text-right">
                                        收货人
                                    </Label>
                                    <Input id="consignee" class="col-span-3" v-model="currentAddress.consignee" />
                                </div>
                                <div class="grid grid-cols-4 items-center gap-4">
                                    <Label for="phone" class="text-right">
                                        手机号
                                    </Label>
                                    <Input id="phone" class="col-span-3" v-model="currentAddress.phone" />
                                </div>
                                <div class="grid grid-cols-4 items-center gap-4">
                                    <Label for="phone" class="text-right">
                                        收货地
                                    </Label>
                                    <Select @update:model-value="getCities" :disabled="loading">
                                        <SelectTrigger class="w-[90px]">
                                            <SelectValue placeholder="省" />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectGroup>
                                                <SelectItem :value="province.adcode" v-for="province in provinces"
                                                    :key="province.adcode">
                                                    {{ province.name }}
                                                </SelectItem>
                                            </SelectGroup>
                                        </SelectContent>
                                    </Select>
                                    <Select @update:model-value="getDistricts"
                                        :disabled="!currentAddress.province.length">
                                        <SelectTrigger class="w-[90px]">
                                            <SelectValue placeholder="市" />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectGroup>
                                                <SelectItem :value="city.adcode" v-for="city in cities"
                                                    :key="city.adcode">
                                                    {{ city.name }}
                                                </SelectItem>
                                            </SelectGroup>
                                        </SelectContent>
                                    </Select>
                                    <Select @update:model-value="setRes" :disabled="!currentAddress.city.length">
                                        <SelectTrigger class="w-[90px]">
                                            <SelectValue placeholder="区" />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectGroup>
                                                <SelectItem :value="district.adcode" v-for="district in districts"
                                                    :key="district.adcode">
                                                    {{ district.name }}
                                                </SelectItem>
                                            </SelectGroup>
                                        </SelectContent>
                                    </Select>
                                </div>
                                <div class="grid grid-cols-4 items-center gap-4">
                                    <Label for="detailAddress" class="text-right">
                                        详细地址
                                    </Label>
                                    <Input id="detailAddress" class="col-span-3"
                                        v-model="currentAddress.detailAddress" />
                                </div>
                            </div>
                            <DialogFooter>
                                <DialogClose as-child>
                                    <Button type="button" @click="add()"
                                        :disabled="!currentAddress.consignee || !currentAddress.phone || !currentAddress.province || !currentAddress.city || !currentAddress.district || !currentAddress.detailAddress">
                                        保存
                                    </Button>
                                </DialogClose>
                            </DialogFooter>
                        </DialogContent>
                    </Dialog>
                </div>
                <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 rounded-lg">
                    <div class="flex flex-col p-2 border-2 rounded-lg hover:border-2 hover:border-orange-500 transition-all"
                        v-for="address in addressList" :key="address.id"
                        :class="{ 'border-orange-500': address.id === selectAddress }"
                        @click="changeAddress(address.id)">
                        <p class="text-xs p-1">{{ address.province }} {{ address.city }} {{ address.district }}</p>
                        <p class="p-1 font-bold">{{ address.detailAddress }}</p>
                        <p class="text-xs p-1">{{ address.consignee }} {{ address.phone }}</p>
                        <div class="flex justify-between">
                            <p class="text-xs pl-1 hover:text-orange-500" @click.stop="setDefault(address.id)">
                                默认
                                <span v-show="address.isDefault === 1">✔</span>
                            </p>
                            <Dialog>
                                <DialogTrigger as-child>
                                    <p class="text-xs pl-1 hover:text-orange-500" @click.stop="copy(address)">编辑</p>
                                </DialogTrigger>
                                <DialogContent class="sm:max-w-[450px]">
                                    <DialogHeader>
                                        <DialogTitle>修改收货地址</DialogTitle>
                                        <DialogDescription></DialogDescription>
                                    </DialogHeader>
                                    <div class="grid gap-4 py-4">
                                        <div class="grid grid-cols-4 items-center gap-4">
                                            <Label for="consignee" class="text-right">
                                                收货人
                                            </Label>
                                            <Input id="consignee" class="col-span-3"
                                                v-model="currentAddress.consignee" />
                                        </div>
                                        <div class="grid grid-cols-4 items-center gap-4">
                                            <Label for="phone" class="text-right">
                                                手机号
                                            </Label>
                                            <Input id="phone" class="col-span-3" v-model="currentAddress.phone" />
                                        </div>
                                        <div class="grid grid-cols-4 items-center gap-4">
                                            <Label for="phone" class="text-right">
                                                收货地
                                            </Label>
                                            <Select @update:model-value="getCities" :disabled="loading">
                                                <SelectTrigger class="w-[90px]">
                                                    <SelectValue :placeholder=currentAddress.province />
                                                </SelectTrigger>
                                                <SelectContent>
                                                    <SelectGroup>
                                                        <SelectItem :value="province.adcode"
                                                            v-for="province in provinces" :key="province.adcode">
                                                            {{ province.name }}
                                                        </SelectItem>
                                                    </SelectGroup>
                                                </SelectContent>
                                            </Select>
                                            <Select @update:model-value="getDistricts"
                                                :disabled="!currentAddress.province.length">
                                                <SelectTrigger class="w-[90px]">
                                                    <SelectValue :placeholder=currentAddress.city />
                                                </SelectTrigger>
                                                <SelectContent>
                                                    <SelectGroup>
                                                        <SelectItem :value="city.adcode" v-for="city in cities"
                                                            :key="city.adcode">
                                                            {{ city.name }}
                                                        </SelectItem>
                                                    </SelectGroup>
                                                </SelectContent>
                                            </Select>
                                            <Select @update:model-value="setRes"
                                                :disabled="!currentAddress.city.length">
                                                <SelectTrigger class="w-[90px]">
                                                    <SelectValue :placeholder=currentAddress.district />
                                                </SelectTrigger>
                                                <SelectContent>
                                                    <SelectGroup>
                                                        <SelectItem :value="district.adcode"
                                                            v-for="district in districts" :key="district.adcode">
                                                            {{ district.name }}
                                                        </SelectItem>
                                                    </SelectGroup>
                                                </SelectContent>
                                            </Select>
                                        </div>
                                        <div class="grid grid-cols-4 items-center gap-4">
                                            <Label for="detailAddress" class="text-right">
                                                详细地址
                                            </Label>
                                            <Input id="detailAddress" class="col-span-3"
                                                v-model="currentAddress.detailAddress" />
                                        </div>
                                    </div>
                                    <DialogFooter>
                                        <DialogClose as-child>
                                            <Button type="button" @click="update()"
                                                :disabled="!currentAddress.consignee || !currentAddress.phone || !currentAddress.province || !currentAddress.city || !currentAddress.district || !currentAddress.detailAddress">
                                                保存
                                            </Button>
                                        </DialogClose>
                                    </DialogFooter>
                                </DialogContent>
                            </Dialog>
                            <AlertDialog>
                                <AlertDialogTrigger as-child>
                                    <p class="text-xs pl-1 hover:text-orange-500" @click.stop>删除</p>
                                </AlertDialogTrigger>
                                <AlertDialogContent>
                                    <AlertDialogHeader>
                                        <AlertDialogTitle>确定要删除吗？</AlertDialogTitle>
                                        <AlertDialogDescription>
                                            点错了就关，别真删除了，在被窝里偷偷听反方向的钟
                                        </AlertDialogDescription>
                                    </AlertDialogHeader>
                                    <AlertDialogFooter>
                                        <AlertDialogCancel>点错了</AlertDialogCancel>
                                        <AlertDialogAction @click="deleteAddress(address.id)">故意的</AlertDialogAction>
                                    </AlertDialogFooter>
                                </AlertDialogContent>
                            </AlertDialog>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 确认订单信息 -->
            <div class="bg-white flex border border-gray-200 p-2 rounded-lg">
                <div class="w-full flex flex-col space-y-2">
                    <div class="flex justify-between w-full items-center">
                        <p class="font-bold">确认订单信息</p>
                    </div>
                    <div v-for="product in products" :key="product.name"
                        class="rounded-lg shadow-sm border border-gray-200 p-4 flex items-center">
                        <img :src="product.image" class="w-15 h-15 object-cover rounded-md mr-4" />
                        <div class="flex-1">
                            <h3 class="font-medium text-gray-900">{{ product.name }}</h3>
                            {{ product.variantName }}
                        </div>
                        <div class="ml-auto">
                            <span class="text-lg font-bold text-orange-500">￥{{ product.price }}×{{ product.quantity
                                }}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 右边付款详细 -->
        <div class="w-2/7 h-80 bg-white p-2 border-2 rounded-lg flex flex-col">
            <div class="flex flex-col space-y-2 flex-grow">
                <div class="flex w-full items-center">
                    <p class="font-bold">付款详细</p>
                </div>
                <div class="flex justify-between">
                    <p class="text-m text-gray-800 mb-4">商品总价</p>
                    <p class="text-m text-gray-800 mb-4">￥{{ originalAmount }}</p>
                </div>
            </div>

            <!-- 这个部分会固定在底部 -->
            <div class="flex justify-between items-center mt-auto pt-4 border-t">
                <div>
                    <p class="text-gray-600">实付款</p>
                    <p class="text-xl font-bold text-orange-500">￥{{ originalAmount - discountAmount }}</p>
                </div>
                <button
                    class="bg-orange-500 hover:bg-orange-600 text-white font-medium py-3 px-6 rounded-lg transition-colors disabled:bg-gray-400 disabled:cursor-not-allowed"
                    :disabled="!selectAddress" @click="pay()">
                    我要付款
                </button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
    import axios from "@/utils/myAxios"
    import { onMounted, ref, reactive } from "vue"
    import { useRoute, useRouter } from 'vue-router'
    const route = useRoute()
    const router = useRouter()
    import { Select, SelectContent, SelectGroup, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
    import { Button } from "@/components/ui/button"
    import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, DialogClose } from "@/components/ui/dialog"
    import { Input } from "@/components/ui/input"
    import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle, AlertDialogTrigger, } from '@/components/ui/alert-dialog'
    import { Label } from "@/components/ui/label"

    onMounted(() => {
        // 判断订单是否为待付款再进行下一步
        getStatus()
    })

    const getStatus = async () => {
        try {
            const response = await axios.get('/user/order/status', {
                params: {
                    number: number
                }
            })
            if (response.data.code === 1) {
                // 获取用户的地址
                getAddress()
                // 获取订单信息
                showOrder()
            } else {
                router.push('/home');
            }
        } catch (error) {
            console.log(error)
        }
    }

    interface District {
        level: string
        adcode: number,
        name: string
    }

    // 显示的省市区
    const provinces = ref<District[]>([])
    const cities = ref<District[]>([])
    const districts = ref<District[]>([])

    // 省是否在加载
    const loading = ref(true)

    const getProvinces = async () => {
        try {
            const response = await axios.get('/user/address/district', {
                params: {
                    adcode: null
                }
            })
            provinces.value = response.data.data

            loading.value = false
        } catch (error) {
            console.log(error)
        }
    }
    const getCities = async (adcode: any) => {
        currentAddress.city = ''
        currentAddress.district = ''
        try {
            const response = await axios.get('/user/address/district', {
                params: {
                    adcode: adcode
                }
            })
            cities.value = response.data.data
        } catch (error) {
            console.log(error)
        }
        const res = provinces.value.find(p => p.adcode === adcode)
        currentAddress.province = res?.name || ''
    }
    const getDistricts = async (adcode: any) => {
        currentAddress.district = ''
        try {
            const response = await axios.get('/user/address/district', {
                params: {
                    adcode: adcode
                }
            })
            districts.value = response.data.data
        } catch (error) {
            console.log(error)
        }
        const res = cities.value.find(p => p.adcode === adcode)
        currentAddress.city = res?.name || ''
    }
    const setRes = (adcode: any) => {
        const res = districts.value.find(p => p.adcode === adcode)
        currentAddress.district = res?.name || ''
    }

    // 新增,修改地址
    // 当前收件人信息
    const currentAddress = reactive({
        id: 0,
        consignee: "",
        phone: "",
        province: "",
        city: "",
        district: "",
        detailAddress: ""
    })

    const addAddress = () => {
        getProvinces()
        currentAddress.consignee = ""
        currentAddress.phone = ""
        currentAddress.province = ""
        currentAddress.city = ""
        currentAddress.district = ""
        currentAddress.detailAddress = ""
    }

    const add = async () => {
        try {
            const response = await axios.post("/user/address/add",
                currentAddress
            )

            currentAddress.consignee = ""
            currentAddress.phone = ""
            currentAddress.province = ""
            currentAddress.city = ""
            currentAddress.district = ""
            currentAddress.detailAddress = ""
            addressList.value = response.data.data
        } catch (error) {
            console.log(error)
        }
    }

    const update = async () => {
        try {
            const response = await axios.post("/user/address/update",
                currentAddress
            )
            currentAddress.consignee = ""
            currentAddress.phone = ""
            currentAddress.province = ""
            currentAddress.city = ""
            currentAddress.district = ""
            currentAddress.detailAddress = ""
            addressList.value = response.data.data
        } catch (error) {
            console.log(error)
        }
    }

    interface AddressList {
        id: number
        consignee: string
        phone: string
        province: string,
        city: string,
        district: string,
        detailAddress: string
        isDefault: number
    }

    const addressList = ref<AddressList[]>([])

    const selectAddress = ref() || undefined

    // 选择收货地址
    const getAddress = async () => {
        try {
            const response = await axios.get("/user/address/get")
            addressList.value = response.data.data
            const defaultId = addressList.value.find(a => a.isDefault === 1)?.id
            if (!(defaultId === undefined)) {
                selectAddress.value = defaultId
            }
        } catch (error) {
            console.log(error)
        }
    }

    // 收货地址的行政区划划分
    const copy = async (address: AddressList) => {
        currentAddress.id = address.id
        currentAddress.consignee = address.consignee
        currentAddress.phone = address.phone
        currentAddress.province = address.province
        currentAddress.city = address.city
        currentAddress.district = address.district
        currentAddress.detailAddress = address.detailAddress
        try {
            getProvinces()

            const res1 = await axios.get("/user/address/district", {
                params: {
                    name: address.province
                }
            })
            cities.value = res1.data.data


            const res2 = await axios.get("/user/address/district", {
                params: {
                    name: address.city
                }
            })
            districts.value = res2.data.data
        } catch (error) {
            console.log(error)
        }
    }

    // 改变收货地址
    const changeAddress = async (AddressId: number) => {
        try {
            await axios.patch("/user/order/address", {
                addressId: AddressId,
                number: number
            })
            selectAddress.value = AddressId

        } catch (error) {
            console.log(error)
        }
    }

    const setDefault = async (id: number) => {
        try {
            const response = await axios.patch(`/user/address/default/${id}`)
            addressList.value = response.data.data

        } catch (error) {
            console.log(error)
        }
    }

    // 删除地址
    const deleteAddress = async (id: number) => {
        try {
            const response = await axios.delete(`/user/address/del/${id}`)
            addressList.value = response.data.data

        } catch (error) {
            console.log(error)
        }
    }

    // 订单
    const number = route.params.number
    interface Product {
        id: number,
        name: string
        variantName: string,
        image: string,
        price: number,
        quantity: number
    }

    // 商品信息
    const products = ref<Product[]>([])
    const originalAmount = ref(0)
    const discountAmount = ref(0)

    const showOrder = async () => {
        try {
            const response = await axios.get("/user/order/list", {
                params: {
                    number: number
                }
            })
            products.value = response.data.data.orderDetails || []
            originalAmount.value = response.data.data.originalAmount
            discountAmount.value = response.data.data.discountAmount
        } catch (error) {
            console.log(error)
        }
    }

    // 提交订单
    const pay = async () => {
        try {
            const response = await axios.patch("/user/order/pay", {
                number: number
            })
            if (response.data.code === 1) {
                router.push('/my-orders');
            }
        } catch (error) {
            console.log(error)
        }
    }

</script>