package com.yunlinker.xiyi.ui;

import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.GridView;
import android.widget.ImageButton;






import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;
import com.yunlinker.xiyi.Adapter.ServeExplainAdapter;
import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyi.utils.BitmapUtil;
import com.yunlinker.xiyi.utils.DragImageView;


//服务说明
public class ServeExplain extends  BaseActivity{
	private int window_width, window_height;// 控件宽度
	private DragImageView dragImageView;// 自定义控件
	private int state_height;// 状态栏的高度
	
	private ViewTreeObserver viewTreeObserver;
	
	private  ImageButton   return12;
//	private  GridView   housing;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
//    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.serve_explain);
    	return12=(ImageButton) findViewById(R.id.return18);
    	dragImageView=(DragImageView) findViewById(R.id.scope);
    	// 方便后续退出整个应用程序
//        MyApplication.getInstance().addActivity(ServeExplain.this);
    	
    	/** 获取可見区域高度 **/
		WindowManager manager = getWindowManager();
		window_width = manager.getDefaultDisplay().getWidth();
		window_height = manager.getDefaultDisplay().getHeight();
    	
    	
		Bitmap bmp = BitmapUtil.ReadBitmapById(this, R.drawable.map,
				window_width, window_height);
		
		// 设置图片
				dragImageView.setImageBitmap(bmp);
				dragImageView.setmActivity(this);//注入Activity.
				
				
				/** 测量状态栏高度 **/
				viewTreeObserver = dragImageView.getViewTreeObserver();
				viewTreeObserver
						.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
							@Override
							public void onGlobalLayout() {
								if (state_height == 0) {
									// 获取状况栏高度
									Rect frame = new Rect();
									getWindow().getDecorView()
											.getWindowVisibleDisplayFrame(frame);
									state_height = frame.top;
									dragImageView.setScreen_H((window_height-state_height));
									dragImageView.setScreen_W(window_width);
								}

							}
						});
				
				
				
				
				
				
		
    	
//       housing.setAdapter(new ServeExplainAdapter(ServeExplain.this,HomeActivity.results));
       return12.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ServeExplain.this.finish();
			
		}
	});
    }
	
	
     
     
     /**
 	 * 读取本地资源的图片
 	 */
 	public static Bitmap ReadBitmapById(Context context, int resId) {
 		BitmapFactory.Options opt = new BitmapFactory.Options();
 		opt.inPreferredConfig = Bitmap.Config.RGB_565;
 		opt.inPurgeable = true;
 		opt.inInputShareable = true;
 		// 获取资源图片
 		InputStream is = context.getResources().openRawResource(resId);
 		return BitmapFactory.decodeStream(is, null, opt);
 	}
	@Override
	protected void onDestroy(){ 
		super.onDestroy();
		if(dragImageView!=null){
			Drawable	v=dragImageView.getDrawable();
			if(v!=null){
				v.setCallback(null);
			}
			}
		System.gc();
	}
}
