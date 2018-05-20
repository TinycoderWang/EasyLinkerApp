package wang.tinycoder.easylinkerapp.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import wang.tinycoder.easylinkerapp.app.AtyContainer;
import wang.tinycoder.easylinkerapp.base.interfaces.IActivity;
import wang.tinycoder.easylinkerapp.base.interfaces.IPresenter;

/**
 * Progect：EasyLinkerApp
 * Package：wang.tinycoder.easylinkerapp.base
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/1 8:53
 */
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IActivity {

    protected final String TAG = this.getClass().getSimpleName();

    protected Unbinder mUnbinder;
    protected P mPresenter;
    protected Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        // 保存当前activity
        AtyContainer.getInstance().addActivity(this);
        initPresenter();
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this);
        initView(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        AtyContainer.getInstance().removeActivity(this);
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
        if (mPresenter != null) {
            mPresenter.onDestroy();//释放资源
        }
        this.mPresenter = null;
        super.onDestroy();
    }

    @Override
    public void showToast(@NonNull String message) {
        if (mToast == null) {
            mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(message);
        mToast.show();
    }

    @Override
    public void launchOtherActivity(@NonNull Intent intent, boolean finishSelfe) {
        startActivity(intent);
        if (finishSelfe) {
            finish();
        }
    }

    @Override
    public void launchOtherActivityForResult(@NonNull Intent intent, int requestCode, boolean finishSelfe) {
        startActivityForResult(intent, requestCode);
        if (finishSelfe) {
            finish();
        }
    }


    protected void showLoading() {
        showToast("正在请求数据。。。。。。");
    }


    protected void hideLoading() {
        showToast("请求数据完成！");
    }

    /**
     * 获取当前本地apk的版本
     *
     * @return
     */
    public int getVersionCode() {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = getPackageManager().
                    getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public void hideInput(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideInput() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            // 获取软键盘的显示状态
            if (imm.isActive()) {
                // 如果软键盘已经显示，则隐藏，反之则显示
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
