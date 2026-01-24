<template>
    <div class="h-full w-full bg-gray-50 overflow-y-auto scrollbar-edge">
        <div class="flex flex-col gap-4 pb-8">
            <!-- 标题栏 -->
            <div class="w-full pt-2">
                <h3 class="text-xl font-bold text-gray-900 tracking-tight flex items-center gap-2">
                    <span class="w-1 h-5 bg-orange-500 rounded-r"></span>
                    确认订单
                </h3>
            </div>

            <div class="w-full flex flex-col lg:flex-row gap-4 items-start">
                <div class="flex-1 w-full space-y-4 min-w-0">
                    <!-- 确认收货地址卡片 -->
                    <div class="bg-white p-5 rounded-lg shadow-sm border border-gray-100">
                        <div class="flex justify-between items-center mb-4">
                            <p class="font-bold text-gray-800 text-base">收货地址</p>
                            <Dialog>
                                <DialogTrigger as-child>
                                    <Button variant="outline" size="sm"
                                        class="h-8 text-xs hover:bg-orange-50 hover:text-orange-600 border-dashed border-gray-300"
                                        @click="addAddress()">
                                        + 新增地址
                                    </Button>
                                </DialogTrigger>
                                <DialogContent class="sm:max-w-[450px]">
                                    <DialogHeader>
                                        <DialogTitle>添加收货地址</DialogTitle>
                                        <DialogDescription></DialogDescription>
                                    </DialogHeader>
                                    <div class="grid gap-4 py-4">
                                        <div class="grid grid-cols-4 items-center gap-4">
                                            <Label for="consignee" class="text-right">收货人</Label>
                                            <Input id="consignee" class="col-span-3"
                                                v-model="currentAddress.consignee" />
                                        </div>
                                        <div class="grid grid-cols-4 items-center gap-4">
                                            <Label for="phone" class="text-right">手机号</Label>
                                            <Input id="phone" class="col-span-3" v-model="currentAddress.phone" />
                                        </div>
                                        <div class="grid grid-cols-4 items-center gap-4">
                                            <Label for="phone" class="text-right">收货地</Label>
                                            <Select @update:model-value="getCities" :disabled="loading">
                                                <SelectTrigger class="w-[90px]">
                                                    <SelectValue placeholder="省" />
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
                                            <Select @update:model-value="setRes"
                                                :disabled="!currentAddress.city.length">
                                                <SelectTrigger class="w-[90px]">
                                                    <SelectValue placeholder="区" />
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
                                            <Label for="detailAddress" class="text-right">详细地址</Label>
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

                        <!-- 地址列表 -->
                        <div class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-3">
                            <div class="group relative flex flex-col p-3 border rounded-lg cursor-pointer transition-all duration-200"
                                v-for="address in addressList" :key="address.id" :class="address.id === selectAddress
                                    ? 'border-orange-500 bg-orange-50/50 ring-1 ring-orange-500 shadow-sm'
                                    : 'border-gray-200 bg-white hover:border-orange-300 hover:shadow-sm'"
                                @click="changeAddress(address.id)">

                                <div v-if="address.defaultAddress === 1"
                                    class="absolute top-0 right-0 bg-orange-500 text-white text-[10px] px-1.5 py-0.5 rounded-bl rounded-tr-lg shadow-sm">
                                    默认
                                </div>

                                <div class="flex items-baseline gap-2 mb-1">
                                    <span class="font-bold text-gray-900 text-base truncate">{{ address.consignee
                                    }}</span>
                                    <span class="text-xs text-gray-500">{{ address.phone }}</span>
                                </div>

                                <div class="flex-grow">
                                    <p class="text-xs text-gray-500">{{ address.province }} {{ address.city }} {{
                                        address.district }}</p>
                                    <p class="text-sm font-medium text-gray-700 line-clamp-2 mt-1 min-h-[2.5em]">{{
                                        address.detailAddress }}</p>
                                </div>

                                <div class="flex items-center justify-between pt-2 border-t border-gray-100 opacity-0 group-hover:opacity-100 transition-opacity"
                                    :class="{ 'opacity-100 border-orange-200': address.id === selectAddress }">
                                    <p class="text-xs cursor-pointer text-gray-400 hover:text-orange-600"
                                        @click.stop="setDefault(address.id)" v-if="address.defaultAddress !== 1">
                                        设为默认
                                    </p>
                                    <span v-else class="text-xs text-orange-500">默认地址</span>

                                    <div class="flex gap-2">
                                        <Dialog>
                                            <DialogTrigger as-child>
                                                <p class="text-xs cursor-pointer text-blue-600 hover:text-blue-700"
                                                    @click.stop="copy(address)">编辑</p>
                                            </DialogTrigger>
                                            <DialogContent class="sm:max-w-[450px]">
                                                <DialogHeader>
                                                    <DialogTitle>修改收货地址</DialogTitle>
                                                    <DialogDescription></DialogDescription>
                                                </DialogHeader>
                                                <div class="grid gap-4 py-4">
                                                    <div class="grid grid-cols-4 items-center gap-4">
                                                        <Label for="consignee" class="text-right">收货人</Label>
                                                        <Input id="consignee" class="col-span-3"
                                                            v-model="currentAddress.consignee" />
                                                    </div>
                                                    <div class="grid grid-cols-4 items-center gap-4">
                                                        <Label for="phone" class="text-right">手机号</Label>
                                                        <Input id="phone" class="col-span-3"
                                                            v-model="currentAddress.phone" />
                                                    </div>
                                                    <div class="grid grid-cols-4 items-center gap-4">
                                                        <Label for="phone" class="text-right">收货地</Label>
                                                        <Select @update:model-value="getCities" :disabled="loading">
                                                            <SelectTrigger class="w-[90px]">
                                                                <SelectValue :placeholder=currentAddress.province />
                                                            </SelectTrigger>
                                                            <SelectContent>
                                                                <SelectGroup>
                                                                    <SelectItem :value="province.adcode"
                                                                        v-for="province in provinces"
                                                                        :key="province.adcode">
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
                                                                    <SelectItem :value="city.adcode"
                                                                        v-for="city in cities" :key="city.adcode">
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
                                                                        v-for="district in districts"
                                                                        :key="district.adcode">
                                                                        {{ district.name }}
                                                                    </SelectItem>
                                                                </SelectGroup>
                                                            </SelectContent>
                                                        </Select>
                                                    </div>
                                                    <div class="grid grid-cols-4 items-center gap-4">
                                                        <Label for="detailAddress" class="text-right">详细地址</Label>
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
                                                <p class="text-xs cursor-pointer text-red-500 hover:text-red-600"
                                                    @click.stop>删除</p>
                                            </AlertDialogTrigger>
                                            <AlertDialogContent>
                                                <AlertDialogHeader>
                                                    <AlertDialogTitle>确定要删除该地址吗？</AlertDialogTitle>
                                                    <AlertDialogDescription>操作一旦完成无法撤回，请谨慎选择</AlertDialogDescription>
                                                </AlertDialogHeader>
                                                <AlertDialogFooter>
                                                    <AlertDialogCancel>算了</AlertDialogCancel>
                                                    <AlertDialogAction class="bg-red-500 hover:bg-red-600"
                                                        @click="deleteAddress(address.id)">删除</AlertDialogAction>
                                                </AlertDialogFooter>
                                            </AlertDialogContent>
                                        </AlertDialog>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 确认订单信息 -->
                    <div class="bg-white rounded-lg shadow-sm border border-gray-100 overflow-hidden">
                        <div class="px-5 py-3 border-b border-gray-100 bg-gray-50/50">
                            <p class="font-bold text-gray-800 text-base">商品清单</p>
                        </div>
                        <div class="divide-y divide-gray-100">
                            <div v-for="product in products" :key="product.name"
                                class="p-4 flex items-center hover:bg-gray-50 transition-colors">
                                <div class="w-16 h-16 rounded border border-gray-200 mr-4 flex-shrink-0 bg-white">
                                    <img :src="product.image" class="w-full h-full object-cover" />
                                </div>
                                <div class="flex-1">
                                    <h3 class="font-medium text-gray-900 text-sm mb-1">{{ product.name }}</h3>
                                    <p class="text-xs text-gray-500 bg-gray-100 px-1.5 py-0.5 rounded w-fit">{{
                                        product.variantName }}</p>
                                </div>
                                <div class="text-right ml-4">
                                    <p class="text-base font-bold text-gray-900">￥{{ product.price }}</p>
                                    <p class="text-xs text-gray-400">× {{ product.quantity }}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 右边付款详细 -->
                <div class="w-full lg:w-[340px] flex-shrink-0">
                    <div class="sticky top-0 bg-white p-5 border border-gray-100 rounded-lg shadow-lg flex flex-col">
                        <div class="border-b border-gray-100 pb-3 mb-4">
                            <p class="font-bold text-gray-800 text-base">付款详情</p>
                        </div>

                        <div class="space-y-3 mb-6">
                            <div class="flex justify-between items-center text-sm">
                                <span class="text-gray-600">商品总价</span>
                                <span class="font-medium text-gray-900">￥{{ originalAmount }}</span>
                            </div>
                            <div class="flex justify-between items-center text-sm cursor-pointer hover:bg-gray-50 -mx-2 px-2 py-1 rounded transition-colors group"
                                @click="showCouponDialog(3)">
                                <span class="text-gray-600 flex items-center gap-1">
                                    优惠券
                                    <span
                                        class="text-[10px] border border-red-200 text-red-500 px-1 rounded bg-red-50">券</span>
                                </span>
                                <div class="flex items-center text-red-500 font-medium">
                                    - ￥{{ discountAmount }}
                                    <span
                                        class="text-gray-400 ml-1 text-xs transform group-hover:translate-x-0.5 transition-transform">▶</span>
                                </div>
                            </div>
                            <div class="flex justify-between items-center text-sm">
                                <span class="text-gray-600">运费</span>
                                <span class="text-gray-900">免运费</span>
                            </div>
                        </div>

                        <div class="mt-auto pt-4 border-t border-gray-100">
                            <div class="flex justify-between items-end mb-4">
                                <p class="text-gray-500 text-sm pb-1">实付款</p>
                                <div class="text-right text-orange-600">
                                    <span class="text-xs font-bold align-bottom mb-1 inline-block">￥</span>
                                    <span class="text-2xl font-extrabold leading-none">{{ originalAmount -
                                        discountAmount }}</span>
                                </div>
                            </div>

                            <!-- 选中的地址预览 -->
                            <div class="bg-orange-50/50 rounded p-2.5 mb-3 border border-orange-100 text-xs text-gray-600"
                                v-if="selectAddress !== 0">
                                <p class="line-clamp-1 font-medium text-gray-900 mb-0.5">
                                    <span class="text-orange-600 mr-1">📍</span>
                                    {{addressList.find(a => a.id === selectAddress)?.province}} {{addressList.find(a =>
                                        a.id === selectAddress)?.city}} {{addressList.find(a => a.id ===
                                        selectAddress)?.district}}
                                </p>
                                <p class="line-clamp-1 pl-4">{{addressList.find(a => a.id ===
                                    selectAddress)?.detailAddress}}</p>
                                <p class="pl-4 mt-1 text-gray-500">{{addressList.find(a => a.id ===
                                    selectAddress)?.consignee}} {{addressList.find(a => a.id === selectAddress)?.phone
                                    }}</p>
                            </div>

                            <AlertDialog>
                                <AlertDialogTrigger as-child>
                                    <button
                                        class="w-full bg-gradient-to-r from-orange-500 to-red-500 hover:from-orange-600 hover:to-red-600 text-white font-bold py-3 rounded-lg shadow-md hover:shadow-lg transition-all active:scale-[0.98] disabled:from-gray-300 disabled:to-gray-400 disabled:shadow-none disabled:cursor-not-allowed text-sm"
                                        :disabled="selectAddress === 0">
                                        {{ selectAddress === 0 ? '请选择收货地址' : '立即付款' }}
                                    </button>
                                </AlertDialogTrigger>
                                <AlertDialogContent>
                                    <AlertDialogHeader>
                                        <AlertDialogTitle>确定付款吗？</AlertDialogTitle>
                                        <AlertDialogDescription>
                                            未接入微信支付宝支付接口，点击付款视为付款成功
                                        </AlertDialogDescription>
                                    </AlertDialogHeader>
                                    <AlertDialogFooter>
                                        <AlertDialogCancel>等一下</AlertDialogCancel>
                                        <AlertDialogAction class="bg-orange-500 hover:bg-orange-600" @click="pay()">付款
                                        </AlertDialogAction>
                                    </AlertDialogFooter>
                                </AlertDialogContent>
                            </AlertDialog>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <CouponDialog :type="couponDialogType" :number="(route.params.number as string) || ''"
            :userCouponId="userCouponId" v-model:isOpen="isOpenCouponDialog" @discountAmount="handleCouponUse"
            @userCouponId="handleUserCouponId">
        </CouponDialog>

        <div v-html="alipayHtml" ref="alipayFormContainer"></div>
    </div>
</template>

<script setup lang="ts">
    import axios from "@/utils/myAxios"
    import { onMounted, ref, reactive, computed, nextTick } from "vue"
    import { useRoute, useRouter } from 'vue-router'
    const route = useRoute()
    const router = useRouter()
    import { Select, SelectContent, SelectGroup, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
    import { Button } from "@/components/ui/button"
    import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, DialogClose } from "@/components/ui/dialog"
    import { Input } from "@/components/ui/input"
    import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle, AlertDialogTrigger, } from '@/components/ui/alert-dialog'
    import { Label } from "@/components/ui/label"
    import CouponDialog from '@/components/front/shop/CouponDialog.vue';

    onMounted(() => {
        // 判断订单是否为待付款再进行下一步
        getStatus()
    })

    const getStatus = async () => {
        const response = await axios.get("/api/user/shop/order/status", {
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
            const response = await axios.get('/api/user/shop/address/district', {
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
            const response = await axios.get('/api/user/shop/address/district', {
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
            const response = await axios.get('/api/user/shop/address/district', {
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
        detailAddress: "",
        // 添加用
        number: route.params.number
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
        const { id, ...res } = currentAddress
        try {
            const response = await axios.post("/api/user/shop/address/add",
                res
            )
            currentAddress.consignee = ""
            currentAddress.phone = ""
            currentAddress.province = ""
            currentAddress.city = ""
            currentAddress.district = ""
            currentAddress.detailAddress = ""
            addressList.value = response.data.data
            showOrder()
        } catch (error) {
            console.log(error)
        }
    }

    const update = async () => {
        const { number, ...res } = currentAddress
        try {
            const response = await axios.post("/api/user/shop/address/update",
                res
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
        defaultAddress: number
    }

    const addressList = ref<AddressList[]>([])

    const selectAddress = ref(0)

    // 选择收货地址
    const getAddress = async () => {
        try {
            const response = await axios.get("/api/user/shop/address/get")
            addressList.value = response.data.data
            const defaultId = addressList.value.find(a => a.defaultAddress === 1)?.id
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

            const res1 = await axios.get("/api/user/shop/address/district", {
                params: {
                    name: address.province
                }
            })
            cities.value = res1.data.data


            const res2 = await axios.get("/api/user/shop/address/district", {
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
        if (AddressId === selectAddress.value) {
            return
        }
        try {
            await axios.patch("/api/user/shop/order/address", {
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
            const response = await axios.patch(`/api/user/shop/address/default/${id}`)
            addressList.value = response.data.data

        } catch (error) {
            console.log(error)
        }
    }

    // 删除地址
    const deleteAddress = async (id: number) => {
        try {
            const response = await axios.delete(`/api/user/shop/address/del/${id}`)
            addressList.value = response.data.data
            if (id === selectAddress.value) {
                selectAddress.value = 0
            }

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
    const userCouponId = ref(0)

    const productNames = computed(() => {
        return products.value.map(item => item.name).join('，')
    })


    const showOrder = async () => {
        try {
            const response = await axios.get("/api/user/shop/order/list", {
                params: {
                    number: number
                }
            })
            products.value = response.data.data.orderDetails || []
            originalAmount.value = response.data.data.originalAmount
            discountAmount.value = response.data.data.discountAmount
            userCouponId.value = response.data.data.userCouponId
            selectAddress.value = response.data.data.addressId

        } catch (error) {
            console.log(error)
        }
    }


    const alipayHtml = ref('')

    // 新增引用 ref
    const alipayFormContainer = ref<HTMLDivElement | null>(null)

    // 提交订单
    const pay = async () => {
        try {
            const response = await axios.patch("/api/user/shop/pay", {
                number: number,
                subject: productNames.value,
                payType: 1
            }, {
                responseType: 'text'
            })
            alipayHtml.value = response.data
            // if (response.data.code === 1) {
            //     router.replace('/my-orders');
            // }
            

            nextTick(() => {
                const form = alipayFormContainer.value?.querySelector('form')
                if (form) {
                    form.submit()
                }
            })

        } catch (error) {
            console.log(error)
        }
    }

    const isOpenCouponDialog = ref(false); // 控制弹窗显示/隐藏
    const couponDialogType = ref(1)
    const showCouponDialog = (type: number) => {
        couponDialogType.value = type
        isOpenCouponDialog.value = true
    }

    const handleCouponUse = (discount: number) => {
        discountAmount.value = discount
    };

    const handleUserCouponId = (value: number) => {
        userCouponId.value = value; // 把子组件传的值赋值给父组件的变量
    };


</script>