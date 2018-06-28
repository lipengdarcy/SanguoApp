package org.darcy.sanguo.sdk.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.darcy.sanguo.activity.PayAct;
import org.darcy.sanguo.sdk.popupwindow.ChooseMoneyPopwpwindow;

import org.darcy.sanguo.client.Payment;
import org.darcy.sanguo.utils.DialogUtil;
import org.darcy.sanguo.utils.MobileCardUtil;
import org.darcy.sanguo.utils.MyResource;

public class CardPayView extends LinearLayout implements View.OnClickListener {
    public static int cardLastInt;
    public static ChooseMoneyPopwpwindow popwpwindow;
    private double amount;
    private TextView amountTextView;
    private LinearLayout cardNumLayout;
    private LinearLayout cardPwdLayout;
    private EditText cardnumEditText;
    private EditText cardpassEditText;
    private Button chooseMoneyButton;
    private TextView explainTextView;
    private String gameServer;
    private Handler handler;
    private boolean isLandScreen = true;
    private TextView konwMoreTv;
    private View.OnClickListener mOnClickListener;
    private Button nextButton;
    private String notifyUrl;
    private String orderId;
    private TextView pnameTextView;
    private int productCount;
    private String productId;
    private String productName;
    private LinearLayout productinfoLayout;
    private int screenWidth = 0;
    private ScrollView scrollView;
    private ImageView titleImg;
    private String uId;
    private String userName;
    private TextView usernameTextView;

    public CardPayView(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt, String paramString6, double paramDouble, String paramString7, boolean paramBoolean, Handler paramHandler) {
        super(paramContext);
        this.uId = paramString1;
        this.userName = paramString2;
        this.productName = paramString3;
        this.productId = paramString4;
        this.orderId = paramString5;
        this.productCount = paramInt;
        this.notifyUrl = paramString6;
        this.amount = paramDouble;
        this.gameServer = paramString7;
        this.isLandScreen = paramBoolean;
        this.handler = paramHandler;
        init(paramContext);
    }

    private void findID() {
        int i;
        this.titleImg = ((ImageView) findViewById(getid("chujian_sdk_pay_detail_card_titleimg")));
        this.titleImg.setOnClickListener(this);
        this.amountTextView = ((TextView) findViewById(getid("chujian_sdk_pay_detail_card_amount_tv")));
        this.amountTextView.setText(this.amount + "元");
        this.productinfoLayout = ((LinearLayout) findViewById(getid("chujian_sdk_pay_detail_card_title_linear")));
        if (PayAct.showDetail) {
            this.productinfoLayout.setVisibility(View.VISIBLE);
            i = MyResource.getIdByName(getContext(), "drawable", "chujian_sdk_pay_detail_visible");
            this.titleImg.setImageResource(i);
            if (!(this.isLandScreen)) {
                this.productinfoLayout.setOrientation(LinearLayout.VERTICAL);
                return;
            }
            this.productinfoLayout.setOrientation(LinearLayout.HORIZONTAL);
        }
        this.usernameTextView = ((TextView) findViewById(getid("chujian_sdk_pay_detail_card_username_tv")));
        this.usernameTextView.setText("用户名：" + this.userName);
        this.pnameTextView = ((TextView) findViewById(getid("chujian_sdk_pay_detail_card_pname_tv")));
        this.pnameTextView.setText("商    品：" + this.productName);
        this.explainTextView = ((TextView) findViewById(getid("chujian_sdk_pay_detail_card_expain_tv")));
        this.nextButton = ((Button) findViewById(getid("chujian_sdk_pay_detail_card_next_btn")));
        this.nextButton.setText("立即付款");
        this.nextButton.setOnClickListener(this);
        measureView(this.nextButton);
        i = this.nextButton.getMeasuredHeight();
        this.scrollView = ((ScrollView) findViewById(getid("chujian_sdk_pay_detail_card_scrollview")));
        ((ViewGroup.MarginLayoutParams) this.scrollView.getLayoutParams()).setMargins(0, 0, 0, i);
        this.cardnumEditText = ((EditText) findViewById(getid("chujian_sdk_pay_detail_card_num_edit")));
        this.cardpassEditText = ((EditText) findViewById(getid("chujian_sdk_pay_detail_card_pass_edit")));
        this.cardNumLayout = ((LinearLayout) findViewById(getid("chujian_sdk_pay_detail_card_name_layout")));
        this.cardPwdLayout = ((LinearLayout) findViewById(getid("chujian_sdk_pay_detail_card_pass_layout")));
        this.chooseMoneyButton = ((Button) findViewById(getid("chujian_sdk_pay_detail_card_chooseamount_btn")));
        this.chooseMoneyButton.setOnClickListener(this);
        this.cardnumEditText.clearFocus();
        this.cardpassEditText.clearFocus();
        i = MyResource.getIdByName(getContext(), "drawable", "chujian_sdk_pay_detail_gone");
        this.productinfoLayout.setVisibility(GONE);
        this.titleImg.setImageResource(i);

        this.productinfoLayout.setOrientation(VERTICAL);

    }

    private void getScreenSize(Context paramContext) {
        this.screenWidth = ((WindowManager) paramContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }

    private int getid(String paramString) {
        return MyResource.getIdByName(getContext(), "id", paramString);
    }

    private void init(Context paramContext) {
        inflate(paramContext, MyResource.getIdByName(paramContext, "layout", "chujian_sdk_pay_cardview"), this);
        getScreenSize(paramContext);
        findID();
        setEditlayoutSize();
    }

    private void measureView(View paramView) {
        ViewGroup.LayoutParams localLayoutParams2 = paramView.getLayoutParams();
        ViewGroup.LayoutParams localLayoutParams1 = localLayoutParams2;
        if (localLayoutParams2 == null)
            localLayoutParams1 = new ViewGroup.LayoutParams(-1, -2);
        int j = ViewGroup.getChildMeasureSpec(0, 0, localLayoutParams1.width);
        int i = localLayoutParams1.height;
        if (i > 0) ;
        for (i = View.MeasureSpec.makeMeasureSpec(i, MeasureSpec.UNSPECIFIED); ; i = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)) {
            paramView.measure(j, i);
            return;
        }
    }

    private void setEditlayoutSize() {
        if (!(this.isLandScreen))
            return;
        ((ViewGroup.MarginLayoutParams) this.cardNumLayout.getLayoutParams()).width = (this.screenWidth / 2);
        ((ViewGroup.MarginLayoutParams) this.cardPwdLayout.getLayoutParams()).width = (this.screenWidth / 2);
        ((ViewGroup.MarginLayoutParams) this.chooseMoneyButton.getLayoutParams()).width = (this.screenWidth / 2);
    }

    public void onClick(View paramView) {
        int i;
        if (paramView.equals(this.titleImg))
            if (this.productinfoLayout.getVisibility() == View.VISIBLE) {
                i = MyResource.getIdByName(getContext(), "drawable", "chujian_sdk_pay_detail_gone");
                this.titleImg.setImageResource(i);
                this.productinfoLayout.setVisibility(GONE);
            }

        String str1;
        i = MyResource.getIdByName(getContext(), "drawable", "chujian_sdk_pay_detail_visible");
        this.titleImg.setImageResource(i);
        this.productinfoLayout.setVisibility(GONE);
        if (paramView.equals(this.nextButton)) {
            PayAct.isClickNext = true;
            String cardNo = this.cardnumEditText.getEditableText().toString();
            String cardPass = this.cardpassEditText.getEditableText().toString();
            if (cardNo.length() == 0)
                MyToast.makeToast(getContext(), "充值卡卡号不能为空~", 0).show();
            if (cardPass.length() == 0)
                MyToast.makeToast(getContext(), "充值卡密码不能为空~", 0).show();
            if (((cardNo.length() < 15) && (cardNo.length() > 20)) || ((cardPass.length() < 18) && (cardPass.length() > 19)))
                MyToast.makeToast(getContext(), "暂不支持此类型卡~", 0).show();
            String str2 = MobileCardUtil.getMobileCardType(cardNo, cardPass);
            if (str2 == null)
                MyToast.makeToast(getContext(), "暂不支持此类型卡~", 0).show();
            cardLastInt = Integer.valueOf(cardNo.substring(cardNo.length() - 4, cardNo.length())).intValue();
            String s = this.chooseMoneyButton.getText().toString();
            double d = 0d;
            String str5 = s.substring(s.lastIndexOf("：") + 1, s.indexOf("元"));
            if (str5 != null && str5.length() > 0) {
                d = Double.parseDouble(str5);
            }
            DialogUtil.showDialog((Activity) getContext(), "正在打开安全支付", true);
            try {
                Payment.onPaymentYibaoNoBack(this.handler, this.uId, Double.valueOf(this.amount), this.productName, this.productId, Integer.valueOf(this.productCount), this.orderId, cardNo, cardPass, d, str2, this.notifyUrl);
            } catch (Exception e) {
                DialogUtil.cancleDialog();
                MyToast.makeToast(getContext(), "获取订单信息失败~", 1).show();
                e.printStackTrace();
            }
        }
        if (paramView.equals(this.chooseMoneyButton)) {
            str1 = this.chooseMoneyButton.getText().toString();
            str1 = str1.substring(str1.indexOf("："));
            if (popwpwindow == null)
                popwpwindow = new ChooseMoneyPopwpwindow(getContext(), str1);
            popwpwindow.showAtLocation(paramView, 17, 0, 0);
            // popwpwindow.setOnDismissListener();
        }
        paramView.equals(this.konwMoreTv);

    }

    public void setInfoLayoutVisible(int paramInt) {
        if (paramInt == 8) {
            paramInt = MyResource.getIdByName(getContext(), "drawable", "chujian_sdk_pay_detail_gone");
            this.titleImg.setImageResource(paramInt);
            this.productinfoLayout.setVisibility(GONE);
        }
        paramInt = MyResource.getIdByName(getContext(), "drawable", "chujian_sdk_pay_detail_visible");
        this.titleImg.setImageResource(paramInt);
        this.productinfoLayout.setVisibility(VISIBLE);
        return;
    }
}