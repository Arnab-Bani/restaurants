package com.restaurant.portal.repository;

import com.restaurant.portal.domain.ClientCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ClientCategory entity.
 */
@SuppressWarnings("unused")
public interface ClientCategoryRepository extends JpaRepository<ClientCategory,Long> {

}
