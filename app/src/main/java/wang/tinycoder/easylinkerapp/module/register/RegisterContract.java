package wang.tinycoder.easylinkerapp.module.register;

import io.reactivex.Observer;
import wang.tinycoder.easylinkerapp.base.interfaces.IModel;
import wang.tinycoder.easylinkerapp.base.interfaces.IView;
import wang.tinycoder.easylinkerapp.bean.NetResult;

/**
 * Progect：EasyLinkerApp
 * Package：wang.tinycoder.easylinkerapp.module.register
 * Desc：注册的契约类
 * Author：TinycoderWang
 * CreateTime：2018/4/1 16:08
 */
public interface RegisterContract {
    public interface View extends IView {

        String getUserName();

        String getUserPassword();

        String getUserRePassword();

        String getUserPhone();

        String getUserEmail();

        void gotoLogin();
    }

    public interface Model extends IModel {

        /**
         * 注册
         *
         * @param userName   用户名
         * @param password   密码
         * @param rePassword 确认密码
         * @param phone      电话
         * @param email      邮箱
         * @param observer
         */
        void register(String userName, String password, String rePassword, String phone, String email, Observer<NetResult> observer);

    }

}
