package org.darcy.sanguo.sdk.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.darcy.sanguo.activity.LoginAct;
import org.darcy.sanguo.sdk.sqlite.LoginUserSQLite;
import org.darcy.sanguo.utils.MyResource;
import org.darcy.sanguo.utils.CommomUtil;

import org.darcy.sanguo.client.Account;

@SuppressLint({"ValidFragment"})
public class LoginingFragment extends Fragment {
    public static boolean clickChangeuserBtn = false;
    private static final int loginSuccess = 400001;
    private int contentHeight;
    private View contentView;
    private int contentWidth;
    LoginUserSQLite datas;
    private FragmentManager fragmentManager;

    @SuppressLint({"HandlerLeak"})
    Handler handler = new Handler();
    private FragmentActivity parentActivity;
    private ImageView switchButton;
    private FragmentTransaction transaction;
    private String userName;
    private TextView usernameTextView;

    public LoginingFragment() {
    }

    public LoginingFragment(String paramString) {
        this.userName = paramString;
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

    private int getids(String paramString) {
        return MyResource.getIdByName(getActivity(), "id", paramString);
    }

    private void init() {
        getWindowSize();
        setSwitchSize();
        setCenterSize();
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

    private void setCenterSize() {
        LinearLayout localLinearLayout = (LinearLayout) this.contentView.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_logining_center_linearlayout"));
        ((ViewGroup.MarginLayoutParams) localLinearLayout.getLayoutParams()).width = (this.contentWidth * 1);
        Object localObject = (LinearLayout) localLinearLayout.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_logining_username_linearlayout"));
        ((ViewGroup.MarginLayoutParams) ((LinearLayout) localObject).getLayoutParams()).setMargins(0, (int) (this.contentWidth * 0.05D), 0, (int) (this.contentWidth * 0.05D));
        ((ViewGroup.MarginLayoutParams) ((TextView) ((LinearLayout) localObject).findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_logining_chujian_textview"))).getLayoutParams()).setMargins(0, 0, (int) (this.contentWidth * 0.02D), 0);
        localLinearLayout = (LinearLayout) localLinearLayout.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_logining_progress_linearlayout"));
        ((ViewGroup.MarginLayoutParams) localLinearLayout.getLayoutParams()).setMargins(0, 0, 0, (int) (this.contentWidth * 0.06D));
        localObject = (ViewGroup.MarginLayoutParams) ((ProgressBar) localLinearLayout.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_logining_progress_progressbar"))).getLayoutParams();
        ((ViewGroup.MarginLayoutParams) localObject).width = (int) (this.contentWidth * 0.09D);
        ((ViewGroup.MarginLayoutParams) localObject).height = ((ViewGroup.MarginLayoutParams) localObject).width;
        ((ViewGroup.MarginLayoutParams) localObject).setMargins(0, 0, (int) (this.contentWidth * 0.04D), 0);
        localObject = (TextView) localLinearLayout.findViewById(getids("chujian_logining_progress_textview"));
    }

    private void setSwitchSize() {
        ((ViewGroup.MarginLayoutParams) ((RelativeLayout) this.contentView.findViewById(getids("chujian_logining_switch_relativelayout"))).getLayoutParams()).setMargins(0, 0, 0, (int) (this.contentWidth * 0.02D));
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) this.switchButton.getLayoutParams();
        localMarginLayoutParams.width = (int) (this.contentWidth * 0.3D);
        localMarginLayoutParams.height = (localMarginLayoutParams.width * 4 / 6);
        localMarginLayoutParams.setMargins(0, (int) (this.contentWidth * 0.04D), (int) (this.contentWidth * 0.04D), 0);
        this.switchButton.getDrawable().setAlpha(150);
        //this.switchButton.setOnClickListener(new 2 (this));
    }

    public void onActivityCreated(Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
        this.datas = LoginUserSQLite.getInstance(getActivity());
    }

    public void onAttach(Activity paramActivity) {
        super.onAttach(paramActivity);
        this.parentActivity = ((FragmentActivity) paramActivity);
        this.fragmentManager = this.parentActivity.getSupportFragmentManager();
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        this.contentView = paramLayoutInflater.inflate(MyResource.getIdByName(getActivity(), "layout", "chujian_sdk_fragment_logining"), null);
        this.switchButton = ((ImageView) this.contentView.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_logining_switch_imageview")));
        this.usernameTextView = ((TextView) this.contentView.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_logining_username_textview")));
        init();
        return this.contentView;
    }

    public void onResume() {
        super.onResume();
        clickChangeuserBtn = false;
        this.usernameTextView.setText(this.userName);
        try {
            Account.onChujianLogin(this.handler, this.userName, CommomUtil.encrpyDecode(this.datas.queryPasswordByName(this.userName)));
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }
}