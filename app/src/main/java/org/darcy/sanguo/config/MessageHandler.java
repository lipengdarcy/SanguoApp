package org.darcy.sanguo.config;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageHandler {
    private MessageInterface a;
    private Context paramContext;

    public MessageHandler(Context paramContext, MessageInterface paramb) {
        this.paramContext = paramContext;
        this.a = paramb;
    }

    private static void runTask(Runnable paramRunnable) {
        if (paramRunnable == null)
            return;
        if (Looper.getMainLooper() == Looper.myLooper())
            paramRunnable.run();
        new Handler(Looper.getMainLooper()).post(paramRunnable);

    }

    private void a(String paramString, Message.status status, boolean paramBoolean) throws JSONException {
        if (TextUtils.isEmpty(paramString))
            return;
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("error", status.ordinal());
        Message message = new Message("callback");
        message.setContent(localJSONObject);

    }

    private Message.status b(Message parama) {
        return Message.status.a;
    }


}