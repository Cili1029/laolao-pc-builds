<template>
    <div class="flex flex-col w-full h-full px-3 py-3 space-y-6 overflow-hidden bg-background">
        <!-- 顶部三卡片：数据概览 -->
        <div class="grid gap-4 md:grid-cols-3 shrink-0">
            <!-- 论坛数据总量 -->
            <Card class="hover:shadow-md transition-shadow border-t-4 border-t-blue-500">
                <CardHeader class="flex flex-row items-center justify-between space-y-0 pb-1">
                    <CardTitle class="text-xs font-bold uppercase text-muted-foreground tracking-wider">全站资源统计
                    </CardTitle>
                    <LayoutGrid class="h-4 w-4 text-blue-500" />
                </CardHeader>
                <CardContent>
                    <div class="flex flex-col space-y-2">
                        <div class="flex justify-between items-baseline border-b border-dashed pb-1">
                            <span class="text-[11px] text-muted-foreground">板块分类</span>
                            <span class="text-xl font-bold tracking-tight text-blue-600">{{ summary.categoryCount
                            }}</span>
                        </div>
                        <div class="flex justify-between items-baseline border-b border-dashed pb-1">
                            <span class="text-[11px] text-muted-foreground">帖子总数</span>
                            <span class="text-xl font-bold tracking-tight text-foreground">{{
                                summary.postCount.toLocaleString() }}</span>
                        </div>
                        <div class="flex justify-between items-baseline">
                            <span class="text-[11px] text-muted-foreground">评论总量</span>
                            <span class="text-xl font-bold tracking-tight text-foreground">{{
                                summary.commentCount.toLocaleString() }}</span>
                        </div>
                    </div>
                </CardContent>
            </Card>

            <!-- 本月活跃度 -->
            <Card class="hover:shadow-md transition-shadow border-t-4 border-t-green-500">
                <CardHeader class="flex flex-row items-center justify-between space-y-0 pb-1">
                    <CardTitle class="text-xs font-bold uppercase text-muted-foreground tracking-wider">本月社区活力
                    </CardTitle>
                    <PenSquare class="h-4 w-4 text-green-500" />
                </CardHeader>
                <CardContent>
                    <div class="flex flex-col space-y-3 pt-1">
                        <div class="flex justify-between items-end">
                            <div class="flex flex-col">
                                <span class="text-[10px] text-muted-foreground">新增帖子</span>
                                <span class="text-2xl font-black text-green-600">+{{ summary.monthPostCount }}</span>
                            </div>
                            <div class="flex flex-col items-end">
                                <span class="text-[10px] text-muted-foreground">活跃评论</span>
                                <span class="text-2xl font-black text-foreground">{{ summary.monthCommentCount }}</span>
                            </div>
                        </div>
                        <p class="text-[10px] text-muted-foreground bg-green-50 p-1 rounded">
                            🌱 较上月环比增长 <span class="text-green-600 font-bold">8.4%</span>
                        </p>
                    </div>
                </CardContent>
            </Card>

            <!-- 内容质量指标 -->
            <Card class="hover:shadow-md transition-shadow border-t-4 border-t-purple-500">
                <CardHeader class="flex flex-row items-center justify-between space-y-0 pb-1">
                    <CardTitle class="text-xs font-bold uppercase text-muted-foreground tracking-wider">互动深度指标
                    </CardTitle>
                    <Zap class="h-4 w-4 text-purple-500" />
                </CardHeader>
                <CardContent>
                    <div class="pt-1">
                        <div class="text-2xl font-black text-purple-600">
                            {{ interactionDepth }} <span class="text-xs font-normal text-muted-foreground">回复/贴</span>
                        </div>
                        <div class="mt-4 space-y-1">
                            <div class="flex justify-between text-[10px]">
                                <span class="text-muted-foreground">社区粘性评价</span>
                                <span class="font-bold text-purple-600">{{ interactionLevel }}</span>
                            </div>
                            <div class="h-1.5 w-full bg-purple-100 rounded-full overflow-hidden">
                                <div class="h-full bg-purple-500 transition-all"
                                    :style="{ width: interactionWidth + '%' }" />
                            </div>
                            <p class="text-[9px] text-muted-foreground mt-2 italic">“本月每篇帖子平均能引发 {{ interactionDepth }}
                                次深度讨论”</p>
                        </div>
                    </div>
                </CardContent>
            </Card>
        </div>

        <!-- 下方图表：趋势与排行 -->
        <div class="flex-1 min-h-0 flex space-x-6">
            <Card class="flex-1 min-h-0 flex flex-col w-1/2">
                <CardHeader class="shrink-0 py-3">
                    <CardTitle class="text-sm">帖子发布趋势</CardTitle>
                    <CardDescription class="text-[10px]">展示过去12个月的内容生产量走势</CardDescription>
                </CardHeader>
                <CardContent class="flex-1 min-h-0 p-0 relative">
                    <v-chart class="h-full w-full" :option="salesTrendOption" autoresize />
                </CardContent>
            </Card>

            <Card class="flex-1 min-h-0 flex flex-col w-1/2">
                <CardHeader class="shrink-0 py-3">
                    <CardTitle class="text-sm">板块活跃度排名</CardTitle>
                    <CardDescription class="text-[10px]">基于本月发帖量统计的热门板块分布</CardDescription>
                </CardHeader>
                <CardContent class="flex-1 min-h-0 p-0 relative">
                    <v-chart v-if="summary.hotCategories.length > 0" class="h-full w-full" :option="chartOption"
                        autoresize />
                    <div v-else class="flex flex-col h-full items-center justify-center gap-4 text-center">
                        <div class="rounded-full bg-muted/30 p-4">
                            <Ghost class="h-10 w-10 text-muted-foreground/60" />
                        </div>
                        <div class="space-y-1">
                            <p class="text-sm text-muted-foreground">
                                这个月没有任何新发布的帖子！
                            </p>
                        </div>
                    </div>
                </CardContent>
            </Card>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { ref, onMounted, computed } from 'vue'
    import axios from '@/utils/myAxios'
    import { LayoutGrid, PenSquare, Zap, Ghost } from 'lucide-vue-next'
    import { Card, CardContent, CardDescription, CardHeader, CardTitle, } from "@/components/ui/card"
    import { use } from 'echarts/core'
    import { CanvasRenderer } from 'echarts/renderers'
    import { GridComponent, TooltipComponent } from 'echarts/components'
    import { BarChart, LineChart } from 'echarts/charts'
    import VChart from 'vue-echarts'

    onMounted(() => { fetchSummary() })

    // 编的逻辑：互动深度
    const interactionDepth = computed(() => {
        if (!summary.value.monthPostCount) return '0'
        return (summary.value.monthCommentCount / summary.value.monthPostCount).toFixed(1)
    })

    const interactionLevel = computed(() => {
        const val = parseFloat(interactionDepth.value)
        if (val > 10) return '极高'
        if (val > 5) return '活跃'
        return '一般'
    })

    const interactionWidth = computed(() => {
        const val = parseFloat(interactionDepth.value)
        return Math.min(val * 10, 100) // 假设10条是满分
    })

    interface MonthCount {
        month: string
        value: number
    }

    interface hotCategory {
        name: string
        postCount: number
    }

    interface SummaryData {
        categoryCount: number
        postCount: number
        commentCount: number
        monthPostCount: number
        monthCommentCount: number
        allMonthPostCount: MonthCount[]
        hotCategories: hotCategory[]
    }

    const summary = ref<SummaryData>({
        categoryCount: 0,
        postCount: 0,
        commentCount: 0,
        monthPostCount: 0,
        monthCommentCount: 0,
        allMonthPostCount: [],
        hotCategories: []
    })

    const fetchSummary = async () => {
        try {
            const res = await axios.get('/api/admin/dashboard/forum_summary')
            summary.value = res.data.data
        } catch (e) { console.error(e) }
    }

    use([CanvasRenderer, BarChart, LineChart, GridComponent, TooltipComponent])

    // 折线图配置：优化单位和颜色
    const salesTrendOption = computed(() => {
        return {
            tooltip: {
                trigger: 'axis',
                backgroundColor: 'rgba(255, 255, 255, 0.9)',
                formatter: (params: any) => {
                    const data = params[0]
                    return `<div class="text-[12px] font-bold">${data.name}</div>
                            <div class="text-[12px] text-blue-500">发帖量: ${data.value} 篇</div>`
                }
            },
            grid: { left: '3%', right: '4%', bottom: '3%', top: '10%', containLabel: true },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: summary.value.allMonthPostCount.map(item => item.month),
                axisLine: { show: false },
                axisTick: { show: false },
                axisLabel: { color: '#999', fontSize: 10 }
            },
            yAxis: {
                type: 'value',
                splitLine: { lineStyle: { type: 'dashed', color: '#f0f0f0' } },
                axisLabel: { color: '#999', fontSize: 10 }
            },
            series: [{
                name: '发帖量',
                type: 'line',
                data: summary.value.allMonthPostCount.map(item => item.value),
                smooth: true,
                showSymbol: false,
                lineStyle: { width: 3, color: '#3b82f6' },
                areaStyle: {
                    color: {
                        type: 'linear', x: 0, y: 0, x2: 0, y2: 1,
                        colorStops: [{ offset: 0, color: 'rgba(59, 130, 246, 0.15)' }, { offset: 1, color: 'rgba(59, 130, 246, 0)' }]
                    }
                }
            }]
        }
    })

    // 柱状图配置：优化换行显示和语义
    const chartOption = computed(() => {
        const data = [...summary.value.hotCategories].reverse()
        return {
            tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
            grid: { left: '3%', right: '12%', bottom: '3%', top: '5%', containLabel: true },
            xAxis: { type: 'value', splitLine: { show: false }, axisLabel: { show: false } },
            yAxis: {
                type: 'category',
                data: data.map(item => item.name),
                axisLine: { show: false },
                axisTick: { show: false },
                axisLabel: {
                    color: '#666',
                    fontSize: 10,
                    width: 80,
                    overflow: 'truncate',
                    interval: 0
                }
            },
            series: [{
                name: '发帖数',
                type: 'bar',
                data: data.map(item => item.postCount),
                barWidth: '40%',
                itemStyle: {
                    color: '#8b5cf6', // 紫色代表活跃
                    borderRadius: [0, 4, 4, 0]
                },
                label: { show: true, position: 'right', fontSize: 10, formatter: '{c} 篇' }
            }]
        }
    })
</script>