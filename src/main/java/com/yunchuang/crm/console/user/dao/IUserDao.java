package com.yunchuang.crm.console.user.dao;

import com.yunchuang.crm.console.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Created by 尹冬飞 on 2017/12/27 16:48
 */
@Mapper
//@Component不加也好使,加了是为了idea能认出来
@Component
public interface IUserDao {
	/**
	 * 1.增加后台管理员信息
	 */
	void add(User user);

	/**
	 * 2.更改后台管理员信息
	 */
	void update(User user);

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
	List<User> find(@Param("min") int min, @Param("max") int max);

	/**
	 * 6.根据ID读取后台管理员信息
	 */
	User loadById(@Param("id") int id);

	/**
	 * 7.查询所有后台管理员信息数量
	 */
	int count();

	/**
	 * 8.根据用户名和密码读取用户信息
	 */
	User loadByNamePassword(@Param("name") String name, @Param("password") String password);

	/**
	 * 9.根据用户名读取用户信息
	 */
	User loadByName(@Param("name") String name);
}
