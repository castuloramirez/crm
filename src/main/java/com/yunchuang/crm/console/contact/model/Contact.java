package com.yunchuang.crm.console.contact.model;

/**
 * Created by 尹冬飞 on 2018/1/16 17:26
 */
public class Contact {
	/**
	 * 1.主键
	 */
	private Integer id;
	/**
	 * 2.客户编号
	 */
	private String customerNo;
	/**
	 * 3.联系人编号
	 */
	private String contactNo;
	/**
	 * 4.联系人名称
	 */
	private String contactName;
	/**
	 * 5.职务
	 */
	private String position;
	/**
	 * 6.座机
	 */
	private String fixedTelephone;
	/**
	 * 7.手机
	 */
	private String mobilePhone;
	/**
	 * 8.电邮
	 */
	private String email;
	/**
	 * 9.QQ
	 */
	private String qq;
	/**
	 * 10.传真
	 */
	private String fax;
	/**
	 * 11.详细地址
	 */
	private String detailedAddress;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName == null ? null : contactName.trim();
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position == null ? null : position.trim();
	}

	public String getFixedTelephone() {
		return fixedTelephone;
	}

	public void setFixedTelephone(String fixedTelephone) {
		this.fixedTelephone = fixedTelephone == null ? null : fixedTelephone.trim();
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq == null ? null : qq.trim();
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax == null ? null : fax.trim();
	}

	public String getDetailedAddress() {
		return detailedAddress;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress == null ? null : detailedAddress.trim();
	}

	@Override
	public String toString() {
		return "Contact{" +
				"id=" + id +
				", customerNo='" + customerNo + '\'' +
				", contactNo='" + contactNo + '\'' +
				", contactName='" + contactName + '\'' +
				", position='" + position + '\'' +
				", fixedTelephone='" + fixedTelephone + '\'' +
				", mobilePhone='" + mobilePhone + '\'' +
				", email='" + email + '\'' +
				", qq='" + qq + '\'' +
				", fax='" + fax + '\'' +
				", detailedAddress='" + detailedAddress + '\'' +
				'}';
	}
}