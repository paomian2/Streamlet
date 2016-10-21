package com.streamlet.module.protocol;

import android.content.Context;

import com.streamlet.module.net.ConnectorManage;
import com.streamlet.module.net.HttpCallBack;
import com.android.volley.Request.Method;
import java.lang.reflect.Type;
import java.util.Map;

public class BaseProtocol {

    /**
     * String_post请求
     * @param context
     * @param tag
     * @param url
     * @param params
     * @param responseClass
     * @param httpCallBack
     * @return
     */
	protected static long sentPostStringRequest(Context context, String tag, String url, Map<String, Object> params,
												Type type, HttpCallBack httpCallBack) {
		return ConnectorManage.getInstance(context).sentStringHttpRequest(Method.POST, tag, url, params, type,
                httpCallBack);
	}

    /**
     * String_get请求
     * @param <T>
     * @param context
     * @param tag
     * @param url
     * @param params
     * @param responseClass
     * @param httpCallBack
     * @return
     */
	protected static <T> long sentGetStringRequest(Context context, String tag, String url, Map<String, Object> params,
												   Type type, HttpCallBack httpCallBack) {
		return ConnectorManage.getInstance(context).sentStringHttpRequest(Method.GET, tag, url, params, type,
				httpCallBack);
	}

    /**
     * String delete请求
     * @param context
     * @param tag
     * @param url
     * @param params
     * @param responseClass
     * @param httpCallBack
     * @return
     */
	protected static long sentDeleteStringRequest(Context context, String tag, String url, Map<String, Object> params,
												  Type type, HttpCallBack httpCallBack) {
		return ConnectorManage.getInstance(context).sentStringHttpRequest(Method.DELETE, tag, url, params, type,
				httpCallBack);
	}
	
	/**
     * String put请求
     * @param context
     * @param tag
     * @param url
     * @param params
     * @param responseClass
     * @param httpCallBack
     * @return
     */
	protected static long sentPutStringRequest(Context context, String tag, String url, Map<String, Object> params,
											   Type type, HttpCallBack httpCallBack) {
		return ConnectorManage.getInstance(context).sentStringHttpRequest(Method.PUT, tag, url, params, type,
				httpCallBack); 
	}
}
