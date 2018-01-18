package com.yunchuang.crm.console.customer.dao;

import com.yunchuang.crm.console.customer.model.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 尹冬飞 on 2018/1/16 17:26
 */
@Mapper
//@Component不加也好使,加了是为了idea能认出来
@Component
public interface ICustomerDao {
	/**
	 * 1.增加
	 */
	void add(Customer customer);

	/**
	 * 2.更改
	 */
	void update(Customer customer);

	/**
	 * 3.删除
	 */
	void delete(Integer[] ids);

	/**
	 * 4.读取全部
	 */
	List<Customer> list();

	/**
	 * 5.根据ID读取
	 */
	Customer loadById(@Param("id") int id);

	/**
	 * 6.查询所有数量
	 */
	int count();

	/**
	 * 7.控制台分页读取
	 */
	List<Customer> findByConsole(@Param("min") int min, @Param("max") int max);

	/**
	 * 8.清除表,不支持回滚
	 */
	void truncateAll();
}