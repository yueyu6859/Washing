package com.yunlinker.xiyi.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunlinker.xiyi.Adapter.SpinnerAdapter;
import com.yunlinker.xiyi.bean.PasswordQuestionsBean;
import com.yunlinker.xiyi.bean.Results;
import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyi.utils.DialogHint;
import com.yunlinker.xiyi.vov.Baseparam;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

//第一次设置密码
public class StartPay extends BaseActivity implements OnClickListener {
	SpinnerAdapter  adapter;
 
	private final String TAG=StartPay.class.getName();
	private  String  pro;
	PasswordQuestionsBean  e;
	private  DialogHint  dialog;
	// 创建请求队列
//	private RequestQueue queue;
	String mcookie;

	private ImageButton return5;
	// 确认
	private Button verify;
    private Spinner probrem;
    private  EditText  answers;
	// 设置密码
	private String pass1, pass2;
	private EditText editText1, editText2, editText3, editText4, editText5,
			editText6, editText7, editText8, editText9, editText10, editText11,
			editText12;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_pay);
		return5=(ImageButton) findViewById(R.id.return5);
		probrem=(Spinner) findViewById(R.id.pr);
		answers=(EditText) findViewById(R.id.answer);
		
//		queue = Volley.newRequestQueue(getApplicationContext());
		MyApplication.getInstance().getQueue(StartPay.this);
		probrem();
		initview();
		
		// //方便后续退出整个应用程序
//		MyApplication.getInstance().addActivity(StartPay.this);
		return5.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				StartPay.this.finish();
				
			}
		});
	}
	
	
	private void probrem() {
		adapter=new SpinnerAdapter(StartPay.this,HomeActivity.Questions);
		probrem.setAdapter(adapter);
		   
		
		
		
		probrem.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				e=(PasswordQuestionsBean) adapter.getItem(position);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
				
			}});
		
		
		
		
		
//		// 建立数据源
//				 String[] myItems=getResources().getStringArray(R.array.qy);
//				// 建立Adapter,还连接了数据源
//				 final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,myItems);
//				adapter.setDropDownViewResource(R.layout.drop_district_item);
//				probrem.setAdapter(adapter);
//				probrem.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
//
//					@Override
//					public void onItemSelected(AdapterView<?> parent,
//							View view, int position, long id) {
//						pro=adapter.getItem(position).toString();
//						 /* 将probrem 显示 */
//						  parent.setVisibility(View.VISIBLE);
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> parent) {
//						parent.setVisibility(View.VISIBLE);
//						
//					}});
//				/* 下拉菜单弹出的内容选项触屏事件处理 */
//				probrem.setOnTouchListener(new Spinner.OnTouchListener(){
//
//					@Override
//					public boolean onTouch(View v, MotionEvent event) {
//						
//						return false;
//					}});
//				/* 下拉菜单弹出的内容选项焦点改变事件处理 */
//				probrem.setOnFocusChangeListener(new Spinner.OnFocusChangeListener(){
//
//					@Override
//					public void onFocusChange(View v, boolean hasFocus) {
//						v.setVisibility(View.VISIBLE);
//						
//					}});
	}


	protected void onDestroy() {
		super.onDestroy();
//		queue.cancelAll(StartPay.this);
		 MyApplication.getInstance().getQueue(StartPay.this).cancelAll(TAG);
	}

	private void initview() {
		// 新密码
		editText1 = (EditText) findViewById(R.id.in1);
		editText2 = (EditText) findViewById(R.id.in2);
		editText3 = (EditText) findViewById(R.id.in3);
		editText4 = (EditText) findViewById(R.id.in4);
		editText5 = (EditText) findViewById(R.id.in5);
		editText6 = (EditText) findViewById(R.id.in6);

		// 确认新密码
		editText7 = (EditText) findViewById(R.id.in7);
		editText8 = (EditText) findViewById(R.id.in8);
		editText9 = (EditText) findViewById(R.id.in9);
		editText10 = (EditText) findViewById(R.id.in10);
		editText11 = (EditText) findViewById(R.id.in11);
		editText12 = (EditText) findViewById(R.id.in12);

		verify = (Button) findViewById(R.id.btn_pay_verify);
		verify.setOnClickListener(this);

		editText1.addTextChangedListener(tw);
		editText2.addTextChangedListener(tw);
		editText3.addTextChangedListener(tw);
		editText4.addTextChangedListener(tw);
		editText5.addTextChangedListener(tw);
		editText6.addTextChangedListener(tw);

		editText7.addTextChangedListener(tw);
		editText8.addTextChangedListener(tw);
		editText9.addTextChangedListener(tw);
		editText10.addTextChangedListener(tw);
		editText11.addTextChangedListener(tw);
		editText12.addTextChangedListener(tw);

	}

	TextWatcher tw = new TextWatcher() {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
                   
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (s.toString().length() == 1) {
				if (editText1.isFocused()) {
					// 焦点清除之后，用requestFocus来指定后面的输入框的焦点
					editText1.clearFocus();
					editText2.requestFocus();
				} else if (editText2.isFocused()) {
					editText2.clearFocus();
					editText3.requestFocus();
				} else if (editText3.isFocused()) {
					editText3.clearFocus();
					editText4.requestFocus();
				} else if (editText4.isFocused()) {
					editText4.clearFocus();
					editText5.requestFocus();
				} else if (editText5.isFocused()) {
					editText5.clearFocus();
					editText6.requestFocus();
				} else if (editText6.isFocused()) {
					editText6.clearFocus();
					editText7.requestFocus();
				} else if (editText7.isFocused()) {
					editText7.clearFocus();
					editText8.requestFocus();
				} else if (editText8.isFocused()) {
					editText8.clearFocus();
					editText9.requestFocus();
				} else if (editText9.isFocused()) {
					editText9.clearFocus();
					editText10.requestFocus();
				} else if (editText10.isFocused()) {
					editText10.clearFocus();
					editText11.requestFocus();
				} else if (editText11.isFocused()) {
					editText11.clearFocus();
					editText12.requestFocus();
				} 
			}
			/////////////////////////////////////
			else if(s.toString().length() == 0){
				if (editText12.isFocused()){
					editText12.clearFocus();
					editText11.requestFocus();
				}else if(editText11.isFocused()){
					editText11.clearFocus();
					editText10.requestFocus();
				}else if(editText10.isFocused()){
					editText10.clearFocus();
					editText9.requestFocus();
				}else if(editText9.isFocused()){
					editText9.clearFocus();
					editText8.requestFocus();
				}else if(editText8.isFocused()){
					editText8.clearFocus();
					editText7.requestFocus();
				}else if(editText7.isFocused()){
					editText7.clearFocus();
					editText6.requestFocus();
				}else if(editText6.isFocused()){
					editText6.clearFocus();
					editText5.requestFocus();
				}else if(editText5.isFocused()){
					editText5.clearFocus();
					editText4.requestFocus();
					
				}else if(editText4.isFocused()){
					editText4.clearFocus();
					editText3.requestFocus();
				}else if(editText3.isFocused()){
					editText3.clearFocus();
					editText2.requestFocus();
				}else if(editText2.isFocused()){
					editText2.clearFocus();
					editText1.requestFocus();
				}
			}
			////////////////////////////////////
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_pay_verify:

			String a1 = editText1.getText().toString();
			String a2 = editText2.getText().toString();
			String a3 = editText3.getText().toString();
			String a4 = editText4.getText().toString();
			String a5 = editText5.getText().toString();
			String a6 = editText6.getText().toString();

			String a7 = editText7.getText().toString();
			String a8 = editText8.getText().toString();
			String a9 = editText9.getText().toString();
			String a10 = editText10.getText().toString();
			String a11 = editText11.getText().toString();
			String a12 = editText12.getText().toString();

			pass1 = a1 + a2 + a3 + a4 + a5 + a6;
			pass2 = a7 + a8 + a9 + a10 + a11 + a12;
		
			if (!pass1.equals(pass2)) {
				Toast.makeText(StartPay.this, "输入有误请重新输入", 6000).show();
				break;
			} else {

				// 取出cookie
				SharedPreferences sharedPreferences = getSharedPreferences(
						"userinfo", Context.MODE_PRIVATE);
				mcookie = sharedPreferences.getString("mcookie", "");
				
				HashMap<String, String> mMap = new HashMap<String, String>();
				mMap.put("password", pass1);
				mMap.put("qid", e.getId());
				mMap.put("answer", answers.getText().toString());
				JSONObject jsonRequest = new JSONObject(mMap);
				JsonObjectRequest jsonObjectPostRequest = new JsonObjectRequest(
						Request.Method.POST,
						Baseparam.set_passwor,
						jsonRequest, new Listener<JSONObject>() {

							@Override
							public void onResponse(JSONObject response) {
								 Log.d("密码情况", ""+response);
								 try {
									String code=response.getString("status_code");
									if("1".equals(code)){
										dialog=new DialogHint(StartPay.this, "密码设置成功", R.style.LoadingDialog);
										 dialog.show();
										 dialog.setCancelable(true);// 让progressDialog点击返回键...
										 dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
										//延迟关闭
											new Handler().postDelayed(new Runnable(){
						                        
											    
												public void run() {
													dialog.dismiss();
													Intent  i=getIntent();
													String  sta=i.getStringExtra("star");
													if(sta!=null&&"basket".equals(sta)){
													Intent  pays=new  Intent(StartPay.this, Pay.class);
													startActivity(pays);
													StartPay.this.finish();
													}else if(sta!=null&&"remainingpay".equals(sta)){
														Intent  v=new Intent(StartPay.this, Remaining_Recharge.class);
														v.putExtra("payss", "star");
														startActivity(v);
														StartPay.this.finish();
													}else {
														Intent iv=new Intent(StartPay.this, Pay.class);
														startActivity(iv);
														StartPay.this.finish();
													}
												}}, 1000);
									}else if("403".equals(code)){
										dialog=new DialogHint(StartPay.this, "身份信息验证失效", R.style.LoadingDialog);
										 dialog.show();
										 dialog.setCancelable(true);// 让progressDialog点击返回键...
										 dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
										//延迟关闭
											new Handler().postDelayed(new Runnable(){
						                        
											    
												public void run() {
													dialog.dismiss();
												
													Intent  pays=new  Intent(StartPay.this, MainActivity.class);
													startActivity(pays);
													StartPay.this.finish();
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
								    

							}
						}) {
					@Override
					public Map<String, String> getHeaders()
							throws AuthFailureError {
						HashMap localHashMap = new HashMap();
						localHashMap.put("Cookie", mcookie);
						return localHashMap;
					}
				};
//				queue.add(jsonObjectPostRequest);
				MyApplication.getInstance().getQueue(StartPay.this).add(jsonObjectPostRequest);
				
				
				
				break;
			}

		default:
			break;
		}
      
	}
	 
}