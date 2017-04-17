package com.restaurant.portal.repository;

import com.restaurant.portal.domain.ClientCategory;

import com.restaurant.portal.domain.ClientFeatures;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the ClientCategory entity.
 */
@SuppressWarnings("unused")
public interface ClientCategoryRepository extends JpaRepository<ClientCategory,Long> {

    @Query(value = "select cc from ClientCategory cc where cc.client.id=?1")
    List<ClientCategory> getClientCategoriesBasedOnClientId(Long clientId);

    @Query(value = "select cc from ClientCategory cc where cc.client.id=:clientId")
    Page<ClientCategory> getClientCategoriesByClientId(@Param("clientId") Long clientId, Pageable pageable);
}
