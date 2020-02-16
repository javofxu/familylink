package com.ilop.sthome.data.bean;

import android.content.Context;
import android.text.TextUtils;

import com.ilop.sthome.data.greenDao.DeviceInfoBean;
import com.ilop.sthome.utils.CoderALiUtils;
import com.ilop.sthome.utils.SceneModelUtil;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.siterwell.familywellplus.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gc-0001 on 2016/12/14.
 */
public class SceneAliBean implements Serializable {

    private String name;
    private String code;
    private int mid;
    private String deviceName;
    private SceneModelUtil sp;
    private List<DeviceInfoBean> input;
    private List<DeviceInfoBean> output;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public String toString() {
        return "SceneAliBean{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", mid=" + mid +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }


    public void create(String deviceName){
        setDeviceName(deviceName);
        String value = getCode();
        if(value.length()>=11){
            try {
                String myname = value.substring(6,38);
                int mid = Integer.parseInt(value.substring(4,6),16);
                String name = CoderALiUtils.getStringFromAscii(myname);
                if(name!=null){
                    setMid(mid);
                    setName(name);

                }
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
    }

    public SceneModelUtil getSp(){
             String length = code.substring(0,4);
             int sLength = Integer.parseInt(length,16)-16;
             if (sLength >= 11){
                 sp = new SceneModelUtil();
                 sp.setSceneNum(String.valueOf(Integer.parseInt(code.substring(4,6),16)));
                 sp.setCondition(code.substring(38,40));
                 sp.setTimer(timeHextoO(code.substring(40,46),6));
                 sp.setClickAction(code.substring(46,48));
                 sp.setInform(code.substring(48,50));
                 sp.setInputNum(code.substring(50,52));
                 sp.setOutputNum(code.substring(52,54));
             }
             return sp;
    }


    public List<DeviceInfoBean> getInput(Context context,String deviceName){
        int iLength = Integer.parseInt(code.substring(50,52),16);
        String inputCode = code.substring(54,54+iLength*12);
        input = new ArrayList<>();
        if(!"000000".equals(timeHextoO(code.substring(40,46),6))){
            DeviceInfoBean equipmentBean = new DeviceInfoBean();
            equipmentBean.setDevice_ID(-1);
            equipmentBean.setDevice_type("TIMER");
            equipmentBean.setDevice_status(timeHextoO(code.substring(40,46),6));
            equipmentBean.setSubdeviceName(context.getResources().getString(R.string.timer));
            input.add(equipmentBean);
        }

        String click = code.substring(46,48);
        if("ab".equals(click) || "AB".equals(click) ){
            DeviceInfoBean equipmentBean = new DeviceInfoBean();
            equipmentBean.setDevice_ID(-1);
            equipmentBean.setDevice_type("CLICK");
            equipmentBean.setDevice_status(click);
            equipmentBean.setSubdeviceName(context.getResources().getString(R.string.clickTo));
            input.add(equipmentBean);
        }

        if(iLength>0){
            for(int i = 1 ; i<= iLength;i++){
                DeviceInfoBean devType= DeviceDaoUtil.getInstance().findByDeviceId(deviceName,Integer.parseInt(inputCode.substring(0,4),16));
                DeviceInfoBean equipmentBean = new DeviceInfoBean();
                equipmentBean.setDevice_ID(Integer.parseInt(inputCode.substring(0,4),16));
                equipmentBean.setDevice_status(inputCode.substring(4,12));
                if(devType!=null){
                    equipmentBean.setDevice_type(devType.getDevice_type());
                    equipmentBean.setSubdeviceName(devType.getSubdeviceName());
                }

                input.add(equipmentBean);
                if(i < iLength){
                    inputCode = inputCode.substring(12);
                }
            }
        }
        return input;
    }
    public List<DeviceInfoBean> getOutput(Context context,String deviceName){
        output = new ArrayList<>();
        int iLength = Integer.parseInt(code.substring(50,52),16);
        String outputCode = code.substring(54+iLength*12);

        if("AC".equals(code.substring(48,50).toUpperCase()) ){
            DeviceInfoBean equipmentBean = new DeviceInfoBean();
            equipmentBean.setDevice_ID(-1);
            equipmentBean.setDevice_type("PHONE");
            equipmentBean.setDevice_status(code.substring(48,50));
            output.add(equipmentBean);
        }

        int outNum = outputCode.length()/16;
        for(int i = 1 ; i<=outNum;i++){
            String delay = outputCode.substring(0,4);
            String eqid = outputCode.substring(4,8);
            String status = outputCode.substring(8,16);
            if(!"0000".equals(delay)){
                DeviceInfoBean equipmentBean = new DeviceInfoBean();
                equipmentBean.setDevice_status(timeHextoO(delay,4));
                equipmentBean.setDevice_type("DELAY");
                equipmentBean.setDevice_ID(Integer.parseInt(eqid,16));
                equipmentBean.setSubdeviceName(context.getResources().getString(R.string.delay));
                output.add(equipmentBean);
            }
            DeviceInfoBean devType= DeviceDaoUtil.getInstance().findByDeviceId(deviceName,Integer.parseInt(eqid,16));
            DeviceInfoBean equipmentBean1 = new DeviceInfoBean();
            equipmentBean1.setDevice_ID(Integer.parseInt(eqid,16));
            equipmentBean1.setDevice_status(status);
            if(Integer.parseInt(eqid,16)==0){
                equipmentBean1.setDevice_type("GATEWAY");
                equipmentBean1.setSubdeviceName(context.getResources().getString(R.string.ali_gateway));
            }else {
                if(devType!=null){
                    equipmentBean1.setDevice_type(devType.getDevice_type());
                    equipmentBean1.setSubdeviceName(devType.getSubdeviceName());
                }
            }
            output.add(equipmentBean1);
            if(i < outNum){
                outputCode = outputCode.substring(16);
            }
        }
        return output;
    }

    /**
     * @param time
     * @return
     */
    private String timeHextoO(String time,int num){
        String newTime = null;
        switch (num){
            case 6:
                if(!TextUtils.isEmpty(time)&& time.length() == 6){
                    String day = time.substring(0,2);
                    String hour = time.substring(2,4);
                    String min = time.substring(4,6);
                    String newHour =
                            String.valueOf(Integer.parseInt(hour,16)).length()==2 ?
                                    String.valueOf(Integer.parseInt(hour,16)): "0"+ Integer.parseInt(hour, 16);
                    String newMin = String.valueOf(Integer.parseInt(min,16)).length()==2 ?
                            String.valueOf(Integer.parseInt(min,16)): "0"+ Integer.parseInt(min, 16);
                    newTime = day+newHour+newMin;
                }
                break;
            case 4:
                if(!TextUtils.isEmpty(time)&& time.length() == 4){
                    String min = time.substring(0,2);
                    String sec = time.substring(2,4);
                    String newMin =
                            String.valueOf(Integer.parseInt(min,16)).length()==2 ?
                                    String.valueOf(Integer.parseInt(min,16)): "0"+ Integer.parseInt(min, 16);
                    String newSec = String.valueOf(Integer.parseInt(sec,16)).length()==2 ?
                            String.valueOf(Integer.parseInt(sec,16)): "0"+ Integer.parseInt(sec, 16);
                    newTime = newMin+newSec;
                }
                break;
            default:
                break;
        }

        return newTime;
    }
}
