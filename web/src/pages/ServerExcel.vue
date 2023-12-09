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
import { ref, onMounted, onActivated } from "vue";
import { ElMessage } from 'element-plus'
import axios from "axios";
// 表格唯一标识符
const gridKey = 'defaultGridKey'
// API接口请求地址
const baseApiUrl = import.meta.env.VITE_APP_BASE_API
// Excel加载接口地址
const excelLoadUrl = `${baseApiUrl}/excelWorkSheet/loadUrl`
// Excel导出接口地址
const exportExcelUrl = `${baseApiUrl}/excelWorkSheet/exportExcel/${gridKey}`
// Excel保存接口地址
const saveExcelUrl = `${baseApiUrl}/excelWorkSheet/save`

/**
 * 初始化表格
 */
async function initExcel() {
    // 使用LuckySheet配置模式创建
    const options = {
        container: 'luckysheet', // 设定DOM容器的id
        gridKey: gridKey, // 表格唯一标识符
        title: '默认表格', // 设定表格名称
        lang: 'zh', // 设定表格语言
        loadUrl: excelLoadUrl, // LuckySheet数据加载地址
        allowEdit: true, // 是否可以编辑
        showtoolbarConfig: { // 顶部菜单
            undoRedo: true, //撤销重做，注意撤消重做是两个按钮，由这一个配置决定显示还是隐藏
            paintFormat: true, //格式刷
            currencyFormat: true, //货币格式
            percentageFormat: true, //百分比格式
            numberDecrease: true, // '减少小数位数'
            numberIncrease: true, // '增加小数位数
            moreFormats: true, // '更多格式'
            font: true, // '字体'
            fontSize: true, // '字号大小'
            bold: true, // '粗体 (Ctrl+B)'
            italic: true, // '斜体 (Ctrl+I)'
            strikethrough: true, // '删除线 (Alt+Shift+5)'
            underline: true, // '下划线 (Alt+Shift+6)'
            textColor: true, // '文本颜色'
            fillColor: true, // '单元格颜色'
            border: true, // '边框'
            mergeCell: true, // '合并单元格'
            horizontalAlignMode: true, // '水平对齐方式'
            verticalAlignMode: true, // '垂直对齐方式'
            textWrapMode: true, // '换行方式'
            textRotateMode: true, // '文本旋转方式'
            image: true, // '插入图片'
            link: true, // '插入链接'
            chart: true, // '图表'（图标隐藏，但是如果配置了chart插件，右击仍然可以新建图表）
            postil: true, //'批注'
            pivotTable: true,  //'数据透视表'
            function: true, // '公式'
            frozenMode: true, // '冻结方式'
            sortAndFilter: true, // '排序和筛选'
            conditionalFormat: true, // '条件格式'
            dataVerification: true, // '数据验证'
            splitColumn: true, // '分列'
            screenshot: true, // '截图'
            findAndReplace: true, // '查找替换'
            protection: true, // '工作表保护'
            print: true, // '打印'
        },
        cellRightClickConfig: {  // 单元格右键菜单
            copy: true, // 复制
            copyAs: true, // 复制为
            paste: true, // 粘贴
            insertRow: true, // 插入行
            insertColumn: true, // 插入列
            deleteRow: true, // 删除选中行
            deleteColumn: true, // 删除选中列
            deleteCell: true, // 删除单元格
            hideRow: true, // 隐藏选中行和显示选中行
            hideColumn: true, // 隐藏选中列和显示选中列
            rowHeight: true, // 行高
            columnWidth: true, // 列宽
            clear: true, // 清除内容
            matrix: true, // 矩阵操作选区
            sort: true, // 排序选区
            filter: true, // 筛选选区
            chart: true, // 图表生成
            image: true, // 插入图片
            link: true, // 插入链接
            data: true, // 数据验证
            cellFormat: true // 设置单元格格式
        },
        forceCalculation: true, // 默认情况下，为提高加载性能，表格初始化的时候，含有公式的单元格会默认直接取得v和m作为数据结果，而不做实时计算,需要开启，不然会导致公式异常
        sheetFormulaBar: true,
        showsheetbar: true // 显示底部Sheet菜单
    }

    // 初始化表格
    luckysheet.create(options)
}

/**
 * APi接口方式保存数据
 */
async function saveExcel() {
    const toJson = JSON.stringify(luckysheet.toJson())

    const params = {
        'gridKey': gridKey,
        'data': toJson
    }

    axios.post(saveExcelUrl, params).then(() => {
        ElMessage({
            message: '保存成功！',
            type: 'success',
        })
    })
}

/**
 * APi接口方式导出数据
 */
async function exportExcel() {

    await axios.get(exportExcelUrl, { responseType: "blob" })
        .then((res) => {
            console.info('导出Excel请求：', res)
            // 创建一个临时的url，参数为blob对象
            let url = window.URL.createObjectURL(res.data);
            let a = document.createElement("a");
            a.href = url;
            a.download = new Date().getTime() + ".xlsx";
            a.click();
            // 释放这个临时的对象url
            window.URL.revokeObjectURL(url);
            ElMessage({
                message: '导出成功！',
                type: 'success',
            })
        })
        .catch((err) => { });
}

onMounted(() => {

})
onActivated(async () => {
    window.document.title = '服务编辑'
    // 因为 luckysheet 挂载的是 windows全局变量，所以导致整个组件或者页面用的都是一个实例，每次切换回来当前页面都要重新初始化表格数据
    await initExcel()
})

</script>