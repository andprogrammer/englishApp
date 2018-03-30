package com.english;

import com.vaadin.ui.Notification;

public class Contract {
	public static boolean isNull(Object entry, String objectName) {
		if(entry == null || entry.toString().isEmpty()) { 
			Notification.show(objectName, " is empty", Notification.Type.HUMANIZED_MESSAGE);
			return true;
		}
		return false;
	}
}
