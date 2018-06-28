package org.darcy.sanguo.client;

import android.os.Handler;

import org.darcy.sanguo.message.MsgSenderImpl;
import org.darcy.sanguo.pojo.SimpleMap;

import org.darcy.sanguo.constant.InnerConstants;
import org.darcy.sanguo.exception.SysException;
import org.darcy.sanguo.utils.BaseInfoUtil;
import org.darcy.sanguo.utils.ParamUtil;

//支付相关
public class Payment {
    public static void onPayLog(String paramString1, String paramString2, Integer paramInteger1, String paramString3, Float paramFloat, String paramString4, Integer paramInteger2)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(14));
        ParamUtil.addParamCanBeNull(localSimpleMap, "adCode", InnerConstants.ADCODE);
        ParamUtil.addParam(localSimpleMap, "uId", Account.USER_ID);
        ParamUtil.addParam(localSimpleMap, "level", Account.USER_LEVEL);
        ParamUtil.addParam(localSimpleMap, "orderId", paramString1);
        ParamUtil.addParam(localSimpleMap, "iapId", paramString2);
        ParamUtil.addParam(localSimpleMap, "currencyAmount", paramInteger1);
        if (paramString3.length() > 50)
            throw new SysException("currentcyType is length over 50");
        ParamUtil.addParam(localSimpleMap, "currencyType", paramString3);
        ParamUtil.addParam(localSimpleMap, "vcAmount", paramFloat);
        if (paramString4.length() > 50)
            throw new SysException("paymentType is length over 50");
        ParamUtil.addParam(localSimpleMap, "paymentType", paramString4);
        ParamUtil.addParam(localSimpleMap, "state", paramInteger2);
        ParamUtil.addParam(localSimpleMap, "gameServer", Account.GAME_SERVER);
        MsgSenderImpl.send(GameAdmin.CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }

    public static void onPayPage(String paramString)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", "13");
        ParamUtil.addParam(localSimpleMap, "uId", Account.USER_ID);
        ParamUtil.addParamCanBeNull(localSimpleMap, "level", Account.USER_LEVEL);
        ParamUtil.addParamCanBeNull(localSimpleMap, "sourceFrom", paramString);
        MsgSenderImpl.send(GameAdmin.CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }

    public static void onPaymentAlipay(Handler paramHandler, String paramString1, Double paramDouble, String paramString2, String paramString3, Integer paramInteger, String paramString4, String paramString5)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(1));
        ParamUtil.addParamCanBeNull(localSimpleMap, "adCode", InnerConstants.ADCODE);
        ParamUtil.addParam(localSimpleMap, "uId", paramString1);
        ParamUtil.addParam(localSimpleMap, "amount", paramDouble);
        ParamUtil.addParam(localSimpleMap, "itemname", paramString2);
        ParamUtil.addParam(localSimpleMap, "itemid", paramString3);
        ParamUtil.addParam(localSimpleMap, "itemcount", paramInteger);
        ParamUtil.addParam(localSimpleMap, "itemno", paramString4);
        ParamUtil.addParam(localSimpleMap, "notifyurl", paramString5);
        MsgSenderImpl.send(GameAdmin.CONTEXT, paramHandler, "/inter/paymentAlipay.page", localSimpleMap);
    }

    public static void onPaymentMo9(Handler paramHandler, String paramString1, Double paramDouble, String paramString2, String paramString3, Integer paramInteger, String paramString4, String paramString5)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(1));
        ParamUtil.addParamCanBeNull(localSimpleMap, "adCode", InnerConstants.ADCODE);
        ParamUtil.addParam(localSimpleMap, "uId", paramString1);
        ParamUtil.addParam(localSimpleMap, "amount", paramDouble);
        ParamUtil.addParam(localSimpleMap, "itemname", paramString2);
        ParamUtil.addParam(localSimpleMap, "itemid", paramString3);
        ParamUtil.addParam(localSimpleMap, "itemcount", paramInteger);
        ParamUtil.addParam(localSimpleMap, "itemno", paramString4);
        ParamUtil.addParam(localSimpleMap, "notifyurl", paramString5);
        MsgSenderImpl.send(GameAdmin.CONTEXT, paramHandler, "/inter/paymentMo9.page", localSimpleMap);
    }

    public static void onPaymentYibaoBack(Handler paramHandler, String paramString1, Double paramDouble, String paramString2, String paramString3, Integer paramInteger, String paramString4, String paramString5, String paramString6, String paramString7)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(2));
        ParamUtil.addParamCanBeNull(localSimpleMap, "adCode", InnerConstants.ADCODE);
        ParamUtil.addParam(localSimpleMap, "uId", paramString1);
        ParamUtil.addParamCanBeNull(localSimpleMap, "level", Account.USER_LEVEL);
        ParamUtil.addParam(localSimpleMap, "amount", paramDouble);
        ParamUtil.addParam(localSimpleMap, "itemname", paramString2);
        ParamUtil.addParam(localSimpleMap, "itemid", paramString3);
        ParamUtil.addParam(localSimpleMap, "itemcount", paramInteger);
        ParamUtil.addParam(localSimpleMap, "itemno", paramString4);
        ParamUtil.addParam(localSimpleMap, "userUA", paramString5);
        ParamUtil.addParam(localSimpleMap, "itemno", paramString4);
        ParamUtil.addParam(localSimpleMap, "terminalID", paramString6);
        ParamUtil.addParam(localSimpleMap, "notifyurl", paramString7);
        MsgSenderImpl.send(GameAdmin.CONTEXT, paramHandler, "/inter/paymentYeepay.page", localSimpleMap);
    }

    public static void onPaymentYibaoNoBack(Handler paramHandler, String paramString1, Double paramDouble1, String paramString2, String paramString3, Integer paramInteger, String paramString4, String paramString5, String paramString6, Double paramDouble2, String paramString7, String paramString8)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(1));
        ParamUtil.addParamCanBeNull(localSimpleMap, "adCode", InnerConstants.ADCODE);
        ParamUtil.addParam(localSimpleMap, "uId", paramString1);
        ParamUtil.addParam(localSimpleMap, "amount", paramDouble1);
        ParamUtil.addParam(localSimpleMap, "itemno", paramString4);
        ParamUtil.addParam(localSimpleMap, "itemid", paramString3);
        ParamUtil.addParam(localSimpleMap, "itemname", paramString2);
        ParamUtil.addParam(localSimpleMap, "itemcount", paramInteger);
        ParamUtil.addParam(localSimpleMap, "cardno", paramString5);
        ParamUtil.addParam(localSimpleMap, "cardpwd", paramString6);
        ParamUtil.addParam(localSimpleMap, "cardamt", paramDouble2);
        ParamUtil.addParam(localSimpleMap, "cardtype", paramString7);
        ParamUtil.addParam(localSimpleMap, "notifyurl", paramString8);
        MsgSenderImpl.send(GameAdmin.CONTEXT, paramHandler, "/inter/paymentYeepay.page", localSimpleMap);
    }
}