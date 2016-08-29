package com.yunlinker.xiyi.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;
import com.yunlinker.xiyi.Adapter.OrderdetailAdapter;
import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyi.utils.DialogHint;
import com.yunlinker.xiyi.vov.Baseparam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//订单详情
public class Orderdetail extends  BaseActivity{
	SharedPreferences	s;
	private  DialogHint  dialog;
	String  Has_offer_password;
	private  String  OrderNum,orderids,prices,order_datas,take_times;
	private  int  grenn;
	private  RelativeLayout ll;
	private String mcookie,userid;
	private  ImageButton  retun;
	private   ListView order;
	private  TextView  zhuangtai, allprices,order_data,wanc,mdatas,mmoenys,name,tel,location,end_times;
	private  Button  btn_pay,del_ord;
	private  RelativeLayout xiyijuan;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.orderdetail);
        retun=(ImageButton) findViewById(R.id.retun20);
        order=(ListView) findViewById(R.id.order);
        zhuangtai=(TextView) findViewById(R.id.zhuangtai);
        xiyijuan=(RelativeLayout) findViewById(R.id.xiyijuan);
        allprices=(TextView) findViewById(R.id.allprices);
        wanc=(TextView) findViewById(R.id.wanc);
        btn_pay=(Button) findViewById(R.id.btn_pay);
        ll=(RelativeLayout) findViewById(R.id.ll);
        mmoenys=(TextView) findViewById(R.id.mmoenys);
        order_data=(TextView) findViewById(R.id.order_data);
        mdatas=(TextView) findViewById(R.id.mdatas);
        name=(TextView) findViewById(R.id.basket_user_name);
        tel=(TextView) findViewById(R.id.basket_user_tel);
        location=(TextView) findViewById(R.id.basket_user_address);
        del_ord=(Button)findViewById(R.id.del_ord);
        
        MyApplication.getInstance().getQueue(Orderdetail.this);
        
    	// 方便后续退出整个应用程序
//		MyApplication.getInstance().addActivity(Orderdetail.this);
     
		
		
		
		
		s=Orderdetail.this.getSharedPreferences(
				"userinfo", Context.MODE_PRIVATE);
		userid=s.getString("id", "");
//		Has_offer_password=s.getString("has_offer_password", "");
		mcookie = s.getString("mcookie", "");
		
        grenn=getResources().getColor(R.color.green);
        has_offer_password();
        
        Intent  inetnt=getIntent();
        OrderNum=inetnt.getStringExtra("Order_num");
        String  Discount=inetnt.getStringExtra("Discount");
       
        String  zhuangtais=inetnt.getStringExtra("zhuangtai");
        prices=inetnt.getStringExtra("allprice");
        orderids=inetnt.getStringExtra("orderid");
		order_datas=inetnt.getStringExtra("order_datas");
		take_times=inetnt.getStringExtra("mtake_times");
		String mname=inetnt.getStringExtra("name");
		String  mtel=inetnt.getStringExtra("tel");
		String  maddress=inetnt.getStringExtra("address");
		String  endtime=inetnt.getStringExtra("endtime");
		name.setText(mname);
		tel.setText(mtel);
		location.setText(maddress);
		if(Integer.parseInt(prices)>0){
        allprices.setText("¥"+prices);}else  if(Integer.parseInt(prices)<0){
        	 allprices.setText("¥"+"0");
        }
        if(zhuangtais.equals("0")){
        	del_ord.setVisibility(View.VISIBLE);
        	 btn_pay.setVisibility(View.VISIBLE);
        	 ll.setVisibility(View.GONE);
        	zhuangtai.setText("待付款");
        	//删除订单
        	del_ord.setOnClickListener(new  OnClickListener() {
				
				@Override
				public void onClick(View v) {
					DELETE();
					
				}
			});
        	 btn_pay.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Has_offer_password=s.getString("has_offer_password", "");
					SharedPreferences shared = Orderdetail.this.getSharedPreferences(
							"order", Context.MODE_PRIVATE);
					Editor  ed=shared.edit();
					ed.putString("order_num", OrderNum);
					ed.putString("orderid", orderids);
					ed.putString("Price",prices);
					ed.commit();
//					if("false".equals(Has_offer_password)){
//						Intent  inetnt=new  Intent(Orderdetail.this, StartPay.class);
//						
//						startActivity(inetnt);
//					}else  if("true".equals(Has_offer_password)){
					Intent  inetnt=new  Intent(Orderdetail.this, Pay.class);
					startActivity(inetnt);
//					}
				}
			});
        }else  if(zhuangtais.equals("1")){
        	
        	zhuangtai.setText("待取件");
        	
        	 order_data.setText(order_datas);
        	 wanc.setText("取件预约时间");
        	if(take_times.equals("")){
        		mdatas.setText("未预约");
        		
        	}else{
        		int  money=getResources()
        				.getColor(R.color.money);
//        		 if(endtime!=null){
//            		 end_times.setText(endtime); 
//            		 end_times.setTextColor(money);
//            	 }
        		mdatas.setText(take_times);
        		
        		mdatas.setTextColor(money);
        	}
        	 
        }else if(zhuangtais.equals("2")){
        	zhuangtai.setText("待送件");
        	
       	 order_data.setText(order_datas);
       	 wanc.setText("送件预约时间");
        if(take_times.equals("")){
    		mdatas.setText("未预约");
    		
    	}else{
    		int  money=getResources()
    				.getColor(R.color.money);
//    		if(endtime!=null){
//       		 end_times.setText(endtime); 
//       		 end_times.setTextColor(money);
//       	 }
    		mdatas.setText(take_times);
    		
    		mdatas.setTextColor(money);
    		
    	}
        
        }else if(zhuangtais.equals("3")){
        	
        	
        	mdatas.setTextColor(grenn);
        	zhuangtai.setTextColor(grenn);
        	zhuangtai.setText("已支付");
        	mdatas.setText(take_times);
        	order_data.setText(order_datas);
        }
        
        if(Discount.equals("0")){
        	xiyijuan.setVisibility(View.GONE);
//        	mdatas.setText(take_times);
        	
        }else{mmoenys.setText(Discount);}
        
        
        if(MyOrderMain.lists!=null){
        order.setAdapter(new OrderdetailAdapter(Orderdetail.this,MyOrderMain.lists));
        setListViewHeightBasedOnChildren(order);
        
        }
        
        retun.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Orderdetail.this.finish();
				
			}
		});
    }
      //密码设置情况
      private void has_offer_password() {
    	  JsonObjectRequest jsonObjectPostRequest = new JsonObjectRequest(Request.Method.GET,Baseparam.USER+userid+"/", new Listener<JSONObject>(){

  			@Override
  			public void onResponse(JSONObject response) {
  				String mcredits= null;
  				try {
  					
  					 String  v=response.getJSONObject("data").getString("has_offer_password");
  					 if(!v.equals("")){
  						 Editor  sv=s.edit();
  						 sv.putString("has_offer_password", v);
  						 sv.commit();
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
  		
  		MyApplication.getInstance().getQueue(Orderdetail.this).add(jsonObjectPostRequest);
		
	}
	//删除订单
   protected void DELETE() {
	 
			JsonObjectRequest jsonObjectPostRequest = new JsonObjectRequest(Method.DELETE, Baseparam.DELETE+orderids+"/",new Listener<JSONObject>(){

				@Override
				public void onResponse(JSONObject response) {
					try {
						String code=response.getString("status_code");
						if("1".equals(code)){
							dialog=new DialogHint(Orderdetail.this, "删除成功", R.style.LoadingDialog);
							 dialog.show();
							 dialog.setCancelable(true);// 让progressDialog点击返回键...
							 dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
							//延迟关闭
								new Handler().postDelayed(new Runnable(){

									@Override
									public void run() {
										dialog.dismiss();
										Orderdetail.this.finish();
									}}, 1000);
		
						}else if("403".equals("code")){
							
							dialog=new DialogHint(Orderdetail.this, "身份信息验证失效，请重新登陆", R.style.LoadingDialog);
							 dialog.show();
							 dialog.setCancelable(true);// 让progressDialog点击返回键...
							 dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
							//延迟关闭
								new Handler().postDelayed(new Runnable(){

									@Override
									public void run() {
										dialog.dismiss();
										Intent  i=new  Intent(Orderdetail.this, MainActivity.class);
										startActivity(i);
										Orderdetail.this.finish();
									}}, 1000);
	
						}
					} catch (JSONException e) {
						
						e.printStackTrace();
					}
					
				}}, new Response.ErrorListener(){

					@Override
					public void onErrorResponse(VolleyError error) {
						dialog=new DialogHint(Orderdetail.this, "请求网络失败，请检查网络", R.style.LoadingDialog);
						 dialog.show();
						 dialog.setCancelable(true);// 让progressDialog点击返回键...
						 dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
						//延迟关闭
							new Handler().postDelayed(new Runnable(){

								@Override
								public void run() {
									dialog.dismiss();
								}}, 1000);
						
					}}){
				@Override
				public Map<String, String> getHeaders()
						throws AuthFailureError {
						HashMap localHash = new HashMap();
						localHash.put("Cookie", mcookie);
						return localHash;
				}
			};
					
			MyApplication.getInstance().getQueue(Orderdetail.this).add(jsonObjectPostRequest);
	}
// listView自定义高度
  	public void setListViewHeightBasedOnChildren(ListView order) {

  		ListAdapter listAdapter =order.getAdapter();

  		if (listAdapter == null) {
  			return;
  		}

  		int totalHeight = 0;

  		for (int i = 0; i < listAdapter.getCount(); i++) {
  			View listItem = listAdapter.getView(i, null, order);
  			listItem.measure(0, 0);
  			totalHeight += listItem.getMeasuredHeight();
  		}

  		ViewGroup.LayoutParams params = order.getLayoutParams();

  		params.height = totalHeight
  				+ (order.getDividerHeight() * (listAdapter.getCount() - 1));

  		((MarginLayoutParams) params).setMargins(0, 0, 0, 0); // 可删

  		order.setLayoutParams(params);
  	}
  	@Override
	public void onResume() {
  		super.onResume();
  		has_offer_password();
  	}

     protected void onDestroy() {
	super.onDestroy();
	MyOrderMain.lists.clear();
}
}
