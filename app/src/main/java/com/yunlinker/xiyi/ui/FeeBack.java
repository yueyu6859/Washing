package com.yunlinker.xiyi.ui;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;
import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyi.utils.DialogHint;
import com.yunlinker.xiyi.vov.Baseparam;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 意见反馈
 * 
 * @author Administrator
 *
 */
public class FeeBack extends BaseActivity {

	private DialogHint mDialog;

	// 请求队列
//		private RequestQueue queue;
	// 返回
	private ImageButton return7;
    private   Button   tijiao; 
    private  TextView   et_shuru;
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    }
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// 方便后续退出整个应用程序
//		MyApplication.getInstance().addActivity(FeeBack.this);

		setContentView(R.layout.feeback);
		return7 = (ImageButton) findViewById(R.id.return7);
		tijiao=(Button) findViewById(R.id.tijiao);
		et_shuru=(TextView) findViewById(R.id.et_shuru);
//		queue = Volley.newRequestQueue(getApplicationContext());
		MyApplication.getInstance().getQueue(FeeBack.this);
		tijiao.setOnClickListener(new  OnClickListener() {
			
			public void onClick(View v) {
				if(et_shuru.getText().toString().equals("")){
					 mDialog =new  DialogHint(FeeBack.this, "请输入您的建议",R.style.LoadingDialog);
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
				mMap.put("content", et_shuru.getText().toString());
				JSONObject jsonRequest = new JSONObject(mMap);
				JsonObjectRequest jsonObjectPostRequest = new JsonObjectRequest(Request.Method.POST, Baseparam.FEEDBACKS,jsonRequest, new Listener<JSONObject>(){

					@Override
					public void onResponse(JSONObject response) {
						Log.d("", ""+response);
						try {
							String fee=response.getString("data");
							
							if(!fee.equals("")){
								 mDialog =new  DialogHint(FeeBack.this, "提交成功",R.style.LoadingDialog);
									mDialog.show();
									mDialog.setCancelable(true);// 让progressDialog点击返回键...
									mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
									
									//延迟关闭
									new Handler().postDelayed(new Runnable(){

										@Override
										public void run() {
											mDialog.dismiss();
											et_shuru.setText("");
											FeeBack.this.finish();
										}}, 1000);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}}, new Response.ErrorListener(){

						@Override
						public void onErrorResponse(VolleyError error) {
							Toast.makeText(FeeBack.this, "网络不稳定，请求失败", 0).show();
							
						}});
//				queue.add(jsonObjectPostRequest);
				MyApplication.getInstance().getQueue(FeeBack.this).add(jsonObjectPostRequest);
				
			}
			}});
		return7.setOnClickListener(new View.OnClickListener() {

		      
			public void onClick(View v) {
				FeeBack.this.finish();

			}
		});
	}
     
}
