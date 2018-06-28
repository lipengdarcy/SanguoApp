package org.darcy.sanguo.utils;

import android.app.Activity;

import org.darcy.sanguo.sdk.view.ProgressDialog;

public class DialogUtil {
    private static ProgressDialog dialog;

    public static void cancleDialog() {
        if (dialog == null)
            return;
        dialog.cancel();
        dialog.dismiss();
    }

    public static void showDialog(Activity paramActivity, String paramString, boolean paramBoolean) {
        dialog = new ProgressDialog(paramActivity, paramString, paramBoolean);
        dialog.show();
    }
}