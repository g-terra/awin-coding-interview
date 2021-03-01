package com.awin.coffeebreak.controller;

import com.awin.coffeebreak.entity.CoffeeBreakPreference;
import com.awin.coffeebreak.entity.StaffMember;
import com.awin.coffeebreak.repository.CoffeeBreakPreferenceRepository;
import com.awin.coffeebreak.services.SlackNotifier;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CoffeeBreakPreferenceControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public CoffeeBreakPreferenceRepository coffeeBreakPreferenceRepository;

    @InjectMocks
    public CoffeeBreakPreferenceController coffeeBreakPreferenceController;

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

    @Before
    public void setUp() {
        expectedRequestedBy = new StaffMember();
        expectedRequestedBy.setSlackIdentifier("ABC123");
        expectedType = "drink";
        expectedSubType = "coffee";
        expectedDetails = new HashMap<>();
        expectedDetails.put("test1", "test1");
        expectedDetails.put("test2", "test2");
        expectedDetails.put("test3", "test3");
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
    public void notifyStaffMember() {
    }
}