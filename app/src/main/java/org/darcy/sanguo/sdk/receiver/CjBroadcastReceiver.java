package org.darcy.sanguo.sdk.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import org.darcy.sanguo.utils.NetUtil;
import org.darcy.sanguo.service.SysPushService;

public class CjBroadcastReceiver extends BroadcastReceiver {
    private static String PREF_NAME_KV = "CjBroadcastReceiver";

    public void onReceive(Context paramContext, Intent paramIntent) {
        SharedPreferences sharedPreferences = paramContext.getSharedPreferences(PREF_NAME_KV, 0);
        long l = sharedPreferences.getLong("firstTimeRec", 0L);
        if (System.currentTimeMillis() - l <= 10000L)
            return;
        l = System.currentTimeMillis();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("firstTimeRec", l);
        editor.commit();
        if ((!(NetUtil.isNetworkAvaiable())) || (SysPushService.isConnected()))
            return;
        SysPushService.start(paramContext);
    }
}