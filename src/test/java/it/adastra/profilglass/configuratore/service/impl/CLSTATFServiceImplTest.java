package it.adastra.profilglass.configuratore.service.impl;

import static org.junit.Assert.assertTrue;

import cart.test.CaratteristicaBean;
import com.deliveredtechnologies.rulebook.Fact;
import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.model.runner.RuleBookRunner;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/**
 * @author nodejs
 * chiaamre per ogni elemento della tabella stato fisico chiamare il rule engine che mi verifica se il vincolo con
 * la lega e il singolo stato fisico è soddisfatto
 * deve restituire un json con l a white list dei valori soddisfatti
 */
class CLSTATFServiceImplTest {

    @Test
    void test() {
        String CLLEGA = "3A";
        String CLSTATF = "H0F";
    }
}
