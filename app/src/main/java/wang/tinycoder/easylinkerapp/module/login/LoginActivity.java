package wang.tinycoder.easylinkerapp.module.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import wang.tinycoder.easylinkerapp.R;
import wang.tinycoder.easylinkerapp.app.AtyContainer;
import wang.tinycoder.easylinkerapp.app.Constants;
import wang.tinycoder.easylinkerapp.app.EasyLinkerApp;
import wang.tinycoder.easylinkerapp.base.BaseActivity;
import wang.tinycoder.easylinkerapp.module.home.HomeActivity;
import wang.tinycoder.easylinkerapp.module.register.RegisterActivity;
import wang.tinycoder.easylinkerapp.widget.PasswordEditText;

/**
 * Progect：EasyLinkerApp
 * Package：wang.tinycoder.easylinkerapp.module.login
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/1 9:13
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.et_user_name)
    EditText mEtUserName;
    @BindView(R.id.et_password)
    PasswordEditText mEtPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.tv_version)
    TextView mTvVersion;
    @BindView(R.id.cb_remember)
    CheckBox mCbRemember;

    // sp
    private SharedPreferences sp;
    // 是否记住用户信息
    private boolean mIsRemember;
    private boolean mCloseOther;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {
        mPresenter = new LoginPresenter(this, new LoginModel());
        mCloseOther = getIntent().getBooleanExtra(Constants.EXTRA_CLOSE_OTHER_ACTIVITY, false);
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        sp = getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);
        // 版本号
        mTvVersion.setText(EasyLinkerApp.getInstance().getVerName());
        mIsRemember = sp.getBoolean(Constants.REMEMBER_USER_STATE, false);
        mCbRemember.setChecked(mIsRemember);
        if (mCloseOther) {
            AtyContainer.getInstance().finishOtherActivity(this);
        }
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
    public void showMessage(@NonNull String message) {
        showToast(message);
    }

    @OnClick({R.id.btn_login, R.id.tv_register, R.id.cb_remember})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:   // 登陆
                mPresenter.login();
                break;
            case R.id.tv_register:   // 注册
                Intent intent = new Intent(this, RegisterActivity.class);
                launchOtherActivity(intent, false);
                break;
            case R.id.cb_remember:   // 记住状态
                toggleRememberState();
                break;
        }
    }

    /**
     * 切换选中状态
     */
    private void toggleRememberState() {
        mIsRemember = !mIsRemember;
        mCbRemember.setChecked(mIsRemember);
        sp.edit().putBoolean(Constants.REMEMBER_USER_STATE, mIsRemember).commit();
    }

    @Override
    public String getUserName() {
        return mEtUserName != null ? mEtUserName.getText().toString().trim() : "";
    }

    @Override
    public String getUserPassword() {
        return mEtPassword != null ? mEtPassword.getText().toString() : "";
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
