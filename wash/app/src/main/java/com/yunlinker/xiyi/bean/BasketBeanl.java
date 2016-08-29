package com.yunlinker.xiyi.bean;

import java.sql.Blob;

import android.graphics.drawable.Drawable;

public class BasketBeanl {
	private Blob mImage;
	private int id; // id
	private String Name; // 衣服名称
	private String price; // 单价
	private String Number; // 数量

	public BasketBeanl(int id, Blob mImage, String Name, String price,
			String Number) {
		this.mImage = mImage;
		this.id = id;
		this.Name = Name;
		this.price = price;
		this.Number = Number;
	}

}
