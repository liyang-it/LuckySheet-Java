<template>
    <div class="main-div">
        我是共享编辑Excel
    </div>
</template>
<script setup lang="ts">
import { ref, onMounted, onActivated } from "vue";
onMounted(() => {
})
onActivated(() => {
    window.document.title = '共享编辑'
})

</script>