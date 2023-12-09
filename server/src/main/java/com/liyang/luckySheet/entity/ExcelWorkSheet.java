package com.liyang.luckySheet.entity;

/**
 * <h2>协同Excel - Excel表格 shee工作表数据 ,数据都是用json字符串存储，因为动态字段太多了，一个一个维护很麻烦</h2>
 * <p>
 * 官网参考：<a href="https://mengshukeji.gitee.io/LuckysheetDocs/zh/guide/sheet.html#%E5%88%9D%E5%A7%8B%E5%8C%96%E9%85%8D%E7%BD%AE">工作表配置</a><br>
 * 工作表dataJson参考：
 * <pre>{@code
 * 	[ // shee工作表数据,一个对象代表一个 sheet表
 *   {
 *       "name": "shee1", //工作表名称
 *       "color": "red", //工作表颜色
 *       "index": 0, //工作表索引
 *       "status": 1, //激活状态
 *       "order": 0, //工作表的下标
 *       "hide": 0,//是否隐藏
 *       "row": 36, //行数
 *       "column": 26, //列数
 *       "defaultRowHeight": 19, //自定义行高
 *       "defaultColWidth": 73, //自定义列宽
 *       "celldata": [ // 单元格数据
 *           {
 *               "r": 0, // 行下标
 *               "c": 0, // 列下标
 *               "v": {
 *                   "m": "损益/Profit and loss",
 *                   "ct": {
 *                       "fa": "General",
 *                       "t": "g"
 *                   },
 *                   "v": "损益/Profit and loss",
 *                   "bl": 1,
 *                   "ht": "0"
 *               }
 *           },
 *           {
 *               "r": 0,
 *               "c": 1,
 *               "v": {
 *                   "m": "本年累计/Current year",
 *                   "ct": {
 *                       "fa": "General",
 *                       "t": "g"
 *                   },
 *                   "v": "本年累计/Current year",
 *                   "bl": 1,
 *                   "ht": "0"
 *               }
 *           },
 *           {
 *               "r": 0,
 *               "c": 2,
 *               "v": {
 *                   "m": "本年目标/Forecast",
 *                   "ct": {
 *                       "fa": "General",
 *                       "t": "g"
 *                   },
 *                   "v": "本年目标/Forecast",
 *                   "bl": 1,
 *                   "ht": "0"
 *               }
 *           },
 *           {
 *               "r": 0,
 *               "c": 3,
 *               "v": {
 *                   "m": "差异率/Difference",
 *                   "ct": {
 *                       "fa": "General",
 *                       "t": "g"
 *                   },
 *                   "v": "差异率/Difference",
 *                   "bl": 1,
 *                   "ht": "0"
 *               }
 *           }
 *       ], //初始化使用的单元格数据 - 这个是shee工作表的数据
 *       "config": {
 *           "merge": {}, //合并单元格
 *           "rowlen": {}, //表格行高
 *           "columnlen": {
 *               "0": 300,
 *               "1": 300,
 *               "2": 300,
 *               "3": 300
 *           }, //表格列宽, 列下标
 *           "rowhidden": {}, //隐藏行
 *           "colhidden": {}, //隐藏列
 *           "borderInfo": {}, //边框
 *           "authority": {}, //工作表保护
 *       },
 *       "scrollLeft": 0, //左右滚动条位置
 *       "scrollTop": 315, //上下滚动条位置
 *       "luckysheet_select_save": [], //选中的区域
 *       "calcChain": [],//公式链
 *       "isPivotTable": false,//是否数据透视表
 *       "pivotTable": {},//数据透视表设置
 *       "filter_select": {},//筛选范围
 *       "filter": null,//筛选配置
 *       "luckysheet_alternateformat_save": [], //交替颜色
 *       "luckysheet_alternateformat_save_modelCustom": [], //自定义交替颜色
 *       "luckysheet_conditionformat_save": {},//条件格式
 *       "frozen": {}, //冻结行列配置
 *       "chart": [], //图表配置
 *       "zoomRatio": 1, // 缩放比例
 *       "image": [], //图片
 *       "showGridLines": 1, //是否显示网格线
 *       "dataVerification": {} //数据验证配置
 *   }
 *  ]
 * }
 * </pre>
 * </p>
 *
 * @author 作者<1 9 2 2 8 0 2 3 5 2 @ qq.com>
 * @since 2023年11月24日 11:36:51
 **/
public class ExcelWorkSheet {
	
	/**
	 * ID
	 */
	private Integer id;
	
	
	/**
	 * excel表格唯一标识符
	 */
	private String gridKey;
	
	/**
	 * 保存sheet工作表数据,使用json字符串存储, 对应Luckysheet.option.data
	 */
	private String sheetJson;
	
	
	public ExcelWorkSheet() {
	
	}
	
	public ExcelWorkSheet(String gridKey, String sheetJson) {
		this.gridKey = gridKey;
		this.sheetJson = sheetJson;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getGridKey() {
		return gridKey;
	}
	
	public void setGridKey(String gridKey) {
		this.gridKey = gridKey;
	}
	
	public String getSheetJson() {
		return sheetJson;
	}
	
	public void setSheetJson(String sheetJson) {
		this.sheetJson = sheetJson;
	}
}

