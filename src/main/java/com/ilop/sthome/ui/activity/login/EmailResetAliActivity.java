package com.ilop.sthome.ui.activity.login;

import android.os.Bundle;
import android.view.View;

import com.alibaba.sdk.android.openaccount.ui.ui.EmailResetPasswordActivity;
import com.siterwell.familywellplus.R;

/**
 * Created by henry on 2019/4/16.
 */

public class EmailResetAliActivity extends EmailResetPasswordActivity {


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mToolBar.setVisibility(View.GONE);
        this.checkCodeInputBox.getSend().setText(getResources().getString(R.string.get_code));
        this.checkCodeInputBox.setSendText("get_code");
        findViewById(R.id.iv_reset_back).setOnClickListener(view -> finish());
    }

    @Override
    protected String getLayoutName() {
        return "activity_mail_reset_ali";
    }


}
