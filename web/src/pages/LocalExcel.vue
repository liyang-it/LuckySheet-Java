<template>
    <div class="main-div">
        <div class="main-controls">
            <el-link style="margin-right: 20px;" @click="saveExcel()">保存Excel</el-link>
            <el-link style="margin-right: 5px;" @click="exportExcel()">导出Excel</el-link>
        </div>
        <div id="luckysheet" class="main-lucky" />
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onActivated } from "vue"
import { ElMessage } from 'element-plus'

// 引入LuckySheet默认数据
import excelDefaultData from '../data/LocalExcelDefautlData.js'

// IndexDB封装类库 https://localforage.github.io/localForage/#installation
import localforage from 'localforage'

const LOCAL_EXCEL_DATA = 'localExcel'

let option = {}

onMounted(async () => {
})

onActivated(async () => {
    window.document.title = '本地编辑'
    // 因为 luckysheet 挂载的是 windows全局变量，所以导致整个组件或者页面用的都是一个实例，每次切换回来当前页面都要重新初始化表格数据
    await initExcel()

})

async function initExcel() {
    // 判断缓存是否存在数据
    await localforage.getItem(LOCAL_EXCEL_DATA, function (err, value) {

        console.log('本地Excel缓存数据：', value);

        if (value == null || value == undefined) {

            // 缓存不存在，赋值默认数据
            option = excelDefaultData

            // 存入缓存
            localforage.setItem(LOCAL_EXCEL_DATA, JSON.stringify(excelDefaultData))
        } else {
            // 取缓存数据
            // 
            option = JSON.parse(value)
        }

        // 初始化
        luckysheet.create(option)
    });
}
/**
 * 保存Excel数据
 */
async function saveExcel() {

    // 获取 LuckySheet整个Excel 数据，可以把这个完整数据直接保存在后端数据库，直接使用 luckysheet.create 初始化
    const saveOption = luckysheet.toJson()

    // 覆盖 完整 option 数据保存
    option = saveOption

    console.info('保存数据：', option)

    // 存储进缓存
    await localforage.setItem(LOCAL_EXCEL_DATA, JSON.stringify(option))

    ElMessage({
        message: '导出成功！',
        type: 'success',
    })


}

/**
 * 导出Excel数据
 */
async function exportExcel() {
    console.info(luckysheet.getluckysheetfile())
}

</script>


<style></style>