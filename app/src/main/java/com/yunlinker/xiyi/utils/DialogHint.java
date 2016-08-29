package com.yunlinker.xiyi.utils;

import com.yunlinker.xiyixi.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


//提示信息弹窗
public class DialogHint extends  Dialog{

	public DialogHint(Context context,String txt,int theme) {
		super(context,theme);
		View  view=LayoutInflater.from(context).inflate(R.layout.dialog_hint, null);
		TextView dialog_txt=(TextView)view.findViewById(R.id.dialog_txt);
		 dialog_txt.setText(txt);;
		
		setContentView(view);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.gravity = Gravity.CENTER;// 居中
		params.alpha = 0.7f;// 透明度
		window.setAttributes(params);
		
	}

}
