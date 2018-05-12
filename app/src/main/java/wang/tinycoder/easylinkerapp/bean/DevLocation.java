package wang.tinycoder.easylinkerapp.bean;

import java.io.Serializable;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.bean
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/15 21:22
 */
public class DevLocation implements Serializable {

    /**
     * latitude : N39°54′6.74″
     * describe : 厦门科技园
     * longitude : E116°23′29.52″
     */

    private String latitude;
    private String describe;
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
