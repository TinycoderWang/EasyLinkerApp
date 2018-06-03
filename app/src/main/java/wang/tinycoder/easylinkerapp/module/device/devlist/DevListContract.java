package wang.tinycoder.easylinkerapp.module.device.devlist;

import io.reactivex.Observer;
import wang.tinycoder.easylinkerapp.base.interfaces.IModel;
import wang.tinycoder.easylinkerapp.base.interfaces.IView;
import wang.tinycoder.easylinkerapp.bean.Device;
import wang.tinycoder.easylinkerapp.bean.GroupDevData;
import wang.tinycoder.easylinkerapp.bean.NetResult;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.device.devlist
 * Desc：设备列表页面的契约类
 * Author：TinycoderWang
 * CreateTime：2018/5/4 13:59
 */
public interface DevListContract {

    interface View extends IView {

        void updateAllDevices();

        void updateBegin(int oldCount);

        void requestError();

        void onDeviceClicked(Device device);

        void refreshComplete(boolean refresh);

        void updateAllDevicesAndtoEnd();

        void onSendClicked(Device device, String command);

        void sendCommandError();

        void createDeviceSuccess();

        void createDeviceError();
    }

    interface Model extends IModel {
        /**
         * 根据群组获取设备列表
         *
         * @param groupId  群组id
         * @param page     页码
         * @param count    数量
         * @param observer
         */
        void requestDeviceByGroup(String groupId, int page, int count, Observer<NetResult<GroupDevData>> observer);

        void sendCommandToDevice(String deviceId, String command, Observer<NetResult> observer);

        void createADevice(String groupId, String groupName, String deviceName, String deviceDesc, String locationDesc, String latitude, String longitude, Observer<NetResult> observer);
    }

}
