package org.darcy.sanguo.thread;

import org.darcy.sanguo.client.Account;
import org.darcy.sanguo.client.GameAdmin;

//心跳包
public class HeartSender implements Runnable {

    public void run() {
        if (Account.USER_ID == null)
            return;
        try {
            GameAdmin.onHeartpacket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}