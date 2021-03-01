package com.awin.coffeebreak.controller;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import com.awin.coffeebreak.repository.CoffeeBreakPreferenceRepository;
import com.awin.coffeebreak.repository.StaffMemberRepository;
import com.awin.coffeebreak.services.CoffeeBreakPreferenceService;
import com.awin.coffeebreak.services.utils.CurrentDayCoffeeBreakPreferenceResponse;
import com.awin.coffeebreak.services.NotificationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.awin.coffeebreak.services.utils.notifications.EmailNotification;
import com.awin.coffeebreak.services.utils.notifications.NotificationStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoffeeBreakPreferenceController {

    public CoffeeBreakPreferenceRepository coffeeBreakPreferenceRepository;
    public StaffMemberRepository staffMemberRepository;
    public CoffeeBreakPreferenceService coffeeBreakPreferenceService;

    public CoffeeBreakPreferenceController(CoffeeBreakPreferenceRepository coffeeBreakPreferenceRepository, CoffeeBreakPreferenceService coffeeBreakPreferenceService
    ) {
        this.coffeeBreakPreferenceRepository = coffeeBreakPreferenceRepository;
        this.coffeeBreakPreferenceService = coffeeBreakPreferenceService;
    }

    @GetMapping(path = "/today", produces = {"application/json"})
    public ResponseEntity<?> todayJson() {
        CurrentDayCoffeeBreakPreferenceResponse todayCoffeeBreakAsJSON = coffeeBreakPreferenceService.getTodayCoffeeBreakAsJSON();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(todayCoffeeBreakAsJSON.getContentType()))
                .body(todayCoffeeBreakAsJSON.getContent());
    }

    @GetMapping(path = "/today", produces = {"text/xml"})
    public ResponseEntity<?> todayXml() {
        CurrentDayCoffeeBreakPreferenceResponse todayCoffeeBreakAsXML = coffeeBreakPreferenceService.getTodayCoffeeBreakAsXML();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(todayCoffeeBreakAsXML.getContentType()))
                .body(todayCoffeeBreakAsXML.getContent());
    }

    @GetMapping(path = "/today", produces = {"text/html"})
    public ResponseEntity<?> todayHtml() {
        CurrentDayCoffeeBreakPreferenceResponse todayCoffeeBreakAsHTML = coffeeBreakPreferenceService.getTodayCoffeeBreakAsHTML();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(todayCoffeeBreakAsHTML.getContentType()))
                .body(todayCoffeeBreakAsHTML.getContent());
    }

    @GetMapping("/notifyStaffMember")
    public ResponseEntity<Object> notifyStaffMember(@RequestParam("staffMemberId") int id) {
        Optional<StaffMember> staffMember = this.staffMemberRepository.findById(id);

        if (staffMember.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        EmailNotification emailNotification = new EmailNotification();

        List<NotificationStrategy> notificationStrategies = Arrays.asList(emailNotification);

        NotificationService notifier = new NotificationService(notificationStrategies);

        boolean sentAllNotifications = notifier.notifyStaffMember(staffMember.get(), coffeeBreakPreferenceService.getTodayPreferences());
        if (sentAllNotifications) return ResponseEntity.ok("All notifications were sent!");
        return ResponseEntity.badRequest().body("Failed to send notification!");
    }

}
