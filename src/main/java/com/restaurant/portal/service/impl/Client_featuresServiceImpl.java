package com.restaurant.portal.service.impl;

import com.restaurant.portal.service.Client_featuresService;
import com.restaurant.portal.domain.Client_features;
import com.restaurant.portal.repository.Client_featuresRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Client_features.
 */
@Service
@Transactional
public class Client_featuresServiceImpl implements Client_featuresService{

    private final Logger log = LoggerFactory.getLogger(Client_featuresServiceImpl.class);
    
    private final Client_featuresRepository client_featuresRepository;

    public Client_featuresServiceImpl(Client_featuresRepository client_featuresRepository) {
        this.client_featuresRepository = client_featuresRepository;
    }

    /**
     * Save a client_features.
     *
     * @param client_features the entity to save
     * @return the persisted entity
     */
    @Override
    public Client_features save(Client_features client_features) {
        log.debug("Request to save Client_features : {}", client_features);
        Client_features result = client_featuresRepository.save(client_features);
        return result;
    }

    /**
     *  Get all the client_features.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Client_features> findAll(Pageable pageable) {
        log.debug("Request to get all Client_features");
        Page<Client_features> result = client_featuresRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one client_features by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Client_features findOne(Long id) {
        log.debug("Request to get Client_features : {}", id);
        Client_features client_features = client_featuresRepository.findOne(id);
        return client_features;
    }

    /**
     *  Delete the  client_features by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Client_features : {}", id);
        client_featuresRepository.delete(id);
    }
}
