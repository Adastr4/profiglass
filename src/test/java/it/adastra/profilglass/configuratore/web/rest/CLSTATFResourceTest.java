package it.adastra.profilglass.configuratore.web.rest;

import org.junit.jupiter.api.Test;

class CLSTATFResourceTest {

    CLSTATFResource CLSTATFR = new CLSTATFResource(null, null, null);

    @Test
    void testGetCLSTATF() {
        //fail("Not yet implemented");
    }

    @Test
    void testApplyRulesToLega() {
        CLSTATFR.applyRulesToLega("3A", "pippo");
    }
}
