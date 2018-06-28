package org.darcy.sanguo.client;

import org.darcy.sanguo.message.MsgSenderImpl;
import org.darcy.sanguo.pojo.SimpleMap;

import org.darcy.sanguo.exception.SysException;
import org.darcy.sanguo.utils.BaseInfoUtil;
import org.darcy.sanguo.utils.ParamUtil;

//消费购买
public class Consume {
    
    public static void onPurchase(String paramString, Integer paramInteger, Double paramDouble) throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(15));
        ParamUtil.addParam(localSimpleMap, "uId", Account.USER_ID);
        ParamUtil.addParam(localSimpleMap, "level", Account.USER_LEVEL);
        ParamUtil.addParam(localSimpleMap, "item", paramString);
        ParamUtil.addParam(localSimpleMap, "itemNumber", paramInteger);
        ParamUtil.addParam(localSimpleMap, "gameServer", Account.GAME_SERVER);
        ParamUtil.addParam(localSimpleMap, "totalPrice", paramDouble);
        MsgSenderImpl.send(GameAdmin.CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }

    public static void onReward(Integer paramInteger, String paramString)
            throws Exception {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(19));
        ParamUtil.addParam(localSimpleMap, "uId", Account.USER_ID);
        ParamUtil.addParam(localSimpleMap, "level", Account.USER_LEVEL);
        ParamUtil.addParam(localSimpleMap, "gameServer", Account.GAME_SERVER);
        ParamUtil.addParam(localSimpleMap, "vcAmount", paramInteger);
        if (paramString.length() > 32)
            throw new SysException("reason is length over 32");
        ParamUtil.addParam(localSimpleMap, "reason", paramString);
        MsgSenderImpl.send(GameAdmin.CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }

    public static void onUse(String paramString, Integer paramInteger, Double paramDouble)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(16));
        ParamUtil.addParam(localSimpleMap, "uId", Account.USER_ID);
        ParamUtil.addParam(localSimpleMap, "level", Account.USER_LEVEL);
        if (paramString.length() > 32)
            throw new SysException("item is length over 32");
        ParamUtil.addParam(localSimpleMap, "item", paramString);
        ParamUtil.addParam(localSimpleMap, "itemNumber", paramInteger);
        ParamUtil.addParam(localSimpleMap, "gameServer", Account.GAME_SERVER);
        ParamUtil.addParam(localSimpleMap, "totalPrice", paramDouble);
        MsgSenderImpl.send(GameAdmin.CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }
}