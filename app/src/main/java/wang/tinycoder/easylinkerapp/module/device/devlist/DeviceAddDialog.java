package wang.tinycoder.easylinkerapp.module.device.devlist;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import wang.tinycoder.easylinkerapp.R;

/**
 * Progect：EasyLinkerApp
 * Package：wang.tinycoder.easylinkerapp.module.device.devlist
 * Desc：添加设备的对话框
 * Author：TinycoderWang
 * CreateTime：2018/6/3 10:04
 */
public class DeviceAddDialog extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "SubjectAnswerDialogFragment";

    TextView title;
    AppCompatEditText deviceName;
    AppCompatEditText deviceDesc;
    AppCompatEditText locationDesc;
    TextView cancle;
    TextView sure;

    // 标题
    private String titleStr;


    private OnCreatSuccess creeatSuccessListener;

    public void setSuccessListener(OnCreatSuccess successListener) {
        this.creeatSuccessListener = successListener;
    }

    private OnActionListener actionListener;

    public void setActionListener(OnActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i(TAG, "onCreate");
        setCancelable(true);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Logger.i(TAG, "onCreateView");
        View mView = inflater.inflate(R.layout.dialog_device_add, null);
        title = (TextView) mView.findViewById(R.id.tv_title);
        deviceName = (AppCompatEditText) mView.findViewById(R.id.et_dev_name);
        deviceDesc = (AppCompatEditText) mView.findViewById(R.id.et_dev_desc);
        locationDesc = (AppCompatEditText) mView.findViewById(R.id.et_dev_location);
        cancle = (TextView) mView.findViewById(R.id.tv_cancle);
        sure = (TextView) mView.findViewById(R.id.tv_sure);

        // 标题
        title.setText(TextUtils.isEmpty(titleStr) ? "添加设备" : titleStr);

        // 点击事件
        cancle.setOnClickListener(this);
        sure.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.85), ViewGroup.LayoutParams.WRAP_CONTENT);
            if (creeatSuccessListener != null) {
                creeatSuccessListener.onCreateSuccess(this);
            }
        }
    }

    public void setTitle(String titleStr) {
        this.titleStr = titleStr;
        if (title != null) {
            title.setText(TextUtils.isEmpty(titleStr) ? "新增群组" : titleStr);
        }
    }


    /**
     * 获取群组名称
     *
     * @return
     */
    public String getDeviceName() {
        return deviceName.getText().toString().trim();
    }


    /**
     * 获取群组描述
     *
     * @return
     */
    public String getDeviceDesc() {
        return deviceDesc.getText().toString().trim();
    }


    /**
     * 获取位置描述
     *
     * @return
     */
    public String getLocationDesc() {
        return locationDesc.getText().toString().trim();
    }


    @Override
    public void onClick(View v) {
        if (actionListener != null) {
            switch (v.getId()) {
                case R.id.tv_cancle:   // 取消
                    actionListener.cancle(this);
                    break;
                case R.id.tv_sure:   // 确定
                    // 设备名称
                    String devName = getDeviceName();
                    // 设备描述
                    String devDesc = getDeviceDesc();
                    // 位置描述
                    String locationDesc = getLocationDesc();
                    // 非空判断
                    if (TextUtils.isEmpty(devName)) {
                        Toast.makeText(getActivity(), "请输入设备名称", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(devDesc)) {
                        Toast.makeText(getActivity(), "请输入设备备注", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(locationDesc)) {
                        Toast.makeText(getActivity(), "请输入位置备注", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    actionListener.sure(this, devName, devDesc, locationDesc);
                    break;
            }
        }

    }


    /**
     * 创建成功回调
     */
    interface OnCreatSuccess {
        void onCreateSuccess(DeviceAddDialog instance);
    }


    /**
     * 动作的回调
     */
    interface OnActionListener {
        void cancle(DeviceAddDialog instance);

        void sure(DeviceAddDialog instance, String deviceName, String deviceDesc, String locationDesc);
    }


    @Override
    public void dismiss() {
        deviceName.setText("");
        deviceDesc.setText("");
        locationDesc.setText("");
        super.dismiss();
    }
}