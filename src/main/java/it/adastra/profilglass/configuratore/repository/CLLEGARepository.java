package it.adastra.profilglass.configuratore.repository;

import it.adastra.profilglass.configuratore.domain.CLLEGA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CLLEGA entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CLLEGARepository extends JpaRepository<CLLEGA, Long>, JpaSpecificationExecutor<CLLEGA> {}
