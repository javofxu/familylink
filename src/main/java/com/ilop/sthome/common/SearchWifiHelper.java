package com.ilop.sthome.common;

import android.text.TextUtils;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jishu0001 on 2016/12/27.
 */
public class SearchWifiHelper {
    private final static String TAG = "SeatchWifiData";
    private static int conut;
    private static Timer timer;
    private TimerTask task;
    private static MyTaskCallback ds;
    public SearchWifiHelper(MyTaskCallback ds2 ){
        ControllerWifi.getInstance().deviceTid = null;
        ControllerWifi.getInstance().bind = null;
        ControllerWifi.getInstance().ctrlKey = null;

        conut = 0;
        timer = new Timer();
        task = new MyTimerTask();
        ds =ds2;
    }

    private static class MyTimerTask extends TimerTask{

        @Override
        public synchronized void run() {

            try {
                Log.i(TAG," re - seatch  ===="+ conut);
                if(!TextUtils.isEmpty(ControllerWifi.getInstance().deviceTid)){
                    if(timer==null){
                        conut = 0;
                    }else{
                        timer.cancel();
                        timer = null;
                        ds.operationSuccess();
                    }

                }else{
                    conut++;
                    ds.doReSendAction();
                    Log.i(TAG,"resend data   times ===" + conut);
                    if(conut >= 3){
                        if(timer == null){
                            conut = 0;
                        }else{
                            timer.cancel();
                            conut = 0;
                            timer = null;
                            ds.operationFailed();
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                conut = 0;
                timer = null;
            }

        }
    }

    public synchronized void startReSend(){

        timer.schedule(task,0,1000);

    }

    public  interface MyTaskCallback  {
        void operationFailed();

         void operationSuccess();

         void doReSendAction();
    }


}
