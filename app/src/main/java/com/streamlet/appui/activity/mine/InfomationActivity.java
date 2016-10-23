package com.streamlet.appui.activity.mine;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.streamlet.R;
import com.streamlet.appui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by streamlet2 on 2016/10/22.
 *
 * @Description
 */
public class InfomationActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_information);
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
        return InfomationActivity.class.getSimpleName();
    }


    @OnClick(R.id.tv_sex)
    public void sexSelector(){
        Dialog dialog=new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sex);
        dialog.show();

    }
    @OnClick(R.id.imgv_cancel)
    public void reBack() {
        finish();
    }
    @Bind(R.id.imgv_cancel)
    ImageView mImgvCancel;
    @Bind(R.id.tv_commit)
    TextView mTvCommit;
    @Bind(R.id.imgv_info_head)
    ImageView mImgvInfoHead;
    @Bind(R.id.edt_nickname)
    EditText mEdtNickname;
    @Bind(R.id.tv_sex)
    TextView mTvSex;
    @Bind(R.id.imgv_sex)
    ImageView mImgvSex;
    @Bind(R.id.tv_age)
    TextView mTvAge;
    @Bind(R.id.imgv_age)
    ImageView mImgvAge;
    @Bind(R.id.tv_address)
    TextView mTvAddress;
    @Bind(R.id.imgv_address)
    ImageView mImgvAddress;
    @Bind(R.id.edt_sign)
    EditText mEdtSign;

}
