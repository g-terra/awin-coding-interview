package com.awin.coffeebreak.services.utils.formattingUtils;

public interface FormattingStrategy<T> {
    String formatName();
    String formatItem(WebFormat t);
    String formatWrapper();
}
