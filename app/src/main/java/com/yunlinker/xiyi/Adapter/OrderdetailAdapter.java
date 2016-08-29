package com.yunlinker.xiyi.Adapter;

import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.yunlinker.xiyi.Adapter.BasketAdapter.ViewHolder;
import com.yunlinker.xiyi.bean.orderdelbean;
import com.yunlinker.xiyi.ui.HomeActivity;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderdetailAdapter extends BaseAdapter{

  
	
	List<orderdelbean>list;
	Context context;
	
	public  OrderdetailAdapter(Context context,List<orderdelbean>lists){
		this.context=context;
		this.list=lists;
	}
	
	
	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	static class ViewHolder {
		 private  NetworkImageView  xiyilan_image;
			private  TextView  name,number,money;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		
		if(convertView==null){
			convertView=View.inflate(context, R.layout.basket_item, null);
			holder=new  ViewHolder();
			holder.xiyilan_image = (NetworkImageView)convertView.findViewById(R.id.xiyilan_image);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.number = (TextView)convertView.findViewById(R.id.number);
			holder.money = (TextView) convertView.findViewById(R.id.money);
			
			convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag(); 
		
	}
	   
		orderdelbean  order=list.get(position);
		holder.name.setText(order.getName());
		holder.number.setText(order.getNum());
		holder.money.setText(order.getPrice());
       String url =order.getImage();
       /**
		 * 参数（view是ImageView的一个实例，defaultImageResId是设置的默认图片，
		 * errorImageResId是请求失败时设置的图片）
		 */
		ImageListener imagelistener = ImageLoader.getImageListener(holder.xiyilan_image,
				0, 0);
//		queue = Volley.newRequestQueue(context);
		// 得到图片加载器
					ImageLoader loader = new ImageLoader(MyApplication.getInstance().getQueue(context), new BitmapCache());
					// 图片加载
					holder.xiyilan_image.setImageUrl(url, loader);
//					loader.get(url, imagelistener);
					return convertView;
	}
	
	//-------------------------------------------------------------------------------
	// //------------------------------------------------
	// /**
	// * 图片内存缓存类 把获取过的图片缓存到内存
	// *
	// * @author Admin
	// *
	// */
	class BitmapCache implements ImageCache {

		// LRU算法（近期最少使用算法）
		private LruCache<String, Bitmap> mCache;

		public BitmapCache() {
			int maxSize = 10 * 1024 * 1024;
			mCache = new LruCache<String, Bitmap>(maxSize) {
				@Override
				protected int sizeOf(String key, Bitmap value) {
					// 返回图片在内存中占用的大小
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
}