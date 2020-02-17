package com.ilop.sthome.utils;

import android.content.Context;

import com.ilop.sthome.data.greenDao.AutomationBean;
import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.utils.greenDao.AutomationDaoUtil;
import com.ilop.sthome.utils.tools.ByteUtil;
import com.ilop.sthome.utils.tools.UnitTools;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-10-31.
 * @Dec:
 */
public class CommandUtil {

    private static AutomationBean mAutomation;
    private static String mMessageCode;

    public static String addModle(String name, String trigger, AutomationBean mAuto, List<DeviceInfoBean> input, List<DeviceInfoBean> output){

        mAutomation = mAuto;
        int length = 2;//自动化长度
        length += 1;//系统自动化编号

        String cm3 = trigger;
        length +=1;//条件选择

        String settime4 ="000000";
        length +=3;//定时

        String cl = "FF";
        for(DeviceInfoBean de : input){
            if( "CLICK".equals(de.getDevice_type())){
                cl = "ab";
            }
        }

        String click5 = cl;
        length += 1;//点击执行

        String ph = "FF";
        for(DeviceInfoBean de : output){
            if( "PHONE".equals(de.getDevice_type())){
                ph = "AC";
            }

        }

        String inf6 =ph;
        length +=1;//通知手机

        int in = 0;
        length +=1;//输入设备个数

        int out = 0;
        length +=1;//输出设备个数


        String inCode="";
        for(int i = 0; i < input.size(); i++ ){
            DeviceInfoBean eq = input.get(i);
            if(!"TIMER".equals(eq.getDevice_type())&&!"CLICK".equals(eq.getDevice_type())){
                in ++;
                length += 6;
                String ei1 ="0";
                if(Integer.toHexString(eq.getDevice_ID()).length() < 4){
                    for (int j = 0; j<4- Integer.toHexString(eq.getDevice_ID()).length()-1; j++ ){
                        ei1 += 0;
                    }
                    ei1 += Integer.toHexString(eq.getDevice_ID());
                }else{
                    ei1 = Integer.toHexString(eq.getDevice_ID());
                }
                inCode += (  ei1 + eq.getDevice_status());
            }else if("TIMER".equals(eq.getDevice_type())){
                settime4 = eq.getDevice_status();
            }
        }
        String outCode ="";
        for(int i = 0; i < output.size(); i++ ){
            DeviceInfoBean eq = output.get(i);
            if(!"DELAY".equals(eq.getDevice_type())&&!"PHONE".equals(eq.getDevice_type())){

                String beforeVelue = "ok";
                if(i >0 && output.get(i-1) != null){
                    DeviceInfoBean before = output.get(i-1);
                    beforeVelue = before.getDevice_type();
                }

                if(i==0 || (i-1 >= 0 && !"DELAY".equals(beforeVelue)&&!"PHONE".equals(beforeVelue))){
                    out ++;  //use for get number
                    length += 8;
                    String ei1 ="0";
                    if(Integer.toHexString(eq.getDevice_ID()).length() < 4){
                        for (int j = 0; j<4- Integer.toHexString(eq.getDevice_ID()).length()-1; j++ ){
                            ei1 += 0;
                        }
                        ei1 += Integer.toHexString(eq.getDevice_ID());
                    }else{
                        ei1 = Integer.toHexString(eq.getDevice_ID());
                    }
                    outCode += ( "0000"+ei1 + eq.getDevice_status());
                }else if("DELAY".equals(beforeVelue)){
                    out ++;  //use for get number
                    length += 6;
                    String ei1 ="0";
                    if(Integer.toHexString(eq.getDevice_ID()).length() < 4){
                        for (int j = 0; j<4- Integer.toHexString(eq.getDevice_ID()).length()-1; j++ ){
                            ei1 += 0;
                        }
                        ei1 += Integer.toHexString(eq.getDevice_ID());
                    }else{
                        ei1 = Integer.toHexString(eq.getDevice_ID());
                    }
                    outCode += ( ei1 + eq.getDevice_status());
                }else if("PHONE".equals(beforeVelue)){
                    out ++;  //use for get number
                    length += 8;
                    String ei1 ="0";
                    if(Integer.toHexString(eq.getDevice_ID()).length() < 4){
                        for (int j = 0; j<4- Integer.toHexString(eq.getDevice_ID()).length()-1; j++ ){
                            ei1 += 0;
                        }
                        ei1 += Integer.toHexString(eq.getDevice_ID());
                    }else{
                        ei1 = Integer.toHexString(eq.getDevice_ID());
                    }
                    outCode += ("0000"+ ei1 + eq.getDevice_status());
                }
            }else if("DELAY".equals(eq.getDevice_type())){
                length += 2;
                //settime4 = eq.getState();
                outCode += UnitTools.timeDecode(eq.getDevice_status(),4);
            }
        }
        String oooo ="0";
        if(Integer.toHexString(length+32).length() < 4){
            for (int i = 0; i<4-Integer.toHexString(length+32).length()-1; i++ ){
                oooo += 0;
            }
            oooo += Integer.toHexString(length+32);
        }else{
            oooo = Integer.toHexString(length+32);
        }

        String ooo = "";
        int amid2 = mAutomation.getMid();
        String amid1 = Integer.toHexString(amid2);
        if(Integer.toHexString(mAutomation.getMid()).length()<2){
            for(int i =0; i<2 - amid1.length();i++){
                ooo += 0;
            }
            ooo +=amid1;
        }else{
            ooo = amid1;
        }

        String oo = "0";
        if(Integer.toHexString(in).length()<2){
            oo = oo + Integer.toHexString(in);
        }else{
            oo = Integer.toHexString(in);
        }

        String o ="0";
        if(Integer.toHexString(out).length()<2){
            o += Integer.toHexString(out);
        }

        String ds = CoderALiUtils.getAscii(name);


        String deCode =  oooo + ooo + ds + cm3 + UnitTools.timeDecode(settime4,6) + click5 + inf6 + oo + o +inCode +outCode;
        String crc = ByteUtil.CRCmakerCharAndCode(deCode);

        String abc = deCode + crc;

        mMessageCode = deCode;
        return abc;
    }

    public static String  getCode(String name, String trigger, AutomationBean mAutomation, List<DeviceInfoBean> input, List<DeviceInfoBean> output) {
        int length = 2;//自动化长度
        length += 1;//系统自动化编号

        String cm3 = trigger;
        length +=1;//条件选择

        String settime4 ="000000";
        length +=3;//定时

        String cl = "FF";
        for(DeviceInfoBean de : input){
            if( "CLICK".equals(de.getDevice_type())){
                cl = "ab";
            }

        }

        String click5 = cl;
        length += 1;//点击执行

        String ph = "FF";
        for(DeviceInfoBean de : output){
            if( "PHONE".equals(de.getDevice_type())){
                ph = "AC";
            }

        }

        String inf6 =ph;
        length +=1;//通知手机

        int in = 0;
        length +=1;//输入设备个数

        int out = 0;
        length +=1;//输出设备个数


        String inCode="";
        for(int i = 0; i < input.size(); i++ ){
            DeviceInfoBean eq = input.get(i);
            if(!"TIMER".equals(eq.getDevice_type())&&!"CLICK".equals(eq.getDevice_type())){
                in ++;
                length += 6;
                String ei1 ="0";
                if(Integer.toHexString(eq.getDevice_ID()).length() < 4){
                    for (int j = 0; j<4- Integer.toHexString(eq.getDevice_ID()).length()-1; j++ ){
                        ei1 += 0;
                    }
                    ei1 += Integer.toHexString(eq.getDevice_ID());
                }else{
                    ei1 = Integer.toHexString(eq.getDevice_ID());
                }
                inCode += (  ei1 + eq.getDevice_status());
            }else if("TIMER".equals(eq.getDevice_type())){
                settime4 = eq.getDevice_status();
            }
        }
        String outCode ="";
        for(int i = 0; i < output.size(); i++ ){
            DeviceInfoBean eq = output.get(i);
            if(!"DELAY".equals(eq.getDevice_type())&&!"PHONE".equals(eq.getDevice_type())){

                String beforeVelue = "ok";
                if(i >0 && output.get(i-1) != null){
                    DeviceInfoBean before = output.get(i-1);
                    beforeVelue = before.getDevice_type();
                }

                if(i==0 || (i-1 >= 0 && !"DELAY".equals(beforeVelue)&&!"PHONE".equals(beforeVelue))){
                    out ++;  //use for get number
                    length += 8;
                    String ei1 ="0";
                    if(Integer.toHexString(eq.getDevice_ID()).length() < 4){
                        for (int j = 0; j<4- Integer.toHexString(eq.getDevice_ID()).length()-1; j++ ){
                            ei1 += 0;
                        }
                        ei1 += Integer.toHexString(eq.getDevice_ID());
                    }else{
                        ei1 = Integer.toHexString(eq.getDevice_ID());
                    }
                    outCode += ( "0000"+ei1 + eq.getDevice_status());
                }else  if("DELAY".equals(beforeVelue)){
                    out ++;  //use for get number
                    length += 6;
                    String ei1 ="0";
                    if(Integer.toHexString(eq.getDevice_ID()).length() < 4){
                        for (int j = 0; j<4- Integer.toHexString(eq.getDevice_ID()).length()-1; j++ ){
                            ei1 += 0;
                        }
                        ei1 += Integer.toHexString(eq.getDevice_ID());
                    }else{
                        ei1 = Integer.toHexString(eq.getDevice_ID());
                    }
                    outCode += ( ei1 + eq.getDevice_status());
                }else  if("PHONE".equals(beforeVelue)){
                    out ++;  //use for get number
                    length += 8;
                    String ei1 ="0";
                    if(Integer.toHexString(eq.getDevice_ID()).length() < 4){
                        for (int j = 0; j<4- Integer.toHexString(eq.getDevice_ID()).length()-1; j++ ){
                            ei1 += 0;
                        }
                        ei1 += Integer.toHexString(eq.getDevice_ID());
                    }else{
                        ei1 = Integer.toHexString(eq.getDevice_ID());
                    }
                    outCode += ( "0000"+ei1 + eq.getDevice_status());
                }
            }else if("DELAY".equals(eq.getDevice_type())){
                length += 2;
                //settime4 = eq.getState();
                outCode += UnitTools.timeDecode(eq.getDevice_status(),4);
            }
        }
        String oooo ="0";
        if(Integer.toHexString(length+32).length() < 4){
            for (int i = 0; i<4-Integer.toHexString(length+32).length()-1; i++ ){
                oooo += 0;
            }
            oooo += Integer.toHexString(length+32);
        }else{
            oooo = Integer.toHexString(length+32);
        }

        String ooo = "";
        int amid2 = mAutomation.getMid();
        String amid1 = Integer.toHexString(amid2);
        if(Integer.toHexString(mAutomation.getMid()).length()<2){
            for(int i =0; i<2 - amid1.length();i++){
                ooo += 0;
            }
            ooo +=amid1;
        }else{
            ooo = amid1;
        }

        String oo = "0";
        if(Integer.toHexString(in).length()<2){
            oo = oo + Integer.toHexString(in);
        }else{
            oo = Integer.toHexString(in);
        }

        String o ="0";
        if(Integer.toHexString(out).length()<2){
            o += Integer.toHexString(out);
        }

        String ds = CoderALiUtils.getAscii(name);

        String deCode =  oooo + ooo + ds + cm3 + UnitTools.timeDecode(settime4,6) + click5 + inf6 + oo + o +inCode +outCode;
        return deCode;
    }

    public static String getMessageCode(){
        if(mMessageCode!=null) {
            return mMessageCode;
        }
        return null;
    }

    public static int getMid(Context context, String deviceName){
        List<Integer> list = AutomationDaoUtil.getInstance().findAllMid(deviceName);

        if(list.size()==0){
            return 1;
        }else if(list.size()==1){
            if(list.get(0)==1){
                return 2;
            }else{
                return 1;
            }
        }else{
            int m = 0;
            for(int i=0;i<list.size()-1;i++){
                if(i==0){
                    int d = list.get(i);
                    if(d!=1){
                        m = 1;
                        break;
                    }
                    else {
                        if( (list.get(i)+1) < list.get(i+1)){
                            m = list.get(i)+1;
                            break;
                        }else{
                            m = list.get(i)+2;
                        }
                    }
                }else{
                    if( (list.get(i)+1) < list.get(i+1)){
                        m = list.get(i)+1;
                        break;
                    }else{
                        m = list.get(i)+2;
                    }
                }
            }
            return m;
        }
    }
}
