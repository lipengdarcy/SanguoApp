package org.darcy.sanguo.message;

import android.os.Handler;
import android.os.Message;
import org.darcy.sanguo.pojo.SimpleMap;
import org.darcy.sanguo.client.GameAdmin;
import org.darcy.sanguo.utils.JsonUtil;
import org.darcy.sanguo.utils.LogUtil;

import java.util.Map;

public class ASender implements Runnable {
    private static final int MO9pay = 3010;
    private static final int YIBAO_PAY = 3020;
    private static final int commonInter = 1000;
    private static final int landRegistration = 2000;
    private static final int paymentAlipay = 3000;
    Handler handler;
    String itfc;
    SimpleMap<String, Object> params;

    public ASender(Handler paramHandler, String paramString, SimpleMap<String, Object> paramSimpleMap) {
        this.itfc = paramString;
        this.params = paramSimpleMap;
        this.handler = paramHandler;
    }

    public void run() {
        Message localMessage = null;
        String str = null;//HttpsUtil2.postHttps(GameAdmin.getContext(), "https://gacj.16801.com:8643" + this.itfc, this.params);
        if (GameAdmin.isConsoleLog())
            System.out.println("rs:::" + str);
        Map localMap = JsonUtil.toMap(str);
        int i = 0;
        if (this.itfc.equals("/inter/commonInter.page"))
            i = 1000;
        if (this.itfc.equals("/inter/landRegistration.page"))
            i = 2000;
        if (this.itfc.equals("/inter/paymentAlipay.page"))
            i = 3000;
        if (this.itfc.equals("/inter/paymentMo9.page"))
            i = 3010;
        if (this.itfc.equals("/inter/paymentYeepay.page"))
            i = 3020;
        if (this.handler != null) {
            localMessage = new Message();
            localMessage.what = (Integer.parseInt((String) this.params.get("funcNo")) + i);
            if (str == null) {
                return;
            }
        }
        LogUtil.writeHttpLog("https://gacj.16801.com:8643" + this.itfc + "------" + str);
    }
}