package com.yunlinker.xiyi.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyi.utils.DialogHint;
import com.yunlinker.xiyi.vov.Baseparam;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * 洗衣币修改密码
 * 
 * @author Administrator
 *
 */
public class XiYiBiChangePassword extends BaseActivity implements OnClickListener {
	private final String TAG=XiYiBiChangePassword.class.getName();
	// 请求队列
//		private RequestQueue queue;
		private SharedPreferences preferences;
		String mcookie;
	private  Button  affirm;
	private  DialogHint  dialog;
	private Button  btn_forget;
	//输入的新密码
	String z,s;
	// 返回
	private ImageButton return10;
	private  EditText  oldpasswoed,newpassword1,newpassword2;
   String  old;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiyibi_change_password);
		return10 = (ImageButton) findViewById(R.id.return10);
		oldpasswoed=(EditText) findViewById(R.id.oldpasswoed);
		newpassword1=(EditText) findViewById(R.id.newpassword1);
		newpassword2=(EditText) findViewById(R.id.newpassword2);
		btn_forget=(Button) findViewById(R.id.btn_forget);
		affirm=(Button) findViewById(R.id.affirm);
		
		// 取出之前保存的cookie
				SharedPreferences sharedPreferences = getSharedPreferences("userinfo",
						Context.MODE_PRIVATE);
				mcookie= sharedPreferences.getString("mcookie", "");
		
		// 方便后续退出整个应用程序
//		MyApplication.getInstance().addActivity(XiYiBiChangePassword.this);
		
	  
		
		btn_forget.setOnClickListener(this);
		affirm.setOnClickListener(this);
		return10.setOnClickListener(this);
		
//		queue = Volley.newRequestQueue(XiYiBiChangePassword.this);
		MyApplication.getInstance().getQueue(XiYiBiChangePassword.this);
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
//		queue.cancelAll(XiYiBiChangePassword.this);
		 MyApplication.getInstance().getQueue(XiYiBiChangePassword.this).cancelAll(TAG);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case  R.id.btn_forget:
			Intent  in=new Intent(XiYiBiChangePassword.this, Forget_Password.class);
			startActivity(in);
			break;
		case R.id.return10:
			XiYiBiChangePassword.this.finish();
			break;

		case R.id.affirm:
			s=newpassword1.getText().toString();
			z=newpassword2.getText().toString();
		
			 if(!s.equals(z)){
				 dialog=new DialogHint(XiYiBiChangePassword.this, "新密码输入不一致!", R.style.LoadingDialog);
				 dialog.show();
				 dialog.setCancelable(true);// 让progressDialog点击返回键...
				 dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
				//延迟关闭
					new Handler().postDelayed(new Runnable(){
                          
					    
						public void run() {
							dialog.dismiss();
							
						}}, 1000);
			 }else if(s.length()!=6){
				 dialog=new DialogHint(XiYiBiChangePassword.this, "密码必须为6位!", R.style.LoadingDialog);
				 dialog.show();
				 dialog.setCancelable(true);// 让progressDialog点击返回键...
				 dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
				//延迟关闭
					new Handler().postDelayed(new Runnable(){
                          
					    
						public void run() {
							dialog.dismiss();
							
						}}, 1000);
			 }else if(s.equals(z)){
			
			 inite();}
			
			break;
		}

	}

	private void inite() {
		 old=oldpasswoed.getText().toString();
		HashMap<String, String> mMap = new HashMap<String, String>();
		mMap.put("old_password", old );
		mMap.put("new_password", z);
	
		JSONObject jsonRequest = new JSONObject(mMap);
		JsonObjectRequest jsonObjectPostRequest = new JsonObjectRequest(Request.Method.POST, Baseparam.CHANGE_PASSWORD, jsonRequest, new Listener<JSONObject>(){

			@Override
			public void onResponse(JSONObject response) {
			  Log.d("我的地址", "sss"+response);
				try {
					if(!response.getString("status_code").equals("1")){
						
						 dialog=new DialogHint(XiYiBiChangePassword.this, "原始密码错误", R.style.LoadingDialog);
						 dialog.show();
						 dialog.setCancelable(true);// 让progressDialog点击返回键...
						 dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
						//延迟关闭
							new Handler().postDelayed(new Runnable(){
		                          
							    
								public void run() {
									dialog.dismiss();
									
								}}, 1000);
						
						
					}else if(response.getString("status_code").equals("1")){
						 dialog=new DialogHint(XiYiBiChangePassword.this, "密码设置成功", R.style.LoadingDialog);
						 dialog.show();
						 dialog.setCancelable(true);// 让progressDialog点击返回键...
						 dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
						//延迟关闭
							new Handler().postDelayed(new Runnable(){
		                          
							    
								public void run() {
									dialog.dismiss();
									XiYiBiChangePassword.this.finish();
								}}, 1000);
							
							 
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}}, new Response.ErrorListener(){

		     
				public void onErrorResponse(VolleyError error) {
					Toast.makeText(XiYiBiChangePassword.this, "网络不稳定，请求失败", 0).show();
					
				}}){
			// 提交cookieֵ
						public Map<String, String> getHeaders()
								throws AuthFailureError {
							HashMap localHashMap = new HashMap();
							localHashMap.put("Cookie", mcookie);
							return localHashMap;
						}
		};
//		queue.add(jsonObjectPostRequest);
		MyApplication.getInstance().getQueue(XiYiBiChangePassword.this).add(jsonObjectPostRequest);
	}

	
    
}
