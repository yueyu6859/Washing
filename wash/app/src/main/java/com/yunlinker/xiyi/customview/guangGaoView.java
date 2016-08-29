package com.yunlinker.xiyi.customview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yunlinker.xiyi.ui.Home;
import com.yunlinker.xiyi.ui.My;
import com.yunlinker.xiyi.vov.Baseparam;
import com.yunlinker.xiyixi.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView.ScaleType;

/**
 * 循环播放广告条
 * 
 * @author Administrator
 *
 */
public class guangGaoView extends FrameLayout {
	public  static String IMAGE_CACHE_PATH = "imageloader/Cache"; // 图片缓存路径
	private RequestQueue queue;
	String  mage1, mage2;
  
	// 使用universal-image-loader插件读取网络图片
	private ImageLoader imageLoader = ImageLoader.getInstance();

	// 轮播图图片数量
	private final static int IMAGE_COUNT = Home.Arraybanner.length();
	
	// 自动轮播的时间间隔
	private final static int TIME_INTERVAL = 5;
	// 自动轮播启用开关
	private final static boolean isAutoPlay = true;

	// 自定义轮播图的资源
	private String[] imageUrls;
	// 放轮播图片的ImageView 的list
	private List<ImageView> imageViewsList;
	// 放圆点的View的list
	private List<View> dotViewsList;

	private ViewPager viewPager;
	// 当前轮播页
	private int currentItem = 0;
	// 定时任务
	private ScheduledExecutorService scheduledExecutorService;

	private Context context;
	LinearLayout dotLayout;

	// Handler
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			viewPager.setCurrentItem(currentItem);
		}

	};

	public guangGaoView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public guangGaoView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public guangGaoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;

		initImageLoader(context);

		initData();
		Banner();
	}

	/**
	 * 开始轮播图切换
	 */
	private void startPlay() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4,
				TimeUnit.SECONDS);
	}

	/**
	 * 停止轮播图切换
	 */
	private void stopPlay() {
		scheduledExecutorService.shutdown();
	}

	/**
	 * 初始化相关Data
	 */
	private void initData() {
//		SharedPreferences	Banner=context.getSharedPreferences(
//				"banner", Context.MODE_PRIVATE);
//		 mage1=Banner.getString("image1", "");
//		mage2=Banner.getString("image2", "");
		
		imageViewsList = new ArrayList<ImageView>();
		dotViewsList = new ArrayList<View>();
		// 一步任务获取图片
	}
	
	Handler mHandler=new Handler(){
	public void handleMessage(Message msg) {
		new GetListTask().execute("");
		};	
	};
	
	   //获取的banner
		private void Banner() {
			queue = Volley.newRequestQueue(context);
			StringRequest stringRequest = new StringRequest(Baseparam.BANNERS, new Listener<String>(){

				@Override
				public void onResponse(String response) {
					
					JSONObject jsonobject;
					
						try {
							jsonobject = new JSONObject(response);
							int status_code = jsonobject.getInt("status_code");
							if (status_code == 1) {
								JSONArray array = jsonobject.getJSONArray("data");
								Log.d("banner的data是",""+ array);
								imageUrls=new String[array.length()];
								for(int i=0;i<array.length();i++){
									JSONObject   js=(JSONObject) array.get(i);
									String	image=js.getString("image");
									imageUrls[i]=image;
								}
								mHandler.sendEmptyMessage(0);
//									JSONObject   js=(JSONObject) array.get(0);
//								String	image1=js.getString("image");
//								JSONObject   js1=(JSONObject) array.get(1);
//								String	image2=js.getString("image");
//								
//								Editor  E=Banner.edit();
//								 E.putString("IMAGE1", image1);
//								 E.putString("IMAGE2", image2);
//								 E.commit();
								
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}}, new Response.ErrorListener(){

					@Override
					public void onErrorResponse(VolleyError error) {
						
						
					}});
			queue.add(stringRequest);
		}

	/**
	 * 初始化Views等UI
	 */
	private void initUI(Context context) {
		if (imageUrls == null || imageUrls.length == 0)
			return;

		LayoutInflater.from(context)
				.inflate(R.layout.guanggao_view, this, true);

		dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
		dotLayout.removeAllViews();
		for (int i = 0; i < imageUrls.length; i++) {
			ImageView view = new ImageView(context);
			view.setTag(imageUrls[i]);
			// 给一个默认图
			if (i == 0)
				view.setBackgroundResource(R.drawable.appmain_subject_1);
			view.setScaleType(ScaleType.FIT_XY);
			imageViewsList.add(view);

			ImageView dotView = new ImageView(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 4;
			params.rightMargin = 4;
			dotLayout.addView(dotView, params);
			dotViewsList.add(dotView);
		}
		// 热点个数与图片个数相等
	
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setFocusable(true);

		viewPager.setAdapter(new MyPagerAdapter());
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
	}

	/**
	 * 填充ViewPager的页面适配器
	 * 
	 */
	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			// ((ViewPag.er)container).removeView((View)object);
			((ViewPager) container).removeView(imageViewsList.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			ImageView imageView = imageViewsList.get(position);

			imageLoader.displayImage(imageView.getTag() + "", imageView);

			((ViewPager) container).addView(imageViewsList.get(position));
			return imageViewsList.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageViewsList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * ViewPager的监听器 当ViewPager中页面的状态发生改变时调用
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

		boolean isAutoPlay = false;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case 1:// 手势滑动，空闲中
				isAutoPlay = false;
				break;
			case 2:// 界面切换中
				isAutoPlay = true;
				break;
			case 0:// 滑动结束，即切换完毕或者加载完毕
					// 当前为最后一张，此时从右向左滑，则切换到第一张
				if (viewPager.getCurrentItem() == viewPager.getAdapter()
						.getCount() - 1 && !isAutoPlay) {
					viewPager.setCurrentItem(0);
				}
				// 当前为第一张，此时从左向右滑，则切换到最后一张
				else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
					viewPager
							.setCurrentItem(viewPager.getAdapter().getCount() - 1);
				}
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int pos) {
			// TODO Auto-generated method stub

			currentItem = pos;
			for (int i = 0; i < dotViewsList.size(); i++) {
				if (i == pos) {
					((View) dotViewsList.get(pos))
							.setBackgroundResource(R.drawable.dot_focus);
				} else {
					((View) dotViewsList.get(i))
							.setBackgroundResource(R.drawable.dot_blur);
				}
			}
		}

	}

	/**
	 * 执行轮播图切换任务
	 *
	 */
	private class SlideShowTask implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % imageViewsList.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}

	/**
	 * 销毁ImageView资源，回收内存
	 * 
	 */
	private void destoryBitmaps() {

		for (int i = 0; i < IMAGE_COUNT; i++) {
			ImageView imageView = imageViewsList.get(i);
			Drawable drawable = imageView.getDrawable();
			if (drawable != null) {
				// 解除drawable对view的引用
				drawable.setCallback(null);
			}
		}
	}
   
	/**
	 * 异步任务,获取数据
	 * 
	 */
//	JSONObject jsonObject1 ;
	class GetListTask extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			
//			try {
//				imageUrls = new String[Home.Arraybanner.length()] ;
				// 服务端接口获取一组轮播图片
//				for (int i = 0; i < Home.Arraybanner.length(); i++){
//					jsonObject1 = (JSONObject) Home.Arraybanner.get(i);
//					imageUrls[i]=jsonObject1.getString("image");
//				}
				
				return true;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return false;
//			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				initUI(context);
				if (isAutoPlay) {
					startPlay();
				}
			}
		}
	}

	/**
	 * ImageLoader 图片组件初始化
	 * 
	 * @param context
	 */
	public static void initImageLoader(Context context) {
	        
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove
									// for
									// release
									// app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}
