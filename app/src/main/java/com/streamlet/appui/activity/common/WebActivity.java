package com.streamlet.appui.activity.common;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.streamlet.R;
import com.streamlet.appui.base.BaseActivity;
import com.streamlet.common.util.UIHelper;


/**
 * 通用的web组件
 */
public class WebActivity extends BaseActivity implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String WEB_URL = "web_url";
    private WebView strategyWebview;
    private ProgressBar strategypb;
    private TextView tvTitle;
    private String webUrl;
    private SwipeRefreshLayout mSwipeLayout;

    public static void launcher(Context context, String webUrl) {
        Intent intent = new Intent();
        intent.setClass(context, WebActivity.class);
        intent.putExtra(WEB_URL, webUrl);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_web);
        initUI();
        initData();
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void initUI() {
        findViewById(R.id.leftButton).setOnClickListener(this);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(R.color.top_bg);
        mSwipeLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeLayout.setEnabled(false);
        strategyWebview = (WebView) findViewById(R.id.ac_search_strategy_webview);
        WebSettings webSettings = strategyWebview.getSettings();
        if (webSettings != null) {
            webSettings.setJavaScriptEnabled(true);
        }
        strategyWebview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        strategyWebview.setHorizontalScrollbarOverlay(true);
        strategyWebview.setHorizontalScrollBarEnabled(true);
        strategyWebview.requestFocus();
        strategyWebview.setWebViewClient(new strategyWebViewClient());
        strategyWebview.setWebChromeClient(new strategyWebChromeClient());
        strategyWebview.setDownloadListener(new MyWebViewDownLoadListener());
        strategypb = (ProgressBar) findViewById(R.id.ac_search_strategy_progress);
        tvTitle = (TextView) findViewById(R.id.centerTitle);
        webUrl = getIntent().getStringExtra(WEB_URL);

    }

    protected void initData() {
        strategyWebview.loadUrl(webUrl);
    }

    private class strategyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mSwipeLayout.setEnabled(true);
            mSwipeLayout.setRefreshing(false);
            super.onPageFinished(view, url);
        }

    }

    private class strategyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            strategypb.setProgress(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (title.length() > 8) {
                title = title.substring(0, 8) + "...";
            }
            //tvTitle.setText(StringUtils.isEmpty(title)?"优车快洗":title);
            tvTitle.setText("窝窝头·优车快洗");
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onPause() {
        super.onPause();
        strategyWebview.onPause();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onResume() {
        super.onResume();
        strategyWebview.onResume();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        strategyWebview.removeAllViews();
        strategyWebview.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            strategyWebview.loadData("", "text/html; charset=UTF-8", null);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            if ("apk".equals(url.substring(url.lastIndexOf(".") + 1))) {
             //   DownloadService.luanch(WebActivity.this, "文件", url, R.drawable.icon_home_logo);
            } else {
                try {
                    UIHelper.openBrowser(activity, url);
                } catch (Exception e) {
                    showToast("附件错误");
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        initData();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.leftButton:
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public String setTag() {
        // TODO Auto-generated method stub
        return null;
    }

}
