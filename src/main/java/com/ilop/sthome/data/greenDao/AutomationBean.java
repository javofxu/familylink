package com.ilop.sthome.data.greenDao;

import android.content.Context;
import android.text.TextUtils;

import com.ilop.sthome.utils.CoderALiUtils;
import com.ilop.sthome.utils.greenDao.DeviceDaoUtil;
import com.ilop.sthome.utils.greenDao.converter.DeviceConverter;
import com.ilop.sthome.utils.greenDao.converter.SceneModelConverter;
import com.siterwell.familywellplus.R;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * @author skygge
 * @date 2020-02-17.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：自动化类
 */

@Entity
public class AutomationBean implements Serializable {

    static final long serialVersionUID = 42L;

    @Id(autoincrement = true)
    private Long id;

    private String name;

    private String code;

    private int mid;

    private String deviceName;

    @Convert(columnType = String.class, converter = SceneModelConverter.class)
    private SceneModelBean model;//实体类套实体类

    @Convert(columnType = String.class, converter = DeviceConverter.class)
    private List<DeviceInfoBean> inputList; //实体类中输入数据

    @Convert(columnType = String.class, converter = DeviceConverter.class)
    private List<DeviceInfoBean> outputList; //实体类中输出数据



    @Generated(hash = 2013169770)
    public AutomationBean(Long id, String name, String code, int mid, String deviceName, SceneModelBean model, List<DeviceInfoBean> inputList,
            List<DeviceInfoBean> outputList) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.mid = mid;
        this.deviceName = deviceName;
        this.model = model;
        this.inputList = inputList;
        this.outputList = outputList;
    }

    @Generated(hash = 207089853)
    public AutomationBean() {
    }

    

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMid() {
        return this.mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public SceneModelBean getModel() {
        return this.model;
    }

    public void setModel(SceneModelBean model) {
        this.model = model;
    }

    public List<DeviceInfoBean> getInputList() {
        return this.inputList;
    }

    public void setInputList(List<DeviceInfoBean> inputList) {
        this.inputList = inputList;
    }

    public List<DeviceInfoBean> getOutputList() {
        return this.outputList;
    }

    public void setOutputList(List<DeviceInfoBean> outputList) {
        this.outputList = outputList;
    }

    public void create(String deviceName){
        setDeviceName(deviceName);
        String value = getCode();
        if(value.length()>=11){
            try {
                String mName = value.substring(6,38);
                int mid = Integer.parseInt(value.substring(4,6),16);
                String name = CoderALiUtils.getStringFromAscii(mName);
                setMid(mid);
                setName(name);

            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
    }

    public SceneModelBean getSceneModel(){
        String length = code.substring(0,4);
        int sLength = Integer.parseInt(length,16)-16;
        if (sLength >= 11){
            model = new SceneModelBean();
            model.setSceneNum(String.valueOf(Integer.parseInt(code.substring(4,6),16)));
            model.setCondition(code.substring(38,40));
            model.setTimer(timeHexToO(code.substring(40,46),6));
            model.setClickAction(code.substring(46,48));
            model.setInform(code.substring(48,50));
            model.setInputNum(code.substring(50,52));
            model.setOutputNum(code.substring(52,54));
        }
        return model;
    }

    public List<DeviceInfoBean> getInput(Context context, String deviceName){
        int iLength = Integer.parseInt(code.substring(50,52),16);
        String inputCode = code.substring(54,54+iLength*12);
        inputList = new ArrayList<>();
        if(!"000000".equals(timeHexToO(code.substring(40,46),6))){
            DeviceInfoBean equipmentBean = new DeviceInfoBean();
            equipmentBean.setDevice_ID(-1);
            equipmentBean.setDevice_type("TIMER");
            equipmentBean.setDevice_status(timeHexToO(code.substring(40,46),6));
            equipmentBean.setSubdeviceName(context.getResources().getString(R.string.timer));
            inputList.add(equipmentBean);
        }

        String click = code.substring(46,48);
        if("ab".equals(click) || "AB".equals(click) ){
            DeviceInfoBean equipmentBean = new DeviceInfoBean();
            equipmentBean.setDevice_ID(-1);
            equipmentBean.setDevice_type("CLICK");
            equipmentBean.setDevice_status(click);
            equipmentBean.setSubdeviceName(context.getResources().getString(R.string.clickTo));
            inputList.add(equipmentBean);
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

                inputList.add(equipmentBean);
                if(i < iLength){
                    inputCode = inputCode.substring(12);
                }
            }
        }
        return inputList;
    }

    public List<DeviceInfoBean> getOutput(Context context,String deviceName){
        outputList = new ArrayList<>();
        int iLength = Integer.parseInt(code.substring(50,52),16);
        String outputCode = code.substring(54+iLength*12);

        if("AC".equals(code.substring(48,50).toUpperCase()) ){
            DeviceInfoBean equipmentBean = new DeviceInfoBean();
            equipmentBean.setDevice_ID(-1);
            equipmentBean.setDevice_type("PHONE");
            equipmentBean.setDevice_status(code.substring(48,50));
            outputList.add(equipmentBean);
        }

        int outNum = outputCode.length()/16;
        for(int i = 1 ; i<=outNum;i++){
            String delay = outputCode.substring(0,4);
            String eqid = outputCode.substring(4,8);
            String status = outputCode.substring(8,16);
            if(!"0000".equals(delay)){
                DeviceInfoBean equipmentBean = new DeviceInfoBean();
                equipmentBean.setDevice_status(timeHexToO(delay,4));
                equipmentBean.setDevice_type("DELAY");
                equipmentBean.setDevice_ID(Integer.parseInt(eqid,16));
                equipmentBean.setSubdeviceName(context.getResources().getString(R.string.delay));
                outputList.add(equipmentBean);
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
            outputList.add(equipmentBean1);
            if(i < outNum){
                outputCode = outputCode.substring(16);
            }
        }
        return outputList;
    }


    /**
     * @param time
     * @return
     */
    private String timeHexToO(String time,int num){
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
