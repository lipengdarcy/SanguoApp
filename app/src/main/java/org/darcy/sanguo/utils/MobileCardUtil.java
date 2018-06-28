package org.darcy.sanguo.utils;

import android.os.Handler;

import java.util.ArrayList;


public class MobileCardUtil {
    public static String cardType_SZX; //移动
    public static String cardType_TELECOM = "TELECOM";//电信
    public static String cardType_UNICOM = "UNICOM";//联通

    static {
        cardType_SZX = "SZX";
    }

    static ArrayList<?> getCardPayParams(String paramString1, String paramString2, String paramString3) {
        return null;
    }

    static String getCradPayPstr(String paramString1, String paramString2, String paramString3) {
        new StringBuffer();
        return null;
    }

    /**
     * @param cardNo   充值卡卡号
     * @param cardPass 充值卡密码
     */
    public static String getMobileCardType(String cardNo, String cardPass) {
        int i = cardNo.length();
        if ((i == 15) && (cardPass.length() == 19))
            cardNo = cardType_UNICOM;
        if ((i == 19) && (cardPass.length() == 18))
            cardNo = cardType_TELECOM;
        if ((i != 17) || (cardPass.length() != 18))
            cardNo = cardType_SZX;
        return cardNo;
    }

    public static void payOnMobileCard(String paramString1, String paramString2, String paramString3, Handler paramHandler) {
        new Thread().start();
    }
}