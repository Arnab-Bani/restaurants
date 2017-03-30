package com.restaurant.portal.service;

import com.restaurant.portal.domain.ThirdPartyFeature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing ThirdPartyFeature.
 */
public interface ThirdPartyFeatureService {

    /**
     * Save a thirdPartyFeature.
     *
     * @param thirdPartyFeature the entity to save
     * @return the persisted entity
     */
    ThirdPartyFeature save(ThirdPartyFeature thirdPartyFeature);

    /**
     *  Get all the thirdPartyFeatures.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ThirdPartyFeature> findAll(Pageable pageable);

    /**
     *  Get the "id" thirdPartyFeature.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ThirdPartyFeature findOne(Long id);

    /**
     *  Delete the "id" thirdPartyFeature.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
