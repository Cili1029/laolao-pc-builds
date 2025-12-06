<template>
  <Sidebar v-bind="props">
    <SidebarHeader>
      <SidebarMenu>
        <SidebarMenuItem>
          <SidebarMenuButton size="lg"
            class="data-[state=open]:bg-sidebar-accent data-[state=open]:text-sidebar-accent-foreground cursor-default">
            <div
              class="flex aspect-square size-8 items-center justify-center rounded-lg bg-sidebar-primary text-sidebar-primary-foreground">
              <img :src="logo" />
            </div>
            <div class="grid flex-1 text-left text-sm leading-tight">
              <span class="truncate font-medium">
                劳劳的装机工坊
              </span>
              <span class="truncate text-xs">管理端</span>
            </div>
          </SidebarMenuButton>
        </SidebarMenuItem>
      </SidebarMenu>
    </SidebarHeader>

    <SidebarContent>
      <NavMain :items="data.navMain" />
      <NavProjects :projects="data.projects" />
    </SidebarContent>

    <SidebarFooter>
      <NavUser :user="data.user" />
    </SidebarFooter>

    <SidebarRail />
  </Sidebar>
</template>

<script setup lang="ts">
  import type { SidebarProps } from '@/components/ui/sidebar'
  import logo from '@/assets/logo.jpg'
  import { User, Store, BotMessageSquare, Frame, Map, PieChart, } from "lucide-vue-next"
  import NavMain from '@/components/admin/sidebar/NavMain.vue'
  import NavProjects from '@/components/admin/sidebar/NavProjects.vue'
  import NavUser from '@/components/admin/sidebar/NavUser.vue'
  import { Sidebar, SidebarContent, SidebarFooter, SidebarHeader, SidebarRail, SidebarMenu, SidebarMenuItem, SidebarMenuButton } from '@/components/ui/sidebar'

  const props = withDefaults(defineProps<SidebarProps>(), {
    collapsible: "icon",
  })

  // 数据源
  const data = {
    user: {
      name: "shadcn",
      email: "m@example.com",
      avatar: "/avatars/shadcn.jpg",
    },
    navMain: [
      {
        title: "用户",
        url: "#",
        icon: User,
        isActive: true,
        items: [
          { title: "用户信息", url: "/admin/user" },
          { title: "订单", url: "#" },
        ],
      },
      {
        title: "商城",
        url: "#",
        icon: Store,
        isActive: false,
        items: [
          { title: "分类标签", url: "/admin/shop/category" },
          { title: "组件", url: "/admin/shop/component" },
          { title: "整机", url: "/admin/shop/bundle" },
          { title: "优惠券", url: "#" },
        ],
      },
      {
        title: "论坛",
        url: "#",
        icon: BotMessageSquare,
        isActive: false,
        items: [
          { title: "分类标签", url: "/admin/forum/category" },
          { title: "帖子", url: "#" },
          { title: "评论", url: "#" },
        ],
      },
    ],
    projects: [
      { name: "销售统计", url: "#", icon: Frame },
      { name: "测试", url: "/admin/test", icon: PieChart },
      { name: "Travel", url: "#", icon: Map },
    ],
  }

</script>