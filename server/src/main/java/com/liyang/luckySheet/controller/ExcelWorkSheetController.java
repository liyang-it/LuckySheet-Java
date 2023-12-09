package com.liyang.luckySheet.controller;

import com.liyang.luckySheet.responseUtil.ResponseUtil;
import com.liyang.luckySheet.service.ExcelWorkSheetService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

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
	
	private final ExcelWorkSheetService service;
	
	public ExcelWorkSheetController(ExcelWorkSheetService service) {
		this.service = service;
	}
	
	@PostMapping(value = "/loadUrl")
	public String loadUrl(@RequestParam String gridKey){
		return service.loadUrl(gridKey);
	}
	
	@GetMapping(value = "/exportExcel/{gridKey}")
	public void exportExcel(@PathVariable String gridKey, HttpServletResponse response) throws IOException {
		 service.exportExcel(gridKey, response);
	}
	
	@PostMapping(value = "/save")
	public ResponseUtil save(@RequestBody Map<String, String> params) throws IOException {
		return service.save(params);
	}
	
}
