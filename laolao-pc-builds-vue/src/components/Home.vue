<template>
    <div class="h-full py-6 sm:py-8 lg:py-12">
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 sm:gap-8 lg:gap-12 max-w-6xl mx-auto px-4 sm:px-6">
            <!-- 配件卡片 -->
            <div
                class="group bg-white rounded-xl sm:rounded-2xl shadow-lg sm:shadow-xl p-6 sm:p-8 border border-gray-100 transform hover:-translate-y-2 sm:hover:-translate-y-3 transition-all duration-500 hover:shadow-xl sm:hover:shadow-2xl relative overflow-hidden">
                <!-- 装饰性背景元素 -->
                <div
                    class="absolute -top-6 -right-6 sm:-top-10 sm:-right-10 w-20 h-20 sm:w-32 sm:h-32 bg-blue-50 rounded-full opacity-60 group-hover:scale-110 transition-transform duration-500">
                </div>

                <div
                    class="w-16 h-16 sm:w-20 sm:h-20 bg-blue-500 rounded-xl sm:rounded-2xl flex items-center justify-center mb-4 sm:mb-6 shadow-md relative z-10">
                    <span class="icon-[material-symbols--shopping-cart-outline] text-3xl sm:text-4xl text-white"></span>
                </div>
                <h3 class="text-2xl sm:text-3xl font-bold mb-3 sm:mb-4 relative z-10">就缺这个了！</h3>
                <p class="text-gray-600 mb-4 sm:mb-6 leading-relaxed text-sm sm:text-base relative z-10">
                    精选CPU、显卡、主板等优质配件，正品保障，价格透明，一站式满足您的装机需求
                </p>
                <router-link to="/buy"
                    class="w-full bg-blue-500 hover:bg-blue-600 text-white font-semibold py-3 sm:py-4 rounded-lg sm:rounded-xl transition-all duration-300 block text-center shadow-md hover:shadow-lg transform hover:scale-[1.02] text-sm sm:text-base">
                    选购配件
                </router-link>
            </div>

            <!-- 论坛卡片 -->
            <div
                class="group bg-white rounded-xl sm:rounded-2xl shadow-lg sm:shadow-xl p-6 sm:p-8 border border-gray-100 transform hover:-translate-y-2 sm:hover:-translate-y-3 transition-all duration-500 hover:shadow-xl sm:hover:shadow-2xl relative overflow-hidden">
                <!-- 装饰性背景元素 -->
                <div
                    class="absolute -top-6 -right-6 sm:-top-10 sm:-right-10 w-20 h-20 sm:w-32 sm:h-32 bg-green-50 rounded-full opacity-60 group-hover:scale-110 transition-transform duration-500">
                </div>

                <div
                    class="w-16 h-16 sm:w-20 sm:h-20 bg-green-500 rounded-xl sm:rounded-2xl flex items-center justify-center mb-4 sm:mb-6 shadow-md relative z-10">
                    <span class="icon-[material-symbols--forum-outline-rounded] text-3xl sm:text-4xl text-white"></span>
                </div>
                <h3 class="text-2xl sm:text-3xl font-bold mb-3 sm:mb-4 relative z-10">带的动XXX不？</h3>
                <p class="text-gray-600 mb-4 sm:mb-6 leading-relaxed text-sm sm:text-base relative z-10">
                    与其他DIY玩家交流心得，分享装机经验，解决技术难题，找到最佳配置方案
                </p>
                <router-link to="/forum"
                    class="w-full bg-green-500 hover:bg-green-600 text-white font-semibold py-3 sm:py-4 rounded-lg sm:rounded-xl transition-all duration-300 block text-center shadow-md hover:shadow-lg transform hover:scale-[1.02] text-sm sm:text-base">
                    进入论坛
                </router-link>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">


</script>

<style scoped></style>