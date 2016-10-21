package com.streamlet.module.protocol;

import android.content.Context;

import com.streamlet.base.Config;
import com.streamlet.common.util.StringUtils;
import com.streamlet.module.entity.response.FamilyListResponse;
import com.streamlet.module.entity.response.LoginResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by streamlet2 on 2016/10/20.
 *
 * @Description 访问网络管理类
 */
public class UserProtocol extends BaseProtocol{

    /**登录*/
    public static long login(Context context, String tag, Map<String,Object> paramers){
        return sentPostStringRequest(context,tag, StringUtils.generateUrl(Config.LOGIN,paramers),null, LoginResponse.class,null);
    }
    /**注册*/
    public static long register(Context context, String tag, Map<String,Object> paramers){
        return sentPostStringRequest(context,tag, StringUtils.generateUrl(Config.REGISTER,paramers),null, LoginResponse.class,null);
    }

    /**搜索Family列表*/
    public static long familyList(Context context,String tag,String familyName){
        HashMap<String,Object> paramers=new HashMap<>();
        paramers.put("familyName",familyName);
        return sentGetStringRequest(context,tag,StringUtils.generateUrl(Config.FAMILY,paramers),null, FamilyListResponse.class,null);
    }
}
