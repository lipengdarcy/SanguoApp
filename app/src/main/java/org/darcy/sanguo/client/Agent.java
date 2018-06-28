package org.darcy.sanguo.client;

import android.content.Context;

public class Agent {

    public static void enableLog(Boolean paramBoolean1, Boolean paramBoolean2) {
        GameAdmin.setConsLog(paramBoolean1.booleanValue());
        GameAdmin.setFileLog(paramBoolean2.booleanValue());
    }

    public static void init(Context paramContext) {
        try {
            GameAdmin.init(paramContext);
            return;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + e.toString());
            e.printStackTrace();
        }
    }

    public static void onActive() {
        try {
            GameAdmin.onActive();
            GameAdmin.onSendPhoneInfo();
            return;
        } catch (Exception localException) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + localException.toString());
            localException.printStackTrace();
        }
    }

    public static void onBeginPlay() {
        try {
            GameAction.onStartGame();
            return;
        } catch (Exception localException) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + localException.toString());
            localException.printStackTrace();
        }
    }

    public static void onChargeCallBack(String paramString1, String paramString2, Integer paramInteger1, String paramString3, Float paramFloat, String paramString4, Integer paramInteger2) {
        try {
            Payment.onPayLog(paramString1, paramString2, paramInteger1, paramString3, paramFloat, paramString4, paramInteger2);
            return;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + e.toString());
            e.printStackTrace();
        }
    }

    public static void onCreateRole(String paramString) {
        try {
            Account.onChoiceGameRole(paramString);
            return;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + e.toString());
            e.printStackTrace();
        }
    }

    public static void onEndPlay() {
        try {
            Account.onLogout();
            return;
        } catch (Exception localException) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + localException.toString());
            localException.printStackTrace();
        }
    }

    public static void onEnterBackground() {
        try {
            Account.onSwichBacLogout();
            return;
        } catch (Exception localException) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + localException.toString());
            localException.printStackTrace();
        }
    }

    public static void onEnterChargePage(String paramString) {
        try {
            Payment.onPayPage(paramString);
            return;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + e.toString());
            e.printStackTrace();
        }
    }

    public static void onEnterLoginPage() {
        try {
            Account.onLoginPage();
            return;
        } catch (Exception localException) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + localException.toString());
            localException.printStackTrace();
        }
    }

    public static void onEnterRegisterPage() {
        try {
            Account.onRegPage();
            return;
        } catch (Exception localException) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + localException.toString());
            localException.printStackTrace();
        }
    }

    public static void onEvent(String paramString1, String paramString2) {
        try {
            GameAdmin.onCostomEvent(paramString1, paramString2);
            return;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + e.toString());
            e.printStackTrace();
        }
    }

    public static void onFinishLoaded(Long paramLong) {
        try {
            GameAdmin.onLoaded(paramLong);
            return;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + e.toString());
            e.printStackTrace();
        }
    }

    public static void onLoginSuccess(String paramString) {
        try {
            Account.onLoginOk(paramString);
            return;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + e.toString());
            e.printStackTrace();
        }
    }

    public static void onMissionCallBack(String paramString, Integer paramInteger) {
        try {
            Mission.onGameMission(paramString, paramInteger);
            return;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + e.toString());
            e.printStackTrace();
        }
    }

    public static void onPurchase(String paramString, Integer paramInteger, Double paramDouble) {
        try {
            Consume.onPurchase(paramString, paramInteger, Double.valueOf(paramInteger.intValue() * paramDouble.doubleValue()));
            return;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + e.toString());
            e.printStackTrace();
        }
    }

    public static void onReward(Integer paramInteger, String paramString) {
        try {
            Consume.onReward(paramInteger, paramString);
            return;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + e.toString());
            e.printStackTrace();
        }
    }

    public static void onSaved(String paramString1, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString2) {
        try {
            setGameServer(paramString2);
            Account.updateAccount(paramString1, paramInteger1, paramInteger2, paramInteger3);
            return;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + e.toString());
            e.printStackTrace();
        }
    }

    public static void onSelectRole(String paramString) {
        try {
            Account.onChoiceGameRole(paramString);
            return;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + e.toString());
            e.printStackTrace();
        }
    }

    public static void onSwitchUser() {
        try {
            Account.onSwitchLogout();
            return;
        } catch (Exception localException) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + localException.toString());
            localException.printStackTrace();
        }
    }

    public static void onUse(String paramString, Integer paramInteger, Double paramDouble) {
        try {
            Consume.onUse(paramString, paramInteger, Double.valueOf(paramInteger.intValue() * paramDouble.doubleValue()));
            return;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + e.toString());
            e.printStackTrace();
        }
    }

    public static void setAge(Integer paramInteger) {
        Account.AGE = paramInteger;
        try {
            Account.updateAccount(Account.USER_NAME, Account.USER_LEVEL, Account.GENDER, Account.AGE);
            return;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + e.toString());
            e.printStackTrace();
        }
    }

    public static void setGameServer(String paramString) {
        Account.GAME_SERVER = paramString;
    }

    public static void setGender(Integer paramInteger) {
        Account.GENDER = paramInteger;
        try {
            Account.updateAccount(Account.USER_NAME, Account.USER_LEVEL, Account.GENDER, Account.AGE);
            return;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + e.toString());
            e.printStackTrace();
        }
    }

    public static void setLevel(Integer paramInteger) {
        Account.USER_LEVEL = paramInteger;
        try {
            Account.updateAccount(Account.USER_NAME, Account.USER_LEVEL, Account.GENDER, Account.AGE);
            return;
        } catch (Exception e) {
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + e.toString());
            e.printStackTrace();
        }
    }

    //用户名更改
    public static void setUName(String newName) {
        try {
            Account.USER_NAME = newName;
            Account.updateAccount(Account.USER_NAME, Account.USER_LEVEL, Account.GENDER, Account.AGE);
        } catch (Exception e) {
            String errorInfo = e.toString();
            if (newName.length() > 32) {
                errorInfo = "uName is length over 32";
            }
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), "CjGaAgent:" + e.toString());
            e.printStackTrace();
        }
    }
}