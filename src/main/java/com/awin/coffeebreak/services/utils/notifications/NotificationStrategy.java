package com.awin.coffeebreak.services.utils.notifications;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;

import java.util.List;

public interface NotificationStrategy {
    String getNotificationType();
    boolean sendNotification(StaffMember receiver , CoffeeBreakPreference preferenceList) throws FailedToNotifyException;


}
