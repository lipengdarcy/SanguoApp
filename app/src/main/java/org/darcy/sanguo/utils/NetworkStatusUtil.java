package org.darcy.sanguo.utils;

import android.content.Context;

import org.darcy.sanguo.client.GameAdmin;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 判断网络状态
 */
public class NetworkStatusUtil {

    public static String getIPAddress(Context paramContext) {
        try {
            Enumeration list = NetworkInterface.getNetworkInterfaces();
            while (list.hasMoreElements()) {
                Enumeration addresses = ((NetworkInterface) list.nextElement()).getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = (InetAddress) addresses.nextElement();
                    if ((address.isLoopbackAddress()) || (!(address instanceof Inet4Address)))
                        continue;
                    return address.getHostAddress().toString();
                }
            }
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), e.toString());
            e.printStackTrace();
        }
        return null;
    }
}