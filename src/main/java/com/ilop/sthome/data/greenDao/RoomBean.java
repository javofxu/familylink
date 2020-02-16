package com.ilop.sthome.data.greenDao;

import com.ilop.sthome.utils.greenDao.converter.DeviceConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author skygge
 * @date 2020-01-08.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：房间实体类
 */
@Entity
public class RoomBean {
    @Id(autoincrement = true)
    Long id;

    String userId;

    int rid;

    String room_name;

    //用到了这个Convert注解，表明它们的转换类，这样就可以转换成String保存到数据库中了
    @Convert(columnType = String.class, converter = DeviceConverter.class)
    List<DeviceInfoBean> gatewayList; //实体类中网关list数据

    @Convert(columnType = String.class, converter = DeviceConverter.class)
    List<DeviceInfoBean> cameraList; //实体类中摄像头list数据

    @Convert(columnType = String.class, converter = DeviceConverter.class)
    List<DeviceInfoBean> subDeviceList; //实体类中子设备list数据

    @Generated(hash = 1880610017)
    public RoomBean(Long id, String userId, int rid, String room_name,
            List<DeviceInfoBean> gatewayList, List<DeviceInfoBean> cameraList,
            List<DeviceInfoBean> subDeviceList) {
        this.id = id;
        this.userId = userId;
        this.rid = rid;
        this.room_name = room_name;
        this.gatewayList = gatewayList;
        this.cameraList = cameraList;
        this.subDeviceList = subDeviceList;
    }

    @Generated(hash = 2135387174)
    public RoomBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRid() {
        return this.rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getRoom_name() {
        return this.room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public List<DeviceInfoBean> getGatewayList() {
        return this.gatewayList;
    }

    public void setGatewayList(List<DeviceInfoBean> gatewayList) {
        this.gatewayList = gatewayList;
    }

    public List<DeviceInfoBean> getCameraList() {
        return this.cameraList;
    }

    public void setCameraList(List<DeviceInfoBean> cameraList) {
        this.cameraList = cameraList;
    }

    public List<DeviceInfoBean> getSubDeviceList() {
        return this.subDeviceList;
    }

    public void setSubDeviceList(List<DeviceInfoBean> subDeviceList) {
        this.subDeviceList = subDeviceList;
    }
}
