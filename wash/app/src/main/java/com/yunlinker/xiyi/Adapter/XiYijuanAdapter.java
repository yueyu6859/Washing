package com.yunlinker.xiyi.Adapter;

import java.util.List;

import com.google.gson.Gson;
import com.yunlinker.xiyi.Adapter.DaiFuKuanAdapter.ViewHolder;
import com.yunlinker.xiyi.bean.Discounts;
import com.yunlinker.xiyi.bean.discountsbean;
import com.yunlinker.xiyi.ui.Home;
import com.yunlinker.xiyi.ui.HomeActivity;
import com.yunlinker.xiyi.ui.MyAccount;
import com.yunlinker.xiyixi.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class XiYijuanAdapter extends BaseAdapter {
	
	String id;
	private Context context;
	List<Discounts> list;
   private  TextView  xiyijuan;
	public XiYijuanAdapter(Context context, List<Discounts> list,TextView  xiyijuan) {
		this.list = list;
		this.context = context;
		this.xiyijuan=xiyijuan;
	}

	@Override
	public int getCount() {
    if(list.size()==0){
    	xiyijuan.setVisibility(View.VISIBLE);
    	
    }else{xiyijuan.setVisibility(View.GONE);}
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	static class ViewHolder {
		private TextView day,mmoenys;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView==null){
			convertView = View.inflate(context, R.layout.xiyijuan_item, null);
			holder=new  ViewHolder();
		
		holder.day = (TextView) convertView.findViewById(R.id.day);
		holder.mmoenys=(TextView) convertView.findViewById(R.id.mmoenys);
		
		RelativeLayout xiyijuans = (RelativeLayout) convertView
				.findViewById(R.id.xiyijuans);
      
		convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag(); 
		}
		Discounts info = list.get(position);
		id = info.getId();
		// 获得时间
		String time = info.getDead_time();
		// 将String类型转换成Long
		Long timesv = Long.parseLong(time);
		Long timess=timesv*1000;
		Long  times=timess-System.currentTimeMillis();
		String days = formatTime(times).toString();
		holder.day.setText(days);
		holder.mmoenys.setText(info.getPrice());
		return convertView;
	}

	// 将毫秒转换成天
	private String formatTime(Long times) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = times / dd;
		long hour = (times - day * dd) / hh;
		long minute = (times - day * dd - hour * hh) / mi;
		long second = (times - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = times - day * dd - hour * hh - minute * mi - second
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
