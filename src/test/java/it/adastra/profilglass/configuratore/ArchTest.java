package it.adastra.profilglass.configuratore;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("it.adastra.profilglass.configuratore");

        noClasses()
            .that()
            .resideInAnyPackage("it.adastra.profilglass.configuratore.service..")
            .or()
            .resideInAnyPackage("it.adastra.profilglass.configuratore.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..it.adastra.profilglass.configuratore.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
