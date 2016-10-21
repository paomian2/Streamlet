package com.streamlet.appui.activity.mine;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.streamlet.R;
import com.streamlet.appui.base.BaseActivity;
import com.streamlet.base.AppActivityManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by streamlet2 on 2016/10/22.
 *
 * @Description
 */
public class SettingActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_setting);
        ButterKnife.bind(this);
        initData();
        initUI();
    }

    @Override
    protected void initData() {

    }
    @Override
    protected void initUI() {

    }

    @Override
    public String setTag() {
        return SettingActivity.class.getSimpleName();
    }
    @OnClick(R.id.imgv_cancel)
    public void reBack() {
        finish();
    }
    @OnClick(R.id.tv_information)
    public void goToInfomationActivity(){
        AppActivityManager.getInstance().goToActivityOfLogin(activity,InfomationActivity.class);
    }
    @Bind(R.id.imgv_cancel)
    ImageView mImgvCancel;
    @Bind(R.id.tv_information)
    TextView mTvInformation;
    @Bind(R.id.tv_language)
    TextView mTvLanguage;
    @Bind(R.id.tv_about)
    TextView mTvAbout;
    @Bind(R.id.tv_security)
    TextView mTvSecurity;
    @Bind(R.id.btn_signout)
    Button mBtnSignout;
}
