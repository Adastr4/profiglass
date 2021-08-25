package it.adastra.profilglass.configuratore.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.adastra.profilglass.configuratore.IntegrationTest;
import it.adastra.profilglass.configuratore.domain.CLSTATF;
import it.adastra.profilglass.configuratore.repository.CLSTATFRepository;
import it.adastra.profilglass.configuratore.service.criteria.CLSTATFCriteria;
import it.adastra.profilglass.configuratore.service.dto.CLSTATFDTO;
import it.adastra.profilglass.configuratore.service.mapper.CLSTATFMapper;
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
 * Integration tests for the {@link CLSTATFResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CLSTATFResourceIT {

    private static final String DEFAULT_OPZIONE = "AAAAAAAAAA";
    private static final String UPDATED_OPZIONE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/clstatfs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CLSTATFRepository cLSTATFRepository;

    @Autowired
    private CLSTATFMapper cLSTATFMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCLSTATFMockMvc;

    private CLSTATF cLSTATF;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CLSTATF createEntity(EntityManager em) {
        CLSTATF cLSTATF = new CLSTATF().opzione(DEFAULT_OPZIONE).descrizione(DEFAULT_DESCRIZIONE);
        return cLSTATF;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CLSTATF createUpdatedEntity(EntityManager em) {
        CLSTATF cLSTATF = new CLSTATF().opzione(UPDATED_OPZIONE).descrizione(UPDATED_DESCRIZIONE);
        return cLSTATF;
    }

    @BeforeEach
    public void initTest() {
        cLSTATF = createEntity(em);
    }

    @Test
    @Transactional
    void createCLSTATF() throws Exception {
        int databaseSizeBeforeCreate = cLSTATFRepository.findAll().size();
        // Create the CLSTATF
        CLSTATFDTO cLSTATFDTO = cLSTATFMapper.toDto(cLSTATF);
        restCLSTATFMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cLSTATFDTO)))
            .andExpect(status().isCreated());

        // Validate the CLSTATF in the database
        List<CLSTATF> cLSTATFList = cLSTATFRepository.findAll();
        assertThat(cLSTATFList).hasSize(databaseSizeBeforeCreate + 1);
        CLSTATF testCLSTATF = cLSTATFList.get(cLSTATFList.size() - 1);
        assertThat(testCLSTATF.getOpzione()).isEqualTo(DEFAULT_OPZIONE);
        assertThat(testCLSTATF.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
    }

    @Test
    @Transactional
    void createCLSTATFWithExistingId() throws Exception {
        // Create the CLSTATF with an existing ID
        cLSTATF.setId(1L);
        CLSTATFDTO cLSTATFDTO = cLSTATFMapper.toDto(cLSTATF);

        int databaseSizeBeforeCreate = cLSTATFRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCLSTATFMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cLSTATFDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CLSTATF in the database
        List<CLSTATF> cLSTATFList = cLSTATFRepository.findAll();
        assertThat(cLSTATFList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOpzioneIsRequired() throws Exception {
        int databaseSizeBeforeTest = cLSTATFRepository.findAll().size();
        // set the field null
        cLSTATF.setOpzione(null);

        // Create the CLSTATF, which fails.
        CLSTATFDTO cLSTATFDTO = cLSTATFMapper.toDto(cLSTATF);

        restCLSTATFMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cLSTATFDTO)))
            .andExpect(status().isBadRequest());

        List<CLSTATF> cLSTATFList = cLSTATFRepository.findAll();
        assertThat(cLSTATFList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCLSTATFS() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        // Get all the cLSTATFList
        restCLSTATFMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cLSTATF.getId().intValue())))
            .andExpect(jsonPath("$.[*].opzione").value(hasItem(DEFAULT_OPZIONE)))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE)));
    }

    @Test
    @Transactional
    void getCLSTATF() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        // Get the cLSTATF
        restCLSTATFMockMvc
            .perform(get(ENTITY_API_URL_ID, cLSTATF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cLSTATF.getId().intValue()))
            .andExpect(jsonPath("$.opzione").value(DEFAULT_OPZIONE))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE));
    }

    @Test
    @Transactional
    void getCLSTATFSByIdFiltering() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        Long id = cLSTATF.getId();

        defaultCLSTATFShouldBeFound("id.equals=" + id);
        defaultCLSTATFShouldNotBeFound("id.notEquals=" + id);

        defaultCLSTATFShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCLSTATFShouldNotBeFound("id.greaterThan=" + id);

        defaultCLSTATFShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCLSTATFShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCLSTATFSByOpzioneIsEqualToSomething() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        // Get all the cLSTATFList where opzione equals to DEFAULT_OPZIONE
        defaultCLSTATFShouldBeFound("opzione.equals=" + DEFAULT_OPZIONE);

        // Get all the cLSTATFList where opzione equals to UPDATED_OPZIONE
        defaultCLSTATFShouldNotBeFound("opzione.equals=" + UPDATED_OPZIONE);
    }

    @Test
    @Transactional
    void getAllCLSTATFSByOpzioneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        // Get all the cLSTATFList where opzione not equals to DEFAULT_OPZIONE
        defaultCLSTATFShouldNotBeFound("opzione.notEquals=" + DEFAULT_OPZIONE);

        // Get all the cLSTATFList where opzione not equals to UPDATED_OPZIONE
        defaultCLSTATFShouldBeFound("opzione.notEquals=" + UPDATED_OPZIONE);
    }

    @Test
    @Transactional
    void getAllCLSTATFSByOpzioneIsInShouldWork() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        // Get all the cLSTATFList where opzione in DEFAULT_OPZIONE or UPDATED_OPZIONE
        defaultCLSTATFShouldBeFound("opzione.in=" + DEFAULT_OPZIONE + "," + UPDATED_OPZIONE);

        // Get all the cLSTATFList where opzione equals to UPDATED_OPZIONE
        defaultCLSTATFShouldNotBeFound("opzione.in=" + UPDATED_OPZIONE);
    }

    @Test
    @Transactional
    void getAllCLSTATFSByOpzioneIsNullOrNotNull() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        // Get all the cLSTATFList where opzione is not null
        defaultCLSTATFShouldBeFound("opzione.specified=true");

        // Get all the cLSTATFList where opzione is null
        defaultCLSTATFShouldNotBeFound("opzione.specified=false");
    }

    @Test
    @Transactional
    void getAllCLSTATFSByOpzioneContainsSomething() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        // Get all the cLSTATFList where opzione contains DEFAULT_OPZIONE
        defaultCLSTATFShouldBeFound("opzione.contains=" + DEFAULT_OPZIONE);

        // Get all the cLSTATFList where opzione contains UPDATED_OPZIONE
        defaultCLSTATFShouldNotBeFound("opzione.contains=" + UPDATED_OPZIONE);
    }

    @Test
    @Transactional
    void getAllCLSTATFSByOpzioneNotContainsSomething() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        // Get all the cLSTATFList where opzione does not contain DEFAULT_OPZIONE
        defaultCLSTATFShouldNotBeFound("opzione.doesNotContain=" + DEFAULT_OPZIONE);

        // Get all the cLSTATFList where opzione does not contain UPDATED_OPZIONE
        defaultCLSTATFShouldBeFound("opzione.doesNotContain=" + UPDATED_OPZIONE);
    }

    @Test
    @Transactional
    void getAllCLSTATFSByDescrizioneIsEqualToSomething() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        // Get all the cLSTATFList where descrizione equals to DEFAULT_DESCRIZIONE
        defaultCLSTATFShouldBeFound("descrizione.equals=" + DEFAULT_DESCRIZIONE);

        // Get all the cLSTATFList where descrizione equals to UPDATED_DESCRIZIONE
        defaultCLSTATFShouldNotBeFound("descrizione.equals=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    void getAllCLSTATFSByDescrizioneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        // Get all the cLSTATFList where descrizione not equals to DEFAULT_DESCRIZIONE
        defaultCLSTATFShouldNotBeFound("descrizione.notEquals=" + DEFAULT_DESCRIZIONE);

        // Get all the cLSTATFList where descrizione not equals to UPDATED_DESCRIZIONE
        defaultCLSTATFShouldBeFound("descrizione.notEquals=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    void getAllCLSTATFSByDescrizioneIsInShouldWork() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        // Get all the cLSTATFList where descrizione in DEFAULT_DESCRIZIONE or UPDATED_DESCRIZIONE
        defaultCLSTATFShouldBeFound("descrizione.in=" + DEFAULT_DESCRIZIONE + "," + UPDATED_DESCRIZIONE);

        // Get all the cLSTATFList where descrizione equals to UPDATED_DESCRIZIONE
        defaultCLSTATFShouldNotBeFound("descrizione.in=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    void getAllCLSTATFSByDescrizioneIsNullOrNotNull() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        // Get all the cLSTATFList where descrizione is not null
        defaultCLSTATFShouldBeFound("descrizione.specified=true");

        // Get all the cLSTATFList where descrizione is null
        defaultCLSTATFShouldNotBeFound("descrizione.specified=false");
    }

    @Test
    @Transactional
    void getAllCLSTATFSByDescrizioneContainsSomething() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        // Get all the cLSTATFList where descrizione contains DEFAULT_DESCRIZIONE
        defaultCLSTATFShouldBeFound("descrizione.contains=" + DEFAULT_DESCRIZIONE);

        // Get all the cLSTATFList where descrizione contains UPDATED_DESCRIZIONE
        defaultCLSTATFShouldNotBeFound("descrizione.contains=" + UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    void getAllCLSTATFSByDescrizioneNotContainsSomething() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        // Get all the cLSTATFList where descrizione does not contain DEFAULT_DESCRIZIONE
        defaultCLSTATFShouldNotBeFound("descrizione.doesNotContain=" + DEFAULT_DESCRIZIONE);

        // Get all the cLSTATFList where descrizione does not contain UPDATED_DESCRIZIONE
        defaultCLSTATFShouldBeFound("descrizione.doesNotContain=" + UPDATED_DESCRIZIONE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCLSTATFShouldBeFound(String filter) throws Exception {
        restCLSTATFMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cLSTATF.getId().intValue())))
            .andExpect(jsonPath("$.[*].opzione").value(hasItem(DEFAULT_OPZIONE)))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE)));

        // Check, that the count call also returns 1
        restCLSTATFMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCLSTATFShouldNotBeFound(String filter) throws Exception {
        restCLSTATFMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCLSTATFMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCLSTATF() throws Exception {
        // Get the cLSTATF
        restCLSTATFMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCLSTATF() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        int databaseSizeBeforeUpdate = cLSTATFRepository.findAll().size();

        // Update the cLSTATF
        CLSTATF updatedCLSTATF = cLSTATFRepository.findById(cLSTATF.getId()).get();
        // Disconnect from session so that the updates on updatedCLSTATF are not directly saved in db
        em.detach(updatedCLSTATF);
        updatedCLSTATF.opzione(UPDATED_OPZIONE).descrizione(UPDATED_DESCRIZIONE);
        CLSTATFDTO cLSTATFDTO = cLSTATFMapper.toDto(updatedCLSTATF);

        restCLSTATFMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cLSTATFDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cLSTATFDTO))
            )
            .andExpect(status().isOk());

        // Validate the CLSTATF in the database
        List<CLSTATF> cLSTATFList = cLSTATFRepository.findAll();
        assertThat(cLSTATFList).hasSize(databaseSizeBeforeUpdate);
        CLSTATF testCLSTATF = cLSTATFList.get(cLSTATFList.size() - 1);
        assertThat(testCLSTATF.getOpzione()).isEqualTo(UPDATED_OPZIONE);
        assertThat(testCLSTATF.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    void putNonExistingCLSTATF() throws Exception {
        int databaseSizeBeforeUpdate = cLSTATFRepository.findAll().size();
        cLSTATF.setId(count.incrementAndGet());

        // Create the CLSTATF
        CLSTATFDTO cLSTATFDTO = cLSTATFMapper.toDto(cLSTATF);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCLSTATFMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cLSTATFDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cLSTATFDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CLSTATF in the database
        List<CLSTATF> cLSTATFList = cLSTATFRepository.findAll();
        assertThat(cLSTATFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCLSTATF() throws Exception {
        int databaseSizeBeforeUpdate = cLSTATFRepository.findAll().size();
        cLSTATF.setId(count.incrementAndGet());

        // Create the CLSTATF
        CLSTATFDTO cLSTATFDTO = cLSTATFMapper.toDto(cLSTATF);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCLSTATFMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cLSTATFDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CLSTATF in the database
        List<CLSTATF> cLSTATFList = cLSTATFRepository.findAll();
        assertThat(cLSTATFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCLSTATF() throws Exception {
        int databaseSizeBeforeUpdate = cLSTATFRepository.findAll().size();
        cLSTATF.setId(count.incrementAndGet());

        // Create the CLSTATF
        CLSTATFDTO cLSTATFDTO = cLSTATFMapper.toDto(cLSTATF);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCLSTATFMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cLSTATFDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CLSTATF in the database
        List<CLSTATF> cLSTATFList = cLSTATFRepository.findAll();
        assertThat(cLSTATFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCLSTATFWithPatch() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        int databaseSizeBeforeUpdate = cLSTATFRepository.findAll().size();

        // Update the cLSTATF using partial update
        CLSTATF partialUpdatedCLSTATF = new CLSTATF();
        partialUpdatedCLSTATF.setId(cLSTATF.getId());

        partialUpdatedCLSTATF.opzione(UPDATED_OPZIONE);

        restCLSTATFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCLSTATF.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCLSTATF))
            )
            .andExpect(status().isOk());

        // Validate the CLSTATF in the database
        List<CLSTATF> cLSTATFList = cLSTATFRepository.findAll();
        assertThat(cLSTATFList).hasSize(databaseSizeBeforeUpdate);
        CLSTATF testCLSTATF = cLSTATFList.get(cLSTATFList.size() - 1);
        assertThat(testCLSTATF.getOpzione()).isEqualTo(UPDATED_OPZIONE);
        assertThat(testCLSTATF.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
    }

    @Test
    @Transactional
    void fullUpdateCLSTATFWithPatch() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        int databaseSizeBeforeUpdate = cLSTATFRepository.findAll().size();

        // Update the cLSTATF using partial update
        CLSTATF partialUpdatedCLSTATF = new CLSTATF();
        partialUpdatedCLSTATF.setId(cLSTATF.getId());

        partialUpdatedCLSTATF.opzione(UPDATED_OPZIONE).descrizione(UPDATED_DESCRIZIONE);

        restCLSTATFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCLSTATF.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCLSTATF))
            )
            .andExpect(status().isOk());

        // Validate the CLSTATF in the database
        List<CLSTATF> cLSTATFList = cLSTATFRepository.findAll();
        assertThat(cLSTATFList).hasSize(databaseSizeBeforeUpdate);
        CLSTATF testCLSTATF = cLSTATFList.get(cLSTATFList.size() - 1);
        assertThat(testCLSTATF.getOpzione()).isEqualTo(UPDATED_OPZIONE);
        assertThat(testCLSTATF.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    void patchNonExistingCLSTATF() throws Exception {
        int databaseSizeBeforeUpdate = cLSTATFRepository.findAll().size();
        cLSTATF.setId(count.incrementAndGet());

        // Create the CLSTATF
        CLSTATFDTO cLSTATFDTO = cLSTATFMapper.toDto(cLSTATF);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCLSTATFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cLSTATFDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cLSTATFDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CLSTATF in the database
        List<CLSTATF> cLSTATFList = cLSTATFRepository.findAll();
        assertThat(cLSTATFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCLSTATF() throws Exception {
        int databaseSizeBeforeUpdate = cLSTATFRepository.findAll().size();
        cLSTATF.setId(count.incrementAndGet());

        // Create the CLSTATF
        CLSTATFDTO cLSTATFDTO = cLSTATFMapper.toDto(cLSTATF);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCLSTATFMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cLSTATFDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CLSTATF in the database
        List<CLSTATF> cLSTATFList = cLSTATFRepository.findAll();
        assertThat(cLSTATFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCLSTATF() throws Exception {
        int databaseSizeBeforeUpdate = cLSTATFRepository.findAll().size();
        cLSTATF.setId(count.incrementAndGet());

        // Create the CLSTATF
        CLSTATFDTO cLSTATFDTO = cLSTATFMapper.toDto(cLSTATF);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCLSTATFMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cLSTATFDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CLSTATF in the database
        List<CLSTATF> cLSTATFList = cLSTATFRepository.findAll();
        assertThat(cLSTATFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCLSTATF() throws Exception {
        // Initialize the database
        cLSTATFRepository.saveAndFlush(cLSTATF);

        int databaseSizeBeforeDelete = cLSTATFRepository.findAll().size();

        // Delete the cLSTATF
        restCLSTATFMockMvc
            .perform(delete(ENTITY_API_URL_ID, cLSTATF.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CLSTATF> cLSTATFList = cLSTATFRepository.findAll();
        assertThat(cLSTATFList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
