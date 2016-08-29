package com.yunlinker.xiyi.ui;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.yunlinker.xiyi.bean.orderdelbean;
import com.yunlinker.xiyi.fragemnt.Accomplish;
import com.yunlinker.xiyi.fragemnt.DaiFukuan;
import com.yunlinker.xiyi.fragemnt.DaiQuJian;
import com.yunlinker.xiyi.fragemnt.DaiSongJian;
import com.yunlinker.xiyi.utils.BaseFragmentActivity;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 我的订单
 * 
 * @author Administrator
 *
 */
public class MyOrderMain extends BaseFragmentActivity {
	//订单详情
		public   static  List<orderdelbean>lists=new  ArrayList<orderdelbean>();
	private static RequestQueue queue;
	// 页卡内容
	private ViewPager viewpager;
	// 动画图片
	private ImageView imageview;
	// 选项名称
	private TextView daifukuan,daiqujian,daisongjian, accomplish;
	// tab页面列表
	private List<Fragment> fragments;
	// 动画图片偏移量
	private int offset = 0;
	// 当前页卡编号
	private int currIndex = 0;
	// 动画图片宽度
	private int bmpW;
	// 返回键
	private ImageButton return3;
	private int selectedColor, unSelectedColor;
	/**
	 * 页卡总数
	 */
	private static final int pagesize = 4;

	
	@Override
	protected void onResume() {
		super.onResume();
//		// 初始化一个请求队列
//		queue = Volley.newRequestQueue(getApplicationContext());
	}
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myorder_mian);
		return3 = (ImageButton) findViewById(R.id.return3);
		// 初始化一个请求队列
				queue = Volley.newRequestQueue(getApplicationContext());
		
		initView();

		

		return3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyOrderMain.this.finish();

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
	protected void onDestroy() {
		super.onDestroy();
//		queue.cancelAll(this);
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
		viewpager = (ViewPager) findViewById(R.id.orderpager);
		fragments = new ArrayList<Fragment>();
		fragments.add(new DaiFukuan());
		fragments.add(new DaiQuJian());
		fragments.add(new DaiSongJian());
		fragments.add(new Accomplish());
		viewpager.setAdapter(new myPagerAdapter(getSupportFragmentManager(),
				fragments));
		viewpager.setCurrentItem(0);
		viewpager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * 初始化头标
	 */
	private void InitTextView() {
		
		daifukuan=(TextView) findViewById(R.id.tab0);
		daiqujian= (TextView) findViewById(R.id.tab1);
		daisongjian= (TextView) findViewById(R.id.tab2);
		accomplish = (TextView) findViewById(R.id.tab3);
		
		daifukuan.setTextColor(selectedColor);
		daiqujian.setTextColor(unSelectedColor);
		daisongjian.setTextColor(unSelectedColor);
		accomplish.setTextColor(unSelectedColor);
		
		daifukuan.setText("待付款");
		daiqujian.setText("待取件");
		daisongjian.setText("待送件");
		accomplish.setText("已完成");
		
	    daifukuan.setOnClickListener(new  MyOnClickListener(0));
		daiqujian.setOnClickListener(new MyOnClickListener(1));
		daisongjian.setOnClickListener(new MyOnClickListener(2));
		accomplish.setOnClickListener(new MyOnClickListener(3));

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
				daifukuan.setTextColor(selectedColor);
				daiqujian.setTextColor(unSelectedColor);
				daisongjian.setTextColor(unSelectedColor);
				accomplish.setTextColor(unSelectedColor);
				break;

			case 1:
				daifukuan.setTextColor(unSelectedColor);
				daiqujian.setTextColor(selectedColor);
				daisongjian.setTextColor(unSelectedColor);
				accomplish.setTextColor(unSelectedColor);

				break;
			case 2:
				daifukuan.setTextColor(unSelectedColor);
				daisongjian.setTextColor(unSelectedColor);
				daiqujian.setTextColor(selectedColor);
				accomplish.setTextColor(unSelectedColor);

				break;
			case  3:
				daifukuan.setTextColor(unSelectedColor);
				daisongjian.setTextColor(unSelectedColor);
				daiqujian.setTextColor(unSelectedColor);
				accomplish.setTextColor(selectedColor);

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
		int trhee=one * 3;

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
				daifukuan.setTextColor(selectedColor);
				daiqujian.setTextColor(unSelectedColor);
				daisongjian.setTextColor(unSelectedColor);
				accomplish.setTextColor(unSelectedColor);

				break;

			case 1:
				daifukuan.setTextColor(unSelectedColor);
				daiqujian.setTextColor(selectedColor);
				daisongjian.setTextColor(unSelectedColor);
				accomplish.setTextColor(unSelectedColor);

				break;
			case 2:
				daifukuan.setTextColor(unSelectedColor);
				daiqujian.setTextColor(unSelectedColor);
				daisongjian.setTextColor(selectedColor);
				accomplish.setTextColor(unSelectedColor);

				break;
			case 3:
				daifukuan.setTextColor(unSelectedColor);
				daisongjian.setTextColor(unSelectedColor);
				daiqujian.setTextColor(unSelectedColor);
				accomplish.setTextColor(selectedColor);

				break;

			}
		}

	}

	/**
	 * 定义适配器
	 */
	class myPagerAdapter extends FragmentPagerAdapter {
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

	}

	
	
}