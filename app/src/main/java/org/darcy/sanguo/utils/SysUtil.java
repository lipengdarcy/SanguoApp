package org.darcy.sanguo.utils;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.darcy.sanguo.SanguoApplication;
import org.darcy.sanguo.client.GameAdmin;

import java.io.InputStream;

public class SysUtil {
    private static final String PREF_NAME_KV = "kv_pref";
    private static final String TAG = "test";
    private static Application sApp;
    private static LayoutInflater sInflater;
    private static NotificationManager sNotifManager;
    private static Resources sRes;

    public static void cancelNotification(int paramInt, String paramString) {
        sNotifManager.cancel(paramString, paramInt);
    }

    public static AssetManager getAsset() {
        return sApp.getAssets();
    }

    public static Bitmap getBitmap(int paramInt) {
        return ((BitmapDrawable) getDrawable(paramInt)).getBitmap();
    }

    public static Boolean getBooleanPref(String paramString) {
        return Boolean.valueOf(sApp.getSharedPreferences("kv_pref", 0).getBoolean(paramString, false));
    }

    public static Boolean getBooleanPref(String paramString, boolean paramBoolean) {
        return Boolean.valueOf(sApp.getSharedPreferences("kv_pref", 0).getBoolean(paramString, paramBoolean));
    }

    public static int getColor(int paramInt) {
        return sRes.getColor(paramInt);
    }

    public static ContentResolver getContentResolver() {
        return sApp.getContentResolver();
    }

    public static DisplayMetrics getDisplayMetrics() {
        return sApp.getResources().getDisplayMetrics();
    }

    public static Drawable getDrawable(int paramInt) {
        return sRes.getDrawable(paramInt);
    }

    public static float getFloatPref(String paramString) {
        return sApp.getSharedPreferences("kv_pref", 0).getFloat(paramString, -1.0F);
    }

    public static int getIntPref(String paramString) {
        return sApp.getSharedPreferences("kv_pref", 0).getInt(paramString, -1);
    }

    //获取手机的IMEI信息
    public static String getPhoneInfo(String paramString) throws SecurityException {
        TelephonyManager telephonyManager = (TelephonyManager) SanguoApplication.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        //手机串号:GSM手机的IMEI 和 CDMA手机的 MEID.
        String deviceID = telephonyManager.getDeviceId();
        //手机号(有些手机号无法获取，是因为运营商在SIM中没有写入手机号)
        if ("PHONE_NO".equals(paramString)){
            return telephonyManager.getLine1Number();
        }
        //获取手机SIM卡的序列号
        if ("IMEI".equals(paramString)){
            return telephonyManager.getSimSerialNumber();
        }
        throw new IllegalArgumentException("cant get info:" + paramString);
    }

    public static PackageInfo getPkgInfo() {
        String packageName = sApp.getPackageName();
        PackageManager localPackageManager = sApp.getPackageManager();
        try {
            PackageInfo info = localPackageManager.getPackageInfo(packageName, PackageManager.MATCH_DISABLED_UNTIL_USED_COMPONENTS);
            return info;
        } catch (PackageManager.NameNotFoundException e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), e.toString());
        }
        return null;
    }

    public static String getPref(String paramString) {
        return sApp.getSharedPreferences("kv_pref", 0).getString(paramString, null);
    }

    public static InputStream getRaw(int paramInt) {
        return sApp.getResources().openRawResource(paramInt);
    }

    public static String getString(int paramInt) {
        return sRes.getString(paramInt);
    }

    public static String getString(int paramInt, Object[] paramArrayOfObject) {
        return sRes.getString(paramInt, paramArrayOfObject);
    }

    public static View inflate(int paramInt) {
        return sInflater.inflate(paramInt, null);
    }

    public static View inflate(int paramInt, ViewGroup paramViewGroup) {
        return sInflater.inflate(paramInt, paramViewGroup);
    }

    public static void init(Application paramApplication) {
        if (sApp != null)
            throw new RuntimeException("the app has been inited!");
        sApp = paramApplication;
        sRes = paramApplication.getResources();
        sInflater = LayoutInflater.from(paramApplication);
        sNotifManager = (NotificationManager) sApp.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static void removePref(String paramString) {
        SharedPreferences.Editor localEditor = sApp.getSharedPreferences("kv_pref", 0).edit();
        localEditor.remove(paramString);
        localEditor.commit();
    }

    public static void savePref(String paramString, float paramFloat) {
        SharedPreferences.Editor localEditor = sApp.getSharedPreferences("kv_pref", 0).edit();
        localEditor.putFloat(paramString, paramFloat);
        localEditor.commit();
    }

    public static void savePref(String paramString, int paramInt) {
        SharedPreferences.Editor localEditor = sApp.getSharedPreferences("kv_pref", 0).edit();
        localEditor.putInt(paramString, paramInt);
        localEditor.commit();
    }

    public static void savePref(String paramString, boolean paramBoolean) {
        SharedPreferences.Editor localEditor = sApp.getSharedPreferences("kv_pref", 0).edit();
        localEditor.putBoolean(paramString, paramBoolean);
        localEditor.commit();
    }

    public static void savePref(String[] paramArrayOfString) {
        if ((paramArrayOfString == null) || (paramArrayOfString.length < 2))
            return;
        SharedPreferences.Editor localEditor = sApp.getSharedPreferences("kv_pref", 0).edit();
        for (int i = 0; ; i += 2) {
            while (i >= paramArrayOfString.length)
                localEditor.commit();
            localEditor.putString(paramArrayOfString[i], paramArrayOfString[(i + 1)]);
        }
    }

    public static void showNotification(int paramInt, String paramString, Notification paramNotification) {
        sNotifManager.notify(paramString, paramInt, paramNotification);
    }

    public int getVerCode() {
        int i;
        String str = sApp.getPackageName();
        PackageManager localPackageManager = sApp.getPackageManager();
        try {
            i = localPackageManager.getPackageInfo(str, PackageManager.GET_CONFIGURATIONS).versionCode;
            return i;
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), localNameNotFoundException.toString());
        }
        return 0;
    }

    public String getVerName() {
        String packageName = sApp.getPackageName();
        Object localObject = sApp.getPackageManager();
        try {
            String versionName = ((PackageManager) localObject).getPackageInfo(packageName, PackageManager.MATCH_DISABLED_UNTIL_USED_COMPONENTS).versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), e.toString());
        }
        return null;
    }
}