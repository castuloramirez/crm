package com.yunchuang.crm.console.project.service;

import com.yunchuang.crm.console.project.model.Project;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List; /**
 * Created by 尹冬飞 on 2018/1/17 14:54
 */
public interface IProjectService {
	/**
	 * 1.下载模版
	 *
	 * @throws IOException
	 */
	void Template(HttpServletResponse response, HttpServletRequest request) throws IOException;

	/**
	 * 2.导入
	 * @return
	 *
	 * @throws Exception
	 */
	int importByExcel(MultipartFile file) throws Exception;

	/**
	 * 3.导出
	 *
	 * @throws IOException
	 */
	void exportToExcel(List<Project> list, HttpServletResponse response, HttpServletRequest request) throws IOException;

	/**
	 * 4.增加
	 */
	void add(Project project);

	/**
	 * 5.更改
	 */
	void update(Project project);

	/**
	 * 6.删除
	 */
	void delete(Integer[] ids);

	/**
	 * 7.前台读取全部
	 */
	List<Project> list();

	/**
	 * 8.根据ID读取
	 */
	Project loadById(int id);

	/**
	 * 10.查询所有数量
	 */
	int count();

	/**
	 * 11.分页读取
	 */
	List<Project> findByConsole(int iDisplayStart, int iDisplayLength);

	/**
	 * 12.批量插入
	 */
	void addBatch(List<Project> list);

	/**
	 * 13.清除表,不支持回滚
	 */
	void truncateAll();
}
