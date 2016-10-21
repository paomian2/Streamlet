package com.streamlet.appui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.streamlet.R;
import com.streamlet.appui.activity.common.FragmentTabAdapter;
import com.streamlet.appui.base.BaseActivity;
import com.streamlet.appui.base.BaseFragment;
import com.streamlet.appui.fragment.HomeFragment;
import com.streamlet.appui.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by streamlet2 on 2016/10/19.
 */
public class MainActivity extends BaseActivity {

    BaseFragment homeFrag,mineFrag;
    private List<Fragment> mFragmentList=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initUI();
    }

    @Override
    protected void initData() {
         homeFrag=new HomeFragment();
         mineFrag=new MineFragment();
        mFragmentList.add(homeFrag);
        mFragmentList.add(mineFrag);
    }

    @Override
    protected void initUI() {
        FragmentTabAdapter adapter=new FragmentTabAdapter(activity,mFragmentList,R.id.fragment_container,mRg);

    }

    @Override
    public String setTag() {
        return MainActivity.class.getSimpleName();
    }

    @Bind(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @Bind(R.id.rg)
    RadioGroup mRg;
}
