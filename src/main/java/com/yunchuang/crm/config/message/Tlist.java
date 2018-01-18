package com.yunchuang.crm.config.message;

/**
 * @author 尹冬飞
 * @date 2015-07-29 14:49:29
 */
public class Tlist {
/*	"date":"发布日期，格式为包含了'年月日时分秒'字符串",
	"title":"消息标题，格式为字符串",
	"text":"消息摘要，格式为字符串",
	"zip":"内容压缩包二进制字节流，格式为经过BASE64编码的字符串",
	"url":"原文链接，格式为经过URLENCODE编码的字符串",
	"appid": 如果打开的链接是轻应用,必须传入轻应用号讯通才能传入参数ticket,参考<轻应用框架>开发,
	"name":"图片的文件名，格式为字符串",
	"pic":"图片的二进制字节流，格式为经过BASE64编码的字符串"*/
	private String date;
	private String title;
	private String text;
	//private String zip;
	private String url;
	//private String appid;
	private String name;
	private String pic;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
/*	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}*/
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
/*	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}*/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}

}
