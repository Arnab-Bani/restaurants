package com.restaurant.portal.repository;

import com.restaurant.portal.domain.ClientFeatures;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ClientFeatures entity.
 */
@SuppressWarnings("unused")
public interface ClientFeaturesRepository extends JpaRepository<ClientFeatures,Long> {

    @Query(value = "select cf from ClientFeatures cf where cf.client.id=?1")
    List<ClientFeatures> findClientFeaturesByClientId(Long clientId);

}
