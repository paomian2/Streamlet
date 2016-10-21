package com.streamlet.module.net;

import android.content.Context;
import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.streamlet.common.util.LogUtil;
import com.streamlet.common.util.StringUtils;
import com.streamlet.module.entity.base.CodeResponse;

import java.lang.reflect.Type;
import java.util.Map;

public class StringRequest<T> extends Request implements Listener<String>, ErrorListener {
    private final String TAG = StringRequest.class.getSimpleName();
    private int method = Method.POST;
    private Type type;
    private ConnectorManage conManager;
    private com.android.volley.toolbox.StringRequest request;
    private Map<String, Object> requestParam;
    private HttpCallBack httpCallBack;
    private long flag;
    private Context context;

    public StringRequest(Context context, int method, long flag, String url, String tag, Map<String, Object> requestParam, Type type,
                         HttpCallBack httpCallBack) {
        this.method = method;
        init(context, flag, url, tag, requestParam, type, httpCallBack);
    }

    public com.android.volley.toolbox.StringRequest getRequest() {
        return request;
    }

    private void init(Context context, long flag, String url, String tag, Map<String, Object> requestParam, Type type,
                      HttpCallBack httpCallBack) {
        this.type = type;
        this.httpCallBack = httpCallBack;
        this.flag = flag;
        this.context = context;
        conManager = ConnectorManage.getInstance(context);
        request = getRequest(url, requestParam);
        request.setRetryPolicy(new DefaultRetryPolicy());
        request.setTag(tag);
        this.requestParam = requestParam;
        RequestManager.getInstance(context).getmRequestQueue().add(request);

    }

    private com.android.volley.toolbox.StringRequest getRequest(final String url, final Map<String, Object> requestParam) {
        switch (method) {
            case Method.POST:
            case Method.PUT:
            default:
                return new com.android.volley.toolbox.StringRequest(method, url, this, this) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return getRequestHeader();
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        // TODO Auto-generated method stub
//                        LogUtil.e(TAG, url + new Gson().toJson(requestParam) + " token "
//                                + SharedPreferenceUtil.getInstance(AppApplication.getInstance()).getString(SharedPreferenceUtil.TOKEN));
                        return new Gson().toJson(requestParam).getBytes();
                    }
                };
            case Method.GET:
            case Method.DELETE:
                final Uri.Builder uriBuilder = Uri.parse(url).buildUpon();
                if (requestParam != null) {
                    for (String key : requestParam.keySet()) {
                        uriBuilder.appendQueryParameter(key, requestParam.get(key) + "");
                    }
                }
                return new com.android.volley.toolbox.StringRequest(method, uriBuilder.toString(), this, this) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        LogUtil.e(TAG, uriBuilder.toString() + " token "
//                                + SharedPreferenceUtil.getInstance(AppApplication.getInstance()).getString(SharedPreferenceUtil.TOKEN));
                        return getRequestHeader();
                    }
                };
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        CodeResponse errorResponse = null;
        try {
            if (error != null && error.networkResponse != null && error.networkResponse.data != null
                    && !StringUtils.isEmpty(error.networkResponse.data.toString())) {
//                LogUtil.e(TAG, "onHttpError:" + flag + new String(error.networkResponse.data).toString());
                LogUtil.e(TAG, "onHttpError:" + flag + " " + new String(error.networkResponse.data));
                errorResponse = new Gson().fromJson(new String(error.networkResponse.data), CodeResponse.class);
            }
        } catch (Exception e) {
            // TODO: handle exception
            ResponseException exception = new ResponseException("When after call URL successfully, exception occurred.", e,
                    request.getUrl(), requestParam, type.getClass(), errorResponse != null ? errorResponse.toString() : "");
            exception.printStackTrace();
        } finally {
            conManager.onHttpError(flag, error, errorResponse);
            if (httpCallBack != null) {
                httpCallBack.onHttpError(flag, error, errorResponse);
            }
        }

    }

    @Override
    public void onResponse(String response) {
        T responseEntity = null;
//        LogUtil.e(TAG, "onHttpSuccess:" + flag + response);
        try {
            if (!StringUtils.isEmpty(response)) {
                responseEntity = new Gson().fromJson(response, type);
            }
        } catch (Exception e) {
            ResponseException exception = new ResponseException("When after call URL successfully, exception occurred.", e,
                    request.getUrl(), requestParam, type != null ? type.getClass() : null, response != null ? response.toString() : null);
            exception.printStackTrace();
        } finally {
            conManager.onHttpSuccess(flag, response, responseEntity);
            if (httpCallBack != null)
                httpCallBack.onHttpSuccess(response, responseEntity);
        }

    }
}
