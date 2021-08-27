package it.adastra.profilglass.configuratore.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParametersMapperTest {

    private ParametersMapper parametersMapper;

    @BeforeEach
    public void setUp() {
        parametersMapper = new ParametersMapperImpl();
    }
}
