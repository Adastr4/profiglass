package it.adastra.profilglass.configuratore.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import it.adastra.profilglass.configuratore.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CLSTATFDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CLSTATFDTO.class);
        CLSTATFDTO cLSTATFDTO1 = new CLSTATFDTO();
        cLSTATFDTO1.setId(1L);
        CLSTATFDTO cLSTATFDTO2 = new CLSTATFDTO();
        assertThat(cLSTATFDTO1).isNotEqualTo(cLSTATFDTO2);
        cLSTATFDTO2.setId(cLSTATFDTO1.getId());
        assertThat(cLSTATFDTO1).isEqualTo(cLSTATFDTO2);
        cLSTATFDTO2.setId(2L);
        assertThat(cLSTATFDTO1).isNotEqualTo(cLSTATFDTO2);
        cLSTATFDTO1.setId(null);
        assertThat(cLSTATFDTO1).isNotEqualTo(cLSTATFDTO2);
    }
}
