package com.yunchuang.crm.config.filter;


import com.yunchuang.crm.console.user.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 后台过滤器
 * 
 * @author 尹冬飞
 * @date 2016年3月24日 上午10:09:28
 */
@WebFilter(urlPatterns = "/console/*")
public class LoginFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hsq = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String requestType = hsq.getHeader("X-Requested-With");
		User user = (User) hsq.getSession().getAttribute("loginUser");
		if (user == null) {
			/* 如果是ajax请求的情况 */
			if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
				response.setHeader("sessionstatus", "timeout");
				response.sendError(518, "session timeout.");
				return;
			}
			/* 如果不是ajax请求的情况 */
			((HttpServletResponse) resp).sendRedirect(hsq.getContextPath() + "/login");
			return;
		}
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
