package org.darcy.sanguo.client;

import org.darcy.sanguo.message.MsgSenderImpl;
import org.darcy.sanguo.pojo.SimpleMap;

import org.darcy.sanguo.exception.SysException;
import org.darcy.sanguo.utils.BaseInfoUtil;
import org.darcy.sanguo.utils.ParamUtil;

public class GameAction {

    public static void onStartGame() throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(7));
        ParamUtil.addParam(localSimpleMap, "uId", Account.USER_ID);
        ParamUtil.addParam(localSimpleMap, "gameServer", Account.GAME_SERVER);
        ParamUtil.addParamCanBeNull(localSimpleMap, "level", Account.USER_LEVEL);
        MsgSenderImpl.send(GameAdmin.CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }
}