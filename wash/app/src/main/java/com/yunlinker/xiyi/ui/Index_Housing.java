package com.yunlinker.xiyi.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;
import com.yunlinker.xiyixi.R.string;
import com.yunlinker.xiyi.Adapter.HousingAdapter;
import com.yunlinker.xiyi.bean.HousingResults;
import com.yunlinker.xiyi.utils.BaseActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * 索引小区地址
 * 
 * @author Administrator
 *
 */
public class Index_Housing extends BaseActivity {

	private ListView  index_list;
	private  HousingAdapter  adapter;
	private ImageButton return16;
	//搜索框
     private    EditText  index_text;
	   //查询后的数据集合
     List<HousingResults> newlist = new ArrayList<HousingResults>();
   
     

   @Override
public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();

    
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_housing);
//		act = (AutoCompleteTextView) findViewById(R.id.act);
		return16 = (ImageButton) findViewById(R.id.return16);
		index_list=(ListView) findViewById(R.id.index_list);
		index_text=(EditText) findViewById(R.id.index_text);
		index_text.addTextChangedListener(index_TextWatcher);
		
		
//		s=(List<HousingResults>) getIntent().getSerializableExtra("listhous");
      
		adapter=new  HousingAdapter(Index_Housing.this, HomeActivity.results);
		index_list.setAdapter(adapter);
       
       //方便后续退出程序
//		MyApplication.getInstance().addActivity(Index_Housing.this);


		return16.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Index_Housing.this.finish();
   
			}
		});
		
		index_list.setOnItemClickListener(new  OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				HousingResults  hous=HomeActivity.results.get(position);
				String   ids=hous.getId();
				String name=hous.getName();
				
				SharedPreferences 	sharedPreferences = Index_Housing.this.getSharedPreferences("locations",
						Context.MODE_PRIVATE);
				Editor editor = sharedPreferences.edit();// 达到编辑器
				editor.putString("housingId",ids);
				editor.putString("housing", name);
//				editor.putString("phone", phone.getText().toString());
//				editor.putString("home", area_id);
//				editor.putString("id",address.getId());
				
				editor.commit();// 提交
//				Intent  intent=new  Intent(Index_Housing.this, Location.class); 
//				intent.putExtra("housingId", ids);
//				intent.putExtra("housing",name);
//				startActivity(intent);		
//				Log.d("我的", ids);
			    Index_Housing.this.finish();
			}
		});
	}
	
	
	//当editetext变化时调用的方法，来判断所输入是否包含在所属数据中  
	 private List<HousingResults> getNewData(String input_info){
		//遍历list  
		 for(int i=0;i<HomeActivity.results.size();i++){
			 HousingResults  results=HomeActivity.results.get(i);
			 //如果遍历到的名字包含所输入字符串  
			 if(results.getName().contains( input_info)){
				//将遍历到的元素重新组成一个list  
				 HousingResults  results2=new HousingResults();
				 results2.setName(results.getName());
				 results2.setDetail(results.getDetail());
				 results2.setId(results.getId());
				 newlist.add(results2);
			 }
		 }
		 
		 
		return newlist;}
	 
	 
	 
	private TextWatcher   index_TextWatcher=new TextWatcher(){

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			  //改变之前会执行的动作a
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			//改变的时候
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			newlist.clear();  
			String input_info = index_text.getText().toString();
			newlist = getNewData(input_info);  
			adapter = new  HousingAdapter(Index_Housing.this,newlist);
			index_list.setAdapter(adapter);
			
			
		}
		
	};
	
}
