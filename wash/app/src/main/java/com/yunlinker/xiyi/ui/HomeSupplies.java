package com.yunlinker.xiyi.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;
import com.yunlinker.xiyi.Adapter.JianXiAdapter;
import com.yunlinker.xiyi.bean.BasketBean;
import com.yunlinker.xiyi.bean.JianXiJavaBean;
import com.yunlinker.xiyi.bean.Results;
import com.yunlinker.xiyi.bean.logbean;
import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyi.utils.jianxipopuwin;
import com.yunlinker.xiyi.vov.Baseparam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity.Header;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

//家居用品
public class HomeSupplies extends  BaseActivity  implements  OnClickListener, OnItemClickListener{
	private final String TAG=HomeSupplies.class.getName();
	private com.yunlinker.xiyi.utils.LoadingDialog mDialog;
	private jianxipopuwin tanchuang;
	private  ImageButton  btn_basekt;
	boolean  isonclk=true;
	String mcookie;
	//创建请求队列
//	private RequestQueue queue;
	private  GridView  Gridview;
   private  ImageButton  return17;
   List<Results> result;
//   private   Button  btn_baskets;
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.home_supplies);
    	
    	return17=(ImageButton) findViewById(R.id.return17);
    	Gridview=(GridView) findViewById(R.id.grid5);
//    	btn_baskets=(Button) findViewById(R.id.btn_baskets);
    	btn_basekt=(ImageButton) findViewById(R.id.btn_basektv);
    	boolean  isonclk=true;
    	return17.setOnClickListener(this);
    	Gridview.setOnItemClickListener(this);
    	// 取出cookie
    				SharedPreferences sharedPreferences = HomeSupplies.this.getSharedPreferences(
    						"userinfo", Context.MODE_PRIVATE);
    				mcookie = sharedPreferences.getString("mcookie", "");
    			
//    	queue = Volley.newRequestQueue(HomeSupplies.this);
    		MyApplication.getInstance().getQueue(HomeSupplies.this);
    	// 方便后续退出整个应用程序
//    			MyApplication.getInstance().addActivity(HomeSupplies.this);
    		havedata();	
    			
    	
    		btn_basekt.setOnClickListener(this);	
    			
    }
    @Override
	public void onResume() {
    	super.onResume();
    	
    	if(HomeActivity.list.size()!=0){
    		btn_basekt.setBackgroundResource(R.drawable.folat_basket_nol);
    	}else{btn_basekt.setBackgroundResource(R.drawable.folat_basket_sel);}
    }
	private void havedata() {
		
		String name = "";
		try {
			name = URLEncoder.encode("家居用品", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	   
		StringRequest stringRequest = new StringRequest(Method.GET,Baseparam.HOME_SUPPLIES+name  ,new Listener<String>(){
        
		
			@Override
			public void onResponse(String response) {
				
				GetListByjson(response);
				if(result.size()!=0){
				Gridview.setAdapter(new JianXiAdapter(result,
						HomeSupplies.this));
				}
			}}, new Response.ErrorListener(){

				@Override
				public void onErrorResponse(VolleyError error) {
					Toast.makeText(HomeSupplies.this, "网络不稳定，请求失败", 0).show();
					mDialog.dismiss();
				}}){
			/**
			 * 转换为utf_8编码
			 */
			protected final String TYPE_UTF8_CHARSET = "charset=UTF-8";

			@Override
			protected Response<String> parseNetworkResponse(
					NetworkResponse response) {
				// TODO Auto-generated method stub
				try {
					String type = response.headers.get(HTTP.CONTENT_TYPE);
					if (type == null) {
						type = TYPE_UTF8_CHARSET;
						response.headers.put(HTTP.CONTENT_TYPE, type);
					} else if (!type.contains("UTF-8")) {
						type += ";" + TYPE_UTF8_CHARSET;
						response.headers.put(HTTP.CONTENT_TYPE, type);
					}
				} catch (Exception e) {
				}
				return super.parseNetworkResponse(response);

			}
			};
//			queue.add(stringRequest);
			MyApplication.getInstance().getQueue(HomeSupplies.this).add(stringRequest);
			mDialog = new com.yunlinker.xiyi.utils.LoadingDialog(HomeSupplies.this, "亲，请耐心等一会哦...",R.style.LoadingDialog);
    		mDialog.show();
    		mDialog.setCancelable(true);// 让progressDialog点击返回键...
    		mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
//    		//延迟关闭
//			new Handler().postDelayed(new Runnable(){

//				@Override
//				public void run() {
					
					
//				}}, 3000);
	}
	//解析json
	protected void GetListByjson(String response) {
		JSONObject jsonobject;
		try {
			jsonobject = new JSONObject(response);
			String dataList = jsonobject.getString("data");
			Gson gson = new Gson();
			if(dataList!=null){
			JianXiJavaBean JianXi = gson.fromJson(dataList,
					JianXiJavaBean.class);
			result = JianXi.getResults();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}finally{
			mDialog.dismiss();
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return17:
//			Intent  intent=new  Intent(this, HomeActivity.class);
//			startActivity(intent);
			HomeSupplies.this.finish();
			break;

		case  R.id.btn_basektv:
			Intent  intentf=new Intent(HomeSupplies.this, HomeActivity.class);
			intentf.putExtra("renturns","1");
			startActivity(intentf);
			HomeSupplies.this.finish();
			break;
		}
		
	}
	Results e;
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(!isonclk)
			return;
		isonclk=false;
		/**
		 * 得到点击子view的参数
		 */

		e = (Results) (Gridview).getItemAtPosition(position);
		
	ImageView  s=(ImageView) view.findViewById(R.id.itme_image);

		
		
			Drawable  image=s.getDrawable();
			boolean isHave=false;
			BasketBean bean=null ;
		
			for (BasketBean b  : HomeActivity.list) {
				
				if((b.getName()).equals(e.getName())){
					
					isHave=true;
					bean=b;
					
				}
			}

		tanchuang = new jianxipopuwin(HomeSupplies.this, e,image,isHave,bean,handler);
		tanchuang.showAtLocation(this.findViewById(R.id.homsupp),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		tanchuang.setOnDismissListener(new  OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				isonclk=true;
			}
		});     
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
//		 queue.cancelAll(HomeSupplies.this);
		 MyApplication.getInstance().getQueue(HomeSupplies.this).cancelAll(TAG);
	
	}
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 12) {
				boolean onclik=(Boolean) msg.obj;
				if(onclik=true){
				btn_basekt.setBackgroundResource(R.drawable.folat_basket_nol);}
				}
		}};
}
