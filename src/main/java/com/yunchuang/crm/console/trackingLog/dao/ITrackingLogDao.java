package com.yunchuang.crm.console.trackingLog.dao;

import com.yunchuang.crm.console.trackingLog.model.TrackingLog;
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
public interface ITrackingLogDao {
	/**
	 * 1.增加
	 */
	void add(TrackingLog trackingLog);

	/**
	 * 2.更改
	 */
	void update(TrackingLog trackingLog);

	/**
	 * 3.删除
	 */
	void delete(Integer[] ids);

	/**
	 * 4.读取全部
	 */
	List<TrackingLog> list();

	/**
	 * 5.根据ID读取
	 */
	TrackingLog loadById(@Param("id") int id);

	/**
	 * 6.查询所有数量
	 */
	int count();

	/**
	 * 7.控制台分页读取
	 */
	List<TrackingLog> findByConsole(@Param("min") int min, @Param("max") int max);

	/**
	 * 8.清除表,不支持回滚
	 */
	void truncateAll();
}