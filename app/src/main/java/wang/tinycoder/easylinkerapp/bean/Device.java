package wang.tinycoder.easylinkerapp.bean;

import java.io.Serializable;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.bean
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/15 21:14
 */
public class Device implements Serializable {


//    "name":"device_Auto_3",
//    "lastActiveDate":"2018-05-03T03:48:59.000+0000",
//    "isOnline":false,
//    "location":"{\"latitude\":\"厦门科技园\"}",
//    "id":1525319410672,
//    "describe":"Product_3",
//    "barCode":"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJYAAACWAQAAAAAUekxPAAAA6ElEQVR42u3WwQ6CQAwE0N76y721t/7yHkzWmWIE8cb2pBBi4JlQbGdBmV/bQ277eQsRzRBPHjRYYh8anlnH6xaar0LaZnPobLThOduMe5jPU1+uWv18bue5XbRZ9208/8jVVQtDDwDHma8Y8zNMh44qs2zsgsYm2WBDzAMJQtLfPV2xyRsmh+91FwzjMUchcWCHoZ1IED4wrQbLujSXIcp0GMNtJiL7zFesMo5vOCRvsFp6DDqKaYNtS4X9PPR0xfDoQlsHemDeZIi3sYg2GWJepVqMjFceY95hnBEND27PBrv/0/yXPQF0iJDRTCF7RwAAAABJRU5ErkJggg=="


    private String name;
    private String lastActiveDate;
    private boolean isOnline;
    private DevLocation location;
    private String id;
    private String describe;
    private String key;
    private String barCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastActiveDate() {
        return lastActiveDate;
    }

    public void setLastActiveDate(String lastActiveDate) {
        this.lastActiveDate = lastActiveDate;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public DevLocation getLocation() {
        return location;
    }

    public void setLocation(DevLocation location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
