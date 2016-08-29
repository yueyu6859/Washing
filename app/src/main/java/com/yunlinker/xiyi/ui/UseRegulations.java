package com.yunlinker.xiyi.ui;


import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * 使用规则
 * 
 * @author Administrator
 *
 */
public class UseRegulations extends BaseActivity {
	private ImageButton return15;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.use_regulations);
		return15 = (ImageButton) findViewById(R.id.return15);
       
		// 方便后续退出整个应用程序
//					MyApplication.getInstance().addActivity(UseRegulations.this);
					
		return15.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UseRegulations.this.finish();

			}
		});
	}

}
