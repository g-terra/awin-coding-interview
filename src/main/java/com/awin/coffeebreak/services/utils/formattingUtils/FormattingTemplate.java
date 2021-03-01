package com.awin.coffeebreak.services.utils.formattingUtils;

import java.util.List;

public class FormattingTemplate<T> {

    private final FormattingStrategy<T> formattingStrategy;

    public FormattingTemplate(FormattingStrategy<T> formattingStrategy) {
        this.formattingStrategy = formattingStrategy;
    }

    public String format(List<? extends WebFormat> list) {

        String reduce = list.stream()
                .map(formattingStrategy::formatItem)
                .reduce("", (partial, current) -> partial += current);

        return String.format(formattingStrategy.formatWrapper(), reduce);
    }

    public String getFormattingName(){
        return formattingStrategy.formatName();
    }






}
