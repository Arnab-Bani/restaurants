package com.restaurant.portal.service.impl;

import com.restaurant.portal.service.ClientMenuService;
import com.restaurant.portal.domain.ClientMenu;
import com.restaurant.portal.repository.ClientMenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing ClientMenu.
 */
@Service
@Transactional
public class ClientMenuServiceImpl implements ClientMenuService{

    private final Logger log = LoggerFactory.getLogger(ClientMenuServiceImpl.class);
    
    private final ClientMenuRepository clientMenuRepository;

    public ClientMenuServiceImpl(ClientMenuRepository clientMenuRepository) {
        this.clientMenuRepository = clientMenuRepository;
    }

    /**
     * Save a clientMenu.
     *
     * @param clientMenu the entity to save
     * @return the persisted entity
     */
    @Override
    public ClientMenu save(ClientMenu clientMenu) {
        log.debug("Request to save ClientMenu : {}", clientMenu);
        ClientMenu result = clientMenuRepository.save(clientMenu);
        return result;
    }

    /**
     *  Get all the clientMenus.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClientMenu> findAll(Pageable pageable) {
        log.debug("Request to get all ClientMenus");
        Page<ClientMenu> result = clientMenuRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one clientMenu by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClientMenu findOne(Long id) {
        log.debug("Request to get ClientMenu : {}", id);
        ClientMenu clientMenu = clientMenuRepository.findOne(id);
        return clientMenu;
    }

    /**
     *  Delete the  clientMenu by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClientMenu : {}", id);
        clientMenuRepository.delete(id);
    }
}
