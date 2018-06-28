package org.darcy.sanguo.sdk.popupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.darcy.sanguo.activity.PayAct;
import org.darcy.sanguo.utils.MyResource;

public class CardPaySuccessPop extends PopupWindow {
    private Button backButton;
    private String orderno;
    private TextView ordernoTextView;
    private View view;

    public CardPaySuccessPop(Context paramContext, String paramString) {
        super(paramContext);
        this.orderno = paramString;
        setWidth(-1);
        setHeight(-1);
        setOutsideTouchable(true);
        setFocusable(true);
        this.view = LayoutInflater.from(paramContext).inflate(MyResource.getIdByName(paramContext, "layout", "chujian_sdk_cardpaysuccesspop"), null);
        setContentView(this.view);
        setView(paramContext);
    }


    private void setView(Context paramContext) {
        this.backButton = ((Button) this.view.findViewById(MyResource.getIdByName(paramContext, "id", "chujian_sdk_pay_card_back_btn")));
        this.backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                CardPaySuccessPop.this.dismiss();
                PayAct.payComplete(1);
            }
        });
        this.ordernoTextView = ((TextView) this.view.findViewById(MyResource.getIdByName(paramContext, "id", "chujian_sdk_pay_card_order_tv")));
        this.ordernoTextView.setText(this.orderno);
    }

}