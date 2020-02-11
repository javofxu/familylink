package com.example.common.view.refresh;

/**
 * @Author skygge.
 * @Date on 2019-08-21.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface FooterViewListener {
    /**
     * 正常的loading的View
     */
    void onLoadingMore();

    /**
     * footerView ui-没有更多数据
     */
    void onNoMore();

    /**
     * footerView ui-加载失败的View
     */
    void onError();
}
