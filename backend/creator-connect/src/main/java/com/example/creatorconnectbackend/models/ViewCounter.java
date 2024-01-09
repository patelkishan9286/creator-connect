package com.example.creatorconnectbackend.models;

import java.util.Date;

public class ViewCounter {


    private Long InfluencerID;

    private Long OrgId;

    private Date Date;

    // Getters and Setters

    /**
     * @return the unique identifier of the influencer.
     */
    public Long getInfluencerId() {
        return InfluencerID;
    }

    /**
     * @param InfluencerID the unique identifier to set for the influencer.
     */
    public void setInfluencerId(Long InfluencerID) {
        this.InfluencerID = InfluencerID;
    }

    /**
     * @return the unique identifier of the organization.
     */
    public Long getOrgId() {
        return OrgId;
    }

    /**
     * @param OrgId the unique identifier to set for the organization.
     */
    public void setOrgId(Long OrgId) {
        this.OrgId = OrgId;
    }

    /**
     * @return the date when the interaction occurred.
     */
    public Date getDate() {
        return Date;
    }

    /**
     * @param Date the date to set for when the interaction occurred.
     */
    public void setDate(Date Date) {
        this.Date = Date;
    }
}
