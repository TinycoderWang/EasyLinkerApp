package wang.tinycoder.easylinkerapp.module.login;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.bean.User;
import wang.tinycoder.easylinkerapp.net.GlobalRetrofit;

/**
 * Progect：EasyLinkerApp
 * Package：wang.tinycoder.easylinkerapp.module.login
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/1 9:52
 */
public class LoginModel implements LoginContract.Model {


    public LoginModel() {
    }


    @Override
    public void login(String userName, String password, Observer<NetResult<User>> observer) {

        // 将参数封装为json
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"loginParam\":\"")
                .append(userName)
                .append("\",")
                .append("\"password\":\"")
                .append(password)
                .append("\"}");
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), sb.toString());
        
        // 请求网络
        GlobalRetrofit.getInstance().getApi()
                .login(requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void onDestroy() {

    }


}
