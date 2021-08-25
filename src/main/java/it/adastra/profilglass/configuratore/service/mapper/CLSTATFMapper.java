package it.adastra.profilglass.configuratore.service.mapper;

import it.adastra.profilglass.configuratore.domain.CLSTATF;
import it.adastra.profilglass.configuratore.service.dto.CLSTATFDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link CLSTATF} and its DTO {@link CLSTATFDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CLSTATFMapper extends EntityMapper<CLSTATFDTO, CLSTATF> {}
