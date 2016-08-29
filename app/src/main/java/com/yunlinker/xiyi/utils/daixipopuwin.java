package com.yunlinker.xiyi.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.yunlinker.xiyi.bean.BasketBean;
import com.yunlinker.xiyi.bean.JianXiJavaBean;
import com.yunlinker.xiyi.bean.Results;
import com.yunlinker.xiyi.ui.Basket;
import com.yunlinker.xiyi.ui.HomeActivity;
import com.yunlinker.xiyi.ui.JianXiMain;
import com.yunlinker.xiyixi.R;

import android.R.integer;
import android.R.raw;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 弹窗
 * 
 * @author Administrator
 *
 */
public class daixipopuwin extends PopupWindow implements OnClickListener {
	String  url;
	HomeActivity  activty;
	boolean isHave=false;
	BasketBean bean=null ;
	private Context context;
//	private RequestQueue queue;
	/**
	 * 加入洗衣蓝
	 */
	private View popwin;
	private Button btn_jiaru;
	private ImageButton sub_btn;
	private ImageButton add_btn;
	private TextView met;
	private ImageView images;
    private  int  id;
	// 名称.价格.描述
	private TextView mnames, tv_daixi_money, description;
	private int i = 1;

	
	public daixipopuwin(Context context, List<Results> a,HomeActivity  activty) {
		
		this.activty=activty;
		
		
		
		
		
		this.context = context;

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		popwin = inflater.inflate(R.layout.daixi, null);
		btn_jiaru = (Button) popwin.findViewById(R.id.btn_jiaru);
		sub_btn = (ImageButton) popwin.findViewById(R.id.sub_btn);
		met =  (TextView) popwin.findViewById(R.id.et);
		add_btn = (ImageButton) popwin.findViewById(R.id.add_btn);
		tv_daixi_money = (TextView) popwin.findViewById(R.id.tv_daixi_money);
		description = (TextView) popwin.findViewById(R.id.description);
		mnames = (TextView) popwin.findViewById(R.id.mnames);
		images = (ImageView) popwin.findViewById(R.id.images);
		
		// JianXiJavaBean userInfo=a.get(0);
		if (a.size() != 0) {
			Results userInfo = a.get(0);
			id=userInfo.getId();
			url=userInfo.getImage();

			for (BasketBean b  : HomeActivity.list) {
				
				if((b.getName()).equals(userInfo.getName())){
					
					Log.e("JianxiOne", b.getName()+"名字");
					
					isHave=true;
					bean=b;
					
				}
				
			}
			
			
			
			mnames.setText("" + userInfo.getName());
			tv_daixi_money.setText("" + userInfo.getPrice());
			description.setText(userInfo.getDescription());
			
			btn_jiaru.setOnClickListener(this);
			sub_btn.setOnClickListener(this);
			add_btn.setOnClickListener(this);
		}
		/**
		 * 设置弹出窗口的内容
		 */
		this.setContentView(popwin);
		/**
		 * a 设置弹窗的宽度
		 */
		this.setWidth(LayoutParams.MATCH_PARENT);
		/**
		 * 设置弹窗的高度
		 */
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		/**
		 * 设置主Activity背景颜色
		 */
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		this.setBackgroundDrawable(dw);
		//点击pop以外区域，取消pop
		popwin.setOnTouchListener(new  OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int height=popwin.findViewById(R.id.lL_daixikuang).getTop();
				int  y=(int) event.getY();
				if(event.getAction()==MotionEvent.ACTION_UP){
					if(y<height){
						dismiss();
					}
				}
				return true;
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sub_btn:
			if (Integer.parseInt(met.getText().toString()) > 1) {
				met.setText("" + (--i));
				
			}
			break;

		case R.id.add_btn:
			met.setText(""+(++i));
			break;

			
			
		case R.id.btn_jiaru:
			
			if (Integer.parseInt(met.getText().toString()) > 0) {
				Toast.makeText(context, "已加入洗衣篮", 6000).show();
				if(isHave){
					int num=Integer.parseInt(bean.getNumber())+Integer.parseInt(met.getText().toString());
					bean.setNumber(num+"");
				}else{
				BasketBean 	bean1 = new BasketBean();
				bean1.setName(mnames.getText().toString());
				bean1.setPrice(tv_daixi_money.getText().toString());
				bean1.setNumber(met.getText().toString());
//				bean1.setMimage(images.getBackground());
				bean1.setMimage(url);
				bean1.setXiYiid(id);
				Log.d("id", "" + bean1.getNumber());
				Log.d("价格", "" + bean1.getPrice());
                
				
				
				
				HomeActivity.list.add(bean1);
//			    BasketBean tmpUser = null;
//				Map<String, BasketBean> map = new HashMap<String, BasketBean>();
//				for( BasketBean a:HomeActivity.list){
//					 tmpUser = map.get(a.getName());
//				 if(tmpUser != null){
//					 int nume=Integer.parseInt((tmpUser.getNumber()))+Integer.parseInt(a.getNumber());
//					 tmpUser.setNumber(Integer.toString(nume));
//					 }else{
//						 map.put(a.getName(), a);
//					 }
//				    
//				 Log.d("衣服有没有",a.getName());
//				}
				
				
				}			
				
				
				
//				
				Log.d("袋洗", "" + HomeActivity.list.size());
				if(HomeActivity.list.size()!=0){
					activty.basket_have.setVisibility(View.VISIBLE);
				}
				try {
					new ArraySer().ser(HomeActivity.list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.dismiss();

				break;
			}
		}

	}

//	class BitmapCache implements ImageCache {
//
//		// LRU算法（近期最少使用算法）
//		private LruCache<String, Bitmap> mCache;
//
//		public BitmapCache() {
//			int maxSize = 10 * 1024 * 1024;
//			mCache = new LruCache<String, Bitmap>(maxSize) {
//				@Override
//				protected int sizeOf(String key, Bitmap value) {
//					// 返回图片在内存中占用的大小
//					return value.getRowBytes() * value.getHeight();
//				}
//			};
//		}
//
//		@Override
//		public Bitmap getBitmap(String url) {
//			return mCache.get(url);
//		}
//
//		@Override
//		public void putBitmap(String url, Bitmap bitmap) {
//			mCache.put(url, bitmap);
//		}
//	}

}
