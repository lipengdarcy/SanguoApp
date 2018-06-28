package org.darcy.sanguo.client;

import org.darcy.sanguo.message.MsgSenderImpl;
import org.darcy.sanguo.pojo.SimpleMap;
import org.darcy.sanguo.exception.SysException;
import org.darcy.sanguo.utils.BaseInfoUtil;
import org.darcy.sanguo.utils.ParamUtil;

//游戏任务
public class Mission {

    public static void onGameMission(String missionId, Integer state) throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(17));
        ParamUtil.addParam(localSimpleMap, "uId", Account.USER_ID);
        ParamUtil.addParamCanBeNull(localSimpleMap, "level", Account.USER_LEVEL);
        if (missionId.length() > 32)
            throw new SysException("missionId is length over 32");
        ParamUtil.addParam(localSimpleMap, "missionId", missionId);
        ParamUtil.addParam(localSimpleMap, "state", state);
        ParamUtil.addParam(localSimpleMap, "gameServer", Account.GAME_SERVER);
        MsgSenderImpl.send(GameAdmin.CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }
}