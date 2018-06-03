package wang.tinycoder.easylinkerapp.module.device.devlist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import wang.tinycoder.easylinkerapp.R;
import wang.tinycoder.easylinkerapp.app.Constants;
import wang.tinycoder.easylinkerapp.base.BaseActivity;
import wang.tinycoder.easylinkerapp.bean.Device;
import wang.tinycoder.easylinkerapp.bean.Group;
import wang.tinycoder.easylinkerapp.module.device.devbind.DevBindActivity;
import wang.tinycoder.easylinkerapp.util.DensityUtils;
import wang.tinycoder.easylinkerapp.widget.recyclerview.decoration.HorizontalDividerItemDecoration;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.device.devlist
 * Desc：设备列表
 * Author：TinycoderWang
 * CreateTime：2018/5/4 13:57
 */
public class DevListActivity extends BaseActivity<DevListPresenter> implements DevListContract.View {

    @BindView(R.id.tv_group_name)
    TextView mTvGroupName;
    @BindView(R.id.tv_bind)
    TextView mTvBind;
    @BindView(R.id.rcv_dev_list)
    RecyclerView mRcvDevList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_message)
    TextView mTvMessage;
    @BindView(R.id.fab_add_device)
    FloatingActionButton mFabAddDevice;

    // 绑定设备的请求码
    private static final int BIND_DEV_REQUEST_CODE = 668;

    // 上个页面点击的分组
    private Group mGroup;
    private LinearLayoutManager mLayoutManager;
    private DeviceAdapter mDeviceAdapter;
    private DeviceAddDialog mDevAddDialog;
    private DeviceAddDialog.OnActionListener addDeviceActionListener = new DeviceAddDialog.OnActionListener() {
        @Override
        public void cancle(DeviceAddDialog instance) {
            instance.dismiss();
        }

        @Override
        public void sure(DeviceAddDialog instance, String deviceName, String deviceDesc, String locationDesc) {
            SharedPreferences sp = getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);
            mPresenter.addDevice(mGroup.getId(), mGroup.getName(), deviceName, deviceDesc, locationDesc, sp.getString(Constants.EXTRA_CITY_LATITUDE, ""), sp.getString(Constants.EXTRA_CITY_LONGITUDE, ""));
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_dev_list;
    }

    @Override
    public void initPresenter() {
        mPresenter = new DevListPresenter(this, new DevListModel());
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mGroup = (Group) getIntent().getSerializableExtra(Constants.EXTRA_GROUP_BEAN);
        // 如果获取群组失败就返回
        if (mGroup == null) {
            showToast("获取群组设备失败，请稍后再试！");
            finish();
            return;
        }

        // 设置群组名称
        mTvGroupName.setText(String.format("%s(%s)", mGroup.getName(), mGroup.getComment()));

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRcvDevList.setLayoutManager(mLayoutManager);
        HorizontalDividerItemDecoration divider = new HorizontalDividerItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.gray))
                .size(DensityUtils.dip2px(this, 2))
                .build();
        mRcvDevList.addItemDecoration(divider);
        mRefreshLayout.setOnRefreshListener(refreshListener);
        mRefreshLayout.setOnLoadMoreListener(loadMoreListener);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.requestDeviceBygroup(mGroup.getId(), true);
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

    @OnClick({R.id.tv_bind, R.id.tv_message, R.id.fab_add_device})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bind:
                // 跳转到扫码页面
                Intent intent = new Intent(this, DevBindActivity.class);
                intent.putExtra(Constants.EXTRA_GROUP_ID, mGroup.getId());
                startActivityForResult(intent, BIND_DEV_REQUEST_CODE);
                break;
            case R.id.tv_message:
                mPresenter.requestDeviceBygroup(mGroup.getId(), true);
                break;
            case R.id.fab_add_device:
                if (mDevAddDialog == null) {
                    mDevAddDialog = new DeviceAddDialog();
                    mDevAddDialog.setActionListener(addDeviceActionListener);
                }
                mDevAddDialog.show(getFragmentManager(), "devaddDialog");
                break;
        }
    }


    private OnRefreshListener refreshListener = new OnRefreshListener() {
        @Override
        public void onRefresh(RefreshLayout refreshLayout) {
            mPresenter.requestDeviceBygroup(mGroup.getId(), true);
        }
    };

    private OnLoadMoreListener loadMoreListener = new OnLoadMoreListener() {
        @Override
        public void onLoadMore(RefreshLayout refreshLayout) {
            mPresenter.requestDeviceBygroup(mGroup.getId(), false);
        }
    };

    // 刷新列表
    @Override
    public void updateAllDevices() {
        List<Device> allDevices = mPresenter.getAllDevices();
        if (allDevices != null && allDevices.size() > 0) {
            if (mDeviceAdapter == null) {
                mDeviceAdapter = new DeviceAdapter(allDevices);
                mRcvDevList.setAdapter(mDeviceAdapter);
            } else {
                mDeviceAdapter.notifyDataSetChanged();
            }
            changeMsgShowable("暂无设备", false);
        } else {
            changeMsgShowable("暂无设备", true);
        }
    }

    @Override
    public void updateAllDevicesAndtoEnd() {
        List<Device> allDevices = mPresenter.getAllDevices();
        if (allDevices != null && allDevices.size() > 0) {
            if (mDeviceAdapter == null) {
                mDeviceAdapter = new DeviceAdapter(allDevices);
                mRcvDevList.setAdapter(mDeviceAdapter);
            } else {
                mDeviceAdapter.notifyDataSetChanged();
            }
            // 滚动到最后
            mRcvDevList.smoothScrollToPosition(allDevices.size());
            changeMsgShowable("暂无设备", false);
        } else {
            changeMsgShowable("暂无设备", true);
        }
    }


    // 上拉加载刷新
    @Override
    public void updateBegin(int oldCount) {
        List<Device> allDevices = mPresenter.getAllDevices();
        if (allDevices != null && allDevices.size() > 0) {
            if (mDeviceAdapter == null) {
                mDeviceAdapter = new DeviceAdapter(allDevices);
                mRcvDevList.setAdapter(mDeviceAdapter);
            } else {
                mDeviceAdapter.notifyItemRangeInserted(oldCount, allDevices.size());
                mDeviceAdapter.notifyItemRangeChanged(oldCount, allDevices.size());
            }
            changeMsgShowable("暂无设备", false);
        } else {
            changeMsgShowable("暂无设备", true);
        }
    }

    /**
     * 显示或隐藏信息
     *
     * @param msg  信息
     * @param show 显示或隐藏
     */
    private void changeMsgShowable(String msg, boolean show) {

        mTvMessage.setText(TextUtils.isEmpty(msg) ? "暂无数据" : msg);

        if (show) {
            mTvMessage.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.GONE);
        } else {
            mTvMessage.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
        }
    }

    // 请求失败
    @Override
    public void requestError() {
        changeMsgShowable("网络异常\n点击刷新", true);
    }

    /**
     * 当设备条目点击
     *
     * @param device 设备
     */
    @Override
    public void onDeviceClicked(Device device) {
        if (device != null) {
            showToast(device.getName());
        }
    }

    /**
     * 发送指令
     *
     * @param device  设备
     * @param command 指令
     */
    @Override
    public void onSendClicked(Device device, String command) {
        if (device != null) {
            if (TextUtils.isEmpty(command)) {
                showToast("请输入要发送的指令");
            } else {
                mPresenter.sendCommandToDevice(device.getId(), command);
                // 隐藏软键盘
                hideInput();
            }
        }
    }

    @Override
    public void sendCommandError() {
        showToast("指令发送失败！");
    }

    @Override
    public void createDeviceSuccess() {
        if (mDevAddDialog != null && mDevAddDialog.isVisible()) {
            mDevAddDialog.dismiss();
            mPresenter.requestDeviceBygroup(mGroup.getId(), true);
        }
    }

    @Override
    public void createDeviceError() {
        showToast("创建设备失败！");
    }

    // 请求完成
    @Override
    public void refreshComplete(boolean refresh) {
        if (mRefreshLayout != null) {
            if (refresh) {
                mRefreshLayout.finishRefresh();
            } else {
                mRefreshLayout.finishLoadMore();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (BIND_DEV_REQUEST_CODE == requestCode) {   // 绑定设备
            if (RESULT_OK == resultCode) {   // 绑定成功
                // 刷新设备列表（如果群组里的设备过多无法显示出新绑定的设备）
                mPresenter.requestDeviceBygroup(mGroup.getId(), true);
//                // 获取所有设备（后台返回数据位置不确定，暂时无用）
//                mPresenter.requestAllDeviceBygroup(mGroup.getId());
            }
        }
    }
}
