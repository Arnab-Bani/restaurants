package com.restaurant.portal.service.impl;

import com.restaurant.portal.service.ClientFeaturesService;
import com.restaurant.portal.domain.ClientFeatures;
import com.restaurant.portal.repository.ClientFeaturesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing ClientFeatures.
 */
@Service
@Transactional
public class ClientFeaturesServiceImpl implements ClientFeaturesService{

    private final Logger log = LoggerFactory.getLogger(ClientFeaturesServiceImpl.class);
    
    private final ClientFeaturesRepository clientFeaturesRepository;

    public ClientFeaturesServiceImpl(ClientFeaturesRepository clientFeaturesRepository) {
        this.clientFeaturesRepository = clientFeaturesRepository;
    }

    /**
     * Save a clientFeatures.
     *
     * @param clientFeatures the entity to save
     * @return the persisted entity
     */
    @Override
    public ClientFeatures save(ClientFeatures clientFeatures) {
        log.debug("Request to save ClientFeatures : {}", clientFeatures);
        ClientFeatures result = clientFeaturesRepository.save(clientFeatures);
        return result;
    }

    /**
     *  Get all the clientFeatures.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClientFeatures> findAll(Pageable pageable) {
        log.debug("Request to get all ClientFeatures");
        Page<ClientFeatures> result = clientFeaturesRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one clientFeatures by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClientFeatures findOne(Long id) {
        log.debug("Request to get ClientFeatures : {}", id);
        ClientFeatures clientFeatures = clientFeaturesRepository.findOne(id);
        return clientFeatures;
    }

    /**
     *  Delete the  clientFeatures by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClientFeatures : {}", id);
        clientFeaturesRepository.delete(id);
    }
}
