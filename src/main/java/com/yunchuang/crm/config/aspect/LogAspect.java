package com.yunchuang.crm.config.aspect;

import com.yunchuang.crm.config.annotation.Log;
import com.yunchuang.crm.console.user.domain.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * aop切面日志
 *
 * @author 尹冬飞
 * @create 2017-05-02 14:16
 */
@Aspect
@Component
public class LogAspect {
	private Logger logger = LoggerFactory.getLogger(LogAspect.class);

	@Pointcut("execution(public * com.yunchuang.crm.console.*.web..*.*(..))")
	public void consoleLog() {
	}

	@Before("consoleLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		//获取request
		HttpServletRequest request = attributes.getRequest();
		//获取session
		HttpSession session = request.getSession();
		//获取方法签名
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		//获取方法
		Method method = signature.getMethod(); //获取被拦截的方法
		//获取自定义Log注解
		Log annotation = method.getAnnotation(Log.class);
		//如果有Log注解,则写入日志
		if (annotation != null) {
			//获取session里的用户对象
			User user = (User) session.getAttribute("loginUser");
			//获取用户名
			String name = user != null ? "[" + user.getNickname() + "]" : "";
			logger.info("[CRM]"+name + "[" + annotation.value() + "][" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "][" + Arrays.toString(joinPoint.getArgs()) + "][" + request.getRemoteAddr() + "]");
		}
	}

	@AfterReturning(returning = "ret", pointcut = "consoleLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		//logger.info("RESPONSE : " + ret);
	}
}
