package wang.tinycoder.easylinkerapp.module.device.devbind;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.net.GlobalRetrofit;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.device.devbind
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/27 20:59
 */
public class DevBindModel implements DevBindContract.Model {
    @Override
    public void onDestroy() {

    }


    /**
     * 绑定设备
     *
     * @param groupId  群组id
     * @param deviceid 设备id
     * @param observer
     */
    @Override
    public void bindDevice(String groupId, String deviceid, Observer<NetResult> observer) {
        GlobalRetrofit.getInstance().getApi()
                .bindDevice(deviceid, groupId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
