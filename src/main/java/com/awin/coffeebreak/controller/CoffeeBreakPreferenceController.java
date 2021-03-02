package com.awin.coffeebreak.controller;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import com.awin.coffeebreak.repository.StaffMemberRepository;
import com.awin.coffeebreak.services.CoffeeBreakPreferenceService;
import com.awin.coffeebreak.services.NotificationService;
import com.awin.coffeebreak.services.utils.CurrentDayCoffeeBreakPreferenceResponse;
import com.awin.coffeebreak.services.utils.notifications.EmailNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CoffeeBreakPreferenceController {

    private final StaffMemberRepository staffMemberRepository;
    private final CoffeeBreakPreferenceService coffeeBreakPreferenceService;
    private final NotificationService notificationService;

    @Autowired
    public CoffeeBreakPreferenceController(
            StaffMemberRepository staffMemberRepository, CoffeeBreakPreferenceService coffeeBreakPreferenceService,
            NotificationService notificationService) {
        this.staffMemberRepository = staffMemberRepository;

        this.coffeeBreakPreferenceService = coffeeBreakPreferenceService;
        this.notificationService = notificationService;
        notificationService.addNotificationStrategy(new EmailNotification());

    }


    @GetMapping(path = "/today")
    public ResponseEntity<?> today(@RequestParam(name = "format" , required = false) String format) {
        if (format == null) format = "html";

        String responseContent;
        String contentType;

        switch (format) {
            case "json":
                CurrentDayCoffeeBreakPreferenceResponse responseJSON = coffeeBreakPreferenceService.getTodayCoffeeBreakAsJSON();
                responseContent = responseJSON.getContent();
                contentType = responseJSON.getContentType();
                break;

            case "xml":
                CurrentDayCoffeeBreakPreferenceResponse responseXML = coffeeBreakPreferenceService.getTodayCoffeeBreakAsXML();
                responseContent = responseXML.getContent();
                contentType = responseXML.getContentType();
                break;

            default:
                CurrentDayCoffeeBreakPreferenceResponse responseHtml = coffeeBreakPreferenceService.getTodayCoffeeBreakAsHTML();
                responseContent = responseHtml.getContent();
                contentType = responseHtml.getContentType();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(contentType))
                .body(responseContent);
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

        boolean sentAllNotifications = this.notificationService.notifyStaffMember(
                staffMember.get(),
                coffeeBreakPreferenceService.getPreferenceOf(staffMember.get()));

        if (sentAllNotifications) return ResponseEntity.ok("All notifications were sent!");
        return ResponseEntity.badRequest().body("Failed to send notification!");
    }

}
