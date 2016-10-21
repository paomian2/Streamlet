package com.streamlet.module.net;

import com.android.volley.VolleyError;
import com.streamlet.module.entity.base.CodeResponse;

/**
 * 网络通信和数据库回调接口
 * 
 * @author Administrator
 * 
 */
public interface ConnectorCallBack {
	// 普通http 同时返回json和封装的实体类
	public <T> void onHttpSuccess(long flag, String json, T response);

	public void onHttpError(long flag, VolleyError e, CodeResponse errorResponse);

}

