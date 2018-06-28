package org.darcy.sanguo.config;

import org.json.JSONException;
import org.json.JSONObject;

//a
public class Message {
    public static final String CallInfo = "CallInfo";
    public static final String call = "call";
    public static final String callback = "callback";
    public static final String bundleName = "bundleName";
    public static final String clientId = "clientId";
    public static final String param = "param";
    public static final String func = "func";
    public static final String msgType = "msgType";
    private String clientIdInfo; //客户端id
    private String j;
    private String k;//
    private String msgTypeInfo;//l
    private JSONObject content;//m
    private boolean flag = false;//n

    public Message(String msgType) {
        setMsgType(msgType);
    }

    public static final String getCallInfo(JSONObject object) {
        String result = null;
        switch (object.hashCode()) {
            case 1:
                result = "runtime error";
            case 2:
                result = "function not found";
            case 3:
                result = "invalid parameter";
            default:
                result = "none";
        }
        return result;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean getFlag() {
        return this.flag;
    }

    public void setClientId(String clientIdInfo) {
        this.clientIdInfo = clientIdInfo;
    }

    public String getClientId() {
        return this.clientIdInfo;
    }

    public void b(String paramString) {
        this.j = paramString;
    }

    public String c() {
        return this.j;
    }

    public void c(String paramString) {
        this.k = paramString;
    }

    public String getMsgTypeInfo() {
        return this.k;
    }

    public void setMsgType(String msgType) {
        this.msgTypeInfo = msgType;
    }

    public String getMsgType() {
        return this.msgTypeInfo;
    }

    public void setContent(JSONObject paramJSONObject) {
        this.content = paramJSONObject;
    }

    public JSONObject getContent() {
        return this.content;
    }

    public String getInfo() throws JSONException {
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("clientId", this.clientIdInfo);
        localJSONObject.put("func", this.func);
        localJSONObject.put("param", this.param);
        localJSONObject.put("msgType", this.msgTypeInfo);
        return localJSONObject.toString();
    }

    public static enum status {
        a, b, c, d, e;
    }
}