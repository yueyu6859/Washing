package com.yunlinker.xiyi.utils;

import com.yunlinker.xiyixi.MyApplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

//activty基类
public class BaseActivity  extends Activity{
	
    @Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		/**
		 * 添加activity实例
		 */
		MyApplication.getInstance().addActivity(this);
	} 
}
