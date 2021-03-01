package com.awin.coffeebreak.services;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import com.awin.coffeebreak.services.utils.notifications.FailedToNotifyException;
import com.awin.coffeebreak.services.utils.notifications.NotificationStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class NotificationService {

    private final List<NotificationStrategy> notificationStrategies;

    public NotificationService() {
        notificationStrategies = new ArrayList<>();
    }

    public List<NotificationStrategy> getNotificationStrategies() {
        return Collections.unmodifiableList(notificationStrategies);
    }

    public void addNotificationStrategy(NotificationStrategy... notificationStrategies) {
        this.notificationStrategies.addAll(Arrays.asList(notificationStrategies));
    }

    public void cleanNotificationList() {
        this.notificationStrategies.clear();
    }

    /**
     * Imagine that this method:
     * Sends a notification to the user on Slack that their coffee break refreshment today will be $preference
     * returns true of false status of notification sent
     */
    public boolean notifyStaffMember(final StaffMember staffMember, final CoffeeBreakPreference preference) throws FailedToNotifyException {

        try {
            if (notificationStrategies.isEmpty()) return true;

            for (NotificationStrategy notificationStrategy : notificationStrategies) {
                notificationStrategy.sendNotification(staffMember, preference);
            }

            return true;

        } catch (Exception e){
            throw new FailedToNotifyException(e.getMessage());
        }

    }

}
