package com.yunlinker.xiyi.fragemnt;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunlinker.xiyixi.R;
import com.yunlinker.xiyi.Adapter.JianXiAdapter;
import com.yunlinker.xiyi.bean.BasketBean;
import com.yunlinker.xiyi.bean.JianXiJavaBean;
import com.yunlinker.xiyi.bean.Results;
import com.yunlinker.xiyi.ui.HomeActivity;
import com.yunlinker.xiyi.ui.JianXiMain;
import com.yunlinker.xiyi.utils.jianxipopuwin;
import com.yunlinker.xiyi.vov.Baseparam;

import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * 件洗选项卡1
 * 
 * @author Administrator
 *
 */
public class JianXiOne extends Fragment implements OnItemClickListener {

	private final String TAG =JianXiOne.class.getName();
	private com.yunlinker.xiyi.utils.LoadingDialog mDialog;
	private jianxipopuwin tanchuang;
	private View view;
	private GridView grid1;
  List<Results> result;
  private  Handler handler;
   boolean  isonclk=true;
	// 创建请求队列
//	private RequestQueue queue;

	public void setHandler(Handler handler) {
		this.handler=handler;
		
	}
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.jianxi_one, null);
		grid1 = (GridView) view.findViewById(R.id.grid1);
		isonclk=true;
//		queue = Volley.newRequestQueue(getActivity());
		grid1.setOnItemClickListener(this);
//		grid1.setClickable(true);
		String name = "";
		try {
			name = URLEncoder.encode("春/秋装", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringRequest stringRequest = new StringRequest(Method.GET,
				Baseparam.PRODUCTS_four+name, new Listener<String>() {

					@Override
					public void onResponse(String response) {

						System.out.println(response);
						// javaBeanList=GetListByjson(response);
						GetListByjson(response);

						// grid1.setAdapter(new
						// JianXiAdapter(javaBeanList,getActivity()));
						grid1.setAdapter(new JianXiAdapter(result,
								getActivity()));

						// System.out.println("集合数据数量"+javaBeanList.size());
						// System.out.println("测试集合数据"+javaBeanList.get(0).toString());

					}

				}, new Response.ErrorListener() {

					public void onErrorResponse(VolleyError error) {
						Toast.makeText(getActivity(), "网络不稳定，请求失败", 0).show();
						mDialog.dismiss();
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
		};
		// stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
		// DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
		// DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//		queue.add(stringRequest);
		JianXiMain.startRequest(stringRequest, TAG);
		
		mDialog = new com.yunlinker.xiyi.utils.LoadingDialog(getActivity(), "亲，请耐心等一会哦...",R.style.LoadingDialog);
		mDialog.show();
		mDialog.setCancelable(true);// 让progressDialog点击返回键...
		mDialog.setCanceledOnTouchOutside(true);// 点击其他地方dismissDialog
		
//		//延迟关闭
//		new Handler().postDelayed(new Runnable(){

//			@Override
//			public void run() {
				
				
//			}}, 2000);
		 
    
		  
		return view;
		
		
	}
    

	private void GetListByjson(String json) {
		JSONObject jsonobject;
		try {
			jsonobject = new JSONObject(json);
			String dataList = jsonobject.getString("data");
			Gson gson = new Gson();
			JianXiJavaBean JianXi = gson.fromJson(dataList,
					JianXiJavaBean.class);
			result = JianXi.getResults();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			mDialog.dismiss();
		}
	}

	Results e;
//  int i=0;
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(!isonclk)
			return;
		isonclk=false;
//		grid1.setClickable(false);

		/**
		 * 得到点击子view的参数
		 */
		
		e = (Results) (grid1).getItemAtPosition(arg2);
		
	ImageView  s=(ImageView) arg1.findViewById(R.id.itme_image);

		
		
			Drawable  image=s.getDrawable();
			boolean isHave=false;
			BasketBean bean=null ;
			
		
			for (BasketBean b  : HomeActivity.list) {
				
				if((b.getName()).equals(e.getName())){
					
	
					
					isHave=true;
					bean=b;
					
				}
				
			}
			
      
		tanchuang = new jianxipopuwin(getActivity(), e,image,isHave,bean,handler);
		tanchuang.showAtLocation(getView().findViewById(R.id.lay_home),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		tanchuang.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				isonclk=true;
			}
		});
	}

	// private void setName(Object object) {
	//
	//
	// }
	
	@Override
	public void onDestroy() {

		super.onDestroy();
//		queue.cancelAll(TAG);
		JianXiMain.camcelRequest(TAG);
	}


	

	
}
