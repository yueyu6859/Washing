package com.yunlinker.xiyi.fragemnt;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.yunlinker.xiyi.Adapter.DaiQuJianAdapter;
import com.yunlinker.xiyi.Adapter.DaiSongJianAdapter;
import com.yunlinker.xiyi.bean.GetOrdersbean;
import com.yunlinker.xiyi.bean.GetOrdersbean.Data.Results;
import com.yunlinker.xiyi.bean.GetOrdersbean.Data.Results.Item;
import com.yunlinker.xiyi.bean.orderdelbean;
import com.yunlinker.xiyi.ui.HomeActivity;
import com.yunlinker.xiyi.ui.MyOrderMain;
import com.yunlinker.xiyi.ui.Orderdetail;
import com.yunlinker.xiyi.vov.Baseparam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 我的订单页面待送件
 * @author Administrator
 *
 */
public class DaiSongJian extends Fragment {
	private  String   oder_nums,sendtimes;
	private   List<Results>  OrderTwo=new ArrayList<Results>();
	private final String TAG =DaiSongJian.class.getName();
	private String mcookie;
	private  ListView  orderlist;
	private  TextView  text;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dai_song_jian, null);
		orderlist=(ListView) view.findViewById(R.id.orderlistTwo);
		text=(TextView) view.findViewById(R.id.textorder);
		// 取出之前保存的cookie
		SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userinfo",
				Context.MODE_PRIVATE);
		mcookie = sharedPreferences.getString("mcookie", "");
      Order();
     
      
      orderlist.setOnItemClickListener(new  OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Results odrer;
			TextView  oder_num=(TextView) arg1.findViewById(R.id.oder_num);
			oder_nums=oder_num.getText().toString();
			
			 odrer=(Results) orderlist.getItemAtPosition(arg2);
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
				intent.putExtra("order_datas",  oder_nums);
				intent.putExtra("mtake_times",  sendtimes);
				intent.putExtra("Discount", Discount);
				intent.putExtra("zhuangtai", "2");
				intent.putExtra("name", names);
				intent.putExtra("tel", pohes);
				intent.putExtra("address",  loaction);
				
				getActivity().startActivity(intent);
				
		}
	});
		return view;
	}

	private void Order() {
		//获取订单列表
		
				StringRequest stringRequest = new StringRequest(Method.GET, Baseparam.GET_ORDERS+"2",  new Listener<String>(){

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
	List<Results>list2=new ArrayList<Results>();
	protected void haveresponse(String response) {
		   GetOrdersbean b=JSONObject.parseObject(response, GetOrdersbean.class);
		   if(b.data!=null){
		   OrderTwo=b.data.results;
			 orderlist.setAdapter(new DaiSongJianAdapter(OrderTwo,getActivity(),text));
		   
		   }
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Order();
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MyOrderMain.camcelRequest(TAG);
		OrderTwo.clear();
	}
}
