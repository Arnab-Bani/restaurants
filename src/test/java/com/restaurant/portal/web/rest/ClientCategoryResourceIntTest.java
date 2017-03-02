package com.restaurant.portal.web.rest;

import com.restaurant.portal.FoodApp;

import com.restaurant.portal.domain.ClientCategory;
import com.restaurant.portal.repository.ClientCategoryRepository;
import com.restaurant.portal.service.ClientCategoryService;
import com.restaurant.portal.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClientCategoryResource REST controller.
 *
 * @see ClientCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FoodApp.class)
public class ClientCategoryResourceIntTest {

    @Autowired
    private ClientCategoryRepository clientCategoryRepository;

    @Autowired
    private ClientCategoryService clientCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientCategoryMockMvc;

    private ClientCategory clientCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientCategoryResource clientCategoryResource = new ClientCategoryResource(clientCategoryService);
        this.restClientCategoryMockMvc = MockMvcBuilders.standaloneSetup(clientCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientCategory createEntity(EntityManager em) {
        ClientCategory clientCategory = new ClientCategory();
        return clientCategory;
    }

    @Before
    public void initTest() {
        clientCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientCategory() throws Exception {
        int databaseSizeBeforeCreate = clientCategoryRepository.findAll().size();

        // Create the ClientCategory

        restClientCategoryMockMvc.perform(post("/api/client-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientCategory)))
            .andExpect(status().isCreated());

        // Validate the ClientCategory in the database
        List<ClientCategory> clientCategoryList = clientCategoryRepository.findAll();
        assertThat(clientCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ClientCategory testClientCategory = clientCategoryList.get(clientCategoryList.size() - 1);
    }

    @Test
    @Transactional
    public void createClientCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientCategoryRepository.findAll().size();

        // Create the ClientCategory with an existing ID
        ClientCategory existingClientCategory = new ClientCategory();
        existingClientCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientCategoryMockMvc.perform(post("/api/client-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingClientCategory)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClientCategory> clientCategoryList = clientCategoryRepository.findAll();
        assertThat(clientCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClientCategories() throws Exception {
        // Initialize the database
        clientCategoryRepository.saveAndFlush(clientCategory);

        // Get all the clientCategoryList
        restClientCategoryMockMvc.perform(get("/api/client-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientCategory.getId().intValue())));
    }

    @Test
    @Transactional
    public void getClientCategory() throws Exception {
        // Initialize the database
        clientCategoryRepository.saveAndFlush(clientCategory);

        // Get the clientCategory
        restClientCategoryMockMvc.perform(get("/api/client-categories/{id}", clientCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientCategory.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClientCategory() throws Exception {
        // Get the clientCategory
        restClientCategoryMockMvc.perform(get("/api/client-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientCategory() throws Exception {
        // Initialize the database
        clientCategoryService.save(clientCategory);

        int databaseSizeBeforeUpdate = clientCategoryRepository.findAll().size();

        // Update the clientCategory
        ClientCategory updatedClientCategory = clientCategoryRepository.findOne(clientCategory.getId());

        restClientCategoryMockMvc.perform(put("/api/client-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientCategory)))
            .andExpect(status().isOk());

        // Validate the ClientCategory in the database
        List<ClientCategory> clientCategoryList = clientCategoryRepository.findAll();
        assertThat(clientCategoryList).hasSize(databaseSizeBeforeUpdate);
        ClientCategory testClientCategory = clientCategoryList.get(clientCategoryList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingClientCategory() throws Exception {
        int databaseSizeBeforeUpdate = clientCategoryRepository.findAll().size();

        // Create the ClientCategory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClientCategoryMockMvc.perform(put("/api/client-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientCategory)))
            .andExpect(status().isCreated());

        // Validate the ClientCategory in the database
        List<ClientCategory> clientCategoryList = clientCategoryRepository.findAll();
        assertThat(clientCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClientCategory() throws Exception {
        // Initialize the database
        clientCategoryService.save(clientCategory);

        int databaseSizeBeforeDelete = clientCategoryRepository.findAll().size();

        // Get the clientCategory
        restClientCategoryMockMvc.perform(delete("/api/client-categories/{id}", clientCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientCategory> clientCategoryList = clientCategoryRepository.findAll();
        assertThat(clientCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientCategory.class);
    }
}
