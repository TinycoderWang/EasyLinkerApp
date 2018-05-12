package wang.tinycoder.easylinkerapp.base.interfaces;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Progect：EasyLinkerApp
 * Package：wang.tinycoder.easylinkerapp.base.interfaces
 * Desc：activity接口
 * Author：TinycoderWang
 * CreateTime：2018/4/1 8:55
 */
public interface IActivity {

    /**
     * 获取布局ID
     *
     * @return
     */
    @LayoutRes
    int getLayoutId();

    /**
     * 初始化presenter
     */
    void initPresenter();

    /**
     * 初始化界面
     *
     * @param savedInstanceState
     * @return
     */
    void initView(@Nullable Bundle savedInstanceState);

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    void initData(@Nullable Bundle savedInstanceState);


    /**
     * 弹出提示信息
     *
     * @param message
     */
    void showToast(@NonNull String message);


    /**
     * 启动其他的activity
     *
     * @param intent
     * @param finishSelfe 是否关闭自己
     */
    void launchOtherActivity(@NonNull Intent intent, boolean finishSelfe);

    /**
     * 启动其他的activity,返回结果
     *
     * @param intent
     * @param requestCode 请求码
     * @param finishSelfe 是否关闭自己
     */
    void launchOtherActivityForResult(@NonNull Intent intent, int requestCode, boolean finishSelfe);


}
