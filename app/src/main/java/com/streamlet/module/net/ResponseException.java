package com.streamlet.module.net;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jay.Chen on 2015/6/11.
 */
public class ResponseException extends Exception {

	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");

	private String time;
	private String url;
	private Object requestParam;
	private Class responseClass;
	private String json;

	public ResponseException(String message, Throwable cause, String url, Object requestParam, Class responseClass, String json) {
		super(message, cause);
		this.time = format.format(new Date());
		this.url = url;
		this.requestParam = requestParam;
		this.responseClass = responseClass;
		this.json = json;
	}

	@Override
	public String getMessage() {
		StringBuffer buffer = new StringBuffer(time);
		buffer.append(super.getMessage());

		buffer.append(" URL : ").append(url).append(". ");

		if (requestParam != null) {
			buffer.append(" Params : ");
			buffer.append(new Gson().toJson(requestParam));
			buffer.append(" End of Params. ");
		}

		if (responseClass != null) {
			buffer.append(" Class : ").append(responseClass.getSimpleName()).append(". ");
		}

		buffer.append("JSON : ").append(json).append(" End of JSON.");

		return buffer.toString();
	}
}
