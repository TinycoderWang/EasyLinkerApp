package wang.tinycoder.easylinkerapp.module.home.fragment;

import java.util.HashMap;
import java.util.Map;

import wang.tinycoder.easylinkerapp.base.BaseFragment;
import wang.tinycoder.easylinkerapp.module.home.fragment.dev.DevManageFragment;
import wang.tinycoder.easylinkerapp.module.home.fragment.my.MySettingFragment;
import wang.tinycoder.easylinkerapp.module.home.fragment.sys.SysStateFragment;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.home.fragment
 * Desc：首页的fragment工厂类
 * Author：TinycoderWang
 * CreateTime：2018/4/13 20:44
 */
public class HomeFragmentFactory {

    private Map<Integer, BaseFragment> fragments;

    public static HomeFragmentFactory getInstance() {
        return Holder.INSTANCE;
    }

    private HomeFragmentFactory() {
        fragments = new HashMap<>();
    }

    static class Holder {
        static HomeFragmentFactory INSTANCE = new HomeFragmentFactory();
    }

    public BaseFragment create(int position) {
        // 1. 先从内存中获取 Fragment对象
        // 1, 如果内存中的Fragment对象为null, 重新创建 添加到内存中
        // 2 , 内存中有Fragment对象, 直接返回
        BaseFragment fragment = fragments.get(position);
        if (fragment == null) {
            if (0 == position) {
                // 设备管理
                fragment = new DevManageFragment();
            } else if (1 == position) {
                // 系统状态
                fragment = new SysStateFragment();
            } else if (2 == position) {
                // 个人设置
                fragment = new MySettingFragment();
            }
            if (fragment != null) {
                fragments.put(position, fragment);
            }
        }
        return fragment;

    }

}
