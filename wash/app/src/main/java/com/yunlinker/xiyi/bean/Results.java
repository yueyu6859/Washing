package com.yunlinker.xiyi.bean;

import java.io.Serializable;

/**
 * 件洗，袋洗bean类
 * 
 * @author Administrator
 *
 */
public class Results {
	public int id;
	public String name;
	public String description;
	public String image;
	public int type;
	public int price;
	public int category;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String toString() {

		return "UserInfo [id=" + id + ", name=" + name + ", image=" + image
				+ ",type=" + type + ",price=" + price + ",category=" + category
				+ "]";
	}
}