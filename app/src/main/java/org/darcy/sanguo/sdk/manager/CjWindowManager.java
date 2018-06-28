package org.darcy.sanguo.sdk.manager;

import android.content.Context;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import org.darcy.sanguo.sdk.view.FloatView;

public class CjWindowManager {
    private static WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
    private static FloatView view;

    static {
        layoutParams.type = 2003;
        layoutParams.flags = 40;
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.format = -2;
        layoutParams.gravity = 19;
    }

    public static void addFloatView(Context paramContext) {
        if (view != null)
            return;
        WindowManager localWindowManager = getWindowmanager(paramContext);
        view = new FloatView(paramContext);
        localWindowManager.addView(view, layoutParams);
    }

    public static void cancelFloatView(Context paramContext) {
        if (view == null)
            return;
        getWindowmanager(paramContext).removeView(view);
        view = null;
    }

    public static WindowManager.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public static WindowManager getWindowmanager(Context paramContext) {
        return ((WindowManager) paramContext.getSystemService("window"));
    }
}