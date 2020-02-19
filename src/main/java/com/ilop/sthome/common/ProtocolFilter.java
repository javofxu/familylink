package com.ilop.sthome.common;

import android.content.Context;

import com.example.common.base.OnCallBackToRefresh;
import com.example.common.utils.LiveDataBus;
import com.example.common.utils.SpUtil;
import com.ilop.sthome.ui.dialog.PrivacyPolicyDialog;

/**
 * @author skygge
 * @date 2019-12-09.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：协议过滤器
 */
public class ProtocolFilter {

    private Context context;
    private Action action;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        if(!isReadProtocol()){
            PrivacyPolicyDialog mDialog = new PrivacyPolicyDialog(context, new OnCallBackToRefresh() {
                @Override
                public void onConfirm() {
                    SpUtil.putBoolean(context,"isAgree", true);
                    if (action!=null) getAction().doAction();
                    LiveDataBus.get().with("close_dialog").postValue(0);
                }

                @Override
                public void onCancel() {
                    SpUtil.putBoolean(context,"isAgree", false);
                    LiveDataBus.get().with("close_dialog").postValue(0);
                }
            });
            mDialog.show();
        }else{
            if (action!=null) getAction().doAction();
        }
    }

    private boolean isReadProtocol(){
        return SpUtil.getBoolean(context, "isAgree", false);
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public interface Action {
        void doAction();
    }
}
