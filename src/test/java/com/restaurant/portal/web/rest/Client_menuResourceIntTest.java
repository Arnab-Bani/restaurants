package com.restaurant.portal.web.rest;

import com.restaurant.portal.FoodApp;

import com.restaurant.portal.domain.ClientMenu;
import com.restaurant.portal.repository.Client_menuRepository;
import com.restaurant.portal.service.Client_menuService;
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
 * Test class for the Client_menuResource REST controller.
 *
 * @see Client_menuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FoodApp.class)
public class Client_menuResourceIntTest {

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

    @Autowired
    private Client_menuRepository client_menuRepository;

    @Autowired
    private Client_menuService client_menuService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClient_menuMockMvc;

    private ClientMenu client_menu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Client_menuResource client_menuResource = new Client_menuResource(client_menuService);
        this.restClient_menuMockMvc = MockMvcBuilders.standaloneSetup(client_menuResource)
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
        ClientMenu client_menu = new ClientMenu()
                .item_id(DEFAULT_ITEM_ID)
                .item_name(DEFAULT_ITEM_NAME)
                .item_image(DEFAULT_ITEM_IMAGE)
                .item_imageContentType(DEFAULT_ITEM_IMAGE_CONTENT_TYPE)
                .item_price(DEFAULT_ITEM_PRICE);
        return client_menu;
    }

    @Before
    public void initTest() {
        client_menu = createEntity(em);
    }

    @Test
    @Transactional
    public void createClient_menu() throws Exception {
        int databaseSizeBeforeCreate = client_menuRepository.findAll().size();

        // Create the ClientMenu

        restClient_menuMockMvc.perform(post("/api/client-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client_menu)))
            .andExpect(status().isCreated());

        // Validate the ClientMenu in the database
        List<ClientMenu> client_menuList = client_menuRepository.findAll();
        assertThat(client_menuList).hasSize(databaseSizeBeforeCreate + 1);
        ClientMenu testClient_menu = client_menuList.get(client_menuList.size() - 1);
        assertThat(testClient_menu.getItem_id()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testClient_menu.getItem_name()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testClient_menu.getItem_image()).isEqualTo(DEFAULT_ITEM_IMAGE);
        assertThat(testClient_menu.getItem_imageContentType()).isEqualTo(DEFAULT_ITEM_IMAGE_CONTENT_TYPE);
        assertThat(testClient_menu.getItem_price()).isEqualTo(DEFAULT_ITEM_PRICE);
    }

    @Test
    @Transactional
    public void createClient_menuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = client_menuRepository.findAll().size();

        // Create the ClientMenu with an existing ID
        ClientMenu existingClient_menu = new ClientMenu();
        existingClient_menu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClient_menuMockMvc.perform(post("/api/client-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingClient_menu)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClientMenu> client_menuList = client_menuRepository.findAll();
        assertThat(client_menuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClient_menus() throws Exception {
        // Initialize the database
        client_menuRepository.saveAndFlush(client_menu);

        // Get all the client_menuList
        restClient_menuMockMvc.perform(get("/api/client-menus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client_menu.getId().intValue())))
            .andExpect(jsonPath("$.[*].item_id").value(hasItem(DEFAULT_ITEM_ID)))
            .andExpect(jsonPath("$.[*].item_name").value(hasItem(DEFAULT_ITEM_NAME.toString())))
            .andExpect(jsonPath("$.[*].item_imageContentType").value(hasItem(DEFAULT_ITEM_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].item_image").value(hasItem(Base64Utils.encodeToString(DEFAULT_ITEM_IMAGE))))
            .andExpect(jsonPath("$.[*].item_price").value(hasItem(DEFAULT_ITEM_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getClient_menu() throws Exception {
        // Initialize the database
        client_menuRepository.saveAndFlush(client_menu);

        // Get the client_menu
        restClient_menuMockMvc.perform(get("/api/client-menus/{id}", client_menu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(client_menu.getId().intValue()))
            .andExpect(jsonPath("$.item_id").value(DEFAULT_ITEM_ID))
            .andExpect(jsonPath("$.item_name").value(DEFAULT_ITEM_NAME.toString()))
            .andExpect(jsonPath("$.item_imageContentType").value(DEFAULT_ITEM_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.item_image").value(Base64Utils.encodeToString(DEFAULT_ITEM_IMAGE)))
            .andExpect(jsonPath("$.item_price").value(DEFAULT_ITEM_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClient_menu() throws Exception {
        // Get the client_menu
        restClient_menuMockMvc.perform(get("/api/client-menus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClient_menu() throws Exception {
        // Initialize the database
        client_menuService.save(client_menu);

        int databaseSizeBeforeUpdate = client_menuRepository.findAll().size();

        // Update the client_menu
        ClientMenu updatedClient_menu = client_menuRepository.findOne(client_menu.getId());
        updatedClient_menu
                .item_id(UPDATED_ITEM_ID)
                .item_name(UPDATED_ITEM_NAME)
                .item_image(UPDATED_ITEM_IMAGE)
                .item_imageContentType(UPDATED_ITEM_IMAGE_CONTENT_TYPE)
                .item_price(UPDATED_ITEM_PRICE);

        restClient_menuMockMvc.perform(put("/api/client-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClient_menu)))
            .andExpect(status().isOk());

        // Validate the ClientMenu in the database
        List<ClientMenu> client_menuList = client_menuRepository.findAll();
        assertThat(client_menuList).hasSize(databaseSizeBeforeUpdate);
        ClientMenu testClient_menu = client_menuList.get(client_menuList.size() - 1);
        assertThat(testClient_menu.getItem_id()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testClient_menu.getItem_name()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testClient_menu.getItem_image()).isEqualTo(UPDATED_ITEM_IMAGE);
        assertThat(testClient_menu.getItem_imageContentType()).isEqualTo(UPDATED_ITEM_IMAGE_CONTENT_TYPE);
        assertThat(testClient_menu.getItem_price()).isEqualTo(UPDATED_ITEM_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingClient_menu() throws Exception {
        int databaseSizeBeforeUpdate = client_menuRepository.findAll().size();

        // Create the ClientMenu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClient_menuMockMvc.perform(put("/api/client-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client_menu)))
            .andExpect(status().isCreated());

        // Validate the ClientMenu in the database
        List<ClientMenu> client_menuList = client_menuRepository.findAll();
        assertThat(client_menuList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClient_menu() throws Exception {
        // Initialize the database
        client_menuService.save(client_menu);

        int databaseSizeBeforeDelete = client_menuRepository.findAll().size();

        // Get the client_menu
        restClient_menuMockMvc.perform(delete("/api/client-menus/{id}", client_menu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientMenu> client_menuList = client_menuRepository.findAll();
        assertThat(client_menuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientMenu.class);
    }
}
