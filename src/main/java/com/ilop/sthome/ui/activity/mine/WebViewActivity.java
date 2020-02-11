package com.ilop.sthome.ui.activity.mine;

import android.webkit.WebViewClient;

import com.example.common.base.BaseActivity;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityWebViewBinding;


/**
 * @author skygge
 * @date 2019-12-03.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */

public class WebViewActivity extends BaseActivity<ActivityWebViewBinding> {

    private String privacy_agreement;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(true);
        privacy_agreement = getIntent().getStringExtra("agree");
    }

    @Override
    protected void initView() {
        super.initView();
        mDBind.webView.getSettings().setJavaScriptEnabled(true);//让浏览器支持javascript
        mDBind.webView.setWebViewClient(new WebViewClient());
        mDBind.webView.loadUrl(privacy_agreement);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivWebViewBack.setOnClickListener(view -> finish());
    }
}
