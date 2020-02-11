package com.ilop.sthome.utils;

import android.content.Context;

import com.ilop.sthome.data.enums.SmartProduct;
import com.siterwell.familywellplus.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ST-020111 on 2017/3/24.
 */

public class HistoryDataUtil {

    /**
     * 得到现在时间
     *
     * @return 字符串 yyyyMMdd HHmmss
     */
    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm:ss.SSS+Z");
        String dateString1 = formatter1.format(currentTime);
        return dateString+"T"+dateString1;
    }

    /**
     * 得到现在小时
     */
    public static String getHour() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    public static String getAlert(Context context,String equipmenttype, String eqstatus){

        try {
            String alertstatus = eqstatus.substring(4,6);
            String battery = eqstatus.substring(2,4);
            if(SmartProduct.EE_DEV_DOOR1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_DOOR2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_DOOR3.equals(SmartProduct.getType(equipmenttype))){
                  if("55".equals(alertstatus)){
                     return context.getResources().getStringArray(R.array.door_actions)[0];
                  }else if("AA".equals(alertstatus)){
                      if(Integer.parseInt(battery,16)<=15){
                          return context.getResources().getString(R.string.low_battery);
                      }else {
                      return context.getResources().getStringArray(R.array.door_actions)[1];
                      }
                  }else if("66".equals(alertstatus)){
                      return context.getResources().getStringArray(R.array.door_actions)[2];
                  }else if("FF".equals(alertstatus)){
                      return context.getResources().getString(R.string.offline);
                  }else {
                      if(Integer.parseInt(battery,16)<=15){
                          return context.getResources().getString(R.string.low_battery);
                      }else {
                      return context.getResources().getString(R.string.offline);
                  }
                  }
            }else if(SmartProduct.EE_DEV_SOS1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SOS2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SOS3.equals(SmartProduct.getType(equipmenttype))){
                if("55".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.sos_signs)[0];
                }else if("66".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.sos_signs)[1];
                }else if("AA".equals(alertstatus)){
                    if(Integer.parseInt(battery,16)<=15){
                        return context.getResources().getString(R.string.low_battery);
                    }else {
                        return context.getResources().getString(R.string.normal);
                    }
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if(Integer.parseInt(battery,16)<=15){
                        return context.getResources().getString(R.string.low_battery);
                    }else {
                    return context.getResources().getString(R.string.offline);
                }
                }
            }else if(SmartProduct.EE_DEV_PIR1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_PIR2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_PIR3.equals(SmartProduct.getType(equipmenttype))){
                if("55".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.pir_signs)[0];
                }else if("AA".equals(alertstatus)){
                    if(Integer.parseInt(battery,16)<=15){
                        return context.getResources().getString(R.string.low_battery);
                    }else {
                        return context.getResources().getStringArray(R.array.pir_signs)[1];
                    }
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if(Integer.parseInt(battery,16)<=15){
                        return context.getResources().getString(R.string.low_battery);
                    }else {
                    return context.getResources().getString(R.string.offline);
                }
                }
            }else if(SmartProduct.EE_DEV_SMALARM1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SMALARM2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SMALARM3.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SMALARM4.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SMALARM5.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SMALARM6.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SMALARM7.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SMALARM8.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SMALARM9.equals(SmartProduct.getType(equipmenttype))){
                if("BB".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert7);
                }else if("55".equals(alertstatus)){
                    return context.getResources().getString(R.string.sthalert);
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if("AA".equals(alertstatus)){
                        if(Integer.parseInt(battery,16)<=15){
                            return context.getResources().getString(R.string.low_battery);
                        }else {
                            return context.getResources().getString(R.string.normal);
                        }
                    }else{
                    return context.getResources().getString(R.string.offline);
                }

                }
            }else if(SmartProduct.EE_DEV_COALARM1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_COALARM2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_COALARM3.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_COALARM4.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_COALARM5.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_COALARM6.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_COALARM7.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_COALARM8.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_COALARM9.equals(SmartProduct.getType(equipmenttype))){
                if("BB".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert7);
                }else if("55".equals(alertstatus)){
                    return context.getResources().getString(R.string.sthalert);
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if("AA".equals(alertstatus)){
                        if(Integer.parseInt(battery,16)<=15){
                            return context.getResources().getString(R.string.low_battery);
                        }else {
                            return context.getResources().getString(R.string.normal);
                        }
                    }else{
                    return context.getResources().getString(R.string.offline);
                }
                }
            }else if(SmartProduct.EE_DEV_WTALARM1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_WTALARM2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_WTALARM3.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_WTALARM4.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_WTALARM5.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_WTALARM6.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_WTALARM7.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_WTALARM8.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_WTALARM9.equals(SmartProduct.getType(equipmenttype))){
                if("BB".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert7);
                }else if("55".equals(alertstatus)){
                    return context.getResources().getString(R.string.sthalert);
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if("AA".equals(alertstatus)){
                        if(Integer.parseInt(battery,16)<=15){
                            return context.getResources().getString(R.string.low_battery);
                        }else {
                            return context.getResources().getString(R.string.normal);
                        }
                    }else{
                    return context.getResources().getString(R.string.offline);
                }
                }
            }else if(SmartProduct.EE_DEV_THCHECK1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THCHECK2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THCHECK3.equals(SmartProduct.getType(equipmenttype))){
                if("AA".equals(alertstatus)){
                    if(Integer.parseInt(battery,16)<=15){
                        return context.getResources().getString(R.string.low_battery);
                    }else {
                        return context.getResources().getString(R.string.normal);
                    }
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else{
                return context.getResources().getString(R.string.offline);
                }
            }else if(SmartProduct.EE_DEV_SXSMALARM1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SXSMALARM2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SXSMALARM3.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SXSMALARM4.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SXSMALARM5.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SXSMALARM6.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SXSMALARM7.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SXSMALARM8.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SXSMALARM9.equals(SmartProduct.getType(equipmenttype))){
                if("17".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert7);
                }else if("19".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert9);
                }else if("12".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert2);
                }else if("15".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert5);
                }else if("1B".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert11);
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {

                    if("AA".equals(alertstatus)){
                        if(Integer.parseInt(battery,16)<=15){
                            return context.getResources().getString(R.string.low_battery);
                        }else {
                            return context.getResources().getString(R.string.normal);
                        }
                    }else{
                    return context.getResources().getString(R.string.offline);
                }
                }
            }else if(SmartProduct.EE_DEV_GASALARM1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_GASALARM2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_GASALARM3.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_GASALARM4.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_GASALARM5.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_GASALARM6.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_GASALARM7.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_GASALARM8.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_GASALARM9.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_GASALARM10.equals(SmartProduct.getType(equipmenttype))){
                if("BB".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert7);
                }else if("55".equals(alertstatus)){
                    return context.getResources().getString(R.string.sthalert);
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if("AA".equals(alertstatus)){
                        if(Integer.parseInt(battery,16)<=15){
                            return context.getResources().getString(R.string.low_battery);
                        }else {
                            return context.getResources().getString(R.string.normal);
                        }
                    }else{
                    return context.getResources().getString(R.string.offline);
                }
                }
            }else if(SmartProduct.EE_DEV_THERMALARM1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THERMALARM2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THERMALARM3.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THERMALARM4.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THERMALARM5.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THERMALARM6.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THERMALARM7.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THERMALARM8.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THERMALARM9.equals(SmartProduct.getType(equipmenttype))){
                if("BB".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert7);
                }else if("55".equals(alertstatus)){
                    return context.getResources().getString(R.string.sthalert);
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if("AA".equals(alertstatus)){
                        if(Integer.parseInt(battery,16)<=15){
                            return context.getResources().getString(R.string.low_battery);
                        }else {
                            return context.getResources().getString(R.string.normal);
                        }
                    }else{
                    return context.getResources().getString(R.string.offline);
                }
                }
            }else if(SmartProduct.EE_DEV_MODE_BUTTON.equals(SmartProduct.getType(equipmenttype))){
                if("55".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.sos_signs)[0];
                }else if("66".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.sos_signs)[1];
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if("AA".equals(alertstatus)){
                        if(Integer.parseInt(battery,16)<=15){
                            return context.getResources().getString(R.string.low_battery);
                        }else {
                            return context.getResources().getString(R.string.normal);
                        }
                    }else{
                    return context.getResources().getString(R.string.offline);
                }
                }
            }else if(SmartProduct.EE_DEV_LOCK.equals(SmartProduct.getType(equipmenttype))){
                if("50".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.lock_input)[0];
                }else if("51".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.lock_input)[1];
                }else if("52".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.lock_input)[2];
                }else if("53".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.lock_input)[3];
                }else if("10".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.lock_input)[4];
                }else if("20".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.lock_input)[5];
                }else if("30".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.lock_input)[6];
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if("AA".equals(alertstatus)){
                        if(Integer.parseInt(battery,16)<=15){
                            return context.getResources().getString(R.string.low_battery);
                        }else {
                            return context.getResources().getString(R.string.normal);
                        }
                    }else{
                        return context.getResources().getString(R.string.offline);
                    }
                }
            }else if(SmartProduct.EE_DEV_BUTTON1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_BUTTON2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_BUTTON3.equals(SmartProduct.getType(equipmenttype))){
                if("01".equals(alertstatus)){
                    return context.getResources().getString(R.string.trigger);
                }
                else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if("AA".equals(alertstatus)){
                        if(Integer.parseInt(battery,16)<=15){
                            return context.getResources().getString(R.string.low_battery);
                        }else {
                            return context.getResources().getString(R.string.normal);
                        }
                    }else{
                        return context.getResources().getString(R.string.offline);
                    }
                }
            }else if(SmartProduct.EE_DEV_SOCKET1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SOCKET2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SOCKET3.equals(SmartProduct.getType(equipmenttype))){
                String alertstatus1 = eqstatus.substring(6,8);
                if("01".equals(alertstatus1)){
                    return context.getResources().getString(R.string.open);
                }else if("00".equals(alertstatus1)){
                    return context.getResources().getString(R.string.close);
                }else {
                    return context.getResources().getString(R.string.offline);
                }
            }else if(SmartProduct.EE_TWO_CHANNEL_SOCKET1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_TWO_CHANNEL_SOCKET2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_TWO_CHANNEL_SOCKET3.equals(SmartProduct.getType(equipmenttype))){
                if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    return context.getResources().getString(R.string.sthalert);
                }
            }else if(SmartProduct.EE_TEMP_CONTROL1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_TEMP_CONTROL2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_TEMP_CONTROL3.equals(SmartProduct.getType(equipmenttype))){
                if("AA".equals(alertstatus)){
                    if(Integer.parseInt(battery,16)<=15)
                        return context.getResources().getString(R.string.low_battery);
                    else
                        return context.getResources().getString(R.string.normal);
                }else{
                    return context.getResources().getString(R.string.offline);
                }
            }
            else{
                return context.getResources().getString(R.string.offline);
            }

        }catch (Exception e){
            return "";
        }


    }


    public static String getsnapshot(Context context,String equipmenttype, String eqstatus){

        try {
            String alertstatus = eqstatus.substring(4,6);
            String battery = eqstatus.substring(2,4);
            if(SmartProduct.EE_DEV_DOOR1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_DOOR2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_DOOR3.equals(SmartProduct.getType(equipmenttype))){
                if("55".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.door_actions)[0];
                }else if("AA".equals(alertstatus)){
                    if(Integer.parseInt(battery,16)<=15){
                        return context.getResources().getString(R.string.low_battery);
                    }else {
                        return context.getResources().getStringArray(R.array.door_actions)[1];
                    }
                }else if("66".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.door_actions)[2];
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if(Integer.parseInt(battery,16)<=15){
                        return context.getResources().getString(R.string.low_battery);
                    }else {
                        return context.getResources().getString(R.string.offline);
                    }
                }
            }else if(SmartProduct.EE_DEV_SOS1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SOS2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SOS3.equals(SmartProduct.getType(equipmenttype))){
                if("55".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.sos_signs)[0];
                }else if("66".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.sos_signs)[1];
                }else if("AA".equals(alertstatus)){
                    if(Integer.parseInt(battery,16)<=15){
                        return context.getResources().getString(R.string.low_battery);
                    }else {
                        return context.getResources().getString(R.string.normal);
                    }
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if(Integer.parseInt(battery,16)<=15){
                        return context.getResources().getString(R.string.low_battery);
                    }else {
                        return context.getResources().getString(R.string.offline);
                    }
                }
            }else if(SmartProduct.EE_DEV_PIR1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_PIR2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_PIR3.equals(SmartProduct.getType(equipmenttype))){
                if("55".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.pir_signs)[0];
                }else if("AA".equals(alertstatus)){
                    if(Integer.parseInt(battery,16)<=15){
                        return context.getResources().getString(R.string.low_battery);
                    }else {
                        return context.getResources().getStringArray(R.array.pir_signs)[1];
                    }
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if(Integer.parseInt(battery,16)<=15){
                        return context.getResources().getString(R.string.low_battery);
                    }else {
                        return context.getResources().getString(R.string.offline);
                    }
                }
            }else if(SmartProduct.EE_DEV_SMALARM1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SMALARM2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SMALARM3.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SMALARM4.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SMALARM5.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SMALARM6.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SMALARM7.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SMALARM8.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SMALARM9.equals(SmartProduct.getType(equipmenttype))){
                if("BB".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert7);
                }else if("55".equals(alertstatus)){
                    return context.getResources().getString(R.string.sthalert);
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if("AA".equals(alertstatus)){
                        if(Integer.parseInt(battery,16)<=15){
                            return context.getResources().getString(R.string.low_battery);
                        }else {
                            return context.getResources().getString(R.string.normal);
                        }
                    }else{
                        return context.getResources().getString(R.string.offline);
                    }

                }
            }else if(SmartProduct.EE_DEV_COALARM1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_COALARM2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_COALARM3.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_COALARM4.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_COALARM5.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_COALARM6.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_COALARM7.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_COALARM8.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_COALARM9.equals(SmartProduct.getType(equipmenttype))){
                if("BB".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert7);
                }else if("55".equals(alertstatus)){
                    return context.getResources().getString(R.string.sthalert);
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if("AA".equals(alertstatus)){
                        if(Integer.parseInt(battery,16)<=15){
                            return context.getResources().getString(R.string.low_battery);
                        }else {
                            return context.getResources().getString(R.string.normal);
                        }
                    }else{
                        return context.getResources().getString(R.string.offline);
                    }
                }
            }else if(SmartProduct.EE_DEV_WTALARM1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_WTALARM2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_WTALARM3.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_WTALARM4.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_WTALARM5.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_WTALARM6.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_WTALARM7.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_WTALARM8.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_WTALARM9.equals(SmartProduct.getType(equipmenttype))){
                if("BB".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert7);
                }else if("55".equals(alertstatus)){
                    return context.getResources().getString(R.string.sthalert);
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if("AA".equals(alertstatus)){
                        if(Integer.parseInt(battery,16)<=15){
                            return context.getResources().getString(R.string.low_battery);
                        }else {
                            return context.getResources().getString(R.string.normal);
                        }
                    }else{
                        return context.getResources().getString(R.string.offline);
                    }
                }
            }else if(SmartProduct.EE_DEV_THCHECK1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THCHECK2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THCHECK3.equals(SmartProduct.getType(equipmenttype))){
                if("AA".equals(alertstatus)){
                    if(Integer.parseInt(battery,16)<=15){
                        return context.getResources().getString(R.string.low_battery);
                    }else {
                        return context.getResources().getString(R.string.normal);
                    }
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else{
                    return context.getResources().getString(R.string.offline);
                }
            }else if(SmartProduct.EE_DEV_SXSMALARM1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SXSMALARM2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SXSMALARM3.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SXSMALARM4.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SXSMALARM5.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SXSMALARM6.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SXSMALARM7.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SXSMALARM8.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SXSMALARM9.equals(SmartProduct.getType(equipmenttype))){
                if("17".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert7);
                }else if("19".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert9);
                }else if("12".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert2);
                }else if("15".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert5);
                }else if("1B".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert11);
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {

                    if("AA".equals(alertstatus)){
                        if(Integer.parseInt(battery,16)<=15){
                            return context.getResources().getString(R.string.low_battery);
                        }else {
                            return context.getResources().getString(R.string.normal);
                        }
                    }else{
                        return context.getResources().getString(R.string.offline);
                    }
                }
            }else if(SmartProduct.EE_DEV_GASALARM1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_GASALARM2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_GASALARM3.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_GASALARM4.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_GASALARM5.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_GASALARM6.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_GASALARM7.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_GASALARM8.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_GASALARM9.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_GASALARM10.equals(SmartProduct.getType(equipmenttype))){
                if("BB".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert7);
                }else if("55".equals(alertstatus)){
                    return context.getResources().getString(R.string.sthalert);
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if("AA".equals(alertstatus)){
                        if(Integer.parseInt(battery,16)<=15){
                            return context.getResources().getString(R.string.low_battery);
                        }else {
                            return context.getResources().getString(R.string.normal);
                        }
                    }else{
                        return context.getResources().getString(R.string.offline);
                    }
                }
            }else if(SmartProduct.EE_DEV_THERMALARM1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THERMALARM2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THERMALARM3.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THERMALARM4.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THERMALARM5.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THERMALARM6.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THERMALARM7.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THERMALARM8.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_THERMALARM9.equals(SmartProduct.getType(equipmenttype))){
                if("BB".equals(alertstatus)){
                    return context.getResources().getString(R.string.cxalert7);
                }else if("55".equals(alertstatus)){
                    return context.getResources().getString(R.string.sthalert);
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if("AA".equals(alertstatus)){
                        if(Integer.parseInt(battery,16)<=15){
                            return context.getResources().getString(R.string.low_battery);
                        }else {
                            return context.getResources().getString(R.string.normal);
                        }
                    }else{
                        return context.getResources().getString(R.string.offline);
                    }
                }
            }else if(SmartProduct.EE_DEV_MODE_BUTTON.equals(SmartProduct.getType(equipmenttype))){
                if("55".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.sos_signs)[0];
                }else if("66".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.sos_signs)[1];
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {

                    if("AA".equals(alertstatus)){
                        if(Integer.parseInt(battery,16)<=15){
                            return context.getResources().getString(R.string.low_battery);
                        }else {
                            return context.getResources().getString(R.string.normal);
                        }
                    }else{
                        int de1 = Integer.parseInt(alertstatus,16);
                        if(de1<=7){
                            if(Integer.parseInt(battery,16)<=15){
                                return context.getResources().getString(R.string.low_battery);
                            }else {
                                return context.getResources().getString(R.string.normal);
                            }
                        }else {
                            return context.getResources().getString(R.string.offline);
                        }
                    }
                }
            }else if(SmartProduct.EE_DEV_LOCK.equals(SmartProduct.getType(equipmenttype))){
                if("50".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.lock_input)[0];
                }else if("51".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.lock_input)[1];
                }else if("52".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.lock_input)[2];
                }else if("53".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.lock_input)[3];
                }else if("10".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.lock_input)[4];
                }else if("20".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.lock_input)[5];
                }else if("30".equals(alertstatus)){
                    return context.getResources().getStringArray(R.array.lock_input)[6];
                }else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if("AA".equals(alertstatus)){
                        if(Integer.parseInt(battery,16)<=15){
                            return context.getResources().getString(R.string.low_battery);
                        }else {
                            return context.getResources().getString(R.string.normal);
                        }
                    }else{
                        return context.getResources().getString(R.string.offline);
                    }
                }
            }else if(SmartProduct.EE_DEV_BUTTON1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_BUTTON2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_BUTTON3.equals(SmartProduct.getType(equipmenttype))){
                if("01".equals(alertstatus)){
                    return context.getResources().getString(R.string.normal);
                }
                else if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    if("AA".equals(alertstatus)){
                        if(Integer.parseInt(battery,16)<=15){
                            return context.getResources().getString(R.string.low_battery);
                        }else {
                            return context.getResources().getString(R.string.normal);
                        }
                    }else{
                        return context.getResources().getString(R.string.offline);
                    }
                }
            }else if(SmartProduct.EE_DEV_SOCKET1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SOCKET2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_DEV_SOCKET3.equals(SmartProduct.getType(equipmenttype))){
                String alertstatus1 = eqstatus.substring(6,8);
                if("01".equals(alertstatus1)){
                    return context.getResources().getString(R.string.open);
                }else if("00".equals(alertstatus1)){
                    return context.getResources().getString(R.string.close);
                }else {
                    return context.getResources().getString(R.string.offline);
                }
            }else if(SmartProduct.EE_TWO_CHANNEL_SOCKET1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_TWO_CHANNEL_SOCKET2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_TWO_CHANNEL_SOCKET3.equals(SmartProduct.getType(equipmenttype))){
                if("FF".equals(alertstatus)){
                    return context.getResources().getString(R.string.offline);
                }else {
                    return context.getResources().getString(R.string.sthalert);
                }
            }else if(SmartProduct.EE_TEMP_CONTROL1.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_TEMP_CONTROL2.equals(SmartProduct.getType(equipmenttype))
                    ||SmartProduct.EE_TEMP_CONTROL3.equals(SmartProduct.getType(equipmenttype))){
                if("AA".equals(alertstatus)){
                    if(Integer.parseInt(battery,16)<=15)
                        return context.getResources().getString(R.string.low_battery);
                    else
                        return context.getResources().getString(R.string.normal);
                }else{
                    return context.getResources().getString(R.string.offline);
                }
            }
            else{
                return context.getResources().getString(R.string.offline);
            }

        }catch (Exception e){
            return "";
        }


    }

    /**
     * 网关的报警（与子设备无关，这些报警由网关判断）
     * @param context
     * @param eqstatus
     * @return
     */
    public static String getGatewayAlert(Context context,String eqstatus){

            if("00000000".equals(eqstatus)){
                return context.getResources().getString(R.string.electric_city_break_off);
            }else if("00000001".equals(eqstatus)){
                return context.getResources().getString(R.string.electric_city_normal);
            }else if("00000002".equals(eqstatus)){
                return context.getResources().getString(R.string.battery_normal);
            }else if("00000003".equals(eqstatus)){
                return context.getResources().getString(R.string.battery_low);
            }else if("00000004".equals(eqstatus)){
                return context.getResources().getString(R.string.ali_gateway_restart);
            }else if("00000005".equals(eqstatus)){
                return context.getResources().getString(R.string.ali_net_break);
            }else if("00000006".equals(eqstatus)){
                return context.getResources().getString(R.string.ali_net_ok);
            }else if("00000007".equals(eqstatus)){
                return context.getResources().getString(R.string.gateway_alert);
            }else if("00000008".equals(eqstatus)){
                return context.getResources().getString(R.string.ali_gateway_init);
            }else if("00000009".equals(eqstatus)){
                return context.getResources().getString(R.string.ali_gateway_config);
            }else if("0000000A".equals(eqstatus)){
                return context.getResources().getString(R.string.ali_gateway_stop_alert);
            }else {
                if(eqstatus.startsWith("0000010") && eqstatus.length()==8){

                    return "Scene";

                }else {
                    return context.getResources().getString(R.string.unknown_error);
                }
            }
    }
}
