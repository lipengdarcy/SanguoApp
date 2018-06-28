package org.cocos2dx.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.HttpURLConnection;
import java.net.URL;

public class PSNetwork {
    static ConnectivityManager mConnManager = null;

    public static int getInternetConnectionStatus() {
        if (isLocalWiFiAvailable())
            return 1;
        if (isInternetConnectionAvailable())
            return 2;
        return 0;
    }

    public static void init(Context paramContext) {
        mConnManager = (ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static boolean isHostNameReachable(String paramString) {

        if ((paramString == null) || (paramString.length() <= 0))
            return false;
        try {
            URL localURL = new URL(paramString);
            int j = ((HttpURLConnection) localURL.openConnection()).getResponseCode();
            if (j == 200)
                return true;
        } catch (Exception localException) {

        }
        return false;
    }

    public static boolean isInternetConnectionAvailable() {
        int i = 0;
        if (mConnManager == null)
            return false;
        if (isLocalWiFiAvailable())
            return true;
        try {
            NetworkInfo.State localState1 = NetworkInfo.State.CONNECTED;
            NetworkInfo.State localState2 = mConnManager.getNetworkInfo(0).getState();
            if (localState1 == localState2)
                return true;
        } catch (Exception e) {
        }
        return false;
    }

    //wifi状态是否可用
    public static boolean isLocalWiFiAvailable() {
        int i = 0;
        if (mConnManager == null)
            return false;
        NetworkInfo.State localState = mConnManager.getNetworkInfo(1).getState();
        if (NetworkInfo.State.CONNECTED != localState)
            return false;
        return true;
    }
}