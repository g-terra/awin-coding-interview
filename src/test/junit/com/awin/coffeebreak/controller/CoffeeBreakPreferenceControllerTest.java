package com.awin.coffeebreak.controller;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import com.awin.coffeebreak.repository.CoffeeBreakPreferenceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CoffeeBreakPreferenceControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public CoffeeBreakPreferenceRepository coffeeBreakPreferenceRepository;

    private CoffeeBreakPreference preference;
    private StaffMember expectedRequestedBy;
    private String expectedType;
    private String expectedSubType;
    private HashMap<String, String> expectedDetails;
    private String jsonPreferencesFormat = "{\"preferences\":[{\"id\":null," +
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
        expectedDetails = new HashMap<>();
        expectedDetails.put("test1", "test1");
        this.preference = new CoffeeBreakPreference(expectedType, expectedSubType, expectedRequestedBy, expectedDetails);
    }

    @Test
    public void getRequestToTodaysPreferencesEndpoint_requestJSON_returnsJsonWithCorrectData() throws Exception {

        List<CoffeeBreakPreference> todayPreferences = Collections.singletonList(preference);

        Mockito.when(coffeeBreakPreferenceRepository.getPreferencesForToday()).thenReturn(todayPreferences);

        URI uri = new URI("/today?format=json");
        String expectedJsonResponseBody = String.format(jsonPreferencesFormat
                , expectedType, expectedSubType, expectedRequestedBy.toString(), expectedDetails)
                .replace(",\\", ", ");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedJsonResponseBody));

    }


    @Test
    public void getRequestToTodaysPreferencesEndpoint_requestJSONWithNullDetails_returnsJsonWithCorrectData() throws Exception {

        CoffeeBreakPreference preferenceWithNullDetails = new CoffeeBreakPreference(expectedType, expectedSubType, expectedRequestedBy, null);
        List<CoffeeBreakPreference> todayPreferences = Collections.singletonList(preferenceWithNullDetails);

        Mockito.when(coffeeBreakPreferenceRepository.getPreferencesForToday()).thenReturn(todayPreferences);

        URI uri = new URI("/today?format=json");
        String expectedJsonResponseBody = String.format(jsonPreferencesFormat
                , expectedType, expectedSubType, expectedRequestedBy.toString(),  "{}")
                .replace(",\\", ", ");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedJsonResponseBody));

    }

    @Test
    public void getRequestToTodaysPreferencesEndpoint_requestXML_returnsXMLWithCorrectData() throws Exception {

        List<CoffeeBreakPreference> todayPreferences = Collections.singletonList(preference);

        Mockito.when(coffeeBreakPreferenceRepository.getPreferencesForToday()).thenReturn(todayPreferences);

        URI uri = new URI("/today?format=xml");
        String expectedJsonResponseBody = String.format(xmlFormat
                , expectedType, expectedSubType, expectedRequestedBy.toString(), expectedDetails)
                .replace(",\\", ", ");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedJsonResponseBody));

    }

    @Test
    public void getRequestToTodaysPreferencesEndpoint_requestXMLWithNullDetails_returnsXMLWithCorrectData() throws Exception {

        CoffeeBreakPreference preferenceWithNullDetails = new CoffeeBreakPreference(expectedType, expectedSubType, expectedRequestedBy, null);
        List<CoffeeBreakPreference> todayPreferences = Collections.singletonList(preferenceWithNullDetails);

        Mockito.when(coffeeBreakPreferenceRepository.getPreferencesForToday()).thenReturn(todayPreferences);

        URI uri = new URI("/today?format=xml");
        String expectedJsonResponseBody = String.format(xmlFormat
                , expectedType, expectedSubType, expectedRequestedBy.toString(), "{}")
                .replace(",\\", ", ");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedJsonResponseBody));

    }

    @Test
    public void getRequestToTodaysPreferencesEndpoint_requestHTML_returnsHTMLWithCorrectData() throws Exception {

        List<CoffeeBreakPreference> todayPreferences = Collections.singletonList(preference);

        Mockito.when(coffeeBreakPreferenceRepository.getPreferencesForToday()).thenReturn(todayPreferences);

        URI uri = new URI("/today?format=html");
        String expectedJsonResponseBody = String.format(htmlFormat,
        expectedRequestedBy.getName() , expectedSubType, "test1 : test1");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedJsonResponseBody));

    }

    @Test
    public void getRequestToTodaysPreferencesEndpoint_requestWithoutSpecifyingTheFormat_returnsHTMLWithCorrectData() throws Exception {

        List<CoffeeBreakPreference> todayPreferences = Collections.singletonList(preference);

        Mockito.when(coffeeBreakPreferenceRepository.getPreferencesForToday()).thenReturn(todayPreferences);

        URI uri = new URI("/today");
        String expectedJsonResponseBody = String.format(htmlFormat,
                expectedRequestedBy.getName() , expectedSubType, "test1 : test1");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedJsonResponseBody));

    }

    @Test
    public void getRequestToTodaysPreferencesEndpoint_requestWithoutWithNullDetails_returnsHTMLWithCorrectData() throws Exception {

        CoffeeBreakPreference preferenceWithNullDetails = new CoffeeBreakPreference(expectedType, expectedSubType, expectedRequestedBy, null);
        List<CoffeeBreakPreference> todayPreferences = Collections.singletonList(preferenceWithNullDetails);

        Mockito.when(coffeeBreakPreferenceRepository.getPreferencesForToday()).thenReturn(todayPreferences);

        URI uri = new URI("/today?format=html");
        String expectedJsonResponseBody = String.format(htmlFormat,
                expectedRequestedBy.getName() , expectedSubType, "");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedJsonResponseBody));

    }


    @Test
    public void notifyStaffMember() {
    }
}