package com.restaurant.portal.web.rest;

import com.restaurant.portal.FoodApp;

import com.restaurant.portal.domain.Client_features;
import com.restaurant.portal.repository.Client_featuresRepository;
import com.restaurant.portal.service.Client_featuresService;
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
 * Test class for the Client_featuresResource REST controller.
 *
 * @see Client_featuresResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FoodApp.class)
public class Client_featuresResourceIntTest {

    private static final Integer DEFAULT_CLIENT_ID = 1;
    private static final Integer UPDATED_CLIENT_ID = 2;

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_YELP = "AAAAAAAAAA";
    private static final String UPDATED_YELP = "BBBBBBBBBB";

    private static final String DEFAULT_FOURSQUARE = "AAAAAAAAAA";
    private static final String UPDATED_FOURSQUARE = "BBBBBBBBBB";

    @Autowired
    private Client_featuresRepository client_featuresRepository;

    @Autowired
    private Client_featuresService client_featuresService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClient_featuresMockMvc;

    private Client_features client_features;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Client_featuresResource client_featuresResource = new Client_featuresResource(client_featuresService);
        this.restClient_featuresMockMvc = MockMvcBuilders.standaloneSetup(client_featuresResource)
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
    public static Client_features createEntity(EntityManager em) {
        Client_features client_features = new Client_features()
                .client_id(DEFAULT_CLIENT_ID)
                .facebook(DEFAULT_FACEBOOK)
                .yelp(DEFAULT_YELP)
                .foursquare(DEFAULT_FOURSQUARE);
        return client_features;
    }

    @Before
    public void initTest() {
        client_features = createEntity(em);
    }

    @Test
    @Transactional
    public void createClient_features() throws Exception {
        int databaseSizeBeforeCreate = client_featuresRepository.findAll().size();

        // Create the Client_features

        restClient_featuresMockMvc.perform(post("/api/client-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client_features)))
            .andExpect(status().isCreated());

        // Validate the Client_features in the database
        List<Client_features> client_featuresList = client_featuresRepository.findAll();
        assertThat(client_featuresList).hasSize(databaseSizeBeforeCreate + 1);
        Client_features testClient_features = client_featuresList.get(client_featuresList.size() - 1);
        assertThat(testClient_features.getClient_id()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testClient_features.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testClient_features.getYelp()).isEqualTo(DEFAULT_YELP);
        assertThat(testClient_features.getFoursquare()).isEqualTo(DEFAULT_FOURSQUARE);
    }

    @Test
    @Transactional
    public void createClient_featuresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = client_featuresRepository.findAll().size();

        // Create the Client_features with an existing ID
        Client_features existingClient_features = new Client_features();
        existingClient_features.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClient_featuresMockMvc.perform(post("/api/client-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingClient_features)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Client_features> client_featuresList = client_featuresRepository.findAll();
        assertThat(client_featuresList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClient_features() throws Exception {
        // Initialize the database
        client_featuresRepository.saveAndFlush(client_features);

        // Get all the client_featuresList
        restClient_featuresMockMvc.perform(get("/api/client-features?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client_features.getId().intValue())))
            .andExpect(jsonPath("$.[*].client_id").value(hasItem(DEFAULT_CLIENT_ID)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
            .andExpect(jsonPath("$.[*].yelp").value(hasItem(DEFAULT_YELP.toString())))
            .andExpect(jsonPath("$.[*].foursquare").value(hasItem(DEFAULT_FOURSQUARE.toString())));
    }

    @Test
    @Transactional
    public void getClient_features() throws Exception {
        // Initialize the database
        client_featuresRepository.saveAndFlush(client_features);

        // Get the client_features
        restClient_featuresMockMvc.perform(get("/api/client-features/{id}", client_features.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(client_features.getId().intValue()))
            .andExpect(jsonPath("$.client_id").value(DEFAULT_CLIENT_ID))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK.toString()))
            .andExpect(jsonPath("$.yelp").value(DEFAULT_YELP.toString()))
            .andExpect(jsonPath("$.foursquare").value(DEFAULT_FOURSQUARE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClient_features() throws Exception {
        // Get the client_features
        restClient_featuresMockMvc.perform(get("/api/client-features/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClient_features() throws Exception {
        // Initialize the database
        client_featuresService.save(client_features);

        int databaseSizeBeforeUpdate = client_featuresRepository.findAll().size();

        // Update the client_features
        Client_features updatedClient_features = client_featuresRepository.findOne(client_features.getId());
        updatedClient_features
                .client_id(UPDATED_CLIENT_ID)
                .facebook(UPDATED_FACEBOOK)
                .yelp(UPDATED_YELP)
                .foursquare(UPDATED_FOURSQUARE);

        restClient_featuresMockMvc.perform(put("/api/client-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClient_features)))
            .andExpect(status().isOk());

        // Validate the Client_features in the database
        List<Client_features> client_featuresList = client_featuresRepository.findAll();
        assertThat(client_featuresList).hasSize(databaseSizeBeforeUpdate);
        Client_features testClient_features = client_featuresList.get(client_featuresList.size() - 1);
        assertThat(testClient_features.getClient_id()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testClient_features.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testClient_features.getYelp()).isEqualTo(UPDATED_YELP);
        assertThat(testClient_features.getFoursquare()).isEqualTo(UPDATED_FOURSQUARE);
    }

    @Test
    @Transactional
    public void updateNonExistingClient_features() throws Exception {
        int databaseSizeBeforeUpdate = client_featuresRepository.findAll().size();

        // Create the Client_features

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClient_featuresMockMvc.perform(put("/api/client-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client_features)))
            .andExpect(status().isCreated());

        // Validate the Client_features in the database
        List<Client_features> client_featuresList = client_featuresRepository.findAll();
        assertThat(client_featuresList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClient_features() throws Exception {
        // Initialize the database
        client_featuresService.save(client_features);

        int databaseSizeBeforeDelete = client_featuresRepository.findAll().size();

        // Get the client_features
        restClient_featuresMockMvc.perform(delete("/api/client-features/{id}", client_features.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Client_features> client_featuresList = client_featuresRepository.findAll();
        assertThat(client_featuresList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client_features.class);
    }
}
