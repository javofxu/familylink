package com.ilop.sthome.data.enums;

import com.siterwell.familywellplus.R;

/**
 * @Author skygge.
 * @Date on 2019-10-25.
 * @Dec:
 */
public enum DeviceTrigger {

    //门磁
    EE_DEV_DOOR1("0101",R.array.door_actions,R.array.EE_DEV_DOOR),
    EE_DEV_DOOR2("1101",R.array.door_actions,R.array.EE_DEV_DOOR),
    EE_DEV_DOOR3("2101",R.array.door_actions,R.array.EE_DEV_DOOR),

    EE_DEV_SOCKET1("0200",R.array.socket_actions,R.array.EE_DEV_SOCKET),
    EE_DEV_SOCKET2("1200",R.array.socket_actions,R.array.EE_DEV_SOCKET),
    EE_DEV_SOCKET3("2200",R.array.socket_actions,R.array.EE_DEV_SOCKET),

    EE_DEV_PIR1("0100",R.array.pir_signs,R.array.EE_DEV_PIR),
    EE_DEV_PIR2("1100",R.array.pir_signs,R.array.EE_DEV_PIR),
    EE_DEV_PIR3("2100",R.array.pir_signs,R.array.EE_DEV_PIR),

    EE_DEV_SOS1("0300",R.array.sos_signs,R.array.EE_DEV_SOS),
    EE_DEV_SOS2("1300",R.array.sos_signs,R.array.EE_DEV_SOS),
    EE_DEV_SOS3("2300",R.array.sos_signs,R.array.EE_DEV_SOS),

    EE_DEV_SMALARM1("0001",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_SMALARM2("1001",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_SMALARM3("2001",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_SMALARM4("0009",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_SMALARM5("1009",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_SMALARM6("2009",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_SMALARM7("000F",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_SMALARM8("100F",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_SMALARM9("200F",R.array.alert_actions,R.array.EE_DEV_SMALARM),

    EE_DEV_COALARM1("0000",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_COALARM2("1000",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_COALARM3("2000",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_COALARM4("0008",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_COALARM5("1008",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_COALARM6("2008",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_COALARM7("000E",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_COALARM8("100E",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_COALARM9("200E",R.array.alert_actions,R.array.EE_DEV_SMALARM),

    EE_DEV_GASALARM1("0002",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_GASALARM2("1002",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_GASALARM3("2002",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_GASALARM4("1006",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_GASALARM5("000A",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_GASALARM6("100A",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_GASALARM7("200A",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_GASALARM8("0010",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_GASALARM9("1010",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_GASALARM10("2010",R.array.alert_actions,R.array.EE_DEV_SMALARM),

    EE_DEV_THERMALARM1("0003",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_THERMALARM2("1003",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_THERMALARM3("2003",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_THERMALARM4("000B",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_THERMALARM5("100B",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_THERMALARM6("200B",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_THERMALARM7("0011",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_THERMALARM8("1011",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_THERMALARM9("2011",R.array.alert_actions,R.array.EE_DEV_SMALARM),

    EE_DEV_WTALARM1("0004",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_WTALARM2("1004",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_WTALARM3("2004",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_WTALARM4("000C",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_WTALARM5("100C",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_WTALARM6("200C",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_WTALARM7("0012",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_WTALARM8("1012",R.array.alert_actions,R.array.EE_DEV_SMALARM),
    EE_DEV_WTALARM9("2012",R.array.alert_actions,R.array.EE_DEV_SMALARM),

    EE_DEV_SXSMALARM1("0005",R.array.cx_alert_actions,R.array.EE_DEV_SXSMALARM),
    EE_DEV_SXSMALARM2("0005",R.array.cx_alert_actions,R.array.EE_DEV_SXSMALARM),
    EE_DEV_SXSMALARM3("2005",R.array.cx_alert_actions,R.array.EE_DEV_SXSMALARM),
    EE_DEV_SXSMALARM4("000D",R.array.cx_alert_actions,R.array.EE_DEV_SXSMALARM),
    EE_DEV_SXSMALARM5("100D",R.array.cx_alert_actions,R.array.EE_DEV_SXSMALARM),
    EE_DEV_SXSMALARM6("200D",R.array.cx_alert_actions,R.array.EE_DEV_SXSMALARM),
    EE_DEV_SXSMALARM7("0013",R.array.cx_alert_actions,R.array.EE_DEV_SXSMALARM),
    EE_DEV_SXSMALARM8("1013",R.array.cx_alert_actions,R.array.EE_DEV_SXSMALARM),
    EE_DEV_SXSMALARM9("2013",R.array.cx_alert_actions,R.array.EE_DEV_SXSMALARM),

    EE_DEV_MODE_BUTTON("0305",R.array.mode_button_signs,R.array.EE_DEV_MODE_BUTTON),

    EE_DEV_LOCK("1213",R.array.lock_input,R.array.EE_DEV_LOCK),

    EE_TEMP_OUTDOOR_SIREN1("020E",R.array.outdoor_siren_action,R.array.EE_TEMP_OUTDOOR_SIREN),
    EE_TEMP_OUTDOOR_SIREN2("120E",R.array.outdoor_siren_action,R.array.EE_TEMP_OUTDOOR_SIREN),
    EE_TEMP_OUTDOOR_SIREN3("220E",R.array.outdoor_siren_action,R.array.EE_TEMP_OUTDOOR_SIREN),

    EE_SIMULATE_GATEWAY("GATEWAY",R.array.gateway_actions,R.array.EE_DEV_GATEWAY);


    private String type;

    private int state;

    private int code;

    DeviceTrigger(String type, int state, int code) {
        this.type = type;
        this.state = state;
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static DeviceTrigger getType(String devType) {
        for (DeviceTrigger deviceTrigger : DeviceTrigger.values()) {
            if (deviceTrigger.getType().equals(devType)) {
                return deviceTrigger;
            }
        }
        return null;
    }

}
