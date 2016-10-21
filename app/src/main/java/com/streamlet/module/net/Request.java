package com.streamlet.module.net;

import com.streamlet.base.AppApplication;
import com.streamlet.common.util.SharedPreferenceUtil;
import com.streamlet.common.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Request {

	// 连接超时时间
	public final static int TIME_OUT = 1200 * 1000;

	// 重试次数
	public final static int RETRY_COUNT = 0;

	protected Map<String, String> getRequestHeader() {
		Map<String, String> headParams = new HashMap<String, String>();
		headParams.put("content-type", "application/json");
		headParams.put("charset", "UTF-8");
		String token = SharedPreferenceUtil.getInstance(AppApplication.getInstance()).getString(SharedPreferenceUtil.TOKEN);
		if(!StringUtils.isEmpty(token)){
			headParams.put("token",token);
		}
		headParams.put("client","android");
		return headParams;
	}
}
