package com.yunlinker.xiyi.ui;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;
import com.yunlinker.xiyi.bean.AddressBean;
import com.yunlinker.xiyi.bean.AddressBean.area;
import com.yunlinker.xiyi.bean.BasketBean;
import com.yunlinker.xiyi.bean.JianXiJavaBean;
import com.yunlinker.xiyi.bean.PasswordQuestionsBean;
import com.yunlinker.xiyi.bean.Results;
import com.yunlinker.xiyi.bean.banner;
import com.yunlinker.xiyi.bean.bannerBean;
import com.yunlinker.xiyi.fragemnt.JianXiOne;
import com.yunlinker.xiyi.utils.CustomDialog;
import com.yunlinker.xiyi.utils.DialogHint;
import com.yunlinker.xiyi.utils.daixipopuwin;
import com.yunlinker.xiyi.utils.jianxipopuwin;
import com.yunlinker.xiyi.vov.Baseparam;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView.FindListener;
import android.widget.ImageButton;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;

public class Home extends Fragment implements OnClickListener {
	public  static JSONArray  Arraybanner=new JSONArray();
	private final String TAG =Home.class.getName();
	//升级描述
	private String description;
	SharedPreferences 	Banner;
	StringRequest stringRequest;
	private DialogHint mDialog;
	// cookie
	private String mcookie;
	private Context context;
//	private RequestQueue queue;
	HomeActivity  activty;
	List<Results> result;
	public int i = 0;
	private View view;
	private ImageButton ibn_daixi, server1,home_supplies;
	private ImageButton ibn_jianxi;
	private daixipopuwin tanchuang;
    private  ImageButton  else_wash;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
  
		view = inflater.inflate(R.layout.home, null);

		ibn_daixi = (ImageButton) view.findViewById(R.id.ibn_daixi);
		ibn_jianxi = (ImageButton) view.findViewById(R.id.ibn_jianxi);
		server1 = (ImageButton) view.findViewById(R.id.server1);
		home_supplies=(ImageButton) view.findViewById(R.id.home_supplies);
		else_wash=(ImageButton) view.findViewById(R.id.else_wash);
		activty=(HomeActivity) getActivity();
	   
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
//		queue = Volley.newRequestQueue(getActivity());
		
		Banner=getActivity().getSharedPreferences(
				"banner", Context.MODE_PRIVATE);
	
		// cookie
		SharedPreferences sharedPreferences = getActivity()
				.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		mcookie = sharedPreferences.getString("mcookie", "");
    
//		Banner();
		//版本
	    if(MyApplication.instance.islog){
	    	
		version();
		}
		home_supplies.setOnClickListener(this);
		server1.setOnClickListener(this);
		ibn_daixi.setOnClickListener(this);
		ibn_jianxi.setOnClickListener(this);
		else_wash.setOnClickListener(this);
		
		String name = "";
		try {
			name = URLEncoder.encode("袋洗", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	stringRequest = new StringRequest(Method.GET,
			Baseparam.PRODUCTS_four+name, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						
						GetListByjson(response);

						
                      
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {


					}
				}) {
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

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap local = new HashMap();
				local.put("Cookie", mcookie);
				return local;

			}
		};
//		queue.add(stringRequest);
		HomeActivity.startRequest(stringRequest, TAG);
		
	
		return view;
	}
	
    private void version() {
    	
		JsonObjectRequest  Jsonrequest=new  JsonObjectRequest(Request.Method.GET,Baseparam.VERSION, new Listener<JSONObject>(){
			@Override
			public void onResponse(JSONObject response){
				try {
					
				  String	versions=response.getJSONObject("data").getString("version");
//					Log.d("我的版本", "我的"+versions);
//				  Toast.makeText(getActivity(), "我的"+versions, 2000).show();
					
				    description=response.getJSONObject("data").getString("description");
				   
					 //获得系统版本
						PackageManager manager = getActivity().getPackageManager();
						PackageInfo info= manager.getPackageInfo(getActivity().getPackageName(),0);
						String	version=info.versionName;
						  
						
					if(!version.equals(versions)){
//						showAlertDialog();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} , new Response.ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError error) {
				
				
			}});
//		queue.add(Jsonrequest);
		HomeActivity.startRequest(Jsonrequest, TAG);
	}
    //版本升级弹窗
//	protected void showAlertDialog() {
//		CustomDialog.Builder  builder=new  CustomDialog.Builder(getActivity());
//
//		if(description!=null&&!description.equals("")){
//			builder.setMessage("description");
//		}
//		else{
//		builder.setMessage("有最新版本");
//		}
//		builder.setTitle("有最新版本,是否升级？");
//		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				Intent  i=new  Intent(activty,AppUpdate.class);
//				startActivity(i);
//				dialog.dismiss();
//				//设置你的操作事项
//			}
//		});
//
//		builder.setNegativeButton("取消",
//				new android.content.DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						MyApplication.instance.islog=false;
//						dialog.dismiss();
//						
//					}
//				});
//
//		builder.create().show();
//
//	}
	//获取的banner
//	private void Banner() {
//		StringRequest string = new StringRequest(Baseparam.BANNERS, new Listener<String>(){
//
//			@Override
//			public void onResponse(String response) {
//				
//				JSONObject jsonobject;
//				
//					try {
//						jsonobject = new JSONObject(response);
//						int status_code = jsonobject.getInt("status_code");
//						if (status_code == 1) {
//							JSONArray array = jsonobject.getJSONArray("data");
//							Arraybanner=array;
////								JSONObject   js=(JSONObject) array.get(0);
////							String	image1=js.getString("image");
////							JSONObject   js1=(JSONObject) array.get(1);
////							String	image2=js.getString("image");
////							
////							Editor  E=Banner.edit();
////							 E.putString("IMAGE1", image1);
////							 E.putString("IMAGE2", image2);
////							 E.commit();
//						}
//						Log.e("数量ddddd", "数量"+Arraybanner.length());
//						
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				
//			}}, new Response.ErrorListener(){
//
//				@Override
//				public void onErrorResponse(VolleyError error) {
//					
//					
//				}});
		
//		queue.add(string);
//		HomeActivity.startRequest(string, TAG);
//	}

	List<Results> list=new ArrayList<Results>();
	private void GetListByjson(String json) {
	
		JSONObject jsonobject;
		try {
			jsonobject = new JSONObject(json);
			String dataList = jsonobject.getString("data");
			Gson gson = new Gson();
			if(!dataList.equals("")){
			JianXiJavaBean JianXi = gson.fromJson(dataList,
					JianXiJavaBean.class);
			list = JianXi.getResults();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}finally{
			result=list;
		}
		
		

	}
	

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.ibn_daixi:

			
         if(result!=null){
  
			tanchuang = new daixipopuwin(getActivity(), result,activty);
			tanchuang.showAtLocation(getView().findViewById(R.id.lay_home),
					Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

         }else{
        	 Toast.makeText(getActivity(), "请检查网络", 3000).show();
         }
			
         break;
		case R.id.ibn_jianxi:
			Intent intent = new Intent();
			intent.setClass(getActivity(), JianXiMain.class);
			// getActivity().startActivityForResult(intent, 0);
			startActivity(intent);

			break;
		// 服务说明
		case R.id.server1:
			Intent inetnt3 = new Intent(getActivity(), ServeExplain.class);
			startActivity(inetnt3);
			break;
		case  R.id.home_supplies:
			Intent inetnt4=new Intent(getActivity(), HomeSupplies.class);
			startActivity(inetnt4);
			break;
		case R.id.else_wash:
			 mDialog =new  DialogHint(getActivity(), "此模块暂未开放，敬请期待",R.style.LoadingDialog);
				mDialog.show();
				mDialog.setCancelable(true);// 让progressDialog点击返回键...
				mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
				
				//延迟关闭
				new Handler().postDelayed(new Runnable(){

					@Override
					public void run() {
						MyApplication.instance.islog=false;
						mDialog.dismiss();
						
					}}, 1000);
			break;
		}

	}

	


   @Override
public void onDestroy() {
	super.onDestroy();
	if(ibn_daixi!=null){
	Drawable 	drawable=ibn_daixi.getDrawable();
	Drawable 	drawable1=ibn_jianxi.getDrawable();
	Drawable 	drawable2=server1.getDrawable();
	Drawable 	drawable3=home_supplies.getDrawable();
	Drawable 	drawable4=else_wash.getDrawable();
	if (drawable != null){
		// 解除drawable对view的引用
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
	
	System.gc();
	}
	HomeActivity.camcelRequest(TAG);
}
}
