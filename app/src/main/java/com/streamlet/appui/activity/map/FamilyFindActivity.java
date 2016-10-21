package com.streamlet.appui.activity.map;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.streamlet.R;
import com.streamlet.appui.base.BaseActivity;
import com.streamlet.common.util.LogUtil;

/**
 * Created by streamlet2 on 2016/10/20.
 *
 * @Description 显示附件Family的类
 */
public class FamilyFindActivity extends BaseActivity implements LocationSource,AMapLocationListener {
    //高德地图
    private MapView mapView;
    private AMap aMap;
    private UiSettings mUiSettings;
    //定位
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private Bundle savedInstanceState;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.savedInstanceState=savedInstanceState;
        setContentView(R.layout.fragment_home);
        initUI();
        initData();
    }

    @Override
    protected void initUI() {
        //map
        mapView=(MapView)findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap=mapView.getMap();
        setUpLocationMap();
    }


    /**
     * 设置一些amap的属性
     */
    private void setUpLocationMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.img_location_now));// 设置小蓝点的图标location_marker
        //myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        //myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(0);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        //AMap.moveCamera(CameraUpdateFactory.zoomTo(zoom))
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        // aMap.setMyLocationType()
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setScaleControlsEnabled(true);//比例尺显示
        mUiSettings.setZoomControlsEnabled(false);//设置手动改变比例尺
        mUiSettings.setCompassEnabled(true);//设置指南针
    }

    @Override
    protected void initData() {
        // TODO Auto-generated method stub

    }

    @Override
    public String setTag() {
        // TODO Auto-generated method stub
        return this.getClass().getSimpleName();
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        startThisLocation();
    }


    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                LogUtil.d("yckx", amapLocation.toString()+"");
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                LogUtil.e("yckx", errText);
            }
        }
    }


    /**启动定位*/
    private void startThisLocation(){
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**停止注销定位*/
    private void stopDestoryLocation(){
        mlocationClient.stopLocation();//停止定位
        mlocationClient.onDestroy();//销毁定位客户端。
    }

    //地图生命周期
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if(mapView!=null){
            if(aMap!=null){
                aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
            }
            mapView.onResume();
        }

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        if(mapView!=null){
            mapView.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if(mapView!=null){
            mapView.onPause();
        }
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if(mapView!=null){
            mapView.onDestroy();
        }
    }

    public void render(Marker marker, View view) {
		/*String title = marker.getTitle();
		TextView titleUi = ((TextView) view.findViewById(R.id.tv_businessName));
		if (title != null) {
			SpannableString titleText = new SpannableString(title);
			titleText.setSpan(new ForegroundColorSpan(Color.RED), 0,
					titleText.length(), 0);
			titleUi.setTextSize(13);
			titleUi.setText(titleText);

		} else {
			titleUi.setText("");
		}*/
    }

}
