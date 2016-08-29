package com.yunlinker.xiyi.customview;

import java.io.File;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yunlinker.xiyi.customview.guangGaoView.GetListTask;
import com.yunlinker.xiyi.vov.Baseparam;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class BannerView extends FrameLayout{
	private RequestQueue  queue;
	private  Context  mContext;
	public static String IMAGE_CACHE_PATH = "imageloader/Cache"; // 图片缓存路径
	private  ViewPager  adViewPager;
	//滑动的图片集合
	private List<ImageView> imageviews;
	//放圆点的list；
	private List<View>dots;
	private  List<View>dotlist;
	//当前轮播页
	private int currentItem=0;
	// 定时任务
	private ScheduledExecutorService scheduledExecutorService;
	// 异步加载图片
	private ImageLoader mImageLoader;
	private DisplayImageOptions options;	
	LinearLayout dotLayout;
	// 自定义轮播图的资源
	private String[] imageUrls;
	
	// Handler
		private Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				adViewPager.setCurrentItem(currentItem);
			}

		};

	public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public BannerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext=context;
		// 使用ImageLoader之前初始化
		initImageLoader(mContext);
		Banner();
		/**
		 * 开始播放
		 */
		startPlay();
	}

	

	public BannerView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	private void  initImageLoader(Context  context){
		File  cacheDir=com.nostra13.universalimageloader.utils.StorageUtils.getOwnCacheDirectory
				(context.getApplicationContext(), IMAGE_CACHE_PATH);
		
		
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		.cacheInMemory(true).cacheOnDisc(true).build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getContext()).defaultDisplayImageOptions(defaultOptions)
				.memoryCache(new LruMemoryCache(12 * 1024 * 1024))
				.memoryCacheSize(12 * 1024 * 1024)
				.discCacheSize(32 * 1024 * 1024).discCacheFileCount(100)
				.discCache(new UnlimitedDiscCache(cacheDir))
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();

		ImageLoader.getInstance().init(config);
	}
	
	//获取banner
   private void  Banner(){
	   queue = Volley.newRequestQueue(mContext);
	   StringRequest mstringRequest=new StringRequest(Method.GET, Baseparam.BANNERS, new Listener<String>() {

		@Override
		public void onResponse(String response) {
			JSONObject  jsonobject;
			try {
				jsonobject=new JSONObject(response);
				int status_code =jsonobject.getInt("status_code");
				if (status_code == 1){
					JSONArray array = jsonobject.getJSONArray("data");
					imageUrls=new String[array.length()];
					for(int i=0;i<array.length();i++){
						JSONObject   js=(JSONObject) array.get(i);
						String	image=js.getString("image");
						imageUrls[i]=image;
					}
//					mHandler.sendEmptyMessage(0);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}, new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub
			
		}
	});
	   queue.add(mstringRequest);
   }
//   Handler mHandler=new Handler(){
//		public void handleMessage(Message msg) {
//			
//			};	
//		};
   private void startPlay(){
	   //Executors.newSingleThreadScheduledExecutor()持续更新
	   scheduledExecutorService=Executors.newSingleThreadScheduledExecutor();
	// 当Activity显示出来后，每两秒切换一次图片显示
	   /**
	    * SlideShowTask执行轮播图片切换任务
	    */
	   scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 2, TimeUnit.SECONDS);
	   
   }
   /**
	 * 执行轮播图切换任务
	 *
	 */
	private class SlideShowTask implements Runnable {

	@Override
	public void run() {
		synchronized(adViewPager){
//			currentItem=(currentItem+1)%+
		}
		
	}
		
	}	
	
   	
		
}
