package wang.tinycoder.easylinkerapp.module.home.fragment.my;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wang.tinycoder.easylinkerapp.base.BasePresenter;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.bean.User;
import wang.tinycoder.easylinkerapp.net.cookie.CookieManager;
import wang.tinycoder.easylinkerapp.util.RegexUtil;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.home.fragment.my
 * Desc：个人设置的presenter
 * Author：TinycoderWang
 * CreateTime：2018/4/22 10:54
 */
public class MySettingPresenter extends BasePresenter<MySettingContract.View, MySettingContract.Model> {
    private User mUserInfo;

    public MySettingPresenter(MySettingContract.View rootView, MySettingContract.Model model) {
        super(rootView, model);
    }

    /**
     * 加载个人信息
     */
    public void loadSelfInfo() {
        mModel.getCurrentUser(new Observer<NetResult<User>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(NetResult<User> userNetResult) {
                if (userNetResult != null) {
                    if (NetResult.SUCCESS == userNetResult.getState()) {
                        mUserInfo = userNetResult.getData();
                        mView.onLoadSelfInfo(true);
                    } else {
                        mView.onLoadSelfInfo(false);
                    }
                } else {
                    mView.onLoadSelfInfo(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("%s --- %s", TAG, e.getMessage());
                mView.onLoadSelfInfo(false);
            }

            @Override
            public void onComplete() {
                Logger.i("%s --- onComplete", TAG);
            }
        });
    }

    public User getUserInfo() {
        return mUserInfo;
    }

    // 退出登录
    public void logout() {
        mModel.logout(new Observer<NetResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(NetResult netResult) {
                if (netResult != null) {
                    if (NetResult.SUCCESS == netResult.getState()) {
                        // 删除cookie
                        CookieManager.getInstance().getCookieJar().cleanCookie();
                        // 回到登陆页面
                        mView.logoutSuccess();
                    }
                    mView.showMessage(netResult.getMessage());
                } else {

                }
            }

            @Override
            public void onError(Throwable e) {
                Logger.i("%s --- %s", TAG, e.getMessage());
            }

            @Override
            public void onComplete() {
                Logger.i("%s --- onComplete", TAG);
            }
        });

    }

    /**
     * 更新用户信息
     */
    public void upDateUserInfo() {
        boolean phoneChange = false;
        String phone = mView.getPhone();
        if (TextUtils.isEmpty(phone)) {
            mView.showMessage("手机号不能为空！");
            return;
        }

        if (mView.phoneIsChange(phone)) {
            // 验证手机号
            if (!RegexUtil.isMobileNumber(phone)) {
                mView.showMessage("手机号码格式错误");
                return;
            }
            phoneChange = true;
        }


        boolean emailChange = false;
        String email = mView.getEmail();
        if (TextUtils.isEmpty(email)) {
            mView.showMessage("邮箱不能为空！");
            return;
        }
        if (mView.emailIsChange(email)) {
            // 验证邮箱
            if (!RegexUtil.isEmailAddr(email)) {
                mView.showMessage("邮箱格式错误");
                return;
            }
            emailChange = true;
        }


        HashMap<String, String> updateInfo = new HashMap<>();
        if (phoneChange || emailChange) {
            if (phoneChange) {
                updateInfo.put("phone", phone);
            }

            if (emailChange) {
                updateInfo.put("email", email);
            }
            mModel.updateUserInfo(updateInfo, new Observer<NetResult>() {
                @Override
                public void onSubscribe(Disposable d) {
                    mCompositeDisposable.add(d);
                }

                @Override
                public void onNext(NetResult netResult) {
                    if (netResult != null) {
                        if (NetResult.SUCCESS == netResult.getState()) {
                            loadSelfInfo();
                        }
                        mView.showMessage(netResult.getMessage());

                    } else {
                        mView.showMessage("网络异常！");
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Logger.i("%s --- %s", TAG, e.getMessage());
                }

                @Override
                public void onComplete() {
                    Logger.i("%s --- onComplete", TAG);
                }
            });
        } else {
            mView.noUserInfoChange();
        }


        mView.showMessage("更新用户信息");
    }
}
