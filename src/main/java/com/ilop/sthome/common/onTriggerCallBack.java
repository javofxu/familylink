package com.ilop.sthome.common;

import com.ilop.sthome.data.bean.DeviceInfoBean;

import java.util.List;

/**
 * @author skygge
 * @date 2019-12-06.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：公共接口返回选择的触发条件信息
 */
public interface onTriggerCallBack {

    void onBack(List<DeviceInfoBean> deviceList);
}
