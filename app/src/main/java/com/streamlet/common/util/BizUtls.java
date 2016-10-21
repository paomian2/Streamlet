package com.streamlet.common.util;

/**
 * @Description  常用的逻辑处理
 * @Author Created by Administrator on 2016/8/29 00:08.
 */
public class BizUtls {

    private static  BizUtls bizUtls;
    private BizUtls(){}
    public BizUtls getInstance(){
        if (bizUtls==null)
            bizUtls=new BizUtls();
        return bizUtls;
    }



}
