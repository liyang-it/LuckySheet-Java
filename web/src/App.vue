<template>
  <el-config-provider namespace="ep">
    <BaseHeader />
    <!-- Vue3缓存组件，写法和Vue2不一样-->
    <router-view v-slot="{ Component }">
      <!-- 使用 keep-alive 缓存组件 -->
      <keep-alive>
        <component :is="Component" />
      </keep-alive>
    </router-view>

  </el-config-provider>
</template>

<style>
#app {
  text-align: center;
  color: var(--ep-text-color-primary);
}
</style>
