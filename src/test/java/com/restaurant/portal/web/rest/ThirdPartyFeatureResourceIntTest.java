package com.restaurant.portal.web.rest;

import com.restaurant.portal.FoodApp;

import com.restaurant.portal.domain.ThirdPartyFeature;
import com.restaurant.portal.repository.ThirdPartyFeatureRepository;
import com.restaurant.portal.service.ThirdPartyFeatureService;
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
 * Test class for the ThirdPartyFeatureResource REST controller.
 *
 * @see ThirdPartyFeatureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FoodApp.class)
public class ThirdPartyFeatureResourceIntTest {

    private static final String DEFAULT_FEATURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_FEATURE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FEATURE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FEATURE_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BILLABLE = false;
    private static final Boolean UPDATED_BILLABLE = true;

    @Autowired
    private ThirdPartyFeatureRepository thirdPartyFeatureRepository;

    @Autowired
    private ThirdPartyFeatureService thirdPartyFeatureService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restThirdPartyFeatureMockMvc;

    private ThirdPartyFeature thirdPartyFeature;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ThirdPartyFeatureResource thirdPartyFeatureResource = new ThirdPartyFeatureResource(thirdPartyFeatureService);
        this.restThirdPartyFeatureMockMvc = MockMvcBuilders.standaloneSetup(thirdPartyFeatureResource)
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
    public static ThirdPartyFeature createEntity(EntityManager em) {
        ThirdPartyFeature thirdPartyFeature = new ThirdPartyFeature()
                .featureCode(DEFAULT_FEATURE_CODE)
                .featureName(DEFAULT_FEATURE_NAME)
                .billable(DEFAULT_BILLABLE);
        return thirdPartyFeature;
    }

    @Before
    public void initTest() {
        thirdPartyFeature = createEntity(em);
    }

    @Test
    @Transactional
    public void createThirdPartyFeature() throws Exception {
        int databaseSizeBeforeCreate = thirdPartyFeatureRepository.findAll().size();

        // Create the ThirdPartyFeature

        restThirdPartyFeatureMockMvc.perform(post("/api/third-party-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdPartyFeature)))
            .andExpect(status().isCreated());

        // Validate the ThirdPartyFeature in the database
        List<ThirdPartyFeature> thirdPartyFeatureList = thirdPartyFeatureRepository.findAll();
        assertThat(thirdPartyFeatureList).hasSize(databaseSizeBeforeCreate + 1);
        ThirdPartyFeature testThirdPartyFeature = thirdPartyFeatureList.get(thirdPartyFeatureList.size() - 1);
        assertThat(testThirdPartyFeature.getFeatureCode()).isEqualTo(DEFAULT_FEATURE_CODE);
        assertThat(testThirdPartyFeature.getFeatureName()).isEqualTo(DEFAULT_FEATURE_NAME);
        assertThat(testThirdPartyFeature.isBillable()).isEqualTo(DEFAULT_BILLABLE);
    }

    @Test
    @Transactional
    public void createThirdPartyFeatureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = thirdPartyFeatureRepository.findAll().size();

        // Create the ThirdPartyFeature with an existing ID
        ThirdPartyFeature existingThirdPartyFeature = new ThirdPartyFeature();
        existingThirdPartyFeature.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThirdPartyFeatureMockMvc.perform(post("/api/third-party-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingThirdPartyFeature)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ThirdPartyFeature> thirdPartyFeatureList = thirdPartyFeatureRepository.findAll();
        assertThat(thirdPartyFeatureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllThirdPartyFeatures() throws Exception {
        // Initialize the database
        thirdPartyFeatureRepository.saveAndFlush(thirdPartyFeature);

        // Get all the thirdPartyFeatureList
        restThirdPartyFeatureMockMvc.perform(get("/api/third-party-features?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thirdPartyFeature.getId().intValue())))
            .andExpect(jsonPath("$.[*].featureCode").value(hasItem(DEFAULT_FEATURE_CODE.toString())))
            .andExpect(jsonPath("$.[*].featureName").value(hasItem(DEFAULT_FEATURE_NAME.toString())))
            .andExpect(jsonPath("$.[*].billable").value(hasItem(DEFAULT_BILLABLE.booleanValue())));
    }

    @Test
    @Transactional
    public void getThirdPartyFeature() throws Exception {
        // Initialize the database
        thirdPartyFeatureRepository.saveAndFlush(thirdPartyFeature);

        // Get the thirdPartyFeature
        restThirdPartyFeatureMockMvc.perform(get("/api/third-party-features/{id}", thirdPartyFeature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(thirdPartyFeature.getId().intValue()))
            .andExpect(jsonPath("$.featureCode").value(DEFAULT_FEATURE_CODE.toString()))
            .andExpect(jsonPath("$.featureName").value(DEFAULT_FEATURE_NAME.toString()))
            .andExpect(jsonPath("$.billable").value(DEFAULT_BILLABLE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingThirdPartyFeature() throws Exception {
        // Get the thirdPartyFeature
        restThirdPartyFeatureMockMvc.perform(get("/api/third-party-features/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThirdPartyFeature() throws Exception {
        // Initialize the database
        thirdPartyFeatureService.save(thirdPartyFeature);

        int databaseSizeBeforeUpdate = thirdPartyFeatureRepository.findAll().size();

        // Update the thirdPartyFeature
        ThirdPartyFeature updatedThirdPartyFeature = thirdPartyFeatureRepository.findOne(thirdPartyFeature.getId());
        updatedThirdPartyFeature
                .featureCode(UPDATED_FEATURE_CODE)
                .featureName(UPDATED_FEATURE_NAME)
                .billable(UPDATED_BILLABLE);

        restThirdPartyFeatureMockMvc.perform(put("/api/third-party-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedThirdPartyFeature)))
            .andExpect(status().isOk());

        // Validate the ThirdPartyFeature in the database
        List<ThirdPartyFeature> thirdPartyFeatureList = thirdPartyFeatureRepository.findAll();
        assertThat(thirdPartyFeatureList).hasSize(databaseSizeBeforeUpdate);
        ThirdPartyFeature testThirdPartyFeature = thirdPartyFeatureList.get(thirdPartyFeatureList.size() - 1);
        assertThat(testThirdPartyFeature.getFeatureCode()).isEqualTo(UPDATED_FEATURE_CODE);
        assertThat(testThirdPartyFeature.getFeatureName()).isEqualTo(UPDATED_FEATURE_NAME);
        assertThat(testThirdPartyFeature.isBillable()).isEqualTo(UPDATED_BILLABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingThirdPartyFeature() throws Exception {
        int databaseSizeBeforeUpdate = thirdPartyFeatureRepository.findAll().size();

        // Create the ThirdPartyFeature

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restThirdPartyFeatureMockMvc.perform(put("/api/third-party-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdPartyFeature)))
            .andExpect(status().isCreated());

        // Validate the ThirdPartyFeature in the database
        List<ThirdPartyFeature> thirdPartyFeatureList = thirdPartyFeatureRepository.findAll();
        assertThat(thirdPartyFeatureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteThirdPartyFeature() throws Exception {
        // Initialize the database
        thirdPartyFeatureService.save(thirdPartyFeature);

        int databaseSizeBeforeDelete = thirdPartyFeatureRepository.findAll().size();

        // Get the thirdPartyFeature
        restThirdPartyFeatureMockMvc.perform(delete("/api/third-party-features/{id}", thirdPartyFeature.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ThirdPartyFeature> thirdPartyFeatureList = thirdPartyFeatureRepository.findAll();
        assertThat(thirdPartyFeatureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThirdPartyFeature.class);
    }
}
