package com.ilop.sthome.data.greenDao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author skygge
 * @date 2020-01-03.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：警告类
 */
@Entity
public class WarnBean {

    @Id(autoincrement = true)
    Long id;

    long time;

    String code;

    String type;

    int device_id;

    String device_type;

    String device_status;

    int mid;

    String deviceName;

    String activityTime;

    String activtiyTimeDetail;

    @Generated(hash = 884707027)
    public WarnBean(Long id, long time, String code, String type, int device_id,
            String device_type, String device_status, int mid, String deviceName,
            String activityTime, String activtiyTimeDetail) {
        this.id = id;
        this.time = time;
        this.code = code;
        this.type = type;
        this.device_id = device_id;
        this.device_type = device_type;
        this.device_status = device_status;
        this.mid = mid;
        this.deviceName = deviceName;
        this.activityTime = activityTime;
        this.activtiyTimeDetail = activtiyTimeDetail;
    }

    @Generated(hash = 794775657)
    public WarnBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDevice_id() {
        return this.device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public String getDevice_type() {
        return this.device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_status() {
        return this.device_status;
    }

    public void setDevice_status(String device_status) {
        this.device_status = device_status;
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

    public String getActivityTime() {
        return this.activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getActivtiyTimeDetail() {
        return this.activtiyTimeDetail;
    }

    public void setActivtiyTimeDetail(String activtiyTimeDetail) {
        this.activtiyTimeDetail = activtiyTimeDetail;
    }

}
