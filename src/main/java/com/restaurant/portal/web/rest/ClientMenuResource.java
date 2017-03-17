package com.restaurant.portal.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.restaurant.portal.domain.ClientMenu;
import com.restaurant.portal.service.ClientMenuService;
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
 * REST controller for managing ClientMenu.
 */
@RestController
@RequestMapping("/api")
public class ClientMenuResource {

    private final Logger log = LoggerFactory.getLogger(ClientMenuResource.class);

    private static final String ENTITY_NAME = "clientMenu";
        
    private final ClientMenuService clientMenuService;

    public ClientMenuResource(ClientMenuService clientMenuService) {
        this.clientMenuService = clientMenuService;
    }

    /**
     * POST  /client-menus : Create a new clientMenu.
     *
     * @param clientMenu the clientMenu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientMenu, or with status 400 (Bad Request) if the clientMenu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-menus")
    @Timed
    public ResponseEntity<ClientMenu> createClientMenu(@RequestBody ClientMenu clientMenu) throws URISyntaxException {
        log.debug("REST request to save ClientMenu : {}", clientMenu);
        if (clientMenu.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new clientMenu cannot already have an ID")).body(null);
        }
        ClientMenu result = clientMenuService.save(clientMenu);
        return ResponseEntity.created(new URI("/api/client-menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-menus : Updates an existing clientMenu.
     *
     * @param clientMenu the clientMenu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientMenu,
     * or with status 400 (Bad Request) if the clientMenu is not valid,
     * or with status 500 (Internal Server Error) if the clientMenu couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-menus")
    @Timed
    public ResponseEntity<ClientMenu> updateClientMenu(@RequestBody ClientMenu clientMenu) throws URISyntaxException {
        log.debug("REST request to update ClientMenu : {}", clientMenu);
        if (clientMenu.getId() == null) {
            return createClientMenu(clientMenu);
        }
        ClientMenu result = clientMenuService.save(clientMenu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientMenu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-menus : get all the clientMenus.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clientMenus in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/client-menus")
    @Timed
    public ResponseEntity<List<ClientMenu>> getAllClientMenus(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ClientMenus");
        Page<ClientMenu> page = clientMenuService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/client-menus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /client-menus/:id : get the "id" clientMenu.
     *
     * @param id the id of the clientMenu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientMenu, or with status 404 (Not Found)
     */
    @GetMapping("/client-menus/{id}")
    @Timed
    public ResponseEntity<ClientMenu> getClientMenu(@PathVariable Long id) {
        log.debug("REST request to get ClientMenu : {}", id);
        ClientMenu clientMenu = clientMenuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clientMenu));
    }

    /**
     * DELETE  /client-menus/:id : delete the "id" clientMenu.
     *
     * @param id the id of the clientMenu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-menus/{id}")
    @Timed
    public ResponseEntity<Void> deleteClientMenu(@PathVariable Long id) {
        log.debug("REST request to delete ClientMenu : {}", id);
        clientMenuService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
