package com.yunchuang.crm.console.user.web;

import com.yunchuang.crm.config.annotation.Log;
import com.yunchuang.crm.console.user.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Created by 尹冬飞 on 2017/12/27 16:46
 */
@Controller
@RequestMapping("/init")
public class InitAdminController {
	/**
	 * 注入service
	 */
	@Resource
	private IUserService userService;

	/**
	 * 10.初始化密码
	 */
	@Log("admin初始化密码-PC")
	@GetMapping("/admin")
	public void initAdmin(HttpServletResponse resp) {
		try {
			userService.initAdmin();
			// 向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
			resp.setHeader("Content-type", "text/html;charset=UTF-8");
			String data = "初始化admin成功,密码是ccmc";
			OutputStream stream = resp.getOutputStream();
			stream.write(data.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
