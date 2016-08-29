package com.yunlinker.xiyi.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunlinker.xiyi.Adapter.XiYijuanAdapter;
import com.yunlinker.xiyi.bean.Discounts;
import com.yunlinker.xiyi.bean.discountsbean;
import com.yunlinker.xiyi.ui.JianXiMain.myPagerAdapter;
import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class XiYiJuan extends BaseActivity implements Serializable {
	private ListView ima;
	private XiYijuanAdapter adapters;
	private TextView text_xiyijuan;
	private ImageButton ibt;
	private ImageButton return2;
	// List<Discounts> s;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiyijuan);
		ima = (ListView) findViewById(R.id.ima);
		return2 = (ImageButton) findViewById(R.id.return2);
		text_xiyijuan = (TextView) findViewById(R.id.text_xiyijuan);
		// s = (List<Discounts>) getIntent().getSerializableExtra("listobj");

		// 方便后续退出整个应用程序
//		MyApplication.getInstance().addActivity(XiYiJuan.this);

		if (HomeActivity.result.size() != 0) {
			adapters = new XiYijuanAdapter(XiYiJuan.this, HomeActivity.result,
					text_xiyijuan);
			// ima.setAdapter(new XiYijuanAdapter(, XiYiJuan.this));
			ima.setAdapter(adapters);
		} else {
			text_xiyijuan.setVisibility(View.VISIBLE);
			ima.setVisibility(View.GONE);
		}
		// ///////////////////////////////
//		ima.setClickable(false);
		Intent i = getIntent();
		if(i!=null){
		String btn = i.getStringExtra("xiyijuan_btn");
		if ("1".equals(btn)) {
			ima.setOnItemClickListener(new OnItemClickListener() {
				
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					Discounts obj = HomeActivity.result.get(arg2);
					String ids = obj.getId();

					String times = obj.getDead_time();
					// 将String类型转换成Long
					Long timev = Long.parseLong(times);
					Long timess=timev*1000;
					Long  time=timess-System.currentTimeMillis();
					String mtime = formatTime(time).toString();
					
					String price = obj.getPrice();
//					 HomeActivity.result.remove(obj);
					adapters.notifyDataSetChanged();
					Toast.makeText(XiYiJuan.this, "已加入洗衣篮", 3000).show();
					// 保存信息
					SharedPreferences sharedPreferences = XiYiJuan.this
							.getSharedPreferences("xiyijuan",
									Context.MODE_APPEND);
					Editor editor = sharedPreferences.edit();// 达到编辑器
					editor.putString("id", ids);
					editor.putString("time", mtime);
					editor.putString("price", price);
					editor.commit();// 提交
//					Intent intent = new Intent(XiYiJuan.this,
//							HomeActivity.class);
					// XiYiJuan.this.startActivityForResult(intent, 8);
//					setResult(RESULT_OK, intent);
					XiYiJuan.this.finish();
				}

			});
		}
		}

		// 返回键
		return2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				XiYiJuan.this.finish();
			}
		});
	}

	/**
	 * 将秒转换为天
	 * 
	 * @param time
	 */
	protected String formatTime(Long time) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = time / dd;
		long hour = (time - day * dd) / hh;
		long minute = (time - day * dd - hour * hh) / mi;
		long second = (time - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = time - day * dd - hour * hh - minute * mi - second
				* ss;

		String strDay = day < 10 ? "0" + day : "" + day; // 天
		String strHour = hour < 10 ? "0" + hour : "" + hour;// 小时
		String strMinute = minute < 10 ? "0" + minute : "" + minute;// 分钟
		String strSecond = second < 10 ? "0" + second : "" + second;// 秒
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : ""
				+ milliSecond;// 毫秒
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : ""
				+ strMilliSecond;
		return strDay;

	}

}
