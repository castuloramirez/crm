package com.yunchuang.crm.config.message;

import java.util.List;

/**
 * @author 尹冬飞
 * @date 2015-07-27 10:25:37
 */
public class Tto {
	private String no;
	private String code;
	private List<String> user;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<String> getUser() {
		return user;
	}

	public void setUser(List<String> user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Tto [no=" + no + ", code=" + code + ", user=" + user + "]";
	}

}
