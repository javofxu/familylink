package com.example.common.mvp;

/**
 * @Author skygge.
 * @Date on 2019-08-13.
 * @Github https://github.com/javofxu
 * @Dec:
 */
public interface IBaseView {

    void showSuccess();

    void showFailed();

    void showProgressDialog();

    void dismissProgressDialog();
}
