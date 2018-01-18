package com.yunchuang.crm.console.project.model;

/**
 * 项目
 * <p>
 * Created by 尹冬飞 on 2018/1/16 16:37
 */
public class Project {
	/**
	 * 1.主键
	 */
	private Integer id;
	/**
	 * 2.项目编号
	 */
	private String projectNo;
	/**
	 * 3.项目名称
	 */
	private String projectName;
	/**
	 * 4.客户编号
	 */
	private String customerNo;
	/**
	 * 5.联系人编号
	 */
	private String contactNo;
	/**
	 * 6.业务类别
	 */
	private String businessCategory;
	/**
	 * 7.备注
	 */
	private String remarks;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo == null ? null : projectNo.trim();
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName == null ? null : projectName.trim();
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo == null ? null : customerNo.trim();
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo == null ? null : contactNo.trim();
	}

	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory == null ? null : businessCategory.trim();
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
	}

	@Override
	public String toString() {
		return "Project{" +
				"id=" + id +
				", projectNo='" + projectNo + '\'' +
				", projectName='" + projectName + '\'' +
				", customerNo='" + customerNo + '\'' +
				", contactNo='" + contactNo + '\'' +
				", businessCategory='" + businessCategory + '\'' +
				", remarks='" + remarks + '\'' +
				'}';
	}
}