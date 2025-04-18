<template>
  <div class="banner-wrapper">
    <div class="banner-title-wrapper">
      <div class="title-line-wrapper" style="opacity: 1; transform: translate(0px, 0px);">
        <div class="title-line" style="transform: translateX(-64px);"></div>
      </div>
      <h1 class="" style="opacity: 1; transform: translate(0px, 0px);">Online CET</h1>
      <p style="opacity: 1; transform: translate(0px, 0px);">
        <span>基于SpringBoot的大学生英语四六级模拟考试系统</span>
      </p>
      <div class="button-wrapper">
        <a @click="$router.push({ name: 'exam-card' })">
          <a-button style="margin: 0 16px;">开始考试</a-button>
        </a>
      </div>
    </div>
    <div class="banner-image-wrapper" style="opacity: 1;">
      <a-carousel arrows autoplay>
        <div v-for="i in 5" :key="i">
          <img :src="`/home/cover${i}.jpg`" style="height: 324px"/>
        </div>
      </a-carousel>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Banner'
}
</script>

<style lang="less">
  @import "home";
</style>
