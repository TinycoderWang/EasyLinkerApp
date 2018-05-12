package wang.tinycoder.easylinkerapp.module.home.fragment.dev;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import wang.tinycoder.easylinkerapp.bean.Group;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.net.GlobalRetrofit;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.home.fragment.dev
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/13 22:30
 */
public class DevManageModel implements DevManageContract.Model {
    @Override
    public void onDestroy() {

    }


    /**
     * 获取所有分组
     *
     * @param observer
     */
    @Override
    public void requestDevGroups(Observer<NetResult<List<Group>>> observer) {
        GlobalRetrofit.getInstance().getApi()
                .getAllDeviceGroups()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void requestDevGroupsByPage(int page, int count, Observer<NetResult<List<Group>>> observer) {
        GlobalRetrofit.getInstance().getApi()
                .getAllDeviceGroupsByPage(page, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    /**
     * 添加群组
     *
     * @param groupName 群组名称
     * @param groupDesc 群组描述
     * @param observer
     */
    @Override
    public void addDevGroup(String groupName, String groupDesc, Observer<NetResult> observer) {

        // 将参数封装为json
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"groupName\":\"")
                .append(groupName)
                .append("\",")
                .append("\"comment\":\"")
                .append(groupDesc)
                .append("\"}");
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), sb.toString());

        GlobalRetrofit.getInstance().getApi()
                .addDevGroup(requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    @Override
    public void updateDevGroup(String groupId, String groupName, String groupDesc, Observer<NetResult> observer) {
        // 将参数封装为json
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"groupId\":")
                .append(groupId)
                .append(",")
                .append("\"groupName\":\"")
                .append(groupName)
                .append("\",")
                .append("\"comment\":\"")
                .append(groupDesc)
                .append("\"}");
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), sb.toString());

        GlobalRetrofit.getInstance().getApi()
                .updateDevGroup(requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
