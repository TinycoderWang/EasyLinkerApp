package wang.tinycoder.easylinkerapp.module.home.fragment.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import wang.tinycoder.easylinkerapp.R;
import wang.tinycoder.easylinkerapp.app.Constants;
import wang.tinycoder.easylinkerapp.base.BaseActivity;
import wang.tinycoder.easylinkerapp.base.BaseFragment;
import wang.tinycoder.easylinkerapp.bean.User;
import wang.tinycoder.easylinkerapp.module.login.LoginActivity;
import wang.tinycoder.easylinkerapp.net.imageloader.GlideApp;
import wang.tinycoder.easylinkerapp.widget.CircleImageView;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.home.fragment.my
 * Desc：个人设置的页面
 * Author：TinycoderWang
 * CreateTime：2018/4/22 10:55
 */
public class MySettingFragment extends BaseFragment<MySettingPresenter> implements MySettingContract.View {
    @BindView(R.id.iv_header)
    CircleImageView mIvHeader;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.divider)
    View mDivider;
    @BindView(R.id.et_sign)
    EditText mEtSign;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.ll_phone)
    LinearLayout mLlPhone;
    @BindView(R.id.et_email)
    EditText mEtEmail;
    @BindView(R.id.ll_email)
    LinearLayout mLlEmail;
    @BindView(R.id.tv_logout)
    TextView mTvLogout;
    @BindView(R.id.ll_edit)
    LinearLayout mLlEdit;
    @BindView(R.id.iv_edit)
    ImageView mIvEdit;
    @BindView(R.id.tv_edit)
    TextView mTvEdit;

    // 是否正在编辑
    private boolean isEditeting;
    // 当前的电话
    private String mCurrentPhone;
    // 当前的邮箱
    private String mCurrentEmail;


    @Override
    protected int getlayoutId() {
        return R.layout.fragment_my_setting;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MySettingPresenter(this, new MySettingModel());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mEtSign.setText("EasyLinker，让物联更简单！");
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        // 加载个人信息
        mPresenter.loadSelfInfo();
    }

    /**
     * 更新UI
     */
    private void updateUi() {
        mIvEdit.setImageResource(isEditeting ? R.drawable.ic_edit_en : R.drawable.ic_edit_dis);
        mTvEdit.setText(isEditeting ? "提交" : "编辑");
        mTvEdit.setTextColor(isEditeting ? ContextCompat.getColor(mActivity, R.color.tab_blue) : ContextCompat.getColor(mActivity, R.color.nomal_text_color));
        mEtPhone.setFocusable(isEditeting);
        mEtPhone.setEnabled(isEditeting);
        mEtPhone.setFocusableInTouchMode(isEditeting);
        mEtEmail.setFocusable(isEditeting);
        mEtEmail.setEnabled(isEditeting);
        mEtEmail.setFocusableInTouchMode(isEditeting);
        if (isEditeting) {
            mEtPhone.requestFocus();
            mEtPhone.setSelection(mEtPhone.getText().toString().length());
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onLoadSelfInfo(boolean success) {
        if (success) {

            isEditeting = false;
            updateUi();

            User userInfo = mPresenter.getUserInfo();
            if (userInfo != null) {

                // 头像
                String avatar = userInfo.getAvatar();
                if (!TextUtils.isEmpty(avatar)) {
                    if (avatar.startsWith("/")) {
                        avatar = Constants.BASE_HOST + "api" + avatar;
                    } else {
                        avatar = Constants.BASE_HOST + "api/" + avatar;
                    }
                }
                GlideApp.with(this)
                        .load(avatar)
                        .placeholder(R.drawable.ic_header_default)
                        .error(R.drawable.ic_header_default)
                        .into(mIvHeader);

                // 名字
                String username = userInfo.getUsername();
                mTvName.setText(TextUtils.isEmpty(username) ? "用户名" : username);
                // 手机
                mCurrentPhone = userInfo.getPhone();
                mEtPhone.setText(TextUtils.isEmpty(mCurrentPhone) ? "无" : mCurrentPhone);
                // 邮箱
                mCurrentEmail = userInfo.getEmail();
                mEtEmail.setText(TextUtils.isEmpty(mCurrentEmail) ? "无" : mCurrentEmail);
            }
        }
    }

    @Override
    public void logoutSuccess() {
        Intent intent = new Intent(mActivity, LoginActivity.class);
        mActivity.startActivity(intent);
        ((BaseActivity) mActivity).finish();
    }

    @Override
    public String getPhone() {
        return mEtPhone.getText().toString().trim();
    }

    @Override
    public String getEmail() {
        return mEtEmail.getText().toString().trim();
    }

    @Override
    public boolean phoneIsChange(String phone) {
        return !phone.equals(mCurrentPhone);
    }

    @Override
    public boolean emailIsChange(String email) {
        return !email.equals(mCurrentEmail);
    }

    @Override
    public void noUserInfoChange() {
        isEditeting = false;
        updateUi();
    }


    @OnClick({R.id.tv_logout, R.id.ll_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_logout:   // 退出
                mPresenter.logout();
                break;
            case R.id.ll_edit:   // 编辑
//                if (isEditeting) {
//                    // 提交修改
//                    mPresenter.upDateUserInfo();
//                } else {
//                    isEditeting = true;
//                    updateUi();
//                }
                break;
        }
    }


}
