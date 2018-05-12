package wang.tinycoder.easylinkerapp.module.device.devbind;

import io.reactivex.Observer;
import wang.tinycoder.easylinkerapp.base.interfaces.IModel;
import wang.tinycoder.easylinkerapp.base.interfaces.IView;
import wang.tinycoder.easylinkerapp.bean.NetResult;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.device.devbind
 * Desc：绑定设备页面的契约类
 * Author：TinycoderWang
 * CreateTime：2018/4/27 20:57
 */
public interface DevBindContract {

    interface View extends IView {

        void onBindResult(boolean result);
    }

    interface Model extends IModel {
        /**
         * 绑定设备
         *
         * @param groupId  群组id
         * @param deviceid 设备id
         * @param observer
         */
        void bindDevice(String groupId, String deviceid, Observer<NetResult> observer);
    }

}
