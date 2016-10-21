package com.streamlet.common.util;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.streamlet.appui.base.BaseActivity;

public class PopupHelper {

	public enum PopGravity {
		BOTTOM_RIGHT, BOTTOM, LEFT, CENTER, TOP, TOP_CENTER;
	}

	public enum PopStyle {
		MATCH_PARENT, WRAP_CONTENT, MATCH_WIDTH
	}

	public static PopupWindow newBasicPopupWindow(Context context, PopStyle popStyle) {
		final PopupWindow window = new PopupWindow(context);

		// when a touch even happens outside of the window
		// make the window go away
		window.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					window.dismiss();
					return true;
				}
				return false;
			}
		});

		if (popStyle == PopStyle.MATCH_PARENT) {
			window.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
			window.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
		} else if (popStyle == PopStyle.MATCH_WIDTH) {
			window.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
			window.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		} else {
			window.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
			window.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		}
		window.setTouchable(true);
		window.setFocusable(true);
		window.setOutsideTouchable(true);
		window.setBackgroundDrawable(new BitmapDrawable());
		return window;
	}

	public static void showLocationPop(PopupWindow window, View anchor, PopGravity gravity) {
		int paddingTop = UIHelper.dip2px(65);
		if (gravity == PopGravity.TOP_CENTER) {
			window.showAtLocation(anchor, Gravity.TOP | Gravity.CENTER, 0, paddingTop);
		} else if (gravity == PopGravity.CENTER) {
			window.showAtLocation(anchor, Gravity.CENTER, 0, 0);
		} else if (gravity == PopGravity.TOP) {
			window.showAtLocation(anchor, Gravity.TOP, 0, 0);
		} else if (gravity == PopGravity.BOTTOM_RIGHT) {
			window.showAtLocation(anchor, Gravity.BOTTOM | Gravity.RIGHT, 0, 0);
		} else if (gravity == PopGravity.BOTTOM) {
			window.showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
		}
	}



	public static void setBackGroundlpha(BaseActivity activity,PopupWindow window){
		backgroundAlpha(activity,0.4f);
		window.setOnDismissListener(new PoponDismissListener(activity));
	}

	/**
	 * 设置添加屏幕的背景透明度
	 * @param bgAlpha
	 */
	private static void backgroundAlpha(BaseActivity activity, float bgAlpha)
	{
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.alpha = bgAlpha; //0.0-1.0
		activity.getWindow().setAttributes(lp);
	}

	/**
	 * 弹出的popWin关闭的事件，主要是为了将背景透明度改回来
	 *
	 */
	static class PoponDismissListener implements PopupWindow.OnDismissListener{
		BaseActivity activity;
		public PoponDismissListener(BaseActivity activity){
           this.activity=activity;
		}

		@Override
		public void onDismiss() {
			// TODO Auto-generated method stub
			backgroundAlpha(activity,1f);
		}

	}

}
