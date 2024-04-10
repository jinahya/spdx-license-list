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
class _ExceptionsTest {

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
        final _Exceptions exceptions;
        {
            final var name = "/json/exceptions.json";
            try (var resource = getClass().getResourceAsStream(name)) {
                assertThat(resource)
                        .as("resource for '%1$s'", name)
                        .isNotNull();
                exceptions = mapper.reader().readValue(resource, _Exceptions.class);
                exceptions.getExceptions().forEach(l -> {
                    assertThat(validator.validate(l))
                            .as("constraint violations of a exceptions")
                            .isEmpty();
                });
                assertThat(validator.validate(exceptions))
                        .as("constraint violations of exceptions")
                        .isEmpty();
                {
                    final var file = rootDirectory.resolve(_Exceptions.NAME).toFile();
                    ObjectIoUtils.write(file, exceptions);
                    assertThat((_Exceptions) ObjectIoUtils.read(file)).isEqualTo(exceptions);
                }
            }
            final var instance = _Exceptions.getInstance();
            instance.getExceptions().forEach(l -> {
                assertThat(instance.getException(l.getLicenseExceptionId())).isSameAs(l);
            });
        }
        // ---------------------------------------------------------------------------------------------------------
        final var detailsDirectory = rootDirectory.resolve("exceptions");
        detailsDirectory.toFile().mkdirs();
        for (var exception : exceptions.getExceptions()) {
            final var name = "/json/exceptions/" + exception.getLicenseExceptionId() + ".json";
            try (var resource = getClass().getResourceAsStream(name)) {
                assertThat(resource)
                        .as("detail resource for '%1$s'", name)
                        .isNotNull();
                exception = mapper.reader().readValue(resource, _Exception.class);
                final var file = detailsDirectory.resolve(exception.getLicenseExceptionId() + ".bin").toFile();
                ObjectIoUtils.write(file, exception);
                assertThat((_Exception) ObjectIoUtils.read(file)).isEqualTo(exception);
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Inject
    private Validator validator;
}