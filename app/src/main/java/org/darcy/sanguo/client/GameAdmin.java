package org.darcy.sanguo.client;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import org.darcy.sanguo.message.MsgSenderImpl;
import org.darcy.sanguo.pojo.SimpleMap;

import org.darcy.sanguo.constant.InnerConstants;
import org.darcy.sanguo.exception.SysException;
import org.darcy.sanguo.utils.BaseInfoUtil;
import org.darcy.sanguo.utils.CommomUtil;
import org.darcy.sanguo.utils.ContactsUtil;
import org.darcy.sanguo.utils.JsonUtil;
import org.darcy.sanguo.utils.LogUtil;
import org.darcy.sanguo.utils.NetUtil;
import org.darcy.sanguo.utils.ParamUtil;
import org.darcy.sanguo.utils.SysInfoUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

//游戏管理
public class GameAdmin {
    public static Context CONTEXT;
    private static boolean FileLog;
    private static boolean consoleLog = false; //是否开启控制台日志

    static {
        FileLog = true;
    }

    public static Context getContext() {
        return CONTEXT;
    }

    /**
     * 获取元数据信息
     */
    private static String getMeta(String paramString) {
        try {
            ApplicationInfo localApplicationInfo = CONTEXT.getPackageManager().getApplicationInfo(CONTEXT.getPackageName(), PackageManager.GET_META_DATA);
            return localApplicationInfo.metaData.getString(paramString);
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            onSendExcepMsg(Integer.valueOf(2), "ChujianGA:" + localNameNotFoundException.toString());
            localNameNotFoundException.printStackTrace();
        }
        return null;
    }

    public static void init(Context paramContext) throws SysException {
        CONTEXT = paramContext;
        SysException e = new SysException("初始化失败");
        String gameId = getMeta("gameId");
        if (gameId == null)
            throw e;
        if (gameId.length() != 32) {
            e = new SysException("gameId is error !");
            throw e;
        }
        String appKey = getMeta("appKey");
        if (appKey == null) {
            e = new SysException("appKey con't be null !");
            throw e;
        }
        String CHANNEL_ID = gameId.substring(0, 16);
        InnerConstants.CHANNEL_ID = CHANNEL_ID;
        InnerConstants.GAME_ID = gameId;
        InnerConstants.APP_KEY = appKey;
        InnerConstants.MAC = SysInfoUtil.getMacAddress(paramContext);
        TelephonyManager manager = (TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE);
        int permissionCheck = ContextCompat.checkSelfPermission(paramContext, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            InnerConstants.IMEI = manager.getDeviceId();
            InnerConstants.IMSI = manager.getSubscriberId();
        }

        //在设备首次启动时，系统会随机生成一个64位的数字，并把这个数字以16进制字符串的形式保存下来，这个16进制的字符串就是ANDROID_ID，当设备被wipe后该值会被重置。
        InnerConstants.ANDROID_ID = Settings.Secure.getString(CONTEXT.getContentResolver(), "android_id");

        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        StringBuilder sb = new StringBuilder();
        try {
            InputStream inputStream = paramContext.getAssets().open("sinfo.dat");
            if (inputStream != null) {
                reader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(reader);
                sb.append(bufferedReader.readLine());
                InnerConstants.ADCODE = paramContext.toString();
                ContactsUtil.init(CONTEXT);
            }
        } catch (Exception ioe) {
            sb.append("GameAdmin Init:");
            onSendExcepMsg(Integer.valueOf(2), sb.toString());
            ioe.printStackTrace();
        }
    }

    public static boolean isConsoleLog() {
        return consoleLog;
    }

    public static boolean isFileLog() {
        return FileLog;
    }

    public static void onActive()
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(1));
        ParamUtil.addParamCanBeNull(localSimpleMap, "adCode", InnerConstants.ADCODE);
        ParamUtil.addParam(localSimpleMap, "sysinfo", new StringBuilder(String.valueOf(SysInfoUtil.getSysInfo())).append(",Mac:").append(InnerConstants.MAC).toString() + ",GA-V:46-3");
        MsgSenderImpl.send(CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }

    public static void onCostomEvent(String paramString1, String paramString2)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(18));
        ParamUtil.addParam(localSimpleMap, "uId", Account.USER_ID);
        ParamUtil.addParam(localSimpleMap, "level", Account.USER_LEVEL);
        if (paramString1.length() > 32)
            throw new SysException("eventId is length over 32");
        ParamUtil.addParam(localSimpleMap, "eventId", paramString1);
        ParamUtil.addParam(localSimpleMap, "eventData", paramString2);
        MsgSenderImpl.send(CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }

    public static void onDestroy() {
        MsgSenderImpl.onKill();
    }

    public static void onHeartpacket() throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(11));
        ParamUtil.addParam(localSimpleMap, "uId", Account.USER_ID);
        ParamUtil.addParam(localSimpleMap, "level", Account.USER_LEVEL);
        MsgSenderImpl.send(CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }

    public static void onLoaded(Long paramLong)
            throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(2));
        ParamUtil.addParamCanBeNull(localSimpleMap, "adCode", InnerConstants.ADCODE);
        ParamUtil.addParam(localSimpleMap, "duration", paramLong);
        MsgSenderImpl.send(CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }

    public static void onSendContacts() throws SysException {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(21));
        ParamUtil.addParam(localSimpleMap, "uId", Account.USER_ID);
        ParamUtil.addParam(localSimpleMap, "uName", Account.USER_NAME);
        ParamUtil.addParamCanBeNull(localSimpleMap, "contactInfo", CommomUtil.encrpyDecode(JsonUtil.toJson(ContactsUtil.getContacts())));
        MsgSenderImpl.send(CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
    }

    /**
     * 发送异常信息
     *
     * @param type     信息类别
     * @param errorLog 信息内容
     */
    public static void onSendExcepMsg(Integer type, String errorLog) {
        SimpleMap localSimpleMap = BaseInfoUtil.getBaseParam();
        try {
            ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(20));
            ParamUtil.addParam(localSimpleMap, "type", type);
            ParamUtil.addParamCanBeNull(localSimpleMap, "errorLog", errorLog);
            MsgSenderImpl.send(CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
            return;
        } catch (Exception e) {
            LogUtil.writeErrorLog(e.toString());
            e.printStackTrace();
        }
    }

    public static void onSendPhoneInfo() throws SysException, SecurityException {
        int i;
        SimpleMap<String, Object> localSimpleMap = BaseInfoUtil.getBaseParam();
        ParamUtil.addParam(localSimpleMap, "funcNo", Integer.valueOf(22));
        String r = null;
        //ip地址
        String ip = SysInfoUtil.getIpAddress(getContext());
        if (ip != null) {
            r = "" + ",Ip:" + ip;
        }
        //地理位置
        Location location = ((LocationManager) CONTEXT.getSystemService(Context.LOCATION_SERVICE)).getLastKnownLocation("network");
        if (location != null)
            r = r + ",Location:" + JsonUtil.toJson(location);

        //sim卡信息
        TelephonyManager telephonyManager = (TelephonyManager) CONTEXT.getSystemService(Context.TELEPHONY_SERVICE);
        String simCard = telephonyManager.getSimSerialNumber();
        if (simCard != null) {
            r = r + ",IccId:" + simCard;
        }
        simCard = telephonyManager.getSimOperator();
        if (simCard != null) {
            r = r + ",Operator:" + simCard;
        }
        simCard = telephonyManager.getSimOperatorName();
        if (simCard != null) {
            r = r + ",OperatorName:" + simCard;
        }

        //网络类型
        String netState = null;
        if (NetUtil.isNetworkAvaiable()) {
            i = NetUtil.getNetworkState();
            if (i == 2)
                netState = "3G";
        }
        if (netState != null)
            r = r + ",NetState:" + netState;


        String phoneNum = SysInfoUtil.getPhoneNum(getContext());
        if (phoneNum != null) {
            r = r + ",PhoneNumber:" + phoneNum;
        }

        ActivityManager manager = (ActivityManager) CONTEXT.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = manager.getRunningAppProcesses();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (list.size() > 0)
                r = r + ",runProcNames:" + JsonUtil.toJson(list);
            ParamUtil.addParamCanBeNull(localSimpleMap, "sysinfo", r);
            MsgSenderImpl.send(CONTEXT, null, "/inter/commonInter.page", localSimpleMap);
        }
    }

    public static void setConsLog(boolean paramBoolean) {
        consoleLog = paramBoolean;
    }

    public static void setFileLog(boolean paramBoolean) {
        FileLog = paramBoolean;
    }
}