package com.example.creatorconnectbackend.interfaces;

import java.util.List;
import com.example.creatorconnectbackend.models.Organization;

public interface OrganizationServiceInterface {


    Organization register(Organization organization, Long userId);
    Organization getById(Long id);
    Organization update(Long id, Organization updatedOrganization);
    List<Organization> getAll();
    void deleteById(Long id);
}
