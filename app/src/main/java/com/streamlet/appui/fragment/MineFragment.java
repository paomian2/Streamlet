package com.streamlet.appui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.streamlet.R;
import com.streamlet.appui.activity.common.LoginActivity;
import com.streamlet.appui.activity.mine.FamilyRegActivity;
import com.streamlet.appui.base.BaseFragment;
import com.streamlet.base.AppActivityManager;
import com.streamlet.common.util.SharedPreferenceUtil;
import com.streamlet.common.widget.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by streamlet2 on 2016/10/19.
 */
public class MineFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void initUI() {
        setContentView(R.layout.fragment_mine);
    }

    @Override
    public void initData() {

    }

    @Override
    public String setTag() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 设置
     */
    @OnClick(R.id.tv_setting)
    public void goToSetting() {
       // AppActivityManager.getInstance().goToActivityOfLogin(activity,);
    }

    /**我的发布*/
    @OnClick(R.id.tv_release)
    public void goToRelese(){

    }
    /**我的收藏*/
    @OnClick(R.id.tv_collection)
    public void goToCollection(){

    }

    /**积分*/
    @OnClick(R.id.tv_points)
    public void goToPoints(){

    }

    /**动态*/
    @OnClick(R.id.tv_dynamic)
    public void goToDynamic(){

    }

    /**家庭*/
    @OnClick(R.id.tv_family)
    public void goToFamily(){
        if(SharedPreferenceUtil.getInstance(activity).isLogin(activity)){
            if (SharedPreferenceUtil.getInstance(activity).getUser().getFamilyId()!=0){
                //进行Family

            }else{
                //注册Family
                AppActivityManager.getInstance().goTo(activity, FamilyRegActivity.class);
            }
        }else{
            AppActivityManager.getInstance().goTo(activity, LoginActivity.class);
        }

    }

    /**钱罐*/
    @OnClick(R.id.tv_moneybox)
    public void goToMoneyBox(){

    }

    /**书房*/
    @OnClick(R.id.tv_study)
    public void goToStudy(){

    }



    @Bind(R.id.tv_phone)
    TextView mTvPhone;
    @Bind(R.id.imgv_head)
    CircleImageView mImgvHead;
    @Bind(R.id.tv_setting)
    TextView mTvSetting;
    @Bind(R.id.tv_release)
    TextView mTvRelease;
    @Bind(R.id.tv_collection)
    TextView mTvCollection;
    @Bind(R.id.tv_points)
    TextView mTvPoints;
    @Bind(R.id.tv_dynamic)
    TextView mTvDynamic;
    @Bind(R.id.tv_family)
    TextView mTvFamily;
    @Bind(R.id.tv_moneybox)
    TextView mTvMoneybox;
    @Bind(R.id.tv_study)
    TextView mTvStudy;


}
