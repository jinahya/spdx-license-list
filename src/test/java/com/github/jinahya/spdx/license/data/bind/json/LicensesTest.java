package com.github.jinahya.spdx.license.data.bind.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jinahya.spdx.license._JsonbProducer;
import com.github.jinahya.spdx.license._ValidatorProducer;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@AddBeanClasses({
        _JsonbProducer.class,
        _ValidatorProducer.class
})
@EnableAutoWeld
@Slf4j
class LicensesTest {

    @Test
    void __() throws IOException {
        final var name = "/json/licenses.json";
        try (var resource = getClass().getResourceAsStream(name)) {
            assertThat(resource)
                    .as("resource for '%1$s'", name)
                    .isNotNull();
            final var licenses = jsonb.fromJson(resource, Licenses.class);
            licenses.getLicenses().forEach(l -> {
                assertThat(validator.validate(l))
                        .as("constraint violations of a licenses")
                        .isEmpty();
            });
            assertThat(validator.validate(licenses))
                    .as("constraint violations of licenses")
                    .isEmpty();
            for (var license : licenses.getLicenses()) {
                final var detailName = "/json/details/" + license.getLicenseId() + ".json";
                try (var detailResource = getClass().getResourceAsStream(detailName)) {
                    assertThat(detailResource)
                            .as("detail resource for '%1$s'", detailName)
                            .isNotNull();
                    license = jsonb.fromJson(detailResource, License.class);
                }
            }
        }
        final var dot = Path.of(".");
        log.debug("dot: {}", dot);
        log.debug("dot.absolute: {}", dot.toAbsolutePath().normalize());
        log.debug("dot.absolute.normalized: {}", dot.toAbsolutePath().normalize());
    }

    @Test
    void __2() throws IOException {
        final var name = "/json/licenses.json";
        try (var resource = getClass().getResourceAsStream(name)) {
            assertThat(resource)
                    .as("resource for '%1$s'", name)
                    .isNotNull();
            final var licenses = new ObjectMapper().reader().readValue(resource, Licenses.class);
            licenses.getLicenses().forEach(l -> {
                assertThat(validator.validate(l))
                        .as("constraint violations of a licenses")
                        .isEmpty();
            });
            assertThat(validator.validate(licenses))
                    .as("constraint violations of licenses")
                    .isEmpty();
            for (var license : licenses.getLicenses()) {
                final var detailName = "/json/details/" + license.getLicenseId() + ".json";
                try (var detailResource = getClass().getResourceAsStream(detailName)) {
                    assertThat(detailResource)
                            .as("detail resource for '%1$s'", detailName)
                            .isNotNull();
                    license = new ObjectMapper().reader().readValue(detailResource, License.class);
                }
            }
        }
        final var dot = Path.of(".");
        log.debug("dot: {}", dot);
        log.debug("dot.absolute: {}", dot.toAbsolutePath().normalize());
        log.debug("dot.absolute.normalized: {}", dot.toAbsolutePath().normalize());
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Inject
    private Jsonb jsonb;

    @Inject
    private Validator validator;
}