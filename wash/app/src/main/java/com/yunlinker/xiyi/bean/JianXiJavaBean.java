package com.yunlinker.xiyi.bean;

import java.util.List;

//件洗
public class JianXiJavaBean {



	public String count;
	public String num_pages;
	public String current_page;

	private List<Results> results;

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getNum_pages() {
		return num_pages;
	}

	public void setNum_pages(String num_pages) {
		this.num_pages = num_pages;
	}

	public String getCurrent_page() {
		return current_page;
	}

	public void setCurrent_page(String current_page) {
		this.current_page = current_page;
	}

	public List<Results> getResults() {
		return results;
	}

	public void setResults(List<Results> results) {
		this.results = results;
	}

	@Override
	public String toString() {

		return "UserInfo[count=" + count + ",num_pages" + num_pages
				+ ",current_page" + current_page + "," + results + "]";
	}
}
