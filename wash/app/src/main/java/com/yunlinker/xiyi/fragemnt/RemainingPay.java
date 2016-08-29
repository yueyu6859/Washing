package com.yunlinker.xiyi.fragemnt;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
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
import com.yunlinker.xiyixi.R;
import com.yunlinker.xiyi.ui.Basket;
import com.yunlinker.xiyi.ui.Forget_Password;
import com.yunlinker.xiyi.ui.HomeActivity;
import com.yunlinker.xiyi.ui.HomeSupplies;
import com.yunlinker.xiyi.ui.MainActivity;
import com.yunlinker.xiyi.ui.Remaining_Recharge;
import com.yunlinker.xiyi.ui.StartPay;
import com.yunlinker.xiyi.ui.XiYiBiChangePassword;
import com.yunlinker.xiyi.utils.DialogHint;
import com.yunlinker.xiyi.vov.Baseparam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 余额支付
 * 
 * @author Administrator
 *
 */
public class RemainingPay extends Fragment implements OnClickListener {
	String  Has_offer_password;
	SharedPreferences sharedPreferences;
	String passwords;
	String userid;
	//设置点击一次
	boolean isMsgSend=true;
	/**
	 * 显示余额
	 */
	String remain;
	Handler mHandler;
	//将余额转成int
	int mremain;
	private DialogHint mDialog;
	// 创建请求队列
	private RequestQueue queue;
	private View view;
	String mcookie;
	// 充值ֵ
	private Button remaining_recharge;
	// 显示余额
	private TextView remaining;
	// 重置密码
	private Button reset_password;
	// 确认支付
	private Button btn_confirm;
	// 密码
	private String password;
	// 密码框
	private EditText editText1;
	private EditText editText2;
	private EditText editText3;
	private EditText editText4;
	private EditText editText5;
	private EditText editText6;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.remaining_pay, null);
		remaining_recharge = (Button) view
				.findViewById(R.id.remaining_recharge);
		isMsgSend=true;
		remaining = (TextView) view.findViewById(R.id.xiyibi_remaining);
		btn_confirm = (Button) view.findViewById(R.id.btn_confirm);

		editText1 = (EditText) view.findViewById(R.id.ps1);
		editText2 = (EditText) view.findViewById(R.id.ps2);
		editText3 = (EditText) view.findViewById(R.id.ps3);
		editText4 = (EditText) view.findViewById(R.id.ps4);
		editText5 = (EditText) view.findViewById(R.id.ps5);
		editText6 = (EditText) view.findViewById(R.id.ps6);
		reset_password = (Button) view.findViewById(R.id.reset_password);

		
		
		// 取出cookie
					sharedPreferences = getActivity()
							.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
					mcookie = sharedPreferences.getString("mcookie", "");
					userid=sharedPreferences.getString("id", "");
					

		queue = Volley.newRequestQueue(getActivity());
		textview();

		initView();

		

		editText1.getText().toString();
		editText1.getText().toString();

		btn_confirm.setOnClickListener(this);
		reset_password.setOnClickListener(this);
		remaining_recharge.setOnClickListener(this);
		
		
		//更新界面
		mHandler = new Handler(){
			
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				if(msg.what==10){
					int s= (Integer) msg.obj;
					if(s>0){
						int f=mremain-s;
						remaining.setText(""+f);
					}else if(s<0){
						remaining.setText(mremain);
					}
				}else 
					if(msg.what==20){
					String  s= (String) msg.obj;
					remaining.setText(s);
					
					 mremain=Integer.parseInt(remaining.getText().toString());
				}else if(msg.what==6){
					String  v= (String) msg.obj;
					remaining.setText(v);
				}
			}
		};
		
		return view;
	}
    

	private void initView() {
		editText1.addTextChangedListener(tw);
		editText2.addTextChangedListener(tw);
		editText3.addTextChangedListener(tw);
		editText4.addTextChangedListener(tw);
		editText5.addTextChangedListener(tw);
		editText6.addTextChangedListener(tw);

	}
  @Override
public void onResume() {
	super.onResume();
	JsonObjectRequest jsonObjectPostRequest = new JsonObjectRequest(Request.Method.GET,Baseparam.USER+userid+"/", new Listener<JSONObject>(){

		@Override
		public void onResponse(JSONObject response) {
			String mcredits= null;
			try {
				mcredits = response.getJSONObject("data").getString("credits");
				 String  v=response.getJSONObject("data").getString("has_offer_password");
				 if(!v.equals("")){
					 Log.d("下载的信息",v);
					 Editor  s=sharedPreferences.edit();
					 s.putString("has_offer_password", v);
					 s.commit();
				 }
				mHandler.sendMessage(mHandler.obtainMessage(6,
						mcredits));
				
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
	queue.add(jsonObjectPostRequest);
}
	/**
	 * 显示余额
	 */

	private void textview() {
//		SharedPreferences mp = getActivity().getSharedPreferences("userinfo",
//				Context.MODE_PRIVATE);
//		Editor edit1 = mp.edit();
//		remain = mp.getString("credits", "");
		
		JsonObjectRequest jsonObjectPostRequest = new JsonObjectRequest(Request.Method.GET,Baseparam.USER+userid+"/", new Listener<JSONObject>(){

			@Override
			public void onResponse(JSONObject response) {
				String mcredits= null;
				try {
					mcredits = response.getJSONObject("data").getString("credits");
					 String  v=response.getJSONObject("data").getString("has_offer_password");
					 if(!v.equals("")){
						 Editor  s=sharedPreferences.edit();
						 s.putString("has_offer_password", v);
						 s.commit();
					 }
					mHandler.sendMessage(mHandler.obtainMessage(20,
							mcredits));
					
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
		queue.add(jsonObjectPostRequest);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.remaining_recharge:
			passwords=sharedPreferences.getString("has_offer_password", "");
			Log.d("passwords", passwords);
			if(passwords.equals("false")){
				mDialog =new  DialogHint(getActivity(), "请设置余额支付密码",R.style.LoadingDialog);
				mDialog.show();
				mDialog.setCancelable(true);// 让progressDialog点击返回键...
				mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
				
				//延迟关闭
				new Handler().postDelayed(new Runnable(){

					@Override
					public void run() {
						mDialog.dismiss();
						Intent intent2 = new Intent(getActivity(), StartPay.class);
						intent2.putExtra("star", "remainingpay");
						startActivity(intent2);
					}}, 1000);
				
			}else if(passwords.equals("true")){
			Intent intent = new Intent(getActivity(), Remaining_Recharge.class);
			startActivity(intent);}
			break;

		case R.id.reset_password:
			Intent intent3 = new Intent(getActivity(),
					Forget_Password.class);
			startActivity(intent3);
			break;

		case R.id.btn_confirm:
			if(!isMsgSend)
				return;
			isMsgSend=false;
			String a = editText1.getText().toString();
			String b = editText2.getText().toString();
			String c = editText3.getText().toString();
			String d = editText4.getText().toString();
			String e = editText5.getText().toString();
			String f = editText6.getText().toString();
			
			password = a + b + c + d + e + f;
			Has_offer_password=sharedPreferences.getString("has_offer_password", "");
			if("false".equals(Has_offer_password)){
				Intent  inetnt=new  Intent(getActivity(), StartPay.class);			
				startActivity(inetnt);
			}else if("true".equals(Has_offer_password)){

			SharedPreferences shared = getActivity().getSharedPreferences(
					"order", Context.MODE_PRIVATE);
			String id = shared.getString("orderid", "");
			
			HashMap<String, String> mMap = new HashMap<String, String>();
			mMap.put("password", password);
			JSONObject jsonRequest = new JSONObject(mMap);
			JsonObjectRequest jsonObjectPostRequest = new JsonObjectRequest(
					Request.Method.POST, "http://123.56.138.192:8002/orders/"
							+ id + "/offer_order/", jsonRequest,
					new Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							
							try {
								
								String status_code = response.getString("status_code");
									
								if ("1".equals(status_code)) {
									
//									
									Gson	gson = new Gson();
									
									int  price=response.getJSONObject("data").getInt("credits");
									
									
									mHandler.sendMessage(mHandler.obtainMessage(10,
											price));
									mDialog = new DialogHint(getActivity(), "支付成功",R.style.LoadingDialog);
									mDialog.show();
									mDialog.setCancelable(true);// 让progressDialog点击返回键...
									mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
									
									//延迟关闭
									new Handler().postDelayed(new Runnable(){

										@Override
										public void run() {
											mDialog.dismiss();
											isMsgSend=true;
											HomeActivity.list.removeAll(HomeActivity.list);
											Intent inetnt=new  Intent(getActivity(), HomeActivity.class);
											startActivity(inetnt);
											getActivity().finish();
										}}, 3000);
									 
									
								
								} else {
									String data = response.getString("error");

								

									if (data.equals("Lack of balance")) {
										isMsgSend=true;
										mDialog =new  DialogHint(getActivity(), "支付失败，余额不足",R.style.LoadingDialog);
										mDialog.show();
										mDialog.setCancelable(true);// 让progressDialog点击返回键...
										mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
										
										//延迟关闭
										new Handler().postDelayed(new Runnable(){

											@Override
											public void run() {
												mDialog.dismiss();
												
											}}, 1000);
										
										
										
									} else if(data.equals("offer password is incorrect")){
										isMsgSend=true;
										mDialog = new DialogHint(getActivity(), "密码错误",R.style.LoadingDialog);
										mDialog.show();
										mDialog.setCancelable(true);// 让progressDialog点击返回键...
										mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
										
										//延迟关闭
										new Handler().postDelayed(new Runnable(){

											@Override
											public void run() {
												mDialog.dismiss();
												
											}}, 1000);
									}
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							isMsgSend=true;
							Toast.makeText(getActivity(), "网络不稳定，请求失败", 0).show();

						}
					}) {
				public Map<String, String> getHeaders() throws AuthFailureError {
					HashMap localHashMap = new HashMap();
					localHashMap.put("Cookie", mcookie);
					return localHashMap;
				}
			};
			queue.add(jsonObjectPostRequest);
		}
			break;
		}

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
				} 			
			}
			
			
			
			//删除
			if(s.toString().length() == 0){
				if(editText6.isFocused()){
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
		}
	};
	@Override
	public void onDestroy() {

		super.onDestroy();
//		queue.cancelAll(getActivity());
	
	}
}
