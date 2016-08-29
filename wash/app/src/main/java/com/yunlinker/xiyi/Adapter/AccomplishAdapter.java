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

public class AccomplishAdapter extends  BaseAdapter{
    private  Context  context;
    private  List<Results> orderThree;
    
    TextView text;
	public   AccomplishAdapter(List<Results> orderThree, Context  context, TextView text){
		this.context=context;
		this.orderThree=orderThree;
		this.text=text;
	}
	public int getCount() {
		if(orderThree.size()==0){
			text.setVisibility(View.VISIBLE);
		}else{
			text.setVisibility(View.GONE);
		}
		return orderThree.size();
	}

	public Object getItem(int position) {
		
		return orderThree.get(position);
	}


	public long getItemId(int position) {
		
		return position;
	}
	static class ViewHolder {
		private  TextView tv_wait,oder_num,take_time,found_data,name;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
	    convertView=View.inflate(context, R.layout.my_oder_item, null);
		holder=new  ViewHolder();
		
		holder.tv_wait=(TextView) convertView.findViewById(R.id.tv_wait);
		holder.found_data=(TextView) convertView.findViewById(R.id.found_data);
		holder.oder_num=(TextView) convertView.findViewById(R.id.oder_num);
		holder.take_time=(TextView) convertView.findViewById(R.id.take_time);
		holder.name=(TextView) convertView.findViewById(R.id.name);
		
		
		 convertView.setTag(holder);
		}else{ holder = (ViewHolder) convertView.getTag(); 
		}
		Results userInfo =orderThree.get(position);
       holder.oder_num.setText(userInfo.order_num);
		
		holder.name.setText("完成日期：");
		holder.tv_wait.setText("已完成");
		
	  int	textcolor=context.getResources().getColor(R.color.green);
		
	  holder.tv_wait.setTextColor(textcolor);
		// 获得时间
	   String time = userInfo.created;
	   //获得完成时间
	   String  mtime=userInfo.modified;
	   
	   int  mtimes=Integer.parseInt(mtime);
	   
	   holder.take_time.setTextColor(textcolor);
	   holder.take_time.setText(paserTime(mtimes));
	   
	  int  times=Integer.parseInt(time);
	  holder.found_data.setText(paserTime(times));
	  return convertView;
	}
	private String paserTime(int times) {
		System.setProperty("user.timezone", "Asia/Shanghai");  
	      TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");  
	      TimeZone.setDefault(tz);  
	      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	      String mtime= format.format(new Date(times * 1000L));  
	      System.out.print("日期格式---->" + times);  
	      return mtime;  
	}
}
