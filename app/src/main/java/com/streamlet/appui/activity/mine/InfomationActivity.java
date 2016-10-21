package com.streamlet.appui.activity.mine;

import android.os.Bundle;

import com.streamlet.R;
import com.streamlet.appui.base.BaseActivity;

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
}
