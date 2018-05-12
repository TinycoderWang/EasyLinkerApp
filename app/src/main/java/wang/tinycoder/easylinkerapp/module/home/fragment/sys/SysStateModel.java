package wang.tinycoder.easylinkerapp.module.home.fragment.sys;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.bean.SystemState;
import wang.tinycoder.easylinkerapp.net.GlobalRetrofit;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.home.fragment.sys
 * Desc：系统状态的model
 * Author：TinycoderWang
 * CreateTime：2018/5/6 16:17
 */
public class SysStateModel implements SysStateContract.Model {
    @Override
    public void onDestroy() {

    }

    @Override
    public void getCurrentState(Observer<NetResult<SystemState>> observer) {
        GlobalRetrofit.getInstance().getApi()
                .getCurrentState()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
