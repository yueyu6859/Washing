package com.yunlinker.xiyi.bean;

import java.util.List;

//获取小区信息
public class HousingBean {
      private  int  count;
      private  int  num_pages;
      private  int  current_page;
      private  List<HousingResults> results;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getNum_pages() {
		return num_pages;
	}
	public void setNum_pages(int num_pages) {
		this.num_pages = num_pages;
	}
	public int getCurrent_page() {
		return current_page;
	}
	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}
	public List<HousingResults> getResults() {
		return results;
	}
	public void setResults(List<HousingResults> results) {
		this.results = results;
	}
}
