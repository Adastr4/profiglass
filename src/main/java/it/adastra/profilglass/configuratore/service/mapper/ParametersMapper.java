package it.adastra.profilglass.configuratore.service.mapper;

import it.adastra.profilglass.configuratore.domain.Parameters;
import it.adastra.profilglass.configuratore.service.dto.ParametersDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Parameters} and its DTO {@link ParametersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ParametersMapper extends EntityMapper<ParametersDTO, Parameters> {}
