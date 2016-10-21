package com.streamlet.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.streamlet.module.entity.bean.User;

public class SharedPreferenceUtil {
    private static SharedPreferenceUtil sharedPreferenceUtil;

    private static SharedPreferences sharedPreferences;//用户资料
    private static SharedPreferences sharedPreferences_CompanyApply;//商家申请入驻

    private final static String KEY = "dbgs_sharedpreferences";
    public final static String GUIDE = "guide"; // 引导页

    public final static String IMEI = "imei"; // 设备唯一码

    public final static String TOKEN = "token"; // 用户token
    public final static String SETMSG = "setmsg"; // 用户消息设置缓存

    public final static String IMTOKEN = "imtoken"; // 用户imtoken
    public final static String XGTOKEN = "xgtoken"; // 用户xgtoken
    public final static String LBSSearchResponse = "LBSSearchResponse";

    public final static String KEY_PUSH = "Key_push";

    public final static String COMPANY_APPLY = "company_apply";

    public final static String SEARCH_RECORD = "search_cord";//商家搜索记录

    /**
     * 搜索页面记录。 <br/><br/>Author: Create by Yu.Yao on 2016/9/26。
     */
    public final static String SEARCH_HISTORY = "search_history";

    private SharedPreferenceUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        sharedPreferences_CompanyApply = context.getSharedPreferences(COMPANY_APPLY, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtil getInstance(Context context) {
        if (sharedPreferenceUtil == null) {
            sharedPreferenceUtil = new SharedPreferenceUtil(context);
        }
        return sharedPreferenceUtil;
    }

    /**
     * 设置String类型值
     *
     * @param key
     * @param value
     */
    public void putString(String key, String value) {
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 设置long类型值
     *
     * @param key
     * @param value
     */
    public void putLong(String key, long value) {
        Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 设置int类型值
     *
     * @param key
     * @param value
     */
    public void putInt(String key, int value) {
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 设置Boolean类型值
     *
     * @param key
     * @param value
     */
    public void putBoolean(String key, boolean value) {
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 设置Float类型值
     *
     * @param key
     * @param value
     */
    public void putFloat(String key, float value) {
        Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * 读取String类型值，默认为""
     *
     * @param key
     */
    public String getString(String key) {
        return sharedPreferences == null ? "" : sharedPreferences.getString(key, "");
    }

    /**
     * 读取boolean类型值，默认为false;
     *
     * @param key
     * @return
     */
    public boolean getBoolean(String key, boolean deafultValue) {
        return sharedPreferences.getBoolean(key, deafultValue);
    }

    /**
     * 读取int类型值，默认为0
     *
     * @param key
     * @return
     */
    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    /**
     * 读取long类型值，默认为0
     *
     * @param key
     * @return
     */
    public long getLong(String key) {
        return sharedPreferences.getLong(key, 0);
    }

    /**
     * 读取float类型值，默认为0
     *
     * @param key
     * @return
     */
    public float getFloat(String key) {
        return sharedPreferences.getFloat(key, 0);
    }

    /**
     * 判断是否存在此字段
     */
    public boolean has(String key) {
        return sharedPreferences.contains(key);
    }

    // 移除某个字段
    public void remove(String key) {
        Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    private final static String USER="user";
    /**保存当前用户信息*/
    public  void saveUser(User user){
        if (user!=null){
            putString(USER,new Gson().toJson(user));
            String token=user.getToken();
            putString(TOKEN,token);
        }
    }

    /**获取当前用户信息*/
    public User getUser(){
        String userJSON=getString(USER);
        if (!StringUtils.isEmpty(userJSON))
            return new Gson().fromJson(userJSON,User.class);
        else
            return null;
    }

    public static boolean isLogin(Context context) {
        String token = SharedPreferenceUtil.getInstance(context).getString(TOKEN);
        if (StringUtils.isEmpty(token))
            return false;
        else
            return true;
    }


    /**
     * 申请成功之后清空表单
     */
    public void charForm() {
        Editor editor = sharedPreferences_CompanyApply.edit();
        editor.putString("CompanyInfo", "");
        editor.commit();
    }

    public void cleanUserCookie() {
        Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

}

