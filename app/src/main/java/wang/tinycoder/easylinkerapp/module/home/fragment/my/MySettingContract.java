package wang.tinycoder.easylinkerapp.module.home.fragment.my;

import java.util.HashMap;

import io.reactivex.Observer;
import wang.tinycoder.easylinkerapp.base.interfaces.IModel;
import wang.tinycoder.easylinkerapp.base.interfaces.IView;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.bean.User;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.home.fragment.my
 * Desc：个人设置的fragment
 * Author：TinycoderWang
 * CreateTime：2018/4/22 10:52
 */
public interface MySettingContract {
    interface View extends IView {

        void onLoadSelfInfo(boolean success);

        void logoutSuccess();

        String getPhone();

        String getEmail();

        boolean phoneIsChange(String phone);

        boolean emailIsChange(String email);

        void noUserInfoChange();
    }

    interface Model extends IModel {
        void getCurrentUser(Observer<NetResult<User>> observer);

        void logout(Observer<NetResult> observer);

        void updateUserInfo(HashMap<String, String> date, Observer<NetResult> observer);
    }
}
