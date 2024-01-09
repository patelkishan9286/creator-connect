package com.example.creatorconnectbackend.interfaces;

import java.util.List;
import com.example.creatorconnectbackend.models.Influencer;

public interface InfluencerServiceInterface {


    Influencer register(Influencer influencer, Long userId);


    Influencer getById(Long id);

    Influencer update(Long id, Influencer updatedInfluencer);

    List<Influencer> getAll();

    void deleteById(Long id);
}
