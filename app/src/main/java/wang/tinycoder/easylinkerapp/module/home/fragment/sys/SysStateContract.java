package wang.tinycoder.easylinkerapp.module.home.fragment.sys;


import io.reactivex.Observer;
import wang.tinycoder.easylinkerapp.base.interfaces.IModel;
import wang.tinycoder.easylinkerapp.base.interfaces.IView;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.bean.SystemState;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.home.fragment.sys
 * Desc：系统状态的契约类
 * Author：TinycoderWang
 * CreateTime：2018/5/6 16:16
 */
public interface SysStateContract {
    interface View extends IView {

        void showState(SystemState state);
    }

    interface Model extends IModel {

        void getCurrentState(Observer<NetResult<SystemState>> observer);
    }
}
