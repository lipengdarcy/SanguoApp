package org.darcy.sanguo.sdk.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.darcy.sanguo.activity.LoginAct;
import org.darcy.sanguo.sdk.sqlite.LoginUserSQLite;

import org.darcy.sanguo.client.GameAdmin;
import org.darcy.sanguo.utils.ErrorUtil;
import org.darcy.sanguo.utils.MyResource;
import org.darcy.sanguo.sdk.view.MyToast;
import org.darcy.sanguo.sdk.view.ProgressDialog;
import org.darcy.sanguo.utils.CommomUtil;

import org.darcy.sanguo.client.Account;
import org.darcy.sanguo.constant.OuterConstants;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class NotifyPwdFragment extends Fragment {
    private static final int RERESHTIME = 500001;
    private static boolean isRun = true;
    private static boolean isStop = true;
    private static int waitTime = 120;
    private LinearLayout contentLayout;
    private FragmentManager fragmentManager;

    @SuppressLint({"HandlerLeak"})
    Handler handler = new Handler() {
        public void handleMessage(Message paramMessage) {
            switch (paramMessage.what) {
                default:
                case 2008:
                case 2007:
                case 500001:
            }
            JSONObject json;
            if (NotifyPwdFragment.this.progressDialog != null) {
                NotifyPwdFragment.this.progressDialog.dismiss();
                NotifyPwdFragment.this.progressDialog.cancel();
            }
            if ((paramMessage.obj != null) && (!("".equals(paramMessage.obj)))) {
                try {
                    json = new JSONObject(paramMessage.obj.toString());
                    if (!json.has("status")) {
                        MyToast.makeToast(NotifyPwdFragment.this.getActivity(), "修改密码失败(003)", 0).show();
                        return;
                    }
                    if (!json.getString("status").equals("ok")) {
                        if (json.has("code")) {
                            String code = json.getString("code");
                            if (code.equals("40000002")) {
                                MyToast.makeToast(NotifyPwdFragment.this.getActivity(), "验证码错误(" + ")", 0).show();
                            }
                            if (code.equals("40000010")) {
                                MyToast.makeToast(NotifyPwdFragment.this.getActivity(), "手机号不存在(" + ")", 0).show();
                            }
                            if (!(code.equals("40000007")))
                                return;
                            MyToast.makeToast(NotifyPwdFragment.this.getActivity(), "手机号错误(" + ")", 0).show();
                        }
                    }
                    MyToast.makeToast(NotifyPwdFragment.this.getActivity(), "修改密码成功", 0).show();
                    LoginUserSQLite sqLite = LoginUserSQLite.getInstance(NotifyPwdFragment.this.getActivity());
                    String str = NotifyPwdFragment.this.newpwdEditText.getEditableText().toString();
                    sqLite.insertOrUpdate(FindPgetCodeFragment.notify_phone, CommomUtil.encrpyEncode(str), 1, System.currentTimeMillis());
                    NotifyPwdFragment.this.replaceFragment(new LoginingFragment(FindPgetCodeFragment.notify_phone));
                } catch (JSONException e) {
                    GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + e.toString());
                    e.printStackTrace();
                }
                MyToast.makeToast(NotifyPwdFragment.this.getActivity(), "修改密码失败(002)", 0).show();
            }
            MyToast.makeToast(NotifyPwdFragment.this.getActivity(), "网络连接错误(001)", 0).show();
            if (NotifyPwdFragment.this.progressDialog != null) {
                NotifyPwdFragment.this.progressDialog.dismiss();
                NotifyPwdFragment.this.progressDialog.cancel();
            }
            if ((paramMessage.obj != null) && (!("".equals(paramMessage.obj)))) {
                try {
                    json = new JSONObject(paramMessage.obj.toString());
                    if (!json.has("status")) {
                        MyToast.makeToast(NotifyPwdFragment.this.getActivity(), "获取验证码失败(003)", 0).show();
                        return;
                    }
                    if (!json.getString("status").equals("ok")) {
                        if (json.has("code")) {
                            int code = 0;
                            if (json.has("code"))
                                code = json.getInt("code");
                            if (OuterConstants.ERROR_SMS_SEND_FAIL == code) {
                                NotifyPwdFragment.isRun = false;
                                NotifyPwdFragment.isStop = false;
                                NotifyPwdFragment.this.refreshButton.setText("重新获取");
                                NotifyPwdFragment.this.refreshButton.setEnabled(true);
                            }
                            ErrorUtil.showErrorInfo(NotifyPwdFragment.this.getActivity(), code, null);
                        }
                    }
                    NotifyPwdFragment.this.replaceFragment(new NotifyPwdFragment());
                } catch (JSONException e) {
                    GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + e.toString());
                    e.printStackTrace();
                }
                MyToast.makeToast(NotifyPwdFragment.this.getActivity(), "获取验证码失败(002)", 0).show();
            }
            MyToast.makeToast(NotifyPwdFragment.this.getActivity(), "网络连接错误(001)", 0).show();
            int i = paramMessage.arg1;
            NotifyPwdFragment.this.refreshButton.setText("重新获取\n(" + i + "s)");
            if (i != 0)
                return;
            NotifyPwdFragment.isRun = false;
            NotifyPwdFragment.isStop = false;
            NotifyPwdFragment.this.refreshButton.setText("重新获取");
            NotifyPwdFragment.this.refreshButton.setEnabled(true);

        }
    };

    private EditText newpwdEditText;
    private String notify_phone = null;
    private Button okButton;
    private ProgressDialog progressDialog;
    private Button refreshButton;
    SharedPreferences sp = null;
    private Thread timeThread;
    private TextView tipView;
    private FragmentTransaction transaction;
    private EditText vcodEditText;
    private View view;
    private int windowH;
    private int windowW;

    class MyTextWatcher implements TextWatcher {
        public void afterTextChanged(Editable paramEditable) {
            if (paramEditable.length() < 6) {
                NotifyPwdFragment.this.tipView.setVisibility(View.VISIBLE);
                NotifyPwdFragment.this.tipView.setText("请输入至少六位密码");
                return;
            }
            if (paramEditable.length() > 20) {
                NotifyPwdFragment.this.tipView.setVisibility(View.VISIBLE);
                NotifyPwdFragment.this.tipView.setText("密码长度不能大于20位");
                return;
            }
            NotifyPwdFragment.this.tipView.setVisibility(View.INVISIBLE);
        }

        public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
        }

        public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
        }
    }

    private void findID() {
        this.tipView = ((TextView) this.view.findViewById(getid("chujian_sdk_notifypwd_tips_tv")));
        this.contentLayout = ((LinearLayout) this.view.findViewById(getid("chujian_sdk_notifypwd_content")));
        FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(this.windowW, this.windowH);
        this.contentLayout.setLayoutParams(localLayoutParams);
        this.vcodEditText = ((EditText) this.view.findViewById(getid("chujian_sdk_notifypwd_vcode_edit")));
        this.newpwdEditText = ((EditText) this.view.findViewById(getid("chujian_sdk_notifypwd_newpwd_edit")));
        this.newpwdEditText.addTextChangedListener(new MyTextWatcher());
        this.okButton = ((Button) this.view.findViewById(getid("chujian_sdk_notifypwd_okbtn")));
        this.okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                String code = NotifyPwdFragment.this.vcodEditText.getEditableText().toString();
                String pass = NotifyPwdFragment.this.newpwdEditText.getEditableText().toString();
                if ((code.equals("")) || (code.length() == 0)) {
                    MyToast.makeToast(NotifyPwdFragment.this.getActivity(), "验证码不能为空（005）", 0).show();
                    return;
                }

                if (pass.length() < 6)
                    MyToast.makeToast(NotifyPwdFragment.this.getActivity(), "密码的长度不能小于6位（006）", 0).show();
                if (pass.length() > 20)
                    MyToast.makeToast(NotifyPwdFragment.this.getActivity(), "密码的长度不能大于20位（007）", 0).show();
                try {
                    Account.onFindPass(NotifyPwdFragment.this.handler, NotifyPwdFragment.this.notify_phone, pass, code);
                } catch (Exception e) {
                    GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + e.toString());
                    e.printStackTrace();
                }

            }
        });
        this.refreshButton = ((Button) this.view.findViewById(getid("chujian_sdk_notifypwd_getcode_btn")));
        this.refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                if (NotifyPwdFragment.this.progressDialog == null)
                    NotifyPwdFragment.this.progressDialog = new ProgressDialog(NotifyPwdFragment.this.getActivity(), "正在获取验证码...", true);
                NotifyPwdFragment.this.progressDialog.show();
                try {
                    Account.findPassGetVCode(NotifyPwdFragment.this.notify_phone, NotifyPwdFragment.this.handler);
                    return;
                } catch (Exception e) {
                    GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + e.toString());
                    e.printStackTrace();
                }
            }
        });
    }

    private void getWindowSize() {
        Display localDisplay = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        this.windowW = localDisplay.getWidth();
        this.windowH = localDisplay.getHeight();
        if (this.windowW < this.windowH)
            this.windowW = (int) (this.windowW * 0.8D);
        for (this.windowH = (int) (this.windowW * 0.9D); ; this.windowH = (int) (this.windowW * 0.9D)) {
            this.windowW = (int) (this.windowW * 0.47D);
        }
    }

    private int getid(String paramString) {
        return MyResource.getIdByName(getActivity(), "id", paramString);
    }

    private void init() {
        getWindowSize();
        findID();
        isStop = false;
        this.sp = getActivity().getSharedPreferences("sp", 0);
    }


    private void initButton() {
        isRun = true;
        if (isStop)
            waitTime = this.sp.getInt("waitTime", 120);
        if (waitTime == 0)
            waitTime = 120;
        this.refreshButton.setEnabled(false);
        if ((this.timeThread != null) && (this.timeThread.isAlive()))
            return;
        this.timeThread = new Thread(new Runnable() {
            public void run() {
                try {
                    if ((!(NotifyPwdFragment.isRun)) || (NotifyPwdFragment.waitTime == 0))
                        return;
                    NotifyPwdFragment.waitTime -= 1;
                    Thread.sleep(1000L);
                    NotifyPwdFragment.this.handler.sendMessage(NotifyPwdFragment.this.handler.obtainMessage(500001, NotifyPwdFragment.waitTime, 0));
                } catch (InterruptedException localInterruptedException) {
                    GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + localInterruptedException.toString());
                    localInterruptedException.printStackTrace();
                }
            }
        });
        this.timeThread.start();
    }

    private void replaceFragment(Fragment paramFragment) {
        List localList;
        if (!(LoginAct.isSaveInstance)) {
            this.fragmentManager = getActivity().getSupportFragmentManager();
            this.transaction = this.fragmentManager.beginTransaction();
            localList = this.fragmentManager.getFragments();
            for (int i = 0; ; ++i) {
                if (i >= localList.size()) {
                    this.transaction.replace(LoginAct.loginFragId, paramFragment);
                    this.transaction.commit();
                    return;
                }
                Fragment localFragment = (Fragment) localList.get(i);
                this.transaction.remove(localFragment);
            }
        }
    }

    public void onActivityCreated(Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
        init();
        initButton();
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        this.view = paramLayoutInflater.inflate(MyResource.getIdByName(getActivity(), "layout", "chujian_sdk_notify_fragment"), null);
        this.notify_phone = FindPgetCodeFragment.notify_phone;
        return this.view;
    }

    public void onPause() {
        isRun = false;
        super.onPause();
    }

    public void onResume() {
        initButton();
        super.onResume();
    }

    public void onStop() {
        isStop = true;
        SharedPreferences.Editor localEditor = this.sp.edit();
        if (waitTime != 0) {
            localEditor.putInt("waitTime", waitTime);

            localEditor.commit();
            super.onStop();
            return;
        }
        localEditor.putInt("waitTime", 120);
    }
}