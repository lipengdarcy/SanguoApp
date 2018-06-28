package org.cocos2dx.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxLuaJavaBridge;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class PSDialog {
    private Vector<String> mButtonLabels = new Vector();
    private boolean mCancelable = true;
    private Cocos2dxActivity mContext = null;
    private AlertDialog mDialog = null;
    private DialogInterface.OnClickListener mDialogClickListener = null;
    private int mDialogLuaListener = 0;
    private Drawable mIcon = null;
    private String mMessage = null;
    private PSDialogListener mPSDialogListener = null;
    private String mTitle = null;

    public PSDialog(Cocos2dxActivity paramCocos2dxActivity) {
        this.mContext = paramCocos2dxActivity;
        this.mDialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, final int paramInt) {
                if (PSDialog.this.mDialogLuaListener != 0)
                    PSDialog.this.mContext.runOnGLThread(new Runnable() {
                        public void run() {
                            Object localObject = new HashMap();
                            ((Map) localObject).put("buttonIndex", String.valueOf(-paramInt));
                            localObject = new JSONObject((Map) localObject);
                            Cocos2dxLuaJavaBridge.callLuaFunctionWithString(PSDialog.this.mDialogLuaListener, ((JSONObject) localObject).toString());
                            Cocos2dxLuaJavaBridge.releaseLuaFunction(PSDialog.this.mDialogLuaListener);
                        }
                    });
                PSDialog.this.dismiss();
            }
        };
    }

    public int addAlertButton(String paramString) {
        if (this.mButtonLabels.size() >= 3) ;
        for (int i = this.mButtonLabels.size(); ; i = this.mButtonLabels.size()) {
            return i;
            // this.mButtonLabels.add(paramString);
        }
    }

    public void dismiss() {
        if ((this.mDialog == null) || (!(isShowing())))
            return;
        this.mDialog.dismiss();
        if (this.mPSDialogListener != null)
            this.mPSDialogListener.onDismiss(this);
        this.mDialog = null;
    }

    public int getButtonsCount() {
        return this.mButtonLabels.size();
    }

    public void hide() {
        if ((this.mDialog == null) || (!(isShowing())))
            return;
        this.mDialog.dismiss();
        this.mDialog = null;

    }

    public boolean isShowing() {
        if (this.mDialog == null) ;
        for (boolean bool = false; ; bool = this.mDialog.isShowing())
            return bool;
    }

    public PSDialog setCancelable(boolean paramBoolean) {
        this.mCancelable = paramBoolean;
        return this;
    }

    public PSDialog setIcon(Drawable paramDrawable) {
        this.mIcon = paramDrawable;
        return this;
    }

    public PSDialog setListener(PSDialogListener paramPSDialogListener) {
        this.mPSDialogListener = paramPSDialogListener;
        return this;
    }

    public PSDialog setLuaListener(int paramInt) {
        this.mDialogLuaListener = paramInt;
        return this;
    }

    public PSDialog setMessage(String paramString) {
        this.mMessage = paramString;
        return this;
    }

    public PSDialog setTitle(String paramString) {
        this.mTitle = paramString;
        return this;
    }

    public void show() {
        int i;
        if ((this.mDialog != null) && (isShowing())) {
            this.mDialog.dismiss();
            return;
        }
        this.mDialog = new AlertDialog.Builder(this.mContext).setCancelable(this.mCancelable).setTitle(this.mTitle).setMessage(this.mMessage).create();
        if ((this.mTitle != null) && (this.mTitle.length() > 0) && (this.mIcon != null))
            this.mDialog.setIcon(this.mIcon);
        i = 0;
        if (i < this.mButtonLabels.size())
            return;
        this.mDialog.show();

        switch (i) {
            default:
            case 0:
            case 1:
            case 2:
        }
        ++i;
        this.mDialog.setButton((CharSequence) this.mButtonLabels.elementAt(i), this.mDialogClickListener);
        this.mDialog.setButton2((CharSequence) this.mButtonLabels.elementAt(i), this.mDialogClickListener);
        this.mDialog.setButton3((CharSequence) this.mButtonLabels.elementAt(i), this.mDialogClickListener);
    }

    public static abstract interface PSDialogListener {
        public abstract void onDismiss(PSDialog paramPSDialog);
    }
}