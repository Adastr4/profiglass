package it.adastra.profilglass.configuratore.web.rest;

import static org.junit.Assert.assertTrue;

import it.adastra.profilglass.configuratore.domain.CARCLFINI;
import it.adastra.profilglass.configuratore.service.dto.CLSTATFDTO;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CLSTATFResourceTest {

    CLSTATFResource CLSTATFR = new CLSTATFResource(null, null, null);

    @Test
    void testGetCLSTATF() {
        // fail("Not yet implemented");
    }

    @Test
    void testApplyRuleToLegafalse() {
        //    assertTrue(!CLSTATFR.applyRulesToLega("3A", "pippo"));
    }

    @Test
    void testApplyRuleToLegaTrue() {
        assertTrue(CLSTATFR.applyRulesToLega("3A", "HA1"));
    }

    @Test
    void testApplyRuleToLegaH0f() {
        // assertTrue(CLSTATFR.applyRulesToLega("3A", "H0F"));
    }

    @Test
    void testApplyRulesToLega() {
        List<CLSTATFDTO> page = new ArrayList<CLSTATFDTO>();
        CLSTATFDTO tmp = new CLSTATFDTO();
        tmp.setOpzione("pippo");
        page.add(tmp);
        tmp = new CLSTATFDTO();
        tmp.setOpzione("HA1");
        page.add(tmp);

        CLSTATFR.applyRules("3A", page);
        //    assertTrue(page.size() == 1);
    }
}
