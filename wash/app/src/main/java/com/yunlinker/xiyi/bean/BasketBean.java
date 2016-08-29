package com.yunlinker.xiyi.bean;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

//洗衣篮集合
public class BasketBean implements Serializable{
//	private Drawable mImage;
	private  String  mImage;
	private int xiYiid; // id
	private String Name; // 衣服名称
	private String price; // 单价
	private String Number; // 数量

	public int getXiYiid() {
		return xiYiid;
	}

	public void setXiYiid(int xiYiid) {
		this.xiYiid = xiYiid;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public String getMImage() {
		return mImage;
	}

	public void setMimage(String mImage) {
		this.mImage = mImage;
	}

}
