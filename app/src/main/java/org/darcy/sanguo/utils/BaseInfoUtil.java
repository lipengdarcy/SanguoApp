package org.darcy.sanguo.utils;

import android.os.Build;
import org.darcy.sanguo.pojo.SimpleMap;
import org.darcy.sanguo.constant.InnerConstants;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BaseInfoUtil {
    private static SimpleMap<String, String> BASE_INFO;

    public static String MD5(String paramString) {
        int i;
        StringBuilder localStringBuilder = null;
        try {
            byte[] array = MessageDigest.getInstance("MD5").digest(paramString.getBytes("UTF-8"));
            localStringBuilder = new StringBuilder(array.length * 2);
            int j = array.length;
            i = 0;
            if (i < j)
                return paramString;
            paramString = localStringBuilder.toString();
            return paramString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    public static SimpleMap<String, Object> getBaseParam() {
        SimpleMap simpleMap = new SimpleMap();
        if (BASE_INFO != null)
            simpleMap = (SimpleMap) BASE_INFO.clone();
        try {
            ParamUtil.addParam(simpleMap, "version", "4");
            ParamUtil.addParam(simpleMap, "sysType", "1");
            ParamUtil.addParam(simpleMap, "channelID", InnerConstants.CHANNEL_ID);
            ParamUtil.addParam(simpleMap, "gameID", InnerConstants.GAME_ID);
            String diviceInfo = getDiviceInfo();
            if (InnerConstants.MAC != null) {
                ParamUtil.addParam(simpleMap, "mac", InnerConstants.MAC);
            }
            if (InnerConstants.IMEI != null) {
                ParamUtil.addParam(simpleMap, "imei", InnerConstants.IMEI);
            }
            if (InnerConstants.IMSI != null) {
                ParamUtil.addParam(simpleMap, "imsi", InnerConstants.IMSI);
            }
            if (InnerConstants.ANDROID_ID != null) {
                ParamUtil.addParam(simpleMap, "diviceInfo", InnerConstants.ANDROID_ID);
            }
            BASE_INFO = simpleMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simpleMap;
    }

    public static String getDiviceInfo() {
        return "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.DISPLAY.length() % 10) + (Build.HOST.length() % 10) + (Build.ID.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10) + (Build.TAGS.length() % 10) + (Build.TYPE.length() % 10) + (Build.USER.length() % 10);
    }
}