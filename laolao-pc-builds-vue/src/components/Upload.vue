<template>
	<div class="h-full relative border-2 border-dashed border-gray-300 rounded-lg p-6 text-center transition-all duration-200 ease-in-out hover:border-blue-400 hover:bg-blue-50"
		@click="chooseFiles" tabindex="0">
		<!-- 空白状态 -->
		<div v-if="files.length === 0" class="h-full py-8 flex flex-col items-center justify-center">
			<p class="icon-[humbleicons--upload] text-8xl text-blue-500 mb-4"></p>
			<div>
				<p class="text-gray-600 font-medium">点击此处上传文件</p>
				<p class="text-gray-500 text-sm mt-2">支持多文件上传(最多 {{ maxFiles }} 个)</p>
			</div>
		</div>

		<div v-else class="flex flex-col items-center justify-center h-full py-8">
			<!-- 文件列表 -->
			<div class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-4">
				<div v-for="(file, index) in files" :key="index"
					class="relative bg-white rounded-lg border border-gray-200 p-3 hover:shadow-md transition-shadow duration-200">
					<div class="flex flex-col items-center">
						<span class="icon-[line-md--file] text-5xl text-blue-500 mb-2"></span>
						<div class="text-sm font-medium text-gray-700 truncate w-full text-center">
							{{ file.name }}
						</div>
						<div class="text-xs text-gray-500 mt-1">
							{{ formatFileSize(file.size) }}
						</div>
					</div>

					<button @click.stop="removeFile(index)" class="absolute -top-2 -right-2 text-blue-500"
						aria-label="删除文件">
						<span class="icon-[lineicons--cross-circle] text-2xl"></span>
					</button>
				</div>
			</div>

			<!-- 文件数量提示 -->
			<div v-if="files.length > 0" class="mt-4 text-sm text-gray-600">
				已选择 {{ files.length }} 个文件
				<span v-if="maxFiles > 0">(最多 {{ maxFiles }} 个)</span>
			</div>
		</div>

		<!-- 隐藏的文件输入 -->
		<input ref="fileInput" type="file" multiple class="hidden" @change="inputChange" />
	</div>
</template>

<script lang="ts" setup>
	import { computed, ref } from 'vue';

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

	const fileInput = ref<HTMLInputElement>();

	const files = computed({
		get() {
			return props.data;
		},
		set: async (val) => {
			// 检查文件数量限制
			if (props.maxFiles > 0 && val.length > props.maxFiles) {
				alert(`最多只能上传 ${props.maxFiles} 个文件`);
				return;
			}

			// 调用文件变化回调
			const flag = await props.fileChange(val);
			if (flag === false) {
				return;
			}

			emits('update:data', val);
		},
	});

	const chooseFiles = () => {
		if (fileInput.value) {
			fileInput.value.click();
		}
	};

	const inputChange = (e: Event) => {
		const target = e.target as HTMLInputElement;
		const newFiles = Array.from(target.files || []);
		files.value = [...files.value, ...newFiles];

		// 清空输入值，允许重复选择相同文件
		target.value = '';
	};

	const removeFile = (index: number) => {
		const newFiles = [...files.value];
		newFiles.splice(index, 1);
		files.value = newFiles;
	};

	const formatFileSize = (bytes: number): string => {
		if (bytes === 0) return '0 Bytes';

		const k = 1024;
		const sizes = ['Bytes', 'KB', 'MB', 'GB'];
		const i = Math.floor(Math.log(bytes) / Math.log(k));

		return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
	};
</script>