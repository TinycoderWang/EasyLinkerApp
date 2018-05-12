package wang.tinycoder.easylinkerapp.module.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import wang.tinycoder.easylinkerapp.R;
import wang.tinycoder.easylinkerapp.base.BaseActivity;
import wang.tinycoder.easylinkerapp.module.login.LoginActivity;
import wang.tinycoder.easylinkerapp.widget.PasswordEditText;

/**
 * Progect：EasyLinkerApp
 * Package：wang.tinycoder.easylinkerapp.module.register
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/1 16:07
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.et_user_name)
    EditText mEtUserName;
    @BindView(R.id.et_password)
    PasswordEditText mEtPassword;
    @BindView(R.id.et_re_password)
    PasswordEditText mEtRePassword;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_email)
    EditText mEtEmail;
    @BindView(R.id.btn_register)
    Button mBtnRegister;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initPresenter() {
        mPresenter = new RegisterPresenter(this, new RegisterModel());
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

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


    /**
     * 注册
     */
    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        mPresenter.register();
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
    public String getUserRePassword() {
        return mEtRePassword != null ? mEtRePassword.getText().toString() : "";
    }

    @Override
    public String getUserPhone() {
        return mEtPhone != null ? mEtPhone.getText().toString().trim() : "";
    }

    @Override
    public String getUserEmail() {
        return mEtEmail != null ? mEtEmail.getText().toString().trim() : "";
    }

    @Override
    public void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        launchOtherActivity(intent, true);
    }
}
