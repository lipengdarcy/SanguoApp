package org.darcy.sanguo.sdk.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.darcy.sanguo.activity.LoginAct;
import org.darcy.sanguo.sdk.sqlite.LoginUserSQLite;
import org.darcy.sanguo.sdk.view.MyToast;
import org.darcy.sanguo.sdk.view.ProgressDialog;

import org.darcy.sanguo.client.Account;
import org.darcy.sanguo.client.GameAdmin;
import org.darcy.sanguo.utils.MyResource;
import org.darcy.sanguo.utils.TextUtil;

public class UsernameRegFragment extends Fragment
        implements View.OnFocusChangeListener {
    private static final int loginSuccess = 400001;
    private Button backImageView;
    private int contentHeight;
    private View contentView;
    private int contentWidth;
    private LoginUserSQLite datas;
    private ProgressDialog dialog;

    @SuppressLint({"HandlerLeak"})
    Handler handler = new Handler();
    InputMethodManager manager;
    private FragmentActivity parentActivity;
    private ImageView passDeleteButton;
    private EditText passwordEditText;
    private TextView promptTextView;
    private Button registerButton;
    private String registerName = "";
    private String registerPass = "";
    private CheckBox showPasswordCheckBox;
    private ImageView usernameDeleteButton;
    private EditText usernameEditText;

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
        setUsernameSize();
        setPasswordSize();
        setCheckBoxSize();
        setRegisterButtonSize();
        setListener();
        setViews();
    }

    private void registNow() {
        this.dialog = new ProgressDialog(this.parentActivity, "正在创建账号...", false);
        this.dialog.show();
        try {
            Account.onUNameRegister(this.handler, this.registerName, this.registerPass);
            return;
        } catch (Exception localException) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + localException.toString());
            if ((this.dialog != null) && (this.dialog.isShowing()))
                this.dialog.dismiss();
            MyToast.makeToast(this.parentActivity, "注册失败~", 1).show();
            localException.printStackTrace();
        }
    }

    private void replaceFragment(Fragment paramFragment) {
        if (LoginAct.isSaveInstance)
            return;
        FragmentTransaction localFragmentTransaction = this.parentActivity.getSupportFragmentManager().beginTransaction();
        localFragmentTransaction.replace(LoginAct.loginFragId, paramFragment);
        localFragmentTransaction.commit();
    }

    private void setCheckBoxSize() {
        Object localObject = (ViewGroup.MarginLayoutParams) ((RelativeLayout) this.contentView.findViewById(getid("chujian_register_by_username_checkbox_relativelayout"))).getLayoutParams();
        ((ViewGroup.MarginLayoutParams) localObject).width = (int) (this.contentWidth * 0.889D);
        ((ViewGroup.MarginLayoutParams) localObject).height = -2;
        ((ViewGroup.MarginLayoutParams) localObject).setMargins(0, (int) (this.contentWidth * 0.01D), 0, (int) (this.contentWidth * 0.01D));
        localObject = new RelativeLayout.LayoutParams((int) (this.contentWidth * 0.08D), (int) (this.contentWidth * 0.08D));
        ((RelativeLayout.LayoutParams) localObject).setMargins(0, (int) (this.contentWidth * 0.05D), 0, (int) (this.contentWidth * 0.05D));
        this.showPasswordCheckBox.setLayoutParams((ViewGroup.LayoutParams) localObject);
        TextView localTextView = (TextView) this.contentView.findViewById(getid("chujian_register_by_username_showpassword_text"));
        localObject = new RelativeLayout.LayoutParams(-2, -2);
        ((RelativeLayout.LayoutParams) localObject).setMargins((int) (this.contentWidth * 0.1D), (int) (this.contentWidth * 0.05D), 0, 0);
        localTextView.setLayoutParams((ViewGroup.LayoutParams) localObject);
    }

    private void setContentSize() {
        FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(this.contentWidth, this.contentHeight, 17);
        this.contentView.setLayoutParams(localLayoutParams);
    }

    class UserNameWatcher implements TextWatcher {
        private String lastUsername;

        public void afterTextChanged(Editable paramEditable) {
            this.lastUsername = paramEditable.toString();
            if (this.lastUsername.length() < 2) {
                UsernameRegFragment.this.showErrorText("账号太短，请使用2位以上字符");
                return;
            }
            if (this.lastUsername.length() > 14) {
                UsernameRegFragment.this.showErrorText("账号太长，请使用14位以内字符");
                return;
            }
        }

        public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
        }

        public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
            if (!(paramCharSequence.toString().equals(""))) {
                UsernameRegFragment.this.usernameDeleteButton.setVisibility(View.VISIBLE);
                UsernameRegFragment.this.passDeleteButton.setVisibility(View.GONE);
            }
            if (paramCharSequence.toString().equals(this.lastUsername))
                return;
            UsernameRegFragment.this.passwordEditText.getText().clear();
            UsernameRegFragment.this.promptTextView.setVisibility(View.INVISIBLE);
        }
    }

    class PasswordWatcher implements TextWatcher {
        private String last;

        public void afterTextChanged(Editable paramEditable) {
            this.last = paramEditable.toString();
        }

        public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
        }

        public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
            if (!(paramCharSequence.toString().equals(this.last)))
                UsernameRegFragment.this.promptTextView.setVisibility(View.INVISIBLE);
            if (paramCharSequence.toString().equals(""))
                return;
            UsernameRegFragment.this.passDeleteButton.setVisibility(View.VISIBLE);
            UsernameRegFragment.this.usernameDeleteButton.setVisibility(View.GONE);
        }
    }

    private void setListener() {
        this.showPasswordCheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                if (UsernameRegFragment.this.showPasswordCheckBox.isChecked()) {
                    UsernameRegFragment.this.passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    return;
                }
                UsernameRegFragment.this.passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
        this.backImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                UsernameRegFragment.this.parentActivity.getSupportFragmentManager().popBackStack();
            }
        });
        this.usernameEditText.addTextChangedListener(new UserNameWatcher());
        this.passwordEditText.addTextChangedListener(new PasswordWatcher());
        this.usernameEditText.setOnFocusChangeListener(this);
        this.passwordEditText.setOnFocusChangeListener(this);
        this.registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                UsernameRegFragment.this.registerName = UsernameRegFragment.this.usernameEditText.getEditableText().toString();
                UsernameRegFragment.this.registerPass = UsernameRegFragment.this.passwordEditText.getEditableText().toString();
                if (TextUtil.isContainChinese(UsernameRegFragment.this.registerName)) {
                    UsernameRegFragment.this.showErrorText("用户名中不能包含中文");
                    return;
                }
                if (UsernameRegFragment.this.registerName.length() < 2)
                    UsernameRegFragment.this.showErrorText("账号太短，请使用2位以上字符");
                if (UsernameRegFragment.this.registerName.length() > 14)
                    UsernameRegFragment.this.showErrorText("账号太长，请使用14位以内字符");
                if (UsernameRegFragment.this.registerPass.length() < 6)
                    UsernameRegFragment.this.showErrorText("密码太短，请使用6位以上字符");
                if (UsernameRegFragment.this.registerPass.length() > 20)
                    UsernameRegFragment.this.showErrorText("密码太长，请使用20位以内字符");
                UsernameRegFragment.this.registNow();
            }
        });
        this.passDeleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                UsernameRegFragment.this.passwordEditText.getText().clear();
            }
        });
        this.usernameDeleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                UsernameRegFragment.this.usernameEditText.getText().clear();
            }
        });
    }

    private void setPasswordSize() {
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) ((LinearLayout) this.contentView.findViewById(getid("chujian_register_by_username_password_linearlayout"))).getLayoutParams();
        localMarginLayoutParams.height = (int) (this.contentHeight * 0.175D);
        localMarginLayoutParams.width = (int) (this.contentWidth * 0.889D);
        localMarginLayoutParams.setMargins(0, 0, 0, (int) (this.contentWidth * 0.01D));
        ((TextView) this.contentView.findViewById(getid("chujian_register_by_username_password_textview"))).setPadding((int) (this.contentWidth * 0.03D), (int) (this.contentWidth * 0.03D), (int) (this.contentWidth * 0.03D), (int) (this.contentWidth * 0.03D));
    }

    private void setPromptSize() {
        ((ViewGroup.MarginLayoutParams) ((LinearLayout) this.contentView.findViewById(getid("chujian_register_by_username_prompt_linearlayout"))).getLayoutParams()).width = (int) (this.contentWidth * 0.889D);
    }

    private void setRegisterButtonSize() {
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) this.registerButton.getLayoutParams();
        localMarginLayoutParams.width = (int) (this.contentWidth * 0.889D);
        localMarginLayoutParams.height = (localMarginLayoutParams.width / 6);
    }

    private void setTitleSize() {
        Object localObject = (ViewGroup.MarginLayoutParams) ((LinearLayout) this.contentView.findViewById(getid("chujian_register_by_username_title_linearlayout"))).getLayoutParams();
        ((ViewGroup.MarginLayoutParams) localObject).width = (int) (this.contentWidth * 0.889D);
        ((ViewGroup.MarginLayoutParams) localObject).setMargins(0, (int) (this.contentWidth * 0.04D), 0, (int) (this.contentWidth * 0.01D));
        localObject = this.backImageView.getLayoutParams();
        ((ViewGroup.LayoutParams) localObject).width = (int) (this.contentWidth * 0.1D);
        ((ViewGroup.LayoutParams) localObject).height = (int) (this.contentWidth * 0.1D);
        localObject = (LinearLayout.LayoutParams) ((ImageView) this.contentView.findViewById(getid("chujian_register_by_username_title_imageview"))).getLayoutParams();
        ((LinearLayout.LayoutParams) localObject).setMargins((int) (0.16D * this.contentWidth), 0, 0, 0);
        ((LinearLayout.LayoutParams) localObject).width = (int) (this.contentWidth * 0.45D);
        ((LinearLayout.LayoutParams) localObject).height = (((LinearLayout.LayoutParams) localObject).width / 5);
    }

    private void setUsernameSize() {
        Object localObject = (ViewGroup.MarginLayoutParams) ((LinearLayout) this.contentView.findViewById(getid("chujian_register_by_username_username_linearlayout"))).getLayoutParams();
        ((ViewGroup.MarginLayoutParams) localObject).width = (int) (this.contentWidth * 0.889D);
        ((ViewGroup.MarginLayoutParams) localObject).height = (int) (this.contentHeight * 0.175D);
        ((ViewGroup.MarginLayoutParams) localObject).setMargins(0, 0, 0, (int) (this.contentWidth * 0.03D));
        ((TextView) this.contentView.findViewById(getid("chujian_register_by_username_username_textview"))).setPadding((int) (this.contentWidth * 0.03D), (int) (this.contentWidth * 0.03D), (int) (this.contentWidth * 0.03D), (int) (this.contentWidth * 0.03D));
        ((ViewGroup.MarginLayoutParams) ((FrameLayout) this.contentView.findViewById(getid("chujian_register_by_username_username_delete_framelayout"))).getLayoutParams()).setMargins(0, 0, (int) (0.03D * this.contentHeight), 0);
        FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams) this.usernameDeleteButton.getLayoutParams();
        localLayoutParams.width = (int) (0.06D * this.contentWidth);
        localLayoutParams.height = localLayoutParams.width;
        localObject = (FrameLayout.LayoutParams) this.passDeleteButton.getLayoutParams();
        ((FrameLayout.LayoutParams) localObject).width = (int) (0.06D * this.contentWidth);
        ((FrameLayout.LayoutParams) localObject).height = localLayoutParams.width;
    }

    private void setViews() {
        this.promptTextView.setVisibility(View.INVISIBLE);
        this.usernameDeleteButton.setVisibility(View.GONE);
        this.passDeleteButton.setVisibility(View.GONE);
        this.usernameEditText.requestFocus();
    }

    private void showErrorText(String paramString) {
        this.promptTextView.setVisibility(View.VISIBLE);
        this.promptTextView.setText(paramString);
    }

    public void onAttach(Activity paramActivity) {
        super.onAttach(paramActivity);
        this.parentActivity = ((FragmentActivity) paramActivity);
        this.datas = LoginUserSQLite.getInstance(this.parentActivity);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View view = paramLayoutInflater.inflate(MyResource.getIdByName(getActivity(), "layout", "chujian_sdk_fragment_register_by_username"), null);
        this.contentView = view.findViewById(getid("chujian_register_by_username_content"));
        this.backImageView = ((Button) view.findViewById(getid("chujian_register_by_username_back_button")));
        this.usernameEditText = ((EditText) view.findViewById(getid("chujian_register_by_username_username_edittext")));
        this.passwordEditText = ((EditText) view.findViewById(getid("chujian_register_by_username_password_edittext")));
        this.showPasswordCheckBox = ((CheckBox) view.findViewById(getid("chujian_register_by_username_showpassword_checkbox")));
        this.registerButton = ((Button) view.findViewById(getid("chujian_register_by_username_register_button")));
        this.usernameDeleteButton = ((ImageView) view.findViewById(getid("chujian_register_by_username_username_delete_imageview")));
        this.passDeleteButton = ((ImageView) view.findViewById(getid("chujian_register_by_username_pass_delete_imageview1")));
        this.promptTextView = ((TextView) view.findViewById(getid("chujian_register_by_username_prompt_textview")));
        init();
        return view;
    }

    public void onFocusChange(View paramView, boolean paramBoolean) {
        String str = null;
        if (paramView.equals(this.usernameEditText)) {
            str = this.usernameEditText.getEditableText().toString();
            if ((paramBoolean) && (!(str.equals(""))))
                this.usernameDeleteButton.setVisibility(View.VISIBLE);
        }
        this.usernameDeleteButton.setVisibility(View.GONE);
        str = this.passwordEditText.getEditableText().toString();
        if ((paramBoolean) && (!(str.equals(""))))
            this.passDeleteButton.setVisibility(View.VISIBLE);
        this.passDeleteButton.setVisibility(View.GONE);

    }

    public void onPause() {
        super.onPause();
        if (this.manager == null)
            return;
        this.manager.hideSoftInputFromWindow(this.usernameEditText.getWindowToken(), 2);
    }

    public void onResume() {
        super.onResume();
        this.manager = ((InputMethodManager) this.parentActivity.getSystemService("input_method"));
        this.manager.toggleSoftInput(0, 2);
    }
}