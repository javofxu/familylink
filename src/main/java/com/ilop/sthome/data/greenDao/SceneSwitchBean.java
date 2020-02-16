package com.ilop.sthome.data.greenDao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author skygge
 * @date 2020-02-16.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：场景开关类
 */
@Entity
public class SceneSwitchBean {

    @Id(autoincrement = true)
    private Long id;

    private int src_sid;

    private int des_sid;

    private String deviceName;

    private int deviceId;

    private int delay;

    @Generated(hash = 354936904)
    public SceneSwitchBean(Long id, int src_sid, int des_sid, String deviceName,
            int deviceId, int delay) {
        this.id = id;
        this.src_sid = src_sid;
        this.des_sid = des_sid;
        this.deviceName = deviceName;
        this.deviceId = deviceId;
        this.delay = delay;
    }

    @Generated(hash = 1487798553)
    public SceneSwitchBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSrc_sid() {
        return this.src_sid;
    }

    public void setSrc_sid(int src_sid) {
        this.src_sid = src_sid;
    }

    public int getDes_sid() {
        return this.des_sid;
    }

    public void setDes_sid(int des_sid) {
        this.des_sid = des_sid;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getDelay() {
        return this.delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
