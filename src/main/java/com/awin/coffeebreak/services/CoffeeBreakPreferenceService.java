package com.awin.coffeebreak.services;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import com.awin.coffeebreak.repository.CoffeeBreakPreferenceRepository;
import com.awin.coffeebreak.services.utils.CurrentDayCoffeeBreakPreferenceResponse;
import com.awin.coffeebreak.services.utils.formatting.FormattingTemplate;
import com.awin.coffeebreak.services.utils.formatting.HTMLFormattingStrategy;

import com.awin.coffeebreak.services.utils.formatting.JSONFormattingStrategy;
import com.awin.coffeebreak.services.utils.formatting.XMLFormattingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoffeeBreakPreferenceService {

    private final CoffeeBreakPreferenceRepository coffeeBreakPreferenceRepository;

    @Autowired
    public CoffeeBreakPreferenceService(CoffeeBreakPreferenceRepository coffeeBreakPreferenceRepository) {
        this.coffeeBreakPreferenceRepository = coffeeBreakPreferenceRepository;
    }

    public List<CoffeeBreakPreference> getTodayPreferences() {
        return coffeeBreakPreferenceRepository.getPreferencesForToday();
    }

    public CoffeeBreakPreference getPreferenceOf(StaffMember staffMember) {
        return coffeeBreakPreferenceRepository.getTodayPreferenceById(staffMember.getId());
    }

    public CurrentDayCoffeeBreakPreferenceResponse getTodayCoffeeBreakAsXML() {
        FormattingTemplate<CoffeeBreakPreference> xmlTemplate = new FormattingTemplate<>(new XMLFormattingStrategy());
        return new CurrentDayCoffeeBreakPreferenceResponse(getTodayPreferences(), xmlTemplate);
    }

    public CurrentDayCoffeeBreakPreferenceResponse getTodayCoffeeBreakAsHTML() {
        FormattingTemplate<CoffeeBreakPreference> htmlTemplate = new FormattingTemplate<>(new HTMLFormattingStrategy());
        return new CurrentDayCoffeeBreakPreferenceResponse(getTodayPreferences(), htmlTemplate);
    }

    public CurrentDayCoffeeBreakPreferenceResponse getTodayCoffeeBreakAsJSON() {
        FormattingTemplate<CoffeeBreakPreference> jsonTemplate = new FormattingTemplate<>(new JSONFormattingStrategy());
        return new CurrentDayCoffeeBreakPreferenceResponse(getTodayPreferences(), jsonTemplate);
    }

}
