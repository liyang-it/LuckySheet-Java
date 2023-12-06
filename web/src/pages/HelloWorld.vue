<template>
  <div class="main-div">
    <p><span>本地编辑</span>不需要启动后端服务，数据存储在浏览器缓存中</p>
    <p><span>共享编辑</span>需要启动后端服务，数据存储在数据库中</p>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted, onActivated } from "vue";
onMounted(() => {

})
onActivated(() => {
  window.document.title = 'Hello Word!'
})
</script>
<style scoped>
span {
  font-size: 20px;
  margin: 10px;
  text-decoration: none;
  white-space: nowrap;
  color: #121314;
  position: relative;
}

span:hover::after {
  top: 0%;
}

span:after {
  content: "";
  position: absolute;
  z-index: -1;
  top: 66%;
  left: -0.1em;
  right: -0.1em;
  bottom: 0;
  -webkit-transition: top .2s cubic-bezier(0, .8, .13, 1);
  transition: top .2s cubic-bezier(0, .8, .13, 1);
  background-color: rgba(50, 184, 185, .5);
}
</style>
