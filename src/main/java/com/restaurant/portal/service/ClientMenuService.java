package com.restaurant.portal.service;

import com.restaurant.portal.domain.ClientMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing ClientMenu.
 */
public interface ClientMenuService {

    /**
     * Save a clientMenu.
     *
     * @param clientMenu the entity to save
     * @return the persisted entity
     */
    ClientMenu save(ClientMenu clientMenu);

    /**
     *  Get all the clientMenus.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClientMenu> findAll(Pageable pageable);

    /**
     *  Get the "id" clientMenu.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClientMenu findOne(Long id);

    /**
     *  Delete the "id" clientMenu.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
