<template>
  <div>
    <!-- 用 v-for 渲染多个按钮和对话框 -->
    <div v-for="user in users" :key="user.id">
      <Dialog v-model:open="dialogStates[user.id]">
        <DialogTrigger as-child>
          <Button variant="outline">
            编辑 {{ user.name }} 的资料
          </Button>
        </DialogTrigger>
        <DialogContent class="sm:max-w-[425px]">
          <DialogHeader>
            <DialogTitle>编辑 {{ user.name }} 的资料</DialogTitle>
          </DialogHeader>
          <div class="grid gap-4 py-4">
            <div class="grid grid-cols-4 items-center gap-4">
              <Label for="name-{{ user.id }}" class="text-right">
                姓名
              </Label>
              <Input :id="`name-${user.id}`" :value="user.name" class="col-span-3" />
            </div>
          </div>
          <DialogFooter>
            <Button type="submit" @click="dialogStates[user.id] = false">
              保存
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
      {{ dialogStates[user.id] }}
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
  import { Button } from "@/components/ui/button"
  import {
    Dialog,
    DialogContent,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
  } from "@/components/ui/dialog"
  import { Input } from "@/components/ui/input"
  import { Label } from "@/components/ui/label"

// 模拟用户数据
const users = ref([
  { id: 1, name: '张三' },
  { id: 2, name: '李四' }, // 需要默认打开这个
  { id: 3, name: '王五' },
])

// 用对象管理每个对话框的状态（键为 user.id，值为布尔值）
const dialogStates = ref<Record<number, boolean>>({})

// 初始化：默认所有对话框关闭
onMounted(() => {
  // 先初始化所有状态为 false
  users.value.forEach(user => {
    dialogStates.value[user.id] = false
  })

  // 根据条件（例如 id=2）自动打开目标对话框
  const targetUser = users.value.find(u => u.id === 2)
  if (targetUser) {
    dialogStates.value[targetUser.id] = true
  }
})
</script>