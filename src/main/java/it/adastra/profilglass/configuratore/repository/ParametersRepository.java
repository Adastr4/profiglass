package it.adastra.profilglass.configuratore.repository;

import it.adastra.profilglass.configuratore.domain.Parameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Parameters entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametersRepository extends JpaRepository<Parameters, Long>, JpaSpecificationExecutor<Parameters> {}
