package com.example.common.mvp;

/**
 * @Author skygge.
 * @Date on 2019-08-13.
 * @Github https://github.com/javofxu
 * @Dec:
 */
public interface IBasePresenter<T extends IBaseView> {

    /**
     * 绑定View
     * @param view
     */
    void attachView(T view);

    /**
     * 分离View
     */
    void detachView();
}
