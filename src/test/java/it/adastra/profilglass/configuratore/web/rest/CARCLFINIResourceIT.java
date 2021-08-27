package it.adastra.profilglass.configuratore.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.adastra.profilglass.configuratore.IntegrationTest;
import it.adastra.profilglass.configuratore.domain.CARCLFINI;
import it.adastra.profilglass.configuratore.repository.CARCLFINIRepository;
import it.adastra.profilglass.configuratore.service.criteria.CARCLFINICriteria;
import it.adastra.profilglass.configuratore.service.dto.CARCLFINIDTO;
import it.adastra.profilglass.configuratore.service.mapper.CARCLFINIMapper;
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
 * Integration tests for the {@link CARCLFINIResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CARCLFINIResourceIT {

    private static final String DEFAULT_CLASSE = "AAAAAAAAAA";
    private static final String UPDATED_CLASSE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/carclfinis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CARCLFINIRepository cARCLFINIRepository;

    @Autowired
    private CARCLFINIMapper cARCLFINIMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCARCLFINIMockMvc;

    private CARCLFINI cARCLFINI;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CARCLFINI createEntity(EntityManager em) {
        CARCLFINI cARCLFINI = new CARCLFINI().classe(DEFAULT_CLASSE);
        return cARCLFINI;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CARCLFINI createUpdatedEntity(EntityManager em) {
        CARCLFINI cARCLFINI = new CARCLFINI().classe(UPDATED_CLASSE);
        return cARCLFINI;
    }

    @BeforeEach
    public void initTest() {
        cARCLFINI = createEntity(em);
    }

    @Test
    @Transactional
    void createCARCLFINI() throws Exception {
        int databaseSizeBeforeCreate = cARCLFINIRepository.findAll().size();
        // Create the CARCLFINI
        CARCLFINIDTO cARCLFINIDTO = cARCLFINIMapper.toDto(cARCLFINI);
        restCARCLFINIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cARCLFINIDTO)))
            .andExpect(status().isCreated());

        // Validate the CARCLFINI in the database
        List<CARCLFINI> cARCLFINIList = cARCLFINIRepository.findAll();
        assertThat(cARCLFINIList).hasSize(databaseSizeBeforeCreate + 1);
        CARCLFINI testCARCLFINI = cARCLFINIList.get(cARCLFINIList.size() - 1);
        assertThat(testCARCLFINI.getClasse()).isEqualTo(DEFAULT_CLASSE);
    }

    @Test
    @Transactional
    void createCARCLFINIWithExistingId() throws Exception {
        // Create the CARCLFINI with an existing ID
        cARCLFINI.setId(1L);
        CARCLFINIDTO cARCLFINIDTO = cARCLFINIMapper.toDto(cARCLFINI);

        int databaseSizeBeforeCreate = cARCLFINIRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCARCLFINIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cARCLFINIDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CARCLFINI in the database
        List<CARCLFINI> cARCLFINIList = cARCLFINIRepository.findAll();
        assertThat(cARCLFINIList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkClasseIsRequired() throws Exception {
        int databaseSizeBeforeTest = cARCLFINIRepository.findAll().size();
        // set the field null
        cARCLFINI.setClasse(null);

        // Create the CARCLFINI, which fails.
        CARCLFINIDTO cARCLFINIDTO = cARCLFINIMapper.toDto(cARCLFINI);

        restCARCLFINIMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cARCLFINIDTO)))
            .andExpect(status().isBadRequest());

        List<CARCLFINI> cARCLFINIList = cARCLFINIRepository.findAll();
        assertThat(cARCLFINIList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCARCLFINIS() throws Exception {
        // Initialize the database
        cARCLFINIRepository.saveAndFlush(cARCLFINI);

        // Get all the cARCLFINIList
        restCARCLFINIMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cARCLFINI.getId().intValue())))
            .andExpect(jsonPath("$.[*].classe").value(hasItem(DEFAULT_CLASSE)));
    }

    @Test
    @Transactional
    void getCARCLFINI() throws Exception {
        // Initialize the database
        cARCLFINIRepository.saveAndFlush(cARCLFINI);

        // Get the cARCLFINI
        restCARCLFINIMockMvc
            .perform(get(ENTITY_API_URL_ID, cARCLFINI.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cARCLFINI.getId().intValue()))
            .andExpect(jsonPath("$.classe").value(DEFAULT_CLASSE));
    }

    @Test
    @Transactional
    void getCARCLFINISByIdFiltering() throws Exception {
        // Initialize the database
        cARCLFINIRepository.saveAndFlush(cARCLFINI);

        Long id = cARCLFINI.getId();

        defaultCARCLFINIShouldBeFound("id.equals=" + id);
        defaultCARCLFINIShouldNotBeFound("id.notEquals=" + id);

        defaultCARCLFINIShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCARCLFINIShouldNotBeFound("id.greaterThan=" + id);

        defaultCARCLFINIShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCARCLFINIShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCARCLFINISByClasseIsEqualToSomething() throws Exception {
        // Initialize the database
        cARCLFINIRepository.saveAndFlush(cARCLFINI);

        // Get all the cARCLFINIList where classe equals to DEFAULT_CLASSE
        defaultCARCLFINIShouldBeFound("classe.equals=" + DEFAULT_CLASSE);

        // Get all the cARCLFINIList where classe equals to UPDATED_CLASSE
        defaultCARCLFINIShouldNotBeFound("classe.equals=" + UPDATED_CLASSE);
    }

    @Test
    @Transactional
    void getAllCARCLFINISByClasseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cARCLFINIRepository.saveAndFlush(cARCLFINI);

        // Get all the cARCLFINIList where classe not equals to DEFAULT_CLASSE
        defaultCARCLFINIShouldNotBeFound("classe.notEquals=" + DEFAULT_CLASSE);

        // Get all the cARCLFINIList where classe not equals to UPDATED_CLASSE
        defaultCARCLFINIShouldBeFound("classe.notEquals=" + UPDATED_CLASSE);
    }

    @Test
    @Transactional
    void getAllCARCLFINISByClasseIsInShouldWork() throws Exception {
        // Initialize the database
        cARCLFINIRepository.saveAndFlush(cARCLFINI);

        // Get all the cARCLFINIList where classe in DEFAULT_CLASSE or UPDATED_CLASSE
        defaultCARCLFINIShouldBeFound("classe.in=" + DEFAULT_CLASSE + "," + UPDATED_CLASSE);

        // Get all the cARCLFINIList where classe equals to UPDATED_CLASSE
        defaultCARCLFINIShouldNotBeFound("classe.in=" + UPDATED_CLASSE);
    }

    @Test
    @Transactional
    void getAllCARCLFINISByClasseIsNullOrNotNull() throws Exception {
        // Initialize the database
        cARCLFINIRepository.saveAndFlush(cARCLFINI);

        // Get all the cARCLFINIList where classe is not null
        defaultCARCLFINIShouldBeFound("classe.specified=true");

        // Get all the cARCLFINIList where classe is null
        defaultCARCLFINIShouldNotBeFound("classe.specified=false");
    }

    @Test
    @Transactional
    void getAllCARCLFINISByClasseContainsSomething() throws Exception {
        // Initialize the database
        cARCLFINIRepository.saveAndFlush(cARCLFINI);

        // Get all the cARCLFINIList where classe contains DEFAULT_CLASSE
        defaultCARCLFINIShouldBeFound("classe.contains=" + DEFAULT_CLASSE);

        // Get all the cARCLFINIList where classe contains UPDATED_CLASSE
        defaultCARCLFINIShouldNotBeFound("classe.contains=" + UPDATED_CLASSE);
    }

    @Test
    @Transactional
    void getAllCARCLFINISByClasseNotContainsSomething() throws Exception {
        // Initialize the database
        cARCLFINIRepository.saveAndFlush(cARCLFINI);

        // Get all the cARCLFINIList where classe does not contain DEFAULT_CLASSE
        defaultCARCLFINIShouldNotBeFound("classe.doesNotContain=" + DEFAULT_CLASSE);

        // Get all the cARCLFINIList where classe does not contain UPDATED_CLASSE
        defaultCARCLFINIShouldBeFound("classe.doesNotContain=" + UPDATED_CLASSE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCARCLFINIShouldBeFound(String filter) throws Exception {
        restCARCLFINIMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cARCLFINI.getId().intValue())))
            .andExpect(jsonPath("$.[*].classe").value(hasItem(DEFAULT_CLASSE)));

        // Check, that the count call also returns 1
        restCARCLFINIMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCARCLFINIShouldNotBeFound(String filter) throws Exception {
        restCARCLFINIMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCARCLFINIMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCARCLFINI() throws Exception {
        // Get the cARCLFINI
        restCARCLFINIMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCARCLFINI() throws Exception {
        // Initialize the database
        cARCLFINIRepository.saveAndFlush(cARCLFINI);

        int databaseSizeBeforeUpdate = cARCLFINIRepository.findAll().size();

        // Update the cARCLFINI
        CARCLFINI updatedCARCLFINI = cARCLFINIRepository.findById(cARCLFINI.getId()).get();
        // Disconnect from session so that the updates on updatedCARCLFINI are not directly saved in db
        em.detach(updatedCARCLFINI);
        updatedCARCLFINI.classe(UPDATED_CLASSE);
        CARCLFINIDTO cARCLFINIDTO = cARCLFINIMapper.toDto(updatedCARCLFINI);

        restCARCLFINIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cARCLFINIDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cARCLFINIDTO))
            )
            .andExpect(status().isOk());

        // Validate the CARCLFINI in the database
        List<CARCLFINI> cARCLFINIList = cARCLFINIRepository.findAll();
        assertThat(cARCLFINIList).hasSize(databaseSizeBeforeUpdate);
        CARCLFINI testCARCLFINI = cARCLFINIList.get(cARCLFINIList.size() - 1);
        assertThat(testCARCLFINI.getClasse()).isEqualTo(UPDATED_CLASSE);
    }

    @Test
    @Transactional
    void putNonExistingCARCLFINI() throws Exception {
        int databaseSizeBeforeUpdate = cARCLFINIRepository.findAll().size();
        cARCLFINI.setId(count.incrementAndGet());

        // Create the CARCLFINI
        CARCLFINIDTO cARCLFINIDTO = cARCLFINIMapper.toDto(cARCLFINI);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCARCLFINIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cARCLFINIDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cARCLFINIDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CARCLFINI in the database
        List<CARCLFINI> cARCLFINIList = cARCLFINIRepository.findAll();
        assertThat(cARCLFINIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCARCLFINI() throws Exception {
        int databaseSizeBeforeUpdate = cARCLFINIRepository.findAll().size();
        cARCLFINI.setId(count.incrementAndGet());

        // Create the CARCLFINI
        CARCLFINIDTO cARCLFINIDTO = cARCLFINIMapper.toDto(cARCLFINI);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCARCLFINIMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cARCLFINIDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CARCLFINI in the database
        List<CARCLFINI> cARCLFINIList = cARCLFINIRepository.findAll();
        assertThat(cARCLFINIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCARCLFINI() throws Exception {
        int databaseSizeBeforeUpdate = cARCLFINIRepository.findAll().size();
        cARCLFINI.setId(count.incrementAndGet());

        // Create the CARCLFINI
        CARCLFINIDTO cARCLFINIDTO = cARCLFINIMapper.toDto(cARCLFINI);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCARCLFINIMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cARCLFINIDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CARCLFINI in the database
        List<CARCLFINI> cARCLFINIList = cARCLFINIRepository.findAll();
        assertThat(cARCLFINIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCARCLFINIWithPatch() throws Exception {
        // Initialize the database
        cARCLFINIRepository.saveAndFlush(cARCLFINI);

        int databaseSizeBeforeUpdate = cARCLFINIRepository.findAll().size();

        // Update the cARCLFINI using partial update
        CARCLFINI partialUpdatedCARCLFINI = new CARCLFINI();
        partialUpdatedCARCLFINI.setId(cARCLFINI.getId());

        restCARCLFINIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCARCLFINI.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCARCLFINI))
            )
            .andExpect(status().isOk());

        // Validate the CARCLFINI in the database
        List<CARCLFINI> cARCLFINIList = cARCLFINIRepository.findAll();
        assertThat(cARCLFINIList).hasSize(databaseSizeBeforeUpdate);
        CARCLFINI testCARCLFINI = cARCLFINIList.get(cARCLFINIList.size() - 1);
        assertThat(testCARCLFINI.getClasse()).isEqualTo(DEFAULT_CLASSE);
    }

    @Test
    @Transactional
    void fullUpdateCARCLFINIWithPatch() throws Exception {
        // Initialize the database
        cARCLFINIRepository.saveAndFlush(cARCLFINI);

        int databaseSizeBeforeUpdate = cARCLFINIRepository.findAll().size();

        // Update the cARCLFINI using partial update
        CARCLFINI partialUpdatedCARCLFINI = new CARCLFINI();
        partialUpdatedCARCLFINI.setId(cARCLFINI.getId());

        partialUpdatedCARCLFINI.classe(UPDATED_CLASSE);

        restCARCLFINIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCARCLFINI.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCARCLFINI))
            )
            .andExpect(status().isOk());

        // Validate the CARCLFINI in the database
        List<CARCLFINI> cARCLFINIList = cARCLFINIRepository.findAll();
        assertThat(cARCLFINIList).hasSize(databaseSizeBeforeUpdate);
        CARCLFINI testCARCLFINI = cARCLFINIList.get(cARCLFINIList.size() - 1);
        assertThat(testCARCLFINI.getClasse()).isEqualTo(UPDATED_CLASSE);
    }

    @Test
    @Transactional
    void patchNonExistingCARCLFINI() throws Exception {
        int databaseSizeBeforeUpdate = cARCLFINIRepository.findAll().size();
        cARCLFINI.setId(count.incrementAndGet());

        // Create the CARCLFINI
        CARCLFINIDTO cARCLFINIDTO = cARCLFINIMapper.toDto(cARCLFINI);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCARCLFINIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cARCLFINIDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cARCLFINIDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CARCLFINI in the database
        List<CARCLFINI> cARCLFINIList = cARCLFINIRepository.findAll();
        assertThat(cARCLFINIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCARCLFINI() throws Exception {
        int databaseSizeBeforeUpdate = cARCLFINIRepository.findAll().size();
        cARCLFINI.setId(count.incrementAndGet());

        // Create the CARCLFINI
        CARCLFINIDTO cARCLFINIDTO = cARCLFINIMapper.toDto(cARCLFINI);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCARCLFINIMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cARCLFINIDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CARCLFINI in the database
        List<CARCLFINI> cARCLFINIList = cARCLFINIRepository.findAll();
        assertThat(cARCLFINIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCARCLFINI() throws Exception {
        int databaseSizeBeforeUpdate = cARCLFINIRepository.findAll().size();
        cARCLFINI.setId(count.incrementAndGet());

        // Create the CARCLFINI
        CARCLFINIDTO cARCLFINIDTO = cARCLFINIMapper.toDto(cARCLFINI);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCARCLFINIMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cARCLFINIDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CARCLFINI in the database
        List<CARCLFINI> cARCLFINIList = cARCLFINIRepository.findAll();
        assertThat(cARCLFINIList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCARCLFINI() throws Exception {
        // Initialize the database
        cARCLFINIRepository.saveAndFlush(cARCLFINI);

        int databaseSizeBeforeDelete = cARCLFINIRepository.findAll().size();

        // Delete the cARCLFINI
        restCARCLFINIMockMvc
            .perform(delete(ENTITY_API_URL_ID, cARCLFINI.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CARCLFINI> cARCLFINIList = cARCLFINIRepository.findAll();
        assertThat(cARCLFINIList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
