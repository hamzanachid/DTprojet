package org.example.utils;

import javax.management.relation.Role;

public class CheckAccess {
    private static EnumRole currentUser;

    public static EnumRole getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(EnumRole currentUser) {
        CheckAccess.currentUser = currentUser;
    }
    public static void checkUserAccess(){
        if(currentUser.name().equals("admin")){
            throw new IllegalStateException("User Cannot access to this function");
        }
    }
}
