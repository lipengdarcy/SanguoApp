package org.darcy.sanguo.sdk.bean;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private String level;
    private String password;
    private String phone;
    private String uid;
    private String username;

    public String getEmail() {
        return this.email;
    }

    public String getLevel() {
        return this.level;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getUid() {
        return this.uid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setEmail(String paramString) {
        this.email = paramString;
    }

    public void setLevel(String paramString) {
        this.level = paramString;
    }

    public void setPassword(String paramString) {
        this.password = paramString;
    }

    public void setPhone(String paramString) {
        this.phone = paramString;
    }

    public void setUid(String paramString) {
        this.uid = paramString;
    }

    public void setUsername(String paramString) {
        this.username = paramString;
    }

    public String toString() {
        return "username:" + getUsername() + ",uid:" + getUid() + ",phone:" + getPhone() + ",email:" + getEmail() + "passWord:" + getPassword();
    }
}