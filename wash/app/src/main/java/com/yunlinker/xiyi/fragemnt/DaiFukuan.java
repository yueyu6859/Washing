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
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.yunlinker.xiyixi.R;
import com.yunlinker.xiyi.Adapter.AccomplishAdapter;
import com.yunlinker.xiyi.Adapter.DaiFuKuanAdapter;
import com.yunlinker.xiyi.bean.BasketBean;
import com.yunlinker.xiyi.bean.GetOrdersbean;
import com.yunlinker.xiyi.bean.GetOrdersbean.Data.Results;
import com.yunlinker.xiyi.bean.GetOrdersbean.Data.Results.Item;
import com.yunlinker.xiyi.bean.orderdelbean;
import com.yunlinker.xiyi.ui.HomeActivity;
import com.yunlinker.xiyi.ui.MainActivity;
import com.yunlinker.xiyi.ui.MyOrderMain;
import com.yunlinker.xiyi.ui.Orderdetail;
import com.yunlinker.xiyi.ui.Pay;
import com.yunlinker.xiyi.vov.Baseparam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//待付款
public class DaiFukuan extends   Fragment{
	private  SharedPreferences shareds;
	private   List<Results>  Order=new ArrayList<Results>();
	private String mcookie;
	private final String TAG =DaiFukuan.class.getName();
	private  ListView   listview;
	private TextView text;
  
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.daifukuan, null);
		listview=(ListView) view.findViewById(R.id.order0);
		text=(TextView) view.findViewById(R.id.textdaifukuan);
		
		
		//订单号
		shareds=getActivity().getSharedPreferences("order", Context.MODE_APPEND);

		
		
		
		
		// 取出之前保存的cookie
				SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userinfo",
						Context.MODE_PRIVATE);
				mcookie = sharedPreferences.getString("mcookie", "");
		      Order();
		      
		    //支付订单id
		    shareds = getActivity().getSharedPreferences(
						"order", Context.MODE_APPEND);
		      
		    
		    
		    listview.setOnItemClickListener(new OnItemClickListener() {
		    	Results odrer;
	
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					
					
					odrer=(Results) listview.getItemAtPosition(position);
					
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
     			    String   names=odrer.address.name;
					String   pohes=odrer.address.tel;
					String   loaction=odrer.address.detail_address;
					//订单号
					String Order_num=odrer.order_num;
					
					String  orderid=odrer.id;
					//优惠劵
					String   Discount=odrer.discount;
				
					//金额
					String   allprice=odrer.price;
					Intent  intent=new  Intent(getActivity(), Orderdetail.class);
				
					intent.putExtra("Order_num", Order_num);
					intent.putExtra("allprice",allprice);
					intent.putExtra("Discount", Discount);
					intent.putExtra("orderid", orderid);
					intent.putExtra("zhuangtai", "0");
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
		
		StringRequest stringRequest = new StringRequest(Method.GET, Baseparam.GET_ORDERS+"0", new Listener<String>(){

			@Override
			public void onResponse(String response) {
				Log.d("我的地址dsdsdsds", "d"+response);
				//返回的数据
			    
				haveresponse(response);
				
			}}, new Response.ErrorListener(){

				@Override
				public void onErrorResponse(VolleyError error) {
					Toast.makeText(getActivity(), "网络不稳定，请求失败", 0).show();
					
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
					}} catch (Exception e) {
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
		JSONObject jsonobject;
		
		GetOrdersbean b=JSONObject.parseObject(response, GetOrdersbean.class);	
        if(b.data!=null){
		Order=b.data.results;   
			 listview.setAdapter(new  DaiFuKuanAdapter(Order,getActivity(),text));
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
//		if(d!=null){
//		Bitmap s=d.getDrawingCache();
//		if ( s!= null&&!s.isRecycled()){
//			s.recycle();          s = null;
//		}
		MyOrderMain.camcelRequest(TAG);
		Order.clear();
		System.gc();
	
}
}