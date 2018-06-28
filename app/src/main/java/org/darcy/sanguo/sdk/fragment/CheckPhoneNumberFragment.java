package org.darcy.sanguo.sdk.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

/**
 * 手机号码验证页面
 */
public class CheckPhoneNumberFragment extends Fragment {
    private static final int RERESHTIME = 600001;
    private static boolean isRun = true;
    private static Thread timeThread;
    private static int waitTime = 120;
    private int contentHeight;
    private View contentView;
    private int contentWidth;
    private ProgressDialog dialog;
    private TextView errorTextView;
    private Button fetchTextView;

    @SuppressLint({"HandlerLeak"})
    Handler handler = new Handler() {
        public void handleMessage(Message paramMessage) {
            if (paramMessage == null)
                return;
            switch (paramMessage.what) {
                default:
                case 600001:
                case 2002:
            }

            int i = paramMessage.arg1;
            CheckPhoneNumberFragment.this.fetchTextView.setText("重新获取\n(" + i + "s)");
            if (i != 0)
                return;
            CheckPhoneNumberFragment.isRun = false;
            CheckPhoneNumberFragment.this.isStop = false;
            CheckPhoneNumberFragment.this.fetchTextView.setText("重新获取");
            CheckPhoneNumberFragment.this.fetchTextView.setEnabled(true);
            CheckPhoneNumberFragment.this.registerButton.setEnabled(true);
            if (CheckPhoneNumberFragment.this.dialog != null)
                CheckPhoneNumberFragment.this.dialog.dismiss();
            if ((paramMessage.obj != null) && (!("".equals(paramMessage.obj)))) {
                JSONObject json;
                String str = paramMessage.obj.toString();
                if (!str.contains("{") && !(str.contains("}")))
                    MyToast.makeToast(CheckPhoneNumberFragment.this.parentActivity, "网络请求异常，请稍后再试~", 1).show();
                try {
                    json = new JSONObject(str);
                    if (!json.getString("status").equals("ok")) {
                        CheckPhoneNumberFragment.this.setButtonclickable();
                    }
                    String uName = null, pass = null, uid = null;
                    if (json.has("uName"))
                        uName = json.getString("uName");
                    if (json.has("pwd"))
                        pass = json.getString("pwd");
                    if (json.has("uId"))
                        uid = json.getString("uId");
                    if ((uName == null) || (pass == null) || (uid == null)) {
                        MyToast.makeToast(CheckPhoneNumberFragment.this.parentActivity, "网络请求异常，请稍后再试~", 2000).show();
                    }
                    LoginUserSQLite.getInstance(CheckPhoneNumberFragment.this.parentActivity).insertOrUpdate(uName, pass, 1, System.currentTimeMillis());
                    CheckPhoneNumberFragment.this.replaceFragemnt(new RegisterByPhoneSuccessFragment(uName, CommomUtil.encrpyDecode(pass)));

                    if (json.has("code"))
                        i = json.getInt("code");
                    if (OuterConstants.ERROR_SMS_SEND_FAIL == i) {
                        CheckPhoneNumberFragment.isRun = false;
                        CheckPhoneNumberFragment.this.isStop = false;
                        CheckPhoneNumberFragment.this.fetchTextView.setText("重新获取");
                        CheckPhoneNumberFragment.this.fetchTextView.setEnabled(true);
                    }
                    ErrorUtil.showErrorInfo(CheckPhoneNumberFragment.this.parentActivity, i, CheckPhoneNumberFragment.this.errorTextView);
                } catch (JSONException e) {
                    MyToast.makeToast(CheckPhoneNumberFragment.this.parentActivity, "网络请求异常，请稍后再试~", 2000).show();
                    GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + e.toString());
                    e.printStackTrace();
                }

            }
            MyToast.makeToast(CheckPhoneNumberFragment.this.parentActivity, "网络请求异常，请稍后再试~", 2000).show();
            CheckPhoneNumberFragment.this.showError("验证失败");

        }
    };

    boolean isStop = false;
    private FragmentActivity parentActivity;
    private EditText phoneNumberEditText;
    private ImageButton registerButton;
    SharedPreferences sp = null;

    private void getWindowSize() {
        Display localDisplay = getActivity().getWindowManager().getDefaultDisplay();
        this.contentWidth = localDisplay.getWidth();
        this.contentHeight = localDisplay.getHeight();
        if (this.contentWidth < this.contentHeight)
            this.contentWidth = (int) (this.contentWidth * 0.8D);
        for (this.contentHeight = (int) (this.contentWidth * 0.9D); ; this.contentHeight = (int) (this.contentWidth * 0.9D)) {
            this.contentWidth = (int) (this.contentWidth * 0.47D);
        }
    }

    private int getid(String paramString) {
        return MyResource.getIdByName(getActivity(), "id", paramString);
    }

    private void init() {
        getWindowSize();
        setContentSize();
        setTitleSize();
        setPromptSize();
        setErrorSize();
        setNumberSize();
        setRegisterButtonSize();
        setListener();
        this.phoneNumberEditText.requestFocus();
    }

    private void replaceFragemnt(Fragment paramFragment) {
        if (LoginAct.isSaveInstance)
            return;
        FragmentTransaction localFragmentTransaction = this.parentActivity.getSupportFragmentManager().beginTransaction();
        localFragmentTransaction.add(LoginAct.loginFragId, paramFragment);
        localFragmentTransaction.addToBackStack(null);
        localFragmentTransaction.commit();
    }

    private void setButtonclickable() {
        isRun = true;
        if (this.isStop)
            waitTime = this.sp.getInt("waitTime", 120);
        if (waitTime == 0)
            waitTime = 120;
        this.fetchTextView.setEnabled(false);
        if ((timeThread != null) && (timeThread.isAlive()))
            return;
        timeThread = new Thread();
        timeThread.start();
    }

    private void setContentSize() {
        FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(this.contentWidth, this.contentHeight, 17);
        this.contentView.setLayoutParams(localLayoutParams);
    }

    private void setErrorSize() {
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) ((LinearLayout) this.contentView.findViewById(getid("chujian_check_phone_number_error_linearlayout"))).getLayoutParams();
        localMarginLayoutParams.width = (int) (this.contentWidth * 0.889D);
        localMarginLayoutParams.setMargins(0, (int) (this.contentWidth * 0.05D), 0, 0);
        this.errorTextView.setPadding((int) (this.contentWidth * 0.005D), (int) (this.contentWidth * 0.005D), (int) (this.contentWidth * 0.005D), (int) (this.contentWidth * 0.005D));
        this.errorTextView.setVisibility(View.INVISIBLE);
    }


    private void setListener() {
        this.fetchTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                CheckPhoneNumberFragment.this.setButtonclickable();
                try {
                    Account.onGetVCode(CheckPhoneNumberFragment.this.handler, FastRegFragment.signPhoneNumber);
                    return;
                } catch (Exception e) {
                    GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + paramView.toString());
                    e.printStackTrace();
                }
            }
        });
        this.registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                String code = CheckPhoneNumberFragment.this.phoneNumberEditText.getEditableText().toString();
                if (code.equals("")) {
                    CheckPhoneNumberFragment.this.showError("验证码不能为空");
                    return;
                }
                CheckPhoneNumberFragment.this.registerButton.setEnabled(false);
                CheckPhoneNumberFragment.this.dialog = new ProgressDialog(CheckPhoneNumberFragment.this.parentActivity, "正在注册", true);
                CheckPhoneNumberFragment.this.dialog.show();
                try {
                    Account.onFastRegiter(CheckPhoneNumberFragment.this.handler, FastRegFragment.signPhoneNumber, code);
                } catch (Exception e) {
                    if ((CheckPhoneNumberFragment.this.dialog != null) && (CheckPhoneNumberFragment.this.dialog.isShowing()))
                        CheckPhoneNumberFragment.this.dialog.dismiss();
                    MyToast.makeToast(CheckPhoneNumberFragment.this.parentActivity, "注册失败~", 1).show();
                    GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + paramView.toString());
                    e.printStackTrace();
                }

            }
        });
    }

    private void setNumberSize() {
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) ((RelativeLayout) this.contentView.findViewById(getid("chujian_check_phone_number_number_relativelayout"))).getLayoutParams();
        localMarginLayoutParams.width = (int) (this.contentWidth * 0.889D);
        localMarginLayoutParams.height = (int) (this.contentWidth * 0.175D);
        localMarginLayoutParams.setMargins(0, 0, 0, (int) (this.contentWidth * 0.08D));
        ((LinearLayout) this.contentView.findViewById(getid("chujian_check_phone_number_number_linearlayout"))).getLayoutParams().width = (localMarginLayoutParams.width * 200 / 300);
        ((TextView) this.contentView.findViewById(getid("chujian_check_phone_number_number_textview"))).setPadding(localMarginLayoutParams.width / 30, localMarginLayoutParams.width / 30, 0, localMarginLayoutParams.width / 30);
        ((ViewGroup.MarginLayoutParams) this.phoneNumberEditText.getLayoutParams()).setMargins(this.contentWidth / 60, 0, 0, 0);
    }

    private void setPromptSize() {
        ((ViewGroup.MarginLayoutParams) ((TextView) this.contentView.findViewById(getid("chujian_check_phone_number_prompt_textview"))).getLayoutParams()).width = (int) (this.contentWidth * 0.889D);
    }

    private void setRegisterButtonSize() {
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) this.registerButton.getLayoutParams();
        localMarginLayoutParams.width = (int) (this.contentWidth * 0.889D);
        localMarginLayoutParams.height = (localMarginLayoutParams.width / 6);
    }

    private void setTitleSize() {
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) ((ImageView) this.contentView.findViewById(getid("chujian_check_phone_number_title_imageview"))).getLayoutParams();
        localMarginLayoutParams.width = (int) (this.contentWidth * 0.4D);
        localMarginLayoutParams.height = (localMarginLayoutParams.width * 4 / 15);
        localMarginLayoutParams.setMargins(0, (int) (this.contentWidth * 0.06D), 0, (int) (this.contentWidth * 0.04D));
    }

    private void showError(String paramString) {
        this.errorTextView.setVisibility(View.VISIBLE);
        this.errorTextView.setText(paramString);
    }

    public void onActivityCreated(Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
    }

    public void onAttach(Activity paramActivity) {
        super.onAttach(paramActivity);
        this.parentActivity = ((FragmentActivity) paramActivity);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View view = paramLayoutInflater.inflate(MyResource.getIdByName(getActivity(), "layout", "chujian_sdk_fragment_check_phone_number"), null);
        this.contentView = view.findViewById(getid("chujian_check_phone_number_content"));
        this.phoneNumberEditText = ((EditText) view.findViewById(getid("chujian_check_phone_number_number_edittext")));
        this.phoneNumberEditText.setImeOptions(6);
        this.fetchTextView = ((Button) view.findViewById(getid("chujian_check_phone_number_fetch_button")));
        this.registerButton = ((ImageButton) view.findViewById(getid("chujiansdk_check_phone_number_register_button")));
        this.errorTextView = ((TextView) view.findViewById(getid("chujian_check_phone_number_error_textview")));
        init();
        this.sp = this.parentActivity.getSharedPreferences("sp", 0);
        return view;
    }

    public void onDestroy() {
        isRun = false;
        super.onDestroy();
    }

    public void onDetach() {
        super.onDetach();
    }

    public void onPause() {
        isRun = false;
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        setButtonclickable();
    }

    public void onStop() {
        this.isStop = true;
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