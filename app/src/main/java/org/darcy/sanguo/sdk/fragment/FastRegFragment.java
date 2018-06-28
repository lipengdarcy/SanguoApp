package org.darcy.sanguo.sdk.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.darcy.sanguo.activity.LoginAct;
import org.darcy.sanguo.utils.MyResource;
import org.darcy.sanguo.sdk.view.MyToast;
import org.darcy.sanguo.sdk.view.ProgressDialog;

import org.darcy.sanguo.client.Agent;
import org.darcy.sanguo.constant.OuterConstants;

public class FastRegFragment extends Fragment
        implements View.OnClickListener {
    public static String signPhoneNumber = "";
    private Button backImageView;
    private Button checkButton;
    private int contentHeight;
    private View contentView;
    private int contentWidth;
    private ProgressDialog dialog;
    private FragmentManager fragmentManager;

    @SuppressLint({"HandlerLeak"})
    Handler handler = new Handler();
    private FragmentActivity parentActivity;
    private EditText phoneNumberEditText;
    private TextView phonePromptTextView;
    private TextView registerByUsernameButton;
    private FragmentTransaction transaction;

    private boolean checkPhonenumber(String tel) {
        String[] array = OuterConstants.NUMBER;
        for (String s : array) {
            if (tel.startsWith(s))
                return true;
        }
        return false;
    }

    private void getWindowSize() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        this.contentWidth = dm.widthPixels;
        this.contentHeight = dm.heightPixels;
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
        setAdvertSize();
        setPhonePromptSize();
        setPhoneNumberSize();
        setCheckButtonSize();
        setRegisterByUsernameSize();
        setListener();
    }

    private void replaceFragment(Fragment paramFragment) {
        if (!(LoginAct.isSaveInstance)) {
            this.fragmentManager = this.parentActivity.getSupportFragmentManager();
            this.transaction = this.fragmentManager.beginTransaction();
            this.transaction.add(LoginAct.loginFragId, paramFragment);
            this.transaction.addToBackStack(null);
            this.transaction.commit();
            return;
        }
        MyToast.makeToast(this.parentActivity, "请清理内存，并且重新打开应用", 1).show();
    }

    private void setAdvertSize() {
        LinearLayout localLinearLayout = (LinearLayout) this.contentView.findViewById(getid("chujian_register_by_phone_advert_linearlayout"));
        Object localObject = (ViewGroup.MarginLayoutParams) localLinearLayout.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) localObject).width = (int) (this.contentWidth * 0.889D);
        ((ViewGroup.MarginLayoutParams) localObject).setMargins(0, 0, 0, (int) (this.contentWidth * 0.03D));
        localObject = (ViewGroup.MarginLayoutParams) ((ImageView) localLinearLayout.findViewById(getid("chujian_register_by_phone_advert_imageview"))).getLayoutParams();
        ((ViewGroup.MarginLayoutParams) localObject).setMargins(0, 0, (int) (this.contentWidth * 0.01D), 0);
        ((ViewGroup.MarginLayoutParams) localObject).width = (int) (this.contentWidth * 0.035D);
        ((ViewGroup.MarginLayoutParams) localObject).height = ((ViewGroup.MarginLayoutParams) localObject).width;
        localObject = (TextView) this.contentView.findViewById(getid("chujian_register_by_phone_advert_textview"));
    }

    private void setCheckButtonSize() {
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) this.checkButton.getLayoutParams();
        localMarginLayoutParams.width = (int) (this.contentWidth * 0.889D);
        localMarginLayoutParams.height = (int) (this.contentWidth * 0.13D);
        localMarginLayoutParams.setMargins(0, 0, 0, (int) (this.contentWidth * 0.06D));
    }

    private void setContentSize() {
        FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(this.contentWidth, this.contentHeight, 17);
        this.contentView.setLayoutParams(localLayoutParams);
    }

    private void setListener() {
        //this.phoneNumberEditText.addTextChangedListener(new MyTextWatcher(this));
        //this.registerByUsernameButton.setOnClickListener(new 2 (this));
        //this.checkButton.setOnClickListener(new 3 (this));
        //this.backImageView.setOnClickListener(this);
    }

    private void setPhoneNumberSize() {
        Object localObject = (ViewGroup.MarginLayoutParams) ((LinearLayout) this.contentView.findViewById(getid("chujian_register_by_phone_phone_linearlayout"))).getLayoutParams();
        ((ViewGroup.MarginLayoutParams) localObject).width = (int) (this.contentWidth * 0.889D);
        ((ViewGroup.MarginLayoutParams) localObject).setMargins(0, 0, 0, (int) (this.contentWidth * 0.03D));
        localObject = (TextView) this.contentView.findViewById(getid("chujian_register_by_phone_phone_textview"));
    }

    private void setPhonePromptSize() {
        ((ViewGroup.MarginLayoutParams) ((LinearLayout) this.contentView.findViewById(getid("chujian_register_by_phone_phone_prompt_linearlayout"))).getLayoutParams()).width = (int) (this.contentWidth * 0.889D);
    }

    private void setRegisterByUsernameSize() {
        Object localObject = (LinearLayout) this.contentView.findViewById(getid("chujian_register_by_phone_register_by_username_linearlayout"));
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) ((LinearLayout) localObject).getLayoutParams();
        localMarginLayoutParams.width = (int) (this.contentWidth * 0.889D);
        localMarginLayoutParams.setMargins(0, 0, 0, (int) (this.contentWidth * 0.04D));
        localObject = (ViewGroup.MarginLayoutParams) ((ImageView) ((LinearLayout) localObject).findViewById(getid("chujian_register_by_phone_register_by_username_imageview"))).getLayoutParams();
        ((ViewGroup.MarginLayoutParams) localObject).setMargins(0, 0, (int) (this.contentWidth * 0.03D), 0);
        ((ViewGroup.MarginLayoutParams) localObject).width = (int) (this.contentWidth * 0.035D);
        ((ViewGroup.MarginLayoutParams) localObject).height = ((ViewGroup.MarginLayoutParams) localObject).width;
    }

    private void setTitleSize() {
        Object localObject = (ViewGroup.MarginLayoutParams) ((LinearLayout) this.contentView.findViewById(getid("chujian_register_by_phone_title_linearlayout"))).getLayoutParams();
        ((ViewGroup.MarginLayoutParams) localObject).width = (int) (this.contentWidth * 0.889D);
        ((ViewGroup.MarginLayoutParams) localObject).setMargins(0, (int) (this.contentWidth * 0.04D), 0, (int) (this.contentWidth * 0.05D));
        localObject = this.backImageView.getLayoutParams();
        ((ViewGroup.LayoutParams) localObject).width = (int) (this.contentWidth * 0.1D);
        ((ViewGroup.LayoutParams) localObject).height = (int) (this.contentWidth * 0.1D);
        localObject = (LinearLayout.LayoutParams) ((ImageView) this.contentView.findViewById(getid("chujian_register_by_phone_title_imageview"))).getLayoutParams();
        ((LinearLayout.LayoutParams) localObject).setMargins((int) (0.18D * this.contentWidth), 0, 0, 0);
        ((LinearLayout.LayoutParams) localObject).width = (int) (this.contentWidth * 0.45D);
        ((LinearLayout.LayoutParams) localObject).height = (((LinearLayout.LayoutParams) localObject).width / 5);
    }

    private void showPrompt(String paramString) {
        this.phonePromptTextView.setVisibility(View.VISIBLE);
        this.phonePromptTextView.setText(paramString);
    }

    public void onAttach(Activity paramActivity) {
        super.onAttach(paramActivity);
        this.parentActivity = ((FragmentActivity) paramActivity);
        this.fragmentManager = this.parentActivity.getSupportFragmentManager();
    }

    public void onClick(View paramView) {
        if (paramView.equals(this.backImageView)) {
            if (this.fragmentManager.getBackStackEntryCount() > 0)
                this.fragmentManager.popBackStack();
        }
        replaceFragment(new LoginFragment());
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View view = paramLayoutInflater.inflate(MyResource.getIdByName(getActivity(), "layout", "chujian_sdk_fragment_register_by_phone"), null);
        this.contentView = view.findViewById(getid("chujian_register_by_phone_content"));
        this.backImageView = ((Button) view.findViewById(getid("chujian_register_by_phone_back_button")));
        this.phoneNumberEditText = ((EditText) view.findViewById(getid("chujian_register_by_phone_phone_edittext")));
        this.checkButton = ((Button) view.findViewById(getid("chujian_register_by_phone_check_button")));
        this.registerByUsernameButton = ((TextView) view.findViewById(getid("chujian_register_by_phone_register_by_username_textview")));
        SpannableString spannableString = new SpannableString("您也可以用用户名注册");
        spannableString.setSpan(new ForegroundColorSpan(Color.rgb(18, 150, 205)), 5, 10, 18);
        spannableString.setSpan(new UnderlineSpan(), 5, 10, 18);
        this.registerByUsernameButton.getPaint().setFakeBoldText(true);
        this.registerByUsernameButton.setText(spannableString);
        this.phonePromptTextView = ((TextView) view.findViewById(getid("chujian_register_by_phone_phone_prompt_textview")));
        this.phonePromptTextView.setVisibility(View.INVISIBLE);
        init();
        Agent.onEnterRegisterPage();
        return view;
    }
}