package com.yunlinker.xiyi.bean;

//地址bean类
public class AddressBean {
	//

	private String id;
	private String name;

	private String tel;

	private String district;
	private   String detail_address;

	public class area{
		private  String id;
		private   String name;
		private  String  detail;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDetail() {
			return detail;
		}
		public void setDetail(String detail) {
			this.detail = detail;
		}
	};
	
	







	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getTel() {
		return tel;
	}




	public void setTel(String tel) {
		this.tel = tel;
	}




	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}




	public String getDetail_address() {
		return detail_address;
	}




	public void setDetail_address(String detail_address) {
		this.detail_address = detail_address;
	}
	
	
	
	

}
