<template>
  <div class='main' id="main">
    <div class="header">
      <h3 class="header-title">大屏看板示例</h3>
      <div class="header-info-r">日期：<span></span>{{ currentDate }}
        <ChangeLan class="header-info-r-lang" />
      </div>
    </div>
    <div class="content">
      <!-- 一组放三个 图表 -->
      <div class="content-group">
        <div class="content-group-chart borderAll" :style="{ 'width': computedWidth, 'height': computedHeight }" />
        <div class="content-group-chart borderAll" :style="{ 'width': computedWidth, 'height': computedHeight }" />
        <!-- 最后一个元素取消 margin-right -->
        <div class="content-group-chart borderAll"
          :style="{ 'width': computedWidth, 'height': computedHeight, 'margin-right': '0px' }" />
      </div>
      <div class="content-group">
        <div class="content-group-chart borderAll" :style="{ 'width': computedWidth, 'height': computedHeight }" />
        <div class="content-group-chart borderAll" :style="{ 'width': computedWidth, 'height': computedHeight }" />
        <!-- 最后一个元素取消 margin-right -->
        <div class="content-group-chart borderAll"
          :style="{ 'width': computedWidth, 'height': computedHeight, 'margin-right': '0px' }" />
      </div>
    </div>
    <div class="footer" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onActivated, computed } from "vue";
const computedWidth = ref('0px')
const computedHeight = ref('0px')

// 当前日期
const currentDate = computed(() => {
  const date = new Date()
  const yyyy = date.getFullYear()
  const month = date.getMonth() + 1
  const sky = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
  return yyyy + '-' + month + '-' + sky
})



// 动态计算单个容器宽高
async function computedSizeFun() {
  // 获取浏览器可视化宽度
  const clientWidth = document.getElementById('main').clientWidth
  console.info('当前屏幕可视化宽度：', clientWidth)

  // 获取浏览器可视化高度
  const clientHeight = document.getElementById('main').clientHeight
  console.info('当前屏幕可视化高度：', clientHeight)

  // 一组是三个容器,计算单个容器宽
  const singleWidth = (clientWidth / 3) + 'px'
  console.info('单个容器宽度：', singleWidth)

  // 一共两组，计算单个容器的高,需要去掉 margin-bottom等的px
  const singleHeight = ((clientHeight / 2) - 60) + 'px'
  console.info('单个容器高度：', singleHeight)


  computedWidth.value = singleWidth
  computedHeight.value = singleHeight
}

onMounted(async () => {
  // 等待计算容器大小计算完成
  await computedSizeFun()

  // 等待日期获取完成

  // 监听屏幕大小改变，重新计算容器大小
  window.onresize = async () => {
    await computedSizeFun()
  }
})
</script>

<style lang="scss" scoped>
.main {
  width: 100%;
  height: 100vh;
  position: relative;
  font-family: "Microsoft Yahei", Arial, sans-serif;
  background: rgb(40, 30, 47) url('@/assets/images/kanban-bg.png') 0 0 / 100% 100% no-repeat;
}

.header {
  position: relative;
  height: 72px;
  background: url('@/assets/images/kanban-header.png') 0 0 / 100% 100% no-repeat;
  overflow: hidden;
}

.header .header-title {
  line-height: 0px;
  text-align: center;
  font-size: 34px;
  font-weight: 400;
  color: #fff;
}

.header .header-info-r {
  position: absolute;
  top: 32px;
  font-size: 18px;
  color: #73aae5;
  right: 50px;
}

.header .header-info-r .header-info-r-lang {
  width: 30px;
  height: 30px;
  position: absolute;
  top: -5px;
  margin-left: 10px;
  cursor: pointer;
  display: inline-table;
}

.content {
  position: absolute;
  top: 72px;
  bottom: 22px;
  left: 0;
  right: 0;
  padding: 0px 10px;
}

.content .content-group {
  color: white;
  margin-bottom: 20px;
  display: flex;
}

.content .content-group .content-group-chart {
  float: left;
  margin-right: 20px;
}

.borderAll {
  border: 1px solid rgba(25, 186, 139, .17);
  padding: 0 0.2rem 0.4rem 0.15rem;
  background: rgba(255, 255, 255, .04) url('@/assets/images/line.png');
  background-size: 100% auto;
  position: relative;
  margin-bottom: 0.15rem;
  z-index: 10;
}

.borderAll:before,
.borderAll:after {
  position: absolute;
  width: 0.1rem;
  height: 0.1rem;
  content: "";
  border-top: 2px solid #02a6b5;
  top: 0;
}

.footer {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 28px;
  background: url('@/assets/images/kanban-footer.png') 0 0 / 100% 100% no-repeat;
}
</style>