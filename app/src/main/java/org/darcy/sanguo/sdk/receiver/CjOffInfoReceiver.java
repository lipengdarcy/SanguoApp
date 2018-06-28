package org.darcy.sanguo.sdk.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import org.darcy.sanguo.message.MsgSenderImpl;
import org.darcy.sanguo.pojo.SimpleMap;
import org.darcy.sanguo.utils.JsonUtil;
import org.darcy.sanguo.utils.LogUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CjOffInfoReceiver extends BroadcastReceiver {
    ExecutorService pool = Executors.newFixedThreadPool(3);

    private void initPool() {
        if (this.pool != null)
            return;
        this.pool = Executors.newFixedThreadPool(3);
    }

    public void onReceive(final Context paramContext, Intent paramIntent) {
        List localList;
        Toast.makeText(paramContext, "get the reciver", Toast.LENGTH_SHORT).show();
        if (((ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null) {
            localList = LogUtil.readOfflineStrings();
            LogUtil.deleteTheOffline();
            initPool();
            if (localList.size() > 0) {
                int i = 0;
                final SimpleMap map = new SimpleMap();
                final String str = (String) localList.get(i);
                Iterator localIterator = JsonUtil.toMap((String) localList.get(i + 1)).entrySet().iterator();

                this.pool.execute(new Runnable() {
                    public void run() {
                        MsgSenderImpl.send(paramContext, null, str, map);
                    }
                });
                i += 2;

                Map.Entry localEntry = (Map.Entry) localIterator.next();
                map.put((String) localEntry.getKey(), (String) localEntry.getValue());

            }
            Log.d("fileutil", "the network can not use now");
        }


    }
}