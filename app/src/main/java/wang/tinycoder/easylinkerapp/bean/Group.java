package wang.tinycoder.easylinkerapp.bean;

import java.io.Serializable;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.bean
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/13 22:38
 */
public class Group implements Serializable {

    /**
     * name : GROUP0001
     * comment : Tinycoder的第一个分组
     * id : 1523176927905
     * user : 1522755044098
     */

    private String name;
    private String comment;
    private String id;
    private String user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
