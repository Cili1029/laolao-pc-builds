<template>
    <div class="flex flex-col w-full h-full px-3 py-3 space-y-6 overflow-hidden">
        <div class="grid gap-6 md:grid-cols-3 shrink-0">
            <!-- 当前在线 -->
            <Card class="hover:shadow-md transition-shadow">
                <CardHeader class="flex flex-row items-center justify-between space-y-0 pb-2">
                    <CardTitle class="text-xs font-medium text-muted-foreground uppercase tracking-wider">实时在线
                    </CardTitle>
                    <Activity class="h-4 w-4 text-green-500" />
                </CardHeader>
                <CardContent>
                    <div class="text-2xl font-bold tracking-tight text-green-600">
                        {{ commonStore.onlineUser.onlineCount }}
                    </div>
                    <div class="flex items-center justify-between mt-3">
                        <div class="flex -space-x-2.5 overflow-hidden">
                            <!-- 只取前4个头像，避免挤爆 -->
                            <TooltipProvider v-for="user in commonStore.onlineUser.onlineUsers.slice(0, 4)"
                                :key="user.id">
                                <Tooltip>
                                    <TooltipTrigger as-child>
                                        <Avatar
                                            class="h-7 w-7 border-2 border-background ring-1 ring-black/5 transition-transform hover:scale-110 hover:z-20">
                                            <AvatarImage :src="user.avatar" />
                                            <AvatarFallback class="text-[10px] bg-green-100 text-green-700">
                                                {{ user.username.substring(0, 2) }}
                                            </AvatarFallback>
                                        </Avatar>
                                    </TooltipTrigger>
                                    <TooltipContent>
                                        <p>{{ user.username }}</p>
                                    </TooltipContent>
                                </Tooltip>
                            </TooltipProvider>
                            <!-- 剩余人数提示 -->
                            <div v-if="commonStore.onlineUser.onlineCount > 4"
                                class="flex h-7 w-7 items-center justify-center rounded-full border-2 border-background bg-muted text-[10px] font-medium text-muted-foreground">
                                +{{ commonStore.onlineUser.onlineCount - 4 }}
                            </div>
                        </div>
                        <span
                            class="text-[10px] font-medium px-1.5 py-0.5 rounded-full bg-green-100 text-green-700">LIVE</span>
                    </div>
                </CardContent>
            </Card>

            <!-- 2. 本月新增 -->
            <Card class="hover:shadow-md transition-shadow">
                <CardHeader class="flex flex-row items-center justify-between space-y-0 pb-2">
                    <CardTitle class="text-xs font-medium text-muted-foreground uppercase tracking-wider">本月新增
                    </CardTitle>
                    <UserPlus class="h-4 w-4 text-blue-500" />
                </CardHeader>
                <CardContent>
                    <div class="text-2xl font-bold tracking-tight text-blue-600">
                        +{{ summary.newCount }}
                    </div>
                    <div class="flex items-center justify-between mt-3">
                        <div class="flex -space-x-2.5 overflow-hidden">
                            <TooltipProvider v-for="user in (summary.newUsers || []).slice(0, 4)" :key="user.id">
                                <Tooltip>
                                    <TooltipTrigger as-child>
                                        <Avatar
                                            class="h-7 w-7 border-2 border-background ring-1 ring-black/5 transition-transform hover:scale-110 hover:z-20">
                                            <AvatarImage :src="user.avatar" />
                                            <AvatarFallback class="text-[10px] bg-blue-100 text-blue-700">
                                                {{ user.name.substring(0, 2) }}
                                            </AvatarFallback>
                                        </Avatar>
                                    </TooltipTrigger>
                                    <TooltipContent>
                                        <p>欢迎新用户: {{ user.name }}</p>
                                    </TooltipContent>
                                </Tooltip>
                            </TooltipProvider>
                        </div>
                        <p class="text-[11px] text-muted-foreground">
                            <span class="text-green-500 font-bold">↑ 12%</span> 较上月
                        </p>
                    </div>
                </CardContent>
            </Card>

            <!-- 3. 总用户数 -->
            <Card class="hover:shadow-md transition-shadow">
                <CardHeader class="flex flex-row items-center justify-between space-y-0 pb-2">
                    <CardTitle class="text-xs font-medium text-muted-foreground uppercase tracking-wider">平台总用户
                    </CardTitle>
                    <Users class="h-4 w-4 text-purple-500" />
                </CardHeader>
                <CardContent>
                    <div class="text-2xl font-bold tracking-tight text-purple-600">
                        {{ summary.totalCount.toLocaleString() }}
                    </div>
                    <div class="mt-4">
                        <div class="flex justify-between text-[10px] mb-1.5">
                            <span class="text-muted-foreground">年度目标进度</span>
                            <span class="font-medium text-purple-700">75%</span>
                        </div>
                        <div class="h-1.5 w-full bg-purple-100 rounded-full overflow-hidden">
                            <!-- 这里的 w-[75%] 实际开发中可以根据比例动态计算 -->
                            <div class="h-full bg-purple-500 rounded-full transition-all duration-1000"
                                style="width: 75%" />
                        </div>
                    </div>
                </CardContent>
            </Card>
        </div>

        <Card class="flex-1 min-h-0 flex flex-col">
            <CardHeader class="shrink-0">
                <CardTitle>月销售额趋势</CardTitle>
                <CardDescription>近12个月每月月销售额</CardDescription>
            </CardHeader>
            <CardContent class="flex-1 min-h-0 p-0 relative">
                <v-chart class="h-full w-full" :option="salesTrendOption" autoresize />
            </CardContent>
            <CardFooter class="hidden"></CardFooter>
        </Card>
    </div>
</template>

<script setup lang="ts">
    import { ref, onMounted, computed } from 'vue'
    import axios from '@/utils/myAxios'
    import { Avatar, AvatarImage, AvatarFallback } from '@/components/ui/avatar'
    import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from '@/components/ui/tooltip'
    import { Activity, Users, UserPlus } from 'lucide-vue-next'
    import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle, } from "@/components/ui/card"
    import { use } from 'echarts/core'
    import { CanvasRenderer } from 'echarts/renderers'
    import { GridComponent, TooltipComponent } from 'echarts/components'
    import { BarChart, LineChart } from 'echarts/charts'
    import VChart from 'vue-echarts'
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

    interface MonthData {
        month: string
        value: number
    }

    interface SummaryData {
        newCount: number
        newUsers: UserSimple[]
        totalCount: number
        monthCounts: MonthData[]
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

    // 2. 注册组件
    use([CanvasRenderer, BarChart, LineChart, GridComponent, TooltipComponent])

    // 3. 新增月销售额趋势的配置
    const salesTrendOption = computed(() => {
        return {
            tooltip: {
                trigger: 'axis',
                backgroundColor: 'rgba(255, 255, 255, 0.9)',
                textStyle: { color: '#000' },
                // 格式化悬浮提示
                formatter: (params: any) => {
                    const data = params[0]
                    return `${data.name}<br/>销售额: <span style="font-weight:bold">￥${data.value.toLocaleString()}</span>`
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                top: '10%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                boundaryGap: false, // 坐标轴两端不留白，折线贴边
                data: summary.value.monthCounts.map(item => item.month),
                axisLine: { show: false },
                axisTick: { show: false },
                axisLabel: { color: '#888' }
            },
            yAxis: {
                type: 'value',
                splitLine: {
                    lineStyle: { type: 'dashed', color: '#eee' }
                },
                axisLabel: { color: '#888' }
            },
            series: [
                {
                    name: '销售额',
                    type: 'line',
                    data: summary.value.monthCounts.map(item => item.value),
                    smooth: true, // 设置为 true 则为平滑曲线，false 则为直线
                    showSymbol: false,
                    lineStyle: {
                        width: 3,
                        color: '#3b82f6'
                    },
                    // 渐变面积填充 (可选，让图表更好看)
                    areaStyle: {
                        color: {
                            type: 'linear',
                            x: 0, y: 0, x2: 0, y2: 1,
                            colorStops: [
                                { offset: 0, color: 'rgba(59, 130, 246, 0.2)' },
                                { offset: 1, color: 'rgba(59, 130, 246, 0)' }
                            ]
                        }
                    }
                }
            ]
        }
    })
</script>