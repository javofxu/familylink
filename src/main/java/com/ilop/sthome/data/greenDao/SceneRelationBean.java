package com.ilop.sthome.data.greenDao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * @author skygge
 * @date 2020-02-16.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：场景关联
 */
@Entity
public class SceneRelationBean {

    @Id(autoincrement = true)
    private Long id;

    private int sid;

    private int mid;

    private String deviceName;

    @Generated(hash = 1054057970)
    public SceneRelationBean(Long id, int sid, int mid, String deviceName) {
        this.id = id;
        this.sid = sid;
        this.mid = mid;
        this.deviceName = deviceName;
    }

    @Generated(hash = 1178040642)
    public SceneRelationBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSid() {
        return this.sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
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
}
