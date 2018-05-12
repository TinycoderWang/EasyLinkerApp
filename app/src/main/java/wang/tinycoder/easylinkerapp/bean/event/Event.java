package wang.tinycoder.easylinkerapp.bean.event;

import java.io.Serializable;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.bean.event
 * Desc：事件
 * Author：TinycoderWang
 * CreateTime：2018/4/20 22:44
 */
public class Event implements Serializable {

    protected int type;
    protected int position;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
