package com.yunchuang.crm.config.message;

/**
 * @author 尹冬飞
 * @date 2015-07-27 10:25:30
 */
public class Tfrom {
	private String no;
	private String pub;
	private String time;
	private String nonce;
	private String pubtoken;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getPub() {
		return pub;
	}
	public void setPub(String pub) {
		this.pub = pub;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getPubtoken() {
		return pubtoken;
	}
	public void setPubtoken(String pubtoken) {
		this.pubtoken = pubtoken;
	}
	@Override
	public String toString() {
		return "TFrom [no=" + no + ", pub=" + pub + ", time=" + time + ", nonce=" + nonce + ", pubtoken=" + pubtoken + "]";
	}
}
