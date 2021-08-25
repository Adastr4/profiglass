package it.adastra.profilglass.configuratore.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.adastra.profilglass.configuratore.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CLLEGATest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CLLEGA.class);
        CLLEGA cLLEGA1 = new CLLEGA();
        cLLEGA1.setId(1L);
        CLLEGA cLLEGA2 = new CLLEGA();
        cLLEGA2.setId(cLLEGA1.getId());
        assertThat(cLLEGA1).isEqualTo(cLLEGA2);
        cLLEGA2.setId(2L);
        assertThat(cLLEGA1).isNotEqualTo(cLLEGA2);
        cLLEGA1.setId(null);
        assertThat(cLLEGA1).isNotEqualTo(cLLEGA2);
    }
}
