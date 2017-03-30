package com.restaurant.portal.service.impl;

import com.restaurant.portal.service.ThirdPartyFeatureService;
import com.restaurant.portal.domain.ThirdPartyFeature;
import com.restaurant.portal.repository.ThirdPartyFeatureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing ThirdPartyFeature.
 */
@Service
@Transactional
public class ThirdPartyFeatureServiceImpl implements ThirdPartyFeatureService{

    private final Logger log = LoggerFactory.getLogger(ThirdPartyFeatureServiceImpl.class);
    
    private final ThirdPartyFeatureRepository thirdPartyFeatureRepository;

    public ThirdPartyFeatureServiceImpl(ThirdPartyFeatureRepository thirdPartyFeatureRepository) {
        this.thirdPartyFeatureRepository = thirdPartyFeatureRepository;
    }

    /**
     * Save a thirdPartyFeature.
     *
     * @param thirdPartyFeature the entity to save
     * @return the persisted entity
     */
    @Override
    public ThirdPartyFeature save(ThirdPartyFeature thirdPartyFeature) {
        log.debug("Request to save ThirdPartyFeature : {}", thirdPartyFeature);
        ThirdPartyFeature result = thirdPartyFeatureRepository.save(thirdPartyFeature);
        return result;
    }

    /**
     *  Get all the thirdPartyFeatures.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ThirdPartyFeature> findAll(Pageable pageable) {
        log.debug("Request to get all ThirdPartyFeatures");
        Page<ThirdPartyFeature> result = thirdPartyFeatureRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one thirdPartyFeature by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ThirdPartyFeature findOne(Long id) {
        log.debug("Request to get ThirdPartyFeature : {}", id);
        ThirdPartyFeature thirdPartyFeature = thirdPartyFeatureRepository.findOne(id);
        return thirdPartyFeature;
    }

    /**
     *  Delete the  thirdPartyFeature by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ThirdPartyFeature : {}", id);
        thirdPartyFeatureRepository.delete(id);
    }
}
