package org.darcy.sanguo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import org.darcy.sanguo.activity.LoginAct;
import org.darcy.sanguo.activity.PayAct;
import org.darcy.sanguo.sdk.interfaces.LoginCallback;
import org.darcy.sanguo.sdk.interfaces.PayCallback;
import org.darcy.sanguo.sdk.manager.CallBackManager;
//import com.tendcloud.tenddata.TalkingDataGA;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxGLSurfaceView;
import org.cocos2dx.lib.Cocos2dxLuaJavaBridge;
import org.cocos2dx.utils.PSNative;
import org.json.JSONException;
import org.json.JSONObject;

public class Sanguo extends Cocos2dxActivity {
    public static Activity actInstance;
    static int luaFuncHnadle;
    static String notifyUrl;
    static String tag = "初见>>>SDK";
    static String uId; //用户id
    private LinearLayout _webLayout;
    private WebView _webView;

    static {
        luaFuncHnadle = -1;
        notifyUrl = "http://p.sg.16801.com/billing/ChuJianCallback";
        uId = "";
        System.loadLibrary("game");
    }

    public static void CJLogin() {
        Intent localIntent = new Intent(actInstance, LoginAct.class);
        Bundle localBundle = new Bundle();
        localBundle.putBoolean("islandScreen", true);
        localIntent.putExtras(localBundle);
        actInstance.startActivity(localIntent);
    }

    static void commonPostEvent(String paramString1, String paramString2) {
        if (-1 == luaFuncHnadle)
            return;
        Cocos2dxLuaJavaBridge.callLuaFunctionWithString(luaFuncHnadle, paramString1 + "," + paramString2);
    }

    public static void directURL(String paramString) {
        if ((paramString == null) || (paramString.length() <= 0))
            return;
        ((Sanguo) actInstance).inerDirectURLHandle(paramString);
    }

    public static Object getJavaActivity() {
        return actInstance;
    }


    private void initSDKCallback() {
        CallBackManager.addLoginCallBack(new LoginCallback() {
            public void loginFinish(String paramString) {
                try {
                    JSONObject json = new JSONObject(paramString);
                    Sanguo.uId = (String) json.get("uId");
                    Log.d("uId", Sanguo.uId);
                    String sessionId = (String) json.get("sessionId");
                    Log.d("sessionId", sessionId);
                    if ((Sanguo.uId == null) || ("".equals(Sanguo.uId)) || (sessionId == null) || ("".equals(sessionId))) {
                        Log.e(Sanguo.tag, "login Error....");
                        Sanguo.commonPostEvent("loginErro", "");
                        return;
                    }
                    Log.d(Sanguo.tag, "login success...");
                    Sanguo.commonPostEvent("loginSuccess", Sanguo.uId);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Sanguo.commonPostEvent("loginErro", "");
                }
            }
        });
        CallBackManager.addPayCallBack(new PayCallback() {
            public void onPayComplete(String paramString) {
                try {
                    JSONObject localJSONObject = new JSONObject(paramString);
                    if ("1".equals(localJSONObject.getString("code"))) {
                        Sanguo.commonPostEvent("PAYSUCCEED", "");
                        return;
                    }
                    Sanguo.commonPostEvent("PAYFAIL", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void pay(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, String paramString5, float paramFloat) {
        Intent intent = new Intent(actInstance, PayAct.class);
        Bundle localBundle = new Bundle();
        localBundle.putBoolean("islandScreen", true);
        localBundle.putString("userName", paramString1);
        localBundle.putString("uId", uId);
        localBundle.putSerializable("productName", paramString3);
        localBundle.putString("productId", paramString4);
        localBundle.putInt("productCount", paramInt);
        localBundle.putString("orderId", paramString5);
        localBundle.putDouble("amount", paramFloat);
        localBundle.putString("notifyUrl", notifyUrl);
        intent.putExtras(localBundle);
        actInstance.startActivity(intent);
    }

    static void setCallBackFun(int paramInt) {
        luaFuncHnadle = paramInt;
    }

    public void displayWebView(final int leftMargin, final int topMargin, final int width, final int height) {
        runOnUiThread(new Runnable() {
            public void run() {
                Sanguo.this._webView = new WebView(Sanguo.actInstance);
                Sanguo.this._webLayout.addView(Sanguo.this._webView);
                LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams) Sanguo.this._webView.getLayoutParams();
                localLayoutParams.leftMargin = leftMargin;
                localLayoutParams.topMargin = topMargin;
                localLayoutParams.width = width;
                localLayoutParams.height = height;
                Sanguo.this._webView.setLayoutParams(localLayoutParams);
                Sanguo.this._webView.setBackgroundColor(0);
                Sanguo.this._webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                Sanguo.this._webView.getSettings().setAppCacheEnabled(false);
                Sanguo.this._webView.getSettings().setJavaScriptEnabled(true);
                CookieManager.getInstance().setAcceptCookie(true);
                Sanguo.this._webView.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString) {
                        return false;
                    }
                });
            }
        });
    }

    protected void inerDirectURLHandle(String paramString) {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(paramString)));
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        actInstance = this;
        this._webLayout = new LinearLayout(this);
        actInstance.addContentView(this._webLayout, new LayoutParams(-1, -1));
        PSNative.init(this);
        //TalkingDataGA.init(this, "190004B32F58CE2F3A35C513B56F2CD4", "ANDROID_CHUJIAN");
        initSDKCallback();
    }

    public Cocos2dxGLSurfaceView onCreateView() {
        Cocos2dxGLSurfaceView localCocos2dxGLSurfaceView = new Cocos2dxGLSurfaceView(this);
        localCocos2dxGLSurfaceView.setEGLConfigChooser(5, 6, 5, 0, 16, 8);
        return localCocos2dxGLSurfaceView;
    }

    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        if ((paramInt == 4) && (this._webView != null)) ;
        for (boolean bool = false; ; bool = super.onKeyDown(paramInt, paramKeyEvent))
            return bool;
    }

    protected void onPause() {
        super.onPause();
        //TalkingDataGA.onPause(this);
    }

    protected void onResume() {
        super.onResume();
        //TalkingDataGA.onResume(this);
    }

    public void removeWebView() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (Sanguo.this._webView == null)
                    return;
                Sanguo.this._webLayout.removeView(Sanguo.this._webView);
                Sanguo.this._webView.destroy();
                Sanguo.this._webView = null;
            }
        });
    }

    public void updateURL(final String url) {
        runOnUiThread(new Runnable() {
            public void run() {
                Sanguo.this._webView.loadUrl(url);
            }
        });
    }
}