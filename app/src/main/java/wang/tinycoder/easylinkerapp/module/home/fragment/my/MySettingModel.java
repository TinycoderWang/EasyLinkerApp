package wang.tinycoder.easylinkerapp.module.home.fragment.my;

import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.bean.User;
import wang.tinycoder.easylinkerapp.net.GlobalRetrofit;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.home.fragment.my
 * Desc：个人设置的model
 * Author：TinycoderWang
 * CreateTime：2018/4/22 10:53
 */
public class MySettingModel implements MySettingContract.Model {
    @Override
    public void onDestroy() {

    }


    /**
     * 获取用户信息
     *
     * @param observer
     */
    @Override
    public void getCurrentUser(Observer<NetResult<User>> observer) {
        GlobalRetrofit.getInstance().getApi()
                .getCurrentUser()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    /**
     * 退出登录
     *
     * @param observer
     */
    @Override
    public void logout(Observer<NetResult> observer) {
        GlobalRetrofit.getInstance().getApi()
                .logout()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 更新用户信息
     *
     * @param date
     * @param observer
     */
    @Override
    public void updateUserInfo(HashMap<String, String> date, Observer<NetResult> observer) {

        if (date != null && date.size() > 0) {
            StringBuilder sb = new StringBuilder("{");
            for (Map.Entry<String, String> entry : date.entrySet()) {
                sb.append("\"").append(entry.getKey()).append("\":\"")
                        .append(entry.getValue()).append("\",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("}");
            Logger.i("requestBody : %s", sb.toString());
            RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), sb.toString());

            GlobalRetrofit.getInstance().getApi()
                    .updateUserInfo(requestBody)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }

    }


}
