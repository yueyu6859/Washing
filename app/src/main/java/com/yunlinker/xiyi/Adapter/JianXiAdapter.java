package com.yunlinker.xiyi.Adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.yunlinker.xiyi.Adapter.BasketAdapter.ViewHolder;
import com.yunlinker.xiyi.bean.JianXiJavaBean;
import com.yunlinker.xiyi.bean.Results;
import com.yunlinker.xiyi.utils.ImageFileCacheUtils;
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

public class JianXiAdapter extends BaseAdapter {
//	// 请求队列
//	private RequestQueue queue;
	List<Results> a;
	private Context context;

	public JianXiAdapter(List<Results> a, Context context) {

		this.context = context;
		this.a = a;
	}

	@Override
	public int getCount() {
		return a.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return a.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	static class ViewHolder {
		private NetworkImageView  itme_image;
		private  TextView itme_text;
		private  TextView money;
		
		}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		Results userInfo = a.get(position);
		if (convertView == null) {
			convertView=View.inflate(context, R.layout.jianxi_gridview_style, null);
			holder=new  ViewHolder();
			holder.itme_image= (NetworkImageView) convertView.findViewById(R.id.itme_image);
			holder.itme_text=(TextView) convertView.findViewById(R.id.itme_text);
			holder.money=(TextView) convertView.findViewById(R.id.money);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		
      
		String url = userInfo.getImage();
		holder.itme_text.setText(userInfo.getName());
		holder.money.setText("" + userInfo.getPrice());
		/**
		 * 参数（view是ImageView的一个实例，defaultImageResId是设置的默认图片，
		 * errorImageResId是请求失败时设置的图片）
		 */
		ImageListener imagelistener = ImageLoader.getImageListener(holder.itme_image,
				R.drawable.jianxide, 0);
//		queue = getQueue();
		// 得到图片加载器
		ImageLoader loader = new ImageLoader(MyApplication.getInstance().getQueue(context), new  BitmapCache());
		// 图片加载
		holder.itme_image.setImageUrl(url,loader);
//		loader.get(url, imagelistener);
		
		return convertView;
	}

}

	class  BitmapCache implements  ImageCache{
		public Bitmap getBitmap(String  url){
			return  ImageFileCacheUtils.getInstance().getImage(url);
		}

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			// TODO Auto-generated method stub
			ImageFileCacheUtils.getInstance().saveBitmap(bitmap, url);
		}
	}

// //------------------------------------------------
// /**
// * 图片内存缓存类 把获取过的图片缓存到内存
// *
// * @author Admin
// *
// */
//class BitmapCache implements ImageCache {
//
//	// LRU算法（近期最少使用算法）
//	private LruCache<String, Bitmap> mCache;
//
//	public BitmapCache() {
//		int maxSize = 10* 1024 * 1024;
//		mCache = new LruCache<String, Bitmap>(maxSize) {
//			@Override
//			protected int sizeOf(String key, Bitmap value) {
//				// 返回图片在内存中占用的大小aaa
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
//		
//		mCache.put(url, bitmap);
//		
//	}
//	
//}