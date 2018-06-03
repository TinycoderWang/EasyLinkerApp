package wang.tinycoder.easylinkerapp.module.device.devlist;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import wang.tinycoder.easylinkerapp.base.BasePresenter;
import wang.tinycoder.easylinkerapp.bean.Device;
import wang.tinycoder.easylinkerapp.bean.GroupDevData;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.bean.event.DeviceEvent;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.device.devlist
 * Desc：设备列表的契约类
 * Author：TinycoderWang
 * CreateTime：2018/5/4 20:59
 */
public class DevListPresenter extends BasePresenter<DevListContract.View, DevListContract.Model> {

    // 所有的设备
    private List<Device> allDevices;
    // 每页的数量
    private final int PAGE_COUNT = 10;

    // 点击位置
    private int mPosition;

    // 是否为最后
    private boolean isLast = false;
    // 当前页码
    private int currentPage = 0;
    // 所有页码
    private int allPage;

    public DevListPresenter(DevListContract.View rootView, DevListContract.Model model) {
        super(rootView, model);
        allDevices = new ArrayList<>();
    }

    @Override
    public void onStart() {
        registerRxBus(DeviceEvent.class, new Consumer<DeviceEvent>() {

            @Override
            public void accept(DeviceEvent deviceEvent) throws Exception {
                if (deviceEvent != null) {
                    mPosition = deviceEvent.getPosition();
                    Device device = deviceEvent.getData();
                    switch (deviceEvent.getType()) {
                        case DeviceEvent.DEVICE_CLICK_TYPE:   // 点击条目
                            mView.onDeviceClicked(device);
                            break;
                        case DeviceEvent.SEND_CLICK_TYPE:   // 点击发送
                            mView.onSendClicked(device, deviceEvent.getCommand());
                            break;
                    }
                }
            }
        });
    }

    /**
     * 获取群组的设备
     *
     * @param groupId 群组id
     * @param refresh 是否为刷新
     */
    public void requestDeviceBygroup(String groupId, final boolean refresh) {

        if (refresh) {
            currentPage = 0;
        }

        if (!refresh && isLast) {   // 没有更多数据了
            mView.refreshComplete(refresh);
            return;
        }

        // 请求数据
        mModel.requestDeviceByGroup(groupId, currentPage, PAGE_COUNT, new Observer<NetResult<GroupDevData>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(NetResult<GroupDevData> groupDevDataNetResult) {
                if (groupDevDataNetResult != null && NetResult.SUCCESS == groupDevDataNetResult.getState()) {
                    // 如果为刷新就清空
                    if (refresh) {
                        allDevices.clear();
                    }
                    GroupDevData data = groupDevDataNetResult.getData();
                    if (data != null) {
                        List<Device> deviceData = data.getData();
                        // 总页码
                        allPage = data.getTotalPages();
                        // 是否为最后
                        if (!(isLast = data.isIsLast())) {
                            currentPage++;
                        }
                        // 请求回来的设备列表
                        if (deviceData != null && deviceData.size() > 0) {
                            int oldCount = allDevices.size();
                            allDevices.addAll(deviceData);
                            if (refresh) {
                                mView.updateAllDevices();
                            } else {
                                mView.updateBegin(oldCount);
                            }
                        } else {
                            mView.updateAllDevices();
                        }
                    }
                } else {
                    mView.requestError();
                }
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("%s requestDeviceBygroup error ! MSG : %s", TAG, e.getMessage());
                mView.requestError();
                mView.refreshComplete(refresh);
            }

            @Override
            public void onComplete() {
                Logger.i("%s requestDeviceBygroup onComplete !", TAG);
                mView.refreshComplete(refresh);
            }
        });
    }


    /**
     * 请求所有设备
     *
     * @param groupId 群组id
     */
    public void requestAllDeviceBygroup(String groupId) {

        // 请求全部数据
        mModel.requestDeviceByGroup(groupId, 0, Integer.MAX_VALUE, new Observer<NetResult<GroupDevData>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(NetResult<GroupDevData> groupDevDataNetResult) {
                if (groupDevDataNetResult != null && NetResult.SUCCESS == groupDevDataNetResult.getState()) {
                    allDevices.clear();
                    GroupDevData data = groupDevDataNetResult.getData();
                    if (data != null) {
                        List<Device> deviceData = data.getData();
                        // 总页码
                        allPage = data.getTotalPages();
                        // 是否到最后页
                        if (!(isLast = data.isIsLast())) {
                            currentPage++;
                        }
                        // 请求的设备列表
                        if (deviceData != null && deviceData.size() > 0) {
                            allDevices.addAll(deviceData);
                        }
                        mView.updateAllDevicesAndtoEnd();
                    }
                } else {
                    mView.requestError();
                }
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("%s requestDeviceBygroup error ! MSG : %s", TAG, e.getMessage());
                mView.requestError();
            }

            @Override
            public void onComplete() {
                Logger.i("%s requestDeviceBygroup onComplete !", TAG);
            }
        });
    }

    public List<Device> getAllDevices() {
        return allDevices;
    }

    /**
     * 向设备发送指令
     *
     * @param deviceId 设备id
     * @param command  指令
     */
    public void sendCommandToDevice(String deviceId, String command) {

        // 处理command
        if (command.startsWith("{") && command.endsWith("}")) {   // 用户自己输入的简单json内容（不支持嵌套）
            try {
                command = command.substring(1, command.length() - 1);
                StringBuilder sb = new StringBuilder();
                String[] commands = command.split(",");
                if (commands != null) {
                    for (String item : commands) {
                        String[] innerItem = item.split(":");
                        if (innerItem != null) {
                            sb.append("\"")
                                    .append(innerItem[0].trim())
                                    .append("\":")
                                    .append("\"")
                                    .append(innerItem[1].trim())
                                    .append("\"")
                                    .append(",");
                        }
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    command = sb.toString();
                }
                command = "{" + command + "}";

            } catch (Exception e) {
                e.printStackTrace();
                mView.showMessage("指令格式错误！");
                return;
            }
        } else {   // 固定的cmd
            StringBuilder sb = new StringBuilder("{\"cmd\":\"");
            sb.append(command)
                    .append("\"}");
            command = sb.toString();
        }


        mModel.sendCommandToDevice(deviceId, command, new Observer<NetResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(NetResult netResult) {
                if (netResult != null) {

                    if (NetResult.SUCCESS == netResult.getState()) {

                    }
                    mView.showMessage(netResult.getMessage());
                } else {
                    mView.sendCommandError();
                }
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("%s requestDeviceBygroup error ! MSG : %s", TAG, e.getMessage());
                mView.requestError();
            }

            @Override
            public void onComplete() {
                Logger.i("%s requestDeviceBygroup onComplete !", TAG);
            }
        });
    }


    /**
     * 添加设备
     *
     * @param groupId      群组id
     * @param groupName    群组名称
     * @param deviceName   设备名称
     * @param deviceDesc   设备描述
     * @param locationDesc 位置描述
     * @param latitude     纬度
     * @param longitude    经度
     */
    public void addDevice(String groupId, String groupName, String deviceName, String deviceDesc, String locationDesc, String latitude, String longitude) {
        mModel.createADevice(groupId,groupName,deviceName,deviceDesc,locationDesc,latitude,longitude,new Observer<NetResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(NetResult netResult) {
                if (netResult != null) {
                    // 添加成功
                    if (NetResult.SUCCESS == netResult.getState()) {
                        mView.createDeviceSuccess();
                    }
                    mView.showMessage(netResult.getMessage());
                } else {
                    mView.createDeviceError();
                }
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("%s addDevice error ! MSG : %s", TAG, e.getMessage());
                mView.createDeviceError();
            }

            @Override
            public void onComplete() {
                Logger.i("%s addDevice onComplete !", TAG);
            }
        });
    }
}
