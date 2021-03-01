package com.awin.coffeebreak.services.utils.formatting;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FormattingTemplateTest {


    private CoffeeBreakPreference preference;
    private StaffMember expectedRequestedBy;
    private String expectedType;
    private String expectedSubType;
    private HashMap<String, String> expectedDetails;
    private String jsonFormat = "{\"preferences\":[{\"id\":null," +
            " \"type\":\"%s\"," +
            " \"subType\":\"%s\"," +
            " \"requestedBy\":\"%s\"," +
            " \"requestedDate\":\"null\"," +
            " \"details\":\"%s\"" +
            "}]}";

    private String xmlFormat = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<Preferences>" +
            "<preference type=\"%s\" subtype=\"%s\">" +
            "<requestedBy>%s</requestedBy>" +
            "<details>%s</details>" +
            "</preference>" +
            "</Preferences>";

    private String htmlFormat = "<ul><li>%s would like a %s. (%s)</li></ul>";


    @Before
    public void setUp() {
        expectedRequestedBy = new StaffMember();
        expectedRequestedBy.setSlackIdentifier("ABC123");
        expectedType = "drink";
        expectedSubType = "coffee";
        expectedDetails = null;
        this.preference = new CoffeeBreakPreference(expectedType, expectedSubType, expectedRequestedBy, expectedDetails);
    }

    @Test
    public void format() {

        List<CoffeeBreakPreference> preferenceList = Arrays.asList(preference);

        FormattingTemplate<CoffeeBreakPreference> xmlTemplate = new FormattingTemplate<>(new XMLFormattingStrategy());
        FormattingTemplate<CoffeeBreakPreference> HTMLTemplate = new FormattingTemplate<>(new HTMLFormattingStrategy());
        FormattingTemplate<CoffeeBreakPreference> jsonTemplate = new FormattingTemplate<>(new JSONFormattingStrategy());

        String expectedXMLResponseBody = String.format(xmlFormat
                , expectedType, expectedSubType, expectedRequestedBy.toString(), "{}")
                .replace(",\\", ", ");

        String expectedJsonResponseBody = String.format(jsonFormat
                , expectedType, expectedSubType, expectedRequestedBy.toString(), "{}")
                .replace(",\\", ", ");

        String expectedHTMLResponseBody = String.format(htmlFormat,
                expectedRequestedBy.getName() , expectedSubType, "");

        assertEquals(expectedXMLResponseBody, xmlTemplate.format(preferenceList));
        assertEquals(expectedJsonResponseBody, jsonTemplate.format(preferenceList));
        assertEquals(expectedHTMLResponseBody, HTMLTemplate.format(preferenceList));

    }

    @Test
    public void getFormattingName() {
        FormattingTemplate<CoffeeBreakPreference> xmlTemplate = new FormattingTemplate<>(new XMLFormattingStrategy());
        FormattingTemplate<CoffeeBreakPreference> HTMLTemplate = new FormattingTemplate<>(new HTMLFormattingStrategy());
        FormattingTemplate<CoffeeBreakPreference> jsonTemplate = new FormattingTemplate<>(new JSONFormattingStrategy());

        assertEquals("text/xml", xmlTemplate.getFormattingName());
        assertEquals("Application/json", jsonTemplate.getFormattingName());
        assertEquals("text/html", HTMLTemplate.getFormattingName());
    }


}