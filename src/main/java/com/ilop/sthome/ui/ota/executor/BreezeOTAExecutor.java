package com.ilop.sthome.ui.ota.executor;

import com.ilop.sthome.ui.ota.business.listener.IOTAQueryStatusCallback;
import com.ilop.sthome.ui.ota.business.listener.IOTAStartUpgradeCallback;
import com.ilop.sthome.ui.ota.business.listener.IOTAStopUpgradeCallback;
import com.ilop.sthome.ui.ota.interfaces.IOTAExecutor;
import com.ilop.sthome.ui.ota.interfaces.IOTAStatusChangeListener;

/**
 * 蓝牙 ota
 */
public class BreezeOTAExecutor implements IOTAExecutor {
    public BreezeOTAExecutor(IOTAStatusChangeListener listener) {

    }

    @Override
    public void queryOTAStatus(String iotId, IOTAQueryStatusCallback callback) {

    }

    @Override
    public void startUpgrade(String iotId, IOTAStartUpgradeCallback callback) {

    }

    @Override
    public void stopUpgrade(String iotId, IOTAStopUpgradeCallback callback) {

    }

    @Override
    public void destroy() {

    }
}
