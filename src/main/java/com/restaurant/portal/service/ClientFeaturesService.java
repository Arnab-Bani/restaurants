package com.restaurant.portal.service;

import com.restaurant.portal.domain.ClientFeatures;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ClientFeatures.
 */
public interface ClientFeaturesService {

    /**
     * Save a client_features.
     *
     * @param client_features the entity to save
     * @return the persisted entity
     */
    ClientFeatures save(ClientFeatures client_features);

    /**
     *  Get all the client_features.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClientFeatures> findAll(Pageable pageable);

    /**
     *  Get the "id" client_features.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClientFeatures findOne(Long id);

    /**
     *  Delete the "id" client_features.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
