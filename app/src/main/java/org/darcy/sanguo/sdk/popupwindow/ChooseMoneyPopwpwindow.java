package org.darcy.sanguo.sdk.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import org.darcy.sanguo.utils.MyResource;

//充值页面
public class ChooseMoneyPopwpwindow extends PopupWindow
        implements View.OnClickListener {
    private CheckBox checkBox10;
    private CheckBox checkBox100;
    private CheckBox checkBox20;
    private CheckBox checkBox30;
    private CheckBox checkBox300;
    private CheckBox checkBox50;
    private CheckBox checkBox500;
    private String chooseText;
    private View contentView;
    private FrameLayout frameLayout10;
    private FrameLayout frameLayout100;
    private FrameLayout frameLayout20;
    private FrameLayout frameLayout30;
    private FrameLayout frameLayout300;
    private FrameLayout frameLayout50;
    private FrameLayout frameLayout500;
    int framelayout10ID;

    public ChooseMoneyPopwpwindow(Context paramContext, String paramString) {
        super(paramContext);
        this.contentView = LayoutInflater.from(paramContext).inflate(MyResource.getIdByName(paramContext, "layout", "chujian_sdk_choose_moneu_pop"), null);
        findId(paramContext);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setWidth(-1);
        setHeight(-1);
        setContentView(this.contentView);
        setCheckOne(paramString);
    }

    private void findId(Context paramContext) {
        this.frameLayout10 = ((FrameLayout) this.contentView.findViewById(getid(paramContext, "chujian_sdk_pay_card_choose_money_10")));
        this.frameLayout20 = ((FrameLayout) this.contentView.findViewById(getid(paramContext, "chujian_sdk_pay_card_choose_money_20")));
        this.frameLayout30 = ((FrameLayout) this.contentView.findViewById(getid(paramContext, "chujian_sdk_pay_card_choose_money_30")));
        this.frameLayout50 = ((FrameLayout) this.contentView.findViewById(getid(paramContext, "chujian_sdk_pay_card_choose_money_50")));
        this.frameLayout100 = ((FrameLayout) this.contentView.findViewById(getid(paramContext, "chujian_sdk_pay_card_choose_money_100")));
        this.frameLayout300 = ((FrameLayout) this.contentView.findViewById(getid(paramContext, "chujian_sdk_pay_card_choose_money_300")));
        this.frameLayout500 = ((FrameLayout) this.contentView.findViewById(getid(paramContext, "chujian_sdk_pay_card_choose_money_500")));
        setListener();
        this.checkBox10 = ((CheckBox) this.contentView.findViewById(getid(paramContext, "chujian_sdk_pay_card_choose_money_10_check")));
        this.checkBox20 = ((CheckBox) this.contentView.findViewById(getid(paramContext, "chujian_sdk_pay_card_choose_money_20_check")));
        this.checkBox30 = ((CheckBox) this.contentView.findViewById(getid(paramContext, "chujian_sdk_pay_card_choose_money_30_check")));
        this.checkBox50 = ((CheckBox) this.contentView.findViewById(getid(paramContext, "chujian_sdk_pay_card_choose_money_50_check")));
        this.checkBox100 = ((CheckBox) this.contentView.findViewById(getid(paramContext, "chujian_sdk_pay_card_choose_money_100_check")));
        this.checkBox300 = ((CheckBox) this.contentView.findViewById(getid(paramContext, "chujian_sdk_pay_card_choose_money_300_check")));
        this.checkBox500 = ((CheckBox) this.contentView.findViewById(getid(paramContext, "chujian_sdk_pay_card_choose_money_500_check")));
        setCheckEnabe();
    }

    private int getid(Context paramContext, String paramString) {
        return MyResource.getIdByName(paramContext, "id", paramString);
    }

    private void setCheckEnabe() {
        this.checkBox10.setOnClickListener(this);
        this.checkBox20.setOnClickListener(this);
        this.checkBox30.setOnClickListener(this);
        this.checkBox50.setOnClickListener(this);
        this.checkBox100.setOnClickListener(this);
        this.checkBox300.setOnClickListener(this);
        this.checkBox500.setOnClickListener(this);
    }

    private void setCheckOne(String paramString) {
        this.checkBox10.setChecked(false);
        this.checkBox20.setChecked(false);
        this.checkBox30.setChecked(false);
        this.checkBox50.setChecked(false);
        this.checkBox100.setChecked(false);
        this.checkBox300.setChecked(false);
        this.checkBox500.setChecked(false);
        if (paramString.equals("10元"))
            this.checkBox10.setChecked(true);

        setChooseText(paramString);
        dismiss();
        if (paramString.equals("20元"))
            this.checkBox20.setChecked(true);
        if (paramString.equals("30元"))
            this.checkBox30.setChecked(true);
        if (paramString.equals("50元"))
            this.checkBox50.setChecked(true);
        if (paramString.equals("100元"))
            this.checkBox100.setChecked(true);
        if (paramString.equals("300元"))
            this.checkBox300.setChecked(true);
        if (paramString.equals("500元"))
            this.checkBox500.setChecked(true);
        this.checkBox10.setChecked(true);

    }

    private void setListener() {
        this.frameLayout10.setOnClickListener(this);
        this.frameLayout20.setOnClickListener(this);
        this.frameLayout30.setOnClickListener(this);
        this.frameLayout50.setOnClickListener(this);
        this.frameLayout100.setOnClickListener(this);
        this.frameLayout300.setOnClickListener(this);
        this.frameLayout500.setOnClickListener(this);
    }

    public void dismiss() {
        super.dismiss();
    }

    public String getChooseText() {
        return this.chooseText;
    }

    public void onClick(View paramView) {
        if (paramView.equals(this.checkBox10))
            setCheckOne("10元");
        if (paramView.equals(this.checkBox20))
            setCheckOne("20元");
        if (paramView.equals(this.checkBox30))
            setCheckOne("30元");
        if (paramView.equals(this.checkBox50))
            setCheckOne("50元");
        if (paramView.equals(this.checkBox100))
            setCheckOne("100元");
        if (paramView.equals(this.checkBox300))
            setCheckOne("300元");
        if (paramView.equals(this.checkBox500))
            setCheckOne("500元");
        if (paramView.equals(this.frameLayout10))
            setCheckOne("10元");
        if (paramView.equals(this.frameLayout20))
            setCheckOne("20元");
        if (paramView.equals(this.frameLayout30))
            setCheckOne("30元");
        if (paramView.equals(this.frameLayout50))
            setCheckOne("50元");
        if (paramView.equals(this.frameLayout100))
            setCheckOne("100元");
        if (paramView.equals(this.frameLayout300))
            setCheckOne("300元");
        if (paramView.equals(this.frameLayout500))
            setCheckOne("500元");
        return;

    }

    public void setChooseText(String paramString) {
        this.chooseText = paramString;
    }
}