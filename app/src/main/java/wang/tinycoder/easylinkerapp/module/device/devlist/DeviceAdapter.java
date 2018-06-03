package wang.tinycoder.easylinkerapp.module.device.devlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wang.tinycoder.easylinkerapp.R;
import wang.tinycoder.easylinkerapp.bean.DevLocation;
import wang.tinycoder.easylinkerapp.bean.Device;
import wang.tinycoder.easylinkerapp.bean.event.DeviceEvent;
import wang.tinycoder.easylinkerapp.util.RxBus;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.home.fragment.dev
 * Desc：分组的适配器
 * Author：TinycoderWang
 * CreateTime：2018/4/20 20:49
 */
public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.Holder> {

    private List<Device> deviceData;

    public DeviceAdapter(List<Device> deviceData) {
        this.deviceData = deviceData;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (deviceData != null && deviceData.size() > position) {
            Device device = deviceData.get(position);
            // 名称
            String name = device.getName();
            String id = device.getId();
            holder.mTvDevName.setText(String.format("%s(%s)",TextUtils.isEmpty(name) ? "设备" : name,TextUtils.isEmpty(id) ? "未知" : id));
            // 在线状态
            holder.mIvOnlineState.setImageResource(device.isOnline() ? R.drawable.green_dot : R.drawable.red_dot);
            // 描述
            String desc = device.getDescribe();
            holder.mTvDevDesc.setText(String.format("备注：%s", TextUtils.isEmpty(desc) ? "无" : desc));
            // 位置
            DevLocation location = device.getLocation();
            String locationStr = location == null ? "未知" : TextUtils.isEmpty(location.getDescribe()) ? "未知" : location.getDescribe();
            holder.mTvLocation.setText(String.format("位置：%s", locationStr));
            // 最后活跃时间
            String lastActiveDate = device.getLastActiveDate();
            if (!TextUtils.isEmpty(lastActiveDate)) {   // 处理时间字符串的显示
                lastActiveDate = lastActiveDate.replace("T", " ");
                int sub = lastActiveDate.lastIndexOf(".");
                lastActiveDate = lastActiveDate.substring(0, sub > 0 ? sub : lastActiveDate.length());
            }
            holder.mTvLastActiveTime.setText(String.format("最后操作：%s", TextUtils.isEmpty(lastActiveDate) ? "未知" : lastActiveDate));
            // 箭头方向
            if (holder.mLlCommand.getVisibility() == View.VISIBLE) {
                holder.mIvCommandShow.setImageResource(R.drawable.ic_arrow_up);
            } else {
                holder.mIvCommandShow.setImageResource(R.drawable.ic_arrow_down);
            }
            // 点击事件
            holder.itemView.setTag(R.id.holder_pos, position);
            holder.itemView.setOnClickListener(clickListener);
            holder.mIvCommandShow.setTag(R.id.holder_pos, position);
            holder.mIvCommandShow.setTag(R.id.holder_value1, holder.mLlCommand);
            holder.mIvCommandShow.setOnClickListener(clickListener);
            // 点击事件
            holder.mTvSend.setTag(R.id.holder_pos, position);
            holder.mTvSend.setTag(R.id.holder_value1, holder.mEtCommand);
            holder.mTvSend.setOnClickListener(clickListener);
        }
    }

    @Override
    public int getItemCount() {
        return deviceData == null ? 0 : deviceData.size();
    }

    public void update(List<Device> groupList) {
        deviceData.clear();
        deviceData.addAll(groupList);
        notifyDataSetChanged();
    }

    static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_dev_name)
        TextView mTvDevName;
        @BindView(R.id.tv_online_state)
        TextView mTvOnlineState;
        @BindView(R.id.iv_online_state)
        ImageView mIvOnlineState;
        @BindView(R.id.tv_dev_desc)
        TextView mTvDevDesc;
        @BindView(R.id.iv_command_show)
        ImageView mIvCommandShow;
        @BindView(R.id.ll_command)
        LinearLayout mLlCommand;
        @BindView(R.id.et_command)
        EditText mEtCommand;
        @BindView(R.id.tv_send)
        TextView mTvSend;
        @BindView(R.id.tv_location)
        TextView mTvLocation;
        @BindView(R.id.tv_last_active_time)
        TextView mTvLastActiveTime;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //R.layout.device_item
        }
    }


    /**
     * 点击事件
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag(R.id.holder_pos);
            if (deviceData != null && deviceData.size() > pos) {
                DeviceEvent event = null;
                Device device = deviceData.get(pos);
                if (v.getId() == R.id.tv_send) {
                    EditText etCommand = (EditText) v.getTag(R.id.holder_value1);
                    if (etCommand != null) {
                        String command = etCommand.getText().toString().trim();
                        event = new DeviceEvent(pos, DeviceEvent.SEND_CLICK_TYPE, device, command);
                        RxBus.getIntanceBus().post(event);
                    }
                } else if (R.id.iv_command_show == v.getId()) {
                    LinearLayout llCommand = (LinearLayout) v.getTag(R.id.holder_value1);
                    if (llCommand.getVisibility() == View.VISIBLE) {
                        llCommand.setVisibility(View.GONE);
                        ((ImageView) v).setImageResource(R.drawable.ic_arrow_down);
                    } else {
                        llCommand.setVisibility(View.VISIBLE);
                        ((ImageView) v).setImageResource(R.drawable.ic_arrow_up);
                    }
                } else {
                    event = new DeviceEvent(pos, DeviceEvent.DEVICE_CLICK_TYPE, device);
                    RxBus.getIntanceBus().post(event);
                }
            }
        }
    };

}