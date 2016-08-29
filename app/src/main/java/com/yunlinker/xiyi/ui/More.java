package com.yunlinker.xiyi.ui;


import com.yunlinker.xiyi.utils.kefupopuwind;
import com.yunlinker.xiyixi.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 * 更多
 * 
 * @author Administrator
 *
 */
public class More extends Fragment implements OnClickListener {
	kefupopuwind kefu ;

	// 联系客服,常见问题,服务范围，关于我们，用户协议，意见反馈
	private RelativeLayout rl_kefu, rl_problem, rl_fuwufanwei, rl_about,
			rl_xieyi, feedback;
	// 返回
	private ImageButton return14;

	View view;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.more, null);
		rl_problem = (RelativeLayout) view.findViewById(R.id.rl_problem);
		rl_fuwufanwei = (RelativeLayout) view.findViewById(R.id.rl_fuwufanwei);
		rl_about = (RelativeLayout) view.findViewById(R.id.rl_about);
		rl_xieyi = (RelativeLayout) view.findViewById(R.id.rl_xieyi);
		feedback = (RelativeLayout) view.findViewById(R.id.feedback);
		return14 = (ImageButton) view.findViewById(R.id.return22);
		rl_kefu=(RelativeLayout) view.findViewById(R.id.rl_kefu);
		rl_fuwufanwei.setOnClickListener(this);
		rl_about.setOnClickListener(this);
		rl_kefu.setOnClickListener(this);
		rl_problem.setOnClickListener(this);
		rl_xieyi.setOnClickListener(this);
		feedback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inetnt = new Intent(getActivity(), FeeBack.class);
				startActivity(inetnt);

			}
		});

		return14.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
//				Intent intent = new Intent(getActivity(), HomeActivity.class);
				HomeActivity activity=(HomeActivity) getActivity();
				activity.mTabHost.setCurrentTab(0);
			}
		});
		return view;
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_kefu:
			
			 kefu = new kefupopuwind(getActivity());
			kefu.setFocusable(true);
			ColorDrawable dw = new ColorDrawable(0xb0000000);
			kefu.setBackgroundDrawable(dw);
			kefu.setOutsideTouchable(true);
			

			kefu.showAtLocation(getView().findViewById(R.id.mores),
					Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			kefu.update();
			break;

		case  R.id.rl_about:
			Intent  intent=new  Intent(getActivity(), About_Us.class);
			startActivity(intent);
			break;
			
		case  R.id.rl_fuwufanwei:
			Intent  intents=new  Intent(getActivity(), ServeExplain.class);
			startActivity(intents);
			break;
		case  R.id.rl_problem:
			Intent  intents2=new  Intent(getActivity(), Often_Problem.class);
			startActivity(intents2);
			break;
		case  R.id.rl_xieyi:
			Intent  intents3=new  Intent(getActivity(), User_Agreement.class);
			startActivity(intents3);
			break;
		}

	}
	 
	

}
