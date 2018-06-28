package org.darcy.sanguo.pojo;

public class NameValuePair implements Cloneable {

    private String name;
    private Object value;

    public NameValuePair(String paramString, Object paramObject) {
        this.name = paramString;
        this.value = paramObject;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }
}