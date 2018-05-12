package wang.tinycoder.easylinkerapp.bean.event;

import java.io.Serializable;

import wang.tinycoder.easylinkerapp.bean.Device;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.bean.event
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/20 22:45
 */
public class DeviceEvent extends Event implements Serializable {

    // 点击条目
    public static final int DEVICE_CLICK_TYPE = 1;
    public static final int SEND_CLICK_TYPE = 2;

    private Device data;
    private String command;

    public DeviceEvent(int position, int type, Device data, String command) {
        this.type = type;
        this.position = position;
        this.data = data;
        this.command = command;
    }

    public DeviceEvent(int position, int type, Device data) {
        this.type = type;
        this.position = position;
        this.data = data;
    }

    public String getCommand() {
        return command;
    }

    public Device getData() {
        return data;
    }
}
