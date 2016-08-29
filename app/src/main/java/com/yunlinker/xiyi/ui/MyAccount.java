package com.yunlinker.xiyi.ui;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunlinker.xiyi.Adapter.XiYijuanAdapter;
import com.yunlinker.xiyi.bean.Discounts;
import com.yunlinker.xiyi.bean.Results;
import com.yunlinker.xiyi.bean.discountsbean;
import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyi.utils.DialogHint;
import com.yunlinker.xiyi.vov.Baseparam;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

//我的账户
public class MyAccount extends BaseActivity implements OnClickListener,
		Serializable {
	File  basket;
	private DialogHint mDialog;
	private final String TAG=MyAccount.class.getName();
	// private List<discountsbean>list;
	String  mcookie,userid;
	SharedPreferences shared;
	SharedPreferences s;
	private ImageButton retun13;

	// 创建请求队列
//	private RequestQueue queue;
	
	String Cookies;
	// 洗衣劵
	discountsbean dis;
	SharedPreferences sp2;
	private LinearLayout my_xiyibi;
	private LinearLayout my_xiyijuan;

	// 退出登陆
	private Button exist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_account);
		my_xiyibi = (LinearLayout) findViewById(R.id.my_xiyibi);
		my_xiyijuan = (LinearLayout) findViewById(R.id.my_xiyijuan);
		exist = (Button) findViewById(R.id.exist);
//		queue = Volley.newRequestQueue(MyAccount.this);
		MyApplication.getInstance().getQueue(MyAccount.this);

		retun13 = (ImageButton) findViewById(R.id.retun13);
		// 洗衣劵
				sp2 = MyAccount.this.getSharedPreferences("xiyijuan",
						Context.MODE_PRIVATE);
				
		shared=MyAccount.this.getSharedPreferences("locations", MODE_PRIVATE);
		basket=new File("/data/data/com.yunlinker.xiyixi/basket.ser");
		s=MyAccount.this.getSharedPreferences("userinfo", MODE_PRIVATE);
		mcookie = s.getString("mcookie", "");
		userid=s.getString("id", "");
		xiyibi();
		
	

		// 方便后续退出整个应用程序
//		MyApplication.getInstance().addActivity(MyAccount.this);

		retun13.setOnClickListener(this);
		my_xiyibi.setOnClickListener(this);
		my_xiyijuan.setOnClickListener(this);
		exist.setOnClickListener(this);
	}

	private void xiyibi() {
		JsonObjectRequest jsonObjectPostRequest = new JsonObjectRequest(Request.Method.GET,Baseparam.USER+userid+"/", new Listener<JSONObject>(){

			@Override
			public void onResponse(JSONObject response) {
				String mcredits= null;
				try {
					String code=response.getString("status_code");
					if("1".equals(code)){
					mcredits = response.getJSONObject("data").getString("credits");
					Editor  v=s.edit();
					v.putString("credits", mcredits);
					v.commit();
					}else if("403".equals("")){
						mDialog =new  DialogHint(MyAccount.this, "身份信息验证失效,请重新登陆",R.style.LoadingDialog);
						mDialog.show();
						mDialog.setCancelable(true);// 让progressDialog点击返回键...
						mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
						
						//延迟关闭
						new Handler().postDelayed(new Runnable(){

							@Override
							public void run() {
								mDialog.dismiss();
								Intent i=new  Intent(MyAccount.this, MainActivity.class);
								startActivity(i);
								MyAccount.this.finish();
							}}, 1000);
						
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
				localHashMap.put("Cookie", mcookie);
				return localHashMap;
			}
		};
//		queue.add(jsonObjectPostRequest);
		MyApplication.getInstance().getQueue(MyAccount.this).add(jsonObjectPostRequest);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_xiyibi:
			Intent intent = new Intent(this, XiYiBi.class);
			startActivity(intent);
			break;

		case R.id.my_xiyijuan:
			Intent intent1 = new Intent(this, XiYiJuan.class);
			startActivity(intent1);
			break;

		case R.id.exist:
			Editor vs=sp2.edit();
			vs.clear();
			vs.commit();
			
			Editor e=s.edit();
			e.clear();
			e.commit();
		
			
			Editor s=shared.edit();
			s.clear();
			s.commit();
			basket.delete();
			HomeActivity.results.clear();
			HomeActivity.list.removeAll(HomeActivity.list);
			HomeActivity.result.removeAll(HomeActivity.result);
//			MyApplication.getInstance().exit();
			Intent  i=new Intent(MyAccount.this, MainActivity.class);
			i.putExtra("MyAccount", "myaccount");
			startActivity(i);
			MyAccount.this.finish();
//			Intent intent5 = new Intent(Intent.ACTION_MAIN);
//			intent5.addCategory(Intent.CATEGORY_HOME);
//			startActivity(intent5);
//			System.exit(0);        
			
						
			break;

		case R.id.retun13:
			MyAccount.this.finish();
		}

	}

   @Override
protected void onResume() {
	super.onResume();
	xiyibi();
}
	@Override
	protected void onDestroy() {
		super.onDestroy();
//		queue.cancelAll(MyAccount.this);
		 MyApplication.getInstance().getQueue(MyAccount.this).cancelAll(TAG);
	}

	

}
