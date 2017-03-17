package com.restaurant.portal.web.rest;

import com.restaurant.portal.FoodApp;

import com.restaurant.portal.domain.ClientMenu;
import com.restaurant.portal.repository.ClientMenuRepository;
import com.restaurant.portal.service.ClientMenuService;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClientMenuResource REST controller.
 *
 * @see ClientMenuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FoodApp.class)
public class ClientMenuResourceIntTest {

    private static final Integer DEFAULT_ITEM_ID = 1;
    private static final Integer UPDATED_ITEM_ID = 2;

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ITEM_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ITEM_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ITEM_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ITEM_IMAGE_CONTENT_TYPE = "image/png";

    private static final Double DEFAULT_ITEM_PRICE = 1D;
    private static final Double UPDATED_ITEM_PRICE = 2D;

    private static final Integer DEFAULT_CATEGORY_ID = 1;
    private static final Integer UPDATED_CATEGORY_ID = 2;

    private static final String DEFAULT_ITEM_DESC = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_DESC = "BBBBBBBBBB";

    @Autowired
    private ClientMenuRepository clientMenuRepository;

    @Autowired
    private ClientMenuService clientMenuService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientMenuMockMvc;

    private ClientMenu clientMenu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientMenuResource clientMenuResource = new ClientMenuResource(clientMenuService);
        this.restClientMenuMockMvc = MockMvcBuilders.standaloneSetup(clientMenuResource)
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
    public static ClientMenu createEntity(EntityManager em) {
        ClientMenu clientMenu = new ClientMenu()
                .item_id(DEFAULT_ITEM_ID)
                .item_name(DEFAULT_ITEM_NAME)
                .item_image(DEFAULT_ITEM_IMAGE)
                .item_imageContentType(DEFAULT_ITEM_IMAGE_CONTENT_TYPE)
                .item_price(DEFAULT_ITEM_PRICE)
                .category_id(DEFAULT_CATEGORY_ID)
                .item_desc(DEFAULT_ITEM_DESC);
        return clientMenu;
    }

    @Before
    public void initTest() {
        clientMenu = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientMenu() throws Exception {
        int databaseSizeBeforeCreate = clientMenuRepository.findAll().size();

        // Create the ClientMenu

        restClientMenuMockMvc.perform(post("/api/client-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientMenu)))
            .andExpect(status().isCreated());

        // Validate the ClientMenu in the database
        List<ClientMenu> clientMenuList = clientMenuRepository.findAll();
        assertThat(clientMenuList).hasSize(databaseSizeBeforeCreate + 1);
        ClientMenu testClientMenu = clientMenuList.get(clientMenuList.size() - 1);
        assertThat(testClientMenu.getItem_id()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testClientMenu.getItem_name()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testClientMenu.getItem_image()).isEqualTo(DEFAULT_ITEM_IMAGE);
        assertThat(testClientMenu.getItem_imageContentType()).isEqualTo(DEFAULT_ITEM_IMAGE_CONTENT_TYPE);
        assertThat(testClientMenu.getItem_price()).isEqualTo(DEFAULT_ITEM_PRICE);
        assertThat(testClientMenu.getCategory_id()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testClientMenu.getItem_desc()).isEqualTo(DEFAULT_ITEM_DESC);
    }

    @Test
    @Transactional
    public void createClientMenuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientMenuRepository.findAll().size();

        // Create the ClientMenu with an existing ID
        ClientMenu existingClientMenu = new ClientMenu();
        existingClientMenu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMenuMockMvc.perform(post("/api/client-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingClientMenu)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClientMenu> clientMenuList = clientMenuRepository.findAll();
        assertThat(clientMenuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClientMenus() throws Exception {
        // Initialize the database
        clientMenuRepository.saveAndFlush(clientMenu);

        // Get all the clientMenuList
        restClientMenuMockMvc.perform(get("/api/client-menus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientMenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].item_id").value(hasItem(DEFAULT_ITEM_ID)))
            .andExpect(jsonPath("$.[*].item_name").value(hasItem(DEFAULT_ITEM_NAME.toString())))
            .andExpect(jsonPath("$.[*].item_imageContentType").value(hasItem(DEFAULT_ITEM_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].item_image").value(hasItem(Base64Utils.encodeToString(DEFAULT_ITEM_IMAGE))))
            .andExpect(jsonPath("$.[*].item_price").value(hasItem(DEFAULT_ITEM_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].category_id").value(hasItem(DEFAULT_CATEGORY_ID)))
            .andExpect(jsonPath("$.[*].item_desc").value(hasItem(DEFAULT_ITEM_DESC.toString())));
    }

    @Test
    @Transactional
    public void getClientMenu() throws Exception {
        // Initialize the database
        clientMenuRepository.saveAndFlush(clientMenu);

        // Get the clientMenu
        restClientMenuMockMvc.perform(get("/api/client-menus/{id}", clientMenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientMenu.getId().intValue()))
            .andExpect(jsonPath("$.item_id").value(DEFAULT_ITEM_ID))
            .andExpect(jsonPath("$.item_name").value(DEFAULT_ITEM_NAME.toString()))
            .andExpect(jsonPath("$.item_imageContentType").value(DEFAULT_ITEM_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.item_image").value(Base64Utils.encodeToString(DEFAULT_ITEM_IMAGE)))
            .andExpect(jsonPath("$.item_price").value(DEFAULT_ITEM_PRICE.doubleValue()))
            .andExpect(jsonPath("$.category_id").value(DEFAULT_CATEGORY_ID))
            .andExpect(jsonPath("$.item_desc").value(DEFAULT_ITEM_DESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClientMenu() throws Exception {
        // Get the clientMenu
        restClientMenuMockMvc.perform(get("/api/client-menus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientMenu() throws Exception {
        // Initialize the database
        clientMenuService.save(clientMenu);

        int databaseSizeBeforeUpdate = clientMenuRepository.findAll().size();

        // Update the clientMenu
        ClientMenu updatedClientMenu = clientMenuRepository.findOne(clientMenu.getId());
        updatedClientMenu
                .item_id(UPDATED_ITEM_ID)
                .item_name(UPDATED_ITEM_NAME)
                .item_image(UPDATED_ITEM_IMAGE)
                .item_imageContentType(UPDATED_ITEM_IMAGE_CONTENT_TYPE)
                .item_price(UPDATED_ITEM_PRICE)
                .category_id(UPDATED_CATEGORY_ID)
                .item_desc(UPDATED_ITEM_DESC);

        restClientMenuMockMvc.perform(put("/api/client-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientMenu)))
            .andExpect(status().isOk());

        // Validate the ClientMenu in the database
        List<ClientMenu> clientMenuList = clientMenuRepository.findAll();
        assertThat(clientMenuList).hasSize(databaseSizeBeforeUpdate);
        ClientMenu testClientMenu = clientMenuList.get(clientMenuList.size() - 1);
        assertThat(testClientMenu.getItem_id()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testClientMenu.getItem_name()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testClientMenu.getItem_image()).isEqualTo(UPDATED_ITEM_IMAGE);
        assertThat(testClientMenu.getItem_imageContentType()).isEqualTo(UPDATED_ITEM_IMAGE_CONTENT_TYPE);
        assertThat(testClientMenu.getItem_price()).isEqualTo(UPDATED_ITEM_PRICE);
        assertThat(testClientMenu.getCategory_id()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testClientMenu.getItem_desc()).isEqualTo(UPDATED_ITEM_DESC);
    }

    @Test
    @Transactional
    public void updateNonExistingClientMenu() throws Exception {
        int databaseSizeBeforeUpdate = clientMenuRepository.findAll().size();

        // Create the ClientMenu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClientMenuMockMvc.perform(put("/api/client-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientMenu)))
            .andExpect(status().isCreated());

        // Validate the ClientMenu in the database
        List<ClientMenu> clientMenuList = clientMenuRepository.findAll();
        assertThat(clientMenuList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClientMenu() throws Exception {
        // Initialize the database
        clientMenuService.save(clientMenu);

        int databaseSizeBeforeDelete = clientMenuRepository.findAll().size();

        // Get the clientMenu
        restClientMenuMockMvc.perform(delete("/api/client-menus/{id}", clientMenu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientMenu> clientMenuList = clientMenuRepository.findAll();
        assertThat(clientMenuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientMenu.class);
    }
}
