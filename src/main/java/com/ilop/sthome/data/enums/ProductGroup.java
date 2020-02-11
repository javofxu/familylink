package com.ilop.sthome.data.enums;

import com.ilop.sthome.data.device.DeviceTypeEnum;
import com.siterwell.familywellplus.R;

/**
 * @author skygge
 * @date 2020-01-01.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：所有产品类型/名称/说明书
 */

public enum ProductGroup {

    // 网关
    EE_DEV_GATEWAY("GATEWAY", DeviceTypeEnum.SMART_DEVICE_GATEWAY, R.string.gateway,
            R.mipmap.gs188,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell网关说明书&type=zh"),

    //智能插座
    EE_DEV_SOCKET1("0200", DeviceTypeEnum.SMART_DEVICE_ENVIRONMENT, R.string.socket,
            R.mipmap.gs350,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell插座说明书&type=zh"),

    //按键
    EE_DEV_BUTTON1("0301", DeviceTypeEnum.SMART_DEVICE_ENVIRONMENT, R.string.button,
            R.mipmap.gs585, "http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell按键说明书&type=zh"),

    //温控器
    EE_TEMP_CONTROL1("0215", DeviceTypeEnum.SMART_DEVICE_ENVIRONMENT, R.string.temp_controler,
            R.mipmap.gs361, "http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell温控器说明书&type=zh"),

    //温湿度探测器
    EE_DEV_THCHECK1("0102", DeviceTypeEnum.SMART_DEVICE_ENVIRONMENT, R.string.thcheck,
            R.mipmap.gs240d, "http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell温湿度探测器说明书&type=zh"),

    //场景开关
    EE_DEV_MODE_BUTTON("0305", DeviceTypeEnum.SMART_DEVICE_ENVIRONMENT, R.string.mode_button,
            R.mipmap.gs584, "http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell场景开关说明书&type=zh"),

    //双路开关
    EE_TWO_CHANNEL_SOCKET1("0201", DeviceTypeEnum.SMART_DEVICE_ENVIRONMENT, R.string.two_channel_socket,
            R.mipmap.gs344, "http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell双路开关说明书&type=zh"),


    // 摄像机
    EE_DEV_IPC("IPC", DeviceTypeEnum.SMART_DEVICE_THEFT, R.string.ali_camera,
            R.mipmap.gs290,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell摄像头说明书&type=zh"),

    //门锁
    EE_DEV_LOCK("1213", DeviceTypeEnum.SMART_DEVICE_THEFT, R.string.lock,
            R.mipmap.gs920, "http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell智能门锁说明书&type=zh"),

    // 门磁
    EE_DEV_DOOR1("0101", DeviceTypeEnum.SMART_DEVICE_THEFT, R.string.door,
            R.mipmap.gs320d,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell门磁说明书&type=zh"),

    //人体移动探测器
    EE_DEV_PIR1("0100", DeviceTypeEnum.SMART_DEVICE_THEFT, R.string.pir,
            R.mipmap.gs300d, "http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellPIR人体移动探测器说明书&type=zh"),

    //SOS按钮
    EE_DEV_SOS1("0300", DeviceTypeEnum.SMART_DEVICE_THEFT, R.string.soskey,
            R.mipmap.gs390, "http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellSOS报警按钮说明书&type=zh"),

    //室外警笛
    EE_TEMP_OUTDOOR_SIREN3("220E", DeviceTypeEnum.SMART_DEVICE_THEFT, R.string.outdoor_siren,
            R.mipmap.gs380, "http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell室外警笛说明书&type=zh"),


    //SM报警器
    EE_DEV_SMALARM1("0001", DeviceTypeEnum.SMART_DEVICE_FIRE, R.string.smalarm,
            R.mipmap.gs530, "http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell烟感说明书&type=zh"),

    //复合型报警器
    EE_DEV_SXSMALARM1("0005", DeviceTypeEnum.SMART_DEVICE_FIRE, R.string.cxsmalarm,
            R.mipmap.gs592, "http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell复合型烟感说明书&type=zh"),

    //水感报警器
    EE_DEV_WTALARM1("0004", DeviceTypeEnum.SMART_DEVICE_FIRE, R.string.wt,
            R.mipmap.gs156, "http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell水感报警器说明书&type=zh"),

    //热感报警器
    EE_DEV_THERMALARM1("0003", DeviceTypeEnum.SMART_DEVICE_FIRE, R.string.thermalalarm,
            R.mipmap.gs412, "http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell热感报警器说明书&type=zh"),

    //气体报警器
    EE_DEV_GASALARM1("0002", DeviceTypeEnum.SMART_DEVICE_FIRE, R.string.gasalarm,
            R.mipmap.gs870w, "http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellGAS报警器说明书&type=zh"),

    //CO报警器
    EE_DEV_COALARM1("0000", DeviceTypeEnum.SMART_DEVICE_FIRE, R.string.coalarm,
            R.mipmap.gs816, "http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellCO报警器说明书&type=zh");

    private String devType;
    private int deviceType;
    private int devResId;
    private int drawResId;
    private String ins_url;
    private String ins_url_en;

    public String getIns_url() {
        return ins_url;
    }

    public String getIns_url_en() {
        return ins_url_en;
    }

    ProductGroup(String devType, int deviceType, int resId, int iconId, String ins_url) {
        this.devType = devType;
        this.deviceType = deviceType;
        this.devResId = resId;
        this.drawResId = iconId;
        this.ins_url= ins_url;
        this.ins_url_en = this.ins_url.replaceAll("type=zh","type=en");
    }

    public int getDeviceType() {
        return deviceType;
    }

    /**
     * 获取设备类型的字符串ID
     *
     * @return 设备类型字符串ID
     */
    public int getTypeStrId() {
        return this.devResId;
    }

    /**
     * 获取设备图标的资源ID
     * @return
     */
    public int getDrawableResId() {
        return this.drawResId;
    }


    /**
     * 获取设备类型的索引号
     *
     * @return
     */
    public String getDevType() {
        return this.devType;
    }


    public static ProductGroup getType(String devType1) {
        for (ProductGroup SmartProduct : ProductGroup.values()) {
            if (SmartProduct.getDevType().equals(devType1)) {
                return SmartProduct;
            }
        }
        return null;
    }
}
