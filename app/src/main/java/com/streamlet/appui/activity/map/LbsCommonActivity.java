package com.streamlet.appui.activity.map;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.streamlet.R;
import com.streamlet.appui.base.BaseActivity;
import com.streamlet.module.entity.bean.LBSAddress;

/**
 * Created by streamlet2 on 2016/10/20.
 *
 * @Description  获取经纬度、地理位置工具类
 */
public class LbsCommonActivity extends BaseActivity implements LocationSource,AMapLocationListener,AMap.OnMapClickListener,GeocodeSearch.OnGeocodeSearchListener,AMap.OnCameraChangeListener {

    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private Button button;
    private String latLng;
    private TextView tv_adressName;
    private LatLonPoint latLonPoint = new LatLonPoint(22.81906, 108.397434);
    public static final int RequestCode=1;
    private ImageView img_location;
    private boolean isFirstLocation=true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lbs_common);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();
    }


    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        img_location=(ImageView) findViewById(R.id.img_location);
        tv_adressName=(TextView) findViewById(R.id.tv_adress);
        tv_adressName.setOnClickListener(listener);
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.img_location_now));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.getUiSettings().setZoomControlsEnabled(false);// 设置手动改变比例尺
        // aMap.setMyLocationType()
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        aMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器
        aMap.setOnCameraChangeListener(this);//获取地图中心点
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
				/*mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
				double lat=amapLocation.getLatitude();
				double lng=amapLocation.getLongitude();
				latLonPoint=new LatLonPoint(lat, lng);
				getAddress(latLonPoint);*/
                if(isFirstLocation){
                    Animation anim = AnimationUtils.loadAnimation(activity,
                            R.anim.anim_jump);
                    img_location.startAnimation(anim);
                    isFirstLocation=false;
                }

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
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

    /**
     * 停止定位
     */
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
    public void onMapClick(LatLng arg0) {
    }



    private GeocodeSearch geocoderSearch;
    private String addressName;
    /**
     * 地理编码查询回调
     */
    @Override
    public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
        // TODO Auto-generated method stub

    }


    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }



    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        Log.d("yckx", ""+result.toString()+rCode);

        if (rCode == 0||rCode==1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                addressName = result.getRegeocodeAddress().getFormatAddress()
                        + "";
				/*aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
						AMapUtil.convertToLatLng(latLonPoint), 15));*/
                tv_adressName.setText(addressName);
                tv_adressName.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
            } else {
            }
        } else if (rCode == 27) {
            tv_adressName.setVisibility(View.GONE);
            //Toast.show(getApplicationContext(), "网络错误", 2000);
           showToast("网络错误");
        } else if (rCode == 32) {
            tv_adressName.setVisibility(View.GONE);
            showToast("key错误");
        } else {
            tv_adressName.setVisibility(View.GONE);
            tv_adressName.setVisibility(View.GONE);
            showToast("其他类型错误"+rCode);
        }
    }


    public static final String Latitude="Latitude";
    public static final String Longitude="Longitude";
    public static final String AddressName="addressName";
    private View.OnClickListener listener=new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {

            Intent intent=new Intent();
            LBSAddress lbsAddress=new LBSAddress(latLonPoint.getLatitude(),latLonPoint.getLongitude(),addressName);
            intent.putExtra("json",lbsAddress);
            activity.setResult(BaseActivity.RESULT_OK, intent);
            activity.finish();
        }
    };
    @Override
    protected void initUI() {
        // TODO Auto-generated method stub

    }


    @Override
    protected void initData() {
        // TODO Auto-generated method stub

    }


    @Override
    public String setTag() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCameraChange(CameraPosition arg0) {
        // TODO Auto-generated method stub
        double lat=arg0.target.latitude;
        double lng=arg0.target.longitude;
        latLonPoint=new LatLonPoint(lat, lng);
        //ToastUtil.show(getApplicationContext(), ""+lat+" "+lng, 2000);
        getAddress(latLonPoint);
    }

    @Override
    public void onCameraChangeFinish(CameraPosition arg0) {
        // TODO Auto-generated method stub

    }
}
