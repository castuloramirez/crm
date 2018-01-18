package com.yunchuang.crm.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 主数据源属性文件-yunchuang数据源
 *
 * @author 尹冬飞
 * @create 2017-04-07 10:57
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource.master")
public class MasterDataSourceProperties {
	/**
	 * 1.数据库驱动
	 */
	private String driverClassName;
	/**
	 * 2.数据库url
	 */
	private String url;
	/**
	 * 3.数据库用户名
	 */
	private String username;
	/**
	 * 4.数据库用户密码
	 */
	private String password;

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
