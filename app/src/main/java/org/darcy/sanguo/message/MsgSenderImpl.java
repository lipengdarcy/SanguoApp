package org.darcy.sanguo.message;

import android.content.Context;
import android.os.Handler;
import org.darcy.sanguo.pojo.SimpleMap;
import org.darcy.sanguo.client.Account;
import org.darcy.sanguo.thread.HeartSender;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MsgSenderImpl {
    private static ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);
    public static ScheduledExecutorService scheduledHeartExecutorService = Executors.newScheduledThreadPool(1);

    static {
        scheduledHeartExecutorService.scheduleAtFixedRate(new HeartSender(), 5L, 300L, TimeUnit.SECONDS);
    }

    public static void onKill() {
        Account.USER_ID = null;
        Account.USER_LEVEL = Integer.valueOf(0);
        THREAD_POOL.shutdown();
        scheduledHeartExecutorService.shutdown();
    }

    public static void send(Context paramContext, Handler paramHandler, String paramString, SimpleMap<String, Object> paramSimpleMap) {
        THREAD_POOL.execute(new ASender(paramHandler, paramString, paramSimpleMap));
    }
}