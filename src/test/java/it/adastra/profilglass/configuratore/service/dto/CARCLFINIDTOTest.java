package it.adastra.profilglass.configuratore.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import it.adastra.profilglass.configuratore.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CARCLFINIDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CARCLFINIDTO.class);
        CARCLFINIDTO cARCLFINIDTO1 = new CARCLFINIDTO();
        cARCLFINIDTO1.setId(1L);
        CARCLFINIDTO cARCLFINIDTO2 = new CARCLFINIDTO();
        assertThat(cARCLFINIDTO1).isNotEqualTo(cARCLFINIDTO2);
        cARCLFINIDTO2.setId(cARCLFINIDTO1.getId());
        assertThat(cARCLFINIDTO1).isEqualTo(cARCLFINIDTO2);
        cARCLFINIDTO2.setId(2L);
        assertThat(cARCLFINIDTO1).isNotEqualTo(cARCLFINIDTO2);
        cARCLFINIDTO1.setId(null);
        assertThat(cARCLFINIDTO1).isNotEqualTo(cARCLFINIDTO2);
    }
}
