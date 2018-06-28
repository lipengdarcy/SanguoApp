package org.darcy.sanguo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;

public class SysInfoUtil {
    public static String getIpAddress(Context paramContext) {
        return intToIp(((WifiManager) paramContext.getApplicationContext().getSystemService("wifi")).getConnectionInfo().getIpAddress());
    }

    @SuppressLint({"DefaultLocale"})
    public static String getMacAddress(Context paramContext) {
        String wifiManager = ((WifiManager) paramContext.getApplicationContext().getSystemService("wifi")).getConnectionInfo().getMacAddress();
        if (wifiManager == null)
            return wifiManager;
        wifiManager = wifiManager.toUpperCase();
        return wifiManager;
    }

    public static String getPhoneNum(Context paramContext) {
        return ((TelephonyManager) paramContext.getSystemService("phone")).getLine1Number();
    }

    public static String getSysInfo() {
        return new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder("Product: ").append(Build.PRODUCT).toString())).append(", CPU_ABI: ").append(Build.CPU_ABI).toString())).append(", TAGS: ").append(Build.TAGS).toString())).append(", VERSION_CODES.BASE: 1").toString())).append(", MODEL: ").append(Build.MODEL).toString())).append(", SDK: ").append(Build.VERSION.SDK).toString())).append(", VERSION.RELEASE: ").append(Build.VERSION.RELEASE).toString())).append(", DEVICE: ").append(Build.DEVICE).toString())).append(", DISPLAY: ").append(Build.DISPLAY).toString())).append(", BRAND: ").append(Build.BRAND).toString())).append(", BOARD: ").append(Build.BOARD).toString())).append(", FINGERPRINT: ").append(Build.FINGERPRINT).toString())).append(", ID: ").append(Build.ID).toString())).append(", MANUFACTURER: ").append(Build.MANUFACTURER).toString() + ", USER: " + Build.USER;
    }

    private static String intToIp(int paramInt) {
        return (paramInt & 0xFF) + "." + (paramInt >> 8 & 0xFF) + "." + (paramInt >> 16 & 0xFF) + "." + (paramInt >> 24 & 0xFF);
    }
}