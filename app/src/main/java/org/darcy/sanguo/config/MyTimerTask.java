package org.darcy.sanguo.config;

import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

//e
public class MyTimerTask extends TimerTask {
    public void run() {
        JSONObject localJSONObject = new JSONObject();
        try {
            localJSONObject.put("toastCallBack", "true");
            Message message = new Message("callback");
            message.getCallInfo(message.getContent());
            message.setContent(localJSONObject);
            return;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}