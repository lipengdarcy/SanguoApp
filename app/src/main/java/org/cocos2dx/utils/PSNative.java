package org.cocos2dx.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Vibrator;
import android.telephony.TelephonyManager;

import java.util.Vector;

import org.cocos2dx.lib.Cocos2dxActivity;

public class PSNative {
    static Drawable mAppIcon;
    static Cocos2dxActivity mContext = null;
    static PSDialog mCreatingDialog;
    static PSDialog.PSDialogListener mPSDialogListener;
    static PSDialog mShowingDialog;
    static Vector<PSDialog> mShowingDialogs;
    static TelephonyManager mTelephonyManager = null;
    static Vibrator mVibrator = null;

    static {
        mCreatingDialog = null;
        mShowingDialog = null;
        mShowingDialogs = null;
        mAppIcon = null;
        mPSDialogListener = new PSDialog.PSDialogListener() {
            public void onDismiss(PSDialog paramPSDialog) {
                PSNative.showPreAlert();
            }
        };
    }

    public static int addAlertButton(String paramString) {
        if (mCreatingDialog == null) ;
        for (int i = 0; ; i = mCreatingDialog.addAlertButton(paramString))
            return i;
    }

    public static void cancelAlert() {
        if (mShowingDialog == null)
            return;
        mContext.runOnUiThread(new Runnable() {
            public void run() {
                PSNative.mShowingDialog.dismiss();
                PSNative.mShowingDialog = null;
            }
        });
    }


    public static void createAlert(final String message, final String title, final Vector<String> paramVector, final int paramInt) {
        if (mContext == null)
            return;
        mContext.runOnUiThread(new Runnable() {
            public void run() {
                PSNative.mCreatingDialog = new PSDialog(PSNative.mContext).setCancelable(false).setMessage(message).setTitle(title).setLuaListener(paramInt).setListener(PSNative.mPSDialogListener).setIcon(PSNative.mAppIcon);
                for (int i = 0; i < paramVector.size(); ++i) {
                    if (i >= paramVector.size()) {
                        if ((PSNative.mShowingDialog != null) && (PSNative.mShowingDialog.isShowing())) {
                            PSNative.mShowingDialogs.add(PSNative.mShowingDialog);
                            PSNative.mShowingDialog.hide();
                        }
                        PSNative.mCreatingDialog.show();
                        PSNative.mShowingDialog = PSNative.mCreatingDialog;
                        PSNative.mCreatingDialog = null;
                        return;
                    }
                    PSNative.addAlertButton((String) paramVector.get(i));
                }
            }
        });
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static String getDeviceName() {
        return Build.USER;
    }

    public static String getInputText(String paramString1, String paramString2, String paramString3) {
        return "";
    }

    private static String getMacAddress() {
        WifiInfo info = ((WifiManager) mContext.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
        if (info == null)
            return null;
        return info.getMacAddress();
    }

    public static String getOpenUDID() throws SecurityException {
        String deviceId = null;
        if (mTelephonyManager != null)
            deviceId = mTelephonyManager.getDeviceId();
        if (deviceId == null)
            deviceId = getMacAddress();
        if (deviceId == null)
            deviceId = "";
        return deviceId;
    }

    public static void init(Cocos2dxActivity paramCocos2dxActivity) {
        mContext = paramCocos2dxActivity;
        mTelephonyManager = (TelephonyManager) paramCocos2dxActivity.getSystemService(Context.TELEPHONY_SERVICE);
        mVibrator = (Vibrator) paramCocos2dxActivity.getSystemService(Context.VIBRATOR_SERVICE);
        mShowingDialogs = new Vector();
    }

    public static void openURL(String paramString) {
        if (mContext == null)
            return;
        Uri uri = Uri.parse(paramString);
        mContext.startActivity(new Intent("android.intent.action.VIEW", uri));
    }

    public static void setAppIcon(Drawable paramDrawable) {
        mAppIcon = paramDrawable;
    }

    public static void showAlert() {
        if (mCreatingDialog == null)
            return;
        mContext.runOnUiThread(new Runnable() {
            public void run() {
                if ((PSNative.mShowingDialog != null) && (PSNative.mShowingDialog.isShowing())) {
                    PSNative.mShowingDialogs.add(PSNative.mShowingDialog);
                    PSNative.mShowingDialog.hide();
                }
                PSNative.mCreatingDialog.show();
                PSNative.mShowingDialog = PSNative.mCreatingDialog;
                PSNative.mCreatingDialog = null;
            }
        });
    }

    public static void showAlertLua(final int paramInt) {
        if (mCreatingDialog == null)
            return;
        mContext.runOnGLThread(new Runnable() {
            public void run() {
                PSNative.mCreatingDialog.setLuaListener(paramInt);
                PSNative.showAlert();
            }
        });
    }

    public static void showPreAlert() {
        if (mShowingDialogs.size() > 0) {
            mShowingDialog = (PSDialog) mShowingDialogs.firstElement();
            mShowingDialogs.remove(0);
            mShowingDialog.show();
            return;
        }
        mShowingDialog = null;
    }

    public static void vibrate(long paramLong) {
        if (mVibrator == null)
            return;
        mVibrator.vibrate(paramLong);
    }

    public static void vibrate(long[] paramArrayOfLong, int paramInt) {
        if (mVibrator == null)
            return;
        mVibrator.vibrate(paramArrayOfLong, paramInt);
    }
}