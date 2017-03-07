package com.restaurant.portal.service;

import com.restaurant.portal.domain.Client_features;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Client_features.
 */
public interface Client_featuresService {

    /**
     * Save a client_features.
     *
     * @param client_features the entity to save
     * @return the persisted entity
     */
    Client_features save(Client_features client_features);

    /**
     *  Get all the client_features.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Client_features> findAll(Pageable pageable);

    /**
     *  Get the "id" client_features.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Client_features findOne(Long id);

    /**
     *  Delete the "id" client_features.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
