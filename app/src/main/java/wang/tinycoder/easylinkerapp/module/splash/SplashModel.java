package wang.tinycoder.easylinkerapp.module.splash;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.bean.User;
import wang.tinycoder.easylinkerapp.net.GlobalRetrofit;

/**
 * @author WangYh
 * @version V1.0
 * @Name: SplashModel
 * @Package wang.tinycoder.easylinkerapp.module.splash
 * @Description: 闪屏model
 * @date 2018/4/3 0003
 */
public class SplashModel implements SplashContract.Model {
    @Override
    public void onDestroy() {

    }

    @Override
    public void getCurrentUser(Observer<NetResult<User>> observer) {
        GlobalRetrofit.getInstance().getApi()
                .getCurrentUser()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
