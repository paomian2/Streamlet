package com.streamlet.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.streamlet.R;
import com.streamlet.common.util.LogUtil;

public class CustomProgressDialog extends Dialog {
	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	private static CustomProgressDialog customProgressDialog = null;

	public static CustomProgressDialog createDialog(Context context) {
		customProgressDialog = new CustomProgressDialog(context, R.style.CustomDialog);
		customProgressDialog.setContentView(R.layout.dialog_loading);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		ProgressWheel progressWheel = (ProgressWheel) customProgressDialog.findViewById(R.id.pg);
		progressWheel.spin();
		progressWheel.setBarWidth(2);
		return customProgressDialog;
	}

	public CustomProgressDialog setTitile(String strTitle) {
		return customProgressDialog;
	}

	public CustomProgressDialog setMessage(String strMessage) {
		TextView tvMsg = (TextView) customProgressDialog.findViewById(R.id.tv_load);
		if (tvMsg != null && strMessage != null) {
			tvMsg.setText(strMessage);
		}
		return customProgressDialog;
	}

	public void dismiss() {
		try {
			super.dismiss();
		} catch (Throwable t) {
			LogUtil.log(t);
		}
	}

}
