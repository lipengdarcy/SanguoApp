package org.darcy.sanguo.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;

import java.util.List;

public class ServiceUtil {
    public static String getServiceClassName(List<ActivityManager.RunningServiceInfo> paramList) {
        String str = "";
        for (int i = 0; ; ++i) {
            if (i >= paramList.size())
                return str;
            str = str + ((ActivityManager.RunningServiceInfo) paramList.get(i)).service.getClassName() + " \n";
        }
    }

    public static boolean someServiceIsStart(List<ActivityManager.RunningServiceInfo> paramList, String paramString) {
        for (int i = 0; i < paramList.size(); ++i) {
            if (!(paramString.equals(((ActivityManager.RunningServiceInfo) paramList.get(i)).service.getClassName())))
                continue;
            else
                return true;

        }
        return false;
    }
}