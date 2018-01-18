package com.yunchuang.crm.console.user.web;

import com.yunchuang.crm.config.annotation.Log;
import com.yunchuang.crm.console.user.domain.User;
import com.yunchuang.crm.console.user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 尹冬飞 on 2017/12/27 16:47
 */
@Controller
/*@RequestMapping("")*/
@SessionAttributes("loginUser")
public class LoginController {
	/**
	 * 注入service
	 */
	@Resource
	private IUserService userService;
	/**
	 * 创建日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	/**
	 * 1.后台登录页
	 */
	@Log("后台管理页面-PC")
	@GetMapping("/login")
	public String login() {
		String encoding = System.getProperty("file.encoding");
		logger.info("file.encoding:" + encoding);
		String name = Charset.defaultCharset().name();
		logger.info("defaultCharset:" + name);
		return "login";
	}

	/**
	 * 2.后台登录验证
	 */
	@Log("后台登录验证-PC")
	@PostMapping("/login")
	public String loginPost(String name, String password, Model model, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		User user = userService.loadByNamePassword(name, password);
		if (user == null) {
			/* 查询不到的时候返回前台错误信息 */
			model.addAttribute("error", "用户名或者密码错误");
			return "login";
		}
		model.addAttribute("loginUser", user);
		session.setAttribute("loginUser", user);
		return "redirect:console/index";
	}

	/**
	 * 3.注销
	 */
	@Log("管理后台-注销-PC")
	@GetMapping("/logout")
	public String logout(Model model, HttpSession session) {
		model.asMap().remove("loginUser");
		session.invalidate();
		return "redirect:login";
	}

	/**
	 * 4.锁屏
	 */
	@Log("管理后台-锁屏-PC")
	@GetMapping("/lock")
	public String lock() {
		return "lock";
	}

	/**
	 * 5.首页
	 */
	@Log("管理后台-首页-PC")
	@GetMapping("/console/index")
	public String consoleIndex(Model model) {
		model.addAttribute("peoples", 23);
		model.addAttribute("other", 35);
		model.addAttribute("countTodo", 59);
		model.addAttribute("countFinished", 98);
		return "console/index";
	}

	/**
	 * 6.首页
	 */
	@GetMapping({"/", ""})
	public String index() {
		return "login";
	}
}
