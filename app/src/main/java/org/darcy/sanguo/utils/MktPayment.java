package org.darcy.sanguo.utils;

import android.app.Activity;
import android.os.Bundle;

/**
 * 机锋支付SDK是机锋网向广大开发者提供的Android平台专业开发辅助工具，开发者在Android产品中添加机锋支付SDK，
 * 即可直接使用各种基于Android的手机支付方式，无需再和各家支付公司逐一签订合同并自行开发服务端支付环境。
 */
public class MktPayment extends Activity {
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        requestWindowFeature(1);
        getWindow().clearFlags(1024);
        setContentView(new MktLineLyt(this));
    }
}