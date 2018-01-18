package com.yunchuang.crm.console.trackingLog.model;

/**
 * 跟踪日志
 * <p>
 * Created by 尹冬飞 on 2018/1/16 16:37
 */
public class TrackingLog {
	/**
	 * 1.主键
	 */
	private Integer id;
	/**
	 * 2.日志编号
	 */
	private String trackingLogNo;
	/**
	 * 3.客户编号
	 */
	private String customerNo;
	/**
	 * 4.项目编号
	 */
	private String projectNo;
	/**
	 * 5.日期
	 */
	private String createTime;
	/**
	 * 6.标题
	 */
	private String title;
	/**
	 * 7.内容
	 */
	private String content;
	/**
	 * 8.创建人openid
	 */
	private String founderOpenid;
	/**
	 * 9.创建人名字
	 */
	private String founderName;
	/**
	 * 10.创建人头像
	 */
	private String founderImage;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTrackingLogNo() {
		return trackingLogNo;
	}

	public void setTrackingLogNo(String trackingLogNo) {
		this.trackingLogNo = trackingLogNo == null ? null : trackingLogNo.trim();
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo == null ? null : customerNo.trim();
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo == null ? null : projectNo.trim();
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getFounderOpenid() {
		return founderOpenid;
	}

	public void setFounderOpenid(String founderOpenid) {
		this.founderOpenid = founderOpenid == null ? null : founderOpenid.trim();
	}

	public String getFounderName() {
		return founderName;
	}

	public void setFounderName(String founderName) {
		this.founderName = founderName == null ? null : founderName.trim();
	}

	public String getFounderImage() {
		return founderImage;
	}

	public void setFounderImage(String founderImage) {
		this.founderImage = founderImage == null ? null : founderImage.trim();
	}

	@Override
	public String toString() {
		return "TrackingLog{" +
				"id=" + id +
				", trackingLogNo='" + trackingLogNo + '\'' +
				", customerNo='" + customerNo + '\'' +
				", projectNo='" + projectNo + '\'' +
				", createTime='" + createTime + '\'' +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", founderOpenid='" + founderOpenid + '\'' +
				", founderName='" + founderName + '\'' +
				", founderImage='" + founderImage + '\'' +
				'}';
	}
}