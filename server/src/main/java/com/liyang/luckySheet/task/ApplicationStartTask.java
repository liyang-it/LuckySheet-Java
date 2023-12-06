package com.liyang.luckySheet.task;

import com.liyang.luckySheet.constant.DefaultGridKeyEmum;
import com.liyang.luckySheet.entity.ExcelWorkSheet;
import com.liyang.luckySheet.mapper.ExcelWorkSheetMapper;
import com.liyang.luckySheet.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * <h2>程序启动时运行任务</h2>
 * <p>
 *
 * </p>
 *
 * @author 作者<1 9 2 2 8 0 2 3 5 2 @ qq.com>
 * @since 2023年12月05日 16:05
 */
@Component
public class ApplicationStartTask implements ApplicationRunner {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ApplicationStartTask.class);
	private final ExcelWorkSheetMapper mapper;
	
	public ApplicationStartTask(ExcelWorkSheetMapper mapper){
		this.mapper = mapper;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		initDefaultExcel();
	}
	
	/**
	 * 初始化默认Excel表格数据
	 */
	private void initDefaultExcel(){
		LOGGER.info("************ 系统启动, 开始初始化默认Excel表格 ************");
		
		Integer exist = mapper.existByGridKey(DefaultGridKeyEmum.DefaultGridKey.getGridKey());
		if(exist == null){
			// 默认表格不存在，初始化
			
			ExcelWorkSheet overallFinancialBudgetSheet = new ExcelWorkSheet(DefaultGridKeyEmum.DefaultGridKey.getGridKey(), "{\"name\": \"shee1\",\"color\": \"red\",\"index\": 0,\"status\": 1,\"order\": 0,\"hide\": 0,\"row\": 36,\"column\": 26,\"defaultRowHeight\": 19,\"defaultColWidth\": 73,\"celldata\": [{\"r\": 0,\"c\": 0,\"v\": {\"m\": \"表头1\",\"ct\": {\"fa\": \"General\",\"t\": \"g\"},\"v\": \"表头1\",\"bl\": 1,\"ht\": \"0\"}},{\"r\": 0,\"c\": 1,\"v\": {\"m\": \"表头2\",\"ct\": {\"fa\": \"General\",\"t\": \"g\"},\"v\": \"表头2\",\"bl\": 1,\"ht\": \"0\"}},{\"r\": 0,\"c\": 2,\"v\": {\"m\": \"表头3\",\"ct\": {\"fa\": \"General\",\"t\": \"g\"},\"v\": \"表头3\",\"bl\": 1,\"ht\": \"0\"}},{\"r\": 0,\"c\": 3,\"v\": {\"m\": \"表头4\",\"ct\": {\"fa\": \"General\",\"t\": \"g\"},\"v\": \"表头4\",\"bl\": 1,\"ht\": \"0\"}}],\"config\": {\"merge\": {},\"rowlen\": {},\"columnlen\": {\"0\": 150,\"1\": 150,\"2\": 150,\"3\": 150},\"rowhidden\": {},\"colhidden\": {},\"borderInfo\": {},\"authority\": {}},\"scrollLeft\": 0,\"scrollTop\": 0,\"luckysheet_select_save\": [],\"calcChain\": [],\"isPivotTable\": false,\"pivotTable\": {},\"filter_select\": {},\"filter\": null,\"luckysheet_alternateformat_save\": [],\"luckysheet_alternateformat_save_modelCustom\": [],\"luckysheet_conditionformat_save\": {},\"frozen\": {},\"chart\": [],\"zoomRatio\": 1,\"image\": [],\"showGridLines\": 1,\"dataVerification\": {}}");
			mapper.save(overallFinancialBudgetSheet);
		}
		
		LOGGER.info("************ 系统启动, 完成初始化默认Excel表格 ************");
	}
}
