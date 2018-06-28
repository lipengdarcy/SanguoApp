package org.darcy.sanguo.sdk.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.darcy.sanguo.activity.LoginAct;
import org.darcy.sanguo.sdk.sqlite.LoginUserSQLite;

import org.darcy.sanguo.client.GameAdmin;
import org.darcy.sanguo.utils.ErrorUtil;
import org.darcy.sanguo.utils.MyResource;
import org.darcy.sanguo.utils.TextUtil;
import org.darcy.sanguo.sdk.view.MyToast;
import org.darcy.sanguo.utils.CommomUtil;

import org.darcy.sanguo.client.Account;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"ValidFragment"})
public class RegisterByPhoneSuccessFragment extends Fragment {
    private static final int notUpdatePass = 200001;
    private int contentHeight;
    private View contentView;
    private int contentWidth;

    @SuppressLint({"HandlerLeak"})
    Handler handler = new Handler() {
        public void handleMessage(Message paramMessage) {
            switch (paramMessage.what) {

                case 2006:
                if ((paramMessage.obj == null) || ("".equals(paramMessage.obj)))
                    return;

                try {
                    String localObject = paramMessage.obj.toString();
                    JSONObject json = new JSONObject(localObject);
                    if (!(json.has("status"))) {
                        MyToast.makeToast(RegisterByPhoneSuccessFragment.this.getActivity(), "修改密码失败~（003）", 0).show();
                        return;
                    }
                    if (!(json.getString("status").equals("ok"))) {
                        ErrorUtil.showErrorInfo(RegisterByPhoneSuccessFragment.this.parentActivity, 1, null);
                        return;
                    }
                    if (RegisterByPhoneSuccessFragment.this.newPassTO != null)
                        LoginUserSQLite.getInstance(RegisterByPhoneSuccessFragment.this.parentActivity).insertOrUpdate(RegisterByPhoneSuccessFragment.this.userName, CommomUtil.encrpyEncode(RegisterByPhoneSuccessFragment.this.newPassTO), 1, System.currentTimeMillis());
                    FragmentManager manager = RegisterByPhoneSuccessFragment.this.parentActivity.getSupportFragmentManager();
                    LoginingFragment loginingFragment = new LoginingFragment(RegisterByPhoneSuccessFragment.this.userName);
                    RegisterByPhoneSuccessFragment.this.relaceFragment(loginingFragment);
                } catch (JSONException e) {
                    GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + paramMessage.toString());
                    e.printStackTrace();
                }
                case 200001:
                default:
            }
            FragmentManager manager = RegisterByPhoneSuccessFragment.this.parentActivity.getSupportFragmentManager();
            int j = manager.getBackStackEntryCount();
            for (int i = 0; i < j; ++i) {
                RegisterByPhoneSuccessFragment.this.relaceFragment(new LoginingFragment(RegisterByPhoneSuccessFragment.this.userName));
                manager.popBackStack();
            }
        }
    };

    private TextView initialPasswordTextView;
    private String newPassTO = null;
    private ImageButton okButton;
    private FragmentActivity parentActivity;
    private EditText passwordEditText;
    private TextView passwordPromptTextView;
    private String userName;
    private String userPass;
    private TextView usernameTextView;

    public RegisterByPhoneSuccessFragment(String userName, String userPass) {
        this.userName = userName;
        this.userPass = userPass;
    }

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
        setUsernameSize();
        setInitialPasswordSize();
        setPromptSize();
        setPasswordSize();
        setOkButtonSize();
        setListener();
        this.usernameTextView.setText("帐    号：" + this.userName);
        this.initialPasswordTextView.setText("初始密码：" + this.userPass);
    }

    private void relaceFragment(Fragment paramFragment) {
        if (LoginAct.isSaveInstance)
            return;
        FragmentTransaction localFragmentTransaction = this.parentActivity.getSupportFragmentManager().beginTransaction();
        localFragmentTransaction.replace(LoginAct.loginFragId, paramFragment);
        localFragmentTransaction.commit();
    }

    private void setContentSize() {
        FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(this.contentWidth, this.contentHeight, 17);
        this.contentView.setLayoutParams(localLayoutParams);
    }

    private void setInitialPasswordSize() {
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) this.initialPasswordTextView.getLayoutParams();
        localMarginLayoutParams.width = (int) (this.contentWidth * 0.889D);
        localMarginLayoutParams.setMargins(0, 0, 0, (int) (this.contentWidth * 0.025D));
    }

    private void setListener() {
        this.okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                String pass = RegisterByPhoneSuccessFragment.this.passwordEditText.getEditableText().toString();
                if (TextUtil.isContainChinese(pass)) {
                    RegisterByPhoneSuccessFragment.this.showError("密码中不能包含中文");
                    return;
                }

                if (pass.equals("")) {
                    RegisterByPhoneSuccessFragment.this.newPassTO = RegisterByPhoneSuccessFragment.this.userPass;
                    try {
                        Account.onChangePass(RegisterByPhoneSuccessFragment.this.handler, RegisterByPhoneSuccessFragment.this.userName, RegisterByPhoneSuccessFragment.this.userPass, RegisterByPhoneSuccessFragment.this.newPassTO);
                    } catch (Exception e) {
                        GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + e.toString());
                        Log.d("notifyPwd", paramView.toString());
                        e.printStackTrace();
                    }
                }
                if ((!(pass.equals(""))) && (pass.length() < 6))
                    RegisterByPhoneSuccessFragment.this.showError("密码太短，请使用6位以上字符");
                if ((pass.length() >= 6) && (pass.length() < 20)) {
                    RegisterByPhoneSuccessFragment.this.newPassTO = pass;
                    try {
                        Account.onChangePass(RegisterByPhoneSuccessFragment.this.handler, RegisterByPhoneSuccessFragment.this.userName, RegisterByPhoneSuccessFragment.this.userPass, pass);
                    } catch (Exception e) {
                        GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + e.toString());
                        Log.d("notifyPwd", e.toString());
                        e.printStackTrace();
                    }
                }
                if (pass.length() > 20) {
                    RegisterByPhoneSuccessFragment.this.showError("密码太长，请使用20位以内字符");
                    return;
                }
                RegisterByPhoneSuccessFragment.this.showError("密码格式有误请重新输入");

            }
        });
    }

    private void setOkButtonSize() {
        ViewGroup.LayoutParams localLayoutParams = this.okButton.getLayoutParams();
        localLayoutParams.width = (int) (this.contentWidth * 0.889D);
        localLayoutParams.height = (localLayoutParams.width / 6);
    }

    private void setPasswordSize() {
        ((ViewGroup.MarginLayoutParams) ((LinearLayout) this.contentView.findViewById(getid("chujian_register_by_phone_success_password_prompt_linearlayout"))).getLayoutParams()).width = (int) (this.contentWidth * 0.889D);
        View localView = this.contentView.findViewById(getid("chujian_register_by_phone_success_password_linearlayout"));
        Object localObject = new LinearLayout.LayoutParams((int) (this.contentWidth * 0.889D), (int) (this.contentHeight * 0.175D));
        ((LinearLayout.LayoutParams) localObject).gravity = 1;
        ((LinearLayout.LayoutParams) localObject).setMargins(0, 0, 0, (int) (0.035D * this.contentHeight));
        localView.setLayoutParams((ViewGroup.LayoutParams) localObject);
        localObject = (TextView) this.contentView.findViewById(getid("chujian_register_by_phone_success_password_textview"));
    }

    private void setPromptSize() {
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) ((TextView) this.contentView.findViewById(getid("chujian_register_by_phone_success_prompt_textview"))).getLayoutParams();
        localMarginLayoutParams.width = (int) (this.contentWidth * 0.889D);
        localMarginLayoutParams.setMargins(0, 0, 0, (int) (this.contentWidth * 0.025D));
        this.passwordPromptTextView.setVisibility(View.INVISIBLE);
    }

    private void setTitleSize() {
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) ((LinearLayout) this.contentView.findViewById(getid("chujian_register_by_phone_success_title_linearlayout"))).getLayoutParams();
        localMarginLayoutParams.width = (int) (this.contentWidth * 0.889D);
        localMarginLayoutParams.setMargins(0, (int) (this.contentWidth * 0.04D), 0, (int) (this.contentWidth * 0.03D));
        localMarginLayoutParams = (ViewGroup.MarginLayoutParams) ((ImageView) this.contentView.findViewById(getid("chujian_register_by_phone_success_title_tick_imageview"))).getLayoutParams();
        localMarginLayoutParams.width = (int) (this.contentWidth * 0.08D);
        localMarginLayoutParams.height = localMarginLayoutParams.width;
        localMarginLayoutParams.setMargins(0, 0, (int) (this.contentWidth * 0.04D), 0);
        localMarginLayoutParams = (ViewGroup.MarginLayoutParams) ((ImageView) this.contentView.findViewById(getid("chujian_register_by_phone_success_title_success_imageview"))).getLayoutParams();
        localMarginLayoutParams.width = (int) (this.contentWidth * 0.3D);
        localMarginLayoutParams.height = (localMarginLayoutParams.width * 3 / 10);
    }

    private void setUsernameSize() {
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) this.usernameTextView.getLayoutParams();
        localMarginLayoutParams.width = (int) (this.contentWidth * 0.889D);
        localMarginLayoutParams.setMargins(0, 0, 0, (int) (this.contentWidth * 0.025D));
    }

    private void showError(String paramString) {
        this.passwordPromptTextView.setVisibility(View.VISIBLE);
        this.passwordPromptTextView.setText(paramString);
    }

    public void onAttach(Activity paramActivity) {
        super.onAttach(paramActivity);
        this.parentActivity = ((FragmentActivity) paramActivity);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View view = paramLayoutInflater.inflate(MyResource.getIdByName(getActivity(), "layout", "chujian_sdk_fragment_register_by_phone_success"), null);
        this.contentView = view.findViewById(getid("chujian_register_by_phone_success_content"));
        this.usernameTextView = ((TextView) view.findViewById(getid("chujian_register_by_phone_success_username_textview")));
        this.initialPasswordTextView = ((TextView) view.findViewById(getid("chujian_register_by_phone_success_intial_password_textview")));
        this.passwordEditText = ((EditText) view.findViewById(getid("chujian_register_by_phone_success_password_edittext")));
        this.okButton = ((ImageButton) view.findViewById(getid("chujian_register_by_phone_success_ok_button")));
        this.passwordPromptTextView = ((TextView) view.findViewById(getid("chujian_register_by_phone_success_password_prompt_textview")));
        init();
        return view;
    }
}