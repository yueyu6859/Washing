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
import com.google.gson.reflect.TypeToken;
import com.yunlinker.xiyixi.R;
import com.yunlinker.xiyi.Adapter.DaiQuJianAdapter;
import com.yunlinker.xiyi.bean.GetOrdersbean;
import com.yunlinker.xiyi.bean.GetOrdersbean.Data.Results;
import com.yunlinker.xiyi.bean.GetOrdersbean.Data.Results.Item;
import com.yunlinker.xiyi.bean.orderdelbean;
import com.yunlinker.xiyi.bean.HousingBean;
import com.yunlinker.xiyi.ui.HomeActivity;
import com.yunlinker.xiyi.ui.MainActivity;
import com.yunlinker.xiyi.ui.MyOrderMain;
import com.yunlinker.xiyi.ui.Orderdetail;
import com.yunlinker.xiyi.vov.Baseparam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 我的订单待取件页面
 * 
 * @author Administrator
 *
 */

public class DaiQuJian extends Fragment {
	private com.yunlinker.xiyi.utils.LoadingDialog mDialog;
	private   List<Results>  OrderOne=new ArrayList<Results>()  ;
	private String mcookie;
    private  ListView  orderlist;
	private final String TAG =DaiQuJian.class.getName();
	private  TextView  text1;
	private  String  order_data,take_times;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dai_qu_jian, null);
		orderlist=(ListView) view.findViewById(R.id.orderlist);
		text1=(TextView) view.findViewById(R.id.text1);
		// 取出之前保存的cookie
				SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userinfo",
						Context.MODE_PRIVATE);
				mcookie = sharedPreferences.getString("mcookie", "");
		      Order();
		     
		      mDialog = new com.yunlinker.xiyi.utils.LoadingDialog(getActivity(), "亲，请耐心等一会哦...",R.style.LoadingDialog);
				mDialog.show();
				mDialog.setCancelable(true);// 让progressDialog点击返回键...
				mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
				
        listview();
		return view;
	}
	
      
	private void listview() {
		orderlist.setOnItemClickListener(new OnItemClickListener() {
			Results odrer;
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			TextView	found_data=(TextView) arg1.findViewById(R.id.found_data);
			TextView  take_time=(TextView) arg1.findViewById(R.id.take_time);
		    order_data=found_data.getText().toString();
		    take_times=take_time.getText().toString();
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
			intent.putExtra("order_datas",  order_data);
			intent.putExtra("mtake_times",  take_times);
			intent.putExtra("Discount", Discount);
			intent.putExtra("zhuangtai", "1");
			intent.putExtra("name", names);
			intent.putExtra("tel", pohes);
			intent.putExtra("address",  loaction);
			getActivity().startActivity(intent);
			}
		});
		
	}


	private void Order() {
		//获取订单列表
		
		StringRequest stringRequest = new StringRequest(Method.GET, Baseparam.GET_ORDERS+"1",  new Listener<String>(){

			@Override
			public void onResponse(String response) {
				Log.d("待取件", response);
				//返回的数据
				haveresponse(response);
				
			}},new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					Toast.makeText(getActivity(), "网络不稳定，请求失败", 0).show();
					mDialog.dismiss();
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
		 OrderOne=b.data.results;
		 orderlist.setAdapter(new  DaiQuJianAdapter(OrderOne,getActivity(),text1));
			 mDialog.dismiss();
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
		MyOrderMain.camcelRequest(TAG);
		OrderOne.clear();
	}
}
