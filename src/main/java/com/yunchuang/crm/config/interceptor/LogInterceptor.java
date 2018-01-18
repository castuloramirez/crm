package com.yunchuang.crm.config.interceptor;

import com.yunchuang.crm.config.annotation.Log;
import com.yunchuang.crm.console.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 日志拦截器
 *
 * @author 尹冬飞
 * @create 2017-05-03 8:59
 */
public class LogInterceptor implements HandlerInterceptor {
	private static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			HandlerMethod handler2 = (HandlerMethod) handler;
			Log annotation = handler2.getMethodAnnotation(Log.class);
			if (annotation != null) {
				HttpSession session = request.getSession();
				User user = (User) session.getAttribute("loginUser");
				String name = user != null ? "[" + user.getNickname() + "]" : "";
				logger.info(name + "[" + annotation.value() + "][" + handler2.getMethod().getDeclaringClass().getName() + "." + handler2.getMethod().getName() + "][" + request.getRemoteAddr() + "]");
			}
		}
		logger.info("preHandle1");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		logger.info("postHandle1");

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		logger.info("afterCompletion1");
	}
}
