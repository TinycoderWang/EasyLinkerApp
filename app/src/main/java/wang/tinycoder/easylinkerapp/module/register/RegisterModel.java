package wang.tinycoder.easylinkerapp.module.register;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.net.GlobalRetrofit;

/**
 * Progect：EasyLinkerApp
 * Package：wang.tinycoder.easylinkerapp.module.register
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/1 16:10
 */
public class RegisterModel implements RegisterContract.Model {

    public RegisterModel() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void register(String userName, String password, String rePassword, String phone, String email, Observer<NetResult> observer) {
        // 封装参数
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"username\":\"")
                .append(userName)
                .append("\",")
                .append("\"password\":\"")
                .append(password)
                .append("\",")
                .append("\"passwordRetry\":\"")
                .append(rePassword)
                .append("\",")
                .append("\"email\":\"")
                .append(email)
                .append("\",")
                .append("\"phone\":")
                .append(phone)
                .append("}");
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), sb.toString());

        // 请求网络
        GlobalRetrofit.getInstance().getApi()
                .register(requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
