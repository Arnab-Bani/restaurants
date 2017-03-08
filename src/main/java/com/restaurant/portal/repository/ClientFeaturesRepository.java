package com.restaurant.portal.repository;

import com.restaurant.portal.domain.ClientFeatures;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the ClientFeatures entity.
 */
@SuppressWarnings("unused")
public interface ClientFeaturesRepository extends JpaRepository<ClientFeatures,Long> {

}
