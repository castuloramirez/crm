package com.yunchuang.crm.console.customer.web;

import com.yunchuang.crm.config.annotation.Log;
import com.yunchuang.crm.console.customer.model.Customer;
import com.yunchuang.crm.console.customer.service.ICustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户表
 * <p>
 * Created by 尹冬飞 on 2018/1/17 10:04
 */
@Controller
@RequestMapping("/console/customer")
public class CustomerConsoleController {
	/**
	 * 注入service
	 */
	@Resource
	private ICustomerService customerService;

	/**
	 * 1.导入excel-customer
	 */
	@Log("客户表-导入excel-PC")
	@PostMapping(value = "/importExcel")
	@ResponseBody
	public Map<String, Object> customerImportExcel(@RequestParam(value = "file") MultipartFile file) {
		Map<String, Object> map = new HashMap<>();
		try {
			// 开始时间
			Long start = System.currentTimeMillis();
			// 导入excel
			int count = customerService.importByExcel(file);
			// 结束时间
			Long end = System.currentTimeMillis();
			// 耗时
			float time = (end - start) / 1000f;
			map.put("success", true);
			map.put("message", "成功导入" + count + "条," + "耗时:" + time + "秒!");
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", e.getMessage());
		}
		return map;
	}

	/**
	 * 2.导出excel-customer
	 */
	@Log("客户表-导出excel-PC")
	@GetMapping("/exportExcel")
	public void customerExportExcel(HttpServletResponse response, HttpServletRequest request) {
		try {
			customerService.exportToExcel(customerService.list(), response, request);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 3.下载模版-customer
	 */
	@Log("客户表-下载模版-PC")
	@GetMapping("/downloadExcelTemplate")
	public void customerTemplate(HttpServletResponse response, HttpServletRequest request) {
		try {
			customerService.Template(response, request);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 4.列表页面
	 */
	@Log("客户表-列表页面-PC")
	@GetMapping("/list")
	public String list() {
		return "console/customer/list";
	}

	/**
	 * 5.ajax列表页面
	 */
	@PostMapping("/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(value = "iDisplayLength") int iDisplayLength, @RequestParam(value = "iDisplayStart") int iDisplayStart, @RequestParam(value = "sEcho") int sEcho) {
		Map<String, Object> map = new HashMap<>();
		map.put("find", customerService.findByConsole(iDisplayStart, iDisplayLength));
		map.put("iTotalRecords", customerService.count());
		map.put("iTotalDisplayRecords", customerService.count());
		map.put("sEcho", sEcho);
		return map;
	}

	/**
	 * 6.增加页面
	 */
	@Log("客户表-增加页面-PC")
	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("customer", new Customer());
		return "console/customer/add";
	}

	/**
	 * 7.ajax增加页面
	 */
	@Log("客户表-增加-PC")
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> add(Customer customer) {
		Map<String, Object> map = new HashMap<>();
		try {
			customerService.add(customer);
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
	 * 8.更改页面
	 */
	@Log("客户表-更改页面-PC")
	@GetMapping("/{id}/update")
	public String update(@PathVariable int id, Model model) {
		model.addAttribute("customer", customerService.loadById(id));
		return "console/customer/update";
	}

	/**
	 * 9.更改页面
	 */
	@Log("客户表-更改-PC")
	@PostMapping("/{customerId}/update")
	@ResponseBody
	public Map<String, Object> update(@PathVariable(value = "customerId") int id, Customer customer) {
		Map<String, Object> map = new HashMap<>();
		try {
			customerService.update(customer);
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
	 * 10.删除
	 *
	 * @param ids 要删除的ID列表
	 * @return 返回删除后的信息
	 */
	@Log("客户表-删除-PC")
	@PostMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestBody Integer[] ids) {
		Map<String, Object> map = new HashMap<>();
		try {
			customerService.delete(ids);
			map.put("success", true);
			map.put("message", "删除成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", e.getMessage());
			e.printStackTrace();
		}
		return map;
	}
}
