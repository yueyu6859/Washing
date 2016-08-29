package com.yunlinker.xiyixi;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.yunlinker.xiyi.vov.CrashHandler;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

//退出整个应用程序
public class MyApplication extends Application {
	 private static final String TAG = "MyApplication";

	//便与后面退出程序
	public List<Activity> mList = new LinkedList<Activity>();
//	public List<FragmentActivity> List = new LinkedList<FragmentActivity>();
	public static MyApplication instance;
	public  ActivityManager  activitymanager=null;
	public MyApplication() {
	}
    
	

	
	public synchronized static MyApplication getInstance() {
		if (null == instance) {
			instance = new MyApplication();
		}
		return instance;
	}

	
	
	
	
	//activty退出
	public void addActivity(Activity activity) {
		mList.add(activity);
	}

	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null){
					Log.e(TAG, "activity ::::"+activity.getClass().getName());
					activity.finish();
				    activity=null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
	}

	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance=this;
		CrashHandler  crashHandler=CrashHandler.getInstance();
		//注册CrashHandler
		crashHandler.init(getApplicationContext());
		//发送以前没发送的报告
		crashHandler.sendPreviousReportsToServer();
	

			
			
		
	}
	
	
	
	
	// 请求队列
	public RequestQueue queue;

	public boolean islog=true;
	
	public RequestQueue getQueue(Context context){
		if(queue == null){
			queue = Volley.newRequestQueue(context);
		}
		return queue;
	}
	
}