package org.darcy.sanguo.sdk.view;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.darcy.sanguo.utils.MyResource;

public class MyToast extends Toast {
    private int contentHeight;
    private View contentView;
    private int contentWidth;

    public MyToast(Context paramContext, String paramString, int paramInt) {
        super(paramContext);
        this.contentView = ((LayoutInflater) paramContext.getSystemService("layout_inflater")).inflate(MyResource.getIdByName(paramContext, "layout", "chujian_sdk_toast"), null);
        ((TextView) this.contentView.findViewById(MyResource.getIdByName(paramContext, "id", "chujian_toast_messageTextView"))).setText(paramString);
        setView(this.contentView);
        getWindowSize(paramContext);
        setGravity(16, 0, (int) (this.contentHeight * 0.25D));
        this.contentView.setPadding((int) (this.contentHeight * 0.06D), (int) (this.contentHeight * 0.04D), (int) (this.contentHeight * 0.04D), (int) (this.contentHeight * 0.06D));
        setDuration(paramInt);
    }

    private void getWindowSize(Context paramContext) {
        Display display = ((Activity) paramContext).getWindowManager().getDefaultDisplay();
        this.contentWidth = display.getWidth();
        this.contentHeight = display.getHeight();
        if (this.contentWidth < this.contentHeight)
            this.contentWidth = (int) (this.contentWidth * 0.8D);
        for (this.contentHeight = (int) (this.contentWidth * 0.9D); ; this.contentHeight = (int) (this.contentWidth * 0.9D)) {
            this.contentWidth = (int) (this.contentWidth * 0.47D);
        }
    }

    public static Toast makeToast(Context paramContext, String paramString, int paramInt) {
        return new MyToast(paramContext, paramString, paramInt);
    }
}