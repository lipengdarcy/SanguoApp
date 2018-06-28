package org.darcy.sanguo.sdk.view;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.darcy.sanguo.activity.PayAct;

import org.darcy.sanguo.utils.MyResource;

public class PayView extends LinearLayout
        implements View.OnClickListener {
    private double amount;
    private TextView amountTextView;
    private TextView explainTextView;
    private Button nextButton;
    private View.OnClickListener onClickListener;
    private String pName;
    private TextView pnameTextView;
    private LinearLayout productinfoLayout;
    private ImageView titleImg;
    private String userName;
    private TextView usernameTextView;

    public PayView(Context paramContext, View.OnClickListener paramOnClickListener, String paramString1, String paramString2, double paramDouble) {
        super(paramContext);
        this.onClickListener = paramOnClickListener;
        this.amount = paramDouble;
        this.userName = paramString1;
        this.pName = paramString2;
        inflate(paramContext, MyResource.getIdByName(paramContext, "layout", "chujian_sdk_paydetail_mo9"), this);
        init(paramContext);
    }

    private int getId(String paramString) {
        return MyResource.getIdByName(getContext(), "id", paramString);
    }

    private void init(Context paramContext) {
        int i;
        this.amountTextView = ((TextView) findViewById(getId("chujian_sdk_pay_detail_mo9_amount_tv")));
        this.amountTextView.setText(this.amount + "元");
        this.usernameTextView = ((TextView) findViewById(getId("chujian_sdk_pay_detail_mo9_username_tv")));
        this.usernameTextView.setText("用户名：" + this.userName);
        this.pnameTextView = ((TextView) findViewById(getId("chujian_sdk_pay_detail_mo9_pname_tv")));
        this.pnameTextView.setText("商    品：" + this.pName);
        this.nextButton = ((Button) findViewById(getId("chujian_sdk_pay_detail_mo9_nextbtn")));
        this.nextButton.setText("去支付宝付款");
        this.explainTextView = ((TextView) findViewById(getId("chujian_sdk_pay_detail_mo9_explain")));
        this.explainTextView.setText("确认无误后去支付宝付款");
        this.nextButton.setOnClickListener(this.onClickListener);
        this.titleImg = ((ImageView) findViewById(getId("chujian_sdk_pay_detail_mo9_titleimg")));
        this.titleImg.setOnClickListener(this);
        this.productinfoLayout = ((LinearLayout) findViewById(getId("chujian_sdk_pay_detail_mo9_title_linear")));
        if (PayAct.showDetail) {
            this.productinfoLayout.setVisibility(VISIBLE);
            i = MyResource.getIdByName(paramContext, "drawable", "chujian_sdk_pay_detail_visible");
            this.titleImg.setImageResource(i);
            if (!(PayAct.isLandScreen)) {
                this.productinfoLayout.setOrientation(VERTICAL);
            } else {
                this.productinfoLayout.setOrientation(HORIZONTAL);
            }

        }
        this.productinfoLayout.setVisibility(GONE);
        i = MyResource.getIdByName(paramContext, "drawable", "chujian_sdk_pay_detail_gone");
        this.titleImg.setImageResource(i);
    }

    private void setTextColor(TextView paramTextView) {
        SpannableString localSpannableString = new SpannableString(paramTextView.getText());
        localSpannableString.setSpan(new ForegroundColorSpan(-65536), 15, 22, 33);
        paramTextView.setText(localSpannableString);
    }

    private void setTextColorMo9(TextView paramTextView) {
        Object localObject = paramTextView.getText().toString();
        int i = ((String) localObject).indexOf("支付宝");
        localObject = new SpannableString((CharSequence) localObject);
        ((SpannableString) localObject).setSpan(new ForegroundColorSpan(-65536), i, i + 15, 33);
        paramTextView.setText((CharSequence) localObject);
    }

    public void onClick(View paramView) {
        int i;
        if (this.productinfoLayout.getVisibility() == VISIBLE) {
            PayAct.showDetail = false;
            i = MyResource.getIdByName(getContext(), "drawable", "chujian_sdk_pay_detail_gone");
            this.titleImg.setImageResource(i);
            this.productinfoLayout.setVisibility(GONE);
            return;
        }
        PayAct.showDetail = true;
        i = MyResource.getIdByName(getContext(), "drawable", "chujian_sdk_pay_detail_visible");
        this.titleImg.setImageResource(i);
        this.productinfoLayout.setVisibility(VISIBLE);
    }

    public void setInfoLayoutVisible(int paramInt) {
        if (paramInt == 0) {
            paramInt = MyResource.getIdByName(getContext(), "drawable", "chujian_sdk_pay_detail_visible");
            this.titleImg.setImageResource(paramInt);
            this.productinfoLayout.setVisibility(VISIBLE);
            return;
        }
        paramInt = MyResource.getIdByName(getContext(), "drawable", "chujian_sdk_pay_detail_gone");
        this.titleImg.setImageResource(paramInt);
        this.productinfoLayout.setVisibility(GONE);
    }

    public void setMethodText(String paramString) {
        if (paramString.equals("ali")) {
            this.nextButton.setText("去支付宝付款");
            this.explainTextView.setText("确认无误后去支付宝付款");
            return;
        }
        if (paramString.equals("mo9")) {
            this.nextButton.setText("去先玩后付");
            this.explainTextView.setText("1、先消费后还款，不扣话费，七日内到www.mo9.com.cn还款即可。\n2、同时也支持支付宝、财付通、储蓄卡和信用卡直接购买哦。");
            setTextColorMo9(this.explainTextView);
        }
        this.nextButton.setText("去银行付款");
        this.explainTextView.setText("确认无误后去银行进行付款，支持储蓄卡和信用卡付款。");
        setTextColor(this.explainTextView);

    }
}