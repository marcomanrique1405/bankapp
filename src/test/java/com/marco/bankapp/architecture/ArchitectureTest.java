package com.marco.bankapp.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(
        packages = "com.marco.bankapp",
        importOptions = ImportOption.DoNotIncludeTests.class
)
class ArchitectureTest {

    @ArchTest
    static final ArchRule controllers_should_not_access_repositories_directly =
            noClasses()
                    .that().resideInAPackage("..controller..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..repository..")
                    .allowEmptyShould(true);

    @ArchTest
    static final ArchRule auth_internal_packages_should_not_be_accessed_from_other_modules =
            internalPackagesShouldNotBeAccessedFromOtherModules("auth");

    @ArchTest
    static final ArchRule customer_internal_packages_should_not_be_accessed_from_other_modules =
            internalPackagesShouldNotBeAccessedFromOtherModules("customer");

    @ArchTest
    static final ArchRule account_internal_packages_should_not_be_accessed_from_other_modules =
            internalPackagesShouldNotBeAccessedFromOtherModules("account");

    @ArchTest
    static final ArchRule transaction_internal_packages_should_not_be_accessed_from_other_modules =
            internalPackagesShouldNotBeAccessedFromOtherModules("transaction");

    @ArchTest
    static final ArchRule card_internal_packages_should_not_be_accessed_from_other_modules =
            internalPackagesShouldNotBeAccessedFromOtherModules("card");

    @ArchTest
    static final ArchRule audit_internal_packages_should_not_be_accessed_from_other_modules =
            internalPackagesShouldNotBeAccessedFromOtherModules("audit");

    private static ArchRule internalPackagesShouldNotBeAccessedFromOtherModules(String module) {
        return noClasses()
                .that().resideOutsideOfPackage(".." + module + "..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(
                        ".." + module + ".repository..",
                        ".." + module + ".entity.."
                );
    }
}