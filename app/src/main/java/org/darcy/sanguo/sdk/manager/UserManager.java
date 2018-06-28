package org.darcy.sanguo.sdk.manager;

import org.darcy.sanguo.sdk.bean.User;

public class UserManager {
    private static User currentUser;

    public static void cancel() {
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User paramUser) {
        if (currentUser != null)
            return;
        currentUser = paramUser;
    }
}