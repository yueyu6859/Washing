package com.yunlinker.xiyi.Adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.yunlinker.xiyixi.R;
import com.yunlinker.xiyi.Adapter.DaiFuKuanAdapter.ViewHolder;
import com.yunlinker.xiyi.bean.GetOrdersbean.Data.Results;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DaiQuJianAdapter extends BaseAdapter{ 
	private  List<Results> orderOne;
	private  Context context;
	
	TextView  text1;
	public   DaiQuJianAdapter(List<Results> orderOne,Context context,TextView  text1){
		this.orderOne=orderOne;
		this.context=context;
		this.text1=text1;
	}
	@Override
	public int getCount() {
		if(orderOne.size()==0){
			text1.setVisibility(View.VISIBLE);
		}else{
			text1.setVisibility(View.GONE);}
		return orderOne.size();
	}

	@Override
	public Object getItem(int position) {
		
		return orderOne.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}
	static class ViewHolder {
		private  TextView tv_wait,oder_num,take_time,found_data,name;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView==null){
		convertView=View.inflate(context, R.layout.my_oder_item, null);
		holder=new  ViewHolder();
		

		holder.tv_wait=(TextView)convertView.findViewById(R.id.tv_wait);
		holder.found_data=(TextView) convertView.findViewById(R.id.found_data);
		holder.oder_num=(TextView) convertView.findViewById(R.id.oder_num);
		holder.take_time=(TextView)convertView.findViewById(R.id.take_time);
		holder.name=(TextView) convertView.findViewById(R.id.name);
		convertView.setTag(holder);
		}
		else{holder = (ViewHolder) convertView.getTag();
		}
		Results userInfo =orderOne.get(position);
		holder.oder_num.setText(userInfo.order_num);
		holder.name.setText("取件预约时间：");
		
		if(userInfo.take_time==null){
			
			int  s=context.getResources().getColor(R.color.hui);
			holder.take_time.setTextColor(s);
			holder.take_time.setText("未预约");
		}else{
			//获得取件时间
			  String  taketime=userInfo.take_time;
			  int  taketimes=Integer.parseInt(taketime);
			  
			  if(userInfo.take_time_end!=null){
					int  end_time=Integer.parseInt(userInfo.take_time_end);
					holder.take_time.setText(takeTime(taketimes)+"—"+EndTime(end_time));
				} else{ holder.take_time.setText(takeTime(taketimes));} 
		}
		holder.tv_wait.setText("待取件");
		// 获得订单时间
	   String time = userInfo.created;
	  int  times=Integer.parseInt(time);
	  
	  
	  

	  holder.found_data.setText(paserTime(times));
	  return convertView;
	}
	private String  takeTime(int taketimes) {
		System.setProperty("user.timezone", "Asia/Shanghai");
		TimeZone  t=TimeZone.getTimeZone("Asia/Shanghai");  
		 TimeZone.setDefault(t); 
		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
		  String mtime= format.format(new Date(taketimes * 1000L));
		  
		return  mtime;
	}
	//
	private String paserTime(int times) {
		System.setProperty("user.timezone", "Asia/Shanghai");  
	      TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");  
	      TimeZone.setDefault(tz);  
	      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");  
	      String mtime= format.format(new Date(times * 1000L));    
	      return mtime;  
		
	}
	private String EndTime(int times) {
		System.setProperty("user.timezone", "Asia/Shanghai");  
	      TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");  
	      TimeZone.setDefault(tz);  
	      SimpleDateFormat format = new SimpleDateFormat("HH:mm");  
	      String mtime= format.format(new Date(times * 1000L));    
	      return mtime;  
		
	}
}
