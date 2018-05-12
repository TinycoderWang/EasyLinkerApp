package wang.tinycoder.easylinkerapp.module.home.fragment.dev;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import wang.tinycoder.easylinkerapp.base.BasePresenter;
import wang.tinycoder.easylinkerapp.bean.Group;
import wang.tinycoder.easylinkerapp.bean.NetResult;
import wang.tinycoder.easylinkerapp.bean.event.DevGroupEvent;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.home.fragment.dev
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/8 20:26
 */
public class DevManagePresenter extends BasePresenter<DevManageContract.View, DevManageContract.Model> {
    private List<Group> mGroupList;
    private int mPosition = -1;

    @Override
    public void onStart() {
        registerRxBus(DevGroupEvent.class, new Consumer<DevGroupEvent>() {
            @Override
            public void accept(DevGroupEvent devGroupEvent) throws Exception {
                if (devGroupEvent != null) {
                    mPosition = devGroupEvent.getPosition();
                    Group group = devGroupEvent.getData();
                    switch (devGroupEvent.getType()) {
                        case DevGroupEvent.GROUP_CLICK_TYPE:   // 点击群组
                            mView.onGroupClicked(group);
                            break;

                        case DevGroupEvent.MANAGE_CLICK_TYPE:   // 点击管理
                            mView.onManageClicked(group);
                            break;
                    }
                }
            }
        });
    }

    public DevManagePresenter(DevManageContract.View rootView, DevManageContract.Model model) {
        super(rootView, model);
        mGroupList = new ArrayList<>();
    }


    /**
     * 获取当前用户的设备分组
     */
    public void requestDevGroups() {
        mModel.requestDevGroups(new Observer<NetResult<List<Group>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(NetResult<List<Group>> listNetResult) {
                mGroupList.clear();
                if (listNetResult != null) {
                    Logger.i("%s --- %s", TAG, listNetResult.getState());
                    List<Group> data = listNetResult.getData();
                    if (data != null && data.size() > 0) {
                        mGroupList.addAll(data);
                    }
                }
                mView.updateGroupsList();
            }

            @Override
            public void onError(Throwable e) {
                mView.onNetError();
            }

            @Override
            public void onComplete() {

            }
        });
    }


    /**
     * 刷新列表
     */
    public void refreshDevGroups() {
        mModel.requestDevGroups(new Observer<NetResult<List<Group>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(NetResult<List<Group>> listNetResult) {
                mGroupList.clear();
                if (listNetResult != null) {
                    Logger.i("%s --- %s", TAG, listNetResult.getState());
                    List<Group> data = listNetResult.getData();
                    if (data != null && data.size() > 0) {
                        mGroupList.addAll(data);
                    }
                }
                mView.updateGroupsList();
            }

            @Override
            public void onError(Throwable e) {
                mView.onNetError();
                mView.finishRefresh(true);
            }

            @Override
            public void onComplete() {
                mView.finishRefresh(true);
            }
        });
    }


    /**
     * 跟新设备列表
     *
     * @param update 更新 true  增加 false
     */
    public void updateDevGroups(final boolean update) {
        mModel.requestDevGroups(new Observer<NetResult<List<Group>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(NetResult<List<Group>> listNetResult) {
                if (listNetResult != null && NetResult.SUCCESS == listNetResult.getState()) {
                    Logger.i("%s --- %s", TAG, listNetResult.getState());
                    List<Group> data = listNetResult.getData();
                    if (data != null && data.size() > 0) {
                        if (!update) {
                            mGroupList.clear();
                            mGroupList.addAll(data);
                        } else {
                            if (mPosition != -1 && mGroupList.size() > mPosition && data.size() > mPosition) {
                                Group group = data.get(mPosition);
                                mGroupList.get(mPosition).setName(group.getName());
                                mGroupList.get(mPosition).setComment(group.getComment());
                            }
                        }

                    }
                    mView.changeGroupsList(true, update, mPosition);
                } else {
                    mView.changeGroupsList(false, update, mPosition);
                }

            }

            @Override
            public void onError(Throwable e) {
                mView.onNetError();
                mView.changeGroupsList(false, update, mPosition);
            }

            @Override
            public void onComplete() {
                Logger.i("%s --- updateDevGroups onComplete", TAG);
            }
        });
    }

    public List<Group> getGroupList() {
        return mGroupList;
    }

    /**
     * 添加群组
     *
     * @param groupName 群组名称
     * @param groupDesc 群组描述
     */
    public void addDevGroup(String groupName, String groupDesc) {
        mModel.addDevGroup(groupName, groupDesc, new Observer<NetResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(NetResult netResult) {
                if (netResult != null) {
                    if (NetResult.SUCCESS == netResult.getState()) {
                        mView.onAddGroupResult(true);
                    } else {
                        mView.onAddGroupResult(false);
                    }
                    mView.showMessage(netResult.getMessage());
                } else {
                    mView.showMessage("请求异常！");
                }
            }

            @Override
            public void onError(Throwable e) {
                Logger.i("%s --- %s", TAG, e.getMessage());
                mView.onAddGroupResult(false);
            }

            @Override
            public void onComplete() {
                Logger.i("%s --- onComplete", TAG);
            }
        });
    }


    /**
     * 添加群组
     *
     * @param groupId   群组Id
     * @param groupName 群组名称
     * @param groupDesc 群组描述
     */
    public void updateDevGroup(String groupId, String groupName, String groupDesc) {
        mModel.updateDevGroup(groupId, groupName, groupDesc, new Observer<NetResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(NetResult netResult) {
                if (netResult != null) {
                    if (NetResult.SUCCESS == netResult.getState()) {
                        mView.onUpdateGroupResult(true);
                    } else {
                        mView.onUpdateGroupResult(false);
                    }
                    mView.showMessage(netResult.getMessage());
                } else {
                    mView.showMessage("请求异常！");
                }
            }

            @Override
            public void onError(Throwable e) {
                Logger.i("%s --- %s", TAG, e.getMessage());
                mView.onUpdateGroupResult(false);
            }

            @Override
            public void onComplete() {
                Logger.i("%s --- onComplete", TAG);
            }
        });
    }

    public void setPosition(int position) {
        mPosition = position;
    }
}
