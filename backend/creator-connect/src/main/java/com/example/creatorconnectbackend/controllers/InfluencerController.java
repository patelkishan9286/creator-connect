package com.example.creatorconnectbackend.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.example.creatorconnectbackend.models.Influencer;
import com.example.creatorconnectbackend.services.InfluencerService;


@RestController

@CrossOrigin
@RequestMapping("/api/influencers")
public class InfluencerController {

    private final InfluencerService influencerService;

    private final Logger logger = LoggerFactory.getLogger(InfluencerController.class);

    /**
     * Class constructor for InfluencerController.
     *
     * @param influencerService service class for executing influencer operations.
     */
    @Autowired
    public InfluencerController(InfluencerService influencerService) {
        this.influencerService = influencerService;
    }

    /**
     * Handles POST requests to register a new influencer.
     *
     * @param userId ID of the user registering the influencer.
     * @param influencer the influencer to register.
     * @param bindingResult object holding the result of the validation process.
     * @return response entity containing the registered influencer or the validation errors.
     */
    @PostMapping("/register/{userId}")
    public ResponseEntity<?> registerInfluencer(@PathVariable Long userId, @RequestBody Influencer influencer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("Validation errors while registering influencer by user with ID: {}", userId);

            // Convert validation errors into a map for a more structured response.
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        logger.info("Attempt to register new influencer by user with ID: {}", userId);
        Influencer registeredInfluencer = influencerService.register(influencer, userId);
        logger.info("Influencer registered successfully with ID: {}", registeredInfluencer.getInfluencerID());
        return new ResponseEntity<>(registeredInfluencer, HttpStatus.CREATED);
    }

    /**
     * Handles GET requests to retrieve an influencer by its ID.
     *
     * @param id the ID of the influencer to retrieve.
     * @return the requested influencer.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Influencer> getInfluencerById(@PathVariable("id") Long id) {
        logger.info("Request to get influencer with ID: {}", id);
        Influencer influencer = influencerService.getById(id);
        return new ResponseEntity<>(influencer, HttpStatus.OK);
    }

    /**
     * Handles PUT requests to update an influencer.
     *
     * @param id the ID of the influencer to update.
     * @param updatedInfluencer influencer object containing the updated details.
     * @param bindingResult object holding the result of the validation process.
     * @return response entity containing the updated influencer or the validation errors.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateInfluencer(@PathVariable("id") Long id, @RequestBody Influencer updatedInfluencer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("Validation errors while updating influencer with ID: {}", id);

            // Convert validation errors into a map for a more structured response.
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        logger.info("Attempt to update influencer with ID: {}", id);
        Influencer influencer = influencerService.update(id, updatedInfluencer);
        logger.info("Influencer with ID: {} updated successfully", id);
        return new ResponseEntity<>(influencer, HttpStatus.OK);
    }

    /**
     * Handles GET requests to retrieve all influencer.
     *
     * @return all influencer.
     */
    @GetMapping
    public ResponseEntity<List<Influencer>> getAllInfluencers() {
        logger.info("Request to get all influencers");
        List<Influencer> influencers = influencerService.getAll();
        return new ResponseEntity<>(influencers, HttpStatus.OK);
    }

    /**
     * Handles DELETE requests to delete an influencer by its ID.
     *
     * @param id the ID of the influencer to delete.
     * @return a no content response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInfluencerById(@PathVariable("id") Long id) {
        logger.info("Attempt to delete influencer with ID: {}", id);
        influencerService.deleteById(id);
        logger.info("Influencer with ID: {} deleted successfully", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
