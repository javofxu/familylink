package com.ilop.sthome.utils;

import com.siterwell.familywellplus.R;

/**
 * Created by ST-020111 on 2017/4/7.
 */

public class ShowDetailInfoUtil {
    private static int[] imageQ = new int[]{
            R.mipmap.battery0,
            R.mipmap.battery1,
            R.mipmap.battery2,
            R.mipmap.battery3,
            R.mipmap.battery4,
            R.mipmap.battery5
    };
    private static int[] imageS = new int[]{
            R.mipmap.signal0,
            R.mipmap.signal1,
            R.mipmap.signal2,
            R.mipmap.signal3,
            R.mipmap.signal4
    };

    /**
     * show quantity
     * @param q
     * @return
     */
    public static int choseQPic(int q){
        int qResource;
        if(q == 0){
            qResource = imageQ[0];
        }else if(q <= 20){
            qResource = imageQ[1];
        }else if(q <= 40){
            qResource = imageQ[2];
        }else if(q <= 60){
            qResource = imageQ[3];
        }else if(q <= 80){
            qResource = imageQ[4];
        }else{
            qResource = imageQ[5];
        }
        return qResource;
    }

    /**
     * show signal
     * @param s
     * @return
     */
    public static int choseSPic(String s){
        int mResource;
        if("00".equals(s)){
            mResource = imageS[0];
        }else if("01".equals(s)){
            mResource = imageS[1];
        }else if("02".equals(s)){
            mResource = imageS[2];
        }else if("03".equals(s)){
            mResource = imageS[3];
        }else if("04".equals(s)){
            mResource = imageS[4];
        }else {
            mResource = imageS[0];
        }
        return mResource;
    }

    public static String choseLNum(int n){
        String num;
        if(n>100){
            num = "100%";
        }else{
            num = n +"%";
        }
        return num;
    }
}
