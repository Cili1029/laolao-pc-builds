<template>
    <Dialog :open="open" @update:open="handleOpenChange">
        <DialogContent class="sm:max-w-3xl">
            <DialogHeader>
                <DialogTitle>图片管理</DialogTitle>
                <DialogDescription>
                    已选 {{ currentTotal }} / {{ maxFiles }} 张。选择新图片后，请点击底部“确认上传”保存。
                </DialogDescription>
            </DialogHeader>

            <!-- 主要内容区域：带滚动条 -->
            <ScrollArea class="max-h-[60vh] pr-4">
                <div class="grid grid-cols-3 sm:grid-cols-4 md:grid-cols-5 gap-4 p-1">

                    <!-- 1. 现有图片展示 (Server Images) -->
                    <div v-for="(url, index) in modelValue" :key="url"
                        class="group relative aspect-square bg-muted rounded-xl border border-border shadow-sm overflow-hidden">
                        <img :src="url"
                            class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-105" />

                        <!-- 遮罩层：删除按钮 -->
                        <div
                            class="absolute inset-0 bg-black/40 opacity-0 group-hover:opacity-100 transition-opacity duration-200 flex items-center justify-center">
                            <Button variant="destructive" size="icon" class="h-8 w-8 rounded-full shadow-md"
                                @click="handleDeleteRemote(url)">
                                <Trash2 class="h-4 w-4" />
                            </Button>
                        </div>

                        <!-- 状态标 -->
                        <div
                            class="absolute bottom-0 right-0 bg-emerald-500/90 text-primary-foreground text-[10px] px-1.5 rounded-tl-md font-medium">
                            已保存
                        </div>
                    </div>

                    <!-- 2. 待上传图片 (Pending Images) -->
                    <div v-for="(item, index) in newFiles" :key="item.key"
                        class="group relative aspect-square bg-background rounded-xl border-2 border-dashed border-blue-400/50 overflow-hidden">
                        <!-- 预览图 -->
                        <img :src="item.previewUrl" class="w-full h-full object-cover opacity-80" />

                        <!-- 加载动画 -->
                        <div v-if="uploading"
                            class="absolute inset-0 bg-black/50 flex flex-col items-center justify-center z-10">
                            <Loader2 class="h-6 w-6 text-white animate-spin" />
                        </div>

                        <!-- 移除按钮 (仅从队列移除) -->
                        <div v-if="!uploading"
                            class="absolute inset-0 bg-black/20 opacity-0 group-hover:opacity-100 transition-opacity duration-200 flex items-start justify-end p-1">
                            <button
                                class="bg-background/90 hover:bg-destructive hover:text-destructive-foreground text-muted-foreground rounded-full p-1 transition-colors shadow-sm"
                                @click="removeNewFile(index)">
                                <X class="h-3.5 w-3.5" />
                            </button>
                        </div>

                        <!-- 状态标 -->
                        <div
                            class="absolute bottom-0 right-0 bg-blue-500/90 text-white text-[10px] px-1.5 rounded-tl-md font-medium">
                            待上传
                        </div>
                    </div>

                    <!-- 3. 上传触发按钮 -->
                    <div v-if="currentTotal < maxFiles"
                        class="aspect-square flex flex-col items-center justify-center rounded-xl border-2 border-dashed border-muted-foreground/25 hover:border-primary hover:bg-muted/50 cursor-pointer transition-all duration-200"
                        @click="triggerSelect">
                        <div
                            class="h-8 w-8 rounded-full bg-muted flex items-center justify-center mb-2 group-hover:scale-110 transition-transform">
                            <Plus class="h-5 w-5 text-muted-foreground" />
                        </div>
                        <span class="text-xs text-muted-foreground font-medium">选择图片</span>
                    </div>

                </div>
            </ScrollArea>

            <DialogFooter class="flex items-center justify-between sm:justify-between gap-4">
                <div class="text-xs text-muted-foreground">
                    <span v-if="newFiles.length > 0" class="text-blue-600 font-medium">
                        等待上传: {{ newFiles.length }} 张
                    </span>
                </div>
                <div class="flex gap-2">
                    <Button variant="outline" @click="handleOpenChange(false)">关闭</Button>
                    <Button @click="handleUpload" :disabled="newFiles.length === 0 || uploading" class="min-w-[100px]">
                        <Loader2 v-if="uploading" class="mr-2 h-4 w-4 animate-spin" />
                        {{ uploading ? '上传中...' : '确认上传' }}
                    </Button>
                </div>
            </DialogFooter>

            <!-- 隐藏 Input -->
            <input ref="fileInput" type="file" multiple accept="image/*" class="hidden" @change="onFileSelected" />
        </DialogContent>
    </Dialog>
</template>

<script setup lang="ts">
    import { ref, computed, onBeforeUnmount, watch } from 'vue'
    import axios from '@/utils/myAxios' // 你的 axios
    import {
        Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle
    } from '@/components/ui/dialog'
    import { Button } from '@/components/ui/button'
    import { ScrollArea } from '@/components/ui/scroll-area'
    import { Plus, Trash2, X, Loader2 } from 'lucide-vue-next'

    // --- Props ---
    const props = withDefaults(defineProps<{
        open: boolean              // 控制弹窗开关
        modelValue?: string[]      // 现有的图片 URL 数组
        maxFiles?: number          // 最大数量
        uploadApi: string          // POST 上传接口
        deleteApi: string          // POST 删除接口
        uploadExtraData?: Record<string, any> // 额外参数
    }>(), {
        modelValue: () => [],
        maxFiles: 5,
        uploadExtraData: () => ({ type: 'common' })
    })

    const emits = defineEmits(['update:open', 'update:modelValue'])

    // --- 状态 ---
    const fileInput = ref<HTMLInputElement>()
    const uploading = ref(false)

    // 待上传队列结构
    interface NewFile {
        key: string
        file: File
        previewUrl: string
    }
    const newFiles = ref<NewFile[]>([])

    // 计算总数
    const currentTotal = computed(() => props.modelValue.length + newFiles.value.length)

    // --- 开关控制 ---
    const handleOpenChange = (val: boolean) => {
        if (!val) {
            // 关闭时，清理待上传队列，避免下次打开还有
            clearNewFiles()
        }
        emits('update:open', val)
    }

    // --- 文件选择 ---
    const triggerSelect = () => fileInput.value?.click()

    const onFileSelected = (e: Event) => {
        const target = e.target as HTMLInputElement
        const rawFiles = Array.from(target.files || [])
        if (!rawFiles.length) return

        const remaining = props.maxFiles - currentTotal.value
        if (remaining <= 0) {
            // 这里的 alert 可以换成 shadcn 的 toast
            alert(`最多只能上传 ${props.maxFiles} 张图片`)
            target.value = ''
            return
        }

        const list = rawFiles.slice(0, remaining)
        list.forEach(file => {
            newFiles.value.push({
                key: `${file.name}-${Date.now()}`,
                file,
                previewUrl: URL.createObjectURL(file)
            })
        })
        target.value = ''
    }

    // 移除待上传队列中的某一项
    const removeNewFile = (index: number) => {
        const item = newFiles.value[index]

        // 只有 item 存在时才释放内存
        if (item) {
            URL.revokeObjectURL(item.previewUrl)
            newFiles.value.splice(index, 1)
        }
    }

    // 清空待上传
    const clearNewFiles = () => {
        newFiles.value.forEach(f => URL.revokeObjectURL(f.previewUrl))
        newFiles.value = []
    }

    // --- 上传逻辑 (POST FormData) ---
    const handleUpload = async () => {
        if (newFiles.value.length === 0) return
        uploading.value = true

        try {
            const formData = new FormData()
            newFiles.value.forEach(item => {
                formData.append('images', item.file) // 后端接收字段名
            })

            // 添加额外参数
            Object.entries(props.uploadExtraData).forEach(([k, v]) => formData.append(k, v))

            // 请求
            const res = await axios.post(props.uploadApi, formData, {
                headers: { 'Content-Type': 'multipart/form-data' }
            })

            // 假设后端返回: { data: { images: ['url1', 'url2'] } }
            const serverUrls = res.data.data?.images || []

            if (serverUrls.length > 0) {
                // 更新双向绑定数据
                emits('update:modelValue', [...props.modelValue, ...serverUrls])
                clearNewFiles() // 清空待上传
                // 可以加个 toast 提示成功
            }
        } catch (err) {
            console.error(err)
            alert('上传失败')
        } finally {
            uploading.value = false
        }
    }

    // --- 删除逻辑 (调用 API) ---
    const handleDeleteRemote = async (url: string) => {
        if (!confirm('确定删除这张已保存的图片吗？')) return

        try {
            // 假设后端接口接收 { url: "..." }
            await axios.post(props.deleteApi, { url })

            // 从本地数组移除
            const newList = props.modelValue.filter(u => u !== url)
            emits('update:modelValue', newList)
        } catch (err) {
            console.error(err)
            alert('删除失败')
        }
    }

    // 销毁前清理内存
    onBeforeUnmount(() => clearNewFiles())
</script>