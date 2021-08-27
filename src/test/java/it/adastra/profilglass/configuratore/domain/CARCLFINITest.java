package it.adastra.profilglass.configuratore.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.adastra.profilglass.configuratore.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CARCLFINITest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CARCLFINI.class);
        CARCLFINI cARCLFINI1 = new CARCLFINI();
        cARCLFINI1.setId(1L);
        CARCLFINI cARCLFINI2 = new CARCLFINI();
        cARCLFINI2.setId(cARCLFINI1.getId());
        assertThat(cARCLFINI1).isEqualTo(cARCLFINI2);
        cARCLFINI2.setId(2L);
        assertThat(cARCLFINI1).isNotEqualTo(cARCLFINI2);
        cARCLFINI1.setId(null);
        assertThat(cARCLFINI1).isNotEqualTo(cARCLFINI2);
    }
}
