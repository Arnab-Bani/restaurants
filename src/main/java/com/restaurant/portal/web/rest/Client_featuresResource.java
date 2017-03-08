package com.restaurant.portal.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.restaurant.portal.domain.ClientFeatures;
import com.restaurant.portal.service.ClientFeaturesService;
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
 * REST controller for managing ClientFeatures.
 */
@RestController
@RequestMapping("/api")
public class Client_featuresResource {

    private final Logger log = LoggerFactory.getLogger(Client_featuresResource.class);

    private static final String ENTITY_NAME = "client_features";

    private final ClientFeaturesService client_featuresService;

    public Client_featuresResource(ClientFeaturesService client_featuresService) {
        this.client_featuresService = client_featuresService;
    }

    /**
     * POST  /client-features : Create a new client_features.
     *
     * @param client_features the client_features to create
     * @return the ResponseEntity with status 201 (Created) and with body the new client_features, or with status 400 (Bad Request) if the client_features has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-features")
    @Timed
    public ResponseEntity<ClientFeatures> createClient_features(@RequestBody ClientFeatures client_features) throws URISyntaxException {
        log.debug("REST request to save ClientFeatures : {}", client_features);
        if (client_features.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new client_features cannot already have an ID")).body(null);
        }
        ClientFeatures result = client_featuresService.save(client_features);
        return ResponseEntity.created(new URI("/api/client-features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-features : Updates an existing client_features.
     *
     * @param client_features the client_features to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated client_features,
     * or with status 400 (Bad Request) if the client_features is not valid,
     * or with status 500 (Internal Server Error) if the client_features couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-features")
    @Timed
    public ResponseEntity<ClientFeatures> updateClient_features(@RequestBody ClientFeatures client_features) throws URISyntaxException {
        log.debug("REST request to update ClientFeatures : {}", client_features);
        if (client_features.getId() == null) {
            return createClient_features(client_features);
        }
        ClientFeatures result = client_featuresService.save(client_features);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, client_features.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-features : get all the client_features.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of client_features in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/client-features")
    @Timed
    public ResponseEntity<List<ClientFeatures>> getAllClient_features(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ClientFeatures");
        Page<ClientFeatures> page = client_featuresService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/client-features");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /client-features/:id : get the "id" client_features.
     *
     * @param id the id of the client_features to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the client_features, or with status 404 (Not Found)
     */
    @GetMapping("/client-features/{id}")
    @Timed
    public ResponseEntity<ClientFeatures> getClient_features(@PathVariable Long id) {
        log.debug("REST request to get ClientFeatures : {}", id);
        ClientFeatures client_features = client_featuresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(client_features));
    }

    /**
     * DELETE  /client-features/:id : delete the "id" client_features.
     *
     * @param id the id of the client_features to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-features/{id}")
    @Timed
    public ResponseEntity<Void> deleteClient_features(@PathVariable Long id) {
        log.debug("REST request to delete ClientFeatures : {}", id);
        client_featuresService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
