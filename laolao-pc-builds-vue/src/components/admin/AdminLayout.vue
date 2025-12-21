<template>
  <SidebarProvider>
    <AppSidebar />
    <SidebarInset class="flex flex-col min-h-screen bg-background">
      <header
        class="flex justify-between h-16 shrink-0 items-center gap-2 sticky top-0 bg-background/80 backdrop-blur z-10 transition-[width,height] ease-linear group-has-data-[collapsible=icon]/sidebar-wrapper:h-12">
        <div class="flex items-center gap-2 px-4 w-full">
          <SidebarTrigger class="-ml-1" />
          <Separator orientation="vertical" class="mr-2 data-[orientation=vertical]:h-4" />
          <ButtonGroup v-if="!route.path.startsWith('/admin/user/order') && !route.path.startsWith('/admin/dashboard')"
            class="ml-auto">
            <Button @click="commonStore.search.search = true" variant="outline" aria-label="Refresh">
              <RotateCw />
            </Button>
            <Input v-model="commonStore.search.searchContent" placeholder="搜索点什么..." />
            <Button :disabled="commonStore.search.searchContent.length === 0" @click="commonStore.search.search = true"
              variant="outline" aria-label="Search">
              <SearchIcon />
            </Button>
          </ButtonGroup>
        </div>
      </header>
      <main class="flex-1 p-2 pt-0 overflow-y-auto scrollbar-edge">
        <RouterView />
      </main>
    </SidebarInset>
  </SidebarProvider>
</template>

<script setup lang="ts">
  import AppSidebar from '@/components/admin/sidebar/AppSidebar.vue'
  import { Separator } from "@/components/ui/separator"
  import { SidebarInset, SidebarProvider, SidebarTrigger, } from '@/components/ui/sidebar'
  import { onUnmounted, watch } from 'vue'
  import { useUserStore } from '@/stores/UserStore'
  const userStore = useUserStore()
  import { useWebSocketStore } from '@/stores/websocketStore'
  const wsStore = useWebSocketStore()
  import { ButtonGroup } from '@/components/ui/button-group'
  import { SearchIcon, RotateCw } from 'lucide-vue-next'
  import { Input } from '@/components/ui/input'
  import { Button } from '@/components/ui/button'
  import { useCommonStore } from '@/stores/CommonStore'
  const commonStore = useCommonStore()
  import { useRoute } from 'vue-router'
  const route = useRoute()

  watch(
    () => userStore.user.id,
    (newId) => {
      if (newId) {
        wsStore.connect()
      } else {
        wsStore.disconnect()
      }
    },
    { immediate: true } // 初始化时立即执行一次
  )

  onUnmounted(() => {
    wsStore.disconnect()
  })
</script>
