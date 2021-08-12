package it.adastra.profiglass.configuratore;

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
            .importPackages("it.adastra.profiglass.configuratore");

        noClasses()
            .that()
            .resideInAnyPackage("it.adastra.profiglass.configuratore.service..")
            .or()
            .resideInAnyPackage("it.adastra.profiglass.configuratore.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..it.adastra.profiglass.configuratore.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
