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
            option = JSON.parse(value)
        }

        // 初始化
        luckysheet.create(option)
    });

})

onActivated(() => {
    window.document.title = '本地编辑'
})

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
        message: 'success',
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


<style>
.main-lucky {
    margin: 0px;
    padding: 0px;
    width: 100%;
    height: 100%;
    margin-top: 30px;
}

.main-controls {
    margin-top: 2px;
    height: 30px;
    position: absolute;
    right: 22px;
    text-align: right;
    width: 500px;
    /* 防止文字换行 */
    white-space: nowrap;
    /* 超出部分隐藏 */
    overflow: hidden;
    /* 显示省略号来表示被截断的文本 */
    text-overflow: ellipsis;
}
</style>