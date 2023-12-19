<template>
  <el-config-provider namespace="ep">
    <BaseHeader v-if="!HiddenMenu" />
    <!-- Vue3缓存组件，写法和Vue2不一样-->
    <router-view v-slot="{ Component }">
      <!-- 使用 keep-alive 缓存组件 -->
      <keep-alive>
        <component :is="Component" />
      </keep-alive>
    </router-view>

  </el-config-provider>
</template>
<script setup lang="ts">
import { computed, onMounted } from "vue";
import { useRouter, useRoute } from 'vue-router'
const router = useRouter()
const route = useRoute()

const currentPath = window.location.href.substring(window.location.href.indexOf('#') + 1)


onMounted(() => {
  console.info('当前Path: ', currentPath)
})

// 不需要显示顶部菜单的页面, 这里只是简单的用 v-if显示隐藏顶部菜单，如果是管理后台是还需要 router路由守卫控制页面
const SpecialPages = [
  '/screen'
]

const HiddenMenu = computed(() => {
  return SpecialPages.indexOf(currentPath) != -1
})

</script>
<style>
#app {
  /* text-align: center; */
  color: var(--ep-text-color-primary);
}
</style>
