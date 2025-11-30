<template>
  <SidebarProvider>
    <AppSidebar />
    <SidebarInset>
      <header
        class="flex justify-between h-16 shrink-0 items-center gap-2 transition-[width,height] ease-linear group-has-data-[collapsible=icon]/sidebar-wrapper:h-12">
        <div class="flex items-center gap-2 px-4">
          <SidebarTrigger class="-ml-1" />
          <Separator orientation="vertical" class="mr-2 data-[orientation=vertical]:h-4" />
        </div>
        <!-- 根据当前页面判断是否显示搜索框 -->
        <div class="flex pr-2 w-full max-w-sm items-center space-x-2">
          <Input v-model="commonStore.search.searchContent" placeholder="搜索点什么..." />
          <Button :disabled="!(commonStore.search.searchContent.length > 0)"
            @click="commonStore.search.search = true">搜索</Button>

          <Button @click="commonStore.openDialog = true">新增</Button>
        </div>
      </header>
      <div class=" flex flex-1 p-2 pt-0">
        <RouterView />
      </div>
    </SidebarInset>
  </SidebarProvider>
</template>

<script setup lang="ts">
  import AppSidebar from '@/components/admin/sidebar/AppSidebar.vue'
  import { Separator } from "@/components/ui/separator"
  import { SidebarInset, SidebarProvider, SidebarTrigger, } from '@/components/ui/sidebar'
  import { Button } from '@/components/ui/button'
  import { Input } from '@/components/ui/input'
  import { useCommonStore } from '@/stores/CommonStore'
  const commonStore = useCommonStore()
</script>
