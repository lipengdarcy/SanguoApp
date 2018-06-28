package org.darcy.sanguo.exception;

import android.os.Build;


import org.darcy.sanguo.client.GameAdmin;

import java.io.PrintWriter;
import java.io.StringWriter;

//异常处理
public class SysExceptionHandler
        implements Thread.UncaughtExceptionHandler {
    private static SysExceptionHandler handler;

    public static SysExceptionHandler getInstance() {
        if (handler == null)
            handler = new SysExceptionHandler();
        return handler;
    }

    public void init() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
        if (handler == null)
            handler = getInstance();
        StringWriter localStringWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(localStringWriter);
        StackTraceElement[] arrayOfStackTraceElement2 = paramThrowable.getStackTrace();
        StackTraceElement[] arrayOfStackTraceElement1 = new StackTraceElement[arrayOfStackTraceElement2.length + 3];
        System.arraycopy(arrayOfStackTraceElement2, 0, arrayOfStackTraceElement1, 0, arrayOfStackTraceElement2.length);
        arrayOfStackTraceElement1[(arrayOfStackTraceElement2.length + 0)] = new StackTraceElement("Android", "MODEL", Build.MODEL, -1);
        arrayOfStackTraceElement1[(arrayOfStackTraceElement2.length + 1)] = new StackTraceElement("Android", "VERSION", Build.VERSION.RELEASE, -1);
        arrayOfStackTraceElement1[(arrayOfStackTraceElement2.length + 2)] = new StackTraceElement("Android", "FINGERPRINT", Build.FINGERPRINT, -1);
        paramThrowable.setStackTrace(arrayOfStackTraceElement1);
        paramThrowable.printStackTrace(pw);
        String errorInfo = localStringWriter.toString();
        pw.close();
        GameAdmin.onSendExcepMsg(Integer.valueOf(2), errorInfo);
        try {
            Thread.sleep(1000L);
            System.exit(1);
            return;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}