package com.restaurant.portal.repository;

import com.restaurant.portal.domain.ThirdPartyFeature;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ThirdPartyFeature entity.
 */
@SuppressWarnings("unused")
public interface ThirdPartyFeatureRepository extends JpaRepository<ThirdPartyFeature,Long> {

}
