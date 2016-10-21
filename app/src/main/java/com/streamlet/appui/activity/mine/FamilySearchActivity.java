package com.streamlet.appui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.streamlet.R;
import com.streamlet.appui.adapter.FamilyAdapter;
import com.streamlet.appui.base.BaseActivity;
import com.streamlet.common.util.UIHelper;
import com.streamlet.module.entity.base.CodeResponse;
import com.streamlet.module.entity.response.FamilyListResponse;
import com.streamlet.module.protocol.UserProtocol;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by streamlet2 on 2016/10/20.
 *
 * @Description 根据名字搜索家庭列表类
 */
public class FamilySearchActivity extends BaseActivity {


    private FamilyAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_family_search);
        ButterKnife.bind(this);
        initData();
        initUI();
    }


    @Override
    protected void initData() {
    }

    @Override
    protected void initUI() {
        mAdapter = new FamilyAdapter(activity);
        mLvFamily.setAdapter(mAdapter);
    }

    @Override
    public String setTag() {
        return FamilySearchActivity.class.getSimpleName();
    }

    private long searchFlag;

    /**
     * 根据FamilyName搜索Family列表数据
     */
    private void getFamliyList() {
        String familyName = mEdtSearch.getText().toString().trim();
        searchFlag = UserProtocol.familyList(activity, setTag(), familyName);
        UIHelper.showProgressDialog(activity, "搜索中...");
    }

    @Override
    public void onHttpError(long flag, VolleyError e, CodeResponse errorResponse) {
        super.onHttpError(flag, e, errorResponse);
        UIHelper.cancleProgressDialog();
        if (flag == searchFlag) {
            showToast(errorResponse == null ? "网络异常" : errorResponse.getDesc());
        }
    }

    @Override
    public <T> void onHttpSuccess(long flag, String json, T response) {
        super.onHttpSuccess(flag, json, response);
        UIHelper.cancleProgressDialog();
        if (flag == searchFlag) {
            FamilyListResponse familyListResponse = (FamilyListResponse) response;
            if (familyListResponse != null) {
                updateListUI(familyListResponse);
            } else {
                //数据异常
            }
        }
    }

    private int page = 0;
    private int size = 10;

    private void updateListUI(FamilyListResponse familyListResponse) {
        if (familyListResponse.getFamilys().size() > 0) {
            mLvFamily.setVisibility(View.VISIBLE);
            mLayoutDataEmpty.setVisibility(View.GONE);
            mAdapter.addAdapterData(familyListResponse.getFamilys());
            if (familyListResponse.getFamilys().size() < size) {
                //已加载完成
            }
        } else {
            mLvFamily.setVisibility(View.GONE);
            mLayoutDataEmpty.setVisibility(View.VISIBLE);
            if (mAdapter.getCount() == 0) {
                //暂无数据
            }
        }
    }

    @OnClick(R.id.imgv_cancel)
    public void reBack() {
        finish();
    }

    @OnClick(R.id.tv_ok)
    public void searchFamilyClick() {
        if (UIHelper.checkTv(activity, mEdtSearch, "请输入搜索内容")) {
            mAdapter.clearAdapter();
            getFamliyList();
        }
    }

    @Bind(R.id.imgv_cancel)
    ImageView mImgvCancel;
    @Bind(R.id.edt_search)
    EditText mEdtSearch;
    @Bind(R.id.tv_ok)
    TextView mTvOk;
    @Bind(R.id.lv_family)
    ListView mLvFamily;
    @Bind(R.id.layout_data_empty)
    RelativeLayout mLayoutDataEmpty;
}
