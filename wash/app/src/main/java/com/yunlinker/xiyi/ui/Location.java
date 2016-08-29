package com.yunlinker.xiyi.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.yunlinker.xiyi.bean.AddressBean;
import com.yunlinker.xiyi.bean.HousingBean;
import com.yunlinker.xiyi.bean.HousingResults;
import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyi.utils.DialogHint;
import com.yunlinker.xiyi.utils.LoadingDialog;
import com.yunlinker.xiyi.vov.Baseparam;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

/**
 * 地址ַ
 * 
 * @author Administrator
 *
 */
public class Location extends BaseActivity implements OnClickListener, Serializable {
	private final String TAG=Location.class.getName();
	private  DialogHint  dialog;
	private LoadingDialog mDialog;
	private ImageButton return5;
	// //返回的小区地址
	// public static List<HousingResults>results;
	// 切换的界面
	private LinearLayout exist, nulls;
	// 请求队列
//	private RequestQueue queue;
	// 姓名框,电话框，单元地址
	private EditText names, phone, location;
	// 已填写的地址ַ
	private TextView exist_name, exist_phone, exist_location,title;
	// 先生，女士点击事件,保存,修改
	private Button man, woman, save_address_button, modification;
	
	private String mcookie,mname,vnames;
	private String  have;
	private String name;
	// 小区id
	private String area_id;
	private ImageView image_front;
	private TextView have_housing;
	private RelativeLayout housing;
	SharedPreferences sp;

	public void onResume() {
		super.onResume();
//		have_location();
		// 显示小区
		if (sp == null)
			sp = getSharedPreferences("locations", Context.MODE_PRIVATE);
		area_id = sp.getString("housingId", "");
		String v = sp.getString("housing", "");
//
//	
//		names.getText().toString();
//		phone.getText().toString();
//
		image_front.setVisibility(View.INVISIBLE);
		have_housing.setVisibility(View.VISIBLE);
		have_housing.setText(v);

	}

	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);
		// 取出之前保存的cookie
				SharedPreferences sharedPreferences = getSharedPreferences("userinfo",
						Context.MODE_PRIVATE);
				mcookie = sharedPreferences.getString("mcookie", "");
		
//		queue = Volley.newRequestQueue(getApplicationContext());
				MyApplication.getInstance().getQueue(Location.this);
		// 姓名框
		names = (EditText) findViewById(R.id.latn_name);

		

		// 电话输入框
		phone = (EditText) findViewById(R.id.phone);
		title=(TextView) findViewById(R.id.title);
		// 先生女士点击事件
		man = (Button) findViewById(R.id.man);
		woman = (Button) findViewById(R.id.woman);

		exist = (LinearLayout) findViewById(R.id.exist);
		image_front = (ImageView) findViewById(R.id.image_front);
		// 显示小区
		have_housing = (TextView) findViewById(R.id.have_housing);
		nulls = (LinearLayout) findViewById(R.id.nulls);
		// 详细地址
		location = (EditText) findViewById(R.id.mlocation);
		housing = (RelativeLayout) findViewById(R.id.housing);
		return5 = (ImageButton) findViewById(R.id.return5);
		// 保存按钮
		save_address_button = (Button) findViewById(R.id.save_address_button);
		// 修改订单
		modification = (Button) findViewById(R.id.modification);
		exist_name = (TextView) findViewById(R.id.exist_name);
		exist_phone = (TextView) findViewById(R.id.exist_phone);
		exist_location = (TextView) findViewById(R.id.exist_location);
		

		// 方便后面关闭程序
//		MyApplication.getInstance().addActivity(Location.this);

		
		// 如果有地址信息
		have_location();
		man.setOnClickListener(new MyOnClickListener(0));
		woman.setOnClickListener(new MyOnClickListener(1));
		housing.setOnClickListener(this);
		save_address_button.setOnClickListener(this);
		return5.setOnClickListener(this);
		
	}
	String mphone;
	String locations,mnames;
	
	// 如果有地址信息
	private void have_location() {
		SharedPreferences shared = this.getSharedPreferences("locations",
				Context.MODE_PRIVATE);
	
		mnames = shared.getString("name", "");
		mphone = shared.getString("phone", "");
		locations= shared.getString("location", "");
		
		
		phone .setText(mphone );
		names.setText(mnames);
		location.setText(locations);
		Intent  intent=getIntent();
		String  baekt=intent.getStringExtra("Locations");
		
		
		if(baekt!=null){
          
			names.setText(mnames);
			location.setText(locations);
			phone.setText(mphone);
			
		}
		else if 
			(!mnames.equals("")){
	
			modification.setVisibility(View.VISIBLE);
			exist.setVisibility(View.VISIBLE);
			nulls.setVisibility(View.INVISIBLE);
			title.setText("我的地址");
			
			
			
			
			exist_name.setText(mnames);
			exist_phone.setText(mphone);
			exist_location.setText(locations);
			modification.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					title.setText("用户信息");
					SharedPreferences shareds = Location.this
							.getSharedPreferences("locations",
									Context.MODE_PRIVATE);
					
					phone .setText(mphone );
					names.setText(mnames);
					location.setText(locations);
					
					Editor edit = shareds.edit();
					edit.clear();
					edit.commit();
					modification.setVisibility(View.INVISIBLE);
	
					exist.setVisibility(View.INVISIBLE);
					nulls.setVisibility(View.VISIBLE);
				}
			});
		} 
	
	}

	


	

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save_address_button:

			/**
			 * 保存信息
			 */
			// SharedPreferences sharedPreferences =
			// this.getSharedPreferences("locations",
			// Context.MODE_APPEND);
			// Editor editor = sharedPreferences.edit();// 达到编辑器
			// editor.putString("name",name);
			// Log.d("姓名", name);
			// editor.putString("location", location.getText().toString());
			//
			// editor.putString("phone", phone.getText().toString());
			// editor.putString("home", area_id);
			// editor.commit();// 提交
			if(names.getText().toString().equals("")){
				 dialog = new DialogHint(Location.this, "请输入姓名",R.style.LoadingDialog);
				 dialog.show();
				 dialog.setCancelable(true);// 让progressDialog点击返回键...
				 dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
				
				//延迟关闭
				new Handler().postDelayed(new Runnable(){

					@Override
					public void run() {
						dialog.dismiss();

					}}, 3000);
				
			}else if(phone.getText().toString().length()!=11){
				dialog = new DialogHint(Location.this, "请输入正确电话号码",R.style.LoadingDialog);
				dialog.show();
				dialog.setCancelable(true);// 让progressDialog点击返回键...
				dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
				
				//延迟关闭
				new Handler().postDelayed(new Runnable(){

					@Override
					public void run() {
						dialog.dismiss();
						
					}}, 1000);
			}else if(have_housing.getText().toString().equals("")){
				dialog = new DialogHint(Location.this, "请输入小区",R.style.LoadingDialog);
				dialog .show();
				dialog .setCancelable(true);// 让progressDialog点击返回键...
				dialog .setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
				
				//延迟关闭
				new Handler().postDelayed(new Runnable(){

					@Override
					public void run() {
						dialog .dismiss();
						
					}}, 1000);
			}else  if(location.getText().toString().equals("")){
				dialog  = new DialogHint(Location.this, "请输入详细地址",R.style.LoadingDialog);
				dialog.show();
				dialog.setCancelable(true);// 让progressDialog点击返回键...
				dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
				
				//延迟关闭
				new Handler().postDelayed(new Runnable(){

					@Override
					public void run() {
						dialog.dismiss();
					}}, 1000);
			}else{

			
			mDialog = new LoadingDialog(Location.this, "正在提交！",R.style.LoadingDialog);
			mDialog.show();
			mDialog.setCancelable(true);// 让progressDialog点击返回键...
			mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
			// 提交到服务端
						submit();
			//延迟关闭
//			new Handler().postDelayed(new Runnable(){

//				@Override
//				public void run() {
					
//				}}, 3000);
					}
			
			
			break;

		case R.id.housing:
			if (HomeActivity.results != null) {
				Intent intent = new Intent(this, Index_Housing.class);
				// 传递小区
				// intent.putExtra("listhous", (Serializable)results);

				startActivity(intent);

				break;
			} else {
				Toast.makeText(Location.this, "请重新登陆", Toast.LENGTH_LONG).show();
				break;
			}

		case R.id.return5:
			// Intent inetnt=new Intent(this, HomeActivity.class);
			// startActivityForResult(inetnt, 6);

			Location.this.finish();
		}

	}

	private void submit() {
		 if("2".equals(have)){
			name=names.getText().toString();
		}else if("1".equals(have)){
			name=names.getText().toString()+mname;
		}else if("3".equals(have)){
			name=vnames;
		}else{
			if(names.getText().toString().length()>2){
				String anes=names.getText().toString().substring(mnames.length()-2);
				if("女士".equals(anes)){
					name=names.getText().toString().replaceAll("女士","先生");
				}else if("先生".equals(anes)){
					name=names.getText().toString();
				}else{name=names.getText().toString()+"先生";}
			}else{
			name=names.getText().toString()+"先生";}
		}
		//////////////////////////////////////////////////
//		if(mname==null){
//			name=names.getText().toString()+"先生";
//		}else{
//			name=names.getText().toString()+mname;
//		}

		HashMap<String, String> mMap = new HashMap<String, String>();
		mMap.put("name", name);
		
		
		mMap.put("tel", phone.getText().toString());
		
		mMap.put("area_id", area_id);
		mMap.put("detail_address", location.getText().toString());
		JSONObject jsonRequest = new JSONObject(mMap);
		JsonObjectRequest jsonObjectPostRequest = new JsonObjectRequest(
				Request.Method.POST, Baseparam.UPDATE_USER_ADDRESS,
				jsonRequest, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							String code=response.getString("status_code");
							if("1".equals(code)){
								SeverLocation(response);
							}else  if("403".equals(code)){
								mDialog.dismiss();
								dialog  = new DialogHint(Location.this, "身份信息验证失效,请重新登陆！",R.style.LoadingDialog);
								dialog.show();
								dialog.setCancelable(true);// 让progressDialog点击返回键...
								dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
								
								//延迟关闭
								new Handler().postDelayed(new Runnable(){

									@Override
									public void run() {
										dialog.dismiss();
										Intent  i=new Intent(Location.this, MainActivity.class);
										startActivity(i);
										Location.this.finish();
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
						mDialog.dismiss();
						dialog  = new DialogHint(Location.this, "请检查网络！",R.style.LoadingDialog);
						dialog.show();
						dialog.setCancelable(true);// 让progressDialog点击返回键...
						dialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
						
						//延迟关闭
						new Handler().postDelayed(new Runnable(){

							@Override
							public void run() {
								dialog.dismiss();
								Intent  i=new Intent(Location.this, HomeActivity.class);
								startActivity(i);
							}}, 1000);
					}
				}) {
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap localHashMap = new HashMap();
				localHashMap.put("Cookie", mcookie);
				return localHashMap;
			}
		};
//		queue.add(jsonObjectPostRequest);
		MyApplication.getInstance().getQueue(Location.this).add(jsonObjectPostRequest);

	}

	// 解析服务端返回的地址信息
	protected void SeverLocation(JSONObject response) {

		try {
			String mdata = (response).getString("data");
			Gson gson = new Gson();

			AddressBean address = gson.fromJson(mdata, AddressBean.class);
			
			SharedPreferences sharedPreferences = this.getSharedPreferences(
					"locations", Context.MODE_APPEND);
			Editor editor = sharedPreferences.edit();// 达到编辑器
			editor.putString("name", name);
			editor.putString("location", location.getText().toString());
			editor.putString("phone", phone.getText().toString());
			editor.putString("home", area_id);
			editor.putString("id", address.getId());

			editor.commit();// 提交
           Location.this.finish();
		} catch (JSONException e) {
			Gson gson = new Gson();
			e.printStackTrace();
		}finally{
			mDialog.dismiss();
		}

	}

	// //女士，男士按钮点击变色情况
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			// 点击后颜色
			int selectedColor = getResources().getColor(R.color.main_color);
			int selectedTextcoler = getResources().getColor(R.color.white);

			int unSelectedColor = getResources().getColor(R.color.white);
			int unselectedTextcoler = getResources().getColor(R.color.zi_er);

			switch (index) {
			case 0:
				woman.setBackgroundColor(unSelectedColor);
				woman.setTextColor(unselectedTextcoler);
				man.setBackgroundColor(selectedColor);
				man.setTextColor(selectedTextcoler);
				if(names.getText().toString().length()>2){
					String manesa=names.getText().toString();
							String manes=manesa.substring(manesa.length()-2);
					if(manes!=null&&"先生".equals(manes)){
						mname ="";
						have="2";
					}else if(manes!=null&&"女士".equals(manes)){
						vnames=names.getText().toString().replaceAll("女士","先生");
						have="3";
					}else{
						have="1";	
	////////////////////////
					mname ="先生";
					}
				}else{
					have="1";	
////////////////////////
				mname ="先生"; 
				}
				
				
				
				
				
				break;

			case 1:
				woman.setBackgroundColor(selectedColor);
				woman.setTextColor(selectedTextcoler);
				man.setBackgroundColor(unSelectedColor);
				man.setTextColor(unselectedTextcoler);	
				if(names.getText().toString().length()>2){
					String maness=names.getText().toString();
					String manes=maness.substring(maness.length()-2);
					if(manes!=null&&"女士".equals(manes)){
						mname ="";
						have="2";
					}else if(manes!=null&&"先生".equals(manes)){
						vnames=names.getText().toString().replaceAll("先生","女士");
						have="3";
					}else {
						have="1";	
						mname ="女士";
					}
				}else{
					have="1";	
					mname ="女士";
				}
				
				
				break;
			}
		}
	}
    @Override
    protected void onDestroy() {
    	super.onDestroy();
//    	queue.cancelAll(Location.this);Location
    	MyApplication.getInstance().getQueue(Location.this).cancelAll(TAG);
    }
}
