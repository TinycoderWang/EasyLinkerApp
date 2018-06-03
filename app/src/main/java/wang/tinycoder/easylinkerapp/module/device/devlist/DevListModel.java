package wang.tinycoder.easylinkerapp.module.device.devlist;

import android.text.TextUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import wang.tinycoder.easylinkerapp.bean.GroupDevData;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.net.GlobalRetrofit;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.device.devlist
 * Desc：设备列表的module
 * Author：TinycoderWang
 * CreateTime：2018/4/27 20:59
 */
public class DevListModel implements DevListContract.Model {
    @Override
    public void onDestroy() {

    }

    /**
     * 根据群组获取设备列表
     *
     * @param groupId  群组id
     * @param page     页码
     * @param count    数量
     * @param observer
     */
    @Override
    public void requestDeviceByGroup(String groupId, int page, int count, Observer<NetResult<GroupDevData>> observer) {
        GlobalRetrofit.getInstance().getApi()
                .getAllDevicesByGroup(groupId, page, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void sendCommandToDevice(String deviceId, String command, Observer<NetResult> observer) {

        // 将参数封装为json
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"deviceId\":")
                .append(deviceId)
                .append(",")
                .append("\"payload\":")
                .append(command)
                .append("}");
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), sb.toString());

        GlobalRetrofit.getInstance().getApi()
                .sendCommandToDevice(requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void createADevice(String groupId, String groupName, String deviceName, String deviceDesc, String locationDesc, String latitude, String longitude, Observer<NetResult> observer) {

        // 将参数封装为json
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"groupId\":\"")
                .append(groupId)
                .append("\",")
                .append("\"deviceNamePrefix\":\"")
                .append(groupName)
                .append("\",")
                .append("\"deviceName\":\"")
                .append(deviceName)
                .append("\",")
                .append("\"deviceDescribe\":\"")
                .append(deviceDesc)
                .append("\",")
                .append("\"locationDescribe\":\"")
                .append(locationDesc)
                .append("\",")
                .append("\"latitude\":\"")
                .append(TextUtils.isEmpty(latitude)?"N39°54′6.74″":latitude)
                .append("\",")
                .append("\"longitude\":\"")
                .append(TextUtils.isEmpty(longitude)?"E116°23′29.52″":longitude)
                .append("\"")
                .append("}");
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), sb.toString());

        GlobalRetrofit.getInstance().getApi()
                .createDevice(requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}
