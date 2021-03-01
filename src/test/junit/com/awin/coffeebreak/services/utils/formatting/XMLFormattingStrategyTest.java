package com.awin.coffeebreak.services.utils.formatting;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class XMLFormattingStrategyTest {

    private XMLFormattingStrategy xmlFormattingStrategy;

    @Before
    public void setUp() {
        this.xmlFormattingStrategy = new XMLFormattingStrategy();
    }

    @Test
    public void formatName() {
        assertEquals("text/xml", this.xmlFormattingStrategy.formatName());
    }

    @Test
    public void formatItem_passCorrectCreatedPreference_getsCorrectFormattedItem() {
        StaffMember expectedRequestedBy = new StaffMember();
        expectedRequestedBy.setSlackIdentifier("ABC123");
        expectedRequestedBy.setName("test");
        CoffeeBreakPreference preference = new CoffeeBreakPreference("drink", "coffee", expectedRequestedBy, null);

        String expected = "<preference type=\"drink\" subtype=\"coffee\">" +
                "<requestedBy>" + expectedRequestedBy + "</requestedBy>" +
                "<details>{}</details>" +
                "</preference>";

        assertEquals(expected, xmlFormattingStrategy.formatItem(preference));
    }

    @Test
    public void formatItem_passNullObject_returnsEmptyTemplate() {
        assertThrows(NullPointerException.class, () -> xmlFormattingStrategy.formatItem(null));
    }

    @Test
    public void formatWrapper() {
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Preferences>%s</Preferences>", this.xmlFormattingStrategy.formatWrapper());
    }
}