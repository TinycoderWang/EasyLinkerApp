package wang.tinycoder.easylinkerapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Progect：EasyLinkerApp
 * Package：wang.tinycoder.easylinkerapp.bean
 * Desc：
 * Author：TinycoderWang
 * CreateTime：2018/4/1 14:34
 */
public class User implements Serializable {
    private String UID;
    // 用户名
    private String username;
    // 手机
    private String phone;
    // 邮箱
    private String email;
    // 头像
    private String avatar;
    // 角色
    private List<String> authorities;

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
