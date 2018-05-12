package wang.tinycoder.easylinkerapp.module.home.fragment.sys;

import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wang.tinycoder.easylinkerapp.base.BasePresenter;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.bean.SystemState;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.home.fragment.sys
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/5/6 16:18
 */
public class SysStatePresenter extends BasePresenter<SysStateContract.View, SysStateContract.Model> {
    public SysStatePresenter(SysStateContract.View rootView, SysStateContract.Model model) {
        super(rootView, model);
    }

    public void requestCurrentState() {
        mModel.getCurrentState(new Observer<NetResult<SystemState>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(NetResult<SystemState> systemStateNetResult) {
                if (systemStateNetResult != null && NetResult.SUCCESS == systemStateNetResult.getState()) {
                    mView.showState(systemStateNetResult.getData());
                }
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("%s onError . MSG : %s", TAG, e.getMessage());
            }

            @Override
            public void onComplete() {
                Logger.i("%s onComplete !", TAG);
            }
        });

    }
}
