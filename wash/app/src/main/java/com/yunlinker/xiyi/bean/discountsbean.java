package com.yunlinker.xiyi.bean;

import java.util.List;

//优惠劵
public class discountsbean {
	

	private int count;
	private int num_pages;
	private int current_page;
	private List<Discounts> results;

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

	public List<Discounts> getResults() {
		return results;
	}

	public void setResults(List<Discounts> results) {
		this.results = results;
	}

}
