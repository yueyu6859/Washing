package com.yunlinker.xiyi.Adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.yunlinker.xiyixi.R;
import com.yunlinker.xiyi.bean.GetOrdersbean.Data.Results;
import com.yunlinker.xiyi.ui.Pay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class DaiFuKuanAdapter extends  BaseAdapter{
	 private  Context  context;
	    private  List<Results> order;

	     
	    TextView text;
	    //订单号
	    String  id;
   
	public  DaiFuKuanAdapter(List<Results> order, Context  context, TextView text){
		this.context=context;
		this.order=order;
		this.text=text;
	}

	@Override
	public int getCount() {
		if(order.size()==0){
			text.setVisibility(View.VISIBLE);
		}else{
			text.setVisibility(View.GONE);
		}
		return order.size();
	
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return order.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	static class ViewHolder {
		private  TextView tv_wait,oder_num,take_time,found_data,name;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		
		if (convertView == null) {
			
			convertView=View.inflate(context, R.layout.my_order_daifukuan, null);
			holder=new  ViewHolder();
			holder.tv_wait=(TextView) convertView.findViewById(R.id.tv_wait);
			holder.found_data=(TextView) convertView.findViewById(R.id.found_data);
			holder.oder_num=(TextView) convertView.findViewById(R.id.oder_num);
			holder.take_time=(TextView)convertView.findViewById(R.id.take_time);
			holder.name=(TextView) convertView.findViewById(R.id.name);
		    convertView.setTag(holder);
			}else{ holder = (ViewHolder) convertView.getTag(); 
			}
		Results userInfo =order.get(position);
		holder.name.setText("订单金额：");
	     id=userInfo.id;
	     holder.oder_num.setText(userInfo.order_num);
	     holder.take_time.setText(userInfo.take_time);
	     holder.tv_wait.setText("未付款");
	     if(Integer.parseInt(userInfo.price)>0){
	     holder.take_time.setText("¥"+userInfo.price);}else if(Integer.parseInt(userInfo.price)<0){
	    	 holder.take_time.setText("¥"+"0");
	     }
		int  money=context.getResources().getColor(R.color.money);
		holder.take_time.setTextColor(money);
		// 获得时间
	   String time = userInfo.created;
	  int  times=Integer.parseInt(time);

	  holder.found_data.setText(paserTime(times));
		return convertView;
		}
	
	
		private String paserTime(int times) {
			System.setProperty("user.timezone", "Asia/Shanghai");  
		      TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");  
		      TimeZone.setDefault(tz);  
		      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");  
		      String mtime= format.format(new Date(times * 1000L));  
		      System.out.print("日期格式---->" + times);  
		      return mtime;  
		}
		
		
}
