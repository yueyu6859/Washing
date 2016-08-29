package com.yunlinker.xiyi.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;



import com.yunlinker.xiyi.utils.BaseActivity;
import com.yunlinker.xiyixi.MyApplication;
import com.yunlinker.xiyixi.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 分享推荐码
 * 
 * @author Administrator
 *
 */
public class Share extends BaseActivity {
	private Bitmap bitmap;
	String invite_code;
	//分享，使用规则
	private Button btn_share,btn_regulations;
	// 返回
	private ImageButton return6;
	private  TextView  tuijiantext;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
     
		// 方便后续退出整个应用程序
//		MyApplication.getInstance().addActivity(Share.this);

		setContentView(R.layout.share_recommend_coding);
		
		ShareSDK.initSDK(this);
		tuijiantext=(TextView) findViewById(R.id.tuijiantext);
		btn_regulations=(Button) findViewById(R.id.btn_regulations);
		 
		
		 
	
		//更改字体
		Typeface typeFace =Typeface.createFromAsset(Share.this.getAssets(), "fonts/hanzhen.ttf");
		tuijiantext .setTypeface(typeFace);
		
		
		SharedPreferences mp = Share.this.getSharedPreferences(
				"userinfo", Context.MODE_PRIVATE);
		invite_code = mp.getString("invite_code", "");
	
		if (!invite_code.equals("")){
			tuijiantext.setText(invite_code);
		} else{
			tuijiantext.setText("暂无推荐码");
		}
		
		btn_share = (Button) findViewById(R.id.btn_share);
		return6 = (ImageButton) findViewById(R.id.return6);
		
		// 方便后续退出整个应用程序
				MyApplication.getInstance().addActivity(this);

		return6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Share.this.finish();

			}
		});

		btn_share.setOnClickListener(new View.OnClickListener() {

		
			public void onClick(View v) {
				String filePath = screenShot(Share.this);
				showShare(filePath);
			}
		});
		
		btn_regulations.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent  intent=new  Intent(Share.this, UseRegulations.class);
				startActivity(intent);
				
			}
		});
	}
	private String screenShot(Activity  activity) {
	 
		Drawable  in = getResources().getDrawable(R.drawable.shar);
		BitmapDrawable bd = (BitmapDrawable) in;
		 bitmap = bd.getBitmap();
		 String filePath = saveImageView(bitmap);
		 return filePath;
	}
	private String saveImageView(Bitmap bitmap2) {
		String name ="-share"+".JPEG";
		FileOutputStream b = null;
		File file = new File("/sdcard/Image/");
		file.mkdirs();// 创建文件夹
		String fileName = "/sdcard/Image/" + name;
		try {
			b = new FileOutputStream(fileName);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return fileName;
	}
	private void showShare(String filePath) {
		 ShareSDK.initSDK(this);//sharesdk的初始化
		 OnekeyShare oks = new OnekeyShare();
		 //关闭sso授权
		 oks.disableSSOWhenAuthorize(); 

		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//		 oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		 oks.setTitle(getString(R.string.share));
		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//		 oks.setTitleUrl("http://sharesdk.cn");
		 oks.setTitleUrl("https://www.yunlinker.com/xiyi/down_user.html");
		 // text是分享文本，所有平台都需要这个字段
		 oks.setText("首次下载（浣客洗衣）注册就送优惠券，填写我的推荐码："+invite_code+"，更有好礼送");
		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		 
//		 oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		 oks.setImagePath(filePath);
		 // url仅在微信（包括好友和朋友圈）中使用
//		 oks.setUrl("http://sharesdk.cn");
		 oks.setUrl("https://www.yunlinker.com/xiyi/down_user.html");
		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		 oks.setComment("");
		 // site是分享此内容的网站名称，仅在QQ空间使用
		 oks.setSite(getString(R.string.app_name));
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//		 oks.setSiteUrl("http://sharesdk.cn");a
		 oks.setSiteUrl("https://www.yunlinker.com/xiyi/down_user.html");

		// 启动分享GUI
		 oks.show(this);
		 }
}
