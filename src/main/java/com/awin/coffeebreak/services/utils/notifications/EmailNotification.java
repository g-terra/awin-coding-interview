package com.awin.coffeebreak.services.utils.notifications;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;

import java.util.List;
import java.util.stream.Collectors;

public class EmailNotification implements NotificationStrategy {

  @Override
    public String getNotificationType() {
        return "email";
    }


    @Override
    public boolean sendNotification(StaffMember receiver, CoffeeBreakPreference preference) throws FailedToNotifyException {

            if (receiver.getEmail() == null) throw new FailedToNotifyException("Not able to notify if email is not set");
            System.out.println("sending email....");
            System.out.println(notification(receiver,preference));
            System.out.println("Notification sent!!");
            return true;

    }

    private String notification(StaffMember receiver ,CoffeeBreakPreference preference) {



        return "EmailNotification{" +
                "emailBody=" + preference +
                ", to="+ receiver.getEmail() +
                ", from=notification System" +
                "}";
    }
}
