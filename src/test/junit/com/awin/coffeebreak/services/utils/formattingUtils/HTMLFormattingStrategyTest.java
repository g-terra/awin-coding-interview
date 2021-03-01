package com.awin.coffeebreak.services.utils.formattingUtils;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HTMLFormattingStrategyTest {

    private HTMLFormattingStrategy htmlFormattingStrategy;

    @Before
    public void setUp() {
        this.htmlFormattingStrategy = new HTMLFormattingStrategy();
    }

    @Test
    public void formatName() {

        assertEquals("text/html", this.htmlFormattingStrategy.formatName());
    }

    @Test
    public void formatItem_passCorrectCreatedPreference_getsCorrectFormattedItem() {
        StaffMember expectedRequestedBy = new StaffMember();
        expectedRequestedBy.setSlackIdentifier("ABC123");
        expectedRequestedBy.setName("test");
        CoffeeBreakPreference preference = new CoffeeBreakPreference("drink", "coffee", expectedRequestedBy, null);

        String expected = "<li>test would like a coffee. ()</li>";

        assertEquals(expected, htmlFormattingStrategy.formatItem(preference));
    }

    @Test
    public void formatItem_passNullObject_returnsEmptyTemplate() {
        assertThrows(NullPointerException.class, () -> htmlFormattingStrategy.formatItem(null));
    }

    @Test
    public void formatWrapper() {
        assertEquals("<ul>%s</ul>", this.htmlFormattingStrategy.formatWrapper());
    }
}