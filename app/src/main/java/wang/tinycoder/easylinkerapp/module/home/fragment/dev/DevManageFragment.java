package wang.tinycoder.easylinkerapp.module.home.fragment.dev;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import wang.tinycoder.easylinkerapp.R;
import wang.tinycoder.easylinkerapp.app.Constants;
import wang.tinycoder.easylinkerapp.base.BaseActivity;
import wang.tinycoder.easylinkerapp.base.BaseFragment;
import wang.tinycoder.easylinkerapp.bean.Banner;
import wang.tinycoder.easylinkerapp.bean.Group;
import wang.tinycoder.easylinkerapp.module.device.devlist.DevListActivity;
import wang.tinycoder.easylinkerapp.util.DensityUtils;
import wang.tinycoder.easylinkerapp.widget.recyclerbanner.RecyclerBanner;
import wang.tinycoder.easylinkerapp.widget.recyclerview.decoration.HorizontalDividerItemDecoration;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.home.fragment
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/8 20:20
 */
public class DevManageFragment extends BaseFragment<DevManagePresenter> implements DevManageContract.View {

    @BindView(R.id.rcb_banner)
    RecyclerBanner mRcbBanner;
    @BindView(R.id.ll_add_group)
    LinearLayout mLlAddGroup;
    @BindView(R.id.rcv_group_list)
    RecyclerView mRcvGroupList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_message)
    TextView mTvMessage;

    // 添加分组的dialog
    private DevGroupAddDialog groupAddDialog;

    // 设备分组的适配器
    private DevGroupAdapter mGroupAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_dev_manage;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new DevManagePresenter(this, new DevManageModel());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        mRcvGroupList.setLayoutManager(mLayoutManager);
        HorizontalDividerItemDecoration divider = new HorizontalDividerItemDecoration.Builder(mActivity)
                .color(ContextCompat.getColor(mActivity, R.color.gray))
                .size(DensityUtils.dip2px(mActivity, 2))
                .build();
        mRcvGroupList.addItemDecoration(divider);
        mRefreshLayout.setOnRefreshListener(refreshListener);

        // 测试用，写死
        List<Banner> data = new ArrayList<>();
        Banner banner1 = new Banner();
        banner1.setImg("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=538916491,4234853604&fm=200&gp=0.jpg");
        banner1.setTitle("标题1");
        data.add(banner1);
        Banner banner2 = new Banner();
        banner2.setImg("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4043833034,657086338&fm=27&gp=0.jpg");
        banner2.setTitle("标题2");
        data.add(banner2);
        Banner banner3 = new Banner();
        banner3.setImg("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1149604226,3297619402&fm=200&gp=0.jpg");
        banner3.setTitle("标题3");
        data.add(banner3);
        Banner banner4 = new Banner();
        banner4.setImg("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=975436950,2877939359&fm=27&gp=0.jpg");
        banner4.setTitle("标题4");
        data.add(banner4);
        Banner banner5 = new Banner();
        banner5.setImg("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=4007068682,575024427&fm=27&gp=0.jpg");
        banner5.setTitle("标题5");
        data.add(banner5);
        mRcbBanner.setAdapter(new BannerAdapter(mActivity, data));

    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        mPresenter.requestDevGroups();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

    @Override
    public void updateGroupsList() {

        List<Group> groupList = mPresenter.getGroupList();
        if (groupList != null && groupList.size() > 0) {
            changeMsgShowable("", false);

            if (mGroupAdapter == null) {
                mGroupAdapter = new DevGroupAdapter(groupList);
                mRcvGroupList.setAdapter(mGroupAdapter);
            } else {
                mGroupAdapter.notifyDataSetChanged();
            }

        } else {
            changeMsgShowable("暂无设备分组", true);
        }

    }

    @Override
    public void onNetError() {
        changeMsgShowable("网络错误\n点击刷新", true);
    }

    @Override
    public void finishRefresh(boolean isRefresh) {
        if (mRefreshLayout != null) {
            if (isRefresh) {
                mRefreshLayout.finishRefresh();
            } else {
                mRefreshLayout.finishLoadMore();
            }
        }
    }

    @Override
    public void onGroupClicked(Group group) {
        Intent intent = new Intent((BaseActivity) mActivity, DevListActivity.class);
        intent.putExtra(Constants.EXTRA_GROUP_BEAN, group);
        startActivity(intent);
        //((BaseActivity) mActivity).showToast("onGroupClicked -- id" + group.getId());
    }

    @Override
    public void onManageClicked(Group group) {
        showGroupUpdateDialog(group);
    }


    @Override
    public void onAddGroupResult(boolean success) {
        if (groupAddDialog != null && success) {
            groupAddDialog.dismiss();
            mPresenter.updateDevGroups(false);
        }
    }

    @Override
    public void onUpdateGroupResult(boolean success) {
        if (groupAddDialog != null && success) {
            groupAddDialog.dismiss();
            mPresenter.updateDevGroups(true);
        }
    }

    @Override
    public void changeGroupsList(boolean success, boolean update, int position) {
        if (success) {
            if (mGroupAdapter != null) {
                if (!update) {
                    List<Group> groupList = mPresenter.getGroupList();
                    mGroupAdapter.notifyItemInserted(groupList.size());
                    mRcvGroupList.smoothScrollToPosition(groupList.size() - 1);
                } else {
                    if (position != -1) {
                        mGroupAdapter.notifyItemChanged(position);
                        mPresenter.setPosition(-1);
                    }
                }
            } else {
                updateGroupsList();
            }
        }
    }


    // 添加群组
    @OnClick({R.id.ll_add_group, R.id.tv_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_add_group:   // 添加群组
                showGroupAddDialog();
                break;
            case R.id.tv_message:   // 刷新
                mPresenter.requestDevGroups();
                break;
        }

    }

    private void showGroupAddDialog() {
        DevGroupAddDialog dialog = new DevGroupAddDialog();
        dialog.setActionListener(addGroupListener);
        dialog.setTitle("新增群组");
        dialog.show(((BaseActivity) mActivity).getFragmentManager(), TAG);
    }

    private void showGroupUpdateDialog(Group group) {
        DevGroupAddDialog dialog = new DevGroupAddDialog();
        dialog.setActionListener(updateGroupListener);
        dialog.setGroup(group);
        dialog.setTitle("更新群组");
        dialog.show(((BaseActivity) mActivity).getFragmentManager(), TAG);
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

    private OnRefreshListener refreshListener = new OnRefreshListener() {
        @Override
        public void onRefresh(RefreshLayout refreshLayout) {
            mPresenter.refreshDevGroups();
        }
    };


    /**
     * 添加群组的监听
     */
    private DevGroupAddDialog.OnActionListener addGroupListener = new DevGroupAddDialog.OnActionListener() {

        @Override
        public void cancle(DevGroupAddDialog instance) {
            instance.dismiss();
        }

        @Override
        public void sure(DevGroupAddDialog instance, String groupId, String groupName, String groupDesc) {
            groupAddDialog = instance;
            mPresenter.addDevGroup(groupName, groupDesc);
            ((BaseActivity) mActivity).hideInput(instance.groupDesc);
        }
    };

    /**
     * 更新群组的监听
     */
    private DevGroupAddDialog.OnActionListener updateGroupListener = new DevGroupAddDialog.OnActionListener() {

        @Override
        public void cancle(DevGroupAddDialog instance) {
            instance.dismiss();
        }

        @Override
        public void sure(DevGroupAddDialog instance, String groupId, String groupName, String groupDesc) {
            groupAddDialog = instance;
            mPresenter.updateDevGroup(groupId, groupName, groupDesc);
            ((BaseActivity) mActivity).hideInput(instance.groupDesc);
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        mGroupAdapter = null;
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        mRcbBanner.setPlaying(true);
    }


    @Override
    protected void onInvisible() {
        super.onInvisible();
        mRcbBanner.setPlaying(false);
    }
}
