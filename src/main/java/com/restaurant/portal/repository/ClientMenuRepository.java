package com.restaurant.portal.repository;

import com.restaurant.portal.domain.ClientMenu;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ClientMenu entity.
 */
@SuppressWarnings("unused")
public interface ClientMenuRepository extends JpaRepository<ClientMenu,Long> {

}
