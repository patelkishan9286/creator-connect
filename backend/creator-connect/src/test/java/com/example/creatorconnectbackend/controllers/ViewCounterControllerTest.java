package com.example.creatorconnectbackend.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.creatorconnectbackend.models.ViewCounter;
import com.example.creatorconnectbackend.services.ViewCounterService;

/**
 * ViewCounterControllerTest
 *
 * This class provides unit tests for the ViewCounterController.
 * It uses mock service layer interactions to test the behavior of view counting-related 
 * API endpoints and ensures they respond correctly for various inputs and scenarios.
 * 
 * Functions:
 * 1. setUp() - Initializes mock objects and instances for testing.
 * 2. testAddView() - Tests adding a view to an influencer's profile.
 * 3. testGetViewsByInfluencerID() - Tests retrieving the number of views by a specific influencer's ID.
 * 4. testGetProfileViewsByCompanyType() - Tests getting the number of profile views categorized by company type.
 * 
 */

public class ViewCounterControllerTest {

    private ViewCounterController viewCounterController;

    @Mock
    private ViewCounterService viewCounterService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        viewCounterController = new ViewCounterController(viewCounterService);
    }

    @Test
    public void testAddView() {
        // Prepare test data
        ViewCounter viewCounter = new ViewCounter();
        viewCounter.setInfluencerId(1L);
        viewCounter.setOrgId(1L);
        viewCounter.setDate(new Date());

        when(viewCounterService.addView(viewCounter)).thenReturn(viewCounter);

        ResponseEntity<ViewCounter> response = viewCounterController.addView(viewCounter);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(viewCounter, response.getBody());
    }

    @Test
    public void testGetViewsByInfluencerID() {
        Long influencerId = 1L;
        Map<Long, Integer> views = new HashMap<>();
        views.put(influencerId, 100);

        when(viewCounterService.getViewsByInfluencerID(influencerId)).thenReturn(views);

        ResponseEntity<Map<Long, Integer>> response = viewCounterController.getViewsByInfluencerID(influencerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(views, response.getBody());
    }

    @Test
    public void testGetProfileViewsByCompanyType() {
        Long influencerId = 1L;
        Map<String, Integer> views = new HashMap<>();
        views.put("IT", 100);

        when(viewCounterService.getProfileViewsByCompanyType(influencerId)).thenReturn(views);

        ResponseEntity<Map<String, Integer>> response = viewCounterController.getProfileViewsByCompanyType(influencerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(views, response.getBody());
    }
}
