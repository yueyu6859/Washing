package com.yunlinker.xiyi.ui;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunlinker.xiyi.bean.AddressBean;
import com.yunlinker.xiyi.bean.BasketBean;
import com.yunlinker.xiyi.bean.Discounts;
import com.yunlinker.xiyi.bean.HousingBean;
import com.yunlinker.xiyi.bean.HousingResults;
import com.yunlinker.xiyi.bean.PasswordQuestionsBean;
import com.yunlinker.xiyi.bean.discountsbean;
import com.yunlinker.xiyi.utils.ArrayOppositeser;
import com.yunlinker.xiyi.utils.ArraySer;
import com.yunlinker.xiyi.utils.BaseFragmentActivity;
import com.yunlinker.xiyi.vov.Baseparam;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

public class HomeActivity extends BaseFragmentActivity  {
	private static RequestQueue queue;
	public static HomeActivity instance;
	//洗衣篮小红点
	public  ImageView  basket_have;
	SharedPreferences mp;
	// 保存在洗衣篮里的洗衣劵，当退出时，清除
	private Editor ed;
	AddressBean.area s;
	// 小区范围
	public static List<HousingResults> results = new ArrayList<HousingResults>();
	// 密码问题
	public static List<PasswordQuestionsBean> Questions = new ArrayList<PasswordQuestionsBean>();
	// 洗衣劵
	public static List<Discounts> result = new ArrayList<Discounts>();
	// 创建请求队列
	// private RequestQueue queue;
	String Cookies, ids;
	// 洗衣篮集合
	public static ArrayList<BasketBean> list = new ArrayList<BasketBean>();
    
	public FragmentTabHost mTabHost = null;;
	private View indicator = null;
	private  View indicator1=null;
	private  View indicator2=null;
	private View myAccountView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		
		
		Intent in = getIntent();
		String s = in.getStringExtra("renturns");
		// queue = Volley.newRequestQueue(HomeActivity.this);
		MyApplication.getInstance().getQueue(HomeActivity.this);
		 mp = HomeActivity.this.getSharedPreferences(
				"userinfo", Context.MODE_PRIVATE);
		ids = mp.getString("id", "");
		
		Cookies = mp.getString("mcookie", "");
		// 初始化一个请求队列
		queue = Volley.newRequestQueue(getApplicationContext());
		inivite();
		// 方便后续退出整个应用程序
//		MyApplication.getInstance().addFragmentActivity(HomeActivity.this);
		// 密码提示问题
		Questions();
		// 获取优惠劵
		getdiscounts();
		// 获取小区范围
		gethousing();
		
	
		// 保存在洗衣篮里的洗衣劵，当退出时，清除
		SharedPreferences sp1 = HomeActivity.this.getSharedPreferences(
				"xiyijuan", Context.MODE_PRIVATE);
		ed = sp1.edit();
		
		new ArrayOppositeser().ArrayOppositeser();
		if(list.size()>0){
			basket_have.setVisibility(View.VISIBLE);
		}
		// 获取当前用户地址信息
				getlocation();
	}
	
	/**
	 * 开始请求
	 * 
	 * @param r
	 * @param tag
	 * @return
	 */
	public static <T> Request<T> startRequest(Request<T> r, Object tag) {
		// 给传来的请求设置TAG
		r.setTag(tag);

		Request<T> request = queue.add(r);

		queue.start();

		return request;
	}
	/**
	 * 取消请求
	 * 
	 * @param tag
	 */
	public static void camcelRequest(Object tag) {
		queue.cancelAll(tag);
	}
	


	private void Questions() {
		JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET,
				Baseparam.PASSWORDQUESTIONS, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						
						try {
							String data = response.getString("data");
							Gson gson = new Gson();
							Questions = gson
									.fromJson(
											data,
											new TypeToken<List<PasswordQuestionsBean>>() {
											}.getType());

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});

		MyApplication.getInstance().getQueue(HomeActivity.this).add(json);

	}

	boolean isTurnToLogin = false;

	@Override
	public void onResume() {
		
		super.onResume();
		Intent e=getIntent();
		if(e!=null){
        	String vs=e.getStringExtra("mianactivty");
        	if(vs!=null&&"main".equals(vs)){
        	mTabHost.setCurrentTab(1);}
        }
		
		//获取洗衣劵
		getdiscounts();
		// 获取小区范围
				gethousing();
		// 密码提示问题
				Questions();
		
		Log.d("Cookies信息", Cookies);
		if (!"".equals(Cookies) && Cookies != null && isTurnToLogin
				) {
			
			myAccountView.setOnClickListener(null);
			mTabHost.newTabSpec("my").setIndicator(myAccountView);
			mTabHost.setCurrentTab(0);
			mTabHost.getTabWidget().getChildAt(2)
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							mTabHost.setCurrentTab(2);
						}

					});
			isTurnToLogin = false;
		}
		
		if(list.size()!=0){
			basket_have.setVisibility(View.VISIBLE);
		}else if(list.size()==0){
			basket_have.setVisibility(View.INVISIBLE);
		}
		// 获取当前用户地址信息
				getlocation();
	}

	// 获取当前用户地址信息
	private void getlocation() {
		StringRequest mstringRequests = new StringRequest(Method.GET,
				Baseparam.URL_GET_ADDRESS, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						// Log.d("HomeActivty当前用户地址信息", response);
						
						getserverloaction(response);
                       
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}
				}) {
			// 转为utf——8
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

			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap localHash = new HashMap();
				localHash.put("Cookie", Cookies);
				return localHash;
			}
		};
		// queue.add(mstringRequests);
		MyApplication.getInstance().getQueue(HomeActivity.this)
				.add(mstringRequests);
	}

	
	
	
	
	
	
	
	
	// 服务器返回的地址信息
	protected void getserverloaction(String response) {
		JSONObject jsonobject;

		try {
			jsonobject = new JSONObject(response);
			String mdata = jsonobject.getString("data");

			// String areaid="";
			Gson gson = new Gson();
			if (mdata!=null&&!"".equals(mdata)) {
				String area = jsonobject.getJSONObject("data")
						.getString("area");
				AddressBean address = gson.fromJson(mdata, AddressBean.class);

				s = gson.fromJson(area, AddressBean.area.class);

				SharedPreferences sharedPreferences = this
						.getSharedPreferences("locations", Context.MODE_APPEND);
				Editor editor = sharedPreferences.edit();// 达到编辑器
				editor.putString("name", address.getName());
				editor.putString("location", address.getDetail_address());
				editor.putString("phone", address.getTel());
				editor.putString("housingId", s.getId());
				editor.putString("housing", s.getName());

				editor.putString("id", address.getId());
				editor.commit();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    
	// 获取小区范围
	private void gethousing() {
		// 获取小区
		StringRequest stringRequests = new StringRequest(Method.GET,
				Baseparam.AREAS, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						// Log.d("HomeActivty--小区范围",response);
						GetListjson(response);

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}
				}) {
			// 转为utf——8
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

			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap localHash = new HashMap();
				localHash.put("Cookie", Cookies);
				return localHash;
			}

		};
		// queue.add(stringRequests);
		MyApplication.getInstance().getQueue(HomeActivity.this)
				.add(stringRequests);
	}

	// 小区范围解析
	HousingBean hous;

	protected void GetListjson(String response) {
		JSONObject jsonobject;
		try {
			jsonobject = new JSONObject(response);
			String dataList = jsonobject.getString("data");
			Gson gson = new Gson();
			hous = gson.fromJson(dataList, HousingBean.class);
			results = hous.getResults();
		} catch (JSONException e) {

			e.printStackTrace();
		} finally {

		}

	}

	// 初始化
	private void inivite() {
		
//		instance=new  HomeActivity();
		
		
		
		
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		indicator = getIndicatorView("首页", R.layout.home_indicator);
		mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator(indicator),
				Home.class, null);
         
		
		
		indicator1 = getIndicatorView("洗衣篮", R.layout.basket_indicator);
		mTabHost.addTab(mTabHost.newTabSpec("basket").setIndicator(indicator1),
				Basket.class, null);
		basket_have=(ImageView) indicator1.findViewById(R.id.basket_have);
		

		indicator2 = getIndicatorView("我", R.layout.my_indicator);
		myAccountView = indicator2;
		mTabHost.addTab(mTabHost.newTabSpec("my").setIndicator(myAccountView),
				My.class, null);
		Log.d("初始cookie", Cookies);
		if ("".equals(Cookies) || Cookies == null) {
			
			isTurnToLogin = true;
			myAccountView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
//					startActivity(new Intent(HomeActivity.this,
//							MainActivity.class));
					Intent i=new Intent(HomeActivity.this,MainActivity.class);
					i.putExtra("homeactivty", "my");
					startActivity(i);
				}
			});
		}
		indicator = getIndicatorView("更多", R.layout.more_indicator);
		mTabHost.addTab(mTabHost.newTabSpec("more").setIndicator(indicator),
				More.class, null);

		Intent s = getIntent();
		String v = s.getStringExtra("renturns");
		
		if (v != null && v.equals("1")) {
			mTabHost.setCurrentTab(1);
			
		}
		// // 设置Tab按钮的背景
		// mTabHost.getTabWidget().getChildAt(4).setBackgroundColor(Color.WHITE);
	}

	
	
	
	
	
	
	
	// 获取优惠劵
	private void getdiscounts() {

		StringRequest stringRequest = new StringRequest(Method.GET,
				Baseparam.DISCOUNTS + ids, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						 Log.d("HomeActivty--优惠劵", "我的洗衣劵"+response);
						GetListByjson(response);

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
				localHashMap.put("Cookie", Cookies);
				return localHashMap;
			}
		};
		// queue.add(stringRequest);
		MyApplication.getInstance().getQueue(HomeActivity.this)
				.add(stringRequest);

	}

	protected void GetListByjson(String response) {
		JSONObject jsonobject;
		try {
			jsonobject = new JSONObject(response);
			String dataList = jsonobject.getString("data");
			Gson gson = new Gson();
			discountsbean dis = gson.fromJson(dataList, discountsbean.class);
			result = dis.getResults();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
   
	private View getIndicatorView(String name, int layoutId) {
		View v = getLayoutInflater().inflate(layoutId, null);
		TextView tv = (TextView) v.findViewById(R.id.tabText);
		tv.setText(name);
		return v;
	}
	
	
	 private long exitTime = 0;  
	  
//	    @Override  
//	    public boolean onKeyDown(int keyCode, KeyEvent event) {  
//	        if (KeyEvent.KEYCODE_BACK == keyCode) {  
//	            // 判断是否在三秒之内连续点击返回键，是则退出，否则不退出  
//	            if (System.currentTimeMillis() - exitTime > 3000) {  
//	                Toast.makeText(getApplicationContext(), "再按一次退出程序",  
//	                        Toast.LENGTH_SHORT).show();  
//	                // 将系统当前的时间赋值给exitTime  
//	                exitTime = System.currentTimeMillis();  
//	            } else {  
//	            	finish();
//	            	MyApplication.getInstance().exit();
//	            }  
//	            return true;  
//	        }  
//	        return super.onKeyDown(keyCode, event);  
//	    }  
	


	// 按两次返回键退出登陆
	// 定义退出程序boolen类型
	private boolean isExit = false;

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)// 当keyCode等于退出事件值时
		{
			exit();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
	

	private void exit() {
		if (isExit) {



			ed.clear();
			ed.commit();
			this.finish();
			MyApplication.getInstance().exit();
			

//			Intent intent = new Intent(Intent.ACTION_MAIN);
//			intent.addCategory(Intent.CATEGORY_HOME);
//			startActivity(intent);
//			finish();
//			System.exit(0);// 使虚拟机停止运行并退出程序
		} else {
			isExit = true;
			Toast.makeText(HomeActivity.this, "再按一次退出", Toast.LENGTH_SHORT)
					.show();
			mHandler.sendEmptyMessageDelayed(0, 3000);// 3秒后发送消息
		}

	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg)// 处理消息
		{
			super.handleMessage(msg);
			isExit = false;
		}
	};

	public void onDestroy() {
		super.onDestroy();
		mTabHost = null;
		// queue.cancelAll(HomeActivity.this);
		// queue.stop();
	};
}
