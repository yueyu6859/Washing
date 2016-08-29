package com.yunlinker.xiyi.bean;

//登陆后得到的用户信息

public class logbean {
	// 用户id
	private String id;
	// 登陆名字
	private String username;
	// 优惠劵数量
	private String discount_count;
	// 推荐码
	private String invite_code;
	// 添加的地址姓名
	private String address_name;
	// 交易密码
	private String has_offer_password;
	// 洗衣币
	private String credits;

	public logbean() {

	}

	public logbean(String id, String username, String discount_count,
			String invite_code, String address_name, String has_offer_password,
			String credits) {
		this.id = id;
		this.username = username;
		this.discount_count = discount_count;
		this.invite_code = invite_code;
		this.address_name = address_name;
		this.has_offer_password = has_offer_password;
		this.credits = credits;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDiscount_count() {
		return discount_count;
	}

	public void setDiscount_count(String discount_count) {
		this.discount_count = discount_count;
	}

	public String getInvite_code() {
		return invite_code;
	}

	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}

	public String getAddress_name() {
		return address_name;
	}

	public void setAddress_name(String address_name) {
		this.address_name = address_name;
	}

	public String getHas_offer_password() {
		return has_offer_password;
	}

	public void setHas_offer_password(String has_offer_password) {
		this.has_offer_password = has_offer_password;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	@Override
	public String toString() {

		return "UserInfo[id=" + id + ",username=" + username
				+ ",discount_count=" + discount_count + ",invite_code="
				+ invite_code + "," + "address_name=" + address_name
				+ ",has_offer_password=" + has_offer_password + "]";

	}
}
