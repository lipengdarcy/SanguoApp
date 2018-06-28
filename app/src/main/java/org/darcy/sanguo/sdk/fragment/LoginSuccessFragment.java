package org.darcy.sanguo.sdk.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.darcy.sanguo.utils.MyResource;

@SuppressLint({"ValidFragment"})
public class LoginSuccessFragment extends Fragment {
    private int contentHeight;
    private View contentView;
    private int contentWidth;
    private String userName;
    private TextView usernameTextView;

    public LoginSuccessFragment(String paramString1, String paramString2) {
        this.userName = paramString1;
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

    private void init() {
        getWindowSize();
        setCenterSize();
    }

    private void setCenterSize() {
        Object localObject1 = (LinearLayout) this.contentView.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_success_center_linearlayout"));
        Object localObject2 = (ViewGroup.MarginLayoutParams) ((LinearLayout) localObject1).getLayoutParams();
        ((ViewGroup.MarginLayoutParams) localObject2).width = (this.contentWidth * 1);
        ((ViewGroup.MarginLayoutParams) localObject2).setMargins(0, (int) (this.contentWidth * 0.1D), 0, 0);
        localObject2 = (LinearLayout) ((LinearLayout) localObject1).findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_success_username_linearlayout"));
        ((ViewGroup.MarginLayoutParams) ((LinearLayout) localObject2).getLayoutParams()).setMargins(0, (int) (this.contentWidth * 0.04D), 0, (int) (this.contentWidth * 0.04D));
        ((ViewGroup.MarginLayoutParams) ((TextView) ((LinearLayout) localObject2).findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_success_chujian_textview"))).getLayoutParams()).setMargins(0, 0, (int) (this.contentWidth * 0.02D), 0);
        localObject2 = (LinearLayout) ((LinearLayout) localObject1).findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_success_ok_linearlayout"));
        ((ViewGroup.MarginLayoutParams) ((LinearLayout) localObject2).getLayoutParams()).setMargins(0, 0, 0, (int) (this.contentWidth * 0.06D));
        localObject1 = (ViewGroup.MarginLayoutParams) ((ImageView) ((LinearLayout) localObject2).findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_success_ok_imageview"))).getLayoutParams();
        ((ViewGroup.MarginLayoutParams) localObject1).width = (int) (this.contentWidth * 0.08D);
        ((ViewGroup.MarginLayoutParams) localObject1).height = ((ViewGroup.MarginLayoutParams) localObject1).width;
        localObject1 = (TextView) ((LinearLayout) localObject2).findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_success_ok_textview"));
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        this.contentView = paramLayoutInflater.inflate(MyResource.getIdByName(getActivity(), "layout", "chujian_sdk_fragment_login_success"), null);
        this.usernameTextView = ((TextView) this.contentView.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_login_success_username_textview")));
        init();
        return this.contentView;
    }

    public void onResume() {
        super.onResume();
        if (this.userName == null)
            return;
        this.usernameTextView.setText(this.userName);
    }
}