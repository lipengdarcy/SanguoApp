package org.darcy.sanguo.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.List;

//分享app到微信、微博等
public class ShareUtil {
    private static List<PackageInfo> getInstallPacakages(Context paramContext) {
        return paramContext.getPackageManager().getInstalledPackages(0);
    }

    public static void shareMsgIntent(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4) {
        Intent localIntent = new Intent("android.intent.action.SEND");
        if ((paramString4 == null) || (paramString4.equals("")))
            localIntent.setType("text/plain");
        localIntent.putExtra("android.intent.extra.SUBJECT", paramString2);
        localIntent.putExtra("android.intent.extra.TEXT", paramString3);
        localIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        paramContext.startActivity(Intent.createChooser(localIntent, paramString1));
        File file = new File(paramString4);
        if ((file == null) || (!(file.exists())) || (!(file.isFile())))
            return;
        localIntent.setType("image/png");
        localIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
    }

    public static boolean sharedTexttoWeixinFriend(Context paramContext, String paramString1, String paramString2) {
        try {
            List<PackageInfo> list = getInstallPacakages(paramContext);
            if (list.size() == 0) {
                Toast.makeText(paramContext, "分享失败,需要下载微信客户端", Toast.LENGTH_SHORT).show();
                Log.e("debug", "用户没有安装微信!!!");
                return false;
            }
            for (int i = 0; i < list.size(); i++) {
                PackageInfo info = list.get(i);
                if (info.packageName.equals("com.tencent.mm")) {
                    Intent intent = new Intent();
                    ComponentName localComponentName = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                    intent.setComponent(localComponentName);
                    intent.setAction("android.intent.action.SEND");
                    intent.setType("image/*");
                    intent.putExtra("android.intent.extra.TEXT", paramString2);
                    if (paramString1 == null) {
                        Log.e("debug", "图片路径不能为null");
                        return false;
                    }
                    File file = new File(paramString1);
                    intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
                    paramContext.startActivity(intent);
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e("debug", "分享报错，请检查参数");
            e.printStackTrace();
        }
        return false;
    }
}