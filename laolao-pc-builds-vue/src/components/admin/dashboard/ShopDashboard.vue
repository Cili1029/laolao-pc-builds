<template>
    <div class="flex flex-col w-full h-full px-3 py-3 space-y-6 overflow-hidden">
        <div class="grid gap-4 md:grid-cols-3">
            <!-- 当前商品数量 -->
            <Card class="hover:shadow-md transition-shadow">
                <CardHeader class="flex flex-row items-center justify-between space-y-0 pb-2">
                    <CardTitle class="text-sm font-medium">库存储备</CardTitle>
                    <Package class="h-4 w-4 text-blue-500" />
                </CardHeader>
                <CardContent>
                    <div class="flex flex-col space-y-1">
                        <div class="flex justify-between items-end">
                            <span class="text-xs text-muted-foreground">组件</span>
                            <span class="text-2xl font-bold">{{ summary.componentCount }}</span>
                        </div>
                        <div class="flex justify-between items-end">
                            <span class="text-xs text-muted-foreground">整机</span>
                            <span class="text-2xl font-bold">{{ summary.bundleCount }}</span>
                        </div>
                    </div>
                </CardContent>
            </Card>

            <!-- 销售额 -->
            <Card class="hover:shadow-md transition-shadow border-l-4 border-l-green-500">
                <CardHeader class="flex flex-row items-center justify-between space-y-0 pb-2">
                    <CardTitle class="text-sm font-medium">本月销售额</CardTitle>
                    <DollarSign class="h-4 w-4 text-green-500" />
                </CardHeader>
                <CardContent>
                    <div class="text-3xl font-bold">¥{{ summary.monthSalesAmount.toLocaleString() }}</div>
                    <p class="text-xs text-muted-foreground mt-2">
                        较上月同期 <span class="text-green-500 font-medium">↑ 12.5%</span>
                    </p>
                </CardContent>
            </Card>

            <!-- 交易情况 -->
            <Card class="hover:shadow-md transition-shadow">
                <CardHeader class="flex flex-row items-center justify-between space-y-0 pb-2">
                    <CardTitle class="text-sm font-medium">交易转化率</CardTitle>
                    <ShoppingCart class="h-4 w-4 text-purple-500" />
                </CardHeader>
                <CardContent>
                    <div class="flex items-baseline space-x-2">
                        <div class="text-2xl font-bold">{{ (conversionRate * 100).toFixed(1) }}%</div>
                        <div class="text-xs text-muted-foreground">({{ summary.completedOrderCount }}/{{
                            summary.orderCount }})</div>
                    </div>
                    <div class="mt-3 h-2 w-full bg-secondary rounded-full overflow-hidden">
                        <div class="h-full bg-purple-500 transition-all duration-500"
                            :style="{ width: `${conversionRate * 100}%` }" />
                    </div>
                </CardContent>
            </Card>
        </div>

        <div class="flex-1 min-h-0 flex space-x-6">
            <!-- 月销售额趋势 -->
            <Card class="flex-1 min-h-0 flex flex-col w-1/2">
                <CardHeader class="shrink-0">
                    <CardTitle>月销售额趋势</CardTitle>
                    <CardDescription>过去12个月的业绩走势</CardDescription>
                </CardHeader>
                <CardContent class="flex-1 min-h-0 p-0 relative">
                    <v-chart class="h-full w-full" :option="salesTrendOption" autoresize />
                </CardContent>
                <CardFooter class="hidden"></CardFooter>
            </Card>

            <!-- 商品销售排名 -->
            <Card class="flex-1 min-h-0 flex flex-col w-1/2">
                <CardHeader class="shrink-0">
                    <CardTitle>商品销售排名</CardTitle>
                    <CardDescription>本月销售表现最突出的前六件商品</CardDescription>
                </CardHeader>
                <CardContent class="flex-1 min-h-0 p-0 relative">
                    <v-chart v-if="summary.monthProducts.length > 0" class="h-full w-full" :option="chartOption" autoresize />
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
    import { Package, DollarSign, ShoppingCart, Ghost } from 'lucide-vue-next'
    import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle, } from "@/components/ui/card"
    import { use } from 'echarts/core'
    import { CanvasRenderer } from 'echarts/renderers'
    import { GridComponent, TooltipComponent } from 'echarts/components'
    import { BarChart, LineChart } from 'echarts/charts'
    import VChart from 'vue-echarts'

    // 模拟初始化
    onMounted(() => {
        fetchSummary()
    })

    const conversionRate = computed(() => {
        if (!summary.value.orderCount || summary.value.orderCount === 0) return 0
        return summary.value.completedOrderCount / summary.value.orderCount
    })

    interface MonthCount {
        month: string
        amount: number
    }

    interface HotProduct {
        name: string
        sales: number
    }

    interface SummaryData {
        componentCount: number
        bundleCount: number
        monthSalesAmount: number
        orderCount: number
        completedOrderCount: number
        monthAmount: MonthCount[]
        monthProducts: HotProduct[]
    }

    const summary = ref<SummaryData>({
        componentCount: 0,
        bundleCount: 0,
        monthSalesAmount: 0,
        orderCount: 0,
        completedOrderCount: 0,
        monthAmount: [],
        monthProducts: []
    })

    const fetchSummary = async () => {
        try {
            const res = await axios.get('/api/admin/dashboard/shop_summary')
            summary.value = res.data.data
        } catch (e) {
            console.error(e)
        }
    }

    // 2. 注册组件
    use([CanvasRenderer, BarChart, LineChart, GridComponent, TooltipComponent])

    // 3. 新增月销售额趋势
    const salesTrendOption = computed(() => {
        return {
            tooltip: {
                trigger: 'axis',
                backgroundColor: 'rgba(255, 255, 255, 0.9)',
                textStyle: { color: '#000' },
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
                boundaryGap: false,
                data: summary.value.monthAmount.map(item => item.month),
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
                    data: summary.value.monthAmount.map(item => item.amount),
                    smooth: true, // 设置为 true 则为平滑曲线，false 则为直线
                    showSymbol: false,
                    lineStyle: {
                        width: 3,
                        color: '#3b82f6'
                    },
                    // 渐变面积填充
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


    use([CanvasRenderer, BarChart, GridComponent, TooltipComponent])
    const chartOption = computed(() => {
        const data = [...summary.value.monthProducts].reverse()
        return {
            // 提示框
            tooltip: {
                trigger: 'axis',
                axisPointer: { type: 'shadow' },
                backgroundColor: 'rgba(255, 255, 255, 0.9)',
                textStyle: { color: '#000' }
            },
            // 布局调整
            grid: {
                left: '3%',
                right: '10%', // 给右侧留点空间显示数值
                bottom: '3%',
                top: '5%',
                containLabel: true // 自动计算边距，防止名字被遮挡
            },
            // X轴：数值轴
            xAxis: {
                type: 'value',
                splitLine: { show: false }, // 隐藏背景网格线
                axisTick: { show: false },
                axisLine: { show: false },
                axisLabel: { show: true }  // 隐藏底部刻度，让界面更清爽
            },
            // Y轴：类别轴
            yAxis: {
                type: 'category',
                data: data.map(item => item.name),
                axisLine: { show: false },
                axisTick: { show: false },
                axisLabel: {
                    color: '#666',
                    fontSize: 11,           // 稍微调小一点字体，更精致
                    width: 90,              // 1. 限制每一行文字的最大宽度（像素）
                    overflow: 'truncate',   // 2. 超过宽度自动换行 (或者用 'truncate' 显示省略号)
                    lineHeight: 14,         // 3. 调整行高，防止换行后行与行太挤
                    interval: 0             // 4. 强制显示所有标签
                }
            },
            series: [
                {
                    name: '销售量',
                    type: 'bar',
                    data: data.map(item => item.sales),
                    barWidth: '50%', // 柱子宽度
                    itemStyle: {
                        color: '#3b82f6', // Tailwind blue-500
                        borderRadius: [0, 4, 4, 0] // 右侧圆角
                    },
                    // 柱子右侧直接显示数字
                    label: {
                        show: true,
                        position: 'right',
                        color: '#333',
                        fontSize: 12
                    }
                }
            ]
        }
    })
</script>