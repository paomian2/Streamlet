package com.streamlet.module.net;

import com.android.volley.VolleyError;
import com.streamlet.module.entity.base.CodeResponse;

/**
 * http接口回调，方面及时使用回调数据
 * 
 * @author cwj
 * 
 */
public interface HttpCallBack {

	public <T> void onHttpSuccess(String response, T responseEntity);

	public void onHttpError(long flag, VolleyError e, CodeResponse errorResponse);

}

