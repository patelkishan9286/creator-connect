package com.example.creatorconnectbackend.interfaces;

import com.example.creatorconnectbackend.models.ConnectionRequest;
import com.example.creatorconnectbackend.models.RequestStatus;

import java.util.Map;

public interface ConnectionRequestServiceInterface {

    ConnectionRequest createRequest(ConnectionRequest connectionRequest);

    
    ConnectionRequest getConnectionRequestByID(Long requestID);

    
    ConnectionRequest updateStatus(Long id, RequestStatus newStatus);

    
    ConnectionRequest updateMessage(Long id, Map<String, String> message);

    
    void deleteByID(Long id);
}
