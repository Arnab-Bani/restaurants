package com.restaurant.portal.repository;

import com.restaurant.portal.domain.Client_features;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Client_features entity.
 */
@SuppressWarnings("unused")
public interface Client_featuresRepository extends JpaRepository<Client_features,Long> {

}
