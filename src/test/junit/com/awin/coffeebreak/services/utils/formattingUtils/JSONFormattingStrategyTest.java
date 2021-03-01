package com.awin.coffeebreak.services.utils.formattingUtils;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JSONFormattingStrategyTest {

    private JSONFormattingStrategy jsonFormattingStrategy;

    @Before
    public void setUp() {
        this.jsonFormattingStrategy = new JSONFormattingStrategy();
    }

    @Test
    public void formatName() {
        assertEquals("Application/json", this.jsonFormattingStrategy.formatName());
    }

    @Test
    public void formatItem_passCorrectCreatedPreference_getsCorrectFormattedItem() {
        StaffMember expectedRequestedBy = new StaffMember();
        expectedRequestedBy.setSlackIdentifier("ABC123");
        expectedRequestedBy.setName("test");
        CoffeeBreakPreference preference = new CoffeeBreakPreference("drink", "coffee", expectedRequestedBy, null);

        String expected = "{\"id\":null," +
                " \"type\":\"drink\"," +
                " \"subType\":\"coffee\"," +
                " \"requestedBy\":\""+expectedRequestedBy+"\"," +
                " \"requestedDate\":\"null\"," +
                " \"details\":\"{}\"" +
                "}";

        assertEquals(expected, jsonFormattingStrategy.formatItem(preference));
    }

    @Test
    public void formatItem_passNullObject_returnsEmptyTemplate() {
        assertThrows(NullPointerException.class, () -> jsonFormattingStrategy.formatItem(null));
    }

    @Test
    public void formatWrapper() {
        assertEquals("{\"preferences\":[%s]}", this.jsonFormattingStrategy.formatWrapper());
    }
}