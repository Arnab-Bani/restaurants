package com.restaurant.portal.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.restaurant.portal.domain.ThirdPartyFeature;
import com.restaurant.portal.service.ThirdPartyFeatureService;
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
 * REST controller for managing ThirdPartyFeature.
 */
@RestController
@RequestMapping("/api")
public class ThirdPartyFeatureResource {

    private final Logger log = LoggerFactory.getLogger(ThirdPartyFeatureResource.class);

    private static final String ENTITY_NAME = "thirdPartyFeature";
        
    private final ThirdPartyFeatureService thirdPartyFeatureService;

    public ThirdPartyFeatureResource(ThirdPartyFeatureService thirdPartyFeatureService) {
        this.thirdPartyFeatureService = thirdPartyFeatureService;
    }

    /**
     * POST  /third-party-features : Create a new thirdPartyFeature.
     *
     * @param thirdPartyFeature the thirdPartyFeature to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thirdPartyFeature, or with status 400 (Bad Request) if the thirdPartyFeature has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/third-party-features")
    @Timed
    public ResponseEntity<ThirdPartyFeature> createThirdPartyFeature(@RequestBody ThirdPartyFeature thirdPartyFeature) throws URISyntaxException {
        log.debug("REST request to save ThirdPartyFeature : {}", thirdPartyFeature);
        if (thirdPartyFeature.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new thirdPartyFeature cannot already have an ID")).body(null);
        }
        ThirdPartyFeature result = thirdPartyFeatureService.save(thirdPartyFeature);
        return ResponseEntity.created(new URI("/api/third-party-features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /third-party-features : Updates an existing thirdPartyFeature.
     *
     * @param thirdPartyFeature the thirdPartyFeature to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thirdPartyFeature,
     * or with status 400 (Bad Request) if the thirdPartyFeature is not valid,
     * or with status 500 (Internal Server Error) if the thirdPartyFeature couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/third-party-features")
    @Timed
    public ResponseEntity<ThirdPartyFeature> updateThirdPartyFeature(@RequestBody ThirdPartyFeature thirdPartyFeature) throws URISyntaxException {
        log.debug("REST request to update ThirdPartyFeature : {}", thirdPartyFeature);
        if (thirdPartyFeature.getId() == null) {
            return createThirdPartyFeature(thirdPartyFeature);
        }
        ThirdPartyFeature result = thirdPartyFeatureService.save(thirdPartyFeature);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, thirdPartyFeature.getId().toString()))
            .body(result);
    }

    /**
     * GET  /third-party-features : get all the thirdPartyFeatures.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of thirdPartyFeatures in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/third-party-features")
    @Timed
    public ResponseEntity<List<ThirdPartyFeature>> getAllThirdPartyFeatures(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ThirdPartyFeatures");
        Page<ThirdPartyFeature> page = thirdPartyFeatureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/third-party-features");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /third-party-features/:id : get the "id" thirdPartyFeature.
     *
     * @param id the id of the thirdPartyFeature to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thirdPartyFeature, or with status 404 (Not Found)
     */
    @GetMapping("/third-party-features/{id}")
    @Timed
    public ResponseEntity<ThirdPartyFeature> getThirdPartyFeature(@PathVariable Long id) {
        log.debug("REST request to get ThirdPartyFeature : {}", id);
        ThirdPartyFeature thirdPartyFeature = thirdPartyFeatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(thirdPartyFeature));
    }

    /**
     * DELETE  /third-party-features/:id : delete the "id" thirdPartyFeature.
     *
     * @param id the id of the thirdPartyFeature to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/third-party-features/{id}")
    @Timed
    public ResponseEntity<Void> deleteThirdPartyFeature(@PathVariable Long id) {
        log.debug("REST request to delete ThirdPartyFeature : {}", id);
        thirdPartyFeatureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
