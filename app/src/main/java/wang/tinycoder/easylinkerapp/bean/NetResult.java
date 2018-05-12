package wang.tinycoder.easylinkerapp.bean;

import java.io.Serializable;

/**
 * Progect：EasyLinkerApp
 * Package：wang.tinycoder.easylinkerapp.bean
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/1 14:32
 */
public class NetResult<T> implements Serializable {


    // 失败
    public static final int FAILD = 0;
    // 成功
    public static final int SUCCESS = 1;

    // 状态
    private int state;
    // 信息
    private String message;
    // 数据
    private T data;

    public NetResult() {
    }

    public NetResult(int state, String message, T data) {
        this.state = state;
        this.message = message;
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
