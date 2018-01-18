package com.yunchuang.crm.config.message;


import com.yunchuang.crm.config.utils.MyUtils;

/**
 * 初始化发送消息的参数
 *
 * @author ydf
 * @create 2017-02-08 10:55
 */
public class InitMessage {
    /**
     * 消息路径url,为进入管理后台-组织的地址
     */
    private String httpURL = "http://do.yunzhijia.com/pubacc/pubsend";
    /**
     * 消息类型，格式为整型
     */
    private int type = 2;
    /**
     * 排版展现模板，格式为整型
     */
    private int model = 2;
    /**
     * 是否推送待办消息,格式为整型,默认1=推送到待办消息;0=推送原公共号消息
     */
    private int todo = 1;
    /**
     * 发送方企业的企业注册号(eID)，格式为字符串
     */
    private String no;
    /**
     * 发送使用的公共号ID，格式为字符串
     */
    private String pub;
    /**
     * 公共号.密钥
     */
    private String pubkey;
    /**
     * 当type=2的时候发送的公共号消息内容
     */
    private String Type2Text;
    /**
     * 用户openid
     */
    private String openid;
    /**
     * 发送时间，为'currentTimeMillis()以毫秒为单位的当前时间'的字符串或数字,取系统时间即可
     */
    private String time = String.valueOf(System.currentTimeMillis());
    /**
     * 随机数，格式为字符串或数字(有且只有6位)
     */
    private String nonce = String.valueOf(MyUtils.nextInt());
    /**
     * 公共号加密串，格式为字符串
     */
    private String pubtoken;


    public String getHttpURL() {
        return httpURL;
    }

    public void setHttpURL(String httpURL) {
        this.httpURL = httpURL;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getType2Text() {
        return Type2Text;
    }

    public void setType2Text(String type2Text) {
        Type2Text = type2Text;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public int getTodo() {
        return todo;
    }

    public void setTodo(int todo) {
        this.todo = todo;
    }

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

    public String getPubkey() {
        return pubkey;
    }

    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }

    public String getTime() {
        return time;
    }

    public String getNonce() {
        return nonce;
    }

    public String getPubtoken() {
        String[] data = {getNo(), getPub(), getPubkey(), getNonce(), getTime()};
        return MyUtils.sha(data);
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

}
