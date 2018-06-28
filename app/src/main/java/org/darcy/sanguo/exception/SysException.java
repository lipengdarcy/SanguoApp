package org.darcy.sanguo.exception;

//系统异常基类
public class SysException extends Exception {
    private static final long serialVersionUID = 1L;

    public SysException() {
    }

    public SysException(String paramString) {
        super(paramString);
    }

    public SysException(String paramString, Throwable paramThrowable) {
        super(paramString, paramThrowable);
    }

    public SysException(Throwable paramThrowable) {
        super(paramThrowable);
    }
}