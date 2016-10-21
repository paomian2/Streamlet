package com.streamlet.module.net;

import android.content.Context;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.streamlet.base.AppApplication;
import com.streamlet.common.util.LogUtil;
import com.streamlet.common.util.SharedPreferenceUtil;
import com.streamlet.common.util.StringUtils;
import com.streamlet.module.entity.base.CodeResponse;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 通信模块：通信的公共模块，所有的网络消息发送出去，以及接收返回的消息都将经过
 * 1、当需要发送网络请求时，将http的参数进行签名封装之后，请求http发送出去，并且每次请求都将返回生成一个请求标识，作为返回时的筛选标识；
 *
 * @author cwj
 * @ClassName: ConnectorCore
 * @Description: 通信模块
 */
public class ConnectorManage implements ConnectorCallBack {
    private final String TAG = ConnectorManage.class.getSimpleName();
    private static ConnectorManage core;
    private ConnectorCallBack activityCallBack;
    private ConnectorCallBack serviceCallBack;
    private ConnectorCallBack pushCallBack;
    private List<ConnectorCallBack> fragmentCallBacks = new CopyOnWriteArrayList<ConnectorCallBack>();
    private Context context;

    // 常规http连接
    private AtomicLong httpCount = new AtomicLong(0);
    @SuppressWarnings("rawtypes")
    private HashMap<Long, com.android.volley.Request> mapHttp = new HashMap<Long, com.android.volley.Request>();


    public static ConnectorManage getInstance(Context context) {
        if (core == null) {
            core = new ConnectorManage();
        }
        core.context = context;
        return core;
    }

    // 设置服务回调
    public void setServiceCallBack(ConnectorCallBack callBack) {
        this.serviceCallBack = callBack;
    }

    // 设置回调
    public void setActivityCallBack(ConnectorCallBack callBack) {
        this.activityCallBack = callBack;
    }

    // 设置推送回调
    public void setPushCallBack(ConnectorCallBack callBack) {
        this.pushCallBack = callBack;
    }

    // 添加一个回调
    public void addFragmentCallBack(ConnectorCallBack callBack) {
        if (!fragmentCallBacks.contains(callBack))
            fragmentCallBacks.add(callBack);
    }

    // 取消一个回调
    public void removeFragmentCallBack(ConnectorCallBack callBack) {
        if (fragmentCallBacks.contains(callBack))
            fragmentCallBacks.remove(callBack);
    }

    // String请求数据
    @SuppressWarnings({"rawtypes", "unchecked"})
    public synchronized <T> long sentStringHttpRequest(int method, String tag, String url, final Map<String, Object> requestParams,
                                                       final Type type, HttpCallBack httpCallBack) {
        final long flag = httpCount.incrementAndGet();
        String logStr = "sentHttpRequest:" + flag + " " + url;
        if (requestParams == null)
            logStr = logStr + StringUtils.EMPTY;
        else
            logStr = logStr + new Gson().toJson(requestParams);
        logStr = logStr + " token:" + SharedPreferenceUtil.getInstance(AppApplication.getInstance())
                .getString(SharedPreferenceUtil.TOKEN);
        LogUtil.d(TAG, logStr);
        StringRequest request = new StringRequest(context, method, flag, url, tag, requestParams, type, httpCallBack);
        mapHttp.put(flag, request.getRequest());
        return flag;
    }


    // 请求数据成功的回调
    public <T> void onHttpSuccess(long flag, String json, T response) {
        LogUtil.d(TAG, "onHttpSuccess:" + flag + " " + json);
        if (mapHttp.containsKey(flag)) {
            mapHttp.remove(flag);
        }

        if (!FilterUtil.filterReceiveHttp(context, flag, json, response))
            return;

        if (activityCallBack != null) {
            activityCallBack.onHttpSuccess(flag, json, response);
        }

        if (serviceCallBack != null) {
            serviceCallBack.onHttpSuccess(flag, json, response);
        }
        if (pushCallBack != null) {
            pushCallBack.onHttpSuccess(flag, json, response);
        }

        if (fragmentCallBacks.size() > 0) {
            // for (ConnectorCallBack callBack : fragmentCallBacks) {
            // callBack.onHttpSuccess(flag, json, response);
            // }
            for (int i = 0; i < fragmentCallBacks.size(); i++) {

                fragmentCallBacks.get(i).onHttpSuccess(flag, json, response);
            }
        }
    }

    ;

    // 请求数据失败的回调
    @Override
    public void onHttpError(long flag, VolleyError e, CodeResponse errorResponse) {
        if (mapHttp.containsKey(flag)) {
            mapHttp.remove(flag);
        }

        if (!FilterUtil.filterReceiveHttpError(context, e, flag))
            return;

        if (activityCallBack != null)
            activityCallBack.onHttpError(flag, e, errorResponse);

        if (serviceCallBack != null)
            serviceCallBack.onHttpError(flag, e, errorResponse);

        if (pushCallBack != null)
            pushCallBack.onHttpError(flag, e, errorResponse);

        if (fragmentCallBacks.size() > 0) {
            // for (ConnectorCallBack callBack : fragmentCallBacks) {
            // callBack.onHttpError(flag, e);
            // }
            for (int i = 0; i < fragmentCallBacks.size(); i++) {

                fragmentCallBacks.get(i).onHttpError(flag, e, errorResponse);
            }
        }
    }


    // 重置
    public void reset() {
        RequestManager.getInstance(context).release();
        mapHttp.clear();
        core = null;
    }

}


