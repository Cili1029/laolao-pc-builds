<template>
  <div>

  </div>
</template>

<script setup lang="ts">
  // 后端返回的原始字符串
  const imageStr = '["https://laolao123.oss-cn-hangzhou.aliyuncs.com/PostImage/57f5cc6d-f258-4930-adc3-f6af747d30f6.png", "https://laolao123.oss-cn-hangzhou.aliyuncs.com/PostImage/6b84dd48-54cd-40af-b09a-5baf31a8f07d.jpg"]';

  let images: string[];

  // 第二步：解析字符串并赋值给 images
  images = JSON.parse(imageStr);

  // 此时 images 就是拆分后的数组
  console.log(images[0]); // 输出第一张图片的 URL
  console.log(images[1]); // 输出第二张图片的 URL
</script>