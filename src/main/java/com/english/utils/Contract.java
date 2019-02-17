package com.english.utils;

import com.vaadin.ui.Notification;


public class Contract {
    public static boolean isNull(Object entry, String objectName) {
        if (entry == null || entry.toString().isEmpty()) {
            Notification.show(objectName, " is empty", Notification.Type.HUMANIZED_MESSAGE);
            return true;
        }
        return false;
    }

    public static boolean incorrectEmail(String email) {
        if (false == GlobalFunctions.isValidEmailAddress(email)) {
            Notification.show("email", " is incorrect", Notification.Type.HUMANIZED_MESSAGE);
            return true;
        }
        return false;
    }
}
