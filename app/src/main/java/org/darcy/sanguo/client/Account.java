package org.darcy.sanguo.client;

import android.os.Handler;

import org.darcy.sanguo.message.MsgSenderImpl;
import org.darcy.sanguo.pojo.SimpleMap;

import org.darcy.sanguo.constant.InnerConstants;
import org.darcy.sanguo.exception.SysException;
import org.darcy.sanguo.utils.BaseInfoUtil;
import org.darcy.sanguo.utils.CommomUtil;
import org.darcy.sanguo.utils.ParamUtil;
import org.darcy.sanguo.utils.SysInfoUtil;

/**
 * 账号管理
 */
public class Account {
    public static Integer AGE;
    public static String GAME_SERVER;
    public static Integer GENDER;
    public static String USER_ID = null;
    public static Integer USER_LEVEL = null;
    public static String USER_NAME;

    static {
        GAME_SERVER = null;
        USER_NAME = null;
        GENDER = null;
        AGE = null;
    }

    public static void findPassGetVCode(String uName, Handler paramHandler)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(7));
        ParamUtil.addParam(localSimpleMap, "uName", uName);
        MsgSenderImpl.send(GameAdmin.CONTEXT, paramHandler, "/inter/landRegistration.page", localSimpleMap);
    }

    public static void onChangePass(Handler paramHandler, String paramString1, String paramString2, String paramString3)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(6));
        ParamUtil.addParam(localSimpleMap, "uName", paramString1);
        ParamUtil.addParam(localSimpleMap, "pwd", CommomUtil.encrpyEncode(paramString2));
        ParamUtil.addParam(localSimpleMap, "newPwd", CommomUtil.encrpyEncode(paramString3));
        MsgSenderImpl.send(GameAdmin.CONTEXT, paramHandler, "/inter/landRegistration.page", localSimpleMap);
    }

    public static void onChoiceGameRole(String paramString)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(12));
        ParamUtil.addParam(localSimpleMap, "uId", USER_ID);
        ParamUtil.addParam(localSimpleMap, "gameServer", GAME_SERVER);
        if (paramString.length() > 18)
            throw new SysException("roleName is length over 18");
        ParamUtil.addParam(localSimpleMap, "roleName", paramString);
        MsgSenderImpl.send(GameAdmin.CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }

    public static void onChujianLogin(Handler paramHandler, String paramString1, String paramString2)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(4));
        ParamUtil.addParamCanBeNull(localSimpleMap, "adCode", InnerConstants.ADCODE);
        ParamUtil.addParam(localSimpleMap, "uName", paramString1);
        ParamUtil.addParam(localSimpleMap, "pwd", CommomUtil.encrpyEncode(paramString2));
        MsgSenderImpl.send(GameAdmin.CONTEXT, paramHandler, "/inter/landRegistration.page", localSimpleMap);
    }

    public static void onFastRegiter(Handler paramHandler, String paramString1, String paramString2)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(2));
        ParamUtil.addParam(localSimpleMap, "telephone", paramString1, SysInfoUtil.getPhoneNum(GameAdmin.getContext()));
        ParamUtil.addParam(localSimpleMap, "vcode", paramString2);
        ParamUtil.addParamCanBeNull(localSimpleMap, "adCode", InnerConstants.ADCODE);
        MsgSenderImpl.send(GameAdmin.CONTEXT, paramHandler, "/inter/landRegistration.page", localSimpleMap);
    }

    public static void onFindPass(Handler paramHandler, String uName, String pwd, String vcode)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(8));
        ParamUtil.addParam(localSimpleMap, "uName", uName);
        ParamUtil.addParam(localSimpleMap, "pwd", CommomUtil.encrpyEncode(pwd));
        ParamUtil.addParam(localSimpleMap, "vcode", vcode);
        MsgSenderImpl.send(GameAdmin.CONTEXT, paramHandler, "/inter/landRegistration.page", localSimpleMap);
    }

    public static void onGetVCode(Handler paramHandler, String paramString)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(5));
        ParamUtil.addParam(localSimpleMap, "telephone", paramString);
        MsgSenderImpl.send(GameAdmin.CONTEXT, paramHandler, "/inter/landRegistration.page", localSimpleMap);
    }

    public static void onKeyRegister(Handler paramHandler, String paramString)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(1));
        ParamUtil.addParamCanBeNull(localSimpleMap, "adCode", InnerConstants.ADCODE);
        ParamUtil.addParam(localSimpleMap, "telephone", paramString, SysInfoUtil.getPhoneNum(GameAdmin.getContext()));
        MsgSenderImpl.send(GameAdmin.CONTEXT, paramHandler, "/inter/landRegistration.page", localSimpleMap);
    }

    public static void onLoginOk(String paramString)
            throws SysException {
        USER_ID = paramString;
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(6));
        ParamUtil.addParam(localSimpleMap, "uId", paramString);
        ParamUtil.addParamCanBeNull(localSimpleMap, "adCode", InnerConstants.ADCODE);
        MsgSenderImpl.send(GameAdmin.CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }

    public static void onLoginPage()
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(5));
        ParamUtil.addParamCanBeNull(localSimpleMap, "adCode", InnerConstants.ADCODE);
        MsgSenderImpl.send(GameAdmin.CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }

    public static void onLogout()
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(8));
        ParamUtil.addParam(localSimpleMap, "uId", USER_ID);
        USER_ID = null;
        ParamUtil.addParamCanBeNull(localSimpleMap, "level", USER_LEVEL);
        MsgSenderImpl.send(GameAdmin.CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }

    public static void onRegPage()
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(3));
        ParamUtil.addParamCanBeNull(localSimpleMap, "adCode", InnerConstants.ADCODE);
        MsgSenderImpl.send(GameAdmin.CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }

    public static void onSwichBacLogout()
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(10));
        ParamUtil.addParam(localSimpleMap, "uId", USER_ID);
        USER_ID = null;
        ParamUtil.addParamCanBeNull(localSimpleMap, "level", USER_LEVEL);
        MsgSenderImpl.send(GameAdmin.CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }

    public static void onSwitchLogout()
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(9));
        ParamUtil.addParam(localSimpleMap, "uId", USER_ID);
        USER_ID = null;
        ParamUtil.addParamCanBeNull(localSimpleMap, "level", USER_LEVEL);
        MsgSenderImpl.send(GameAdmin.CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }

    public static void onUNameRegister(Handler paramHandler, String paramString1, String paramString2)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(3));
        ParamUtil.addParamCanBeNull(localSimpleMap, "adCode", InnerConstants.ADCODE);
        ParamUtil.addParam(localSimpleMap, "uName", paramString1);
        ParamUtil.addParam(localSimpleMap, "pwd", CommomUtil.encrpyEncode(paramString2));
        MsgSenderImpl.send(GameAdmin.CONTEXT, paramHandler, "/inter/landRegistration.page", localSimpleMap);
    }

    public static void updateAccount(String paramString, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(4));
        ParamUtil.addParamCanBeNull(localSimpleMap, "adCode", InnerConstants.ADCODE);
        ParamUtil.addParamCanBeNull(localSimpleMap, "uId", USER_ID);
        ParamUtil.addParamCanBeNull(localSimpleMap, "uName", paramString);
        USER_NAME = paramString;
        ParamUtil.addParamCanBeNull(localSimpleMap, "level", paramInteger1);
        USER_LEVEL = paramInteger1;
        ParamUtil.addParamCanBeNull(localSimpleMap, "gender", paramInteger2);
        GENDER = paramInteger2;
        ParamUtil.addParamCanBeNull(localSimpleMap, "age", paramInteger3);
        AGE = paramInteger3;
        MsgSenderImpl.send(GameAdmin.CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }
}