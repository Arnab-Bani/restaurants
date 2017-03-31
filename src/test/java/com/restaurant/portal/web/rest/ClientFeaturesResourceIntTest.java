package com.restaurant.portal.web.rest;

import com.restaurant.portal.FoodApp;

import com.restaurant.portal.domain.ClientFeatures;
import com.restaurant.portal.repository.ClientFeaturesRepository;
import com.restaurant.portal.service.ClientFeaturesService;
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
 * Test class for the ClientFeaturesResource REST controller.
 *
 * @see ClientFeaturesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FoodApp.class)
public class ClientFeaturesResourceIntTest {

    private static final String DEFAULT_FEATURE_URL = "AAAAAAAAAA";
    private static final String UPDATED_FEATURE_URL = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    @Autowired
    private ClientFeaturesRepository clientFeaturesRepository;

    @Autowired
    private ClientFeaturesService clientFeaturesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientFeaturesMockMvc;

    private ClientFeatures clientFeatures;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientFeaturesResource clientFeaturesResource = new ClientFeaturesResource(clientFeaturesService);
        this.restClientFeaturesMockMvc = MockMvcBuilders.standaloneSetup(clientFeaturesResource)
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
    public static ClientFeatures createEntity(EntityManager em) {
        ClientFeatures clientFeatures = new ClientFeatures()
                .featureUrl(DEFAULT_FEATURE_URL)
                .price(DEFAULT_PRICE);
        return clientFeatures;
    }

    @Before
    public void initTest() {
        clientFeatures = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientFeatures() throws Exception {
        int databaseSizeBeforeCreate = clientFeaturesRepository.findAll().size();

        // Create the ClientFeatures

        restClientFeaturesMockMvc.perform(post("/api/client-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientFeatures)))
            .andExpect(status().isCreated());

        // Validate the ClientFeatures in the database
        List<ClientFeatures> clientFeaturesList = clientFeaturesRepository.findAll();
        assertThat(clientFeaturesList).hasSize(databaseSizeBeforeCreate + 1);
        ClientFeatures testClientFeatures = clientFeaturesList.get(clientFeaturesList.size() - 1);
        assertThat(testClientFeatures.getFeatureUrl()).isEqualTo(DEFAULT_FEATURE_URL);
        assertThat(testClientFeatures.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createClientFeaturesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientFeaturesRepository.findAll().size();

        // Create the ClientFeatures with an existing ID
        ClientFeatures existingClientFeatures = new ClientFeatures();
        existingClientFeatures.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientFeaturesMockMvc.perform(post("/api/client-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingClientFeatures)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClientFeatures> clientFeaturesList = clientFeaturesRepository.findAll();
        assertThat(clientFeaturesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClientFeatures() throws Exception {
        // Initialize the database
        clientFeaturesRepository.saveAndFlush(clientFeatures);

        // Get all the clientFeaturesList
        restClientFeaturesMockMvc.perform(get("/api/client-features?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientFeatures.getId().intValue())))
            .andExpect(jsonPath("$.[*].featureUrl").value(hasItem(DEFAULT_FEATURE_URL.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getClientFeatures() throws Exception {
        // Initialize the database
        clientFeaturesRepository.saveAndFlush(clientFeatures);

        // Get the clientFeatures
        restClientFeaturesMockMvc.perform(get("/api/client-features/{id}", clientFeatures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientFeatures.getId().intValue()))
            .andExpect(jsonPath("$.featureUrl").value(DEFAULT_FEATURE_URL.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClientFeatures() throws Exception {
        // Get the clientFeatures
        restClientFeaturesMockMvc.perform(get("/api/client-features/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientFeatures() throws Exception {
        // Initialize the database
        clientFeaturesService.save(clientFeatures);

        int databaseSizeBeforeUpdate = clientFeaturesRepository.findAll().size();

        // Update the clientFeatures
        ClientFeatures updatedClientFeatures = clientFeaturesRepository.findOne(clientFeatures.getId());
        updatedClientFeatures
                .featureUrl(UPDATED_FEATURE_URL)
                .price(UPDATED_PRICE);

        restClientFeaturesMockMvc.perform(put("/api/client-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientFeatures)))
            .andExpect(status().isOk());

        // Validate the ClientFeatures in the database
        List<ClientFeatures> clientFeaturesList = clientFeaturesRepository.findAll();
        assertThat(clientFeaturesList).hasSize(databaseSizeBeforeUpdate);
        ClientFeatures testClientFeatures = clientFeaturesList.get(clientFeaturesList.size() - 1);
        assertThat(testClientFeatures.getFeatureUrl()).isEqualTo(UPDATED_FEATURE_URL);
        assertThat(testClientFeatures.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingClientFeatures() throws Exception {
        int databaseSizeBeforeUpdate = clientFeaturesRepository.findAll().size();

        // Create the ClientFeatures

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClientFeaturesMockMvc.perform(put("/api/client-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientFeatures)))
            .andExpect(status().isCreated());

        // Validate the ClientFeatures in the database
        List<ClientFeatures> clientFeaturesList = clientFeaturesRepository.findAll();
        assertThat(clientFeaturesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClientFeatures() throws Exception {
        // Initialize the database
        clientFeaturesService.save(clientFeatures);

        int databaseSizeBeforeDelete = clientFeaturesRepository.findAll().size();

        // Get the clientFeatures
        restClientFeaturesMockMvc.perform(delete("/api/client-features/{id}", clientFeatures.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientFeatures> clientFeaturesList = clientFeaturesRepository.findAll();
        assertThat(clientFeaturesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientFeatures.class);
    }
}
