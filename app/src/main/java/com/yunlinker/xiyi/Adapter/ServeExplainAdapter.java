package com.yunlinker.xiyi.Adapter;

import java.util.List;

import com.yunlinker.xiyi.Adapter.DaiFuKuanAdapter.ViewHolder;
import com.yunlinker.xiyi.bean.HousingResults;
import com.yunlinker.xiyi.ui.HomeActivity;
import com.yunlinker.xiyixi.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ServeExplainAdapter extends BaseAdapter{
	
	 private  Context  context;
     List<HousingResults>housing;
	
	public  ServeExplainAdapter(Context context,List<HousingResults>list){
		this.context=context;
		this.housing=list;
		
	}

	@Override
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
		private  TextView  serve_housing;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		HousingResults  info=housing.get(position);
		if(convertView==null){
			convertView=convertView.inflate(context, R.layout.serve_explain_item, null);
			holder=new  ViewHolder();
			holder.serve_housing=(TextView) convertView.findViewById(R.id.serve_housing);
			
			 convertView.setTag(holder);
		}else{
			 holder = (ViewHolder) convertView.getTag(); 
		}
		holder.serve_housing.setText(info.getName());
		return convertView;
	}
        
}
