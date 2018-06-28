package org.darcy.sanguo.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import org.darcy.sanguo.sdk.view.CjNotification;
import org.darcy.sanguo.utils.NetUtil;
import org.darcy.sanguo.constant.InnerConstants;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;

/**
 * 系统推送服务
 */
public class SysPushService extends Service implements MqttCallback {

    private static final String ACTION_KEEPALIVE = "MqMsgListenerService.KEEPALIVE";
    protected static final String ACTION_RECIVE_MSG = "MqMsgListenerService.RECIVE_MSG";
    private static final String ACTION_RECONNECT = "MqMsgListenerService.RECONNECT";
    private static final String ACTION_START = "MqMsgListenerService.START";
    private static final String ACTION_STOP = "MqMsgListenerService.STOP";
    public static String COMMON_TOPIC;
    public static final String DEBUG_TAG = "MqMsgListenerService";
    private static final String DEVICE_ID_FORMAT = "cj_%s";
    private static final String MQTT_BROKER = "m.chujian.com";
    private static final boolean MQTT_CLEAN_SESSION = true;
    private static final int MQTT_KEEP_ALIVE = 240000;
    private static final byte[] MQTT_KEEP_ALIVE_MESSAGE = new byte[1];
    private static final int MQTT_KEEP_ALIVE_QOS = 0;
    private static final String MQTT_KEEP_ALIVE_TOPIC_FORAMT = "/users/%s/keepalive";
    private static final int MQTT_PORT = 1883;
    public static final int MQTT_QOS_0 = 0;
    public static final int MQTT_QOS_1 = 1;
    public static final int MQTT_QOS_2 = 2;
    private static final String MQTT_THREAD_NAME = "MqMsgListenerService[MqMsgListenerService]";
    private static final String MQTT_URL_FORMAT = "tcp://%s:%d";
    public static InputStream largeIcon;
    private static boolean mStarted = false;
    private static MqttClient mqttClient;
    protected static CjNotification notifyBox;
    public static InputStream style;
    private AlarmManager mAlarmManager;
    private Handler mConnHandler;
    private MqttDefaultFilePersistence mDataStore;
    private String mDeviceId;
    private MqttTopic mKeepAliveTopic;
    private MemoryPersistence mMemStore;
    private MqttConnectOptions mqttConnectOptions;
    private int reConnecTime = 0;

    static {
        COMMON_TOPIC = "common_Sms";
        largeIcon = null;
        style = null;
    }

    class task implements Runnable {
        List list;

        public task(SysPushService father, List list) {

        }

        public void run() {
            try {
                //SysPushService.task.connect(SysPushService.task(this.this$0));
                //SysPushService.access$0().subscribe((String[]) this.val$topicArray.toArray(this.val$topics));
                //SysPushService.access$0().setCallback(this.this$0);
                //SysPushService.mStarted(true);
                Log.i("MqMsgListenerService", "Successfully connected and subscribed starting keep alives");
                //SysPushService.access$5(this.this$0);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void connect() {
        String url = null; //推送的后台地址
        MqttClient client = null;
        try {
            Log.d("DEBUG_TAG", "connect");
            url = String.format(Locale.US, "tcp://%s:%d", new Object[]{"m.chujian.com", Integer.valueOf(1883)});
            Log.i("MqMsgListenerService", url);

            if (this.mDataStore != null) {
                Log.i("MqMsgListenerService", "Connecting with DataStore");
                client = new MqttClient(url, this.mDeviceId, this.mDataStore);
                mqttClient = client;
                List list = new ArrayList();
                list.add(COMMON_TOPIC);
                if ((InnerConstants.CHANNEL_ID != null) && (!("".equals(InnerConstants.CHANNEL_ID))))
                    list.add(InnerConstants.CHANNEL_ID);
                if ((InnerConstants.GAME_ID != null) && (!("".equals(InnerConstants.GAME_ID))))
                    list.add(InnerConstants.GAME_ID);
                list.add(String.valueOf(InnerConstants.MAC));
                list.add(InnerConstants.IMEI);
                list.add(Settings.Secure.getString(getContentResolver(), "android_id"));
                Runnable runnable = new SysPushService.task(this, list);
                this.mConnHandler.post(runnable);

                return;
            }
            Log.i("MqMsgListenerService", "Connecting with MemStore");
            client = new MqttClient(url, this.mDeviceId, this.mMemStore);
            mqttClient = (MqttClient) client;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean hasScheduledKeepAlives() {
        Log.d("DEBUG_TAG", "hasScheduledKeepAlives");
        Intent intent = new Intent();
        intent.setClass(this, SysPushService.class);
        intent.setAction("MqMsgListenerService.KEEPALIVE");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        if (pendingIntent != null)
            return true;
        return false;
    }

    public static boolean isConnected() {
        if (mqttClient != null && mStarted && mqttClient.isConnected())
            return true;
        return false;
    }


    private boolean isNetworkAvailable() {
        Log.d("DEBUG_TAG", "isNetworkAvailable");
        return NetUtil.isNetworkAvaiable();
    }

    // ERROR //
    private void keepAlive() {

    }

    public static void keepalive(Context paramContext) {
        Log.d("DEBUG_TAG", "keepalive");
        Intent localIntent = new Intent(paramContext, SysPushService.class);
        localIntent.setAction("MqMsgListenerService.KEEPALIVE");
        paramContext.startService(localIntent);
    }

    private void reconnectIfNecessary() {
        try {
            Log.d("DEBUG_TAG", "reconnectIfNecessary");
            Thread.sleep(1000L);
            connect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private MqttDeliveryToken sendKeepAlive() throws MqttPersistenceException, MqttException {

        Log.d("DEBUG_TAG", "sendKeepAlive");
        if (isConnected())
            throw new MqttException(1);

        if (this.mKeepAliveTopic == null)
            this.mKeepAliveTopic = mqttClient.getTopic(String.format(Locale.US, "/users/%s/keepalive", new Object[]{this.mDeviceId}));
        Log.i("MqMsgListenerService", "Sending Keepalive to m.chujian.com");
        MqttMessage mqttMessage = new MqttMessage(MQTT_KEEP_ALIVE_MESSAGE);
        mqttMessage.setQos(0);
        return this.mKeepAliveTopic.publish(mqttMessage);
    }

    private void start() {
        Log.d("DEBUG_TAG", "start");
        if (mStarted) {
            Log.i("MqMsgListenerService", "Attempt to start while already started");
            return;
        }
        if (hasScheduledKeepAlives())
            stopKeepAlives();
    }

    public static void start(Context paramContext) {
        Log.d("DEBUG_TAG", "start");
        Intent localIntent = new Intent(paramContext, SysPushService.class);
        localIntent.setAction("MqMsgListenerService.START");
        paramContext.startService(localIntent);
    }

    private void startKeepAlives() {
        Log.d("DEBUG_TAG", "startKeepAlives");
        Object localObject = new Intent();
        ((Intent) localObject).setClass(this, SysPushService.class);
        ((Intent) localObject).setAction("MqMsgListenerService.KEEPALIVE");
        localObject = PendingIntent.getService(this, 0, (Intent) localObject, 0);
        this.mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 240000L, 240000L, (PendingIntent) localObject);
    }

    private void stop() {

        Handler localHandler;
        Log.d("DEBUG_TAG", "stop");
        if (!mStarted)
            Log.i("MqMsgListenerService", "Attempt to stop connection that isn't running");


    }

    public static void stop(Context paramContext) {
        Log.d("DEBUG_TAG", "stop");
        Intent localIntent = new Intent(paramContext, SysPushService.class);
        localIntent.setAction("MqMsgListenerService.STOP");
        paramContext.startService(localIntent);
    }

    private void stopKeepAlives() {
        Log.d("DEBUG_TAG", "stopKeepAlives");
        Object localObject = new Intent();
        ((Intent) localObject).setClass(this, SysPushService.class);
        ((Intent) localObject).setAction("MqMsgListenerService.KEEPALIVE");
        localObject = PendingIntent.getService(this, 0, (Intent) localObject, 0);
        this.mAlarmManager.cancel((PendingIntent) localObject);
    }

    public void connectionLost(Throwable paramThrowable) {
        Log.d("DEBUG_TAG", "connectionLost");
        stopKeepAlives();
        mqttClient = null;
        mStarted = false;
        if (!(isNetworkAvailable()))
            return;
        reconnectIfNecessary();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    public void deliveryComplete(MqttDeliveryToken paramMqttDeliveryToken) {
    }

    public void messageArrived(MqttTopic paramMqttTopic, MqttMessage paramMqttMessage) throws
            Exception {
        Log.d("DEBUG_TAG", "messageArrived");
        String str = new String(paramMqttMessage.getPayload());
        CjNotification notifyBox = new CjNotification(getApplicationContext(), str);
        notifyBox.show();
    }

    public IBinder onBind(Intent paramIntent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        Log.d("DEBUG_TAG", "onCreate");
        this.mDeviceId = String.format("cj_%s", new Object[]{Settings.Secure.getString(getContentResolver(), "android_id")});
        Object localObject = new HandlerThread("MqMsgListenerService[MqMsgListenerService]");
        ((HandlerThread) localObject).start();
        this.mConnHandler = new Handler(((HandlerThread) localObject).getLooper());
        try {
            localObject = new MqttDefaultFilePersistence(getCacheDir().getAbsolutePath());
            this.mDataStore = ((MqttDefaultFilePersistence) localObject);
            this.mqttConnectOptions = new MqttConnectOptions();
            this.mqttConnectOptions.setCleanSession(true);
            this.mAlarmManager = ((AlarmManager) getSystemService(Context.ALARM_SERVICE));
            return;
        } catch (Exception e) {
            e.printStackTrace();
            this.mDataStore = null;
            this.mMemStore = new MemoryPersistence();
        }
    }

    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
        super.onStartCommand(paramIntent, paramInt1, paramInt2);
        String str = paramIntent.getAction();
        Log.i("MqMsgListenerService", "Received action of " + str);
        if (str == null)
            Log.i("MqMsgListenerService", "Starting service with no action\n Probably from a crash");
        if (str.equals("MqMsgListenerService.START")) {
            Log.i("MqMsgListenerService", "Received ACTION_START");
            start();
        }
        if (str.equals("MqMsgListenerService.STOP"))
            stop();
        if (str.equals("MqMsgListenerService.KEEPALIVE"))
            keepAlive();
        if (str.equals("MqMsgListenerService.RECONNECT") || !(isNetworkAvailable()))
            reconnectIfNecessary();
        return Service.START_REDELIVER_INTENT;
    }
}