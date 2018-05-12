package wang.tinycoder.easylinkerapp.bean.event;

import java.io.Serializable;

import wang.tinycoder.easylinkerapp.bean.Group;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.bean.event
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/20 22:45
 */
public class DevGroupEvent extends Event implements Serializable {

    // 点击群组
    public static final int GROUP_CLICK_TYPE = 1;
    // 点击管理
    public static final int MANAGE_CLICK_TYPE = 2;

    private Group data;

    public DevGroupEvent(int position, int type, Group data) {
        this.type = type;
        this.position = position;
        this.data = data;
    }

    public Group getData() {
        return data;
    }
}
