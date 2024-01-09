package com.example.creatorconnectbackend.controllers;

import com.example.creatorconnectbackend.controllers.ConnectionRequestController;
import com.example.creatorconnectbackend.models.ConnectionRequest;
import com.example.creatorconnectbackend.models.RequestStatus;
import com.example.creatorconnectbackend.services.ConnectionRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
/**
 * ConnectionRequestControllerTests.java
 * 
 * Description:
 * This class contains test cases for the ConnectionRequestController.
 * The tests utilize Mockito framework for mocking dependencies.
 * 
 * Functions:
 * - setup(): Initializes mock objects and sets up the test environment.
 * 
 * - testCreateRequest_ValidConnectionRequest_ReturnsCreatedRequest(): Tests the scenario where a valid connection request is made.
 * 
 * - testCreateRequest_InvalidConnectionRequest_ReturnsBadRequest(): Tests the scenario where an invalid connection request is made.
 * 
 * - testGetConnectionById_ValidRequestId_ReturnsConnectionRequest(): Tests fetching a connection request by its ID.
 * 
 * - testUpdateConnectionRequestStatus_ValidRequestIdAndPayload_ReturnsSuccessMessage(): Tests updating the status of a connection request with a valid ID and payload.
 * 
 * - testUpdateConnectionRequestStatus_InvalidPayload_ReturnsBadRequest(): Tests updating the status of a connection request with an invalid payload.
 * 
 * - testGetRequestsByInfluencerID_ValidInfluencerId_ReturnsConnectionRequests(): Tests fetching connection requests by influencer ID.
 * 
 * - testGetRequestsByOrganizationID_ValidOrganizationId_ReturnsConnectionRequests(): Tests fetching connection requests by organization ID.
 * 
 * - testGetRequestsByStatus_ValidOrganizationIdAndStatus_ReturnsConnectionRequests(): Tests fetching connection requests by their status.
 * 
 * - testGetAllRequests_ValidRequest_ReturnsAllConnectionRequests(): Tests fetching all connection requests.
 * 
 * - testDeleteByID_ValidRequestId_ReturnsNoContent(): Tests deleting a connection request by its ID.
 * 
 * - testUpdateRequestMessage_ValidRequestIdAndMessage_ReturnsUpdatedConnectionRequest(): Tests updating the message of a connection request.
 */

class ConnectionRequestControllerTests {

    @Mock
    private ConnectionRequestService connectionRequestService;

    private ConnectionRequestController connectionRequestController;

    @BeforeEach
    void setup() {
        connectionRequestService = mock(ConnectionRequestService.class);
        connectionRequestController = new ConnectionRequestController(connectionRequestService);
    }

    @Test
    void testCreateRequest_ValidConnectionRequest_ReturnsCreatedRequest() {
        ConnectionRequest connectionRequest = mock(ConnectionRequest.class);
        ConnectionRequest createdRequest = mock(ConnectionRequest.class);

        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(connectionRequestService.createRequest(connectionRequest)).thenReturn(createdRequest);

        ResponseEntity<?> response = connectionRequestController.createRequest(connectionRequest, bindingResult);

        verify(connectionRequestService).createRequest(connectionRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof ConnectionRequest);
        assertEquals(createdRequest, response.getBody());
    }
    
	private void assertTrue(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
	@Test
	void testCreateRequest_InvalidConnectionRequest_ReturnsBadRequest() {
	    ConnectionRequest connectionRequest = mock(ConnectionRequest.class);
	    BindingResult bindingResult = mock(BindingResult.class);
	    List<ObjectError> allErrors = Arrays.asList(new ObjectError("test", "test"));

	    when(bindingResult.hasErrors()).thenReturn(true);
	    when(bindingResult.getAllErrors()).thenReturn(allErrors);

	    ResponseEntity<?> response = connectionRequestController.createRequest(connectionRequest, bindingResult);

	    verify(connectionRequestService, never()).createRequest(connectionRequest);
	    verifyNoInteractions(connectionRequestService);

	    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	    assertTrue(response.getBody() instanceof List<?>);
	    assertEquals(allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()), response.getBody());
	}


	@Test
    void testGetConnectionById_ValidRequestId_ReturnsConnectionRequest() {
        ConnectionRequest connectionRequest = mock(ConnectionRequest.class);

        when(connectionRequestService.getConnectionRequestByID(1L)).thenReturn(connectionRequest);

        ResponseEntity<ConnectionRequest> response = connectionRequestController.getConnectionById(1L);

        verify(connectionRequestService).getConnectionRequestByID(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(connectionRequest, response.getBody());
    }

    @Test
    void testUpdateConnectionRequestStatus_ValidRequestIdAndPayload_ReturnsSuccessMessage() {
        Long requestId = 1L;
        Map<String, String> payload = new HashMap<>();
        payload.put("requestStatus", "Accepted");

        ResponseEntity<String> response = connectionRequestController.updateConnectionRequestStatus(requestId, payload);

        verify(connectionRequestService).updateStatus(requestId, RequestStatus.Accepted);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Connection request updated successfully", response.getBody());
    }

    @Test
    void testUpdateConnectionRequestStatus_InvalidPayload_ReturnsBadRequest() {
        Long requestId = 1L;
        Map<String, String> payload = new HashMap<>();

        ResponseEntity<String> response = connectionRequestController.updateConnectionRequestStatus(requestId, payload);

        verify(connectionRequestService, never()).updateStatus(anyLong(), any(RequestStatus.class));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request payload", response.getBody());
    }

    @Test
    void testGetRequestsByInfluencerID_ValidInfluencerId_ReturnsConnectionRequests() {
        List<ConnectionRequest> requests = Arrays.asList(mock(ConnectionRequest.class), mock(ConnectionRequest.class));

        when(connectionRequestService.getRequestsByInfluencerID(1L)).thenReturn(requests);

        ResponseEntity<List<ConnectionRequest>> response = connectionRequestController.getRequestsByInfluencerID(1L);

        verify(connectionRequestService).getRequestsByInfluencerID(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(requests, response.getBody());
    }

    @Test
    void testGetRequestsByOrganizationID_ValidOrganizationId_ReturnsConnectionRequests() {
        List<ConnectionRequest> requests = Arrays.asList(mock(ConnectionRequest.class), mock(ConnectionRequest.class));

        when(connectionRequestService.getRequestsByOrgID(1L)).thenReturn(requests);

        ResponseEntity<List<ConnectionRequest>> response = connectionRequestController.getRequestsByOrganizationID(1L);

        verify(connectionRequestService).getRequestsByOrgID(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(requests, response.getBody());
    }

    @Test
    void testGetRequestsByStatus_ValidOrganizationIdAndStatus_ReturnsConnectionRequests() {
        List<ConnectionRequest> requests = Arrays.asList(mock(ConnectionRequest.class), mock(ConnectionRequest.class));

        when(connectionRequestService.getRequestsByStatus(1L, "Pending")).thenReturn(requests);

        ResponseEntity<List<ConnectionRequest>> response = connectionRequestController.getRequestsByStatus(1L, "Pending");

        verify(connectionRequestService).getRequestsByStatus(1L, "Pending");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(requests, response.getBody());
    }

    @Test
    void testGetAllRequests_ValidRequest_ReturnsAllConnectionRequests() {
        List<ConnectionRequest> requests = Arrays.asList(mock(ConnectionRequest.class), mock(ConnectionRequest.class));

        when(connectionRequestService.getAllRequests()).thenReturn(requests);

        List<ConnectionRequest> response = connectionRequestController.getAllRequests();

        verify(connectionRequestService).getAllRequests();

        assertEquals(requests, response);
    }
    @Test
    void testDeleteByID_ValidRequestId_ReturnsNoContent() {
        ResponseEntity<String> response = connectionRequestController.deleteByID(1L);

        verify(connectionRequestService).deleteByID(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testUpdateRequestMessage_ValidRequestIdAndMessage_ReturnsUpdatedConnectionRequest() {
        ConnectionRequest updatedRequest = mock(ConnectionRequest.class);

        Map<String, String> map = new HashMap<>();
        map.put("Message", "Hello!");
        when(connectionRequestService.updateMessage(1L, map)).thenReturn(updatedRequest);

        ResponseEntity<ConnectionRequest> response = connectionRequestController.updateRequestMessage(1L, map);


        verify(connectionRequestService).updateMessage(1L, map);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedRequest, response.getBody());
    }
}


