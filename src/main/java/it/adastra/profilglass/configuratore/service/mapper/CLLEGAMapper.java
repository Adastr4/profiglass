package it.adastra.profilglass.configuratore.service.mapper;

import it.adastra.profilglass.configuratore.domain.*;
import it.adastra.profilglass.configuratore.service.dto.CLLEGADTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CLLEGA} and its DTO {@link CLLEGADTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CLLEGAMapper extends EntityMapper<CLLEGADTO, CLLEGA> {}
