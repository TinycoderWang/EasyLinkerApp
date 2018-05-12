package wang.tinycoder.easylinkerapp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Progect：EasyLinkerApp
 * Package：wang.tinycoder.easylinkerapp.util
 * Desc：正则工具类
 * Author：TinycoderWang
 * CreateTime：2018/4/1 19:03
 */
public class RegexUtil {
    /**
     * 车牌号码Pattern
     */
    public static final Pattern PLATE_NUMBER_PATTERN = Pattern
            .compile("^[\u0391-\uFFE5]{1}[a-zA-Z0-9]{6}$");

    /**
     * 证件号码Pattern
     */
    public static final Pattern ID_CODE_PATTERN = Pattern
            .compile("^[a-zA-Z0-9]+$");

    /**
     * 编码Pattern
     */
    public static final Pattern CODE_PATTERN = Pattern
            .compile("^[a-zA-Z0-9]+$");

    /**
     * 固定电话编码Pattern
     */
    public static final Pattern PHONE_NUMBER_PATTERN = Pattern
            .compile("0\\d{2,3}-[0-9]+");

    /**
     * 邮政编码Pattern
     */
    public static final Pattern POST_CODE_PATTERN = Pattern.compile("\\d{6}");

    /**
     * 面积Pattern
     */
    public static final Pattern AREA_PATTERN = Pattern.compile("\\d*.?\\d*");

    /**
     * 手机号码Pattern
     */
    public static final Pattern MOBILE_NUMBER_PATTERN = Pattern
            .compile("\\d{11}");


    /**
     * 邮箱
     */
    public static final Pattern EMAIL_PATTERN = Pattern
            .compile("^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$");


    /**
     * 银行帐号Pattern
     */
    public static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern
            .compile("\\d{16,21}");

    /**
     * 车牌号码是否正确
     *
     * @param s
     * @return
     */
    public static boolean isPlateNumber(String s) {
        Matcher m = PLATE_NUMBER_PATTERN.matcher(s);
        return m.matches();
    }

    /**
     * 证件号码是否正确
     *
     * @param s
     * @return
     */
    public static boolean isIDCode(String s) {
        Matcher m = ID_CODE_PATTERN.matcher(s);
        return m.matches();
    }

    /**
     * 编码是否正确
     *
     * @param s
     * @return
     */
    public static boolean isCode(String s) {
        Matcher m = CODE_PATTERN.matcher(s);
        return m.matches();
    }

    /**
     * 固话编码是否正确
     *
     * @param s
     * @return
     */
    public static boolean isPhoneNumber(String s) {
        Matcher m = PHONE_NUMBER_PATTERN.matcher(s);
        return m.matches();
    }

    /**
     * 邮政编码是否正确
     *
     * @param s
     * @return
     */
    public static boolean isPostCode(String s) {
        Matcher m = POST_CODE_PATTERN.matcher(s);
        return m.matches();
    }

    /**
     * 面积是否正确
     *
     * @param s
     * @return
     */
    public static boolean isArea(String s) {
        Matcher m = AREA_PATTERN.matcher(s);
        return m.matches();
    }

    /**
     * 手机号码否正确
     *
     * @param s
     * @return
     */
    public static boolean isMobileNumber(String s) {
        Matcher m = MOBILE_NUMBER_PATTERN.matcher(s);
        return m.matches();
    }

    /**
     * 邮箱是否正确
     *
     * @param s
     * @return
     */
    public static boolean isEmailAddr(String s) {
        Matcher m = EMAIL_PATTERN.matcher(s);
        return m.matches();
    }

    /**
     * 银行账号否正确
     *
     * @param s
     * @return
     */
    public static boolean isAccountNumber(String s) {
        Matcher m = ACCOUNT_NUMBER_PATTERN.matcher(s);
        return m.matches();
    }
}
