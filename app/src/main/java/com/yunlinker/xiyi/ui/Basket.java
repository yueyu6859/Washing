package com.yunlinker.xiyi.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue.RequestFilter;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.yunlinker.xiyi.Adapter.BasketAdapter;
import com.yunlinker.xiyi.bean.BasketBean;
import com.yunlinker.xiyi.bean.USERBean;
import com.yunlinker.xiyi.bean.basketItem;
import com.yunlinker.xiyi.bean.discountsbean;
import com.yunlinker.xiyi.bean.ordersBean;
import com.yunlinker.xiyi.utils.ArrayOppositeser;
import com.yunlinker.xiyi.utils.DialogHint;
import com.yunlinker.xiyi.utils.LoadingDialog;
import com.yunlinker.xiyi.vov.Baseparam;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

/**
 * 洗衣篮
 * 
 * @author Administrator
 *
 */
public class Basket extends Fragment implements OnClickListener {
	private final String TAG=Basket.class.getName();
	File basket;
	//支付点击按钮点击一次
	boolean isMsgSend=true;
	HomeActivity activity;
	private DialogHint mDialog;
	LoadingDialog 	dialogs;
	String password,UserId;
	SharedPreferences sharedPreferences;
	private  String mprice;
	private  DialogHint  dialog;
	SharedPreferences sp1;
	SharedPreferences shareds ;
	private final int RESULT_OK = 0;
	// 请求队列
//	private RequestQueue queue;
	private String mcookie;
	private BasketAdapter adapter;
	private View view;
	// 返回上一级
	private ImageButton return2;
	// 填写信息.洗衣劵
	private LinearLayout address, ll_xiyijuan;
	// 点击后的洗衣劵
	private RelativeLayout xiyijuan_btn;
	// 删除订单
	private Button del_order;
	// 折后金额
	private TextView allmoney,mmoenys;
	// 洗衣劵剩余天数
	private TextView mday;
	// 洗衣劵id,地址Id
	String id, LocationID;
	// 确认支付
	private Button pay;
	private TextView addresseeName; // 收货人姓名
	private TextView addresseephone; // 收货人区地址ַ
	private TextView detailAddress; // 收货人详细地址ַ
	private TextView allprice; // 费用总计
	private TextView title_left; // title左标题,返回
	private ListView lanzi; // 商品列表

	// 客户填写地址信息显示
	private TextView names, phones, location;
	private RelativeLayout have_location;
	private TextView finalTotalPrice;

	// 没有商品的时候
	private ImageView iv_empty;
	SharedPreferences sp2;

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
  
	}
	
   
	@Override
	public void onResume() {

		super.onResume();
		new ArrayOppositeser().ArrayOppositeser();
		JsonObjectRequest jsonObjectPostRequest = new JsonObjectRequest(Request.Method.GET,Baseparam.USER+UserId+"/", new Listener<JSONObject>(){

			@Override
			public void onResponse(JSONObject response) {
			
				
			
				try {
					String  code=response.getString("status_code");
					if("403".equals(code)){
						mDialog =new  DialogHint(getActivity(), "身份信息验证失效,请重新登陆",R.style.LoadingDialog);
						mDialog.show();
						mDialog.setCancelable(true);// 让progressDialog点击返回键...
						mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
						
						//延迟关闭
						new Handler().postDelayed(new Runnable(){

							@Override
							public void run() {
								mDialog.dismiss();
								Intent  itent=new  Intent(getActivity(), MainActivity.class);
							     startActivity(itent);
							     getActivity().finish();
							}}, 1000);
						
						 
					}else if("1".equals(code)){
					 String  v=response.getJSONObject("data").getString("has_offer_password");
					
					 if(!v.equals("")){
						 Editor  s=sharedPreferences.edit();
						 s.putString("has_offer_password", v);
						 s.commit();
					 }
					
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}}, new Response.ErrorListener(){

				@Override
				public void onErrorResponse(VolleyError error) {
					
					
				}}){
			@Override
			public Map<String, String> getHeaders()
					throws AuthFailureError {
				HashMap localHashMap = new HashMap();
				localHashMap.put("Cookie", mcookie );
				return localHashMap;
			}
		};
//		queue.add(jsonObjectPostRequest);
		HomeActivity.startRequest(jsonObjectPostRequest, TAG);		
		
		// adapter.notifyDataSetChanged();
		adapter = new BasketAdapter(getActivity(), HomeActivity.list, handler,
				lanzi, allprice);
		lanzi.setAdapter(adapter);
		
		if (HomeActivity.list.size() == 0) {
			
			iv_empty.setVisibility(View.VISIBLE);
			del_order.setVisibility(View.INVISIBLE);
			activity.basket_have.setVisibility(View.INVISIBLE);
			
		}

		setListViewHeightBasedOnChildren(lanzi);
		adapter.notifyDataSetChanged();

//		// 洗衣劵
//		 sp1 = getActivity().getSharedPreferences("xiyijuan",
//				Context.MODE_PRIVATE);
		id = sp1.getString("id", "");
		String time = sp1.getString("time", "");
		 mprice=sp1.getString("price", "");
		 String price=allprice.getText().toString().replaceAll("¥", "");
			String pric=price.trim();
			if((Integer.parseInt(pric))<28){
				Editor  r=sp1.edit();
				r.clear();
				r.commit();}
		   if (!id.equals("")&&id!=null) {
			
			
			xiyijuan_btn.setVisibility(View.VISIBLE);
			xiyijuan_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i=new Intent(getActivity(), XiYiJuan.class);
					i.putExtra("xiyijuan_btn", "1");
					startActivity(i);
				}
			});
			ll_xiyijuan.setVisibility(View.INVISIBLE);
			mday.setText(time);
			mmoenys.setText(mprice);
		}
		
			
	
	

		// 地址
        
		SharedPreferences sp = getActivity().getSharedPreferences("locations",
				Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		String mname = sp.getString("name", "");
		String phone = sp.getString("phone", "");
		String locations = sp.getString("location", "");
		String housing = sp.getString("housing", "");
		
		LocationID = sp.getString("id", "");
		Log.e("mname!!! onresume", "!!!!" + mname);
		if (!mname.equals("")) {
			have_location.setVisibility(View.VISIBLE);
			address.setVisibility(View.INVISIBLE);
			names.setText(mname);
			phones.setText(phone);
			location.setText(housing +","+locations);
			have_location.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent inetnt3 = new Intent(getActivity(), Location.class);
					inetnt3.putExtra("Locations", "3");
					startActivity(inetnt3);

				}
			});
		}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.basket_for, null);
		new ArrayOppositeser().ArrayOppositeser();
		isMsgSend=true;
		lanzi = (ListView) view.findViewById(R.id.lanzi);
		pay = (Button) view.findViewById(R.id.pay);
		address = (LinearLayout) view.findViewById(R.id.linearLayout1);
		iv_empty = (ImageView) view.findViewById(R.id.iv_empty);
		return2 = (ImageButton) view.findViewById(R.id.return2);
		ll_xiyijuan = (LinearLayout) view.findViewById(R.id.ll_xiyijuan);
		xiyijuan_btn = (RelativeLayout) view.findViewById(R.id.xiyijuan_btn);
		del_order = (Button) view.findViewById(R.id.del_order);
		allprice = (TextView) view.findViewById(R.id.allprice);
		allmoney = (TextView) view.findViewById(R.id.allmoney);
		mday = (TextView) view.findViewById(R.id.mday);
		mmoenys=(TextView) view.findViewById(R.id.mmoenys);
	     activity=(HomeActivity) getActivity();
//	     SharedPrefsHelper.getInstance().doSave();
	    
//		if(HomeActivity.getInstance() != null&&HomeActivity.getInstance().basket_have != null){
//		HomeActivity.getInstance().basket_have.setVisibility(View.VISIBLE);
//		}
		// 取出之前保存的cookie
				 sharedPreferences = getActivity()
						.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
				mcookie = sharedPreferences.getString("mcookie", "");
				UserId=sharedPreferences.getString("id", "");
				password = sharedPreferences.getString("has_offer_password", "");
				Log.d("修改密码", "s"+password);
				Log.d("cookei111wwwww", mcookie);
		//洗衣劵
//		sp2=getActivity().getSharedPreferences("xiyijuan",
//				Context.MODE_APPEND);
				sp1=getActivity().getSharedPreferences("xiyijuan",
					Context.MODE_APPEND);
		
		shareds = getActivity().getSharedPreferences(
				"order", Context.MODE_PRIVATE);
//		queue = Volley.newRequestQueue(getActivity());
		MyApplication.getInstance().getQueue(getActivity());
		have_location = (RelativeLayout) view.findViewById(R.id.have_location);
		names = (TextView) view.findViewById(R.id.basket_user_name);
		phones = (TextView) view.findViewById(R.id.basket_user_tel);
		location = (TextView) view.findViewById(R.id.basket_user_address);
		

		locations();

		if (HomeActivity.list.size() == 0) {
			iv_empty.setVisibility(View.VISIBLE);
			del_order.setVisibility(View.INVISIBLE);
			activity.basket_have.setVisibility(View.INVISIBLE);
		}

		adapter = new BasketAdapter(getActivity(), HomeActivity.list, handler,
				lanzi, allprice);
		lanzi.setAdapter(adapter);
		

		return2.setOnClickListener(this);
		pay.setOnClickListener(this);
		address.setOnClickListener(this);
		ll_xiyijuan.setOnClickListener(this);
		del_order.setOnClickListener(this);
		
	  basket=new File("/data/data/com.yunlinker.xiyixi/basket.ser");

	
		return view;
	}

	/**
	 * 用户填写的地址信息
	 */
	private void locations() {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linearLayout1:
			
			if(mcookie.equals("")){
				Intent  intent=new  Intent(getActivity(), MainActivity.class);
				startActivity(intent);
			}else{
			Intent intent = new Intent(getActivity(), Location.class);
			startActivity(intent);}
			// getActivity().finish();
			break;

		case R.id.pay:
			if(!isMsgSend)
				return;
			isMsgSend=false;
//			SharedPreferences sp = getActivity().getSharedPreferences(
//					"userinfo", Context.MODE_PRIVATE);
			
			if(mcookie.equals("")){
				isMsgSend=true;
				Intent  intents=new Intent(getActivity(), MainActivity.class);
				intents.putExtra("homeactivty", "basekt");
				startActivity(intents);
			}else{       
            if(LocationID.equals("")){
            	isMsgSend=true;
            	dialog=new DialogHint(getActivity(), "地址不能为空！", R.style.LoadingDialog);
   			 dialog.show();
   			 dialog.setCancelable(true);// 让progressDialog点击返回键...
   			 dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
   			//延迟关闭
   				new Handler().postDelayed(new Runnable(){

   					@Override
   					public void run() {
   						dialog.dismiss();
   						
   					}}, 1000);
            
            }else{   
			
            
   				orders();
			
			
			
			}
			}
            break;
            
            

		case R.id.return2:
			// 实现fragment之间的跳转拿到管理器
//			Intent intent3 = new Intent(getActivity(), HomeActivity.class);
//			startActivityForResult(intent3, 1);
			
			
			activity.mTabHost.setCurrentTab(0);
		
			break;

		case R.id.ll_xiyijuan:
			String price=allprice.getText().toString().replaceAll("¥", "");
			String pric=price.trim();
			if((Integer.parseInt(pric))<28){
				mDialog =new  DialogHint(getActivity(), "订单金额需满28元，才能使用优惠卷",R.style.LoadingDialog);
				mDialog.show();
				mDialog.setCancelable(true);// 让progressDialog点击返回键...
				mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
				
				//延迟关闭
				new Handler().postDelayed(new Runnable(){

					@Override
					public void run() {
						mDialog.dismiss();
					}}, 1000);
			}else{
			Intent inetnt2 = new Intent(getActivity(), XiYiJuan.class);
			inetnt2.putExtra("xiyijuan_btn", "1");
//			startActivityForResult(inetnt2, 2);
			startActivity(inetnt2);}
			break;
		// 删除订单
		case R.id.del_order:
			Editor edit = sp1.edit();
			edit.clear();
			edit.commit();
			
			basket.delete();
			HomeActivity.list.removeAll(HomeActivity.list);
			dialog=new DialogHint(getActivity(), "删除成功！", R.style.LoadingDialog);
  			 dialog.show();
  			 dialog.setCancelable(true);// 让progressDialog点击返回键...
  			 dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
  			//延迟关闭
  				new Handler().postDelayed(new Runnable(){

  					@Override
  					public void run() {
  						dialog.dismiss();
  						
  					}}, 1000);
			Log.d("集合数量", "" + HomeActivity.list.size());
			iv_empty.setVisibility(View.VISIBLE);
			del_order.setVisibility(View.INVISIBLE);
			activity.basket_have.setVisibility(View.INVISIBLE);
			///////////////////////////////////////////////////
			StringRequest stringRequest = new StringRequest(Method.GET,
					Baseparam.DISCOUNTS + UserId, new Listener<String>() {

						@Override
						public void onResponse(String response) {
							// Log.d("HomeActivty--优惠劵", response);
							GetListByjsond(response);

						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							// TODO Auto-generated method stub

						}
					}) {
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					HashMap localHashMap = new HashMap();
					localHashMap.put("Cookie", mcookie);
					return localHashMap;
				}
			};
//			queue.add(stringRequest);
			HomeActivity.startRequest(stringRequest, TAG);		
		}

	}

	protected void GetListByjsond(String response) {
		JSONObject jsonobject;
		try {
			jsonobject = new JSONObject(response);
			String dataList = jsonobject.getString("data");
			Gson gson = new Gson();
			discountsbean dis = gson.fromJson(dataList, discountsbean.class);
			activity.result = dis.getResults();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 10) {// 更改商品的总价格
				float price = (Float) msg.obj;
				if (price > 0) {
					allprice.setText("¥" + (int)price);
				 if(price<28){
					 xiyijuan_btn.setVisibility(View.GONE);
					 ll_xiyijuan.setVisibility(View.VISIBLE);
//					 allmoney.setText(""+(int)price);
//					 SharedPreferences	 sp1 = getActivity().getSharedPreferences("xiyijuan",
//								Context.MODE_PRIVATE);
					 Editor  s=sp1.edit();
					 s.clear();
					 s.commit();
					 allmoney.setText(""+(int)price);
				 }
			}else if(price==0){
				iv_empty.setVisibility(View.VISIBLE);
				del_order.setVisibility(View.INVISIBLE);
				activity.basket_have.setVisibility(View.INVISIBLE);
			}
               if (!id.equals("")){
            	   if((int)price<28){
            		   Log.d("我的价格","DE"+(int)price);
            		   
            		  
            		   Editor  s=sp1.edit();
  					 s.clear();
  					 s.commit();
  					mprice="";
  					id="";
  					 allmoney.setText(""+(int)price);
  					 
  					Log.d("我的价格1","DE"+mprice);
            	   }else if((int)price>28){
            		   Log.d("我的价格2s","er"+mprice);
            		
            	   int p=(int)price-Integer.parseInt(mprice);
            	   Log.d("我的价格2","er"+p);
            	   if(p>0){
            		   allmoney.setText(""+p);
            	   }
//            	   else{
//            		   allmoney.setText("0");
//            	   }
            	   }
            	  
               }else{
				allmoney.setText("" + (int)price);}
			}
		}
	};

	// listView自定义高度
	public void setListViewHeightBasedOnChildren(ListView lanzi) {

		ListAdapter listAdapter = lanzi.getAdapter();

		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, lanzi);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = lanzi.getLayoutParams();

		params.height = totalHeight
				+ (lanzi.getDividerHeight() * (listAdapter.getCount() - 1));

		((MarginLayoutParams) params).setMargins(0, 0, 0, 0); // 可删

		lanzi.setLayoutParams(params);
	}

	// 提交订单

	private void orders() {
		
		List<basketItem> mitem = new ArrayList<basketItem>();
		BasketBean bean = null;
		for (int i = 0; i < HomeActivity.list.size(); i++) {
			bean = HomeActivity.list.get(i);
			basketItem Item = new basketItem();
			Item.setNum(bean.getNumber());
			Item.setProduct_id(bean.getXiYiid());
			mitem.add(Item);
		}

		Log.d("提交的数据的长度", "" + mitem.size());
		HashMap<String, Object> mMap = new HashMap<String, Object>();

		mMap.put("items", mitem);

		mMap.put("address_id", LocationID);

		mMap.put("discount_id", id);
		Gson gson = new Gson();
		String s = gson.toJson(mMap);
		JSONObject jsonRequest = null;
		try {
			jsonRequest = new JSONObject(s);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JsonObjectRequest jsonObjec = new JsonObjectRequest(
				Request.Method.POST, Baseparam.ORDERS, jsonRequest,
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d("提交的订单", "" + response);
						try {
							String code=response.getString("status_code");
							
							if("1".equals(code)){
								obtainorder(response);
								Log.d("过去没", "00000");
								}else if("403".equals(code)){
									dialog=new DialogHint(getActivity(), "身份信息验证失效,请重新进行登陆", R.style.LoadingDialog);
						   			 dialog.show();
						   			 dialog.setCancelable(true);// 让progressDialog点击返回键...
						   			 dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
						   			//延迟关闭
						   				new Handler().postDelayed(new Runnable(){

						   					@Override
						   					public void run() {
						   						dialog.dismiss();
						   						Intent   intent=new  Intent(getActivity(), MainActivity.class);
						   						
						   						startActivity(intent);
						   						
						   					}}, 1000);
								}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(getActivity(), "网络不稳定，请求失败", 0).show();
					}
				}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {

				HashMap localHashMap = new HashMap();
				localHashMap.put("Cookie", mcookie);
				
				return localHashMap;
			}
		};
//		queue.add(jsonObjectPostRequest);
		HomeActivity.startRequest(jsonObjec, TAG);
	}

	protected void obtainorder(JSONObject response) {
		String mdata = null;
		Gson gson = null;
		try {
			mdata = (response).getString("data");
			
			gson = new Gson();
			if(!mdata.equals("")){
			ordersBean bean = gson.fromJson(mdata, ordersBean.class);
			String id = bean.getId();
			Log.e("提交订单的值有没有", id);
			
			Editor e = shareds.edit();
			e.putString("order_num",bean.getOrder_num());
			e.putString("Price", bean.getPrice());
			e.putString("orderid", id);
			e.commit();
			HomeActivity.list.clear();
			
//			Log.d("密码情况", "1"+password);
//			if (password.equals("false")) {
//
//				Intent intent2 = new Intent(getActivity(), StartPay.class);
//				intent2.putExtra("star", "basket");
//				Editor edit = sp2.edit();
//				edit.clear();
//				edit.commit();
//				startActivity(intent2);
//				
//			} else {
			Editor edit = sp1.edit();
			edit.clear();
			edit.commit();
			dialogs=new LoadingDialog(getActivity(), "正在提交订单....", R.style.LoadingDialog);
  			 dialogs.show();
  			 dialogs.setCancelable(true);// 让progressDialog点击返回键...
  			 dialogs.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
  			//延迟关闭
  				new Handler().postDelayed(new Runnable(){

  					@Override
  					public void run() {
  						dialogs.dismiss();
  						basket.delete();
  						Intent intent1 = new Intent(getActivity(), Pay.class);
  						
  						startActivity(intent1);
  					}}, 1000);	
				
//				}
			
			
			
			
			
			
			
			
			}
		} catch (JSONException e) {

			e.printStackTrace();
		} 

	}

	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(iv_empty!=null){
		Drawable	s=iv_empty.getDrawable();
		if (s != null){
			s.setCallback(null);
		}
		
		}
//		queue.cancelAll(getActivity());
//		queue.stop();
		System.gc();
		HomeActivity.camcelRequest(TAG);
	}

}
