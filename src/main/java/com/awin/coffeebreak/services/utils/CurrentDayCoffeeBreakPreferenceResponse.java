package com.awin.coffeebreak.services.utils;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.services.utils.formatting.FormattingTemplate;

import java.util.List;

public class CurrentDayCoffeeBreakPreferenceResponse {

    private final List<CoffeeBreakPreference> preferences;
    private final FormattingTemplate<CoffeeBreakPreference> formattingTemplate;

    public CurrentDayCoffeeBreakPreferenceResponse(List<CoffeeBreakPreference> preference, FormattingTemplate<CoffeeBreakPreference> formattingTemplate) {
        if (preference == null) throw new NullPointerException("Not possible to initialize with null list of preferences");
        if (formattingTemplate == null) throw new NullPointerException("Not possible to initialize with null formatting template");
        this.preferences = preference;
        this.formattingTemplate = formattingTemplate;
    }

    public String getContent() {
        return formattingTemplate.format(this.preferences);
    }

    public String getContentType(){
        return formattingTemplate.getFormattingName();
    }
}
