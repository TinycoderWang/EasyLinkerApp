package wang.tinycoder.easylinkerapp.bean;

import java.io.Serializable;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.bean
 * Desc：banner对象
 * Author：TinycoderWang
 * CreateTime：2018/5/6 12:53
 */
public class Banner implements Serializable {

    private String img;
    private String url;
    private String title;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
