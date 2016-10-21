package com.streamlet.base;

import com.streamlet.common.util.PhoneUtil;
import com.streamlet.module.entity.config.Environment_Enum;

/**
 * 常用常量，设置<BR>
 */
public class Config {
	// 是否开发模式
	public static boolean DEBUG = PhoneUtil.isApkDebugable();
	//分享链接//ShareURL
	public  static String ShareURL="http://sharetest.youchekuaixi.com/";
	//http://share.youchekuaixi.com/(正式环境)
    public static String wwtAPPDownload="http://yckx.lhcpig.com/app-download.html";
	//http://share.youchekuaixi.com/app-download.html（正式环境）


	public static String ABOUT_YCKX="http://youchekuaixi.com/about.html";
	//注册协议
	public static String REGISTER_AGREEMENT="http://app.yckx.com/apply-agreement.html";
	//交易协议
	public static String TRAD_GREEMENT="http://app.yckx.com/trade-agreement.html";
	//商家申请协议
	public static String COMPANY_APPLY_AGREEMENT="http://app.yckx.com/apply-agreement.html";
	/** 当前环境，修改这里的枚举值改变环境 */

	public final static Environment_Enum CURRENT_ENVIRONMENT = Environment_Enum.OFFICIAL;
	/** 接口地址 */
	public static String TEST_BASE_URL = "";
	/** 图片地址 */
	public static String TEST_IMG_BASE_URL = "";
	/** 融云key */
	public static String TEST_RY_KEY = "";

	/**
	 * @Description
	 * @author Created by qinxianyuzou on 2014-11-11.
	 * @see #TEST_BASE_URL
	 * @return the TEST_BASE_URL
	 */
	public static String getTEST_BASE_URL() {
		switch (CURRENT_ENVIRONMENT) {
		case OFFICIAL:
			TEST_BASE_URL = "http://192.168.1.11:8080/Streamlet";
			TEST_IMG_BASE_URL = "http://192.168.1.11:8080/Streamlet";
			break;
		case TEST:
			TEST_BASE_URL = "http://192.168.1.11:8080/Streamlet";
			TEST_IMG_BASE_URL = "http://192.168.1.11:8080/Streamlet";
			break;
		default:
			break;
		}
		//根据不同环境动态配置
		/*if (!StringUtils.isBlank(BuildConfig.TEST_BASE_URL)) {
			TEST_BASE_URL = BuildConfig.TEST_BASE_URL;
		}

		if (!StringUtils.isBlank(BuildConfig.TEST_IMG_BASE_URL)) {
			TEST_IMG_BASE_URL = BuildConfig.TEST_IMG_BASE_URL;
		}

		if (!StringUtils.isBlank(BuildConfig.ShareURL)) {
			ShareURL = BuildConfig.ShareURL;
		}

		if (!StringUtils.isBlank(BuildConfig.TEST_RY_KEY)) {
			TEST_RY_KEY = BuildConfig.TEST_RY_KEY;
		}*/
		return TEST_BASE_URL;
	}

	public final static String LOGIN=getTEST_BASE_URL()+"/login";
	public final static String REGISTER=getTEST_BASE_URL()+"/register";
	//Family
	public final static String FAMILY=getTEST_BASE_URL()+"/family";

}

