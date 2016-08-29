package com.yunlinker.xiyi.ui;


import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyi.vov.Baseparam;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;

//用户协议
public class User_Agreement extends  BaseActivity{
	private  WebView  agreement;
	private  ImageButton  return25;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.user_agreement);
    	agreement=(WebView) findViewById(R.id.agreement);
    	agreement.loadUrl(Baseparam.XIEYI);
    	return25=(ImageButton) findViewById(R.id.return25);
    	
    	// 方便后续退出整个应用程序
//		MyApplication.getInstance().addActivity(User_Agreement.this);
		
		
		return25.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				User_Agreement.this.finish();
				
			}
		});
    }
}
