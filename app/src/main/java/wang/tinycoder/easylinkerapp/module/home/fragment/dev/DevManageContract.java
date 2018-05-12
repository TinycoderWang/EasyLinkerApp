package wang.tinycoder.easylinkerapp.module.home.fragment.dev;

import java.util.List;

import io.reactivex.Observer;
import wang.tinycoder.easylinkerapp.base.interfaces.IModel;
import wang.tinycoder.easylinkerapp.base.interfaces.IView;
import wang.tinycoder.easylinkerapp.bean.Group;
import wang.tinycoder.easylinkerapp.bean.NetResult;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.home.fragment.dev
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/8 20:26
 */
public interface DevManageContract {
    interface View extends IView {

        /**
         * 更新群组数据
         */
        void updateGroupsList();

        /**
         * 请求网络时发生错误
         */
        void onNetError();

        /**
         * 刷新完成
         *
         * @param isRefresh 是否为刷新
         */
        void finishRefresh(boolean isRefresh);

        /**
         * 点击群组
         *
         * @param group
         */
        void onGroupClicked(Group group);

        /**
         * 点击管理
         *
         * @param group
         */
        void onManageClicked(Group group);

        /**
         * 添加群组结果
         *
         * @param success
         */
        void onAddGroupResult(boolean success);

        /**
         * 更新群组结果
         *
         * @param success
         */
        void onUpdateGroupResult(boolean success);

        void changeGroupsList(boolean success, boolean update, int position);
    }

    interface Model extends IModel {

        /**
         * 获取所有设备分组
         */
        void requestDevGroups(Observer<NetResult<List<Group>>> observer);

        /**
         * 分页获取设备分组
         *
         * @param page     页码
         * @param count    数量
         * @param observer
         */
        void requestDevGroupsByPage(int page, int count, Observer<NetResult<List<Group>>> observer);

        /**
         * 添加群组
         *
         * @param groupName
         * @param groupDesc
         * @param observer
         */
        void addDevGroup(String groupName, String groupDesc, Observer<NetResult> observer);

        /**
         * 更新群组
         *
         * @param groupId
         * @param groupName
         * @param groupDesc
         * @param observer
         */
        void updateDevGroup(String groupId, String groupName, String groupDesc, Observer<NetResult> observer);
    }
}
