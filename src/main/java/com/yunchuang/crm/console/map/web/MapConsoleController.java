package com.yunchuang.crm.console.map.web;

import com.yunchuang.crm.config.annotation.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 客户分布地图
 * <p>
 * Created by 尹冬飞 on 2018/1/17 18:09
 */
@Controller
@RequestMapping("/console/map")
public class MapConsoleController {

	/**
	 * 1.客户分布地图
	 */
	@Log("客户分布地图-PC")
	@GetMapping("/index")
	public String index() {
		return "console/map/index";
	}


}
