package com.yunlinker.xiyi.ui;


import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class AppUpdate extends BaseActivity{
	
	private  WebView   update;
	private  ImageView  returns;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.app_update);
    	 update=(WebView) findViewById(R.id.update);
//    	// 方便后续退出整个应用程序
//			MyApplication.getInstance().addActivity(AppUpdate.this);
    	 returns=(ImageView) findViewById(R.id.returnsd);
    	 returns.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AppUpdate.this.finish();
			}
		});
    	 update.setWebViewClient(new WebViewClient(){
    		 @Override
             public boolean shouldOverrideUrlLoading(WebView view, String url)
             {
        
               view.loadUrl(url); // 在当前的webview中跳转到新的url
        
               return true;
             }
    	 });
    	 update.loadUrl("https://www.yunlinker.com/xiyi/down_user.html");
    }
      @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    }
}
