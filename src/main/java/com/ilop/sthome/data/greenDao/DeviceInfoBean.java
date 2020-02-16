package com.ilop.sthome.data.greenDao;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DeviceInfoBean implements Parcelable {


    /**
     * gmtModified : 1531360639000
     * categoryImage : http://iotx-paas-admin.oss-cn-shanghai.aliyuncs.com/publish/image/1526474704326.png
     * netType : NET_WIFI
     * nodeType : DEVICE
     * productKey : a1XoFUJWkPr
     * deviceName : VD_l2c4LuifwY
     * productName : 198网关
     * identityAlias : 15700192592
     * iotId : g1VQsQvQvHdkuGjI5unE0010eedc00
     * owned : 0
     * identityId : 5072op548214e63f60e5c5bb17631d9201ea1295
     * thingType : VIRTUAL
     * status : 1
     */

    @Id(autoincrement = true)
    Long id;

    private long gmtModified;

    private String categoryImage;

    private String netType;

    private String nodeType;

    private String productKey;

    private String deviceName;

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

    private int device_ID;

    private String device_type;

    private String device_status;

    private int current_mode;

    private int scene_id;

    @Generated(hash = 1949649412)
    public DeviceInfoBean(Long id, long gmtModified, String categoryImage, String netType, String nodeType,
            String productKey, String deviceName, String productName, String identityAlias, String iotId,
            int owned, String identityId, String thingType, int status, String nickName, int isEdgeGateway,
            String binVersion, String subdeviceName, int device_ID, String device_type, String device_status,
            int current_mode, int scene_id) {
        this.id = id;
        this.gmtModified = gmtModified;
        this.categoryImage = categoryImage;
        this.netType = netType;
        this.nodeType = nodeType;
        this.productKey = productKey;
        this.deviceName = deviceName;
        this.productName = productName;
        this.identityAlias = identityAlias;
        this.iotId = iotId;
        this.owned = owned;
        this.identityId = identityId;
        this.thingType = thingType;
        this.status = status;
        this.nickName = nickName;
        this.isEdgeGateway = isEdgeGateway;
        this.binVersion = binVersion;
        this.subdeviceName = subdeviceName;
        this.device_ID = device_ID;
        this.device_type = device_type;
        this.device_status = device_status;
        this.current_mode = current_mode;
        this.scene_id = scene_id;
    }

    @Generated(hash = 784809703)
    public DeviceInfoBean() {
    }

    protected DeviceInfoBean(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        gmtModified = in.readLong();
        categoryImage = in.readString();
        netType = in.readString();
        nodeType = in.readString();
        productKey = in.readString();
        deviceName = in.readString();
        productName = in.readString();
        identityAlias = in.readString();
        iotId = in.readString();
        owned = in.readInt();
        identityId = in.readString();
        thingType = in.readString();
        status = in.readInt();
        nickName = in.readString();
        isEdgeGateway = in.readInt();
        binVersion = in.readString();
        subdeviceName = in.readString();
        device_ID = in.readInt();
        device_type = in.readString();
        device_status = in.readString();
        current_mode = in.readInt();
        scene_id = in.readInt();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getGmtModified() {
        return this.gmtModified;
    }

    public void setGmtModified(long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getCategoryImage() {
        return this.categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getNetType() {
        return this.netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getProductKey() {
        return this.productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getIdentityAlias() {
        return this.identityAlias;
    }

    public void setIdentityAlias(String identityAlias) {
        this.identityAlias = identityAlias;
    }

    public String getIotId() {
        return this.iotId;
    }

    public void setIotId(String iotId) {
        this.iotId = iotId;
    }

    public int getOwned() {
        return this.owned;
    }

    public void setOwned(int owned) {
        this.owned = owned;
    }

    public String getIdentityId() {
        return this.identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getThingType() {
        return this.thingType;
    }

    public void setThingType(String thingType) {
        this.thingType = thingType;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getIsEdgeGateway() {
        return this.isEdgeGateway;
    }

    public void setIsEdgeGateway(int isEdgeGateway) {
        this.isEdgeGateway = isEdgeGateway;
    }

    public String getBinVersion() {
        return this.binVersion;
    }

    public void setBinVersion(String binVersion) {
        this.binVersion = binVersion;
    }

    public String getSubdeviceName() {
        return this.subdeviceName;
    }

    public void setSubdeviceName(String subdeviceName) {
        this.subdeviceName = subdeviceName;
    }

    public int getDevice_ID() {
        return this.device_ID;
    }

    public void setDevice_ID(int device_ID) {
        this.device_ID = device_ID;
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

    public int getCurrent_mode() {
        return this.current_mode;
    }

    public void setCurrent_mode(int current_mode) {
        this.current_mode = current_mode;
    }

    public int getScene_id() {
        return this.scene_id;
    }

    public void setScene_id(int scene_id) {
        this.scene_id = scene_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(gmtModified);
        parcel.writeString(categoryImage);
        parcel.writeString(netType);
        parcel.writeString(nodeType);
        parcel.writeString(productKey);
        parcel.writeString(deviceName);
        parcel.writeString(productName);
        parcel.writeString(identityAlias);
        parcel.writeString(iotId);
        parcel.writeInt(owned);
        parcel.writeString(identityId);
        parcel.writeString(thingType);
        parcel.writeInt(status);
        parcel.writeString(nickName);
        parcel.writeInt(isEdgeGateway);
        parcel.writeString(binVersion);
        parcel.writeString(subdeviceName);
        parcel.writeInt(device_ID);
        parcel.writeString(device_type);
        parcel.writeString(device_status);
        parcel.writeInt(current_mode);
        parcel.writeInt(scene_id);
    }

    public static final Creator<DeviceInfoBean> CREATOR = new Creator<DeviceInfoBean>() {
        @Override
        public DeviceInfoBean createFromParcel(Parcel in) {
            DeviceInfoBean device = new DeviceInfoBean();
            device.id = in.readLong();
            device.gmtModified = in.readLong();
            device.categoryImage = in.readString();
            device.netType= in.readString();
            device.nodeType= in.readString();
            device.productKey= in.readString();
            device.deviceName= in.readString();
            device.productName= in.readString();
            device.identityAlias= in.readString();
            device.iotId= in.readString();
            device.owned= in.readInt();
            device.identityId= in.readString();
            device.thingType= in.readString();
            device.status= in.readInt();
            device.nickName= in.readString();
            device.isEdgeGateway= in.readInt();
            device.binVersion= in.readString();
            device.subdeviceName= in.readString();
            device.device_ID= in.readInt();
            device.device_type= in.readString();
            device.device_status= in.readString();
            device.current_mode= in.readInt();
            device.scene_id= in.readInt();
            return device;
        }

        @Override
        public DeviceInfoBean[] newArray(int size) {
            return new DeviceInfoBean[size];
        }
    };
}
