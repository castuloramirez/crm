package com.yunchuang.crm.console.project.dao;

import com.yunchuang.crm.console.project.model.Project;
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
public interface IProjectDao {
	/**
	 * 1.增加
	 */
	void add(Project project);

	/**
	 * 2.批量增加
	 */
	void addList(List<Project> projects);

	/**
	 * 3.更改
	 */
	void update(Project project);

	/**
	 * 4.删除
	 */
	void delete(Integer[] ids);

	/**
	 * 5.读取全部
	 */
	List<Project> list();

	/**
	 * 6.根据ID读取
	 */
	Project loadById(@Param("id") int id);

	/**
	 * 8.查询所有数量
	 */
	int count();

	/**
	 * 9.控制台分页读取
	 */
	List<Project> findByConsole(@Param("min") int min, @Param("max") int max);

	/**
	 * 10.清除表,不支持回滚
	 */
	void truncateAll();
}