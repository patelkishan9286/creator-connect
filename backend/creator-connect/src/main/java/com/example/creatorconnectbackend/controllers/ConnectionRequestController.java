package com.example.creatorconnectbackend.controllers;

import com.example.creatorconnectbackend.models.ConnectionRequest;
import com.example.creatorconnectbackend.models.RequestStatus;
import com.example.creatorconnectbackend.services.ConnectionRequestService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/connectionReq")
public class ConnectionRequestController {

    private final ConnectionRequestService connectionRequestService;

    private final Logger logger = LoggerFactory.getLogger(ConnectionRequestController.class);

    /**
     * Class constructor for ConnectionRequestController.
     *
     * @param connectionRequestService service class for executing connection request operations.
     */
    @Autowired
    public ConnectionRequestController(ConnectionRequestService connectionRequestService) {
        this.connectionRequestService = connectionRequestService;
    }

    /**
     * Handles POST requests to create a new connection request.
     *
     * @param connectionRequest the connection request to create.
     * @param bindingResult object holding the result of the validation process.
     * @return response entity containing the created connection request or the validation errors.
     */
    @PostMapping("/create")
    public ResponseEntity<?> createRequest(@Valid @RequestBody ConnectionRequest connectionRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors while creating connection request");
            List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        logger.info("Creating new connection request");
        ConnectionRequest createdRequest = connectionRequestService.createRequest(connectionRequest);
        logger.info("Created connection request with ID: {}", createdRequest.getRequestID());
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    /**
     * Handles GET requests to retrieve a connection request by its ID.
     *
     * @param requestId the ID of the connection request to retrieve.
     * @return the requested connection request.
     */
    @GetMapping("/getByRequestID/{requestId}")
    public ResponseEntity<ConnectionRequest> getConnectionById(@PathVariable("requestId") Long requestId) {
        logger.info("Getting connection request with ID: {}", requestId);
        ConnectionRequest connectionRequest = connectionRequestService.getConnectionRequestByID(requestId);
        return ResponseEntity.ok(connectionRequest);
    }

    /**
     * Handles PUT requests to update the status of a connection request.
     *
     * @param requestId the ID of the connection request to update.
     * @param payload request body containing the new status.
     * @return response entity containing a success message or an error message.
     */
    @PutMapping("/update/{requestId}")
    public ResponseEntity<String> updateConnectionRequestStatus(
            @PathVariable Long requestId,
            @RequestBody Map<String, String> payload) {

        String requestStatus = payload.get("requestStatus");

        if (requestStatus != null) {
            logger.info("Updating connection request with ID: {}", requestId);
            connectionRequestService.updateStatus(requestId, RequestStatus.valueOf(requestStatus));
            logger.info("Updated connection request with ID: {}", requestId);
            return ResponseEntity.ok("Connection request updated successfully");
        } else {
            logger.warn("Invalid request payload for updating connection request");
            return ResponseEntity.badRequest().body("Invalid request payload");
        }
    }

    /**
     * Handles GET requests to retrieve all connection requests by the influencer ID.
     *
     * @param influencerID the ID of the influencer.
     * @return all connection requests of the specified influencer.
     */
    @GetMapping("/influencer/getByID/{id}")
    public ResponseEntity<List<ConnectionRequest>> getRequestsByInfluencerID(@PathVariable("id") Long influencerID) {
        List<ConnectionRequest> requests = connectionRequestService.getRequestsByInfluencerID(influencerID);
        return ResponseEntity.ok(requests);
    }

    /**
     * Handles GET requests to retrieve all connection requests by the organization ID.
     *
     * @param orgID the ID of the organization.
     * @return all connection requests of the specified organization.
     */
    @GetMapping("/organization/getByID/{id}")
    public ResponseEntity<List<ConnectionRequest>> getRequestsByOrganizationID(@PathVariable("id") Long orgID) {
        List<ConnectionRequest> requests = connectionRequestService.getRequestsByOrgID(orgID);
        return ResponseEntity.ok(requests);
    }

    /**
     * Handles GET requests to retrieve all connection requests by their status.
     *
     * @param orgID the ID of the organization.
     * @param status the status of the connection requests.
     * @return all connection requests of the specified organization with the specified status.
     */
    @GetMapping("/organization/{orgID}/status/{status}")
    public ResponseEntity<List<ConnectionRequest>> getRequestsByStatus(@PathVariable("orgID") Long orgID, @PathVariable("status") String status) {
        List<ConnectionRequest> requests = connectionRequestService.getRequestsByStatus(orgID, status);
        return ResponseEntity.ok(requests);
    }

    /**
     * Handles GET requests to retrieve all connection requests.
     *
     * @return all connection requests.
     */
    @GetMapping("/getAll")
    public List<ConnectionRequest> getAllRequests() {
        List<ConnectionRequest> allRequests = connectionRequestService.getAllRequests();
        return ResponseEntity.ok(allRequests).getBody();
    }

    /**
     * Handles DELETE requests to delete a connection request by its ID.
     *
     * @param id the ID of the connection request to delete.
     * @return a no content response.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteByID(@PathVariable("id") Long id) {
        logger.info("Deleting connection request with ID: {}", id);
        connectionRequestService.deleteByID(id);
        logger.info("Deleted connection request with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Handles PUT requests to update the message of a connection request.
     *
     * @param requestId the ID of the connection request to update.
     * @param map request body containing the new message.
     * @return the updated connection request.
     */
    @PutMapping("/updateMessage/{id}")
    public ResponseEntity<ConnectionRequest> updateRequestMessage(@PathVariable("id") Long requestId, @RequestBody Map<String, String> map) {
        logger.info("Updating connection request message with ID: {}", requestId);
        ConnectionRequest updatedRequest = connectionRequestService.updateMessage(requestId, map);
        logger.info("Updated connection request message with ID: {}", requestId);
        return ResponseEntity.ok(updatedRequest);
    }
}
