package org.darcy.sanguo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;

import org.darcy.sanguo.sdk.fragment.LoginFragment;
import org.darcy.sanguo.sdk.fragment.LoginingFragment;
import org.darcy.sanguo.sdk.manager.CallBackManager;
import org.darcy.sanguo.sdk.sqlite.LoginUserSQLite;

import org.darcy.sanguo.client.GameAdmin;
import org.darcy.sanguo.utils.MyResource;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

/**
 * 用户登录
 */
public class LoginAct extends FragmentActivity {
    public static final String TAG = "FragmentActivity";
    public static Activity activity;
    private static boolean isLandScreen = true;
    public static boolean isSaveInstance = false;
    public static int loginFragId;
    private String lastUser = null;
    FragmentManager manager = getSupportFragmentManager();

    FragmentTransaction transaction;

    public static void close() {
        if (activity.isFinishing())
            return;
        activity.finish();
    }

    public static void loginCallBack(String paramString) {
        try {
            JSONObject localJSONObject = new org.json.JSONObject(paramString);
            Map map = new HashMap();
            if (localJSONObject.has("sessionId"))
                localJSONObject.put("sessionId", localJSONObject.getString("sessionId"));
            if (localJSONObject.has("uId"))
                localJSONObject.put("uId", localJSONObject.getString("uId"));
            CallBackManager.doLoginCallBack(localJSONObject.toString());
            return;
        } catch (JSONException e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "LoginAct:" + e.toString());
            e.printStackTrace();
        }
    }

    public void finish() {
        super.finish();
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations();
    }

    protected String getLastUser() {
        return LoginUserSQLite.getInstance(this).queryLastUserName();
    }

    public int getRequestedOrientation() {
        return super.getRequestedOrientation();
    }

    protected String[] getUsers() {
        return LoginUserSQLite.getInstance(this).queryAllUserName();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        Log.d("LoginAct", "onCreate");
        setContentView(MyResource.getIdByName(this, "layout", "chujian_sdk_containeract"));
        paramBundle = getIntent().getExtras();
        if (paramBundle != null) {
            isLandScreen = paramBundle.getBoolean("islandScreen", true);
            if (!isLandScreen) {
                setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
                return;
            }
            setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
        }
        while (true) {
            loginFragId = MyResource.getIdByName(this, "id", "chujian_container_contentlayout");
            try {
                while (true) {
                    this.lastUser = getLastUser();
                    isSaveInstance = false;
                    if (this.lastUser != null)
                        break;
                    this.transaction = this.manager.beginTransaction();
                    this.transaction.add(loginFragId, new LoginFragment());
                    this.transaction.addToBackStack(null);
                    this.transaction.commit();
                    isLandScreen = true;
                    return;
                }
                setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
            } catch (Exception e) {
                while (true) {
                    GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + e.toString());
                    e.printStackTrace();
                    this.transaction = this.manager.beginTransaction();
                    this.transaction.add(loginFragId, new LoginingFragment(this.lastUser));
                    this.transaction.commit();
                }
            }
        }
    }

    protected void onDestroy() {
        LoginingFragment.clickChangeuserBtn = true;
        super.onDestroy();
    }

    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        boolean bool = true;
        if ((paramInt == 4) && (this.manager.getBackStackEntryCount() == 1))
            finish();
        while (bool) {
            bool = super.onKeyDown(paramInt, paramKeyEvent);
        }
        return bool;
    }

    protected void onNewIntent(Intent paramIntent) {
        super.onNewIntent(paramIntent);
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onPostResume() {
        isSaveInstance = false;
        super.onPostResume();
    }

    protected void onRestoreInstanceState(Bundle paramBundle) {
        isSaveInstance = false;
        super.onRestoreInstanceState(paramBundle);
    }

    protected void onResume() {
        activity = this;
        super.onResume();
    }

    protected void onResumeFragments() {
        isSaveInstance = false;
        super.onResumeFragments();
    }

    protected void onSaveInstanceState(Bundle paramBundle) {
        isSaveInstance = true;
        super.onSaveInstanceState(paramBundle);
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onStop() {
        super.onStop();
    }

    public void setRequestedOrientation(int paramInt) {
        if (isLandScreen)
            super.setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
        while (true) {
            super.setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}