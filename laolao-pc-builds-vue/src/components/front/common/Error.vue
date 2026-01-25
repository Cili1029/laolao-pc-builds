<template>
    <div class="h-full w-full flex items-center justify-center bg-slate-50/50 p-4">
        <div class="max-w-md w-full bg-white rounded-2xl shadow-sm border border-slate-200 p-8 text-center space-y-6">

            <div class="flex justify-center">
                <div class="bg-red-50 p-4 rounded-full">
                    <AlertCircle class="w-16 h-16 text-red-500" />
                </div>
            </div>

            <div class="space-y-2">
                <h2 class="text-2xl font-bold text-slate-900">未知错误！</h2>
                <p class="text-slate-500">不晓得发生了什么，请联系管理员！</p>
            </div>

            <div class="bg-slate-50 rounded-xl py-3 px-4 flex items-center justify-center gap-2">
                <Loader2 class="w-4 h-4 animate-spin text-slate-400" />
                <span class="text-sm font-medium text-slate-600">
                    将在 <span class="text-orange-500 font-bold tabular-nums">{{ timeLeft }}</span> 秒后自动跳转主页
                </span>
            </div>

            <div class="flex flex-col gap-3 pt-2">
                <Button variant="default" class="w-full h-11 text-sm font-semibold shadow-sm" @click="jumpToHome()">
                    <Home class="mr-2 h-4 w-4" /> 返回主页
                </Button>

                <div class="grid grid-cols-2 gap-3">
                    <Button variant="outline" class="h-11 border-slate-200 hover:bg-slate-50"
                        @click="router.push('/buy/0')">
                        <ShoppingBag class="mr-2 h-4 w-4" /> 去商城
                    </Button>
                    <Button variant="outline" class="h-11 border-slate-200 hover:bg-slate-50"
                        @click="router.push('/forum/-1')">
                        <MessageSquare class="mr-2 h-4 w-4" /> 去论坛
                    </Button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { ref, onMounted, onUnmounted } from "vue";
    import { useRouter } from "vue-router";
    import { Button } from "@/components/ui/button";
    import { AlertCircle, Home, ShoppingBag, MessageSquare, Loader2 } from "lucide-vue-next";

    const router = useRouter();
    const timeLeft = ref(5);
    let timer: any = null;

    const jumpToHome = () => {
        if (timer) {
            clearInterval(timer);
            timer = null;
        }
        router.push('/home');
    };

    onMounted(() => {
        timer = setInterval(() => {
            timeLeft.value--;
            if (timeLeft.value <= 0) {
                jumpToHome();
            }
        }, 1000);
    });

    onUnmounted(() => {
        if (timer) {
            clearInterval(timer);
        }
    });
</script>