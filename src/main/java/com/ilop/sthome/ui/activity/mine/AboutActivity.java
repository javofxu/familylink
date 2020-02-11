package com.ilop.sthome.ui.activity.mine;

import android.content.Intent;
import android.net.Uri;

import com.example.common.base.BaseActivity;
import com.ilop.sthome.ui.activity.main.ExplainActivity;
import com.ilop.sthome.ui.dialog.BaseListDialog;
import com.ilop.sthome.utils.tools.CacheUtil;
import com.ilop.sthome.utils.tools.Config;
import com.ilop.sthome.utils.tools.SiterSDK;
import com.ilop.sthome.utils.tools.UnitTools;
import com.ilop.sthome.utils.updateApp.UpdateAppUtil;
import com.siterwell.familywellplus.R;
import com.siterwell.familywellplus.databinding.ActivityAboutBinding;

import org.json.JSONException;

/**
 * Created by 许格 on 2019/11/2.
 */
public class AboutActivity extends BaseActivity<ActivityAboutBinding>{
    private UpdateAppUtil mUpdateAppUtil;
    private String dd;
    private String privacy_agreement;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        super.initView();
        mUpdateAppUtil = new UpdateAppUtil(this, mDBind.appVersion,true);
        String verName = Config.getVerName(this, getPackageName());
        mDBind.aboutCenter.getBackground().setAlpha(150);
        mDBind.appVersion.setDetailText(verName);
        mUpdateAppUtil.getUpdateInfo();
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
                dd = json2.getString(unitTools.readLanguage());
            }else {
                dd = json2.getString("default");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mDBind.guanwang.setText(dd);

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

        mDBind.sale.setOnClickListener(view -> openPhoneAlert());

        mDBind.guanwang.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse("http://"+dd+"/");
            intent.setData(content_url);
            startActivity(intent);
        });

        mDBind.privacyAgree.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(mContext, WebViewActivity.class);
            intent.putExtra("agree", privacy_agreement);
            startActivity(intent);
        });

        mDBind.userAgree.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(mContext, ExplainActivity.class);
            intent.putExtra("mid", 666);
            startActivity(intent);
        });
    }

    private void openPhoneAlert(){
        String[] ad = getResources().getStringArray(R.array.lianxi);
        if(getPackageName().equals("com.siterwell.lifebox")){
            ad[0] = "SAV (0,85€/mn):"+ad[0];
        }

        BaseListDialog mDialog = new BaseListDialog(mContext, i -> {
            switch (i){
                case 0:
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+getResources().getStringArray(R.array.lianxi)[0]));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                    break;
                case 1:
                    Intent data=new Intent(Intent.ACTION_SENDTO);
                    data.setData(Uri.parse("mailto:"+getResources().getStringArray(R.array.lianxi)[1]));
                    startActivity(data);
                    break;
                default:
                    break;
            }
        });
        mDialog.setMsgAndButton(ad, getString(R.string.cancel));
        mDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
