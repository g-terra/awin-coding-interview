package com.awin.coffeebreak.services.utils.formatting;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;

public class JSONFormattingStrategy implements FormattingStrategy<CoffeeBreakPreference> {

    @Override
    public String formatName() {
        return "Application/json" ;
    }

    @Override
    public String formatItem(WebFormat o) {
        return o.getAsJson();
    }

    @Override
    public String formatWrapper() {
        return "{\"preferences\":[%s]}";
    }

}
