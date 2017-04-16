package com.restaurant.portal.repository;

import com.restaurant.portal.domain.ClientCategory;

import com.restaurant.portal.domain.ClientFeatures;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ClientCategory entity.
 */
@SuppressWarnings("unused")
public interface ClientCategoryRepository extends JpaRepository<ClientCategory,Long> {

    @Query(value = "select cc from ClientCategory cc where cc.client.id=?1")
    List<ClientCategory> getClientCategoriesBasedOnClientId(Long clientId);
}
