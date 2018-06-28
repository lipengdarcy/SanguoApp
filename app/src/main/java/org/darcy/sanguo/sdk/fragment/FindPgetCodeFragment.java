package org.darcy.sanguo.sdk.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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

import org.darcy.sanguo.client.GameAdmin;
import org.darcy.sanguo.utils.MyResource;
import org.darcy.sanguo.sdk.view.ProgressDialog;

import org.darcy.sanguo.client.Account;

public class FindPgetCodeFragment extends Fragment {
    public static String notify_cooike;
    public static String notify_phone;
    public static final String tag = "test";
    private FragmentActivity activity;
    private Button backButton;
    private Button checkButton;
    private LinearLayout contentLayout;
    private EditText editText;
    private FragmentManager fragmentManager;
    Handler handler = new Handler();
    private ProgressDialog progressDialog;
    private TextView showTextView;
    private TextView textView;
    private FragmentTransaction transaction;
    private View view;
    private int windowH;
    private int windowW;

    private void findId() {
        this.editText = ((EditText) this.view.findViewById(getid("chujian_sdk_findp_code_edit")));
        this.showTextView = ((TextView) this.view.findViewById(getid("chujian_sdk_findp_code_showtext")));
        this.showTextView.setVisibility(View.INVISIBLE);
        this.checkButton = ((Button) this.view.findViewById(getid("chujian_sdk_findp_code_btn")));
        this.checkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                String user = FindPgetCodeFragment.this.editText.getEditableText().toString();
                if (user.equals("")) {
                    FindPgetCodeFragment.this.showText("帐号不能为空");
                    return;
                }
                if (user.length() != 11)
                    FindPgetCodeFragment.this.showText("帐号长度不合法");
                if (FindPgetCodeFragment.this.progressDialog == null)
                    FindPgetCodeFragment.this.progressDialog = new ProgressDialog(FindPgetCodeFragment.this.activity, "正在获取验证码...", true);
                FindPgetCodeFragment.this.progressDialog.show();
                try {
                    Account.findPassGetVCode(user, FindPgetCodeFragment.this.handler);
                    FindPgetCodeFragment.notify_phone = user;
                } catch (Exception e) {
                    GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + e.toString());
                    e.printStackTrace();
                }

            }
        });
        this.backButton = ((Button) this.view.findViewById(getid("chujian_find_password_back_button")));
        this.backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                FindPgetCodeFragment.this.fragmentManager = FindPgetCodeFragment.this.activity.getSupportFragmentManager();
                FindPgetCodeFragment.this.fragmentManager.popBackStack();
            }
        });
        this.textView = ((TextView) this.view.findViewById(getid("chujian_sdk_findp_othermethod")));
        setColor(this.textView);
        this.textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                FindPgetCodeFragment.this.replaceFragment(new FindPasswordFragment());
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
        findId();
        this.contentLayout = ((LinearLayout) this.view.findViewById(getid("chujian_sdk_findp_code_content")));
        FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(this.windowW, this.windowH);
        this.contentLayout.setLayoutParams(localLayoutParams);
    }

    private void replaceFragment(Fragment paramFragment) {
        if (LoginAct.isSaveInstance)
            return;
        this.fragmentManager = this.activity.getSupportFragmentManager();
        this.transaction = this.fragmentManager.beginTransaction();
        this.transaction.add(LoginAct.loginFragId, paramFragment);
        this.transaction.addToBackStack(null);
        this.transaction.commit();
    }

    private void setColor(TextView paramTextView) {
        SpannableString localSpannableString = new SpannableString(paramTextView.getText());
        localSpannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#1E92FE")), 9, 13, 33);
        paramTextView.setText(localSpannableString);
    }

    private void showText(String paramString) {
        this.showTextView.setVisibility(View.VISIBLE);
        this.showTextView.setText(paramString);
    }

    public void onActivityCreated(Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
        this.activity = getActivity();
        init();
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        this.view = paramLayoutInflater.inflate(MyResource.getIdByName(getActivity(), "layout", "chujian_sdk_fragment_findpassword_getcode"), null);
        return this.view;
    }

    public void onPause() {
        super.onPause();
        notify_cooike = "";
    }
}