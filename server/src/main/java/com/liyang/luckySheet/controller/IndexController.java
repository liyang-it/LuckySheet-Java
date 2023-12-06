package com.liyang.luckySheet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <h2>主页控制层</h2>
 * <p>
 *
 * </p>
 *
 * @author 作者<1 9 2 2 8 0 2 3 5 2 @ qq.com>
 * @since 2023年12月01日 11:56
 */
@Controller
public class IndexController {
	
	@GetMapping(value = "/")
	public String index(){
		return "index.html";
	}
}
