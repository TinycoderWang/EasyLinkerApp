package wang.tinycoder.easylinkerapp.module.splash;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wang.tinycoder.easylinkerapp.base.BasePresenter;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.bean.User;

/**
 * @author WangYh
 * @version V1.0
 * @Name: SplashPresenter
 * @Package wang.tinycoder.easylinkerapp.module.splash
 * @Description: 闪屏presenter
 * @date 2018/4/3 0003
 */
public class SplashPresenter extends BasePresenter<SplashContract.View, SplashContract.Model> {

    public SplashPresenter(SplashContract.View rootView, SplashContract.Model model) {
        super(rootView, model);
    }

    public void requestCurrentUser() {
        mModel.getCurrentUser(new Observer<NetResult<User>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(NetResult<User> userNetResult) {
                if (userNetResult != null) {
                    User user = userNetResult.getData();
                    // 保存用户
                    mView.response(NetResult.SUCCESS == userNetResult.getState());
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mView.response(false);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
