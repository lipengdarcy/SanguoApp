package org.darcy.sanguo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.darcy.sanguo.sdk.view.ProgressDialog;

import org.darcy.sanguo.client.GameAdmin;
import org.darcy.sanguo.client.Payment;

//易宝支付
public class YibaoBankAct extends Activity {
    private static final String YEEPAY_REQUEST_ADDRESS = "https://ok.yeepay.com/paymobile/api/pay/request";
    private double amount;
    private String callbackUrl = null;
    private ProgressDialog dialog;

    @SuppressLint({"HandlerLeak"})
    Handler handler = new Handler();
    private boolean isPaySuccess = false;
    private String notifyUrl;
    private String now_url = null;
    private String orderId;
    private int productCount;
    private String productId;
    private String productName;
    private String uId;
    private WebView webView;

    public void finish() {
        if (this.isPaySuccess)
            PayAct.payComplete(1);
        super.finish();
        PayAct.payComplete(0);
        return;
    }

    protected void onCreate(Bundle paramBundle) throws  SecurityException{
        super.onCreate(paramBundle);
        paramBundle = getIntent().getExtras();
        if (paramBundle == null)
            finish();
        this.amount = paramBundle.getDouble("amount");
        this.productName = paramBundle.getString("productName");
        this.uId = paramBundle.getString("uId");
        this.productId = paramBundle.getString("productId");
        this.orderId = paramBundle.getString("orderId");
        this.notifyUrl = paramBundle.getString("notifyUrl");
        this.productCount = paramBundle.getInt("productCount");
        this.webView = new WebView(this);
        WebSettings settings = this.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        setContentView(this.webView);
        String str = settings.getUserAgentString();
        String deviceId = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
        this.dialog = new ProgressDialog(this, "请稍候...", true);
        this.dialog.setCancelable(true);
        this.dialog.show();
        try {
            Payment.onPaymentYibaoBack(this.handler, this.uId, Double.valueOf(this.amount), this.productName, this.productId, Integer.valueOf(this.productCount), this.orderId, str, deviceId, this.notifyUrl);
            return;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + e.toString());
            Log.d("onPaymentYibao", e.toString());
            e.printStackTrace();
        }
    }
}