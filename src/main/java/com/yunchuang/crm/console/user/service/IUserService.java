package com.yunchuang.crm.console.user.service;


import com.yunchuang.crm.console.user.domain.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by 尹冬飞 on 2017/12/27 16:46
 */
public interface IUserService {
	/**
	 * 1.增加后台管理员信息
	 * 
	 * @throws Exception
	 */
	void add(User user) throws Exception;

	/**
	 * 2.更改后台管理员信息
	 */
	void update(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException;

	/**
	 * 3.删除后台管理员信息
	 */
	void delete(Integer[] ids);

	/**
	 * 4.读取全部后台管理员信息
	 */
	List<User> list();

	/**
	 * 5.分页读取后台管理员信息
	 */
	List<User> find(int iDisplayStart, int iDisplayLength);

	/**
	 * 6.根据ID读取后台管理员信息
	 */
	User loadById(int id);

	/**
	 * 7.查询所有后台管理员信息数量
	 */
	int count();

	/**
	 * 8.根据用户名和密码读取用户信息
	 */
	User loadByNamePassword(String name, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;

	/**
	 * 9.初始化密码
	 */
	String restorePassword(int id) throws UnsupportedEncodingException, NoSuchAlgorithmException;

	/**
	 * 10.初始化管理员user信息
	 * 
	 * @throws Exception
	 */
	void initAdmin() throws Exception;
}
