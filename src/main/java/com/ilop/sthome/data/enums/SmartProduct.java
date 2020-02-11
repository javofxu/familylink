package com.ilop.sthome.data.enums;

import com.siterwell.familywellplus.R;

/**
 * @author skygge
 * @date 2020-01-01.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：所有产品
 */

public enum SmartProduct {
    // 门磁
    EE_DEV_DOOR1("0101",
            R.string.door,
            R.mipmap.gs320d,R.drawable.config_door,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell门磁说明书&type=zh"),
    EE_DEV_DOOR2("1101",
            R.string.door,
            R.mipmap.gs320d,R.drawable.config_door,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell门磁说明书&type=zh"),
    EE_DEV_DOOR3("2101",
            R.string.door,
            R.mipmap.gs320d,R.drawable.config_door,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell门磁说明书&type=zh"),

    //智能插座
    EE_DEV_SOCKET1("0200",
            R.string.socket,
            R.mipmap.gs350,R.drawable.config_socket,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell插座说明书&type=zh"),
    EE_DEV_SOCKET2("1200",
            R.string.socket,
            R.mipmap.gs350,R.drawable.config_socket,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell插座说明书&type=zh"),
    EE_DEV_SOCKET3("2200",
            R.string.socket,
            R.mipmap.gs350,R.drawable.config_socket,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell插座说明书&type=zh"),

    //人体移动探测器
    EE_DEV_PIR1("0100",
            R.string.pir,
            R.mipmap.gs300d,R.drawable.config_pir,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellPIR人体移动探测器说明书&type=zh"),
    EE_DEV_PIR2("1100",
            R.string.pir,
            R.mipmap.gs300d,R.drawable.config_pir,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellPIR人体移动探测器说明书&type=zh"),
    EE_DEV_PIR3("2100",
            R.string.pir,
            R.mipmap.gs300d,R.drawable.config_pir,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellPIR人体移动探测器说明书&type=zh"),

    //SOS按钮
    EE_DEV_SOS1("0300",
            R.string.soskey,
            R.mipmap.gs390,R.drawable.config_sos,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellSOS报警按钮说明书&type=zh"),
    EE_DEV_SOS2("1300",
            R.string.soskey,
            R.mipmap.gs390,R.drawable.config_sos,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellSOS报警按钮说明书&type=zh"),
    EE_DEV_SOS3("2300",
            R.string.soskey,
            R.mipmap.gs390,R.drawable.config_sos,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellSOS报警按钮说明书&type=zh"),

    //SM报警器
    EE_DEV_SMALARM1("0001",
            R.string.smalarm,
            R.mipmap.gs530,R.drawable.config_sm,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell烟感说明书&type=zh"),
    EE_DEV_SMALARM2("1001",
            R.string.smalarm,
            R.mipmap.gs530,R.drawable.config_sm,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell烟感说明书&type=zh"),
    EE_DEV_SMALARM3("2001",
            R.string.smalarm,
            R.mipmap.gs530,R.drawable.config_sm,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell烟感说明书&type=zh"),
    EE_DEV_SMALARM4("0009",
            R.string.smalarm,
            R.mipmap.gs530,R.drawable.config_sm,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell烟感说明书&type=zh"),
    EE_DEV_SMALARM5("1009",
            R.string.smalarm,
            R.mipmap.gs530,R.drawable.config_sm,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell烟感说明书&type=zh"),
    EE_DEV_SMALARM6("2009",
            R.string.smalarm,
            R.mipmap.gs530,R.drawable.config_sm,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell烟感说明书&type=zh"),
    EE_DEV_SMALARM7("000F",
            R.string.smalarm,
            R.mipmap.gs530,R.drawable.config_sm,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell烟感说明书&type=zh"),
    EE_DEV_SMALARM8("100F",
            R.string.smalarm,
            R.mipmap.gs530,R.drawable.config_sm,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell烟感说明书&type=zh"),
    EE_DEV_SMALARM9("200F",
            R.string.smalarm,
            R.mipmap.gs530,R.drawable.config_sm,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell烟感说明书&type=zh"),

    //CO报警器
    EE_DEV_COALARM1("0000",
            R.string.coalarm,
            R.mipmap.gs816,R.drawable.config_co,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellCO报警器说明书&type=zh"),
    EE_DEV_COALARM2("1000",
            R.string.coalarm,
            R.mipmap.gs816,R.drawable.config_co,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellCO报警器说明书&type=zh"),
    EE_DEV_COALARM3("2000",
            R.string.coalarm,
            R.mipmap.gs816,R.drawable.config_co,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellCO报警器说明书&type=zh"),
    EE_DEV_COALARM4("0008",
            R.string.coalarm,
            R.mipmap.gs816,R.drawable.config_co,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellCO报警器说明书&type=zh"),
    EE_DEV_COALARM5("1008",
            R.string.coalarm,
            R.mipmap.gs816,R.drawable.config_co,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellCO报警器说明书&type=zh"),
    EE_DEV_COALARM6("2008",
            R.string.coalarm,
            R.mipmap.gs816,R.drawable.config_co,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellCO报警器说明书&type=zh"),
    EE_DEV_COALARM7("000E",
            R.string.coalarm,
            R.mipmap.gs816,R.drawable.config_co,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellCO报警器说明书&type=zh"),
    EE_DEV_COALARM8("100E",
            R.string.coalarm,
            R.mipmap.gs816,R.drawable.config_co,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellCO报警器说明书&type=zh"),
    EE_DEV_COALARM9("200E",
            R.string.coalarm,
            R.mipmap.gs816,R.drawable.config_co,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellCO报警器说明书&type=zh"),

    //水感报警器
    EE_DEV_WTALARM1("0004",
            R.string.wt,
            R.mipmap.gs156,R.drawable.config_water,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell水感报警器说明书&type=zh"),
    EE_DEV_WTALARM2("1004",
            R.string.wt,
            R.mipmap.gs156,R.drawable.config_water,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell水感报警器说明书&type=zh"),
    EE_DEV_WTALARM3("2004",
            R.string.wt,
            R.mipmap.gs156,R.drawable.config_water,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell水感报警器说明书&type=zh"),
    EE_DEV_WTALARM4("000C",
            R.string.wt,
            R.mipmap.gs156,R.drawable.config_water,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell水感报警器说明书&type=zh"),
    EE_DEV_WTALARM5("100C",
            R.string.wt,
            R.mipmap.gs156,R.drawable.config_water,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell水感报警器说明书&type=zh"),
    EE_DEV_WTALARM6("200C",
            R.string.wt,
            R.mipmap.gs156,R.drawable.config_water,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell水感报警器说明书&type=zh"),
    EE_DEV_WTALARM7("0012",
            R.string.wt,
            R.mipmap.gs156,R.drawable.config_water,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell水感报警器说明书&type=zh"),
    EE_DEV_WTALARM8("1012",
            R.string.wt,
            R.mipmap.gs156,R.drawable.config_water,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell水感报警器说明书&type=zh"),
    EE_DEV_WTALARM9("2012",
            R.string.wt,
            R.mipmap.gs156,R.drawable.config_water,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell水感报警器说明书&type=zh"),

    //温湿度探测器
    EE_DEV_THCHECK1("0102",
            R.string.thcheck,
            R.mipmap.gs240d,R.drawable.config_thcheck,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell温湿度探测器说明书&type=zh"),
    EE_DEV_THCHECK2("1102",
            R.string.thcheck,
            R.mipmap.gs240d,R.drawable.config_thcheck,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell温湿度探测器说明书&type=zh"),
    EE_DEV_THCHECK3("2102",
            R.string.thcheck,
            R.mipmap.gs240d,R.drawable.config_thcheck,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell温湿度探测器说明书&type=zh"),
    //按键
    EE_DEV_BUTTON1("0301",
            R.string.button,
            R.mipmap.gs585,R.drawable.config_button,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell按键说明书&type=zh"),
    EE_DEV_BUTTON2("1301",
            R.string.button,
            R.mipmap.gs585,R.drawable.config_button,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell按键说明书&type=zh"),
    EE_DEV_BUTTON3("2301",
            R.string.button,
            R.mipmap.gs585,R.drawable.config_button,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell按键说明书&type=zh"),

    //复合型报警器
    EE_DEV_SXSMALARM1("0005",
            R.string.cxsmalarm,
            R.mipmap.gs592,R.drawable.config_cxsm,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell复合型烟感说明书&type=zh"),
    EE_DEV_SXSMALARM2("1005",
            R.string.cxsmalarm,
            R.mipmap.gs592,R.drawable.config_cxsm,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell复合型烟感说明书&type=zh"),
    EE_DEV_SXSMALARM3("2005",
            R.string.cxsmalarm,
            R.mipmap.gs592,R.drawable.config_cxsm,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell复合型烟感说明书&type=zh"),
    EE_DEV_SXSMALARM4("000D",
            R.string.cxsmalarm,
            R.mipmap.gs592,R.drawable.config_cxsm,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell复合型烟感说明书&type=zh"),
    EE_DEV_SXSMALARM5("100D",
            R.string.cxsmalarm,
            R.mipmap.gs592,R.drawable.config_cxsm,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell复合型烟感说明书&type=zh"),
    EE_DEV_SXSMALARM6("200D",
            R.string.cxsmalarm,
            R.mipmap.gs592,R.drawable.config_cxsm,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell复合型烟感说明书&type=zh"),
    EE_DEV_SXSMALARM7("0013",
            R.string.cxsmalarm,
            R.mipmap.gs592,R.drawable.config_cxsm,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell复合型烟感说明书&type=zh"),
    EE_DEV_SXSMALARM8("1013",
            R.string.cxsmalarm,
            R.mipmap.gs592,R.drawable.config_cxsm,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell复合型烟感说明书&type=zh"),
    EE_DEV_SXSMALARM9("2013",
            R.string.cxsmalarm,
            R.mipmap.gs592,R.drawable.config_cxsm,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell复合型烟感说明书&type=zh"),
    //气体报警器
    EE_DEV_GASALARM1("0002",
            R.string.gasalarm,
            R.mipmap.gs870w,R.drawable.config_gas,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellGAS报警器说明书&type=zh"),
    EE_DEV_GASALARM2("1002",
            R.string.gasalarm,
            R.mipmap.gs870w,R.drawable.config_gas,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellGAS报警器说明书&type=zh"),
    EE_DEV_GASALARM3("2002",
            R.string.gasalarm,
            R.mipmap.gs870w,R.drawable.config_gas,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellGAS报警器说明书&type=zh"),
    EE_DEV_GASALARM4("1006",
            R.string.gasalarm,
            R.mipmap.gs870w,R.drawable.config_gas,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellGAS报警器说明书&type=zh"),
    EE_DEV_GASALARM5("000A",
            R.string.gasalarm,
            R.mipmap.gs870w,R.drawable.config_gas,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellGAS报警器说明书&type=zh"),
    EE_DEV_GASALARM6("100A",
            R.string.gasalarm,
            R.mipmap.gs870w,R.drawable.config_gas,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellGAS报警器说明书&type=zh"),
    EE_DEV_GASALARM7("200A",
            R.string.gasalarm,
            R.mipmap.gs870w,R.drawable.config_gas,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellGAS报警器说明书&type=zh"),
    EE_DEV_GASALARM8("0010",
            R.string.gasalarm,
            R.mipmap.gs870w,R.drawable.config_gas,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellGAS报警器说明书&type=zh"),
    EE_DEV_GASALARM9("1010",
            R.string.gasalarm,
            R.mipmap.gs870w,R.drawable.config_gas,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellGAS报警器说明书&type=zh"),
    EE_DEV_GASALARM10("2010",
            R.string.gasalarm,
            R.mipmap.gs870w,R.drawable.config_gas,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=FamilywellGAS报警器说明书&type=zh"),
    //热感报警器
    EE_DEV_THERMALARM1("0003",
            R.string.thermalalarm,
            R.mipmap.gs412,R.drawable.config_hot,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell热感报警器说明书&type=zh"),
    EE_DEV_THERMALARM2("1003",
            R.string.thermalalarm,
            R.mipmap.gs412,R.drawable.config_hot,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell热感报警器说明书&type=zh"),
    EE_DEV_THERMALARM3("2003",
            R.string.thermalalarm,
            R.mipmap.gs412,R.drawable.config_hot,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell热感报警器说明书&type=zh"),
    EE_DEV_THERMALARM4("000B",
            R.string.thermalalarm,
            R.mipmap.gs412,R.drawable.config_hot,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell热感报警器说明书&type=zh"),
    EE_DEV_THERMALARM5("100B",
            R.string.thermalalarm,
            R.mipmap.gs412,R.drawable.config_hot,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell热感报警器说明书&type=zh"),
    EE_DEV_THERMALARM6("200B",
            R.string.thermalalarm,
            R.mipmap.gs412,R.drawable.config_hot,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell热感报警器说明书&type=zh"),
    EE_DEV_THERMALARM7("0011",
            R.string.thermalalarm,
            R.mipmap.gs412,R.drawable.config_hot,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell热感报警器说明书&type=zh"),
    EE_DEV_THERMALARM8("1011",
            R.string.thermalalarm,
            R.mipmap.gs412,R.drawable.config_hot,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell热感报警器说明书&type=zh"),
    EE_DEV_THERMALARM9("2011",
            R.string.thermalalarm,
            R.mipmap.gs412,R.drawable.config_hot,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell热感报警器说明书&type=zh"),
    //场景开关
    EE_DEV_MODE_BUTTON("0305",
            R.string.mode_button,
            R.mipmap.gs584,R.drawable.config_mode_button,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell场景开关说明书&type=zh"),
    //门锁
    EE_DEV_LOCK("1213",
            R.string.lock,
            R.mipmap.gs920,R.drawable.config_lock,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell智能门锁说明书&type=zh"),
    //双路开关
    EE_TWO_CHANNEL_SOCKET1("0201",
            R.string.two_channel_socket,
            R.mipmap.gs344,R.drawable.config_switch_mode,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell开关模块说明书&type=zh"),
    EE_TWO_CHANNEL_SOCKET2("1201",
            R.string.two_channel_socket,
            R.mipmap.gs344,R.drawable.config_switch_mode,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell开关模块说明书&type=zh"),
    EE_TWO_CHANNEL_SOCKET3("2201",
            R.string.two_channel_socket,
            R.mipmap.gs344,R.drawable.config_switch_mode,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell开关模块说明书&type=zh"),
    //温控器
    EE_TEMP_CONTROL1("0215",
            R.string.temp_controler,
            R.mipmap.gs361,R.drawable.config_temp,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell温控器说明书&type=zh"),
    EE_TEMP_CONTROL2("1215",
            R.string.temp_controler,
            R.mipmap.gs361,R.drawable.config_temp,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell温控器说明书&type=zh"),
    EE_TEMP_CONTROL3("2215",
            R.string.temp_controler,
            R.mipmap.gs361,R.drawable.config_temp,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell温控器说明书&type=zh"),
    //室外警笛
    EE_TEMP_OUTDOOR_SIREN1("020E",
            R.string.outdoor_siren,
            R.mipmap.gs380,R.drawable.config_shiwai,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell室外警笛说明书&type=zh"),
    EE_TEMP_OUTDOOR_SIREN2("120E",
            R.string.outdoor_siren,
            R.mipmap.gs380,R.drawable.config_shiwai,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell室外警笛说明书&type=zh"),
    EE_TEMP_OUTDOOR_SIREN3("220E",
            R.string.outdoor_siren,
            R.mipmap.gs380,R.drawable.config_shiwai,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell室外警笛说明书&type=zh"),
    EE_SIMULATE_GATEWAY("GATEWAY",
            R.string.ali_gateway,
            R.mipmap.gs188,R.drawable.config_moren,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell网关说明书&type=zh"),
    EE_SIMULATE_IPC("IPC",
            R.string.ali_camera,
            R.mipmap.gs290,R.drawable.config_moren,"http://61.164.94.198:1415/point/app/DownLoadFile!downLoad.action?fileName=Familywell摄像头说明书&type=zh"),

    EE_SIMULATE_TIMER("TIMER", R.string.timer,   R.mipmap.timing_64, R.drawable.config_moren,""),

    EE_SIMULATE_CLICK("CLICK", R.string.clickTo, R.mipmap.manual_clicking_64, R.drawable.config_moren,""),

    EE_SIMULATE_DELAY("DELAY", R.string.delay,   R.mipmap.delayed_64, R.drawable.config_moren,""),

    EE_SIMULATE_PHONE("PHONE", R.string.phone,   R.mipmap.mobile_phone_64, R.drawable.config_moren,""),

    //未知设备
    EE_DEV_UN_KNOW("un_know", R.string.unamed, R.mipmap.ali_moren,R.drawable.config_moren,"");

    private String devType;
    private int devResId;
    private int drawResId;
    private int drawGuideResId;
    private String ins_url;
    private String ins_url_en;

    public String getIns_url() {
        return ins_url;
    }

    public void setIns_url(String ins_url) {
        this.ins_url = ins_url;
    }

    public String getIns_url_en() {
        return ins_url_en;
    }

    public void setIns_url_en(String ins_url_en) {
        this.ins_url_en = ins_url_en;
    }

    public int getDrawGuideResId() {
        return drawGuideResId;
    }

    public void setDrawGuideResId(int drawGuideResId) {
        this.drawGuideResId = drawGuideResId;
    }

    SmartProduct(String devType, int resid, int iconid, int guideresid, String ins_url) {
        this.devType = devType;
        this.devResId = resid;
        this.drawResId = iconid;
        this.drawGuideResId = guideresid;
        this.ins_url = ins_url;
        this.ins_url_en = this.ins_url.replaceAll("type=zh","type=en");
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

    public static SmartProduct getType(String devType1) {
        for (SmartProduct SmartProduct : SmartProduct.values()) {
            if (SmartProduct.getDevType().equals(devType1)) {
                return SmartProduct;
            }
        }
        return EE_DEV_UN_KNOW;
    }
}
