package com.yunlinker.xiyi.bean;

import java.util.List;


//获取订单
public class GetOrdersbean {
	public String status_code;
    public Data data;

    public class Data{
        public String count;
        public String num_pages;
        public String current_page;
        public List<Results> results;

        public class Results{
            public String id;
            public List<Item> items;
            public String discount;
            public Address address;
            public String price;
            public String created;
            public String take_time;
            public String sned_time;

            public String take_time_end;
            public String sned_time_end;
            public String order_num;
            public String status;
            public String modified;

            public class Address{
                public String id;
                public String name;
                public String tel;
                public String detail_address;
                public Area area;

                public class Area{
                    public String id;
                    public String name;
                    public String detail;
                }
            }

            public class Item{
                public String id;
                public String product_id;
                public String name;
                public String image;
                public String price;
                public String num;
            }
        }
    }
	
}
