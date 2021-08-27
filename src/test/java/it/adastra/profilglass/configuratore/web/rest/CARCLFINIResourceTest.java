package it.adastra.profilglass.configuratore.web.rest;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import it.adastra.profilglass.configuratore.domain.CARCLFINI;
import it.adastra.profilglass.configuratore.service.dto.CARCLFINIDTO;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CARCLFINIResourceTest {

    CARCLFINIResource CLSTATFR = new CARCLFINIResource(null, null, null);

    @Test
    void testApplyRulesFinitura() {
        //fail("Not yet implemented");
    }

    @Test
    void testApplyRulesToFinitura() {
        List<CARCLFINIDTO> page = new ArrayList<CARCLFINIDTO>();
        CARCLFINIDTO tmp = new CARCLFINIDTO();
        tmp.setClasse("pippo");
        page.add(tmp);
        tmp = new CARCLFINIDTO();
        tmp.setClasse("HA1");
        page.add(tmp);

        CLSTATFR.applyRulesFinitura("3A", "H12", page);
        assertTrue(page.size() == 1);
    }
}
