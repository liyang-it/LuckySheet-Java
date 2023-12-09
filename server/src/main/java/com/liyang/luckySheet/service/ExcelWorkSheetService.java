package com.liyang.luckySheet.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liyang.luckySheet.entity.ExcelWorkSheet;
import com.liyang.luckySheet.mapper.ExcelWorkSheetMapper;
import com.liyang.luckySheet.responseUtil.ResponseUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <h2>Excel表格Sheet服务层</h2>
 * <p>
 *
 * </p>
 *
 * @author 作者<1 9 2 2 8 0 2 3 5 2 @ qq.com>
 * @since 2023年12月05日 16:04
 */
@Service
public class ExcelWorkSheetService {
	
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ExcelWorkSheetService.class);
	
	private final ExcelWorkSheetMapper workSheetMapper;
	
	
	public ExcelWorkSheetService(ExcelWorkSheetMapper workSheetMapper) {
		this.workSheetMapper = workSheetMapper;
	}
	
	
	/**
	 * 接口请求和返回数据格式对应 luckySheet官方 loadUrl接口,返回为json字符串，不能为对象<br>
	 * 返回Sheet列表数据，<a href="https://mengshukeji.gitee.io/LuckysheetDocs/zh/guide/sheet.html#%E5%88%9D%E5%A7%8B%E5%8C%96%E9%85%8D%E7%BD%AE">参数参考</a>
	 * </pre>
	 */
	public String loadUrl(String gridKey) {
		
		// 直接返回
		return JSONObject.toJSONString(workSheetMapper.getDataListByGridKey(gridKey).stream().map(JSONObject::parseObject).collect(Collectors.toList()));
	}
	
	/**
	 * 修改表格单元格内容
	 *
	 * @param gridKey  表格唯一标识符
	 * @param dataJson LuckySheet 操作数据
	 */
	public void updateCellData(String gridKey, JSONObject dataJson) {
		Thread.ofVirtual().start(() -> {
			// 查找表格的Sheet列表
			List<ExcelWorkSheet> querySheetList = workSheetMapper.getListByGridKey(gridKey);
			
			// 判断修改的Sheet是否存在, 查找下标
			int index = -1;
			JSONObject querySheetJson = null;
			
			for (int i = 0; i < querySheetList.size(); i++) {
				
				querySheetJson = JSONObject.parseObject(querySheetList.get(i).getSheetJson());
				// 判断 Sheet下标是否存在
				if (dataJson.getString("i").equals(querySheetJson.getString("index"))) {
					index = i;
					break;
				}
			}
			
			if (index == -1) {
				// 不存在，保存Sheet数据
				JSONObject saveJsonSheet = new JSONObject();
				saveJsonSheet.put("name", dataJson.getString("i"));
				saveJsonSheet.put("index", dataJson.getString("i"));
				saveJsonSheet.put("status", 0);
				saveJsonSheet.put("celldata", Collections.singletonList(dataJson));
				
				ExcelWorkSheet saveSheet = new ExcelWorkSheet(gridKey, saveJsonSheet.toJSONString());
				workSheetMapper.save(saveSheet);
			} else {
				// 存在，直接修改替换, 根据修改的 Sheet 行下标、列下标查找对应单元格内容，如果不存在单元格直接保存整个数据
				
				JSONArray celldata = querySheetJson.getJSONArray("celldata");
				
				if (celldata == null) {
					celldata = new JSONArray();
				}
				
				// 查找单元格是否存在
				int cellIndex = -1;
				
				for (int i = 0; i < celldata.size(); i++) {
					if (celldata.getJSONObject(i).getString("r").equals(dataJson.getString("r")) && celldata.getJSONObject(i).getString("c").equals(dataJson.getString("c"))) {
						cellIndex = i;
						break;
					}
				}
				
				if (cellIndex == -1) {
					// 单元格不存在,添加
					celldata.add(dataJson);
				} else {
					// 修改替换单元格内容
					celldata.getJSONObject(cellIndex).put("v", dataJson.getJSONObject("v"));
				}
				
				
				// 覆盖替换celldata
				querySheetJson.put("celldata", celldata);
				
				workSheetMapper.updateSheetById(querySheetList.get(index).getId(), querySheetJson.toJSONString());
			}
		});
		
	}
	
	/**
	 * 修改表格单元格属性(列宽、文字居中等)
	 *
	 * @param gridKey  表格唯一标识符
	 * @param dataJson LuckySheet 操作数据
	 */
	public void updateCellConfig(String gridKey, JSONObject dataJson) {
		Thread.ofVirtual().start(() -> {
			// 查找表格的Sheet列表
			List<ExcelWorkSheet> querySheetList = workSheetMapper.getListByGridKey(gridKey);
			
			// 判断修改的Sheet是否存在, 查找下标
			int index = -1;
			JSONObject querySheetJson = null;
			
			for (int i = 0; i < querySheetList.size(); i++) {
				
				querySheetJson = JSONObject.parseObject(querySheetList.get(i).getSheetJson());
				// 判断 Sheet下标是否存在
				if (dataJson.getString("i").equals(querySheetJson.getString("index"))) {
					index = i;
					break;
				}
			}
			
			if (index == -1) {
				// 不存在，保存Sheet数据
				JSONObject saveJsonSheet = new JSONObject();
				saveJsonSheet.put("name", dataJson.getString("i"));
				saveJsonSheet.put("index", dataJson.getString("i"));
				saveJsonSheet.put("status", 0);
				
				// 根据操作key修改对应单元格配置： 边框：'borderInfo'，行隐藏：'rowhidden'， 列隐藏：'colhidden'， 行高：'rowlen'， 列宽：'columnlen'
				JSONObject config = new JSONObject();
				config.put(dataJson.getString("k"), dataJson.getJSONArray("v"));
				
				saveJsonSheet.put("config", config);
				
				ExcelWorkSheet saveSheet = new ExcelWorkSheet(gridKey, saveJsonSheet.toJSONString());
				workSheetMapper.save(saveSheet);
			} else {
				// Sheet存在，直接修改替换 config单元格属性
				JSONObject config = querySheetJson.getJSONObject("config");
				
				if (config == null) {
					// config属性为空
					config = new JSONObject();
				}
				// 修改替换config指定属性值
				config.put(dataJson.getString("k"), dataJson.getJSONObject("v"));
				
				// 修改替换 config
				querySheetJson.put("config", config);
				
				workSheetMapper.updateSheetById(querySheetList.get(index).getId(), querySheetJson.toJSONString());
			}
		});
		
	}
	
	/**
	 * 范围单元格刷新，例如更改整个列的内容居中等, 这里的范围指的是在行或者列是连续的，例如 使用 Ctrl 选中多个单元格不属于范围，使用Shift 选中多个单元格才算<br>
	 * 例如 同时修改第一行 第一列、第三列数据，虽然是同时修改两列，但并不是连续的，不属于单元格范围，属于单元格内容变更，会发起两次单元格内容变更指令<br>
	 * 例如 同时修改第一行 第一列、第二列、第三列数据(行号或者列序号必须是有序连在一块的) 这个就属于单元格范围更新，范围是只 开始到结束有序的范围，例如 1,2,3,4,5,6,7,8,9,10 这样才算<br>
	 * 例如 同时修改第一行 第一列、第二行 第一列数据(行号或者列序号必须是有序连在一块的) 这个就属于单元格范围更新，范围是只 开始到结束有序的范围，例如 1,2,3,4,5,6,7,8,9,10 这样才算<br>
	 *
	 * @param gridKey  表格唯一标识符
	 * @param dataJson LuckySheet 操作数据
	 */
	public void updateMultipleCellConfig(String gridKey, JSONObject dataJson) {
		Thread.ofVirtual().start(() -> {
			// 查找表格的Sheet列表
			List<ExcelWorkSheet> querySheetList = workSheetMapper.getListByGridKey(gridKey);
			
			// 判断修改的Sheet是否存在, 查找下标
			int index = -1;
			
			JSONObject querySheetJson = null;
			
			for (int i = 0; i < querySheetList.size(); i++) {
				
				querySheetJson = JSONObject.parseObject(querySheetList.get(i).getSheetJson());
				
				// 判断 Sheet下标是否存在
				if (dataJson.getString("i").equals(querySheetJson.getString("index"))) {
					index = i;
					break;
				}
			}
			
			// 修改的行范围，例如 [0,10] 表示修改 第0行 到 第10行
			JSONArray rangeRow = dataJson.getJSONObject("range").getJSONArray("row");
			int rowIndexStart = rangeRow.getInteger(0);
			int rowIndexEnd = rangeRow.getInteger(1);
			
			// 修改的列范围，例如 [0,10] 表示修改 第0列 到 第10列
			JSONArray rangeColumn = dataJson.getJSONObject("range").getJSONArray("column");
			int columnIndexStart = rangeColumn.getInteger(0);
			int columnIndexEnd = rangeColumn.getInteger(1);
			
			// 修改的范围数据，是二维数组对象存储, 一维数组存储对应的是行, 二维存储的是行对应的列，使用 rangeRow 作为下标获取对应行，使用 rangeColumn作为下标获取对应列
			JSONArray rangeArray = dataJson.getJSONArray("v");
			
			List<JSONObject> celldata;
			
			
			if (index == -1) {
				// Sheet不存在，新增Sheet，并且保存celldata单元格数据
				JSONObject saveJsonSheet = new JSONObject();
				saveJsonSheet.put("name", dataJson.getString("i"));
				saveJsonSheet.put("index", dataJson.getString("i"));
				saveJsonSheet.put("status", 0);
				
				// 新增单元格
				celldata = new LinkedList<>();
				
				// 循环 行范围
				for (int fRowIndex = rowIndexStart; fRowIndex <= rowIndexEnd; fRowIndex++) {
					
					// 因为是范围循环，需要计算出对应下标，例如 修改的行范围 第 1行到 第4行，那么获取对应数据的下标就是 0,1,2,3
					int computeRowIndex = rowIndexEnd - fRowIndex;
					
					// 循环列范围
					for (int fcolumnIndex = columnIndexStart; fcolumnIndex <= columnIndexEnd; fcolumnIndex++) {
						
						int computeColumnIndex = columnIndexEnd - fcolumnIndex;
						
						
						JSONObject saveJson = rangeArray.getJSONArray(computeRowIndex).getJSONObject(computeColumnIndex);
						
						LOGGER.info("单元格范围需要 新增 第 {} 行， 第 {} 列的数据：{} ", computeRowIndex, computeColumnIndex, saveJson);
						
						celldata.add(saveJson);
					}
				}
				
				// 覆盖保存
				saveJsonSheet.put("celldata", celldata);
				
				ExcelWorkSheet saveSheet = new ExcelWorkSheet(gridKey, saveJsonSheet.toJSONString());
				
				workSheetMapper.save(saveSheet);
				
				
			} else {
				// 存在，获取 celldata 按行范围更新
				String celldataStr = querySheetJson.getString("celldata");
				
				
				if (StringUtils.isBlank(celldataStr)) {
					celldata = new LinkedList<>();
				} else {
					celldata = JSONObject.parseArray(celldataStr, JSONObject.class);
				}
				
				// 如果 celldata数据列表为空，下面循环直接新增即可
				boolean areEmpty = celldata.isEmpty();
				
				
				// 循环 行范围
				for (int fRowIndex = rowIndexStart; fRowIndex <= rowIndexEnd; fRowIndex++) {
					
					// 因为是范围循环，需要计算出对应下标，例如 修改的行范围 第 1行到 第4行，那么获取对应数据的下标就是 0,1,2,3
					int computeRowIndex = rowIndexEnd - fRowIndex;
					
					// 循环列范围
					for (int fcolumnIndex = columnIndexStart; fcolumnIndex <= columnIndexEnd; fcolumnIndex++) {
						
						int computeColumnIndex = columnIndexEnd - fcolumnIndex;
						
						
						JSONObject updateJson = rangeArray.getJSONArray(computeRowIndex).getJSONObject(computeColumnIndex);
						
						LOGGER.info("单元格范围需要 修改 第 {} 行， 第 {} 列的数据：{} ", computeRowIndex, computeColumnIndex, updateJson);
						
						
						// celldata列表为空，直接新增, 判空赋值一定要放在上面，因为这里新增数据之后肯定不为空了
						
						if (areEmpty) {
							JSONObject saveJson = new JSONObject();
							// 行下标
							saveJson.put("r", computeRowIndex);
							
							// 列下标
							saveJson.put("c", computeColumnIndex);
							
							// 具体单元格数据
							saveJson.put("v", updateJson);
							celldata.add(saveJson);
							
						} else {
							// 使用 Stream流过滤修改，返回过滤的数量，如果数量为0.表示查找的数据不存在
							long updateRows = celldata.stream().filter(filter -> {
								boolean are = false;
								
								// 根据行下标和列下标判断是否存在
								if (filter.getInteger("r").equals(computeRowIndex) && filter.getInteger("c").equals(computeColumnIndex)) {
									// 修改
									filter.put("v", updateJson);
									are = true;
								}
								return are;
							}).count();
							
							if (updateRows == 0) {
								// 修改的数据不存在，新增
								JSONObject saveJson = new JSONObject();
								// 行下标
								saveJson.put("r", computeRowIndex);
								
								// 列下标
								saveJson.put("c", computeColumnIndex);
								
								// 具体单元格数据
								saveJson.put("v", updateJson);
								celldata.add(saveJson);
							}
						}
						
						// 覆盖替换celldata
						querySheetJson.put("celldata", celldata);
						
						workSheetMapper.updateSheetById(querySheetList.get(index).getId(), querySheetJson.toJSONString());
						
					}
				}
			}
		});
		
	}
	
	/**
	 * 手动保存整个Excel数据
	 *
	 * @param params Map集合,存储两参数, gridKey: 表格唯一标识符, data: 调用luckysheet.toJson()的数据
	 * @return ResponseUtil
	 */
	@Transactional(rollbackFor = Exception.class)
	public ResponseUtil save(Map<String, String> params) {
		final String gridKey = params.get("gridKey");
		final String dataStr = params.get("data");
		
		if (StringUtils.isBlank(gridKey)) {
			return ResponseUtil.fail("gridKey 不能为空");
		}
		if (StringUtils.isBlank(dataStr)) {
			return ResponseUtil.fail("data数据 不能为空");
		}
		
		JSONObject dataJson = JSONObject.parseObject(dataStr);
		
		// 目前只用到了Sheet表数据，t_excel_work_book 考虑删除用不到，所以这里只保存Sheet数据，表格标题、工具栏配置等信息不做保存
		JSONArray sheetData = dataJson.getJSONArray("data");
		
		
		if (CollectionUtils.isEmpty(sheetData)) {
			// 如果Sheet为空，不做任何处理,因为不想保存空数据
			
		} else {
			// 查询数据库Sheet列表
			List<ExcelWorkSheet> sheetList = workSheetMapper.getListByGridKey(gridKey);
			
			if (CollectionUtils.isEmpty(sheetList)) {
				// 新建Sheet保存
				sheetList = new LinkedList<>();
				for (int i = 0; i < sheetData.size(); i++) {
					sheetList.add(new ExcelWorkSheet(gridKey, sheetData.getString(0)));
				}
			} else {
				// 修改Sheet保存
				for (int i = 0; i < sheetData.size(); i++) {
					JSONObject sheet = sheetData.getJSONObject(i);
					
					// 使用stream.filter 过滤数据进行修改保存
					long updateRow = sheetList.stream().filter(item -> {
						boolean are = false;
						
						JSONObject json = JSONObject.parseObject(item.getSheetJson());
						
						if (sheet.getString("name").equals(json.getString("name"))) {
							
							// 存在修改，这里注意修改一定要指定具体的配置，例如 celldata单元格数据，不能直接把整个Sheet直接替换，会导致Socket编辑无法保存
							json.put("celldata", sheet.getJSONArray("celldata"));
							
							// 以及对应公式数据
							json.put("calcChain", sheet.getJSONArray("calcChain"));
							// 单元格属性配置
							
							
							item.setSheetJson(json.toJSONString());
							are = true;
						}
						return are;
					}).count();
					
					if (updateRow == 0) {
						// 过滤修改未修改一行数据表示修改的数据不存在
						sheetList.add(new ExcelWorkSheet(gridKey, sheet.toJSONString()));
					}
				}
			}
			
			// 保存已修改的列表
			for (ExcelWorkSheet sheet : sheetList) {
				if (sheet.getId() == null) {
					workSheetMapper.save(sheet);
				} else {
					workSheetMapper.updateSheetById(sheet.getId(), sheet.getSheetJson());
				}
			}
			
		}
		
		return ResponseUtil.success();
	}
	
	
	/**
	 * 将Sheet的单元格数组解析为List表格数据
	 *
	 * @param celldata Sheet单元格数组数据
	 * @return HashMap <pre>
	 * {@code
	 *  {
	 *      maxColumn: 最大列,可以根据这个字段在前端动态生成列数量,
	 *      record: List集合列表
	 *  }
	 * }
	 * </pre>
	 */
	public HashMap<String, Object> getExcelTableData(JSONArray celldata) {
		
		List<JSONObject> list = null;
		
		JSONObject object = null;
		
		int maxRow = 0;
		
		int maxColumn = 0;
		
		if (!CollectionUtils.isEmpty(celldata)) {
			
			
			list = new LinkedList<>();
			
			// 将json字符串转为JSON对象
			List<JSONObject> celldataJson = celldata.stream().map(Object::toString).map(JSONObject::parseObject).collect(Collectors.toList());
			
			
			// 获取单元格 最大行，最大列，循环行最大行数，一行数据解析为一个对象
			JSONObject maxRowJson = celldataJson.stream().max(new Comparator<JSONObject>() {
				@Override
				public int compare(JSONObject o1, JSONObject o2) {
					return o1.getInteger("r").compareTo(o2.getInteger("r"));
				}
			}).get();
			
			maxRow = maxRowJson.getInteger("r");
			
			JSONObject maxColumnJson = celldataJson.stream().max(new Comparator<JSONObject>() {
				@Override
				public int compare(JSONObject o1, JSONObject o2) {
					return o1.getInteger("c").compareTo(o2.getInteger("c"));
				}
			}).get();
			
			maxColumn = maxColumnJson.getInteger("c");
			
			// 循环最大行数，一行为一个对象
			for (int r = 0; r <= maxRow; r++) {
				
				int index = 1;
				
				object = new JSONObject();
				
				// 循环最大列数,一列表示一个字段, 因为LuckySheet保存会有很多个空数据单元格，需要过滤，判断 v和m值是否都不为空
				for (int c = 0; c <= maxColumn; c++) {
					
					// 查找 当前行下标和列下标对应的值
					int finalR = r;
					int finalC = c;
					
					Optional<JSONObject> find = celldataJson.stream().filter(item -> item.getInteger("r").equals(finalR) && item.getInteger("c").equals(finalC)).findFirst();
					
					if (find.isPresent()) {
						// 字段名 value + 数字递增,如果数据为空不处理
						String value = find.get().getJSONObject("v").getString("v");
						if (StringUtils.isNotBlank(value)) {
							object.put("value" + index, value);
						}

					}else{
						// 空白数据，赋值空值
						object.put("value" + index, "");
					}
					index++;
				}
				// 只新增 不为空的对象，过滤为空的单元格
				if (!object.isEmpty()) {
					list.add(object);
				}
			}
			// 最后最大列数量需要 + 1，因为需要把该字段返回前段作为动态列大小以及动态字段(value)
			maxColumn = maxColumn + 1;
		}
		HashMap<String, Object> result = new HashMap<>(2);
		result.put("maxColumn", maxColumn);
		result.put("record", list);
		return result;
	}
	
	
	/**
	 * 导出Excel数据,当前只实现了单个Sheet表导出，多个Sheet需要其他实现
	 *
	 * @param gridKey  表格唯一标识符
	 * @param response {@link HttpServletResponse}
	 */
	public void exportExcel(String gridKey, HttpServletResponse response) throws IOException {
		
		// 默认只取一个Sheet的内容，如果有多个请额外实现
		List<String> jsonStrList = workSheetMapper.getDataListByGridKey(gridKey);
		
		String jsonStr = null;
		if (CollectionUtils.isEmpty(jsonStrList)) {
			throw new RuntimeException(String.format("表格唯一标识符：%s的 Sheet 数据为空", gridKey));
		}
		
		jsonStr = jsonStrList.get(0);
		
		JSONObject sheet = JSONObject.parseObject(jsonStr);
		
		// 获取单元格数组, 单元格数组是一维数组，需要转换为对象集合，使用 r(行)下标 和 c(列)下标进行对应转换，第一行(0)是表头
		JSONArray celldata = sheet.getJSONArray("celldata");
		
		// 获取Excel解析表格数据
		HashMap<String, Object> tableMap = getExcelTableData(celldata);
		
		Integer maxColumn = (Integer) tableMap.get("maxColumn");
		
		List<JSONObject> record = (List<JSONObject>) tableMap.get("record");
		
		// 遍历最大字段数量，获取动态字段值
		
		// 组装Excel表头数据，第一行为表头, EasyExcel示例：https://easyexcel.opensource.alibaba.com/docs/current/quickstart/write#%E4%B8%8D%E5%88%9B%E5%BB%BA%E5%AF%B9%E8%B1%A1%E7%9A%84%E5%86%99
		List<List<String>> head = ListUtils.newArrayList();
		
		for (int c = 1; c <= maxColumn; c++) {
			String value = record.get(0).getString("value" + c);
			
			List<String> head0 = Collections.singletonList(value);
			
			head.add(head0);
		}
		
		// 组装表格数据，行下标从1开始
		List<List<String>> data = ListUtils.newArrayList();
		
		for (int i = 1; i < record.size(); i++) {
			
			List<String> dataObj = new LinkedList<>();
			
			for (int c = 1; c <= maxColumn; c++) {
				String value = record.get(i).getString("value" + c);
				dataObj.add(value);
			}
			
			
			data.add(dataObj);
		}
		
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		
		response.setCharacterEncoding("utf-8");
		
		// 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
		
		String fileName = URLEncoder.encode("dynamicHeadWrite" + System.currentTimeMillis(), "UTF-8").replaceAll("\\+", "%20");
		
		response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
		
		response.setHeader("Content-Type", "application/vnd.ms-excel");
		
		// 头的策略
		WriteCellStyle headWriteCellStyle = new WriteCellStyle();
		
		// 背景设置为黄色
		headWriteCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		WriteFont headWriteFont = new WriteFont();
		headWriteFont.setFontHeightInPoints((short) 15);
		headWriteCellStyle.setWriteFont(headWriteFont);
		
		// 文字对齐
		headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
		
		// 内容的策略
		WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
		
		// 内容背景颜色
		contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
		
		// 文字对齐
		contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
		
		// 字体大小
		WriteFont contentWriteFont = new WriteFont();
		contentWriteFont.setFontHeightInPoints((short) 12);
		contentWriteCellStyle.setWriteFont(contentWriteFont);
		
		// 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
		HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
		
		EasyExcel.write(response.getOutputStream())
				// 自定义样式
				.registerWriteHandler(horizontalCellStyleStrategy)
				// 列宽度
				.registerWriteHandler(new SimpleColumnWidthStyleStrategy(40)) // 简单的列宽策略，列宽20
				// 行高度
				.registerWriteHandler(new SimpleRowHeightStyleStrategy((short) 25, (short) 25)) // 简单的行高策略：头行高，内容行高
				// 这里放入动态头
				.head(head).sheet("Sheet1")
				// 当然这里数据也可以用 List<List<String>> 去传入
				.doWrite(data);
	}
}
