package com.streamlet.module.net;

import android.content.Context;

import com.android.volley.VolleyError;

/**
 * 所有网络的消息都将经过这里，进行初步筛选，之后才向上派发
 * 
 * @author cwj
 */
class FilterUtil {

	/**
	 * 过滤http接收的消息
	 * 
	 * @param <T>
	 **/
	protected static <T> Boolean filterReceiveHttp(Context context, long flag, String json, T response) {
		return true;
	}

	/**
	 * 过滤接http请求错误
	 * 
	 * @param e
	 * @param flag
	 * @return void
	 */
	protected static Boolean filterReceiveHttpError(Context context, VolleyError e, long flag) {
		return true;
	}

}
