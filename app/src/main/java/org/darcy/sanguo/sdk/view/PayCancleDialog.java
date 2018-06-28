package org.darcy.sanguo.sdk.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import org.darcy.sanguo.utils.MyResource;

public class PayCancleDialog extends Dialog {
    private int Hight;
    private int Width;
    private View contentView;
    private Button continueButton;
    private Button exitButton;
    private int largeSize;
    private View.OnClickListener onClickListener;
    private int smallSize;

    private PayCancleDialog(Context paramContext) {
        super(paramContext, MyResource.getIdByName(paramContext, "style", "chujian_ProgressDialog"));
    }

    public PayCancleDialog(Context paramContext, View.OnClickListener paramOnClickListener) {
        super(paramContext, MyResource.getIdByName(paramContext, "style", "chujian_ProgressDialog"));
        this.onClickListener = paramOnClickListener;
        setTitle(null);
        setCancelable(true);
        setOnCancelListener(null);
        this.contentView = getLayoutInflater().inflate(MyResource.getIdByName(paramContext, "layout", "chujian_sdk_paycancledialog"), null, false);
        setContentView(this.contentView);
        init(paramContext);
    }

    private void findID() {
        this.exitButton = ((Button) this.contentView.findViewById(MyResource.getIdByName(getContext(), "id", "chujian_sdk_pay_cancledialog_exitbtn")));
        this.continueButton = ((Button) this.contentView.findViewById(MyResource.getIdByName(getContext(), "id", "chujian_sdk_pay_cancledialog_continuebtn")));
        this.exitButton.setOnClickListener(this.onClickListener);
        this.continueButton.setOnClickListener(this.onClickListener);
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