package com.yunlinker.xiyi.ui;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yunlinker.xiyi.Adapter.SpinnerAdapter;
import com.yunlinker.xiyi.bean.PasswordQuestionsBean;
import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyi.utils.DialogHint;
import com.yunlinker.xiyi.vov.Baseparam;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

//忘记密码
public class Forget_Password extends  BaseActivity{
	private ImageButton  returns;
	private DialogHint mDialog;
	SpinnerAdapter  adapter;
	private  Spinner  spinner;
	PasswordQuestionsBean  e;
	private EditText  answers,pass1,pass2;
	private  Button  sure;
	private  String  mcookie,name;
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 
	}
      @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.forgent_password);
    	spinner=(Spinner) findViewById(R.id.prsz);
    	answers=(EditText) findViewById(R.id.ed1);
    	pass1=(EditText) findViewById(R.id.PS1);
    	pass2=(EditText) findViewById(R.id.PS2);
    	sure=(Button) findViewById(R.id.sure);
    	returns=(ImageButton) findViewById(R.id.return29);
    	adapter=new SpinnerAdapter(Forget_Password.this,HomeActivity.Questions);
    	spinner.setAdapter(adapter);
    	
    	
    	// 方便后续退出整个应用程序
//    			MyApplication.getInstance().addActivity(Forget_Password.this);

    	
    	SharedPreferences  s=Forget_Password.this.getSharedPreferences("userinfo", MODE_PRIVATE);
		mcookie = s.getString("mcookie", "");
		name=s.getString("username", "");
		
    	
    	
    	spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				e=(PasswordQuestionsBean) adapter.getItem(position);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
				
			}});
    	returns.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Forget_Password.this.finish();
				
			}
		});
    	sure.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if("".equals(answers.getText().toString())){
					 mDialog =new  DialogHint(Forget_Password.this, "答案不能为空",R.style.LoadingDialog);
						mDialog.show();
						mDialog.setCancelable(true);// 让progressDialog点击返回键...
						mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
						
						//延迟关闭
						new Handler().postDelayed(new Runnable(){

							@Override
							public void run() {
								mDialog.dismiss();
								
							}}, 1000);
				}else if(pass2.getText().toString().length()!=6||pass1.getText().toString().length()!=6){
					 mDialog =new  DialogHint(Forget_Password.this, "密码不能小于6位",R.style.LoadingDialog);
						mDialog.show();
						mDialog.setCancelable(true);// 让progressDialog点击返回键...
						mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
						
						//延迟关闭
						new Handler().postDelayed(new Runnable(){

							@Override
							public void run() {
								mDialog.dismiss();
								
							}}, 1000);
					
				}else if("".equals(pass1.getText().toString())||"".equals(pass2.getText().toString())){
					 mDialog =new  DialogHint(Forget_Password.this, "密码不能为空",R.style.LoadingDialog);
						mDialog.show();
						mDialog.setCancelable(true);// 让progressDialog点击返回键...
						mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
						
						//延迟关闭
						new Handler().postDelayed(new Runnable(){

							@Override
							public void run() {
								mDialog.dismiss();
								
							}}, 1000);
				}else  if(!pass1.getText().toString().equals(pass2.getText().toString())){
					 mDialog =new  DialogHint(Forget_Password.this, "密码输入不一致",R.style.LoadingDialog);
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
					
					HashMap<String, String> mMap = new HashMap<String, String>();
					mMap.put("username", name);
					mMap.put("password",pass2.getText().toString());
					mMap.put("qid",e.getId());
					mMap.put("answer",answers.getText().toString());
				
					JSONObject jsonRequest = new JSONObject(mMap);
					JsonObjectRequest jsonObjectPostRequest = new JsonObjectRequest(
							Request.Method.POST, Baseparam.RESET_PASSWORD, jsonRequest,
							new Listener<JSONObject>() {

								@Override
								public void onResponse(JSONObject response) {
								Log.d("设置情况",""+ response);
								try {
									String code=response.getString("status_code");
									if("1".equals(code)){
										 mDialog =new  DialogHint(Forget_Password.this, "设置成功",R.style.LoadingDialog);
											mDialog.show();
											mDialog.setCancelable(true);// 让progressDialog点击返回键...
											mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
											
											//延迟关闭
											new Handler().postDelayed(new Runnable(){

												@Override
												public void run() {
													mDialog.dismiss();
													Forget_Password.this.finish();
												}}, 1000);
									}else if("1007".equals(code)){
										mDialog =new  DialogHint(Forget_Password.this, "答案或问题不正确，请重新输入",R.style.LoadingDialog);
										mDialog.show();
										mDialog.setCancelable(true);// 让progressDialog点击返回键...
										mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
										
										//延迟关闭
										new Handler().postDelayed(new Runnable(){

											@Override
											public void run() {
												mDialog.dismiss();
												
											}}, 1000);
									}else if("403".equals(code)){
										mDialog =new  DialogHint(Forget_Password.this, "身份信息验证失效，请重新登陆",R.style.LoadingDialog);
										mDialog.show();
										mDialog.setCancelable(true);// 让progressDialog点击返回键...
										mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
										
										//延迟关闭
										new Handler().postDelayed(new Runnable(){

											@Override
											public void run() {
												mDialog.dismiss();
												Intent  intent=new  Intent(Forget_Password.this, MainActivity.class);
												startActivity(intent);
												Forget_Password.this.finish();
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
									Toast.makeText(Forget_Password.this, "网络不稳定，请求失败", 0).show();
								}
							}) {
						// 提交cookieֵ
						public Map<String, String> getHeaders()
								throws AuthFailureError {
							HashMap localHashMap = new HashMap();
							localHashMap.put("Cookie", mcookie);
							return localHashMap;

						}

						
					};
					MyApplication.getInstance().getQueue(Forget_Password.this).add(jsonObjectPostRequest);
					
					
					
				}
				
			}
		});
    	}
      
      
}
