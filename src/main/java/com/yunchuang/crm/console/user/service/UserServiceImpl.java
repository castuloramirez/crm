package com.yunchuang.crm.console.user.service;

import com.yunchuang.crm.config.utils.MyUtils;
import com.yunchuang.crm.console.user.dao.IUserDao;
import com.yunchuang.crm.console.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by 尹冬飞 on 2017/12/27 16:46
 */
@Transactional(value = "masterTransactionManager", isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements IUserService {
	/* 注入dao */
	@Resource
	private IUserDao userDao;

	/**
	 * 1.增加后台管理员信息
	 */
	@Override
	public void add(User user) throws Exception {
		if (userDao.loadByName(user.getName()) == null) {
			user.setPassword(MyUtils.md5(user.getPassword()));
			userDao.add(user);
		} else {
			throw new Exception("要创建的用户已存在,请更换用户名");
		}
	}

	/**
	 * 2.更改后台管理员信息
	 */
	@Override
	public void update(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		if (user.getPassword().length() < 25) {
			user.setPassword(MyUtils.md5(user.getPassword()));
		}
		userDao.update(user);
	}

	/**
	 * 3.删除后台管理员信息
	 */
	@Override
	public void delete(Integer[] ids) {
		userDao.delete(ids);
	}

	/**
	 * 4.读取全部后台管理员信息
	 */
	@Override
	public List<User> list() {
		return userDao.list();
	}

	/**
	 * 5.分页读取后台管理员信息
	 */
	@Override
	public List<User> find(int iDisplayStart, int iDisplayLength) {
		/* 前台选择ALL的时候 */
		if (iDisplayLength == -1) {
			return userDao.list();
		}
		return userDao.find((iDisplayStart + 1), (iDisplayLength + iDisplayStart));
	}

	/**
	 * 6.根据ID读取后台管理员信息
	 */
	@Override
	public User loadById(int id) {
		return userDao.loadById(id);
	}

	/**
	 * 7.查询所有后台管理员信息数量
	 */
	@Override
	public int count() {
		return userDao.count();
	}

	/**
	 * 8.根据用户名和密码读取用户信息
	 */
	@Override
	public User loadByNamePassword(String name, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		return userDao.loadByNamePassword(name, MyUtils.md5(password));
	}

	/*
	 * 9.初始化密码
	 */
	@Override
	public String restorePassword(int id) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		User user = userDao.loadById(id);
		String password;
		/* 管理员初始密码为ccmc,其他人初始密码为123456 */
		if (user.getName().equals("admin")) {
			password = "ccmc";
		} else {
			password = "123456";
		}
		user.setPassword(MyUtils.md5(password));
		userDao.update(user);
		return password;
	}

	/*
	 * 10.初始化密码
	 */
	@Override
	public void initAdmin() throws Exception {
		User user = userDao.loadByName("admin");
		if (user == null) {
			user = new User();
			user.setName("admin");
			user.setNickname("超级管理员");
			user.setPassword(MyUtils.md5("ccmc"));
			user.setStatus(0);
			userDao.add(user);
		} else {
			user.setName("admin");
			user.setNickname("超级管理员");
			user.setPassword(MyUtils.md5("ccmc"));
			user.setStatus(0);
			userDao.update(user);
		}
	}

}
