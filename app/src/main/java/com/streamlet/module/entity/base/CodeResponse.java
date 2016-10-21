package com.streamlet.module.entity.base;


import com.streamlet.common.util.StringUtils;

import java.io.Serializable;

/**
 * @Description 解释基础
 * @author Created by qinxianyuzou on 2014-8-21.
 */
public class CodeResponse implements Serializable {
	
	//Unauthorized

	/** 序列号 */
	private static final long serialVersionUID = 6945319206585873015L;

	/** 状态编码 */
	protected String code= StringUtils.EMPTY;
	/** 介绍 */
	protected String desc= StringUtils.EMPTY;

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
	 * @see #desc
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @Description
	 * @author Created by qinxianyuzou on 2014-8-21.
	 * @see #desc
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "BaseMsg [code=" + code + ", desc=" + desc + "]";
	}

}
