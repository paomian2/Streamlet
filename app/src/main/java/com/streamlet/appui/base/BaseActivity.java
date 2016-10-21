package com.streamlet.appui.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.streamlet.R;
import com.streamlet.base.AppActivityManager;
import com.streamlet.base.AppApplication;
import com.streamlet.common.util.StringUtils;
import com.streamlet.common.util.SystemBarTintManager;
import com.streamlet.common.util.UIHelper;
import com.streamlet.module.entity.base.CodeResponse;
import com.streamlet.module.net.ConnectorCallBack;
import com.streamlet.module.net.ConnectorManage;
import com.streamlet.module.net.RequestManager;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;

/**
 * 所有activity的基类
 *
 * @author cwj
 */
public abstract class BaseActivity extends FragmentActivity implements UncaughtExceptionHandler, ConnectorCallBack {
    protected AppApplication application;
    protected ConnectorManage connectorManage;
    protected String Tag = StringUtils.EMPTY;
    protected BaseActivity activity;
    private Toast toast = null;

    public static final int ACTION_SET_PASSWORD = 1;
    public static final int ACTION_RESET_PASSWORD = 2;

    public static final String ACTION_PAY_RESULT = "PayResult";
    public static final int RESULT_PAY_SUCCESS = 1008;
    public static final int RESULT_PAY_FAIL = 1007;
    public static final int RESULT_PAY_CANCAL = 1006;

    public static final int REQUEST_PAY_CODE = 666;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (AppApplication) getApplication();
        activity = this;
        AppActivityManager.getInstance().addActivity(this);
        setTag();
        Bundle b = getIntent().getBundleExtra("Bundle");
        onGetBundle(b);
        //setPhoneActionbarBackground();
    }

    public void onGetBundle(Bundle bundle) {

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setConnectorListener();
//        MobclickAgent.onResume(this);

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
//        MobclickAgent.onPause(this);

    }

    @Override
    public void finish() {
        super.finish();
        AppActivityManager.getInstance().removeActivity(this);
        RequestManager.getInstance(this).getmRequestQueue().cancelAll(Tag);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //IStudyActivityManager.getInstance().removeActivity(this);
        AppActivityManager.getInstance().cleanActivity();
        RequestManager.getInstance(this).getmRequestQueue().cancelAll(Tag);
    }

    protected abstract void initUI();

    protected abstract void initData();

    public abstract String setTag();

    // 创建网络连接，并设置当前的activity为接收消息的对象
    private void setConnectorListener() {
        connectorManage = ConnectorManage.getInstance(this);
        connectorManage.setActivityCallBack(this);
    }

    /**
     * 默认时间LENGTH_LONG
     */
    public void showToast(String msg) {
        UIHelper.showToast(this, toast, msg);
    }

    /**
     * @param msg
     * @param length 显示时间
     */
    public void showToast(String msg, int length) {
        UIHelper.showToast(this, toast, msg, length);
    }

    /**
     * 设置手机背景跟actionbar一样的颜色
     */
    private void setPhoneActionbarBackground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.common_txt_yellow);//通知栏所需颜色
        }
    }

    public void showNullDataTips(List list, TextView tvTxt, String tips) {
        if (list.size() == 0) {
            tvTxt.setVisibility(View.VISIBLE);
            tvTxt.setText(tips);
        } else {
            tvTxt.setVisibility(View.GONE);
        }
    }

    @Override
    public <T> void onHttpSuccess(long flag, String json, T response) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onHttpError(long flag, VolleyError e, CodeResponse errorResponse) {
        // TODO Auto-generated method stub

    }

    public void onComplete() {
    }


}
