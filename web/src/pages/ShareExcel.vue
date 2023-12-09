<template>
    <div class="main-div">
        <div class="main-controls">
            <el-link style="margin-right: 15px;" @click="exportExcel()">导出Excel</el-link>
            <el-popover placement="bottom" :width="400" trigger="click">
                <template #reference>
                    <el-link> 查看当前表格协同用户({{ joinUserList.length }})</el-link>
                </template>
                <li v-for="item in joinUserList" :key="item.uid">
                    {{ item.uid }}
                    <span style="margin-left: 15px;font-size: 13px;color: #a39e9e;">{{ item.time }}</span>
                </li>
            </el-popover>

        </div>
        <div id="luckysheet" class="main-lucky" />
    </div>
</template>
<script setup lang="ts">
import { ref, onMounted, onActivated } from "vue";
import axios from "axios";
// 表格唯一标识符
const gridKey = 'defaultGridKey'
// API接口请求地址
const baseApiUrl = import.meta.env.VITE_APP_BASE_API

// SOCKET接口请求地址
const baseSocketUrl = import.meta.env.VITE_APP_WEBSOCKET_URL
// Excel加载接口地址
const excelLoadUrl = `${baseApiUrl}/excelWorkSheet/loadUrl`
// Excel导出接口地址
const exportExcelUrl = `${baseApiUrl}/excelWorkSheet/exportExcel/${gridKey}`

// 初始化当前连接人
const date = new Date()
const _user = date.getTime() + '_aoe'

// LuckySheet 组件实时编辑Socket地址
const luckySheetSocketUrl = `${baseSocketUrl}?u=${_user}&ly=1`

// 加入实时编辑用户组Socket地址
const joinUserSocketUrl = `${baseSocketUrl}?g=${gridKey}&u=${_user}&ly=2`

// 当前表格编辑用户组列表
const joinUserList = ref([])

/**
 * 初始化 实时编辑用户组Socket
 */
async function initJoinUserSocket() {

    // 创建 WebSocket加入Excel表格协同编辑

    // Create WebSocket connection.
    const socket = new WebSocket(joinUserSocketUrl);


    // 连接成功后的回调函数
    socket.addEventListener("open", function () {

        // socket.send(JSON.stringify({ "excel_type": 1, "excel_cmd": gridKey }));
        console.info('[表格群组] WebSocket服务连接成功')

        setInterval(() => {
            // 每隔60秒发送一次消息，防止被异常断开
            socket.send('1');
        }, 600000);


    });

    // 监听接收消息事件,主要监听服务端的用户列表变动消息
    socket.addEventListener("message", function (event) {
        const data = JSON.parse(event.data)
        if (data.code === 2502) {
            joinUserList.value = data.data
        }
        console.log("[表格群组] 接收到消息 ", data);
    });

    // 连接关闭后的回调函数
    socket.onclose = function () {

        console.log("[表格群组] WebSocket服务关闭.");

    };

    // 连接失败后的回调函数
    socket.addEventListener("error", function (event) {
        console.log("加入 [表格群组] 异常 : ", event);
    });
}

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
        allowUpdate: true, //LuckySheet数据允许更新, 如果需要测试手动保存，请关闭这个选项
        updateUrl: luckySheetSocketUrl, // LuckySheet数据更新地址
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
        }).catch((err) => { });
}

onMounted(async () => {


})
onActivated(async () => {
    window.document.title = '共享编辑'
    // 因为 luckysheet 挂载的是 windows全局变量，所以导致整个组件或者页面用的都是一个实例，每次切换回来当前页面都要重新初始化表格数据
    await initJoinUserSocket()
    await initExcel()
})


</script>