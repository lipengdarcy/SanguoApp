package org.darcy.sanguo.sdk.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.darcy.sanguo.utils.MyResource;

public class ProgressDialog extends Dialog {
    private int contentHeight;
    private View contentView;
    private int contentWidth;
    private TextView messageTextView;
    private ProgressBar progressBar;

    private ProgressDialog(Context paramContext) {
        super(paramContext, MyResource.getIdByName(paramContext, "style", "chujian_ProgressDialog"));
    }

    public ProgressDialog(Context paramContext, String paramString, boolean paramBoolean) {
        super(paramContext, MyResource.getIdByName(paramContext, "style", "chujian_ProgressDialog"));
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        this.contentView = getLayoutInflater().inflate(MyResource.getIdByName(getContext(), "layout", "chujian_sdk_dialog_progress"), null, false);
        this.messageTextView = ((TextView) this.contentView.findViewById(getids("chujian_messageTextView")));
        this.messageTextView.setText(paramString);
        this.progressBar = ((ProgressBar) this.contentView.findViewById(getids("chujian_dialog_progress_progressbar")));
        setContentView(this.contentView);
        init(paramContext, paramBoolean);
    }

    private void getWindowSize(Context paramContext) {
        Display display = ((WindowManager) paramContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        this.contentWidth = display.getWidth();
        this.contentHeight = display.getHeight();
        if (this.contentWidth < this.contentHeight)
            this.contentWidth = (int) (this.contentWidth * 0.8D);
        else
            this.contentWidth = (int) (this.contentWidth * 0.47D);
    }


    private int getids(String paramString) {
        return MyResource.getIdByName(getContext(), "id", paramString);
    }

    private void init(Context paramContext, boolean paramBoolean) {
        getWindowSize(paramContext);
        setContentSize(paramBoolean);
        setDimStyle(paramBoolean);
    }

    private void setContentSize(boolean paramBoolean) {
        this.contentView.setPadding((int) (this.contentHeight * 0.2D), (int) (this.contentHeight * 0.08D), (int) (this.contentHeight * 0.2D), (int) (this.contentHeight * 0.08D));
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.contentView.getLayoutParams();
        if (!(paramBoolean))
            layoutParams.setMargins(0, 0, 0, (int) (this.contentHeight * 0.6D));
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) ((ProgressBar) this.contentView.findViewById(getids("chujian_dialog_progress_progressbar"))).getLayoutParams();
        marginLayoutParams.width = (int) (this.contentHeight * 0.1D);
        marginLayoutParams.height = marginLayoutParams.width;
        marginLayoutParams = (ViewGroup.MarginLayoutParams) this.messageTextView.getLayoutParams();
    }

    private void setDimStyle(boolean paramBoolean) {
        if (paramBoolean)
            return;
        getWindow().getAttributes().dimAmount = 0.0F;
    }
}