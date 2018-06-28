package org.darcy.sanguo.sdk.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import org.darcy.sanguo.utils.MyResource;

public class PayCanclebefDialog extends Dialog {
    private int Hight;
    private int Width;
    private Button confirmButton;
    private View contentView;
    private int largeSize;
    private View.OnClickListener onClickListener;
    private int smallSize;

    private PayCanclebefDialog(Context paramContext) {
        super(paramContext, MyResource.getIdByName(paramContext, "style", "chujian_ProgressDialog"));
    }

    public PayCanclebefDialog(Context paramContext, View.OnClickListener paramOnClickListener) {
        super(paramContext, MyResource.getIdByName(paramContext, "style", "chujian_ProgressDialog"));
        this.onClickListener = paramOnClickListener;
        setTitle(null);
        setCancelable(true);
        setOnCancelListener(null);
        this.contentView = getLayoutInflater().inflate(MyResource.getIdByName(paramContext, "layout", "chujian_sdk_pay_cancle_dialog2"), null, false);
        setContentView(this.contentView);
        init(paramContext);
    }

    private void findID() {
        this.confirmButton = ((Button) this.contentView.findViewById(MyResource.getIdByName(getContext(), "id", "chujian_sdk_pay_cancledialog2_confirm_btn")));
        this.confirmButton.setOnClickListener(this.onClickListener);
    }

    private void getWidowSize(Context paramContext) {
        Display display = ((WindowManager) paramContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        this.Width = display.getWidth();
        this.Hight = display.getHeight();
        if (this.Width > this.Hight) {
            this.smallSize = this.Hight;
        }
        if (this.Width <= this.Hight) {
            this.largeSize = this.Width;
        }
    }

    private void init(Context paramContext) {
        findID();
        getWidowSize(paramContext);
        setSize(paramContext);
    }

    private void setSize(Context paramContext) {
        WindowManager manager = (WindowManager) paramContext.getSystemService(Context.WINDOW_SERVICE);
        Window localWindow = getWindow();
        localWindow.setGravity(17);
        WindowManager.LayoutParams layoutParams = localWindow.getAttributes();
        layoutParams.width = (int) (this.smallSize * 0.8D);
        layoutParams.height = (int) (this.smallSize * 0.65D);
        localWindow.setAttributes(layoutParams);
    }
}