package com.yunlinker.xiyi.fragemnt;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


































import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.auth.AlipaySDK;
import com.alipay.sdk.pay.PayResult;
import com.alipay.sdk.pay.SignUtils;
import com.yunlinker.xiyixi.R;
import com.yunlinker.xiyi.ui.HomeActivity;
import com.yunlinker.xiyi.ui.MyOrderMain;
import com.yunlinker.xiyi.vov.Baseparam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 支付宝支付
 * 
 * @author Administrator
 *
 */
public class Alipay extends Fragment {
	FragmentManager 	fm;
	String  mPrice;
   String  ordernum;
	SharedPreferences shareds ;

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
							Editor  edit=shareds.edit();
							edit.clear();
							edit.commit();
				
							Toast.makeText(getActivity(), "支付成功",
									Toast.LENGTH_SHORT).show();
							 Intent  v=new  Intent(getActivity(),HomeActivity.class);  
							startActivity(v);
							getActivity().finish();
							
						} else {
							// 判断resultStatus 为非“9000”则代表可能支付失败
							// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
							if (TextUtils.equals(resultStatus, "8000")) {
								Toast.makeText(getActivity(), "支付结果确认中",
										Toast.LENGTH_SHORT).show();

							} 
							else {
								Toast.makeText(getActivity(), "支付失败",
										Toast.LENGTH_SHORT).show();
//								Intent  i=new Intent(getActivity(), HomeActivity.class);
//								startActivity(i);
								getActivity().finish();
							}
								// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
								
//							}
						}
						break;
					}
					case SDK_CHECK_FLAG: {
						Toast.makeText(getActivity(), "检查结果为：" + msg.obj,
								Toast.LENGTH_SHORT).show();
						break;
					}
					default:
						break;
					}
				};
			};

	   @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		  View view=inflater.inflate(R.layout.alipay,container,  false);
          
		  shareds = getActivity().getSharedPreferences(
					"order", Context.MODE_PRIVATE);
			
	  return view;
	   }
	   
	   @Override
	public void onResume() {
		super.onResume();
		
	}
	   

		public void pay() {
			fm = getFragmentManager();
			
			
			
			
		  
		  mPrice=shareds.getString("Price", "");
			ordernum=shareds.getString("order_num","");
			
			

			String  orderInfo = getOrderInfo("洗衣支付", "洗衣支付费用",mPrice);
//			String  orderInfo = getOrderInfo("洗衣支付", "洗衣支付费用","0.01");
		
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
					PayTask alipay = new PayTask(getActivity());
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
//			SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
//					Locale.getDefault());
//			Date date = new Date();
//			String key = format.format(date);
//
//			Random r = new Random();
//			key = key + r.nextInt();
//			key = key.substring(0, 15);
			return ordernum;
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
public void onDestroy() {
	super.onDestroy();
	
}
	   
}

