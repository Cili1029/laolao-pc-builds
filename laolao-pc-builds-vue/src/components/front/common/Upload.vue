<template>
    <div class="w-full">
        <!-- 文件网格容器 -->
        <TransitionGroup 
            tag="div" 
            name="list" 
            class="grid grid-cols-3 sm:grid-cols-4 md:grid-cols-5 gap-4"
        >
            <!-- 1. 文件卡片列表 -->
            <div v-for="(file, index) in files" :key="getFileKey(file)"
                class="group relative aspect-square bg-white rounded-xl border border-gray-200 shadow-sm overflow-hidden select-none hover:shadow-md transition-shadow">
                
                <!-- 内容：图片预览 或 图标 -->
                <div class="w-full h-full flex items-center justify-center bg-gray-50">
                    <img v-if="isImage(file)" 
                        :src="getObjectURL(file)" 
                        class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-110" 
                    />
                    <div v-else class="flex flex-col items-center p-2 text-center">
                        <span class="icon-[line-md--file] text-3xl text-gray-400 mb-1"></span>
                        <span class="text-[10px] text-gray-500 uppercase font-bold truncate w-full px-1">
                            {{ getExtension(file.name) }}
                        </span>
                    </div>
                </div>

                <!-- 遮罩：悬停显示删除按钮和文件名 -->
                <div class="absolute inset-0 bg-black/40 opacity-0 group-hover:opacity-100 transition-opacity duration-200 flex flex-col justify-between p-2">
                    <div class="flex justify-end">
                        <button @click.stop="removeFile(index)" 
                            class="bg-white/90 text-red-500 rounded-full p-1.5 hover:bg-red-50 hover:scale-110 transition-all shadow-sm"
                            title="删除">
                            <span class="icon-[mingcute--delete-2-line] text-sm block"></span>
                        </button>
                    </div>
                    <div class="text-white text-xs truncate font-medium drop-shadow-md">
                        {{ file.name }}
                    </div>
                </div>
            </div>

            <!-- 2. 上传按钮 (始终显示在最后，除非达到限制) -->
            <div v-if="maxFiles === -1 || files.length < maxFiles" key="upload-btn"
                class="aspect-square flex flex-col items-center justify-center rounded-xl border-2 border-dashed border-gray-300 text-gray-400 hover:text-blue-500 hover:border-blue-400 hover:bg-blue-50/30 cursor-pointer transition-all duration-200"
                @click="triggerUpload">
                <span class="icon-[mingcute--add-line] text-3xl mb-1"></span>
                <span class="text-xs font-medium">{{ files.length === 0 ? '上传文件' : '继续添加' }}</span>
            </div>
        </TransitionGroup>

        <!-- 底部提示 -->
        <div class="mt-2 text-right text-xs text-gray-400" v-if="files.length > 0">
            已选 {{ files.length }} {{ maxFiles > 0 ? `/ ${maxFiles}` : '' }} 个文件
        </div>

        <!-- 隐藏的原生 Input -->
        <input ref="fileInput" type="file" multiple class="hidden" @change="onFileChange" />
    </div>
</template>

<script lang="ts" setup>
import { computed, ref, onBeforeUnmount } from 'vue';

// --- 类型定义 ---
type Prop = {
    maxFiles?: number;
    data: File[];
    fileChange?: (data: File[]) => boolean | Promise<boolean>;
};

const props = withDefaults(defineProps<Prop>(), {
    maxFiles: -1,
    data: () => [],
    fileChange: () => true,
});

const emits = defineEmits(['update:data']);

// --- 状态与引用 ---
const fileInput = ref<HTMLInputElement>();
const objectURLs = new Map<File, string>(); // 缓存图片预览链接

// 双向绑定处理
const files = computed({
    get: () => props.data,
    set: async (val) => {
        if (await props.fileChange(val) !== false) {
            emits('update:data', val);
        }
    },
});

// --- 核心逻辑 ---

// 1. 触发选择
const triggerUpload = () => fileInput.value?.click();

// 2. 处理文件选择
const onFileChange = (e: Event) => {
    const target = e.target as HTMLInputElement;
    if (!target.files?.length) return;

    const newFiles = Array.from(target.files);
    
    // 数量限制逻辑
    if (props.maxFiles > 0) {
        const remainingSlots = props.maxFiles - files.value.length;
        if (remainingSlots <= 0) {
            alert('已达到文件数量上限');
            target.value = '';
            return;
        }
        if (newFiles.length > remainingSlots) {
            // 截取允许的数量
            files.value = [...files.value, ...newFiles.slice(0, remainingSlots)];
        } else {
            files.value = [...files.value, ...newFiles];
        }
    } else {
        files.value = [...files.value, ...newFiles];
    }
    
    target.value = ''; // 重置 Input，允许重复选择同名文件
};

// 3. 删除文件
const removeFile = (index: number) => {
    const file = files.value[index]!;
    revokeUrl(file); // 清理内存
    
    const newList = [...files.value];
    newList.splice(index, 1);
    files.value = newList;
};

// --- 工具函数 ---

// 这里的 key 使用名字+大小，避免 index 导致的渲染闪烁
const getFileKey = (file: File) => `${file.name}-${file.size}-${file.lastModified}`;

const isImage = (file: File) => file.type.startsWith('image/');

const getExtension = (name: string) => {
    const ext = name.split('.').pop();
    return ext ? ext.toUpperCase() : '?';
};

// 获取并缓存 Blob URL
const getObjectURL = (file: File) => {
    if (objectURLs.has(file)) return objectURLs.get(file);
    const url = URL.createObjectURL(file);
    objectURLs.set(file, url);
    return url;
};

// 清理单个 URL
const revokeUrl = (file: File) => {
    if (objectURLs.has(file)) {
        URL.revokeObjectURL(objectURLs.get(file)!);
        objectURLs.delete(file);
    }
};

// 组件销毁时彻底清理
onBeforeUnmount(() => {
    objectURLs.forEach(url => URL.revokeObjectURL(url));
    objectURLs.clear();
});
</script>

<style scoped>
/* 列表动画：让添加/删除操作更自然 */
.list-enter-active,
.list-leave-active {
    transition: all 0.3s ease;
}
.list-enter-from,
.list-leave-to {
    opacity: 0;
    transform: scale(0.9);
}
</style>