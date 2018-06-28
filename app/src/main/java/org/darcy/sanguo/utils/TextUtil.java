package org.darcy.sanguo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断字符串是否包含中文
 * */
public class TextUtil {
    public static boolean isContainChinese(String paramString) {
        Pattern pattern = Pattern.compile("[\u4e00-\u9fcc]+");
        if (pattern.matcher(paramString).find())
            return true;
        return false;
    }
}