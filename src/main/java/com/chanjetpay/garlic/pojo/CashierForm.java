package com.chanjetpay.garlic.pojo;

import java.util.Set;

//收银台
public class CashierForm {
	private String orderDesc;
	private Double orderAmount;

	private String walletLogo;
	private Double walletBalance;
	private String walletDesc;

	private Boolean registed;

	//收银台支持的支付方式
	private Set<String> payMethods;

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getWalletLogo() {
		return walletLogo;
	}

	public void setWalletLogo(String walletLogo) {
		this.walletLogo = walletLogo;
	}

	public Double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(Double walletBalance) {
		this.walletBalance = walletBalance;
	}

	public String getWalletDesc() {
		return walletDesc;
	}

	public void setWalletDesc(String walletDesc) {
		this.walletDesc = walletDesc;
	}

	public Boolean getRegisted() {
		return registed;
	}

	public void setRegisted(Boolean registed) {
		this.registed = registed;
	}

	public Set<String> getPayMethods() {
		return payMethods;
	}

	public void setPayMethods(Set<String> payMethods) {
		this.payMethods = payMethods;
	}
}
