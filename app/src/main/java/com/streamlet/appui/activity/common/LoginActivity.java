package com.streamlet.appui.activity.common;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.streamlet.R;
import com.streamlet.appui.base.BaseActivity;
import com.streamlet.base.AppActivityManager;
import com.streamlet.common.util.SharedPreferenceUtil;
import com.streamlet.common.util.UIHelper;
import com.streamlet.module.entity.base.CodeResponse;
import com.streamlet.module.entity.bean.User;
import com.streamlet.module.entity.response.LoginResponse;
import com.streamlet.module.protocol.UserProtocol;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initData();
        initUI();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initUI() {
        mCbPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //如果选中，显示密码
                    mEdtLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    mEdtLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    @Override
    public String setTag() {
        return LoginActivity.class.getSimpleName();
    }

    private long loginFlag;
    /**登录操作*/
    private void login(){
        //登录
        HashMap<String,Object> paramers=new HashMap<>();
        paramers.put("phone",mEdtLoginPhone.getText().toString());
        paramers.put("password",mEdtLoginPwd.getText().toString());
        loginFlag=UserProtocol.login(activity,setTag(),paramers);
        UIHelper.showProgressDialog(activity,"登录中...");
    }

    @Override
    public <T> void onHttpSuccess(long flag, String json, T response) {
        super.onHttpSuccess(flag, json, response);
        UIHelper.cancleProgressDialog();
        if (flag==loginFlag){
            LoginResponse loginResponse= (LoginResponse) response;
            if (loginResponse != null&&loginResponse.getCode().equals("0")) {
                User user=loginResponse.getUser();
                SharedPreferenceUtil.getInstance(activity).saveUser(user);
                showToast("登录成功");
                finish();
            }else{
                showToast(loginResponse==null?"未知错误":loginResponse.getMsg());
            }
        }
    }

    @Override
    public void onHttpError(long flag, VolleyError e, CodeResponse errorResponse) {
        super.onHttpError(flag, e, errorResponse);
        UIHelper.cancleProgressDialog();
        if (flag==loginFlag){
            showToast(errorResponse==null?"网络异常":errorResponse.getDesc());
        }
    }

    @OnClick(R.id.imgv_cancel)
    public void reBack(){
        finish();
    }
    @OnClick(R.id.tv_goto_register)
    public void goToRegActivity(){
        AppActivityManager.getInstance().goTo(activity,RegisterActivity.class);

    }


    @OnClick(R.id.btn_login)
    public void loginClick(){
        if(UIHelper.checkTv(activity,mEdtLoginPhone,"手机号不能为空")
                &&UIHelper.checkPhoneAvalible(activity,mEdtLoginPhone.getText().toString(),"请输入正确的手机号")
                &&UIHelper.checkTv(activity,mEdtLoginPwd,"密码不能为空")){
            login();
        }
    }
    @Bind(R.id.edt_login_phone)
    EditText mEdtLoginPhone;
    @Bind(R.id.edt_login_pwd)
    EditText mEdtLoginPwd;
    @Bind(R.id.cb_pwd)
    CheckBox mCbPwd;
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    @Bind(R.id.imgv_cancel)
    ImageView mImgvCancel;
    @Bind(R.id.tv_goto_register)
    TextView mTvGotoRegister;

}
