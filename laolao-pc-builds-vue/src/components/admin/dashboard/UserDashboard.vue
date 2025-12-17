<template>
    <div class="flex flex-col w-full px-3 py-3 space-y-6">
        <div class="grid gap-6 md:grid-cols-3">
            <!-- 当前在线 -->
            <Card>
                <CardHeader class="flex flex-row items-center justify-between space-y-0">
                    <CardTitle class="text-sm font-medium">当前在线</CardTitle>
                    <Activity class="h-4 w-4 text-muted-foreground" />
                </CardHeader>
                <CardContent>
                    <div class="text-2xl font-bold">{{ commonStore.onlineUser.onlineCount }}</div>
                    <div class="flex items-center justify-between mt-4">
                        <!-- 头像堆叠组件 -->
                        <div class="flex -space-x-3 overflow-hidden">
                            <TooltipProvider v-for="user in commonStore.onlineUser.onlineUsers" :key="user.id">
                                <Tooltip>
                                    <TooltipTrigger as-child>
                                        <Avatar
                                            class="inline-block h-8 w-8 rounded-full border-2 border-background cursor-pointer hover:z-10 hover:scale-110 transition-transform">
                                            <AvatarImage :src="user.avatar" />
                                            <AvatarFallback>{{ user.username.substring(0, 2) }}</AvatarFallback>
                                        </Avatar>
                                    </TooltipTrigger>
                                    <TooltipContent>
                                        <p>{{ user.username }}</p>
                                    </TooltipContent>
                                </Tooltip>
                            </TooltipProvider>
                            <!-- 超过5个显示 +N -->
                            <div v-if="commonStore.onlineUser.onlineCount > 5"
                                class="flex h-8 w-8 items-center justify-center rounded-full border-2 border-background bg-muted text-[10px] font-medium text-muted-foreground hover:z-10">
                                +{{ commonStore.onlineUser.onlineCount - 3 }}
                            </div>
                        </div>
                        <p class="text-xs text-muted-foreground">实时活跃中</p>
                    </div>
                </CardContent>
            </Card>

            <!-- 今日新增 -->
            <Card>
                <CardHeader class="flex flex-row items-center justify-between space-y-0 pb-2">
                    <CardTitle class="text-sm font-medium">本月新增用户</CardTitle>
                    <UserPlus class="h-4 w-4 text-muted-foreground" />
                </CardHeader>
                <CardContent>
                    <div class="text-2xl font-bold">+{{ summary.newCount }}</div>
                    <div class="flex items-center justify-between mt-4">
                        <div class="flex -space-x-3 overflow-hidden">
                            <TooltipProvider v-for="user in summary.newUsers" :key="user.id">
                                <Tooltip>
                                    <TooltipTrigger as-child>
                                        <Avatar
                                            class="inline-block h-8 w-8 rounded-full border-2 border-background cursor-pointer hover:z-10 hover:scale-110 transition-transform">
                                            <AvatarImage :src="user.avatar" />
                                            <AvatarFallback>{{ user.name.substring(0, 2) }}</AvatarFallback>
                                        </Avatar>
                                    </TooltipTrigger>
                                    <TooltipContent>
                                        <p>新用户: {{ user.name }}</p>
                                    </TooltipContent>
                                </Tooltip>
                            </TooltipProvider>
                        </div>
                        <p class="text-xs text-muted-foreground">
                            较上个月 <span class="text-green-500 font-medium">↑ 12%</span>
                        </p>
                    </div>
                </CardContent>
            </Card>

            <!-- 总用户数 -->
            <Card>
                <CardHeader class="flex flex-row items-center justify-between space-y-0 pb-2">
                    <CardTitle class="text-sm font-medium">平台总用户</CardTitle>
                    <Users class="h-4 w-4 text-muted-foreground" />
                </CardHeader>
                <CardContent>
                    <div class="text-2xl font-bold">{{ summary.totalCount }}</div>
                    <p class="text-xs text-muted-foreground mt-4">
                        总注册量持续增长中
                    </p>
                    <div class="mt-2 h-1.5 w-full bg-secondary rounded-full overflow-hidden">
                        <div class="h-full bg-primary w-[75%] rounded-full" /> <!-- 模拟进度条装饰 -->
                    </div>
                </CardContent>
            </Card>
        </div>

        <Card class="mt-auto">
            <CardHeader>
                <CardTitle>用户增长趋势</CardTitle>
                <CardDescription>近12个月每月新增用户数</CardDescription>
            </CardHeader>
            <CardContent>
                <ChartContainer :config="chartConfig" class="max-h-[256px] w-full 2xl:max-h-[464px]">
                    <VisXYContainer :data="summary.monthCounts" :margin="{ left: -24 }" :y-domain="[0, undefined]">
                        <VisLine :x="(_: Data, i: number) => i" :y="(d: Data) => d.count"
                            :color="chartConfig.count.color" :curve-type="CurveType.Linear" />
                        <VisAxis type="x" :x="(_: Data, i: number) => i" :tick-line="false" :domain-line="false"
                            :grid-line="false" :tick-values="summary.monthCounts.map((_, i) => i)" :tick-format="(i: number) => {
                                const item = summary.monthCounts[i];
                                return item!.month
                            }" />
                        <VisAxis type="y" :num-ticks="3" :tick-line="false" :domain-line="false" />
                        <ChartTooltip />
                        <ChartCrosshair
                            :template="componentToString(chartConfig, ChartTooltipContent, { hideLabel: true })"
                            :color="chartConfig.count.color" />
                    </VisXYContainer>
                </ChartContainer>
            </CardContent>
            <CardFooter class="flex-col items-start gap-2 text-sm">
            </CardFooter>
        </Card>
    </div>
</template>

<script setup lang="ts">
    import { ref, onMounted } from 'vue'
    import axios from '@/utils/myAxios'
    import { Avatar, AvatarImage, AvatarFallback } from '@/components/ui/avatar'
    import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from '@/components/ui/tooltip'
    import { Activity, Users, UserPlus } from 'lucide-vue-next'
    import type { ChartConfig, } from "@/components/ui/chart"
    import { CurveType } from "@unovis/ts"
    import { VisAxis, VisLine, VisXYContainer } from "@unovis/vue"
    import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle, } from "@/components/ui/card"
    import { ChartContainer, ChartCrosshair, ChartTooltip, ChartTooltipContent, componentToString, } from "@/components/ui/chart"
    import { useCommonStore } from '@/stores/CommonStore'
    const commonStore = useCommonStore()

    // 模拟初始化
    onMounted(() => {
        fetchSummary()
    })

    // 定义数据接口
    interface UserSimple {
        id: number;
        name: string;
        avatar: string;
    }

    interface MonthCount {
        month: string
        count: number
    }

    interface SummaryData {
        newCount: number
        newUsers: UserSimple[]
        totalCount: number
        monthCounts: MonthCount[]
    }

    const summary = ref<SummaryData>({
        newCount: 0,
        newUsers: [],
        totalCount: 0,
        monthCounts: []
    })

    const fetchSummary = async () => {
        try {
            const res = await axios.get('/api/admin/dashboard/user_summary')
            summary.value = res.data.data
        } catch (e) {
            console.error(e)
        }
    }

    type Data = typeof summary.value.monthCounts[number]

    const chartConfig = {
        count: {
            label: "新增用户",
            color: "var(--chart-1)",
        },
    } satisfies ChartConfig
</script>