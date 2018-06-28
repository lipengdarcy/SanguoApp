package org.darcy.sanguo.sdk.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.darcy.sanguo.activity.LoginAct;
import org.darcy.sanguo.sdk.popupwindow.UsersPopupWindow;
import org.darcy.sanguo.sdk.sqlite.LoginUserSQLite;

import org.darcy.sanguo.client.GameAdmin;
import org.darcy.sanguo.utils.MyResource;
import org.darcy.sanguo.utils.TextUtil;
import org.darcy.sanguo.sdk.view.MyToast;
import org.darcy.sanguo.sdk.view.ProgressDialog;
import org.darcy.sanguo.utils.SysInfoUtil;

import org.darcy.sanguo.client.Account;

public class LoginFragment extends Fragment
        implements View.OnClickListener, View.OnFocusChangeListener, AdapterView.OnItemClickListener {
    private static final int loginSuccess = 400001;
    private int contentHeight;
    private View contentView;
    private int contentWidth;
    private LoginUserSQLite datas;
    ProgressDialog dialog;
    private RelativeLayout findPassLayout;
    private TextView findPasswordTextView;
    FragmentManager fragmentManager;

    @SuppressLint({"HandlerLeak"})
    Handler handler = new Handler();
    private Button loginButton;
    private String loginPassword = null;
    private FragmentActivity parentActivity;
    private View passwordDeleteView;
    private EditText passwordEditText;
    private TextView promptTextView;
    private FrameLayout pulldownFrameLayout;
    private RelativeLayout registTipLayout;
    private Button registerButton;
    FragmentTransaction transaction;
    private String userName = null;
    private View usernameDeleteView;
    private EditText usernameEditText;
    private View usernameLinearLayout;
    private ImageView usernamePullDownView;
    private UsersPopupWindow usersPopupWindow;
    private View view;

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

    private int getmid(String paramString) {
        return MyResource.getIdByName(getActivity(), "id", paramString);
    }

    private void init() {
        getWindowSize();
        setContentSize();
        setTitleSize();
        setPromptSize();
        setUsernameSize();
        setPasswordSize();
        setButtonSize();
        setFindPasswordSize();
        setViewByPhonenumber();
        setListener();
        initUsername();
    }

    private void initUsername() {
        String str = this.datas.queryLastUserName();
        if (str != null) {
            this.usernameEditText.setText(str);
            this.passwordEditText.requestFocus();
            this.usernameDeleteView.setVisibility(View.GONE);
            return;
        }
        this.pulldownFrameLayout.setVisibility(View.GONE);
    }

    private void loginNow() {
        this.userName = this.usernameEditText.getEditableText().toString();
        this.loginPassword = this.passwordEditText.getEditableText().toString();
        if ((this.userName == null) || ("".equals(this.userName))) {
            showPromptText("请输入用户名");
            return;
        }
        if ((this.loginPassword == null) || ("".equals(this.loginPassword)))
            showPromptText("请输入密码");
        if (TextUtil.isContainChinese(this.userName))
            showPromptText("用户名中不能包含中文");
        if (this.loginPassword.length() < 6)
            showPromptText("密码长度不能小于6");
        try {
            Account.onChujianLogin(this.handler, this.userName, this.loginPassword);
            ProgressDialog localProgressDialog = new ProgressDialog(this.parentActivity, "正在登录...", true);
            this.dialog = localProgressDialog;
            this.dialog.show();
        } catch (Exception localException) {
            if ((this.dialog != null) && (this.dialog.isShowing()))
                this.dialog.dismiss();
            MyToast.makeToast(this.parentActivity, "登陆失败", 1).show();
            localException.printStackTrace();
        }

    }

    private void replaceFragment(Fragment paramFragment) {
        if (LoginAct.isSaveInstance)
            return;
        this.fragmentManager = this.parentActivity.getSupportFragmentManager();
        this.transaction = this.fragmentManager.beginTransaction();
        this.transaction.replace(LoginAct.loginFragId, paramFragment);
        if (this.parentActivity.isFinishing())
            return;
        this.transaction.commit();
    }

    private void setButtonSize() {
        Object localObject = (RelativeLayout) this.contentView.findViewById(MyResource.getIdByName(getActivity(), "id", "chujiansdk_login_button_relativelayout"));
        LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams) ((RelativeLayout) localObject).getLayoutParams();
        localLayoutParams.width = (int) (this.contentWidth * 0.889D);
        localLayoutParams.height = (int) (this.contentHeight * 0.186D);
        ((RelativeLayout) localObject).setLayoutParams(localLayoutParams);
        localObject = this.registerButton.getLayoutParams();
        ((ViewGroup.LayoutParams) localObject).width = (int) (this.contentWidth * 0.422D);
        ((ViewGroup.LayoutParams) localObject).height = (int) (this.contentHeight * 0.181D);
        localObject = this.loginButton.getLayoutParams();
        ((ViewGroup.LayoutParams) localObject).width = (int) (this.contentWidth * 0.422D);
        ((ViewGroup.LayoutParams) localObject).height = (int) (this.contentHeight * 0.181D);
    }

    private void setContentSize() {
        FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(this.contentWidth, this.contentHeight, 17);
        this.contentView.setLayoutParams(localLayoutParams);
    }

    private void setFindPasswordSize() {
        this.findPassLayout = ((RelativeLayout) this.contentView.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_find_password_layout")));
        ((ViewGroup.MarginLayoutParams) this.findPassLayout.getLayoutParams()).width = (int) (this.contentWidth * 0.889D);
        this.registTipLayout = ((RelativeLayout) this.contentView.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_register_tips_layout")));
    }

    class NameTextWatcher implements TextWatcher {
        String lastContent = "";

        public void afterTextChanged(Editable paramEditable) {
            this.lastContent = paramEditable.toString();
            if (paramEditable.toString().equals("")) {
                LoginFragment.this.passwordDeleteView.setVisibility(View.GONE);
                return;
            }
            LoginFragment.this.usernameDeleteView.setVisibility(View.VISIBLE);
        }

        public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
        }

        public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
            if (paramCharSequence.toString().equals(this.lastContent))
                return;
            LoginFragment.this.promptTextView.setVisibility(View.INVISIBLE);
            LoginFragment.this.passwordEditText.getText().clear();
        }
    }

    class PasswordTextwatcher implements TextWatcher {
        private String lastContent = "";

        public void afterTextChanged(Editable paramEditable) {
            this.lastContent = paramEditable.toString();
            if (paramEditable.toString().equals("")) {
                LoginFragment.this.passwordDeleteView.setVisibility(View.GONE);
                return;
            }
            LoginFragment.this.passwordDeleteView.setVisibility(View.VISIBLE);
        }

        public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
        }

        public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
            if (paramCharSequence.toString().equals(this.lastContent))
                return;
            LoginFragment.this.promptTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void setListener() {
        this.promptTextView.setVisibility(View.INVISIBLE);
        this.loginButton.setOnClickListener(this);
        this.usernameDeleteView.setOnClickListener(this);
        this.passwordDeleteView.setOnClickListener(this);
        this.usernameDeleteView.setVisibility(View.GONE);
        this.passwordDeleteView.setVisibility(View.GONE);
        this.usernameEditText.addTextChangedListener(new NameTextWatcher());
        this.passwordEditText.addTextChangedListener(new PasswordTextwatcher());
        this.usernameEditText.setOnFocusChangeListener(this);
        this.passwordEditText.setOnFocusChangeListener(this);
        this.usernamePullDownView.setOnClickListener(this);
        this.findPasswordTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                LoginFragment.this.showFragment(new FindPgetCodeFragment());
            }
        });
        this.passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView paramTextView, int paramInt, KeyEvent paramKeyEvent) {
                LoginFragment.this.loginNow();
                return false;
            }
        });
    }


    private void setPasswordSize() {
        Object localObject = this.contentView.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_password_linearlayout"));
        LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams((int) (this.contentWidth * 0.889D), (int) (this.contentHeight * 0.175D));
        localLayoutParams.gravity = 1;
        localLayoutParams.setMargins(0, 0, 0, (int) (0.035D * this.contentHeight));
        ((View) localObject).setLayoutParams(localLayoutParams);
        ((TextView) this.contentView.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_password_textview"))).setPadding((int) (this.contentWidth * 0.03D), (int) (this.contentWidth * 0.03D), (int) (this.contentWidth * 0.03D), (int) (this.contentWidth * 0.03D));
        ((ViewGroup.MarginLayoutParams) ((FrameLayout) this.contentView.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_password_delete_framelayout"))).getLayoutParams()).setMargins((int) (0.01D * this.contentHeight), 0, (int) (this.contentHeight * 0.03D), 0);
        localObject = (FrameLayout.LayoutParams) this.passwordDeleteView.getLayoutParams();
        ((FrameLayout.LayoutParams) localObject).width = (int) (0.06D * this.contentWidth);
        ((FrameLayout.LayoutParams) localObject).height = ((FrameLayout.LayoutParams) localObject).width;
    }

    private void setPromptSize() {
        ((ViewGroup.MarginLayoutParams) ((LinearLayout) this.contentView.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_prompt_linearlayout"))).getLayoutParams()).width = (int) (this.contentWidth * 0.889D);
    }

    private void setTitleSize() {
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) ((ImageView) this.contentView.findViewById(getmid("chujian_login_title_imageview"))).getLayoutParams();
        localMarginLayoutParams.width = (int) (this.contentWidth * 0.45D);
        localMarginLayoutParams.height = (localMarginLayoutParams.width / 5);
        localMarginLayoutParams.setMargins(0, (int) (this.contentWidth * 0.04D), 0, (int) (this.contentWidth * 0.03D));
    }

    private void setUsernameSize() {
        this.usernameLinearLayout = this.contentView.findViewById(getmid("chujian_login_username_linearlayout"));
        Object localObject = new LinearLayout.LayoutParams((int) (this.contentWidth * 0.889D), (int) (this.contentHeight * 0.175D));
        ((LinearLayout.LayoutParams) localObject).gravity = 1;
        ((LinearLayout.LayoutParams) localObject).setMargins(0, 0, 0, (int) (0.04D * this.contentHeight));
        this.usernameLinearLayout.setLayoutParams((ViewGroup.LayoutParams) localObject);
        ((TextView) this.contentView.findViewById(getmid("chujian_login_username_textview"))).setPadding((int) (this.contentWidth * 0.03D), (int) (this.contentWidth * 0.03D), (int) (this.contentWidth * 0.03D), (int) (this.contentWidth * 0.03D));
        ((ViewGroup.MarginLayoutParams) ((FrameLayout) this.contentView.findViewById(getmid("chujian_login_username_delete_framelayout"))).getLayoutParams()).setMargins((int) (0.01D * this.contentHeight), 0, (int) (this.contentHeight * 0.03D), 0);
        localObject = (FrameLayout.LayoutParams) this.usernameDeleteView.getLayoutParams();
        ((FrameLayout.LayoutParams) localObject).width = (int) (0.06D * this.contentWidth);
        ((FrameLayout.LayoutParams) localObject).height = ((FrameLayout.LayoutParams) localObject).width;
        this.pulldownFrameLayout = ((FrameLayout) this.contentView.findViewById(getmid("chujian_login_username_pulldown_framelayout")));
        ((ViewGroup.MarginLayoutParams) this.pulldownFrameLayout.getLayoutParams()).width = (int) (this.contentWidth * 0.135D);
        localObject = (ViewGroup.MarginLayoutParams) this.usernamePullDownView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) localObject).width = (int) (this.contentWidth * 0.135D);
        ((ViewGroup.MarginLayoutParams) localObject).height = ((ViewGroup.MarginLayoutParams) localObject).width;
    }

    private void setViewByPhonenumber() {
        final String str1 = SysInfoUtil.getPhoneNum(getActivity());
        if ((str1 != null) && (str1.length() > 0)) {
            this.registerButton.setText("手机一键注册");
            String str2 = str1;
            if (str1.contains("+86"))
                str2 = str1.substring(3);
            this.usernameEditText.setText(str2);
            this.usernameEditText.requestFocus();
            this.registerButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View paramView) {
                    try {
                        Account.onKeyRegister(LoginFragment.this.handler, str1);
                        ProgressDialog localProgressDialog = new ProgressDialog(LoginFragment.this.getActivity(), "正在创建账号...", true);
                        LoginFragment.this.dialog = localProgressDialog;
                        LoginFragment.this.dialog.show();
                        return;
                    } catch (Exception e) {
                        if ((LoginFragment.this.dialog != null) && (LoginFragment.this.dialog.isShowing()))
                            LoginFragment.this.dialog.dismiss();
                        GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + e.toString());
                        MyToast.makeToast(LoginFragment.this.parentActivity, "创建账号失败", 1).show();
                        e.printStackTrace();
                    }
                }
            });
            this.registTipLayout.setVisibility(View.VISIBLE);
            return;
        }
        this.registerButton.setText("快速注册");
        this.registTipLayout.setVisibility(View.GONE);
        this.registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                LoginFragment.this.showFragment(new FastRegFragment());
            }
        });
    }

    private void showFragment(Fragment paramFragment) {
        this.fragmentManager = this.parentActivity.getSupportFragmentManager();
        this.transaction = this.fragmentManager.beginTransaction();
        this.transaction.add(LoginAct.loginFragId, paramFragment);
        this.transaction.addToBackStack(null);
        this.transaction.commit();
    }

    private void showPromptText(String paramString) {
        this.promptTextView.setVisibility(View.VISIBLE);
        this.promptTextView.setText(paramString);
    }

    public void onActivityCreated(Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
    }

    public void onAttach(Activity paramActivity) {
        super.onAttach(paramActivity);
        this.parentActivity = ((FragmentActivity) paramActivity);
        this.fragmentManager = this.parentActivity.getSupportFragmentManager();
        this.datas = LoginUserSQLite.getInstance(this.parentActivity);
    }

    public void onClick(View paramView) {
        if (paramView.equals(this.loginButton)) {
            loginNow();
            return;
        }
        if (paramView.equals(this.usernameDeleteView)) {
            this.usernameEditText.getText().clear();
            return;
        }
        if (paramView.equals(this.passwordDeleteView)) {
            this.passwordEditText.getText().clear();
            return;
        }
        if (!(paramView.equals(this.usernamePullDownView)))
            return;
        Log.d("", "pop show");
        int i = MyResource.getIdByName(getActivity(), "drawable", "chujiansdk_login_pull_down_yes");
        this.usernamePullDownView.setImageResource(i);
        this.usersPopupWindow = new UsersPopupWindow(this.parentActivity, this.usernameLinearLayout.getMeasuredWidth(), this.usernameLinearLayout.getMeasuredHeight(), this, this.handler);
        this.usersPopupWindow.showAsDropDown(this.usernameLinearLayout);
        this.usersPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                int i = MyResource.getIdByName(LoginFragment.this.getActivity(), "drawable", "chujiansdk_login_pull_down");
                LoginFragment.this.usernamePullDownView.setImageResource(i);
            }
        });
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        this.view = paramLayoutInflater.inflate(MyResource.getIdByName(getActivity(), "layout", "chujian_sdk_fragment_login"), null);
        this.contentView = this.view.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_content"));
        this.usernameEditText = ((EditText) this.view.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_username_edittext")));
        this.usernameDeleteView = this.view.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_username_delete"));
        this.usernamePullDownView = ((ImageView) this.view.findViewById(MyResource.getIdByName(getActivity(), "id", "chujiansdk_login_username_pulldown")));
        this.passwordEditText = ((EditText) this.view.findViewById(MyResource.getIdByName(getActivity(), "id", "chujiansdk_login_password_edittext")));
        this.registerButton = ((Button) this.view.findViewById(MyResource.getIdByName(getActivity(), "id", "chujiansdk_login_register_button")));
        this.loginButton = ((Button) this.view.findViewById(MyResource.getIdByName(getActivity(), "id", "chujiansdk_login_login_button")));
        this.findPasswordTextView = ((TextView) this.view.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_find_password_textview")));
        this.findPasswordTextView.setText(Html.fromHtml("<u>找回密码</u>"));
        this.promptTextView = ((TextView) this.view.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_prompt_textview")));
        this.passwordDeleteView = this.view.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_password_delete"));
        init();
        return this.view;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onDetach() {
        super.onDetach();
    }

    public void onFocusChange(View paramView, boolean paramBoolean) {
        if (paramView.equals(this.usernameEditText))
            if (paramBoolean) {
                this.passwordDeleteView.setVisibility(View.GONE);
                if (this.usernameEditText.getEditableText().toString().equals(""))
                    this.usernameDeleteView.setVisibility(View.GONE);
            }
        if (!(paramView.equals(this.passwordEditText)))
            return;
    }

    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
        if (this.usersPopupWindow != null)
            this.usersPopupWindow.dismiss();
        String str = UsersPopupWindow.userNames[paramInt];
        this.usernameEditText.setText(str);
        this.passwordEditText.requestFocus();
        this.usernameDeleteView.setVisibility(View.GONE);
    }

    public void onPause() {
        if (!(LoginAct.isSaveInstance)) {
            this.fragmentManager = this.parentActivity.getSupportFragmentManager();
            this.transaction = this.fragmentManager.beginTransaction();
            this.transaction.hide(this);
            this.transaction.commit();
        }
        super.onPause();
    }

    public void onResume() {
        this.fragmentManager = this.parentActivity.getSupportFragmentManager();
        this.transaction = this.fragmentManager.beginTransaction();
        this.transaction.show(this);
        this.transaction.commit();
        super.onResume();
    }

    public void onStop() {
        super.onStop();
    }
}