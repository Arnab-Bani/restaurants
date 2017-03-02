package com.restaurant.portal.service;

import com.restaurant.portal.domain.ClientCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing ClientCategory.
 */
public interface ClientCategoryService {

    /**
     * Save a clientCategory.
     *
     * @param clientCategory the entity to save
     * @return the persisted entity
     */
    ClientCategory save(ClientCategory clientCategory);

    /**
     *  Get all the clientCategories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClientCategory> findAll(Pageable pageable);

    /**
     *  Get the "id" clientCategory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClientCategory findOne(Long id);

    /**
     *  Delete the "id" clientCategory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
