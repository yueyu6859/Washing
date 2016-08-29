package com.yunlinker.xiyi.bean;

import java.util.List;

//支付订单返回bean类
public class ordersBean {
     private String  id;
     private List<OrdersItem>items;
     private  String  discount;
     private  String    price;
     private  String    created;
     private   String    take_time;
     private   String    send_time;
     private   String     order_num;
     public   class  address{
    	 private  String   id;
    	 private  String   name;
    	 private  String   tel;
    	 private   String  detail_address;
    	 public  class  area{
    		 private  String  id;
    		 private  String  name;
    		 private  String  detail;
    	 }
     }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<OrdersItem> getItems() {
		return items;
	}
	public void setItems(List<OrdersItem> items) {
		this.items = items;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getTake_time() {
		return take_time;
	}
	public void setTake_time(String take_time) {
		this.take_time = take_time;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getOrder_num() {
		return order_num;
	}
	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}
     
}
