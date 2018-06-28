package org.darcy.sanguo.sdk.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import org.darcy.sanguo.sdk.manager.CjWindowManager;
import org.darcy.sanguo.utils.MyResource;

@Deprecated
public class FloatView extends LinearLayout {
    private static int statusBarHeight = 0;
    private boolean childIsShowing = false;
    private View childView;
    private float downX = 5.0F;
    private float downXinScren = 11.0F;
    private float downY = 6.0F;
    private float downYinScren = 12.0F;
    private ImageView iconImageView;
    private WindowManager mWindowManager;
    private float moveX = 7.0F;
    private float moveXInScreen = 17.0F;
    private float moveY = 8.0F;
    private float moveYInScreen = 18.0F;
    private int screenHeight = 0;
    private int screenWidth = 1;
    private int smallSize;
    private float upX = 9.0F;
    private float upXinScren = 13.0F;
    private float upY = 10.0F;
    private float upYinScren = 14.0F;
    private int viewHeight = 16;
    private int viewWidth = 15;

    public FloatView(Context paramContext) {
        super(paramContext);
        this.mWindowManager = CjWindowManager.getWindowmanager(paramContext);
        this.screenWidth = this.mWindowManager.getDefaultDisplay().getWidth();
        this.screenHeight = this.mWindowManager.getDefaultDisplay().getHeight();
        if (this.screenWidth > this.screenHeight) ;
        for (int i = this.screenHeight; ; i = this.screenWidth) {
            this.smallSize = i;
            i = MyResource.getIdByName(paramContext, "layout", "chujian_sdk_floatview");
            LayoutInflater.from(paramContext).inflate(i, this);
            init(paramContext);
            return;
        }
    }

    private int getStatusBarHeight() {
        if (statusBarHeight == 0) ;
        try {
            Class localClass = Class.forName("com.android.internal.R$dimen");
            Object localObject = localClass.newInstance();
            int i = ((Integer) localClass.getField("status_bar_height").get(localObject)).intValue();
            statusBarHeight = getResources().getDimensionPixelSize(i);
            return statusBarHeight;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return 0;
    }

    private void init(Context paramContext) {
        this.iconImageView = ((ImageView) findViewById(MyResource.getIdByName(paramContext, "id", "chujian_sdk_floatview_iconimg")));
        int i = this.smallSize / 8;
        LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(i, i);
        this.iconImageView.setLayoutParams(localLayoutParams);
       // this.iconImageView.setOnTouchListener(new MyTouchListener(this));
        initChildView(paramContext);
    }

    private void initChildView(Context paramContext) {
        int i = MyResource.getIdByName(paramContext, "layout", "chujian_sdk_floatviewchildview");
        this.childView = LayoutInflater.from(paramContext).inflate(i, null);
        this.childView.setVisibility(8);
    }

    private void showChildView() {
        if (CjWindowManager.getLayoutParams().x > this.screenWidth / 2)
            if (this.childView.getVisibility() == 8) {
                this.childView.setVisibility(0);
                addView(this.childView, 0);
                return;
            }
        this.childView.setVisibility(8);
        removeView(this.childView);
    }
}