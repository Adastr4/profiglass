package it.adastra.profilglass.configuratore.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import it.adastra.profilglass.configuratore.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CLLEGADTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CLLEGADTO.class);
        CLLEGADTO cLLEGADTO1 = new CLLEGADTO();
        cLLEGADTO1.setId(1L);
        CLLEGADTO cLLEGADTO2 = new CLLEGADTO();
        assertThat(cLLEGADTO1).isNotEqualTo(cLLEGADTO2);
        cLLEGADTO2.setId(cLLEGADTO1.getId());
        assertThat(cLLEGADTO1).isEqualTo(cLLEGADTO2);
        cLLEGADTO2.setId(2L);
        assertThat(cLLEGADTO1).isNotEqualTo(cLLEGADTO2);
        cLLEGADTO1.setId(null);
        assertThat(cLLEGADTO1).isNotEqualTo(cLLEGADTO2);
    }
}
