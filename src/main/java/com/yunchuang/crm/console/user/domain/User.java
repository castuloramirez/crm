package com.yunchuang.crm.console.user.domain;

import java.io.Serializable;


/**
 * Created by 尹冬飞 on 2017/12/27 16:46
 */
public class User implements Serializable {
	private static final long serialVersionUID = 3580277389227482834L;
	/**
	 * 1.主键
	 */
	private int id;
	/**
	 * 2.用户名
	 */
	private String name;
	/**
	 * 3.密码
	 */
	private String password;
	/**
	 * 4.昵称
	 */
	private String nickname;
	/**
	 * 5.状态:锁定是1,否则是0.
	 */
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", nickname=" + nickname + ", status=" + status + "]";
	}

}
