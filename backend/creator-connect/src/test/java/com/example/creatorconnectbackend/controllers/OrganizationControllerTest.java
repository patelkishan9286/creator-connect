package com.example.creatorconnectbackend.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.example.creatorconnectbackend.models.Organization;
import com.example.creatorconnectbackend.services.OrganizationService;
/**
 * OrganizationControllerTest
 *
 * This class provides unit tests for the OrganizationController.
 * It mocks the service layer interactions and tests controller responses
 * to ensure the correct behavior of the application's organization-related API endpoints.
 * 
 * Functions:
 * 1. setUp() - Initializes the required objects for testing.
 * 2. testRegisterOrganization() - Tests the registration of an organization without validation errors.
 * 3. testRegisterOrganization_WithValidationErrors() - Tests the registration of an organization with validation errors.
 * 4. testGetOrganizationById() - Tests retrieving a single organization by its ID.
 * 5. testUpdateOrganization() - Tests updating an organization's details without validation errors.
 * 6. testUpdateOrganization_WithValidationErrors() - Tests updating an organization's details with validation errors.
 * 7. testGetAllOrganizations() - Tests retrieving all organizations.
 * 8. testDeleteOrganizationById() - Tests deleting an organization by its ID.
 * 
 */

public class OrganizationControllerTest {
    private OrganizationController organizationController;

    @Mock
    private OrganizationService organizationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        organizationController = new OrganizationController(organizationService);
    }

    @Test
    public void testRegisterOrganization() {
        Organization organization = new Organization();
        Long userId = 12345L;
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(organizationService.register(organization, userId)).thenReturn(organization);

        ResponseEntity<?> response = organizationController.registerOrganization(userId, organization, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof Organization);
        assertEquals(organization, response.getBody());
    }

    @Test
    public void testRegisterOrganization_WithValidationErrors() {
        Organization organization = new Organization();
        Long userId = 12345L;
        BindingResult bindingResult = mock(BindingResult.class);
        ObjectError error = new ObjectError("organization", "error message");

        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(Arrays.asList(error));

        ResponseEntity<?> response = organizationController.registerOrganization(userId, organization, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);

        List<String> errors = Arrays.asList("error message");
        assertEquals(errors, response.getBody());
    }

    @Test
    public void testGetOrganizationById() {
        Organization organization = new Organization();
        Long id = 1L;
        when(organizationService.getById(id)).thenReturn(organization);

        ResponseEntity<Organization> response = organizationController.getOrganizationById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(organization, response.getBody());
    }

    @Test
    public void testUpdateOrganization() {
        Organization organization = new Organization();
        Long id = 1L;
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(organizationService.update(id, organization)).thenReturn(organization);

        ResponseEntity<?> response = organizationController.updateOrganization(id, organization, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Organization);
        assertEquals(organization, response.getBody());
    }

    @Test
    public void testUpdateOrganization_WithValidationErrors() {
        Organization organization = new Organization();
        Long id = 1L;
        BindingResult bindingResult = mock(BindingResult.class);
        ObjectError error = new ObjectError("organization", "error message");

        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(Arrays.asList(error));

        ResponseEntity<?> response = organizationController.updateOrganization(id, organization, bindingResult);

        // Verify the result
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);

        List<String> errors = Arrays.asList("error message");
        assertEquals(errors, response.getBody());
    }

    @Test
    public void testGetAllOrganizations() {
        Organization organization1 = new Organization();
        Organization organization2 = new Organization();
        List<Organization> organizations = Arrays.asList(organization1, organization2);
        when(organizationService.getAll()).thenReturn(organizations);

        ResponseEntity<List<Organization>> response = organizationController.getAllOrganizations();

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(organizations, response.getBody());
    }

    @Test
    public void testDeleteOrganizationById() {
        Long id = 1L;

        // Execute the controller method
        ResponseEntity<?> response = organizationController.deleteOrganizationById(id);

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(organizationService).deleteById(id);
    }
}
