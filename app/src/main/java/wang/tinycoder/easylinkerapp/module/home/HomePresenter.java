package wang.tinycoder.easylinkerapp.module.home;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import io.reactivex.functions.Consumer;
import wang.tinycoder.easylinkerapp.base.BaseFragment;
import wang.tinycoder.easylinkerapp.base.BasePresenter;
import wang.tinycoder.easylinkerapp.bean.event.CookieOverTime;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.home
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/8 17:21
 */
public class HomePresenter extends BasePresenter<HomeContract.View, HomeContract.Model> {

    // fragment的管理器和当前fragment
    private FragmentManager mFragmentManager;
    private BaseFragment mFragment;

    public HomePresenter(HomeContract.View rootView, HomeContract.Model model) {
        super(rootView, model);
        registerRxBus(CookieOverTime.class, new Consumer<CookieOverTime>() {
            @Override
            public void accept(CookieOverTime cookieOverTime) throws Exception {
                mView.cookieOverTime();
            }
        });
    }


    /**
     * 切换fragment
     *
     * @param fragmentContent 容器id
     * @param from            从哪个fragment
     * @param to              到哪个fragment
     * @param position        到的fragment的索引
     */
    public void switchContent(@IdRes int fragmentContent, BaseFragment from, BaseFragment to, int position) {
        if (mFragment != to) {
            mFragment = to;
            if (mFragmentManager == null) {
                mFragmentManager = ((HomeActivity) mView).getSupportFragmentManager();
            }
            FragmentTransaction transaction = mFragmentManager.beginTransaction();

            if (!to.isAdded()) { // 先判断是否被add过
                if (from == null) {
                    transaction.add(fragmentContent, to, String.valueOf(position)).show(to).commit(); // add到Activity中
                } else {
                    transaction.hide(from)
                            .add(fragmentContent, to, String.valueOf(position)).show(to).commit(); // 隐藏当前的fragment，add下一个到Activity中
                }
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }
}
