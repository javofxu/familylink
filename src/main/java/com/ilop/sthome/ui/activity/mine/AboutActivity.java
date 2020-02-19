package com.ilop.sthome.ui.activity.mine;

import android.content.Intent;
import android.net.Uri;

import com.example.common.base.BaseActivity;
import com.ilop.sthome.utils.AppMarketUtil;
import com.ilop.sthome.utils.tools.CacheUtil;
import com.ilop.sthome.utils.tools.Config;
import com.ilop.sthome.utils.tools.SiterSDK;
import com.ilop.sthome.utils.tools.UnitTools;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityAboutBinding;

import org.json.JSONException;

/**
 * @author skygge
 * @date 2020-02-19.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：关于页面
 */
public class AboutActivity extends BaseActivity<ActivityAboutBinding>{
    private String mAddress;
    private String privacy_agreement;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        super.initView();
        String verName = Config.getVerName(this, getPackageName());
        mDBind.appVersion.setText("v"+verName);
    }

    @Override
    protected void initData() {
        super.initData();
        UnitTools unitTools = new UnitTools(this);
        try {
            String url = CacheUtil.getString(SiterSDK.SETTINGS_CONFIG_WEBSITE,"");
            org.json.JSONObject jsonConfig = new org.json.JSONObject(url);
            org.json.JSONObject json2 = jsonConfig.getJSONObject("url");
            if(json2.has(unitTools.readLanguage())){
                mAddress = json2.getString(unitTools.readLanguage());
            }else {
                mAddress = json2.getString("default");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String url = CacheUtil.getString(SiterSDK.SETTINGS_CONFIG_PRIVACY_AGREE,"");
            org.json.JSONObject jsonConfig = new org.json.JSONObject(url);
            org.json.JSONObject json2 = jsonConfig.getJSONObject("url");
            if(json2.has(unitTools.readLanguage())){
                privacy_agreement = json2.getString(unitTools.readLanguage());
            }else {
                privacy_agreement = json2.getString("default");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDBind.ivAboutBack.setOnClickListener(view -> finish());

        mDBind.officialWebsite.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse("http://"+mAddress+"/");
            intent.setData(content_url);
            startActivity(intent);
        });

        mDBind.versionUpdate.setOnClickListener(view -> AppMarketUtil.versionUpdate(mContext));

        mDBind.contactUs.setOnClickListener(view -> skipAnotherActivity(ContactUsActivity.class));


        mDBind.privacyAgree.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(mContext, WebViewActivity.class);
            intent.putExtra("agree", privacy_agreement);
            startActivity(intent);
        });

        mDBind.userAgree.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(mContext, AgreementActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
