package org.darcy.sanguo.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.util.UUID;

public class MktLineLyt extends LinearLayout {
    private static Intent intent;
    private static WebView wb;
    private boolean cacheEnabled = false;

    private Handler handler = new Handler() {
        public void handleMessage(Message paramMessage) {
            super.handleMessage(paramMessage);
            switch (paramMessage.what) {
                case -1:
                case 0:
                case 1:
                case 2:
                    String[] array = (String[]) paramMessage.obj;
                    MktLineLyt.wb.loadDataWithBaseURL(MktLineLyt.this.localBaseUrl, array[1], "text/html", "utf-8", array[0]);
                    break;
                default:
                    String str = (String) paramMessage.obj;
                    MktLineLyt.wb.loadUrl(str);
                    break;
            }
        }
    };

    private String localBaseUrl;
    private String localCacheDir;
    private ProgressDialog pdDialog;
    private MktPluginSetting setting;

    public MktLineLyt(Context paramContext) {
        super(paramContext);
        intent = ((Activity) paramContext).getIntent();
        this.setting = ((MktPluginSetting) intent.getSerializableExtra("mokredit_android"));
        setBackgroundColor(-1);
        getBackground().setAlpha(50);
        setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        setOrientation(VERTICAL);
        wb = new WebView(paramContext);
        Object localObject = new LinearLayout.LayoutParams(-1, -1);
        ((LinearLayout.LayoutParams) localObject).topMargin = 20;
        ((LinearLayout.LayoutParams) localObject).bottomMargin = 20;
        ((LinearLayout.LayoutParams) localObject).rightMargin = 20;
        ((LinearLayout.LayoutParams) localObject).leftMargin = 20;
        ((LinearLayout.LayoutParams) localObject).gravity = 17;
        wb.setLayoutParams((ViewGroup.LayoutParams) localObject);
        addView(wb);
        if (paramContext.getCacheDir() != null)
            localObject = paramContext.getCacheDir();
        if ((localObject != null) && (((File) localObject).exists())) {
            this.localCacheDir = ((File) localObject).getPath() + "/mokredit";
            this.localBaseUrl = "file://" + this.localCacheDir + "/www/";
            localObject = new File(this.localCacheDir);
            if (!(((File) localObject).exists())) {
                Log.i("Cache dir does not exist", "try creating");
                ((File) localObject).mkdirs();
            }
            if (((File) localObject).exists()) {
                Log.i("Cache dir exists", "mark cache enabled");
                this.cacheEnabled = true;
            }
        }
        if (this.cacheEnabled) ;
        try {
            File localFile = new File("/www");
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(this.localCacheDir));
            if ((!(localFile.exists())) || (System.currentTimeMillis() - localFile.lastModified() > 604800000L)) {
                //ZipUtils.unzipToFolder(super.getClass().getResourceAsStream("jquery.zip"), this.localCacheDir);
                localFile.setLastModified(System.currentTimeMillis());
            }
            wb.setVerticalScrollBarEnabled(false);
            wb.getSettings().setSupportZoom(false);
            wb.getSettings().setSaveFormData(false);
            wb.getSettings().setSavePassword(false);
            wb.getSettings().setJavaScriptEnabled(true);
            MokreditJS mokreditJS = new MokreditJS((MktPayment) paramContext);
            //wb.addJavascriptInterface(mokreditJS, "mokredit");
            if (this.cacheEnabled)
                wb.getSettings().setUserAgentString(wb.getSettings().getUserAgentString() + "/mokredit mobile");
            wb.requestFocus();
            wb.setWebViewClient(new WebViewClient() {
                public void onReceivedSslError(WebView paramWebView, SslErrorHandler paramSslErrorHandler, SslError paramSslError) {
                    paramSslErrorHandler.proceed();
                }
            });
            wb.getSettings().setDefaultTextEncodingName("utf-8");
            if (this.setting.getUrl() == null){
                localObject = paramContext.getExternalCacheDir();
                return;
            }
            wb.loadUrl(this.setting.getUrl());
            this.pdDialog = ProgressDialog.show((Activity) paramContext, "", "Please Wait...", true, false);
            new Thread() {
                public void run() {
                    try {
                        sleep(3000L);
                        return;
                    } catch (InterruptedException localInterruptedException) {
                        localInterruptedException.printStackTrace();
                    } finally {
                        MktLineLyt.this.pdDialog.dismiss();
                    }
                }
            }
                    .start();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(paramContext, "url is not empty", Toast.LENGTH_SHORT).show();
        }

    }

    public static int a(Context paramContext, float paramFloat) {
        return (int) (paramFloat * paramContext.getResources().getDisplayMetrics().density + 0.5F);
    }

    private boolean isFromMokredit(String paramString) {
        int i = this.setting.getUrl().indexOf("gateway");
        if (paramString.contains(this.setting.getUrl().substring(0, i) + "mobile/")) {
            Log.i("fafafafafafaf", this.setting.getUrl().substring(0, i) + "mobile/");
            return true;
        }
        return false;
    }

    private class HttpThread implements Runnable {
        private String url;

        public HttpThread(String paramString) {
            this.url = paramString;
        }

        private String getHttpResponse(String paramString)
                throws Exception {
            return HttpConnectionUtil.getResponse(paramString);
        }

        public void run() {
            Message localMessage = MktLineLyt.this.handler.obtainMessage();
            try {
                Log.i("ccccccccccccccccccc", "vvvvvvvvvvvvvvvvvv");
                localMessage.what = 1;
                localMessage.obj = new String[]{this.url, getHttpResponse(this.url)};
                MktLineLyt.this.handler.sendMessage(localMessage);
                return;
            } catch (Exception localException) {
                localMessage.what = 2;
                MktLineLyt.this.handler.sendMessage(localMessage);
                localMessage.what = -1;
            }
        }
    }

    private class MokreditJS {
        private MktPayment parentActivity;

        public MokreditJS() {
        }

        public MokreditJS(MktPayment paramMktPayment) {
            this.parentActivity = paramMktPayment;
        }

        public void close() {
            this.parentActivity.finish();
        }

        public String getAi() {
            SharedPreferences localSharedPreferences = MktLineLyt.this.getContext().getSharedPreferences("mokredit", 0);
            String str = localSharedPreferences.getString("ai", null);
            if ((str != null) && (str.startsWith("MO9")))
                return str;
            str = Settings.Secure.getString(MktLineLyt.this.getContext().getContentResolver(), "android_id");
            if (!("9774d56d682e549c".equals(str))) ;
            for (str = "MO9" + Md5Encrypt.encrypt(str); ; str = "MO9" + Md5Encrypt.encrypt(UUID.randomUUID().toString()))
                while (true)
                    localSharedPreferences.edit().putString("ai", str).commit();
        }

        public String getVersion() {
            return Build.MODEL + "::" + Build.VERSION.SDK + "::" + Build.VERSION.RELEASE;
        }

        public void setFailed() {
            this.parentActivity.setResult(11);
        }

        public void setSuccess() {
            this.parentActivity.setResult(10);
        }
    }
}