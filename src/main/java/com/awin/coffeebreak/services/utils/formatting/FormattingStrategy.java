package com.awin.coffeebreak.services.utils.formatting;

public interface FormattingStrategy<T> {
    String formatName();
    String formatItem(WebFormat t);
    String formatWrapper();
}
