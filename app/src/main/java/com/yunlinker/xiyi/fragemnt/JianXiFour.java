package com.yunlinker.xiyi.fragemnt;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
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

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * 件洗选项卡4
 * 
 * @author Administrator
 *
 */
public class JianXiFour extends Fragment implements OnItemClickListener {
	private final String TAG = JianXiFour.class.getName();
	private jianxipopuwin tanchuang;
	private View view;
	private GridView grid1;
	List<Results> result;
    private  Handler handler;
//	private RequestQueue queue;
    boolean  isonclk=true;
	// private List<JianXiJavaBean> javaBeanList;
	public void setHandler(Handler handler) {
		this.handler=handler;
		
	}
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.jianxi_four, null);
		grid1 = (GridView) view.findViewById(R.id.grid1);
//		queue = Volley.newRequestQueue(getActivity());
		grid1.setOnItemClickListener(this);
		boolean  isonclk=true;
//		grid1.setClickable(true);
		String name = "";
		try {
			name = URLEncoder.encode("皮衣", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringRequest stringRequest = new StringRequest(Method.GET,
				Baseparam.PRODUCTS_four+name, new Listener<String>() {

					@Override
					public void onResponse(String response) {

						Log.d("我的衣服", "ss"+response);
						GetListByjson(response);

						
						grid1.setAdapter(new JianXiAdapter(result,
								getActivity()));


					}

				}, new Response.ErrorListener() {

					public void onErrorResponse(VolleyError error) {
						Toast.makeText(getActivity(), "网络不稳定，请求失败", 0).show();
						

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
		return view;
	}

	// private List<JianXiJavaBean> GetListByjson(String json){
	// List<JianXiJavaBean>list=new ArrayList<JianXiJavaBean>();
	// JSONObject jsonobject;
	// try {
	// jsonobject=new JSONObject(json);
	// String dataList=jsonobject.getString("data");
	// Gson gson=new Gson();
	// list=gson.fromJson(dataList,new
	// TypeToken<List<JianXiJavaBean>>(){}.getType());
	//
	// } catch (JSONException e) {
	// e.printStackTrace();
	// System.out.println("Josn��������");
	// return list;
	// }
	// return list;
	//
	// }
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
		}
	}

	Results e;

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		grid1.setClickable(false);
		if(!isonclk)
			return;
		isonclk=false;
		/**
		 * 得到所点击子view的参数
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

	private void setName(Object object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {

		super.onDestroy();
//		queue.cancelAll(getActivity());
		JianXiMain.camcelRequest(TAG);
		
	}


	

}
