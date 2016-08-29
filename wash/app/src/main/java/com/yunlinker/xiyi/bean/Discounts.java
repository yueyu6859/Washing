package com.yunlinker.xiyi.bean;

import java.io.Serializable;

//洗衣劵
public class Discounts implements Serializable {
	private String id;
	private String price;
	private String user_id;
	private String dead_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getDead_time() {
		return dead_time;
	}

	public void setDead_time(String dead_time) {
		this.dead_time = dead_time;
	}
}
