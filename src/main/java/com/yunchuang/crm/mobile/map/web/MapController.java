package com.yunchuang.crm.mobile.map.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 尹冬飞 on 2018/1/15 8:44
 */
@Controller
@RequestMapping("/mobile/map")
public class MapController {
	/**
	 * 1.项目-进入页面
	 *
	 * @return 页面
	 */
	//@Log("兴齐眼药-流程管理-项目负责人管理-列表页面-PC")
	@GetMapping("/index")
	public String list() {
		return "mobile/map/index";
	}
}
