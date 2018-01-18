package com.yunchuang.crm.console.customer.model;

import java.math.BigDecimal;

/**
 * 业务数据_客户
 * <p>
 * Created by 尹冬飞 on 2018/1/16 16:48
 */
public class Customer {
	/**
	 * 1.主键
	 */
	private Integer id;
	/**
	 * 2.客户编号
	 */
	private String customerNo;
	/**
	 * 3.客户名称
	 */
	private String customerName;
	/**
	 * 4.地区
	 */
	private String area;
	/**
	 * 5.省份
	 */
	private String province;
	/**
	 * 6.城市
	 */
	private String city;
	/**
	 * 7.区县
	 */
	private String county;
	/**
	 * 8.通讯地址
	 */
	private String postalAddress;
	/**
	 * 9.行业分类
	 */
	private String industryClassification;
	/**
	 * 10.业务类别
	 */
	private String businessCategory;
	/**
	 * 11.法人
	 */
	private String legalPerson;
	/**
	 * 12.企业联系电话
	 */
	private String enterpriseContactPhone;
	/**
	 * 13.经度
	 */
	private BigDecimal longitude;
	/**
	 * 14.纬度
	 */
	private BigDecimal latitude;

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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName == null ? null : customerName.trim();
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area == null ? null : area.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county == null ? null : county.trim();
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress == null ? null : postalAddress.trim();
	}

	public String getIndustryClassification() {
		return industryClassification;
	}

	public void setIndustryClassification(String industryClassification) {
		this.industryClassification = industryClassification == null ? null : industryClassification.trim();
	}

	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory == null ? null : businessCategory.trim();
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson == null ? null : legalPerson.trim();
	}

	public String getEnterpriseContactPhone() {
		return enterpriseContactPhone;
	}

	public void setEnterpriseContactPhone(String enterpriseContactPhone) {
		this.enterpriseContactPhone = enterpriseContactPhone == null ? null : enterpriseContactPhone.trim();
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"id=" + id +
				", customerNo='" + customerNo + '\'' +
				", customerName='" + customerName + '\'' +
				", area='" + area + '\'' +
				", province='" + province + '\'' +
				", city='" + city + '\'' +
				", county='" + county + '\'' +
				", postalAddress='" + postalAddress + '\'' +
				", industryClassification='" + industryClassification + '\'' +
				", businessCategory='" + businessCategory + '\'' +
				", legalPerson='" + legalPerson + '\'' +
				", enterpriseContactPhone='" + enterpriseContactPhone + '\'' +
				", longitude=" + longitude +
				", latitude=" + latitude +
				'}';
	}
}