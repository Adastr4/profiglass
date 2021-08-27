package it.adastra.profilglass.configuratore.service.mapper;

import it.adastra.profilglass.configuratore.domain.*;
import it.adastra.profilglass.configuratore.service.dto.ParametersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Parameters} and its DTO {@link ParametersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ParametersMapper extends EntityMapper<ParametersDTO, Parameters> {}
