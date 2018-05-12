package wang.tinycoder.easylinkerapp.module.home.fragment.dev;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wang.tinycoder.easylinkerapp.R;
import wang.tinycoder.easylinkerapp.bean.Group;
import wang.tinycoder.easylinkerapp.bean.event.DevGroupEvent;
import wang.tinycoder.easylinkerapp.util.RxBus;
import wang.tinycoder.easylinkerapp.widget.SwipeMenuLayout;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.home.fragment.dev
 * Desc：分组的适配器
 * Author：TinycoderWang
 * CreateTime：2018/4/20 20:49
 */
public class DevGroupAdapter extends RecyclerView.Adapter<DevGroupAdapter.Holder> {


    private List<Group> groupData;

    public DevGroupAdapter(List<Group> groups) {
        this.groupData = groups;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dev_group_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (groupData != null && groupData.size() > position) {
            Group group = groupData.get(position);
            // 名称
            String name = group.getName();
            holder.mTvName.setText(TextUtils.isEmpty(name) ? "群组" : name);
            // 描述
            String desc = group.getComment();
            holder.mTvDesc.setText(TextUtils.isEmpty(desc) ? "" : desc);

            // 点击事件
            holder.mLlGroupMsg.setTag(R.id.holder_pos, position);
            holder.mLlGroupMsg.setOnClickListener(clickListener);
            holder.mTvManage.setTag(R.id.holder_pos, position);
            holder.mTvManage.setTag(R.id.holder_value1, holder.mSwnContent);
            holder.mTvManage.setOnClickListener(clickListener);
        }
    }

    @Override
    public int getItemCount() {
        return groupData == null ? 0 : groupData.size();
    }

    public void update(List<Group> groupList) {
        groupData.clear();
        groupData.addAll(groupList);
        notifyDataSetChanged();
    }

    static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.swm_content)
        SwipeMenuLayout mSwnContent;
        @BindView(R.id.iv_icon)
        ImageView mIvIcon;
        @BindView(R.id.ll_group_msg)
        LinearLayout mLlGroupMsg;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_desc)
        TextView mTvDesc;
        @BindView(R.id.iv_arrow)
        ImageView mIvArrow;
        @BindView(R.id.tv_manage)
        TextView mTvManage;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //R.layout.dev_group_item
        }
    }


    /**
     * 点击事件
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag(R.id.holder_pos);
            if (groupData != null && groupData.size() > pos) {
                Group group = groupData.get(pos);
                DevGroupEvent event = null;
                switch (v.getId()) {
                    case R.id.ll_group_msg:   // 群组
                        event = new DevGroupEvent(pos, DevGroupEvent.GROUP_CLICK_TYPE, group);
                        RxBus.getIntanceBus().post(event);
                        break;
                    case R.id.tv_manage:   // 管理
                        SwipeMenuLayout content = (SwipeMenuLayout) v.getTag(R.id.holder_value1);
                        event = new DevGroupEvent(pos, DevGroupEvent.MANAGE_CLICK_TYPE, group);
                        RxBus.getIntanceBus().post(event);
                        content.smoothClose();
                        break;
                }
            }
        }
    };

}
