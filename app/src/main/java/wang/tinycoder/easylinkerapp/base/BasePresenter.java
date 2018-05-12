package wang.tinycoder.easylinkerapp.base;

import android.support.annotation.NonNull;
import android.util.Log;

import com.orhanobut.logger.Logger;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import wang.tinycoder.easylinkerapp.base.interfaces.IModel;
import wang.tinycoder.easylinkerapp.base.interfaces.IPresenter;
import wang.tinycoder.easylinkerapp.base.interfaces.IView;
import wang.tinycoder.easylinkerapp.util.RxBus;

/**
 * Progect：EasyLinkerApp
 * Package：wang.tinycoder.easylinkerapp.base.interfaces
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/1 9:14
 */
public class BasePresenter<V extends IView, M extends IModel> implements IPresenter {

    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeDisposable mCompositeDisposable;

    protected V mView;
    protected M mModel;

    public BasePresenter(V rootView, M model) {
        mCompositeDisposable = new CompositeDisposable();
        mView = rootView;
        mModel = model;
        onStart();
    }

    @Override
    public void onStart() {
        Logger.i("%s onStart!", TAG);
    }

    @Override
    public void onDestroy() {
        Logger.i("%s onDestroy!", TAG);
        unDispose();
        if (mModel != null)
            mModel.onDestroy();
        this.mModel = null;
        this.mView = null;
        this.mCompositeDisposable = null;
        // 解绑事件注册
        unregisterRxBus();
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    public void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
        }
    }

    public <T> void registerRxBus(Class<T> eventType, Consumer<T> action) {
        Disposable disposable = RxBus.getIntanceBus().doSubscribe(eventType, action, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.e("NewsMainPresenter", throwable.toString());
            }
        });
        RxBus.getIntanceBus().addSubscription(this, disposable);
    }

    public void unregisterRxBus() {
        RxBus.getIntanceBus().unSubscribe(this);
    }
}
