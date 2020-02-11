package com.ilop.sthome.data.bean;

import java.io.Serializable;

public class DeviceInfoBean implements Serializable {


    /**
     * gmtModified : 1531360639000
     * categoryImage : http://iotx-paas-admin.oss-cn-shanghai.aliyuncs.com/publish/image/1526474704326.png
     * netType : NET_WIFI
     * nodeType : DEVICE
     * productKey : a1XoFUJWkPr
     * deviceName : VD_l2c4LuifwY
     * productName : 风扇-Demo
     * identityAlias : 15700192592
     * iotId : g1VQsQvQvHdkuGjI5unE0010eedc00
     * owned : 0
     * identityId : 5072op548214e63f60e5c5bb17631d9201ea1295
     * thingType : VIRTUAL
     * status : 1
     */

    private long gmtModified;
    private String categoryImage;
    private String netType;
    private String nodeType;
    private String productKey;
    private String deviceName;//gateway id
    private String productName;
    private String identityAlias;
    private String iotId;
    private int owned;
    private String identityId;
    private String thingType;
    private int status;
    private String nickName;
    private int isEdgeGateway;
    private String binVersion;
    private String subdeviceName;
    private   int   device_ID;
    private String device_type;
    private String device_status;
    private   int current_mode;
    private int is_open;

    public long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getIdentityAlias() {
        return identityAlias;
    }

    public void setIdentityAlias(String identityAlias) {
        this.identityAlias = identityAlias;
    }

    public String getIotId() {
        return iotId;
    }

    public void setIotId(String iotId) {
        this.iotId = iotId;
    }

    public int getOwned() {
        return owned;
    }

    public void setOwned(int owned) {
        this.owned = owned;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getThingType() {
        return thingType;
    }

    public void setThingType(String thingType) {
        this.thingType = thingType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getIsEdgeGateway() {
        return isEdgeGateway;
    }

    public void setIsEdgeGateway(int isEdgeGateway) {
        this.isEdgeGateway = isEdgeGateway;
    }

    public String getBinVersion() {
        return binVersion;
    }

    public void setBinVersion(String binVersion) {
        this.binVersion = binVersion;
    }

    public String getSubdeviceName() {
        return subdeviceName;
    }

    public void setSubdeviceName(String subdeviceName) {
        this.subdeviceName = subdeviceName;
    }

    public int getDevice_ID() {
        return device_ID;
    }

    public void setDevice_ID(int device_ID) {
        this.device_ID = device_ID;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_status() {
        return device_status;
    }

    public void setDevice_status(String device_status) {
        this.device_status = device_status;
    }

    public int getCurrent_mode() {
        return current_mode;
    }

    public void setCurrent_mode(int current_mode) {
        this.current_mode = current_mode;
    }

    public int getIs_open() {
        return is_open;
    }

    public void setIs_open(int is_open) {
        this.is_open = is_open;
    }

    @Override
    public String toString() {
        return "DeviceInfoBean{" +
                "gmtModified=" + gmtModified +
                ", categoryImage='" + categoryImage + '\'' +
                ", netType='" + netType + '\'' +
                ", nodeType='" + nodeType + '\'' +
                ", productKey='" + productKey + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", productName='" + productName + '\'' +
                ", identityAlias='" + identityAlias + '\'' +
                ", iotId='" + iotId + '\'' +
                ", owned=" + owned +
                ", identityId='" + identityId + '\'' +
                ", thingType='" + thingType + '\'' +
                ", status=" + status +
                ", nickName='" + nickName + '\'' +
                ", isEdgeGateway=" + isEdgeGateway +
                ", binVersion='" + binVersion + '\'' +
                ", subdeviceName='" + subdeviceName + '\'' +
                ", device_ID=" + device_ID +
                ", device_type='" + device_type + '\'' +
                ", device_status='" + device_status + '\'' +
                ", current_mode=" + current_mode +
                ", is_open=" + is_open +
                '}';
    }
}
