<script setup lang="ts">
    import type {
        ChartConfig,
    } from "@/components/ui/chart"

    import { Orientation } from "@unovis/ts"
    import { VisAxis, VisGroupedBar, VisXYContainer } from "@unovis/vue"
    import { TrendingUp } from "lucide-vue-next"
    import {
        Card,
        CardContent,
        CardDescription,
        CardFooter,
        CardHeader,
        CardTitle,
    } from "@/components/ui/card"
    import {
        ChartContainer,
        ChartCrosshair,
        ChartTooltip,
        ChartTooltipContent,
        componentToString,
    } from "@/components/ui/chart"

    const description = "A line chart"

    const chartData = [
        { date: new Date("2024-01-01"), desktop: 186 },
        { date: new Date("2024-02-01"), desktop: 305 },
        { date: new Date("2024-03-01"), desktop: 237 },
        { date: new Date("2024-04-01"), desktop: 73 },
        { date: new Date("2024-05-01"), desktop: 209 },
        { date: new Date("2024-06-01"), desktop: 214 },
    ]

    type Data = typeof chartData[number]

    const chartConfig = {
        desktop: {
            label: "Desktop",
            color: "var(--chart-1)",
        },
    } satisfies ChartConfig
</script>

<template>
    <Card class="flex-1 min-h-0 flex flex-col w-1/2">
        <CardHeader class="shrink-0">
            <CardTitle>商品销售排名</CardTitle>
            <CardDescription>本月商品前五</CardDescription>
        </CardHeader>
        <CardContent class="flex-1 min-h-0 p-0 relative">
            <div class="absolute inset-0 px-6">
                <ChartContainer :config="chartConfig" style="aspect-ratio: auto;" class="h-full w-full">
                    <VisXYContainer :data="chartData">
                        <VisGroupedBar :x="(d: Data) => d.date" :y="(d: Data) => d.desktop"
                            :color="chartConfig.desktop.color" :rounded-corners="5"
                            :orientation="Orientation.Horizontal" />
                        <VisAxis type="y" :tick-line="false" :domain-line="false" :grid-line="false" :num-ticks="6"
                            :tick-format="(d: number) => {
                                const date = new Date(d)
                                return date.toLocaleDateString('en-US', {
                                    month: 'short',
                                })
                            }" :tick-values="chartData.map(d => d.date)" />
                        <ChartTooltip />
                        <ChartCrosshair
                            :template="componentToString(chartConfig, ChartTooltipContent, { hideLabel: true })"
                            color="#0000" />
                    </VisXYContainer>
                </ChartContainer>
            </div>
        </CardContent>
        <CardFooter class="hidden"></CardFooter>
    </Card>
</template>
