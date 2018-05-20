package wang.tinycoder.easylinkerapp.app;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Progect：EasyLinkerApp
 * Package：wang.tinycoder.easylinkerapp.app
 * Desc：activity的容器类（存放所有打开的activity）
 * Author：TinycoderWang
 * CreateTime：2018/5/18 21:14
 */
public class AtyContainer {
    private AtyContainer() {
    }

    private static AtyContainer instance = new AtyContainer();
    private static List<Activity> activityStack = new ArrayList<Activity>();

    public static AtyContainer getInstance() {
        return instance;
    }

    public void addActivity(Activity aty) {
        if (!activityStack.contains(aty)) {
            activityStack.add(aty);
        }
    }

    public void removeActivity(Activity aty) {
        if (activityStack.contains(aty)) {
            activityStack.remove(aty);
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束除自己之外的Activity
     */
    public void finishOtherActivity(Activity currentActivity) {
        Iterator<Activity> iterator = activityStack.iterator();
        if(iterator.hasNext()){
            Activity next = iterator.next();
            if (null != next) {
                // 忽略自己
                if (currentActivity != next) {
                    iterator.remove();
                    next.finish();
                }
            }
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishTheActivity(Class<? extends Activity> clazz) {
        Iterator<Activity> iterator = activityStack.iterator();
        if(iterator.hasNext()){
            Activity next = iterator.next();
            if (null != next) {
                // 忽略其他
                String name = clazz.getName();
                String comName = next.getClass().getName();
                if (name.equals(comName)) {
                    iterator.remove();
                    next.finish();
                }
            }
        }
    }

    /**
     * 检查是否有此Activity
     *
     * @param clazz 类
     * @return
     */
    public boolean hasActivity(Class<? extends Activity> clazz) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                // 忽略其他
                String name = clazz.getName();
                String comName = activityStack.get(i).getClass().getName();
                if (name.equals(comName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Activity getActivity(Class<? extends Activity> clazz) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                // 忽略其他
                String name = clazz.getName();
                String comName = activityStack.get(i).getClass().getName();
                if (name.equals(comName)) {
                    return activityStack.get(i);
                }
            }
        }
        return null;
    }

}
