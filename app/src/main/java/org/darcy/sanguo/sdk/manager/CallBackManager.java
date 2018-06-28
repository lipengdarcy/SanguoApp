package org.darcy.sanguo.sdk.manager;

import org.darcy.sanguo.sdk.interfaces.InitCallback;
import org.darcy.sanguo.sdk.interfaces.LoginCallback;
import org.darcy.sanguo.sdk.interfaces.PayCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CallBackManager {
    private static List<InitCallback> initCallbackList;
    private static List<LoginCallback> loginCallbackList = new ArrayList();
    private static List<PayCallback> payCallbackList = new ArrayList();

    static {
        initCallbackList = new ArrayList();
    }

    public static void addInitCallback(InitCallback paramInitCallback) {
        initCallbackList.add(paramInitCallback);
    }

    public static void addLoginCallBack(LoginCallback paramLoginCallback) {
        loginCallbackList.add(paramLoginCallback);
    }

    public static void addPayCallBack(PayCallback paramPayCallback) {
        payCallbackList.add(paramPayCallback);
    }

    public static void doInitCallback(Boolean paramBoolean) {
        Iterator localIterator = initCallbackList.iterator();
        while (true) {
            if (!(localIterator.hasNext()))
                return;
            ((InitCallback) localIterator.next()).initFinish(paramBoolean.booleanValue());
        }
    }

    public static void doLoginCallBack(String paramString) {
        Iterator localIterator = loginCallbackList.iterator();
        while (true) {
            if (!(localIterator.hasNext()))
                return;
            ((LoginCallback) localIterator.next()).loginFinish(paramString);
        }
    }

    public static void doPayCallBack(String paramString) {
        Iterator localIterator = payCallbackList.iterator();
        while (true) {
            if (!(localIterator.hasNext()))
                return;
            ((PayCallback) localIterator.next()).onPayComplete(paramString);
        }
    }

    public static void remove(InitCallback paramInitCallback) {
        initCallbackList.remove(paramInitCallback);
    }

    public static void removeLoginCallBack(LoginCallback paramLoginCallback) {
        loginCallbackList.remove(paramLoginCallback);
    }

    public static void removePayCallBack(PayCallback paramPayCallback) {
        payCallbackList.remove(paramPayCallback);
    }
}