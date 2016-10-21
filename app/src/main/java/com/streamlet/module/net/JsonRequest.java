package com.streamlet.module.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.streamlet.common.util.LogUtil;
import com.streamlet.common.util.StringUtils;
import com.streamlet.module.entity.base.CodeResponse;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonRequest<T> extends Request implements Listener<JSONObject>, ErrorListener {
	private final String TAG = JsonRequest.class.getSimpleName();
	private int method = Method.POST;
	private Type type;
	private ConnectorManage conManager;
	private JsonObjectRequest request;
	private Object requestParam;
	private HttpCallBack httpCallBack;
	private long flag;
	private Context context;

	public JsonRequest(Context context, int method, long flag, String url, String tag, Object requestParam, Type type,
					   HttpCallBack httpCallBack) {
		this.method = method;
		this.requestParam = requestParam;
		init(context, flag, url, tag, requestParam, type, httpCallBack);

	}

	public JsonObjectRequest getRequest() {
		return request;
	}

	private void init(Context context, long flag, String url, String tag, Object param, Type type, HttpCallBack httpCallBack) {
		this.type = type;
		this.httpCallBack = httpCallBack;
		this.flag = flag;
		this.context = context;
		conManager = ConnectorManage.getInstance(context);
		request = getRequest(url, param);
		request.setTag(tag);
		request.setRetryPolicy(new DefaultRetryPolicy());
		RequestManager.getInstance(context).getmRequestQueue().add(request);

	}

	private JsonObjectRequest getRequest(String url, final Object param) {

		try {
			return new JsonObjectRequest(method, url, new JSONObject(new Gson().toJson(param)), this, this) {
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					return getRequestHeader();
				}

			};
		} catch (Exception e) {
			ResponseException exception = new ResponseException("When get JsonObjectRequest by getRequest, exception occurred.", e,
					request.getUrl(), requestParam, type.getClass(), "");
			exception.printStackTrace();
			LogUtil.e(TAG, exception.getMessage());
			return null;
		}
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		if (error != null && error.networkResponse != null && error.networkResponse.data != null
				&& !StringUtils.isEmpty(error.networkResponse.data.toString())) {
			LogUtil.e(TAG, "onHttpError:" + flag + new String(error.networkResponse.data).toString());
			CodeResponse errorResponse = new Gson().fromJson(new String(error.networkResponse.data).toString(), CodeResponse.class);
			conManager.onHttpError(flag, error, errorResponse);
			if (httpCallBack != null) {
				httpCallBack.onHttpError(flag, error, errorResponse);
			}
		}

	}

	@Override
	public void onResponse(JSONObject response) {
		if (response != null) {
			try {
				LogUtil.e(TAG, "onHttpSuccess:" + flag + response.toString());
				T responseEntity = new Gson().fromJson(response.toString(), type);
				conManager.onHttpSuccess(flag, response.toString(), responseEntity);
				if (httpCallBack != null)
					httpCallBack.onHttpSuccess(response.toString(), responseEntity);
			} catch (Exception e) {
				ResponseException exception = new ResponseException("When after call URL successfully, exception occurred.", e,
						request.getUrl(), requestParam, type.getClass(), response.toString());
				exception.printStackTrace();
				LogUtil.e(TAG, exception.getMessage());
			}
		}
	}

}

