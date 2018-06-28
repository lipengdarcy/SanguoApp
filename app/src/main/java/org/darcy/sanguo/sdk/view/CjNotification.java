package org.darcy.sanguo.sdk.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.view.WindowManager;

import org.darcy.sanguo.activity.LoginAct;
import org.darcy.sanguo.sdk.bean.NotifyBean;

import org.darcy.sanguo.client.GameAdmin;
import org.darcy.sanguo.utils.HttpsUtil;
import org.json.JSONObject;

import java.io.InputStream;

public class CjNotification {
    private Context context;
    private Intent intent;
    private Notification notification;
    private NotificationManager notificationManager;
    private int notification_id = 0;
    private String notifyInfo;
    private PendingIntent pendingIntent;

    public CjNotification(Context paramContext, String paramString) {
        this.context = paramContext;
        this.notifyInfo = paramString;
        initNotification();
    }

    //初始化通知提醒
    private void initNotification() {
        JSONObject json;
        NotifyBean localNotifyBean = new NotifyBean();
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            json = new JSONObject(this.notifyInfo);
            if (json.has("url"))
                localNotifyBean.url = json.getString("url");
            if (json.has("largeIcon"))
                localNotifyBean.largeIcon = json.getString("largeIcon");
            if (json.has("bigPictureStyle"))
                localNotifyBean.bigPictureStyle = json.getString("bigPictureStyle");
            if (json.has("title"))
                localNotifyBean.title = json.getString("title");
            if (json.has("text"))
                localNotifyBean.text = json.getString("text");
            if (json.has("ticker"))
                localNotifyBean.ticker = json.getString("ticker");

            this.notificationManager = ((NotificationManager) this.context.getSystemService(context.NOTIFICATION_SERVICE));
            NotificationCompat.BigPictureStyle localBigPictureStyle = new NotificationCompat.BigPictureStyle();
            if ((localNotifyBean.url == null) || (!("".equals(localNotifyBean.url)))) {
                this.intent = new Intent(this.context, LoginAct.class);
                this.pendingIntent = PendingIntent.getActivity(this.context, 1, this.intent, 0);
                if ((localNotifyBean.largeIcon == null) || ("".equals(localNotifyBean.largeIcon))) {
                    return;
                }
                inputStream = HttpsUtil.getInputStream(localNotifyBean.largeIcon);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;
                options.outHeight = 50;
                options.outWidth = 50;
                bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                localBigPictureStyle.bigLargeIcon(bitmap);
                this.notification = new NotificationCompat.Builder(this.context).setDefaults(-1).setAutoCancel(false).setSmallIcon(2130837591).setTicker(localNotifyBean.ticker).setLargeIcon(bitmap).setContentIntent(this.pendingIntent).setContentTitle(localNotifyBean.title).setContentText(localNotifyBean.text).setStyle(localBigPictureStyle).setWhen(System.currentTimeMillis()).build();
                inputStream.close();
            }


            Object localObject4 = null;
            localBigPictureStyle = new NotificationCompat.BigPictureStyle();
            WindowManager localWindowManager = (WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE);
            if (localNotifyBean.bigPictureStyle != null) {
                if (!("".equals(localNotifyBean.bigPictureStyle))) {
                    inputStream = HttpsUtil.getInputStream(localNotifyBean.bigPictureStyle);
                    BitmapFactory.Options localOptions = new BitmapFactory.Options();
                    localOptions.inJustDecodeBounds = false;
                    localOptions.outHeight = 80;
                    localOptions.outWidth = localWindowManager.getDefaultDisplay().getWidth();
                    bitmap = BitmapFactory.decodeStream(inputStream, null, localOptions);
                    localBigPictureStyle.bigPicture(bitmap);
                    inputStream.close();
                }
            }
            this.notification.flags = 145;
            this.intent = new Intent();
            this.intent.setAction("android.intent.action.VIEW");
            this.intent.setData(Uri.parse(localNotifyBean.url));


            bitmap = BitmapFactory.decodeResource(this.context.getResources(), 2130837591);
            this.notification = new NotificationCompat.Builder(this.context).setDefaults(-1).setAutoCancel(false).setSmallIcon(2130837591).setTicker(localNotifyBean.ticker).setLargeIcon(bitmap).setContentIntent(this.pendingIntent).setContentTitle(localNotifyBean.title).setContentText(localNotifyBean.text).setWhen(System.currentTimeMillis()).build();

        } catch (Exception e) {
            e.printStackTrace();
            GameAdmin.onSendExcepMsg(Integer.valueOf(2), super.getClass().getName() + ":" + e.toString());
            e.printStackTrace();
        }
    }

    public void show() {
        this.notification_id = (int) Math.round(Math.random() * 1000.0D);
        this.notificationManager.notify(this.notification_id, this.notification);
    }
}