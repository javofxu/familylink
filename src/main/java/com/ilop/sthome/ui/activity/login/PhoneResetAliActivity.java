package com.ilop.sthome.ui.activity.login;

import android.os.Bundle;
import android.view.View;

import com.alibaba.sdk.android.openaccount.ui.ui.ResetPasswordActivity;
import com.siterwell.familywellplus.R;

/**
 * Created by henry on 2019/4/16.
 */

public class PhoneResetAliActivity extends ResetPasswordActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mToolBar.setVisibility(View.GONE);
        this.smsCodeInputBox.getSend().setText(getResources().getString(R.string.get_code));
        this.smsCodeInputBox.setSendText("get_code");
        this.mobileInputBox.setSupportForeignMobile(this,null,false);
        findViewById(R.id.iv_reset_back).setOnClickListener(view -> finish());
    }


    @Override
    protected String getLayoutName() {
        return "activity_phone_reset_ali";
    }


}
