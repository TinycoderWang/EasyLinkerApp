package wang.tinycoder.easylinkerapp.module.splash;

import io.reactivex.Observer;
import wang.tinycoder.easylinkerapp.base.interfaces.IModel;
import wang.tinycoder.easylinkerapp.base.interfaces.IView;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.bean.User;

/**
 * @author WangYh
 * @version V1.0
 * @Name: SplashContract
 * @Package wang.tinycoder.easylinkerapp.module.splash
 * @Description: 闪屏契约类
 * @date 2018/4/3 0003
 */
public interface SplashContract {

    interface View extends IView {
        // 请求结果返回
        void response(boolean hasCookie);
    }

    interface Model extends IModel {
        /**
         * 获取当前登陆的用户
         */
        void getCurrentUser(Observer<NetResult<User>> observer);
    }

}
