package com.liyang.luckySheet.controller;

import com.liyang.luckySheet.service.ExcelWorkSheetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h2>Excel表格Sheet控制层</h2>
 * <p>
 *
 * </p>
 *
 * @author 作者<1 9 2 2 8 0 2 3 5 2 @ qq.com>
 * @since 2023年12月01日 12:30
 */
@RestController
@RequestMapping(value = "/excelWorkSheet")
public class ExcelWorkSheetController {
	
	private ExcelWorkSheetService service;
	
	public ExcelWorkSheetController(ExcelWorkSheetService service){
		this.service = service;
	}
}
