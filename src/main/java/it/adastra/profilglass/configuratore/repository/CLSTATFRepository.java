package it.adastra.profilglass.configuratore.repository;

import it.adastra.profilglass.configuratore.domain.CLSTATF;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CLSTATF entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CLSTATFRepository extends JpaRepository<CLSTATF, Long>, JpaSpecificationExecutor<CLSTATF> {}
