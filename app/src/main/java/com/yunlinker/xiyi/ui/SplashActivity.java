package com.yunlinker.xiyi.ui;

import java.util.ArrayList;
import java.util.List;

import com.yunlinker.xiyi.Adapter.ViewPagerAdapter;
import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

/**
 * 引导页面
 * 
 * @author Administrator
 *
 */
public class SplashActivity extends BaseActivity implements OnClickListener,
		OnPageChangeListener {
	ImageButton btn_zhuye;
	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;
	Boolean isfistopen;
	private SharedPreferences preferences;
	// 引导图片资源
	private static final int[] pics = { R.drawable.wash01, R.drawable.wash02,
			R.drawable.wash03, };

	// 底部小点图片
	private ImageView[] dot;

	// 记录当前选中位置
	private int currentIndex;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_viewpager);
        btn_zhuye = (ImageButton) findViewById(R.id.btn_zhuye);
        preferences = getSharedPreferences("count", MODE_PRIVATE);
        isfistopen=preferences.getBoolean("isfistopen", true);
        if(isfistopen!=true){
        	Intent  intent=new Intent(SplashActivity.this, XiyiLoging.class);
        	startActivity(intent);
        }else{
		
		
		// 方便后续退出整个应用程序
//		MyApplication.getInstance().addActivity(SplashActivity.this);

		views = new ArrayList<View>();

		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);

		// 初始化引导图片列表
		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(mParams);
			iv.setImageResource(pics[i]);
			iv.setScaleType(ScaleType.FIT_XY);
			views.add(iv);
		}
		vp = (ViewPager) findViewById(R.id.viewpager);
		// 初始化Adapter
		vpAdapter = new ViewPagerAdapter(views);
		vp.setAdapter(vpAdapter);
		// 绑定回调
		vp.setOnPageChangeListener(this);

		// 初始化底部小点
		initDots();

	}}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

		dot = new ImageView[pics.length];

		// 循环取得小点图片
		for (int i = 0; i < pics.length; i++) {
			dot[i] = (ImageView) ll.getChildAt(i);
			dot[i].setEnabled(true);// 都设为灰色
			dot[i].setOnClickListener(this);
			dot[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
		}

		currentIndex = 0;
		dot[currentIndex].setEnabled(false);// 设置为白色，即选中状态
	}

	/**
	 * 设置当前的引导页
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}

		vp.setCurrentItem(position);
	}

	/**
	 * 这只当前引导小点的选中
	 */
	private void setCurDot(int positon) {
		if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
			return;
		}

		dot[positon].setEnabled(false);
		dot[currentIndex].setEnabled(true);

		currentIndex = positon;
	}

	// 当滑动状态改变时调用
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	// 当前页面被滑动时调用
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	// 当新的页面被选中时调用
	@Override
	public void onPageSelected(int arg0) {
		// 设置底部小点选中状态
		setCurDot(arg0);
		if (arg0 == pics.length - 1) {
			
			btn_zhuye.setVisibility(btn_zhuye.VISIBLE);
			btn_zhuye.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Editor editor = preferences.edit();
					editor.putBoolean("isfistopen", false);
					editor.commit();
     			// 存入数据
					Intent intent = new Intent(SplashActivity.this,
							XiyiLoging.class);
					startActivity(intent);
					finish();

				}
			});
		}else{btn_zhuye.setVisibility(btn_zhuye.INVISIBLE);}
	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);
	}
	
}
