package wang.tinycoder.easylinkerapp.base.interfaces;

import io.reactivex.functions.Consumer;

/**
 * Progect：EasyLinkerApp
 * Package：wang.tinycoder.easylinkerapp.base
 * Desc：presenter接口
 * Author：TinycoderWang
 * CreateTime：2018/4/1 8:50
 */
public interface IPresenter {


    /**
     * 注册事件
     *
     * @param eventType
     * @param action
     * @param <T>
     */
    public <T> void registerRxBus(Class<T> eventType, Consumer<T> action);

    void onStart();


    void onDestroy();

}
