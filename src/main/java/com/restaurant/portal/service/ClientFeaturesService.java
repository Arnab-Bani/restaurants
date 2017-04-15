package com.restaurant.portal.service;

import com.restaurant.portal.domain.ClientFeatures;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing ClientFeatures.
 */
public interface ClientFeaturesService {

    /**
     * Save a clientFeatures.
     *
     * @param clientFeatures the entity to save
     * @return the persisted entity
     */
    ClientFeatures save(ClientFeatures clientFeatures);

    /**
     *  Get all the clientFeatures.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClientFeatures> findAll(Pageable pageable);

    /**
     *  Get the "id" clientFeatures.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClientFeatures findOne(Long id);

    /**
     *  Delete the "id" clientFeatures.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<ClientFeatures> findClientFeaturesByClientId(Long clientId);
}
