package com.streamlet.module.entity.base;


import com.streamlet.common.util.StringUtils;

import java.io.Serializable;

/**
 * @Description 解释基础
 * @author Created by qinxianyuzou on 2014-8-21.
 */
public class BaseResponse implements Serializable {

	/** 序列号 */
	private static final long serialVersionUID = 6945319206585873015L;

	/** 状态编码 */
	protected String code= StringUtils.EMPTY;
	/** 介绍 */
	protected String msg= StringUtils.EMPTY;
	/**网络请求状态*/
	protected String status=StringUtils.EMPTY;

	/**
	 * @Description
	 * @author Created by wj on 2014-8-21.
	 * @see #code
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @Description
	 * @author Created by qinxianyuzou on 2014-8-21.
	 * @see #code
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @Description
	 * @author Created by qinxianyuzou on 2014-8-21.
	 * @see #msg
	 * @return the desc
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @Description
	 * @author Created by qinxianyuzou on 2014-8-21.
	 * @see #msg
	 * @param msg
	 *            the desc to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BaseMsg [code=" + code + ", desc=" + msg + "]";
	}

	public BaseResponse(){}

	public BaseResponse(String code, String msg, String status) {
		this.code = code;
		this.msg = msg;
		this.status = status;
	}
}
