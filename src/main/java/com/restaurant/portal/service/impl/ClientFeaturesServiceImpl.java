package com.restaurant.portal.service.impl;

import com.restaurant.portal.domain.ClientFeatures;
import com.restaurant.portal.service.ClientFeaturesService;
import com.restaurant.portal.repository.ClientFeaturesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing ClientFeatures.
 */
@Service
@Transactional
public class ClientFeaturesServiceImpl implements ClientFeaturesService {

    private final Logger log = LoggerFactory.getLogger(ClientFeaturesServiceImpl.class);

    private final ClientFeaturesRepository client_featuresRepository;

    public ClientFeaturesServiceImpl(ClientFeaturesRepository client_featuresRepository) {
        this.client_featuresRepository = client_featuresRepository;
    }

    /**
     * Save a client_features.
     *
     * @param client_features the entity to save
     * @return the persisted entity
     */
    @Override
    public ClientFeatures save(ClientFeatures client_features) {
        log.debug("Request to save ClientFeatures : {}", client_features);
        ClientFeatures result = client_featuresRepository.save(client_features);
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
    public Page<ClientFeatures> findAll(Pageable pageable) {
        log.debug("Request to get all ClientFeatures");
        Page<ClientFeatures> result = client_featuresRepository.findAll(pageable);
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
    public ClientFeatures findOne(Long id) {
        log.debug("Request to get ClientFeatures : {}", id);
        ClientFeatures client_features = client_featuresRepository.findOne(id);
        return client_features;
    }

    /**
     *  Delete the  client_features by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClientFeatures : {}", id);
        client_featuresRepository.delete(id);
    }
}
