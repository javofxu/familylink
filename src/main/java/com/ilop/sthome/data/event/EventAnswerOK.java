package com.ilop.sthome.data.event;

/**
 * Created by imac on 2019/5/3.
 */

public class EventAnswerOK {
    private int CMD_CODE;
    private int msg_ID;
    private String data_str1;
    private String data_str2;
    private String data_str3;

    public int getCMD_CODE() {
        return CMD_CODE;
    }

    public void setCMD_CODE(int CMD_CODE) {
        this.CMD_CODE = CMD_CODE;
    }

    public int getMsg_ID() {
        return msg_ID;
    }

    public void setMsg_ID(int msg_ID) {
        this.msg_ID = msg_ID;
    }

    public String getData_str1() {
        return data_str1;
    }

    public void setData_str1(String data_str1) {
        this.data_str1 = data_str1;
    }

    public String getData_str2() {
        return data_str2;
    }

    public void setData_str2(String data_str2) {
        this.data_str2 = data_str2;
    }

    public String getData_str3() {
        return data_str3;
    }

    public void setData_str3(String data_str3) {
        this.data_str3 = data_str3;
    }

    @Override
    public String toString() {
        return "EventAnswerOK{" +
                "CMD_CODE=" + CMD_CODE +
                ", msg_ID=" + msg_ID +
                ", data_str1='" + data_str1 + '\'' +
                ", data_str2='" + data_str2 + '\'' +
                ", data_str3='" + data_str3 + '\'' +
                '}';
    }
}
