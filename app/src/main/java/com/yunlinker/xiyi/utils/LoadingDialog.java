package com.yunlinker.xiyi.utils;

import com.yunlinker.xiyixi.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadingDialog extends  Dialog{

	public LoadingDialog(Context context,int theme) {
		super(context,theme);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
		ImageView img = (ImageView) view.findViewById(R.id.dialog_loading_img);
		TextView tv = (TextView) view.findViewById(R.id.dialog_txt);
		tv.setVisibility(View.GONE);
		Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading);
		img.startAnimation(animation);
		
		setContentView(view);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.gravity = Gravity.CENTER;// 居中
		params.alpha = 0.7f;// 透明度
		window.setAttributes(params);
		
	}
	public LoadingDialog(Context context, String txt, int theme) {
		super(context, theme);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
		
		
		ImageView img = (ImageView) view.findViewById(R.id.dialog_loading_img);
		TextView tv = (TextView) view.findViewById(R.id.dialog_txt);
		tv.setText(txt);
		Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading);
		img.startAnimation(animation);

		setContentView(view);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.gravity = Gravity.CENTER;// 居中
		params.alpha = 0.7f;// 透明度
		window.setAttributes(params);
	}
	public LoadingDialog(Context context) {
		super(context);
	}
}
