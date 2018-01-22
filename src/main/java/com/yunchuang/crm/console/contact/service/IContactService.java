package com.yunchuang.crm.console.contact.service;

import com.yunchuang.crm.console.contact.model.Contact;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by 尹冬飞 on 2018/1/18 10:16
 */
public interface IContactService {
	/**
	 * 1.下载模版
	 *
	 * @throws IOException
	 */
	void Template(HttpServletResponse response, HttpServletRequest request) throws IOException;

	/**
	 * 2.导入
	 *
	 * @return
	 * @throws Exception
	 */
	int importByExcel(MultipartFile file) throws Exception;

	/**
	 * 3.导出
	 *
	 * @throws IOException
	 */
	void exportToExcel(List<Contact> list, HttpServletResponse response, HttpServletRequest request) throws IOException;

	/**
	 * 4.增加
	 */
	void add(Contact contact);

	/**
	 * 5.更改
	 */
	void update(Contact contact);

	/**
	 * 6.删除
	 */
	void delete(Integer[] ids);

	/**
	 * 7.前台读取全部
	 */
	List<Contact> list();

	/**
	 * 8.根据ID读取
	 */
	Contact loadById(int id);

	/**
	 * 10.查询所有数量
	 */
	int count();

	/**
	 * 11.分页读取
	 */
	List<Contact> findByConsole(int iDisplayStart, int iDisplayLength, String customerNo);

	/**
	 * 12.批量插入
	 */
	void addBatch(List<Contact> list);

	/**
	 * 13.清除表,不支持回滚
	 */
	void truncateAll();
}
