package com.yunchuang.crm.config.annotation;

import java.lang.annotation.*;

/**
 * 日志
 *
 * @author 尹冬飞
 * @date 2015-07-02 15:43:26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Log {
	/**
	 * 1.日志内容
	 */
	String value();
}
