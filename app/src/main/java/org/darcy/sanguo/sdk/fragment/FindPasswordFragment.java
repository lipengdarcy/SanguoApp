package org.darcy.sanguo.sdk.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.darcy.sanguo.utils.MyResource;

public class FindPasswordFragment extends Fragment implements View.OnClickListener {
    private Button backImageView;
    private int contentHeight;
    private View contentView;
    private int contentWidth;
    private Button okButton;
    private FragmentActivity parentActivity;

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
        setContentSize();
        setTip1Size();
        setOkButtonSize();
        setListener();
    }

    private void setContentSize() {
        FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(this.contentWidth, this.contentHeight, 17);
        this.contentView.setLayoutParams(localLayoutParams);
    }

    private void setListener() {
        this.backImageView.setOnClickListener(this);
        this.okButton.setOnClickListener(this);
    }

    private void setOkButtonSize() {
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams) this.okButton.getLayoutParams();
        localMarginLayoutParams.width = (int) (this.contentWidth * 0.889D);
        localMarginLayoutParams.height = (localMarginLayoutParams.width / 6);
        localMarginLayoutParams.setMargins(0, 0, 0, (int) (this.contentWidth * 0.06D));
    }

    private void setTextColor(TextView paramTextView) {
        SpannableString localSpannableString = new SpannableString(paramTextView.getText().toString());
        localSpannableString.setSpan(new ForegroundColorSpan(-16776961), 7, localSpannableString.length(), 33);
        paramTextView.setText(localSpannableString);
    }

    private void setTip1Size() {
        Object localObject1 = (LinearLayout) this.contentView.findViewById(getids("chujian_find_password_tip1_linearlayout"));
        Object localObject2 = (ViewGroup.MarginLayoutParams) ((LinearLayout) localObject1).getLayoutParams();
        ((ViewGroup.MarginLayoutParams) localObject2).width = (int) (this.contentWidth * 0.889D);
        ((ViewGroup.MarginLayoutParams) localObject2).setMargins(0, 0, 0, (int) (this.contentWidth * 0.06D));
        localObject1 = (TextView) ((LinearLayout) localObject1).findViewById(getids("chujian_find_password_tip1_title_textview"));
        localObject2 = (TextView) this.contentView.findViewById(getids("chujian_find_password_tip1_content_textview"));
        localObject1 = (ViewGroup.MarginLayoutParams) ((TextView) localObject2).getLayoutParams();
        ((ViewGroup.MarginLayoutParams) localObject1).width = (int) (this.contentWidth * 0.889D);
        ((ViewGroup.MarginLayoutParams) localObject1).setMargins(0, 0, 0, (int) (this.contentWidth * 0.06D));
        ((TextView) localObject2).setPadding((int) (this.contentWidth * 0.12D), 0, 0, 0);
        setTextColor((TextView) localObject2);
    }

    private void setTitleSize() {
        Object localObject = (ViewGroup.MarginLayoutParams) ((FrameLayout) this.contentView.findViewById(getids("chujian_find_password_title_linearlayout"))).getLayoutParams();
        ((ViewGroup.MarginLayoutParams) localObject).width = (int) (this.contentWidth * 0.889D);
        ((ViewGroup.MarginLayoutParams) localObject).setMargins(0, (int) (this.contentWidth * 0.08D), 0, (int) (this.contentWidth * 0.08D));
        localObject = this.backImageView.getLayoutParams();
        ((ViewGroup.LayoutParams) localObject).width = (int) (this.contentWidth * 0.1D);
        ((ViewGroup.LayoutParams) localObject).height = (int) (this.contentWidth * 0.1D);
    }

    public void onAttach(Activity paramActivity) {
        super.onAttach(paramActivity);
        this.parentActivity = ((FragmentActivity) paramActivity);
    }

    public void onClick(View paramView) {
        if (paramView.equals(this.backImageView))
            this.parentActivity.getSupportFragmentManager().popBackStack();
        if (!(paramView.equals(this.okButton)))
            return;
        this.parentActivity.getSupportFragmentManager().popBackStack();
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View view = paramLayoutInflater.inflate(MyResource.getIdByName(getActivity(), "layout", "chujian_sdk_fragment_find_password"), null);
        this.contentView = view.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_find_password_content"));
        this.okButton = ((Button) view.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_find_password_ok_button")));
        this.backImageView = ((Button) view.findViewById(MyResource.getIdByName(getActivity(), "id", "chujian_find_password_back_button")));
        init();
        return view;
    }
}