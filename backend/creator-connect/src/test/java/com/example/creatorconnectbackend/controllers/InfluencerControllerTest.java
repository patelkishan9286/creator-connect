package com.example.creatorconnectbackend.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.example.creatorconnectbackend.models.Influencer;
import com.example.creatorconnectbackend.services.InfluencerService;
/**
 * InfluencerControllerTest
 *
 * This class provides unit tests for the InfluencerController.
 * It mocks the service layer interactions and tests controller responses
 * to ensure the correct behavior of the application's influencer-related API endpoints.
 * 
 * Functions:
 * 1. setUp() - Initializes the required objects for testing.
 * 2. testRegisterInfluencer() - Tests the registration of an influencer without validation errors.
 * 3. testRegisterInfluencer_WithValidationErrors() - Tests the registration of an influencer with validation errors.
 * 4. testGetInfluencerById() - Tests retrieving a single influencer by its ID.
 * 5. testUpdateInfluencer() - Tests updating an influencer's details without validation errors.
 * 6. testUpdateInfluencer_WithValidationErrors() - Tests updating an influencer's details with validation errors.
 * 7. testGetAllInfluencers() - Tests retrieving all influencers.
 * 8. testDeleteInfluencerById() - Tests deleting an influencer by its ID.
 * 
 */

public class InfluencerControllerTest {
    private InfluencerController influencerController;

    @Mock
    private InfluencerService influencerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        influencerController = new InfluencerController(influencerService);
    }

    @Test
    public void testRegisterInfluencer() {
        Influencer influencer = new Influencer();
        Long userId = 12345L;
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false); 
        when(influencerService.register(influencer, userId)).thenReturn(influencer);

        ResponseEntity<?> response = influencerController.registerInfluencer(userId, influencer, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(influencer, response.getBody());
    }

    @Test
    public void testRegisterInfluencer_WithValidationErrors() {
        Influencer influencer = new Influencer();
        Long userId = 12345L;
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError error = new FieldError("influencer", "field", "error message");

        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(error));

        ResponseEntity<?> response = influencerController.registerInfluencer(userId, influencer, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);

        Map<String, String> errors = new HashMap<>();
        errors.put("field", "error message");
        assertEquals(errors, response.getBody());
    }

    @Test
    public void testGetInfluencerById() {
        Influencer influencer = new Influencer();
        Long id = 1L;
        when(influencerService.getById(id)).thenReturn(influencer);

        ResponseEntity<Influencer> response = influencerController.getInfluencerById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(influencer, response.getBody());
    }

    @Test
    public void testUpdateInfluencer() {
        Influencer influencer = new Influencer();
        Long id = 1L;
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);  
        when(influencerService.update(id, influencer)).thenReturn(influencer);

        ResponseEntity<?> response = influencerController.updateInfluencer(id, influencer, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(influencer, response.getBody());
    }

    @Test
    public void testUpdateInfluencer_WithValidationErrors() {
        Influencer influencer = new Influencer();
        Long id = 1L;
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError error = new FieldError("influencer", "field", "error message");

        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(error));

        ResponseEntity<?> response = influencerController.updateInfluencer(id, influencer, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);

        Map<String, String> errors = new HashMap<>();
        errors.put("field", "error message");
        assertEquals(errors, response.getBody());
    }

    @Test
    public void testGetAllInfluencers() {
        Influencer influencer1 = new Influencer();
        Influencer influencer2 = new Influencer();
        List<Influencer> influencers = Arrays.asList(influencer1, influencer2);
        when(influencerService.getAll()).thenReturn(influencers);

        ResponseEntity<List<Influencer>> response = influencerController.getAllInfluencers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(influencers, response.getBody());
    }

    @Test
    public void testDeleteInfluencerById() {
        Long id = 1L;

        ResponseEntity<?> response = influencerController.deleteInfluencerById(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(influencerService).deleteById(id);
    }
}
