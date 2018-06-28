package org.darcy.sanguo.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import org.darcy.sanguo.sdk.view.MyToast;
import org.darcy.sanguo.constant.OuterConstants;

public class ErrorUtil {
    public static void showErrorInfo(Context paramContext, int paramInt, TextView paramTextView) {
        String str = null;
        if (paramInt == OuterConstants.ERROR_USER_EXIST)
            str = "用户名已存在";
        if (paramInt == OuterConstants.ERROR_VCODE)
            str = "验证码错误";
        if (paramInt == OuterConstants.ERROR_PW_USERNAME)
            str = "用户名或密码输入错误";
        if (paramInt == OuterConstants.ERROR_SIGN)
            str = "签名错误";
        if (paramInt == OuterConstants.ERROR_PHONE_EXIST)
            str = "电话号已存在";
        if (paramInt == OuterConstants.ERROR_PGID_NOT_EXIST)
            str = "游戏不存在";
        if (paramInt == OuterConstants.ERROR_PHONE)
            str = "手机号格式错误";
        if (paramInt == OuterConstants.ERROR_PARAMETER)
            str = "参数错误";
        if (paramInt == OuterConstants.ERROR_USER_NOT_EXIST)
            str = "用户名不存在";
        if (paramInt == OuterConstants.ERROR_PHONE_NOT_EXIST)
            str = "手机号不存在";
        if (paramInt == OuterConstants.ERROR_SMS_SEND_FAIL)
            str = "短信发送失败";
        if (paramInt == OuterConstants.ERROR_REG)
            str = "非银行卡支付-后台服务器向易宝请求失败";
        if (paramInt == OuterConstants.ERROR_CHARGE_INFO)
            str = "非银行卡支付-向易宝提交的充值信息有误";
        if (paramInt == OuterConstants.ERROR_MSG_SEND_FAST)
            str = "一分钟内不能同时发送两次短信";
        if (paramInt == OuterConstants.ERROR_DURATION)
            str = "时长错误";
        if (paramInt == OuterConstants.ERROR_NOT_BIND_PHONE)
            str = "用户未绑定手机号";
        if (paramInt == OuterConstants.ERROR_SYSTEM)
            str = "系统异常";
        if (paramInt == OuterConstants.ERROR_PASSWORD)
            str = "密码错误";
        if (paramTextView != null) {
            paramTextView.setVisibility(View.VISIBLE);
            paramTextView.setText(str);
        }
        MyToast.makeToast(paramContext, str, 1).show();
        return;

    }
}