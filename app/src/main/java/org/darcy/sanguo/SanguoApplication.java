package org.darcy.sanguo;

import android.app.Application;
import org.darcy.sanguo.utils.SysUtil;
import org.darcy.sanguo.client.Agent;
import org.darcy.sanguo.exception.SysExceptionHandler;
import org.darcy.sanguo.service.SysPushService;

/**
 * app入口
 * MQTT（Message Queuing Telemetry Transport，消息队列遥测传输）是IBM开发的一个即时通讯协议，有可能成为物联网的重要组成部分
 */
public class SanguoApplication extends Application {
    private static SanguoApplication App;
    private int m_age = 0;
    private String m_gameServer = "";
    private String m_gameServerName = "";
    private int m_gender = 0;
    private int m_level = 0;
    private String m_uId = "";
    private String m_uName = "";

    public static SanguoApplication getApp() {
        return App;
    }

    public void enableLog(boolean paramBoolean1, boolean paramBoolean2) {
        try {
            Agent.enableLog(Boolean.valueOf(paramBoolean1), Boolean.valueOf(paramBoolean2));
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public void onBeginPlay() {
        try {
            Agent.onBeginPlay();
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public void onChargeCallBack(String paramString1, String paramString2, int paramInt1, String paramString3, float paramFloat, String paramString4, int paramInt2) {
        try {
            Agent.onChargeCallBack(paramString1, paramString2, Integer.valueOf(paramInt1), paramString3, Float.valueOf(paramFloat), paramString4, Integer.valueOf(paramInt2));
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCreate() {
        App = this;
        SysUtil.init(this);
        try {
            Agent.init(this);
            SysExceptionHandler.getInstance().init();
            SysPushService.start(this);
        } catch (Exception localException2) {
            try {
                Agent.onActive();
                SysUtil.savePref("active", true);
                super.onCreate();
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onEndPlay() {
        try {
            Agent.onEndPlay();
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public void onEnterBackground() {
        try {
            Agent.onEnterBackground();
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public void onEnterChargePage(String paramString) {
        try {
            Agent.onEnterChargePage(paramString);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onEnterLoginPage() {
        try {
            Agent.onEnterLoginPage();
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public void onEnterRegisterPage() {
        try {
            Agent.onEnterRegisterPage();
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public void onFinishLoaded(long paramLong) {
        try {
            Agent.onFinishLoaded(Long.valueOf(paramLong));
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public void onLoginSuccess(String paramString) {
        try {
            Agent.onLoginSuccess(paramString);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onMissionCallBack(String paramString, int paramInt) {
        try {
            Agent.onMissionCallBack(paramString, Integer.valueOf(paramInt));
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPurchase(String paramString, int paramInt, double paramDouble) {
        try {
            Agent.onPurchase(paramString, Integer.valueOf(paramInt), Double.valueOf(paramDouble));
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSaved() {
        try {
            if (!(this.m_gameServer.isEmpty()))
                Integer.parseInt(this.m_gameServer);
            Agent.onSaved(this.m_uName, Integer.valueOf(this.m_level), Integer.valueOf(this.m_gender), Integer.valueOf(this.m_age), this.m_gameServer);
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public void onSelectRole(String paramString) {
        try {
            Agent.onSelectRole(paramString);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSetAcountID(String paramString) {
        try {
            this.m_uId = paramString;
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSwitchUser() {
        try {
            Agent.onSwitchUser();
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public void onTerminate() {
        super.onTerminate();
    }

    public void onUse(String paramString, int paramInt, double paramDouble) {
        try {
            Agent.onUse(paramString, Integer.valueOf(paramInt), Double.valueOf(paramDouble));
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAge(int paramInt) {
        try {
            this.m_age = paramInt;
            Agent.setAge(Integer.valueOf(paramInt));
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public void setGameServer(String paramString) {
        try {
            this.m_gameServer = paramString;
            Agent.setGameServer(paramString);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGameServerName(String paramString) {
        try {
            this.m_gameServerName = paramString;
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGender(int paramInt) {
        try {
            this.m_gender = paramInt;
            Agent.setGender(Integer.valueOf(paramInt));
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public void setLevel(int paramInt) {
        try {
            this.m_level = paramInt;
            Agent.setLevel(Integer.valueOf(paramInt));
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public void setUName(String paramString) {
        try {
            this.m_uName = paramString;
            Agent.setUName(paramString);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}