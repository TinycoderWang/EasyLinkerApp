package wang.tinycoder.easylinkerapp.module.home.fragment.dev;

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
import wang.tinycoder.easylinkerapp.bean.Group;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.home.fragment.dev
 * Desc：添加分组的对话框
 * Author：TinycoderWang
 * CreateTime：2018/4/21 20:13
 */
public class DevGroupAddDialog extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "SubjectAnswerDialogFragment";

    TextView title;
    AppCompatEditText groupName;
    AppCompatEditText groupDesc;
    TextView cancle;
    TextView sure;

    // 标题
    private String titleStr;
    // 群组
    private Group group;


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
        View mView = inflater.inflate(R.layout.dialog_dev_group_add, null);
        title = (TextView) mView.findViewById(R.id.tv_title);
        groupName = (AppCompatEditText) mView.findViewById(R.id.et_group_name);
        groupDesc = (AppCompatEditText) mView.findViewById(R.id.et_group_desc);
        cancle = (TextView) mView.findViewById(R.id.tv_cancle);
        sure = (TextView) mView.findViewById(R.id.tv_sure);

        // 标题
        title.setText(TextUtils.isEmpty(titleStr) ? "新增群组" : titleStr);

        // 设置信息
        if (group != null) {
            String name = group.getName();
            groupName.setText(TextUtils.isEmpty(name) ? "" : name);
            String comment = group.getComment();
            groupDesc.setText(TextUtils.isEmpty(comment) ? "" : comment);
        }

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
    public String getGroupName() {
        return groupName.getText().toString().trim();
    }


    /**
     * 获取群组描述
     *
     * @return
     */
    public String getGroupDesc() {
        return groupDesc.getText().toString().trim();
    }


    @Override
    public void onClick(View v) {
        if (actionListener != null) {
            switch (v.getId()) {
                case R.id.tv_cancle:   // 取消
                    actionListener.cancle(this);
                    break;
                case R.id.tv_sure:   // 确定
                    // 群组名称
                    String groupName = getGroupName();
                    // 描述
                    String groupDesc = getGroupDesc();
                    // 非空判断
                    if (TextUtils.isEmpty(groupName)) {
                        Toast.makeText(getActivity(), "请输入群组名称", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(groupDesc)) {
                        Toast.makeText(getActivity(), "请输入群组备注", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String groupId = "";
                    if (group != null) {
                        groupId = group.getId();
                    }
                    actionListener.sure(this, groupId, groupName, groupDesc);
                    break;
            }
        }

    }


    /**
     * 创建成功回调
     */
    interface OnCreatSuccess {
        void onCreateSuccess(DevGroupAddDialog instance);
    }


    /**
     * 动作的回调
     */
    interface OnActionListener {
        void cancle(DevGroupAddDialog instance);

        void sure(DevGroupAddDialog instance, String groupId, String groupName, String groupDesc);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public void dismiss() {
        if (group != null) {
            group = null;
        }
        super.dismiss();
    }
}
