package it.adastra.profilglass.configuratore.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.adastra.profilglass.configuratore.IntegrationTest;
import it.adastra.profilglass.configuratore.domain.Parameters;
import it.adastra.profilglass.configuratore.repository.ParametersRepository;
import it.adastra.profilglass.configuratore.service.criteria.ParametersCriteria;
import it.adastra.profilglass.configuratore.service.dto.ParametersDTO;
import it.adastra.profilglass.configuratore.service.mapper.ParametersMapper;
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
 * Integration tests for the {@link ParametersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParametersResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/parameters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParametersRepository parametersRepository;

    @Autowired
    private ParametersMapper parametersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParametersMockMvc;

    private Parameters parameters;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parameters createEntity(EntityManager em) {
        Parameters parameters = new Parameters().key(DEFAULT_KEY).value(DEFAULT_VALUE).description(DEFAULT_DESCRIPTION);
        return parameters;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parameters createUpdatedEntity(EntityManager em) {
        Parameters parameters = new Parameters().key(UPDATED_KEY).value(UPDATED_VALUE).description(UPDATED_DESCRIPTION);
        return parameters;
    }

    @BeforeEach
    public void initTest() {
        parameters = createEntity(em);
    }

    @Test
    @Transactional
    void createParameters() throws Exception {
        int databaseSizeBeforeCreate = parametersRepository.findAll().size();
        // Create the Parameters
        ParametersDTO parametersDTO = parametersMapper.toDto(parameters);
        restParametersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parametersDTO)))
            .andExpect(status().isCreated());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeCreate + 1);
        Parameters testParameters = parametersList.get(parametersList.size() - 1);
        assertThat(testParameters.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testParameters.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testParameters.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createParametersWithExistingId() throws Exception {
        // Create the Parameters with an existing ID
        parameters.setId(1L);
        ParametersDTO parametersDTO = parametersMapper.toDto(parameters);

        int databaseSizeBeforeCreate = parametersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parametersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametersRepository.findAll().size();
        // set the field null
        parameters.setKey(null);

        // Create the Parameters, which fails.
        ParametersDTO parametersDTO = parametersMapper.toDto(parameters);

        restParametersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parametersDTO)))
            .andExpect(status().isBadRequest());

        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametersRepository.findAll().size();
        // set the field null
        parameters.setValue(null);

        // Create the Parameters, which fails.
        ParametersDTO parametersDTO = parametersMapper.toDto(parameters);

        restParametersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parametersDTO)))
            .andExpect(status().isBadRequest());

        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllParameters() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList
        restParametersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parameters.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getParameters() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get the parameters
        restParametersMockMvc
            .perform(get(ENTITY_API_URL_ID, parameters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parameters.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getParametersByIdFiltering() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        Long id = parameters.getId();

        defaultParametersShouldBeFound("id.equals=" + id);
        defaultParametersShouldNotBeFound("id.notEquals=" + id);

        defaultParametersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultParametersShouldNotBeFound("id.greaterThan=" + id);

        defaultParametersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultParametersShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllParametersByKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList where key equals to DEFAULT_KEY
        defaultParametersShouldBeFound("key.equals=" + DEFAULT_KEY);

        // Get all the parametersList where key equals to UPDATED_KEY
        defaultParametersShouldNotBeFound("key.equals=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    void getAllParametersByKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList where key not equals to DEFAULT_KEY
        defaultParametersShouldNotBeFound("key.notEquals=" + DEFAULT_KEY);

        // Get all the parametersList where key not equals to UPDATED_KEY
        defaultParametersShouldBeFound("key.notEquals=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    void getAllParametersByKeyIsInShouldWork() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList where key in DEFAULT_KEY or UPDATED_KEY
        defaultParametersShouldBeFound("key.in=" + DEFAULT_KEY + "," + UPDATED_KEY);

        // Get all the parametersList where key equals to UPDATED_KEY
        defaultParametersShouldNotBeFound("key.in=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    void getAllParametersByKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList where key is not null
        defaultParametersShouldBeFound("key.specified=true");

        // Get all the parametersList where key is null
        defaultParametersShouldNotBeFound("key.specified=false");
    }

    @Test
    @Transactional
    void getAllParametersByKeyContainsSomething() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList where key contains DEFAULT_KEY
        defaultParametersShouldBeFound("key.contains=" + DEFAULT_KEY);

        // Get all the parametersList where key contains UPDATED_KEY
        defaultParametersShouldNotBeFound("key.contains=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    void getAllParametersByKeyNotContainsSomething() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList where key does not contain DEFAULT_KEY
        defaultParametersShouldNotBeFound("key.doesNotContain=" + DEFAULT_KEY);

        // Get all the parametersList where key does not contain UPDATED_KEY
        defaultParametersShouldBeFound("key.doesNotContain=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    void getAllParametersByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList where value equals to DEFAULT_VALUE
        defaultParametersShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the parametersList where value equals to UPDATED_VALUE
        defaultParametersShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllParametersByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList where value not equals to DEFAULT_VALUE
        defaultParametersShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the parametersList where value not equals to UPDATED_VALUE
        defaultParametersShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllParametersByValueIsInShouldWork() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultParametersShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the parametersList where value equals to UPDATED_VALUE
        defaultParametersShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllParametersByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList where value is not null
        defaultParametersShouldBeFound("value.specified=true");

        // Get all the parametersList where value is null
        defaultParametersShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllParametersByValueContainsSomething() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList where value contains DEFAULT_VALUE
        defaultParametersShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the parametersList where value contains UPDATED_VALUE
        defaultParametersShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllParametersByValueNotContainsSomething() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList where value does not contain DEFAULT_VALUE
        defaultParametersShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the parametersList where value does not contain UPDATED_VALUE
        defaultParametersShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllParametersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList where description equals to DEFAULT_DESCRIPTION
        defaultParametersShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the parametersList where description equals to UPDATED_DESCRIPTION
        defaultParametersShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllParametersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList where description not equals to DEFAULT_DESCRIPTION
        defaultParametersShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the parametersList where description not equals to UPDATED_DESCRIPTION
        defaultParametersShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllParametersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultParametersShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the parametersList where description equals to UPDATED_DESCRIPTION
        defaultParametersShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllParametersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList where description is not null
        defaultParametersShouldBeFound("description.specified=true");

        // Get all the parametersList where description is null
        defaultParametersShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllParametersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList where description contains DEFAULT_DESCRIPTION
        defaultParametersShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the parametersList where description contains UPDATED_DESCRIPTION
        defaultParametersShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllParametersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        // Get all the parametersList where description does not contain DEFAULT_DESCRIPTION
        defaultParametersShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the parametersList where description does not contain UPDATED_DESCRIPTION
        defaultParametersShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultParametersShouldBeFound(String filter) throws Exception {
        restParametersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parameters.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restParametersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultParametersShouldNotBeFound(String filter) throws Exception {
        restParametersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParametersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingParameters() throws Exception {
        // Get the parameters
        restParametersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewParameters() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();

        // Update the parameters
        Parameters updatedParameters = parametersRepository.findById(parameters.getId()).get();
        // Disconnect from session so that the updates on updatedParameters are not directly saved in db
        em.detach(updatedParameters);
        updatedParameters.key(UPDATED_KEY).value(UPDATED_VALUE).description(UPDATED_DESCRIPTION);
        ParametersDTO parametersDTO = parametersMapper.toDto(updatedParameters);

        restParametersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parametersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parametersDTO))
            )
            .andExpect(status().isOk());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
        Parameters testParameters = parametersList.get(parametersList.size() - 1);
        assertThat(testParameters.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testParameters.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testParameters.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingParameters() throws Exception {
        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();
        parameters.setId(count.incrementAndGet());

        // Create the Parameters
        ParametersDTO parametersDTO = parametersMapper.toDto(parameters);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParametersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parametersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parametersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParameters() throws Exception {
        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();
        parameters.setId(count.incrementAndGet());

        // Create the Parameters
        ParametersDTO parametersDTO = parametersMapper.toDto(parameters);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParametersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parametersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParameters() throws Exception {
        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();
        parameters.setId(count.incrementAndGet());

        // Create the Parameters
        ParametersDTO parametersDTO = parametersMapper.toDto(parameters);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParametersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parametersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParametersWithPatch() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();

        // Update the parameters using partial update
        Parameters partialUpdatedParameters = new Parameters();
        partialUpdatedParameters.setId(parameters.getId());

        partialUpdatedParameters.key(UPDATED_KEY).description(UPDATED_DESCRIPTION);

        restParametersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParameters.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParameters))
            )
            .andExpect(status().isOk());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
        Parameters testParameters = parametersList.get(parametersList.size() - 1);
        assertThat(testParameters.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testParameters.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testParameters.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateParametersWithPatch() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();

        // Update the parameters using partial update
        Parameters partialUpdatedParameters = new Parameters();
        partialUpdatedParameters.setId(parameters.getId());

        partialUpdatedParameters.key(UPDATED_KEY).value(UPDATED_VALUE).description(UPDATED_DESCRIPTION);

        restParametersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParameters.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParameters))
            )
            .andExpect(status().isOk());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
        Parameters testParameters = parametersList.get(parametersList.size() - 1);
        assertThat(testParameters.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testParameters.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testParameters.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingParameters() throws Exception {
        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();
        parameters.setId(count.incrementAndGet());

        // Create the Parameters
        ParametersDTO parametersDTO = parametersMapper.toDto(parameters);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParametersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parametersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parametersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParameters() throws Exception {
        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();
        parameters.setId(count.incrementAndGet());

        // Create the Parameters
        ParametersDTO parametersDTO = parametersMapper.toDto(parameters);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParametersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parametersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParameters() throws Exception {
        int databaseSizeBeforeUpdate = parametersRepository.findAll().size();
        parameters.setId(count.incrementAndGet());

        // Create the Parameters
        ParametersDTO parametersDTO = parametersMapper.toDto(parameters);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParametersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(parametersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parameters in the database
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParameters() throws Exception {
        // Initialize the database
        parametersRepository.saveAndFlush(parameters);

        int databaseSizeBeforeDelete = parametersRepository.findAll().size();

        // Delete the parameters
        restParametersMockMvc
            .perform(delete(ENTITY_API_URL_ID, parameters.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Parameters> parametersList = parametersRepository.findAll();
        assertThat(parametersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
