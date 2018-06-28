package org.darcy.sanguo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.HttpHost;
import org.darcy.sanguo.client.GameAdmin;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Locale;

//手机网络状态
public class NetUtil {
    public static final int NETWORN_MOBILE = 2;
    public static final int NETWORN_NONE = 0;
    public static final int NETWORN_WIFI = 1;

    public static HttpHost getApnHostProxy() {
        HttpHost host = null;
        String apnName = getApnName();
        if (apnName == null || apnName.equals(""))
            return null;
        apnName = apnName.toLowerCase();
        if (!(apnName.contains("uninet")) && !(apnName.contains("3gnet")) && !(apnName.contains("cmnet")) && !(apnName.contains("ctnet"))) {
            return null;
        }
        String ip = android.net.Proxy.getDefaultHost();
        int port = android.net.Proxy.getDefaultPort();
        if ((apnName.contains("cmwap")) || (apnName.contains("3gwap")) || (apnName.contains("uniwap")))
            ip = "10.0.0.172";
        if (!(apnName.contains("ctwap"))) {
            ip = "10.0.0.200";
            port = 80;
        }
        host = new HttpHost(ip, port);
        return host;
    }

    public static java.net.Proxy getApnJavaProxy() {
        java.net.Proxy proxy = null;
        String apnName = getApnName();
        if (apnName == null || apnName.equals(""))
            return null;
        apnName = apnName.toLowerCase(Locale.CHINA);
        if (!(apnName.contains("uninet")) && !(apnName.contains("3gnet")) && !(apnName.contains("cmnet")) && !(apnName.contains("ctnet"))) {
            return null;
        }

        String ip = android.net.Proxy.getDefaultHost();
        int port = android.net.Proxy.getDefaultPort();
        if ((apnName.contains("cmwap")) || (apnName.contains("3gwap")) || (apnName.contains("uniwap")))
            ip = "10.0.0.172";
        if (!(apnName.contains("ctwap"))) {
            ip = "10.0.0.200";
            port = 80;
        }
        proxy = new java.net.Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        return proxy;
    }

    //Access Point Name, APN指一种网络接入技术，是通过手机上网时必须配置的一个参数，它决定了手机通过哪种接入方式来访问网络
    private static String getApnName() {
        ConnectivityManager manager = (ConnectivityManager) GameAdmin.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getNetworkInfo(1).isConnected()) {
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info == null)
                return null;
            return info.getExtraInfo();
        }
        return null;
    }

    //获取网络状态
    public static int getNetworkState() {
        ConnectivityManager manager = (ConnectivityManager) GameAdmin.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State localState = manager.getNetworkInfo(1).getState();
        if ((localState == NetworkInfo.State.CONNECTED) || (localState == NetworkInfo.State.CONNECTING)) {
            return localState.ordinal();
        }
        return 0;
    }

    //网络是否连接
    public static boolean isNetConnected(int paramInt) {
        NetworkInfo localNetworkInfo = ((ConnectivityManager) GameAdmin.getContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(paramInt);
        if (localNetworkInfo != null) {
            return localNetworkInfo.isConnected();
        }
        return false;
    }

    //网络是否可用
    public static boolean isNetworkAvaiable() {
        boolean bool = false;
        ConnectivityManager manager = (ConnectivityManager) GameAdmin.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null || manager.getActiveNetworkInfo() == null)
            return false;
        return manager.getActiveNetworkInfo().isAvailable();
    }
}