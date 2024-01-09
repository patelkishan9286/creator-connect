package com.example.creatorconnectbackend.interfaces;

import com.example.creatorconnectbackend.models.ViewCounter;

import java.util.Map;

public interface ViewCounterServiceInterface {

    
    ViewCounter addView(ViewCounter viewCounter);
    
    
    Map<Long, Integer> getViewsByInfluencerID(Long id);


    Map<String, Integer> getProfileViewsByCompanyType(Long id);
}
