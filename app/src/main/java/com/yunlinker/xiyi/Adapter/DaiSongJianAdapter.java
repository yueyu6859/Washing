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

public class DaiSongJianAdapter extends BaseAdapter{
    private  List<Results> orderTwo;
    private Context  context;
   
    TextView text1;
	public DaiSongJianAdapter(List<Results> orderTwo,
			Context context, TextView text) {
		this.context=context;
		this.orderTwo=orderTwo;
		this.text1=text;
	}

	public int getCount() {
		if(orderTwo.size()==0){
			text1.setVisibility(View.VISIBLE);
		}else{
			text1.setVisibility(View.GONE);
		}
		return orderTwo.size();
	}

	@Override
	public Object getItem(int position) {
		
		return orderTwo.get(position);
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
			convertView =View.inflate(context, R.layout.my_oder_item, null);
		holder=new  ViewHolder();
		
		holder.tv_wait=(TextView) convertView.findViewById(R.id.tv_wait);
	
		holder.found_data=(TextView)convertView.findViewById(R.id.found_data);
		holder.oder_num=(TextView) convertView.findViewById(R.id.oder_num);
		holder.take_time=(TextView) convertView.findViewById(R.id.take_time);
		holder.name=(TextView) convertView.findViewById(R.id.name);
		
		convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag(); 
		}
		Results userInfo =orderTwo.get(position);
		holder.oder_num.setText(userInfo.order_num);
		holder.name.setText("待送件预约时间:");
		
		int  rose=context.getResources().getColor(R.color.rose);
		int hui=context.getResources().getColor(R.color.hui);
		
		if(userInfo.sned_time==null){
			holder.take_time.setTextColor(hui);
			holder.take_time.setText("未预约");
		}else{
			//获得送件时间
	   String  sendtime=userInfo.sned_time;
	   int  sendtimes=Integer.parseInt(sendtime);
	   holder.take_time.setTextColor(rose);
	   
	   if(userInfo.sned_time_end!=null){
			int  end_time=Integer.parseInt(userInfo.sned_time_end);
			holder.take_time.setText( SendTime(sendtimes)+"—"+paserTime(end_time));
		}
		}
		holder.tv_wait.setText("待送件");
		// 获得时间
		   String time = userInfo.created;
		  int  times=Integer.parseInt(time);

		  holder.found_data.setText(paserTime(times));
		  return convertView;
	}
    //送件时间
	private String SendTime(int sendtimes) {
		System.setProperty("user.timezone", "Asia/Shanghai");
		TimeZone  t=TimeZone.getTimeZone("Asia/Shanghai");  
		 TimeZone.setDefault(t); 
		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
		  String mtime= format.format(new Date(sendtimes* 1000L));
		  System.out.print("日期格式---->" + sendtimes); 
		
		return mtime;
		
		
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
	private String EndTime(int times) {
		System.setProperty("user.timezone", "Asia/Shanghai");  
	      TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");  
	      TimeZone.setDefault(tz);  
	      SimpleDateFormat format = new SimpleDateFormat("HH:mm");  
	      String mtime= format.format(new Date(times * 1000L));  
	      System.out.print("日期格式---->" + times);  
	      return mtime;  

	}

}
