package com.github.jinahya.spdx.license.data.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jinahya.spdx.license._ValidatorProducer;
import com.github.jinahya.spdx.license.util.ObjectIoUtils;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@AddBeanClasses({
        _ValidatorProducer.class
})
@EnableAutoWeld
@Slf4j
class LicensesTest {

    @Test
    void __() throws IOException, ClassNotFoundException {
        final var rootDirectory = Stream.concat(Stream.of(".", "src", "main", "resources"),
                                                Arrays.stream(getClass().getPackageName().split("\\.")))
                .map(Path::of)
                .reduce(Path::resolve)
                .orElseThrow()
                .toAbsolutePath()
                .normalize();
        log.debug("d: {}", rootDirectory);
        rootDirectory.toFile().mkdirs();
        // -------------------------------------------------------------------------------------------------------------
        final var mapper = new ObjectMapper().findAndRegisterModules();
        final Licenses licenses;
        {
            final var name = "/json/licenses.json";
            try (var resource = getClass().getResourceAsStream(name)) {
                assertThat(resource)
                        .as("resource for '%1$s'", name)
                        .isNotNull();
                licenses = mapper.reader().readValue(resource, Licenses.class);
                licenses.getLicenses().forEach(l -> {
                    assertThat(validator.validate(l))
                            .as("constraint violations of a licenses")
                            .isEmpty();
                });
                assertThat(validator.validate(licenses))
                        .as("constraint violations of licenses")
                        .isEmpty();
                {
                    final var file = rootDirectory.resolve(Licenses.NAME).toFile();
                    ObjectIoUtils.write(file, licenses);
                    assertThat((Licenses) ObjectIoUtils.read(file)).isEqualTo(licenses);
                }
            }
            final var instance = Licenses.getInstance();
            instance.getLicenses().forEach(l -> {
                assertThat(instance.getLicense(l.getLicenseId())).isSameAs(l);
            });
        }
        // ---------------------------------------------------------------------------------------------------------
        final var detailsDirectory = rootDirectory.resolve("details");
        detailsDirectory.toFile().mkdirs();
        for (var license : licenses.getLicenses()) {
            final var name = "/json/details/" + license.getLicenseId() + ".json";
            try (var resource = getClass().getResourceAsStream(name)) {
                assertThat(resource)
                        .as("detail resource for '%1$s'", name)
                        .isNotNull();
                license = mapper.reader().readValue(resource, License.class);
                final var file = detailsDirectory.resolve(license.getLicenseId() + ".bin").toFile();
                ObjectIoUtils.write(file, license);
                assertThat((License) ObjectIoUtils.read(file)).isEqualTo(license);
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Inject
    private Validator validator;
}