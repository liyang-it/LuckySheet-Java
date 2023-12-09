<!-- 页面顶部组件 -->
<script lang="ts" setup>
import { toggleDark } from "~/composables";
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

/**
 * 
 * @param url 跳转页面
 * @param type 1 内部页面 2 外部链接
 */
async function toLink(url: any, type: any) {
  if (type == 1) {
    router.push({ path: url })
  } else {
    window.open(url)
  }

}

onMounted(() => {
  // 页面加载完成钩子
})

</script>

<template>
  <el-menu class="el-menu-demo tom-menu" mode="horizontal">
    <el-menu-item index="1" @click="toLink('/', 1)">首页</el-menu-item>
    <el-menu-item index="2" @click="toLink('/local', 1)">Excel本地编辑</el-menu-item>
    <el-menu-item index="3" @click="toLink('/server', 1)">Excel服务编辑</el-menu-item>
    <el-menu-item index="4" @click="toLink('/share', 1)">Excel共享编辑</el-menu-item>
    <el-menu-item index="5" @click="toLink('/chart', 1)">Echart简单示例</el-menu-item>
    <el-menu-item index="6" @click="toLink('https://github.com/liyang-it/LuckySheet-Java', 2)">GitHub</el-menu-item>
    <el-menu-item h="full" @click="toggleDark()">
      <button class="border-none w-full bg-transparent cursor-pointer" style="height: var(--ep-menu-item-height)">
        <i inline-flex i="dark:ep-moon ep-sunny" />
      </button>
    </el-menu-item>
  </el-menu>
</template>
