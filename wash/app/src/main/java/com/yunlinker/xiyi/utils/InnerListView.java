package com.yunlinker.xiyi.utils;

import com.yunlinker.xiyi.Adapter.BasketAdapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.ListView;
import android.widget.ScrollView;

public class InnerListView extends ListView {

	ScrollView parentScrollView;

	public ScrollView getParentScrollView() {
		return parentScrollView;
	}

	public void setParentScrollView(ScrollView parentScrollView) {
		this.parentScrollView = parentScrollView;
	}

	// 最大高度
	private int maxHeight;

	public int getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public InnerListView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (maxHeight > -1) {
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight,
					MeasureSpec.AT_MOST);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		System.out.println(getChildAt(0));
	}

	// @Override
	// public boolean onInterceptTouchEvent(MotionEvent ev) {
	// switch (ev.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	// setParentScrollAble(false);
	// break;
	//
	// case MotionEvent.ACTION_MOVE:
	// break;
	// case MotionEvent.ACTION_UP:
	//
	// case MotionEvent.ACTION_CANCEL:
	// setParentScrollAble(true);
	// break;
	// }
	// return super.onInterceptTouchEvent(ev);
	// }
	//
	private void setParentScrollAble(boolean flag) {
		parentScrollView.requestDisallowInterceptTouchEvent(!flag);

	}

}
