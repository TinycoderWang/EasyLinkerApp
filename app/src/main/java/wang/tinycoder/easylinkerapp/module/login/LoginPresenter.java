package wang.tinycoder.easylinkerapp.module.login;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wang.tinycoder.easylinkerapp.base.BasePresenter;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.bean.User;
import wang.tinycoder.easylinkerapp.net.cookie.CookieManager;

/**
 * Progect：EasyLinkerApp
 * Package：wang.tinycoder.easylinkerapp.module.login
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/1 9:22
 */
public class LoginPresenter extends BasePresenter<LoginContract.View, LoginContract.Model> {

    public LoginPresenter(LoginContract.View rootView, LoginContract.Model model) {
        super(rootView, model);
    }


    public void login() {
        String userName = mView.getUserName();
        if (TextUtils.isEmpty(userName)) {
            mView.showMessage("请输入用户名");
            return;
        }
        String userPassword = mView.getUserPassword();
        if (TextUtils.isEmpty(userPassword)) {
            mView.showMessage("请输入密码");
            return;
        }

        mModel.login(userName, userPassword, new Observer<NetResult<User>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(NetResult<User> userNetResult) {
                if (userNetResult != null) {
                    Logger.i("%s login result -- %s", TAG, userNetResult.getMessage());
                    if (userNetResult.getState() == NetResult.FAILD) {
                        // 登录失败，清空cookie
                        CookieManager.getInstance().getCookieJar().cleanCookie();
                    } else if (userNetResult.getState() == NetResult.SUCCESS) {
                        // 登录成功
                        mView.loginSuccess();
                    }
                    mView.showMessage(userNetResult.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("%s login faild -- %s", TAG, e.getMessage());
            }

            @Override
            public void onComplete() {
                Logger.e("%s login finish", TAG);
            }
        });

    }
}
