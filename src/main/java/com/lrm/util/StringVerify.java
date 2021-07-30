package com.lrm.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串校验
 *
 * @author 山水夜止
 * @version 1.0
 * @date 2021-07-21
 */
public class StringVerify {
    /**
     * 在使用正则表达式时，利用好其预编译功能，可以有效加快正则匹配速度。
     */
    static Pattern p = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
    static Pattern p1 = Pattern.compile("[a-zA-Z]");

    /**
     * 字符串是否包含中文
     *
     * @param str 待校验字符串
     * @return true 包含中文字符  false 不包含中文字符
     */
    public static boolean isContainChinese(String str) {

        //检验是否为空
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        Matcher m = StringVerify.p.matcher(str);
        return m.find();
    }
    /**
     * 字符串是否包含英文
     *
     * @param str 待校验字符串
     * @return true 包含中文字符  false 不包含英文
     */
    public static boolean isContainLetter(String str) {

        //检验是否为空
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        Matcher m = StringVerify.p1.matcher(str);
        return m.find();
    }
}
