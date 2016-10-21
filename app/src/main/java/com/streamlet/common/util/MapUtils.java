package com.streamlet.common.util;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.streamlet.appui.base.BaseActivity;

/**
 * Created by streamlet2 on 2016/10/21.
 *
 * @Description 高德地图工具类
 */
public class MapUtils  implements AMapLocationListener {

    private BaseActivity activity;
    private AMapLocationClientOption mLocationOption;
    private AMapLocationClient aMapLocationClient;

    private static MapUtils instance;

    private MapUtils(BaseActivity activity) {
        this.activity = activity;
        initLocation();
    }

    public static MapUtils getInstance(BaseActivity activity) {
        if (instance == null) {
            synchronized (MapUtils.class){
                if (instance==null)
                    instance=new MapUtils(activity);
            }
        }
        return instance;
    }

    /**
     * 配置定位信息
     */
    private void initLocation() {
        mLocationOption = new AMapLocationClientOption();
        //初始化定位
        aMapLocationClient = new AMapLocationClient(activity);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位参数
        aMapLocationClient.setLocationOption(mLocationOption);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置定位监听
        aMapLocationClient.setLocationListener(this);
    }

    //定位成功
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            if (mLocationCallBack != null)
                mLocationCallBack.onLocationSuccess(aMapLocation);
        } else {
            mLocationCallBack.onLocationFailed(aMapLocation == null ? "定位失败：未知错误" : "定位失败" + aMapLocation.getErrorCode() + ":" + aMapLocation.getErrorInfo());
        }
        aMapLocationClient.stopLocation();
    }

    /**启动定位，每次启动只定位一次。再次定位需要重新调用*/
    public void startLocation(){
        if (aMapLocationClient == null) {
            initLocation();
        }
        aMapLocationClient.startLocation();
    }

    /**定位结果回调接口*/
    public interface LocationCallBack {
        public void onLocationSuccess(AMapLocation aMapLocation);

        public void onLocationFailed(String errorCode);
    }

    private LocationCallBack mLocationCallBack;

    public void setLocationCallBack(LocationCallBack mLocationCallBack) {
        this.mLocationCallBack = mLocationCallBack;
    }

    /**根据经纬度计算两点的距离*/
    public float caculateDistance(LatLng startLatlng,LatLng endLatlng){
        return AMapUtils.calculateLineDistance(startLatlng,endLatlng);
    }
}
