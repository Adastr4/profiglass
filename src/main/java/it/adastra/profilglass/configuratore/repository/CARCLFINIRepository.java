package it.adastra.profilglass.configuratore.repository;

import it.adastra.profilglass.configuratore.domain.CARCLFINI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CARCLFINI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CARCLFINIRepository extends JpaRepository<CARCLFINI, Long>, JpaSpecificationExecutor<CARCLFINI> {}
