package it.adastra.profilglass.configuratore.service.mapper;

import it.adastra.profilglass.configuratore.domain.CARCLFINI;
import it.adastra.profilglass.configuratore.service.dto.CARCLFINIDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link CARCLFINI} and its DTO {@link CARCLFINIDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CARCLFINIMapper extends EntityMapper<CARCLFINIDTO, CARCLFINI> {}
