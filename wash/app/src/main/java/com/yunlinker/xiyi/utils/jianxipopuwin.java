package com.yunlinker.xiyi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.yunlinker.xiyi.Adapter.BasketAdapter;
import com.yunlinker.xiyi.bean.BasketBean;
import com.yunlinker.xiyi.bean.JianXiJavaBean;
import com.yunlinker.xiyi.bean.Results;
import com.yunlinker.xiyi.ui.Basket;
import com.yunlinker.xiyi.ui.HomeActivity;
import com.yunlinker.xiyi.ui.JianXiMain;
import com.yunlinker.xiyixi.R;

import android.R.raw;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
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
public class jianxipopuwin extends PopupWindow implements OnClickListener {
	private RequestQueue queue;
	private Activity activity;
	private Context context;
	/**
	 * 加入洗衣蓝
	 */

	private View popwin;
	private Button btn_jiaru;
	private ImageView sub_btn;
	private ImageView add_btn;
	private TextView et;
	private int Id;
	private int i = 1;
	private  Handler handler;
	/**
	 * 图片
	 */
	private ImageView image;
	/**
	 * 单件衣服名称
	 */
	private TextView name;
	/**
	 * 价格
	 */
	private TextView tv_jianxi_money,categorys;
	String url;
	boolean  isHave;
	BasketBean bean;
	public jianxipopuwin(Activity activity, Results e, Drawable image2,boolean  isHave,BasketBean bean,Handler handler) {
		
		
	
		this.handler=handler;
		this.bean=bean;
        this.isHave=isHave;
		this.activity = activity;
		// super(context);
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		popwin = inflater.inflate(R.layout.jianxipopu, null);
		btn_jiaru = (Button) popwin.findViewById(R.id.btn_jiaru);
		sub_btn = (ImageView) popwin.findViewById(R.id.sub_btn);
		et = (TextView) popwin.findViewById(R.id.et);
		add_btn = (ImageView) popwin.findViewById(R.id.add_btn);
		image = (ImageView) popwin.findViewById(R.id.image);
		name = (TextView) popwin.findViewById(R.id.name);
		categorys=(TextView) popwin.findViewById(R.id.categorys);
		tv_jianxi_money = (TextView) popwin.findViewById(R.id.tv_jianxi_money);

		Id = e.getId();
		//////////////////////////////////////////////////////////////////////////
		url = e.getImage();
//		ImageListener imagelistener = ImageLoader.getImageListener(image, 0, 0);
//		queue = Volley.newRequestQueue(activity);
		// 得到图片加载器
//		ImageLoader loader = new ImageLoader(queue, new BitmapCache());
		// 图片加载
//		loader.get(url, imagelistener);
		image.setImageDrawable(image2);
		name.setText(e.getName());
		tv_jianxi_money.setText("" + e.getPrice());
		
		categorys.setText(e.getDescription());
		sub_btn.setOnClickListener(this);
		add_btn.setOnClickListener(this);
		btn_jiaru.setOnClickListener(this);

		/**
		 * 设置弹出窗口的内容
		 */
		this.setContentView(popwin);
		/**
		 * 设置弹窗的宽度
		 */
		this.setWidth(LayoutParams.MATCH_PARENT);
		/**
		 * 设置弹窗的高度
		 */
		// this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setHeight(LayoutParams.MATCH_PARENT);
		this.setFocusable(true);
		/**
		 * 设置主Activity背景颜色
		 */
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		this.setBackgroundDrawable(dw);
		//添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		popwin.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int height=popwin.findViewById(R.id.lL_daixikuang).getTop();
				int y=(int) event.getY();
				if(event.getAction()==MotionEvent.ACTION_UP){
					if(y<height){
						dismiss();
					}
				}
				return true;
			}
		});
//		this.setOutsideTouchable(true);
	}

	
   boolean onclik=false;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sub_btn:

			if (Integer.parseInt(et.getText().toString()) >1) {
				et.setText("" + (--i));
			}
			break;

		case R.id.add_btn:

			et.setText("" + (++i));
			break;
		case R.id.btn_jiaru:
           
			if (Integer.parseInt(et.getText().toString()) > 0) {
				onclik=true;
				handler.sendMessage(handler.obtainMessage(12,onclik));
				Toast.makeText(activity, "已加入洗衣篮", 6000).show();
	
				
				if(isHave){
			
					Log.e("jianxipopuwin", "isHave==true?="+isHave);
						int num=Integer.parseInt(bean.getNumber())+Integer.parseInt(et.getText().toString());
						bean.setNumber(num+"");
					
				}else{
					
					Log.e("jianxipopuwin", "isHave==true?="+isHave);
					
					BasketBean bean = new BasketBean();
					bean.setName(name.getText().toString());
					bean.setPrice(tv_jianxi_money.getText().toString());
					bean.setNumber(et.getText().toString());
					bean.setXiYiid(Id);
					//存的图片
//					bean.setMimage(image.getDrawable());
	               //该存url地址
					bean.setMimage(url);
				 
					HomeActivity.list.add(bean);
			
				}


				
				try {
					new ArraySer().ser(HomeActivity.list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				onclik=false;
				this.dismiss();
				
			}
			break;

		}
	}
}

//class BitmapCache implements ImageCache {
//
//	// LRU算法（近期最少使用算法）
//	private LruCache<String, Bitmap> mCache;
//
//	public BitmapCache() {
//		int maxSize = 10 * 1024 * 1024;
//		mCache = new LruCache<String, Bitmap>(maxSize) {
//			@Override
//			protected int sizeOf(String key, Bitmap value) {
//				// 返回图片在内存中占用的大小
//				return value.getRowBytes() * value.getHeight();
//			}
//		};
//	}
//
//	@Override
//	public Bitmap getBitmap(String url) {
//		return mCache.get(url);
//	}
//
//	@Override
//	public void putBitmap(String url, Bitmap bitmap) {
//		mCache.put(url, bitmap);
//	}
//}
