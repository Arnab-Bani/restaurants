package com.restaurant.portal.service.impl;

import com.restaurant.portal.service.ClientCategoryService;
import com.restaurant.portal.domain.ClientCategory;
import com.restaurant.portal.repository.ClientCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing ClientCategory.
 */
@Service
@Transactional
public class ClientCategoryServiceImpl implements ClientCategoryService{

    private final Logger log = LoggerFactory.getLogger(ClientCategoryServiceImpl.class);

    private final ClientCategoryRepository clientCategoryRepository;

    public ClientCategoryServiceImpl(ClientCategoryRepository clientCategoryRepository) {
        this.clientCategoryRepository = clientCategoryRepository;
    }

    /**
     * Save a clientCategory.
     *
     * @param clientCategory the entity to save
     * @return the persisted entity
     */
    @Override
    public ClientCategory save(ClientCategory clientCategory) {
        log.debug("Request to save ClientCategory : {}", clientCategory);
        ClientCategory result = clientCategoryRepository.save(clientCategory);
        return result;
    }

    /**
     *  Get all the clientCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClientCategory> findAll(Pageable pageable) {
        log.debug("Request to get all ClientCategories");
        Page<ClientCategory> result = clientCategoryRepository.findAll(pageable);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientCategory> getClientCategoriesByClientId(Long clientId, Pageable pageable) {
        Page<ClientCategory> result = clientCategoryRepository.getClientCategoriesByClientId(clientId, pageable);
        return result;
    }

    /**
     *  Get one clientCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClientCategory findOne(Long id) {
        log.debug("Request to get ClientCategory : {}", id);
        ClientCategory clientCategory = clientCategoryRepository.findOne(id);
        return clientCategory;
    }

    /**
     *  Delete the  clientCategory by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClientCategory : {}", id);
        clientCategoryRepository.delete(id);
    }

    @Override
    public List<ClientCategory> getClientCategoriesBasedOnClientId(Long clientId) {
        return clientCategoryRepository.getClientCategoriesBasedOnClientId(clientId);
    }
}
