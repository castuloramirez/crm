package com.yunchuang.crm.console.user.web;

import com.yunchuang.crm.config.annotation.Log;
import com.yunchuang.crm.console.user.domain.User;
import com.yunchuang.crm.console.user.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * 管理员的管理页面
 * <p>
 * Created by 尹冬飞 on 2017/12/27 16:47
 */
@Controller
@RequestMapping("/console/user")
public class UserConsoleController {
	/**
	 * 注入service
	 */
	@Resource
	private IUserService userService;

	/**
	 * 1.用户列表页面
	 */
	@Log("系统设置-账户管理-用户列表页面-PC")
	@GetMapping("/list")
	public String list() {
		return "console/user/list";
	}

	/**
	 * 2.ajax用户列表页面
	 */
	@PostMapping("/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(value = "iDisplayLength") int iDisplayLength, @RequestParam(value = "iDisplayStart") int iDisplayStart, @RequestParam(value = "sEcho") int sEcho) {
		Map<String, Object> map = new HashMap<>();
		map.put("find", userService.find(iDisplayStart, iDisplayLength));
		map.put("iTotalRecords", userService.count());
		map.put("iTotalDisplayRecords", userService.count());
		map.put("sEcho", sEcho);
		return map;
	}

	/**
	 * 3.用户增加页面
	 */
	@Log("系统设置-账户管理-用户增加页面-PC")
	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("user", new User());
		return "console/user/add";
	}

	/**
	 * 4.用户增加页面
	 */
	@Log("系统设置-账户管理-用户增加-PC")
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> add(User user) {
		Map<String, Object> map = new HashMap<>();
		try {
			userService.add(user);
			map.put("success", true);
			map.put("message", "新建成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 5.用户更改页面
	 */
	@Log("系统设置-账户管理-用户更改页面-PC")
	@GetMapping("/{id}/update")
	public String update(@PathVariable int id, Model model) {
		model.addAttribute("user", userService.loadById(id));
		return "console/user/update";
	}

	/**
	 * 6.用户更改页面
	 */
	@Log("系统设置-账户管理-用户更改-PC")
	@PostMapping("/{userId}/update")
	@ResponseBody
	public Map<String, Object> update(@PathVariable(value = "userId") int id, User user) {
		Map<String, Object> map = new HashMap<>();
		try {
			userService.update(user);
			map.put("success", true);
			map.put("message", "修改成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 7.用户删除
	 *
	 * @param ids 要删除的ID列表
	 * @return 返回删除后的信息
	 */
	@Log("系统设置-账户管理-用户删除-PC")
	@PostMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestBody Integer[] ids) {
		Map<String, Object> map = new HashMap<>();
		try {
			userService.delete(ids);
			map.put("success", true);
			map.put("message", "删除成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 9.更改用户类型
	 */
	@Log("系统设置-账户管理-更改用户类型-PC")
	@PostMapping("/{id}/changeStatus")
	@ResponseBody
	public Map<String, Object> changeStatus(@PathVariable int id) {
		Map<String, Object> map = new HashMap<>();
		try {
			User user = userService.loadById(id);
			if (user.getStatus() == 0) {
				user.setStatus(1);
			} else {
				user.setStatus(0);
			}
			userService.update(user);
			map.put("success", true);
			map.put("message", "更改成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 10.初始化密码
	 */
	@Log("系统设置-账户管理-初始化密码-PC")
	@PostMapping("/{id}/restorePassword")
	@ResponseBody
	public Map<String, Object> restorePassword(@PathVariable int id) {
		Map<String, Object> map = new HashMap<>();
		try {
			String restorePassword = userService.restorePassword(id);
			map.put("success", true);
			map.put("message", "密码修改为:" + restorePassword);
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

}
