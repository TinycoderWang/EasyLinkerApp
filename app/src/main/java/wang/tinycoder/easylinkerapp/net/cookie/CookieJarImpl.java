package wang.tinycoder.easylinkerapp.net.cookie;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import wang.tinycoder.easylinkerapp.net.cookie.store.CookieStore;

public class CookieJarImpl implements CookieJar {
    private CookieStore cookieStore;

    public CookieJarImpl(CookieStore cookieStore) {
        if (cookieStore == null) {
            new IllegalArgumentException("cookieStore can not be null.");
        }
        this.cookieStore = cookieStore;
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.add(url, cookies);
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url) {
        return cookieStore.get(url);
    }

    /**
     * 清空所有的cookie
     *
     * @return
     */
    public boolean cleanCookie() {
        return cookieStore.removeAll();
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }
}