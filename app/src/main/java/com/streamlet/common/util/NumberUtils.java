package com.streamlet.common.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * @Description  专门对数据进行数量
 * @Author Created by LinXZ on 2016/8/31 00:08.
 */
public class NumberUtils {




    /***
     * double 数据保存小数操作
     *
     * */
   public static double scaleNumber(double targetNumber,int scale){
       BigDecimal b1 = new BigDecimal(targetNumber);
       return b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
   }


    /**
     *
     * double 数据保留小数操作
     * */
    public static String sacleNumber2(double targetNumber, int scale ){
        NumberFormat ddf1 = NumberFormat.getNumberInstance();
        ddf1.setMaximumFractionDigits(scale);
       return ddf1.format(targetNumber);
    }

    /**格式化距离*/
    public static String formatNumberofDistance(float distance){
        if(distance<1000){
            return sacleNumber2(distance,1)+"m";
        }else{
            return sacleNumber2(distance/1000,2)+"km";
        }
    }
}
