<template>
    <div class="grid grid-cols-1 pt-10 md:grid-cols-3 gap-8 max-w-5xl mx-auto">
        <!-- 配件 -->
        <div class="bg-white rounded-xl shadow-lg p-8 border-2 border-accent relative transform hover:-translate-y-2 transition-transform duration-300 h-fit">
            <div class="w-16 h-16 bg-blue-100 rounded-xl flex items-center justify-center mb-6">
                <span class="icon-[material-symbols--shopping-cart-outline] text-4xl"></span>
            </div>
            <h3 class="text-2xl font-bold text-gray-800 mb-4">就缺这个了！</h3>
            <p class="text-gray-600 mb-6">精选CPU、显卡、主板等优质配件，正品保障，价格透明</p>
            <router-link to="/buy" class="w-full bg-gray-100 hover:bg-gray-200 text-gray-800 font-medium py-3 rounded-lg transition-colors block text-center">
                选购配件
            </router-link>
        </div>

        <!-- 装机 -->
        <div
            class="bg-white rounded-xl shadow-lg p-8 border-2 border-accent relative transform hover:-translate-y-2 transition-transform duration-300 h-fit">

            <div class="w-16 h-16 bg-orange-100 rounded-xl flex items-center justify-center mb-6">
                <span class="icon-[material-symbols--computer-outline] text-4xl"></span>
            </div>
            <h3 class="text-2xl font-bold text-gray-800 mb-4">该怎么配呢？</h3>
            <p class="text-gray-600 mb-6">智能兼容性检查，一键生成专属配置方案，小白也能轻松装机</p>
            <button class="w-full bg-gray-100 hover:bg-gray-200 text-gray-800 font-medium py-3 rounded-lg transition-colors">
                开始装机
            </button>
        </div>

        <!-- 论坛 -->
        <div
            class="bg-white rounded-xl shadow-lg p-8 border-2 border-accent relative transform hover:-translate-y-2 transition-transform duration-300 h-fit">
            <div class="w-16 h-16 bg-green-100 rounded-xl flex items-center justify-center mb-6">
                <span class="icon-[material-symbols--forum-outline-rounded] text-4xl"></span>
            </div>
            <h3 class="text-2xl font-bold text-gray-800 mb-4">带的动XXX不？</h3>
            <p class="text-gray-600 mb-6">与其他DIY玩家一起吹牛，分享装机经验，解决技术难题</p>
            <router-link to="/forum" class="w-full bg-gray-100 hover:bg-gray-200 text-gray-800 font-medium py-3 rounded-lg transition-colors block text-center">
                进入论坛
            </router-link>
        </div>
    </div>
</template>

<script setup lang="ts">


</script>

<style scoped></style>