package com.streamlet.appui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.streamlet.R;
import com.streamlet.appui.activity.map.LbsCommonActivity;
import com.streamlet.appui.base.BaseActivity;
import com.streamlet.base.AppActivityManager;
import com.streamlet.common.util.SharedPreferenceUtil;

public class LaunchActivity extends BaseActivity implements Animation.AnimationListener {

    ImageView iv;
    String guide;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_launch);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Animation at = AnimationUtils.loadAnimation(this, R.anim.launch_ad);
        iv = (ImageView) findViewById(R.id.iv);
        at.setAnimationListener(this);
        iv.startAnimation(at);
        guide = SharedPreferenceUtil.getInstance(activity).getString(SharedPreferenceUtil.GUIDE);

    }

    boolean isGuide() {
        if ("1".equals(guide)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }

    @Override
    public void onAnimationEnd(Animation arg0) {
        // TODO Auto-generated method stub
        activityStart();
    }

    void activityStart() {
//		if (isGuide()) {
//			AppActivityManager.getInstance().goTo(activity, MainActivity.class);
//		} else {
//			AppActivityManager.getInstance().goTo(activity, GuideActivity.class);
//		}MainActivity
        AppActivityManager.getInstance().goTo(activity, MainActivity.class);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationStart(Animation arg0) {
    }

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
        return LaunchActivity.class.getSimpleName();
    }


}
