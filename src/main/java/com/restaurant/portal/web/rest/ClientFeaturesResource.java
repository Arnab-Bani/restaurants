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
public class ClientFeaturesResource {

    private final Logger log = LoggerFactory.getLogger(ClientFeaturesResource.class);

    private static final String ENTITY_NAME = "clientFeatures";
        
    private final ClientFeaturesService clientFeaturesService;

    public ClientFeaturesResource(ClientFeaturesService clientFeaturesService) {
        this.clientFeaturesService = clientFeaturesService;
    }

    /**
     * POST  /client-features : Create a new clientFeatures.
     *
     * @param clientFeatures the clientFeatures to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientFeatures, or with status 400 (Bad Request) if the clientFeatures has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-features")
    @Timed
    public ResponseEntity<ClientFeatures> createClientFeatures(@RequestBody ClientFeatures clientFeatures) throws URISyntaxException {
        log.debug("REST request to save ClientFeatures : {}", clientFeatures);
        if (clientFeatures.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new clientFeatures cannot already have an ID")).body(null);
        }
        ClientFeatures result = clientFeaturesService.save(clientFeatures);
        return ResponseEntity.created(new URI("/api/client-features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-features : Updates an existing clientFeatures.
     *
     * @param clientFeatures the clientFeatures to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientFeatures,
     * or with status 400 (Bad Request) if the clientFeatures is not valid,
     * or with status 500 (Internal Server Error) if the clientFeatures couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-features")
    @Timed
    public ResponseEntity<ClientFeatures> updateClientFeatures(@RequestBody ClientFeatures clientFeatures) throws URISyntaxException {
        log.debug("REST request to update ClientFeatures : {}", clientFeatures);
        if (clientFeatures.getId() == null) {
            return createClientFeatures(clientFeatures);
        }
        ClientFeatures result = clientFeaturesService.save(clientFeatures);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientFeatures.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-features : get all the clientFeatures.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clientFeatures in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/client-features")
    @Timed
    public ResponseEntity<List<ClientFeatures>> getAllClientFeatures(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ClientFeatures");
        Page<ClientFeatures> page = clientFeaturesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/client-features");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /client-features/:id : get the "id" clientFeatures.
     *
     * @param id the id of the clientFeatures to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientFeatures, or with status 404 (Not Found)
     */
    @GetMapping("/client-features/{id}")
    @Timed
    public ResponseEntity<ClientFeatures> getClientFeatures(@PathVariable Long id) {
        log.debug("REST request to get ClientFeatures : {}", id);
        ClientFeatures clientFeatures = clientFeaturesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clientFeatures));
    }

    /**
     * DELETE  /client-features/:id : delete the "id" clientFeatures.
     *
     * @param id the id of the clientFeatures to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-features/{id}")
    @Timed
    public ResponseEntity<Void> deleteClientFeatures(@PathVariable Long id) {
        log.debug("REST request to delete ClientFeatures : {}", id);
        clientFeaturesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
