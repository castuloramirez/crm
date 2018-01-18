package com.yunchuang.crm.console.business.model;

import java.math.BigDecimal;
/**
 * Created by 尹冬飞 on 2018/1/16 17:26
 */
public class Business {
	private Integer id;

	private String customerNo;

	private String businessNo;

	private Integer year;

	private BigDecimal annualIncome;

	private BigDecimal annualProfit;

	private BigDecimal profitMargin;

	private Integer numberOfEmployees;

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

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo == null ? null : businessNo.trim();
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public BigDecimal getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(BigDecimal annualIncome) {
		this.annualIncome = annualIncome;
	}

	public BigDecimal getAnnualProfit() {
		return annualProfit;
	}

	public void setAnnualProfit(BigDecimal annualProfit) {
		this.annualProfit = annualProfit;
	}

	public BigDecimal getProfitMargin() {
		return profitMargin;
	}

	public void setProfitMargin(BigDecimal profitMargin) {
		this.profitMargin = profitMargin;
	}

	public Integer getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(Integer numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}
}