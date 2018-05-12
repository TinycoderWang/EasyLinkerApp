package wang.tinycoder.easylinkerapp.module.device.devbind;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import wang.tinycoder.easylinkerapp.R;
import wang.tinycoder.easylinkerapp.app.Constants;
import wang.tinycoder.easylinkerapp.base.BaseActivity;
import wang.tinycoder.qrcoder.core.QRCodeView;
import wang.tinycoder.qrcoder.zxing.ZXingView;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.device.devbind
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/27 20:57
 */
public class DevBindActivity extends BaseActivity<DevBindPresenter> implements DevBindContract.View, QRCodeView.Delegate {


    // 是否拥有摄像头的权限
    private boolean hasCameraPermission = false;

    @BindView(R.id.zxv_scanner)
    ZXingView mZxvScanner;
    private String mGroupId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dev_bind;
    }

    @Override
    public void initPresenter() {

        // 群组的id
        mGroupId = getIntent().getStringExtra(Constants.EXTRA_GROUP_ID);
        if (TextUtils.isEmpty(mGroupId)) {
            showToast("群组绑定设备异常，请退出后重试！");
            return;
        }
        mPresenter = new DevBindPresenter(this, new DevBindModel());
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mZxvScanner.setDelegate(this);
        // 权限检验
        RxPermissions rxPermissions = new RxPermissions(this);
        // 获取相机权限
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        hasCameraPermission = aBoolean.booleanValue();
                        if (hasCameraPermission) {
                            // 开始扫描二维码
                            mZxvScanner.startSpotDelay(100);
                            mZxvScanner.startCamera();
                            mZxvScanner.showScanRect();
                        } else {
                            showMessage("您拒绝了相机权限，无法进行扫码绑定！");
                            finish();
                        }
                    }
                });
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


    @Override
    protected void onStart() {
        super.onStart();
        if (hasCameraPermission) {
            mZxvScanner.startSpotDelay(1000);
            mZxvScanner.startCamera();
//            // 打开前摄像头
//            mZxvScanner.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
            mZxvScanner.showScanRect();
        }
    }

    @Override
    protected void onStop() {
        if (hasCameraPermission) {
            mZxvScanner.stopSpot();
            mZxvScanner.stopCamera();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZxvScanner.onDestroy();
        super.onDestroy();
    }


    @Override
    public void onScanQRCodeSuccess(String result) {
        Logger.i("%s onScanQRCodeSuccess result : ", TAG, result);
        if (!TextUtils.isEmpty(result) && isMatchDevId(result)) {
            // 停止扫码
            mZxvScanner.stopSpot();
            // 绑定设备
            mPresenter.bindDevice(mGroupId, result);
        }

    }

    // 是否符合设备id格式
    private boolean isMatchDevId(String result) {
        Pattern deviceId = Pattern.compile("\\d{13}");
        Matcher m = deviceId.matcher(result);
        return m.matches();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错,请确认您是否开启了相应权限！");
    }

    /**
     * 绑定结果
     *
     * @param result
     */
    @Override
    public void onBindResult(boolean result) {
        if (result) {   // 绑定成功
            Intent intent = getIntent();
            setResult(RESULT_OK, intent);
            finish();
        } else {   // 绑定失败
            if (hasCameraPermission) {
                mZxvScanner.startSpotDelay(100);
                mZxvScanner.startCamera();
//            // 打开前摄像头
//            mZxvScanner.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
                mZxvScanner.showScanRect();
            }
        }
    }
}
