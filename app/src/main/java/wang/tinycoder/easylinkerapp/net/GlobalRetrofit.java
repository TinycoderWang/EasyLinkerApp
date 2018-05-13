package wang.tinycoder.easylinkerapp.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import wang.tinycoder.easylinkerapp.app.Constants;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.bean.User;
import wang.tinycoder.easylinkerapp.bean.event.CookieOverTime;
import wang.tinycoder.easylinkerapp.net.cookie.CookieManager;
import wang.tinycoder.easylinkerapp.util.RxBus;

/**
 * Progect：EasyLinkerApp
 * Package：wang.tinycoder.easylinkerapp.net
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/1 14:12
 */
public class GlobalRetrofit {

    private static final String LOG_TAG = GlobalRetrofit.class.getName();

    private static final String COOKIE_NAME_PREFIX = "cookie_";

    private Retrofit retrofit;
    private Api mApi;

    public static final String CONTENT_TYPE_NAME = "Content-Type";
    public static final String CONTENT_ACCEPT_NAME = "Accept";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_FILE_JSON = "multipart/form-data";

    private static class LazyHolder {
        private static GlobalRetrofit INSTANCE = new GlobalRetrofit();
    }

    public static GlobalRetrofit getInstance() {
        return LazyHolder.INSTANCE;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Api getApi() {
        return mApi;
    }

    private GlobalRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(genericClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//为了使用Rxjava必须添加
                .build();
        mApi = retrofit.create(Api.class);
    }

    public OkHttpClient genericClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // OkHttpClient
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .cookieJar(CookieManager.getInstance().getCookieJar())
                .addInterceptor(interceptor)
                .addInterceptor(headerInterceptor)
                //.addInterceptor(loginSaveCookieInterceptor)
                .addInterceptor(cookieOverTime)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        return httpClient;
    }

    // 统一添加请求头的拦截器
    private Interceptor headerInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader(CONTENT_TYPE_NAME, CONTENT_TYPE_JSON)
                    .addHeader(CONTENT_ACCEPT_NAME, CONTENT_TYPE_JSON)
                    .build();
            return chain.proceed(request);
        }
    };

    // cookie过期的处理
    private Interceptor cookieOverTime = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder()
                    .build();
            Response response = chain.proceed(request);
            byte[] responseByte = response.body().bytes();
            try {
                String responseBody = new String(responseByte);
                if (!TextUtils.isEmpty(responseBody)) {
                    Gson gson = new Gson();
                    NetResult<User> userNetResult = gson.fromJson(responseBody, NetResult.class);
                    if (userNetResult.getState() == NetResult.FAILD && "只有经过登陆认证成功才能访问!".equals(userNetResult.getMessage())) {
                        // cookie过期
                        Logger.i("cookie Over Time!");
                        CookieManager.getInstance().getCookieJar().cleanCookie();
                        RxBus.getIntanceBus().post(new CookieOverTime());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 在前面获取bytes的时候response的stream已经被关闭了,要重新生成response
            return response.newBuilder().body(ResponseBody.create(null, responseByte)).build();
        }
    };

    private Interceptor loginSaveCookieInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request()
                    .newBuilder()
                    .build();
            HttpUrl url = request.url();
            Logger.i("Request url : %s", url);
            Response response = chain.proceed(request);
            byte[] responseByte = response.body().bytes();
            try {
                if ("/userLogin".equals(url.encodedPath())) {   // 登录接口
                    String responseBody = new String(responseByte);
                    if (!TextUtils.isEmpty(responseBody)) {
                        Gson gson = new Gson();
                        NetResult<User> userNetResult = gson.fromJson(responseBody, NetResult.class);
                        if (userNetResult.getState() == NetResult.FAILD) {
                            // 登录失败，清空cookie
                            Logger.i("Login Faild!");
                            CookieManager.getInstance().getCookieJar().cleanCookie();
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 在前面获取bytes的时候response的stream已经被关闭了,要重新生成response
            return response.newBuilder().body(ResponseBody.create(null, responseByte)).build();
        }
    };

}
