package com.yunlinker.xiyi.ui;



import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;


//关于我们
public class About_Us extends  BaseActivity{
	private  Button  official;
	private   ImageButton  return14;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.about_us);
    	official=(Button) findViewById(R.id.official);
    	return14=(ImageButton) findViewById(R.id.return14);
    	
    	// 方便后续退出整个应用程序
//    			MyApplication.getInstance().addActivity(About_Us.this);
    			
    			return14.setOnClickListener(new  OnClickListener() {
					
					@Override
					public void onClick(View v) {
						About_Us.this.finish();
						
					}
				});
    			official.setOnClickListener(new OnClickListener() {
		        			
					@Override
					public void onClick(View v) {
						Intent inetnt=new  Intent(About_Us.this, Web_official.class);
						startActivity(inetnt);
					}
				});		
    }
       @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    }
}
