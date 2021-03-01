package com.awin.coffeebreak.services

import com.awin.coffeebreak.entity.CoffeeBreakPreference
import com.awin.coffeebreak.entity.StaffMember
import com.awin.coffeebreak.services.utils.notifications.EmailNotification
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class NotificationServiceSpec extends Specification {

    def "testStatusOfNotificationIsTrue"() {
        given:
        def staff = new StaffMember()
        staff.setSlackIdentifier("ABC123")
        staff.setEmail("test@test.com")
        def preference = new CoffeeBreakPreference("drink", "coffee", staff, null)

        def notificationService = new NotificationService()

        notificationService.addNotificationStrategy(new EmailNotification())

        when:
        def status = notificationService.notifyStaffMember(staff, preference)

        then:
        assertThat(status).isTrue()
    }

    def "testThrowsExceptionWhenCannotNotify"() {
        given:
        def staff = new StaffMember()
        def preference = new CoffeeBreakPreference("drink", "tea", staff, null)
        def notificationService = new NotificationService()
        notificationService.addNotificationStrategy(new EmailNotification())

        when:

        def status = notificationService.notifyStaffMember(staff, [preference])

        then:
        thrown(RuntimeException.class)
    }
}
