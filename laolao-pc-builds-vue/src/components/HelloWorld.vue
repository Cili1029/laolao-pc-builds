<template>
    <div class="w-full p-5">
        <h3 class="text-xl font-bold text-gray-800">确认订单</h3>
    </div>

    <!-- 左边地址，订单 -->
    <div class="w-5/7">
        <!-- 确认收货地址 -->
        <div class="grid grid-cols-1 p-2 border-2 md:grid-cols-2 lg:grid-cols-3 gap-4 rounded-lg">
            <div class="flex flex-col p-2 border-2 rounded-lg hover:border-2 hover:border-orange-500 transition-all"
                v-for="address in addressList" :key="address.id">
                <p class="text-xs p-1">{{ address.province }} {{ address.city }} {{ address.district }}</p>
                <p class="p-1 font-bold">{{ address.detailAddress }}</p>
                <p class="text-xs p-1">{{ address.consignee }} {{ address.phone }}</p>
                <div class="flex justify-between">
                    <p class="text-xs pl-1 hover:text-orange-500">默认</p>
                    <Dialog>
                        <DialogTrigger as-child>
                            <p class="text-xs pl-1 hover:text-orange-500" @click="copy(address)">编辑</p>
                        </DialogTrigger>
                        <DialogContent class="sm:max-w-[450px]">
                            <DialogHeader>
                                <DialogTitle>修改收获地址</DialogTitle>
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
                                            <SelectValue :placeholder=currentAddress.province />
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
                                    <Select @update:model-value="setRes" :disabled="!currentAddress.city.length">
                                        <SelectTrigger class="w-[90px]">
                                            <SelectValue :placeholder=currentAddress.district />
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
                                    <Button type="button" @click="update()"
                                        :disabled="!currentAddress.consignee || !currentAddress.phone || !currentAddress.province || !currentAddress.city || !currentAddress.district || !currentAddress.detailAddress">
                                        保存
                                    </Button>
                                </DialogClose>
                            </DialogFooter>
                        </DialogContent>
                    </Dialog>
                </div>
            </div>
        </div>
    </div>

    <!-- 右边付款详细 -->
    <div class="w-2/7">
    </div>
</template>

<script setup lang="ts">
    import axios from "@/utils/myAxios"
    import { onMounted, ref, reactive } from "vue"
    import { Select, SelectContent, SelectGroup, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
    import { Button } from "@/components/ui/button"
    import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger, DialogClose } from "@/components/ui/dialog"
    import { Input } from "@/components/ui/input"
    import { Label } from "@/components/ui/label"

    onMounted(() => {
        getProvinces()
        getAddress()
    })

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

    // 新增或修改地址
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

    const update = async () => {
        try {
            await axios.post("/user/address/update",
                currentAddress
            )
            currentAddress.consignee = ""
            currentAddress.phone = ""
            currentAddress.province = ""
            currentAddress.city = ""
            currentAddress.district = ""
            currentAddress.detailAddress = ""
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

    // 选择收货地址
    const getAddress = async () => {
        try {
            const response = await axios.get("/user/address/get")
            addressList.value = response.data.data

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
</script>