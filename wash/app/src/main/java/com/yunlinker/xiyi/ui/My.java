package com.yunlinker.xiyi.ui;




import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yunlinker.xiyi.utils.DialogHint;
import com.yunlinker.xiyi.vov.Baseparam;
import com.yunlinker.xiyixi.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/*
 * 我的
 * 
 */
public class My extends Fragment implements OnClickListener{
	private final String TAG=My.class.getName();
//	private RequestQueue queue;
	private DialogHint mDialog;
	private  LinearLayout  xiyijuan;
	private View view;
	// 我的订单
	private ImageButton myorder;
	// 我的账户
	private ImageButton my_account;
	// 我的地址ַ
	private ImageButton ib_mylocation;
	// 我的分享码
	private ImageButton fenxiangma;
	String  mcookie,userid;
	// 验证推荐码
	private ImageButton yanzhengtuijian;
	// 电话.姓氏.推荐码
	private TextView tv_phone, tv_name, tuijianma, count;
	
	SharedPreferences sp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		// 取出之前保存的cookie
		SharedPreferences sharedPreferences =getActivity().getSharedPreferences("userinfo",
				Context.MODE_PRIVATE);
		String mcookie = sharedPreferences.getString("mcookie", "");
		
		if(mcookie.equals("")){
			Intent  intent=new Intent(getActivity(), MainActivity.class);
			startActivity(intent);
			}else{
		view = inflater.inflate(R.layout.my, null);
		xiyijuan=(LinearLayout) view.findViewById(R.id.cc);
		myorder = (ImageButton) view.findViewById(R.id.myorder);
		my_account = (ImageButton) view.findViewById(R.id.my_account);
		ib_mylocation = (ImageButton) view.findViewById(R.id.ib_mylocation);
		fenxiangma = (ImageButton) view.findViewById(R.id.fenxiangma);
		yanzhengtuijian = (ImageButton) view.findViewById(R.id.yanzhengtuijian);
		tv_phone = (TextView) view.findViewById(R.id.tv_phone);
		tv_name = (TextView) view.findViewById(R.id.tv_name);
		tuijianma = (TextView) view.findViewById(R.id.tuijianma);
		count = (TextView) view.findViewById(R.id.count);
		//设置字体
		Typeface typeFace =Typeface.createFromAsset(getActivity().getAssets(), "fonts/hanzhen.ttf");
		tuijianma .setTypeface(typeFace);
//		queue = Volley.newRequestQueue(getActivity());
		SharedPreferences mp = getActivity().getSharedPreferences(
				"userinfo", Context.MODE_PRIVATE);
		String username = mp.getString("username", "");
		String invite_code = mp.getString("invite_code", "");
		mcookie=mp.getString("mcookie", "");
		userid=mp.getString("id", "");
		xiyijuan();
//		String discount_count = mp.getString("discount_count", "");
		if (!username.equals("")) {
			tv_phone.setText(username);
		
			tuijianma.setText(invite_code);
//			count.setText(discount_count);
			
		}
		
		
		
		

		sp = getActivity().getSharedPreferences("locations",
				Context.MODE_PRIVATE);
		String mname = sp.getString("name", "");
		
		if (!mname.equals("")) {
			tv_name.setText(mname);
		}
		tv_name.setOnClickListener(this);
		my_account.setOnClickListener(this);
		myorder.setOnClickListener(this);
		ib_mylocation.setOnClickListener(this);
		fenxiangma.setOnClickListener(this);
		yanzhengtuijian.setOnClickListener(this);
		xiyijuan.setOnClickListener(this);
	}
		
		
		return view;
	}

	private void xiyijuan() {
		JsonObjectRequest jsonObjectPostRequest = new JsonObjectRequest(Request.Method.GET,Baseparam.USER+userid+"/", new Listener<JSONObject>(){

			@Override
			public void onResponse(JSONObject response) {
				String discount_count= null;
				try {
					String code=response.getString("status_code");
					if("1".equals(code)){
						discount_count = response.getJSONObject("data").getString("discount_count");
						count.setText(discount_count);
					}else if("403".equals("")){
						mDialog =new  DialogHint(getActivity(), "身份信息验证失效,请重新登陆",R.style.LoadingDialog);
						mDialog.show();
						mDialog.setCancelable(true);// 让progressDialog点击返回键...
						mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
						
						//延迟关闭
						new Handler().postDelayed(new Runnable(){

							@Override
							public void run() {
								mDialog.dismiss();
								Intent i=new  Intent(getActivity(), MainActivity.class);
								startActivity(i);
								getActivity().finish();
							}}, 1000);
						
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
//		queue.add(jsonObjectPostRequest);
		HomeActivity.startRequest(jsonObjectPostRequest, TAG);
	}
	@Override
	public void onResume() {
		super.onResume();
     
		sp = getActivity().getSharedPreferences("locations",
				Context.MODE_PRIVATE);
		String mnames = sp.getString("name", "");
		xiyijuan();
		
		if (!mnames.equals("")) {
			tv_name.setText(mnames);

			

			SharedPreferences mp = getActivity().getSharedPreferences(
					"userinfo", Context.MODE_PRIVATE);
			String username = mp.getString("username", "");
			String invite_code = mp.getString("invite_code", "");
//			String discount_count = mp.getString("discount_count", "");
			if (!username.equals("")) {
				tv_phone.setText(username);
				
				tuijianma.setText(invite_code);
//				count.setText(discount_count);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_mylocation:
			Intent intent = new Intent(getActivity(), Location.class);
			startActivity(intent);
			break;

		case R.id.my_account:
			Intent intent1 = new Intent(getActivity(), MyAccount.class);
			startActivity(intent1);
			break;

		case R.id.myorder:

			/**
			 * 获取服务端待取件
			 */
			getMyorder();
			Intent intent2 = new Intent(getActivity(), MyOrderMain.class);
			startActivity(intent2);
			break;

		case R.id.fenxiangma:
			Intent intent3 = new Intent(getActivity(), Share.class);
			startActivity(intent3);
			break;
		case R.id.yanzhengtuijian:
			Intent intent4 = new Intent(getActivity(), VerifyTuiJian.class);
			startActivity(intent4);
			break;
		case R.id.tv_name:
			Intent intent5 = new Intent(getActivity(), Location.class);
			startActivity(intent5);
			break;
			
		case R.id.cc:
			Intent intent6= new Intent(getActivity(),XiYiJuan.class);
			startActivity(intent6);
			break;
		}

	}

	private void getMyorder() {

	}
  @Override
public void onDestroy() {
	super.onDestroy();
	if(myorder!=null){
	Drawable 	drawable=myorder.getDrawable();
	Drawable 	drawable1=my_account.getDrawable();
	Drawable 	drawable2=ib_mylocation.getDrawable();
	Drawable 	drawable3=fenxiangma.getDrawable();
	Drawable 	drawable4=yanzhengtuijian.getDrawable();
	
	if(drawable!=null){
		drawable.setCallback(null);
	}if(drawable1!=null){
		drawable1.setCallback(null);
	}if(drawable2!=null){
		drawable2.setCallback(null);
	}if(drawable3!=null){
		drawable3.setCallback(null);
	}if(drawable4!=null){
		drawable4.setCallback(null);
	}
	}
	HomeActivity.camcelRequest(TAG);
}
   
}
