package com.yunchuang.crm.config.ds;

import com.yunchuang.crm.config.properties.MasterDataSourceProperties;
import com.yunchuang.crm.config.utils.MyUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * 主数据源配置-yunchuang数据源
 *
 * @author 尹冬飞
 * @create 2017-12-27 13:18
 */
@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = {MasterDataSourceConfig.PACKAGE1,MasterDataSourceConfig.PACKAGE2}, sqlSessionFactoryRef = MasterDataSourceConfig.NAME + "SqlSessionFactory")
public class MasterDataSourceConfig {

	@Autowired
	private MasterDataSourceProperties masterDataSourceProperties;
	// 精确到 master 目录，以便跟其他数据源隔离
	//dao目录
	static final String PACKAGE1 = "com.yunchuang.crm.console.*.dao";
	static final String PACKAGE2 = "com.yunchuang.crm.mobile.*.dao";
	//xml目录
	private static final String mapperLocation1 = "classpath*:com/yunchuang/crm/console/*/dao/*Mapper.xml";
	private static final String mapperLocation2 = "classpath*:com/yunchuang/crm/mobile/*/dao/*Mapper.xml";
	private static final String[] mapperLocations = {mapperLocation1,mapperLocation2};
	//全局名字前缀
	static final String NAME = "master";

	//数据源
	@Bean(name = NAME + "DataSource")
	@Primary
	public DataSource dataSource() {
		return MyUtils.getDruidDataSource(
				masterDataSourceProperties.getDriverClassName(),
				masterDataSourceProperties.getUrl(),
				masterDataSourceProperties.getUsername(),
				masterDataSourceProperties.getPassword());
	}



	//事务管理器
	@Bean(name = NAME + "TransactionManager")
	@Primary
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	//工厂
	@Bean(name = NAME + "SqlSessionFactory")
	@Primary
	public SqlSessionFactory sqlSessionFactory(@Qualifier(NAME + "DataSource") DataSource dataSource) throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setMapperLocations(MyUtils.resolveMapperLocations(mapperLocations));
		return sessionFactory.getObject();
	}
}
