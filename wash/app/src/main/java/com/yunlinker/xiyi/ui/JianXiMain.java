package com.yunlinker.xiyi.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


















import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.yunlinker.xiyi.bean.BasketBean;
import com.yunlinker.xiyi.bean.Discounts;
import com.yunlinker.xiyi.bean.Results;
import com.yunlinker.xiyi.fragemnt.JianXiFour;
import com.yunlinker.xiyi.fragemnt.JianXiOne;
import com.yunlinker.xiyi.fragemnt.JianXiTrhee;
import com.yunlinker.xiyi.fragemnt.JianXiTwo;
import com.yunlinker.xiyi.utils.BaseFragmentActivity;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

//件洗
public class JianXiMain extends BaseFragmentActivity {
	private static RequestQueue queue;
   private   ImageButton  btn_fbasekt;
	// 返回上一级
	private ImageButton retun1;
	// 页卡内容
	private ViewPager viewpager;
	// 动画图片
	private ImageView imageview;
	// 选项名称
	private TextView jianxi1, jianxi2, jianxi3, jianxi4;
	// tab页面列表
	private List<Fragment> fragments;
	// 动画图片偏移量
	private int offset = 0;
	// 当前页卡编号
	private int currIndex = 0;
	Boolean have=false;
	// 动画图片宽度
	private int bmpW;
	private int selectedColor, unSelectedColor;
	

//	private Handler handler = new Handler() {
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			if (msg.what == 12) {
//				boolean onclik=(Boolean) msg.obj;
//				if(onclik=true){
//					btn_fbasekt.setBackgroundResource(R.drawable.folat_basket_nol);}
//				}
//				    
//				
//		}};

	/**
	 * 页卡总数
	 */
	private static final int pagesize = 4;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jianxi_main);
      
		
		// 方便后续退出整个应用程序
//				MyApplication.getInstance().addFragmentActivity(JianXiMain.this);
				// 初始化一个请求队列
				queue = Volley.newRequestQueue(getApplicationContext());
       
		initView();
		
		
		retun1 = (ImageButton) findViewById(R.id.retun1);
		
		
		btn_fbasekt=(ImageButton) findViewById(R.id.btn_fbasekt);
//		if(HomeActivity.list.size()!=0){
//			Log.d("不等于0了", "..............................");
//			btn_fbasekt.setBackgroundResource(R.drawable.folat_basket_nol);
//			
//		}else{btn_fbasekt.setBackgroundResource(R.drawable.folat_basket_sel);}
		
	

		btn_fbasekt.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
			  Intent intent=new  Intent(JianXiMain.this,HomeActivity.class);
			  intent.putExtra("renturns","1");
			  startActivity(intent);
			  JianXiMain.this.finish();	
			}
		});
		retun1.setOnClickListener(new View.OnClickListener() {

		
			public void onClick(View v) {
				JianXiMain.this.finish();

			}
		});
	}
	/**
	 * 开始请求
	 * 
	 * @param r
	 * @param tag
	 * @return
	 */
	public static <T> Request<T> startRequest(Request<T> r, Object tag) {
		// 给传来的请求设置TAG
		r.setTag(tag);

		Request<T> request = queue.add(r);

		queue.start();

		return request;
	}
	/**
	 * 取消请求
	 * 
	 * @param tag
	 */
	public static void camcelRequest(Object tag) {
		queue.cancelAll(tag);
	}
	@Override
	public void onResume() {
		super.onResume();
	
		if(HomeActivity.list.size()!=0){
			Log.d("不等于0了", "..............................");
			btn_fbasekt.setBackgroundResource(R.drawable.folat_basket_nol);
			
		}else{btn_fbasekt.setBackgroundResource(R.drawable.folat_basket_sel);}
		
	}
        
        
	private void initView() {
		selectedColor = getResources()
				.getColor(R.color.tab_title_pressed_color);
		unSelectedColor = getResources().getColor(
				R.color.tab_title_normal_color);
		InitImageView();
		InitTextView();
		InitViewPager();
	}

	/**
	 * 初始化viewpager页
	 */
	private void InitViewPager() {
		viewpager = (ViewPager) findViewById(R.id.jianxipager);
		fragments = new ArrayList<Fragment>();
		JianXiOne  jianxione=new JianXiOne();
		JianXiTwo  jianxitwo=new JianXiTwo();
		JianXiTrhee  jianxitrhee=new JianXiTrhee();
		JianXiFour  jianxifour=new JianXiFour();
		jianxione.setHandler(handler);
		jianxitwo.setHandler(handler);
		jianxitrhee.setHandler(handler);
		jianxifour.setHandler(handler);
	
		fragments.add(jianxione);
		fragments.add(jianxitwo);
		fragments.add(jianxitrhee);
		fragments.add(jianxifour);
		viewpager.setAdapter(new myPagerAdapter(getSupportFragmentManager(),
				fragments));
		viewpager.setCurrentItem(0);
		viewpager.setOnPageChangeListener(new MyOnPageChangeListener());
		viewpager.setOffscreenPageLimit(0);
		
		
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 12) {
				boolean onclik=(Boolean) msg.obj;
				if(onclik=true){
					btn_fbasekt.setBackgroundResource(R.drawable.folat_basket_nol);}
				}
				    
				
		}};
	/**
	 * 初始化头标
	 */
	private void InitTextView() {
		jianxi1 = (TextView) findViewById(R.id.tab_1);
		jianxi2 = (TextView) findViewById(R.id.tab_2);
		jianxi3 = (TextView) findViewById(R.id.tab_3);
		jianxi4 = (TextView) findViewById(R.id.tab_4);

		jianxi1.setTextColor(selectedColor);
		jianxi2.setTextColor(unSelectedColor);
		jianxi3.setTextColor(unSelectedColor);
		jianxi4.setTextColor(unSelectedColor);

		jianxi1.setText("春/秋装");
		jianxi2.setText("夏装");
		jianxi3.setText("冬装");
		jianxi4.setText("皮衣");

		jianxi1.setOnClickListener(new MyOnClickListener(0));
		jianxi2.setOnClickListener(new MyOnClickListener(1));
		jianxi3.setOnClickListener(new MyOnClickListener(2));
		jianxi4.setOnClickListener(new MyOnClickListener(4));
	}

	/**
	 * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果
	 */
	private void InitImageView() {
		imageview = (ImageView) findViewById(R.id.cursor);
		// 获得滑动时下面横线也跟着滑动的横线宽度
		BitmapFactory
				.decodeResource(getResources(), R.drawable.tab_selected_bg)
				.getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 获取分辨率宽度
		int screenw = dm.widthPixels;
		/**
		 * 计算偏移量 （屏幕宽度/页卡总数-图片实际宽度）/2=偏移量
		 */
		offset = (screenw / pagesize - bmpW) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		/**
		 * 设置动画的初始位置
		 */
		imageview.setImageMatrix(matrix);
	}

	/**
	 * 头标点击监听
	 * 
	 * @author Administrator
	 *
	 */
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			switch (index) {
			case 0:
				jianxi1.setTextColor(selectedColor);
				jianxi2.setTextColor(unSelectedColor);
				jianxi3.setTextColor(unSelectedColor);
				jianxi4.setTextColor(unSelectedColor);
				break;

			case 1:
				jianxi1.setTextColor(unSelectedColor);
				jianxi2.setTextColor(selectedColor);
				jianxi3.setTextColor(unSelectedColor);
				jianxi4.setTextColor(unSelectedColor);
				break;
			case 2:
				jianxi1.setTextColor(unSelectedColor);
				jianxi2.setTextColor(unSelectedColor);
				jianxi3.setTextColor(selectedColor);
				jianxi3.setTextColor(selectedColor);
				break;
			case 3:
				jianxi1.setTextColor(unSelectedColor);
				jianxi2.setTextColor(unSelectedColor);
				jianxi3.setTextColor(unSelectedColor);
				jianxi4.setTextColor(selectedColor);
				break;
			}
			viewpager.setCurrentItem(index);
		}

	}

	/**
	 * 为选项卡绑定监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		/**
		 * 页卡1->页卡2偏移量----------------------------------------------------------
		 * -----------------------------------------
		 */
		int one = offset * 2 + bmpW;
		/**
		 * 页卡1->页卡3的偏移了量
		 */
		int two = one * 2;

		@Override
		public void onPageScrollStateChanged(int index) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int index) {
			/**
			 * 选项卡滑动动画
			 */
			Animation animation = new TranslateAnimation(one * currIndex, one
					* index, 0, 0);
			currIndex = index;
			// 图片停在动画结束位置
			animation.setFillAfter(true);
			animation.setDuration(300);
			imageview.startAnimation(animation);
			/**
			 * 某个选项卡选中后，title颜色改变
			 */
			switch (index) {
			case 0:
				jianxi1.setTextColor(selectedColor);
				jianxi2.setTextColor(unSelectedColor);
				jianxi3.setTextColor(unSelectedColor);
				jianxi4.setTextColor(unSelectedColor);
				break;

			case 1:
				jianxi1.setTextColor(unSelectedColor);
				jianxi2.setTextColor(selectedColor);
				jianxi3.setTextColor(unSelectedColor);
				jianxi4.setTextColor(unSelectedColor);
				break;
			case 2:
				jianxi1.setTextColor(unSelectedColor);
				jianxi2.setTextColor(unSelectedColor);
				jianxi3.setTextColor(selectedColor);
				jianxi4.setTextColor(unSelectedColor);
				break;
			case 3:
				jianxi1.setTextColor(unSelectedColor);
				jianxi2.setTextColor(unSelectedColor);
				jianxi3.setTextColor(unSelectedColor);
				jianxi4.setTextColor(selectedColor);
				break;
			}
		}

	}

	/**
	 * 定义适配器
	 */
	class myPagerAdapter extends FragmentStatePagerAdapter {
	
		private List<Fragment> fragmentList;

		public myPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
			super(fm);
			this.fragmentList = fragmentList;
	
		}

		/**
		 * 得到每个界面
		 */
		@Override
		public Fragment getItem(int arg0) {
	
             
			return (fragmentList == null || fragmentList.size() == 0) ? null
					: fragmentList.get(arg0);
		}
		
		

		/**
		 * 得到每个界面的title
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return super.getPageTitle(position);
		}

		/**
		 * 页面总个数
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragmentList == null ? 0 : fragmentList.size();
		}
		
//		@Override
//		public void destroyItem(View container, int position, Object object) {
//			((ViewPager) container).removeView((View)object);
//		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			super.destroyItem(container, position, object);
			container.removeView(fragmentList.get(position).getView());
		}
	}
}
