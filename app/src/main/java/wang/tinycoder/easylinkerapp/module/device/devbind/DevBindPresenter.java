package wang.tinycoder.easylinkerapp.module.device.devbind;

import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wang.tinycoder.easylinkerapp.base.BasePresenter;
import wang.tinycoder.easylinkerapp.bean.NetResult;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.device.devbind
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/27 20:59
 */
public class DevBindPresenter extends BasePresenter<DevBindContract.View, DevBindContract.Model> {
    public DevBindPresenter(DevBindContract.View rootView, DevBindContract.Model model) {
        super(rootView, model);
    }

    /**
     * 绑定设备
     *
     * @param groupId
     * @param deviceId
     */
    public void bindDevice(String groupId, String deviceId) {
        mModel.bindDevice(groupId, deviceId, new Observer<NetResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(NetResult netResult) {
                if (netResult != null) {

                    if (NetResult.SUCCESS == netResult.getState()) {
                        mView.onBindResult(true);
                    } else {
                        mView.onBindResult(false);
                    }

                    mView.showMessage(netResult.getMessage());

                } else {
                    mView.showMessage("网络异常，请稍后再试！");
                    mView.onBindResult(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("%s requestDeviceBygroup error ! MSG : %s", TAG, e.getMessage());
                mView.onBindResult(false);
            }

            @Override
            public void onComplete() {
                Logger.i("%s requestDeviceBygroup onComplete !", TAG);
            }
        });
    }
}
