package com.example.creatorconnectbackend.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import com.example.creatorconnectbackend.models.Organization;
import com.example.creatorconnectbackend.services.OrganizationService;


@RestController

@CrossOrigin

@RequestMapping("/api/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;

    private final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    /**
     * Class constructor for OrganizationController.
     *
     * @param organizationService service class for executing organization operations.
     */
    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * Handles POST requests to register a new organization.
     *
     * @param userId ID of the user registering the organization.
     * @param organization the organization to register.
     * @param bindingResult object holding the result of the validation process.
     * @return response entity containing the registered organization or the validation errors.
     */
    @PostMapping("/register/{userId}")
    public ResponseEntity<?> registerOrganization(@PathVariable Long userId, @RequestBody Organization organization, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Log if there are validation errors during registration.
            logger.info("Validation errors while registering organization");
            // Convert validation errors to a list of error messages.
            List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        logger.info("Attempt to register new organization by user with ID: {}", userId);
        Organization registeredOrganization = organizationService.register(organization, userId);
        logger.info("Organization registered successfully with ID: {}", registeredOrganization.getOrgID());
        return new ResponseEntity<>(registeredOrganization, HttpStatus.CREATED);
    }

    /**
     * Handles GET requests to retrieve an organization by its ID.
     *
     * @param id the ID of the organization to retrieve.
     * @return the requested organization.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable("id") Long id) {
        logger.info("Request to get organization with ID: {}", id);
        Organization organization = organizationService.getById(id);
        return new ResponseEntity<>(organization, HttpStatus.OK);
    }

    /**
     * Handles PUT requests to update an organization.
     *
     * @param id the ID of the organization to update.
     * @param updatedOrganization organization object containing the updated details.
     * @param bindingResult object holding the result of the validation process.
     * @return response entity containing the updated organization or the validation errors.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrganization(@PathVariable("id") Long id, @RequestBody Organization updatedOrganization, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Log if there are validation errors during update.
            logger.info("Validation errors while updating organization");
            // Convert validation errors to a list of error messages.
            List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        logger.info("Attempt to update organization with ID: {}", id);
        Organization organization = organizationService.update(id, updatedOrganization);
        logger.info("Organization with ID: {} updated successfully", id);
        return new ResponseEntity<>(organization, HttpStatus.OK);
    }

    /**
     * Handles GET requests to retrieve all organizations.
     *
     * @return all organizations.
     */
    @GetMapping
    public ResponseEntity<List<Organization>> getAllOrganizations() {
        logger.info("Request to get all organizations");
        List<Organization> organizations = organizationService.getAll();
        return new ResponseEntity<>(organizations, HttpStatus.OK);
    }

    /**
     * Handles DELETE requests to delete an organization by its ID.
     *
     * @param id the ID of the organization to delete.
     * @return a no content response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrganizationById(@PathVariable("id") Long id) {
        logger.info("Attempt to delete organization with ID: {}", id);
        organizationService.deleteById(id);
        logger.info("Organization with ID: {} deleted successfully", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
