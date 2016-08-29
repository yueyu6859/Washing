package com.yunlinker.xiyi.Adapter;

import java.util.ArrayList;
import java.util.List;

import com.yunlinker.xiyi.bean.PasswordQuestionsBean;
import com.yunlinker.xiyixi.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends  BaseAdapter{
    private  TextView  itemspi;
	Context   context;
	List<PasswordQuestionsBean> list;
	public SpinnerAdapter(Context   context,List<PasswordQuestionsBean> list) {
		this.context=context;
	     this.list=list;
	}

	@Override
	public int getCount() {
		Log.d("list的长度", "s"+list);
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PasswordQuestionsBean  item=list.get(position);
		convertView=View.inflate(context, R.layout.spinner_item, null);
		itemspi=(TextView) convertView.findViewById(R.id.itemspi);
		itemspi.setText(item.getTitle());
		return convertView;
	}

}
