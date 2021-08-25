package it.adastra.profilglass.configuratore.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.adastra.profilglass.configuratore.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CLSTATFTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CLSTATF.class);
        CLSTATF cLSTATF1 = new CLSTATF();
        cLSTATF1.setId(1L);
        CLSTATF cLSTATF2 = new CLSTATF();
        cLSTATF2.setId(cLSTATF1.getId());
        assertThat(cLSTATF1).isEqualTo(cLSTATF2);
        cLSTATF2.setId(2L);
        assertThat(cLSTATF1).isNotEqualTo(cLSTATF2);
        cLSTATF1.setId(null);
        assertThat(cLSTATF1).isNotEqualTo(cLSTATF2);
    }
}
