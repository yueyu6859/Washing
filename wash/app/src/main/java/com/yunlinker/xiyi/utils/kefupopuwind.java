package com.yunlinker.xiyi.utils;

import com.yunlinker.xiyixi.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

//客服电话弹窗
public class kefupopuwind extends PopupWindow implements OnClickListener {
	private Context context;
	// 确定 //取消
	private Button confirm, dimi;

	private View pop;

	public kefupopuwind(Context context) {
		this.context = context;
		LayoutInflater inflatr = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		pop = inflatr.inflate(R.layout.kefu_popuwind, null);
		confirm = (Button) pop.findViewById(R.id.btn1);
		dimi = (Button) pop.findViewById(R.id.btn2);

		confirm.setOnClickListener(this);
		dimi.setOnClickListener(this);

		this.setContentView(pop);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.MATCH_PARENT);
//		this.setFocusable(true);

//		ColorDrawable dw = new ColorDrawable(0xb0000000);
//		this.setBackgroundDrawable(dw);
		//点击pop以外区域dimiss
		
		pop.setOnTouchListener(new  OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int  height=pop.findViewById(R.id.rl).getTop();
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

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:

			String phone = "02861438029";
			phone = phone.trim();
			Intent inetnt = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ phone));
			context.startActivity(inetnt);
			break;

		case R.id.btn2:
			this.dismiss();
			break;
		}

	}

}
