package com.yunlinker.xiyi.ui;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.ErrorListener;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.yunlinker.xiyi.bean.logbean;
import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyi.utils.DialogHint;
import com.yunlinker.xiyi.utils.LoadingDialog;
import com.yunlinker.xiyi.vov.Baseparam;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

//验证推荐码
public class VerifyTuiJian extends BaseActivity implements OnClickListener {
	private final String TAG=VerifyTuiJian.class.getName();
	String  code;
	private  DialogHint  dialog;
	// 创建请求队列
//	private RequestQueue queue;
    
	String z="";
	// cookie
	private String mcookie;
	// 输入推荐码
	
	private EditText tuijian_import;
	private Button queren;
	// 返回
	private ImageButton return8;
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
//    	queue.cancelAll(VerifyTuiJian.this);
    	 MyApplication.getInstance().getQueue(VerifyTuiJian.this).cancelAll(TAG);
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verify_tuijian);
		return8 = (ImageButton) findViewById(R.id.return8);
		tuijian_import = (EditText) findViewById(R.id.tuijian_import);
		queren = (Button) findViewById(R.id.queren);
//		queue = Volley.newRequestQueue(this);

		// 方便后续退出整个应用程序
//		MyApplication.getInstance().addActivity(VerifyTuiJian.this);
		MyApplication.getInstance().getQueue(VerifyTuiJian.this);
//		queue = Volley.newRequestQueue(getApplicationContext());
		return8.setOnClickListener(this);
		queren.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return8:
			VerifyTuiJian.this.finish();
			break;

		case R.id.queren:

			String impo = tuijian_import.getText().toString();
			Log.d("输入的数据", impo);
			// 取出cookie
			SharedPreferences sharedPreferences = getSharedPreferences(
					"userinfo", Context.MODE_PRIVATE);
			mcookie = sharedPreferences.getString("mcookie", "");
			Log.d("mcookie", mcookie);
			HashMap<String, String> mMap = new HashMap<String, String>();
			mMap.put("code", impo);
			JSONObject jsonRequest = new JSONObject(mMap);
			JsonObjectRequest jsonObjectPostRequest = new JsonObjectRequest(
					Request.Method.POST, Baseparam.URL_INPUT_INVITE,
					jsonRequest, new Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							Log.d("获取优惠码返回的数据", "" + response);
							try {
								code=response.getString("status_code");
								if("1".equals(code)){
//									String  f=response.getString("status_code");
//									if(f!=null&&"1".equals(f)){
										String  money=response.getJSONObject("data").getString("credits");
										
									dialog=new DialogHint(VerifyTuiJian.this,"验证成功，获得总金额"+money+"元优惠劵", R.style.LoadingDialog);
										 dialog.show();
										 dialog.setCancelable(true);// 让progressDialog点击返回键...
										 dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
										//延迟关闭
											new Handler().postDelayed(new Runnable(){

												@Override
												public void run() {
													dialog.dismiss();
													VerifyTuiJian.this.finish();
												}}, 1000);
										
								}else if("1230".equals(code)){
									    
									    dialog=new DialogHint(VerifyTuiJian.this,"邀请码无效", R.style.LoadingDialog);
										 dialog.show();
										 dialog.setCancelable(true);// 让progressDialog点击返回键...
										 dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
										//延迟关闭
											new Handler().postDelayed(new Runnable(){

												@Override
												public void run() {
													dialog.dismiss();
													
												}}, 1000);
								}else if("1231".equals(code)){
								    dialog=new DialogHint(VerifyTuiJian.this,"输入失败，已经输入过验证码了", R.style.LoadingDialog);
									 dialog.show();
									 dialog.setCancelable(true);// 让progressDialog点击返回键...
									 dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
									//延迟关闭
										new Handler().postDelayed(new Runnable(){

											@Override
											public void run() {
												dialog.dismiss();
												
											}}, 1000);
								}
								else if("403".equals(code)){
									dialog=new DialogHint(VerifyTuiJian.this, "身份信息验证失效，请重新登陆", R.style.LoadingDialog);
									 dialog.show();
									 dialog.setCancelable(true);// 让progressDialog点击返回键...
									 dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
									//延迟关闭
										new Handler().postDelayed(new Runnable(){

											@Override
											public void run() {
												dialog.dismiss();
												Intent  i=new  Intent(VerifyTuiJian.this, MainActivity.class);
												startActivity(i);
												VerifyTuiJian.this.finish();
											}}, 1000);
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							
							

							}}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {

						}
					}) {
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					HashMap localHashMap = new HashMap();
					localHashMap.put("Cookie", mcookie);
					return localHashMap;
				}
			};
//			queue.add(jsonObjectPostRequest);
			MyApplication.getInstance().getQueue(VerifyTuiJian.this).add(jsonObjectPostRequest);
			 
			
			break;
		};
		
		
		
	
	}
//	private void show() {
//		if(z.equals("")){
//			dialog=new DialogHint(VerifyTuiJian.this, "请再点击一次", R.style.LoadingDialog);
//			 dialog.show();
//			 dialog.setCancelable(true);// 让progressDialog点击返回键...
//			 dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
//			//延迟关闭
//				new Handler().postDelayed(new Runnable(){
//
//					@Override
//					public void run() {
//						dialog.dismiss();
//						
//					}}, 1000);
//			
//		}else{
//		Log.d("推荐码显示", z);
		
		
//	}
//	}
}
