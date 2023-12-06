/**
 * LocalExcel默认数据，存储整个 LuckySheet配置 option完整数据
 */
const excelDefaultData = {
    container: 'luckysheet', // 设定DOM容器的id，指定渲染的元素ID
    gridKey: 'luckysheet', // 表格唯一标识符
    title: '本地Excel', // 设定表格名称
    lang: 'zh', // 设定表格语言
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
    showsheetbar: true, // 显示底部Sheet菜单
    data: [ // Sheet数据 https://mengshukeji.gitee.io/LuckysheetDocs/zh/guide/sheet.html#%E5%88%9D%E5%A7%8B%E5%8C%96%E9%85%8D%E7%BD%AE
        {
            "name": "我是Shee1嘿嘿", //工作表名称
            "color": "", //工作表颜色
            "index": 0, //工作表索引
            "status": 1, //激活状态
            "order": 0, //工作表的下标
            "hide": 0,//是否隐藏
            "row": 36, //行数
            "column": 30, //列数
            "defaultRowHeight": 19, //自定义行高
            "defaultColWidth": 73, //自定义列宽
            "celldata": [
                {
                    "r": 0,
                    "c": 0,
                    "v": {
                        "ct": {
                            "t": "g",
                            "fa": "General"
                        },
                        "v": "表头一",
                        "bl": 1,
                        "ht": "0",
                        "m": "表头一"
                    }
                },
                {
                    "r": 0,
                    "c": 1,
                    "v": {
                        "ct": {
                            "t": "g",
                            "fa": "General"
                        },
                        "v": "表头2",
                        "bl": 1,
                        "ht": "0",
                        "m": "表头2"
                    }
                },
                {
                    "r": 0,
                    "c": 2,
                    "v": {
                        "ct": {
                            "t": "g",
                            "fa": "General"
                        },
                        "v": "表头3",
                        "bl": 1,
                        "ht": "0",
                        "m": "表头3"
                    }
                },
                {
                    "r": 0,
                    "c": 3,
                    "v": {
                        "ct": {
                            "t": "g",
                            "fa": "General"
                        },
                        "v": "表头4",
                        "bl": 1,
                        "ht": "0",
                        "m": "表头4"
                    }
                }
            ], //初始化使用的单元格数据
            "config": {
                "merge": {}, //合并单元格
                "rowlen": {}, //表格行高
                "columnlen": {
                    0: 200, // 对应列下标
                    1: 200,
                    2: 200,
                    3: 200
                }, //表格列宽
                "rowhidden": {}, //隐藏行
                "colhidden": {}, //隐藏列
                "borderInfo": {}, //边框
                "authority": {}, //工作表保护

            },
            "scrollLeft": 0, //左右滚动条位置
            "scrollTop": 0, //上下滚动条位置
            "luckysheet_select_save": [], //选中的区域
            "calcChain": [],//公式链
            "isPivotTable": false,//是否数据透视表
            "pivotTable": {},//数据透视表设置
            "filter_select": {},//筛选范围
            "filter": null,//筛选配置
            "luckysheet_alternateformat_save": [], //交替颜色
            "luckysheet_alternateformat_save_modelCustom": [], //自定义交替颜色	
            "luckysheet_conditionformat_save": {},//条件格式
            "frozen": {}, //冻结行列配置
            "chart": [], //图表配置
            "zoomRatio": 1, // 缩放比例
            "image": [], //图片
            "showGridLines": 1, //是否显示网格线
            "dataVerification": {} //数据验证配置
        },
        {
            "name": "Sheet2",
            "color": "",
            "index": 1,
            "status": 0,
            "order": 1,
            "celldata": [],
            "config": {}
        },
        {
            "name": "Sheet3",
            "color": "",
            "index": 2,
            "status": 0,
            "order": 2,
            "celldata": [],
            "config": {},
        }
    ]
}

export default excelDefaultData;