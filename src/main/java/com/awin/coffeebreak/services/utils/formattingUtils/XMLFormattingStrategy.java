package com.awin.coffeebreak.services.utils.formattingUtils;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;

public class XMLFormattingStrategy implements FormattingStrategy<CoffeeBreakPreference> {

    @Override
    public String formatName() {
        return "text/xml";
    }

    @Override
    public String formatItem(WebFormat o) {
        if (o == null) throw new NullPointerException("Not possible to format null Element");
        return o.getAsXml();
    }

    @Override
    public String formatWrapper() {

        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Preferences>%s</Preferences>";
    }

}
