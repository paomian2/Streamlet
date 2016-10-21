package com.streamlet.appui.activity.common;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.streamlet.R;
import com.streamlet.appui.base.BaseActivity;
import com.streamlet.common.util.UIHelper;
import com.streamlet.module.entity.base.BaseResponse;
import com.streamlet.module.entity.base.CodeResponse;
import com.streamlet.module.protocol.UserProtocol;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
    @OnClick(R.id.btn_register)
    public void regClick(){
        if(UIHelper.checkTv(activity,mEdtRegPhone,"手机号不能为空")
                &&UIHelper.checkPhoneAvalible(activity,mEdtRegPhone.getText().toString(),"请输入正确的手机号")
                &&UIHelper.checkTv(activity,mEdtRegPwd,"密码不能为空")
                &&UIHelper.checkTv(activity,mEdtRegPwdConfirm,"密码不能为空")
                &&UIHelper.checkPwdSame(activity,mEdtRegPwd,mEdtRegPwdConfirm,"密码不一致")
                ){
            register();
        }
    }
    private long regFlag;
    public void register(){
        HashMap<String,Object> paramers=new HashMap<>();
        paramers.put("phone",mEdtRegPhone.getText().toString());
        paramers.put("password",mEdtRegPwd.getText().toString());
        regFlag=UserProtocol.register(activity,setTag(),paramers);
        UIHelper.showProgressDialog(activity,"注册中...");
    }

    @Override
    public <T> void onHttpSuccess(long flag, String json, T response) {
        UIHelper.cancleProgressDialog();
        if(flag==regFlag){
            BaseResponse baseResponse= (BaseResponse) response;
            if(baseResponse!=null && baseResponse.getCode().equals("0")){
                showToast(baseResponse.getMsg());
                finish();
            }else{
                showToast(baseResponse==null ? "未知错误":baseResponse.getMsg());
            }
        }
    }

    @Override
    public void onHttpError(long flag, VolleyError e, CodeResponse errorResponse) {
        UIHelper.cancleProgressDialog();
        if(flag==regFlag){
            showToast(errorResponse==null ?"网络异常":errorResponse.getDesc());
        }
    }

    @OnClick(R.id.imgv_cancel)
    public void reBack(){
        finish();
    }
    @Override
    public String setTag() {
        return RegisterActivity.class.getSimpleName();
    }

    @Bind(R.id.edt_reg_phone)
    EditText mEdtRegPhone;
    @Bind(R.id.edt_reg_pwd)
    EditText mEdtRegPwd;
    @Bind(R.id.edt_reg_pwd_confirm)
    EditText mEdtRegPwdConfirm;
    @Bind(R.id.btn_register)
    Button mBtnRegister;
    @Bind(R.id.imgv_cancel)
    ImageView mImgvCancel;
}
