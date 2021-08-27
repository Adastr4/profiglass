package it.adastra.profilglass.configuratore.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.adastra.profilglass.configuratore.IntegrationTest;
import it.adastra.profilglass.configuratore.domain.CLLEGA;
import it.adastra.profilglass.configuratore.repository.CLLEGARepository;
import it.adastra.profilglass.configuratore.service.criteria.CLLEGACriteria;
import it.adastra.profilglass.configuratore.service.dto.CLLEGADTO;
import it.adastra.profilglass.configuratore.service.mapper.CLLEGAMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CLLEGAResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CLLEGAResourceIT {

    private static final String DEFAULT_OPZIONE = "AAAAAAAAAA";
    private static final String UPDATED_OPZIONE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cllegas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CLLEGARepository cLLEGARepository;

    @Autowired
    private CLLEGAMapper cLLEGAMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCLLEGAMockMvc;

    private CLLEGA cLLEGA;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CLLEGA createEntity(EntityManager em) {
        CLLEGA cLLEGA = new CLLEGA().opzione(DEFAULT_OPZIONE).descrizione(DEFAULT_DESCRIZIONE);
        return cLLEGA;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CLLEGA createUpdatedEntity(EntityManager em) {
        CLLEGA cLLEGA = new CLLEGA().opzione(UPDATED_OPZIONE).descrizione(UPDATED_DESCRIZIONE);
        return cLLEGA;
    }

    @BeforeEach
    public void initTest() {
        cLLEGA = createEntity(em);
    }

    @Test
    @Transactional
    void createCLLEGA() throws Exception {
        int databaseSizeBeforeCreate = cLLEGARepository.findAll().size();
        // Create the CLLEGA
        CLLEGADTO cLLEGADTO = cLLEGAMapper.toDto(cLLEGA);
        restCLLEGAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cLLEGADTO)))
            .andExpect(status().isCreated());

        // Validate the CLLEGA in the database
        List<CLLEGA> cLLEGAList = cLLEGARepository.findAll();
        assertThat(cLLEGAList).hasSize(databaseSizeBeforeCreate + 1);
        CLLEGA testCLLEGA = cLLEGAList.get(cLLEGAList.size() - 1);
        assertThat(testCLLEGA.getOpzione()).isEqualTo(DEFAULT_OPZIONE);
        assertThat(testCLLEGA.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
    }

    @Test
    @Transactional
    void createCLLEGAWithExistingId() throws Exception {
        // Create the CLLEGA with an existing ID
        cLLEGA.setId(1L);
        CLLEGADTO cLLEGADTO = cLLEGAMapper.toDto(cLLEGA);

        int databaseSizeBeforeCreate = cLLEGARepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCLLEGAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cLLEGADTO)))
            .andExpect(status().isBadRequest());

        // Validate the CLLEGA in the database
        List<CLLEGA> cLLEGAList = cLLEGARepository.findAll();
        assertThat(cLLEGAList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOpzioneIsRequired() throws Exception {
        int databaseSizeBeforeTest = cLLEGARepository.findAll().size();
        // set the field null
        cLLEGA.setOpzione(null);

        // Create the CLLEGA, which fails.
        CLLEGADTO cLLEGADTO = cLLEGAMapper.toDto(cLLEGA);

        restCLLEGAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cLLEGADTO)))
            .andExpect(status().isBadRequest());

        List<CLLEGA> cLLEGAList = cLLEGARepository.findAll();
        assertThat(cLLEGAList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCLLEGAS() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        // Get all the cLLEGAList
        restCLLEGAMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cLLEGA.getId().intValue())))
            .andExpect(jsonPath("$.[*].opzione").value(hasItem(DEFAULT_OPZIONE)))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE)));
    }

    @Test
    @Transactional
    void getCLLEGA() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        // Get the cLLEGA
        restCLLEGAMockMvc
            .perform(get(ENTITY_API_URL_ID, cLLEGA.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cLLEGA.getId().intValue()))
            .andExpect(jsonPath("$.opzione").value(DEFAULT_OPZIONE))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE));
    }

    @Test
    @Transactional
    void getCLLEGASByIdFiltering() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        Long id = cLLEGA.getId();

        defaultCLLEGAShouldBeFound("id.equals=" + id);
        defaultCLLEGAShouldNotBeFound("id.notEquals=" + id);

        defaultCLLEGAShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCLLEGAShouldNotBeFound("id.greaterThan=" + id);

        defaultCLLEGAShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCLLEGAShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCLLEGASByOpzioneIsEqualToSomething() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        // Get all the cLLEGAList where opzione equals to DEFAULT_OPZIONE
        defaultCLLEGAShouldBeFound("opzione.equals=" + DEFAULT_OPZIONE);

        // Get all the cLLEGAList where opzione equals to UPDATED_OPZIONE
        defaultCLLEGAShouldNotBeFound("opzione.equals=" + UPDATED_OPZIONE);
    }

    @Test
    @Transactional
    void getAllCLLEGASByOpzioneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        // Get all the cLLEGAList where opzione not equals to DEFAULT_OPZIONE
        defaultCLLEGAShouldNotBeFound("opzione.notEquals=" + DEFAULT_OPZIONE);

        // Get all the cLLEGAList where opzione not equals to UPDATED_OPZIONE
        defaultCLLEGAShouldBeFound("opzione.notEquals=" + UPDATED_OPZIONE);
    }

    @Test
    @Transactional
    void getAllCLLEGASByOpzioneIsInShouldWork() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        // Get all the cLLEGAList where opzione in DEFAULT_OPZIONE or UPDATED_OPZIONE
        defaultCLLEGAShouldBeFound("opzione.in=" + DEFAULT_OPZIONE + "," + UPDATED_OPZIONE);

        // Get all the cLLEGAList where opzione equals to UPDATED_OPZIONE
        defaultCLLEGAShouldNotBeFound("opzione.in=" + UPDATED_OPZIONE);
    }

    @Test
    @Transactional
    void getAllCLLEGASByOpzioneIsNullOrNotNull() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        // Get all the cLLEGAList where opzione is not null
        defaultCLLEGAShouldBeFound("opzione.specified=true");

        // Get all the cLLEGAList where opzione is null
        defaultCLLEGAShouldNotBeFound("opzione.specified=false");
    }

    @Test
    @Transactional
    void getAllCLLEGASByOpzioneContainsSomething() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        // Get all the cLLEGAList where opzione contains DEFAULT_OPZIONE
        defaultCLLEGAShouldBeFound("opzione.contains=" + DEFAULT_OPZIONE);

        // Get all the cLLEGAList where opzione contains UPDATED_OPZIONE
        defaultCLLEGAShouldNotBeFound("opzione.contains=" + UPDATED_OPZIONE);
    }

    @Test
    @Transactional
    void getAllCLLEGASByOpzioneNotContainsSomething() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        // Get all the cLLEGAList where opzione does not contain DEFAULT_OPZIONE
        defaultCLLEGAShouldNotBeFound("opzione.doesNotContain=" + DEFAULT_OPZIONE);

        // Get all the cLLEGAList where opzione does not contain UPDATED_OPZIONE
        defaultCLLEGAShouldBeFound("opzione.doesNotContain=" + UPDATED_OPZIONE);
    }

    @Test
    @Transactional
    void getAllCLLEGASByDescrizioneIsEqualToSomething() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        // Get all the cLLEGAList where descrizione equals to DEFAULT_DESCRIZIONE
        defaultCLLEGAShouldBeFound("descrizione.equals=" + DEFAULT_DESCRIZIONE);

        // Get all the cLLEGAList where descrizione equals to UPDATED_DESCRIZIONE
        defaultCLLEGAShouldNotBeFound("descrizione.equals=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    void getAllCLLEGASByDescrizioneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        // Get all the cLLEGAList where descrizione not equals to DEFAULT_DESCRIZIONE
        defaultCLLEGAShouldNotBeFound("descrizione.notEquals=" + DEFAULT_DESCRIZIONE);

        // Get all the cLLEGAList where descrizione not equals to UPDATED_DESCRIZIONE
        defaultCLLEGAShouldBeFound("descrizione.notEquals=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    void getAllCLLEGASByDescrizioneIsInShouldWork() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        // Get all the cLLEGAList where descrizione in DEFAULT_DESCRIZIONE or UPDATED_DESCRIZIONE
        defaultCLLEGAShouldBeFound("descrizione.in=" + DEFAULT_DESCRIZIONE + "," + UPDATED_DESCRIZIONE);

        // Get all the cLLEGAList where descrizione equals to UPDATED_DESCRIZIONE
        defaultCLLEGAShouldNotBeFound("descrizione.in=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    void getAllCLLEGASByDescrizioneIsNullOrNotNull() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        // Get all the cLLEGAList where descrizione is not null
        defaultCLLEGAShouldBeFound("descrizione.specified=true");

        // Get all the cLLEGAList where descrizione is null
        defaultCLLEGAShouldNotBeFound("descrizione.specified=false");
    }

    @Test
    @Transactional
    void getAllCLLEGASByDescrizioneContainsSomething() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        // Get all the cLLEGAList where descrizione contains DEFAULT_DESCRIZIONE
        defaultCLLEGAShouldBeFound("descrizione.contains=" + DEFAULT_DESCRIZIONE);

        // Get all the cLLEGAList where descrizione contains UPDATED_DESCRIZIONE
        defaultCLLEGAShouldNotBeFound("descrizione.contains=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    void getAllCLLEGASByDescrizioneNotContainsSomething() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        // Get all the cLLEGAList where descrizione does not contain DEFAULT_DESCRIZIONE
        defaultCLLEGAShouldNotBeFound("descrizione.doesNotContain=" + DEFAULT_DESCRIZIONE);

        // Get all the cLLEGAList where descrizione does not contain UPDATED_DESCRIZIONE
        defaultCLLEGAShouldBeFound("descrizione.doesNotContain=" + UPDATED_DESCRIZIONE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCLLEGAShouldBeFound(String filter) throws Exception {
        restCLLEGAMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cLLEGA.getId().intValue())))
            .andExpect(jsonPath("$.[*].opzione").value(hasItem(DEFAULT_OPZIONE)))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE)));

        // Check, that the count call also returns 1
        restCLLEGAMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCLLEGAShouldNotBeFound(String filter) throws Exception {
        restCLLEGAMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCLLEGAMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCLLEGA() throws Exception {
        // Get the cLLEGA
        restCLLEGAMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCLLEGA() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        int databaseSizeBeforeUpdate = cLLEGARepository.findAll().size();

        // Update the cLLEGA
        CLLEGA updatedCLLEGA = cLLEGARepository.findById(cLLEGA.getId()).get();
        // Disconnect from session so that the updates on updatedCLLEGA are not directly saved in db
        em.detach(updatedCLLEGA);
        updatedCLLEGA.opzione(UPDATED_OPZIONE).descrizione(UPDATED_DESCRIZIONE);
        CLLEGADTO cLLEGADTO = cLLEGAMapper.toDto(updatedCLLEGA);

        restCLLEGAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cLLEGADTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cLLEGADTO))
            )
            .andExpect(status().isOk());

        // Validate the CLLEGA in the database
        List<CLLEGA> cLLEGAList = cLLEGARepository.findAll();
        assertThat(cLLEGAList).hasSize(databaseSizeBeforeUpdate);
        CLLEGA testCLLEGA = cLLEGAList.get(cLLEGAList.size() - 1);
        assertThat(testCLLEGA.getOpzione()).isEqualTo(UPDATED_OPZIONE);
        assertThat(testCLLEGA.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    void putNonExistingCLLEGA() throws Exception {
        int databaseSizeBeforeUpdate = cLLEGARepository.findAll().size();
        cLLEGA.setId(count.incrementAndGet());

        // Create the CLLEGA
        CLLEGADTO cLLEGADTO = cLLEGAMapper.toDto(cLLEGA);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCLLEGAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cLLEGADTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cLLEGADTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CLLEGA in the database
        List<CLLEGA> cLLEGAList = cLLEGARepository.findAll();
        assertThat(cLLEGAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCLLEGA() throws Exception {
        int databaseSizeBeforeUpdate = cLLEGARepository.findAll().size();
        cLLEGA.setId(count.incrementAndGet());

        // Create the CLLEGA
        CLLEGADTO cLLEGADTO = cLLEGAMapper.toDto(cLLEGA);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCLLEGAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cLLEGADTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CLLEGA in the database
        List<CLLEGA> cLLEGAList = cLLEGARepository.findAll();
        assertThat(cLLEGAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCLLEGA() throws Exception {
        int databaseSizeBeforeUpdate = cLLEGARepository.findAll().size();
        cLLEGA.setId(count.incrementAndGet());

        // Create the CLLEGA
        CLLEGADTO cLLEGADTO = cLLEGAMapper.toDto(cLLEGA);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCLLEGAMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cLLEGADTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CLLEGA in the database
        List<CLLEGA> cLLEGAList = cLLEGARepository.findAll();
        assertThat(cLLEGAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCLLEGAWithPatch() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        int databaseSizeBeforeUpdate = cLLEGARepository.findAll().size();

        // Update the cLLEGA using partial update
        CLLEGA partialUpdatedCLLEGA = new CLLEGA();
        partialUpdatedCLLEGA.setId(cLLEGA.getId());

        partialUpdatedCLLEGA.descrizione(UPDATED_DESCRIZIONE);

        restCLLEGAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCLLEGA.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCLLEGA))
            )
            .andExpect(status().isOk());

        // Validate the CLLEGA in the database
        List<CLLEGA> cLLEGAList = cLLEGARepository.findAll();
        assertThat(cLLEGAList).hasSize(databaseSizeBeforeUpdate);
        CLLEGA testCLLEGA = cLLEGAList.get(cLLEGAList.size() - 1);
        assertThat(testCLLEGA.getOpzione()).isEqualTo(DEFAULT_OPZIONE);
        assertThat(testCLLEGA.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    void fullUpdateCLLEGAWithPatch() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        int databaseSizeBeforeUpdate = cLLEGARepository.findAll().size();

        // Update the cLLEGA using partial update
        CLLEGA partialUpdatedCLLEGA = new CLLEGA();
        partialUpdatedCLLEGA.setId(cLLEGA.getId());

        partialUpdatedCLLEGA.opzione(UPDATED_OPZIONE).descrizione(UPDATED_DESCRIZIONE);

        restCLLEGAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCLLEGA.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCLLEGA))
            )
            .andExpect(status().isOk());

        // Validate the CLLEGA in the database
        List<CLLEGA> cLLEGAList = cLLEGARepository.findAll();
        assertThat(cLLEGAList).hasSize(databaseSizeBeforeUpdate);
        CLLEGA testCLLEGA = cLLEGAList.get(cLLEGAList.size() - 1);
        assertThat(testCLLEGA.getOpzione()).isEqualTo(UPDATED_OPZIONE);
        assertThat(testCLLEGA.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    void patchNonExistingCLLEGA() throws Exception {
        int databaseSizeBeforeUpdate = cLLEGARepository.findAll().size();
        cLLEGA.setId(count.incrementAndGet());

        // Create the CLLEGA
        CLLEGADTO cLLEGADTO = cLLEGAMapper.toDto(cLLEGA);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCLLEGAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cLLEGADTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cLLEGADTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CLLEGA in the database
        List<CLLEGA> cLLEGAList = cLLEGARepository.findAll();
        assertThat(cLLEGAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCLLEGA() throws Exception {
        int databaseSizeBeforeUpdate = cLLEGARepository.findAll().size();
        cLLEGA.setId(count.incrementAndGet());

        // Create the CLLEGA
        CLLEGADTO cLLEGADTO = cLLEGAMapper.toDto(cLLEGA);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCLLEGAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cLLEGADTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CLLEGA in the database
        List<CLLEGA> cLLEGAList = cLLEGARepository.findAll();
        assertThat(cLLEGAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCLLEGA() throws Exception {
        int databaseSizeBeforeUpdate = cLLEGARepository.findAll().size();
        cLLEGA.setId(count.incrementAndGet());

        // Create the CLLEGA
        CLLEGADTO cLLEGADTO = cLLEGAMapper.toDto(cLLEGA);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCLLEGAMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cLLEGADTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CLLEGA in the database
        List<CLLEGA> cLLEGAList = cLLEGARepository.findAll();
        assertThat(cLLEGAList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCLLEGA() throws Exception {
        // Initialize the database
        cLLEGARepository.saveAndFlush(cLLEGA);

        int databaseSizeBeforeDelete = cLLEGARepository.findAll().size();

        // Delete the cLLEGA
        restCLLEGAMockMvc
            .perform(delete(ENTITY_API_URL_ID, cLLEGA.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CLLEGA> cLLEGAList = cLLEGARepository.findAll();
        assertThat(cLLEGAList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
