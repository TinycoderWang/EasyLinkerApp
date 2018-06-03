package wang.tinycoder.easylinkerapp.module.splash;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import okhttp3.Cookie;
import wang.tinycoder.easylinkerapp.R;
import wang.tinycoder.easylinkerapp.app.EasyLinkerApp;
import wang.tinycoder.easylinkerapp.base.BaseActivity;
import wang.tinycoder.easylinkerapp.module.home.HomeActivity;
import wang.tinycoder.easylinkerapp.module.login.LoginActivity;
import wang.tinycoder.easylinkerapp.net.cookie.CookieManager;
import wang.tinycoder.easylinkerapp.net.cookie.store.CookieStore;
import yanzhikai.textpath.AsyncTextPathView;
import yanzhikai.textpath.PathAnimatorListener;

/**
 * @author WangYh
 * @version V1.0
 * @Name: SplashActivity
 * @Package wang.tinycoder.easylinkerapp.module.splash
 * @Description: 闪屏页面
 * @date 2018/4/3 0003
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    @BindView(R.id.tpv_name)
    AsyncTextPathView mTpvName;
    @BindView(R.id.tpv_msg)
    AsyncTextPathView mTpvMsg;
    @BindView(R.id.tv_version)
    TextView mTvVersion;

    // 闪屏延时时间（单位ms）
    public int DELAY_TIME = 2500;
    // 时间
    long start = 0;
    long end = 0;
    // 消息类型
    private final int GOTO_HOME = 0;
    private final int GOTO_LOGIN = 1;
    private int msgType = GOTO_HOME;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg != null) {
                Intent intent = null;
                switch (msg.what) {
                    case GOTO_HOME:
                        intent = new Intent(SplashActivity.this, HomeActivity.class);
                        SplashActivity.this.startActivity(intent);
                        break;
                    case GOTO_LOGIN:
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                        SplashActivity.this.startActivity(intent);
                        break;
                }
                SplashActivity.this.finish();
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initPresenter() {
        mPresenter = new SplashPresenter(this, new SplashModel());
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        // 开始动画
        mTpvName.setDuration((int) (DELAY_TIME * 0.6));
        mTpvName.setAnimatorListener(new PathAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mTpvName != null) {
                    mTpvName.showFillColorText();
                }
            }
        });
        mTpvName.startAnimation(0, 1);

        mTpvMsg.setDuration((int) (DELAY_TIME * 0.8));
        mTpvMsg.setAnimatorListener(new PathAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mTpvMsg != null) {
                    mTpvMsg.showFillColorText();
                }
            }
        });
        mTpvMsg.startAnimation(0, 1);

        // 版本
        mTvVersion.setText(EasyLinkerApp.getInstance().getVerName());
        // 记录开始时间
        start = SystemClock.currentThreadTimeMillis();
        // 检查本地是否存在cookie
        boolean hasCookie = hasLocalCookie();
        end = start;
        if (!hasCookie) {   // 本地不存在cookie
            end = SystemClock.currentThreadTimeMillis();
            if (end - start > DELAY_TIME) {   // 耗时超过规定时间
                mHandler.sendEmptyMessage(GOTO_LOGIN);
            } else {
                new Thread() {
                    @Override
                    public void run() {
                        // 延时剩余时间
                        SystemClock.sleep(DELAY_TIME - (end - start));
                        mHandler.sendEmptyMessage(GOTO_LOGIN);
                    }
                }.start();
            }
        } else {   // 存在cookie联网确认cookie是否有效
            mPresenter.requestCurrentUser();
        }

//        // 暂时不需要权限处理
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   // 6.0需要动态获取权限
//            SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
//            dialog.setTitleText("权限申请")
//                    .setContentText("亲爱的上帝，为了您能够正常使用Easylinker,程序将会获取以下手机权限，是否继续？")
//                    .setCancelText("取消")
//                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                            sweetAlertDialog.dismiss();
//                        }
//                    })
//                    .setConfirmText("确定")
//                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                            sweetAlertDialog.dismiss();
//                        }
//                    }).show();
//        }

    }

    @Override
    public void response(final boolean hasCookie) {
        end = SystemClock.currentThreadTimeMillis();
        if (end - start > DELAY_TIME) {   // 耗时超过规定时间
            if (hasCookie) {
                mHandler.sendEmptyMessage(GOTO_HOME);
            } else {
                mHandler.sendEmptyMessage(GOTO_LOGIN);
            }
        } else {
            new Thread() {
                @Override
                public void run() {
                    // 延时剩余时间
                    SystemClock.sleep(DELAY_TIME - (end - start));
                    if (hasCookie) {
                        if (mHandler != null) {
                            mHandler.sendEmptyMessage(GOTO_HOME);
                        }
                    } else {
                        if (mHandler != null) {
                            mHandler.sendEmptyMessage(GOTO_LOGIN);
                        }
                    }
                }
            }.start();
        }
    }

    // 检查是否需要登录
    private boolean hasLocalCookie() {
        // 判断本地是否存在cookie
        CookieStore cookieStore = CookieManager.getInstance().getCookieJar().getCookieStore();
        if (cookieStore != null) {
            List<Cookie> cookies = cookieStore.getCookies();
            if (cookies != null && cookies.size() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        mHandler.removeMessages(GOTO_HOME);
        mHandler.removeMessages(GOTO_LOGIN);
        mHandler = null;
        super.onDestroy();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

}
