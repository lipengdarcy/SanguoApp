package org.darcy.sanguo.utils;

import java.io.Serializable;

public class MktPluginSetting
        implements Serializable {
    private static final long serialVersionUID = 1L;
    private String url;

    public MktPluginSetting(String paramString) {
        this.url = paramString;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String paramString) {
        this.url = paramString;
    }
}