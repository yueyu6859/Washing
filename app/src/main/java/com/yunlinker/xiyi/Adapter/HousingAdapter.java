package com.yunlinker.xiyi.Adapter;

import java.util.List;

import com.yunlinker.xiyi.Adapter.DaiFuKuanAdapter.ViewHolder;
import com.yunlinker.xiyi.bean.HousingResults;
import com.yunlinker.xiyixi.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HousingAdapter extends BaseAdapter{
      private  Context  context;
      List<HousingResults>housing;
     

     public  HousingAdapter(Context  context,List<HousingResults>housing){
    	 this.housing=housing;
    	 this.context=context;
    	 
     }
	public int getCount() {
		return housing.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return housing.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	static class ViewHolder {
		 private TextView  nams;
	      private  TextView  detail;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView==null){
			convertView=View.inflate(context, R.layout.index_housing_item, null);
			holder=new  ViewHolder();
		
		holder.nams =(TextView)convertView.findViewById(R.id.nams_housing);
		holder.detail=(TextView)convertView.findViewById(R.id.detail);
		
		 convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag(); 
		}
		HousingResults  info=housing.get(position);
		holder.nams.setText(info.getName());
		holder.detail.setText(info.getDetail());
		return convertView;
	}
}
