package com.example.creatorconnectbackend.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ConnectionRequest {

    private Long requestID;

    private Long orgID;

    private Long influencerID;

    @Size(max = 500)
    private String requestMessage;

    @NotNull
    private RequestStatus requestStatus;

    public Long getRequestID() {
        return requestID;
    }

    public void setRequestID(Long requestID) {
        this.requestID = requestID;
    }

    public Long getOrgID() {
        return orgID;
    }

    public void setOrgID(Long orgID) {
        this.orgID = orgID;
    }

    public Long getInfluencerID() {
        return influencerID;
    }

    public void setInfluencerID(Long influencerID) {
        this.influencerID = influencerID;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }    
}
