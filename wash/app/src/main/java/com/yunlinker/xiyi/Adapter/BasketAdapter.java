package com.yunlinker.xiyi.Adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.yunlinker.xiyi.bean.BasketBean;
import com.yunlinker.xiyi.ui.HomeActivity;
import com.yunlinker.xiyi.utils.ArraySer;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

public class BasketAdapter extends BaseAdapter {
	ArraySer  Ser;
	String url;
	private Handler mHandler;
	private Context context;
    private  HomeActivity  activity;
	List<BasketBean> list;
	int nowCount;
	ListView productList;

	TextView totalPrice;

	public BasketAdapter(Context context, List<BasketBean> list,
			Handler mHandler, ListView listView, TextView txt) {
		this.context = context;
		this.list = list;
		this.mHandler = mHandler;
		this.productList = listView;
		this.totalPrice = txt;
		
	}

	@Override
	public int getCount() {
      
		return list.size();

	}

	@Override
	public Object getItem(int position) {

		// return HomeActivity.list.get(position);
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	public void removeItem(int position) {
		list.remove(position);

		ViewGroup.LayoutParams params = productList.getLayoutParams();

		int totalHeight = 0;
		for (int i = 0; i < list.size(); i++) {
			View listItem = this.getView(i, null, productList);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		params.height = totalHeight
				+ (productList.getDividerHeight() * (list.size() - 1));

		((MarginLayoutParams) params).setMargins(0, 0, 0, 0); // 可删

		productList.setLayoutParams(params);
		// productList.setAdapter(this);
		this.notifyDataSetChanged();

	}
	
	static class ViewHolder {
		// 图片
		private  NetworkImageView xiyilan_image;
		// 名字
		private TextView name;
		// 数量加减
		private ImageView msubbtn, maddbtn;
		// 数量显示框
		private TextView number;
		// 价格
		private TextView money;
		// 删除
		private ImageView del;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ArraySer  Ser=new ArraySer();
		BasketBean userinfo = list.get(position);
		final ViewHolder holder;
		if (convertView == null) {
			convertView= View.inflate(context, R.layout.basket_item, null);
			holder = new ViewHolder();
			holder.xiyilan_image = (NetworkImageView) convertView.findViewById(R.id.xiyilan_image);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.msubbtn = (ImageView) convertView.findViewById(R.id.msubbtn);
			holder.maddbtn = (ImageView) convertView.findViewById(R.id.maddbtn);
			holder.number = (TextView) convertView.findViewById(R.id.number);
			holder.money = (TextView) convertView.findViewById(R.id.money);
			holder.del = (ImageView) convertView.findViewById(R.id.del);

			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		
//		holder.xiyilan_image.setImageDrawable(userinfo.getMImage());

       
		holder.name.setText(userinfo.getName());
		holder.number.setText(userinfo.getNumber());
		holder.money.setText(userinfo.getPrice());
		
		/**
		 * 参数（view是ImageView的一个实例，defaultImageResId是设置的默认图片，
		 * errorImageResId是请求失败时设置的图片）
		 */
		url = userinfo.getMImage();
		ImageListener imagelistener = ImageLoader.getImageListener(holder.xiyilan_image,
				R.drawable.jianxide, 0);
//		queue = getQueue();
		// 得到图片加载器
		ImageLoader loader = new ImageLoader(MyApplication.getInstance().getQueue(context), new  BitmapCach());
		// 图片加载
		holder.xiyilan_image.setImageUrl(url, loader);
//		loader.get(url, imagelistener);
		
		
		
		holder.msubbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
//				int nowCount = Integer
//						.parseInt(	holder.number.getText().toString());
				if(Integer.parseInt(holder.number.getText().toString())>1){
					int nowCount = Integer.parseInt(holder.number.getText().toString());
					String nowNumStr=Integer.toString(nowCount - 1);
					if (nowCount > 1) {
						holder.number.setText(nowNumStr);
					}
					list.get(position).setNumber(nowNumStr);
                    try {
						Ser.ser(list);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mHandler.sendMessage(mHandler.obtainMessage(10,
							getTotalPrice()));
				}
				

			}
		});

		holder.maddbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int nowCount = Integer
						.parseInt(holder.number.getText().toString());
				String nowNumStr=Integer.toString(nowCount + 1);
				holder.number.setText(nowNumStr);
				list.get(position).setNumber(nowNumStr);
				try {
					Ser.ser(list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mHandler.sendMessage(mHandler.obtainMessage(10,
						getTotalPrice()));
			}
		});

		mHandler.sendMessage(mHandler.obtainMessage(10, getTotalPrice()));

		holder.del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				removeItem(position);
				mHandler.sendMessage(mHandler.obtainMessage(10,
						getTotalPrice()));
				try {
			    	  
			    	   Ser.ser(list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 }
			}
		});
		return convertView;
	}

	/**
	 * 计算商品的总金额
	 * 
	 * @return 返回需要付费的总金额
	 */
	public float getTotalPrice() {
		BasketBean bean = null;
		float totalPrice = 0;
		for (int i = 0; i < list.size(); i++) {
			bean = list.get(i);

			totalPrice += Integer.parseInt(bean.getPrice())
					* Integer.parseInt(bean.getNumber().toString());
		}
	
		return totalPrice;
		
	}
  
	
}
////------------------------------------------------
///**
//* 图片内存缓存类 把获取过的图片缓存到内存
//*
//* @author Admin
//*
//*/
class BitmapCach implements ImageCache {

	// LRU算法（近期最少使用算法）
	private LruCache<String, Bitmap> mCache;

	public BitmapCach() {
		int maxSize = 10* 1024 * 1024;
		mCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// 返回图片在内存中占用的大小aaa
				return value.getRowBytes() * value.getHeight();
			}
		};
	}

	@Override
	public Bitmap getBitmap(String url) {
		return mCache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		
		mCache.put(url, bitmap);
		
	}
}
	