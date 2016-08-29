package com.yunlinker.xiyi.ui;



import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

public class XiyiLoging extends BaseActivity {
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiyiloing);
		// 方便后续退出整个应用程序
//		MyApplication.getInstance().addActivity(XiyiLoging.this);


		new Thread(new MyThread()).start();
	}

	public class MyThread implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(1500);
				Intent intent = new Intent(XiyiLoging.this, HomeActivity.class);
				startActivity(intent);
				finish();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}