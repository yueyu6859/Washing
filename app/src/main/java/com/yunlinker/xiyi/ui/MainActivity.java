package com.yunlinker.xiyi.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.yunlinker.xiyi.bean.discountsbean;
import com.yunlinker.xiyi.bean.logbean;
import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyi.utils.CommonTools;
import com.yunlinker.xiyi.utils.DialogHint;
import com.yunlinker.xiyi.utils.LoadingDialog;
import com.yunlinker.xiyi.vov.Baseparam;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 登陆界面
 * 
 * @author Administrator
 *
 */
public class MainActivity extends BaseActivity implements OnClickListener {
	private String vs,account;
	private final String TAG=MainActivity.class.getName();
	private DialogHint mDialog;
	private TimeCount time;
	String mdata ;
	// //定义退出程序boolen类型
	private boolean isExit = false;

	private Context context = MainActivity.this;
	// 请求队列
//	private RequestQueue queue;
	private SharedPreferences preferences;
	// 返回
	private ImageButton ib_return;
	// 号码输入框
	private EditText ed_phonenumer;
	// 获取验证码
	private Button btn_jieshou;

	// 验证码
	private EditText et_yanzhengma;
	// 绑定登陆
	private Button btn_bound;
	// 协议
	private Button xieyi;
	// 电话号码
	String tels;

	// cookie
	String[] cookies;
	String[] mcookies;
	String cookie;
	String mcookie;

	private logbean login;
	boolean isMsgSend=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);

		// 方便后续退出整个应用程序
//		MyApplication.getInstance().addActivity(MainActivity.this);

		btn_bound = (Button) findViewById(R.id.btn_bound);
		ib_return = (ImageButton) findViewById(R.id.ib_return);
		ed_phonenumer = (EditText) findViewById(R.id.ed_phonenumer);
		btn_jieshou = (Button) findViewById(R.id.btn_jieshou);
		et_yanzhengma = (EditText) findViewById(R.id.et_yanzhengma);
		xieyi=(Button) findViewById(R.id.xieyi);
        
		String codey = et_yanzhengma.getText().toString();
        Intent i=getIntent();
        if(i!=null){
        	vs=i.getStringExtra("homeactivty");
        	account=i.getStringExtra("MyAccount");
        }
		btn_jieshou.setOnClickListener(this);
		btn_bound.setOnClickListener(this);
		ib_return.setOnClickListener(this);
		xieyi.setOnClickListener(this);
//		queue = Volley.newRequestQueue(getApplicationContext());
		MyApplication.getInstance().getQueue(MainActivity.this);
		
	}

	@Override
	public void onClick(View v) {
	
		switch (v.getId()) {
		case R.id.btn_bound:
			String name = ed_phonenumer.getText().toString();
			String usercode = et_yanzhengma.getText().toString();

			if (usercode.equals("")
					||(name.length() !=11)) {
				Toast.makeText(context, "请重新输入电话号码或验证码", 6000).show();
				break;
			}

			else {
				try {
					name = URLEncoder.encode(name, "UTF-8");

					usercode = URLEncoder.encode(usercode, "UTF-8");
				} catch (UnsupportedEncodingException e) {

					e.printStackTrace();
				}
				HashMap<String, String> mMap = new HashMap<String, String>();
				mMap.put("username", name);
				mMap.put("code", usercode);
				JSONObject jsonRequest = new JSONObject(mMap);
				JsonObjectRequest jsonObjectPostRequest = new JsonObjectRequest(
						Request.Method.POST, Baseparam.LOGING, jsonRequest,
						new Listener<JSONObject>() {

							@Override
							public void onResponse(JSONObject response) {
								String code;
								try {
									code = response.getString("status_code");
									 if("1".equals(code)){
		                                  
											GetListByjson(response);
										 }else  if("1220".equals(code)){
											 mDialog =new  DialogHint(MainActivity.this, "验证码错误",R.style.LoadingDialog);
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
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							
							}
						}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								mDialog =new  DialogHint(MainActivity.this, "请注意检查网络",R.style.LoadingDialog);
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
						}) {
					// 提交cookieֵ
					public Map<String, String> getHeaders()
							throws AuthFailureError {
						HashMap localHashMap = new HashMap();
						localHashMap.put("Cookie", cookie);
						return localHashMap;

					}

					protected Response<JSONObject> parseNetworkResponse(
							NetworkResponse response) {
						try {

							Map<String, String> responseHeade = response.headers;

							String Cookies = responseHeade.get("Set-Cookie");
							String data = new String(response.data, "UTF-8");
							for (String key : responseHeade.keySet()) {
								System.out.println("key= " + key
										+ " and value= "
										+ responseHeade.get(key));
								
							}
							mcookies = responseHeade.get("Set-Cookie").split(
									";");
							mcookie = mcookies[0];
							
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
							return Response.error(new ParseError(e));
						}
						return super.parseNetworkResponse(response);
					}
				};

//				queue.add(jsonObjectPostRequest);
				MyApplication.getInstance().getQueue(MainActivity.this).add(jsonObjectPostRequest);
                  
				
				break;
			}

		case R.id.btn_jieshou:
			tels = ed_phonenumer.getText().toString();

			if (tels.length() != 11) {
				mDialog =new  DialogHint(MainActivity.this, "请正确输入电话号码",R.style.LoadingDialog);
				mDialog.show();
				mDialog.setCancelable(true);// 让progressDialog点击返回键...
				mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
				
				//延迟关闭
				new Handler().postDelayed(new Runnable(){

					@Override
					public void run() {
						mDialog.dismiss();
						
					}}, 1000);
			
				break;
			} else {
				if(!isMsgSend)
					return;
				isMsgSend=false;
				try {
					tels = URLEncoder.encode(tels, "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Map<String, String> params = new HashMap<String, String>();
				params.put("tel", tels);

				JSONObject opJsonObject = new JSONObject(params);

				JsonObjectRequest newMissRequest = new JsonObjectRequest(
						Request.Method.POST, Baseparam.REQUEST_SMS_CODE,
						opJsonObject, new Response.Listener<JSONObject>() {

							public void onResponse(JSONObject jsonobj) {
								time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
								time.start();
								Toast.makeText(context, "请注意查收", 6000).show();
							}
						}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								isMsgSend=true;
								mDialog =new  DialogHint(MainActivity.this, "请检查网络",R.style.LoadingDialog);
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
						}) {
					protected Response<JSONObject> parseNetworkResponse(
							NetworkResponse response) {
						
						try {
							Map<String, String> responseHeaders = response.headers;
							String rawCookies = responseHeaders
									.get("Set-Cookie");
							String dataString = new String(response.data,
									"UTF-8");
							for (String key : responseHeaders.keySet()) {
								System.out.println("key= " + key
										+ " and value= "
										+ responseHeaders.get(key));
								Log.d("获取验证码时的cookie",
										"" + responseHeaders.get("Set-Cookie"));
							}

							cookies = responseHeaders.get("Set-Cookie").split(
									";");
							cookie = cookies[0];

						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
							return Response.error(new ParseError(e));
						}
						return super.parseNetworkResponse(response);

					}
				};
//				queue.add(newMissRequest);
				MyApplication.getInstance().getQueue(MainActivity.this).add(newMissRequest);

				break;
			}

		case R.id.ib_return:
//			if(vs!=null&&"my".equals(vs)){
//			Intent inten = new Intent(context, HomeActivity.class);
//			startActivity(inten);
//			MainActivity.this.finish();}
//			else{
			if(account!=null&&"myaccount".equals(account)){
				Intent inten = new Intent(context, HomeActivity.class);
				startActivity(inten);
				MainActivity.this.finish();
			}else{
				MainActivity.this.finish();
			}
			break;
		case  R.id.xieyi:
			Intent intenS = new Intent(MainActivity.this,User_Agreement.class);
			startActivity(intenS);
			break;
		}

	}

	private void GetListByjson(JSONObject json) {
		try {
			mdata = (json).getString("data");
			Log.d("data", "" + mdata);
			Gson gson = new Gson();
			login = new logbean();
			login = gson.fromJson(mdata, logbean.class);
			

			// 保存客户登陆时获取到的一些信息
			SharedPreferences sharedPreferences = getSharedPreferences(
					"userinfo", MODE_APPEND);
			Editor editor = sharedPreferences.edit();// 得到编辑器
			editor.putString("id", login.getId());
			editor.putString("username", login.getUsername());
			
			editor.putString("discount_count", login.getDiscount_count());
			editor.putString("invite_code", login.getInvite_code());
			editor.putString("address_name", login.getUsername());
			editor.putString("has_offer_password",
					login.getHas_offer_password());
			Log.d("保存的信息", login.getHas_offer_password());
			editor.putString("credits", login.getCredits());
			editor.putString("mcookie", mcookie);
			editor.putString("id", login.getId());
			
			editor.commit();// 提交
			if(mdata!=null){
				 Toast.makeText(context, "登陆成功", 6000).show();
				 if(vs!=null&&"basekt".equals(vs)){
					  Intent intent1 = new Intent(MainActivity.this,
								HomeActivity.class);
					  intent1.putExtra("mianactivty", "main");
					  startActivity(intent1);
				  } else if(vs!=null&&"my".equals(vs)){
					Intent intent = new Intent(MainActivity.this,
							HomeActivity.class);
					startActivity(intent);
					MainActivity.this.finish();
				  }else if(vs==null||"".equals(vs)&&account!=null&&"myaccount".equals(account)){
					  Intent intent = new Intent(MainActivity.this,
								HomeActivity.class);
						startActivity(intent);
						MainActivity.this.finish();
				  }
				  else {
					MainActivity.this.finish();}}
			      else if(mdata==null){
						Toast.makeText(context, "请重新登陆", 6000).show();
					}

		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println("Josn解析出错");
		}finally{
			
		}
 
	}
//    @Override
//    protected void onResume() {
//    	// TODO Auto-generated method stub
//    	super.onResume();
//    	Intent i=getIntent();
//        if(i!=null){
//        	vs=i.getStringExtra("homeactivty");
//        	
//        }
//    }
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		queue.cancelAll(MainActivity.this);
//		queue.stop();
		 MyApplication.getInstance().getQueue(MainActivity.this).cancelAll(TAG);

	}

	// 按两次返回键退出登陆
	// 定义退出程序boolen类型
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)// // 使虚拟机停止运行并退出程序
		{
//			if(vs!=null&&"my".equals(vs)){
//			Intent inten = new Intent(context, HomeActivity.class);
//			startActivity(inten);
//			MainActivity.this.finish();}
//			else{
//				MainActivity.this.finish();
//			}
			if(account!=null&&"myaccount".equals(account)){
				Intent inten = new Intent(context, HomeActivity.class);
				startActivity(inten);
				MainActivity.this.finish();
			}else{
				MainActivity.this.finish();
			}
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

//	private void exit() {
//		if (isExit) {
//			Intent intent = new Intent(Intent.ACTION_MAIN);
//			intent.addCategory(Intent.CATEGORY_HOME);
//			startActivity(intent);
//			System.exit(0);//
//		} else {
//			isExit = true;
//			Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT)
//					.show();
//			mHandler.sendEmptyMessageDelayed(0, 3000);// 3秒后发送消息
//		}
//
//	}
//
//	Handler mHandler = new Handler() {
//		public void handleMessage(Message msg)// 处理消息
//		{
//			super.handleMessage(msg);
//			isExit = false;
//		}
//	};

	// 获取验证码倒计时
	class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔

		}

		@Override
		public void onFinish() {// 计时完毕时触发
			isMsgSend=true;
			btn_jieshou.setText("重新验证");
			int main_color=getResources().getColor(R.color.main_color);
			btn_jieshou.setBackgroundColor(main_color);
			
//            btn_jieshou.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					
//					
//				}
//			});
//            btn_jieshou.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
//			btn_jieshou.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					
//					
//				}
//			});
//			btn_jieshou.setClickable(false);
			int hui=getResources().getColor(R.color.hui);
			btn_jieshou.setBackgroundColor(hui);
			btn_jieshou.setText("(" + millisUntilFinished / 1000 + ")"
					+ "后重新获取");
		}
         
	}
	

}
