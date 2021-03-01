package com.awin.coffeebreak.services;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.repository.CoffeeBreakPreferenceRepository;
import com.awin.coffeebreak.services.utils.CurrentDayCoffeeBreakPreferenceResponse;
import com.awin.coffeebreak.services.utils.formattingUtils.FormattingTemplate;
import com.awin.coffeebreak.services.utils.formattingUtils.HTMLFormattingStrategy;

import com.awin.coffeebreak.services.utils.formattingUtils.JSONFormattingStrategy;
import com.awin.coffeebreak.services.utils.formattingUtils.XMLFormattingStrategy;
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

    private List<CoffeeBreakPreference> getTodayPreferences() {
        return coffeeBreakPreferenceRepository.getPreferencesForToday();
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
