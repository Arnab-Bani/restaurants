package com.restaurant.portal.repository;

import com.restaurant.portal.domain.Client;

import com.restaurant.portal.domain.ClientFeatures;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Client entity.
 */
@SuppressWarnings("unused")
public interface ClientRepository extends JpaRepository<Client,Long> {

    @Query(value = "select c from Client c where c.website=?1")
    Client findByWebsite(String website);

}
