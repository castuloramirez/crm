package com.yunchuang.crm.config.annotation;

import java.lang.annotation.*;

/**
 * 权限
 *
 * @author 尹冬飞
 * @date 2015-07-02 09:57:35
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AuthPassport {
	/**
	 * 1.值是对应的部门表里的部门名
	 */
	String name();
}
