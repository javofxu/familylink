package com.ilop.sthome.data.enums;

import com.siterwell.familywellplus.R;

/**
 * @author skygge
 * @date 2020-01-01.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：区别国内和海外版
 */

public enum DevType {
    // 网关
    EE_GATEWAY("a1yzkZjV8Dh",
            R.string.ali_gateway,
            R.mipmap.gs188),
    //wifi插座
    EE_WIFI_SOCKET("a1RRjWWuIoS",
            R.string.socket,
            R.mipmap.gs350),
    //未知设备
    EE_DEV_UN_KNOW("un_know",
               R.string.unamed,
               R.mipmap.default_pic_152);

    private String productKey;
    private int devResId;
    private int drawResId;

    DevType(String productkey, int resid, int iconid) {
        this.productKey = productkey;
        this.devResId = resid;
        this.drawResId = iconid;
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
    public String getProductkey() {
        return this.productKey;
    }

    public static DevType getType(String productkey1) {
        for (DevType SmartProduct : DevType.values()) {
            if (SmartProduct.getProductkey().equals(productkey1)) {
                return SmartProduct;
            }
        }
        return EE_DEV_UN_KNOW;
    }
}
