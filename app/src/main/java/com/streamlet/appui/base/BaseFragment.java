package com.streamlet.appui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.streamlet.base.AppApplication;
import com.streamlet.common.util.StringUtils;
import com.streamlet.common.util.UIHelper;
import com.streamlet.module.entity.base.CodeResponse;
import com.streamlet.module.net.ConnectorCallBack;
import com.streamlet.module.net.ConnectorManage;
import com.streamlet.module.net.RequestManager;

import java.lang.reflect.Field;

public abstract class BaseFragment extends Fragment implements ConnectorCallBack {
    public AppApplication application;
    public BaseActivity activity;
    protected ConnectorManage connectorManage;
    protected String Tag = StringUtils.EMPTY;
    private Toast toast = null;
    protected LayoutInflater inflater;
    private View contentView;
    private Context context;
    private ViewGroup container;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();
        application = (AppApplication) getActivity().getApplication();
        connectorManage = ConnectorManage.getInstance(activity);
        context = getActivity().getApplicationContext();
        Bundle b = getArguments();
        onGetBundle(b);
        setTag();
    }

    public void onGetBundle(Bundle bundle) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        onCreateView(savedInstanceState);
        if (contentView == null) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        return contentView;
    }

    protected void onCreateView(Bundle savedInstanceState) {
        initUI();
        initData();


    }

    public void startActivityResult(int requestCode, Bundle bundle, Class c) {
        Intent intent = new Intent();
        intent.putExtra("Bundle", bundle);
        intent.setClass(getContext(), c);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onResume() {
        super.onResume();
        connectorManage.addFragmentCallBack(this);
    }

    // Fragment不一样
    @Override
    public void onDestroy() {
        super.onDestroy();
        connectorManage.removeFragmentCallBack(this);
        RequestManager.getInstance(activity).getmRequestQueue().cancelAll(Tag);
    }

    public abstract void initUI();

    public abstract void initData();

    public abstract String setTag();

    @Override
    public <T> void onHttpSuccess(long flag, String json, T response) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onHttpError(long flag, VolleyError e, CodeResponse errorResponse) {
        // TODO Auto-generated method stub

    }

    /**
     * 默认时间LENGTH_LONG
     */
    public void showToast(String msg) {
        UIHelper.showToast(activity, toast, msg);
    }

    /**
     * @param msg
     * @param length 显示时间
     */
    protected void showToast(String msg, int length) {
        UIHelper.showToast(activity, toast, msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentView = null;
        container = null;
        inflater = null;
    }

    public Context getApplicationContext() {
        return context;
    }

    public void setContentView(int layoutResID) {
        setContentView((ViewGroup) inflater.inflate(layoutResID, container, false));
    }

    public void setContentView(View view) {
        contentView = view;
    }

    public View getContentView() {
        return contentView;
    }

    public View findViewById(int id) {
        if (contentView != null)
            return contentView.findViewById(id);
        return null;
    }

    // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Description: 请求完成统一处理方法,非强制.<br/><br/>
     * Author: Create by Yu.Yao on 2016/9/26.<br/><br/>
     */
    public void onComplete() {
    }
}
