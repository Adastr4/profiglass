package it.adastra.profilglass.configuratore.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import it.adastra.profilglass.configuratore.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParametersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParametersDTO.class);
        ParametersDTO parametersDTO1 = new ParametersDTO();
        parametersDTO1.setId(1L);
        ParametersDTO parametersDTO2 = new ParametersDTO();
        assertThat(parametersDTO1).isNotEqualTo(parametersDTO2);
        parametersDTO2.setId(parametersDTO1.getId());
        assertThat(parametersDTO1).isEqualTo(parametersDTO2);
        parametersDTO2.setId(2L);
        assertThat(parametersDTO1).isNotEqualTo(parametersDTO2);
        parametersDTO1.setId(null);
        assertThat(parametersDTO1).isNotEqualTo(parametersDTO2);
    }
}
