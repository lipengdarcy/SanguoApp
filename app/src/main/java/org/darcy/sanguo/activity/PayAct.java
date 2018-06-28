package org.darcy.sanguo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.darcy.sanguo.sdk.manager.CallBackManager;
import org.darcy.sanguo.sdk.popupwindow.CardPaySuccessPop;

import org.darcy.sanguo.client.GameAdmin;
import org.darcy.sanguo.client.Payment;
import org.darcy.sanguo.utils.MyResource;
import org.darcy.sanguo.sdk.view.CardPayView;
import org.darcy.sanguo.sdk.view.MyToast;
import org.darcy.sanguo.sdk.view.PayCancleDialog;
import org.darcy.sanguo.sdk.view.PayCanclebefDialog;
import org.darcy.sanguo.sdk.view.PayView;
import org.darcy.sanguo.sdk.view.ProgressDialog;
import com.google.gson.Gson;
import org.darcy.sanguo.utils.MktPayment;
import org.darcy.sanguo.utils.MktPluginSetting;

import java.util.HashMap;

public class PayAct extends Activity
        implements View.OnClickListener {
    private static final int RESULT_ALIPAY = 4001;
    public static boolean isClickNext = false;
    public static boolean isLandScreen = false;
    public static boolean isPaySuccess = false;
    public static final int payFail = 0;
    public static int payState = 0;
    public static final int paySuccess = 1;
    public static boolean showDetail;
    private RelativeLayout aliLayout;
    private TextView aliTextView;
    private double amount;
    private Button backButton;
    private PayCanclebefDialog beforeDialog;
    private RelativeLayout cardLayout;
    private CardPaySuccessPop cardPaySuccessPop;
    private CardPayView cardPayView;
    private TextView cardTextView;
    private int chooseMethod = 1;
    private PayCancleDialog dialog;
    private String gameServer;

    @SuppressLint({"HandlerLeak"})
    Handler handler = new Handler();
    private RelativeLayout mo9Layout;
    private TextView mo9TextView;
    private String notifyUrl;
    private String orderId;
    private FrameLayout payDetailLayout;
    private PayView payView;
    private ProgressDialog pdDialog = null;
    private int productCount;
    private String productId;
    private String productName;
    int screenHeight = 0;
    int screenWidth = 0;
    private FrameLayout titleLayout;
    private String uId;
    int useLength = 0;
    private String userName;
    private RelativeLayout yibaoBankLayout;
    private TextView yibaoBankTextView;

    static {
        isClickNext = false;
        payState = 0;
        showDetail = false;
        isPaySuccess = false;
    }

    private void dismissCancleDialog() {
        if (this.dialog != null)
            this.dialog.dismiss();
        if (this.beforeDialog == null)
            return;
        this.beforeDialog.dismiss();
    }

    private void findID() {
        this.titleLayout = findViewById(MyResource.getIdByName(this, "id", "chujian_sdk_pay_title_layout"));
        this.backButton = findViewById(MyResource.getIdByName(this, "id", "chujian_sdk_pay_title_backbtn"));
        this.aliLayout = findViewById(MyResource.getIdByName(this, "id", "chujian_sdk_pay_left_layout_ali"));
        this.aliTextView = findViewById(MyResource.getIdByName(this, "id", "chujian_sdk_pay_left_layout_ali_tv"));
        this.mo9Layout = findViewById(MyResource.getIdByName(this, "id", "chujian_sdk_pay_left_layout_mo9"));
        this.mo9TextView = findViewById(getid("chujian_sdk_pay_left_layout_mo9_tv"));
        this.yibaoBankLayout = findViewById(getid("chujian_sdk_pay_left_layout_yibao"));
        this.yibaoBankTextView = findViewById(getid("chujian_sdk_pay_left_layout_yibao_tv"));
        this.cardLayout = findViewById(getid("chujian_sdk_pay_left_layout_card"));
        this.cardTextView = findViewById(getid("chujian_sdk_pay_left_layout_card_tv"));
        this.payDetailLayout = findViewById(getid("chujian_sdk_pay_detail_container"));
    }

    private void getExtras(Intent paramIntent) {
        if (paramIntent != null) {
            isLandScreen = paramIntent.getBooleanExtra("isLandScreen", true);
            this.userName = paramIntent.getStringExtra("userName");
            this.uId = paramIntent.getStringExtra("uId");
            this.productName = paramIntent.getStringExtra("productName");
            this.productId = paramIntent.getStringExtra("productId");
            this.productCount = paramIntent.getIntExtra("productCount", 0);
            this.orderId = paramIntent.getStringExtra("orderId");
            this.amount = paramIntent.getDoubleExtra("amount", 0);
            this.notifyUrl = paramIntent.getStringExtra("notifyUrl");
            if (!(isLandScreen)) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return;
            }
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        isLandScreen = true;
    }

    private void getWindowSize() {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        this.screenWidth = localDisplayMetrics.widthPixels;
        this.screenHeight = localDisplayMetrics.heightPixels;
        if (this.screenWidth > this.screenHeight) ;
        for (int i = this.screenHeight; ; i = this.screenWidth) {
            this.useLength = i;
            return;
        }
    }

    private int getid(String paramString) {
        return MyResource.getIdByName(this, "id", paramString);
    }

    private void init() {
        getWindowSize();
        findID();
        setTitleLayoutSize();
        setListener();
        this.payView = new PayView(this, this, this.userName, this.productName, this.amount);
        this.cardPayView = new CardPayView(this, this.uId, this.userName, this.productName, this.productId, this.orderId, this.productCount, this.notifyUrl, this.amount, this.gameServer, isLandScreen, this.handler);
        this.payDetailLayout.addView(this.payView);
    }

    public static void payComplete(int paramInt) {
        HashMap<String, Object> localHashMap = new HashMap();
        Gson localGson = new Gson();
        switch (paramInt) {
            default:
                isPaySuccess = false;
                localHashMap.put("code", paramInt);
                localHashMap.put("info", "fail");
            case 1:
                isPaySuccess = true;
                localHashMap.put("code", paramInt);
                localHashMap.put("info", "success");
            case 0:
                isPaySuccess = false;
                localHashMap.put("code", paramInt);
                localHashMap.put("info", "fail");
        }
        while (true) {
            CallBackManager.doPayCallBack(localGson.toJson(localHashMap));
        }
    }

    private void setLayoutBkgAndTcolor(int paramInt) {
        setLayoutBkgAndTcolorDefault();
        int i = MyResource.getIdByName(this, "drawable", "chujian_sdk_pay_leftbkg");
        switch (paramInt) {
            default:
                return;
            case 1:
                this.payDetailLayout.removeAllViews();
                this.payDetailLayout.addView(this.payView);
                if (showDetail)
                    this.payView.setInfoLayoutVisible(0);
                while (true) {
                    this.payView.setMethodText("ali");
                    this.chooseMethod = 1;
                    this.aliLayout.setBackgroundResource(i);
                    this.aliTextView.setTextColor(-1);
                    this.payView.setInfoLayoutVisible(8);
                }
            case 2:
                this.payDetailLayout.removeAllViews();
                this.payDetailLayout.addView(this.payView);
                if (showDetail)
                    this.payView.setInfoLayoutVisible(0);
                while (true) {
                    this.payView.setMethodText("yibao");
                    this.chooseMethod = 2;
                    this.yibaoBankLayout.setBackgroundResource(i);
                    this.yibaoBankTextView.setTextColor(-1);
                    this.payView.setInfoLayoutVisible(8);
                }
            case 3:
                this.payDetailLayout.removeAllViews();
                this.payDetailLayout.addView(this.cardPayView);
                if (showDetail)
                    this.cardPayView.setInfoLayoutVisible(0);
                while (true) {
                    this.cardLayout.setBackgroundResource(i);
                    this.cardTextView.setTextColor(-1);
                    this.cardPayView.setInfoLayoutVisible(8);
                }
            case 4:
        }
        this.payDetailLayout.removeAllViews();
        this.payDetailLayout.addView(this.payView);
        if (showDetail)
            this.payView.setInfoLayoutVisible(0);
        while (true) {
            this.payView.setMethodText("mo9");
            this.chooseMethod = 3;
            this.mo9Layout.setBackgroundResource(i);
            this.mo9TextView.setTextColor(-1);
            this.payView.setInfoLayoutVisible(8);
        }
    }

    private void setLayoutBkgAndTcolorDefault() {
        this.aliLayout.setBackgroundColor(0);
        this.aliTextView.setTextColor(-5213392);
        this.yibaoBankLayout.setBackgroundColor(0);
        this.yibaoBankTextView.setTextColor(-5213392);
        this.cardLayout.setBackgroundColor(0);
        this.cardTextView.setTextColor(-5213392);
        this.mo9Layout.setBackgroundColor(0);
        this.mo9TextView.setTextColor(-5213392);
    }

    private void setListener() {
        this.backButton.setOnClickListener(this);
        this.aliLayout.setOnClickListener(this);
        this.mo9Layout.setOnClickListener(this);
        this.yibaoBankLayout.setOnClickListener(this);
        this.cardLayout.setOnClickListener(this);
    }

    private void setTitleLayoutSize() {
        LayoutParams localLayoutParams = new LayoutParams(-1, this.useLength / 10);
        this.titleLayout.setLayoutParams(localLayoutParams);
    }

    private void showCancleDialog() {
        if (isClickNext) {
            isClickNext = false;
            if (this.beforeDialog == null)
                this.beforeDialog = new PayCanclebefDialog(this, this);
            this.beforeDialog.show();
            return;
        }
        if (this.dialog == null)
            this.dialog = new PayCancleDialog(this, this);
        this.dialog.show();
    }

    private void showToast(String paramString) {
        MyToast toast = new MyToast(this, paramString, 0);
        toast.setGravity(80, 0, 100);
        toast.show();
    }

    private void toAli() {
        try {
            Payment.onPaymentAlipay(this.handler, this.uId, Double.valueOf(this.amount), this.productName, this.productId, Integer.valueOf(this.productCount), this.orderId, this.notifyUrl);
            return;
        } catch (Exception localException) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + localException.toString());
            if (this.pdDialog != null)
                this.pdDialog.dismiss();
            showToast("获取支付信息失败");
            localException.printStackTrace();
        }
    }

    private void toMO9() {
        try {
            Payment.onPaymentMo9(this.handler, this.uId, Double.valueOf(this.amount), this.productName, this.productId, Integer.valueOf(this.productCount), this.orderId, this.notifyUrl);
            return;
        } catch (Exception localException) {
            if (this.pdDialog != null)
                this.pdDialog.dismiss();
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + localException.toString());
            showToast("获取支付信息失败");
            localException.printStackTrace();
        }
    }

    private void toMo9Pay(String paramString) {
        MktPluginSetting localMktPluginSetting = new MktPluginSetting(paramString);
        Intent intent = new Intent();
        intent.setClass(this, MktPayment.class);
        intent.putExtra("mokredit_android", localMktPluginSetting);
        startActivityForResult(intent, 100);
    }

    private void toYibaoBank() {
        Intent localIntent = new Intent(this, YibaoBankAct.class);
        Bundle localBundle = new Bundle();
        localBundle.putDouble("amount", this.amount);
        localBundle.putString("productName", this.productName);
        localBundle.putString("uId", this.uId);
        localBundle.putString("productId", this.productId);
        localBundle.putString("orderId", this.orderId);
        localBundle.putString("notifyUrl", this.notifyUrl);
        localBundle.putInt("productCount", this.productCount);
        localIntent.putExtras(localBundle);
        startActivity(localIntent);
    }

    public int getRequestedOrientation() {
        return super.getRequestedOrientation();
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        if ((paramInt1 == 100) && (paramInt2 == 10)) {
            payComplete(1);
            return;
        }
        payComplete(0);

    }

    public void onClick(View paramView) {
        if (paramView.equals(this.backButton))
            if (!(isPaySuccess))
                showCancleDialog();
        finish();
        int i3 = paramView.getId();
        int i4 = getid("chujian_sdk_pay_cancledialog_exitbtn");
        int i2 = getid("chujian_sdk_pay_cancledialog_continuebtn");
        int i1 = getid("chujian_sdk_pay_cancledialog2_confirm_btn");
        int i5 = getid("chujian_sdk_pay_left_layout_ali");
        int j = getid("chujian_sdk_pay_left_layout_mo9");
        int k = getid("chujian_sdk_pay_left_layout_yibao");
        int i = getid("chujian_sdk_pay_left_layout_card");
        int l = getid("chujian_sdk_pay_detail_mo9_nextbtn");
        if (i3 == i4) {
            dismissCancleDialog();
            payComplete(0);
            finish();
        }
        if (i3 == i2)
            dismissCancleDialog();
        if (i3 == i1) {
            dismissCancleDialog();
            payComplete(0);
            this.beforeDialog.dismiss();
            finish();
        }
        if (i3 == i5)
            setLayoutBkgAndTcolor(1);
        if (i3 == k)
            setLayoutBkgAndTcolor(2);
        if (i3 == i)
            setLayoutBkgAndTcolor(3);
        if (i3 == j)
            setLayoutBkgAndTcolor(4);
        if (i3 != l)
            isClickNext = true;
        if (this.pdDialog == null)
            this.pdDialog = new ProgressDialog(this, "正在请求初见安全支付...", true);
        this.pdDialog.show();
        if (this.chooseMethod == 1)
            toAli();
        if (this.chooseMethod == 2)
            toYibaoBank();
        if (this.chooseMethod != 3)
            toMO9();

    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        getExtras(getIntent());
        setContentView(MyResource.getIdByName(this, "layout", "chujian_sdk_payact"));
        init();
    }

    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        boolean bool = false;
        if (paramInt == 4)
            if ((CardPayView.popwpwindow != null) && (CardPayView.popwpwindow.isShowing()))
                CardPayView.popwpwindow.dismiss();
        if (!(isPaySuccess))
            showCancleDialog();
        finish();
        bool = super.onKeyDown(paramInt, paramKeyEvent);
        return bool;

    }

    protected void onNewIntent(Intent paramIntent) {
        super.onNewIntent(paramIntent);
    }

    protected void onPause() {
        if (this.pdDialog != null)
            this.pdDialog.dismiss();
        super.onPause();
    }

    protected void onRestoreInstanceState(Bundle paramBundle) {
        super.onRestoreInstanceState(paramBundle);
        this.chooseMethod = paramBundle.getInt("method");
        setLayoutBkgAndTcolor(this.chooseMethod);
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onSaveInstanceState(Bundle paramBundle) {
        super.onSaveInstanceState(paramBundle);
        paramBundle.putInt("method", this.chooseMethod);
    }

    public void setRequestedOrientation(int paramInt) {
        if (isLandScreen)
            super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return;
    }

    public void showCardPaySuccess(String paramString) {
        this.cardPaySuccessPop = new CardPaySuccessPop(this, paramString);
        this.cardPaySuccessPop.showAtLocation(findViewById(getid("chujian_sdk_payact_view")), 17, 0, 0);
    }
}