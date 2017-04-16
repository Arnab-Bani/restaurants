package com.restaurant.portal.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.restaurant.portal.domain.ClientCategory;
import com.restaurant.portal.service.ClientCategoryService;
import com.restaurant.portal.web.rest.util.HeaderUtil;
import com.restaurant.portal.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ClientCategory.
 */
@RestController
@RequestMapping("/api")
public class ClientCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ClientCategoryResource.class);

    private static final String ENTITY_NAME = "clientCategory";

    private final ClientCategoryService clientCategoryService;

    public ClientCategoryResource(ClientCategoryService clientCategoryService) {
        this.clientCategoryService = clientCategoryService;
    }

    /**
     * POST  /client-categories : Create a new clientCategory.
     *
     * @param clientCategory the clientCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientCategory, or with status 400 (Bad Request) if the clientCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-categories")
    @Timed
    public ResponseEntity<ClientCategory> createClientCategory(@RequestBody ClientCategory clientCategory) throws URISyntaxException {
        log.debug("REST request to save ClientCategory : {}", clientCategory);
        if (clientCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new clientCategory cannot already have an ID")).body(null);
        }
        ClientCategory result = clientCategoryService.save(clientCategory);
        return ResponseEntity.created(new URI("/api/client-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-categories : Updates an existing clientCategory.
     *
     * @param clientCategory the clientCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientCategory,
     * or with status 400 (Bad Request) if the clientCategory is not valid,
     * or with status 500 (Internal Server Error) if the clientCategory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-categories")
    @Timed
    public ResponseEntity<ClientCategory> updateClientCategory(@RequestBody ClientCategory clientCategory) throws URISyntaxException {
        log.debug("REST request to update ClientCategory : {}", clientCategory);
        if (clientCategory.getId() == null) {
            return createClientCategory(clientCategory);
        }
        ClientCategory result = clientCategoryService.save(clientCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-categories : get all the clientCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clientCategories in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/client-categories")
    @Timed
    public ResponseEntity<List<ClientCategory>> getAllClientCategories(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ClientCategories");
        Page<ClientCategory> page = clientCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/client-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /client-categories/:id : get the "id" clientCategory.
     *
     * @param id the id of the clientCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientCategory, or with status 404 (Not Found)
     */
    @GetMapping("/client-categories/{id}")
    @Timed
    public ResponseEntity<ClientCategory> getClientCategory(@PathVariable Long id) {
        log.debug("REST request to get ClientCategory : {}", id);
        ClientCategory clientCategory = clientCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clientCategory));
    }

    @GetMapping("/client-categories/client/{clientId}")
    @Timed
    public ResponseEntity<List<ClientCategory>> getClientCategoriesBasedOnClientId(@PathVariable Long clientId) {
        log.debug("REST request to get ClientCategories Based on ClientId : {}", clientId);
        List<ClientCategory> clientCategories = clientCategoryService.getClientCategoriesBasedOnClientId(clientId);
        return ResponseEntity.ok(clientCategories);
    }

    /**
     * DELETE  /client-categories/:id : delete the "id" clientCategory.
     *
     * @param id the id of the clientCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteClientCategory(@PathVariable Long id) {
        log.debug("REST request to delete ClientCategory : {}", id);
        clientCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
