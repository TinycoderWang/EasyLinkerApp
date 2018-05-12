package wang.tinycoder.easylinkerapp.module.register;

import android.text.TextUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wang.tinycoder.easylinkerapp.base.BasePresenter;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.util.RegexUtil;

/**
 * Progect：EasyLinkerApp
 * Package：wang.tinycoder.easylinkerapp.module.register
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/1 16:11
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View, RegisterContract.Model> {

    public RegisterPresenter(RegisterContract.View rootView, RegisterContract.Model model) {
        super(rootView, model);
    }

    /**
     * 注册
     */
    public void register() {
        // 用户名
        String userName = mView.getUserName();
        if (TextUtils.isEmpty(userName)) {
            mView.showMessage("请输入用户名");
            return;
        }
        // 密码
        String userPassword = mView.getUserPassword();
        if (TextUtils.isEmpty(userPassword)) {
            mView.showMessage("请输入密码");
            return;
        }

        // 密码
        String userRePassword = mView.getUserRePassword();
        if (TextUtils.isEmpty(userRePassword)) {
            mView.showMessage("请再次输入密码");
            return;
        }

        // 电话
        String userPhone = mView.getUserPhone();
        if (TextUtils.isEmpty(userPhone)) {
            mView.showMessage("请输入手机号码");
            return;
        }

        // 邮箱
        String userEmail = mView.getUserEmail();
        if (TextUtils.isEmpty(userEmail)) {
            mView.showMessage("请输入邮箱");
            return;
        }


        // 验证手机号
        if (!RegexUtil.isMobileNumber(userPhone)) {
            mView.showMessage("手机号码格式错误");
            return;
        }

        // 验证邮箱
        if (!RegexUtil.isEmailAddr(userEmail)) {
            mView.showMessage("邮箱格式错误");
            return;
        }

        // 密码是否一致
        if (!userPassword.equals(userRePassword)) {
            mView.showMessage("密码不一致");
            return;
        }


        mModel.register(userName, userPassword, userRePassword, userPhone, userEmail, new Observer<NetResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(NetResult netResult) {
                if (netResult != null) {
                    mView.showMessage(netResult.getMessage());
                    // 注册成功
                    if (netResult.getState() == NetResult.SUCCESS) {
                        mView.gotoLogin();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


    }
}
