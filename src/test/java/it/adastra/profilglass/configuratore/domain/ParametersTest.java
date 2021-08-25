package it.adastra.profilglass.configuratore.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.adastra.profilglass.configuratore.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParametersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parameters.class);
        Parameters parameters1 = new Parameters();
        parameters1.setId(1L);
        Parameters parameters2 = new Parameters();
        parameters2.setId(parameters1.getId());
        assertThat(parameters1).isEqualTo(parameters2);
        parameters2.setId(2L);
        assertThat(parameters1).isNotEqualTo(parameters2);
        parameters1.setId(null);
        assertThat(parameters1).isNotEqualTo(parameters2);
    }
}
