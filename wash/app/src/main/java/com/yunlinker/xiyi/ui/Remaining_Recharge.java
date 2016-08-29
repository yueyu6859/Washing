package com.yunlinker.xiyi.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.PayResult;
import com.alipay.sdk.pay.SignUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;
import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyi.utils.DialogHint;
import com.yunlinker.xiyi.vov.Baseparam;

import android.app.Activity;
import android.app.SearchManager.OnCancelListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * 支付里的充值界面
 * 
 * @author Administrator
 *
 */
public class Remaining_Recharge extends BaseActivity implements OnClickListener {
	String  starpay;
	SharedPreferences sharedPreferences ;
	private final String TAG=Remaining_Recharge.class.getName();
	String userid,mcookie;
	private DialogHint mDialog;
	// 创建请求队列
//			private RequestQueue queue;
	String  order_num;
    private  Button  pay3;
    private EditText  moeny5;
    private  ImageButton  return4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remaining_recharge);
		pay3=(Button) findViewById(R.id.pay3);
		moeny5=(EditText) findViewById(R.id.moeny5);
		return4=(ImageButton) findViewById(R.id.return4);
//		queue = Volley.newRequestQueue(Remaining_Recharge.this);
		MyApplication.getInstance().getQueue(Remaining_Recharge.this);
		
		// 取出之前保存的cookie
		sharedPreferences = getSharedPreferences("userinfo",
				Context.MODE_PRIVATE);
		mcookie = sharedPreferences.getString("mcookie", "");
		userid=sharedPreferences.getString("id", "");
		password();
		// 方便后续退出整个应用程序
//		MyApplication.getInstance().addActivity(Remaining_Recharge.this);
		int maincolor=getResources().getColor(R.color.main_color);
		int hui=getResources().getColor(R.color.hui);
	    Intent  I=getIntent();
	    if(I!=null){
	    	  starpay=I.getStringExtra("payss");
	    }
		return4.setOnClickListener(this);
		pay3.setOnClickListener(this);
//		if(moeny5.getText().toString()!=null){
//			pay3.setBackgroundColor(maincolor);
//			
//		}else{
//			pay3.setBackgroundColor(hui);
//			
//		}
	}
	private void password() {
		JsonObjectRequest jsonObjectPostRequest = new JsonObjectRequest(Request.Method.GET,Baseparam.USER+userid+"/", new Listener<JSONObject>(){

			@Override
			public void onResponse(JSONObject response) {
				String mcredits= null;
				try {
					
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
		
		MyApplication.getInstance().getQueue(Remaining_Recharge.this).add(jsonObjectPostRequest);
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return4:
			Remaining_Recharge.this.finish();
			break;
        
		case   R.id.pay3:
			if(moeny5.getEditableText().toString().equals("")){
				mDialog = new DialogHint(Remaining_Recharge.this, "请输入充值金额",R.style.LoadingDialog);
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
			Map<String, String> params = new HashMap<String, String>();
			params.put("price", moeny5.getText().toString());
			JSONObject opJsonObject = new JSONObject(params);
		  JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, Baseparam.RECHARGE, opJsonObject,new Response.Listener<JSONObject>(){

			@Override
			public void onResponse(JSONObject response) {
				try {
					String  code=response.getString("status_code");
					if("1".equals(code)){
					order_num=response.getJSONObject("data").getString("order_num");
				    
				    pay();}else if("403".equals(code)){
				    	mDialog = new DialogHint(Remaining_Recharge.this, "请检查网络",R.style.LoadingDialog);
						mDialog.show();
						mDialog.setCancelable(true);// 让progressDialog点击返回键...
						mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
						
						//延迟关闭
						new Handler().postDelayed(new Runnable(){

							@Override
							public void run() {
								mDialog.dismiss();
								Intent  intent=new Intent(Remaining_Recharge.this,MainActivity.class);
									startActivity(intent);
								    Remaining_Recharge.this.finish();
							}}, 1000);
				    }
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}}, new Response.ErrorListener(){

				@Override
				public void onErrorResponse(VolleyError error) {
					mDialog = new DialogHint(Remaining_Recharge.this, "请检查网络",R.style.LoadingDialog);
					mDialog.show();
					mDialog.setCancelable(true);// 让progressDialog点击返回键...
					mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
					
					//延迟关闭
					new Handler().postDelayed(new Runnable(){

						@Override
						public void run() {
							mDialog.dismiss();
						}}, 1000);
					
				}}){
			  public Map<String, String> getHeaders() throws AuthFailureError {
					HashMap localHashMap = new HashMap();
					localHashMap.put("Cookie", mcookie);
					return localHashMap;
				}
			  
		  };
//		  queue.add(jsonRequest);
		  MyApplication.getInstance().getQueue(Remaining_Recharge.this).add(jsonRequest);
			}
			break;
		}
		
	}
	//商户PID
	public static final String PARTNER = "2088911762499134";
	//商户收款账号
	public static final String SELLER = "562521821@qq.com";
	//商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMCNfmLNqSq+arPnfJ+6MWp6tjoo/ZCARTUsAB+fYLBBhuNazrDjmAxpvPDlbrpoj1YdeKRiTMFjTGITUmGWG6mNTwSJSZafUnpUb5h92KxW7lHZWJNX5zU3G7k7iniofNQsIfF/IkFnxgJUlHFAhrAgT+NWNxyZL1SOa35G7hYVAgMBAAECgYAz6PFdqZwljdguUAXk+clAr3xZCZvyTOJbh1UxMUJbwg6fVGfMgE8JaagqOXA0iOO7j45qoqWWrTJ2bkcTT+h8wVwvTYQ2B6wLF3Auv8xtxqxY0Pq4rCzCUzPO4hv22w48vIngJqj8RL70Ko2Y1jYpPAdIGWkYIqCxsTeBkoppsQJBAOp6jssv4b6DU3tQE/nhT6/Rgbc/9dpME+hAwrds1evP7/gP91QVOOOSUz4ZrDPZp9Kd78AobJQDGkYkrY5ltVsCQQDSOdGUyJJz7ymUZURqEixx4F7OaWJykr95xW4r3NMlY7GQ4YOxyVqeHs5OJswuXSyrWPv64wXcIGZNebhKeI1PAkBWW4cFzH++081GSErjKBlaLrYwkIzytjxKuLc+KQZskCvYV8EGpb5LClRANeJXQl1t058+TWUX6kCd/tow7MKrAkEAzlkCZtAZMMLoRUjBRnxgM8bQSDsY7u0sFRK9eUBf4Ktid5tj8PZ7hAko530dncTHO8k0TKbZ8Z+7ywBW7II38QJAN+uXQoBNnXAE8fZE4OEoil5KYL3IQsqfapewbu3dZZJbXEjSv8OUPzPfDDxV7oC4X74HmKJILveqihFSE1gG0g==";
	//支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";


	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				
				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();
				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {

					Toast.makeText(Remaining_Recharge.this, "支付成功",
							Toast.LENGTH_SHORT).show();
					if("star".equals(starpay)){
						Intent  i=new Intent(Remaining_Recharge.this, Pay.class);
						startActivity(i);
						
					}else{
	                 Remaining_Recharge.this.finish();}
					
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(Remaining_Recharge.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} 
					else {
						Toast.makeText(Remaining_Recharge.this, "支付失败",
								Toast.LENGTH_SHORT).show();
					}
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//						Toast.makeText(getActivity(), "支付失败",
//								Toast.LENGTH_SHORT).show();
//					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(Remaining_Recharge.this, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};
	public void pay() {
	
		// 订单

		String  orderInfo = getOrderInfo("洗衣币充值", "洗衣币充值费用",moeny5.getText().toString());
//		String  orderInfo = getOrderInfo("洗衣币充值", "洗衣币充值费用","0.01");
		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(Remaining_Recharge.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
		
		
	
}




	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + Baseparam.NOTIFY_URL+"/payment/alipay_notify/"
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo() {
//		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
//				Locale.getDefault());
//		Date date = new Date();
//		String key = format.format(date);
//
//		Random r = new Random();
//		key = key + r.nextInt();
//		key = key.substring(0, 15);
		return order_num;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyApplication.getInstance().getQueue(Remaining_Recharge.this).cancelAll(TAG);
	}
}
