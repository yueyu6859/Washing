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

//常见问题
public class Often_Problem extends  BaseActivity{
	private WebView  Problem;
	private ImageButton  return22;
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.ofter_problem);
    	Problem=(WebView) findViewById(R.id.ofterpr);
    	return22=(ImageButton) findViewById(R.id.return27);
    	Problem.loadUrl(Baseparam.CHANGJIANWENTI);
    	// 方便后续退出整个应用程序
//    			MyApplication.getInstance().addActivity(Often_Problem.this); 
    			
    			return22.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						Often_Problem.this.finish();
					}
				});		
    }
}
