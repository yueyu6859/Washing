package com.yunlinker.xiyi.ui;

import java.util.ArrayList;
import java.util.List;

import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;
import com.yunlinker.xiyi.fragemnt.Alipay;
import com.yunlinker.xiyi.fragemnt.RemainingPay;
import com.yunlinker.xiyi.ui.JianXiMain.MyOnPageChangeListener;
import com.yunlinker.xiyi.utils.BaseFragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * ֧支付界面
 * 
 * @author Administrator
 *
 */
public class Pay extends BaseFragmentActivity implements OnClickListener {
	// 返回
	private ImageButton return11;
	// 页卡内容
	private ViewPager viewpager;
	// 选项名称
	private TextView remaining_money, alipay;
	// tab页面列表
	private List<Fragment> mfragment;
	// 当前页卡编号
	private int currIndex = 0;
	// 字体颜色和背景颜色
	
	Alipay appPay = new Alipay();
	private int selectedColorText, unSelectedColorText,
			selectedColorBackground, unSelectedColorBackground;
	/**
	 * 页卡总数
	 */
	private static final int pagesize = 2;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay);
		initView();
		return11 = (ImageButton) findViewById(R.id.return11);

		
       
		
		return11.setOnClickListener(this);
		
		
	}
 
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.return11:
			Pay.this.finish();

			break;

		default:
			break;
		}

	}

	/**
	 * 字体颜色和背景颜色
	 */
	private void initView() {
		selectedColorText = getResources().getColor(R.color.white);
		unSelectedColorText = getResources().getColor(R.color.zi_yi);
		selectedColorBackground = getResources().getColor(R.color.qianju);
		unSelectedColorBackground = getResources().getColor(R.color.white);

		InitTextView();
		InitViewPager();
		
		 
			
		
		
	}
	
	/**
	 * 初始化viewpager页
	 */
	private void InitViewPager() {
		viewpager = (ViewPager) findViewById(R.id.paymentpager);
		mfragment = new ArrayList<Fragment>();
		mfragment.add(new RemainingPay());
		mfragment.add(appPay);
			
		viewpager.setAdapter(new myPagerAdapter(getSupportFragmentManager(),
				mfragment));
		viewpager.setCurrentItem(0);
		viewpager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * 初始化头标
	 */
	private void InitTextView() {
		remaining_money = (TextView) findViewById(R.id.mtab_1);
		alipay = (TextView) findViewById(R.id.mtab_2);

		remaining_money.setTextColor(selectedColorText);
		alipay.setTextColor(unSelectedColorText);

		remaining_money.setText("余额支付");
		alipay.setText("支付宝支付");

		remaining_money.setBackgroundColor(selectedColorBackground);
		alipay.setBackgroundColor(unSelectedColorBackground);

		remaining_money.setOnClickListener(new MyOnClickListener(0));
		alipay.setOnClickListener(new MyOnClickListener(1));
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
				remaining_money.setTextColor(selectedColorText);
				alipay.setTextColor(unSelectedColorText);

				remaining_money.setBackgroundColor(selectedColorBackground);
				alipay.setBackgroundColor(unSelectedColorBackground);
				break;

			case 1:
				appPay.pay();
				remaining_money.setTextColor(unSelectedColorText);
				alipay.setTextColor(selectedColorText);

				remaining_money.setBackgroundColor(unSelectedColorBackground);
				alipay.setBackgroundColor(selectedColorBackground);
				break;
			}
			viewpager.setCurrentItem(index);
		}
	}

	/**
	 * 定义适配器
	 */
	class myPagerAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragmentlist;

		public myPagerAdapter(FragmentManager fm, List<Fragment> fragmentlist) {
			super(fm);
			this.fragmentlist = fragmentlist;
		}

		/**
		 * 得到每个界面
		 */
		public Fragment getItem(int arg0) {

			return (fragmentlist == null || fragmentlist.size() == 0) ? null
					: fragmentlist.get(arg0);
		}

		/**
		 * 得到每个界面的title
		 */
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return super.getPageTitle(position);
		}

		/**
		 * 页面总个数
		 */
		public int getCount() {

			return fragmentlist == null ? 0 : fragmentlist.size();
		}

	}

	/**
	 * 为选项卡绑定监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

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
			 * 某个选项卡选中后，title颜色改变
			 */
			switch (index) {
			case 0:
				remaining_money.setTextColor(selectedColorText);
				alipay.setTextColor(unSelectedColorText);

				remaining_money.setBackgroundColor(selectedColorBackground);
				alipay.setBackgroundColor(unSelectedColorBackground);
				break;

			case 1:
//				appPay.pay();
				remaining_money.setTextColor(unSelectedColorText);
				alipay.setTextColor(selectedColorText);

				remaining_money.setBackgroundColor(unSelectedColorBackground);
				alipay.setBackgroundColor(selectedColorBackground);
				break;
			}

		}

	}
	

		
	
}
