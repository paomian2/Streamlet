package com.streamlet.appui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.streamlet.R;
import com.streamlet.appui.activity.map.LbsCommonActivity;
import com.streamlet.appui.base.BaseActivity;
import com.streamlet.base.AppActivityManager;
import com.streamlet.common.util.NumberUtils;
import com.streamlet.module.entity.bean.LBSAddress;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by streamlet2 on 2016/10/20.
 *
 * @Description
 */
public class FamilyRegActivity extends BaseActivity {

    private final int REQUEST_CODE=1001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_reg);
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
    @OnClick(R.id.imgv_cancel)
    public void reBack(){
        finish();
    }
    @OnClick(R.id.imgv_location)
    public void goToLbsActivity(){
        AppActivityManager.getInstance().goFoResult(activity, LbsCommonActivity.class,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            LBSAddress lbsAddress= (LBSAddress) data.getExtras().getSerializable("json");
            mTvLatitudelongitude.setText(NumberUtils.sacleNumber2(lbsAddress.getLatitude(),6)+","+(NumberUtils.sacleNumber2(lbsAddress.getLongitude(),6)));
            mEdtAddress.setText(lbsAddress.getAddress());
        }
    }

    @OnClick(R.id.tv_search)
    public void goToFamilySearchActivity(){
        AppActivityManager.getInstance().goTo(activity,FamilySearchActivity.class);
    }
    @Override
    public String setTag() {
        return FamilyRegActivity.class.getSimpleName();
    }
    @Bind(R.id.imgv_cancel)
    ImageView mImgvCancel;
    @Bind(R.id.tv_search)
    TextView mTvSearch;
    @Bind(R.id.edt_name)
    EditText mEdtName;
    @Bind(R.id.tv_latitudelongitude)
    TextView mTvLatitudelongitude;
    @Bind(R.id.imgv_location)
    ImageView mImgvLocation;
    @Bind(R.id.edt_address)
    EditText mEdtAddress;
    @Bind(R.id.btn_submit)
    Button mBtnSubmit;
}
