package wang.tinycoder.easylinkerapp.module.device;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;
import wang.tinycoder.easylinkerapp.R;
import wang.tinycoder.qrcoder.core.QRCodeView;
import wang.tinycoder.qrcoder.zxing.ZXingView;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.device
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/27 19:55
 */
public class DeviceActivity extends AppCompatActivity implements QRCodeView.Delegate {

    private static final String TAG = "DeviceActivity";
    // 是否拥有摄像头的权限
    private boolean hasCameraPermission = false;
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;

    private QRCodeView mQRCodeView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcoder_scan);

        // 权限检验
        RxPermissions rxPermissions = new RxPermissions(this);
        // 获取相机权限
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        hasCameraPermission = aBoolean.booleanValue();
                        if(hasCameraPermission){
                            // 开始扫描二维码
                            mQRCodeView.startCamera();
                            mQRCodeView.showScanRect();
                        }else{
                            Toast.makeText(DeviceActivity.this,"您拒绝了相机权限，无法进行扫码绑定！",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });
        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        mQRCodeView.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (hasCameraPermission) {
            mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
            mQRCodeView.showScanRect();
        }
    }

    @Override
    protected void onStop() {
        if (hasCameraPermission) {
            mQRCodeView.stopCamera();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }


    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}