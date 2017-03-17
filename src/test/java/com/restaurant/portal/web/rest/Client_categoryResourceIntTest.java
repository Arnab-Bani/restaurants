package com.restaurant.portal.web.rest;

import com.restaurant.portal.FoodApp;

import com.restaurant.portal.domain.Client_category;
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
 * Test class for the Client_categoryResource REST controller.
 *
 * @see Client_categoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FoodApp.class)
public class Client_categoryResourceIntTest {

    @Autowired
    private Client_categoryRepository client_categoryRepository;

    @Autowired
    private Client_categoryService client_categoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClient_categoryMockMvc;

    private Client_category client_category;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Client_categoryResource client_categoryResource = new Client_categoryResource(client_categoryService);
        this.restClient_categoryMockMvc = MockMvcBuilders.standaloneSetup(client_categoryResource)
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
    public static Client_category createEntity(EntityManager em) {
        Client_category client_category = new Client_category();
        return client_category;
    }

    @Before
    public void initTest() {
        client_category = createEntity(em);
    }

    @Test
    @Transactional
    public void createClient_category() throws Exception {
        int databaseSizeBeforeCreate = client_categoryRepository.findAll().size();

        // Create the Client_category

        restClient_categoryMockMvc.perform(post("/api/client-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client_category)))
            .andExpect(status().isCreated());

        // Validate the Client_category in the database
        List<Client_category> client_categoryList = client_categoryRepository.findAll();
        assertThat(client_categoryList).hasSize(databaseSizeBeforeCreate + 1);
        Client_category testClient_category = client_categoryList.get(client_categoryList.size() - 1);
    }

    @Test
    @Transactional
    public void createClient_categoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = client_categoryRepository.findAll().size();

        // Create the Client_category with an existing ID
        Client_category existingClient_category = new Client_category();
        existingClient_category.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClient_categoryMockMvc.perform(post("/api/client-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingClient_category)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Client_category> client_categoryList = client_categoryRepository.findAll();
        assertThat(client_categoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClient_categories() throws Exception {
        // Initialize the database
        client_categoryRepository.saveAndFlush(client_category);

        // Get all the client_categoryList
        restClient_categoryMockMvc.perform(get("/api/client-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client_category.getId().intValue())));
    }

    @Test
    @Transactional
    public void getClient_category() throws Exception {
        // Initialize the database
        client_categoryRepository.saveAndFlush(client_category);

        // Get the client_category
        restClient_categoryMockMvc.perform(get("/api/client-categories/{id}", client_category.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(client_category.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClient_category() throws Exception {
        // Get the client_category
        restClient_categoryMockMvc.perform(get("/api/client-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClient_category() throws Exception {
        // Initialize the database
        client_categoryService.save(client_category);

        int databaseSizeBeforeUpdate = client_categoryRepository.findAll().size();

        // Update the client_category
        Client_category updatedClient_category = client_categoryRepository.findOne(client_category.getId());

        restClient_categoryMockMvc.perform(put("/api/client-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClient_category)))
            .andExpect(status().isOk());

        // Validate the Client_category in the database
        List<Client_category> client_categoryList = client_categoryRepository.findAll();
        assertThat(client_categoryList).hasSize(databaseSizeBeforeUpdate);
        Client_category testClient_category = client_categoryList.get(client_categoryList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingClient_category() throws Exception {
        int databaseSizeBeforeUpdate = client_categoryRepository.findAll().size();

        // Create the Client_category

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClient_categoryMockMvc.perform(put("/api/client-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client_category)))
            .andExpect(status().isCreated());

        // Validate the Client_category in the database
        List<Client_category> client_categoryList = client_categoryRepository.findAll();
        assertThat(client_categoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClient_category() throws Exception {
        // Initialize the database
        client_categoryService.save(client_category);

        int databaseSizeBeforeDelete = client_categoryRepository.findAll().size();

        // Get the client_category
        restClient_categoryMockMvc.perform(delete("/api/client-categories/{id}", client_category.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Client_category> client_categoryList = client_categoryRepository.findAll();
        assertThat(client_categoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client_category.class);
    }
}
