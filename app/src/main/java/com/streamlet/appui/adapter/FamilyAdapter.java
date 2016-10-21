package com.streamlet.appui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;
import com.streamlet.R;
import com.streamlet.appui.base.BaseActivity;
import com.streamlet.appui.base.BaseAdapter2;
import com.streamlet.common.util.MapUtils;
import com.streamlet.common.util.NumberUtils;
import com.streamlet.common.util.UIHelper;
import com.streamlet.module.entity.bean.Family;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by streamlet2 on 2016/10/21.
 *
 * @Description 搜索Family数据
 */
public class FamilyAdapter extends BaseAdapter2 {
    private BaseActivity mActivity;
    private LatLng minelatlng;
    /**定位成功*/
    private static final int LOCATION_SUCCESS=1001;
    /**定位失败*/
    private static final int LOCATION_FAIL=1002;
    private int flag;

    public FamilyAdapter(Context context) {
        super(context);
        mActivity= (BaseActivity) context;
        MapUtils.getInstance(mActivity).setLocationCallBack(mLocationCallBack);
        MapUtils.getInstance(mActivity).startLocation();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_family, parent, false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Family family= (Family) getItem(position);
        UIHelper.imageNet(context,family.getLogo(),viewHolder.mImgLogo,false,R.drawable.img_def);
        viewHolder.mTvName.setText(family.getName());
        //距离
        if(flag==LOCATION_SUCCESS){
            LatLng familylatLng=new LatLng(family.getLat(),family.getLng());
            viewHolder.mTvDistance.setText("距离"+ NumberUtils.formatNumberofDistance(MapUtils.getInstance(mActivity).caculateDistance(familylatLng,minelatlng)));
        }
        viewHolder.mTvIntroduce.setText(family.getIntroduce());
        return convertView;
    }

     class ViewHolder {
        @Bind(R.id.img_logo)
        ImageView mImgLogo;
        @Bind(R.id.tv_Name)
        TextView mTvName;
        @Bind(R.id.tv_distance)
        TextView mTvDistance;
        @Bind(R.id.tv_introduce)
        TextView mTvIntroduce;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    MapUtils.LocationCallBack mLocationCallBack=new MapUtils.LocationCallBack() {
        @Override
        public void onLocationSuccess(AMapLocation aMapLocation) {
            flag=LOCATION_SUCCESS;
            if (aMapLocation != null) {
                minelatlng=new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
            }
        }

        @Override
        public void onLocationFailed(String errorCode) {
            flag=LOCATION_FAIL;
        }
    };
}
