package com.yunlinker.xiyi.fragemnt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.yunlinker.xiyixi.R;
import com.yunlinker.xiyi.Adapter.AccomplishAdapter;
import com.yunlinker.xiyi.Adapter.DaiQuJianAdapter;
import com.yunlinker.xiyi.bean.GetOrdersbean;
import com.yunlinker.xiyi.bean.GetOrdersbean.Data.Results;
import com.yunlinker.xiyi.bean.GetOrdersbean.Data.Results.Item;
import com.yunlinker.xiyi.bean.orderdelbean;
import com.yunlinker.xiyi.ui.HomeActivity;
import com.yunlinker.xiyi.ui.MainActivity;
import com.yunlinker.xiyi.ui.MyOrderMain;
import com.yunlinker.xiyi.ui.Orderdetail;
import com.yunlinker.xiyi.vov.Baseparam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 我的订单已完成页面
 * 
 * @author Administrator
 *
 */
public class Accomplish extends Fragment {
	private String  order_data,mtime;
	private   List<Results>  OrderThree=new ArrayList<Results>();
	private String mcookie;
	private final String TAG =Accomplish.class.getName();
	private  ListView   listview;
	private TextView text;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.accomplish, null);
		listview=(ListView) view.findViewById(R.id.order3);
		text=(TextView) view.findViewById(R.id.textaccomplish);
		// 取出之前保存的cookie
		SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userinfo",
				Context.MODE_PRIVATE);
		mcookie = sharedPreferences.getString("mcookie", "");
      Order();
     
      
      listview.setOnItemClickListener(new OnItemClickListener() {
    	  Results odrer;
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			TextView v=(TextView) arg1.findViewById(R.id.found_data);
			TextView	time=(TextView) arg1.findViewById(R.id.take_time);
			mtime=time.getText().toString();
			order_data=v.getText().toString();
			 odrer=(Results) listview.getItemAtPosition(arg2);
				List<Item>particular=odrer.items;
				for(Item s:particular){
					orderdelbean bean = new orderdelbean();
					if(s.image!=null){
					bean.setImage(s.image);}
					bean.setName(s.name);
					bean.setNum(s.num);
					bean.setPrice(s.price);
					MyOrderMain.lists.add(bean);   
				}	
				//优惠劵
				String   Discount=odrer.discount;
				//金额
				String   allprice=odrer.price;
				String   names=odrer.address.name;
				String   pohes=odrer.address.tel;
				String   loaction=odrer.address.detail_address;
				Intent  intent=new  Intent(getActivity(), Orderdetail.class);
				intent.putExtra("allprice",allprice);
				intent.putExtra("order_datas",  order_data);
				intent.putExtra("mtake_times",  mtime);
				intent.putExtra("Discount", Discount);
				intent.putExtra("zhuangtai", "3");
				intent.putExtra("name", names);
				intent.putExtra("tel", pohes);
				intent.putExtra("address",  loaction);
								
				getActivity().startActivity(intent);
		}
	});
		return view;
	}
//	@Override
//	public void onResume() {
//		super.onResume();
//		if(OrderThree!=null){
//	    	  listview.setAdapter(new  AccomplishAdapter(OrderThree,getActivity()));
//	      }else{
//	    	  listview.setVisibility(View.INVISIBLE);
//	    		  text.setVisibility(View.VISIBLE);
//	      };
//	}
	private void Order() {
		//获取订单列表
		
				StringRequest stringRequest = new StringRequest(Method.GET, Baseparam.GET_ORDERS+"3",  new Listener<String>(){

					@Override
					public void onResponse(String response) {
						
						//返回的数据
						haveresponse(response);
						
					}},new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							
							
						}}){
					
						//转为utf——8
							protected final String TYPE_UTF8_CHARSET = "charset=UTF-8";
							protected Response<String> parseNetworkResponse(
									NetworkResponse response) {
								try {
									String type = response.headers.get(HTTP.CONTENT_TYPE);
									if (type == null) {
										type = TYPE_UTF8_CHARSET;
										response.headers.put(HTTP.CONTENT_TYPE, type);
									} else if (!type.contains("UTF-8")) {
										type += ";" + TYPE_UTF8_CHARSET;
										response.headers.put(HTTP.CONTENT_TYPE, type);
									}
								} catch (Exception e) {
								}
								return super.parseNetworkResponse(response);
							}
							             
											public Map<String, String> getHeaders()
													throws AuthFailureError {
												HashMap localHash = new HashMap();
												localHash.put("Cookie", mcookie);
												return localHash;
											}
					
				};

				MyOrderMain.startRequest(stringRequest , TAG);
			
				
		
	}
	List<Results>list=new ArrayList<Results>();
	protected void haveresponse(String response) {
		GetOrdersbean b=JSONObject.parseObject(response, GetOrdersbean.class);
		if(b.data!=null){
		 OrderThree=b.data.results;
			
			 listview.setAdapter(new  AccomplishAdapter(OrderThree,getActivity(),text));
		      
		}
	
		
	}
	@Override
	public void onResume() {
		super.onResume();
		Order();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		 OrderThree.clear();
		 MyOrderMain.camcelRequest(TAG);
	}
}
