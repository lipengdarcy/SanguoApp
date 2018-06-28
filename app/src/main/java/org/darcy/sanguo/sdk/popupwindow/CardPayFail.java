package org.darcy.sanguo.sdk.popupwindow;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.darcy.sanguo.utils.MyResource;

public class CardPayFail extends PopupWindow {
    private int cardLast;
    private Button confirmButton;
    private RelativeLayout contentLayout;
    private TextView expainTextView;
    private View view;

    public CardPayFail(Context paramContext, int paramInt) {
        super(paramContext);
        this.cardLast = paramInt;
        this.view = LayoutInflater.from(paramContext).inflate(MyResource.getIdByName(paramContext, "layout", "chujian_sdk_cardpay_fail_pop"), null);
        setContentView(this.view);
        setWidth(-1);
        setHeight(-1);
        setOutsideTouchable(true);
        setFocusable(true);
        init(paramContext);
    }

    private int getid(Context paramContext, String paramString) {
        return MyResource.getIdByName(paramContext, "id", paramString);
    }

    private void init(Context paramContext) {
        this.contentLayout = ((RelativeLayout) this.view.findViewById(getid(paramContext, "chujian_sdk_card_pay_fail_content_layout")));
        setcontentLaytouSzie(paramContext);
        this.expainTextView = ((TextView) this.view.findViewById(getid(paramContext, "chujian_sdk_card_pay_fail_expain")));
        this.expainTextView.setText("充值卡（尾号：" + this.cardLast + "）卡号密码验证失败，请稍候再试");
        this.confirmButton = ((Button) this.view.findViewById(getid(paramContext, "chujian_sdk_card_pay_fail_confirm_btn")));
        this.confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                CardPayFail.this.dismiss();
            }
        });
    }

    private void setcontentLaytouSzie(Context paramContext) {
        Display display = ((WindowManager) paramContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int j = display.getWidth();
        int i = display.getHeight();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(i * 4 / 5, -2, 17);
        this.contentLayout.setLayoutParams(params);
        return;
    }
}