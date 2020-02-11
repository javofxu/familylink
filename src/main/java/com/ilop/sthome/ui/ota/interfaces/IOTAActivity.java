package com.ilop.sthome.ui.ota.interfaces;


public interface IOTAActivity {
    /**
     * 设置标题
     */
    void setTitle();

    /**
     * 显示加载状态
     */
    void showLoading();

    /**
     * 显示加载完成
     * @param msg 消息
     */
    void showLoaded(String msg);

    /**
     * 显示加载失败
     */
    void showLoadError();

    /**
     * 显示固件升级失败对话框
     */
    void showUpgradeFailureDialog();

    /**
     * 显示最新版本
     * @param latestVersion
     */
    void showTips(String latestVersion);



    void showNoNetToast();

    /**
     * 显示当前版本
     * @param currentVersion
     */
    void showCurrentVersion(String currentVersion);

    /**
     * 显示当前版本，并且此版本已经是最新版本
     * @param currentVersion
     */
    void showCurrentVersionNoNeedToupdate(String currentVersion);

    /**
     * 显示升级状态
     * @param status
     */
    void showUpgradeStatus(int status);
}
