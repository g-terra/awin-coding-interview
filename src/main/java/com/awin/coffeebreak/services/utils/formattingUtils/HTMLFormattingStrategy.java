package com.awin.coffeebreak.services.utils.formattingUtils;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;

public class HTMLFormattingStrategy implements FormattingStrategy<CoffeeBreakPreference> {

    @Override
    public String formatName() {
        return "text/html" ;
    }

    @Override
    public String formatItem(WebFormat o) {
        if (o == null) throw new NullPointerException("Not possible to format null Element");
        return o.getAsListElement();
    }

    @Override
    public String formatWrapper() {
        return "<ul>%s</ul>";
    }

}
