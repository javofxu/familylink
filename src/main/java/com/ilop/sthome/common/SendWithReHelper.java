package com.ilop.sthome.common;

import com.example.common.utils.RxTimerUtil;
import com.ilop.sthome.network.udp.GatewayUdpListConstant;

public class SendWithReHelper {

    private static int mCount = 0;
    private MyTaskCallback mCallback;

    public SendWithReHelper(MyTaskCallback callback ){
        this.mCallback =callback;

        RxTimerUtil.interval(2000, number -> {
            GatewayUdpListConstant gatewayUdpListConstant = GatewayUdpListConstant.getInstance();
            if(gatewayUdpListConstant.isReveiveDataOrNot()){
                mCallback.operationSuccess(mCount);
                RxTimerUtil.cancel();
            }else {
                if (mCount >=3){
                    mCallback.operationFailed(mCount);
                    RxTimerUtil.cancel();
                }else {
                    mCount++;
                    mCallback.doReSendAction(mCount);
                }
            }
        });
    }


    public  interface MyTaskCallback  {

        void operationFailed(int count);

        void operationSuccess(int count);

        void doReSendAction(int count);
    }

}
