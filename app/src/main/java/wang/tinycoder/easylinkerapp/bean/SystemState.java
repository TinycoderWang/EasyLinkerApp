package wang.tinycoder.easylinkerapp.bean;

import java.io.Serializable;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.bean
 * Desc：当前系统状态
 * Author：TinycoderWang
 * CreateTime：2018/5/2 20:33
 */
public class SystemState implements Serializable {

    private int offLine;   // 离线
    private int total;   // 总数
    private int onLine;   // 在线

    public SystemState() {
    }

    public SystemState(int offLine, int total, int onLine) {
        this.offLine = offLine;
        this.total = total;
        this.onLine = onLine;
    }

    public int getOffLine() {
        return offLine;
    }

    public void setOffLine(int offLine) {
        this.offLine = offLine;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOnLine() {
        return onLine;
    }

    public void setOnLine(int onLine) {
        this.onLine = onLine;
    }
}
