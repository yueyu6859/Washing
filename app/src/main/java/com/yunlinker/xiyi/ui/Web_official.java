package com.yunlinker.xiyi.ui;



import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyixi.R;

//官网
public class Web_official extends BaseActivity{
	private WebView  web;
	private  ImageButton  return32;
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.web_official);
    	web=(WebView) findViewById(R.id.web);
    	// 方便后续退出整个应用程序
//    			MyApplication.getInstance().addActivity(Web_official.this);
    	return32=(ImageButton) findViewById(R.id.return32);
    	return32.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Web_official.this.finish();
				
			}
		});
    	web.getSettings().setUseWideViewPort(true);//关键点  
    	  
    	web.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);  
    	web.getSettings().setDisplayZoomControls(false);  
    	web.getSettings().setAllowFileAccess(true); // 允许访问文件  
    	web.getSettings().setBuiltInZoomControls(true); // 设置显示缩放按钮  
    	web.getSettings().setLoadWithOverviewMode(true);  
    	
    	
    	web.getSettings().setJavaScriptEnabled(true);
    	web.getSettings().setSupportZoom(true);
    	web.setWebChromeClient(new WebChromeClient()
        {
    	
        });
    	web.setWebViewClient(new WebViewClient()
        {
          @Override
          public boolean shouldOverrideUrlLoading(WebView view, String url)
          {
     
            view.loadUrl(url); // 在当前的webview中跳转到新的url
     
            return true;
          }
        });
    	web.loadUrl("http://www.huankxy.com/");
    }
}
