package com.streamlet.appui.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.streamlet.common.widget.PagedLoader;

/**
 * Description: 基础刷新页面.<br/><br/>
 * Author: Create by Yu.Yao on 2016/9/13$.<br/><br/>
 */
public abstract class BaseRefreshFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        PagedLoader.OnLoadListener {
    private PagedLoader mPagedLoader;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean isLoading;

    @Override
    public abstract void initUI();

    @Override
    public abstract void initData();

    @Override
    public abstract String setTag();

    @Override
    public void onRefresh() {
        onPullDownRefresh();
    }

    @Override
    public void onLoading(PagedLoader pagedLoader, boolean isAutoLoad) {
        onPullUpRefresh();
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    /**
     * Description: 必须实现, 为mSwipeRefreshLayout设置基本配置<br/><br/>
     * Author: Create by Yu.Yao in 2015/12/16.<br/><br/>
     */
    public void initRefresh(ListView listView, BaseAdapter listAdapter) {
        setSwipeRefreshListener();
        mPagedLoader = PagedLoader.from(listView).setOnLoadListener(this).builder();
        mPagedLoader.setAdapter(listAdapter);

    }

    /**
     * @description 为initSwipeRefreshLayout设置监听器
     * @author Create by Yu.Yao in 2015/12/16.
     */
    public void setSwipeRefreshListener() {
        if (getSwipeRefreshLayout() != null)
            getSwipeRefreshLayout().setOnRefreshListener(this);
    }

    /**
     * Description: 下拉刷新动画,不会回调onRefreshing()方法.<br/><br/>
     * Author: Create by Yu.Yao on 2016/5/3.<br/><br/>
     */
    public void pullDownRefreshing(final boolean isRefresh) {
        if (isLoading() && isRefresh) {
            return;
        }
        setLoading(isRefresh);
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setVisibility(View.VISIBLE);
            getSwipeRefreshLayout().post(new Runnable() {
                @Override
                public void run() {
                    if (getSwipeRefreshLayout() != null) {
                        getSwipeRefreshLayout().setRefreshing(true);
                    }
                }
            });
        }
    }

    /**
     * Description: 操作完成时调用.<br/><br/>
     * Author: Create by Yu.Yao on 2016/5/3.<br/><br/>
     */
    protected void complete() {
        if (getSwipeRefreshLayout() != null) {
            setLoading(false);
            getSwipeRefreshLayout().post(new Runnable() {
                @Override
                public void run() {
                    if (getSwipeRefreshLayout() != null) {
                        getSwipeRefreshLayout().setRefreshing(false);
                    }
                }
            });
        }
    }

    /**
     * Description: 返回布局中的SwipeRefreshLayout控件.<br/><br/>
     * Author: Create by Yu.Yao on 2016/4/28.<br/><br/>
     */
    public abstract SwipeRefreshLayout getSwipeRefreshLayout();

    /**
     * Description: 下拉刷新回调.<br/><br/>
     * Author: Create by Yu.Yao on 2016/4/28.<br/><br/>
     */
    abstract public void onPullDownRefresh();

    /**
     * Description: 上拉刷新回调.<br/><br/>
     * Author: Create by Yu.Yao on 2016/4/28.<br/><br/>
     */
    abstract public void onPullUpRefresh();
}
