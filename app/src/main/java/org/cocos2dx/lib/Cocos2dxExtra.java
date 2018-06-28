package org.cocos2dx.lib;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Cocos2dxExtra {
    public static void openURL(String paramString) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.google.com"));
        Cocos2dxActivity.getContext().startActivity(intent);
    }
}